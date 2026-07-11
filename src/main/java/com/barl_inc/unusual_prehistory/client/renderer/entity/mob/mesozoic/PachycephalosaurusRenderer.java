package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.PachycephalosaurusModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Pachycephalosaurus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class PachycephalosaurusRenderer extends MobRenderer<Pachycephalosaurus, PachycephalosaurusModel> {

    public PachycephalosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new PachycephalosaurusModel(context.bakeLayer(UP2ModelLayers.PACHYCEPHALOSAURUS)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Pachycephalosaurus entity) {
        Pachycephalosaurus.PachycephalosaurusVariant variant = Pachycephalosaurus.PachycephalosaurusVariant.byId(entity.getVariant().getId());
        if (entity.isEepy()) {
            return UnusualPrehistory2.modPrefix("textures/entity/mob/pachycephalosaurus/" + variant.name().toLowerCase(Locale.ROOT) + "_eepy.png");
        }
        return UnusualPrehistory2.modPrefix("textures/entity/mob/pachycephalosaurus/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}
