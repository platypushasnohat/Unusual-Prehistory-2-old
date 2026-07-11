package com.barl_inc.unusual_prehistory.entity.ai.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;

public class FlyingRandomLookAroundGoal extends RandomLookAroundGoal {

    protected final Mob mob;

    public FlyingRandomLookAroundGoal(Mob mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return mob.onGround() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return mob.onGround() && super.canContinueToUse();
    }
}
