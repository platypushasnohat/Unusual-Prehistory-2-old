package com.barl_inc.unusual_prehistory.blocks.egg;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MultipleEggBlock extends EggBlock {

    private final VoxelShape multipleShape;
    public static final IntegerProperty EGGS = BlockStateProperties.EGGS;
    private final int maxEggs;

    public MultipleEggBlock(Properties properties, Supplier<EntityType<?>> hatchedEntity, int hatchAmount, int maxEggs, int widthPx, int heightPx, int multipleWidthPx, int multipleHeightPx) {
        super(properties, hatchedEntity, hatchAmount, widthPx, heightPx);
        this.maxEggs = maxEggs;
        int px = (16 - multipleWidthPx) / 2;
        this.multipleShape = Block.box(px, 0, px, 16 - px, multipleHeightPx, 16 - px);
        this.registerDefaultState(this.defaultBlockState().setValue(HATCH, 0).setValue(EGGS, 1));
    }

    protected void removeOneEgg(Level level, BlockPos pos, BlockState state) {
        level.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.getRandom().nextFloat() * 0.2F);
        int i = state.getValue(EGGS);
        if (i <= 1) {
            level.destroyBlock(pos, false);
        } else {
            level.setBlock(pos, state.setValue(EGGS, i - 1), 2);
            level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
            level.levelEvent(2001, pos, Block.getId(state));
        }
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack stack) {
        super.playerDestroy(level, player, pos, state, blockEntity, stack);
        this.removeOneEgg(level, pos, state);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(EGGS) > 1 ? multipleShape : shape;
    }

    @Override
    public boolean canBeReplaced(@NotNull BlockState state, BlockPlaceContext context) {
        return context.getItemInHand().getItem() == this.asItem() && state.getValue(EGGS) < maxEggs || super.canBeReplaced(state, context);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        return blockstate.getBlock() == this ? blockstate.setValue(EGGS, Math.min(maxEggs, blockstate.getValue(EGGS) + 1)) : super.getStateForPlacement(context);
    }

    @Override
    protected int getMobsBornFrom(BlockState state) {
        return state.getValue(EGGS) * hatchAmount;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(EGGS);
    }
}
