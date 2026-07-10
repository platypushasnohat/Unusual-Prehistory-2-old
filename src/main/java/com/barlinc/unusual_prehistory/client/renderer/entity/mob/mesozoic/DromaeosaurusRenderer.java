package com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.DromaeosaurusModel;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Dromaeosaurus;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DromaeosaurusRenderer extends MobRenderer<Dromaeosaurus, DromaeosaurusModel> {

    public DromaeosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new DromaeosaurusModel(context.bakeLayer(UP2ModelLayers.DROMAEOSAURUS)), 0.4F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Dromaeosaurus entity) {
        return entity.getVariantTexture();
    }
}