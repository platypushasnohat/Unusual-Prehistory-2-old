package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricFlyingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothFlyingNavigation;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricWallAttachingFlyingMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class Kimmeridgebrachypteraeschnidium extends PrehistoricWallAttachingFlyingMob implements Bucketable {

    private static final EntityDataAccessor<Integer> BASE_COLOR = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN_COLOR = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_PATTERN = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> WING_COLOR = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SWELL_DURATION = SynchedEntityData.defineId(Kimmeridgebrachypteraeschnidium.class, EntityDataSerializers.INT);

    private int oldSwell;
    private int swell;
    private final int maxSwell = 60;

    public final SmoothAnimationState attachedAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState preenAnimationState = new SmoothAnimationState(1.0F);

    public Kimmeridgebrachypteraeschnidium(EntityType<? extends PrehistoricWallAttachingFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15F)
                .add(Attributes.FLYING_SPEED, 0.75F)
                .add(Attributes.STEP_HEIGHT, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this) {
            @Override
            public boolean canUse() {
                return !Kimmeridgebrachypteraeschnidium.this.isBaby() && super.canUse();
            }
        });
        this.goalSelector.addGoal(1, new FlyingPanicGoal(this, 1.5D) {
            @Override
            public boolean canUse() {
                return !Kimmeridgebrachypteraeschnidium.this.isBaby() && super.canUse();
            }
        });
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_INSECTIVORE), false));
        this.goalSelector.addGoal(3, new LandOrAttachFromFlightGoal(this, 300) {
            @Override
            public boolean canUse() {
                return !Kimmeridgebrachypteraeschnidium.this.isBaby() && super.canUse();
            }
        });
        this.goalSelector.addGoal(4, new KimmeridgebrachypteraeschnidiumFlightGoal(this));
        this.goalSelector.addGoal(5, new FlyingRandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return !Kimmeridgebrachypteraeschnidium.this.isBaby() && super.canUse();
            }
        });
        this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 60, 1, true, 0.001F, this::canPreen));
        this.registerNymphGoals();
    }

    private void registerNymphGoals() {
        this.goalSelector.addGoal(0, new PrehistoricBabyPanicGoal(this, 1.7D, 10, 4, true));
        this.goalSelector.addGoal(3, new PrehistoricAvoidEntityGoal<>(this, Mob.class, 6.0F, 1.7D, true, this::avoidsMobs));
        this.goalSelector.addGoal(3, new PrehistoricAvoidEntityGoal<>(this, Player.class, 6.0F, 1.7D, true));
        this.goalSelector.addGoal(4, new PrehistoricWanderGoal(this, 1.0D, false) {
            @Override
            public boolean canUse() {
                return Kimmeridgebrachypteraeschnidium.this.isBaby() && super.canUse();
            }
        });
        this.goalSelector.addGoal(5, new KimmeridgebrachypteraeschnidiumFindWaterGoal(this));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return Kimmeridgebrachypteraeschnidium.this.isBaby() && super.canUse();
            }
        });
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F) {
            @Override
            public boolean canUse() {
                return Kimmeridgebrachypteraeschnidium.this.isBaby() && super.canUse();
            }
        });
    }

    @Override
    public void switchNavigator(boolean onLand) {
        if (this.isBaby()) {
            this.moveControl = new PrehistoricMoveControl(this);
            this.navigation = this.createNavigation(this.level());
            this.setPathfindingMalus(PathType.WATER, 0.0F);
            this.setPathfindingMalus(PathType.LEAVES, -1.0F);
        } else {
            if (onLand) {
                this.moveControl = new PrehistoricMoveControl(this);
                this.navigation = this.createNavigation(this.level());
                this.isLandNavigator = true;
            } else {
                this.moveControl = new PrehistoricFlyingMoveControl(this, 20);
                this.navigation = this.getFlyingPathNavigation();
                this.isLandNavigator = false;
            }
            this.setPathfindingMalus(PathType.WATER, 8.0F);
            this.setPathfindingMalus(PathType.LEAVES, 0.0F);
        }
    }

    private @NotNull PathNavigation getFlyingPathNavigation() {
        SmoothFlyingNavigation flyingPathNavigation = new SmoothFlyingNavigation(this, this.level()) {
            @Override
            public boolean isStableDestination(BlockPos blockPos) {
                return !level().getBlockState(blockPos.below()).isAir();
            }
        };
        flyingPathNavigation.setCanOpenDoors(false);
        flyingPathNavigation.setCanFloat(false);
        flyingPathNavigation.setCanPassDoors(true);
        return flyingPathNavigation;
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        if (this.isBaby()) {
            return super.getWalkTargetValue(pos, level);
        } else {
            return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
        }
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (!this.isBaby() && this.refuseToMove()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        if (this.isBaby() && this.isEffectiveAi() && this.isInWaterOrBubble()) {
            this.moveRelative(this.getSpeed(), travelVec);
            Vec3 delta = this.getDeltaMovement();
            this.move(MoverType.SELF, delta);
            if (this.jumping || horizontalCollision) {
                delta = delta.add(0, 0.1F, 0);
            } else {
                delta = delta.add(0, -0.05F, 0);
            }
            this.setDeltaMovement(delta.multiply(0.1D, 1.0D, 0.1D));
        }
        else {
            super.travel(travelVec);
        }
    }


    @Override
    public boolean canDrownInFluidType(@NotNull FluidType fluidType) {
        return fluidType != NeoForgeMod.WATER_TYPE.value();
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.setAirSupply(300);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_INSECTIVORE);
    }

    @Override
    protected boolean shouldUseStuckTicks() {
        return false;
    }

    public boolean avoidsMobs(LivingEntity entity) {
        return this.isBaby() && !(entity instanceof Kimmeridgebrachypteraeschnidium);
    }

    @Override
    public boolean canFly() {
        return !this.isBaby();
    }

    @Override
    public void tick() {
        super.tick();

        // It is imperative that the name be changed
        this.tickDraconoptera();

        if (this.level().isClientSide && this.isAlive() && !this.isBaby()) {
            UnusualPrehistory2.PROXY.playWorldSound(this, (byte) 1);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlying() && this.getIdleState() != 1 && !this.isAttachedToFace(), this.tickCount);
        this.hoverAnimationState.animateWhen(this.isFlying() && this.getDeltaMovement().length() <= 0.1D, this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying() && this.getDeltaMovement().length() > 0.1D, this.tickCount);
        this.preenAnimationState.animateWhen(!this.isFlying() && this.getIdleState() == 1, this.tickCount);
        this.attachedAnimationState.animateWhen(!this.isFlying() && this.isAttachedToFace(), this.tickCount);
    }

    private boolean canPreen(Entity entity) {
        if (entity instanceof Kimmeridgebrachypteraeschnidium dragonfly) {
            return dragonfly.onGround() && !dragonfly.isFlying() && !dragonfly.isAttachedToFace() && !dragonfly.isBaby();
        }
        return false;
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 800 + this.getRandom().nextInt(1200);
        } else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    private void tickDraconoptera() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            if (this.hasCustomName() && this.getName().getString().contains("draconoptera")) {
                this.setSwellDuration(1);
            }
            int i = this.getSwellDuration();
            if (i > 0 && this.swell == 0) {
                this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                this.gameEvent(GameEvent.PRIME_FUSE);
            }

            this.swell += i;
            if (this.swell < 0) {
                this.swell = 0;
            }

            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                this.explode();
            }
        }
    }

    private void explode() {
        if (!this.level().isClientSide) {
            this.dead = true;
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), (float) 7, Level.ExplosionInteraction.MOB);
            this.discard();
        }
    }

    public float getSwelling(float partialTicks) {
        return Mth.lerp(partialTicks, (float) this.oldSwell, (float) this.swell) / (float) (this.maxSwell - 2);
    }

    @Override
    public void remove(Entity.@NotNull RemovalReason removalReason) {
        UnusualPrehistory2.PROXY.clearSoundCacheFor(this);
        super.remove(removalReason);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == Items.GLASS_BOTTLE && this.isAlive() && !this.isBaby()) {
            playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH, 0.5F, 1.0F);
            itemstack.shrink(1);
            ItemStack bottle = new ItemStack(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get());
            this.saveToBucketTag(bottle);
            if (!this.level().isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bottle);
            }
            if (itemstack.isEmpty() && !player.isCreative()) {
                player.setItemInHand(hand, bottle);
            } else if (!player.getInventory().add(bottle)) {
                player.drop(bottle, false);
            }
            this.discard();
            return InteractionResult.SUCCESS;
        } else if (this.isBaby()) {
            return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
        }
        return super.mobInteract(player, hand);
    }

