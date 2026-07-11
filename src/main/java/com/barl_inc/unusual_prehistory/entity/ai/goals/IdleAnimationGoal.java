package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.function.Predicate;

public class IdleAnimationGoal extends Goal {

    public final PrehistoricMob prehistoricMob;
    public final Predicate<Mob> canUse;
    public final int animationTime;
    public final int idleState;
    public final boolean stopMoving;
    public final float chance;

    public int timer;

    public IdleAnimationGoal(PrehistoricMob prehistoricMob, int animationTime, int idleState, boolean stopMoving, float chance) {
        this(prehistoricMob, animationTime, idleState, stopMoving, chance, LivingEntity::isAlive);
    }

    public IdleAnimationGoal(PrehistoricMob prehistoricMob, int animationTime, int idleState, boolean stopMoving, float chance, Predicate<Mob> canUse) {
        this.prehistoricMob = prehistoricMob;
        this.animationTime = animationTime;
        this.idleState = idleState;
        this.stopMoving = stopMoving;
        this.chance = chance;
        this.canUse = canUse;
    }

    @Override
    public boolean canUse() {
        if (this.isInCombat()) {
            return false;
        } else if (stopMoving && !prehistoricMob.getNavigation().isDone()) {
            return false;
        }
        return canUse.test(prehistoricMob) && prehistoricMob.getIdleAnimationCooldown() == 0 && prehistoricMob.getRandom().nextFloat() < chance && prehistoricMob.isAlive() && prehistoricMob.getIdleState() == 0 && !prehistoricMob.isEepy();
    }

    @Override
    public void start() {
        this.prehistoricMob.setIdleState(idleState);
        this.prehistoricMob.setIdleAnimationCooldown(prehistoricMob.getIdleAnimationCooldown(idleState));
        this.timer = animationTime;
        if (stopMoving) {
            this.prehistoricMob.getNavigation().stop();
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.isInCombat()) {
            return false;
        }
        return canUse.test(prehistoricMob) && timer > 0 && prehistoricMob.getIdleState() == idleState && prehistoricMob.isAlive() && !prehistoricMob.isEepy();
    }

    @Override
    public void tick() {
        this.timer--;
        if (stopMoving) {
            this.prehistoricMob.getNavigation().stop();
        }
    }

    @Override
    public void stop() {
        this.prehistoricMob.setIdleState(0);
        if (stopMoving) {
            this.prehistoricMob.getNavigation().stop();
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected boolean isInCombat() {
        return prehistoricMob.getLastHurtByMob() != null || prehistoricMob.getTarget() != null;
    }
}
