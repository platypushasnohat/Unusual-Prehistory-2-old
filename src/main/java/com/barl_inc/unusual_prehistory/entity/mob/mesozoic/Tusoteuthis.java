package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.AmphibiousPanicGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricNearestAttackableTargetGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricSwimGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2MobEffects;
import com.barl_inc.unusual_prehistory.registry.UP2Particles;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class Tusoteuthis extends PrehistoricAquaticMob {

    private int attackCooldown = 0;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState flashAnimationState = new SmoothAnimationState();

    private int warnTicks = 0;

    public float spinProgress;
    public float prevSpinProgress;
    public float spinSpeed;
    public float prevSpinSpeed;

    public float prevFlashProgress;
    public float flashProgress;

    private final byte BUBBLES = 67;
    private final byte FLASH = 68;

    public Tusoteuthis(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.switchShallowNavigation(false);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6F)
                .add(Attributes.ATTACK_DAMAGE, 7.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new TusoteuthisPanicGoal(this));
        this.goalSelector.addGoal(2, new TusoteuthisAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new PrehistoricSwimGoal(this, 1.0D, 100, 7, 15));
        this.targetSelector.addGoal(1, new TusoteuthisTargetUnderneathGoal<>(this, LivingEntity.class, this::canTargetEntitiesUnderneath));
        this.targetSelector.addGoal(2, new TusoteuthisTargetUnderneathGoal<>(this, Player.class, this::canTargetEntitiesUnderneath));
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.refuseToMove() && this.isInWaterOrBubble()) {
            travelVector = travelVector.multiply(0.0D, 0.0D, 0.0D);
        }
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

    private boolean canTargetEntitiesUnderneath(LivingEntity target) {
        if (!this.canAttack(target)) {
            return false;
        }
        if (target instanceof Tusoteuthis) {
            return false;
        }
        if (!this.isInWaterOrBubble()) {
            return false;
        }
        return (target.getBbWidth() < this.getBbWidth() * 0.33F && target.getBbHeight() < this.getBbHeight() * 0.33F) && target.getY() < this.getY();
    }

    public float getSpinProgress(float partialTicks) {
        return prevSpinProgress + (spinProgress - prevSpinProgress) * partialTicks;
    }

    public float getFlashProgress(float partialTicks) {
        return (prevFlashProgress + (flashProgress - prevFlashProgress) * partialTicks) * 0.2F;
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getPose() == UP2Poses.WARNING.get();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (hurt) {
            this.setTarget(null);
        }
        return hurt;
    }

    @Override
    public void tick() {
        super.tick();

        this.prevSpinProgress = spinProgress;
        this.prevSpinSpeed = spinSpeed;

        this.prevFlashProgress = flashProgress;

        if (this.getPose() == UP2Poses.ATTACKING.get()) {
            this.spinSpeed += 2.0F;
            if (spinSpeed > 45.0F) {
                this.spinSpeed = 45.0F;
            }
        } else {
            this.spinSpeed *= 0.9F;
            if (spinSpeed < 0.1F) {
                this.spinSpeed = 0.0F;
            }
        }

        this.spinProgress += spinSpeed;

        if (spinSpeed == 0.0F) {
            float spinDegrees = spinProgress % 360.0F;
            if (spinDegrees > 180.0F) {
                spinDegrees -= 360.0F;
            } else if (spinDegrees < -180.0F) {
                spinDegrees += 360.0F;
            }
            this.spinProgress -= spinDegrees * 0.15F;
            if (Math.abs(spinDegrees) < 0.25F) {
                this.spinProgress = 0.0F;
                this.prevSpinProgress = 0.0F;
                this.spinSpeed = 0.0F;
                this.prevSpinSpeed = 0.0F;
            }
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackCooldown > 0) {
            this.attackCooldown--;
        }
        if (warnTicks > 0) {
            this.warnTicks--;
        }

        if (this.level().isClientSide) {
            if (this.getPose() == UP2Poses.WARNING.get() && warnTicks <= 6) {
                this.flashProgress += (5.0F - flashProgress);
            } else {
                this.flashProgress += (0.0F - flashProgress) * 0.33F;
            }
        } else {
            if (warnTicks == 5) {
                for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5.5F))) {
                    if (!(entity instanceof Tusoteuthis)) {
                        entity.addEffect(new MobEffectInstance(UP2MobEffects.DAZZLED, 200));
                    }
                }
                this.playSound(UP2SoundEvents.TUSOTEUTHIS_FLASH.get(), 1.5F, this.getVoicePitch());
                this.level().broadcastEntityEvent(this, FLASH);
            }
        }

        if (warnTicks == 0 && this.getPose() == UP2Poses.WARNING.get()) {
            this.setPose(Pose.STANDING);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isAttackingOrFlashing(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
        this.flashAnimationState.animateWhen(this.getPose() == UP2Poses.WARNING.get(), this.tickCount);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isAttackingOrFlashing() {
        return this.getPose() == UP2Poses.ATTACKING.get() || this.getPose() == UP2Poses.WARNING.get();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 700 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 2) {
            return 800 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    public boolean canPlayIdles(Entity entity) {
        return entity.isInWaterOrBubble();
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.WARNING.get()) {
                this.warnTicks = 25;
            }
        }
    }

    private Vec3 getMouthVec(LivingEntity target) {
        return new Vec3(this.getX(), this.getBoundingBox().minY - target.getBbHeight(), this.getZ());
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == BUBBLES) {
            Vec3 vec3 = new Vec3(this.getX(), this.getBoundingBox().minY - 0.5F, this.getZ());
            double depth = this.getRandom().nextDouble() * 6.0D;
            for (int i = 0; i < 3; i++) {
                this.level().addAlwaysVisibleParticle(ParticleTypes.BUBBLE, vec3.x + (this.getRandom().nextDouble() - 0.5D), vec3.y - depth, vec3.z + (this.getRandom().nextDouble() - 0.5D), 0.0D, 0.15D, 0.0D);
            }
        }
        if (id == FLASH) {
            Vec3 look = this.getLookAngle();
            double distance = this.getBbWidth() * 0.75D;
            this.level().addAlwaysVisibleParticle(UP2Particles.TUSOTEUTHIS_FLASH.get(), this.getX() + look.x * distance, this.getEyeY() + 0.25F, this.getZ() + look.z * distance, 0.0D, 0.0D, 0.0D);
        }
        super.handleEntityEvent(id);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.TUSOTEUTHIS.get().create(level);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.TUSOTEUTHIS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.TUSOTEUTHIS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.TUSOTEUTHIS_DEATH.get();
    }

    private static class TusoteuthisPanicGoal extends AmphibiousPanicGoal {

        private final Tusoteuthis tusoteuthis;

        public TusoteuthisPanicGoal(Tusoteuthis tusoteuthis) {
            super(tusoteuthis, 1.75D, 16, 10);
            this.tusoteuthis = tusoteuthis;
        }

        @Override
        public void start() {
            if (tusoteuthis.isInWaterOrBubble()) {
                this.tusoteuthis.setPose(UP2Poses.WARNING.get());
            }
            super.start();
        }

        @Override
        public void tick() {
            LivingEntity hurtByMob = tusoteuthis.getLastHurtByMob();
            if (hurtByMob != null) {
                if (tusoteuthis.isInWaterOrBubble() && tusoteuthis.getPose() == UP2Poses.WARNING.get()) {
                    this.tusoteuthis.lookAt(hurtByMob, 30.0F, 30.0F);
                    this.tusoteuthis.getLookControl().setLookAt(hurtByMob, 30.0F, 30.0F);
                }
            }
        }
    }

    private static class TusoteuthisAttackGoal extends AttackGoal {

        private final Tusoteuthis tusoteuthis;

        public TusoteuthisAttackGoal(Tusoteuthis tusoteuthis) {
            super(tusoteuthis);
            this.tusoteuthis = tusoteuthis;
        }

        @Override
        public void tick() {
            LivingEntity target = tusoteuthis.getTarget();
            if (target != null) {

                double dx = tusoteuthis.getX() - target.getX();
                double dz = tusoteuthis.getZ() - target.getZ();
                double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
                double verticalDistance = tusoteuthis.getY() - target.getY();
                int attackState = tusoteuthis.getAttackState();

                if (attackState == 1) {
                    this.tickAttack(target, verticalDistance, horizontalDistance);
                    this.tusoteuthis.getNavigation().stop();
                }
                else {
                    this.tusoteuthis.getNavigation().moveTo(target.getX(), target.getY() + 5.0D, target.getZ(), 1.75D);
                    if (horizontalDistance < 4.0D && verticalDistance > 4.0D && verticalDistance < 8.0D && tusoteuthis.attackCooldown == 0) {
                        this.tusoteuthis.setAttackState(1);
                    }
                }
            }
        }

        protected void tickAttack(LivingEntity target, double verticalDistance, double horizontalDistance) {
            this.timer++;
            if (timer == 1) {
                this.tusoteuthis.setPose(UP2Poses.ATTACKING.get());
                this.tusoteuthis.playSound(SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, 1.0F, 0.9F + tusoteuthis.getRandom().nextFloat() * 0.2F);
            }
            if (timer < 100 && tusoteuthis.hasLineOfSight(target) && verticalDistance <= 10.0D && horizontalDistance < 5.0D) {
                Vec3 offset = tusoteuthis.getMouthVec(target).subtract(target.position());
                Vec3 velocity = offset.scale(0.05D);
                target.setDeltaMovement(target.getDeltaMovement().scale(0.5D).add(velocity));
                if (target instanceof PathfinderMob pathfinderMob) {
                    if (pathfinderMob.getNavigation().getPath() != null) {
                        pathfinderMob.getNavigation().stop();
                    }
                }
                target.hurtMarked = true;
                if (timer > 20 && timer % 20 == 0 && this.isInAttackRange(target, 0.25D) ) {
                    this.tusoteuthis.doHurtTarget(target);
                }
                if (timer % 2 == 0) {
                    this.tusoteuthis.level().broadcastEntityEvent(tusoteuthis, tusoteuthis.BUBBLES);
                }
                if (timer % 60 == 0) {
                    this.tusoteuthis.playSound(SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, 1.0F, 0.9F + tusoteuthis.getRandom().nextFloat() * 0.2F);
                }
            }
            if (timer > 100) {
                this.timer = 0;
                this.tusoteuthis.attackCooldown = 100 + tusoteuthis.getRandom().nextInt(50);
                this.tusoteuthis.setPose(Pose.STANDING);
                this.tusoteuthis.setAttackState(0);
            }
        }
    }

    private static class TusoteuthisTargetUnderneathGoal<T extends LivingEntity> extends PrehistoricNearestAttackableTargetGoal<T> {

        private final Tusoteuthis tusoteuthis;

        public TusoteuthisTargetUnderneathGoal(Tusoteuthis tusoteuthis, Class<T> targetClass, Predicate<LivingEntity> targetPredicate) {
            super(tusoteuthis, targetClass, 50, false, false, targetPredicate);
            this.tusoteuthis = tusoteuthis;
        }

        @Override
        protected @NotNull AABB getTargetSearchArea(double distance) {
            double radius = 4.0D;
            return new AABB(tusoteuthis.getX() - radius, tusoteuthis.getY() - 32.0D, tusoteuthis.getZ() - radius, tusoteuthis.getX() + radius, tusoteuthis.getY(), tusoteuthis.getZ() + radius);
        }
    }
}
