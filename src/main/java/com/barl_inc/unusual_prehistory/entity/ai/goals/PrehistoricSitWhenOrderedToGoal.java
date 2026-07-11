package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class PrehistoricSitWhenOrderedToGoal extends Goal {

    protected final PrehistoricMob tamedMob;
    protected final boolean stopSittingInWater;

    public PrehistoricSitWhenOrderedToGoal(PrehistoricMob mob) {
        this(mob, true);
    }

    public PrehistoricSitWhenOrderedToGoal(PrehistoricMob mob, boolean stopSittingInWater) {
        this.tamedMob = mob;
        this.stopSittingInWater = stopSittingInWater;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canContinueToUse() {
        return this.tamedMob.isOrderedToSit();
    }

    @Override
    public boolean canUse() {
        if (!tamedMob.isTame()) {
            return false;
        } else if (tamedMob.isInWaterOrBubble() && stopSittingInWater) {
            return false;
        } else if (!tamedMob.onGround() && !tamedMob.isInWaterOrBubble()) {
            return false;
        } else {
            LivingEntity livingentity = tamedMob.getOwner();
            if (livingentity == null) {
                return true;
            } else {
                return (!(tamedMob.distanceToSqr(livingentity) < 144.0D) || livingentity.getLastHurtByMob() == null) && tamedMob.isOrderedToSit();
            }
        }
    }

    @Override
    public void start() {
        this.tamedMob.setInSittingPose(true);
        this.tamedMob.getNavigation().stop();
        this.tamedMob.setSitting(true);
    }

    @Override
    public void stop() {
        this.tamedMob.setInSittingPose(false);
        if (tamedMob.isSitting() && tamedMob.getCommand() != 1) {
            this.tamedMob.setSitting(false);
        }
    }
}