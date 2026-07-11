package com.barl_inc.unusual_prehistory.items;

import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Kimmeridgebrachypteraeschnidium;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class KimmeridgebrachypteraeschnidiumBottleItem extends Item {

    public KimmeridgebrachypteraeschnidiumBottleItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        level.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.BLOCKS, 1.0F, 1.0F);
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
                context.getPlayer().setItemInHand(context.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            }
            Entity entity = UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get().spawn((ServerLevel) level, itemstack, context.getPlayer(), blockpos1, MobSpawnType.BUCKET, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);

            if (entity instanceof Kimmeridgebrachypteraeschnidium mob) {
                int age = customdata.contains("Age") ? customdata.copyTag().getInt("Age") : 0;
                float health = customdata.contains("Health") ? customdata.copyTag().getFloat("Health") : 6.0F;
                int base_color = customdata.contains("BaseColor") ? customdata.copyTag().getInt("BaseColor") : level.random.nextInt(16);
                int pattern = customdata.contains("Pattern") ? customdata.copyTag().getInt("Pattern") : level.random.nextInt(7);
                int pattern_color = customdata.contains("PatternColor") ? customdata.copyTag().getInt("PatternColor") : level.random.nextInt(16);
                int wing_color = customdata.contains("WingColor") ? customdata.copyTag().getInt("WingColor") : level.random.nextInt(16);
                boolean hasPattern = customdata.contains("HasPattern") && customdata.copyTag().getBoolean("HasPattern");
                mob.setAge(age);
                mob.setHealth(health);
                mob.setPersistenceRequired();
                mob.setBaseColor(base_color);
                mob.setPattern(pattern);
                mob.setPatternColor(pattern_color);
                mob.setWingColor(wing_color);
                mob.setHasPattern(hasPattern);
            }
            return InteractionResult.CONSUME;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> components, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);
        ChatFormatting[] grayChatFormatting = new ChatFormatting[]{ChatFormatting.ITALIC, ChatFormatting.GRAY};
        CustomData customdata = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
        if (!customdata.isEmpty() && customdata.contains("BaseColor")) {

            int base_color = customdata.copyTag().getInt("BaseColor");
            int pattern = customdata.copyTag().getInt("Pattern");
            int pattern_color = customdata.copyTag().getInt("PatternColor");
            int wing_color = customdata.copyTag().getInt("WingColor");
            boolean hasPattern = customdata.copyTag().getBoolean("HasPattern");

            String base = "entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.base_color_" + base_color;
            String patterns = "entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_" + Kimmeridgebrachypteraeschnidium.getPatternName(pattern);
            String patternColor = "entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.pattern_color_" + pattern_color;
            String wingColor = "entity.unusual_prehistory.kimmeridgebrachypteraeschnidium.wing_color_" + wing_color;

            MutableComponent patternInfo = Component.translatable(patternColor);
            patternInfo.append(CommonComponents.SPACE).append(Component.translatable(patterns));
            patternInfo.withStyle(grayChatFormatting);

            components.add(Component.translatable(base).withStyle(grayChatFormatting));
            components.add(Component.translatable(wingColor).withStyle(grayChatFormatting));
            if (hasPattern) {
                components.add(patternInfo);
            }
        }
    }
}