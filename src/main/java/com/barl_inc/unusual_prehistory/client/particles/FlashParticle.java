package com.barl_inc.unusual_prehistory.client.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class FlashParticle extends TextureSheetParticle {

    @SuppressWarnings("FieldCanBeLocal, unused")
    private final SpriteSet spriteSet;

    protected FlashParticle(ClientLevel level, double x, double y, double z, int color, SpriteSet spriteSet) {
        super(level, x, y, z, 0, 0, 0);
        this.lifetime = 4;
        this.spriteSet = spriteSet;
        float randCol = level.getRandom().nextFloat() * 0.05F;
        this.setColor(Math.min(FastColor.ARGB32.red(color) / 255F + randCol, 1), Math.min(1F, FastColor.ARGB32.green(color) / 255F + randCol), Math.min(1F, FastColor.ARGB32.blue(color) / 255F + randCol));
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void render(@NotNull VertexConsumer consumer, @NotNull Camera camera, float partialTicks) {
        this.setAlpha(0.75F - ((float) age + partialTicks - 1.0F) * 0.25F * 0.5F);
        super.render(consumer, camera, partialTicks);
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        return 8.0F * Mth.sin(((float) age + scaleFactor - 1.0F) * 0.25F * (float) Math.PI);
    }

    @Override
    public int getLightColor(float partialTicks) {
        return 15728880;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class TusoteuthisProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public TusoteuthisProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FlashParticle(level, x, y, z, 0xfbf994, spriteSet);
        }
    }
}
