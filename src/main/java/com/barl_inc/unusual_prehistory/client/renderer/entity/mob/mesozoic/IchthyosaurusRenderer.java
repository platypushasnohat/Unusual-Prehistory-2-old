package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.IchthyosaurusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.layers.CustomPlayerRidePose;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.IchthyosaurusRiderLayer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Ichthyosaurus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class IchthyosaurusRenderer extends MobRenderer<Ichthyosaurus, IchthyosaurusModel> implements CustomPlayerRidePose {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/ichthyosaurus.png");

    public IchthyosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new IchthyosaurusModel(context.bakeLayer(UP2ModelLayers.ICHTHYOSAURUS)), 0.5F);
        this.addLayer(new IchthyosaurusRiderLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Ichthyosaurus entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Ichthyosaurus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}
