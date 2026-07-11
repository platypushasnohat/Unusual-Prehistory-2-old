package com.barl_inc.unusual_prehistory.client.renderer.entity.layers;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.other.LivingOozeModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.other.LivingOozeRenderer;
import com.barl_inc.unusual_prehistory.entity.mob.other.LivingOoze;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.utils.UP2ColorUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LivingOozeOuterLayer extends RenderLayer<LivingOoze, LivingOozeModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/living_ooze/normal.png");
    private static final ResourceLocation TEXTURE_GULPING = UnusualPrehistory2.modPrefix("textures/entity/mob/living_ooze/gulping.png");
    private static final ResourceLocation TEXTURE_SPITTING = UnusualPrehistory2.modPrefix("textures/entity/mob/living_ooze/spitting.png");
    private static final ResourceLocation TEXTURE_COOLDOWN = UnusualPrehistory2.modPrefix("textures/entity/mob/living_ooze/clarity.png");
    private static final ResourceLocation TEXTURE_SAD = UnusualPrehistory2.modPrefix("textures/entity/mob/living_ooze/sad.png");
    private static final ResourceLocation TEXTURE_SCREAMING = UnusualPrehistory2.modPrefix("textures/entity/mob/living_ooze/screaming.png");

    public LivingOozeOuterLayer(LivingOozeRenderer render) {
        super(render);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, @NotNull LivingOoze entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
        if (!entity.isInvisible()) {
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), UP2ColorUtils.packColor(1.0F, 1.0F, 1.0F, 1.0F));
        }
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull LivingOoze entity) {
        int spitTime = entity.getSpitTime();
        if (spitTime > 0 && entity.getPose() == UP2Poses.SPITTING.get()) {
            if (spitTime <= 6) return TEXTURE_SPITTING;
            if (spitTime <= 35) return TEXTURE_GULPING;
        }
        if (entity.getCooldown() > 0) return TEXTURE_COOLDOWN;
        if (entity.isAlive() && entity.getSadTime() > 0) return TEXTURE_SAD;
        if (!entity.isAlive()) return TEXTURE_SCREAMING;
        return TEXTURE;
    }
}