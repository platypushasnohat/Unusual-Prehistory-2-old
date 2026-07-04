package com.barlinc.unusual_prehistory.mixins.minecraft;

import com.barlinc.unusual_prehistory.entity.mob.other.LivingOoze;
import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CarvedPumpkinBlock.class)
public final class CarvedPumpkinBlockMixin {

	@Inject(method = "canSpawnGolem", at = @At("RETURN"), cancellable = true)
	private void unusualPrehistory$canSpawnGolem(LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		BlockState belowState = level.getBlockState(pos.below());
		if (belowState.is(UP2Blocks.ORGANIC_OOZE_BLOCK)) {
            cir.setReturnValue(true);
        }
	}

	@Inject(method = "trySpawnGolem", at = @At("HEAD"), cancellable = true)
	private void unusualPrehistory$trySpawnGolem(Level level, BlockPos pos, CallbackInfo ci) {
		BlockState belowState = level.getBlockState(pos.below());
		if (belowState.is(UP2Blocks.ORGANIC_OOZE_BLOCK)) {
            LivingOoze.createLivingOoze(level, pos, level.getBlockState(pos));
			ci.cancel();
		}
	}
}