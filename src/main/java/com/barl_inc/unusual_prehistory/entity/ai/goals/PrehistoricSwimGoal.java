package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class PrehistoricSwimGoal extends RandomStrollGoal {

    private static final int STUCK_CHECKS = 40;

    protected final PrehistoricMob prehistoricMob;
    protected final int radius;
    protected final int height;
    private final int proximity;
    private final boolean hasProximity;
    @Nullable
    protected Vec3 wantedPos;
    private int stuckChecks;
    private Vec3 lastPos = Vec3.ZERO;

    public PrehistoricSwimGoal(PrehistoricMob prehistoricMob, double speedMultiplier, int interval, int radius, int height) {
        this(prehistoricMob, speedMultiplier, interval, radius, height, 0, false);
    }

    public PrehistoricSwimGoal(PrehistoricMob prehistoricMob, double speedMultiplier, int interval) {
        this(prehistoricMob, speedMultiplier, interval, 10, 7, 0, false);
    }

    public PrehistoricSwimGoal(PrehistoricMob prehistoricMob, double speedMultiplier, int interval, int proximity) {
        this(prehistoricMob, speedMultiplier, interval, 10, 7, proximity, true);
    }

    public PrehistoricSwimGoal(PrehistoricMob prehistoricMob, double speedMultiplier, int interval, int radius, int height, int proximity, boolean hasProximity) {
        super(prehistoricMob, speedMultiplier, interval);
        this.prehistoricMob = prehistoricMob;
        this.radius = radius;
        this.height = height;
        this.proximity = proximity;
        this.hasProximity = hasProximity;
    }

    @Override
    public void start() {
        super.start();
        this.stuckChecks = 0;
        this.lastPos = prehistoricMob.position();
    }

    @Override
    public boolean canUse() {
        if (prehistoricMob.isSitting() || prehistoricMob.isEepy()) {
            return false;
        } else {
            return super.canUse();
        }
    }

    @Override
    public boolean canContinueToUse() {
        this.wantedPos = new Vec3(wantedX, wantedY, wantedZ);
        Vec3 position = prehistoricMob.position();
        this.stuckChecks = position.distanceToSqr(lastPos) < 0.0025D ? stuckChecks + 1 : 0;
        this.lastPos = position;
        if (this.stuckChecks > STUCK_CHECKS) {
            return false;
        }
        if (hasProximity) {
            return super.canContinueToUse() && !(wantedPos.distanceTo(prehistoricMob.position()) <= prehistoricMob.getBbWidth() * proximity);
        }
        return super.canContinueToUse();
    }

    @Nullable
    protected Vec3 getPosition() {
        return BehaviorUtils.getRandomSwimmablePos(prehistoricMob, radius, height);
    }
}