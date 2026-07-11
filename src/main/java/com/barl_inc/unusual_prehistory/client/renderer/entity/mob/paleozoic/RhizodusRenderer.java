package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.RhizodusModel;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Rhizodus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RhizodusRenderer extends MobRenderer<Rhizodus, RhizodusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/rhizodus.png");

    public RhizodusRenderer(EntityRendererProvider.Context context) {
        super(context, new RhizodusModel(context.bakeLayer(UP2ModelLayers.RHIZODUS)), 1.0F);
    }

    @Override
    protected void scale(Rhizodus entity, PoseStack poseStack, float partialTicks) {
        int size = entity.getRhizodusSize();
        float scale = 1.0F + 0.05F * (float) size;
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Rhizodus entity) {
        return TEXTURE;
    }
}
