package com.barl_inc.unusual_prehistory.potions;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.UP2Particles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public class Dazzled extends MobEffect {

    public static final ResourceLocation DAZZLED_MODIFIER = UnusualPrehistory2.modPrefix("dazzled_speed_decrease");

    public Dazzled() {
        super(MobEffectCategory.HARMFUL, 0xfbf994);
    }

    @Override
    public @NotNull ParticleOptions createParticleOptions(@NotNull MobEffectInstance instance) {
        return UP2Particles.DAZZLE.get();
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
//            if (entity.tickCount % 8 == 0) {
//                ParticlePacket particlePacket = new ParticlePacket();
//                int index = (entity.tickCount / 8) % 5;
//                float offset = index * (360.0F / 5);
//                particlePacket.queueParticle(UP2Particles.STUN.get(), entity.getX(), entity.getEyeY(), entity.getZ(), entity.getId(), offset, 0.0D);
//                if (entity.level() instanceof ServerLevel serverLevel) {
//                    PacketDistributor.sendToPlayersTrackingChunk(serverLevel, new ChunkPos(entity.blockPosition()), particlePacket);
//                }
//            }
        }
        AttributeInstance instance = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (instance != null) {
            instance.removeModifier(DAZZLED_MODIFIER);
            instance.addTransientModifier(new AttributeModifier(DAZZLED_MODIFIER, -0.2F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
        return true;
    }

    @Override
    public void removeAttributeModifiers(AttributeMap attributeMap) {
        AttributeInstance instance = attributeMap.getInstance(Attributes.MOVEMENT_SPEED);
        if (instance != null) {
            instance.removeModifier(DAZZLED_MODIFIER);
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration > 0;
    }
}
