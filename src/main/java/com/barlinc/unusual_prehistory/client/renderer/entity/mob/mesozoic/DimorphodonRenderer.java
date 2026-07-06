package com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.DimorphodonModel;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Dimorphodon;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class DimorphodonRenderer extends MobRenderer<Dimorphodon, DimorphodonModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/dimorphodon/dimorphodon.png");

    public DimorphodonRenderer(EntityRendererProvider.Context context) {
        super(context, new DimorphodonModel(context.bakeLayer(UP2ModelLayers.DIMORPHODON)), 0.4F);
    }

    @Override
    public void render(Dimorphodon entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        if (entity.getClimbDirection() != Direction.UP || (entity.prevClimbDirection != Direction.UP && entity.climbProgress > 0)) {
            float progress = entity.getClimbProgress(partialTicks);
            Direction dir = entity.getClimbDirection() != Direction.UP ? entity.getClimbDirection() : entity.prevClimbDirection;
            float offset = entity.getBbWidth() / 2 * progress;
            poseStack.translate(dir.getStepX() * offset, 0.5 * progress, dir.getStepZ() * offset);
            Direction dirRot = dir.getClockWise();
            poseStack.mulPose(Axis.of(new Vector3f(dirRot.getStepX(), 0, dirRot.getStepZ())).rotationDegrees(90 * progress));
        }
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(Dimorphodon entity) {
        return TEXTURE;
    }
}
