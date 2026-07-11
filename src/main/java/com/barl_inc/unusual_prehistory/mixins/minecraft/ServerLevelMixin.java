package com.barl_inc.unusual_prehistory.mixins.minecraft;

import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Brontoscorpio;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Inject(method = "findLightningTargetAround", at = @At("HEAD"), cancellable = true)
    private void unusualPrehistory$redirectLightning(BlockPos blockPos, CallbackInfoReturnable<BlockPos> cir) {
        ServerLevel level = (ServerLevel) (Object) this;
        AABB aabb = new AABB(blockPos).inflate(64);
        List<Brontoscorpio> evilScorpions = level.getEntitiesOfClass(Brontoscorpio.class, aabb, mob -> mob.isAlive() && level.canSeeSky(mob.blockPosition()) && !mob.isInWater());
        if (!evilScorpions.isEmpty() && level.getRandom().nextFloat() < 0.25F) {
            Brontoscorpio target = evilScorpions.get(level.getRandom().nextInt(evilScorpions.size()));
            cir.setReturnValue(target.blockPosition().above());
        }
    }
}
