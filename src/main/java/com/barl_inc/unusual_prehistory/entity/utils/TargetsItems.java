package com.barl_inc.unusual_prehistory.entity.utils;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;

public interface TargetsItems {

    boolean canTargetItems(ItemStack stack);

    void onGetItem(ItemEntity item);

    default void onFindItem(ItemEntity item) {
    }

    default double getMaxDistToItem() {
        return 3.0D;
    }

    default void eatItem(PrehistoricMob mob, ItemStack itemStack) {
        mob.playSound(mob.getEatingSound(), 1.0F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
        mob.applyFoodEffects(mob, itemStack, mob.level());
        mob.gameEvent(GameEvent.EAT);
        if (mob.getHealth() < mob.getMaxHealth()) {
            float healAmount = mob.getMaxHealth() * 0.1F;
            if (itemStack.has(DataComponents.FOOD)) {
                FoodProperties foodproperties = itemStack.getFoodProperties(mob);
                healAmount = foodproperties != null ? (float) foodproperties.nutrition() * 2.0F : mob.getMaxHealth() * 0.1F;
                mob.level().broadcastEntityEvent(mob, (byte) 11);
            }
            mob.heal(healAmount);
        }
        itemStack.shrink(1);
    }
}