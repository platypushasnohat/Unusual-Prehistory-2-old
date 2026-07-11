package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class PrehistoricPanicGoal extends PanicGoal {

    protected final PrehistoricMob prehistoricMob;
    protected final int radius;
    protected final int height;
    protected final boolean shouldEscapeToWater;

    public PrehistoricPanicGoal(PrehistoricMob mob, double speedModifier) {
        this(mob, speedModifier, 10, 4, false);
    }

    public PrehistoricPanicGoal(PrehistoricMob mob, double speedModifier, int radius, int height) {
        this(mob, speedModifier, radius, height, false);
    }

    public PrehistoricPanicGoal(PrehistoricMob mob, double speedModifier, int radius, int height, boolean shouldEscapeToWater) {
        super(mob, speedModifier);
        this.prehistoricMob = mob;
        this.radius = radius;
        this.height = height;
        this.shouldEscapeToWater = shouldEscapeToWater;
    }

    @Override
    public void start() {
        super.start();
        this.prehistoricMob.setRunning(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.prehistoricMob.setRunning(false);
    }

    @Override
    public boolean canUse() {
        if (!this.shouldPanic()) {
            return false;
        } else {
            if (mob.isOnFire() || (shouldEscapeToWater && !mob.isInWaterOrBubble())) {
                BlockPos blockpos = this.lookForWater(mob.level(), mob, 10);
                if (blockpos != null) {
                    this.posX = blockpos.getX();
                    this.posY = blockpos.getY();
                    this.posZ = blockpos.getZ();
                    return true;
                }
            }
            return this.findRandomPosition();
        }
    }

    @Override
    protected boolean findRandomPosition() {
        Vec3 vec3 = DefaultRandomPos.getPos(prehistoricMob, radius, height);
        if (prehistoricMob.getLastHurtByMob() != null) {
            vec3 = LandRandomPos.getPosAway(prehistoricMob, radius, height, prehistoricMob.getLastHurtByMob().position());
        }
        if (vec3 == null) {
            return false;
        } else {
            this.posX = vec3.x;
            this.posY = vec3.y;
            this.posZ = vec3.z;
            return true;
        }
    }

    @Nullable
    @Override
    protected BlockPos lookForWater(BlockGetter level, Entity entity, int range) {
        BlockPos blockpos = entity.blockPosition();
        return !level.getBlockState(blockpos).getCollisionShape(level, blockpos).isEmpty() ? null : BlockPos.findClosestMatch(entity.blockPosition(), range, 6, (pos) -> level.getFluidState(pos).is(FluidTags.WATER)).orElse(null);
    }
}

