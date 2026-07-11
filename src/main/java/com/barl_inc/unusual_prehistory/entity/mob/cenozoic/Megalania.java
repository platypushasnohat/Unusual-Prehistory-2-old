package com.barl_inc.unusual_prehistory.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.ai.goals.update_1.MegalaniaAttackGoal;
import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class Megalania extends PrehistoricAmphibiousMob {

    private static final EntityDataAccessor<Integer> TEMPERATURE_STATE = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREV_TEMPERATURE_STATE = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(Megalania.class, EntityDataSerializers.INT);

    public enum TemperatureStates {
        TEMPERATE,
        COLD,
        WARM,
        NETHER
    }

    public TemperatureStates localTemperatureState = TemperatureStates.TEMPERATE;
    public float tempProgress = 0F;
    public float prevTempProgress = 0F;

    @Nullable
    private PrehistoricWanderGoal randomStrollGoal;

    public int attackCooldown = 0;
    public int talWhipCooldown = 150;

    public final SmoothAnimationState tongueAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState roarAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState tailWhipAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState aggroAnimationState = new SmoothAnimationState();

    public boolean attackAlt = false;

    public Megalania(EntityType<? extends Megalania> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(true);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.randomStrollGoal = new AmphibiousWanderGoal(this, 1.0D);
        this.goalSelector.addGoal(0, new PrehistoricSitWhenOrderedToGoal(this, false));
        this.goalSelector.addGoal(1, new MegalaniaAttackGoal(this));
        this.goalSelector.addGoal(2, new PrehistoricFollowOwnerGoal(this, 1.2D, 1.8D, 7.0F, 4.0F, false));
        this.goalSelector.addGoal(3, new PrehistoricBabyPanicGoal(this, 1.6D, 10, 4));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_CARNIVORE), false));
        this.goalSelector.addGoal(5, new LeaveWaterGoal(this, 1.0D, 3000));
        this.goalSelector.addGoal(6, new PrehistoricSwimGoal(this, 1.0D, 50, 10, 5));
        this.goalSelector.addGoal(6, this.randomStrollGoal);
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new EepyGoal(this));
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 20, 1, false, 0.01F));
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 80, 2, true, 0.001F, this::canRoar) {
            @Override
            public void tick() {
                super.tick();
                if (timer == 61) Megalania.this.playSound(UP2SoundEvents.MEGALANIA_ROAR.get(), 1.5F, Megalania.this.getVoicePitch());
            }
        });
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new PrehistoricOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 200, true, false, this::isHostileToPlayers));
        this.targetSelector.addGoal(4, new MegalaniaTargetGoal<>(this, LivingEntity.class));
        this.targetSelector.addGoal(5, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, true, this::isHostileToEverything));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 38.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.16F)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothAmphibiousNavigation(this, level);
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new PrehistoricMoveControl(this);
            this.lookControl = new PrehistoricLookControl(this);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.6F);
            this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
            this.isLandNavigator = false;
        }
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            if (this.onGround()) {
                travelVec = travelVec.multiply(0.0, 1.0, 0.0);
            } else if (this.isInWaterOrBubble()) {
                travelVec = travelVec.multiply(0.0, 0.0, 0.0);
            }
        }
        if (this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVec);
            this.calculateEntityAnimation(false);
        } else {
            super.travel(travelVec);
        }
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isInWaterOrBubble() ? 1 : super.getMaxHeadXRot();
    }

    @Override
    public int getMaxHeadYRot() {
        return this.isInWaterOrBubble() ? 1 : super.getMaxHeadXRot();
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);

        if (!this.isTame() && itemstack.is(UP2ItemTags.TAMES_MEGALANIA)) {
            this.gameEvent(GameEvent.ENTITY_INTERACT);
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                if (this.getTameAttempts() > 4 && this.getRandom().nextBoolean()) {
                    this.level().broadcastEntityEvent(this, (byte) 7);
                    this.tame(player);
                    this.heal(this.getMaxHealth());
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                    this.setTameAttempts(this.getTameAttempts() + 1);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return type;
    }

    public boolean isHostileToPlayers(LivingEntity entity) {
        return this.canAttack(entity) && (this.getTemperatureState() == TemperatureStates.WARM || this.getTemperatureState() == TemperatureStates.NETHER);
    }

    public boolean isHostileToEverything(LivingEntity entity) {
        return this.canAttack(entity) && this.getTemperatureState() == TemperatureStates.NETHER;
    }

    private int getHuntingInterval() {
        switch (this.getTemperatureState()) {
            case COLD -> {
                return 500;
            }
            case WARM -> {
                return 200;
            }
            case NETHER -> {
                return 100;
            }
            default -> {
                return 300;
            }
        }
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.7D, 0.3D, this.getBbWidth() * 0.9F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_CARNIVORE);
    }

    @Override
    public boolean canOwnerCommand(Player player, @NotNull InteractionHand hand) {
        return true;
    }

    public void applyPoison(@NotNull LivingEntity entity) {
        float chance = 0;
        int i = 0;

        if (this.level().getDifficulty() == Difficulty.NORMAL) i = 5;
        else if (this.level().getDifficulty() == Difficulty.HARD) i = 10;

        if (this.getTemperatureState() == TemperatureStates.COLD) chance = -0.1F;
        else if (this.getTemperatureState() == TemperatureStates.WARM) chance = 0.1F;
        else if (this.getTemperatureState() == TemperatureStates.NETHER) chance = 0.2F;

        if (i > 0 && this.getRandom().nextFloat() < 0.3F + chance) {
            entity.addEffect(new MobEffectInstance(this.getTemperatureState() == TemperatureStates.NETHER ? MobEffects.WITHER : MobEffects.POISON, i * 40, 0), this);
        }
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        return !effectInstance.is(MobEffects.POISON) && !effectInstance.is(MobEffects.WITHER) && super.canBeAffected(effectInstance);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TEMPERATURE_STATE, 0);
        builder.define(PREV_TEMPERATURE_STATE, -1);
        builder.define(TAME_ATTEMPTS, 0);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TemperatureState", this.getTemperatureState().ordinal());
        compoundTag.putInt("TameAttempts", this.getTameAttempts());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(TEMPERATURE_STATE, compoundTag.getInt("TemperatureState"));
        this.setTameAttempts(compoundTag.getInt("TameAttempts"));
    }

    public TemperatureStates getTemperatureState() {
        return TemperatureStates.values()[Mth.clamp(entityData.get(TEMPERATURE_STATE), 0, 3)];
    }

    public void setTemperatureState(TemperatureStates state) {
        if (this.getTemperatureState() != state) {
            this.entityData.set(PREV_TEMPERATURE_STATE, this.entityData.get(TEMPERATURE_STATE));
        }
        this.entityData.set(TEMPERATURE_STATE, state.ordinal());
    }

    public TemperatureStates getPrevTemperatureState() {
        if (entityData.get(PREV_TEMPERATURE_STATE) == -1) {
            return null;
        }
        return TemperatureStates.values()[Mth.clamp(entityData.get(PREV_TEMPERATURE_STATE), 0, 3)];
    }

    public void setTameAttempts(int tameAttempts) {
        this.entityData.set(TAME_ATTEMPTS, tameAttempts);
    }

    public int getTameAttempts() {
        return this.entityData.get(TAME_ATTEMPTS);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 2;
    }

    @Override
    public void tick() {
        super.tick();

        final boolean ground = !this.isInWaterOrBubble();
        if (!ground && this.isLandNavigator) {
            this.switchNavigator(false);
        }
        if (ground && !this.isLandNavigator) {
            this.switchNavigator(true);
        }

        this.tickTemperatureStates();

        if (!this.level().isClientSide) {
            if (attackCooldown > 0) attackCooldown--;
            if (talWhipCooldown > 0 && !this.isInWaterOrBubble()) talWhipCooldown--;
        }
    }

    private void tickTemperatureStates() {
        if (this.level().getBiome(this.blockPosition()).value().getBaseTemperature() >= 1.0F) {
            if (this.level().dimension() == Level.NETHER) this.setTemperatureState(TemperatureStates.NETHER);
            else this.setTemperatureState(TemperatureStates.WARM);
        }
        else if (this.level().getBiome(this.blockPosition()).value().getBaseTemperature() <= 0.0F) {
            this.setTemperatureState(TemperatureStates.COLD);
        }
        else {
            this.setTemperatureState(TemperatureStates.TEMPERATE);
        }
        this.prevTempProgress = tempProgress;
        if (localTemperatureState != this.getPrevTemperatureState()) {
            this.localTemperatureState = this.getPrevTemperatureState();
            this.tempProgress = 0.0F;
        }
        if (this.getPrevTemperatureState() != this.getTemperatureState() && tempProgress < 5F) tempProgress += 0.4F;
        if (this.getPrevTemperatureState() == this.getTemperatureState() && tempProgress > 0F) tempProgress -= 0.4F;
    }

    public float getTempProgress(float partialTicks) {
        return (prevTempProgress + (tempProgress - prevTempProgress) * partialTicks) * 0.2F;
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && this.getIdleState() != 2 && this.getPose() != UP2Poses.TAIL_WHIPPING.get() && !this.isSitting() && !this.isEepy(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.aggroAnimationState.animateWhen(this.isAggressive() && this.getPose() == Pose.STANDING, this.tickCount);
        this.sitAnimationState.animateWhen(!this.isInWaterOrBubble() && this.isSitting(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.tongueAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.roarAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.tailWhipAnimationState.animateWhen(this.getPose() == UP2Poses.TAIL_WHIPPING.get(), this.tickCount);
    }

    private boolean canRoar(Entity entity) {
        return !entity.isInWaterOrBubble();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 150 + this.getRandom().nextInt(200);
        }
        else if (idleState == 2) {
            return 1100 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (TEMPERATURE_STATE.equals(key)) {
            if (this.getTemperatureState().equals(TemperatureStates.COLD)) {
                Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.14F);
                Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(10.0D);
                if (this.randomStrollGoal != null) {
                    this.randomStrollGoal.setInterval(200);
                }
            }
            else if (this.getTemperatureState().equals(TemperatureStates.TEMPERATE)) {
                Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.18F);
                Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(16.0D);
                if (this.randomStrollGoal != null) {
                    this.randomStrollGoal.setInterval(120);
                }
            }
            else if (this.getTemperatureState().equals(TemperatureStates.WARM)) {
                Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.2F);
                Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(20.0D);
                if (this.randomStrollGoal != null) {
                    this.randomStrollGoal.setInterval(80);
                }
            }
            else if (this.getTemperatureState().equals(TemperatureStates.NETHER)) {
                Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.3F);
                Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(32.0D);
                if (this.randomStrollGoal != null) {
                    this.randomStrollGoal.setInterval(50);
                }
            }
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.MEGALANIA.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.MEGALANIA_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.MEGALANIA_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.MEGALANIA_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.MEGALANIA_STEP.get(), 0.6F, 1.0F);
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor levelAccessor, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        this.entityData.set(PREV_TEMPERATURE_STATE, 0);
        this.setTemperatureState(TemperatureStates.TEMPERATE);
        return super.finalizeSpawn(levelAccessor, difficulty, spawnType, spawnData);
    }

    // Goals
    private static class MegalaniaTargetGoal<T extends LivingEntity> extends PrehistoricNearestAttackableTargetGoal<T> {

        private final Megalania megalania;

        public MegalaniaTargetGoal(Megalania megalania, Class<T> targetClass) {
            super(megalania, targetClass, 0, true, true, entity -> entity.getType().is(UP2EntityTags.MEGALANIA_TARGETS));
            this.megalania = megalania;
        }

        @Override
        public boolean canUse() {
            return this.getInterval() && !prehistoricMob.isPacified() && !prehistoricMob.isBaby();
        }

        private boolean getInterval() {
            if (megalania.getHuntingInterval() > 0 && megalania.getRandom().nextInt(megalania.getHuntingInterval()) != 0) {
                return false;
            } else {
                this.findTarget();
                return this.target != null;
            }
        }
    }
}