package com.barl_inc.unusual_prehistory.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TintedConnectedGlassBlock extends ConnectedGlassBlock {

    public TintedConnectedGlassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos) {
        return false;
    }

    @Override
    public int getLightBlock(@NotNull BlockState state, BlockGetter blockGetter, @NotNull BlockPos pos) {
        return blockGetter.getMaxLightLevel();
    }
}