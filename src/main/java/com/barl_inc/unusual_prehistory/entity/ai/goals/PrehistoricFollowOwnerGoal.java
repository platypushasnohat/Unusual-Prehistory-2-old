package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import java.util.EnumSet;

public class PrehistoricFollowOwnerGoal extends Goal {

    protected final PrehistoricMob tamedMob;
    protected LivingEntity owner;
    private final double speedModifier;
    private final double sprintSpeedModifier;
    protected int timeToRecalcPath;
    private final float stopDistance;
    private final float startDistance;
    private float oldWaterCost;
    protected final boolean canFly;
    protected final boolean shouldChangeMalus;

    public PrehistoricFollowOwnerGoal(PrehistoricMob tamedMob, double speedModifier, double sprintSpeedModifier, float startDistance, float stopDistance) {
        this(tamedMob, speedModifier, sprintSpeedModifier, startDistance, stopDistance, false, false);
    }

    public PrehistoricFollowOwnerGoal(PrehistoricMob tamedMob, double speedModifier, double sprintSpeedModifier, float startDistance, float stopDistance, boolean shouldChangeMalus) {
        this(tamedMob, speedModifier, sprintSpeedModifier, startDistance, stopDistance, false, shouldChangeMalus);
    }

    public PrehistoricFollowOwnerGoal(PrehistoricMob tamedMob, double speedModifier, double sprintSpeedModifier, float startDistance, float stopDistance, boolean canFly, boolean shouldChangeMalus) {
        this.tamedMob = tamedMob;
        this.speedModifier = speedModifier;
        this.sprintSpeedModifier = sprintSpeedModifier;
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.canFly = canFly;
        this.shouldChangeMalus = shouldChangeMalus;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = tamedMob.getOwner();
        if (owner == null) {
            return false;
        } else if (owner.isSpectator()) {
            return false;
        } else if (this.unableToMove()) {
            return false;
        } else if (this.tamedMob.distanceToSqr(owner) < (double) (this.startDistance * this.startDistance)) {
            return false;
        } else {
            this.owner = owner;
            return this.shouldFollow() && !this.isInCombat();
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (tamedMob.getNavigation().isDone()) {
            return false;
        } else if (this.unableToMove()) {
            return false;
        } else {
            return !(tamedMob.distanceToSqr(owner) <= (double) (stopDistance * stopDistance)) && this.shouldFollow() && !this.isInCombat();
        }
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        if (shouldChangeMalus) {
            this.oldWaterCost = tamedMob.getPathfindingMalus(PathType.WATER);
            this.tamedMob.setPathfindingMalus(PathType.WATER, 0.0F);
        }
    }

    @Override
    public void stop() {
        this.owner = null;
        this.tamedMob.getNavigation().stop();
        this.tamedMob.setRunning(false);
        if (shouldChangeMalus) {
            this.tamedMob.setPathfindingMalus(PathType.WATER, oldWaterCost);
        }
    }

    @Override
    public void tick() {
        this.tamedMob.getLookControl().setLookAt(owner, 10.0F, (float) tamedMob.getMaxHeadXRot());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            if (this.tamedMob.distanceToSqr(owner) >= 256.0D) {
                this.tamedMob.setRunning(false);
                this.teleportToOwner();
            } else if (this.tamedMob.distanceToSqr(owner) >= 64.0D) {
                this.tamedMob.setRunning(true);
                this.tamedMob.getNavigation().moveTo(owner, sprintSpeedModifier);
            } else {
                this.tamedMob.setRunning(false);
                this.tamedMob.getNavigation().moveTo(owner, speedModifier);
            }
        }
    }

    protected boolean unableToMove() {
        return tamedMob.isOrderedToSit() || tamedMob.isPassenger() || tamedMob.isLeashed();
    }

    protected void teleportToOwner() {
        BlockPos blockpos = owner.blockPosition();
        for (int i = 0; i < 10; ++i) {
            int j = this.randomIntInclusive(-3, 3);
            int k = this.randomIntInclusive(-1, 1);
            int l = this.randomIntInclusive(-3, 3);
            boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
            if (flag) {
                return;
            }
        }
    }

    protected boolean maybeTeleportTo(int x, int y, int z) {
        if (Math.abs((double) x - owner.getX()) < 2.0D && Math.abs((double) z - owner.getZ()) < 2.0D) {
            return false;
        } else if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        } else {
            this.tamedMob.moveTo((double) x + 0.5D, y, (double) z + 0.5D, tamedMob.getYRot(), tamedMob.getXRot());
            this.tamedMob.getNavigation().stop();
            return true;
        }
    }

    protected boolean canTeleportTo(BlockPos blockPos) {
        PathType blockpathtypes = WalkNodeEvaluator.getPathTypeStatic(tamedMob, blockPos.mutable());
        if (blockpathtypes != PathType.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = tamedMob.level().getBlockState(blockPos.below());
            if (!canFly && blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = blockPos.subtract(tamedMob.blockPosition());
                return tamedMob.level().noCollision(tamedMob, tamedMob.getBoundingBox().move(blockpos));
            }
        }
    }

    protected boolean shouldFollow() {
        return tamedMob.getCommand() == 2;
    }

    protected boolean isInCombat() {
        Entity owner = tamedMob.getOwner();
        if (owner != null) {
            return tamedMob.distanceTo(owner) < 30 && tamedMob.getTarget() != null && tamedMob.getTarget().isAlive();
        }
        return false;
    }

    protected int randomIntInclusive(int min, int max) {
        return this.tamedMob.getRandom().nextInt(max - min + 1) + min;
    }
}