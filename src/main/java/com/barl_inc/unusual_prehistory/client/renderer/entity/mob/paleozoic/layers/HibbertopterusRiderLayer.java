package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic.layers;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.HibbertopterusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.layers.RiderLayer;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Hibbertopterus;
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
public class HibbertopterusRiderLayer extends RiderLayer<Hibbertopterus, HibbertopterusModel> {

    public HibbertopterusRiderLayer(RenderLayerParent<Hibbertopterus, HibbertopterusModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Hibbertopterus entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        float bodyYaw = entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTicks;
        if (entity.isVehicle()) {
            Vec3 offset = new Vec3(0, 0.2F, 0.6F);
            Vec3 ridePos = this.getParentModel().getRiderPosition(offset);
            for (Entity passenger : entity.getPassengers()) {
                if (passenger == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                    continue;
                }
                UnusualPrehistory2.PROXY.releaseRenderingEntity(passenger.getUUID());
                poseStack.pushPose();
                this.getParentModel().positionRider(poseStack);
                poseStack.translate(ridePos.x, ridePos.y - 2.0F + passenger.getBbHeight(), ridePos.z);
                poseStack.mulPose(Axis.XP.rotationDegrees(180F));
                poseStack.mulPose(Axis.YN.rotationDegrees(360 - bodyYaw));
                passenger.setYBodyRot(entity.getYRot());
                renderPassenger(passenger, 0, 0, 0, 0, partialTicks, poseStack, bufferSource, packedLight);
                poseStack.popPose();
                UnusualPrehistory2.PROXY.blockRenderingEntity(passenger.getUUID());
            }
        }
    }
}
