package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class RandomSitGoal extends Goal {

    protected final PrehistoricMob prehistoricMob;
    protected int timer;

    public RandomSitGoal(PrehistoricMob prehistoricMob) {
        this.prehistoricMob = prehistoricMob;
    }

    @Override
    public boolean canUse() {
        return prehistoricMob.getLastHurtByMob() == null && prehistoricMob.getTarget() == null && !prehistoricMob.isInWaterOrBubble() && !prehistoricMob.isInLava() && prehistoricMob.getSitCooldown() == 0 && !prehistoricMob.isEepy() && !prehistoricMob.isBaby() && !prehistoricMob.isFollowingOwner();
    }

    @Override
    public boolean canContinueToUse() {
        if (prehistoricMob.getLastHurtByMob() != null || !super.canContinueToUse() || prehistoricMob.getTarget() != null || prehistoricMob.isInWaterOrBubble() || prehistoricMob.isInLava() || prehistoricMob.isFollowingOwner() || prehistoricMob.isLeashed()) {
            this.stop();
            return false;
        }
        else return prehistoricMob.getSittingTicks() > 0;
    }

    @Override
    public void start() {
        this.timer = 0;
        this.prehistoricMob.xxa = 0.0F;
        this.prehistoricMob.yya = 0.0F;
        this.prehistoricMob.zza = 0.0F;
        this.prehistoricMob.getNavigation().stop();
        if (prehistoricMob.getSittingTicks() == 0) {
            this.prehistoricMob.setSittingTicks(1200 + prehistoricMob.getRandom().nextInt(1200));
        }
        this.prehistoricMob.setSitting(true);
    }

    @Override
    public void stop() {
        this.prehistoricMob.setSittingTicks(0);
        this.prehistoricMob.setSitCooldown(3000 + prehistoricMob.getRandom().nextInt(3000));
        this.prehistoricMob.setSitting(false);
    }

    @Override
    public void tick() {
        this.timer++;
        this.prehistoricMob.getNavigation().stop();
        if (prehistoricMob.getLastHurtByMob() != null || prehistoricMob.getTarget() != null || prehistoricMob.isInWaterOrBubble() || prehistoricMob.isInLava() || prehistoricMob.isFollowingOwner() || prehistoricMob.isLeashed()) {
            this.stop();
        }
    }
}
