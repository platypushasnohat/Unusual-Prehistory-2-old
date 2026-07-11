package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class OpenWaterSwimGoal extends PrehistoricSwimGoal {

    private static final float FRONT_ANGLE = 45.0F;

    public OpenWaterSwimGoal(PrehistoricMob prehistoricMob, double speedMultiplier, int interval, int radius, int height) {
        this(prehistoricMob, speedMultiplier, interval, radius, height, 0, false);
    }

    public OpenWaterSwimGoal(PrehistoricMob prehistoricMob, double speedMultiplier, int interval) {
        this(prehistoricMob, speedMultiplier, interval, 24, 8, 0, false);
    }

    public OpenWaterSwimGoal(PrehistoricMob prehistoricMob, double speedMultiplier, int interval, int proximity) {
        this(prehistoricMob, speedMultiplier, interval, 24, 8, proximity, true);
    }

    public OpenWaterSwimGoal(PrehistoricMob prehistoricMob, double speedModifier, int interval, int radius, int height, int proximity, boolean hasProximity) {
        super(prehistoricMob, speedModifier, interval, radius, height, proximity, hasProximity);
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        if (prehistoricMob.getRandom().nextFloat() < 0.7F) {
            Vec3 ahead = this.findPositionAhead();
            if (ahead != null) {
                return ahead;
            }
        }
        for (int i = 0; i < 12; i++) {
            Vec3 pos = BehaviorUtils.getRandomSwimmablePos(prehistoricMob, radius, height);
            if (pos != null && this.isOpenWater(pos)) {
                return pos;
            }
        }
        return BehaviorUtils.getRandomSwimmablePos(prehistoricMob, radius, height);
    }

    @Nullable
    private Vec3 findPositionAhead() {
        Vec3 forward = Vec3.directionFromRotation(0.0F, prehistoricMob.yBodyRot);
        for (int i = 0; i < 10; i++) {
            Vec3 pos = prehistoricMob.position().add(forward.yRot((prehistoricMob.getRandom().nextFloat() * 2.0F - 1.0F) * FRONT_ANGLE * Mth.DEG_TO_RAD).scale(14.0D + prehistoricMob.getRandom().nextFloat() * 8.0D)).add(0.0D, -8.0D + prehistoricMob.getRandom().nextFloat() * 12.0D, 0.0D);
            if (this.isOpenWater(pos)) {
                return pos;
            }
        }
        return null;
    }

    private boolean isOpenWater(Vec3 pos) {
        BlockPos center = BlockPos.containing(pos);
        return this.isWaterAt(center) && this.isWaterAt(center.above(3)) && this.isWaterAt(center.below()) && this.isWaterAt(center.offset(2, 0, 0)) && this.isWaterAt(center.offset(-2, 0, 0)) && this.isWaterAt(center.offset(0, 0, 2)) && this.isWaterAt(center.offset(0, 0, -2));
    }

    public boolean isWaterAt(BlockPos pos) {
        return prehistoricMob.level().getFluidState(pos).is(FluidTags.WATER);
    }
}