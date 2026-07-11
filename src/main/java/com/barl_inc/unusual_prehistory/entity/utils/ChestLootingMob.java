package com.barl_inc.unusual_prehistory.entity.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public interface ChestLootingMob {

    default boolean isLootable(Container inventory) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (this.shouldLootItem(inventory.getItem(i))) {
                return true;
            }
        }
        return false;
    }

    boolean shouldLootItem(ItemStack stack);

    default void afterLooting(BlockPos blockPos) {
    }

    default void startOpeningChest() {
    }

    default boolean openChest() {
        return true;
    }

}