package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class SurfaceSwimGoal extends Goal {

    protected final PrehistoricMob mob;
    protected final double surfaceThreshold;

    public SurfaceSwimGoal(PrehistoricMob mob, double surfaceThreshold) {
        this.mob = mob;
        this.surfaceThreshold = surfaceThreshold;
        this.mob.getNavigation().setCanFloat(true);
    }

    @Override
    public boolean canUse() {
        return mob.isInWater() || mob.isInLava();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void tick() {
        Vec3 motion = mob.getDeltaMovement();
        double fluidHeight = mob.getFluidHeight(FluidTags.WATER);
        double y = motion.y;
        if (fluidHeight > surfaceThreshold) {
            if (y < -0.02D) {
                y *= 0.3F;
            }
            else if (fluidHeight > surfaceThreshold + 0.1D) {
                y += motion.y < 0.12D ? 0.03D : 0.0D;
            }
            else if (y < 0.0D) {
                y = 0.0D;
            }
        }
        this.mob.setDeltaMovement(motion.x, y, motion.z);
    }
}
