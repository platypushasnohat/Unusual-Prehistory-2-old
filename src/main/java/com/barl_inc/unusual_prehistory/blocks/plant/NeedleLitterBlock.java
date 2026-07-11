package com.barl_inc.unusual_prehistory.blocks.plant;

import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2Features;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class NeedleLitterBlock extends SnowyDirtBlock implements BonemealableBlock {

    public NeedleLitterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, @NotNull BlockState state) {
        return level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, @NotNull RandomSource random, BlockPos pos, @NotNull BlockState state) {
        BlockPos abovePos = pos.above();
        BlockState horsetail = UP2Blocks.HORSETAIL.get().defaultBlockState();
        BlockState fern = Blocks.FERN.defaultBlockState();
        Optional<Holder.Reference<PlacedFeature>> patch = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(UP2Features.NEEDLE_LITTER_PLANTS);

        outerLoop:
        for (int i = 0; i < 128; i++) {
            BlockPos blockpos = abovePos;

            for (int j = 0; j < i / 16; j++) {
                blockpos = blockpos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!level.getBlockState(blockpos.below()).is(this) || level.getBlockState(blockpos).isCollisionShapeFullBlock(level, blockpos)) {
                    continue outerLoop;
                }
            }

            BlockState state1 = level.getBlockState(blockpos);
            if ((state1.is(horsetail.getBlock()) || state1.is(fern.getBlock())) && random.nextInt(10) == 0) {
                ((BonemealableBlock) state1.getBlock()).performBonemeal(level, random, blockpos, state1);
            }

            if (state1.isAir()) {
                if (patch.isEmpty()) {
                    continue;
                }
                Holder<PlacedFeature> featureHolder = patch.get();
                featureHolder.value().place(level, level.getChunkSource().getGenerator(), random, blockpos);
            }
        }
    }
}
