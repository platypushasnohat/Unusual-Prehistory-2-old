package com.barl_inc.unusual_prehistory.mixins.minecraft;

import com.barl_inc.unusual_prehistory.entity.accessor.MobAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Mob.class)
public abstract class MobMixin implements MobAccessor {

    @Shadow
    protected abstract void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean playerKill);

    @Override
    public void unusualPrehistory$dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean playerKill) {
        this.dropCustomDeathLoot(serverLevel, damageSource, playerKill);
    }
}