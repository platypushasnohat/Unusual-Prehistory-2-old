package com.barl_inc.unusual_prehistory.entity.mob.other;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricWanderGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.LeapingMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.registry.UP2DamageTypes;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("deprecation")
public class Grug extends PrehistoricMob implements LeapingMob {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Grug.class, EntityDataSerializers.BOOLEAN);

    public final SmoothAnimationState jumpAnimationState = new SmoothAnimationState();

    public Grug(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new GrugAttackGoal(this));
        this.goalSelector.addGoal(2, new PrehistoricWanderGoal(this, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 20.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1000.0D)
                .add(Attributes.ATTACK_DAMAGE, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 100.0D)
                .add(Attributes.STEP_HEIGHT, 2.5D);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_EXPLOSION) || source.is(DamageTypeTags.IS_PROJECTILE);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public double getFluidJumpThreshold() {
        return (double) this.getEyeHeight() < 0.4D ? 0.0D : 0.4D;
    }

    @Override
    public void remove(Entity.@NotNull RemovalReason removalReason) {
        UnusualPrehistory2.PROXY.clearSoundCacheFor(this);
        super.remove(removalReason);
    }

    @Override
    public void tick() {
        super.tick();
        if (EventHooks.canEntityGrief(this.level(), this) && this.isAggressive()) {
            boolean flag = false;
            AABB aabb = this.getBoundingBox().move(0.0D, 1.5D, 0.0D).inflate(0.5D);
            for (BlockPos blockpos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
                flag = this.level().destroyBlock(blockpos, false, this) || flag;
            }
        }

        if (this.level().isClientSide && this.isAlive() && this.isAggressive()) {
            UnusualPrehistory2.PROXY.playWorldSound(this, (byte) 4);
        }

        if ((this.onGround() || this.isInWaterOrBubble() || this.onClimbable()) && this.isLeaping()) {
            this.setLeaping(false);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isLeaping(), this.tickCount);
        this.jumpAnimationState.animateWhen(this.isLeaping(), this.tickCount);
    }

//    @Override
//    public float getStepHeight() {
//        return 2.0F;
//    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

    @Override
    public int getHealCooldown() {
        return 4;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LEAPING, false);
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.GRUG_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.GRUG_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.GRUG_DEATH.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 220;
    }

    @Override
    public boolean isFood(@NotNull ItemStack itemStack) {
        return false;
    }

    // goals
    private static class GrugAttackGoal extends AttackGoal {

        private final Grug grug;

        public GrugAttackGoal(Grug grug) {
            super(grug);
            this.grug = grug;
        }

        @Override
        public void tick() {
            LivingEntity target = this.grug.getTarget();
            if (target != null) {
                this.grug.lookAt(target, 30F, 30F);
                this.grug.getLookControl().setLookAt(target, 30F, 30F);
                double distance = this.grug.distanceToSqr(target);

                int attackState = this.grug.getAttackState();
                this.grug.getNavigation().moveTo(target, 1.5D);

                double dx = target.getX() - grug.getX();
                double dz = target.getZ() - grug.getZ();
                double horizontalDistanceSqr = dx * dx + dz * dz;

                if ((target.getY() > grug.getY() + 4) && grug.onGround() && horizontalDistanceSqr <= 100) {
                    this.grug.addDeltaMovement(new Vec3(0, 2.0D, 0));
                    this.grug.addDeltaMovement(this.grug.getLookAngle().scale(2.0D).multiply(0.6D, 0, 0.6D));
                    this.grug.setLeaping(true);
                }
                if (attackState == 1) {
                    this.tickAttack();
                } else if (distance < this.getAttackReachSqr(target)) {
                    this.grug.setAttackState(1);
                }
            }
        }

        protected void tickAttack() {
            this.timer++;
            if (timer == 1) {
                this.attackNearbyEntities();
            }
            if (timer > 1) {
                this.timer = 0;
                this.grug.setAttackState(0);
            }
        }

        private void attackNearbyEntities() {
            List<LivingEntity> nearbyEntities = grug.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), grug, grug.getBoundingBox().inflate(3.0D));
            if (!nearbyEntities.isEmpty()) {
                nearbyEntities.stream().filter(entity -> entity != grug).forEach(entity -> {
                    DamageSource damagesource = UP2DamageTypes.grug(grug.level(), grug, grug);
                    entity.hurt(damagesource, (float) grug.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    this.strongKnockback(entity, 4.0D, 0.1D);
                    if (entity.isDamageSourceBlocked(damagesource) && entity instanceof Player player) {
                        player.disableShield();
                    }
                });
            }
        }
    }
}
