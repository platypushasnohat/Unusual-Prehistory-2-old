package com.barl_inc.unusual_prehistory.entity.ai.control;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;

public class PrehistoricSwimmingLookControl extends SmoothSwimmingLookControl {

    protected final PrehistoricMob mob;

    public PrehistoricSwimmingLookControl(PrehistoricMob mob, int maxYRotFromCenter) {
        super(mob, maxYRotFromCenter);
        this.mob = mob;
    }

    @Override
    public void tick() {
        if (!mob.refuseToLook()) super.tick();
    }
}
