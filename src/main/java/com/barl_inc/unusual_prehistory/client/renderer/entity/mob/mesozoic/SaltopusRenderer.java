package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.SaltopusModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Saltopus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SaltopusRenderer extends MobRenderer<Saltopus, SaltopusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/saltopus.png");

    public SaltopusRenderer(EntityRendererProvider.Context context) {
        super(context, new SaltopusModel(context.bakeLayer(UP2ModelLayers.SALTOPUS)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Saltopus entity) {
        return TEXTURE;
    }
}