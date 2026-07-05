package com.barlinc.unusual_prehistory.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Anurognathus extends PrehistoricFlyingMob implements VariantHolder<Anurognathus.AnurognathusVariant> {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Anurognathus.class, EntityDataSerializers.INT);

    @Nullable
    private BlockPos hivePos;
    private int remainingCooldownBeforeLocatingNewHive;
    private int stayOutOfHiveCountdown;

    public Anurognathus(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.LEAVES, 0.0F);
        this.switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.225F)
                .add(Attributes.FLYING_SPEED, 0.65F)
                .add(Attributes.STEP_HEIGHT, 1.15D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FlyingPanicGoal(this, 1.8D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_INSECTIVORE), true));
        this.goalSelector.addGoal(3, new FlyingGroundWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LandFromFlightGoal(this, 200));
        this.goalSelector.addGoal(5, new FlyingWanderGoal(this, 1.0F, 7));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new EepyGoal(this));
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        if (this.isFlying()) {
            return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
        }
        return super.getWalkTargetValue(pos, level);
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    public boolean isEepyTime() {
        return !this.isFlying() && this.onGround() && this.level().isDay();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        return !effectInstance.is(MobEffects.POISON) && super.canBeAffected(effectInstance);
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, this.getBbHeight() * 0.5F, this.getBbWidth() * 0.4F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    private boolean wantsToEnterHive() {
        if (stayOutOfHiveCountdown <= 0 && this.getTarget() == null) {
            boolean flag = this.level().isRaining() || this.level().isDay();
            return flag && !this.isHiveNearFire();
        } else {
            return false;
        }
    }

    private boolean isHiveNearFire() {
        if (hivePos == null) {
            return false;
        } else {
            BlockEntity blockentity = this.level().getBlockEntity(hivePos);
            return blockentity instanceof BeehiveBlockEntity beehive && beehive.isFireNearby();
        }
    }

    private boolean isHiveValid() {
        if (!this.hasHive()) {
            return false;
        } else if (this.isTooFarAway(this.hivePos)) {
            return false;
        } else {
            BlockEntity blockentity = this.level().getBlockEntity(this.hivePos);
            return blockentity instanceof BeehiveBlockEntity;
        }
    }

    private boolean isTooFarAway(BlockPos pos) {
        return !pos.closerThan(this.blockPosition(), 32.0D);
    }

    public void setStayOutOfHiveCountdown(int stayOutOfHiveCountdown) {
        this.stayOutOfHiveCountdown = stayOutOfHiveCountdown;
    }

    private boolean doesHiveHaveSpace(BlockPos hivePos) {
        BlockEntity blockentity = this.level().getBlockEntity(hivePos);
        return blockentity instanceof BeehiveBlockEntity beehive && !beehive.isFull();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            if (this.stayOutOfHiveCountdown > 0) {
                this.stayOutOfHiveCountdown--;
            }

            if (this.remainingCooldownBeforeLocatingNewHive > 0) {
                this.remainingCooldownBeforeLocatingNewHive--;
            }

            if (this.tickCount % 20 == 0 && !this.isHiveValid()) {
                this.hivePos = null;
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlying() && !this.isEepy(), this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying() && !this.isRunning(), this.tickCount);
        this.flyFastAnimationState.animateWhen(this.isFlying() && this.isRunning(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_INSECTIVORE);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant().getId());
        if (this.hasHive() && this.getHivePos() != null) {
            compoundTag.put("hive_pos", NbtUtils.writeBlockPos(this.getHivePos()));
        }
        compoundTag.putInt("CannotEnterHiveTicks", this.stayOutOfHiveCountdown);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(AnurognathusVariant.byId(compoundTag.getInt("Variant")));
        this.hivePos = NbtUtils.readBlockPos(compoundTag, "hive_pos").orElse(null);
        this.stayOutOfHiveCountdown = compoundTag.getInt("CannotEnterHiveTicks");
    }

    @Override
    public @NotNull Anurognathus.AnurognathusVariant getVariant() {
        return AnurognathusVariant.byId(this.entityData.get(VARIANT));
    }

    @Override
    public void setVariant(AnurognathusVariant variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant.getId(), 0, AnurognathusVariant.values().length));
    }

    @VisibleForDebug
    public boolean hasHive() {
        return this.hivePos != null;
    }
    @Nullable
    @VisibleForDebug
    public BlockPos getHivePos() {
        return this.hivePos;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.ANUROGNATHUS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.ANUROGNATHUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.ANUROGNATHUS_DEATH.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        Anurognathus anurognathus = UP2Entities.ANUROGNATHUS.get().create(level);
        if (anurognathus != null) {
            anurognathus.setVariant(this.getVariant());
        }
        return anurognathus;
    }

    public enum AnurognathusVariant {
        GRASS(0),
        HAWK(1),
        NIGHTBIRD(2),
        JUNGLE(3),
        SPIDER(4);

        private final int id;

        AnurognathusVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static AnurognathusVariant byId(int id) {
            if (id < 0 || id >= AnurognathusVariant.values().length) {
                id = 0;
            }
            return AnurognathusVariant.values()[id];
        }
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
        this.setVariant(AnurognathusVariant.byId(level.getRandom().nextInt(AnurognathusVariant.values().length)));
        return spawnData;
    }
}