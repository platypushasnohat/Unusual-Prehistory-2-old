package com.barl_inc.unusual_prehistory.entity.mob.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public abstract class AmbientMob extends PathfinderMob {

    protected static final EntityDataAccessor<Integer> DESPAWN_TIME = SynchedEntityData.defineId(AmbientMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> SHOULD_BE_RESTRICTED = SynchedEntityData.defineId(AmbientMob.class, EntityDataSerializers.BOOLEAN);

    protected AmbientMob(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected @NotNull MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    protected void doWaterSplashEffect() {
    }

    @Override
    protected void doPush(@NotNull Entity entity) {
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource damageSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public boolean canTrample(@NotNull BlockState state, @NotNull BlockPos pos, float fallDistance) {
        return false;
    }

    @Override
    public int getBaseExperienceReward() {
        return 0;
    }

    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    @Override
    protected boolean shouldDropLoot() {
        return false;
    }

    @Override
    protected float getSoundVolume() {
        return 0.25F;
    }

    @Override
    public void tick () {
        super.tick();
        boolean persistent = this.requiresCustomPersistence() || this.isPersistenceRequired();
        if (!level().isClientSide) {
            if (!persistent) {
                if (this.getDespawnTime() > 0) this.setDespawnTime(this.getDespawnTime() - 1);
                else discard();
            } else if (this.getDespawnTime() > 0) {
                this.setDespawnTime(0);
            }
            if (this.shouldBeRestricted()) {
                if (!this.hasRestriction() && !persistent) restrictTo(this.blockPosition(), 16);
                else if (persistent) {
                    this.clearRestriction();
                    this.setShouldBeRestricted(false);
                }
            }
        } else {
            this.setupAnimationStates();
        }
    }

    public void setupAnimationStates() {
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float pos = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float speed = Math.min(pos * this.getWalkAnimationSpeed(), 1.0F);
        this.walkAnimation.update(speed, 0.4F);
    }

    public float getWalkAnimationSpeed() {
        return 10.0F;
    }

    // Data
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHOULD_BE_RESTRICTED, false);
        builder.define(DESPAWN_TIME, 2400 + this.getRandom().nextInt(1200));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("ShouldBeRestricted", this.shouldBeRestricted());
        compoundTag.putInt("DespawnTime", this.getDespawnTime());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setShouldBeRestricted(compoundTag.getBoolean("ShouldBeRestricted"));
        this.setDespawnTime(compoundTag.getInt("DespawnTime"));
    }

    public int getDespawnTime() {
        return this.entityData.get(DESPAWN_TIME);
    }

    public void setDespawnTime(int despawnTime) {
        this.entityData.set(DESPAWN_TIME, despawnTime);
    }

    public boolean shouldBeRestricted() {
        return this.entityData.get(SHOULD_BE_RESTRICTED);
    }

    public void setShouldBeRestricted(boolean restricted) {
        this.entityData.set(SHOULD_BE_RESTRICTED, restricted);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.setDespawnTime(2400 + this.getRandom().nextInt(1200));
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }
}
