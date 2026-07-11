package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.ShringasaurusModel;
import com.barl_inc.unusual_prehistory.client.renderer.UP2RenderTypes;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Shringasaurus;
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
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ShringasaurusGlowLayer extends RenderLayer<Shringasaurus, ShringasaurusModel> {

    private static final ResourceLocation GLOW_TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/shringasaurus/shringasaurus_glow.png");

    public ShringasaurusGlowLayer(RenderLayerParent<Shringasaurus, ShringasaurusModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Shringasaurus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            ShringasaurusModel model = this.getParentModel();
            this.onlyDrawSelectedParts(model, entity.getFeedings());
            float alpha = ((float) (Math.sin(ageInTicks * 0.2F)) * 0.3F + 0.7F);
            VertexConsumer consumer = buffer.getBuffer(UP2RenderTypes.getEyesAlphaEnabled(GLOW_TEXTURE));
            model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), UP2ColorUtils.packColor(1.0F, 1.0F, 1.0F, alpha));
            this.resetDrawForAllParts(model);
        }
    }

    private void onlyDrawSelectedParts(ShringasaurusModel model, int feedings) {
        model.root().getAllParts().forEach(part -> part.skipDraw = true);
        model.getGlowParts(feedings).forEach(part -> part.skipDraw = false);
    }

    private void resetDrawForAllParts(ShringasaurusModel model) {
        model.root().getAllParts().forEach(part -> part.skipDraw = false);
    }
}