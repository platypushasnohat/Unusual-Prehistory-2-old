package com.barl_inc.unusual_prehistory.potions;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class Tranquility extends MobEffect {

    public Tranquility() {
        super(MobEffectCategory.BENEFICIAL, 0xe64558);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int level) {
        if (!entity.level().isClientSide) {
            if (entity instanceof Mob mob && mob.tickCount % 10 == 0) {
                if (mob.getTarget() != null) {
                    mob.setTarget(null);
                }
                if (mob.getLastHurtByMob() != null) {
                    mob.setLastHurtByMob(null);
                }
            }
            if (entity instanceof LivingEntity livingEntity && livingEntity.getHealth() < livingEntity.getMaxHealth() && livingEntity.tickCount % 50 == 0) {
                livingEntity.heal(1.0F);
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration > 0;
    }
}