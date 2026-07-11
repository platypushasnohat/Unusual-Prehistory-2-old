package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.PraepusaModel;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Praepusa;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PraepusaRenderer extends MobRenderer<Praepusa, PraepusaModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/praepusa.png");

    public PraepusaRenderer(EntityRendererProvider.Context context) {
        super(context, new PraepusaModel(context.bakeLayer(UP2ModelLayers.PRAEPUSA)), 0.4F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Praepusa entity) {
        return TEXTURE;
    }
}