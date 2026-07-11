package com.barl_inc.unusual_prehistory.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class FossilizedBoneRowBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<FossilizedBoneRowBlock> CODEC = simpleCodec(FossilizedBoneRowBlock::new);

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(
            ImmutableMap.of(
                    Direction.NORTH, Block.box(6, 0, 0, 10, 7, 16),
                    Direction.SOUTH, Block.box(6, 0, 0, 10, 7, 16),
                    Direction.EAST, Block.box(0, 0, 6, 16, 7, 10),
                    Direction.WEST, Block.box(0, 0, 6, 16, 7, 10)
            )
    );

    private static final Map<Direction, VoxelShape> ATTACHED_AABBS = Maps.newEnumMap(
            ImmutableMap.of(
                    Direction.NORTH, Block.box(6, 9, 0, 10, 16, 16),
                    Direction.SOUTH, Block.box(6, 9, 0, 10, 16, 16),
                    Direction.EAST, Block.box(0, 9, 6, 16, 16, 10),
                    Direction.WEST, Block.box(0, 9, 6, 16, 16, 10)
            )
    );

    public FossilizedBoneRowBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(ATTACHED, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected @NotNull MapCodec<FossilizedBoneRowBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(ATTACHED) ? ATTACHED_AABBS.get(state.getValue(FACING)) : AABBS.get(state.getValue(FACING));
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos().above();
        BlockState blockstate = level.getBlockState(blockpos);
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean facingDown = Block.isFaceFull(blockstate.getCollisionShape(level, blockpos), Direction.DOWN);
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(ATTACHED, facingDown).setValue(WATERLOGGED, fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, ATTACHED, WATERLOGGED);
    }
}
