package com.barlinc.unusual_prehistory.entity.mob.base;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothWallClimberNavigation;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class PrehistoricClimbingFlyingMob extends PrehistoricFlyingMob {

    private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(PrehistoricClimbingFlyingMob.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Direction> CLIMB_DIRECTION = SynchedEntityData.defineId(PrehistoricClimbingFlyingMob.class, EntityDataSerializers.DIRECTION);

    public final SmoothAnimationState climbAnimationState = new SmoothAnimationState();

    public float climbProgress;
    public float prevClimbProgress;
    public Direction prevClimbDirection = Direction.UP;
    public int climbTicks = 0;
    private int climbCooldown = 0;

    protected PrehistoricClimbingFlyingMob(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.lookControl = new FlyingClimbingLookControl(this);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothWallClimberNavigation(this, level);
    }

    @Override
    public void tick() {
        super.tick();
        this.tickClimbing();
    }

    @SuppressWarnings("deprecation")
    public void tickClimbing() {
        if (this.level().isClientSide) {
            this.prevClimbProgress = climbProgress;
            if (this.isClimbing() && climbProgress < 5F) climbProgress++;
            if (!this.isClimbing() && climbProgress > 0F) climbProgress--;
        }
        if (this.isClimbing()) {
            this.climbTicks++;
            boolean onCooldown = climbTicks >= this.getMaxClimbTicks() || this.level().getBlockState(this.blockPosition().above()).isSolid();
            if (!horizontalCollision || onCooldown) {
                this.setClimbing(false);
                this.setClimbDirection(Direction.UP);
                this.climbTicks = 0;
                if (onCooldown) {
                    this.climbCooldown = 900;
                }
            } else {
                Pair<Direction, Double> dir = this.getClosestSide(this.getBoundingBox(), this.blockPosition());
                this.setClimbDirection(dir.getFirst());
                if (getDeltaMovement().horizontalDistance() < Mth.EPSILON) {
                    this.setYRot(dir.getFirst().toYRot());
                }
            }
        } else {
            this.climbCooldown--;
            if (this.canClimb()) {
                this.climbTicks = 0;
                this.setClimbing(true);
            }
        }
    }

    protected boolean canClimb() {
        return climbCooldown <= 0 && horizontalCollision && !this.isEepy() && !this.isSitting();
    }

    public float getClimbProgress(float partialTicks) {
        return (prevClimbProgress + (climbProgress - prevClimbProgress) * partialTicks) * 0.2F;
    }

    public int getMaxClimbTicks() {
        return 100;
    }

    public Pair<Direction, Double> getClosestSide(AABB bounding, BlockPos blockPos) {
        AABB aabb = bounding.move(Vec3.atBottomCenterOf(blockPos).scale(-1));
        double maxX = Math.abs(Math.abs(aabb.maxX) - 0.5);
        double minZ = Math.abs(Math.abs(aabb.minZ) - 0.5);
        double maxZ = Math.abs(Math.abs(aabb.maxZ) - 0.5);
        double smallest = Math.abs(Math.abs(aabb.minX) - 0.5);
        Direction dir = Direction.WEST;
        if (maxX < smallest) {
            smallest = maxX;
            dir = Direction.EAST;
        }
        if (minZ < smallest) {
            smallest = minZ;
            dir = Direction.NORTH;
        }
        if (maxZ < smallest) {
            smallest = maxZ;
            dir = Direction.SOUTH;
        }
        return Pair.of(dir, smallest);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CLIMBING, false);
        builder.define(CLIMB_DIRECTION, Direction.UP);
    }

    public boolean isClimbing() {
        return entityData.get(CLIMBING);
    }

    public void setClimbing(boolean climbing) {
        this.entityData.set(CLIMBING, climbing);
    }

    public Direction getClimbDirection() {
        return entityData.get(CLIMB_DIRECTION);
    }

    public void setClimbDirection(Direction direction) {
        this.entityData.set(CLIMB_DIRECTION, direction);
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing() && !this.isFlying();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (this.level().isClientSide) {
            if (CLIMB_DIRECTION.equals(accessor)) {
                if (entityData.get(CLIMB_DIRECTION) != Direction.UP) {
                    this.prevClimbDirection = entityData.get(CLIMB_DIRECTION);
                }
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    protected static class FlyingClimbingLookControl extends PrehistoricLookControl {

        protected final PrehistoricClimbingFlyingMob mob;

        public FlyingClimbingLookControl(PrehistoricClimbingFlyingMob mob) {
            super(mob);
            this.mob = mob;
        }

        @Override
        public void tick() {
            if (!mob.isClimbing()) super.tick();
        }
    }
}
