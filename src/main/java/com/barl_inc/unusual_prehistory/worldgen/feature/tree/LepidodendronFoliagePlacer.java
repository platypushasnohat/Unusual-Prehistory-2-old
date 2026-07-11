package com.barl_inc.unusual_prehistory.worldgen.feature.tree;

import com.barl_inc.unusual_prehistory.registry.UP2Trees;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

public class LepidodendronFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<LepidodendronFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((lepidodendron) ->
            foliagePlacerParts(lepidodendron)
                    .and(Codec.intRange(0, 16).fieldOf("height").forGetter((lepidodendronHeight) -> lepidodendronHeight.height))
                    .apply(lepidodendron, LepidodendronFoliagePlacer::new));

    protected final int height;

    public LepidodendronFoliagePlacer(IntProvider intProvider, IntProvider intProvider1, int height) {
        super(intProvider, intProvider1);
        this.height = height;
    }

    @Override
    protected @NotNull FoliagePlacerType<?> type() {
        return UP2Trees.LEPIDODENDRON_FOLIAGE.get();
    }

    @Override
    protected void createFoliage(@NotNull LevelSimulatedReader level, @NotNull FoliageSetter placer, @NotNull RandomSource random, @NotNull TreeConfiguration config, int trunkHeight, FoliagePlacer.FoliageAttachment treeNode, int foliageHeight, int radius, int offset) {
        BlockPos blockPos = treeNode.pos().above(offset);
        BlockPos.MutableBlockPos mutable = blockPos.mutable();

        this.placeLeavesRow(level, placer, random, config, blockPos, radius + 1 + treeNode.radiusOffset(), 3, false);
        this.generateDiamond(level, placer, random, config, blockPos, radius + 2 + treeNode.radiusOffset(), 3, treeNode.doubleTrunk());

        this.placeLeavesRow(level, placer, random, config, blockPos, radius + 3 + treeNode.radiusOffset(), 4, false);

        this.placeLeavesRow(level, placer, random, config, blockPos, radius + 2 + treeNode.radiusOffset(), 5, false);
        mutable.setWithOffset(blockPos, 0, 5, 0);
        tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, -5));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, -5));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, -5));

        tryPlaceLeaf(level, placer, random, config, mutable.offset(-1, 0, 5));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(0, 0, 5));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(1, 0, 5));

        tryPlaceLeaf(level, placer, random, config, mutable.offset(5, 0, -1));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(5, 0, 0));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(5, 0, 1));

        tryPlaceLeaf(level, placer, random, config, mutable.offset(-5, 0, -1));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(-5, 0, 0));
        tryPlaceLeaf(level, placer, random, config, mutable.offset(-5, 0, 1));

        this.placeLeavesRow(level, placer, random, config, blockPos, radius + 1 + treeNode.radiusOffset(), 6, false);
        this.generateDiamond(level, placer, random, config, blockPos, radius + 2 + treeNode.radiusOffset(), 6, treeNode.doubleTrunk());
    }

    @Override
    public int foliageHeight(@NotNull RandomSource random, int trunkHeight, @NotNull TreeConfiguration config) {
        return 3;
    }

    @Override
    protected boolean shouldSkipLocation(@NotNull RandomSource random, int x, int y, int z, int radius, boolean giantTrunk) {
        return x == radius && z == radius && radius > 0;
    }

    protected void generateDiamond(LevelSimulatedReader world, FoliageSetter placer, RandomSource random, TreeConfiguration config, BlockPos centerPos, int radius, int y, boolean giantTrunk) {
        int i = giantTrunk ? 1 : 0;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int j = -radius; j <= radius + i; ++j) {
            for (int k = -radius; k <= radius + i; ++k) {
                if (!this.validateDiamond(j, k, giantTrunk)) {
                    mutable.setWithOffset(centerPos, j, y, k);
                    tryPlaceLeaf(world, placer, random, config, mutable);
                }
            }
        }
    }

    protected boolean validateDiamond(int dx, int dz, boolean giantTrunk) {
        if (giantTrunk) {
            dx = Math.min(Math.abs(dx), Math.abs(dx - 1));
            dz = Math.min(Math.abs(dz), Math.abs(dz - 1));
        } else {
            dx = Math.abs(dx);
            dz = Math.abs(dz);
        }
        return (dx > 1 || dz > 1) && dx != 0 && dz != 0;
    }
}