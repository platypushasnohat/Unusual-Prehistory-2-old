package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.multi_era;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.multi_era.LystrosaurusModel;
import com.barl_inc.unusual_prehistory.entity.mob.multi_era.Lystrosaurus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LystrosaurusRenderer extends MobRenderer<Lystrosaurus, LystrosaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/lystrosaurus.png");

    public LystrosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new LystrosaurusModel(context.bakeLayer(UP2ModelLayers.LYSTROSAURUS)), 0.7F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Lystrosaurus entity) {
        return TEXTURE;
    }
}
