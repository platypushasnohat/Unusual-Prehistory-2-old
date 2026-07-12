package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.UlughbegsaurusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.UlughbegsaurusRiderLayer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Ulughbegsaurus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UlughbegsaurusRenderer extends MobRenderer<Ulughbegsaurus, UlughbegsaurusModel> {

    public UlughbegsaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new UlughbegsaurusModel(context.bakeLayer(UP2ModelLayers.ULUGHBEGSAURUS)), 0.95F);
        this.addLayer(new UlughbegsaurusRiderLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Ulughbegsaurus entity) {
        return entity.getVariantTexture();
    }
}