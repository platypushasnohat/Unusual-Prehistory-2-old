package com.barl_inc.unusual_prehistory.client.renderer.entity.layers;

import com.barl_inc.unusual_prehistory.client.models.entity.mob.other.LivingOozeModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.other.LivingOozeRenderer;
import com.barl_inc.unusual_prehistory.entity.mob.other.LivingOoze;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.utils.UP2LoadedMods;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class LivingOozeContainedItemLayer extends RenderLayer<LivingOoze, LivingOozeModel> {

    public LivingOozeContainedItemLayer(LivingOozeRenderer renderer) {
        super(renderer);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, @NotNull LivingOoze entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemstack = entity.getMainHandItem();
        if (!itemstack.isEmpty() && !entity.isInvisible() && !entity.isCurrentlyGlowing()) {
            poseStack.pushPose();
            this.translateItemInCore(poseStack, entity, partialTicks);
            Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), (int) entity.blockPosition().asLong());
            if (bufferSource instanceof MultiBufferSource.BufferSource source && !UP2LoadedMods.isOculusLoaded()) {
                source.endBatch();
            }
            poseStack.popPose();
        }
    }

    private void translateItemInCore(PoseStack poseStack, LivingOoze entity, float partialTicks) {
        float age = entity.tickCount + partialTicks;
        float yRot = age / 5;
        float yPos = entity.getPose() == UP2Poses.SPITTING.get() ? 0.0F : (float) (0.0F + (Math.sin(age * 0.07F) * 0.03F));
        float xScale = (this.getParentModel().core_squish.xScale * this.getParentModel().core.xScale) * 0.38F;
        float yScale = (this.getParentModel().core_squish.yScale * this.getParentModel().core.yScale) * 0.38F;
        float zScale = (this.getParentModel().core_squish.zScale * this.getParentModel().core.zScale) * 0.38F;
        this.getParentModel().translateToCore(poseStack);
        poseStack.translate(0.0F, yPos, 0.0F);
        poseStack.mulPose(new Quaternionf().rotateX(Mth.PI));
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot * (30F / (float) Math.PI)));
        poseStack.scale(xScale, yScale, zScale);
    }
}
