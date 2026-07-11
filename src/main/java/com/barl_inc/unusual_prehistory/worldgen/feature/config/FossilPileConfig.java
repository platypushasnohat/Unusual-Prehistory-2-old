package com.barl_inc.unusual_prehistory.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record FossilPileConfig(BlockStateProvider state, BlockStateProvider floorState, Holder<PlacedFeature> feature) implements FeatureConfiguration {

    public static final Codec<FossilPileConfig> CODEC = RecordCodecBuilder.create((configInstance) ->
            configInstance.group(
                            BlockStateProvider.CODEC.fieldOf("state").forGetter((config) -> config.state),
                            BlockStateProvider.CODEC.fieldOf("floor_state").forGetter((config) -> config.floorState),
                            PlacedFeature.CODEC.fieldOf("feature").forGetter((config) -> config.feature))
                    .apply(configInstance, FossilPileConfig::new)
    );

}