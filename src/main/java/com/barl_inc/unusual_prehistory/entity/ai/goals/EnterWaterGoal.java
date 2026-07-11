package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EnterWaterGoal extends Goal {

    protected final PrehistoricAmphibiousMob amphibiousMob;
    protected final int maxTimeOnLand;
    private final double speedModifier;
    private final boolean shouldWander;
    private Vec3 waterPos;

    public EnterWaterGoal(PrehistoricAmphibiousMob amphibiousMob, double speedModifier) {
        this(amphibiousMob, speedModifier, 6000, false);
    }

    public EnterWaterGoal(PrehistoricAmphibiousMob amphibiousMob, double speedModifier, int maxTimeOnLand, boolean shouldWander) {
        this.speedModifier = speedModifier;
        this.amphibiousMob = amphibiousMob;
        this.maxTimeOnLand = maxTimeOnLand;
        this.shouldWander = shouldWander;
    }

    @Override
    public boolean canUse() {
        if (amphibiousMob.getTarget() != null) {
            return false;
        } else if (amphibiousMob.isInWater() || amphibiousMob.isEepy() || amphibiousMob.isSitting()) {
            return false;
        } else if (amphibiousMob.getTimeOnLand() < maxTimeOnLand) {
            return false;
        }
        return this.findWaterPos();
    }

    @Override
    public boolean canContinueToUse() {
        return !amphibiousMob.getNavigation().isDone() && !amphibiousMob.isInWater() && amphibiousMob.getTarget() == null && !amphibiousMob.isEepy() && !amphibiousMob.isSitting();
    }

    @Override
    public void start() {
        this.amphibiousMob.getNavigation().moveTo(waterPos.x, waterPos.y, waterPos.z, speedModifier);
    }

    private boolean findWaterPos() {
        RandomSource random = amphibiousMob.getRandom();
        Level level = amphibiousMob.level();
        BlockPos original = amphibiousMob.blockPosition();
        BlockPos.MutableBlockPos mutable = original.mutable();

        for (int i = 0; i < 10; i++) {
            mutable.move(random.nextInt(20) - 10, random.nextInt(16) - 8, random.nextInt(20) - 10);
            if (level.getFluidState(mutable).is(FluidTags.WATER)) {
                this.waterPos = Vec3.atCenterOf(mutable.immutable());
                return true;
            }
        }
        if (shouldWander) {
            Vec3 randomPos = DefaultRandomPos.getPos(amphibiousMob, 10, 7);
            if (randomPos != null) {
                this.waterPos = randomPos;
                return true;
            }
        }
        return false;
    }
}