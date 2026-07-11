package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.AquilolamnaModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Aquilolamna;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class AquilolamnaRenderer extends MobRenderer<Aquilolamna, AquilolamnaModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/aquilolamna.png");

    public AquilolamnaRenderer(EntityRendererProvider.Context context) {
        super(context, new AquilolamnaModel(context.bakeLayer(UP2ModelLayers.AQUILOLAMNA)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Aquilolamna entity) {
        return TEXTURE;
    }
}
