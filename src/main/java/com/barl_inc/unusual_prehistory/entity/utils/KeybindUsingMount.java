package com.barl_inc.unusual_prehistory.entity.utils;

import net.minecraft.world.entity.Entity;

public interface KeybindUsingMount {

    void onKeyPacket(Entity keyPresser, int type);
}
