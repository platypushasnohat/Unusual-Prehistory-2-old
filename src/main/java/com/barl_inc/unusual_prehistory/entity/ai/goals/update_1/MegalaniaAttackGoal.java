package com.barl_inc.unusual_prehistory.entity.ai.goals.update_1;

import com.barl_inc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Megalania;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class MegalaniaAttackGoal extends AttackGoal {

    private final Megalania megalania;

    public MegalaniaAttackGoal(Megalania megalania) {
        super(megalania);
        this.megalania = megalania;
        megalania.getNavigation().setCanFloat(false);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && megalania.getPose() != Pose.ROARING && !megalania.isSitting();
    }

    @Override
    public void tick() {
        LivingEntity target = megalania.getTarget();
        if (target != null) {
            this.megalania.lookAt(megalania.getTarget(), 30F, 30F);
            this.megalania.getLookControl().setLookAt(megalania.getTarget(), 30F, 30F);

            double distance = megalania.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = megalania.getAttackState();

            if (attackState == 1) {
                this.megalania.getNavigation().moveTo(target, megalania.isInWater() ? this.getSpeedMultiplier() * 0.25F : this.getSpeedMultiplier() * 0.4F);
                this.tickBite();
            }
            else if (attackState == 2) {
                this.tickTailWhip();
            }
            else {
                this.megalania.getNavigation().moveTo(target, megalania.isInWater() ? this.getSpeedMultiplier() * 0.7F : this.getSpeedMultiplier());
                if (distance <= this.getAttackReachSqr(target)) {
                    this.selectAttack();
                }
            }
        }
    }

    private double getSpeedMultiplier() {
        switch (megalania.getTemperatureState()) {
            case COLD -> {
                return 1.4D;
            }
            case WARM -> {
                return 1.9D;
            }
            case NETHER -> {
                return 1.5D;
            }
            default -> {
                return 2.0D;
            }
        }
    }

    private void selectAttack() {
        if (megalania.isInWater() && megalania.attackCooldown == 0) {
            this.megalania.setAttackState(1);
        } else {
            if (megalania.getRandom().nextBoolean() && megalania.talWhipCooldown == 0) megalania.setAttackState(2);
            else if (megalania.attackCooldown == 0) megalania.setAttackState(1);
        }
    }

    private void tickBite() {
        this.timer++;
        LivingEntity target = megalania.getTarget();
        if (timer == 1) {
            this.megalania.attackAlt = megalania.getRandom().nextBoolean();
            this.megalania.setPose(UP2Poses.ATTACKING.get());
        }
        if (timer == 5) megalania.playSound(UP2SoundEvents.MEGALANIA_BITE.get(), 1.0F, 1.0F);
        if (timer == 11) {
            if (this.isInAttackRange(target, 1.4D)) {
                this.megalania.swing(InteractionHand.MAIN_HAND);
                this.megalania.doHurtTarget(target);
                this.megalania.applyPoison(target);
            }
        }
        if (timer > 20) {
            this.timer = 0;
            this.megalania.attackCooldown = 5 + megalania.getRandom().nextInt(3);
            this.megalania.setPose(Pose.STANDING);
            this.megalania.setAttackState(0);
        }
    }

    private void tickTailWhip() {
        this.timer++;
        this.megalania.getNavigation().stop();

        if (timer == 1) megalania.setPose(UP2Poses.TAIL_WHIPPING.get());
        if (timer == 9) megalania.playSound(UP2SoundEvents.MEGALANIA_TAIL_SWING.get(), 1.0F, 1.0F);
        if (timer == 14) {
            this.whipNearbyEnemies();
        }
        if (timer > 30) {
            this.timer = 0;
            this.megalania.talWhipCooldown = 100 + megalania.getRandom().nextInt(50);
            this.megalania.setPose(Pose.STANDING);
            this.megalania.setAttackState(0);
        }
    }

    private void whipNearbyEnemies() {
        List<LivingEntity> nearbyEntities = megalania.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), megalania, megalania.getBoundingBox().inflate(2.7, -0.25, 2.7));
        if (!nearbyEntities.isEmpty()) {
            nearbyEntities.stream().filter(entity -> !entity.is(megalania) && !entity.isAlliedTo(megalania)).limit(3).forEach(entity -> {
                entity.hurt(entity.damageSources().mobAttack(megalania), (float) megalania.getAttributeValue(Attributes.ATTACK_DAMAGE));
                this.strongKnockback(entity, 1.3D, 0.2D);
                if (entity.isDamageSourceBlocked(megalania.damageSources().mobAttack(megalania)) && entity instanceof Player player) {
                    player.disableShield();
                }
                this.megalania.swing(InteractionHand.MAIN_HAND);
            });
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 1.75F * this.mob.getBbWidth() * 1.75F + target.getBbWidth();
    }
}
