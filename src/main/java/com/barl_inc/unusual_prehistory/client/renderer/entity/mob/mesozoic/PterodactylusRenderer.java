package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.PterodactylusModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Pterodactylus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PterodactylusRenderer extends MobRenderer<Pterodactylus, PterodactylusModel> {

    public PterodactylusRenderer(EntityRendererProvider.Context context) {
        super(context, new PterodactylusModel(context.bakeLayer(UP2ModelLayers.PTERODACTYLUS)), 0.25F);
    }

    @Override
    public ResourceLocation getTextureLocation(Pterodactylus entity) {
        return entity.getVariantTexture();
    }
}
