package com.barlinc.unusual_prehistory.items;

import com.barlinc.unusual_prehistory.entity.mob.paleozoic.Arthropleura;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BrownMushroomOnAStickItem extends UP2FoodOnAStickItem {

    public BrownMushroomOnAStickItem(Properties properties, int consumeItemDamage) {
      super(properties, consumeItemDamage);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            Arthropleura arthropleura = (Arthropleura) player.getVehicle();
            if (arthropleura != null && arthropleura.getRidingPlayer() == player && player.isPassenger() && arthropleura.boost()) {
                EquipmentSlot equipmentslot = LivingEntity.getSlotForHand(hand);
                ItemStack convertStack = itemstack.hurtAndConvertOnBreak(consumeItemDamage, Items.FISHING_ROD, player, equipmentslot);
                arthropleura.playSound(arthropleura.getEatingSound());
                return InteractionResultHolder.success(convertStack);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }
        return InteractionResultHolder.pass(itemstack);
    }
}