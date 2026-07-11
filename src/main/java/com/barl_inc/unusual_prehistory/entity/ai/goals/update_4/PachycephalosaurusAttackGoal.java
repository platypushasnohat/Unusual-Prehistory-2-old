package com.barl_inc.unusual_prehistory.entity.ai.goals.update_4;

import com.barl_inc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Pachycephalosaurus;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class PachycephalosaurusAttackGoal extends AttackGoal {

    protected final Pachycephalosaurus pachycephalosaurus;
    private boolean hitTarget;

    public PachycephalosaurusAttackGoal(Pachycephalosaurus pachycephalosaurus) {
        super(pachycephalosaurus);
        this.pachycephalosaurus = pachycephalosaurus;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && pachycephalosaurus.getHealth() > pachycephalosaurus.getMaxHealth() * 0.5F;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && pachycephalosaurus.getHealth() > pachycephalosaurus.getMaxHealth() * 0.5F;
    }

    @Override
    public void start() {
        this.mob.setPose(Pose.STANDING);
        this.mob.setAttackState(0);
        this.mob.setAggressive(true);
        this.mob.setRunning(true);
        this.timer = 0;
        this.hitTarget = false;
    }

    @Override
    public void stop() {
        if (this.mob.getPose() != UP2Poses.RECOVERING.get()) {
            this.mob.setPose(Pose.STANDING);
        }
        this.mob.setTarget(null);
        this.mob.setAttackState(0);
        this.mob.setAggressive(false);
        this.mob.setRunning(false);
        this.mob.getNavigation().stop();
        this.pachycephalosaurus.setFightCooldown(1000 + pachycephalosaurus.getRandom().nextInt(1000));
        this.pachycephalosaurus.setFindTargetCooldown(1200 + pachycephalosaurus.getRandom().nextInt(1200));
        this.pachycephalosaurus.setFightPartner(false);
    }

    @Override
    public void tick() {
        LivingEntity target = pachycephalosaurus.getTarget();
        if (target != null) {
            double distance = pachycephalosaurus.distanceToSqr(target);
            int attackState = pachycephalosaurus.getAttackState();

            this.pachycephalosaurus.getLookControl().setLookAt(target, 30F, 30F);

            if (attackState == 1) {
                this.pachycephalosaurus.getNavigation().stop();
                this.tickCharge();
            }
            else {
                if (distance > 50) {
                    this.pachycephalosaurus.getNavigation().moveTo(target, 1.7D);
                }
                if (distance <= 70 && this.isWithinYRange(target) && !pachycephalosaurus.isInWater() && pachycephalosaurus.getChargeCooldown() == 0 && pachycephalosaurus.getPose() != UP2Poses.RECOVERING.get()) {
                    this.pachycephalosaurus.setAttackState(1);
                }
            }
        }
    }

    protected void tickCharge() {
        this.timer++;
        LivingEntity target = pachycephalosaurus.getTarget();
        if (timer == 1) pachycephalosaurus.setPose(UP2Poses.WARNING.get());
        if (timer < 50) {
            this.pachycephalosaurus.lookAt(target, 360F, 30F);
            this.pachycephalosaurus.getLookControl().setLookAt(target, 30F, 30F);
        }
        if (timer == 50) pachycephalosaurus.setPose(Pose.STANDING);
        if (timer == 27 && !pachycephalosaurus.isFightPartner()) pachycephalosaurus.playSound(UP2SoundEvents.PACHYCEPHALOSAURUS_WARN.get(), 1.8F, 0.9F + pachycephalosaurus.getRandom().nextFloat() * 0.3F);
        if (timer > 50 && timer < 70) {
            if (this.isInAttackRange(target, target instanceof Pachycephalosaurus ? 0.1D : 1.1D) && !hitTarget) {
                this.hurtAndKnockbackTarget(target);
                if (target instanceof Pachycephalosaurus) {
                    this.bonkAndRecover(!pachycephalosaurus.isFightPartner());
                    this.stop();
                }
            } else {
                this.chargeAtTarget(target, 0.85F);
            }
        }
        if (pachycephalosaurus.horizontalCollision && timer > 50) {
            this.bonkAndRecover(true);
            if (target instanceof Pachycephalosaurus || !pachycephalosaurus.wantsToKill()) {
                this.stop();
            }
        }
        if (timer > 70) {
            this.timer = 0;
            this.pachycephalosaurus.setPose(Pose.STANDING);
            this.pachycephalosaurus.setAttackState(0);
            this.pachycephalosaurus.setChargeCooldown(15 + pachycephalosaurus.getRandom().nextInt(10));
            this.hitTarget = false;
            if (target instanceof Pachycephalosaurus || !pachycephalosaurus.wantsToKill()) {
                this.stop();
            }
        }
    }

    private void hurtAndKnockbackTarget(LivingEntity entity) {
        BlockPos pos = pachycephalosaurus.blockPosition();
        Vec3 targetPos = entity.position();
        Vec3 knockbackDirection = (new Vec3(pos.getX() - targetPos.x(), 0.0D, pos.getZ() - targetPos.z())).normalize();
        int speedFactor = pachycephalosaurus.hasEffect(MobEffects.MOVEMENT_SPEED) ? pachycephalosaurus.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
        int slownessFactor = pachycephalosaurus.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? pachycephalosaurus.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
        float effectSpeed = 0.15F * (speedFactor - slownessFactor);
        float speedForce = Mth.clamp(pachycephalosaurus.getSpeed() * 1.5F, 0.2F, 3.0F) + effectSpeed;
        float knockbackForce = entity.isDamageSourceBlocked(pachycephalosaurus.level().damageSources().mobAttack(pachycephalosaurus)) ? 2.25F : 3.0F;
        if (entity instanceof Pachycephalosaurus) {
            knockbackForce = 1.0F;
        } else {
            entity.hurt(entity.damageSources().mobAttack(pachycephalosaurus), (float) pachycephalosaurus.getAttributeValue(Attributes.ATTACK_DAMAGE));
        }
        entity.knockback((knockbackForce * speedForce) * 1.5F, knockbackDirection.x(), knockbackDirection.z());
        if (entity.isDamageSourceBlocked(pachycephalosaurus.damageSources().mobAttack(pachycephalosaurus)) && entity instanceof Player player) {
            player.disableShield();
            this.bonkAndRecover(true);
        }
        this.pachycephalosaurus.swing(InteractionHand.MAIN_HAND);
        this.hitTarget = true;
    }

    private void bonkAndRecover(boolean playSound) {
        this.pachycephalosaurus.level().broadcastEntityEvent(pachycephalosaurus, (byte) 39);
        if (playSound) {
            this.pachycephalosaurus.playSound(UP2SoundEvents.PACHYCEPHALOSAURUS_BONK.get(), 1.5F, 0.9F + pachycephalosaurus.getRandom().nextFloat() * 0.25F);
        }
        this.timer = 0;
        this.pachycephalosaurus.setPose(UP2Poses.RECOVERING.get());
        this.pachycephalosaurus.setAttackState(0);
        this.pachycephalosaurus.setChargeCooldown(15 + pachycephalosaurus.getRandom().nextInt(10));
        this.hitTarget = false;
    }

    private boolean isWithinYRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - pachycephalosaurus.getY()) < 2;
    }
}