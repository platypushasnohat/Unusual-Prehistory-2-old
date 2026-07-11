package com.barl_inc.unusual_prehistory.blocks.egg;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RaftEggBlock extends EggBlock {

    public RaftEggBlock(Properties properties, Supplier<EntityType<?>> hatchedEntity, int hatchAmount) {
        super(properties, hatchedEntity, hatchAmount, 16, 1.5D, SoundEvents.FROGSPAWN_HATCH, false);
    }

    @Override
    protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, BlockPos pos) {
        return mayPlaceOn(level, pos.below());
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        return !this.canSurvive(state, level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean hatchBoost(BlockGetter blockGetter, BlockPos pos) {
        return blockGetter.getBlockState(pos.below()).getBlock() instanceof BubbleColumnBlock;
    }

    private static boolean mayPlaceOn(BlockGetter level, BlockPos pos) {
        FluidState fluidstate = level.getFluidState(pos);
        FluidState aboveFluidstate = level.getFluidState(pos.above());
        return fluidstate.getType() == Fluids.WATER && aboveFluidstate.getType() == Fluids.EMPTY;
    }
}
