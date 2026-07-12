package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.MajungasaurusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.MajungasaurusAngryEmissiveLayer;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.MajungasaurusAngryLayer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Majungasaurus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class MajungasaurusRenderer extends MobRenderer<Majungasaurus, MajungasaurusModel> {

    public MajungasaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new MajungasaurusModel(context.bakeLayer(UP2ModelLayers.MAJUNGASAURUS)), 0.8F);
        this.addLayer(new MajungasaurusAngryLayer(this));
        this.addLayer(new MajungasaurusAngryEmissiveLayer(this));
    }

    @Override
    public void render(Majungasaurus entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isInvisible()) return;
        this.shadowRadius = entity.getCamoProgress(partialTicks) > 0.0F ? 0.0F : 0.8F;
        float alpha = 1.0F - entity.getCamoProgress(partialTicks);
        if (alpha > 0) super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Majungasaurus entity) {
        return entity.getVariantTexture();
    }

    @Nullable
    @Override
    protected RenderType getRenderType(Majungasaurus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        if (entity.getCamoProgress(1.0F) > 0.0F) {
            return RenderType.entityTranslucent(this.getTextureLocation(entity));
        } else {
            return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
        }
    }

    @Override
    protected void scale(Majungasaurus entity, PoseStack poseStack, float partialTicks) {
        float alpha = 1.0F - entity.getCamoProgress(partialTicks);
        this.model.setAlpha(alpha);
    }
}