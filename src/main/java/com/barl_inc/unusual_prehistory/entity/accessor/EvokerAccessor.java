package com.barl_inc.unusual_prehistory.entity.accessor;

import net.minecraft.world.entity.LivingEntity;

public interface EvokerAccessor {

    void unusualPrehistory$setSpellCastingTickCount(int spellCastingTickCount);

    void unusualPrehistory$setLivingWololoTarget(LivingEntity entity);

    LivingEntity unusualPrehistory$getLivingWololoTarget();

}
