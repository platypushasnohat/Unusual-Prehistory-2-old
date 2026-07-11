package com.barl_inc.unusual_prehistory.entity.utils;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public interface PrehistoricMobInteractions {

    default SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_EAT;
    }

    default void applyFoodEffects(PrehistoricMob mob, ItemStack food, Level level) {
        Item item = food.getItem();
        if (!item.components().has(DataComponents.FOOD)) return;
        FoodProperties foodproperties = food.getFoodProperties(mob);
        if (foodproperties == null) return;
        for (FoodProperties.PossibleEffect effect : foodproperties.effects()) {
            if (!level.isClientSide && effect != null && level.random.nextFloat() < effect.probability()) {
                mob.addEffect(effect.effect());
            }
        }
    }

    default void feedItemToMob(PrehistoricMob mob, Player player, ItemStack food) {
        food.consume(1, player);
        mob.playSound(this.getEatingSound(), 1.0F, mob.getVoicePitch());
        this.applyFoodEffects(mob, food, mob.level());
        mob.gameEvent(GameEvent.EAT);
    }

    default void cycleMobCommands(PrehistoricMob mob, Player player) {
        mob.setCommand(mob.getCommand() + 1);
        if (mob.getCommand() == 3) {
            mob.setCommand(0);
        }
        player.displayClientMessage(Component.translatable("entity.unusual_prehistory.all.command_" + mob.getCommand(), mob.getName()), true);
        mob.setOrderedToSit(mob.getCommand() == 1);
    }

    default void ownerStartRidingMob(PrehistoricMob mob, Player player) {
        player.setYRot(mob.getYRot());
        player.setXRot(mob.getXRot());
        player.startRiding(mob);
    }

    default void pacifyMob(PrehistoricMob mob, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        this.feedItemToMob(mob, player, itemStack);
        mob.setPacified(true);
        mob.level().broadcastEntityEvent(mob, (byte) 10);
    }

    default void healMob(PrehistoricMob mob, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        this.feedItemToMob(mob, player, itemStack);
        float healAmount = mob.getMaxHealth() * 0.1F;
        if (itemStack.has(DataComponents.FOOD)) {
            FoodProperties foodproperties = itemStack.getFoodProperties(mob);
            healAmount = foodproperties != null ? (float) foodproperties.nutrition() * 2.0F : mob.getMaxHealth() * 0.1F;
            mob.level().broadcastEntityEvent(mob, (byte) 11);
        }
        mob.heal(healAmount);
    }

    default void increaseMobAge(PrehistoricMob mob, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        this.feedItemToMob(mob, player, itemStack);
        mob.ageUp(AgeableMob.getSpeedUpSecondsWhenFeeding(-mob.getAge()), true);
    }

    default void lockMobAge(PrehistoricMob mob, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        this.feedItemToMob(mob, player, itemStack);
        mob.setAgeLocked(true);
    }
}
