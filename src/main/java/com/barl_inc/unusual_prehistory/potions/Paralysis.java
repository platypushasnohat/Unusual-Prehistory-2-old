package com.barl_inc.unusual_prehistory.potions;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForgeMod;

public class Paralysis extends MobEffect {

    public static final ResourceLocation PARALYSIS_MODIFIER = UnusualPrehistory2.modPrefix("paralysis_speed_decrease");

	public Paralysis() {
		super(MobEffectCategory.HARMFUL, 0xffd649);
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.getType().is(UP2EntityTags.UNAFFECTED_BY_PARALYSIS)) {
			return false;
		}
        if (!(entity instanceof Player player && player.getAbilities().invulnerable) && entity.getHealth() > 1.0F) {
            entity.hurt(entity.damageSources().source(NeoForgeMod.POISON_DAMAGE), 1.0F);
        }
        AttributeInstance instance = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (instance != null) {
            instance.removeModifier(PARALYSIS_MODIFIER);
            instance.addTransientModifier(new AttributeModifier(PARALYSIS_MODIFIER, -0.8F, Operation.ADD_MULTIPLIED_TOTAL));
        }
		return true;
	}

    @Override
    public void removeAttributeModifiers(AttributeMap attributeMap) {
        AttributeInstance instance = attributeMap.getInstance(Attributes.MOVEMENT_SPEED);
        if (instance != null) {
            instance.removeModifier(PARALYSIS_MODIFIER);
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 60 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}