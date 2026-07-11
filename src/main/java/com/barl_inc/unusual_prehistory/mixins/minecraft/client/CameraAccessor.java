package com.barl_inc.unusual_prehistory.mixins.minecraft.client;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Camera.class)
public interface CameraAccessor {

    @Invoker("move")
    void invokeMove(float zoom, float dy, float dx);

    @Invoker("getMaxZoom")
    float invokeGetMaxZoom(float maxZoom);
}
