package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.NihohaeModel;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Nihohae;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NihohaeRenderer extends MobRenderer<Nihohae, NihohaeModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/nihohae/nihohae.png");

    public NihohaeRenderer(EntityRendererProvider.Context context) {
        super(context, new NihohaeModel(context.bakeLayer(UP2ModelLayers.NIHOHAE)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Nihohae entity) {
        return TEXTURE;
    }
}
