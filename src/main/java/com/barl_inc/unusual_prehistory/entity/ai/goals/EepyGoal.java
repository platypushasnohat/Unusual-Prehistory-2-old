package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class EepyGoal extends Goal {

    protected final PrehistoricMob prehistoricMob;
    protected final boolean stopInWater;

    public EepyGoal(PrehistoricMob prehistoricMob) {
        this(prehistoricMob, true);
    }

    public EepyGoal(PrehistoricMob prehistoricMob, boolean stopInWater) {
        this.prehistoricMob = prehistoricMob;
        this.stopInWater = stopInWater;
        // todo: maybe enable this later and make sleep priority higher?
//        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return !this.shouldStopEepy() && prehistoricMob.getEepyCooldown() == 0 && prehistoricMob.getRandom().nextInt(80) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.shouldStopEepy()) {
            this.stop();
            return false;
        }
        else return true;
    }

    protected boolean shouldStopEepy() {
        return prehistoricMob.isVehicle() || !prehistoricMob.isEepyTime() || prehistoricMob.getLastHurtByMob() != null || prehistoricMob.getTarget() != null || (stopInWater && prehistoricMob.isInWaterOrBubble()) || prehistoricMob.isInLava() || prehistoricMob.isSitting() || prehistoricMob.isBaby() || prehistoricMob.isLeashed() || prehistoricMob.isFollowingOwner();
    }

    @Override
    public void start() {
        this.prehistoricMob.xxa = 0.0F;
        this.prehistoricMob.yya = 0.0F;
        this.prehistoricMob.zza = 0.0F;
        this.prehistoricMob.getNavigation().stop();
        this.prehistoricMob.setEepy(true);
    }

    @Override
    public void stop() {
        this.prehistoricMob.setEepyCooldown(100);
        this.prehistoricMob.setEepy(false);
    }
}