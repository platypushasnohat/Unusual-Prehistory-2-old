package com.barl_inc.unusual_prehistory.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

public class ConnectedGlassBlock extends TransparentBlock {

    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");

    public ConnectedGlassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(UP, false).setValue(DOWN, false).setValue(EAST, false).setValue(WEST, false).setValue(NORTH, false).setValue(SOUTH, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelReader level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockPos north = blockpos.north();
        BlockPos east = blockpos.east();
        BlockPos south = blockpos.south();
        BlockPos west = blockpos.west();
        BlockPos up = blockpos.above();
        BlockPos down = blockpos.below();
        BlockState northState = level.getBlockState(north);
        BlockState eastState = level.getBlockState(east);
        BlockState southState = level.getBlockState(south);
        BlockState westState = level.getBlockState(west);
        BlockState upState = level.getBlockState(up);
        BlockState downState = level.getBlockState(down);
        return defaultBlockState().setValue(NORTH, northState.is(this)).setValue(NORTH, northState.is(this)).setValue(EAST, eastState.is(this)).setValue(SOUTH, southState.is(this)).setValue(WEST, westState.is(this)).setValue(UP, upState.is(this)).setValue(DOWN, downState.is(this));
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState state2, LevelAccessor level, BlockPos blockpos, @NotNull BlockPos pos2) {
        BlockPos north = blockpos.north();
        BlockPos east = blockpos.east();
        BlockPos south = blockpos.south();
        BlockPos west = blockpos.west();
        BlockPos up = blockpos.above();
        BlockPos down = blockpos.below();
        BlockState northState = level.getBlockState(north);
        BlockState eastState = level.getBlockState(east);
        BlockState southState = level.getBlockState(south);
        BlockState westState = level.getBlockState(west);
        BlockState upState = level.getBlockState(up);
        BlockState downState = level.getBlockState(down);
        return state.setValue(NORTH, northState.is(this)).setValue(NORTH, northState.is(this)).setValue(EAST, eastState.is(this)).setValue(SOUTH, southState.is(this)).setValue(WEST, westState.is(this)).setValue(UP, upState.is(this)).setValue(DOWN, downState.is(this));
    }
}