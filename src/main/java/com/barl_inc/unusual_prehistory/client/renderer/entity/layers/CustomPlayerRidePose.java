package com.barl_inc.unusual_prehistory.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public interface CustomPlayerRidePose {

    default void applyRiderPose(HumanoidModel<LivingEntity> humanoidModel, @NotNull LivingEntity rider) {
    }

    default void applyRiderMatrixStack(@NotNull Entity entity, @NotNull PoseStack poseStack) {
    }

    default float rad(float f) {
        return (float) Math.toRadians(f);
    }

}