package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.KimmeridgebrachypteraeschnidiumRenderer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Kimmeridgebrachypteraeschnidium;
import com.barl_inc.unusual_prehistory.utils.UP2ColorUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumBaseLayer extends RenderLayer<Kimmeridgebrachypteraeschnidium, UP2Model<Kimmeridgebrachypteraeschnidium>> {

    public KimmeridgebrachypteraeschnidiumBaseLayer(RenderLayerParent<Kimmeridgebrachypteraeschnidium, UP2Model<Kimmeridgebrachypteraeschnidium>> renderer) {
        super(renderer);
    }

    public ResourceLocation baseTextures(Kimmeridgebrachypteraeschnidium entity) {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/kimmeridgebrachypteraeschnidium/base/base_" + entity.getBaseColor() + ".png");
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Kimmeridgebrachypteraeschnidium entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible() && !entity.isBaby()) {
            ResourceLocation resourceLocation = this.baseTextures(entity);
            renderTranslucentModel(this.getParentModel(), resourceLocation, poseStack, bufferSource, packedLight, partialTicks, entity);
        }
    }

    protected static void renderTranslucentModel(EntityModel<Kimmeridgebrachypteraeschnidium> model, @NotNull ResourceLocation resourceLocation, @NotNull PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTicks, Kimmeridgebrachypteraeschnidium entity) {
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityTranslucent(resourceLocation));
        int i = LivingEntityRenderer.getOverlayCoords(entity, KimmeridgebrachypteraeschnidiumRenderer.getExplosionOverlayProgress(entity, partialTicks));
        model.renderToBuffer(poseStack, vertexconsumer, packedLight, i, UP2ColorUtils.packColor(1, 1, 1, 1));
    }
}