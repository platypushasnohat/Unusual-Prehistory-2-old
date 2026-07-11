package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.AegirocassisBabyModel;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.AegirocassisModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic.layers.AegirocassisGlowLayer;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Aegirocassis;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AegirocassisRenderer extends MobRenderer<Aegirocassis, UP2Model<Aegirocassis>> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/aegirocassis/aegirocassis.png");
    private static final ResourceLocation TEXTURE_BABY = UnusualPrehistory2.modPrefix("textures/entity/mob/aegirocassis/aegirocassis_baby.png");

    private final AegirocassisModel adultModel;
    private final AegirocassisBabyModel babyModel;

    public AegirocassisRenderer(EntityRendererProvider.Context context) {
        super(context, new AegirocassisModel(context.bakeLayer(UP2ModelLayers.AEGIROCASSIS)), 1.5F);
        this.adultModel = new AegirocassisModel(context.bakeLayer(UP2ModelLayers.AEGIROCASSIS));
        this.babyModel = new AegirocassisBabyModel(context.bakeLayer(UP2ModelLayers.AEGIROCASSIS_BABY));
        this.addLayer(new AegirocassisGlowLayer(this));
    }

    @Override
    public void render(Aegirocassis entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.model = entity.isBaby() ? babyModel : adultModel;
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    protected float getFlipDegrees(Aegirocassis entity) {
        return !entity.isInWaterOrBubble() ? 0.0F : super.getFlipDegrees(entity);
    }

    @Override
    public ResourceLocation getTextureLocation(Aegirocassis entity) {
        return entity.isBaby() ? TEXTURE_BABY : TEXTURE;
    }
}
