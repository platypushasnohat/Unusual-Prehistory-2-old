package com.barl_inc.unusual_prehistory.entity.ai.control;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;

public class SwimmingMoveControl extends SmoothSwimmingMoveControl {

    public SwimmingMoveControl(Mob mob, int maxTurnX, int maxTurnY, float speedModifier) {
        super(mob, maxTurnX, maxTurnY, speedModifier, 0.1F, false);
    }
}