//    @Override
//    public boolean refuseToMove() {
//        return !this.isBaby() && super.refuseToMove() || this.getIdleState() == 1 || (this.onGround() && !this.isFlying());
//    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BASE_COLOR, 0);
        builder.define(PATTERN, 0);
        builder.define(PATTERN_COLOR, 0);
        builder.define(HAS_PATTERN, false);
        builder.define(WING_COLOR, 0);
        builder.define(SWELL_DURATION, -1);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("BaseColor", this.getBaseColor());
        compoundTag.putInt("Pattern", this.getPattern());
        compoundTag.putInt("PatternColor", this.getPatternColor());
        compoundTag.putInt("WingColor", this.getWingColor());
        compoundTag.putBoolean("HasPattern", this.hasPattern());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setBaseColor(compoundTag.getInt("BaseColor"));
        this.setPattern(compoundTag.getInt("Pattern"));
        this.setPatternColor(compoundTag.getInt("PatternColor"));
        this.setWingColor(compoundTag.getInt("WingColor"));
        this.setHasPattern(compoundTag.getBoolean("HasPattern"));
    }

    public static String getPatternName(int pattern) {
        return switch (pattern){
            case 1 -> "tailshade";
            case 2 -> "topshade";
            case 3 -> "halfshade";
            case 4 -> "large_stripe";
            case 5 -> "racing_stripe";
            case 6 -> "large_racing_stripe";
            default -> "stripe";
        };
    }

    public int getBaseColor() {
        return this.entityData.get(BASE_COLOR);
    }

    public void setBaseColor(int variant) {
        this.entityData.set(BASE_COLOR, Mth.clamp(variant, 0, 15));
    }

    public int getPattern() {
        return this.entityData.get(PATTERN);
    }

    public void setPattern(int variant) {
        this.entityData.set(PATTERN, Mth.clamp(variant, 0, 6));
    }

    public int getPatternColor() {
        return this.entityData.get(PATTERN_COLOR);
    }

    public void setPatternColor(int variant) {
        this.entityData.set(PATTERN_COLOR, Mth.clamp(variant, 0, 15));
    }

    public int getWingColor() {
        return this.entityData.get(WING_COLOR);
    }

    public void setWingColor(int variant) {
        this.entityData.set(WING_COLOR, Mth.clamp(variant, 0, 15));
    }

    public boolean hasPattern() {
        return this.entityData.get(HAS_PATTERN);
    }

    public void setHasPattern(boolean hasPattern) {
        this.entityData.set(HAS_PATTERN, hasPattern);
    }

    public int getSwellDuration() {
        return this.entityData.get(SWELL_DURATION);
    }

    public void setSwellDuration(int duration) {
        this.entityData.set(SWELL_DURATION, duration);
    }

    @Override
    public void setBaby(boolean baby) {
        super.setBaby(baby);
        if (baby) {
            this.switchNavigator(true);
        }
    }

    @Override
    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (!this.isBaby()) {
            this.switchNavigator(true);
        }
    }

    @Override
    protected void doPush(@NotNull Entity entity) {
        if (this.isBaby()) {
            super.doPush(entity);
        }
    }

    @Override
    protected void pushEntities() {
        if (this.isBaby()) {
            super.pushEntities();
        }
    }

    @Override
    protected void doWaterSplashEffect() {
    }

    @Override
    protected Entity.@NotNull MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
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
        if (this.isBaby()) {
            return new ItemStack(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET.get());
        } else {
            return new ItemStack(UP2Items.KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get());
        }
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        if (this.isBaby()) {
            return SoundEvents.BUCKET_FILL_FISH;
        } else {
            return SoundEvents.BOTTLE_FILL_DRAGONBREATH;
        }
    }

    @Override
    public void saveToBucketTag(@NotNull ItemStack bucket) {
        UP2MobUtils.savePrehistoricDataToBucket(this, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, (compoundTag) -> {
            compoundTag.putInt("BaseColor", this.getBaseColor());
            compoundTag.putInt("Pattern", this.getPattern());
            compoundTag.putInt("PatternColor", this.getPatternColor());
            compoundTag.putInt("WingColor", this.getWingColor());
            compoundTag.putBoolean("HasPattern", this.hasPattern());
        });
    }

    @Override
    public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
        UP2MobUtils.loadPrehistoricDataFromBucket(this, compoundTag);
        this.setBaseColor(compoundTag.getInt("BaseColor"));
        this.setPattern(compoundTag.getInt("Pattern"));
        this.setPatternColor(compoundTag.getInt("PatternColor"));
        this.setWingColor(compoundTag.getInt("WingColor"));
        this.setHasPattern(compoundTag.getBoolean("HasPattern"));
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        if (this.isBaby()) {
            this.playSound(SoundEvents.SILVERFISH_STEP, 0.05F, 1.8F);
        }
    }

    @Override
    protected float getSoundVolume() {
        return 0.5F;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return null;
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/kimmeridgebrachypteraeschnidium/base/base_0.png");
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        if (spawnType == MobSpawnType.BUCKET) {
            return data;
        } else {
            this.setBaseColor(level.getRandom().nextInt(16));
            this.setPattern(level.getRandom().nextInt(7));
            this.setPatternColor(level.getRandom().nextInt(16));
            this.setWingColor(level.getRandom().nextInt(16));
            this.setHasPattern(level.getRandom().nextInt(3) == 0);
        }
        this.switchNavigator(true);
        return data;
    }

    // Goals
    private static class KimmeridgebrachypteraeschnidiumFlightGoal extends AttachingFlyingWanderGoal {

        private final Kimmeridgebrachypteraeschnidium dragonfly;

        public KimmeridgebrachypteraeschnidiumFlightGoal(Kimmeridgebrachypteraeschnidium dragonfly) {
            super(dragonfly, 1.0F, 5);
            this.dragonfly = dragonfly;
        }

        @Override
        public boolean canUse() {
            if (dragonfly.isBaby()) {
                return false;
            } else if (dragonfly.isAttachedToFace()) {
                return false;
            } else if (dragonfly.isEepy() || dragonfly.isVehicle() || (dragonfly.getTarget() != null && dragonfly.getTarget().isAlive()) || dragonfly.getIdleState() != 0) {
                return false;
            } else if (pathCooldown-- > 0) {
                return false;
            } else if (dragonfly.canFly() && !dragonfly.isPassenger()) {
                if (!dragonfly.isFlying() && dragonfly.getRandom().nextInt(200) != 0 && dragonfly.fallDistance < 2.0F) {
                    return false;
                } else {
                    Vec3 target = this.findFlightPos();
                    this.x = target.x;
                    this.y = target.y;
                    this.z = target.z;
                    return true;
                }
            } else {
                return false;
            }
        }

        @Override
        public void start() {
            super.start();
            this.pathCooldown = 15 + dragonfly.getRandom().nextInt(12);
        }

        @Override
        protected Vec3 findFlightPos() {
            Vec3 target = dragonfly.position().add(dragonfly.getRandom().nextInt(7 * 2) - 7, 0, dragonfly.getRandom().nextInt(7 * 2) - 7);
            target = this.adjustFlightHeight(target);
            return this.clipFlightTarget(target);
        }
    }

    public static class KimmeridgebrachypteraeschnidiumFindWaterGoal extends MoveToBlockGoal {

        protected final Kimmeridgebrachypteraeschnidium dragonfly;

        public KimmeridgebrachypteraeschnidiumFindWaterGoal(Kimmeridgebrachypteraeschnidium dragonfly) {
            super(dragonfly, 1.0D, 16);
            this.dragonfly = dragonfly;
        }

        @Override
        public boolean canUse() {
            if (!dragonfly.isBaby()) {
                return false;
            } else if (dragonfly.isInWater()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        protected @NotNull BlockPos getMoveToTarget() {
            return this.blockPos;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !dragonfly.isInWater();
        }

        @Override
        protected boolean isValidTarget(LevelReader level, @NotNull BlockPos pos) {
            return level.getBlockState(pos).is(Blocks.WATER);
        }
    }
}
