package com.barlinc.unusual_prehistory.datagen.server;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.tags.UP2BlockTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.barlinc.unusual_prehistory.registry.UP2Blocks.*;

public class UP2BlockTagProvider extends BlockTagsProvider {

    public UP2BlockTagProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        super(output, provider, UnusualPrehistory2.MOD_ID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(@NotNull Provider provider) {
        // Unusual Prehistory

        this.tag(UP2BlockTags.DRYOPHYLLUM_LOGS).add(
                DRYOPHYLLUM.log().get(),
                DRYOPHYLLUM.wood().get(),
                DRYOPHYLLUM.strippedLog().get(),
                DRYOPHYLLUM.strippedWood().get()
        );

        this.tag(UP2BlockTags.GINKGO_LOGS).add(
                GINKGO.log().get(),
                GINKGO.wood().get(),
                GINKGO.strippedLog().get(),
                GINKGO.strippedWood().get()
        );

        this.tag(UP2BlockTags.LEPIDODENDRON_LOGS).add(
                LEPIDODENDRON.log().get(),
                LEPIDODENDRON.wood().get(),
                LEPIDODENDRON.strippedLog().get(),
                LEPIDODENDRON.strippedWood().get(),
                MOSSY_LEPIDODENDRON_LOG.get(),
                MOSSY_LEPIDODENDRON_WOOD.get()
        );

        this.tag(UP2BlockTags.METASEQUOIA_LOGS).add(
                METASEQUOIA.log().get(),
                METASEQUOIA.wood().get(),
                METASEQUOIA.strippedLog().get(),
                METASEQUOIA.strippedWood().get()
        );

        this.tag(UP2BlockTags.ANCIENT_PLANT_PLACEABLE).addTag(BlockTags.SAND).addTag(BlockTags.DIRT).add(Blocks.GRAVEL).add(Blocks.FARMLAND);

        this.tag(UP2BlockTags.ACCELERATES_EGG_HATCHING).addTag(BlockTags.WOOL).add(
                Blocks.HAY_BLOCK,
                Blocks.MOSS_BLOCK
        );
        this.tag(UP2BlockTags.PREVENTS_EGG_HATCHING).add(Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE);

        this.tag(UP2BlockTags.REINFORCED_GLASS).add(
                REINFORCED_GLASS.get(),
                TINTED_REINFORCED_GLASS.get(),
                WHITE_REINFORCED_GLASS.get(),
                LIGHT_GRAY_REINFORCED_GLASS.get(),
                GRAY_REINFORCED_GLASS.get(),
                BLACK_REINFORCED_GLASS.get(),
                BROWN_REINFORCED_GLASS.get(),
                RED_REINFORCED_GLASS.get(),
                ORANGE_REINFORCED_GLASS.get(),
                YELLOW_REINFORCED_GLASS.get(),
                LIME_REINFORCED_GLASS.get(),
                GREEN_REINFORCED_GLASS.get(),
                CYAN_REINFORCED_GLASS.get(),
                LIGHT_BLUE_REINFORCED_GLASS.get(),
                BLUE_REINFORCED_GLASS.get(),
                PURPLE_REINFORCED_GLASS.get(),
                MAGENTA_REINFORCED_GLASS.get(),
                PINK_REINFORCED_GLASS.get()
        );

        this.tag(UP2BlockTags.FOSSILIZED_BONE_BLOCKS).add(
                FOSSILIZED_BONE_BLOCK.get(),
                FOSSILIZED_BONE_BARK.get(),
                FOSSILIZED_BONE_VERTEBRA.get()
        );

        this.tag(UP2BlockTags.PETRIFIED_WOOD).add(
                PETRIFIED_LOG.get(),
                PETRIFIED_WOOD.get()
        );

        this.tag(UP2BlockTags.GUANGDEDENDRON_PLANTABLE_ON).addTag(BlockTags.SAND).addTag(BlockTags.DIRT).add(
                GUANGDEDENDRON.get(),
                GUANGDEDENDRON_SPORE.get(),
                Blocks.GRAVEL,
                Blocks.SUSPICIOUS_GRAVEL
        );

        this.tag(UP2BlockTags.GUARDED_BY_KENTROSAURUS).add(
                Blocks.SWEET_BERRY_BUSH,
                Blocks.CACTUS
        );

        this.tag(UP2BlockTags.DESMATOSUCHUS_ROLLING_BLOCKS).addTag(UP2BlockTags.DESMATOSUCHUS_SNOWY_BLOCKS).addTag(
                UP2BlockTags.DESMATOSUCHUS_MUDDY_BLOCKS).addTag(UP2BlockTags.DESMATOSUCHUS_MOSSY_BLOCKS
        );

        this.tag(UP2BlockTags.DESMATOSUCHUS_BURROWING_BLOCKS).addTag(UP2BlockTags.DESMATOSUCHUS_SNOWY_BLOCKS).addTag(BlockTags.SAND).addTag(Tags.Blocks.GRAVELS).addTag(
                UP2BlockTags.DESMATOSUCHUS_MUDDY_BLOCKS
        );

        this.tag(UP2BlockTags.DESMATOSUCHUS_MOSSY_BLOCKS).add(
                MOSSY_DIRT.get(),
                Blocks.MOSS_BLOCK
        );

        this.tag(UP2BlockTags.DESMATOSUCHUS_MUDDY_BLOCKS).add(
                Blocks.MUD
        );

        this.tag(UP2BlockTags.DESMATOSUCHUS_SNOWY_BLOCKS).add(
                Blocks.SNOW_BLOCK,
                Blocks.SNOW
        );

        this.tag(UP2BlockTags.DIPLOCAULUS_SLIDING_BLOCKS).add(
                Blocks.MUD,
                Blocks.ICE,
                Blocks.PACKED_ICE,
                Blocks.BLUE_ICE,
                Blocks.SNOW_BLOCK
        );

        this.tag(UP2BlockTags.DIPLOCAULUS_BURROWING_BLOCKS).add(
                Blocks.MUD,
                Blocks.SAND,
                Blocks.GRAVEL
        );

        this.tag(UP2BlockTags.LYSTROSAURUS_DIGGING_BLOCKS).add(
                MOSSY_DIRT.get(),
                Blocks.GRASS_BLOCK,
                Blocks.DIRT,
                Blocks.COARSE_DIRT,
                Blocks.SNOW_BLOCK,
                Blocks.SAND,
                Blocks.ROOTED_DIRT,
                Blocks.PACKED_MUD
        );

        this.tag(UP2BlockTags.DESMATOSUCHUS_FOOD_BLOCKS).add(
                MOSSY_DIRT.get(),
                Blocks.MOSS_BLOCK,
                Blocks.ROOTED_DIRT
        );

        this.tag(UP2BlockTags.JAWLESS_FISH_FOOD_BLOCKS).add(
                Blocks.MOSS_BLOCK,
                Blocks.MOSS_CARPET
        );

        this.tag(UP2BlockTags.KENTROSAURUS_FOOD_BLOCKS).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.LOBE_FINNED_FISH_FOOD_BLOCKS).add(
                Blocks.SEAGRASS
        );

        this.tag(UP2BlockTags.LYSTROSAURUS_FOOD_BLOCKS).add(
                MOSSY_DIRT.get(),
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.PACHYCEPHALOSAURUS_FOOD_BLOCKS).add(
                MOSSY_DIRT.get(),
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.PSILOPTERUS_DIGGING_BLOCKS).add(
                MOSSY_DIRT.get(),
                Blocks.GRASS_BLOCK,
                Blocks.DIRT,
                Blocks.COARSE_DIRT,
                Blocks.SNOW_BLOCK,
                Blocks.SNOW,
                Blocks.PODZOL,
                Blocks.ROOTED_DIRT,
                Blocks.GRAVEL
        );

        this.tag(UP2BlockTags.TALPANAS_FOOD_BLOCKS).add(
                Blocks.MOSS_BLOCK,
                Blocks.MOSS_CARPET
        );

        this.tag(UP2BlockTags.TARTUOSTEUS_FOOD_BLOCKS).add(
                Blocks.MOSS_BLOCK,
                Blocks.MOSS_CARPET
        );

        this.tag(UP2BlockTags.TELECREX_FOOD_BLOCKS).add(
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.COTYLORHYNCHUS_FOOD_BLOCKS).add(
                MOSSY_DIRT.get(),
                Blocks.MOSS_BLOCK,
                Blocks.GRASS_BLOCK
        );

        this.tag(UP2BlockTags.CONCAVENATOR_SWIMS_ON).addTags(BlockTags.SAND).add(
                Blocks.SOUL_SAND,
                Blocks.SOUL_SOIL
        );

        this.tag(UP2BlockTags.CONCAVENATOR_SAND_ARMOR_BLOCKS).add(
                Blocks.SAND
        );
        this.tag(UP2BlockTags.CONCAVENATOR_RED_SAND_ARMOR_BLOCKS).add(
                Blocks.RED_SAND
        );
        this.tag(UP2BlockTags.CONCAVENATOR_SOUL_SAND_ARMOR_BLOCKS).add(
                Blocks.SOUL_SAND,
                Blocks.SOUL_SOIL
        );

        this.tag(UP2BlockTags.PLUSHIES).add(
                BRACHIOSAURUS_PLUSHIE.get(),
                CARNOTAURUS_PLUSHIE.get(),
                COTYLORHYNCHUS_PLUSHIE.get(),
                GIANT_CAMPANILE_PLUSHIE.get(),
                HIBBERTOPTERUS_PLUSHIE.get(),
                KENTROSAURUS_PLUSHIE.get(),
                MAJUNGASAURUS_PLUSHIE.get(),
                TARTUOSTEUS_PLUSHIE.get()
        );

        this.tag(UP2BlockTags.MINEABLE_WITH_SHEARS).addTags(
                UP2BlockTags.PLUSHIES
        );

        this.tag(UP2BlockTags.FOSSIL_PILE_PLACEABLE).addTags(
                BlockTags.DIRT
        ).add(
                COBBLED_FOSSILIZED_BONE.get(),
                FOSSILIZED_BONE_BARK.get(),
                FOSSILIZED_BONE_BLOCK.get(),
                FOSSILIZED_SKULL.get(),
                FOSSILIZED_BONE_VERTEBRA.get()
        );

        // Minecraft
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                TRANSMOGRIFIER.get(),
                FOSSILIZED_BONE_BLOCK.get(),
                FOSSILIZED_BONE_BARK.get(),
                FOSSILIZED_BONE_VERTEBRA.get(),
                FOSSILIZED_SKULL.get(),
                FOSSILIZED_SKULL_LANTERN.get(),
                FOSSILIZED_SKULL_SOUL_LANTERN.get(),
                COBBLED_FOSSILIZED_BONE.get(),
                COBBLED_FOSSILIZED_BONE_STAIRS.get(),
                COBBLED_FOSSILIZED_BONE_SLAB.get(),
                FOSSILIZED_BONE_ROD.get(),
                FOSSILIZED_BONE_SPIKE.get(),
                FOSSILIZED_BONE_ROW.get(),
                PETRIFIED_LOG.get(),
                PETRIFIED_WOOD.get(),
                POLISHED_PETRIFIED_WOOD.get(),
                POLISHED_PETRIFIED_WOOD_STAIRS.get(),
                POLISHED_PETRIFIED_WOOD_SLAB.get(),
                POLISHED_PETRIFIED_WOOD_PRESSURE_PLATE.get(),
                POLISHED_PETRIFIED_WOOD_BUTTON.get(),
                ASPHALT.get(),

                COMMON_FOSSIL_BED.get(),
                UNCOMMON_FOSSIL_BED.get(),
                RARE_FOSSIL_BED.get(),
                UNUSUAL_FOSSIL_BED.get()
        ).addTag(UP2BlockTags.REINFORCED_GLASS);

