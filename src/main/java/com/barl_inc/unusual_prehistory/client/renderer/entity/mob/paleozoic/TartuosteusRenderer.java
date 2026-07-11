package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.TartuosteusModel;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Tartuosteus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class TartuosteusRenderer extends MobRenderer<Tartuosteus, TartuosteusModel> {

    public TartuosteusRenderer(EntityRendererProvider.Context context) {
        super(context, new TartuosteusModel(context.bakeLayer(UP2ModelLayers.TARTUOSTEUS)), 0.7F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Tartuosteus entity) {
        Tartuosteus.TartuosteusVariant variant = Tartuosteus.TartuosteusVariant.byId(entity.getVariant().getId());
        return UnusualPrehistory2.modPrefix("textures/entity/mob/tartuosteus/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}