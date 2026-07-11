package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.GiantCampanileModel;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.GiantCampanile;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class GiantCampanileRenderer extends MobRenderer<GiantCampanile, GiantCampanileModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/giant_campanile.png");

    public GiantCampanileRenderer(EntityRendererProvider.Context context) {
        super(context, new GiantCampanileModel(context.bakeLayer(UP2ModelLayers.GIANT_CAMPANILE)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GiantCampanile entity) {
        return TEXTURE;
    }
}