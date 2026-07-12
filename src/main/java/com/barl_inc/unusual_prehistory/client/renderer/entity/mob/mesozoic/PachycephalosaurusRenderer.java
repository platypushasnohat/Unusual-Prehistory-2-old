package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.PachycephalosaurusModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Pachycephalosaurus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PachycephalosaurusRenderer extends MobRenderer<Pachycephalosaurus, PachycephalosaurusModel> {

    public PachycephalosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new PachycephalosaurusModel(context.bakeLayer(UP2ModelLayers.PACHYCEPHALOSAURUS)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Pachycephalosaurus entity) {
        return entity.getVariantTexture();
    }
}
