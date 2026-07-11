package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;

public class RandomUnderwaterSitGoal extends RandomSitGoal {

    public RandomUnderwaterSitGoal(PrehistoricMob prehistoricMob) {
        super(prehistoricMob);
    }

    @Override
    public boolean canUse() {
        return prehistoricMob.getLastHurtByMob() == null && prehistoricMob.getTarget() == null && prehistoricMob.isInWaterOrBubble() && !prehistoricMob.isInLava() && prehistoricMob.getSitCooldown() == 0 && !prehistoricMob.isEepy() && !prehistoricMob.isBaby() && !prehistoricMob.isFollowingOwner();
    }

    @Override
    public boolean canContinueToUse() {
        if (prehistoricMob.getLastHurtByMob() != null || !this.canUse() || prehistoricMob.getTarget() != null || !prehistoricMob.isInWaterOrBubble() || prehistoricMob.isInLava() || prehistoricMob.isFollowingOwner() || prehistoricMob.isLeashed()) {
            this.stop();
            return false;
        }
        else return prehistoricMob.getSittingTicks() > 0;
    }

    @Override
    public void tick() {
        this.timer++;
        this.prehistoricMob.getNavigation().stop();
        if (prehistoricMob.getLastHurtByMob() != null || prehistoricMob.getTarget() != null || !prehistoricMob.isInWaterOrBubble() || prehistoricMob.isInLava() || prehistoricMob.isFollowingOwner() || prehistoricMob.isLeashed()) {
            this.stop();
        }
    }
}
