package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.LeptictidiumModel;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Leptictidium;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LeptictidiumRenderer extends MobRenderer<Leptictidium, LeptictidiumModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/leptictidium.png");

    public LeptictidiumRenderer(EntityRendererProvider.Context context) {
        super(context, new LeptictidiumModel(context.bakeLayer(UP2ModelLayers.LEPTICTIDIUM)), 0.2F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Leptictidium entity) {
        return TEXTURE;
    }
}
