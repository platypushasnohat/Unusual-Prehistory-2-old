package com.barl_inc.unusual_prehistory.datagen.server;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.tags.UP2GameEventTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.GameEventTagsProvider;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class UP2GameEventTagProvider extends GameEventTagsProvider {

    public UP2GameEventTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, UnusualPrehistory2.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        this.tag(UP2GameEventTags.THERIZINOSAURUS_CAN_LISTEN).add(
                GameEvent.STEP.key(),
                GameEvent.SWIM.key(),
                GameEvent.FLAP.key()
        );
    }
}
