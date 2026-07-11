package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;

public class PrehistoricBabyPanicGoal extends PrehistoricPanicGoal {

    public PrehistoricBabyPanicGoal(PrehistoricMob mob, double speedModifier) {
        this(mob, speedModifier, 10, 4, false);
    }

    public PrehistoricBabyPanicGoal(PrehistoricMob mob, double speedModifier, int radius, int height) {
        this(mob, speedModifier, radius, height, false);
    }

    public PrehistoricBabyPanicGoal(PrehistoricMob mob, double speedModifier, int radius, int height, boolean shouldEscapeToWater) {
        super(mob, speedModifier, radius, height, shouldEscapeToWater);
    }

    @Override
    protected boolean shouldPanic() {
        return (this.mob.getLastHurtByMob() != null || this.mob.isFreezing() || this.mob.isOnFire()) && mob.isBaby();
    }
}

