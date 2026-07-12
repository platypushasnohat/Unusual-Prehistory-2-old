package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricFlyingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.FlyingPanicGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.IdleAnimationGoal;
import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothFlyingNavigation;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;

@SuppressWarnings("deprecation")
public class Pterodactylus extends PrehistoricFlyingMob implements Bucketable {

    private static final EntityDataAccessor<Boolean> HANGING = SynchedEntityData.defineId(Pterodactylus.class, EntityDataSerializers.BOOLEAN);

    private boolean validHangingPos = false;
    private int checkHangingTime;
    private BlockPos prevHangPos;
    public int timeHanging = 0;

    public final SmoothAnimationState hangIdleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState stretchAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState hangingStretchAnimationState = new SmoothAnimationState();

    public Pterodactylus(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.LEAVES, 0.0F);
        this.switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.01F)
                .add(Attributes.FLYING_SPEED, 0.7F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FlyingPanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_INSECTIVORE), true));
        this.goalSelector.addGoal(3, new PterodactylusFlyAndHangGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new IdleAnimationGoal(this, 40, 1, true, 0.001F, this::canStretch));
    }

    @Override
    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new PrehistoricMoveControl(this);
            this.navigation = this.createNavigation(this.level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new PrehistoricFlyingMoveControl(this, 16);
            SmoothFlyingNavigation flyingPathNavigation = new SmoothFlyingNavigation(this, this.level()){
                @Override
                public boolean isStableDestination(BlockPos blockPos) {
                    return !level().getBlockState(blockPos.below()).isAir();
                }
            };
            flyingPathNavigation.setCanOpenDoors(false);
            flyingPathNavigation.setCanFloat(false);
            flyingPathNavigation.setCanPassDoors(true);
            this.navigation = flyingPathNavigation;
            this.isLandNavigator = false;
        }
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() || this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    public boolean canHangFrom(BlockPos pos, BlockState state) {
        return state.isFaceSturdy(level(), pos, Direction.DOWN) && level().isEmptyBlock(pos.below()) && level().isEmptyBlock(pos.below(2));
    }

    public BlockPos posAbove() {
        return BlockPos.containing(this.getX(), this.getBoundingBox().maxY + 0.1F, this.getZ());
    }

    @Override
    protected void doPush(@NotNull Entity entity) {
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    protected @NotNull MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    @Override
    protected void doWaterSplashEffect() {
    }

    @Override
    public boolean refuseToLook() {
        return super.refuseToLook() || this.isHanging() || this.getIdleState() == 1;
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.isHanging() || this.getIdleState() == 1;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            this.tickHanging();
        }
    }

    private void tickHanging() {
        if (this.isHanging()) {
            BlockPos above = this.posAbove();
            if (checkHangingTime-- < 0 || this.getRandom().nextFloat() < 0.1F || prevHangPos != above) {
                this.validHangingPos = this.canHangFrom(above, this.level().getBlockState(above));
                this.checkHangingTime = 5 + this.getRandom().nextInt(5);
                this.prevHangPos = above;
            }
            if (validHangingPos) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.1F, 0.3F, 0.1F).add(0, 0.08D, 0));
            } else {
                this.setHanging(false);
                this.setFlying(true);
            }
            this.timeHanging++;
            if (this.isHanging() && timeHanging > 400) {
                this.setFlying(true);
                this.setHanging(false);
            }
        } else {
            this.timeHanging = 0;
            this.validHangingPos = false;
            this.prevHangPos = null;
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlying() && !this.isHanging(), this.tickCount);
        this.hangIdleAnimationState.animateWhen(!this.isFlying() && this.isHanging(), this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying() && !this.isRunning() && !this.isHanging(), this.tickCount);
        this.flyFastAnimationState.animateWhen(this.isFlying() && this.isRunning() && !this.isHanging(), this.tickCount);
        this.stretchAnimationState.animateWhen(this.getIdleState() == 1 && !this.isHanging(), this.tickCount);
        this.hangingStretchAnimationState.animateWhen(this.getIdleState() == 1 && this.isHanging(), this.tickCount);
    }

    private boolean canStretch(Entity entity) {
        if (entity instanceof Pterodactylus pterodactylus) {
            return (pterodactylus.isHanging() || !pterodactylus.isFlying()) && !pterodactylus.isInWaterOrBubble();
        }
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_INSECTIVORE);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HANGING, false);
    }

    public boolean isHanging() {
        return this.entityData.get(HANGING);
    }

    public void setHanging(boolean hanging) {
        this.entityData.set(HANGING, hanging);
    }

    @Override
    public boolean fromBucket() {
        return false;
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.JAWLESS_FISH_BUCKET.get());
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    @Override
    public void saveToBucketTag(@NotNull ItemStack bucket) {
        UP2MobUtils.savePrehistoricDataToBucket(this, bucket);
    }

    @Override
    public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
        UP2MobUtils.loadPrehistoricDataFromBucket(this, compoundTag);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == Items.FLOWER_POT && this.isAlive()) {
            playSound(SoundEvents.ITEM_PICKUP, 0.5F, 1.1F);
            itemstack.shrink(1);
            ItemStack pot = new ItemStack(UP2Items.PTERODACTYLUS_POT.get());
            this.saveToBucketTag(pot);
            if (!this.level().isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, pot);
            }
            if (itemstack.isEmpty() && !player.isCreative()) {
                player.setItemInHand(hand, pot);
            } else if (!player.getInventory().add(pot)) {
                player.drop(pot, false);
            }
            this.discard();
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.PARROT_EAT;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.PTERODACTYLUS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.PTERODACTYLUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.PTERODACTYLUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    }

    @Override
    protected float getSoundVolume() {
        return 0.5F;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        Pterodactylus baby = UP2Entities.PTERODACTYLUS.get().create(level);
        if (baby != null) {
            baby.setVariantRawId(this.inheritVariantFrom(mob, this.getRandom()));
        }
        return baby;
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/pterodactylus/pterodactylus_chocolate.png");
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.pickVariantForSpawn(level);
        return data;
    }

    // Goals
    private static class PterodactylusFlyAndHangGoal extends Goal {

        private final Pterodactylus pterodactylus;
        private boolean wantsToHang = false;
        private double x;
        private double y;
        private double z;
        private int hangCheck = 0;

        public PterodactylusFlyAndHangGoal(Pterodactylus pterodactylus) {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
            this.pterodactylus = pterodactylus;
        }

        @Override
        public boolean canUse() {
            if (pterodactylus.isVehicle() || (pterodactylus.getTarget() != null && pterodactylus.getTarget().isAlive()) || pterodactylus.isPassenger()) {
                return false;
            }
            if (pterodactylus.isHanging()) {
                return false;
            }
            if (!pterodactylus.isFlying() && pterodactylus.getRandom().nextInt(70) != 0) {
                return false;
            }
            this.wantsToHang = pterodactylus.flightTicks > 160;
            Vec3 target = this.getPosition();
            this.x = target.x;
            this.y = target.y;
            this.z = target.z;
            return true;
        }

        private Vec3 getPosition() {
            if (wantsToHang) {
                Vec3 hangPos = this.findHangFromPos();
                if (hangPos != null) {
                    return hangPos;
                }
            }
            return this.findFlightPos();
        }

        @Override
        public void start() {
            this.pterodactylus.setFlying(true);
            this.pterodactylus.setHanging(false);
            this.hangCheck = 0;
            this.pterodactylus.getNavigation().moveTo(this.x, this.y, this.z, 0.9F);
        }

        @Override
        public void tick() {
            if (wantsToHang) {
                if (hangCheck-- < 0) {
                    this.hangCheck = 5 + pterodactylus.getRandom().nextInt(5);
                    if (!pterodactylus.isHanging() && pterodactylus.canHangFrom(pterodactylus.posAbove(), pterodactylus.level().getBlockState(pterodactylus.posAbove()))) {
                        this.pterodactylus.setHanging(true);
                        this.pterodactylus.setFlying(false);
                    }
                }
            }
            if (pterodactylus.isFlying() && pterodactylus.onGround() && pterodactylus.flightTicks > 40) {
                this.pterodactylus.setFlying(false);
            }
        }

        @Override
        public boolean canContinueToUse() {
            if (wantsToHang) {
                return !pterodactylus.getNavigation().isDone() && !pterodactylus.isHanging();
            } else {
                return pterodactylus.isFlying() && !pterodactylus.getNavigation().isDone();
            }
        }

        @Override
        public void stop() {
            if (wantsToHang) {
                this.pterodactylus.getNavigation().stop();
            }
            this.wantsToHang = false;
        }

        private Vec3 findFlightPos() {
            int range = 13;
            Vec3 heightAdjusted = pterodactylus.position().add(pterodactylus.getRandom().nextInt(range * 2) - range, 0, pterodactylus.getRandom().nextInt(range * 2) - range);
            if (pterodactylus.level().canSeeSky(BlockPos.containing(heightAdjusted))) {
                Vec3 ground = groundPosition(heightAdjusted);
                heightAdjusted = new Vec3(heightAdjusted.x, ground.y + 4 + pterodactylus.getRandom().nextInt(3), heightAdjusted.z);
            } else {
                Vec3 ground = groundPosition(heightAdjusted);
                BlockPos ceiling = BlockPos.containing(ground).above(2);
                while (ceiling.getY() < pterodactylus.level().getMaxBuildHeight() && !pterodactylus.level().getBlockState(ceiling).isSolid()) {
                    ceiling = ceiling.above();
                }
                float randCeilVal = 0.3F + pterodactylus.getRandom().nextFloat() * 0.5F;
                heightAdjusted = new Vec3(heightAdjusted.x, ground.y + (ceiling.getY() - ground.y) * randCeilVal, heightAdjusted.z);
            }

            BlockHitResult result = pterodactylus.level().clip(new ClipContext(pterodactylus.getEyePosition(), heightAdjusted, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pterodactylus));
            if (result.getType() == HitResult.Type.MISS) {
                return heightAdjusted;
            } else {
                return result.getLocation();
            }
        }

        public Vec3 groundPosition(Vec3 airPosition) {
            BlockPos.MutableBlockPos ground = new BlockPos.MutableBlockPos();
            ground.set(airPosition.x, airPosition.y, airPosition.z);
            boolean flag = false;
            while (ground.getY() < pterodactylus.level().getMaxBuildHeight() && !pterodactylus.level().getBlockState(ground).isSolid() && pterodactylus.level().getFluidState(ground).isEmpty()){
                ground.move(0, 1, 0);
                flag = true;
            }
            ground.move(0, -1, 0);
            while (ground.getY() > pterodactylus.level().getMinBuildHeight() && !pterodactylus.level().getBlockState(ground).isSolid() && pterodactylus.level().getFluidState(ground).isEmpty()) {
                ground.move(0, -1, 0);
            }
            return Vec3.atCenterOf(flag ? ground.above() : ground.below());
        }

        public Vec3 findHangFromPos() {
            BlockPos blockpos = null;
            int range = 14;
            for (int i = 0; i < 15; i++) {
                BlockPos blockpos1 = pterodactylus.blockPosition().offset(pterodactylus.getRandom().nextInt(range) - range / 2, 0, pterodactylus.getRandom().nextInt(range) - range / 2);
                if (!pterodactylus.level().isEmptyBlock(blockpos1) || !pterodactylus.level().isLoaded(blockpos1)) {
                    continue;
                }
                while (this.pterodactylus.level().isEmptyBlock(blockpos1) && blockpos1.getY() < pterodactylus.level().getMaxBuildHeight()) {
                    blockpos1 = blockpos1.above();
                }
                if (blockpos1.getY() > pterodactylus.getY() - 1 && pterodactylus.canHangFrom(blockpos1, pterodactylus.level().getBlockState(blockpos1)) && this.clipHangTarget(blockpos1)) {
                    blockpos = blockpos1;
                }
            }
            return blockpos == null ? null : Vec3.atCenterOf(blockpos);
        }

        public boolean clipHangTarget(BlockPos in) {
            HitResult hitResult = pterodactylus.level().clip(new ClipContext(pterodactylus.getEyePosition(1.0F), new Vec3(in.getX() + 0.5, in.getY() + 0.5, in.getZ() + 0.5), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pterodactylus));
            if (hitResult instanceof BlockHitResult blockRayTraceResult) {
                BlockPos pos = blockRayTraceResult.getBlockPos();
                return pos.equals(in) || pterodactylus.level().isEmptyBlock(pos);
            }
            return true;
        }
    }
}