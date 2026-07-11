package com.barl_inc.unusual_prehistory.entity.ai.goals.update_1;

import com.barl_inc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Carnotaurus;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

public class CarnotaurusAttackGoal extends AttackGoal {

    private final Carnotaurus carnotaurus;
    private int collisionTicks;

    public CarnotaurusAttackGoal(Carnotaurus carnotaurus) {
        super(carnotaurus);
        this.carnotaurus = carnotaurus;
    }

    @Override
    public void start() {
        super.start();
        this.collisionTicks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.collisionTicks = 0;
    }

    @Override
    public boolean canUse() {
        return mob.getTarget() != null && mob.getTarget().isAlive() && !mob.isVehicle() && !mob.isSitting() && !mob.isEepy();
    }

    @Override
    public void tick() {
        LivingEntity target = carnotaurus.getTarget();
        if (target != null) {
            double distance = carnotaurus.distanceTo(target);
            int attackState = carnotaurus.getAttackState();

            if (carnotaurus.horizontalCollision) {
                this.collisionTicks++;
            } else if (collisionTicks > 0) {
                this.collisionTicks--;
            }

            if (attackState != 3 && attackState != 2 && carnotaurus.getPose() != UP2Poses.STOP_CHARGING.get()) {
                this.carnotaurus.lookAt(target, 30F, 30F);
                this.carnotaurus.getLookControl().setLookAt(target, 30F, 30F);
            }

            if (attackState == 1) {
                this.carnotaurus.getNavigation().stop();
                this.tickBite(target);
            }
            else if (attackState == 2) {
                this.carnotaurus.getNavigation().stop();
                this.tickHeadbutt(target);
            }
            else if (attackState == 3) {
                this.carnotaurus.getNavigation().stop();
                this.tickCharge(target);
            }
            else if (attackState == 4) {
                this.carnotaurus.getNavigation().stop();
                this.tickRoar();
            }
            else if (carnotaurus.getPose() == Pose.STANDING && attackState == 0) {
                this.carnotaurus.getNavigation().moveTo(target, 1.8D);
                if (this.isInAttackRange(target, 1.8D)) {
                    if (carnotaurus.getRandom().nextFloat() < 0.75F && carnotaurus.biteCooldown == 0) {
                        this.carnotaurus.setAttackState(1);
                    }
                    else if (carnotaurus.headbuttCooldown == 0) {
                        this.carnotaurus.setAttackState(2);
                    }
                }
                else if (distance < 16.0D && distance > 3.0D && carnotaurus.chargeCooldown == 0 && !carnotaurus.isInWaterOrBubble() && this.isWithinYRange(target)) {
                    this.carnotaurus.setAttackState(3);
                }
                else if (carnotaurus.roarCooldown == 0 && carnotaurus.getHealth() < carnotaurus.getMaxHealth() * 0.5F) {
                    this.carnotaurus.setAttackState(4);
                }
            }
        }
    }

    protected void tickBite(LivingEntity target) {
        this.timer++;
        if (timer == 1) {
            this.carnotaurus.setPose(UP2Poses.ATTACKING.get());
        }
        if (timer == 9) {
            this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_BITE.get(), 1.0F, carnotaurus.getVoicePitch());
        }

