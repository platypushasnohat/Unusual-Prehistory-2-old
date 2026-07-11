package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;

public class FlyingGroundWanderGoal extends PrehistoricWanderGoal {

    protected final PrehistoricFlyingMob flyingMob;

    public FlyingGroundWanderGoal(PrehistoricFlyingMob flyingMob, double speedModifier) {
        this(flyingMob, speedModifier, 120, 18, 7, true);
    }

    public FlyingGroundWanderGoal(PrehistoricFlyingMob flyingMob, double speedModifier, boolean shouldAvoidWater) {
        this(flyingMob, speedModifier, 120, 18, 7, shouldAvoidWater);
    }

    public FlyingGroundWanderGoal(PrehistoricFlyingMob flyingMob, double speedModifier, int interval, int radius, int height, boolean shouldAvoidWater) {
        super(flyingMob, speedModifier, interval, radius, height, shouldAvoidWater);
        this.flyingMob = flyingMob;
    }

    @Override
    public boolean canUse() {
        return !flyingMob.isFlying() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !flyingMob.isFlying() && super.canContinueToUse();
    }
}
