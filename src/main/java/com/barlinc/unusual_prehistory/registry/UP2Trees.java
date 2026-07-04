package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.worldgen.feature.tree.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2Trees {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, UnusualPrehistory2.MOD_ID);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, UnusualPrehistory2.MOD_ID);
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, UnusualPrehistory2.MOD_ID);

    // Trunk placers
    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<LepidodendronTrunkPlacer>> LEPIDODENDRON_TRUNK = TRUNK_PLACERS.register("lepidodendron_trunk_placer", () -> new TrunkPlacerType<>(LepidodendronTrunkPlacer.CODEC));

    // Foliage placers
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<GinkgoFoliagePlacer>> GINKGO_FOLIAGE = FOLIAGE_PLACERS.register("ginkgo_foliage_placer", () -> new FoliagePlacerType<>(GinkgoFoliagePlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<LepidodendronFoliagePlacer>> LEPIDODENDRON_FOLIAGE = FOLIAGE_PLACERS.register("lepidodendron_foliage_placer", () -> new FoliagePlacerType<>(LepidodendronFoliagePlacer.CODEC));
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<MetasequoiaFoliagePlacer>> METASEQUOIA_FOLIAGE = FOLIAGE_PLACERS.register("metasequoia_foliage_placer", () -> new FoliagePlacerType<>(MetasequoiaFoliagePlacer.CODEC));

    // Tree decorators
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<?>> HANGING_LEPIDODENDRON_LEAVES = TREE_DECORATORS.register("hanging_lepidodendron_leaves", () -> new TreeDecoratorType<>(HangingLepidodendronLeavesDecorator.CODEC));
    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<?>> TREE_BRANCH = TREE_DECORATORS.register("tree_branch", () -> new TreeDecoratorType<>(TreeBranchDecorator.CODEC));

}
