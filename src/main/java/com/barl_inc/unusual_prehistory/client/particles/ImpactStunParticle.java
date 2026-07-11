package com.barl_inc.unusual_prehistory.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class ImpactStunParticle extends TextureSheetParticle {

    private final SpriteSet sprites;
    private final float spinIncrement;

    protected ImpactStunParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z);
        this.sprites = sprites;
        this.quadSize *= 1.5F;
        this.friction = 0.98F;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.lifetime = 15 + level.getRandom().nextInt(10);
        this.roll = (float) Math.toRadians(360F * random.nextFloat());
        this.oRoll = roll;
        this.spinIncrement = (random.nextBoolean() ? -1 : 1) * random.nextFloat() * 0.6F;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(sprites);
        float ageProgress = this.age / (float) lifetime;
        float f = ageProgress - 0.5F;
        float f1 = 1.0F - f * 2F;
        this.oRoll = roll;
        this.roll += f1 * spinIncrement;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        return this.quadSize * Mth.clamp(((float) this.age + scaleFactor) / (float) this.lifetime * 3.0F, 0.0F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double zSpeed, double ySpeed) {
            ImpactStunParticle particle = new ImpactStunParticle(level, x, y, z, xSpeed, zSpeed, ySpeed, sprites);
            particle.pickSprite(sprites);
            return particle;
        }
    }
}