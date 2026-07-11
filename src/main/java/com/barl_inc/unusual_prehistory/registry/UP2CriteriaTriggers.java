package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2CriteriaTriggers {

    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<CriterionTrigger<?>, PlayerTrigger> OPEN_BOOK_CREATIVE_MODE = TRIGGERS.register("open_book_creative_mode", PlayerTrigger::new);

}
