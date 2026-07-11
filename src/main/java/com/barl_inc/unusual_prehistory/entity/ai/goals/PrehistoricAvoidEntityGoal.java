package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class PrehistoricAvoidEntityGoal<T extends LivingEntity> extends Goal {

    protected final PrehistoricMob mob;
    private final double speedModifier;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected final boolean shouldEscapeToWater;
    @Nullable
    protected T toAvoid;
    protected final float maxDist;
    @Nullable
    protected final Class<T> avoidClass;
    protected final Predicate<LivingEntity> avoidPredicate;
    protected final Predicate<LivingEntity> predicateOnAvoidEntity;
    protected final TargetingConditions avoidEntityTargeting;

    public PrehistoricAvoidEntityGoal(PrehistoricMob mob, Class<T> classToAvoid, float distance, double speedModifier, boolean shouldEscapeToWater) {
        this(mob, classToAvoid, (entity) -> true, distance, speedModifier, shouldEscapeToWater, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
    }

    public PrehistoricAvoidEntityGoal(PrehistoricMob mob, @Nullable Class<T> classToAvoid, float distance, double speedModifier, Predicate<LivingEntity> avoidPredicate) {
        this(mob, classToAvoid, avoidPredicate, distance, speedModifier, false, (entity) -> true);
    }

    public PrehistoricAvoidEntityGoal(PrehistoricMob mob, @Nullable Class<T> classToAvoid, float distance, double speedModifier, boolean shouldEscapeToWater, Predicate<LivingEntity> avoidPredicate) {
        this(mob, classToAvoid, avoidPredicate, distance, speedModifier, shouldEscapeToWater, (entity) -> true);
    }

    public PrehistoricAvoidEntityGoal(PrehistoricMob mob, @Nullable Class<T> classToAvoid, Predicate<LivingEntity> avoidPredicate, float distance, double speedModifier, boolean shouldEscapeToWater, Predicate<LivingEntity> predicateOnAvoid) {
        this.mob = mob;
        this.avoidClass = classToAvoid;
        this.avoidPredicate = avoidPredicate;
        this.maxDist = distance;
        this.speedModifier = speedModifier;
        this.shouldEscapeToWater = shouldEscapeToWater;
        this.predicateOnAvoidEntity = predicateOnAvoid;
        this.avoidEntityTargeting = TargetingConditions.forCombat().range(distance).selector(predicateOnAvoid.and(avoidPredicate));
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (avoidClass != null) {
            this.toAvoid = mob.level().getNearestEntity(mob.level().getEntitiesOfClass(avoidClass, mob.getBoundingBox().inflate(maxDist, 3.0D, maxDist), (entity) -> true), avoidEntityTargeting, mob, mob.getX(), mob.getY(), mob.getZ());
        }
        if (toAvoid == null) {
            return false;
        } else {
            if (shouldEscapeToWater && !mob.isInWaterOrBubble()) {
                BlockPos blockpos = this.lookForWater(mob.level(), mob);
                if (blockpos != null) {
                    this.posX = blockpos.getX();
                    this.posY = blockpos.getY();
                    this.posZ = blockpos.getZ();
                    return true;
                }
            }
            return this.findRandomPositionAway();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.mob.setRunning(true);
        if (mob.isSitting()) {
            this.mob.setSitting(false);
            this.mob.setSitCooldown(mob.getSitCooldown() + 200);
        }
        this.mob.getNavigation().moveTo(posX, posY, posZ, speedModifier);
    }

    @Override
    public void stop() {
        this.mob.setRunning(false);
        this.toAvoid = null;
    }

    protected boolean findRandomPositionAway() {
        Vec3 vec3 = DefaultRandomPos.getPos(mob, 10, 4);
        if (toAvoid != null) {
            vec3 = LandRandomPos.getPosAway(mob, 10, 4, toAvoid.position());
        }
        if (vec3 != null) {
            this.mob.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, speedModifier);
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

    protected BlockPos lookForWater(BlockGetter level, Entity entity) {
        BlockPos entityPos = entity.blockPosition();
        if (!level.getBlockState(entityPos).getCollisionShape(level, entityPos).isEmpty()) {
            return null;
        }
        return BlockPos.findClosestMatch(entityPos, 16, 4, (pos) -> level.getFluidState(pos).is(FluidTags.WATER)).orElse(null);
    }
}