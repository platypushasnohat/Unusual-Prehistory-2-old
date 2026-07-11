package com.barl_inc.unusual_prehistory.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.AquaticLeapGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.IdleAnimationGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.OpenWaterSwimGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricBabyPanicGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.AmbientMob;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMobPart;
import com.barl_inc.unusual_prehistory.entity.utils.LeapingMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
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
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;

public class Aegirocassis extends PrehistoricAquaticMob implements Bucketable, LeapingMob {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Aegirocassis.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SPAWN_CHILDREN_COOLDOWN = SynchedEntityData.defineId(Aegirocassis.class, EntityDataSerializers.INT);

    private static final float MAX_TILT = 40.0F;
    private static final float MAX_ROLL = 20.0F;
    private static final float ROLL_PER_YAW = 2.0F;

    private static final int IDLE_EAT = 1;

    private final AegirocassisPart headPart;
    private final AegirocassisPart tailPart1;
    private final AegirocassisPart tailPart2;
    private final AegirocassisPart[] allParts;

    private boolean wasPreviouslyBaby;

    @SuppressWarnings("FieldMayBeFinal")
    private float[] yawBuffer = new float[128];
    private int yawPointer = -1;
    private float fakeYRot;

    public float prevGlowProgress;
    public float glowProgress;

    public final SmoothAnimationState eyesAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState mouthAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState leapStartAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState leapAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState eatAnimationState = new SmoothAnimationState(1.0F);

    private int leapStartTicks;
    private int leapTicks;

