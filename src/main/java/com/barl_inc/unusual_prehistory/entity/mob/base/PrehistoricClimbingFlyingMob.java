package com.barl_inc.unusual_prehistory.entity.mob.base;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothWallClimberNavigation;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;

public abstract class PrehistoricClimbingFlyingMob extends PrehistoricFlyingMob {

    private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.defineId(PrehistoricClimbingFlyingMob.class, EntityDataSerializers.BYTE);

    public final SmoothAnimationState climbAnimationState = new SmoothAnimationState();

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
        if (!this.level().isClientSide) {
            this.setClimbing(horizontalCollision);
        }
        if (this.horizontalCollision && this.onClimbable()) {
            this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y * this.getClimbSpeedMultiplier(), this.getDeltaMovement().z);
        }
    }

    @Override
    public boolean onClimbable() {
        return !this.isFlying() && this.isClimbing();
    }

    @Override
    public boolean canFly() {
        return !this.isClimbing();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CLIMBING, (byte) 0);
    }

    public boolean isClimbing() {
        return (entityData.get(CLIMBING) & 1) != 0;
    }
    public void setClimbing(boolean climbing) {
        byte flag = entityData.get(CLIMBING);
        if (climbing) {
            flag = (byte) (flag | 1);
        } else {
            flag = (byte) (flag & -2);
        }

        this.entityData.set(CLIMBING, flag);
    }

    @Override
    protected float getJumpPower() {
        return 0.0F;
    }

    protected float getClimbSpeedMultiplier() {
        return 1.0F;
    }

    protected static class FlyingClimbingLookControl extends PrehistoricLookControl {

        protected final PrehistoricClimbingFlyingMob mob;

        public FlyingClimbingLookControl(PrehistoricClimbingFlyingMob mob) {
            super(mob);
            this.mob = mob;
        }

        @Override
        public void tick() {
            if (!mob.isClimbing()) {
                super.tick();
            }
        }
    }
}
