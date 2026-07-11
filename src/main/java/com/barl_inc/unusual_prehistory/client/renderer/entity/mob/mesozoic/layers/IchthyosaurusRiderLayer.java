package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.IchthyosaurusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.layers.RiderLayer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Ichthyosaurus;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class IchthyosaurusRiderLayer extends RiderLayer<Ichthyosaurus, IchthyosaurusModel> {

    public IchthyosaurusRiderLayer(RenderLayerParent<Ichthyosaurus, IchthyosaurusModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Ichthyosaurus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isVehicle()) {
            Vec3 offset = new Vec3(0, -1.2F, 1.4F);
            Vec3 ridePos = this.getParentModel().getRiderPosition(offset);
            for (Entity passenger : entity.getPassengers()) {
                if (passenger == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                    continue;
                }
                UnusualPrehistory2.PROXY.releaseRenderingEntity(passenger.getUUID());
                poseStack.pushPose();
                this.getParentModel().translateRiderToBody(poseStack);
                poseStack.translate(ridePos.x, ridePos.y, ridePos.z);
                poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(180.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(-12.0F));
                renderPassenger(passenger, 0, 0, 0, 0, partialTicks, poseStack, bufferSource, packedLight);
                poseStack.popPose();
                UnusualPrehistory2.PROXY.blockRenderingEntity(passenger.getUUID());
            }
        }
    }
}
