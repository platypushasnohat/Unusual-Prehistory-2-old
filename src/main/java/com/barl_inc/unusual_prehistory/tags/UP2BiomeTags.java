package com.barl_inc.unusual_prehistory.tags;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class UP2BiomeTags {

    public static final TagKey<Biome> HAS_FOSSIL = modBiomeTag("has_structure/fossil");
    public static final TagKey<Biome> HAS_TAR_PIT = modBiomeTag("has_structure/tar_pit");
    public static final TagKey<Biome> HAS_PETRIFIED_TREE = modBiomeTag("has_structure/petrified_tree");

    private static TagKey<Biome> modBiomeTag(String name) {
        return biomeTag(UnusualPrehistory2.MOD_ID, name);
    }

    private static TagKey<Biome> commonBiomeTag(String name) {
        return biomeTag("c", name);
    }

    public static TagKey<Biome> biomeTag(String modid, String name) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(modid, name));
    }
}
