package com.barl_inc.unusual_prehistory.blocks;

import com.barl_inc.unusual_prehistory.blocks.entity.FossilBedBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FossilBedBlock extends BaseEntityBlock {

    private final IntProvider xpRange;

    public FossilBedBlock(IntProvider xpRange, Properties properties) {
        super(properties);
        this.xpRange = xpRange;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected @NotNull MapCodec<FossilBedBlock> codec() {
        return null;
    }

    @Override
    public int getExpDrop(@NotNull BlockState state, LevelAccessor level, @NotNull BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity breaker, @NotNull ItemStack stack) {
        return this.xpRange.sample(level.getRandom());
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if (!level.isClientSide && !player.isCreative()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof FossilBedBlockEntity fossilBed && level instanceof ServerLevel serverLevel) {
                fossilBed.unpackLootTable(serverLevel);
            }
        }
        super.playerWillDestroy(level, pos, state, player);
        return state;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new FossilBedBlockEntity(pos, state);
    }
}
