package com.barl_inc.unusual_prehistory.datagen.server;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.tags.UP2BiomeTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class UP2BiomeTagProvider extends BiomeTagsProvider {

    public UP2BiomeTagProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        super(output, provider, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull Provider provider) {
        this.addTags();
    }

    protected void addTags() {

        this.tag(UP2BiomeTags.HAS_FOSSIL)
                .addTag(BiomeTags.IS_TAIGA)
                .addTag(BiomeTags.IS_FOREST)
                .addTag(BiomeTags.IS_JUNGLE)
                .addTag(BiomeTags.IS_SAVANNA)
                .addTag(BiomeTags.IS_BADLANDS)
                .addTag(Tags.Biomes.IS_PLAINS)
                .addTag(Tags.Biomes.IS_SWAMP)
                .addTag(Tags.Biomes.IS_DESERT);

        this.tag(UP2BiomeTags.HAS_TAR_PIT)
                .addTag(BiomeTags.IS_TAIGA)
                .addTag(BiomeTags.IS_FOREST)
                .addTag(BiomeTags.IS_JUNGLE)
                .addTag(BiomeTags.IS_SAVANNA)
                .addTag(Tags.Biomes.IS_PLAINS);

        this.tag(UP2BiomeTags.HAS_PETRIFIED_TREE)
                .addTag(BiomeTags.IS_TAIGA)
                .addTag(BiomeTags.IS_FOREST)
                .addTag(BiomeTags.IS_SAVANNA)
                .addTag(BiomeTags.IS_BADLANDS)
                .addTag(Tags.Biomes.IS_PLAINS)
                .addTag(Tags.Biomes.IS_DESERT);

    }
}
