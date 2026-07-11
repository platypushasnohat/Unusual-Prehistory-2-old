package com.barl_inc.unusual_prehistory.client.sounds;

import com.barl_inc.unusual_prehistory.entity.mob.base.AmbientMob;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BugBuzzSound extends AbstractTickableSoundInstance {

    protected final AmbientMob entity;

    public BugBuzzSound(AmbientMob entity) {
        super(UP2SoundEvents.BUG_BUZZ.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
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
        if (!entity.onGround()) {
            this.pitch = Mth.lerp(Mth.clamp(horizontalDistance, 1.0F, 1.25F), 1.0F, 1.25F);
            this.volume = Mth.lerp(Mth.clamp(horizontalDistance, 0.0F, 0.2F), 0.1F, 0.2F);
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
        return !this.entity.isSilent() && this.entity.isAlive();
    }

    public boolean isSameEntity(AmbientMob entity) {
        return this.entity.isAlive() && this.entity.getId() == entity.getId();
    }
}
