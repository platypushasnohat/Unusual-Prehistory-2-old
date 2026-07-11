package com.barl_inc.unusual_prehistory.tags;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class UP2BlockTags {

    // Update 1
    public static final TagKey<Block> DIPLOCAULUS_SLIDING_BLOCKS = modBlockTag("diplocaulus_sliding_blocks");
    public static final TagKey<Block> DIPLOCAULUS_BURROWING_BLOCKS = modBlockTag("diplocaulus_burrowing_blocks");

    public static final TagKey<Block> JAWLESS_FISH_FOOD_BLOCKS = modBlockTag("jawless_fish_food_blocks");

    public static final TagKey<Block> GUARDED_BY_KENTROSAURUS = modBlockTag("guarded_by_kentrosaurus");
    public static final TagKey<Block> KENTROSAURUS_FOOD_BLOCKS = modBlockTag("kentrosaurus_food_blocks");

    public static final TagKey<Block> TALPANAS_FOOD_BLOCKS = modBlockTag("talpanas_food_blocks");
    public static final TagKey<Block> TELECREX_FOOD_BLOCKS = modBlockTag("telecrex_food_blocks");

    public static final TagKey<Block> ACCELERATES_EGG_HATCHING = modBlockTag("accelerates_egg_hatching");
    public static final TagKey<Block> PREVENTS_EGG_HATCHING = modBlockTag("prevents_egg_hatching");

    public static final TagKey<Block> ANCIENT_PLANT_PLACEABLE = modBlockTag("ancient_plant_placeable");

    public static final TagKey<Block> GINKGO_LOGS = modBlockTag("ginkgo_logs");
    public static final TagKey<Block> LEPIDODENDRON_LOGS = modBlockTag("lepidodendron_logs");

    public static final TagKey<Block> FOSSILIZED_BONE_BLOCKS = modBlockTag("fossilized_bone_blocks");
    public static final TagKey<Block> PETRIFIED_WOOD = modBlockTag("petrified_wood");

    // Update 3
    public static final TagKey<Block> TARTUOSTEUS_FOOD_BLOCKS = modBlockTag("tartuosteus_food_blocks");

    // Update 4
    public static final TagKey<Block> LOBE_FINNED_FISH_FOOD_BLOCKS = modBlockTag("lobe_finned_fish_food_blocks");

    public static final TagKey<Block> LYSTROSAURUS_DIGGING_BLOCKS = modBlockTag("lystrosaurus_digging_blocks");
    public static final TagKey<Block> LYSTROSAURUS_FOOD_BLOCKS = modBlockTag("lystrosaurus_food_blocks");

    public static final TagKey<Block> PACHYCEPHALOSAURUS_FOOD_BLOCKS = modBlockTag("pachycephalosaurus_food_blocks");

    public static final TagKey<Block> DRYOPHYLLUM_LOGS = modBlockTag("dryophyllum_logs");
    public static final TagKey<Block> METASEQUOIA_LOGS = modBlockTag("metasequoia_logs");

    public static final TagKey<Block> GUANGDEDENDRON_PLANTABLE_ON = modBlockTag("guangdedendron_plantable_on");

    public static final TagKey<Block> REINFORCED_GLASS = modBlockTag("reinforced_glass");

    // Update 5
    public static final TagKey<Block> DESMATOSUCHUS_BURROWING_BLOCKS = modBlockTag("desmatosuchus_burrowing_blocks");
    public static final TagKey<Block> DESMATOSUCHUS_ROLLING_BLOCKS = modBlockTag("desmatosuchus_rolling_blocks");
    public static final TagKey<Block> DESMATOSUCHUS_MOSSY_BLOCKS = modBlockTag("desmatosuchus_mossy_blocks");
    public static final TagKey<Block> DESMATOSUCHUS_MUDDY_BLOCKS = modBlockTag("desmatosuchus_muddy_blocks");
    public static final TagKey<Block> DESMATOSUCHUS_SNOWY_BLOCKS = modBlockTag("desmatosuchus_snowy_blocks");
    public static final TagKey<Block> DESMATOSUCHUS_FOOD_BLOCKS = modBlockTag("desmatosuchus_food_blocks");

    public static final TagKey<Block> PSILOPTERUS_DIGGING_BLOCKS = modBlockTag("psilopterus_digging_blocks");

    // Update 6
    public static final TagKey<Block> COTYLORHYNCHUS_FOOD_BLOCKS = modBlockTag("cotylorhynchus_food_blocks");

    public static final TagKey<Block> CONCAVENATOR_SWIMS_ON = modBlockTag("concavenator_swims_on");
    public static final TagKey<Block> CONCAVENATOR_SAND_ARMOR_BLOCKS = modBlockTag("concavenator_sand_armor_blocks");
    public static final TagKey<Block> CONCAVENATOR_RED_SAND_ARMOR_BLOCKS = modBlockTag("concavenator_red_sand_armor_blocks");
    public static final TagKey<Block> CONCAVENATOR_SOUL_SAND_ARMOR_BLOCKS = modBlockTag("concavenator_soul_sand_armor_blocks");

    public static final TagKey<Block> PLUSHIES = modBlockTag("plushies");
    public static final TagKey<Block> MINEABLE_WITH_SHEARS = modBlockTag("mineable/shears");

    public static final TagKey<Block> FOSSIL_PILE_PLACEABLE = modBlockTag("fossil_pile_placeable");

    private static TagKey<Block> modBlockTag(String name) {
        return blockTag(UnusualPrehistory2.MOD_ID, name);
    }

    private static TagKey<Block> commonBlockTag(String name) {
        return blockTag("c", name);
    }

    public static TagKey<Block> blockTag(String modid, String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(modid, name));
    }
}
