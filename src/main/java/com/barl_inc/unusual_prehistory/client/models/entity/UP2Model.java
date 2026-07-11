package com.barl_inc.unusual_prehistory.client.models.entity;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import org.joml.Vector3f;

import java.util.function.Function;

/* todo:
*   - Remove some redundant things like old animateIdle, animate head
* */

public abstract class UP2Model<E extends Entity> extends HierarchicalModel<E> {

    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    protected final float youngScaleFactor;
    protected final float bodyYOffset;

    protected float rotationX;
    protected float rotationY;

    public UP2Model(float youngScaleFactor, float youngBodyYoffset) {
        this(youngScaleFactor, youngBodyYoffset, RenderType::entityCutoutNoCull);
    }

    public UP2Model(float youngScaleFactor, float youngBodyYoffset, Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
        this.bodyYOffset = youngBodyYoffset;
        this.youngScaleFactor = youngScaleFactor;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, int color) {
        poseStack.pushPose();
        if (young) {
            poseStack.scale(youngScaleFactor, youngScaleFactor, youngScaleFactor);
            poseStack.translate(0.0F, bodyYOffset / 16.0F, 0.0F);
        }
        this.root().render(poseStack, consumer, packedLight, packedOverlay, color);
        poseStack.popPose();
    }

    protected void animateIdle(AnimationState animationState, AnimationDefinition definition, float ageInTicks, float limbSwingAmount) {
        this.animateIdle(animationState, definition, ageInTicks, 1, limbSwingAmount);
    }

    protected void animateIdle(AnimationState animationState, AnimationDefinition definition, float ageInTicks, float speed, float limbSwingAmount) {
        if (!animationState.isStarted()) {
            return;
        }
        float scale = Math.clamp(1 - Math.abs(limbSwingAmount), 0, 1);
        animationState.updateTime(ageInTicks, speed);
        KeyframeAnimations.animate(this, definition, animationState.getAccumulatedTime(), scale, UP2Model.ANIMATION_VECTOR_CACHE);
    }

    @Override
    protected void animate(AnimationState animationState, AnimationDefinition definition, float ageInTicks) {
        this.animate(animationState, definition, ageInTicks, 1.0F);
    }

    @Override
    protected void animateWalk(AnimationDefinition definition, float limbSwing, float limbSwingAmount, float maxAnimationSpeed, float animationScaleFactor) {
        if (limbSwingAmount < 0.01F || limbSwing < 0.01F) {
            return;
        }
        long i = (long) (limbSwing * 50.0F * maxAnimationSpeed);
        float f = Math.min(limbSwingAmount * animationScaleFactor, 1.0F);
        KeyframeAnimations.animate(this, definition, i, f, UP2Model.ANIMATION_VECTOR_CACHE);
    }

    protected void animate(AnimationState animationState, AnimationDefinition definition, float ageInTicks, float speed) {
        if (!animationState.isStarted()) {
            return;
        }
        animationState.updateTime(ageInTicks, speed);
        KeyframeAnimations.animate(this, definition, animationState.getAccumulatedTime(), 1.0F, UP2Model.ANIMATION_VECTOR_CACHE);
    }

    protected void animateIdleSmooth(SmoothAnimationState animationState, AnimationDefinition definition, float ageInTicks, float partialTicks, float limbSwingAmount) {
        if (!animationState.isActive(partialTicks)) {
            return;
        }
        animationState.animateIdle(this, definition, ageInTicks, partialTicks, limbSwingAmount, 1.5F, 1.0F);
    }

    protected void animateIdleSmooth(SmoothAnimationState animationState, AnimationDefinition definition, float ageInTicks, float partialTicks, float limbSwingAmount, float animationScaleFactor) {
        if (!animationState.isActive(partialTicks)) {
            return;
        }
        animationState.animateIdle(this, definition, ageInTicks, partialTicks, limbSwingAmount, animationScaleFactor, 1.0F);
    }

    protected void animateIdleSmooth(SmoothAnimationState animationState, AnimationDefinition definition, float ageInTicks, float partialTicks, float limbSwingAmount, float animationScaleFactor, float speed) {
        if (!animationState.isActive(partialTicks)) {
            return;
        }
        animationState.animateIdle(this, definition, ageInTicks, partialTicks, limbSwingAmount, animationScaleFactor, speed);
    }

    protected void animateSmooth(SmoothAnimationState animationState, AnimationDefinition definition, float ageInTicks, float partialTicks) {
        this.animateSmooth(animationState, definition, ageInTicks, partialTicks, 1.0F);
    }

    protected void animateSmooth(SmoothAnimationState animationState, AnimationDefinition definition, float ageInTicks, float partialTicks, float speed) {
        if (!animationState.isActive(partialTicks)) {
            return;
        }
        animationState.animate(this, definition, ageInTicks, partialTicks, speed);
    }

    @Override
    protected void applyStatic(AnimationDefinition definition) {
        KeyframeAnimations.animate(this, definition, 0L, 1.0F, UP2Model.ANIMATION_VECTOR_CACHE);
    }

    @Deprecated
    protected void animateHead(PrehistoricMob entity, ModelPart part, float netHeadYaw, float headPitch) {
        if (!entity.isEepy() && !entity.isSitting()) {
            part.xRot += headPitch * ((float) Math.PI / 180) / 2;
            part.yRot += netHeadYaw * ((float) Math.PI / 180) / 2;
        }
    }

    public void faceTarget(PrehistoricMob entity, float yaw, float pitch, float rotationDivisor, ModelPart... parts) {
        float actualRotationDivisor = rotationDivisor * parts.length;
        float yawAmount = yaw / (180.0F / (float) Math.PI) / actualRotationDivisor;
        float pitchAmount = pitch / (180.0F / (float) Math.PI) / actualRotationDivisor;
        if (!entity.isEepy() && !entity.isSitting()) {
            for (ModelPart part : parts) {
                part.yRot += yawAmount;
                part.xRot += pitchAmount;
            }
        }
    }

    public void setBodyRotation(float rotationX, float rotationY) {
        this.rotationX = rotationX;
        this.rotationY = rotationY;
    }

    public void look(ModelPart part, float netHeadYaw, float headPitch, float yawDivisor, float pitchDivisor) {
        part.yRot += (netHeadYaw * ((float) Math.PI / 180F)) / yawDivisor;
        part.xRot += (headPitch * ((float) Math.PI / 180F)) / pitchDivisor;
    }
}