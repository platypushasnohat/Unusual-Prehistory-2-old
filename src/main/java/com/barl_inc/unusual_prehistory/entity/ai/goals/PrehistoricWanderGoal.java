package com.barl_inc.unusual_prehistory.entity.ai.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class PrehistoricWanderGoal extends RandomStrollGoal {

    protected final boolean shouldAvoidWater;
    protected final int radius;
    protected final int height;

    public PrehistoricWanderGoal(PathfinderMob mob, double speedModifier) {
        this(mob, speedModifier, 18, 7, 120, true);
    }

    public PrehistoricWanderGoal(PathfinderMob mob, double speedModifier, boolean shouldAvoidWater) {
        this(mob, speedModifier, 18, 7, 120, shouldAvoidWater);
    }

    public PrehistoricWanderGoal(PathfinderMob mob, double speedModifier, int radius, int height,  int interval, boolean shouldAvoidWater) {
        super(mob, speedModifier, interval, true);
        this.shouldAvoidWater = shouldAvoidWater;
        this.radius = radius;
        this.height = height;
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        if (shouldAvoidWater) {
            Vec3 randomPos;
            if (mob.isInWater()) {
                randomPos = LandRandomPos.getPos(mob, radius, height);
                return randomPos == null ? LandRandomPos.getPos(mob, radius, height) : randomPos;
            }
            randomPos = mob.getRandom().nextFloat() > 0.001F ? LandRandomPos.getPos(mob, radius, height) : DefaultRandomPos.getPos(mob, radius, height);
            return randomPos;
        } else {
            return DefaultRandomPos.getPos(mob, radius, height);
        }
    }
}
