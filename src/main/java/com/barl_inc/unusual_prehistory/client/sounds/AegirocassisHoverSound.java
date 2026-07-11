package com.barl_inc.unusual_prehistory.client.sounds;

import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Aegirocassis;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AegirocassisHoverSound extends AbstractTickableSoundInstance {

    protected final Aegirocassis entity;
    private int fade = 0;

    public AegirocassisHoverSound(Aegirocassis entity) {
        super(UP2SoundEvents.AEGIROCASSIS_HOVER.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.entity = entity;
        this.x = (float) entity.getX();
        this.y = (float) entity.getY();
        this.z = (float) entity.getZ();
        this.looping = true;
        this.delay = 0;
        this.attenuation = SoundInstance.Attenuation.LINEAR;
    }

    @Override
    public void tick() {
        if (entity.isRemoved()) {
            this.stop();
            return;
        }
        this.x = (float) entity.getX();
        this.y = (float) entity.getY();
        this.z = (float) entity.getZ();
        if (this.entity.isLeaping()) {
            this.pitch = 1.0F;
            if (fade > 0) {
                this.fade--;
            }
        } else {
            this.fade++;
        }
        this.volume = Mth.clamp(1F - fade / 40F, 0F, 1F);
        if (fade > 40) {
            this.stop();
        }
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.entity.isSilent() && this.entity.isAlive() && this.entity.isLeaping();
    }

    public boolean isSameEntity(Aegirocassis entity) {
        return this.entity.isAlive() && this.entity.getId() == entity.getId();
    }
}
