package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.MetriorhynchusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.layers.RiderLayer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Metriorhynchus;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class MetriorhynchusHeldMobLayer extends RiderLayer<Metriorhynchus, MetriorhynchusModel> {

    public MetriorhynchusHeldMobLayer(RenderLayerParent<Metriorhynchus, MetriorhynchusModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Metriorhynchus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Entity heldMob = entity.getHeldMob(entity);
        if (heldMob != null) {
            UnusualPrehistory2.PROXY.releaseRenderingEntity(heldMob.getUUID());
            poseStack.pushPose();
            this.getParentModel().translateToMouth(poseStack);
            poseStack.translate(0.0F, 0.22F * entity.getScale(), -1.15F * entity.getScale());
            poseStack.mulPose(Axis.XN.rotationDegrees(180F));
            poseStack.mulPose(Axis.YN.rotationDegrees(-90F));
            poseStack.mulPose(Axis.XN.rotationDegrees(-10F));
            if (!UnusualPrehistory2.PROXY.isFirstPersonPlayer(heldMob)) {
                renderPassenger(heldMob, 0, 0, 0, 0, partialTicks, poseStack, bufferSource, packedLight);
            }
            poseStack.popPose();
            UnusualPrehistory2.PROXY.blockRenderingEntity(heldMob.getUUID());
        }
    }
}