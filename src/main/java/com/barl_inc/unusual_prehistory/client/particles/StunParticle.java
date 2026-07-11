package com.barl_inc.unusual_prehistory.client.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class StunParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    private final int entityId;
    private final float offset;

    protected StunParticle(ClientLevel level, double x, double y, double z, int entityId, float offset, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.hasPhysics = false;
        this.lifetime = 42;
        this.quadSize = 0.15F;
        this.offset = offset;
        this.entityId = entityId;
        Vec3 orbitPos = this.getOrbitPos();
        this.setPos(orbitPos.x, orbitPos.y, orbitPos.z);
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void render(@NotNull VertexConsumer consumer, @NotNull Camera camera, float partialTick) {
        if (age < 2) {
            return;
        }
        super.render(consumer, camera, partialTick);
    }

    @Override
    public void tick() {
        super.tick();
        if (entityId != -1) {
            Vec3 orbitPos = this.getOrbitPos();
            this.setPos(orbitPos.x, orbitPos.y, orbitPos.z);
        }
        this.setSpriteFromAge(spriteSet);
    }

    public Vec3 getOrbitPos() {
        Entity entity = level.getEntity(entityId);
        if (entity != null) {
            float angle = entity.tickCount * 10.0F + offset;
            Vec3 center = entity.position().add(0.0D, entity.getBbHeight() + 0.75F, 0.0D);
            Vec3 orbitOffset = new Vec3(0.0D, Mth.sin(entity.tickCount * 0.6F + (offset * 12.0F) * Mth.DEG_TO_RAD) * 0.05F, entity.getBbWidth() * 0.5F + 0.3F).yRot(angle * Mth.DEG_TO_RAD);
            return center.add(orbitOffset);
        }
        return Vec3.ZERO;
    }

    @Override
    protected int getLightColor(float partialTicks) {
        return 240;
    }

    @OnlyIn(Dist.CLIENT)
    public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new StunParticle(level, x, y, z, (int) xSpeed, (float) ySpeed, spriteSet);
        }
    }
}