package com.barl_inc.unusual_prehistory.mixins.minecraft;

import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Desmatosuchus;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Snowball.class)
public class SnowballMixin {

    @Inject(at = @At("TAIL"), method = "onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V")
    protected void unusualPrehistory$onHitEntity(EntityHitResult result, CallbackInfo ci) {
        Entity entity = result.getEntity();
        if (entity instanceof Desmatosuchus desmatosuchus && !desmatosuchus.isDirty()) {
            desmatosuchus.setDirtType(Desmatosuchus.DirtType.SNOWY);
        }
    }
}
