package com.barl_inc.unusual_prehistory.entity.mob.ambient;

import com.barl_inc.unusual_prehistory.entity.mob.base.AmbientWaterCrawlingMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Setapedites extends AmbientWaterCrawlingMob {

    public UUID leaderUUID;

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState(1.0F);

    public Setapedites(EntityType<? extends AmbientWaterCrawlingMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.5F, 1.0F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 20);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.21F)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.STEP_HEIGHT, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new WaterCrawlerFindWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new WaterCrawlerRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new WaterCrawlerRandomSwimGoal(this, 1.0D, 20));
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isCrawling() || !this.isInWaterOrBubble(), this.tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isCrawling(), this.tickCount);
    }

    public void setLeader(Entity entity) {
        this.leaderUUID = entity != null ? entity.getUUID() : null;
    }

    public Optional<Entity> getLeader() {
        if (this.leaderUUID != null) {
            Level var2 = this.level();
            if (var2 instanceof ServerLevel serverLevel) {
                Entity entity = serverLevel.getEntity(this.leaderUUID);
                return Optional.ofNullable(entity);
            }
        }
        return Optional.empty();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.BUG_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.BUG_DEATH.get();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
        if (spawnType == MobSpawnType.NATURAL) {
            int schoolCount = (int) (24 * this.getRandom().nextFloat());
            if (schoolCount > 0 && !this.level().isClientSide) {
                for (int i = 0; i < schoolCount; i++) {
                    float distance = 1.0F;
                    Setapedites entity = new Setapedites(UP2Entities.SETAPEDITES.get(), this.level());
                    entity.moveTo(this.getX() + this.getRandom().nextFloat() * distance, this.getY() + this.getRandom().nextFloat() * distance, this.getZ() + this.getRandom().nextFloat() * distance);
                    this.level().addFreshEntity(entity);
                }
            }
        }
        return spawnData;
    }

    public static class FollowLeaderGoal extends Goal {

        private final Setapedites fish;
        private int timeToRecalcPath;
        private Entity leader;

        public FollowLeaderGoal(Setapedites fish) {
            this.fish = fish;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public void start() {
            this.timeToRecalcPath = 0;
        }

        @Override
        public boolean canUse() {
            return this.fish.getLeader().map((entity) -> {
                if (entity.isAlive() && entity.isInWater() && !(entity.distanceTo(this.fish) > 8.0F)) {
                    this.leader = entity;
                    return true;
                } else {
                    this.fish.setLeader(null);
                    return false;
                }
            }).orElse(false);
        }

        @Override
        public void tick() {
            if (this.timeToRecalcPath-- <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                this.fish.getNavigation().moveTo(leader, 1.0D);
            }
        }
    }

    public static class FindLeaderGoal extends Goal {

        private final Setapedites fish;
        private int nextStartTick;

        public FindLeaderGoal(Setapedites fish) {
            this.fish = fish;
            this.setFlags(EnumSet.of(Flag.MOVE));
            this.nextStartTick = this.nextStartTick(this.fish);
        }

        public boolean canUse() {
            if (this.fish.getLeader().isPresent()) {
                return false;
            } else if (this.nextStartTick > 0) {
                this.nextStartTick--;
                return false;
            } else {
                this.nextStartTick = this.nextStartTick(this.fish);
                return true;
            }
        }

        protected int nextStartTick(Setapedites taskOwner) {
            return reducedTickDelay(200 + taskOwner.getRandom().nextInt(200) % 20);
        }

        public void start() {
            List<Entity> nearby = this.fish.level().getEntitiesOfClass(Entity.class, (new AABB(this.fish.blockPosition())).inflate(12.0F), (entity) -> entity.isAlive() && entity.getType() == UP2Entities.AEGIROCASSIS.get() && entity != this.fish);
            if (!nearby.isEmpty()) {
                this.fish.setLeader(Util.getRandom(nearby, this.fish.random));
                List<Setapedites> otherFish = this.fish.level().getEntitiesOfClass(Setapedites.class, (new AABB(this.fish.blockPosition())).inflate(24.0F), (entity) -> entity.isAlive() && entity != this.fish && this.fish.leaderUUID == null);
                otherFish.forEach((fish1) -> fish1.setLeader(this.fish.getLeader().orElse(null)));
            }
        }
    }
}
