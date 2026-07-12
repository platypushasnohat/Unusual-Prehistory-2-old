package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.HesperornisModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Hesperornis;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HesperornisRenderer extends MobRenderer<Hesperornis, HesperornisModel> {

    public HesperornisRenderer(EntityRendererProvider.Context context) {
        super(context, new HesperornisModel(context.bakeLayer(UP2ModelLayers.HESPERORNIS)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Hesperornis entity) {
        return entity.getVariantTexture();
    }
}
