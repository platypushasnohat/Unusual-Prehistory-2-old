package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.MajungasaurusModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Majungasaurus;
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

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class MajungasaurusAngryEmissiveLayer extends RenderLayer<Majungasaurus, MajungasaurusModel> {

    public MajungasaurusAngryEmissiveLayer(RenderLayerParent<Majungasaurus, MajungasaurusModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Majungasaurus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isInvisible()) return;
        float angryProgress = entity.getAngryProgress(partialTicks);
        if (angryProgress <= 0.0F) return;
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(this.getAngryTexture(entity)));
        this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), UP2ColorUtils.packColor(angryProgress, angryProgress, angryProgress, angryProgress));
    }

    public ResourceLocation getAngryTexture(Majungasaurus entity) {
        Majungasaurus.MajungasaurusVariant variant = Majungasaurus.MajungasaurusVariant.byId(entity.getVariant().getId());
        return UnusualPrehistory2.modPrefix("textures/entity/mob/majungasaurus/" + variant.name().toLowerCase(Locale.ROOT) + "_angry_glow.png");
    }
}