        this.tag(BlockTags.IMPERMEABLE).addTag(UP2BlockTags.REINFORCED_GLASS);

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                HORSETAIL.get(),
                LARGE_HORSETAIL.get(),

                GUANGDEDENDRON.get(),
                GUANGDEDENDRON_SPORE.get(),

                CYCAD_STEM.get(),
                CYCAD_CROWN.get(),

                MOSSY_LEPIDODENDRON_LOG.get(), MOSSY_LEPIDODENDRON_WOOD.get(),

                LEEDSICHTHYS_SLICE_BLOCK.get(),
                LEEDSICHTHYS_SLICE_STAIRS.get(),
                LEEDSICHTHYS_SLICE_SLAB.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                MOSS_LAYER.get(),
                DRYOPHYLLUM_LEAVES.get(),
                GINKGO_LEAVES.get(),
                GOLDEN_GINKGO_LEAVES.get(),
                LEPIDODENDRON_LEAVES.get(),
                METASEQUOIA_LEAVES.get(),
                DAWN_METASEQUOIA_LEAVES.get(),
                DUSK_METASEQUOIA_LEAVES.get(),

                LEEDSICHTHYS_SLICE_BLOCK.get(),
                LEEDSICHTHYS_SLICE_STAIRS.get(),
                LEEDSICHTHYS_SLICE_SLAB.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                MOSSY_DIRT.get(),
                NEEDLE_LITTER.get(),
                PEAT.get(),
                LIVING_PEAT.get(),

