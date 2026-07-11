package com.barl_inc.unusual_prehistory.tags;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.gameevent.GameEvent;

public class UP2GameEventTags {

    public static final TagKey<GameEvent> THERIZINOSAURUS_CAN_LISTEN = modGameEventTag("therizinosaurus_can_listen");

    private static TagKey<GameEvent> modGameEventTag(String name) {
        return gameEventTag(UnusualPrehistory2.MOD_ID, name);
    }

    private static TagKey<GameEvent> commonGameEventTaf(String name) {
        return gameEventTag("c", name);
    }

    public static TagKey<GameEvent> gameEventTag(String modId, String name) {
        return TagKey.create(Registries.GAME_EVENT, ResourceLocation.fromNamespaceAndPath(modId, name));
    }
}
