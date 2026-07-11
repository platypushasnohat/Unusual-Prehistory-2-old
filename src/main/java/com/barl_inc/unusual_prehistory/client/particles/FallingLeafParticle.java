package com.barl_inc.unusual_prehistory.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class FallingLeafParticle extends TextureSheetParticle {

    private float rotSpeed;
    private final float spinAcceleration;
    private final float windBig;
    private final boolean swirl;
    private final boolean flowAway;
    private final double xaFlowScale;
    private final double zaFlowScale;
    private final double swirlPeriod;

    public FallingLeafParticle(ClientLevel level, double xPos, double yPos, double zPos, SpriteSet sprites, float gravityMultiplier, float windBig, boolean swirl, boolean flowAway, float size, float ySpeed) {
        super(level, xPos, yPos, zPos);
        this.setSprite(sprites.get(this.random.nextInt(12), 12));
        this.rotSpeed = (float) Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
        this.spinAcceleration = (float) Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
        this.windBig = windBig;
        this.swirl = swirl;
        this.flowAway = flowAway;
        this.lifetime = 300;
        this.gravity = gravityMultiplier * 1.2F * 0.0025F;
        float f = size * (this.random.nextBoolean() ? 0.05F : 0.075F);
        this.quadSize = f;
        this.setSize(f, f);
        this.friction = 1.0F;
        this.yd = -ySpeed;
        this.xaFlowScale = Math.cos(Math.toRadians(this.random.nextFloat() * 60.0F)) * this.windBig;
        this.zaFlowScale = Math.sin(Math.toRadians(this.random.nextFloat() * 60.0F)) * this.windBig;
        this.swirlPeriod = Math.toRadians(1000.0F + this.random.nextFloat() * 3000.0F);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        }

        if (!this.removed) {
            float f = 300 - this.lifetime;
            float f1 = Math.min(f / 300.0F, 1.0F);
            double d0 = 0.0;
            double d1 = 0.0;
            if (this.flowAway) {
                d0 += this.xaFlowScale * Math.pow(f1, 1.25);
                d1 += this.zaFlowScale * Math.pow(f1, 1.25);
            }

            if (this.swirl) {
                d0 += f1 * Math.cos(f1 * this.swirlPeriod) * this.windBig;
                d1 += f1 * Math.sin(f1 * this.swirlPeriod) * this.windBig;
            }

            this.xd += d0 * 0.0025F;
            this.zd += d1 * 0.0025F;
            this.yd = this.yd - this.gravity;
            this.rotSpeed = this.rotSpeed + this.spinAcceleration / 20.0F;
            this.oRoll = this.roll;
            this.roll = this.roll + this.rotSpeed / 20.0F;
            this.move(this.xd, this.yd, this.zd);
            if (this.onGround || this.lifetime < 299 && (this.xd == 0.0 || this.zd == 0.0)) {
                this.remove();
            }

            if (!this.removed) {
                this.xd = this.xd * this.friction;
                this.yd = this.yd * this.friction;
                this.zd = this.zd * this.friction;
            }
        }
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public record GinkgoProvider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double xPos, double yPos, double zPos, double xSpeed, double ySpeed, double zSpeed) {
            return new FallingLeafParticle(level, xPos, yPos, zPos, this.sprites, 0.11F, 10.0F, true, false, 2.0F, 0.023F);
        }
    }
}
