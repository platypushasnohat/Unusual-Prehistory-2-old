package com.barlinc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic.*;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.JawlessFish;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class JawlessFishRenderer extends MobRenderer<JawlessFish, UP2Model<JawlessFish>> {

    private final JawlessFishCephalaspisModel jawlessFishCephalaspisModel;
    private final JawlessFishDoryaspisModel jawlessFishDoryaspisModel;
    private final JawlessFishFurcacaudaModel jawlessFishFurcacaudaModel;
    private final JawlessFishSacabambaspisModel jawlessFishSacabambaspisModel;
    private final JawlessFishArandaspisModel arandaspisModel;

    public JawlessFishRenderer(EntityRendererProvider.Context context) {
        super(context, new JawlessFishCephalaspisModel(context.bakeLayer(UP2ModelLayers.JAWLESS_FISH_CEPHALASPIS)), 0.25F);
        this.jawlessFishCephalaspisModel = new JawlessFishCephalaspisModel(context.bakeLayer(UP2ModelLayers.JAWLESS_FISH_CEPHALASPIS));
        this.jawlessFishDoryaspisModel = new JawlessFishDoryaspisModel(context.bakeLayer(UP2ModelLayers.JAWLESS_FISH_DORYASPIS));
        this.jawlessFishFurcacaudaModel = new JawlessFishFurcacaudaModel(context.bakeLayer(UP2ModelLayers.JAWLESS_FISH_FURACACAUDA));
        this.jawlessFishSacabambaspisModel = new JawlessFishSacabambaspisModel(context.bakeLayer(UP2ModelLayers.JAWLESS_FISH_SACABAMBASPIS));
        this.arandaspisModel = new JawlessFishArandaspisModel(context.bakeLayer(UP2ModelLayers.JAWLESS_FISH_ARANDASPIS));
    }

    @Override
    public void render(JawlessFish entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        switch (entity.getVariant().getId()) {
            case 1:
                this.model = jawlessFishCephalaspisModel;
                break;
            case 2:
                this.model = jawlessFishDoryaspisModel;
                break;
            case 3:
                this.model = jawlessFishFurcacaudaModel;
                break;
            case 4:
                this.model = jawlessFishSacabambaspisModel;
                break;
            default:
                this.model = arandaspisModel;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(JawlessFish entity) {
        JawlessFish.JawlessFishVariant variant = JawlessFish.JawlessFishVariant.byId(entity.getVariant().getId());
        return UnusualPrehistory2.modPrefix("textures/entity/mob/jawless_fish/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }

    @Override
    @Nullable
    protected RenderType getRenderType(@NotNull JawlessFish entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}
