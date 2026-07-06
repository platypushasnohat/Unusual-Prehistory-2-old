package com.barlinc.unusual_prehistory.entity.mob.base;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricFlyingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothFlyingNavigation;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class PrehistoricFlyingMob extends PrehistoricMob implements FlyingAnimal {

    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(PrehistoricFlyingMob.class, EntityDataSerializers.BOOLEAN);

    public int flightTicks = 0;
    protected float flightPitch = 0.0F;
    protected float prevFlightPitch = 0.0F;
    protected float flightRoll = 0.0F;
    protected float prevFlightRoll = 0.0F;
    public boolean isLandNavigator;

    public int stuckTicks = 0;

    public final SmoothAnimationState flyAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState flyFastAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState hoverAnimationState = new SmoothAnimationState();

    protected PrehistoricFlyingMob(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
    }

    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new PrehistoricMoveControl(this);
            this.navigation = this.createNavigation(this.level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new PrehistoricFlyingMoveControl(this);
            SmoothFlyingNavigation flyingPathNavigation = new SmoothFlyingNavigation(this, this.level()){
                @Override
                public boolean isStableDestination(BlockPos blockPos) {
                    return !level().getBlockState(blockPos.below()).isAir();
                }
            };
            flyingPathNavigation.setCanOpenDoors(false);
            flyingPathNavigation.setCanFloat(false);
            flyingPathNavigation.setCanPassDoors(true);
            this.navigation = flyingPathNavigation;
            this.isLandNavigator = false;
        }
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
        if (!this.canFly()) {
            super.checkFallDamage(y, onGround, state, pos);
        }
    }

    @Override
    public boolean canTrample(BlockState state, BlockPos pos, float fallDistance) {
        return !this.canFly();
    }

    public boolean onClimbable() {
        return !this.canFly() && super.onClimbable();
    }

    public boolean canFly() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.canFly()) {
            this.tickFlight();
        }
        this.tickRotation((float) (this.getDeltaMovement().y * 2.0F * -57.295776F));
    }

    protected boolean shouldUseStuckTicks() {
        return true;
    }

    protected boolean shouldFlyOutOfWater() {
        return true;
    }

    public void tickFlight() {
        if (this.isFlying() && Math.abs(this.getDeltaMovement().x) < 0.01D && Math.abs(this.getDeltaMovement().z) < 0.01D && this.shouldUseStuckTicks()) {
            this.stuckTicks++;
        } else {
            this.stuckTicks = 0;
        }

        if (this.isFlying() && horizontalCollision) {
            this.addDeltaMovement(new Vec3(0.0F, 0.15D, 0.0F));
        }
        if (this.flightTicks > 20 && this.isFlying() && (this.isInWaterOrBubble() || verticalCollision || !this.canFly() || stuckTicks > 10)) {
            this.switchNavigator(true);
        }

        if (this.isFlying()) {
            this.flightTicks++;
            this.setNoGravity(true);
            if (isLandNavigator) {
                this.switchNavigator(false);
            }
        } else {
            this.flightTicks = 0;
            this.setNoGravity(false);
            if (!isLandNavigator) {
                this.switchNavigator(true);
            }
        }

        if (this.isInWaterOrBubble() && this.shouldFlyOutOfWater() && !this.isFlying()) {
            this.setFlying(true);
        }
    }

    public void tickRotation(float yMov) {
        this.prevFlightPitch = flightPitch;
        this.prevFlightRoll = flightRoll;
        this.flightPitch = yMov;
        float threshold = 1.0F;
        boolean flag = false;
        if (this.isFlying() && yRotO - this.getYRot() > threshold) {
            this.flightRoll += 2.0F;
            flag = true;
        }
        if (this.isFlying() && yRotO - this.getYRot() < -threshold) {
            this.flightRoll -= 2.0F;
            flag = true;
        }
        if (!flag) {
            if (flightRoll > 0.0F) {
                this.flightRoll = Math.max(flightRoll - 2.0F, 0.0F);
            }
            if (flightRoll < 0.0F) {
                this.flightRoll = Math.min(flightRoll + 2.0F, 0.0F);
            }
        }
        this.flightRoll = Mth.clamp(flightRoll, -40.0F, 40.0F);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FLYING, false);
    }

    @Override
    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
    }

    public float getFlightPitch(float partialTicks) {
        return (prevFlightPitch + (flightPitch - prevFlightPitch) * partialTicks);
    }

    public float getFlightRoll(float partialTicks) {
        return (prevFlightRoll + (flightRoll - prevFlightRoll) * partialTicks);
    }
}
