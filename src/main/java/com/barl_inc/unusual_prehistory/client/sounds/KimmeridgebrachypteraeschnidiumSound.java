package com.barl_inc.unusual_prehistory.client.sounds;

import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Kimmeridgebrachypteraeschnidium;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumSound extends AbstractTickableSoundInstance {

    protected final Kimmeridgebrachypteraeschnidium entity;

    public KimmeridgebrachypteraeschnidiumSound(Kimmeridgebrachypteraeschnidium entity) {
        super(UP2SoundEvents.KIMMERIDGEBRACHYPTERAESCHNIDIUM_LOOP.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.entity = entity;
        this.x = (float) entity.getX();
        this.y = (float) entity.getY();
        this.z = (float) entity.getZ();
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0F;
    }

    @Override
    public void tick() {
        if (this.entity.isRemoved()) {
            this.stop();
            return;
        }
        this.x = (float) this.entity.getX();
        this.y = (float) this.entity.getY();
        this.z = (float) this.entity.getZ();
        float horizontalDistance = (float) this.entity.getDeltaMovement().horizontalDistance();
        if (entity.isFlying()) {
            this.pitch = Mth.lerp(Mth.clamp(horizontalDistance, 1.5F, 1.75F), 1.5F, 1.75F);
            this.volume = Mth.lerp(Mth.clamp(horizontalDistance, 0.0F, 0.25F), 0.2F, 0.3F);
        } else {
            this.volume = 0.0F;
            this.pitch = 0.0F;
        }
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.entity.isSilent() && this.entity.isAlive() && !this.entity.isBaby();
    }

    public boolean isSameEntity(Kimmeridgebrachypteraeschnidium entity) {
        return this.entity.isAlive() && this.entity.getId() == entity.getId();
    }
}
