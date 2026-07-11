package com.barl_inc.unusual_prehistory.mixins.minecraft.client;

import com.barl_inc.unusual_prehistory.entity.mob.recently_extinct.GastricBroodingFrog;
import com.barl_inc.unusual_prehistory.events.custom.ModelRotationEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin extends EntityRenderer<LivingEntity> implements RenderLayerParent<LivingEntity, EntityModel<LivingEntity>> {

    @Shadow
    protected EntityModel<LivingEntity> model;

    protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = "setupRotations(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;FFFF)V", at = @At("HEAD"), cancellable = true)
    protected void unusualPrehistory$onSetupRotation(LivingEntity entity, PoseStack poseStack, float bob, float yBodyRot, float partialTicks, float scale, CallbackInfo ci) {
        ModelRotationEvent event = new ModelRotationEvent(entity, this.model, poseStack);
        NeoForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Redirect(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;shouldRiderSit()Z"))
    private boolean unusualPrehistory$shouldRiderSitRender(Entity vehicle) {
        for (Entity passenger : vehicle.getPassengers()) {
            if (passenger instanceof GastricBroodingFrog) {
                return false;
            }
        }
        return vehicle.shouldRiderSit();
    }
}