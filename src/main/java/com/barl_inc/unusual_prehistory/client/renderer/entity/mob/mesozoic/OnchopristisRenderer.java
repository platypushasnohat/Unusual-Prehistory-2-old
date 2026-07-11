package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.OnchopristisModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Onchopristis;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class OnchopristisRenderer extends MobRenderer<Onchopristis, OnchopristisModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/onchopristis.png");

    public OnchopristisRenderer(EntityRendererProvider.Context context) {
        super(context, new OnchopristisModel(context.bakeLayer(UP2ModelLayers.ONCHOPRISTIS)), 0.7F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Onchopristis entity) {
        return TEXTURE;
    }
}
