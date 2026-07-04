package com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.KimmeridgebrachypteraeschnidiumModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.KimmeridgebrachypteraeschnidiumNymphModel;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.KimmeridgebrachypteraeschnidiumBaseLayer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.KimmeridgebrachypteraeschnidiumPatternLayer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.KimmeridgebrachypteraeschnidiumWingLayer;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Kimmeridgebrachypteraeschnidium;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumRenderer extends MobRenderer<Kimmeridgebrachypteraeschnidium, UP2Model<Kimmeridgebrachypteraeschnidium>> {

    private static final ResourceLocation NYMPH_TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/kimmeridgebrachypteraeschnidium/nymph.png");

    private final KimmeridgebrachypteraeschnidiumModel adultModel;
    private final KimmeridgebrachypteraeschnidiumNymphModel babyModel;

    public KimmeridgebrachypteraeschnidiumRenderer(EntityRendererProvider.Context context) {
        super(context, new KimmeridgebrachypteraeschnidiumModel(context.bakeLayer(UP2ModelLayers.KIMMERIDGEBRACHYPTERAESCHNIDIUM)), 0.3F);
        this.adultModel = new KimmeridgebrachypteraeschnidiumModel(context.bakeLayer(UP2ModelLayers.KIMMERIDGEBRACHYPTERAESCHNIDIUM));
        this.babyModel = new KimmeridgebrachypteraeschnidiumNymphModel(context.bakeLayer(UP2ModelLayers.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH));
        this.addLayer(new KimmeridgebrachypteraeschnidiumBaseLayer(this));
        this.addLayer(new KimmeridgebrachypteraeschnidiumPatternLayer(this));
        this.addLayer(new KimmeridgebrachypteraeschnidiumWingLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Kimmeridgebrachypteraeschnidium entity) {
        if (entity.isBaby()) {
            return NYMPH_TEXTURE;
        } else {
            return UnusualPrehistory2.modPrefix("textures/entity/mob/kimmeridgebrachypteraeschnidium/base/base_" + entity.getBaseColor() + ".png");
        }
    }

    @Override
    public void render(Kimmeridgebrachypteraeschnidium entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        this.model = entity.isBaby() ? babyModel : adultModel;
        if (!entity.isInvisible()) {
            poseStack.pushPose();
            if (entity.isAttachedToFace()) {
                Direction face = entity.getAttachmentFace();
                Vec3i normal = face.getNormal();
                float yRot = -face.getOpposite().toYRot();
                float xRot = (float) -Mth.atan2(Mth.sqrt(normal.getX() * normal.getX() + normal.getZ() * normal.getZ()), 0) * Mth.RAD_TO_DEG;
                float translate = entity.getBbWidth() / 2;
                float yTranslate = 0.2F;
                if (!entity.isAttached()) {
                    Vec3 pos = entity.getAttachmentPos();
                    double dist = pos.distanceTo(entity.position());
                    if (dist < 1) {
                        xRot *= (float) Math.min((-dist + 1) * 1.2, 1);
                        translate *= (float) Math.min((-dist + 1) * 1.2, 1);
                        yTranslate *= (float) Math.min((-dist + 1) * 1.2, 1);
                    } else {
                        xRot = 0;
                        translate = 0;
                        yTranslate = 0;
                    }
                    Vec3 offset = pos.subtract(entity.position());
                    double yawDiff = (Mth.atan2(offset.z, offset.x) * Mth.RAD_TO_DEG);
                    yRot = -(this.clampTo360(Mth.wrapDegrees(yawDiff - 90)));
                }
                yRot += entityYaw;
                poseStack.translate(-normal.getX() * translate, yTranslate, -normal.getZ() * translate);
                poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
                poseStack.mulPose(Axis.XP.rotationDegrees(xRot));
            }
            super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
            poseStack.popPose();
        }
    }

    private float clampTo360(double x) {
        return (float) (x - Math.floor(x / 360) * 360);
    }

    @Override
    protected void scale(Kimmeridgebrachypteraeschnidium entity, PoseStack poseStack, float partialTicks) {
        float f = entity.getSwelling(partialTicks);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.05F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f *= f;
        f *= f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        poseStack.scale(f2, f3, f2);
    }

    public static float getExplosionOverlayProgress(Kimmeridgebrachypteraeschnidium entity, float partialTicks) {
        float f = entity.getSwelling(partialTicks);
        return (int) (f * 20.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Kimmeridgebrachypteraeschnidium entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutout(this.getTextureLocation(entity));
    }
}
