package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.other;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.other.UnicornModel;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.other.UnicornSkeletonModel;
import com.barl_inc.unusual_prehistory.entity.mob.other.Unicorn;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class UnicornRenderer extends MobRenderer<Unicorn, HierarchicalModel<Unicorn>> {

    private final UnicornModel unicornModel;
    private final UnicornSkeletonModel unicornSkeletonModel;

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/unicorn/unicorn.png");
    private static final ResourceLocation TEXTURE_SKELETON = UnusualPrehistory2.modPrefix("textures/entity/mob/unicorn/unicorn_skeleton.png");

    public UnicornRenderer(EntityRendererProvider.Context context) {
        super(context, new UnicornModel(context.bakeLayer(UP2ModelLayers.UNICORN)), 0.9F);
        this.unicornModel = new UnicornModel(context.bakeLayer(UP2ModelLayers.UNICORN));
        this.unicornSkeletonModel = new UnicornSkeletonModel(context.bakeLayer(UP2ModelLayers.UNICORN_SKELETON));
    }

    @Override
    public void render(Unicorn entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        if (entity.isSkeletal()) {
            this.model = unicornSkeletonModel;
        } else {
            this.model = unicornModel;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Unicorn entity) {
        if (entity.isSkeletal()) {
            return TEXTURE_SKELETON;
        }
        return TEXTURE;
    }

    @Override
    @Nullable
    protected RenderType getRenderType(Unicorn entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        if (entity.isSkeletal()) {
            return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
        }
        return RenderType.entityCutout(this.getTextureLocation(entity));
    }
}
