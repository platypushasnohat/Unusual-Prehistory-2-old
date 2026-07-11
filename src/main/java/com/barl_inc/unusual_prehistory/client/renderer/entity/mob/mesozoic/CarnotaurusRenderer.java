package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.CarnotaurusModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Carnotaurus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class CarnotaurusRenderer extends MobRenderer<Carnotaurus, CarnotaurusModel> {

    public CarnotaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new CarnotaurusModel(context.bakeLayer(UP2ModelLayers.CARNOTAURUS)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Carnotaurus entity) {
        Carnotaurus.CarnotaurusVariant variant = Carnotaurus.CarnotaurusVariant.byId(entity.getVariant().getId());
        return UnusualPrehistory2.modPrefix("textures/entity/mob/carnotaurus/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}