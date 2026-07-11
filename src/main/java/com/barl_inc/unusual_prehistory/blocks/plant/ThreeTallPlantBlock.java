package com.barl_inc.unusual_prehistory.blocks.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ThreeTallPlantBlock extends PrehistoricPlantBlock implements BonemealableBlock {

    public static final IntegerProperty LAYER = IntegerProperty.create("layer", 0, 2);
    protected static final VoxelShape BOTTOM_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    protected static final VoxelShape TOP_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 14.0D, 12.0D);

    public ThreeTallPlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSurvive(BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        if (state.getValue(LAYER) == 0) {
            return super.canSurvive(state, level, pos);
        } else {
            BlockState blockstate = level.getBlockState(pos.below());
            if (state.getBlock() != this) return super.canSurvive(state, level, pos);
            return blockstate.getBlock() == this;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        return blockpos.getY() < context.getLevel().getMaxBuildHeight() - 1 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context) && context.getLevel().getBlockState(blockpos.above(2)).canBeReplaced(context) ? super.getStateForPlacement(context) : null;
    }

    @Override
    public void setPlacedBy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
        level.setBlock(pos, this.defaultBlockState().setValue(LAYER, 0), 2);
        level.setBlock(pos.above(), this.defaultBlockState().setValue(LAYER, 1), 2);
        level.setBlock(pos.above(2), this.defaultBlockState().setValue(LAYER, 2), 2);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, BlockState state, @NotNull Player player) {
        if (state.getValue(LAYER) == 0) {
            if (!player.isCreative()) {
                level.destroyBlock(pos, true);
                level.destroyBlock(pos.above(), false);
                level.destroyBlock(pos.above(2), false);
            } else {
                level.destroyBlock(pos, false);
                level.destroyBlock(pos.above(), false);
                level.destroyBlock(pos.above(2), false);
            }
        } else if (state.getValue(LAYER) == 1) {
            if (!player.isCreative()) {
                level.destroyBlock(pos.below(), true);
                level.destroyBlock(pos, false);
                level.destroyBlock(pos.above(), false);
            } else {
                level.destroyBlock(pos.below(), false);
                level.destroyBlock(pos, false);
                level.destroyBlock(pos.above(), false);
            }
        } else if (state.getValue(LAYER) == 2) {
            if(!player.isCreative()) {
                level.destroyBlock(pos.below(2), true);
                level.destroyBlock(pos.below(), false);
                level.destroyBlock(pos, false);
            } else {
                level.destroyBlock(pos.below(2), false);
                level.destroyBlock(pos.below(), false);
                level.destroyBlock(pos, false);
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYER);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        popResource(level, pos, new ItemStack(this));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);
        if (state.getValue(LAYER) == 2) {
            return TOP_SHAPE.move(offset.x, offset.y, offset.z);
        }
        return BOTTOM_SHAPE.move(offset.x, offset.y, offset.z);
    }
}