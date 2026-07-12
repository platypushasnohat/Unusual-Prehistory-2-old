package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.CarnotaurusModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Carnotaurus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CarnotaurusRenderer extends MobRenderer<Carnotaurus, CarnotaurusModel> {

    public CarnotaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new CarnotaurusModel(context.bakeLayer(UP2ModelLayers.CARNOTAURUS)), 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(Carnotaurus entity) {
        return entity.getVariantTexture();
    }
}