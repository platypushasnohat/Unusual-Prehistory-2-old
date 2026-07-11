package com.barl_inc.unusual_prehistory.datagen.server;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.UP2Fluids;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class UP2FluidTagsProvider extends FluidTagsProvider {

    public UP2FluidTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        super(output, provider, UnusualPrehistory2.MOD_ID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(@NotNull Provider provider) {
        // Unusual Prehistory
        this.tag(FluidTags.WATER).add(UP2Fluids.TAR_FLUID_SOURCE.get(), UP2Fluids.TAR_FLUID_FLOWING.get());

    }
}
