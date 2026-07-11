package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.EepyGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.HerdWanderGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricBabyPanicGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Pachyrhinosaurus extends PrehistoricMob {

    private int attackCooldown = 0;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);

    public Pachyrhinosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new PachyrhinosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_HERBIVORE), false));
        this.goalSelector.addGoal(4, new HerdWanderGoal(this, 1.0D, 1.2D, 6));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new EepyGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 90.0D)
                .add(Attributes.ATTACK_DAMAGE, 9.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18F)
                .add(Attributes.STEP_HEIGHT, 1.2D);
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0D, 1.0D, 0.0D);
        }
        super.travel(travelVec);
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.35D * this.getBbHeight();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_HERBIVORE);
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, -0.2F * this.getBbHeight(), this.getBbWidth() * 1.12F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (!this.level().isClientSide) {
            if (attackCooldown > 0) {
                this.attackCooldown--;
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy() && !this.isInWaterOrBubble(), tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float) Mth.length(this.getX() - xo, includeHeight ? this.getY() - yo : 0.0, this.getZ() - zo);
        if (this.isBaby()) {
            this.updateWalkAnimation(f * 0.5F);
        } else {
            this.updateWalkAnimation(f);
        }
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f = Math.min(partialTick * 25.0F, 1.0F);
        this.walkAnimation.update(f, 0.4F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.PACHYRHINOSAURUS.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.PACHYRHINOSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.PACHYRHINOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.PACHYRHINOSAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.PACHYRHINOSAURUS_STEP.get(), 0.2F, this.isBaby() ? 1.5F : 1.0F);
    }

    // Goals
    private static class PachyrhinosaurusAttackGoal extends AttackGoal {

        protected final Pachyrhinosaurus pachyrhinosaurus;

        public PachyrhinosaurusAttackGoal(Pachyrhinosaurus pachyrhinosaurus) {
            super(pachyrhinosaurus);
            this.pachyrhinosaurus = pachyrhinosaurus;
        }

        @Override
        public void tick() {
            LivingEntity target = pachyrhinosaurus.getTarget();
            if (target != null) {
                double distance = pachyrhinosaurus.distanceToSqr(target);
                this.pachyrhinosaurus.lookAt(target, 30.0F, 30.0F);
                this.pachyrhinosaurus.getLookControl().setLookAt(target, 30.0F, 30.0F);
                if (this.pachyrhinosaurus.getAttackState() == 1) {
                    this.pachyrhinosaurus.getNavigation().stop();
                    this.tickAttack();
                } else {
                    this.pachyrhinosaurus.getNavigation().moveTo(target, 1.85D);
                    if (distance <= this.getAttackReachSqr(target, 1.5D) && pachyrhinosaurus.attackCooldown == 0) {
                        this.pachyrhinosaurus.setAttackState(1);
                    }
                }
            }
        }

        private void tickAttack() {
            this.timer++;
            if (timer == 1) {
                this.pachyrhinosaurus.playSound(UP2SoundEvents.PACHYRHINOSAURUS_ATTACK.get(), 1.0F, pachyrhinosaurus.getVoicePitch());
                this.pachyrhinosaurus.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 10) {
                this.headbuttNearbyEntities();
            }
            if (timer > 15) {
                this.timer = 0;
                this.pachyrhinosaurus.attackCooldown = 10;
                this.pachyrhinosaurus.setPose(Pose.STANDING);
                this.pachyrhinosaurus.setAttackState(0);
            }
        }

        private void headbuttNearbyEntities() {
            AABB attackBox = pachyrhinosaurus.getBoundingBox().move(pachyrhinosaurus.getLookAngle().normalize().scale(1.5D)).inflate(2.25D);
            List<LivingEntity> nearbyEntities = pachyrhinosaurus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), pachyrhinosaurus, attackBox);
            if (!nearbyEntities.isEmpty()) {
                nearbyEntities.stream().filter(entity -> !(entity instanceof Pachyrhinosaurus)).limit(3).forEach(entity -> {
                    if (entity.hurt(entity.damageSources().mobAttack(pachyrhinosaurus), (float) pachyrhinosaurus.getAttributeValue(Attributes.ATTACK_DAMAGE))) {
                        this.pachyrhinosaurus.playSound(UP2SoundEvents.PACHYRHINOSAURUS_HEADBUTT.get(), 1.0F, pachyrhinosaurus.getVoicePitch());
                    }
                    this.strongKnockback(entity, 0.8D, 0.55D);
                    if (entity.isDamageSourceBlocked(pachyrhinosaurus.damageSources().mobAttack(pachyrhinosaurus)) && entity instanceof Player player) {
                        player.disableShield();
                    }
                    this.pachyrhinosaurus.swing(InteractionHand.MAIN_HAND);
                });
            }
        }
    }
}
