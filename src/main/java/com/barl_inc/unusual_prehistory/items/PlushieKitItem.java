package com.barl_inc.unusual_prehistory.items;

import com.barl_inc.unusual_prehistory.entity.utils.PlushableMob;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlushieKitItem extends Item {

    public PlushieKitItem(Properties properties) {
        super(properties.stacksTo(1).durability(16));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity entity, @NotNull InteractionHand hand) {
        if (entity instanceof PlushableMob mob) {
            BlockPos blockPos = entity.blockPosition();
            boolean isClient = entity.level().isClientSide;
            if (!isClient) {
                ItemStack drop = mob.getPlushieItemStack();
                mob.spawnPlushie(entity.level(), blockPos, drop);
                entity.level().playSound(null, entity, SoundEvents.WOOL_HIT, SoundSource.BLOCKS, 1.0F, 0.9F + entity.getRandom().nextFloat() * 0.25F);
            }
            if (!isClient) {
                stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }
            return InteractionResult.sidedSuccess(isClient);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> components, @NotNull TooltipFlag flag) {
        components.add(Component.translatable("item.unusual_prehistory.plushie_kit.desc").withStyle(ChatFormatting.GRAY));
    }
}
