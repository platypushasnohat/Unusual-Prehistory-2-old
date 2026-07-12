package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.LeedsichthysBabyModel;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.LeedsichthysModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Leedsichthys;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LeedsichthysRenderer extends MobRenderer<Leedsichthys, UP2Model<Leedsichthys>> {

    private final LeedsichthysModel adultModel;
    private final LeedsichthysBabyModel babyModel;

    public LeedsichthysRenderer(EntityRendererProvider.Context context) {
        super(context, new LeedsichthysModel(context.bakeLayer(UP2ModelLayers.LEEDSICHTHYS)), 1.0F);
        this.adultModel = new LeedsichthysModel(context.bakeLayer(UP2ModelLayers.LEEDSICHTHYS));
        this.babyModel = new LeedsichthysBabyModel(context.bakeLayer(UP2ModelLayers.LEEDSICHTHYS_BABY));
    }

    @Override
    public void render(Leedsichthys entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.model = entity.isBaby() ? babyModel : adultModel;
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Leedsichthys entity) {
        return entity.isBaby() ? entity.getVariantBabyTexture() : entity.getVariantTexture();
    }
}
