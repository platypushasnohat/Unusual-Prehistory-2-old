package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.KentrosaurusModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Kentrosaurus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class KentrosaurusRenderer extends MobRenderer<Kentrosaurus, KentrosaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/kentrosaurus/kentrosaurus.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/kentrosaurus/kentrosaurus_eepy.png");

    public KentrosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new KentrosaurusModel(context.bakeLayer(UP2ModelLayers.KENTROSAURUS)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Kentrosaurus entity) {
        return entity.isEepy() ? TEXTURE_EEPY : TEXTURE;
    }
}
