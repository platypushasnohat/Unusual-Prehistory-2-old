package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.AustroraptorModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.AustroraptorHeldItemLayer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Austroraptor;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class AustroraptorRenderer extends MobRenderer<Austroraptor, AustroraptorModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/austroraptor.png");

    public AustroraptorRenderer(EntityRendererProvider.Context context) {
        super(context, new AustroraptorModel(context.bakeLayer(UP2ModelLayers.AUSTRORAPTOR)), 0.5F);
        this.addLayer(new AustroraptorHeldItemLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Austroraptor entity) {
        return TEXTURE;
    }
}