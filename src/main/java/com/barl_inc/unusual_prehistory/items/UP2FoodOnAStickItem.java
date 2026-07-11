package com.barl_inc.unusual_prehistory.items;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ItemSteerable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class UP2FoodOnAStickItem extends Item {

    protected final int consumeItemDamage;

    public UP2FoodOnAStickItem(Properties properties, int consumeItemDamage) {
      super(properties);
      this.consumeItemDamage = consumeItemDamage;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            Entity entity = player.getControlledVehicle();
            if (player.isPassenger() && entity instanceof ItemSteerable itemsteerable && itemsteerable.boost()) {
                EquipmentSlot equipmentslot = LivingEntity.getSlotForHand(hand);
                ItemStack convertStack = itemstack.hurtAndConvertOnBreak(this.consumeItemDamage, Items.FISHING_ROD, player, equipmentslot);
                if (entity instanceof PrehistoricMob mob) {
                    mob.playSound(mob.getEatingSound());
                }
                return InteractionResultHolder.success(convertStack);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        return InteractionResultHolder.pass(itemstack);
    }
}