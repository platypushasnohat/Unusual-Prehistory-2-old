package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.TusoteuthisModel;
import com.barl_inc.unusual_prehistory.client.renderer.UP2RenderTypes;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Tusoteuthis;
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
public class TusoteuthisFlashLayer extends RenderLayer<Tusoteuthis, TusoteuthisModel> {

    private static final ResourceLocation FLASH_TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/tusoteuthis/tusoteuthis_flash.png");

    public TusoteuthisFlashLayer(RenderLayerParent<Tusoteuthis, TusoteuthisModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Tusoteuthis entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            float flashProgress = entity.getFlashProgress(partialTicks);
            if (flashProgress <= 0.0F) return;
            VertexConsumer consumer = buffer.getBuffer(UP2RenderTypes.getEyesAlphaEnabled(FLASH_TEXTURE));
            this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), UP2ColorUtils.packColor(1.0F, 1.0F, 1.0F, flashProgress));
        }
    }
}