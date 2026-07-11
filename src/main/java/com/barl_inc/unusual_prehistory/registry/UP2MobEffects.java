package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.potions.Dazzled;
import com.barl_inc.unusual_prehistory.potions.Paralysis;
import com.barl_inc.unusual_prehistory.potions.Tranquility;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2MobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> DAZZLED = MOB_EFFECTS.register("dazzled", Dazzled::new);
    public static final DeferredHolder<MobEffect, MobEffect> PARALYSIS = MOB_EFFECTS.register("paralysis", Paralysis::new);
    public static final DeferredHolder<MobEffect, MobEffect> TRANQUILITY = MOB_EFFECTS.register("tranquility", Tranquility::new);

}
