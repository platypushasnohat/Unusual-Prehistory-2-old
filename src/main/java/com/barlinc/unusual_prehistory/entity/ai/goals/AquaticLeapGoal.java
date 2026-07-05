package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class AquaticLeapGoal extends JumpGoal {

    private static final int[] STEPS_TO_CHECK = new int[]{0, 1, 4, 5, 6, 7};
    protected final PrehistoricMob prehistoricMob;
    protected final int interval;
    protected boolean breached;
    protected final double jumpDistance;
    protected final double jumpHeight;

    public AquaticLeapGoal(PrehistoricMob prehistoricMob) {
        this(prehistoricMob, 10, 0.6D, 0.7D);
    }

    public AquaticLeapGoal(PrehistoricMob prehistoricMob, int interval, double jumpDistance, double jumpHeight) {
        this.prehistoricMob = prehistoricMob;
        this.interval = reducedTickDelay(interval);
        this.jumpDistance = jumpDistance;
        this.jumpHeight = jumpHeight;
    }

    @Override
    public boolean canUse() {
        if (prehistoricMob.hasControllingPassenger()) {
            return false;
        } else if (prehistoricMob.getRandom().nextInt(interval) != 0) {
            return false;
        } else {
            Direction direction = prehistoricMob.getMotionDirection();
            int i = direction.getStepX();
            int j = direction.getStepZ();
            BlockPos blockpos = prehistoricMob.blockPosition();
            for (int k : STEPS_TO_CHECK) {
                if (!this.waterIsClear(blockpos, i, j, k) || !this.surfaceIsClear(blockpos, i, j, k)) {
                    return false;
                }
            }
            return true;
        }
    }

    @SuppressWarnings("deprecation")
    private boolean waterIsClear(BlockPos pos, int x, int z, int scale) {
        BlockPos blockpos = pos.offset(x * scale, 0, z * scale);
        return prehistoricMob.level().getFluidState(blockpos).is(FluidTags.WATER) && !prehistoricMob.level().getBlockState(blockpos).blocksMotion();
    }

    private boolean surfaceIsClear(BlockPos pos, int x, int z, int scale) {
        return prehistoricMob.level().getBlockState(pos.offset(x * scale, 1, z * scale)).isAir() && prehistoricMob.level().getBlockState(pos.offset(x * scale, 2, z * scale)).isAir();
    }

    @Override
    public boolean canContinueToUse() {
        double y = prehistoricMob.getDeltaMovement().y;
        return (!(y * y < (double) 0.03F) || prehistoricMob.getXRot() == 0.0F || !(Math.abs(prehistoricMob.getXRot()) < 10.0F) || !prehistoricMob.isInWater()) && !prehistoricMob.onGround() && ((LeapingMob) prehistoricMob).isLeaping();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        Direction direction = prehistoricMob.getMotionDirection();
        this.prehistoricMob.setDeltaMovement(prehistoricMob.getDeltaMovement().add((double) direction.getStepX() * jumpDistance, jumpHeight, (double) direction.getStepZ() * jumpDistance));
        this.prehistoricMob.getNavigation().stop();
        if (prehistoricMob instanceof LeapingMob leapingMob) {
            leapingMob.setLeaping(true);
        }
    }

    @Override
    public void stop() {
        this.prehistoricMob.setXRot(0.0F);
        if (prehistoricMob instanceof LeapingMob leapingMob) {
            leapingMob.setLeaping(false);
        }
    }

    @Override
    public void tick() {
        boolean flag = breached;
        if (!flag) {
            FluidState fluidstate = prehistoricMob.level().getFluidState(prehistoricMob.blockPosition());
            this.breached = fluidstate.is(FluidTags.WATER);
        }

        if (breached && !flag) {
            this.prehistoricMob.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vec3 vec3 = prehistoricMob.getDeltaMovement();
        if (vec3.y * vec3.y < (double) 0.03F && prehistoricMob.getXRot() != 0.0F) {
            this.prehistoricMob.setXRot(Mth.rotLerp(0.2F, prehistoricMob.getXRot(), 0.0F));
        } else if (vec3.length() > (double) 1.0E-5F) {
            double horizontalDistance = vec3.horizontalDistance();
            double xRot = Math.atan2(-vec3.y, horizontalDistance) * (double) (180F / (float) Math.PI);
//            this.prehistoricMob.setXRot((float) xRot);
            this.prehistoricMob.setYRot(((float) Mth.atan2(prehistoricMob.getMotionDirection().getStepZ(), prehistoricMob.getMotionDirection().getStepX())) * Mth.RAD_TO_DEG - 90F);
        }
    }
}