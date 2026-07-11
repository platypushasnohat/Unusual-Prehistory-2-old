package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.PathType;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class PrehistoricFollowMobGoal extends Goal {

    protected final PrehistoricMob prehistoricMob;
    protected final double speedModifier;
    protected final float stopDistance;
    protected final float areaSize;
    protected final Predicate<Mob> followPredicate;
    @Nullable
    protected Mob followingMob;
    protected final int chance;
    protected int timeToRecalcPath;
    protected float oldWaterCost;

    public PrehistoricFollowMobGoal(PrehistoricMob prehistoricMob, int chance, double speedModifier, float stopDistance, float areaSize, Predicate<Mob> followPredicate) {
        this.prehistoricMob = prehistoricMob;
        this.chance = chance;
        this.speedModifier = speedModifier;
        this.stopDistance = stopDistance;
        this.areaSize = areaSize;
        this.followPredicate = followPredicate;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        List<Mob> list = prehistoricMob.level().getEntitiesOfClass(Mob.class, prehistoricMob.getBoundingBox().inflate(areaSize), followPredicate);
        if (!list.isEmpty()) {
            for (Mob mobs : list) {
                if (!mobs.isInvisible() && mobs != prehistoricMob) {
                    this.followingMob = mobs;
                    return prehistoricMob.getRandom().nextInt(chance) == 0;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return followingMob != null && followingMob.isAlive() && !prehistoricMob.getNavigation().isDone() && prehistoricMob.distanceToSqr(followingMob) > (double) (stopDistance * stopDistance);
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = prehistoricMob.getPathfindingMalus(PathType.WATER);
        this.prehistoricMob.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.followingMob = null;
        this.prehistoricMob.getNavigation().stop();
        this.prehistoricMob.setPathfindingMalus(PathType.WATER, oldWaterCost);
    }

    @Override
    public void tick() {
        if (followingMob != null && !prehistoricMob.isLeashed()) {
            this.prehistoricMob.getLookControl().setLookAt(followingMob, 10.0F, (float) prehistoricMob.getMaxHeadXRot());
            if (--timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                double distSqr = prehistoricMob.distanceToSqr(followingMob);
                if (distSqr > (double) (stopDistance * stopDistance)) {
                    this.prehistoricMob.getNavigation().moveTo(followingMob, speedModifier);
                } else {
                    this.prehistoricMob.getNavigation().stop();
                    LookControl targetLook = followingMob.getLookControl();
                    if (targetLook.getWantedX() == prehistoricMob.getX() && targetLook.getWantedY() == prehistoricMob.getY() && targetLook.getWantedZ() == prehistoricMob.getZ()) {
                        double dx = followingMob.getX() - prehistoricMob.getX();
                        double dz = followingMob.getZ() - prehistoricMob.getZ();
                        this.prehistoricMob.getNavigation().moveTo(prehistoricMob.getX() - dx, prehistoricMob.getY(), prehistoricMob.getZ() - dz, speedModifier);
                    }
                }
            }
        }
    }
}