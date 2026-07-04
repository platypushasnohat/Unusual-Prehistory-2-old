package com.barlinc.unusual_prehistory.client.sounds;

import com.barlinc.unusual_prehistory.entity.mob.other.Grug;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GrugSound extends AbstractTickableSoundInstance {

    protected final Grug entity;
    private int fade = 0;

    public GrugSound(Grug entity) {
        super(UP2SoundEvents.GRUG_CHASE.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
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
        if (this.entity.isAggressive()) {
            this.pitch = 1.0F;
            if (fade > 0) {
                this.fade--;
            }
        } else {
            this.fade++;
        }
        this.volume = Mth.clamp(1.0F - fade / 40.0F, 0.0F, 1.0F);
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
        return !this.entity.isSilent() && this.entity.isAlive() && this.entity.isAggressive();
    }

    public boolean isSameEntity(Grug entity) {
        return this.entity.isAlive() && this.entity.getId() == entity.getId();
    }
}
