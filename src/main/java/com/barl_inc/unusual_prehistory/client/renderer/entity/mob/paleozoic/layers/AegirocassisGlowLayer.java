package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic.layers;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.client.renderer.UP2RenderTypes;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Aegirocassis;
import com.barl_inc.unusual_prehistory.utils.UP2ColorUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AegirocassisGlowLayer extends RenderLayer<Aegirocassis, UP2Model<Aegirocassis>> {

    private static final ResourceLocation GLOW_TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/aegirocassis/aegirocassis_glow.png");

    public AegirocassisGlowLayer(RenderLayerParent<Aegirocassis, UP2Model<Aegirocassis>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Aegirocassis entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible() || entity.isBaby()) return;
        float glowProgress = entity.getGlowProgress(partialTicks);
        if (glowProgress <= 0.0F) {
            return;
        }
        VertexConsumer consumer = buffer.getBuffer(UP2RenderTypes.getEyesAlphaEnabled(GLOW_TEXTURE));
        this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), UP2ColorUtils.packColor(1.0F, 1.0F, 1.0F, glowProgress));
    }
}