package com.barl_inc.unusual_prehistory.mixins.minecraft.client;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends Player {

    public LocalPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    // Camera lag fix while riding a mount
    @ModifyReturnValue(method = "getViewYRot", at = @At("RETURN"))
    private float unusualPrehistory$onGetViewYRot(float original) {
        return this.getVehicle() instanceof PrehistoricMob ? this.getYRot() : original;
    }
}