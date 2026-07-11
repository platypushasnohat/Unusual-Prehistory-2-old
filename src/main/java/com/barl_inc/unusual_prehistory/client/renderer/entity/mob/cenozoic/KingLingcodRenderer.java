package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.KingLingcodModel;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.KingLingcod;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class KingLingcodRenderer extends MobRenderer<KingLingcod, KingLingcodModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/lingcod/king_lingcod.png");

    public KingLingcodRenderer(EntityRendererProvider.Context context) {
        super(context, new KingLingcodModel(context.bakeLayer(UP2ModelLayers.KING_LINGCOD)), 0.4F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull KingLingcod entity) {
        return TEXTURE;
    }
}
