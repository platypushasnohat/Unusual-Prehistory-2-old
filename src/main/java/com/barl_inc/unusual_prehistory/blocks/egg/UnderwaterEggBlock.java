package com.barl_inc.unusual_prehistory.blocks.egg;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class UnderwaterEggBlock extends EggBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public UnderwaterEggBlock(Properties properties, Supplier<EntityType<?>> hatchedEntity, int hatchAmount) {
        this(properties, hatchedEntity, hatchAmount, 16.0D, 1.5D);
    }

    public UnderwaterEggBlock(Properties properties, Supplier<EntityType<?>> hatchedEntity, int hatchAmount, double widthPx, double heightPx) {
        super(properties, hatchedEntity, hatchAmount, widthPx, heightPx, SoundEvents.FROGSPAWN_HATCH, false);
        this.registerDefaultState(this.defaultBlockState().setValue(HATCH, 0).setValue(WATERLOGGED, false));
    }

    @Override
    public boolean hatchBoost(BlockGetter blockGetter, BlockPos pos) {
        return true;
    }

    @Override
    protected void spawnParticles(Level level, BlockPos pos) {
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        BlockPos blockpos = pos.below();
        FluidState fluidstate = level.getFluidState(pos);
        return this.mayPlaceOn(level.getBlockState(blockpos), level, blockpos) && fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8;
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return state.isFaceSturdy(blockGetter, pos, Direction.UP) && !state.is(Blocks.MAGMA_BLOCK);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState state = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, state.is(FluidTags.WATER) && state.getAmount() == 8);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
