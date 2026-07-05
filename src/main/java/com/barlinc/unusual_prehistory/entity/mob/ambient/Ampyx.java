package com.barlinc.unusual_prehistory.entity.mob.ambient;

import com.barlinc.unusual_prehistory.entity.mob.base.AmbientMob;
import com.barlinc.unusual_prehistory.entity.mob.base.AmbientWaterCrawlingMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Ampyx extends AmbientWaterCrawlingMob {

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState(1.0F);

    public Ampyx(EntityType<? extends AmbientMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.5F, 1.0F, false);
        this.lookControl = new SmoothSwimmingLookControl(this, 20);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18F)
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
                    Ampyx entity = new Ampyx(UP2Entities.AMPYX.get(), this.level());
                    entity.moveTo(this.getX() + this.getRandom().nextFloat() * distance, this.getY() + this.getRandom().nextFloat() * distance, this.getZ() + this.getRandom().nextFloat() * distance);
                    this.level().addFreshEntity(entity);
                }
            }
        }
        return spawnData;
    }


}