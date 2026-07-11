package com.barl_inc.unusual_prehistory.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class EepyParticle extends SimpleAnimatedParticle {

    private final float sinOffset;
    private final float cosOffset;
    private final float rotationDirection;
    private final float initialX;
    private final float initialZ;

    private EepyParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, sprites, 0.0F);
        this.xd = (float) xSpeed;
        this.yd = (float) ySpeed;
        this.zd = (float) zSpeed;
        this.lifetime = 100 + random.nextInt(20);
        this.gravity = -0.065F;
        this.sinOffset = random.nextFloat();
        this.cosOffset = random.nextFloat();
        this.rotationDirection = random.nextBoolean() ? 1 : -1;
        this.initialX = (float) x;
        this.initialZ = (float) z;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        float radius = 0.2F;
        float speed = 0.1F;
        float xTarget = initialX + (float) Math.cos(age * speed + cosOffset * 4.0F) * radius * rotationDirection;
        float zTarget = initialZ + (float) Math.sin(age * speed + sinOffset * 4.0F) * radius * rotationDirection;
        this.xd = 0.25F * (xTarget - x);
        this.zd = 0.25F * (zTarget - z);
        this.setAlpha(1.0F - (age / (float) lifetime));
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new EepyParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
        }
    }
}
