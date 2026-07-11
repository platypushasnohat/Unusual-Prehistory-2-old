package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic.layers;

import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.PsilopterusModel;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Psilopterus;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PsilopterusHeldItemLayer extends RenderLayer<Psilopterus, PsilopterusModel> {

    public PsilopterusHeldItemLayer(RenderLayerParent<Psilopterus, PsilopterusModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Psilopterus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemstack = entity.getMainHandItem();
        if (!itemstack.isEmpty()) {
            poseStack.pushPose();
            if (entity.isBaby()) {
                poseStack.scale(0.5F, 0.5F, 0.5F);
                poseStack.translate(0.0D, 1.5D, 0.0D);
            }
            poseStack.pushPose();
            this.getParentModel().translateToMouth(poseStack);
            poseStack.translate(-0.1F, 0.0F, -0.3F);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(-45.0F));
            ItemInHandRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer();
            renderer.renderItem(entity, itemstack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight);
            poseStack.popPose();
            poseStack.popPose();
        }
    }
}