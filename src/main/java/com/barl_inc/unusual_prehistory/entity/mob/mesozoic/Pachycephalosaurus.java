package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.goals.EepyGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricNearestAttackableTargetGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricPanicGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricWanderGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.update_4.PachycephalosaurusAttackGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Particles;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class Pachycephalosaurus extends PrehistoricMob {

    private static final EntityDataAccessor<Integer> CHARGE_COOLDOWN = SynchedEntityData.defineId(Pachycephalosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FIGHT_COOLDOWN = SynchedEntityData.defineId(Pachycephalosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FIGHT_PARTNER = SynchedEntityData.defineId(Pachycephalosaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WANTS_TO_KILL = SynchedEntityData.defineId(Pachycephalosaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> FIND_TARGET_COOLDOWN = SynchedEntityData.defineId(Pachycephalosaurus.class, EntityDataSerializers.INT);

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState huffAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState grazeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState warnAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState recoverAnimationState = new SmoothAnimationState();

    private int recoverTicks;

    public Pachycephalosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 1.7D) {
            @Override
            public boolean canUse() {
                return super.canUse() && Pachycephalosaurus.this.isBaby() || Pachycephalosaurus.this.getHealth() <= Pachycephalosaurus.this.getMaxHealth() * 0.5F;
            }
        });
        this.goalSelector.addGoal(2, new PachycephalosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_HERBIVORE), false));
        this.goalSelector.addGoal(4, new PrehistoricWanderGoal(this, 1));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new EepyGoal(this));
