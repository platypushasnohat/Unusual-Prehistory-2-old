package com.barl_inc.unusual_prehistory.items;

import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Kimmeridgebrachypteraeschnidium;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Aegirocassis;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.IntFunction;

public class UP2MobBucketItem extends MobBucketItem {

    protected final IntFunction<String> variantNameGetter;
    protected final EntityType<?> entityType;

    public UP2MobBucketItem(EntityType<?> entityType, Fluid fluid, SoundEvent sound, Properties properties) {
        this(entityType, fluid, sound, properties, null);
    }

    public UP2MobBucketItem(EntityType<?> entityType, Fluid fluid, SoundEvent sound, Properties properties, @Nullable IntFunction<String> variantNameGetter) {
        super(entityType, fluid, sound, properties.stacksTo(1));
        this.variantNameGetter = variantNameGetter;
        this.entityType = entityType;
    }

    @Override
    public void checkExtraContent(@Nullable Player player, @NotNull Level level, @NotNull ItemStack stack, @NotNull BlockPos pos) {
        if (level instanceof ServerLevel) {
            this.spawn((ServerLevel)level, stack, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    private void spawn(ServerLevel level, ItemStack stack, BlockPos pos) {
        Entity entity = this.entityType.spawn(level, stack, null, pos, MobSpawnType.BUCKET, true, false);
        if (entity instanceof Bucketable bucketable) {
            CustomData customdata = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
            bucketable.loadFromBucketTag(customdata.copyTag());
            bucketable.setFromBucket(true);
        }
        if (entity instanceof Aegirocassis aegirocassis && !aegirocassis.isBaby()) {
            aegirocassis.setBaby(true);
        }
        if (entity instanceof Kimmeridgebrachypteraeschnidium dragonfly && !dragonfly.isBaby()) {
            dragonfly.setBaby(true);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);

        if (variantNameGetter == null) {
            return;
        }
        ChatFormatting[] grayChatFormatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
        CustomData customdata = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);

        if (customdata.isEmpty() || !customdata.contains("Variant")) {
            return;
        }

        int variantId = customdata.copyTag().getInt("Variant");
        String variantName = variantNameGetter.apply(variantId);

        EntityType<?> type = entityType;
        ResourceLocation key = EntityType.getKey(type);

        String translationKey = "entity." + key.getNamespace() + "." + key.getPath() + ".variant_" + variantName;

        components.add(Component.translatable(translationKey).withStyle(grayChatFormatting));

        if (type == UP2Entities.COELACANTHUS.get()) {
            if (customdata.contains("Size")) {
                int i = customdata.copyTag().getInt("Size");
                String size = "entity." + key.getNamespace() + "." + key.getPath() + ".size";
                components.add((Component.translatable(size, i)).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            }
        }
    }
}