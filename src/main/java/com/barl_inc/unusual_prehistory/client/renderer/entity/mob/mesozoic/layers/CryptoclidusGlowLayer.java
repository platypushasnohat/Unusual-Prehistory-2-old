package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.CryptoclidusModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Cryptoclidus;
import com.barl_inc.unusual_prehistory.utils.UP2ColorUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
public class CryptoclidusGlowLayer extends RenderLayer<Cryptoclidus, CryptoclidusModel> {

    private static final ResourceLocation GLOW_TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/cryptoclidus/cryptoclidus_glow.png");

    public CryptoclidusGlowLayer(RenderLayerParent<Cryptoclidus, CryptoclidusModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Cryptoclidus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) return;
        VertexConsumer consumer = buffer.getBuffer(RenderType.eyes(GLOW_TEXTURE));
        this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), UP2ColorUtils.packColor(1.0F, 1.0F, 1.0F, 1.0F));
    }
}