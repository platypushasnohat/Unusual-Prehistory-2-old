package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.ConcavenatorModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.ConcavenatorArmorLayer;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.ConcavenatorGlowLayer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Concavenator;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ConcavenatorRenderer extends MobRenderer<Concavenator, ConcavenatorModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/concavenator/concavenator.png");

    public ConcavenatorRenderer(EntityRendererProvider.Context context) {
        super(context, new ConcavenatorModel(context.bakeLayer(UP2ModelLayers.CONCAVENATOR)), 0.5F);
        this.addLayer(new ConcavenatorArmorLayer(this));
        this.addLayer(new ConcavenatorGlowLayer(this));
    }

    @Override
    protected void scale(@NotNull Concavenator entity, @NotNull PoseStack poseStack, float partialTicks) {
        super.scale(entity, poseStack, partialTicks);
        if (entity.isPackLeader()) {
            poseStack.scale(1.1F, 1.1F, 1.1F);
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Concavenator entity) {
        return TEXTURE;
    }
}
