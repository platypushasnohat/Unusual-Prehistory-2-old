package com.barl_inc.unusual_prehistory.entity.mob.base;

import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;

public abstract class PrehistoricAmphibiousMob extends PrehistoricAquaticMob {

    public static final EntityDataAccessor<Integer> TIME_IN_WATER = SynchedEntityData.defineId(PrehistoricAmphibiousMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> TIME_ON_LAND = SynchedEntityData.defineId(PrehistoricAmphibiousMob.class, EntityDataSerializers.INT);

    public boolean isLandNavigator;

    protected PrehistoricAmphibiousMob(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothAmphibiousNavigation(this, level);
    }

    @Override
    public boolean shouldFlop() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isEepy() && !this.isSitting() && !this.level().isClientSide) {
            if (this.isInWater()) {
                this.setTimeInWater(this.getTimeInWater() + 1);
                this.setTimeOnLand(0);
            } else {
                this.setTimeOnLand(this.getTimeOnLand() + 1);
                this.setTimeInWater(0);
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TIME_IN_WATER, 0);
        builder.define(TIME_ON_LAND, 0);
    }

    public int getTimeInWater() {
        return this.entityData.get(TIME_IN_WATER);
    }
    public void setTimeInWater(int time) {
        this.entityData.set(TIME_IN_WATER, time);
    }

    public int getTimeOnLand() {
        return this.entityData.get(TIME_ON_LAND);
    }
    public void setTimeOnLand(int time) {
        this.entityData.set(TIME_ON_LAND, time);
    }

    protected void handleAirSupply(int airSupply) {
    }
}
