package com.barl_inc.unusual_prehistory.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FossilBedBlockItem extends BlockItem {

    private final FossilBedRarity rarity;

    public FossilBedBlockItem(Block block, Properties properties, FossilBedRarity rarity) {
        super(block, properties);
        this.rarity = rarity;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        this.getBlock().appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(this.getDisplayName().withColor(rarity.color));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public MutableComponent getDisplayName() {
        return Component.translatable(this.getDescriptionId() + ".desc");
    }

    public enum FossilBedRarity {
        COMMON(0x986748),
        UNCOMMON(0x16ac24),
        RARE(0xe44d00),
        UNUSUAL(0xa682f4);

        public final int color;

        FossilBedRarity(int color) {
            this.color = color;
        }
    }
}