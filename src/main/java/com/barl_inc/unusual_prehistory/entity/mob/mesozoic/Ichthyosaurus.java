package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothWaterNavigation;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricSchoolingAquaticMob;
import com.barl_inc.unusual_prehistory.entity.utils.LeapingMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Ichthyosaurus extends PrehistoricSchoolingAquaticMob implements LeapingMob, PlayerRideableJumping {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Ichthyosaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DASHING = SynchedEntityData.defineId(Ichthyosaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(Ichthyosaurus.class, EntityDataSerializers.INT);

    private int dashCooldown = 0;
    protected float playerJumpPendingScale;

    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState roll1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState roll2AnimationState = new SmoothAnimationState(1.0F);

    private boolean rollAlt = false;

    public Ichthyosaurus(EntityType<? extends PrehistoricSchoolingAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 1000, 10, 0.04F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.1F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 2.0D, 16, 8));
        this.goalSelector.addGoal(2, new AquaticLeapGoal(this, 10, 0.8D, 0.9D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new SwimWithPlayerGoal(this));
        this.goalSelector.addGoal(5, new PrehistoricSwimGoal(this, 1.0D, 10, 30, 15, 3, true));
        this.goalSelector.addGoal(6, new PrehistoricFollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new FollowVariantLeaderGoal(this));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(9, new FollowBoatGoal(this));
        this.goalSelector.addGoal(10, new IdleAnimationGoal(this, 20, 1, false, 0.001F, this::canPlayIdles) {
            @Override
            public void start() {
                super.start();
                Ichthyosaurus.this.getNavigation().stop();
                Ichthyosaurus.this.rollAlt = Ichthyosaurus.this.getRandom().nextBoolean();
                Ichthyosaurus.this.addDeltaMovement(Ichthyosaurus.this.calculateViewVector(0.0F, Ichthyosaurus.this.getYRot()).scale(2.0D).multiply(0.5D, Ichthyosaurus.this.getDeltaMovement().y * 0.5D, 0.5D));
                Ichthyosaurus.this.makeSound(UP2SoundEvents.ICHTHYOSAURUS_DASH.get());
            }
        });
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isControlledByLocalInstance() && this.getControllingPassenger() instanceof Player player && this.isInWater()) {
            this.moveRelative(this.getRiddenSpeed(player), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            this.calculateEntityAnimation(false);
        } else if (this.isInWater() && this.isEffectiveAi()) {
            UP2MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothWaterNavigation(this, level, true);
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return this.level().isNight() ? UP2MobUtils.getDepthPathfindingFavor(pos, level) : super.getWalkTargetValue(pos, level);
    }

    @Override
    public int getMaxSchoolSize() {
        return 6;
    }

    @Override
    public int getMaxHeadXRot() {
        return 1;
    }

    @Override
    public int getMaxHeadYRot() {
        return 1;
    }

    @Override
    protected void handleAirSupply(int airSupply) {
        this.setAirSupply(300);
    }

    private void applyPassengerEffects() {
        if (this.getFirstPassenger() instanceof Player player) {
            boolean shouldRefresh = this.level().getGameTime() % 40L == 0L;
            if (!player.hasEffect(MobEffects.WATER_BREATHING) || shouldRefresh) {
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 100, 0, true, false, false));
            }
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();

        if (!this.level().isClientSide && ((!this.isInWater() && this.onGround() && this.isVehicle()) || (this.isVehicle() && this.isBaby()))) {
            this.ejectPassengers();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.isInWater() && this.getDeltaMovement().lengthSqr() > 0.05D) {
                Vec3 viewVector = this.getViewVector(0.0F);
                this.level().addParticle(ParticleTypes.BUBBLE, this.getRandomX(0.5D) - viewVector.x * 0.8D, this.getRandomY() - viewVector.y * 0.25D, this.getRandomZ(0.5D) - viewVector.z * 0.8D, 0.0D, 0.0D, 0.0D);
            }
        } else {
            this.applyPassengerEffects();
        }

        if (this.isDashing() && this.dashCooldown < 60) {
            this.setDashing(false);
        }

        if (dashCooldown > 0) {
            this.dashCooldown--;
            if (this.dashCooldown == 0) {
                this.level().playSound(null, this.blockPosition(), UP2SoundEvents.ICHTHYOSAURUS_DASH_READY.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() || this.isLeaping() || this.isDashing(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isLeaping() && !this.isDashing() && !this.hasControllingPassenger(), this.tickCount);
        this.roll1AnimationState.animateWhen(this.shouldRoll() && !rollAlt, this.tickCount);
        this.roll2AnimationState.animateWhen(this.shouldRoll() && rollAlt, this.tickCount);
    }

    private boolean shouldRoll() {
        return this.getIdleState() == 1 || this.isDashing();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 400 + this.getRandom().nextInt(400);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    public boolean canPlayIdles(Entity entity) {
        return (entity.isInWaterOrBubble() || ((Ichthyosaurus) entity).isLeaping()) && !entity.hasControllingPassenger();
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!this.isVehicle() && !this.isFood(itemStack) && !this.isBaby() && !itemStack.is(Items.LEAD)) {
            player.startRiding(this);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean canJump() {
        return this.isInWater();
    }

    @Override
    public void onPlayerJump(int jumpAmount) {
        if (this.dashCooldown <= 0) {
            this.playerJumpPendingScale = this.getPlayerJumpPendingScale(jumpAmount);
        }
    }

    private float getPlayerJumpPendingScale(int jumpAmount) {
        return jumpAmount >= 90 ? 1.0F : 0.4F + 0.4F * (float) jumpAmount / 90.0F;
    }

    @Override
    public void handleStartJump(int jumpPower) {
        this.makeSound(UP2SoundEvents.ICHTHYOSAURUS_DASH.get());
        this.gameEvent(GameEvent.ENTITY_ACTION);
        this.setDashing(true);
    }

    @Override
    public void handleStopJump() {
    }

    protected void executeRidersJump(float amount, Player controller) {
        this.addDeltaMovement(controller.getLookAngle().scale((1.3F * amount) * this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (double) this.getBlockSpeedFactor()));
        this.dashCooldown = 80;
        this.setDashing(true);
        this.hasImpulse = true;
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(Player controller, @NotNull Vec3 selfInput) {
        float forwardLook = Mth.cos(controller.getXRot() * ((float) Math.PI / 180F));
        float upLook = -Mth.sin(controller.getXRot() * ((float) Math.PI / 180F));
        return new Vec3(0.0D, upLook, forwardLook);
    }

    @Override
    protected float getRiddenSpeed(@NotNull Player controller) {
        return this.isInWater() ? 0.07F * (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) : 0.0F;
    }

    @Override
    protected void tickRidden(@NotNull Player controller, @NotNull Vec3 travelVector) {
        super.tickRidden(controller, travelVector);
        Vec2 rotation = this.getRiddenRotation(controller);
        float yRot = this.getYRot();
        float diff = Mth.wrapDegrees(rotation.y - yRot);
        yRot += diff * 0.5F;
        this.setRot(yRot, rotation.x);
        this.yRotO = yBodyRot = yHeadRot = yRot;
        this.setXRot(controller.getXRot());
        if (this.isInWater()) {
            if (this.playerJumpPendingScale > 0.0F && !this.jumping) {
                this.executeRidersJump(this.playerJumpPendingScale, controller);
            }
            this.playerJumpPendingScale = 0.0F;
        }
    }

    @Override
    public int getJumpCooldown() {
        return dashCooldown;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (!this.firstTick && DASHING.equals(key)) {
            this.dashCooldown = this.dashCooldown == 0 ? 80 : this.dashCooldown;
            this.rollAlt = this.getRandom().nextBoolean();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LEAPING, false);
        builder.define(DASHING, false);
        builder.define(TAME_ATTEMPTS, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TameAttempts", this.getTameAttempts());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setTameAttempts(compoundTag.getInt("TameAttempts"));
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    public void setDashing(boolean dashing) {
        this.entityData.set(DASHING, dashing);
    }

    public boolean isDashing() {
        return this.entityData.get(DASHING);
    }

    public void setTameAttempts(int tameAttempts) {
        this.entityData.set(TAME_ATTEMPTS, tameAttempts);
    }

    public int getTameAttempts() {
        return this.entityData.get(TAME_ATTEMPTS);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.ICHTHYOSAURUS.get().create(level);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.ICHTHYOSAURUS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.ICHTHYOSAURUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.ICHTHYOSAURUS_DEATH.get();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.ICHTHYOSAURUS_FLOP.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData data) {
        this.setXRot(0.0F);
        return super.finalizeSpawn(level, difficulty, spawnType, data);
    }

    private static class SwimWithPlayerGoal extends Goal {

        private final Ichthyosaurus ichthyosaurus;
        @Nullable
        private Player player;

        private SwimWithPlayerGoal(Ichthyosaurus ichthyosaurus) {
            this.ichthyosaurus = ichthyosaurus;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            this.player = ichthyosaurus.level().getNearestPlayer(TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight(), ichthyosaurus);
            return ichthyosaurus.getRandom().nextInt(40) == 0 && player != null && ichthyosaurus.getTarget() != player && ichthyosaurus.getLastHurtByMob() == null && ichthyosaurus.getControllingPassenger() != player;
        }

        @Override
        public boolean canContinueToUse() {
            return player != null && ichthyosaurus.distanceToSqr(player) < 128 && ichthyosaurus.getLastHurtByMob() == null;
        }

        @Override
        public void stop() {
            this.player = null;
            this.ichthyosaurus.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (player != null) {
                this.ichthyosaurus.getLookControl().setLookAt(player, (float) (ichthyosaurus.getMaxHeadYRot() + 20), (float) ichthyosaurus.getMaxHeadXRot());
                if (ichthyosaurus.distanceToSqr(player) < 12) {
                    this.ichthyosaurus.getNavigation().stop();
                } else {
                    this.ichthyosaurus.getNavigation().moveTo(player, 1.5D);
                }
            }
        }
    }
}
