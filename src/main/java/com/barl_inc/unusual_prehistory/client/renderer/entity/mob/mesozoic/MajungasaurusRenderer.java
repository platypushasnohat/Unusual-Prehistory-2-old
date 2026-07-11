package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.MajungasaurusModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.MajungasaurusAngryEmissiveLayer;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.MajungasaurusAngryLayer;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Majungasaurus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
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
public class MajungasaurusRenderer extends MobRenderer<Majungasaurus, MajungasaurusModel> {

    public MajungasaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new MajungasaurusModel(context.bakeLayer(UP2ModelLayers.MAJUNGASAURUS)), 0.8F);
        this.addLayer(new MajungasaurusAngryLayer(this));
        this.addLayer(new MajungasaurusAngryEmissiveLayer(this));
    }

    @Override
    public void render(Majungasaurus entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        if (entity.isInvisible()) return;
        this.shadowRadius = entity.getCamoProgress(partialTicks) > 0.0F ? 0.0F : 0.8F;
        float alpha = 1.0F - entity.getCamoProgress(partialTicks);
        if (alpha > 0) super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Majungasaurus entity) {
        Majungasaurus.MajungasaurusVariant variant = Majungasaurus.MajungasaurusVariant.byId(entity.getVariant().getId());
        return UnusualPrehistory2.modPrefix("textures/entity/mob/majungasaurus/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }

    @Override
    protected @Nullable RenderType getRenderType(Majungasaurus entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        if (entity.getCamoProgress(1.0F) > 0.0F) {
            return RenderType.entityTranslucent(this.getTextureLocation(entity));
        } else {
            return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
        }
    }

    @Override
    protected void scale(Majungasaurus entity, @NotNull PoseStack poseStack, float partialTicks) {
        float alpha = 1.0F - entity.getCamoProgress(partialTicks);
        this.model.setAlpha(alpha);
    }
}