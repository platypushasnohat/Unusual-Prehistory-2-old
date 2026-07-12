package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.PsilopterusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic.layers.PsilopterusHeldItemLayer;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Psilopterus;
import com.barl_inc.unusual_prehistory.entity.variant.UP2MobVariant;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PsilopterusRenderer extends MobRenderer<Psilopterus, PsilopterusModel> {

    public PsilopterusRenderer(EntityRendererProvider.Context context) {
        super(context, new PsilopterusModel(context.bakeLayer(UP2ModelLayers.PSILOPTERUS)), 0.4F);
        this.addLayer(new PsilopterusHeldItemLayer(this));
    }

    @Override
    protected void scale(@NotNull Psilopterus entity, @NotNull PoseStack poseStack, float partialTicks) {
        super.scale(entity, poseStack, partialTicks);
        if (entity.isPackLeader()) {
            poseStack.scale(1.1F, 1.1F, 1.1F);
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Psilopterus entity) {
        if (entity.isPackLeader()) {
            return UP2MobVariant.fullTextureId(entity.getVariantTextureRaw().withPath(path -> path + "_pack_leader"));
        }
        return entity.getVariantTexture();
    }
}
