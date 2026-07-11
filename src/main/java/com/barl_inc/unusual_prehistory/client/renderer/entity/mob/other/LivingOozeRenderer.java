package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.other;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.other.LivingOozeModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.layers.LivingOozeContainedItemLayer;
import com.barl_inc.unusual_prehistory.client.renderer.entity.layers.LivingOozeOuterLayer;
import com.barl_inc.unusual_prehistory.entity.mob.other.LivingOoze;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class LivingOozeRenderer extends MobRenderer<LivingOoze, LivingOozeModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/misc/empty.png");

    public LivingOozeRenderer(EntityRendererProvider.Context context) {
        super(context, new LivingOozeModel(context.bakeLayer(UP2ModelLayers.LIVING_OOZE)), 0.6F);
        this.addLayer(new LivingOozeContainedItemLayer(this));
        this.addLayer(new LivingOozeOuterLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LivingOoze entity) {
        return TEXTURE;
    }

    @Nullable
    @Override
    protected RenderType getRenderType(@NotNull LivingOoze entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityTranslucent(this.getTextureLocation(entity));
    }
}