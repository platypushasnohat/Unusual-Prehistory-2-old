package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;

public class AmphibiousWanderGoal extends PrehistoricWanderGoal {

    protected final PrehistoricAmphibiousMob semiAquaticMob;

    public AmphibiousWanderGoal(PrehistoricAmphibiousMob mob, double speedModifier) {
        this(mob, speedModifier, 18, 7, 120);
    }

    public AmphibiousWanderGoal(PrehistoricAmphibiousMob mob, double speedModifier, int radius, int height, int interval) {
        super(mob, speedModifier, radius, height, interval, true);
        this.semiAquaticMob = mob;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !semiAquaticMob.isInWaterOrBubble();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !semiAquaticMob.isInWaterOrBubble();
    }
}
