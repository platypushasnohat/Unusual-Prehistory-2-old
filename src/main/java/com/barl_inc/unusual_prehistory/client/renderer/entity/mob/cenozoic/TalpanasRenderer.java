package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.TalpanasModel;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Talpanas;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TalpanasRenderer extends MobRenderer<Talpanas, TalpanasModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/talpanas/talpanas.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/talpanas/talpanas_eepy.png");

    public TalpanasRenderer(EntityRendererProvider.Context context) {
        super(context, new TalpanasModel(context.bakeLayer(UP2ModelLayers.TALPANAS)), 0.3F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Talpanas entity) {
        return entity.isEepy() ? TEXTURE_EEPY : TEXTURE;
    }
}
