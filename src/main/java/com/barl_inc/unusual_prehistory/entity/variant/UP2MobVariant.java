package com.barl_inc.unusual_prehistory.entity.variant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class UP2MobVariant {

    public static final Codec<UP2MobVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(variant -> variant.texture),
            ResourceLocation.CODEC.optionalFieldOf("baby_texture").forGetter(variant -> variant.babyTexture),
            RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(UP2MobVariant::biomes),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("weight", 1).forGetter(UP2MobVariant::weight),
            Codec.INT.optionalFieldOf("priority", 0).forGetter(UP2MobVariant::priority),
            VariantTime.CODEC.optionalFieldOf("time", VariantTime.ANY).forGetter(UP2MobVariant::time),
            VariantWeather.CODEC.optionalFieldOf("weather", VariantWeather.ANY).forGetter(UP2MobVariant::weather),
            Codec.INT.optionalFieldOf("min_spawn_height").forGetter(UP2MobVariant::minSpawnHeight),
            Codec.INT.optionalFieldOf("max_spawn_height").forGetter(UP2MobVariant::maxSpawnHeight),
            Codec.STRING.optionalFieldOf("model_key").forGetter(UP2MobVariant::modelKey)
    ).apply(instance, UP2MobVariant::new));

    private final ResourceLocation texture;
    private final Optional<ResourceLocation> babyTexture;
    private final Optional<HolderSet<Biome>> biomes;
    private final int weight;
    private final int priority;
    private final VariantTime time;
    private final VariantWeather weather;
    private final Optional<Integer> minSpawnHeight;
    private final Optional<Integer> maxSpawnHeight;
    private final Optional<String> modelKey;

    public UP2MobVariant(ResourceLocation texture, Optional<ResourceLocation> babyTexture, Optional<HolderSet<Biome>> biomes, int weight, int priority, VariantTime time, VariantWeather weather, Optional<Integer> minSpawnHeight, Optional<Integer> maxSpawnHeight, Optional<String> modelKey) {
        this.texture = texture;
        this.babyTexture = babyTexture;
        this.biomes = biomes;
        this.weight = weight;
        this.priority = priority;
        this.time = time;
        this.weather = weather;
        this.minSpawnHeight = minSpawnHeight;
        this.maxSpawnHeight = maxSpawnHeight;
        this.modelKey = modelKey;
    }

    public ResourceLocation texture() {
        return fullTextureId(this.texture);
    }

    public ResourceLocation babyTexture() {
        return this.babyTexture.map(UP2MobVariant::fullTextureId).orElse(this.texture);
    }

    public Optional<HolderSet<Biome>> biomes() {
        return this.biomes;
    }

    public int weight() {
        return this.weight;
    }

    public int priority() {
        return this.priority;
    }

    public VariantTime time() {
        return this.time;
    }

    public VariantWeather weather() {
        return this.weather;
    }

    public Optional<Integer> minSpawnHeight() {
        return this.minSpawnHeight;
    }

    public Optional<Integer> maxSpawnHeight() {
        return this.maxSpawnHeight;
    }

    public Optional<String> modelKey() {
        return this.modelKey;
    }

    private static ResourceLocation fullTextureId(ResourceLocation texture) {
        return texture.withPath(path -> "textures/" + path + ".png");
    }
}
