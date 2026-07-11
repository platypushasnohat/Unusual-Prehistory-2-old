package com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.PrognathodonModel;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Prognathodon;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PrognathodonRenderer extends MobRenderer<Prognathodon, PrognathodonModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/prognathodon/prognathodon.png");

    public PrognathodonRenderer(EntityRendererProvider.Context context) {
        super(context, new PrognathodonModel(context.bakeLayer(UP2ModelLayers.PROGNATHODON)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Prognathodon entity) {
        return TEXTURE;
    }
}
