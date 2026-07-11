package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.GrabbingMob;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class AttackGoal extends Goal {

    protected int timer = 0;
    protected final PrehistoricMob mob;

    public AttackGoal(PrehistoricMob mob) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.mob = mob;
    }

    @Override
    public void start() {
        this.mob.setPose(Pose.STANDING);
        this.mob.setAttackState(0);
        this.mob.setAggressive(true);
        this.mob.setRunning(true);
        this.timer = 0;
        if (mob instanceof GrabbingMob grabbingMob) {
            grabbingMob.setHeldMobId(-1);
        }
    }

    @Override
    public void stop() {
        this.mob.setPose(Pose.STANDING);
        this.mob.setTarget(null);
        this.mob.setAttackState(0);
        this.mob.setAggressive(false);
        this.mob.setRunning(false);
        this.mob.getNavigation().stop();
        if (mob instanceof GrabbingMob grabbingMob) {
            grabbingMob.setHeldMobId(-1);
        }
    }

    @Override
    public boolean canUse() {
        return !mob.isBaby() && mob.getTarget() != null && mob.getTarget().isAlive() && !mob.isVehicle() && !mob.isSitting() && !mob.isEepy();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = mob.getTarget();
        if (target == null) return false;
        else if (!target.isAlive()) return false;
        else if (!mob.isWithinRestriction(target.blockPosition())) return false;
        else return !(target instanceof Player) || !target.isSpectator() && !((Player) target).isCreative() || !mob.getNavigation().isDone();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected double getAttackReachSqr(LivingEntity target) {
        return mob.getBbWidth() * 2.0F * mob.getBbWidth() * 2.0F + target.getBbWidth();
    }

    protected double getAttackReachSqr(LivingEntity target, double distance) {
        return mob.getBbWidth() * distance * mob.getBbWidth() * distance + target.getBbWidth();
    }

    protected boolean isInAttackRange(LivingEntity target, double reach) {
        return mob.hasLineOfSight(target) && mob.distanceTo(target) < mob.getBbWidth() + target.getBbWidth() + reach;
    }

    protected void chargeAtTarget(Entity target, float speed) {
        int speedFactor = mob.hasEffect(MobEffects.MOVEMENT_SPEED) ? mob.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
        int slownessFactor = mob.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? mob.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
        float effectSpeed = 0.1F * (speedFactor - slownessFactor);
        Vec3 chargeDirection = new Vec3(target.getX() - mob.getX(), target.getY() - mob.getY(), target.getZ() - mob.getZ()).normalize();
        float YRot = Mth.approachDegrees(mob.getYRot(), (float) (Mth.atan2(chargeDirection.z, chargeDirection.x) * (180F / Math.PI)) - 90.0F, 0.25F);
        speed = speed + effectSpeed;
        this.mob.setYRot(YRot);
        this.mob.setYBodyRot(YRot);
        this.mob.setDeltaMovement(-Mth.sin(YRot * ((float) Math.PI / 180F)) * speed, mob.getDeltaMovement().y, Mth.cos(YRot * ((float) Math.PI / 180F)) * speed);
    }

    protected void strongKnockback(Entity entity, double horizontalStrength, double verticalStrength) {
        double x = entity.getX() - mob.getX();
        double y = entity.getZ() - mob.getZ();
        double scale = Math.max(x * x + y * y, 0.001D);
        entity.push(x / scale * horizontalStrength, verticalStrength, y / scale * horizontalStrength);
    }

    protected void lookAtTarget(LivingEntity target, float yaw, float pitch) {
        this.mob.getLookControl().setLookAt(target, yaw, pitch);
        this.mob.lookAt(target, yaw, pitch);
    }
}
