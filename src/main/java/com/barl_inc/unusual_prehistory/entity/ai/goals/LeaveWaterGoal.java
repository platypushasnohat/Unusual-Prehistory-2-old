package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public class LeaveWaterGoal extends Goal {

    protected final PrehistoricAmphibiousMob amphibiousMob;
    protected final int maxTimeInWater;
    private final double speedModifier;
    private BlockPos landPos;

    public LeaveWaterGoal(PrehistoricAmphibiousMob amphibiousMob, double speedModifier) {
        this(amphibiousMob, speedModifier, 6000);
    }

    public LeaveWaterGoal(PrehistoricAmphibiousMob amphibiousMob, double speedModifier, int maxTimeInWater) {
        this.speedModifier = speedModifier;
        this.amphibiousMob = amphibiousMob;
        this.maxTimeInWater = maxTimeInWater;
    }

    @Override
    public boolean canUse() {
        if (amphibiousMob.getTarget() != null) {
            return false;
        } else if (!amphibiousMob.isInWater() || amphibiousMob.isEepy() || amphibiousMob.isSitting()) {
            return false;
        } else if (amphibiousMob.getTimeInWater() < maxTimeInWater) {
            return false;
        }
        return this.findLandPos();
    }

    @Override
    public boolean canContinueToUse() {
        return !amphibiousMob.getNavigation().isDone() && amphibiousMob.isInWater() && amphibiousMob.getTarget() == null && !amphibiousMob.isEepy() && !amphibiousMob.isSitting();
    }

    @Override
    public void start() {
        this.amphibiousMob.getNavigation().moveTo(landPos.getX(), landPos.getY(), landPos.getZ(), speedModifier);
    }

    @Override
    public void tick() {
        if (amphibiousMob.horizontalCollision && amphibiousMob.isInWater()) {
            float f1 = amphibiousMob.getYRot() * ((float) Math.PI / 180F);
            this.amphibiousMob.setDeltaMovement(amphibiousMob.getDeltaMovement().add(-Mth.sin(f1) * 0.2F, 0.2D, Mth.cos(f1) * 0.2F));
        }
    }

    private boolean findLandPos() {
        RandomSource random = amphibiousMob.getRandom();
        Level level = amphibiousMob.level();
        BlockPos original = amphibiousMob.blockPosition();
        BlockPos.MutableBlockPos mutable = original.mutable();

        for (int i = 0; i < 10; i++) {
            mutable.move(random.nextInt(20) - 10, 1 + random.nextInt(6), random.nextInt(20) - 10);
            if (level.getBlockState(mutable).isSolidRender(level, mutable) && level.getBlockState(mutable.above()).isAir() && mutable.getY() >= original.getY()) {
                this.landPos = mutable.immutable();
                return true;
            }
        }
        return false;
    }
}