package com.barl_inc.unusual_prehistory.entity.mob.base;

import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothFlyingNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;

public abstract class AmbientFlyingMob extends AmbientMob implements FlyingAnimal {

    protected float flightPitch = 0.0F;
    protected float prevFlightPitch = 0.0F;
    protected float flightRoll = 0.0F;
    protected float prevFlightRoll = 0.0F;

    protected AmbientFlyingMob(EntityType<? extends AmbientMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        SmoothFlyingNavigation flyingPathNavigation = new SmoothFlyingNavigation(this, level){
            @Override
            public boolean isStableDestination(BlockPos blockPos) {
                return !level().getBlockState(blockPos.below()).isAir();
            }
        };
        flyingPathNavigation.setCanOpenDoors(false);
        flyingPathNavigation.setCanFloat(false);
        flyingPathNavigation.setCanPassDoors(true);
        return flyingPathNavigation;
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos blockPos, LevelReader levelReader) {
        return levelReader.getBlockState(blockPos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isFlying() && this.getDeltaMovement().y < 0.0 && this.isAlive()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
        }

        this.tickRotation((float) (this.getDeltaMovement().y * 2.0F * -57.295776F));
    }

    @Override
    public boolean onClimbable() {
        return !this.isFlying() && super.onClimbable();
    }

    public void tickRotation(float yMov) {
        this.prevFlightPitch = this.flightPitch;
        this.prevFlightRoll = this.flightRoll;
        this.flightPitch = yMov;
        float threshold = 1.0F;
        boolean flag = false;
        if (this.isFlying() && this.yRotO - this.getYRot() > threshold) {
            this.flightRoll += 2.0F;
            flag = true;
        }
        if (this.isFlying() && this.yRotO - this.getYRot() < -threshold) {
            this.flightRoll -= 2.0F;
            flag = true;
        }
        if (!flag) {
            if (this.flightRoll > 0.0F) {
                this.flightRoll = Math.max(this.flightRoll - 2.0F, 0.0F);
            }
            if (this.flightRoll < 0.0F) {
                this.flightRoll = Math.min(this.flightRoll + 2.0F, 0.0F);
            }
        }
        this.flightRoll = Mth.clamp(this.flightRoll, -40.0F, 40.0F);
    }

    public float getFlightPitch(float partialTick) {
        return (prevFlightPitch + (flightPitch - prevFlightPitch) * partialTick);
    }

    public float getFlightRoll(float partialTick) {
        return (prevFlightRoll + (flightRoll - prevFlightRoll) * partialTick);
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    public static class FlyingWanderAroundGoal extends Goal {

        private final AmbientFlyingMob ambientMob;
        private final double speedModifier;

        public FlyingWanderAroundGoal(AmbientFlyingMob ambientMob, double speedModifier) {
            this.ambientMob = ambientMob;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return ambientMob.getNavigation().isDone() && ambientMob.random.nextInt(20) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return ambientMob.getNavigation().isInProgress();
        }

        @Override
        public void start() {
            if (ambientMob.onGround()) {
                this.ambientMob.addDeltaMovement(new Vec3(0.0F, 0.3F, 0.0F));
            }
            Vec3 location = this.getRandomLocation();
            if (location != null) {
                this.ambientMob.getNavigation().moveTo(ambientMob.getNavigation().createPath(BlockPos.containing(location), 1), speedModifier);
            }
        }

        @Nullable
        private Vec3 getRandomLocation() {
            Vec3 vec3d2 = ambientMob.getViewVector(0.0F);
            Vec3 vec3d3 = HoverRandomPos.getPos(ambientMob, 10, 3, vec3d2.x, vec3d2.z, 1.5707964F, 3, 1);
            return vec3d3 != null ? vec3d3 : AirAndWaterRandomPos.getPos(ambientMob, 10, 2, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }
    }

    public static class FlyingLookAroundGoal extends RandomLookAroundGoal {

        private final AmbientFlyingMob ambientMob;

        public FlyingLookAroundGoal(AmbientFlyingMob ambientMob) {
            super(ambientMob);
            this.ambientMob = ambientMob;
        }

        @Override
        public boolean canUse() {
            return ambientMob.onGround() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return ambientMob.onGround() && super.canContinueToUse();
        }
    }
}
