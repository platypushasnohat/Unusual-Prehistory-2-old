package com.barlinc.unusual_prehistory.client.renderer.entity.mob.other;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.other.GrugModel;
import com.barlinc.unusual_prehistory.entity.mob.other.Grug;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class GrugRenderer extends MobRenderer<Grug, GrugModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/grug.png");

    public GrugRenderer(EntityRendererProvider.Context context) {
        super(context, new GrugModel(context.bakeLayer(UP2ModelLayers.GRUG)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Grug entity) {
        return TEXTURE;
    }
}
