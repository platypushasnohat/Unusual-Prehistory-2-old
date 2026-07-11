package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.worldgen.feature.FossilPileFeature;
import com.barl_inc.unusual_prehistory.worldgen.feature.config.FossilPileConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2FeatureTypes {

    public static final DeferredRegister<Feature<?>> FEATURE_TYPES = DeferredRegister.create(Registries.FEATURE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<FossilPileConfig>> FOSSIL_PILE = FEATURE_TYPES.register("fossil_pile", () -> new FossilPileFeature(FossilPileConfig.CODEC));

}
