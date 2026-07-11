package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.CamerocerasModel;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Cameroceras;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CamerocerasRenderer extends MobRenderer<Cameroceras, CamerocerasModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/cameroceras.png");

    public CamerocerasRenderer(EntityRendererProvider.Context context) {
        super(context, new CamerocerasModel(context.bakeLayer(UP2ModelLayers.CAMEROCERAS)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Cameroceras entity) {
        return TEXTURE;
    }
}