package com.barl_inc.unusual_prehistory.worldgen.feature;

import com.barl_inc.unusual_prehistory.worldgen.feature.config.FossilPileConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;

public class FossilPileFeature extends Feature<FossilPileConfig> {

    public FossilPileFeature(Codec<FossilPileConfig> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<FossilPileConfig> context) {
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        FossilPileConfig config = context.config();

        while (origin.getY() > level.getMinBuildHeight() + 3 && level.isEmptyBlock(origin.below())) {
            origin = origin.below();
        }

        if (origin.getY() <= level.getMinBuildHeight() + 3) {
            return false;
        }

        int count = 1 + random.nextInt(3);
        List<BlockPos> nodes = new ArrayList<>();
        List<Float> radii = new ArrayList<>();
        int spread = 4;

        for (int i = 0; i < count; i++) {
            nodes.add(origin.offset(random.nextInt(-spread, spread + 1), 0, random.nextInt(-spread, spread + 1)));
            radii.add(0.25F + random.nextFloat());
        }

        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-spread, 0, -spread), origin.offset(spread, 2, spread))) {
            for (int i = 0; i < nodes.size(); i++) {
                BlockPos node = nodes.get(i);
                float radius = radii.get(i);
                if (pos.distSqr(node) <= radius * radius) {
                    BlockPos.MutableBlockPos placePos = pos.mutable();
                    while (placePos.getY() > level.getMinBuildHeight() + 1) {
                        BlockState below = level.getBlockState(placePos.below());
                        boolean canSink = level.isEmptyBlock(placePos.below()) || below.is(BlockTags.REPLACEABLE) || level.getFluidState(placePos.below()).is(Fluids.WATER);
                        if (!canSink) {
                            break;
                        }
                        placePos.move(0, -1, 0);
                    }

                    level.setBlock(placePos, config.state().getState(random, placePos), 3);
                    int floorRadius = 2;
                    for (BlockPos groundPos : BlockPos.betweenClosed(node.offset(-floorRadius, -1, -floorRadius), node.offset(floorRadius, -1, floorRadius))) {
                        if (!isDirt(level.getBlockState(groundPos))) {
                            continue;
                        }
                        double dx = groundPos.getX() - node.getX();
                        double dz = groundPos.getZ() - node.getZ();
                        if (dx * dx + dz * dz <= floorRadius * floorRadius) {
                            if (random.nextFloat() < 0.6F) {
                                level.setBlock(groundPos, config.floorState().getState(random, groundPos), 3);
                            }
                        }
                    }
                    if (level.isEmptyBlock(pos.below()) || level.getBlockState(pos.below()).is(BlockTags.REPLACEABLE) || level.getFluidState(pos.below()).is(Fluids.WATER)) {
                        BlockPos.MutableBlockPos below = pos.mutable().move(0, -1, 0);
                        for (int j = 0; j < 4; j++) {
                            if (!level.isEmptyBlock(below) && !level.getFluidState(below).is(Fluids.WATER) && !level.getBlockState(pos.below()).is(BlockTags.REPLACEABLE)) {
                                break;
                            }
                            level.setBlock(below, config.state().getState(random, below), 3);
                            below.move(0, -1, 0);
                        }
                    }
                    break;
                }
            }
        }

        config.feature().value().place(level, context.chunkGenerator(), random, origin.above());
        return true;
    }
}