package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.blocks.*;
import com.barlinc.unusual_prehistory.blocks.egg.EggBlock;
import com.barlinc.unusual_prehistory.blocks.egg.MultipleEggBlock;
import com.barlinc.unusual_prehistory.blocks.egg.RaftEggBlock;
import com.barlinc.unusual_prehistory.blocks.egg.UnderwaterEggBlock;
import com.barlinc.unusual_prehistory.blocks.plant.*;
import com.barlinc.unusual_prehistory.items.FossilBedBlockItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

@SuppressWarnings("deprecation")
public class UP2Blocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(UnusualPrehistory2.MOD_ID);

    public static final List<Supplier<? extends Block>> EGG_BLOCKS = new ArrayList<>();
    public static final List<Supplier<? extends Block>> PLUSHIE_BLOCKS = new ArrayList<>();
    public static List<DeferredBlock<? extends Block>> BLOCK_TRANSLATIONS = new ArrayList<>();
    public static List<WoodSet> WOOD_SETS = new ArrayList<>();

    // Update 1
    public static final DeferredBlock<Block> CARNOTAURUS_EGG = registerEggBlock("carnotaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.CARNOTAURUS::get, 12, 15));
    public static final DeferredBlock<Block> DIPLOCAULUS_EGGS = registerWaterEggBlock("diplocaulus_eggs", () -> new RaftEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.DIPLOCAULUS::get, 2));
    public static final DeferredBlock<Block> DUNKLEOSTEUS_SAC = registerEggBlock("dunkleosteus_sac", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.DUNKLEOSTEUS::get, 1));
    public static final DeferredBlock<Block> JAWLESS_FISH_ROE = registerEggBlock("jawless_fish_roe", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.JAWLESS_FISH::get, 4));
    public static final DeferredBlock<Block> KENTROSAURUS_EGG = registerEggBlock("kentrosaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.KENTROSAURUS::get, 8, 15));
    public static final DeferredBlock<Block> KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS = registerWaterEggBlock("kimmeridgebrachypteraeschnidium_eggs", () -> new RaftEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM::get, 4));
    public static final DeferredBlock<Block> MAJUNGASAURUS_EGG = registerEggBlock("majungasaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.MAJUNGASAURUS::get, 12, 11));
    public static final DeferredBlock<Block> MEGALANIA_EGG = registerEggBlock("megalania_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.MEGALANIA::get, 12, 14));
    public static final DeferredBlock<Block> STETHACANTHUS_SAC = registerEggBlock("stethacanthus_sac", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.STETHACANTHUS::get, 2));

    public static final DeferredBlock<Block> BENNETTITALES = registerBlock("bennettitales", () -> new PrehistoricPlantBlock(UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_BENNETTITALES = registerBlockWithoutItem("potted_bennettitales", () -> new FlowerPotBlock(UP2Blocks.BENNETTITALES.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> CALAMOPHYTON = registerBlock("calamophyton", () -> new CalamophytonBlock(BlockBehaviour.Properties.of().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> COOKSONIA = registerBlock("cooksonia", () -> new PrehistoricFlowerBlock(MobEffects.REGENERATION, 9, UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_COOKSONIA = registerBlockWithoutItem("potted_cooksonia", () -> new FlowerPotBlock(UP2Blocks.COOKSONIA.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> CLADOPHLEBIS = registerBlock("cladophlebis", () -> new PrehistoricPlantBlock(UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_CLADOPHLEBIS = registerBlockWithoutItem("potted_cladophlebis", () -> new FlowerPotBlock(UP2Blocks.CLADOPHLEBIS.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> HORSETAIL = registerBlock("horsetail", () -> new HorsetailBlock(UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_HORSETAIL = registerBlockWithoutItem("potted_horsetail", () -> new FlowerPotBlock(UP2Blocks.HORSETAIL.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> LARGE_HORSETAIL = registerBlock("large_horsetail", () -> new LargeHorsetailBlock(UP2BlockProperties.TALL_PLANT));
    public static final DeferredBlock<Block> QUILLWORT = registerBlock("quillwort", () -> new QuillwortBlock(UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_QUILLWORT = registerBlockWithoutItem("potted_quillwort", () -> new FlowerPotBlock(UP2Blocks.QUILLWORT.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> LEEFRUCTUS = registerBlock("leefructus", () -> new PrehistoricFlowerBlock(MobEffects.ABSORPTION, 9, UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_LEEFRUCTUS = registerBlockWithoutItem("potted_leefructus", () -> new FlowerPotBlock(UP2Blocks.LEEFRUCTUS.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> RAIGUENRAYUN = registerBlock("raiguenrayun", () -> new PrehistoricTallFlowerBlock(UP2BlockProperties.TALL_PLANT));
    public static final DeferredBlock<Block> RHYNIA = registerBlock("rhynia", () -> new PrehistoricPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> POTTED_RHYNIA = registerBlockWithoutItem("potted_rhynia", () -> new FlowerPotBlock(UP2Blocks.RHYNIA.get(), registerFlowerPot()));

    public static final DeferredBlock<Block> MOSSY_DIRT = registerBlock("mossy_dirt", () -> new MossyDirtBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.5F).sound(UP2SoundTypes.MOSSY_DIRT)));
    public static final DeferredBlock<Block> MOSS_LAYER = registerBlock("moss_layer", () -> new MossLayerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).replaceable().noCollission().strength(0.2F).sound(SoundType.GLOW_LICHEN).ignitedByLava().pushReaction(PushReaction.DESTROY)));

    public static final WoodSet GINKGO = new WoodSet("ginkgo");
    public static final DeferredBlock<Block> GINKGO_LEAVES = registerBlock("ginkgo_leaves", () -> new FallingLeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES), UP2Particles.GINKGO_LEAVES));
    public static final DeferredBlock<Block> GOLDEN_GINKGO_LEAVES = registerBlock("golden_ginkgo_leaves", () -> new FallingLeavesBlock(UP2BlockProperties.leaves(MapColor.GOLD, SoundType.AZALEA_LEAVES), UP2Particles.GOLDEN_GINKGO_LEAVES));
    public static final DeferredBlock<Block> GINKGO_SAPLING = registerBlock("ginkgo_sapling", () -> new SaplingBlock(UP2TreeGrowers.GINKGO, UP2BlockProperties.sapling(MapColor.PLANT, SoundType.CHERRY_SAPLING)));
    public static final DeferredBlock<Block> POTTED_GINKGO_SAPLING = registerBlockWithoutItem("potted_ginkgo_sapling", () -> new FlowerPotBlock(GINKGO_SAPLING.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> GOLDEN_GINKGO_SAPLING = registerBlock("golden_ginkgo_sapling", () -> new SaplingBlock(UP2TreeGrowers.GOLDEN_GINKGO, UP2BlockProperties.sapling(MapColor.GOLD, SoundType.CHERRY_SAPLING)));
    public static final DeferredBlock<Block> POTTED_GOLDEN_GINKGO_SAPLING = registerBlockWithoutItem("potted_golden_ginkgo_sapling", () -> new FlowerPotBlock(GOLDEN_GINKGO_SAPLING.get(), registerFlowerPot()));

    public static final WoodSet LEPIDODENDRON = new WoodSet("lepidodendron");
    public static final DeferredBlock<Block> MOSSY_LEPIDODENDRON_LOG = registerBlock("mossy_lepidodendron_log", () -> new WoodBlock(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> MOSSY_LEPIDODENDRON_WOOD = registerBlock("mossy_lepidodendron_wood", () -> new WoodBlock(UP2BlockProperties.log(MapColor.TERRACOTTA_BLACK, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS)));
    public static final DeferredBlock<Block> LEPIDODENDRON_LEAVES = registerBlock("lepidodendron_leaves", () -> new LepidodendronLeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES)));
    public static final DeferredBlock<Block> HANGING_LEPIDODENDRON_LEAVES = registerBlock("hanging_lepidodendron_leaves", () -> new HangingLeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES).noCollission()));
    public static final DeferredBlock<Block> LEPIDODENDRON_CONE = registerBlock("lepidodendron_cone", () -> new LepidodendronConeBlock(UP2TreeGrowers.LEPIDODENDRON, UP2BlockProperties.sapling(MapColor.PLANT, SoundType.CHERRY_SAPLING)));

    public static final DeferredBlock<Block> TRANSMOGRIFIER = registerBlock("transmogrifier", () -> new TransmogrifierBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().noOcclusion().strength(5.0F, 6.0F).sound(SoundType.METAL).lightLevel(litBlockEmission(7))));

    public static final DeferredBlock<Block> FOSSILIZED_BONE_BLOCK = registerBlock("fossilized_bone_block", () -> new RotatedPillarBlock(UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_BARK = registerBlock("fossilized_bone_bark", () -> new RotatedPillarBlock(UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_VERTEBRA = registerBlock("fossilized_bone_vertebra", () -> new RotatedPillarBlock(UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> FOSSILIZED_SKULL = registerBlock("fossilized_skull", () -> new FossilizedSkullBlock(UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> FOSSILIZED_SKULL_LANTERN = registerBlock("fossilized_skull_lantern", () -> new FossilizedSkullBlock(UP2BlockProperties.fossilLantern(15)));
    public static final DeferredBlock<Block> FOSSILIZED_SKULL_SOUL_LANTERN = registerBlock("fossilized_skull_soul_lantern", () -> new FossilizedSkullBlock(UP2BlockProperties.fossilLantern(10)));
    public static final DeferredBlock<Block> COBBLED_FOSSILIZED_BONE = registerBlock("cobbled_fossilized_bone", () -> new Block(UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> COBBLED_FOSSILIZED_BONE_STAIRS = registerBlock("cobbled_fossilized_bone_stairs", () -> new StairBlock(COBBLED_FOSSILIZED_BONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(COBBLED_FOSSILIZED_BONE.get())));
    public static final DeferredBlock<Block> COBBLED_FOSSILIZED_BONE_SLAB = registerBlock("cobbled_fossilized_bone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(COBBLED_FOSSILIZED_BONE.get())));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_ROD = registerBlock("fossilized_bone_rod", () -> new FossilizedBoneRodBlock(UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_SPIKE = registerBlock("fossilized_bone_spike", () -> new FossilizedBoneSpikeBlock(UP2BlockProperties.FOSSIL_BLOCK.noOcclusion()));
    public static final DeferredBlock<Block> FOSSILIZED_BONE_ROW = registerBlock("fossilized_bone_row", () -> new FossilizedBoneRowBlock(UP2BlockProperties.FOSSIL_BLOCK.noOcclusion()));

    public static final DeferredBlock<Block> PETRIFIED_BUSH = registerBlock("petrified_bush", () -> new PetrifiedBushBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).noOcclusion().noCollission().instrument(NoteBlockInstrument.HARP).strength(1.0F).instabreak().sound(SoundType.DRIPSTONE_BLOCK)));
    public static final DeferredBlock<Block> PETRIFIED_LOG = registerBlock("petrified_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.HARP).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.DRIPSTONE_BLOCK)));
    public static final DeferredBlock<Block> PETRIFIED_WOOD = registerBlock("petrified_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.HARP).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.DRIPSTONE_BLOCK)));
    public static final DeferredBlock<Block> POLISHED_PETRIFIED_WOOD = registerBlock("polished_petrified_wood", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.HARP).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.DRIPSTONE_BLOCK)));
    public static final DeferredBlock<Block> POLISHED_PETRIFIED_WOOD_STAIRS = registerBlock("polished_petrified_wood_stairs", () -> new StairBlock(POLISHED_PETRIFIED_WOOD.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(POLISHED_PETRIFIED_WOOD.get())));
    public static final DeferredBlock<Block> POLISHED_PETRIFIED_WOOD_SLAB = registerBlock("polished_petrified_wood_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_PETRIFIED_WOOD.get())));
    public static final DeferredBlock<Block> POLISHED_PETRIFIED_WOOD_PRESSURE_PLATE = registerBlock("polished_petrified_wood_pressure_plate", () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.HARP).requiresCorrectToolForDrops().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> POLISHED_PETRIFIED_WOOD_BUTTON = registerBlock("polished_petrified_wood_button", () -> new ButtonBlock(BlockSetType.STONE, 20, BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> TAR = registerBlockWithoutItem("tar", () -> new TarBlock(UP2Fluids.TAR_FLUID_SOURCE.get(), UP2BlockProperties.TAR));
    public static final DeferredBlock<Block> ASPHALT = registerBlock("asphalt", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 5.0F).sound(SoundType.STONE).speedFactor(1.15F)));

    // Update 2
    public static final DeferredBlock<Block> ONCHOPRISTIS_SAC = registerEggBlock("onchopristis_sac", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.ONCHOPRISTIS::get, 1));

    // Update 3
    public static final DeferredBlock<Block> TARTUOSTEUS_ROE = registerEggBlock("tartuosteus_roe", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.TARTUOSTEUS::get, 1));

    // Update 4
    public static final DeferredBlock<Block> BRACHIOSAURUS_EGG = registerEggBlock("brachiosaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.BRACHIOSAURUS::get, 1, 16, 24));
    public static final DeferredBlock<Block> COELACANTHUS_ROE = registerEggBlock("coelacanthus_roe", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.COELACANTHUS::get, 1));
    public static final DeferredBlock<Block> HIBBERTOPTERUS_EGGS = registerWaterEggBlock("hibbertopterus_eggs", () -> new RaftEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.HIBBERTOPTERUS::get, 1));
    public static final DeferredBlock<Block> KAPROSUCHUS_EGG = registerEggBlock("kaprosuchus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.KAPROSUCHUS::get, 1, 8, 12));
    public static final DeferredBlock<Block> LOBE_FINNED_FISH_ROE = registerEggBlock("lobe_finned_fish_roe", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.LOBE_FINNED_FISH::get, 3));
    public static final DeferredBlock<Block> LYSTROSAURUS_EGG = registerEggBlock("lystrosaurus_egg", () -> new MultipleEggBlock(UP2BlockProperties.EGG, UP2Entities.LYSTROSAURUS::get, 1, 3, 6, 6, 12, 12));
    public static final DeferredBlock<Block> PACHYCEPHALOSAURUS_EGG = registerEggBlock("pachycephalosaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.PACHYCEPHALOSAURUS::get, 1, 10, 10));
    public static final DeferredBlock<Block> ULUGHBEGSAURUS_EGG = registerEggBlock("ulughbegsaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.ULUGHBEGSAURUS::get, 1, 10, 14));

    public static final DeferredBlock<Block> AETHOPHYLLUM = registerBlock("aethophyllum", () -> new PrehistoricTallFlowerBlock(UP2BlockProperties.TALL_PLANT));
    public static final DeferredBlock<Block> ARCHAEOSIGILLARIA = registerBlock("archaeosigillaria", () -> new PrehistoricPlantBlock(UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_ARCHAEOSIGILLARIA = registerBlockWithoutItem("potted_archaeosigillaria", () -> new FlowerPotBlock(UP2Blocks.ARCHAEOSIGILLARIA.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> BRACHYPHYLLUM = registerBlock("brachyphyllum", () -> new ThreeTallPlantBlock(UP2BlockProperties.TALL_PLANT));
    public static final DeferredBlock<Block> CYCAD_SEEDLING = registerBlock("cycad_seedling", () -> new CycadSeedlingBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).instabreak().pushReaction(PushReaction.DESTROY).noOcclusion().ignitedByLava()));
    public static final DeferredBlock<Block> CYCAD_STEM = registerBlock("cycad_stem", () -> new CycadStemBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).strength(1.5F).sound(SoundType.WOOD).ignitedByLava()));
    public static final DeferredBlock<Block> CYCAD_CROWN = registerBlock("cycad_crown", () -> new CycadCrownBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).instrument(NoteBlockInstrument.BASS).strength(1.5F).sound(SoundType.WOOD).noOcclusion().ignitedByLava()));
    public static final DeferredBlock<Block> GUANGDEDENDRON_SPORE = registerBlockWithoutItem("guangdedendron_spore", () -> new GuangdedendronSporeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().instabreak().noCollission().strength(1.0F).sound(SoundType.BAMBOO_SAPLING).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> GUANGDEDENDRON = registerBlock("guangdedendron", () -> new GuangdedendronStalkBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().instabreak().strength(1.0F).sound(SoundType.BAMBOO).noOcclusion().offsetType(BlockBehaviour.OffsetType.XZ).dynamicShape().ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(UP2BlockProperties::never)));
    public static final DeferredBlock<Block> NEOMARIOPTERIS = registerBlock("neomariopteris", () -> new PrehistoricPlantBlock(UP2BlockProperties.PLANT));
    public static final DeferredBlock<Block> POTTED_NEOMARIOPTERIS = registerBlockWithoutItem("potted_neomariopteris", () -> new FlowerPotBlock(UP2Blocks.NEOMARIOPTERIS.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> PROTOTAXITES = registerBlock("prototaxites", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASS).strength(0.2F).sound(SoundType.CORAL_BLOCK).ignitedByLava()));
    public static final DeferredBlock<Block> PROTOTAXITES_NUB = registerBlock("prototaxites_nub", () -> new PrototaxitesNubBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASS).strength(0.2F).noCollission().sound(SoundType.CORAL_BLOCK).ignitedByLava()));
    public static final DeferredBlock<Block> LARGE_PROTOTAXITES_NUB = registerBlock("large_prototaxites_nub", () -> new LargePrototaxitesNubBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASS).strength(0.2F).noCollission().sound(SoundType.CORAL_BLOCK).ignitedByLava()));
    public static final DeferredBlock<Block> PROTOTAXITES_CLUSTER = registerBlock("prototaxites_cluster", () -> new PrototaxitesClusterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).instrument(NoteBlockInstrument.BASS).strength(0.2F).noCollission().sound(SoundType.CORAL_BLOCK).ignitedByLava()));
    public static final DeferredBlock<Block> TEMPSKYA = registerBlock("tempskya", () -> new PrehistoricTallPlantBlock(UP2BlockProperties.TALL_PLANT));

    public static final WoodSet DRYOPHYLLUM = new WoodSet("dryophyllum");
    public static final DeferredBlock<Block> DRYOPHYLLUM_LEAVES = registerBlock("dryophyllum_leaves", () -> new LeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES)));
    public static final DeferredBlock<Block> DRYOPHYLLUM_SAPLING = registerBlock("dryophyllum_sapling", () -> new SaplingBlock(UP2TreeGrowers.DRYOPHYLLUM, UP2BlockProperties.sapling(MapColor.PLANT, SoundType.CHERRY_SAPLING)));
    public static final DeferredBlock<Block> POTTED_DRYOPHYLLUM_SAPLING = registerBlockWithoutItem("potted_dryophyllum_sapling", () -> new FlowerPotBlock(DRYOPHYLLUM_SAPLING.get(), registerFlowerPot()));

    public static final WoodSet METASEQUOIA = new WoodSet("metasequoia");
    public static final DeferredBlock<Block> METASEQUOIA_LEAVES = registerBlock("metasequoia_leaves", () -> new LeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES)));
    public static final DeferredBlock<Block> DAWN_METASEQUOIA_LEAVES = registerBlock("dawn_metasequoia_leaves", () -> new LeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES)));
    public static final DeferredBlock<Block> DUSK_METASEQUOIA_LEAVES = registerBlock("dusk_metasequoia_leaves", () -> new LeavesBlock(UP2BlockProperties.leaves(MapColor.PLANT, SoundType.AZALEA_LEAVES)));

    public static final DeferredBlock<Block> METASEQUOIA_SAPLING = registerBlock("metasequoia_sapling", () -> new MetasequoiaSaplingBlock(UP2TreeGrowers.METASEQUOIA, UP2BlockProperties.sapling(MapColor.PLANT, SoundType.CHERRY_SAPLING)));
    public static final DeferredBlock<Block> POTTED_METASEQUOIA_SAPLING = registerBlockWithoutItem("potted_metasequoia_sapling", () -> new FlowerPotBlock(METASEQUOIA_SAPLING.get(), registerFlowerPot()));

    public static final DeferredBlock<Block> REINFORCED_GLASS = registerBlock("reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.NONE)));
    public static final DeferredBlock<Block> TINTED_REINFORCED_GLASS = registerBlock("tinted_reinforced_glass", ()-> new TintedConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_GRAY)));
    public static final DeferredBlock<Block> WHITE_REINFORCED_GLASS = registerBlock("white_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.SNOW)));
    public static final DeferredBlock<Block> LIGHT_GRAY_REINFORCED_GLASS = registerBlock("light_gray_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_LIGHT_GRAY)));
    public static final DeferredBlock<Block> GRAY_REINFORCED_GLASS = registerBlock("gray_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_GRAY)));
    public static final DeferredBlock<Block> BLACK_REINFORCED_GLASS = registerBlock("black_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_BLACK)));
    public static final DeferredBlock<Block> BROWN_REINFORCED_GLASS = registerBlock("brown_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<Block> RED_REINFORCED_GLASS = registerBlock("red_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_RED)));
    public static final DeferredBlock<Block> ORANGE_REINFORCED_GLASS = registerBlock("orange_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_ORANGE)));
    public static final DeferredBlock<Block> YELLOW_REINFORCED_GLASS = registerBlock("yellow_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<Block> LIME_REINFORCED_GLASS = registerBlock("lime_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_LIGHT_GREEN)));
    public static final DeferredBlock<Block> GREEN_REINFORCED_GLASS = registerBlock("green_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_GREEN)));
    public static final DeferredBlock<Block> CYAN_REINFORCED_GLASS = registerBlock("cyan_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_CYAN)));
    public static final DeferredBlock<Block> LIGHT_BLUE_REINFORCED_GLASS = registerBlock("light_blue_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_LIGHT_BLUE)));
    public static final DeferredBlock<Block> BLUE_REINFORCED_GLASS = registerBlock("blue_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_BLUE)));
    public static final DeferredBlock<Block> PURPLE_REINFORCED_GLASS = registerBlock("purple_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_PURPLE)));
    public static final DeferredBlock<Block> MAGENTA_REINFORCED_GLASS = registerBlock("magenta_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_MAGENTA)));
    public static final DeferredBlock<Block> PINK_REINFORCED_GLASS = registerBlock("pink_reinforced_glass", ()-> new ConnectedGlassBlock(UP2BlockProperties.reinforcedGlass(MapColor.COLOR_PINK)));

    // Update 5
    public static final DeferredBlock<Block> AEGIROCASSIS_EGGS = registerEggBlock("aegirocassis_eggs", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.AEGIROCASSIS::get, 1));
    public static final DeferredBlock<Block> DESMATOSUCHUS_EGG = registerEggBlock("desmatosuchus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.DESMATOSUCHUS::get, 1, 8, 13));

    public static final DeferredBlock<Block> ZHANGSOLVA_BLOOM = registerBlock("zhangsolva_bloom", () -> new TallAmbientPlantBlock(UP2BlockProperties.TALL_PLANT, UP2Entities.ZHANGSOLVA::get, 2));
    public static final DeferredBlock<Block> DELITZSCHALA_STALK = registerBlock("delitzschala_stalk", () -> new TallAmbientPlantBlock(UP2BlockProperties.TALL_PLANT, UP2Entities.DELITZSCHALA::get, 3));

    // Update 6
    public static final DeferredBlock<Block> AMMONITE_EGGS = registerEggBlock("ammonite_eggs", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.AMMONITE::get, 4));
    public static final DeferredBlock<Block> CAMEROCERAS_EGGS = registerEggBlock("cameroceras_eggs", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.CAMEROCERAS::get, 1));
    public static final DeferredBlock<Block> CONCAVENATOR_EGG = registerEggBlock("concavenator_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.CONCAVENATOR::get, 1, 11, 15));
    public static final DeferredBlock<Block> GIANT_CAMPANILE_EGGS = registerEggBlock("giant_campanile_eggs", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.GIANT_CAMPANILE::get, 2));
    public static final DeferredBlock<Block> HYNERPETON_EGGS = registerWaterEggBlock("hynerpeton_eggs", () -> new RaftEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.HYNERPETON::get, 1));
    public static final DeferredBlock<Block> KING_LINGCOD_ROE = registerEggBlock("king_lingcod_roe", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.KING_LINGCOD::get, 1));
    public static final DeferredBlock<Block> LEEDSICHTHYS_ROE = registerEggBlock("leedsichthys_roe", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.LEEDSICHTHYS::get, 1));
    public static final DeferredBlock<Block> RHIZODUS_ROE = registerEggBlock("rhizodus_roe", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.RHIZODUS::get, 1));
    public static final DeferredBlock<Block> SPIKE_TOOTHED_SALMON_ROE = registerEggBlockNoLang("spike_toothed_salmon_roe", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.SPIKE_TOOTHED_SALMON::get, 2));
    public static final DeferredBlock<Block> THERIZINOSAURUS_EGG = registerEggBlock("therizinosaurus_egg", () -> new EggBlock(UP2BlockProperties.EGG, UP2Entities.THERIZINOSAURUS::get, 1, 16, 16));
    public static final DeferredBlock<Block> TUSOTEUTHIS_EGGS = registerEggBlock("tusoteuthis_eggs", () -> new UnderwaterEggBlock(UP2BlockProperties.WATER_EGG, UP2Entities.TUSOTEUTHIS::get, 1, 16, 10));

    public static final DeferredBlock<Block> NEEDLE_LITTER = registerBlock("needle_litter", ()-> new NeedleLitterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> PEAT = registerBlock("peat", ()-> new PeatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.MUD)));
    public static final DeferredBlock<Block> LIVING_PEAT = registerBlock("living_peat", ()-> new LivingPeatBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(UP2SoundTypes.LIVING_PEAT)));

    public static final DeferredBlock<Block> ORGANIC_OOZE_BLOCK = registerBlock("organic_ooze_block", ()-> new OrganicOozeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).friction(0.8F).sound(SoundType.SLIME_BLOCK).noOcclusion().instabreak()));

    public static final DeferredBlock<Block> DIRT_MATRIX = registerBlock("dirt_matrix", () -> new MatrixBlock(Blocks.DIRT, SoundEvents.BRUSH_GENERIC, SoundEvents.GRAVEL_BREAK, BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> MUD_MATRIX = registerBlock("mud_matrix", () -> new MudMatrixBlock(Blocks.MUD, SoundEvents.BRUSH_GENERIC, SoundEvents.MUD_BREAK, BlockBehaviour.Properties.ofLegacyCopy(Blocks.DIRT).mapColor(MapColor.TERRACOTTA_CYAN).sound(SoundType.MUD)));
    public static final DeferredBlock<Block> GRAVEL_MATRIX = registerBlock("gravel_matrix", () -> new MatrixBlock(Blocks.GRAVEL, SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.SNARE).strength(0.6F).sound(SoundType.GRAVEL)));
    public static final DeferredBlock<Block> SAND_MATRIX = registerBlock("sand_matrix", () -> new MatrixBlock(Blocks.SAND, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED, BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
    public static final DeferredBlock<Block> RED_SAND_MATRIX = registerBlock("red_sand_matrix", () -> new MatrixBlock(Blocks.RED_SAND, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
    public static final DeferredBlock<Block> SNOW_MATRIX = registerBlock("snow_matrix", () -> new MatrixBlock(Blocks.SNOW_BLOCK, SoundEvents.BRUSH_GENERIC, SoundEvents.SNOW_BREAK, BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).requiresCorrectToolForDrops().strength(0.2F).sound(SoundType.SNOW)));

    public static final DeferredBlock<Block> COMMON_FOSSIL_BED = registerFossilBedBlock("common_fossil_bed", FossilBedBlockItem.FossilBedRarity.COMMON, () -> new FossilBedBlock(UniformInt.of(0, 1), UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> UNCOMMON_FOSSIL_BED = registerFossilBedBlock("uncommon_fossil_bed", FossilBedBlockItem.FossilBedRarity.UNCOMMON, () -> new FossilBedBlock(UniformInt.of(1, 3), UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> RARE_FOSSIL_BED = registerFossilBedBlock("rare_fossil_bed", FossilBedBlockItem.FossilBedRarity.RARE, () -> new FossilBedBlock(UniformInt.of(2, 5), UP2BlockProperties.FOSSIL_BLOCK));
    public static final DeferredBlock<Block> UNUSUAL_FOSSIL_BED = registerFossilBedBlock("unusual_fossil_bed", FossilBedBlockItem.FossilBedRarity.UNUSUAL, () -> new FossilBedBlock(UniformInt.of(3, 7), UP2BlockProperties.FOSSIL_BLOCK));

    public static final DeferredBlock<Block> BRACHIOSAURUS_PLUSHIE = registerPlushieBlock("brachiosaurus_plushie", () -> new PlushieBlock(UP2BlockProperties.PLUSHIE, 12, 16, UP2SoundEvents.BRACHIOSAURUS_IDLE.get()));
    public static final DeferredBlock<Block> CARNOTAURUS_PLUSHIE = registerPlushieBlock("carnotaurus_plushie", () -> new PlushieBlock(UP2BlockProperties.PLUSHIE, 10, 16, UP2SoundEvents.CARNOTAURUS_IDLE.get()));
    public static final DeferredBlock<Block> COTYLORHYNCHUS_PLUSHIE = registerPlushieBlock("cotylorhynchus_plushie", () -> new PlushieBlock(UP2BlockProperties.PLUSHIE, 14, 8, UP2SoundEvents.COTYLORHYNCHUS_IDLE.get()));
    public static final DeferredBlock<Block> GIANT_CAMPANILE_PLUSHIE = registerPlushieBlock("giant_campanile_plushie", () -> new PlushieBlock(UP2BlockProperties.PLUSHIE, 10, 16, UP2SoundEvents.GIANT_CAMPANILE_STEP.get()));
    public static final DeferredBlock<Block> HIBBERTOPTERUS_PLUSHIE = registerPlushieBlock("hibbertopterus_plushie", () -> new PlushieBlock(UP2BlockProperties.PLUSHIE, 14, 8, UP2SoundEvents.HIBBERTOPTERUS_IDLE.get()));
    public static final DeferredBlock<Block> KENTROSAURUS_PLUSHIE = registerPlushieBlock("kentrosaurus_plushie", () -> new PlushieBlock(UP2BlockProperties.PLUSHIE, 14, 8, UP2SoundEvents.KENTROSAURUS_IDLE.get()));
    public static final DeferredBlock<Block> MAJUNGASAURUS_PLUSHIE = registerPlushieBlock("majungasaurus_plushie", () -> new PlushieBlock(UP2BlockProperties.PLUSHIE, 10, 16, UP2SoundEvents.MAJUNGASAURUS_IDLE.get()));
    public static final DeferredBlock<Block> TARTUOSTEUS_PLUSHIE = registerPlushieBlock("tartuosteus_plushie", () -> new PlushieBlock(UP2BlockProperties.PLUSHIE, 14, 4, UP2SoundEvents.JAWLESS_FISH_FLOP.get()));

    public static final DeferredBlock<Block> LEEDSICHTHYS_SLICE_BLOCK = registerBlock("leedsichthys_slice_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).strength(0.6F).sound(SoundType.CORAL_BLOCK)));
    public static final DeferredBlock<Block> LEEDSICHTHYS_SLICE_STAIRS = registerBlock("leedsichthys_slice_stairs", () -> new StairBlock(LEEDSICHTHYS_SLICE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(LEEDSICHTHYS_SLICE_BLOCK.get())));
    public static final DeferredBlock<Block> LEEDSICHTHYS_SLICE_SLAB = registerBlock("leedsichthys_slice_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(LEEDSICHTHYS_SLICE_BLOCK.get())));

    public static final DeferredBlock<Block> BLOOD_GASTRICLIGHT = registerBlock("blood_gastriclight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.3F).lightLevel(state -> 15).sound(SoundType.FROGLIGHT)));
    public static final DeferredBlock<Block> BILE_GASTRICLIGHT = registerBlock("bile_gastriclight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.3F).lightLevel(state -> 15).sound(SoundType.FROGLIGHT)));
    public static final DeferredBlock<Block> BROOD_GASTRICLIGHT = registerBlock("brood_gastriclight", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.3F).lightLevel(state -> 15).sound(SoundType.FROGLIGHT)));

    private static <B extends Block> DeferredBlock<B> registerBlock(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        BLOCK_TRANSLATIONS.add(block);
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerBlockNoLang(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerBlockWithoutItem(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        BLOCK_TRANSLATIONS.add(block);
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerBlockWithoutItemNoLang(String name, Supplier<? extends B> supplier) {
        return BLOCKS.register(name, supplier);
    }

    private static <B extends Block> DeferredBlock<B> registerEggBlock(String name, Supplier<B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        BLOCK_TRANSLATIONS.add(block);
        EGG_BLOCKS.add(block);
        return block;
    }

    @SuppressWarnings("SameParameterValue")
    private static <B extends Block> DeferredBlock<B> registerEggBlockNoLang(String name, Supplier<B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        EGG_BLOCKS.add(block);
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerWaterEggBlock(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new PlaceOnWaterBlockItem(block.get(), new Item.Properties()));
        BLOCK_TRANSLATIONS.add(block);
        EGG_BLOCKS.add(block);
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerPlushieBlock(String name, Supplier<B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        BLOCK_TRANSLATIONS.add(block);
        PLUSHIE_BLOCKS.add(block);
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerFossilBedBlock(String name, FossilBedBlockItem.FossilBedRarity rarity, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        UP2Items.ITEMS.register(name, () -> new FossilBedBlockItem(block.get(), new Item.Properties(), rarity));
        return block;
    }

    private static BlockBehaviour.Properties registerFlowerPot(FeatureFlag... featureFlags) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            properties = properties.requiredFeatures(featureFlags);
        }
        return properties;
    }

    @SuppressWarnings("SameParameterValue")
    private static ToIntFunction<BlockState> litBlockEmission(int lightLevel) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightLevel : 0;
    }

    public static class WoodSet {

        private final DeferredBlock<Block> PLANKS;
        private final DeferredBlock<Block> STAIRS;
        private final DeferredBlock<Block> SLAB;
        private final DeferredBlock<Block> LOG;
        private final DeferredBlock<Block> WOOD;
        private final DeferredBlock<Block> STRIPPED_LOG;
        private final DeferredBlock<Block> STRIPPED_WOOD;
        private final DeferredBlock<Block> FENCE;
        private final DeferredBlock<Block> FENCE_GATE;
        private final DeferredBlock<Block> BUTTON;
        private final DeferredBlock<Block> PRESSURE_PLATE;
        private final DeferredBlock<Block> DOOR;
        private final DeferredBlock<Block> TRAPDOOR;
        private final DeferredBlock<Block> SIGN;
        private final DeferredBlock<Block> WALL_SIGN;
        private final DeferredBlock<Block> HANGING_SIGN;
        private final DeferredBlock<Block> HANGING_WALL_SIGN;

        public WoodSet(String name) {
            BlockSetType blockSetType = BlockSetType.register(new BlockSetType(UnusualPrehistory2.MOD_ID + ":" + name));
            WoodType woodType = WoodType.register(new WoodType(UnusualPrehistory2.MOD_ID + ":" + name, blockSetType));
            WOOD_SETS.add(this);
            PLANKS = registerBlock(name + "_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
            STAIRS = registerBlock(name + "_stairs", () -> new StairBlock(PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(PLANKS.get())));
            SLAB = registerBlock(name + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PLANKS.get())));
            LOG = registerBlock(name + "_log", () -> new WoodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
            WOOD = registerBlock(name + "_wood", () -> new WoodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
            STRIPPED_LOG = registerBlock("stripped_" + name + "_log", () -> new WoodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
            STRIPPED_WOOD = registerBlock("stripped_" + name + "_wood", () -> new WoodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
            FENCE = registerBlock(name + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));
            FENCE_GATE = registerBlock(name + "_fence_gate", () -> new FenceGateBlock(woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));
            BUTTON = registerBlock(name + "_button", () -> new ButtonBlock(blockSetType, 15, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));
            PRESSURE_PLATE = registerBlock(name + "_pressure_plate", () -> new PressurePlateBlock(blockSetType, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
            DOOR = registerBlock(name + "_door", () -> new DoorBlock(blockSetType, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)));
            TRAPDOOR = registerBlock(name + "_trapdoor", () -> new TrapDoorBlock(blockSetType, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));
            SIGN = registerBlockWithoutItemNoLang(name + "_sign", () -> new StandingSignBlock(woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN)));
            WALL_SIGN = registerBlockWithoutItemNoLang(name + "_wall_sign", () -> new WallSignBlock(woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).lootFrom(SIGN)));
            HANGING_SIGN = registerBlockWithoutItemNoLang(name + "_hanging_sign", () -> new CeilingHangingSignBlock(woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN)));
            HANGING_WALL_SIGN = registerBlockWithoutItemNoLang(name + "_wall_hanging_sign", () -> new WallHangingSignBlock(woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).lootFrom(HANGING_SIGN)));
        }

        public DeferredBlock<Block> planks() {
            return PLANKS;
        }
        public DeferredBlock<Block> stairs() {
            return STAIRS;
        }
        public DeferredBlock<Block> slab() {
            return SLAB;
        }
        public DeferredBlock<Block> log() {
            return LOG;
        }
        public DeferredBlock<Block> wood() {
            return WOOD;
        }
        public DeferredBlock<Block> strippedLog() {
            return STRIPPED_LOG;
        }
        public DeferredBlock<Block> strippedWood() {
            return STRIPPED_WOOD;
        }
        public DeferredBlock<Block> fence() {
            return FENCE;
        }
        public DeferredBlock<Block> fenceGate() {
            return FENCE_GATE;
        }
        public DeferredBlock<Block> button() {
            return BUTTON;
        }
        public DeferredBlock<Block> pressurePlate() {
            return PRESSURE_PLATE;
        }
        public DeferredBlock<Block> door() {
            return DOOR;
        }
        public DeferredBlock<Block> trapdoor() {
            return TRAPDOOR;
        }
        public DeferredBlock<Block> sign() {
            return SIGN;
        }
        public DeferredBlock<Block> wallSign() {
            return WALL_SIGN;
        }
        public DeferredBlock<Block> hangingSign() {
            return HANGING_SIGN;
        }
        public DeferredBlock<Block> hangingWallSign() {
            return HANGING_WALL_SIGN;
        }

        public void setFlammables() {
            FireBlock fireBlock = (FireBlock) Blocks.FIRE;
            fireBlock.setFlammable(PLANKS.get(), 5, 20);
            fireBlock.setFlammable(STAIRS.get(), 5, 20);
            fireBlock.setFlammable(SLAB.get(), 5, 20);
            fireBlock.setFlammable(FENCE.get(), 5, 20);
        }

        public @Nullable BlockState checkLogStripping(BlockState state) {
            if (state.is(LOG.get())) return STRIPPED_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            if (state.is(WOOD.get())) return STRIPPED_WOOD.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
            return null;
        }
    }
}
