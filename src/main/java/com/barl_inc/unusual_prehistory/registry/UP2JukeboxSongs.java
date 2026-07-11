package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public class UP2JukeboxSongs {

    public static final ResourceKey<JukeboxSong> TARIFYING = create("tarifying");
    public static final ResourceKey<JukeboxSong> DOOMSURF = create("doomsurf");
    public static final ResourceKey<JukeboxSong> MEGALANIA = create("megalania");
    public static final ResourceKey<JukeboxSong> PUMMEL_AND_SNATCH = create("pummel_and_snatch");

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        register(context, DOOMSURF, UP2SoundEvents.DOOMSURF_DISC, 197, 15);
        register(context, MEGALANIA, UP2SoundEvents.MEGALANIA_DISC, 71, 15);
        register(context, PUMMEL_AND_SNATCH, UP2SoundEvents.PUMMEL_AND_SNATCH_DISC, 92, 15);
        register(context, TARIFYING, UP2SoundEvents.TARIFYING_DISC, 259, 14);
    }

    private static ResourceKey<JukeboxSong> create(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, UnusualPrehistory2.modPrefix(name));
    }

    private static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, Holder<SoundEvent> soundEvent, int lengthInSeconds, int comparatorOutput) {
        context.register(key, new JukeboxSong(soundEvent, Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())), lengthInSeconds, comparatorOutput));
    }
}
