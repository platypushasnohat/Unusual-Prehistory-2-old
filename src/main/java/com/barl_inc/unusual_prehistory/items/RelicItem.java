package com.barl_inc.unusual_prehistory.items;

import com.barl_inc.unusual_prehistory.registry.UP2EnumProxy;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RelicItem extends Item {

    private final int ageType;
    private final int minAge;
    private final int maxAge;

    public RelicItem(int ageType, int minAge, int maxAge, Properties properties) {
        super(properties.stacksTo(64).rarity(UP2EnumProxy.RELIC.getValue()));
        this.ageType = ageType;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));

        int age = getAge(stack);
        if (age <= 0) {
            return;
        }
        tooltip.add(Component.literal(this.formatAge(age)).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        super.appendHoverText(stack, context, tooltip, flag);
    }

    public MutableComponent getDisplayName() {
        return Component.translatable(this.getDescriptionId() + ".desc");
    }

    private String formatAge(int age) {
        return switch (ageType) {
            case 0 -> age + " CE";
            case 1 -> age + " BCE";
            case 2 -> age + " KYA";
            case 3 -> age + " MYA";
            case 4 -> this.roundAge(age) + " BYA";
            default -> String.valueOf(age);
        };
    }

    private String roundAge(int age) {
        double v = age / 10.0D;
        String string = String.format("%.1f", v);
        return string.replaceAll("\\.?0+$", "");
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slot, boolean selected) {
        if (level.isClientSide) {
            return;
        }
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        CompoundTag compoundTag = (data != null) ? data.copyTag() : new CompoundTag();
        if (!compoundTag.contains("Age")) {
            int normalized = this.normalized(level.getRandom(), minAge, maxAge);
            compoundTag.putInt("Age", normalized);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(compoundTag));
        }
    }

    public static int getAge(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null) {
            return 0;
        }
        CompoundTag compoundTag = data.copyTag();
        return compoundTag.contains("Age") ? compoundTag.getInt("Age") : 0;
    }

    private int normalized(RandomSource random, int min, int max) {
        double mean = (min + max) / 2.0D;
        double deviation = (max - min) / 6.0D;
        double value = mean + random.nextGaussian() * deviation;
        return (int) Math.clamp(value, min, max);
    }
}
