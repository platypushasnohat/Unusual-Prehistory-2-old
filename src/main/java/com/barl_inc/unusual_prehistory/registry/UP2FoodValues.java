package com.barl_inc.unusual_prehistory.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class UP2FoodValues {

    public static final FoodProperties GINKGO_FRUIT = new FoodProperties.Builder().nutrition(4).saturationModifier(0.2F).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 300, 0), 0.5F).build();

    public static final FoodProperties LEEDSICHTHYS_SLICE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.5F).build();

}
