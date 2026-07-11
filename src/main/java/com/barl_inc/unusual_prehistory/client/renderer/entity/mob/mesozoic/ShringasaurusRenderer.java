package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.ShringasaurusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.ShringasaurusGlowLayer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Shringasaurus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ShringasaurusRenderer extends MobRenderer<Shringasaurus, ShringasaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/shringasaurus/shringasaurus.png");

    public ShringasaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new ShringasaurusModel(context.bakeLayer(UP2ModelLayers.SHRINGASAURUS)), 1.0F);
        this.addLayer(new ShringasaurusGlowLayer(this));
    }

    @Override
    protected void scale(Shringasaurus entity, PoseStack poseStack, float partialTicks) {
        int size = entity.getShringasaurusSize();
        float scale = 1.0F + 0.1F * (float) size;
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Shringasaurus entity) {
        return TEXTURE;
    }
}
