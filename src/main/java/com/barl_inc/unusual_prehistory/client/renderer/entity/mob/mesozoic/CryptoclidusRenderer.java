package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.CryptoclidusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.CryptoclidusGlowLayer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Cryptoclidus;
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
public class CryptoclidusRenderer extends MobRenderer<Cryptoclidus, CryptoclidusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/cryptoclidus/cryptoclidus.png");

    public CryptoclidusRenderer(EntityRendererProvider.Context context) {
        super(context, new CryptoclidusModel(context.bakeLayer(UP2ModelLayers.CRYPTOCLIDUS)), 0.5F);
        this.addLayer(new CryptoclidusGlowLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Cryptoclidus entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Cryptoclidus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}
