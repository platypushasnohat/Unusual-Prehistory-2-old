package com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.AnurognathusModel;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.AnurognathusGlowLayer;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Anurognathus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class AnurognathusRenderer extends MobRenderer<Anurognathus, AnurognathusModel> {

    public AnurognathusRenderer(EntityRendererProvider.Context context) {
        super(context, new AnurognathusModel(context.bakeLayer(UP2ModelLayers.ANUROGNATHUS)), 0.5F);
        this.addLayer(new AnurognathusGlowLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Anurognathus entity) {
        Anurognathus.AnurognathusVariant variant = Anurognathus.AnurognathusVariant.byId(entity.getVariant().getId());
        return UnusualPrehistory2.modPrefix("textures/entity/mob/anurognathus/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}
