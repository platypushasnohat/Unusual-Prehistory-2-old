package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.DesmatosuchusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.DesmatosuchusDirtyLayer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Desmatosuchus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DesmatosuchusRenderer extends MobRenderer<Desmatosuchus, DesmatosuchusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/desmatosuchus/desmatosuchus.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/desmatosuchus/desmatosuchus_eepy.png");

    public DesmatosuchusRenderer(EntityRendererProvider.Context context) {
        super(context, new DesmatosuchusModel(context.bakeLayer(UP2ModelLayers.DESMATOSUCHUS)), 0.8F);
        this.addLayer(new DesmatosuchusDirtyLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Desmatosuchus entity) {
        return entity.isEepy() ? TEXTURE_EEPY : TEXTURE;
    }
}
