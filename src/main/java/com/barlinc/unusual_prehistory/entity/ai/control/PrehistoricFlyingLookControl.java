package com.barlinc.unusual_prehistory.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

public class PrehistoricFlyingLookControl extends LookControl {

	private final int maxYRotFromCenter;
	
	public PrehistoricFlyingLookControl(Mob mob, int maxYRotFromCenter) {
		super(mob);
		this.maxYRotFromCenter = maxYRotFromCenter;
	}

	@Override
	public void tick() {
		if (lookAtCooldown > 0) {
			this.lookAtCooldown--;
			this.getYRotD().ifPresent(f -> mob.yHeadRot = this.rotateTowards(mob.yHeadRot, f + 40.0F, yMaxRotSpeed));
			this.getXRotD().ifPresent(f -> mob.setXRot(this.rotateTowards(mob.getXRot(), f + 2.0F, xMaxRotAngle)));
		} else {
			if (mob.getNavigation().isDone()) {
				this.mob.setXRot(this.rotateTowards(mob.getXRot(), 0.0F, 10.0F));
			}
			this.mob.yHeadRot = this.rotateTowards(mob.yHeadRot, mob.yBodyRot, yMaxRotSpeed);
		}
		float f = Mth.wrapDegrees(mob.yHeadRot - mob.yBodyRot);
		if (f < -maxYRotFromCenter) {
			this.mob.yBodyRot -= 8.0F;
		} else if (f > maxYRotFromCenter) {
			this.mob.yBodyRot += 8.0F;
		}
	}
}