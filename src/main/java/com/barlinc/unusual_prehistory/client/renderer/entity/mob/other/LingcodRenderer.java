package com.barlinc.unusual_prehistory.client.renderer.entity.mob.other;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.other.LingcodModel;
import com.barlinc.unusual_prehistory.entity.mob.other.Lingcod;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LingcodRenderer extends MobRenderer<Lingcod, LingcodModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/lingcod/lingcod.png");

    public LingcodRenderer(EntityRendererProvider.Context context) {
        super(context, new LingcodModel(context.bakeLayer(UP2ModelLayers.LINGCOD)), 0.4F);
    }

    @Override
    protected void scale(@NotNull Lingcod entity, @NotNull PoseStack poseStack, float partialTicks) {
        super.scale(entity, poseStack, partialTicks);
        poseStack.scale(0.75F, 0.75F, 0.75F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Lingcod entity) {
        return TEXTURE;
    }
}
