package com.barl_inc.unusual_prehistory.mixins.minecraft;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LandRandomPos.class)
public class LandRandomPosMixin {

	// Based on fix by https://github.com/Favouriteless/StupidCowStopClimbing
	@ModifyVariable(method = "generateRandomPosTowardDirection", at = @At("STORE"), ordinal = 1)
	private static BlockPos unusualPrehistory$fixMC265474(BlockPos blockPos, PathfinderMob mob, int radius, boolean shortCircuit, BlockPos blockPos1) {
		// Should fix MC-265474 https://bugs.mojang.com/browse/MC/issues/MC-265474
		// (might be a fair bit more expensive though and there is probably a better way, but this works for now)
		if (mob instanceof PrehistoricMob prehistoricMob) {
			MutableBlockPos mutable = blockPos.mutable();
			for (int i = 0; i < radius; i++) {
				if (!GoalUtils.isNotStable(prehistoricMob.getNavigation(), mutable)) {
					return mutable;
				}
				mutable.move(0, -1, 0);
			}
		}
		return blockPos;
	}
}