package com.barl_inc.unusual_prehistory.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMobPart;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import com.google.common.annotations.VisibleForTesting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class Rhizodus extends PrehistoricAmphibiousMob {

    private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(Rhizodus.class, EntityDataSerializers.INT);

    private final RhizodusPart headPart;
    private final RhizodusPart tailPart1;
    private final RhizodusPart tailPart2;
    private final RhizodusPart[] allParts;

    @SuppressWarnings("all")
    private float[] yawBuffer = new float[128];
    private int yawPointer = -1;

    private boolean wasPreviouslyBaby;

    private int attackCooldown = 0;
    private int suctionCooldown = 0;

    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState landAttack1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState landAttack2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState suctionAttackAnimationState = new SmoothAnimationState();

    private boolean attackAlt = false;

    public Rhizodus(EntityType<? extends PrehistoricAmphibiousMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.switchNavigator(true);
        this.headPart = new RhizodusPart(this);
        this.tailPart1 = new RhizodusPart(this);
        this.tailPart2 = new RhizodusPart(this);
        this.allParts = new RhizodusPart[]{headPart, tailPart1, tailPart2};
        this.fixupDimensions();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new EnterWaterGoal(this, 1.0D, 100, true));
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new RhizodusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_CARNIVORE), false));
        this.goalSelector.addGoal(4, new AmphibiousWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new PrehistoricSwimGoal(this, 1.0D, 40, 30, 15, 3, true));
        this.goalSelector.addGoal(5, new RhizodusLookAtPlayerGoal(this, Player.class));
        this.goalSelector.addGoal(5, new RhizodusRandomLookGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, Mob.class, 200, true, true, this::canAttackTarget));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 200, true, true, this::canAttack));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.17F)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new PrehistoricMoveControl(this);
            this.lookControl = new PrehistoricLookControl(this);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new PrehistoricSwimmingMoveControl(this, 1000, 6, 0.6F, 0.5F);
            this.lookControl = new PrehistoricSwimmingLookControl(this, 4);
            this.isLandNavigator = false;
        }
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothAmphibiousNavigation(this, level);
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return this.isInWaterOrBubble() ? UP2MobUtils.getSurfacePathfindingFavor(pos, level) : super.getWalkTargetValue(pos, level);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVector = travelVector.multiply(0.0, 1.0, 0.0);
        }
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_CARNIVORE);
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isInWaterOrBubble() ? 1 : super.getMaxHeadXRot();
    }

    @Override
    public int getMaxHeadYRot() {
        return this.isInWaterOrBubble() ? 1 : 45;
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
        return this.getBoundingBox().inflate(3);
    }

    public boolean canAttackTarget(LivingEntity target) {
        if (target == null) {
            return false;
        }
        if (target.getType().is(UP2EntityTags.RHIZODUS_CANT_ATTACK)) {
            return false;
        }
        return (target.getBbWidth() < this.getBbWidth() && target.getBbHeight() < this.getBbHeight()) || target.getType().is(UP2EntityTags.RHIZODUS_CAN_ATTACK);
    }

    @Override
    public void tick() {
        this.tickMultipart();
        super.tick();
        final boolean ground = !this.isInWaterOrBubble();
        if (!ground && this.isLandNavigator) {
            this.switchNavigator(false);
        }
        if (ground && !this.isLandNavigator) {
            this.switchNavigator(true);
        }

        if (wasPreviouslyBaby != this.isBaby()) {
            this.wasPreviouslyBaby = this.isBaby();
            this.refreshDimensions();
            for (RhizodusPart rhizodusPart : this.allParts) {
                rhizodusPart.refreshDimensions();
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && this.getPose() != UP2Poses.ATTACKING.get() && this.getPose() != UP2Poses.GRABBING.get(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.isInWaterOrBubble() && this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.isInWaterOrBubble() && this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.landAttack1AnimationState.animateWhen(!this.isInWaterOrBubble() && this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.landAttack2AnimationState.animateWhen(!this.isInWaterOrBubble() && this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.suctionAttackAnimationState.animateWhen(this.getPose() == UP2Poses.GRABBING.get(), this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (SIZE.equals(key)) {
            this.refreshDimensions();
            this.setYRot(yHeadRot);
            this.yBodyRot = yHeadRot;
        }
        if (DATA_POSE.equals(key)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.attackAlt = this.getRandom().nextBoolean();
            }
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackCooldown > 0) {
            this.attackCooldown--;
        }
        if (suctionCooldown > 0) {
            this.suctionCooldown--;
        }
    }

    private void tickMultipart() {
        if (yawPointer == -1) {
            for (int i = 0; i < yawBuffer.length; i++) {
                this.yawBuffer[i] = this.yBodyRot;
            }
        }
        if (++this.yawPointer == this.yawBuffer.length) {
            this.yawPointer = 0;
        }
        this.yawBuffer[this.yawPointer] = this.yBodyRot;

        Vec3[] vec3s = new Vec3[this.allParts.length];
        for (int j = 0; j < this.allParts.length; ++j) {
            vec3s[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
        }

        Vec3 center = this.position().add(0, this.getBbHeight() * 0.5F, 0);
        float scale = 1.0F + (this.getRhizodusSize() * 0.05F);
        float offset = (this.isBaby() ? 0.85F : 1.7F) * scale;

        this.headPart.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, offset), this.getXRot(), this.getYHeadRot()).add(center));
        this.tailPart1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -offset), this.getXRot(), this.getYawFromBuffer(2, 1.0F)).add(center));
        this.tailPart2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -offset), this.getXRot(), this.getYawFromBuffer(4, 1.0F)).add(this.tailPart1.centeredPosition()));

        for (int l = 0; l < this.allParts.length; l++) {
            this.allParts[l].xo = vec3s[l].x;
            this.allParts[l].yo = vec3s[l].y;
            this.allParts[l].zo = vec3s[l].z;
            this.allParts[l].xOld = vec3s[l].x;
            this.allParts[l].yOld = vec3s[l].y;
            this.allParts[l].zOld = vec3s[l].z;
        }
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        super.remove(removalReason);
        if (allParts != null) {
            for (RhizodusPart part : allParts) {
                part.remove(RemovalReason.KILLED);
            }
        }
    }

    private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
        return offset.xRot(-xRot * ((float) Math.PI / 180F)).yRot(-yRot * ((float) Math.PI / 180F));
    }

    public float getYawFromBuffer(int pointer, float partialTicks) {
        int i = this.yawPointer - pointer & 127;
        int j = this.yawPointer - pointer - 1 & 127;
        float d0 = this.yawBuffer[j];
        float d1 = this.yawBuffer[i] - d0;
        return d0 + d1 * partialTicks;
    }

    @Override
    public boolean isPushable() {
        return this.getPose() != UP2Poses.GRABBING.get();
    }

    @Override
    public void refreshDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.refreshDimensions();
        for (RhizodusPart part : this.allParts) {
            part.refreshDimensions();
        }
        this.setPos(x, y, z);
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        EntityDimensions dimensions = super.getDefaultDimensions(pose);
        float scale = 1.0F + (this.getRhizodusSize() * 0.05F);
        return dimensions.scale(scale);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SIZE, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("RhizodusSize", this.getRhizodusSize());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setRhizodusSize(compoundTag.getInt("RhizodusSize"));
    }

    public int getRhizodusSize() {
        return this.entityData.get(SIZE);
    }

    @VisibleForTesting
    public void setRhizodusSize(int size) {
        int maxSize = Mth.clamp(size, 0, 15);
        this.entityData.set(SIZE, maxSize);
        this.reapplyPosition();
        this.refreshDimensions();
        Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(50 + this.getRhizodusSize() * 4.0F);
        Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(8 + this.getRhizodusSize());
        this.setHealth(this.getMaxHealth());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.RHIZODUS.get().create(level);
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.RHIZODUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.RHIZODUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.RHIZODUS_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public float getVoicePitch() {
        float scale = 1.0F + (this.getRhizodusSize() * 0.05F);
        return super.getVoicePitch() * (1.0F / scale) * 1.2F;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
        float cubed = level.getRandom().nextFloat();
        cubed = cubed * cubed * cubed;
        int size = Mth.floor(cubed * 16.0F);
        this.setRhizodusSize(size);
        return spawnData;
    }

    // Goals
    private static class RhizodusAttackGoal extends AttackGoal {

        private final Rhizodus rhizodus;

        public RhizodusAttackGoal(Rhizodus rhizodus) {
            super(rhizodus);
            this.rhizodus = rhizodus;
        }

        @Override
        public void tick() {
            LivingEntity target = rhizodus.getTarget();
            if (target != null) {
                double distance = rhizodus.distanceToSqr(target.getX(), target.getY(), target.getZ());
                this.rhizodus.lookAt(target, 30F, 30F);
                this.rhizodus.getLookControl().setLookAt(target, 30F, 30F);

                if (rhizodus.getAttackState() == 1) {
                    this.tickAttack(target);
                    this.rhizodus.getNavigation().stop();
                }
                else if (rhizodus.getAttackState() == 2) {
                    this.tickSuctionAttack(target);
                    this.rhizodus.getNavigation().stop();
                }
                else {
                    this.rhizodus.getNavigation().moveTo(target, 1.5D);

                    if (!target.isInWaterOrBubble() && rhizodus.horizontalCollision && rhizodus.isInWaterOrBubble() && rhizodus.tickCount % 2 == 0) {
                        float rot = rhizodus.getYRot() * ((float) Math.PI / 180F);
                        this.rhizodus.getNavigation().stop();
                        this.rhizodus.setDeltaMovement(rhizodus.getDeltaMovement().add(-Mth.sin(rot) * 0.3F, 0.2D, Mth.cos(rot) * 0.3F));
                    }

                    if (distance <= this.getAttackReachSqr(target, 1.5D) && rhizodus.attackCooldown == 0) {
                        this.rhizodus.setAttackState(1);
                    }
                    else if (distance <= this.getAttackReachSqr(target, 3.5D) && rhizodus.suctionCooldown == 0 && target.isInWaterOrBubble() && rhizodus.isInWaterOrBubble() && rhizodus.canAttackTarget(target)) {
                        this.rhizodus.setAttackState(2);
                    }
                }
            }
        }

        private void tickAttack(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.rhizodus.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 6) {
                if (this.isInAttackRange(target, 1.5D)) {
                    this.rhizodus.doHurtTarget(target);
                }
            }
            if (timer == 15) {
                this.rhizodus.setPose(Pose.STANDING);
            }
            if (timer > 20) {
                this.timer = 0;
                this.rhizodus.attackCooldown = 10;
                this.rhizodus.setAttackState(0);
            }
        }

        private void tickSuctionAttack(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.rhizodus.setPose(UP2Poses.GRABBING.get());
                this.rhizodus.playSound(SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, 1.25F, rhizodus.getVoicePitch());
            }
            if (timer > 10 && timer < 36 && rhizodus.hasLineOfSight(target)) {
                this.suctionNearbyEntities();
                this.spawnSuctionParticles();
            }
            if (timer == 36) {
                this.attackNearbyEntities();
            }
            if (timer > 45) {
                this.timer = 0;
                this.rhizodus.suctionCooldown = 60 + rhizodus.getRandom().nextInt(30);
                this.rhizodus.setPose(Pose.STANDING);
                this.rhizodus.setAttackState(0);
            }
        }

        private void spawnSuctionParticles() {
            if (rhizodus.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 3; i++) {
                    Vec3 vec3 = this.getMouthVec().add((rhizodus.getRandom().nextDouble() - 0.5D) * 0.8D, (rhizodus.getRandom().nextDouble() - 0.5D) * 0.8D, (rhizodus.getRandom().nextDouble() - 0.5D) * 0.8D);
                    Vec3 motion = this.getMouthVec().subtract(vec3).normalize();
                    serverLevel.sendParticles(ParticleTypes.BUBBLE, vec3.x, vec3.y, vec3.z, 1, motion.x, motion.y, motion.z, 0.1D);
                }
            }
        }

        private void suctionNearbyEntities() {
            AABB attackBox = rhizodus.getBoundingBox().move(rhizodus.getLookAngle().normalize().scale(2.0D)).inflate(4.0D);
            List<LivingEntity> nearbyEntities = rhizodus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), rhizodus, attackBox);
            if (!nearbyEntities.isEmpty()) {
                nearbyEntities.stream().filter(entity -> entity != rhizodus && entity.isInWaterOrBubble() && entity.getBbWidth() < rhizodus.getBbWidth() && entity.getBbHeight() < rhizodus.getBbHeight()).limit(3).forEach(entity -> {
                    Vec3 delta = this.getMouthVec().subtract(entity.position()).normalize().scale(0.08F);
                    entity.setDeltaMovement(entity.getDeltaMovement().add(delta));
                    entity.hurtMarked = true;
                });
            }
        }

        private void attackNearbyEntities() {
            AABB attackBox = rhizodus.getBoundingBox().move(rhizodus.getLookAngle().normalize().scale(2.0D)).inflate(1.5D);
            List<LivingEntity> nearbyEntities = rhizodus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), rhizodus, attackBox);
            if (!nearbyEntities.isEmpty()) {
                nearbyEntities.stream().filter(entity -> entity != rhizodus).limit(3).forEach(entity -> {
                    entity.hurt(entity.damageSources().mobAttack(rhizodus), (float) rhizodus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    this.strongKnockback(entity, 0.5D, 0.1D);
                    if (entity.isDamageSourceBlocked(rhizodus.damageSources().mobAttack(rhizodus)) && entity instanceof Player player) {
                        player.disableShield();
                    }
                });
            }
        }

        private Vec3 getMouthVec() {
            final Vec3 vec3 = new Vec3(0, rhizodus.getBbHeight() * 0.5F, rhizodus.getBbWidth() * 1.1F).xRot(rhizodus.getXRot() * Mth.DEG_TO_RAD).yRot(-rhizodus.getYRot() * Mth.DEG_TO_RAD);
            return rhizodus.position().add(vec3);
        }
    }

    private static class RhizodusLookAtPlayerGoal extends LookAtPlayerGoal {

        public RhizodusLookAtPlayerGoal(Mob mob, Class<? extends LivingEntity> lookAtType) {
            super(mob, lookAtType, 6.0F);
        }

        @Override
        public boolean canUse() {
            return !mob.isInWaterOrBubble() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !mob.isInWaterOrBubble() && super.canUse();
        }
    }

    private static class RhizodusRandomLookGoal extends RandomLookAroundGoal {

        private final Rhizodus rhizodus;

        public RhizodusRandomLookGoal(Rhizodus rhizodus) {
            super(rhizodus);
            this.rhizodus = rhizodus;
        }

        @Override
        public boolean canUse() {
            return !rhizodus.isInWaterOrBubble() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !rhizodus.isInWaterOrBubble() && super.canUse();
        }
    }

    private static class RhizodusPart extends PrehistoricMobPart<Rhizodus> {

        public RhizodusPart(Rhizodus parent) {
            super(parent, 1.7F, 1.8F);
        }
    }
}
