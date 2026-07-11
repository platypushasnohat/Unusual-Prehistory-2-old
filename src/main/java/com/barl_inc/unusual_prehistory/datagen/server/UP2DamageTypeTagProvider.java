package com.barl_inc.unusual_prehistory.datagen.server;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.tags.UP2DamageTypeTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.barl_inc.unusual_prehistory.registry.UP2DamageTypes.EXECUTE;
import static com.barl_inc.unusual_prehistory.registry.UP2DamageTypes.GRUG;

public class UP2DamageTypeTagProvider extends TagsProvider<DamageType> {

    public UP2DamageTypeTagProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        super(output, Registries.DAMAGE_TYPE, provider, UnusualPrehistory2.MOD_ID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(@NotNull Provider provider) {

        this.tag(UP2DamageTypeTags.KENTROSAURUS_IMMUNE_TO).add(
                DamageTypes.CACTUS,
                DamageTypes.SWEET_BERRY_BUSH,
                DamageTypes.THORNS
        );

        this.tag(UP2DamageTypeTags.LYSTROSAURUS_IMMUNE_TO).add(
                DamageTypes.CACTUS,
                DamageTypes.SWEET_BERRY_BUSH,
                DamageTypes.THORNS,
                DamageTypes.FALLING_ANVIL,
                DamageTypes.FALLING_STALACTITE,
                DamageTypes.IN_WALL
        ).addTags(
                DamageTypeTags.IS_FIRE,
                DamageTypeTags.IS_FREEZING,
                DamageTypeTags.IS_FALL,
                DamageTypeTags.IS_PROJECTILE,
                DamageTypeTags.IS_EXPLOSION
        );

        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(
                EXECUTE,
                GRUG
        );
    }
}
