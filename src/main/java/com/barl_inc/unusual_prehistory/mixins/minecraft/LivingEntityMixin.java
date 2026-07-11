package com.barl_inc.unusual_prehistory.mixins.minecraft;

import com.barl_inc.unusual_prehistory.entity.accessor.LivingEntityAccessor;
import com.barl_inc.unusual_prehistory.registry.UP2MobEffects;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityAccessor {

    @SuppressWarnings("WrongEntityDataParameterClass")
    @Unique
    private static final EntityDataAccessor<Boolean> DATA_GRABBED = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.BOOLEAN);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("TAIL"), method = "defineSynchedData")
    private void unusualPrehistory$defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_GRABBED, false);
    }

    @Override
    public boolean unusualPrehistory$isBeingGrabbed() {
        return this.entityData.get(DATA_GRABBED);
    }

    @Override
    public void unusualPrehistory$setBeingGrabbed(boolean grabbed) {
        this.entityData.set(DATA_GRABBED, grabbed);
    }

    // todo: change to sinew tag
    @Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
    private void unusualPrehistory$blockSprinting(boolean sprinting, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.hasEffect(UP2MobEffects.PARALYSIS) || entity.hasEffect(UP2MobEffects.DAZZLED)) {
            ci.cancel();
        }
    }

    // todo: change to sinew tag?
    @Inject(method = "canAttack(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At(value = "HEAD"), cancellable = true)
    protected void unusualPrehistory$canAttack(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.hasEffect(UP2MobEffects.DAZZLED) || entity.hasEffect(UP2MobEffects.TRANQUILITY)) {
            cir.setReturnValue(false);
        }
    }

//    @Inject(method = "onClimbable", at = @At("HEAD"), cancellable = true)
//    private void unusualPrehistory$brachiosaurusClimbing(CallbackInfoReturnable<Boolean> cir) {
//        LivingEntity entity = (LivingEntity) (Object) this;
//        if (entity instanceof Player player) {
//            AABB aabb = player.getBoundingBox().inflate(0.25D);
//            for (Entity nearby : player.level().getEntities(player, aabb)) {
//                if (player.horizontalCollision && (nearby instanceof Brachiosaurus || nearby instanceof BrachiosaurusPart)) {
//                    cir.setReturnValue(true);
//                    return;
//                }
//            }
//        }
//    }
}
