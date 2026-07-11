package com.barl_inc.unusual_prehistory.mixins.minecraft.client;

import com.barl_inc.unusual_prehistory.events.custom.PlayerPoseEvent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin extends Model {

    public HumanoidModelMixin(Function<ResourceLocation, RenderType> function) {
        super(function);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Unique
    private HumanoidModel<LivingEntity> unusualPrehistory$getHumanoidModel() {
        return ((HumanoidModel) (Object) this);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"), cancellable = true)
    public void unusualPrehistory$onSetupAnimations(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        PlayerPoseEvent event = new PlayerPoseEvent(entity, this.unusualPrehistory$getHumanoidModel());
        NeoForge.EVENT_BUS.post(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}