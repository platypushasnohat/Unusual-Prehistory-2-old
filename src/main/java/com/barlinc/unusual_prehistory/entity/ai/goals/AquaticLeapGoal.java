package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.phys.Vec3;

public class AquaticLeapGoal extends JumpGoal {

    private static final int[] STEPS_TO_CHECK = new int[]{0, 1, 4, 5, 6, 7};
    protected final PrehistoricMob mob;
    protected final int interval;
    protected boolean breached;
    protected final double jumpDistance;
    protected final double jumpHeight;

    public AquaticLeapGoal(PrehistoricMob mob) {
        this(mob, 10, 0.6D, 0.7D);
    }

    public AquaticLeapGoal(PrehistoricMob mob, int interval, double jumpDistance, double jumpHeight) {
        this.mob = mob;
        this.interval = reducedTickDelay(interval);
        this.jumpDistance = jumpDistance;
        this.jumpHeight = jumpHeight;
    }

    @Override
    public boolean canUse() {
        if (this.mob.hasControllingPassenger()) {
            return false;
        } else if (this.mob.getRandom().nextInt(this.interval) != 0) {
            return false;
        } else {
            Direction direction = this.mob.getMotionDirection();
            int stepX = direction.getStepX();
            int stepZ = direction.getStepZ();
            BlockPos blockpos = this.mob.blockPosition();
            for (int steps : STEPS_TO_CHECK) {
                if (!this.waterIsClear(blockpos, stepX, stepZ, steps) || !this.surfaceIsClear(blockpos, stepX, stepZ, steps)) {
                    return false;
                }
            }
            return true;
        }
    }

    @SuppressWarnings("deprecation")
    private boolean waterIsClear(BlockPos pos, int x, int z, int scale) {
        BlockPos blockpos = pos.offset(x * scale, 0, z * scale);
        return this.mob.level().getFluidState(blockpos).is(FluidTags.WATER) && !this.mob.level().getBlockState(blockpos).blocksMotion();
    }

    private boolean surfaceIsClear(BlockPos pos, int x, int z, int scale) {
        return this.mob.level().getBlockState(pos.offset(x * scale, 1, z * scale)).isAir() && this.mob.level().getBlockState(pos.offset(x * scale, 2, z * scale)).isAir();
    }

    @Override
    public boolean canContinueToUse() {
        double y = this.mob.getDeltaMovement().y;
        return (!(y * y < (double) 0.03F) || !this.mob.isInWater()) && !this.mob.onGround() && ((LeapingMob) this.mob).isLeaping();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        this.breached = false;
        Direction direction = this.mob.getMotionDirection();
        this.mob.setDeltaMovement(this.mob.getDeltaMovement().add((double) direction.getStepX() * this.jumpDistance, this.jumpHeight, (double) direction.getStepZ() * this.jumpDistance));
        this.mob.getNavigation().stop();
        if (this.mob instanceof LeapingMob leapingMob) {
            leapingMob.setLeaping(true);
        }
    }

    @Override
    public void stop() {
        this.mob.setXRot(0.0F);
        if (this.mob instanceof LeapingMob leapingMob) {
            leapingMob.setLeaping(false);
        }
    }

    @Override
    public void tick() {
        if (!this.breached && !this.mob.isInWater()) {
            this.breached = true;
            this.mob.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        if (this.mob instanceof LeapingMob leapingMob && this.breached && this.mob.isInWater()) {
            leapingMob.setLeaping(false);
            this.breached = false;
        }

        Vec3 deltaMovement = this.mob.getDeltaMovement();
        if (deltaMovement.length() > 1.0E-5F) {
            this.mob.setYRot(((float) Mth.atan2(this.mob.getMotionDirection().getStepZ(), this.mob.getMotionDirection().getStepX())) * Mth.RAD_TO_DEG - 90F);
        }
    }
}