package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class UP2PaintingVariants {

    public static final ResourceKey<PaintingVariant> UNUSUAL_ENCOUNTER = create("unusual_encounter");

    public UP2PaintingVariants() {
    }

    @SuppressWarnings("SameParameterValue")
    private static ResourceKey<PaintingVariant> create(String name) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, UnusualPrehistory2.modPrefix(name));
    }
}