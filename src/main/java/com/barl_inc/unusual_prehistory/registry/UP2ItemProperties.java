package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.items.UP2MobBucketItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.registries.DeferredHolder;

public class UP2ItemProperties {

    public static void registerItemProperties() {
        for (DeferredHolder<Item, ? extends Item> item : UP2Items.ITEMS.getEntries()) {
            if (item.get() instanceof UP2MobBucketItem) {
                ItemProperties.register(item.get(), UnusualPrehistory2.modPrefix("variant"), (stack, level, living, i) -> {
                    CustomData customdata = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
                    return customdata.isEmpty() ? 0 : customdata.copyTag().getInt("Variant");
                });
            }
        }
        ItemProperties.register(UP2Items.PTERODACTYLUS_POT.get(), UnusualPrehistory2.modPrefix("variant"), (stack, level, living, i) -> {
            CustomData customdata = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
            return customdata.isEmpty() ? 0 : customdata.copyTag().getInt("Variant");
        });
        ItemProperties.register(UP2Items.ORGANIC_OOZE.get(), UnusualPrehistory2.modPrefix("ooze_shape"), (stack, level, living, j) -> (stack.getCount() % 5) / 5F);
    }
}
