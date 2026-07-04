package com.barlinc.unusual_prehistory.mixins.minecraft;

import com.barlinc.unusual_prehistory.entity.mob.paleozoic.Arthropleura;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.ArthropleuraPart;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "canCollideWith(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void unusualPrehistory$noCollisionWhileRiding(Entity entity1, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        if (this.unusualPrehistory$ridingArthropleura(entity) || this.unusualPrehistory$ridingArthropleura(entity1)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private boolean unusualPrehistory$ridingArthropleura(Entity entity) {
        return entity.getVehicle() instanceof Arthropleura || entity.getVehicle() instanceof ArthropleuraPart;
    }
}
