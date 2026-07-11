package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class UP2LootTables {

    public static final ResourceKey<LootTable> AUSTRORAPTOR_SHEARING = createLootTableKey("entities/austroraptor_shearing");

    public static final ResourceKey<LootTable> DESMATOSUCHUS_MOSSY = createLootTableKey("entities/desmatosuchus_mossy");
    public static final ResourceKey<LootTable> DESMATOSUCHUS_MUDDY = createLootTableKey("entities/desmatosuchus_muddy");
    public static final ResourceKey<LootTable> DESMATOSUCHUS_SNOWY = createLootTableKey("entities/desmatosuchus_snowy");

    private static ResourceKey<LootTable> createLootTableKey(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, UnusualPrehistory2.modPrefix(name));
    }
}
