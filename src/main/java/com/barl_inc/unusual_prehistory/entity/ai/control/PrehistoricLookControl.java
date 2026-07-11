package com.barl_inc.unusual_prehistory.entity.ai.control;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.LeapingMob;
import net.minecraft.world.entity.ai.control.LookControl;

public class PrehistoricLookControl extends LookControl {

    protected final PrehistoricMob mob;
    protected final boolean resetXRotOnTick;

    public PrehistoricLookControl(PrehistoricMob mob) {
        this(mob, true);
    }

    public PrehistoricLookControl(PrehistoricMob mob, boolean resetXRotOnTick) {
        super(mob);
        this.mob = mob;
        this.resetXRotOnTick = resetXRotOnTick;
    }

    @Override
    public void tick() {
        if (!mob.refuseToLook()) {
            super.tick();
        }
    }

    @Override
    protected boolean resetXRotOnTick() {
        if (mob instanceof LeapingMob leapingMob) {
            return !leapingMob.isLeaping();
        }
        else {
            return resetXRotOnTick;
        }
    }
}
