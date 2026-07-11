package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.HibbertopterusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic.layers.HibbertopterusRiderLayer;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Hibbertopterus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class HibbertopterusRenderer extends MobRenderer<Hibbertopterus, HibbertopterusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/hibbertopterus.png");

    public HibbertopterusRenderer(EntityRendererProvider.Context context) {
        super(context, new HibbertopterusModel(context.bakeLayer(UP2ModelLayers.HIBBERTOPTERUS)), 1.2F);
        this.addLayer(new HibbertopterusRiderLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Hibbertopterus entity) {
        return TEXTURE;
    }
}