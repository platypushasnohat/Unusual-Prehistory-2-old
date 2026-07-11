package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;

public class PrehistoricFollowParentGoal extends FollowParentGoal {

    protected final PrehistoricMob prehistoricMob;

    public PrehistoricFollowParentGoal(PrehistoricMob prehistoricMob, double speedModifier) {
        super(prehistoricMob, speedModifier);
        this.prehistoricMob = prehistoricMob;
    }

    @Override
    public boolean canUse() {
        if (prehistoricMob.getLastHurtByMob() != null) {
            return false;
        } else {
            return super.canUse();
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (prehistoricMob.getLastHurtByMob() != null) {
            return false;
        } else {
            return super.canContinueToUse();
        }
    }
}
