package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.StethacanthusModel;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Stethacanthus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class StethacanthusRenderer extends MobRenderer<Stethacanthus, StethacanthusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/stethacanthus.png");

    public StethacanthusRenderer(EntityRendererProvider.Context context) {
        super(context, new StethacanthusModel(context.bakeLayer(UP2ModelLayers.STETHACANTHUS)), 0.4F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Stethacanthus entity) {
        return TEXTURE;
    }
}