                DIRT_MATRIX.get(),
                MUD_MATRIX.get(),
                GRAVEL_MATRIX.get(),
                SAND_MATRIX.get(),
                RED_SAND_MATRIX.get(),
                SNOW_MATRIX.get()
        );

        this.tag(BlockTags.SWORD_EFFICIENT).add(
                GUANGDEDENDRON_SPORE.get(),
                GUANGDEDENDRON.get()
        );

        this.tag(BlockTags.SLABS).add(
                POLISHED_PETRIFIED_WOOD_SLAB.get(),
                LEEDSICHTHYS_SLICE_SLAB.get()
        );

        this.tag(BlockTags.STAIRS).add(
                POLISHED_PETRIFIED_WOOD_STAIRS.get(),
                LEEDSICHTHYS_SLICE_STAIRS.get()
        );

        this.tag(BlockTags.STONE_PRESSURE_PLATES).add(
                POLISHED_PETRIFIED_WOOD_PRESSURE_PLATE.get()
        );

        this.tag(BlockTags.STONE_BUTTONS).add(
                POLISHED_PETRIFIED_WOOD_BUTTON.get()
        );

        this.tag(BlockTags.REPLACEABLE).add(
                HORSETAIL.get(),
                LARGE_HORSETAIL.get()
        );

        this.tag(BlockTags.REPLACEABLE_BY_TREES).add(
                HORSETAIL.get(),
                LARGE_HORSETAIL.get(),
                RAIGUENRAYUN.get(),
                AETHOPHYLLUM.get(),
                ZHANGSOLVA_BLOOM.get(),
                DELITZSCHALA_STALK.get()
        );

        this.tag(BlockTags.DIRT).add(
                MOSSY_DIRT.get(),
                NEEDLE_LITTER.get(),
                PEAT.get(),
                LIVING_PEAT.get()
        );

        this.tag(BlockTags.SMALL_FLOWERS).add(
                COOKSONIA.get(),
                LEEFRUCTUS.get()
        );

        this.tag(BlockTags.TALL_FLOWERS).add(
                AETHOPHYLLUM.get(),
                RAIGUENRAYUN.get(),
                ZHANGSOLVA_BLOOM.get()
        );

        this.tag(BlockTags.SAPLINGS).add(
                DRYOPHYLLUM_SAPLING.get(),
                GINKGO_SAPLING.get(),
                GOLDEN_GINKGO_SAPLING.get(),
                LEPIDODENDRON_CONE.get()
        );

        this.tag(BlockTags.LEAVES).add(
                DRYOPHYLLUM_LEAVES.get(),
                GINKGO_LEAVES.get(),
                GOLDEN_GINKGO_LEAVES.get(),
                LEPIDODENDRON_LEAVES.get()
        );

        this.tag(BlockTags.LOGS_THAT_BURN).addTags(UP2BlockTags.LEPIDODENDRON_LOGS, UP2BlockTags.DRYOPHYLLUM_LOGS, UP2BlockTags.GINKGO_LOGS, UP2BlockTags.METASEQUOIA_LOGS);

        this.tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(
                DRYOPHYLLUM.log().get(),
                GINKGO.log().get(),
                LEPIDODENDRON.log().get(),
                MOSSY_LEPIDODENDRON_LOG.get(),
                METASEQUOIA.log().get()
        );

        for (WoodSet woodset : WOOD_SETS) {
            this.tag(BlockTags.STANDING_SIGNS).add(woodset.sign().get());
            this.tag(BlockTags.WALL_SIGNS).add(woodset.wallSign().get());
            this.tag(BlockTags.CEILING_HANGING_SIGNS).add(woodset.hangingSign().get());
            this.tag(BlockTags.WALL_HANGING_SIGNS).add(woodset.hangingWallSign().get());
            this.tag(BlockTags.PLANKS).add(woodset.planks().get());
            this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(woodset.fenceGate().get());
            this.tag(BlockTags.FENCE_GATES).add(woodset.fenceGate().get());
            this.tag(Tags.Blocks.FENCES_WOODEN).add(woodset.fence().get());
            this.tag(BlockTags.WOODEN_FENCES).add(woodset.fence().get());
            this.tag(BlockTags.WOODEN_STAIRS).add(woodset.stairs().get());
            this.tag(BlockTags.WOODEN_BUTTONS).add(woodset.button().get());
            this.tag(BlockTags.WOODEN_DOORS).add(woodset.door().get());
            this.tag(BlockTags.WOODEN_SLABS).add(woodset.slab().get());
            this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(woodset.pressurePlate().get());
            this.tag(BlockTags.WOODEN_TRAPDOORS).add(woodset.trapdoor().get());
            this.tag(BlockTags.LOGS).add(woodset.log().get());
            this.tag(Tags.Blocks.STRIPPED_LOGS).add(woodset.strippedLog().get());
            this.tag(Tags.Blocks.STRIPPED_WOODS).add(woodset.strippedWood().get());
        }
    }
}
