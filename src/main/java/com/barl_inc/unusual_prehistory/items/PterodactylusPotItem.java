package com.barl_inc.unusual_prehistory.items;

import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Pterodactylus;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.IntFunction;

public class PterodactylusPotItem extends Item {

    private final IntFunction<String> variantNameGetter;

    public PterodactylusPotItem(Properties properties, @Nullable IntFunction<String> variantNameGetter) {
        super(properties);
        this.variantNameGetter = variantNameGetter;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        level.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1.1F);
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            CustomData customdata = itemstack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
            if (!context.getPlayer().getAbilities().instabuild) {
                context.getPlayer().setItemInHand(context.getHand(), new ItemStack(Items.FLOWER_POT));
            }
            Entity entity = UP2Entities.PTERODACTYLUS.get().spawn((ServerLevel) level, itemstack, context.getPlayer(), blockpos1, MobSpawnType.BUCKET, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);

            if (entity instanceof Pterodactylus mob) {
                mob.loadFromBucketTag(customdata.copyTag());
                mob.setFromBucket(true);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> components, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);

        if (variantNameGetter == null) return;
        ChatFormatting[] grayChatFormatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
        CustomData customdata = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);

        if (customdata.isEmpty() || !customdata.contains("BucketVariantTag")) return;

        int variantId = customdata.copyTag().getInt("BucketVariantTag");
        String variantName = variantNameGetter.apply(variantId);

        EntityType<?> type = UP2Entities.PTERODACTYLUS.get();
        ResourceLocation key = EntityType.getKey(type);

        String translationKey = "entity." + key.getNamespace() + "." + key.getPath() + ".variant_" + variantName;

        components.add(Component.translatable(translationKey).withStyle(grayChatFormatting));
    }
}