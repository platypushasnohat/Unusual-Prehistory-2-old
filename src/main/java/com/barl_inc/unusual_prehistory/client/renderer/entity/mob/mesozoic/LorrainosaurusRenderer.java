package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.LorrainosaurusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.LorrainosaurusHeldMobLayer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Lorrainosaurus;
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
public class LorrainosaurusRenderer extends MobRenderer<Lorrainosaurus, LorrainosaurusModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/lorrainosaurus.png");

    public LorrainosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new LorrainosaurusModel(context.bakeLayer(UP2ModelLayers.LORRAINOSAURUS)), 1.0F);
        this.addLayer(new LorrainosaurusHeldMobLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Lorrainosaurus entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Lorrainosaurus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}
