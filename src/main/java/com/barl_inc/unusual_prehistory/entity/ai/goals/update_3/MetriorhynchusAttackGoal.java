package com.barl_inc.unusual_prehistory.entity.ai.goals.update_3;

import com.barl_inc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Metriorhynchus;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class MetriorhynchusAttackGoal extends AttackGoal {

    private final Metriorhynchus metriorhynchus;

    public MetriorhynchusAttackGoal(Metriorhynchus metriorhynchus) {
        super(metriorhynchus);
        this.metriorhynchus = metriorhynchus;
    }

    @Override
    public void tick() {
        LivingEntity target = this.metriorhynchus.getTarget();
        if (target != null) {
            double distance = this.metriorhynchus.distanceToSqr(target);
            int attackState = this.metriorhynchus.getAttackState();

            this.metriorhynchus.getLookControl().setLookAt(target, 30F, 30F);

            if (attackState == 1) this.tickBite();
            else if (attackState == 2) this.tickDeathRoll();
            else if (distance <= this.getAttackReachSqr(target)) {
                if (this.canGrab()) this.metriorhynchus.setAttackState(2);
                else if (metriorhynchus.attackCooldown == 0 && !this.canGrab()) this.metriorhynchus.setAttackState(1);
            } else {
                this.metriorhynchus.getNavigation().moveTo(target, 1.5D);
            }
        }
    }

    private boolean canGrab() {
        return metriorhynchus.grabCooldown == 0 && !(metriorhynchus.getTarget() instanceof Player) && metriorhynchus.isInWater() && metriorhynchus.canPickUpTarget(metriorhynchus.getTarget());
    }

    protected void tickBite() {
        this.timer++;
        LivingEntity target = metriorhynchus.getTarget();
        this.metriorhynchus.getNavigation().moveTo(target, metriorhynchus.isInWater() ? 1.1D : 1.3D);
        if (timer == 1) {
            this.metriorhynchus.attackAlt = metriorhynchus.getRandom().nextBoolean();
            this.metriorhynchus.setPose(UP2Poses.ATTACKING.get());
        }
        if (timer == 2) metriorhynchus.playSound(UP2SoundEvents.METRIORHYNCHUS_BITE.get(), 1.0F, metriorhynchus.getVoicePitch());
        if (timer == 5) {
            if (this.isInAttackRange(target, 1.6D)) {
                this.metriorhynchus.doHurtTarget(target);
                this.metriorhynchus.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 10) {
            this.timer = 0;
            this.metriorhynchus.setAttackState(0);
            this.metriorhynchus.setPose(Pose.STANDING);
            this.metriorhynchus.attackCooldown = 5;
        }
    }

    protected void tickDeathRoll() {
        this.timer++;
        this.metriorhynchus.getNavigation().stop();
        LivingEntity target = metriorhynchus.getTarget();
        if (timer == 1) {
            this.metriorhynchus.grabAlt = metriorhynchus.getRandom().nextBoolean();
            this.metriorhynchus.setPose(UP2Poses.GRABBING.get());
        }
        if (timer == 3) metriorhynchus.playSound(UP2SoundEvents.METRIORHYNCHUS_BITE.get(), 1.0F, metriorhynchus.getVoicePitch() * 0.9F);
        if (timer == 5) {
            if (this.isInAttackRange(target, 1.5D)) {
                this.metriorhynchus.setHeldMobId(target.getId());
            }
        }
        if (timer > 5 && timer <= 40 && metriorhynchus.getHeldMobId() != -1) {
            Entity entity = metriorhynchus.level().getEntity(metriorhynchus.getHeldMobId());
            if (entity != null) {
                this.positionHeldMob(entity);
                if (timer % 20 == 0) {
                    entity.hurt(metriorhynchus.damageSources().mobAttack(metriorhynchus), (float) metriorhynchus.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.6F);
                }
            } else {
                this.metriorhynchus.setHeldMobId(-1);
            }
        }
        if (timer > 40) {
            this.timer = 0;
            this.metriorhynchus.setAttackState(0);
            this.metriorhynchus.setPose(Pose.STANDING);
            this.metriorhynchus.grabCooldown = 200 + metriorhynchus.getRandom().nextInt(200);
            if (metriorhynchus.getHeldMobId() != -1) {
                this.metriorhynchus.setHeldMobId(-1);
            }
        }
    }

    private void positionHeldMob(Entity entity) {
        Vec3 heldPos = metriorhynchus.getEyePosition().add(new Vec3(0.0F, 0.0F, 2.2F).yRot(-metriorhynchus.yBodyRot * ((float) Math.PI / 180F)));
        Vec3 minus = new Vec3(heldPos.x - entity.getX(), heldPos.y - entity.getY(), heldPos.z - entity.getZ());
        entity.setDeltaMovement(minus);
        entity.fallDistance = 0.0F;
        entity.setYRot(0.0F);
        entity.setYBodyRot(0.0F);
        entity.setYHeadRot(0.0F);
        entity.setXRot(0.0F);
        entity.setDeltaMovement(minus);
        entity.setDeltaMovement(entity.getDeltaMovement().scale(0.4F));
    }
}