    public Aegirocassis(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.switchShallowNavigation(false);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 4, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 6);
        this.headPart = new AegirocassisPart(this, 4.2F, 4.2F);
        this.tailPart1 = new AegirocassisPart(this, 4.2F, 4.2F);
        this.tailPart2 = new AegirocassisPart(this, 4.2F, 4.2F);
        this.allParts = new AegirocassisPart[]{headPart, tailPart1, tailPart2};
        this.fakeYRot = this.getYRot();
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 250.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6F)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PrehistoricBabyPanicGoal(this, 2.0D, 10, 4));
        this.goalSelector.addGoal(1, new AegirocassisTryToFlyGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new OpenWaterSwimGoal(this, 1.0D, 40, 30, 15, 3, true));
        this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 40, IDLE_EAT, false, 0.001F, this::canPlayIdles));
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return this.level().isNight() ? UP2MobUtils.getSurfacePathfindingFavor(pos, level) : UP2MobUtils.getDepthPathfindingFavor(pos, level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
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
    protected boolean shouldUseShallowNavigation() {
        return true;
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
    public PartEntity<?>[] getParts() {
        return allParts;
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(4, 6, 4);
    }

    @Override
    public void remove(RemovalReason removalReason) {
        UnusualPrehistory2.PROXY.clearSoundCacheFor(this);
        super.remove(removalReason);
        for (AegirocassisPart part : allParts) {
            part.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public boolean refuseToLook() {
        return super.refuseToLook() && this.getIdleState() != 2;
    }

    @Override
    public void tick() {
        this.tickMultipart();
        super.tick();
        this.tickRotations(MAX_TILT, MAX_ROLL, ROLL_PER_YAW);
        PrehistoricMobPart.pushEntities(this, allParts);
        if (!this.level().isClientSide) {
            PrehistoricMobPart.resolveCollisions(this, allParts);
        }

        this.prevGlowProgress = glowProgress;
        if (this.isInWaterOrBubble() && glowProgress < 5.0F) {
            this.glowProgress++;
        }
        else if (!this.isInWaterOrBubble() && glowProgress > 0.0F) {
            this.glowProgress--;
        }

        this.fakeYRot = Mth.approachDegrees(fakeYRot, yBodyRot, 10);

        if (wasPreviouslyBaby != this.isBaby()) {
            this.wasPreviouslyBaby = this.isBaby();
            this.refreshDimensions();
            for (AegirocassisPart aegirocassisPart : allParts) {
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

        if (this.level().isClientSide && this.isAlive() && this.isLeaping()) {
            UnusualPrehistory2.PROXY.playWorldSound(this, (byte) 2);
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
        this.eyesAnimationState.animateWhen(this.isAlive() && !this.isTryingToFly(), tickCount);
        this.mouthAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isTryingToFly() && this.getIdleState() != 2, tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isTryingToFly(), tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isTryingToFly(), tickCount);
        this.leapStartAnimationState.animateWhen(this.getPose() == UP2Poses.START_FLYING.get(), tickCount);
        this.leapAnimationState.animateWhen(this.getPose() == Pose.FALL_FLYING, tickCount);
        this.eatAnimationState.animateWhen(this.getIdleState() == IDLE_EAT, tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float) Mth.length(this.getX() - xo, this.getY() - yo, this.getZ() - zo);
        if (this.isBaby()) {
            this.updateWalkAnimation(f * 0.5F);
        } else {
            this.updateWalkAnimation(f);
        }
    }

    @Override
    protected void updateWalkAnimation(float partialTicks) {
        float speed;
        if (this.getDeltaMovement().lengthSqr() < 0.001D) {
            speed = 0.0F;
        } else {
            speed = Math.min(partialTicks * 22.5F, 1.0F);
        }
        this.walkAnimation.update(speed, 0.4F);
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
        if (idleState == IDLE_EAT) {
            return 1100 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (leapStartTicks > 0) {
            this.leapStartTicks--;
        }
        if (leapTicks > 0) {
            this.leapTicks--;
        }
        if (leapStartTicks == 0 && this.getPose() == UP2Poses.START_FLYING.get()) {
            this.setPose(Pose.FALL_FLYING);
        }
        if (leapTicks == 0 && this.getPose() == Pose.FALL_FLYING) {
            this.setPose(Pose.STANDING);
        }
        if (this.isInWaterOrBubble()) {
            if (this.getSpawnChildrenCooldown() > 0) {
                this.setSpawnChildrenCooldown(this.getSpawnChildrenCooldown() - 1);
            }
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_POSE.equals(key)) {
            if (this.getPose() == UP2Poses.START_FLYING.get()) {
                this.leapStartTicks = 120;
            }
            else if (this.getPose() == Pose.FALL_FLYING) {
                this.leapTicks = 60;
            }
        }
        super.onSyncedDataUpdated(key);
    }

    private void tickMultipart() {
        if (yawPointer == -1) {
            this.fakeYRot = yBodyRot;
            for (int i = 0; i < yawBuffer.length; i++) {
                this.yawBuffer[i] = fakeYRot;
            }
        }
        if (++yawPointer ==yawBuffer.length) {
            this.yawPointer = 0;
        }
        this.yawBuffer[yawPointer] = fakeYRot;

        Vec3[] vec3s = new Vec3[allParts.length];
        for (int j = 0; j < allParts.length; ++j) {
            vec3s[j] = new Vec3(allParts[j].getX(), allParts[j].getY(), allParts[j].getZ());
        }
        Vec3 center = this.position().add(0, this.getBbHeight() * 0.5F, 0);
        float headOffset = this.isBaby() ? 0.8F : 4.2F;
        float tailOffset = this.isBaby() ? 0.5F : 4.2F;
        this.headPart.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, headOffset), this.getXRot() * 0.45F, this.getYHeadRot()).add(center));
        this.tailPart1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -tailOffset), this.getXRot() * 0.45F, this.getYawFromBuffer(2, 1.0F)).add(center));
        this.tailPart2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -tailOffset), this.getXRot() * 0.45F, this.getYawFromBuffer(4, 1.0F)).add(tailPart1.centeredPosition()));
        for (int l = 0; l < allParts.length; ++l) {
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
        int i = yawPointer - pointer & 127;
        int j = yawPointer - pointer - 1 & 127;
        float d0 = yawBuffer[j];
        float d1 = yawBuffer[i] - d0;
        return d0 + d1 * partialTick;
    }

    private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
        return offset.xRot(-xRot * ((float) Math.PI / 180F)).yRot(-yRot * ((float) Math.PI / 180F));
    }

    public float getYawFromBuffer(int pointer, float partialTick) {
        int i = yawPointer - pointer & 127;
        int j = yawPointer - pointer - 1 & 127;
        float d0 = yawBuffer[j];
        float d1 = yawBuffer[i] - d0;
        return d0 + d1 * partialTick;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LEAPING, false);
        builder.define(SPAWN_CHILDREN_COOLDOWN, 70);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpawnChildrenCooldown", this.getSpawnChildrenCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSpawnChildrenCooldown(compoundTag.getInt("SpawnChildrenCooldown"));
    }

    @Override
    public boolean isLeaping() {
        return entityData.get(LEAPING);
    }
    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    public int getSpawnChildrenCooldown() {
        return entityData.get(SPAWN_CHILDREN_COOLDOWN);
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
    public ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.BABY_AEGIROCASSIS_BUCKET.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_EMPTY_FISH;
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {
        UP2MobUtils.savePrehistoricDataToBucket(this, bucket);
    }

    @Override
    public void loadFromBucketTag(CompoundTag compoundTag) {
        UP2MobUtils.loadPrehistoricDataFromBucket(this, compoundTag);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.isBaby()) {
            return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.EMPTY;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return UP2SoundEvents.AEGIROCASSIS_HURT.get();
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
    public int getAmbientSoundInterval() {
        return 200;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob otherParent) {
        return UP2Entities.AEGIROCASSIS.get().create(serverLevel);
    }

    @Override
    public float getSoundVolume() {
        return this.isBaby() ? 1.0F : 2.0F;
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

    private static class AegirocassisPart extends PrehistoricMobPart<Aegirocassis> {

        public AegirocassisPart(Aegirocassis parent, float width, float height) {
            super(parent, width, height);
        }
    }
}