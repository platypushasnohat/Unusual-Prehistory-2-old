package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.CotylorhynchusModel;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Cotylorhynchus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CotylorhynchusRenderer extends MobRenderer<Cotylorhynchus, CotylorhynchusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/cotylorhynchus.png");

    public CotylorhynchusRenderer(EntityRendererProvider.Context context) {
        super(context, new CotylorhynchusModel(context.bakeLayer(UP2ModelLayers.COTYLORHYNCHUS)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Cotylorhynchus entity) {
        return TEXTURE;
    }
}
