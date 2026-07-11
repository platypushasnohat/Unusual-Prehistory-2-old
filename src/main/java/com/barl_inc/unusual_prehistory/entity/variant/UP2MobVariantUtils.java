package com.barl_inc.unusual_prehistory.entity.variant;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.Optional;

public class UP2MobVariantUtils {

    public static Optional<Holder.Reference<UP2MobVariant>> byId(HolderLookup.Provider provider, ResourceKey<Registry<UP2MobVariant>> registryKey, ResourceLocation id) {
        return provider.lookup(registryKey).flatMap(lookup -> lookup.get(ResourceKey.create(registryKey, id)));
    }

    public static Optional<Holder.Reference<UP2MobVariant>> byKey(HolderLookup.Provider provider, ResourceKey<Registry<UP2MobVariant>> registryKey, ResourceKey<UP2MobVariant> key) {
        return provider.lookup(registryKey).flatMap(lookup -> lookup.get(key));
    }

    public static Optional<Holder.Reference<UP2MobVariant>> selectVariantForSpawn(ServerLevelAccessor level, BlockPos pos, ResourceKey<Registry<UP2MobVariant>> registryKey) {
        Optional<Registry<UP2MobVariant>> maybeRegistry = level.registryAccess().registry(registryKey);
        if (maybeRegistry.isEmpty()) {
            return Optional.empty();
        }
        Registry<UP2MobVariant> registry = maybeRegistry.get();
        Holder<Biome> biome = level.getBiome(pos);
        List<Holder.Reference<UP2MobVariant>> pool = registry.holders().filter(holder -> holder.value().weight() > 0).filter(holder -> holder.value().biomes().map(biomes -> biomes.contains(biome)).orElse(false)).toList();
        if (pool.isEmpty()) {
            pool = registry.holders().filter(holder -> holder.value().weight() > 0 && holder.value().biomes().isEmpty()).toList();
        }
        return weightedChoice(highestPriority(pool), level.getRandom());
    }

    private static List<Holder.Reference<UP2MobVariant>> highestPriority(List<Holder.Reference<UP2MobVariant>> pool) {
        int max = pool.stream().mapToInt(holder -> holder.value().priority()).max().orElse(0);
        return pool.stream().filter(holder -> holder.value().priority() == max).toList();
    }

    private static Optional<Holder.Reference<UP2MobVariant>> weightedChoice(List<Holder.Reference<UP2MobVariant>> pool, RandomSource random) {
        int total = pool.stream().mapToInt(holder -> holder.value().weight()).sum();
        if (total <= 0) {
            return Optional.empty();
        }
        int roll = random.nextInt(total);
        for (Holder.Reference<UP2MobVariant> holder : pool) {
            roll -= holder.value().weight();
            if (roll < 0) {
                return Optional.of(holder);
            }
        }
        return Optional.empty();
    }
}
