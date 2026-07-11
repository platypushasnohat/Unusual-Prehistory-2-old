package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.PsilopterusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic.layers.PsilopterusHeldItemLayer;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Psilopterus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PsilopterusRenderer extends MobRenderer<Psilopterus, PsilopterusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/psilopterus/psilopterus.png");
    private static final ResourceLocation TEXTURE_ALPHA = UnusualPrehistory2.modPrefix("textures/entity/mob/psilopterus/psilopterus_alpha.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/psilopterus/psilopterus_eepy.png");
    private static final ResourceLocation TEXTURE_ALPHA_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/psilopterus/psilopterus_alpha_eepy.png");

    public PsilopterusRenderer(EntityRendererProvider.Context context) {
        super(context, new PsilopterusModel(context.bakeLayer(UP2ModelLayers.PSILOPTERUS)), 0.4F);
        this.addLayer(new PsilopterusHeldItemLayer(this));
    }

    @Override
    protected void scale(@NotNull Psilopterus entity, @NotNull PoseStack poseStack, float partialTicks) {
        super.scale(entity, poseStack, partialTicks);
        if (entity.isPackLeader()) {
            poseStack.scale(1.1F, 1.1F, 1.1F);
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Psilopterus entity) {
        if (entity.isPackLeader()) return entity.isEepy() ? TEXTURE_ALPHA_EEPY : TEXTURE_ALPHA;
        return entity.isEepy() ? TEXTURE_EEPY : TEXTURE;
    }
}
