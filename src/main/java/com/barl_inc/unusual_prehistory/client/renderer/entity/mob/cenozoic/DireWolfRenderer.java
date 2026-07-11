package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.DireWolfModel;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.DireWolf;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DireWolfRenderer extends MobRenderer<DireWolf, DireWolfModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/dire_wolf.png");

    public DireWolfRenderer(EntityRendererProvider.Context context) {
        super(context, new DireWolfModel(context.bakeLayer(UP2ModelLayers.DIRE_WOLF)), 0.4F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull DireWolf entity) {
        return TEXTURE;
    }
}