        if (timer == 11) {
            if (this.isInAttackRange(target, 1.6D)) {
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
                this.carnotaurus.doHurtTarget(target);
            }
        }
        if (timer > 15) {
            this.timer = 0;
            this.carnotaurus.setPose(Pose.STANDING);
            this.carnotaurus.biteCooldown = 3;
            this.carnotaurus.setAttackState(0);
        }
    }

    protected void tickHeadbutt(LivingEntity target) {
        this.timer++;
        if (timer == 1) {
            this.carnotaurus.setPose(UP2Poses.HEADBUTTING.get());
            this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_HEADBUTT_VOCAL.get(), 1.5F, carnotaurus.getVoicePitch());
        }
        if (timer < 7) {
            this.carnotaurus.lookAt(target, 30F, 30F);
            this.carnotaurus.getLookControl().setLookAt(target, 30F, 30F);
        }
        if (timer == 12) {
            this.headbuttNearbyEntities();
        }
        if (timer > 20) {
            this.timer = 0;
            this.carnotaurus.setPose(Pose.STANDING);
            this.carnotaurus.headbuttCooldown = 40 + carnotaurus.getRandom().nextInt(20);
            this.carnotaurus.setAttackState(0);
        }
    }

    protected void tickCharge(LivingEntity target) {
        this.timer++;

        if (timer == 1) {
            this.carnotaurus.setPose(UP2Poses.START_CHARGING.get());
        }
        if (timer == 18) {
            this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_CHARGE.get(), 1.0F, carnotaurus.getVoicePitch());
        }

        if (timer < 18) {
            this.carnotaurus.lookAt(target, 30F, 30F);
            this.carnotaurus.getLookControl().setLookAt(target, 30F, 30F);
        }

        if (timer > 25 && timer < 55) {
            this.chargeAtTarget(target, 0.8F);
            this.hurtNearbyEntities();
        }

        if (timer > 55 || collisionTicks > 10) {
            this.timer = 0;
            this.carnotaurus.setPose(UP2Poses.STOP_CHARGING.get());
            this.carnotaurus.chargeCooldown = 150 + carnotaurus.getRandom().nextInt(150);
            this.carnotaurus.setAttackState(0);
        }
    }

    protected void tickRoar() {
        this.timer++;
        if (timer == 1) {
            this.carnotaurus.setPose(UP2Poses.ROARING.get());
        }
        if (timer == 5) {
            this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_ROAR.get(), 2.0F, carnotaurus.getVoicePitch());
        }
        if (timer == 8) {
            this.carnotaurus.roar();
        }
        if (timer > 40) {
            this.timer = 0;
            this.carnotaurus.setPose(Pose.STANDING);
            this.carnotaurus.roarCooldown = 200 + carnotaurus.getRandom().nextInt(150);
            this.carnotaurus.setAttackState(0);
        }
    }

    private void headbuttNearbyEntities() {
        List<LivingEntity> nearbyEntities = carnotaurus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), carnotaurus, carnotaurus.getBoundingBox().inflate(2.0));
        if (!nearbyEntities.isEmpty()) {
            nearbyEntities.stream().filter(entity -> !(entity instanceof Carnotaurus) && !(entity instanceof Creeper)).limit(3).forEach(entity -> {
                if (entity.hurt(entity.damageSources().mobAttack(carnotaurus), (float) carnotaurus.getAttributeValue(Attributes.ATTACK_DAMAGE))) {
                    this.carnotaurus.playSound(UP2SoundEvents.CARNOTAURUS_HEADBUTT.get(), 1.0F, carnotaurus.getVoicePitch());
                }
                this.strongKnockback(entity, 1.0D, 0.5D);
                if (entity.isDamageSourceBlocked(carnotaurus.damageSources().mobAttack(carnotaurus)) && entity instanceof Player player) {
                    player.disableShield();
                }
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
            });
        }
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = carnotaurus.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), carnotaurus, carnotaurus.getBoundingBox().inflate(1.5D));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.getFirst();
            if (!(entity instanceof Carnotaurus)) {
                BlockPos pos = carnotaurus.blockPosition();
                Vec3 targetPos = entity.position();
                Vec3 knockbackDirection = (new Vec3(pos.getX() - targetPos.x(), 0.0D, pos.getZ() - targetPos.z())).normalize();
                int speedFactor = carnotaurus.hasEffect(MobEffects.MOVEMENT_SPEED) ? Objects.requireNonNull(carnotaurus.getEffect(MobEffects.MOVEMENT_SPEED)).getAmplifier() + 1 : 0;
                int slownessFactor = carnotaurus.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? Objects.requireNonNull(carnotaurus.getEffect(MobEffects.MOVEMENT_SLOWDOWN)).getAmplifier() + 1 : 0;
                float effectSpeed = 0.15F * (speedFactor - slownessFactor);
                float speedForce = Mth.clamp(carnotaurus.getSpeed() * 1.65F, 0.2F, 3.0F) + effectSpeed;
                float knockbackForce = entity.isDamageSourceBlocked(carnotaurus.level().damageSources().mobAttack(carnotaurus)) ? 1.0F : 1.25F;
                entity.hurt(entity.damageSources().mobAttack(carnotaurus), (float) carnotaurus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                entity.knockback((knockbackForce * speedForce) * 1.5F, knockbackDirection.x(), knockbackDirection.z());
                if (entity.isDamageSourceBlocked(carnotaurus.damageSources().mobAttack(carnotaurus)) && entity instanceof Player player){
                    player.disableShield();
                }
                this.carnotaurus.swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    public boolean isWithinYRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - carnotaurus.getY()) < 2;
    }
}
