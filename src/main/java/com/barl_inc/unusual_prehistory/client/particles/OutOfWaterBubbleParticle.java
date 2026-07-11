package com.barl_inc.unusual_prehistory.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class OutOfWaterBubbleParticle extends TextureSheetParticle {

    protected OutOfWaterBubbleParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z);
        this.setSize(0.02F, 0.02F);
        this.quadSize = this.quadSize * (0.6F + (this.random.nextFloat() * 0.1F));
        this.xd = xSpeed * (double) 0.2F + (Math.random() * 2.0D - 1.0D) * (double) 0.02F;
        this.yd = ySpeed * (double) 0.2F + (Math.random() * 2.0D - 1.0D) * (double) 0.02F;
        this.zd = zSpeed * (double) 0.2F + (Math.random() * 2.0D - 1.0D) * (double) 0.02F;
        this.lifetime = (int) (8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        } else {
            this.yd += 0.002D;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.9F;
            this.yd *= 0.9F;
            this.zd *= 0.9F;
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            OutOfWaterBubbleParticle bubbleParticle = new OutOfWaterBubbleParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            bubbleParticle.pickSprite(this.sprites);
            return bubbleParticle;
        }
    }
}