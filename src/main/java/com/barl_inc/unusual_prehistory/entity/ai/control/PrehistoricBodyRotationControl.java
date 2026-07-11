package com.barl_inc.unusual_prehistory.entity.ai.control;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class PrehistoricBodyRotationControl extends BodyRotationControl {

    protected final PrehistoricMob mob;

    public PrehistoricBodyRotationControl(PrehistoricMob mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void clientTick() {
        if (!mob.refuseToLook()) super.clientTick();
    }
}
