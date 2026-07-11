package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.TusoteuthisModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.TusoteuthisFlashLayer;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.TusoteuthisGlowLayer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Tusoteuthis;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TusoteuthisRenderer extends MobRenderer<Tusoteuthis, TusoteuthisModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/tusoteuthis/tusoteuthis.png");

    public TusoteuthisRenderer(EntityRendererProvider.Context context) {
        super(context, new TusoteuthisModel(context.bakeLayer(UP2ModelLayers.TUSOTEUTHIS)), 1.0F);
        this.addLayer(new TusoteuthisGlowLayer(this));
        this.addLayer(new TusoteuthisFlashLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Tusoteuthis entity) {
        return TEXTURE;
    }

    @Override
    protected void setupRotations(@NotNull Tusoteuthis entity, @NotNull PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getSpinProgress(partialTicks)));
    }
}
