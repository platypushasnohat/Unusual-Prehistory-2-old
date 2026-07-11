package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.variant.UP2MobVariant;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UP2MobVariants {

    public static final Set<ResourceKey<Registry<UP2MobVariant>>> REGISTRIES = new HashSet<>();

    public static ResourceKey<Registry<UP2MobVariant>> register(String name) {
        ResourceKey<Registry<UP2MobVariant>> key = registryFor(name);
        REGISTRIES.add(key);
        return key;
    }

    public static void registerVariantRegistries(DataPackRegistryEvent.NewRegistry event) {
        REGISTRIES.forEach(registry -> event.dataPackRegistry(registry, UP2MobVariant.DIRECT_CODEC, UP2MobVariant.DIRECT_CODEC));
    }

    public static ResourceKey<Registry<UP2MobVariant>> registryFor(String mobName) {
        return ResourceKey.createRegistryKey(UnusualPrehistory2.modPrefix("mob_variant/" + mobName));
    }

    public static ResourceKey<Registry<UP2MobVariant>> registryFor(EntityType<?> type) {
        return registryFor(EntityType.getKey(type).getPath());
    }

    public static Optional<ResourceKey<Registry<UP2MobVariant>>> findRegistry(EntityType<?> type) {
        ResourceLocation id = EntityType.getKey(type);

        if (!id.getNamespace().equals(UnusualPrehistory2.MOD_ID)) {
            return Optional.empty();
        }

        ResourceKey<Registry<UP2MobVariant>> registry = registryFor(id.getPath());
        return REGISTRIES.contains(registry) ? Optional.of(registry) : Optional.empty();
    }

    public static ResourceKey<UP2MobVariant> defaultFor(EntityType<?> type) {
        String name = EntityType.getKey(type).getPath();
        return createKey(registryFor(name), name);
    }

    public static ResourceKey<UP2MobVariant> createKey(ResourceKey<Registry<UP2MobVariant>> registry, String name) {
        return ResourceKey.create(registry, UnusualPrehistory2.modPrefix(name));
    }
}
