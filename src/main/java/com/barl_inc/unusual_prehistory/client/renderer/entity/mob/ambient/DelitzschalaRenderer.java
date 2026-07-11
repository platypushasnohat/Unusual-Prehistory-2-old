package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.ambient;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.ambient.DelitzschalaModel;
import com.barl_inc.unusual_prehistory.entity.mob.ambient.Delitzschala;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DelitzschalaRenderer extends MobRenderer<Delitzschala, DelitzschalaModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/ambient/delitzschala.png");

    public DelitzschalaRenderer(EntityRendererProvider.Context context) {
        super(context, new DelitzschalaModel(context.bakeLayer(UP2ModelLayers.DELITZSCHALA)), 0.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Delitzschala entity) {
        return TEXTURE;
    }
}
