package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.SpikeToothedSalmonModel;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.SpikeToothedSalmon;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpikeToothedSalmonRenderer extends MobRenderer<SpikeToothedSalmon, SpikeToothedSalmonModel> {

    public SpikeToothedSalmonRenderer(EntityRendererProvider.Context context) {
        super(context, new SpikeToothedSalmonModel(context.bakeLayer(UP2ModelLayers.SPIKE_TOOTHED_SALMON)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(SpikeToothedSalmon entity) {
        return entity.getVariantTexture();
    }
}
