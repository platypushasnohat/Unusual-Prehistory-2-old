package com.barl_inc.unusual_prehistory.datagen.server;

import com.barl_inc.unusual_prehistory.blocks.plant.ThreeTallPlantBlock;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.barl_inc.unusual_prehistory.registry.UP2Blocks.*;

public class UP2LootTableProvider extends LootTableProvider {

    public UP2LootTableProvider(PackOutput output, CompletableFuture<Provider> provider) {
        super(output, BuiltInLootTables.all(), ImmutableList.of(new LootTableProvider.SubProviderEntry(UP2BlockLootProvider::new, LootContextParamSets.BLOCK)), provider);
    }

    @Override
    protected void validate(@NotNull WritableRegistry<LootTable> registry, @NotNull ValidationContext context, ProblemReporter.@NotNull Collector collector) {
    }

    public static class UP2BlockLootProvider extends BlockLootSubProvider {

        public static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.TOOLS_SHEAR));

        protected LootItemCondition.@NotNull Builder doesNotHaveSilkTouch() {
            return this.hasSilkTouch().invert();
        }

        private LootItemCondition.Builder hasShearsOrSilkTouch() {
            return HAS_SHEARS.or(this.hasSilkTouch());
        }

        private LootItemCondition.Builder doesNotHaveShearsOrSilkTouch() {
            return this.hasShearsOrSilkTouch().invert();
        }

        private static final float[] LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
        private static final float[] LEAVES_STICK_CHANCES = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};
        private static final float[] GINKGO_FRUIT_CHANCES = new float[]{0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F};

        private final Set<Block> knownBlocks = new HashSet<>();

        protected UP2BlockLootProvider(Provider provider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
        }

        @Override
        protected void add(@NotNull Block block, LootTable.@NotNull Builder builder) {
            super.add(block, builder);
            this.knownBlocks.add(block);
        }

        @Override
        public void generate() {
            this.dropSelf(ASPHALT.get());

            this.add(FOSSILIZED_BONE_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, COBBLED_FOSSILIZED_BONE.get()));
            this.add(FOSSILIZED_BONE_BARK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, COBBLED_FOSSILIZED_BONE.get()));
            this.add(FOSSILIZED_BONE_VERTEBRA.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, COBBLED_FOSSILIZED_BONE.get()));
            this.add(FOSSILIZED_BONE_ROD.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, COBBLED_FOSSILIZED_BONE.get()));
            this.add(FOSSILIZED_BONE_SPIKE.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, COBBLED_FOSSILIZED_BONE.get()));
            this.add(FOSSILIZED_BONE_ROW.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, COBBLED_FOSSILIZED_BONE.get()));

            this.dropSelf(FOSSILIZED_SKULL.get());
            this.dropSelf(FOSSILIZED_SKULL_LANTERN.get());
            this.dropSelf(FOSSILIZED_SKULL_SOUL_LANTERN.get());

            this.dropSelf(COBBLED_FOSSILIZED_BONE.get());
            this.dropSelf(COBBLED_FOSSILIZED_BONE_STAIRS.get());
            this.add(COBBLED_FOSSILIZED_BONE_SLAB.get(), this::createSlabItemTable);

            this.dropSelf(PETRIFIED_BUSH.get());
            this.dropSelf(PETRIFIED_LOG.get());
            this.dropSelf(PETRIFIED_WOOD.get());
            this.dropSelf(POLISHED_PETRIFIED_WOOD.get());
            this.dropSelf(POLISHED_PETRIFIED_WOOD_STAIRS.get());
            this.add(POLISHED_PETRIFIED_WOOD_SLAB.get(), this::createSlabItemTable);
            this.dropSelf(POLISHED_PETRIFIED_WOOD_PRESSURE_PLATE.get());
            this.dropSelf(POLISHED_PETRIFIED_WOOD_BUTTON.get());

            this.dropSelf(TRANSMOGRIFIER.get());

            this.dropSelf(REINFORCED_GLASS.get());
            this.dropSelf(TINTED_REINFORCED_GLASS.get());
            this.dropSelf(WHITE_REINFORCED_GLASS.get());
            this.dropSelf(LIGHT_GRAY_REINFORCED_GLASS.get());
            this.dropSelf(GRAY_REINFORCED_GLASS.get());
            this.dropSelf(BLACK_REINFORCED_GLASS.get());
            this.dropSelf(BROWN_REINFORCED_GLASS.get());
            this.dropSelf(RED_REINFORCED_GLASS.get());
            this.dropSelf(ORANGE_REINFORCED_GLASS.get());
            this.dropSelf(YELLOW_REINFORCED_GLASS.get());
            this.dropSelf(LIME_REINFORCED_GLASS.get());
            this.dropSelf(GREEN_REINFORCED_GLASS.get());
            this.dropSelf(CYAN_REINFORCED_GLASS.get());
            this.dropSelf(LIGHT_BLUE_REINFORCED_GLASS.get());
            this.dropSelf(BLUE_REINFORCED_GLASS.get());
            this.dropSelf(PURPLE_REINFORCED_GLASS.get());
            this.dropSelf(MAGENTA_REINFORCED_GLASS.get());
            this.dropSelf(PINK_REINFORCED_GLASS.get());

            // Update 1
            this.dropSelf(CARNOTAURUS_EGG.get());
            this.dropSelf(DIPLOCAULUS_EGGS.get());
            this.dropSelf(DUNKLEOSTEUS_SAC.get());
            this.dropSelf(JAWLESS_FISH_ROE.get());
            this.dropSelf(KENTROSAURUS_EGG.get());
            this.dropSelf(KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get());
            this.dropSelf(MAJUNGASAURUS_EGG.get());
            this.dropSelf(MEGALANIA_EGG.get());
            this.dropSelf(STETHACANTHUS_SAC.get());

            // Update 2
            this.dropSelf(ONCHOPRISTIS_SAC.get());

            // Update 3
            this.dropSelf(TARTUOSTEUS_ROE.get());

            // Update 4
            this.dropSelf(BRACHIOSAURUS_EGG.get());
            this.dropSelf(COELACANTHUS_ROE.get());
            this.dropSelf(HIBBERTOPTERUS_EGGS.get());
            this.dropSelf(KAPROSUCHUS_EGG.get());
            this.dropSelf(LOBE_FINNED_FISH_ROE.get());
            this.dropSelf(LYSTROSAURUS_EGG.get());
            this.dropSelf(PACHYCEPHALOSAURUS_EGG.get());
            this.dropSelf(ULUGHBEGSAURUS_EGG.get());

            // Update 5
            this.dropSelf(AEGIROCASSIS_EGGS.get());
            this.dropSelf(DESMATOSUCHUS_EGG.get());

            this.dropSelf(BENNETTITALES.get());
            this.dropPottedContents(POTTED_BENNETTITALES.get());

            this.dropSelf(NEOMARIOPTERIS.get());
            this.dropPottedContents(POTTED_NEOMARIOPTERIS.get());

            this.add(CALAMOPHYTON.get(), (block) -> this.createLayeredSinglePropConditionTable(block, ThreeTallPlantBlock.LAYER, 0));
            this.add(BRACHYPHYLLUM.get(), (block) -> this.createLayeredSinglePropConditionTable(block, ThreeTallPlantBlock.LAYER, 0));

            this.dropSelf(COOKSONIA.get());
            this.dropPottedContents(POTTED_COOKSONIA.get());

            this.dropSelf(CLADOPHLEBIS.get());
            this.dropPottedContents(POTTED_CLADOPHLEBIS.get());

            this.dropSelf(ARCHAEOSIGILLARIA.get());
            this.dropPottedContents(POTTED_ARCHAEOSIGILLARIA.get());

            this.dropSelf(QUILLWORT.get());
            this.dropPottedContents(POTTED_QUILLWORT.get());

            this.dropSelf(LEEFRUCTUS.get());
            this.dropPottedContents(POTTED_LEEFRUCTUS.get());

            this.add(RAIGUENRAYUN.get(), (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
            this.add(TEMPSKYA.get(), (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
            this.add(AETHOPHYLLUM.get(), (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
            this.add(ZHANGSOLVA_BLOOM.get(), (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
            this.add(DELITZSCHALA_STALK.get(), (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));

            this.dropSelf(RHYNIA.get());
            this.dropPottedContents(POTTED_RHYNIA.get());

            this.dropSelf(HORSETAIL.get());
            this.dropPottedContents(POTTED_HORSETAIL.get());
            this.add(LARGE_HORSETAIL.get(), block -> this.createDoublePlantDrops(block, HORSETAIL.get()));

            this.add(MOSSY_DIRT.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
            this.dropSelf(MOSS_LAYER.get());

            this.add(GUANGDEDENDRON_SPORE.get(), this.createSingleItemTable(GUANGDEDENDRON.get(), ConstantValue.exactly(1.0F)));
            this.dropSelf(GUANGDEDENDRON.get());

            this.dropSelf(CYCAD_SEEDLING.get());
            this.dropSelf(CYCAD_STEM.get());
            this.dropSelf(CYCAD_CROWN.get());

            this.woodSetDrops(DRYOPHYLLUM);
            this.dropSelf(DRYOPHYLLUM_SAPLING.get());
            this.dropPottedContents(POTTED_DRYOPHYLLUM_SAPLING.get());

            this.add(DRYOPHYLLUM_LEAVES.get(), (block) -> this.createLeavesDrops(block, DRYOPHYLLUM_SAPLING.get(), LEAVES_SAPLING_CHANCES));

            this.woodSetDrops(GINKGO);
            this.dropSelf(GINKGO_SAPLING.get());
            this.dropPottedContents(POTTED_GINKGO_SAPLING.get());
            this.dropSelf(GOLDEN_GINKGO_SAPLING.get());
            this.dropPottedContents(POTTED_GOLDEN_GINKGO_SAPLING.get());

            this.add(GOLDEN_GINKGO_LEAVES.get(), (block) -> this.createGinkgoLeavesDrops(block, GOLDEN_GINKGO_SAPLING.get(), LEAVES_SAPLING_CHANCES));

            this.woodSetDrops(LEPIDODENDRON);
            this.dropSelf(MOSSY_LEPIDODENDRON_LOG.get());
            this.dropSelf(MOSSY_LEPIDODENDRON_WOOD.get());
            this.dropSelf(LEPIDODENDRON_CONE.get());

            this.add(LEPIDODENDRON_LEAVES.get(), this::createLepidodendronLeavesDrops);
            this.add(HANGING_LEPIDODENDRON_LEAVES.get(), this::createLepidodendronLeavesDrops);

            this.woodSetDrops(METASEQUOIA);
            this.dropSelf(METASEQUOIA_SAPLING.get());
            this.dropPottedContents(POTTED_METASEQUOIA_SAPLING.get());

            this.add(METASEQUOIA_LEAVES.get(), (block) -> this.createLeavesDrops(block, METASEQUOIA_SAPLING.get(), LEAVES_SAPLING_CHANCES));
            this.add(DAWN_METASEQUOIA_LEAVES.get(), (block) -> this.createLeavesDrops(block, METASEQUOIA_SAPLING.get(), LEAVES_SAPLING_CHANCES));
            this.add(DUSK_METASEQUOIA_LEAVES.get(), (block) -> this.createLeavesDrops(block, METASEQUOIA_SAPLING.get(), LEAVES_SAPLING_CHANCES));
            this.add(NEEDLE_LITTER.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, Blocks.DIRT));

            this.dropSelf(PROTOTAXITES_NUB.get());
            this.dropSelf(PROTOTAXITES_CLUSTER.get());
            this.dropSelf(PROTOTAXITES.get());
            this.add(LARGE_PROTOTAXITES_NUB.get(), (block) -> this.createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));

            this.dropSelf(PEAT.get());
            this.add(LIVING_PEAT.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, PEAT.get()));

            // Update 6
            this.dropSelf(AMMONITE_EGGS.get());
            this.dropSelf(CONCAVENATOR_EGG.get());
            this.dropSelf(GIANT_CAMPANILE_EGGS.get());
            this.dropSelf(HYNERPETON_EGGS.get());
            this.dropSelf(KING_LINGCOD_ROE.get());
            this.dropSelf(LEEDSICHTHYS_ROE.get());
            this.dropSelf(RHIZODUS_ROE.get());
            this.dropSelf(SPIKE_TOOTHED_SALMON_ROE.get());
            this.dropSelf(THERIZINOSAURUS_EGG.get());
            this.dropSelf(TUSOTEUTHIS_EGGS.get());
            this.dropSelf(CAMEROCERAS_EGGS.get());

            this.dropSelf(BRACHIOSAURUS_PLUSHIE.get());
            this.dropSelf(CARNOTAURUS_PLUSHIE.get());
            this.dropSelf(COTYLORHYNCHUS_PLUSHIE.get());
            this.dropSelf(GIANT_CAMPANILE_PLUSHIE.get());
            this.dropSelf(HIBBERTOPTERUS_PLUSHIE.get());
            this.dropSelf(KENTROSAURUS_PLUSHIE.get());
            this.dropSelf(MAJUNGASAURUS_PLUSHIE.get());
            this.dropSelf(TARTUOSTEUS_PLUSHIE.get());

            this.dropSelf(LEEDSICHTHYS_SLICE_BLOCK.get());
            this.dropSelf(LEEDSICHTHYS_SLICE_STAIRS.get());
            this.add(LEEDSICHTHYS_SLICE_SLAB.get(), this::createSlabItemTable);
        }

        @Override
        public @NotNull Iterable<Block> getKnownBlocks() {
            return knownBlocks;
        }

        protected void woodSetDrops(WoodSet woodSet) {
            this.dropSelf(woodSet.log().get());
            this.dropSelf(woodSet.wood().get());
            this.dropSelf(woodSet.strippedLog().get());
            this.dropSelf(woodSet.strippedWood().get());
            this.dropSelf(woodSet.planks().get());
            this.dropSelf(woodSet.stairs().get());
            this.add(woodSet.slab().get(), this::createSlabItemTable);
            this.dropSelf(woodSet.fence().get());
            this.dropSelf(woodSet.fenceGate().get());
            this.add(woodSet.door().get(), this::createDoorTable);
            this.dropSelf(woodSet.trapdoor().get());
            this.dropSelf(woodSet.pressurePlate().get());
            this.dropSelf(woodSet.button().get());
            this.dropSelf(woodSet.sign().get());
            this.dropSelf(woodSet.wallSign().get());
            this.dropSelf(woodSet.hangingSign().get());
            this.dropSelf(woodSet.hangingWallSign().get());
        }

        protected LootTable.Builder createDoublePlantDrops(Block block, Block sheared) {
            LootPoolEntryContainer.Builder<?> builder = LootItem.lootTableItem(sheared).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)));
            return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))), new BlockPos(0, -1, 0))));
        }

        @SuppressWarnings("SameParameterValue")
        protected LootTable.Builder createLayeredSinglePropConditionTable(Block block, IntegerProperty property, int value) {
            return LootTable.lootTable().withPool(this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value))))));
        }

        protected LootTable.Builder createLepidodendronLeavesDrops(Block leaves) {
            HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
            return this.createSilkTouchOrShearsDispatchTable(leaves, this.applyExplosionDecay(leaves, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), LEAVES_STICK_CHANCES)));
        }

        @SuppressWarnings("SameParameterValue")
        protected LootTable.Builder createGinkgoLeavesDrops(Block leaves, Block sapling, float... chances) {
            HolderLookup.RegistryLookup<Enchantment> enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
            return this.createLeavesDrops(leaves, sapling, chances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(this.doesNotHaveShearsOrSilkTouch()).add(this.applyExplosionCondition(leaves, LootItem.lootTableItem(UP2Items.GINKGO_FRUIT.get())).when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), GINKGO_FRUIT_CHANCES))));
        }
    }
}