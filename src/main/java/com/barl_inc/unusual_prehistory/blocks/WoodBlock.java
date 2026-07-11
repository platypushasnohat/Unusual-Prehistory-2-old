package com.barl_inc.unusual_prehistory.blocks;

import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WoodBlock extends RotatedPillarBlock {

    public WoodBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFlammable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(@NotNull BlockState state, @NotNull UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility.equals(ItemAbilities.AXE_STRIP)) {
            BlockState checkedState;

            checkedState = UP2Blocks.DRYOPHYLLUM.checkLogStripping(state);
            if (checkedState != null) {
                return checkedState;
            }

            checkedState = UP2Blocks.GINKGO.checkLogStripping(state);
            if (checkedState != null) {
                return checkedState;
            }

            checkedState = UP2Blocks.LEPIDODENDRON.checkLogStripping(state);
            if (checkedState != null) {
                return checkedState;
            }

            checkedState = UP2Blocks.METASEQUOIA.checkLogStripping(state);
            if (checkedState != null) {
                return checkedState;
            }

        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }

}
