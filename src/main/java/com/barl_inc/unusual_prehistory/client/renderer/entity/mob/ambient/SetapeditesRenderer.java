package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.ambient;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.ambient.SetapeditesModel;
import com.barl_inc.unusual_prehistory.entity.mob.ambient.Setapedites;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SetapeditesRenderer extends MobRenderer<Setapedites, SetapeditesModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/ambient/setapedites/pink.png");

    public SetapeditesRenderer(EntityRendererProvider.Context context) {
        super(context, new SetapeditesModel(context.bakeLayer(UP2ModelLayers.SETAPEDITES)), 0.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Setapedites entity) {
        return TEXTURE;
    }

    @Override
    protected int getBlockLightLevel(@NotNull Setapedites entity, @NotNull BlockPos pos) {
        return 15;
    }
}
