package com.barlinc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic.ArthropleuraHeadModel;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.paleozoic.layers.ArthropleuraRiderLayer;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.Arthropleura;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class ArthropleuraRenderer extends MobRenderer<Arthropleura, ArthropleuraHeadModel> {

    public ArthropleuraRenderer(EntityRendererProvider.Context context) {
        super(context, new ArthropleuraHeadModel(context.bakeLayer(UP2ModelLayers.ARTHROPLEURA_HEAD)), 0.5F);
        this.addLayer(new ArthropleuraRiderLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Arthropleura entity) {
        Arthropleura.ArthropleuraVariant variant = Arthropleura.ArthropleuraVariant.byId(entity.getVariant().getId());
        return UnusualPrehistory2.modPrefix("textures/entity/mob/arthropleura/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }

    @Override
    protected float getFlipDegrees(@NotNull Arthropleura entity) {
        return 0.0F;
    }
}
