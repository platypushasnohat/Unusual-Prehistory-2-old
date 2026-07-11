package com.barl_inc.unusual_prehistory.entity.utils;

import net.minecraft.world.entity.Entity;

public interface GrabbingMob {

    void setHeldMobId(int id);

    int getHeldMobId();

    default Entity getHeldMob(Entity entity) {
        int id = this.getHeldMobId();
        return id == -1 ? null : entity.level().getEntity(id);
    }
}
