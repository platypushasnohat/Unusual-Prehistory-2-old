package com.barl_inc.unusual_prehistory.entity.variant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class UP2MobVariant {

    public static final Codec<UP2MobVariant> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(variant -> variant.texture),
            ResourceLocation.CODEC.optionalFieldOf("baby_texture").forGetter(variant -> variant.babyTexture),
            RegistryCodecs.homogeneousList(Registries.BIOME).optionalFieldOf("biomes").forGetter(UP2MobVariant::biomes),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("weight", 1).forGetter(UP2MobVariant::weight),
            Codec.INT.optionalFieldOf("priority", 0).forGetter(UP2MobVariant::priority),
            ComponentSerialization.CODEC.optionalFieldOf("tooltip").forGetter(UP2MobVariant::tooltip)
    ).apply(instance, UP2MobVariant::new));

    private final ResourceLocation texture;
    private final Optional<ResourceLocation> babyTexture;
    private final Optional<HolderSet<Biome>> biomes;
    private final int weight;
    private final int priority;
    private final Optional<Component> tooltip;
    private final ResourceLocation textureFull;
    private final ResourceLocation babyTextureFull;

    public UP2MobVariant(ResourceLocation texture, Optional<ResourceLocation> babyTexture, Optional<HolderSet<Biome>> biomes, int weight, int priority, Optional<Component> tooltip) {
        this.texture = texture;
        this.babyTexture = babyTexture;
        this.biomes = biomes;
        this.weight = weight;
        this.priority = priority;
        this.tooltip = tooltip;
        this.textureFull = fullTextureId(texture);
        this.babyTextureFull = fullTextureId(babyTexture.orElse(texture));
    }

    public ResourceLocation texture() {
        return this.textureFull;
    }

    public ResourceLocation babyTexture() {
        return this.babyTextureFull;
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

    public Optional<Component> tooltip() {
        return this.tooltip;
    }

    private static ResourceLocation fullTextureId(ResourceLocation texture) {
        return texture.withPath(path -> "textures/" + path + ".png");
    }
}
