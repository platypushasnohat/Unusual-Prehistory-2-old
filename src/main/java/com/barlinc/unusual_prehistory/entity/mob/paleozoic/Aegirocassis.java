package com.barlinc.unusual_prehistory.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.AquaticLeapGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.IdleAnimationGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricSwimGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothWaterNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.AmbientMob;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricPartEntity;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Aegirocassis extends PrehistoricAquaticMob implements Bucketable, LeapingMob {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Aegirocassis.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SPAWN_CHILDREN_COOLDOWN = SynchedEntityData.defineId(Aegirocassis.class, EntityDataSerializers.INT);

    private final AegirocassisPart headPart;
    private final AegirocassisPart tailPart1;
    private final AegirocassisPart tailPart2;
    private final AegirocassisPart[] allParts;

    private boolean wasPreviouslyBaby;

    @SuppressWarnings("all")
    private float[] yawBuffer = new float[128];
    private int yawPointer = -1;
    private float fakeYRot = 0;

    public float prevGlowProgress;
    public float glowProgress;

    public final SmoothAnimationState eyesAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState mouthAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState leapStartAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState leapAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState rollAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState eatAnimationState = new SmoothAnimationState(1.0F);

    private int leapStartTicks;
    private int leapTicks;

    public Aegirocassis(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.switchShallowNavigation(false);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 1000, 3, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 2);
        this.headPart = new AegirocassisPart(this, 3.5F, 3.9F);
        this.tailPart1 = new AegirocassisPart(this, 3.5F, 3.9F);
        this.tailPart2 = new AegirocassisPart(this, 3.5F, 3.9F);
        this.allParts = new AegirocassisPart[]{headPart, tailPart1, tailPart2};
        this.fakeYRot = this.getYRot();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 250.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.7F)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PrehistoricBabyPanicGoal(this, 2.0D, 10, 4));
        this.goalSelector.addGoal(1, new AegirocassisTryToFlyGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new PrehistoricSwimGoal(this, 1, 40, 30, 15, 3, true));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && Aegirocassis.this.isInWaterOrBubble();
            }
        });
        this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 80, 1, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 40, 2, false, 0.001F, this::canPlayIdles));
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return this.level().isNight() ? UP2MobUtils.getSurfacePathfindingFavor(pos, level) : UP2MobUtils.getDepthPathfindingFavor(pos, level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    protected void switchShallowNavigation(boolean inShallows) {
        this.navigation.stop();
        if (inShallows) {
            this.navigation = new SmoothAmphibiousNavigation(this, this.level());
            this.shallowWater = true;
        } else {
            this.navigation = new SmoothWaterNavigation(this, this.level(), true);
            this.shallowWater = false;
        }
    }

    @Override
    public boolean isPushable() {
        return this.isBaby();
    }

    @Override
    public boolean shouldFlop() {
        return false;
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.25F : 1.0F;
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?> @NotNull [] getParts() {
        return allParts;
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(4, 6, 4);
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        UnusualPrehistory2.PROXY.clearSoundCacheFor(this);
        super.remove(removalReason);
        if (allParts != null) {
            for (AegirocassisPart part : allParts) {
                part.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    public int getHeadRotSpeed() {
        return 4;
    }

    @Override
    public boolean refuseToLook() {
        return super.refuseToLook() && this.getIdleState() != 2;
    }

    @Override
    public void tick() {
        this.tickMultipart();
        super.tick();

        this.prevGlowProgress = glowProgress;
        if (this.isInWaterOrBubble() && glowProgress < 5.0F) glowProgress++;
        else if (!this.isInWaterOrBubble() && glowProgress > 0.0F) glowProgress--;

        this.fakeYRot = Mth.approachDegrees(fakeYRot, this.yBodyRot, 10);

        if (wasPreviouslyBaby != this.isBaby()) {
            this.wasPreviouslyBaby = this.isBaby();
            this.refreshDimensions();
            for (AegirocassisPart aegirocassisPart : this.allParts) {
                aegirocassisPart.refreshDimensions();
            }
        }

        if (this.getPose() == UP2Poses.START_FLYING.get() && leapStartTicks > 20) {
            if (!this.isInWaterOrBubble() && this.getDeltaMovement().y < 0.0) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0F, 0.3F, 1.0F));
            }
        }
        if (this.getPose() == Pose.FALL_FLYING && (this.isInWaterOrBubble() || this.onGround())) {
            this.setPose(Pose.STANDING);
        }

        if (this.level().isClientSide && this.isAlive() && this.isLeaping()) UnusualPrehistory2.PROXY.playWorldSound(this, (byte) 2);

        final boolean shallowWater = this.isInShallowWater();
        if (shallowWater && !this.shallowWater) {
            this.switchShallowNavigation(true);
        } else if (!shallowWater && this.shallowWater) {
            this.switchShallowNavigation(false);
        }

        if (this.getSpawnChildrenCooldown() == 0 && !this.isBaby()) {
            if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
                Entity entity = this.getRandom().nextBoolean() ? UP2Entities.AMPYX.get().create(serverLevel) : UP2Entities.SETAPEDITES.get().create(serverLevel);
                Vec3 vec3 = this.blockPosition().getCenter();
                if (entity instanceof AmbientMob mob) {
                    mob.setShouldBeRestricted(true);
                    entity.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(serverLevel.getRandom().nextFloat() * 360.0F), 0.0F);
                    serverLevel.addFreshEntity(entity);
                    EventHooks.finalizeMobSpawn(mob, serverLevel, serverLevel.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.NATURAL, null);
                }
            }
            this.setSpawnChildrenCooldown(2600 + this.level().getRandom().nextInt(1200));
        }
    }

    public float getGlowProgress(float partialTicks) {
        return (prevGlowProgress + (glowProgress - prevGlowProgress) * partialTicks) * 0.2F;
    }

    @Override
    public void setupAnimationStates() {
        this.eyesAnimationState.animateWhen(this.isAlive() && !this.isTryingToFly(), this.tickCount);
        this.mouthAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isTryingToFly() && this.getIdleState() != 2, this.tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isTryingToFly(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isTryingToFly(), this.tickCount);
        this.leapStartAnimationState.animateWhen(this.getPose() == UP2Poses.START_FLYING.get(), this.tickCount);
        this.leapAnimationState.animateWhen(this.getPose() == Pose.FALL_FLYING, this.tickCount);
        this.rollAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.eatAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isTryingToFly() {
        return this.getPose() == UP2Poses.START_FLYING.get() || this.getPose() == Pose.FALL_FLYING;
    }

    private boolean canPlayIdles(Entity entity) {
        return entity.isInWaterOrBubble() && !((Aegirocassis) entity).isLeaping();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 1100 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 2) {
            return 1200 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (leapStartTicks > 0) leapStartTicks--;
        if (leapTicks > 0) leapTicks--;
        if (leapStartTicks == 0 && this.getPose() == UP2Poses.START_FLYING.get()) this.setPose(Pose.FALL_FLYING);
        if (leapTicks == 0 && this.getPose() == Pose.FALL_FLYING) this.setPose(Pose.STANDING);
        if (this.isInWaterOrBubble()) {
            if (this.getSpawnChildrenCooldown() > 0) {
                this.setSpawnChildrenCooldown(this.getSpawnChildrenCooldown() - 1);
            }
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.START_FLYING.get()) {
                this.leapStartTicks = 120;
            }
            else if (this.getPose() == Pose.FALL_FLYING) {
                this.leapTicks = 60;
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    private void tickMultipart() {
        if (yawPointer == -1) {
            this.fakeYRot = this.yBodyRot;
            for (int i = 0; i < yawBuffer.length; i++) {
                this.yawBuffer[i] = this.fakeYRot;
            }
        }
        if (++this.yawPointer == this.yawBuffer.length) {
            this.yawPointer = 0;
        }
        this.yawBuffer[this.yawPointer] = this.fakeYRot;

        Vec3[] vec3s = new Vec3[this.allParts.length];
        for (int j = 0; j < this.allParts.length; ++j) {
            vec3s[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
        }
        Vec3 center = this.position().add(0, this.getBbHeight() * 0.5F, 0);
        float headOffset = this.isBaby() ? 0.8F : 4.0F;
        float tailOffset = this.isBaby() ? 0.5F : 4.0F;
        this.headPart.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, headOffset), this.getXRot() * 0.33F, this.getYHeadRot()).add(center));
        this.tailPart1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -tailOffset), this.getXRot() * 0.33F, this.getYawFromBuffer(2, 1.0F)).add(center));
        this.tailPart2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -tailOffset), this.getXRot() * 0.33F, this.getYawFromBuffer(4, 1.0F)).add(this.tailPart1.centeredPosition()));
        for (int l = 0; l < this.allParts.length; ++l) {
            this.allParts[l].xo = vec3s[l].x;
            this.allParts[l].yo = vec3s[l].y;
            this.allParts[l].zo = vec3s[l].z;
            this.allParts[l].xOld = vec3s[l].x;
            this.allParts[l].yOld = vec3s[l].y;
            this.allParts[l].zOld = vec3s[l].z;
        }
    }

    public float getTrailTransformation(int pointer, float partialTick) {
        if (this.isRemoved()) {
            partialTick = 1.0F;
        }
        int i = this.yawPointer - pointer & 127;
        int j = this.yawPointer - pointer - 1 & 127;
        float d0 = this.yawBuffer[j];
        float d1 = this.yawBuffer[i] - d0;
        return d0 + d1 * partialTick;
    }

    private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
        return offset.xRot(-xRot * ((float) Math.PI / 180F)).yRot(-yRot * ((float) Math.PI / 180F));
    }

    public float getYawFromBuffer(int pointer, float partialTick) {
        int i = this.yawPointer - pointer & 127;
        int j = this.yawPointer - pointer - 1 & 127;
        float d0 = this.yawBuffer[j];
        float d1 = this.yawBuffer[i] - d0;
        return d0 + d1 * partialTick;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LEAPING, false);
        builder.define(SPAWN_CHILDREN_COOLDOWN, 70);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpawnChildrenCooldown", this.getSpawnChildrenCooldown());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSpawnChildrenCooldown(compoundTag.getInt("SpawnChildrenCooldown"));
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    public int getSpawnChildrenCooldown() {
        return this.entityData.get(SPAWN_CHILDREN_COOLDOWN);
    }

    public void setSpawnChildrenCooldown(int cooldown) {
        this.entityData.set(SPAWN_CHILDREN_COOLDOWN, cooldown);
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
        return new ItemStack(UP2Items.BABY_AEGIROCASSIS_BUCKET.get());
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
        if (this.isBaby()) {
            return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
        }
        return super.mobInteract(player, hand);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.AEGIROCASSIS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.AEGIROCASSIS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.AEGIROCASSIS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return SoundEvents.EMPTY;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.AEGIROCASSIS.get().create(serverLevel);
    }

    @Override
    public float getSoundVolume() {
        return this.isBaby() ? 1.0F : 3.0F;
    }

    // Goals
    private static class AegirocassisTryToFlyGoal extends AquaticLeapGoal {

        private final Aegirocassis aegirocassis;

        public AegirocassisTryToFlyGoal(Aegirocassis aegirocassis) {
            super(aegirocassis, 20, 1.0D, 1.25D);
            this.aegirocassis = aegirocassis;
        }

        @Override
        public void start() {
            super.start();
            this.aegirocassis.setPose(UP2Poses.START_FLYING.get());
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !aegirocassis.isBaby();
        }

        @Override
        public boolean canContinueToUse() {
            Vec3 motion = aegirocassis.getDeltaMovement();
            if (!aegirocassis.isInWaterOrBubble() && motion.y < -0.1D) return false;
            if (aegirocassis.onGround()) return false;
            return aegirocassis.isLeaping();
        }

        @Override
        public void tick() {
            boolean flag = breached;
            this.aegirocassis.getNavigation().stop();
            if (!flag) {
                FluidState fluidstate = aegirocassis.level().getFluidState(aegirocassis.blockPosition());
                this.breached = fluidstate.is(FluidTags.WATER);
            }

            if (breached && !flag) {
                this.aegirocassis.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
            }

            Vec3 vec3 = aegirocassis.getDeltaMovement();
            Vec3 movement = new Vec3(aegirocassis.getMotionDirection().getStepX(), 0, aegirocassis.getMotionDirection().getStepZ()).normalize().scale(0.1F);
            Vec3 glide = new Vec3(movement.x, vec3.y, movement.z);
            this.aegirocassis.setDeltaMovement(glide);
            this.aegirocassis.setYRot(((float) Mth.atan2(aegirocassis.getMotionDirection().getStepZ(), aegirocassis.getMotionDirection().getStepX())) * Mth.RAD_TO_DEG - 90F);
        }
    }

    private static class AegirocassisPart extends PrehistoricPartEntity<Aegirocassis> {

        public AegirocassisPart(Aegirocassis parent, float width, float height) {
            super(parent, width, height);
        }
    }
}