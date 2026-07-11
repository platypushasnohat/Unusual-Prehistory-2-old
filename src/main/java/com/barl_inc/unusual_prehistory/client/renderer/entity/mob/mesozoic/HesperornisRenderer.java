package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.HesperornisModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Hesperornis;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class HesperornisRenderer extends MobRenderer<Hesperornis, HesperornisModel> {

    public HesperornisRenderer(EntityRendererProvider.Context context) {
        super(context, new HesperornisModel(context.bakeLayer(UP2ModelLayers.HESPERORNIS)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Hesperornis entity) {
        Hesperornis.HesperornisVariant variant = Hesperornis.HesperornisVariant.byId(entity.getVariant().getId());
        return UnusualPrehistory2.modPrefix("textures/entity/mob/hesperornis/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}