//        this.goalSelector.addGoal(8, new PachycephalosaurusGrazeGoal(this));
//        this.goalSelector.addGoal(8, new PachycephalosaurusHuffGoal(this));
        this.targetSelector.addGoal(0, new PachycephalosaurusHurtByTargetGoal(this, Pachycephalosaurus.class));
        this.targetSelector.addGoal(1, new PachycephalosaurusTargetToKillGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.PACHYCEPHALOSAURUS_TARGETS_TO_KILL)));
        this.targetSelector.addGoal(2, new PachycephalosaurusTargetOthersGoal<>(this, Pachycephalosaurus.class));
        this.targetSelector.addGoal(3, new PachycephalosaurusNearestAttackableTargetGoal<>(this, LivingEntity.class, 500, true, true, entity -> entity.getType().is(UP2EntityTags.PACHYCEPHALOSAURUS_TARGETS)));
        this.targetSelector.addGoal(4, new PachycephalosaurusNearestAttackableTargetGoal<>(this, Player.class, 500, true, true, this::canAttack));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ARMOR, 4.0F)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_HERBIVORE);
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, 0.6D, this.getBbWidth() * 1.05F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    public boolean canChargeAtOtherPachycephalosaurus(LivingEntity entity) {
        if (entity instanceof Mob mob && mob.getTarget() != null) {
            return false;
        }
        return this.canAttack(entity) && !this.isInWaterOrBubble() && this.getFightCooldown() <= 0 && entity.getLastHurtByMob() == null && !entity.isInWaterOrBubble();
    }

    @Override
    public boolean canSpawnSprintParticle() {
        return this.getDeltaMovement().horizontalDistance() > 0.05D && this.getAttackState() == 1 && !this.isInWater() && !this.isSpectator() && !this.isCrouching() && !this.isInLava() && this.isAlive() && !this.isInFluidType();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.getChargeCooldown() > 0) {
                this.setChargeCooldown(this.getChargeCooldown() - 1);
            }
            if (this.getFightCooldown() > 0 && !this.isEepy() && !this.isInWaterOrBubble()) {
                this.setFightCooldown(this.getFightCooldown() - 1);
            }
            if (this.getFindTargetCooldown() > 0 && !this.isEepy() && !this.isInWaterOrBubble()) {
                this.setFindTargetCooldown(this.getFindTargetCooldown() - 1);
            }
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (recoverTicks > 0) recoverTicks--;
        if (recoverTicks == 0 && this.getPose() == UP2Poses.RECOVERING.get()) this.setPose(Pose.STANDING);
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.getAttackState() != 1 && !this.isInWaterOrBubble() && !this.isEepy() && this.getPose() != UP2Poses.RECOVERING.get(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble() && this.getAttackState() != 1, this.tickCount);
        this.recoverAnimationState.animateWhen(this.getPose() == UP2Poses.RECOVERING.get(), this.tickCount);
        this.warnAnimationState.animateWhen(this.getPose() == UP2Poses.WARNING.get(), this.tickCount);
        this.grazeAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.huffAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (DATA_POSE.equals(key)) {
            if (this.getPose() == UP2Poses.RECOVERING.get()) {
                this.recoverTicks = 70;
            }
        }
        super.onSyncedDataUpdated(key);
    }

    public void handleEntityEvent(byte id) {
        if (id == 39) {
            this.spawnImpactParticles(4, 0.25D);
        } else {
            super.handleEntityEvent(id);
        }
    }

    public void spawnImpactParticles(int amount, double speed) {
        Vec3 impactPos = this.getEyePosition().add(this.getViewVector(0.0F).scale(2.5F).add(0, -0.25F, -this.getBbWidth() * 0.5F));
        Vec3 forward = this.getViewVector(0.0F).normalize().scale(-1);
        for (int i = 0; i < amount; i++) {
            double theta = (this.getRandom().nextFloat() - 0.5F) * Math.PI * 0.75F;
            Vec3 rotated = forward.yRot((float) theta);
            double xVelocity = rotated.x * this.getRandom().nextFloat() * 0.6F;
            double yVelocity = this.getRandom().nextFloat() * 0.8F;
            double zVelocity = rotated.z * this.getRandom().nextFloat() * 0.6F;
            this.level().addParticle(UP2Particles.IMPACT_STUN.get(), false, impactPos.x, impactPos.y, impactPos.z, xVelocity * speed, yVelocity * speed, zVelocity * speed);
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 3 || this.getPose() == UP2Poses.RECOVERING.get();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CHARGE_COOLDOWN, 0);
        builder.define(FIGHT_COOLDOWN, 800);
        builder.define(FIGHT_PARTNER, false);
        builder.define(WANTS_TO_KILL, false);
        builder.define(FIND_TARGET_COOLDOWN, 100);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("FightCooldown", this.getFightCooldown());
        compoundTag.putInt("FindTargetCooldown", this.getFindTargetCooldown());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setFightCooldown(compoundTag.getInt("FightCooldown"));
        this.setFindTargetCooldown(compoundTag.getInt("FindTargetCooldown"));
    }

    public void setChargeCooldown(int cooldown) {
        this.entityData.set(CHARGE_COOLDOWN, cooldown);
    }

    public int getChargeCooldown() {
        return this.entityData.get(CHARGE_COOLDOWN);
    }

    public void setFightCooldown(int cooldown) {
        this.entityData.set(FIGHT_COOLDOWN, cooldown);
    }

    public int getFightCooldown() {
        return this.entityData.get(FIGHT_COOLDOWN);
    }

    public void setFightPartner(boolean partner) {
        this.entityData.set(FIGHT_PARTNER, partner);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isFightPartner() {
        return this.entityData.get(FIGHT_PARTNER);
    }

    public void setWantsToKill(boolean wantsToKill) {
        this.entityData.set(WANTS_TO_KILL, wantsToKill);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean wantsToKill() {
        return this.entityData.get(WANTS_TO_KILL);
    }

    public void setFindTargetCooldown(int cooldown) {
        this.entityData.set(FIND_TARGET_COOLDOWN, cooldown);
    }

    public int getFindTargetCooldown() {
        return this.entityData.get(FIND_TARGET_COOLDOWN);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.PACHYCEPHALOSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.PACHYCEPHALOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.PACHYCEPHALOSAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.PACHYCEPHALOSAURUS_STEP.get(), 0.15F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        Pachycephalosaurus baby = UP2Entities.PACHYCEPHALOSAURUS.get().create(level);
        if (baby != null) {
            baby.setVariantRawId(this.inheritVariantFrom(mob, this.getRandom()));
        }
        return baby;
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/pachycephalosaurus/pachycephalosaurus_lavender.png");
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.pickVariantForSpawn(level);
        return data;
    }

    // Goals
    private static class PachycephalosaurusHurtByTargetGoal extends HurtByTargetGoal {

        private final Pachycephalosaurus pachycephalosaurus;

        public PachycephalosaurusHurtByTargetGoal(Pachycephalosaurus pachycephalosaurus, Class<?>... toIgnoreDamage) {
            super(pachycephalosaurus, toIgnoreDamage);
            this.pachycephalosaurus = pachycephalosaurus;
        }

        @Override
        public void start() {
            super.start();
            this.pachycephalosaurus.setWantsToKill(true);
        }

        @Override
        public void stop() {
            super.stop();
            this.pachycephalosaurus.setWantsToKill(false);
        }
    }

    private static class PachycephalosaurusTargetToKillGoal<T extends LivingEntity> extends PrehistoricNearestAttackableTargetGoal<T> {

        private final Pachycephalosaurus pachycephalosaurus;

        public PachycephalosaurusTargetToKillGoal(Pachycephalosaurus pachycephalosaurus, Class<T> targetClass, int interval, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> entityPredicate) {
            super(pachycephalosaurus, targetClass, interval, mustSee, mustReach, entityPredicate);
            this.pachycephalosaurus = pachycephalosaurus;
        }

        @Override
        public void start() {
            super.start();
            this.pachycephalosaurus.setWantsToKill(true);
        }

        @Override
        public void stop() {
            super.stop();
            this.pachycephalosaurus.setWantsToKill(false);
        }
    }

    private static class PachycephalosaurusNearestAttackableTargetGoal<T extends LivingEntity> extends PrehistoricNearestAttackableTargetGoal<T> {

        private final Pachycephalosaurus pachycephalosaurus;

        public PachycephalosaurusNearestAttackableTargetGoal(Pachycephalosaurus pachycephalosaurus, Class<T> targetClass, int interval, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> entityPredicate) {
            super(pachycephalosaurus, targetClass, interval, mustSee, mustReach, entityPredicate);
            this.pachycephalosaurus = pachycephalosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && pachycephalosaurus.getFindTargetCooldown() <= 0;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && pachycephalosaurus.getFindTargetCooldown() <= 0;
        }
    }

    private static class PachycephalosaurusTargetOthersGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

        private final Pachycephalosaurus prehistoricMob;

        public PachycephalosaurusTargetOthersGoal(Pachycephalosaurus pachycephalosaurus, Class<T> targetClass) {
            super(pachycephalosaurus, targetClass, 200, true, true, pachycephalosaurus::canChargeAtOtherPachycephalosaurus);
            this.prehistoricMob = pachycephalosaurus;
        }

        @Override
        public void start() {
            this.mob.setTarget(target);
            if (target instanceof Pachycephalosaurus pachycephalosaurus && pachycephalosaurus.getTarget() == null && pachycephalosaurus.getLastHurtByMob() == null && !pachycephalosaurus.isInWaterOrBubble()) {
                pachycephalosaurus.setFightPartner(true);
                pachycephalosaurus.setTarget(mob);
            }
            super.start();
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !prehistoricMob.isBaby() && !prehistoricMob.isEepy();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !prehistoricMob.isEepy();
        }
    }

//    private static class PachycephalosaurusGrazeGoal extends IdleAnimationGoal {
//
//        private final Pachycephalosaurus pachycephalosaurus;
//
//        public PachycephalosaurusGrazeGoal(Pachycephalosaurus pachycephalosaurus) {
//            super(pachycephalosaurus, 60, 1);
//            this.pachycephalosaurus = pachycephalosaurus;
//        }
//
//        @Override
//        public boolean canUse() {
//            return super.canUse() && pachycephalosaurus.grazeCooldown == 0 && pachycephalosaurus.level().getBlockState(pachycephalosaurus.blockPosition().below()).is(UP2BlockTags.PACHYCEPHALOSAURUS_FOOD_BLOCKS);
//        }
//
//        @Override
//        public void stop() {
//            super.stop();
//            this.pachycephalosaurus.grazeCooldown();
//        }
//    }
//
//    private static class PachycephalosaurusHuffGoal extends IdleAnimationGoal {
//
//        private final Pachycephalosaurus pachycephalosaurus;
//
//        public PachycephalosaurusHuffGoal(Pachycephalosaurus pachycephalosaurus) {
//            super(pachycephalosaurus, 30, 2, false);
//            this.pachycephalosaurus = pachycephalosaurus;
//        }
//
//        @Override
//        public boolean canUse() {
//            return super.canUse() && pachycephalosaurus.huffCooldown == 0;
//        }
//
//        @Override
//        public void stop() {
//            super.stop();
//            this.pachycephalosaurus.huffCooldown();
//        }
//    }
}
