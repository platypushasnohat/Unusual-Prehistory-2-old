package com.barl_inc.unusual_prehistory.items;

import com.barl_inc.unusual_prehistory.registry.UP2CriteriaTriggers;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;

public class PaleopediaItem extends Item {

    public PaleopediaItem(Properties properties) {
        super(properties);
    }

    public static boolean isOpen() {
        return BuiltInRegistries.ITEM.getKey(UP2Items.PALEOPEDIA.get()).equals(PatchouliAPI.get().getOpenBookGui());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> components, @NotNull TooltipFlag flag) {
        components.add(Component.translatable("item.unusual_prehistory.paleopedia.desc").withStyle(ChatFormatting.GRAY));
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer) {
            PatchouliAPI.get().openBookGUI(serverPlayer, BuiltInRegistries.ITEM.getKey(this));
            player.playSound(SoundEvents.BOOK_PAGE_TURN, 1F, 0.9F + level.getRandom ().nextFloat() * 0.25F);
            if (serverPlayer.isCreative()) {
                UP2CriteriaTriggers.OPEN_BOOK_CREATIVE_MODE.get().trigger(serverPlayer);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
