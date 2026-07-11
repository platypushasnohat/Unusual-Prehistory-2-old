package com.barl_inc.unusual_prehistory.datagen.server;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.UP2BannerPatterns;
import com.barl_inc.unusual_prehistory.registry.UP2JukeboxSongs;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class UP2DatapackProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.JUKEBOX_SONG, UP2JukeboxSongs::bootstrap)
            .add(Registries.BANNER_PATTERN, UP2BannerPatterns::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, UP2BiomeModifierProvider::bootstrap);


    public UP2DatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,BUILDER, Set.of(UnusualPrehistory2.MOD_ID));
    }
}
