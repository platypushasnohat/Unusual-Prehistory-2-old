package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.recently_extinct;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.recently_extinct.GastricBroodingFrogModel;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.recently_extinct.GastricBroodingFrogletModel;
import com.barl_inc.unusual_prehistory.entity.mob.recently_extinct.GastricBroodingFrog;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class GastricBroodingFrogRenderer extends MobRenderer<GastricBroodingFrog, UP2Model<GastricBroodingFrog>> {

    private final GastricBroodingFrogModel adultModel;
    private final GastricBroodingFrogletModel babyModel;

    public GastricBroodingFrogRenderer(EntityRendererProvider.Context context) {
        super(context, new GastricBroodingFrogModel(context.bakeLayer(UP2ModelLayers.GASTRIC_BROODING_FROG)), 0.3F);
        this.adultModel = new GastricBroodingFrogModel(context.bakeLayer(UP2ModelLayers.GASTRIC_BROODING_FROG));
        this.babyModel = new GastricBroodingFrogletModel(context.bakeLayer(UP2ModelLayers.GASTRIC_BROODING_FROGLET));
    }

    @Override
    public void render(GastricBroodingFrog entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        this.model = entity.isBaby() ? babyModel : adultModel;
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GastricBroodingFrog entity) {
        GastricBroodingFrog.GastricBroodingFrogVariant variant = GastricBroodingFrog.GastricBroodingFrogVariant.byId(entity.getVariant().getId());
        if (entity.isBaby()) {
            return UnusualPrehistory2.modPrefix("textures/entity/mob/gastric_brooding_frog/" + variant.name().toLowerCase(Locale.ROOT) + "_froglet.png");
        }
        return UnusualPrehistory2.modPrefix("textures/entity/mob/gastric_brooding_frog/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}
