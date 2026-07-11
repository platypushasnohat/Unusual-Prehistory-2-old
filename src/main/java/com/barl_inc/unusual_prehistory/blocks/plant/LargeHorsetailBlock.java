package com.barl_inc.unusual_prehistory.blocks.plant;

import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class LargeHorsetailBlock extends PrehistoricTallPlantBlock implements BonemealableBlock {

    public LargeHorsetailBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource source, @NotNull BlockPos pos, @NotNull BlockState state) {
        popResource(level, pos, new ItemStack(UP2Blocks.HORSETAIL.get()));
    }
}
