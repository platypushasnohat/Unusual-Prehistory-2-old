package com.barl_inc.unusual_prehistory.worldgen.feature.tree;

import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2Trees;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

public class MetasequoiaFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<MetasequoiaFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> foliagePlacerParts(instance)
                    .and(IntProvider.codec(0, 24).fieldOf("crown_height").forGetter(placer -> placer.crownHeight))
                    .apply(instance, MetasequoiaFoliagePlacer::new)
    );

    private final IntProvider crownHeight;

    public MetasequoiaFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider crownHeight) {
        super(radius, offset);
        this.crownHeight = crownHeight;
    }

    @Override
    protected @NotNull FoliagePlacerType<?> type() {
        return UP2Trees.METASEQUOIA_FOLIAGE.get();
    }

    @Override
    protected void createFoliage(@NotNull LevelSimulatedReader level, FoliagePlacer.@NotNull FoliageSetter blockSetter, @NotNull RandomSource random, @NotNull TreeConfiguration config, int maxFreeTreeHeight, FoliagePlacer.FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos blockpos = attachment.pos();
        int i = 0;
        int start = blockpos.getY() - foliageHeight + offset;

        for (int j = start; j <= blockpos.getY() + offset; j++) {
            int k = blockpos.getY() - j;
            int l = foliageRadius + attachment.radiusOffset() + Mth.floor((float) k / (float) foliageHeight * 3.8F);
            int i1;
            if (k > 0 && l == i && (j & 1) == 0) {
                i1 = l + 1;
            }  else {
                i1 = l;
            }

            if (j == start) {
                i1 -= 1;
            }

            this.placeMetasequoiaLeaves(level, blockSetter, random, new BlockPos(blockpos.getX(), j, blockpos.getZ()), i1, attachment.doubleTrunk(), (float) k / (float) foliageHeight);
            i = l;
        }
    }

    protected void placeMetasequoiaLeaves(LevelSimulatedReader level, FoliageSetter setter, RandomSource random, BlockPos center, int range, boolean large, float heightRatio) {
        int i = large ? 1 : 0;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = -range; x <= range + i; x++) {
            for (int z = -range; z <= range + i; z++) {
                if (!this.shouldSkipLocationSigned(random, x, 0, z, range, large)) {
                    pos.setWithOffset(center, x, 0, z);
                    if (TreeFeature.validTreePos(level, pos)) {
                        BlockState state = this.getStateForHeight(heightRatio, random);
                        setter.set(pos, state);
                    }
                }
            }
        }
    }

    private BlockState getStateForHeight(float heightRatio, RandomSource random) {
        BlockState state;
        if (heightRatio < 0.35F) {
            state = UP2Blocks.DAWN_METASEQUOIA_LEAVES.get().defaultBlockState();
        } else if (heightRatio < 0.5F) {
            state = random.nextFloat() < 0.5F ? UP2Blocks.DUSK_METASEQUOIA_LEAVES.get().defaultBlockState() : UP2Blocks.DAWN_METASEQUOIA_LEAVES.get().defaultBlockState();
        } else if (heightRatio < 0.6F) {
            state = UP2Blocks.DUSK_METASEQUOIA_LEAVES.get().defaultBlockState();
        } else if (heightRatio < 0.7F) {
            state = random.nextFloat() < 0.5F ? UP2Blocks.METASEQUOIA_LEAVES.get().defaultBlockState() : UP2Blocks.DUSK_METASEQUOIA_LEAVES.get().defaultBlockState();
        } else {
            state = UP2Blocks.METASEQUOIA_LEAVES.get().defaultBlockState();
        }
        return state.setValue(LeavesBlock.DISTANCE, 7).setValue(LeavesBlock.PERSISTENT, false).setValue(LeavesBlock.WATERLOGGED, false);
    }

    @Override
    public int foliageHeight(@NotNull RandomSource random, int height, @NotNull TreeConfiguration config) {
        return this.crownHeight.sample(random);
    }

    @Override
    protected boolean shouldSkipLocation(@NotNull RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return localX + localZ >= 7 || localX * localX + localZ * localZ > range * range;
    }
}