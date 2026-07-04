package com.barlinc.unusual_prehistory.client.renderer.entity.mob.paleozoic.layers;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic.ArthropleuraHeadModel;
import com.barlinc.unusual_prehistory.client.renderer.entity.layers.RiderLayer;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.Arthropleura;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ArthropleuraRiderLayer extends RiderLayer<Arthropleura, ArthropleuraHeadModel> {

    public ArthropleuraRiderLayer(RenderLayerParent<Arthropleura, ArthropleuraHeadModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Arthropleura entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        float bodyYaw = entity.yRotO + (entity.getYRot() - entity.yRotO) * partialTicks;
        if (entity.isVehicle()) {
            for (Entity passenger : entity.getPassengers()) {
                if (passenger == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                    continue;
                }
                UnusualPrehistory2.PROXY.releaseRenderingEntity(passenger.getUUID());
                poseStack.pushPose();
                this.getParentModel().translateRiderToBody(poseStack);
                double yOffset = passenger instanceof Player ? 0.25D : -0.25D;
                poseStack.translate(0.0D, yOffset, 0.25D);
                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                poseStack.mulPose(Axis.YN.rotationDegrees(360.0F - bodyYaw));
                passenger.setYBodyRot(entity.getYRot());
                RiderLayer.renderPassenger(passenger, 0, 0, 0, 0, partialTicks, poseStack, bufferSource, packedLight);
                poseStack.popPose();
                UnusualPrehistory2.PROXY.blockRenderingEntity(passenger.getUUID());
            }
        }
    }
}
