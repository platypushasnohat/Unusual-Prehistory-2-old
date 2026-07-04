package com.barlinc.unusual_prehistory.mixins.minecraft.client;

import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Ichthyosaurus;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Ulughbegsaurus;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.Arthropleura;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class CameraMixin {

    @Inject(method = "getMaxZoom", at = @At("RETURN"), cancellable = true)
    private void unusualPrehistory$modifyZoom(float maxZoom, CallbackInfoReturnable<Float> cir) {
        if (Minecraft.getInstance().getCameraEntity() instanceof Player player && player.isPassenger()) {
            float zoom = 4.0F;
            if (player.getVehicle() instanceof Ulughbegsaurus) {
                cir.setReturnValue(zoom + 1.25F);
            }
            if (player.getVehicle() instanceof Ichthyosaurus) {
                cir.setReturnValue(zoom + 1.1F);
            }
            if (player.getVehicle() instanceof Arthropleura) {
                cir.setReturnValue(zoom + 1.5F);
            }
        }
    }
}