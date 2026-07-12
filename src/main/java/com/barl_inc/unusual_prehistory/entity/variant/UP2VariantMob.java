package com.barl_inc.unusual_prehistory.entity.variant;

import com.barl_inc.unusual_prehistory.registry.UP2MobVariants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.Optional;

public interface UP2VariantMob {

    String VARIANT_TAG = "Variant";

    default ResourceKey<Registry<UP2MobVariant>> variantRegistryKey() {
        return UP2MobVariants.registryFor(((Mob) this).getType());
    }

    default ResourceKey<UP2MobVariant> defaultVariant() {
        return UP2MobVariants.defaultFor(((Mob) this).getType());
    }

    ResourceLocation fallbackVariantTexture();

    String getVariantRawId();

    void setVariantRawId(String id);

    default ResourceLocation getVariantId() {
        ResourceLocation id = ResourceLocation.tryParse(this.getVariantRawId());
        return id != null ? id : this.defaultVariant().location();
    }

    default void setVariant(Holder<UP2MobVariant> variant) {
        variant.unwrapKey().ifPresent(key -> this.setVariantRawId(key.location().toString()));
    }

    default Optional<Holder.Reference<UP2MobVariant>> getVariantHolder() {
        return UP2MobVariantUtils.byId(((Mob) this).level().registryAccess(), this.variantRegistryKey(), this.getVariantId());
    }

    default boolean hasNonDefaultVariant() {
        return !this.getVariantId().equals(this.defaultVariant().location());
    }

    default ResourceLocation getVariantTexture() {
        return this.getVariantHolder().map(holder -> holder.value().texture()).orElseGet(this::fallbackVariantTexture);
    }
    default ResourceLocation getVariantBabyTexture() {
        return this.getVariantHolder().map(holder -> holder.value().babyTexture()).orElseGet(this::fallbackVariantTexture);
    }

    default ResourceLocation getVariantTextureRaw() {
        return this.getVariantHolder().map(holder -> holder.value().rawTexture()).orElseGet(this::fallbackVariantTexture);
    }
    default ResourceLocation getVariantBabyTextureRaw() {
        return this.getVariantHolder().map(holder -> holder.value().rawBabyTexture()).orElseGet(this::fallbackVariantTexture);
    }

    default Optional<String> getVariantModelKey() {
        return this.getVariantHolder().flatMap(holder -> holder.value().modelKey());
    }

    default void saveVariant(CompoundTag tag) {
        tag.putString(VARIANT_TAG, this.getVariantId().toString());
    }

    default void loadVariant(CompoundTag compoundTag) {
        if (compoundTag.contains(VARIANT_TAG)) {
            this.setVariantRawId(compoundTag.getString(VARIANT_TAG));
        }
    }

    default void pickVariantForSpawn(ServerLevelAccessor level) {
        UP2MobVariantUtils.selectVariantForSpawn(level, ((Mob) this).blockPosition(), this.variantRegistryKey()).ifPresent(this::setVariant);
    }

    default String inheritVariantFrom(AgeableMob otherParent, RandomSource random) {
        return otherParent instanceof UP2VariantMob other && random.nextBoolean() ? other.getVariantRawId() : this.getVariantRawId();
    }
}
