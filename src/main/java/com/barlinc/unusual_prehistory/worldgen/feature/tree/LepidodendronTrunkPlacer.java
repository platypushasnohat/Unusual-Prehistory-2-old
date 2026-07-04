package com.barlinc.unusual_prehistory.worldgen.feature.tree;

import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.registry.UP2Trees;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

public class LepidodendronTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<LepidodendronTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(lepidodendron ->
            trunkPlacerParts(lepidodendron).apply(lepidodendron, LepidodendronTrunkPlacer::new));

    public LepidodendronTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected @NotNull TrunkPlacerType<?> type() {
        return UP2Trees.LEPIDODENDRON_TRUNK.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int height, @NotNull BlockPos startPos, @NotNull TreeConfiguration config) {

        int trunkHeight = height + random.nextInt(heightRandA, heightRandA + 3) + random.nextInt(heightRandB - 1, heightRandB + 1);

        for (int i = 0; i < trunkHeight; i++) {

            if (i > trunkHeight - (4 + random.nextInt(4))) {
                blockSetter.accept(startPos.above(i), UP2Blocks.MOSSY_LEPIDODENDRON_LOG.get().defaultBlockState());
            } else {
                placeLog(level, blockSetter, random, startPos.above(i), config);
            }

            for (Direction direction : Direction.values()) {
                if (direction != Direction.UP && direction != Direction.DOWN) {
                    blockSetter.accept(startPos.above(trunkHeight).relative(direction, 1), UP2Blocks.MOSSY_LEPIDODENDRON_LOG.get().defaultBlockState());
                    blockSetter.accept(startPos.above(trunkHeight + 1).relative(direction, 2), UP2Blocks.MOSSY_LEPIDODENDRON_LOG.get().defaultBlockState());
                    blockSetter.accept(startPos.above(trunkHeight + 2).relative(direction, 2), UP2Blocks.MOSSY_LEPIDODENDRON_LOG.get().defaultBlockState());
                    blockSetter.accept(startPos.above(trunkHeight + 3).relative(direction, 2), UP2Blocks.MOSSY_LEPIDODENDRON_LOG.get().defaultBlockState());
                    blockSetter.accept(startPos.above(trunkHeight + 4).relative(direction, 3), UP2Blocks.MOSSY_LEPIDODENDRON_LOG.get().defaultBlockState());
                }
            }
        }
        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(startPos.above(trunkHeight), 0, false));
    }
}
