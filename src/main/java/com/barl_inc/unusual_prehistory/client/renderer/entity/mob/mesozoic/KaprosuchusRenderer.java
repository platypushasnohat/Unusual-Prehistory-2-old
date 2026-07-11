package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.KaprosuchusModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Kaprosuchus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class KaprosuchusRenderer extends MobRenderer<Kaprosuchus, KaprosuchusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/kaprosuchus/kaprosuchus.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/kaprosuchus/kaprosuchus_eepy.png");

    public KaprosuchusRenderer(EntityRendererProvider.Context context) {
        super(context, new KaprosuchusModel(context.bakeLayer(UP2ModelLayers.KAPROSUCHUS)), 0.6F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Kaprosuchus entity) {
        return entity.isEepy() ? TEXTURE_EEPY : TEXTURE;
    }
}