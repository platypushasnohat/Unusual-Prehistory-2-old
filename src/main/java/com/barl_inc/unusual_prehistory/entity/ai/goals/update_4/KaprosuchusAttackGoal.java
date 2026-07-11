package com.barl_inc.unusual_prehistory.entity.ai.goals.update_4;

import com.barl_inc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Kaprosuchus;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;

public class KaprosuchusAttackGoal extends AttackGoal {

    protected final Kaprosuchus kaprosuchus;
    private int waitTimer;

    public KaprosuchusAttackGoal(Kaprosuchus kaprosuchus) {
        super(kaprosuchus);
        this.kaprosuchus = kaprosuchus;
    }

    @Override
    public void start() {
        super.start();
        this.waitTimer = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.kaprosuchus.setLeaping(false);
    }

    @Override
    public void tick() {
        LivingEntity target = kaprosuchus.getTarget();
        if (target != null) {
            double distance = kaprosuchus.distanceToSqr(target);
            if (!kaprosuchus.isLeaping()) {
                this.kaprosuchus.lookAt(target, 30F, 30F);
                this.kaprosuchus.getLookControl().setLookAt(target, 30F, 30F);
            }

            if (kaprosuchus.isLeaping()) {
                Vec3 vec3 = kaprosuchus.getDeltaMovement();
                if (vec3.y * vec3.y < 0.03F && kaprosuchus.getXRot() != 0.0F) {
                    this.kaprosuchus.setXRot(Mth.rotLerp(0.2F, kaprosuchus.getXRot(), 0.0F));
                } else if (vec3.length() > 1.0E-5F) {
                    double horizontalDistance = vec3.horizontalDistance();
                    double xRot = Math.atan2(-vec3.y, horizontalDistance) * (180F / (float) Math.PI);
                    this.kaprosuchus.setXRot((float) xRot);
                    this.kaprosuchus.setYRot(((float) Mth.atan2(kaprosuchus.getMotionDirection().getStepZ(), kaprosuchus.getMotionDirection().getStepX())) * Mth.RAD_TO_DEG - 90F);
                }
            }

            if (waitTimer > 0) {
                this.waitTimer--;
                this.kaprosuchus.getNavigation().stop();
            }

            if ((kaprosuchus.onGround() || kaprosuchus.isInWaterOrBubble()) && kaprosuchus.isLeaping()) {
                this.kaprosuchus.setAttackState(0);
                this.kaprosuchus.setLeaping(false);
                this.kaprosuchus.leapCooldown();
                this.waitTimer = 15;
            }

            if (this.kaprosuchus.getAttackState() == 1) {
                this.kaprosuchus.getNavigation().moveTo(target, 1.7D);
                this.tickBite();
            }
            else if (kaprosuchus.getAttackState() == 2) {
                this.tickLeap();
            }
            else {
                if (waitTimer == 0) this.kaprosuchus.getNavigation().moveTo(target, 1.9D);
                if (distance > 12.0F && distance < 90.0F && kaprosuchus.leapCooldown == 0 && this.isPathClear(target) && waitTimer == 0) {
                    this.kaprosuchus.setAttackState(2);
                }
                else if (distance <= this.getAttackReachSqr(target) && kaprosuchus.attackCooldown == 0 && waitTimer == 0) {
                    this.kaprosuchus.setAttackState(1);
                }
            }
        }
    }

    protected void tickBite() {
        this.timer++;
        LivingEntity target = kaprosuchus.getTarget();
        if (timer == 1) {
            this.kaprosuchus.attackAlt = kaprosuchus.getRandom().nextBoolean();
            this.kaprosuchus.setPose(UP2Poses.ATTACKING.get());
        }
        if (timer == 8) {
            if (this.isInAttackRange(target, 2.0D)) {
                this.kaprosuchus.doHurtTarget(target);
                this.kaprosuchus.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 10) {
            this.timer = 0;
            this.kaprosuchus.setPose(Pose.STANDING);
            this.kaprosuchus.attackCooldown = 4 + kaprosuchus.getRandom().nextInt(3);
            this.kaprosuchus.setAttackState(0);
        }
    }

    protected void tickLeap() {
        this.timer++;
        LivingEntity target = kaprosuchus.getTarget();
        this.kaprosuchus.getNavigation().stop();
        if (timer <= 10) {
            this.kaprosuchus.lookAt(target, 30F, 30F);
            this.kaprosuchus.getLookControl().setLookAt(target, 30F, 30F);
        }
        if (timer > 10 && kaprosuchus.onGround()) {
            this.kaprosuchus.setLeaping(true);
            Vec3 deltaMovement = kaprosuchus.getDeltaMovement();
            Vec3 vec3 = new Vec3(target.getX() - kaprosuchus.getX(), 0.0D, target.getZ() - kaprosuchus.getZ());
            if (vec3.lengthSqr() > 1.0E-7D) {
                vec3 = vec3.normalize().scale(1.1D).add(deltaMovement.scale(0.5D));
            }
            this.kaprosuchus.setDeltaMovement(vec3.x, 1.1F, vec3.z);
        }

        if (this.isInAttackRange(target, 0.5D) && timer > 20) {
            this.kaprosuchus.doHurtTarget(target);
            this.strongKnockback(target, 0.5D, 0.05D);
            this.kaprosuchus.swing(InteractionHand.MAIN_HAND);
            this.kaprosuchus.setLeaping(false);
            this.kaprosuchus.setPose(Pose.STANDING);
            this.kaprosuchus.setAttackState(0);
            this.kaprosuchus.leapCooldown();
            this.waitTimer = 15;
        } else if (timer > 80) {
            this.kaprosuchus.setPose(Pose.STANDING);
            this.kaprosuchus.setLeaping(false);
            this.kaprosuchus.setAttackState(0);
            this.kaprosuchus.leapCooldown();
            this.waitTimer = 15;
        }
    }

    private boolean isPathClear(LivingEntity target) {
        double z = target.getZ() - kaprosuchus.getZ();
        double x = target.getX() - kaprosuchus.getX();
        double d2 = z / x;

        for (int j = 0; j < 6; j++) {
            double d3 = d2 == 0.0D ? 0.0D : z * (j / 6.0D);
            double d4 = d2 == 0.0D ? x * (j / 6.0D) : d3 / d2;
            for (int k = 1; k < 4; k++) {
                if (!kaprosuchus.level().getBlockState(BlockPos.containing(kaprosuchus.getX() + d4, kaprosuchus.getY() + k, kaprosuchus.getZ() + d3)).canBeReplaced()) {
                    return false;
                }
            }
        }
        return true;
    }
}