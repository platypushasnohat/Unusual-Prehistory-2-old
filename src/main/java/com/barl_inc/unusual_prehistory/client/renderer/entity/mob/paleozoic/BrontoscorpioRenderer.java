package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.BrontoscorpioModel;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Brontoscorpio;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BrontoscorpioRenderer extends MobRenderer<Brontoscorpio, BrontoscorpioModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/brontoscorpio.png");

    public BrontoscorpioRenderer(EntityRendererProvider.Context context) {
        super(context, new BrontoscorpioModel(context.bakeLayer(UP2ModelLayers.BRONTOSCORPIO)), 0.3F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Brontoscorpio entity) {
        return TEXTURE;
    }
}