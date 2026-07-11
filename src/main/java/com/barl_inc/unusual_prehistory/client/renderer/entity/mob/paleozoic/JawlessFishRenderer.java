package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.*;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.JawlessFish;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JawlessFishRenderer extends MobRenderer<JawlessFish, JawlessFishModel> {

    private final JawlessFishCephalaspisModel jawlessFishCephalaspisModel;
    private final JawlessFishDoryaspisModel jawlessFishDoryaspisModel;
    private final JawlessFishFurcacaudaModel jawlessFishFurcacaudaModel;
    private final JawlessFishSacabambaspisModel jawlessFishSacabambaspisModel;
    private final JawlessFishArandaspisModel jawlessFishArandaspisModel;

    public JawlessFishRenderer(EntityRendererProvider.Context context) {
        super(context, new JawlessFishCephalaspisModel(context.bakeLayer(UP2ModelLayers.JAWLESS_FISH_CEPHALASPIS)), 0.25F);
        this.jawlessFishCephalaspisModel = new JawlessFishCephalaspisModel(context.bakeLayer(UP2ModelLayers.JAWLESS_FISH_CEPHALASPIS));
        this.jawlessFishDoryaspisModel = new JawlessFishDoryaspisModel(context.bakeLayer(UP2ModelLayers.JAWLESS_FISH_DORYASPIS));
        this.jawlessFishFurcacaudaModel = new JawlessFishFurcacaudaModel(context.bakeLayer(UP2ModelLayers.JAWLESS_FISH_FURACACAUDA));
        this.jawlessFishSacabambaspisModel = new JawlessFishSacabambaspisModel(context.bakeLayer(UP2ModelLayers.JAWLESS_FISH_SACABAMBASPIS));
        this.jawlessFishArandaspisModel = new JawlessFishArandaspisModel(context.bakeLayer(UP2ModelLayers.JAWLESS_FISH_ARANDASPIS));
    }

    @Override
    public void render(JawlessFish entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        switch (entity.getVariantModelKey().orElse("cephalaspis")) {
            case "arandaspis":
                this.model = this.jawlessFishArandaspisModel;
                break;
            case "doryaspis":
                this.model = this.jawlessFishDoryaspisModel;
                break;
            case "furcacauda":
                this.model = this.jawlessFishFurcacaudaModel;
                break;
            case "sacabambaspis":
                this.model = this.jawlessFishSacabambaspisModel;
                break;
            default:
                this.model = this.jawlessFishCephalaspisModel;
                break;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(JawlessFish entity) {
        return entity.getVariantTexture();
    }
}
