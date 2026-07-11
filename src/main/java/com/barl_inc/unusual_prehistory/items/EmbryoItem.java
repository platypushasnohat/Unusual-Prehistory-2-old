package com.barl_inc.unusual_prehistory.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class EmbryoItem extends Item {

    public final Supplier<? extends EntityType<?>> toSpawn;

    public EmbryoItem(Properties properties, Supplier<? extends EntityType<?>> toSpawn) {
        super(properties);
        this.toSpawn = toSpawn;
    }
}
