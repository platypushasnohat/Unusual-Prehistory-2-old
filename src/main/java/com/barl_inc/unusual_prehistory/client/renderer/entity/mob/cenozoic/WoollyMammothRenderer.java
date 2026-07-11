package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.WoollyMammothModel;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.WoollyMammoth;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class WoollyMammothRenderer extends MobRenderer<WoollyMammoth, WoollyMammothModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/woolly_mammoth.png");

    public WoollyMammothRenderer(EntityRendererProvider.Context context) {
        super(context, new WoollyMammothModel(context.bakeLayer(UP2ModelLayers.WOOLLY_MAMMOTH)), 1.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull WoollyMammoth entity) {
        return TEXTURE;
    }
}
