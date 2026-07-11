package com.barl_inc.unusual_prehistory.entity.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface PlushableMob {

    ItemStack getPlushieItemStack();

    default void spawnPlushie(Level level, BlockPos pos, ItemStack drop) {
        if (this instanceof Entity entity) {
            ItemEntity itemEntity = entity.spawnAtLocation(drop, entity.getBbHeight() + 0.1F);
            if (itemEntity != null) {
                RandomSource random = entity.getRandom();
                Vec3 newDelta = itemEntity.getDeltaMovement().add((random.nextFloat() - random.nextFloat()) * 0.1F, random.nextFloat() * 0.05F, (random.nextFloat() - random.nextFloat()) * 0.1F);
                itemEntity.setDeltaMovement(newDelta);
            }
        } else {
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), drop));
        }
    }
}
