package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.TalpanasModel;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Talpanas;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TalpanasRenderer extends MobRenderer<Talpanas, TalpanasModel> {

    public TalpanasRenderer(EntityRendererProvider.Context context) {
        super(context, new TalpanasModel(context.bakeLayer(UP2ModelLayers.TALPANAS)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(Talpanas entity) {
        return entity.getVariantTexture();
    }
}
