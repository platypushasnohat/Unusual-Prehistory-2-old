package com.barlinc.unusual_prehistory.entity.mob.base;

import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothWaterBoundNavigation;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;

@SuppressWarnings("deprecation")
public abstract class PrehistoricAquaticMob extends PrehistoricMob {

    public boolean shallowWater;

    public float tilt;
    public float prevTilt;
    public float roll;
    public float prevRoll;
    private float lastYRot;
    private Vec3 lastMoveDir = Vec3.ZERO;

    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState flopAnimationState = new SmoothAnimationState();

    protected PrehistoricAquaticMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothWaterBoundNavigation(this, level);
    }

    protected void switchNavigator(boolean inShallows) {
        this.navigation.stop();
        if (inShallows) {
            this.navigation = new SmoothAmphibiousNavigation(this, this.level());
            this.shallowWater = true;
        } else {
            this.navigation = this.createNavigation(this.level());
            this.shallowWater = false;
        }
    }

    @Override
    public boolean canDrownInFluidType(FluidType fluidType) {
        return fluidType != NeoForgeMod.WATER_TYPE.value();
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    public boolean isInShallowWater() {
        return this.isInWaterOrBubble() && this.getFluidHeight(FluidTags.WATER) < this.getBbHeight();
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return level.isUnobstructed(this);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;
    }

    @SuppressWarnings("SameParameterValue")
    protected void tickRotations(float maxTilt, float maxRoll, float rollPerYaw) {
        // tilt
        this.prevTilt = tilt;
        float targetTilt = 0.0F;
        if (this.isInWater() || (this instanceof LeapingMob leapingMob && leapingMob.isLeaping())) {
            Vec3 movement = this.getDeltaMovement();
            if (movement.lengthSqr() > 1.0E-6) {
                this.lastMoveDir = movement;
            }
            targetTilt = -((float) Mth.atan2(lastMoveDir.y, lastMoveDir.horizontalDistance()) * (180.0F / (float) Math.PI));
            targetTilt = Mth.clamp(targetTilt, -maxTilt, maxTilt);
        }
        this.tilt += (targetTilt - tilt) * 0.2F;

        // roll
        this.prevRoll = roll;
        float yawDelta = Mth.wrapDegrees(this.getYRot() - lastYRot);
        this.lastYRot = this.getYRot();
        float targetRoll = this.isInWater() ? Mth.clamp(-yawDelta * rollPerYaw, -maxRoll, maxRoll) : 0.0F;
        this.roll += (targetRoll - roll) * 0.2F;
    }

    public float getTilt(float partialTick) {
        return Mth.lerp(partialTick, prevTilt, tilt);
    }

    public float getRoll(float partialTick) {
        return Mth.lerp(partialTick, prevRoll, roll);
    }

    @Override
    public void tick() {
        super.tick();
        this.tickFlopping();
        if (this.shouldUseShallowNavigation()) {
            this.fixShallowNavigation();
        }
    }

    protected boolean shouldUseShallowNavigation() {
        return false;
    }

    protected void fixShallowNavigation() {
        final boolean shallowWater = this.isInShallowWater();
        if (shallowWater && !this.shallowWater) {
            this.switchNavigator(true);
        } else if (!shallowWater && this.shallowWater) {
            this.switchNavigator(false);
        }
    }

    public float flopChance() {
        return 1.0F;
    }

    public boolean shouldFlop() {
        return true;
    }

    public void tickFlopping() {
        if (!this.isInWater() && this.onGround() && this.getRandom().nextFloat() < this.flopChance() && this.shouldFlop()) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.getRandom().nextFloat() * 2.0F - 1.0F) * 0.2F, 0.5D, (this.getRandom().nextFloat() * 2.0F - 1.0F) * 0.2F));
            if (this.getRandom().nextFloat() < 0.25F) this.setYRot(this.getRandom().nextFloat() * 360.0F);
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }
    }

    @Override
    public void baseTick() {
        int airSupply = this.getAirSupply();
        super.baseTick();
        this.handleAirSupply(airSupply);
    }

    protected void handleAirSupply(int airSupply) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(airSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }
    }
}
