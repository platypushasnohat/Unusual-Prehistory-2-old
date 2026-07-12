package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.*;
import com.barl_inc.unusual_prehistory.network.MountedEntityKeyPacket;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2LoadedMods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Ulughbegsaurus extends PrehistoricMob implements KeybindUsingMount, PlayerRideableJumping, LeapingMob, TargetsItems {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Ulughbegsaurus.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(Ulughbegsaurus.class, EntityDataSerializers.INT);

    private boolean leapImpulse;

    private int attackCooldown = 0;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState jumpAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState blinkAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState eatAnimationState = new SmoothAnimationState();

    private int attackTicks;
    private boolean attackAlt = false;

    private final byte EAT = 67;

    public Ulughbegsaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricSitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new PrehistoricBabyPanicGoal(this, 1.7D, 10, 4));
        this.goalSelector.addGoal(3, new UlughbegsaurusAttackGoal(this));
        this.goalSelector.addGoal(4, new PrehistoricFollowOwnerGoal(this, 1.2D, 1.7D, 7.0F, 4.0F));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.1D, Ingredient.of(UP2ItemTags.DIET_CARNIVORE), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new PrehistoricWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new EepyGoal(this));
        this.goalSelector.addGoal(10, new IdleAnimationGoal(this, 20, 1, false, 0.001F));
        this.goalSelector.addGoal(10, new IdleAnimationGoal(this, 80, 2, false, 0.001F, this::canShake));
        this.goalSelector.addGoal(10, new IdleAnimationGoal(this, 60, 3, false, 0.001F, this::canYawn));
        this.targetSelector.addGoal(0, new TargetItemsGoal(this, true));
        this.targetSelector.addGoal(1, new PrehistoricOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new PrehistoricOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.ULUGHBEGSAURUS_TARGETS)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.JUMP_STRENGTH, 0.5D)
                .add(Attributes.STEP_HEIGHT, 1.5D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_CARNIVORE);
    }

    @Override
    public boolean canTargetItems(ItemStack stack) {
        return this.isFood(stack);
    }

    @Override
    public void onGetItem(ItemEntity item) {
        if (this.getNavigation().getPath() != null) {
            this.getNavigation().stop();
        }
        this.setEatTicks(60);
        this.setEatCooldown(this.getEatCooldown() + 60);
        this.level().broadcastEntityEvent(this, EAT);
        this.eatItem(this, item.getItem());
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);
        if (!this.isTame() && itemStack.is(UP2ItemTags.TAMES_ULUGHBEGSAURUS) && this.getEatTicks() <= 0) {
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                this.level().broadcastEntityEvent(this, EAT);
                this.playSound(this.getEatingSound(), 1.0F, 0.9F + this.getRandom().nextFloat() * 0.2F);
                if (this.getNavigation().getPath() != null) {
                    this.getNavigation().stop();
                }
                if (this.getTameAttempts() > 3 && this.getRandom().nextBoolean()) {
                    this.level().broadcastEntityEvent(this, (byte) 7);
                    this.tame(player);
                    this.setTameAttempts(0);
                    this.heal(this.getMaxHealth());
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                    this.setTameAttempts(this.getTameAttempts() + 1);
                }
            } else {
                this.spawnEatingParticles(itemStack);
            }
            this.setEatTicks(60);
            return InteractionResult.SUCCESS;
        }
        else {
            return type;
        }
    }

    @Override
    public boolean canOwnerCommand(Player player, @NotNull InteractionHand hand) {
        return player.isShiftKeyDown();
    }

    @Override
    public boolean canOwnerMount(Player player, @NotNull InteractionHand hand) {
        return !this.isBaby() && !player.isShiftKeyDown();
    }

    private void spawnEatingParticles(ItemStack itemStack) {
        for (int i = 0; i < 8; i++) {
            Vec3 eatPos = this.getEyePosition().add(this.getViewVector(0.0F).scale(2.0F).add(0, 0.1F, -this.getBbWidth() * 0.7F));
            Vec3 vec3 = (new Vec3((this.getRandom().nextFloat() - 0.5F) * 0.1F, this.getRandom().nextFloat() * 0.1F + 0.1F, 0.0F)).xRot(-this.getXRot() * ((float) Math.PI / 180F)).yRot(-this.getYRot() * ((float) Math.PI / 180F));
            this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemStack), eatPos.x, eatPos.y, eatPos.z, vec3.x, vec3.y + 0.05, vec3.z);
        }
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        this.floatWhileRidden(this, travelVec);
        super.travel(travelVec);
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, -this.getBbHeight() * 0.4F, this.getBbWidth() * 1.55F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    // Riding
    @Override
    protected float getRiddenSpeed(@NotNull Player rider) {
        float sprintSpeed = rider.isSprinting() ? 0.1F : 0.0F;
        return ((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.55F) + sprintSpeed;
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    @Override
    public void onKeyPacket(Entity keyPresser, int type) {
        if (keyPresser.isPassengerOfSameVehicle(this)) {
            if (type == 3) {
                if (this.getPose() == Pose.STANDING) {
                    this.setYHeadRot(keyPresser.getYHeadRot());
                    this.setXRot(keyPresser.getXRot());
                    this.attackAlt = this.getRandom().nextBoolean();
                    this.setPose(UP2Poses.ATTACKING.get());
                }
            }
        }
    }

    private void tickPlayerBite() {
        if (this.attackCooldown == 0) {
            if (!level().isClientSide) {
                if (this.getPose() == UP2Poses.ATTACKING.get() && this.hasControllingPassenger()) {
                    if (attackTicks == 7)
                        this.level().playSound(null, this, UP2SoundEvents.ULUGHBEGSAURUS_ATTACK.get(), SoundSource.PLAYERS, 1.0F, 0.9F + this.getRandom().nextFloat() * 0.2F);
                    if (attackTicks <= 6 && attackTicks > 4) {
                        this.biteNearbyEntities();
                    }
                }
            } else {
                Player player = UnusualPrehistory2.PROXY.getClientSidePlayer();
                if (player != null && player.isPassengerOfSameVehicle(this)) {
                    if (UnusualPrehistory2.PROXY.isKeyDown(3) && this.getPose() != UP2Poses.ATTACKING.get()) {
                        PacketDistributor.sendToServer(new MountedEntityKeyPacket(this.getId(), player.getId(), 3));
                    }
                }
            }
        }
    }

    private void biteNearbyEntities() {
        List<LivingEntity> nearbyEntities = this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this, this.getBoundingBox().inflate(2.3));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.getFirst();
            if (!entity.is(this) && !this.isAlliedTo(entity)) {
                this.doHurtTarget(entity);
            }
        }
    }

    @Override
    public void onPlayerJump(int jumpPower) {
        this.setLeaping(true);
        if (this.onGround()) {
            this.leapImpulse = true;
            float f = 0.05F + jumpPower * 0.01F;
            float jump = f * this.getBlockJumpFactor() + this.getJumpBoostPower();
            Vec3 jumpForwards = new Vec3(0F, jump * 0.9F, this.zza).yRot((float) Math.toRadians(-this.yBodyRot));
            this.setDeltaMovement(this.getDeltaMovement().add(jumpForwards));
        }
    }

    @Override
    public boolean canJump() {
        return !this.isLeaping() && !this.isInWaterOrBubble();
    }

    @Override
    public void handleStartJump(int jumpPower) {
    }

    @Override
    public void handleStopJump() {
    }

    @Override
    public int getMaxFallDistance() {
        return super.getMaxFallDistance() + 6;
    }

    @Override
    public void tick() {
        super.tick();
        this.tickPlayerBite();

        if ((this.onGround() || this.isInWaterOrBubble()) && this.isLeaping() && !leapImpulse) {
            this.setLeaping(false);
        }
        if (leapImpulse) {
            this.leapImpulse = false;
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackTicks > 0) attackTicks--;
        if (attackTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) {
            this.attackCooldown = 2 + this.getRandom().nextInt(2);
            this.setPose(Pose.STANDING);
        }
        if (attackCooldown > 0) attackCooldown--;
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy() && !this.isSitting() && !this.isInWaterOrBubble(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.jumpAnimationState.animateWhen(this.isLeaping(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.sitAnimationState.animateWhen(this.isSitting(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.blinkAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == 3, this.tickCount);
    }

    public void handleEntityEvent(byte id) {
        if (id == EAT) {
            this.eatAnimationState.start(this.tickCount);
        }
        else {
            super.handleEntityEvent(id);
        }
    }

    private boolean canShake(Entity entity) {
        return !entity.isInWaterOrBubble() && !((Ulughbegsaurus) entity).isSitting();
    }

    private boolean canYawn(Entity entity) {
        return !entity.isInWaterOrBubble();
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (DATA_POSE.equals(key)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.attackTicks = 15;
                this.attackAlt = this.getRandom().nextBoolean();
            }
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TAME_ATTEMPTS, 0);
        builder.define(LEAPING, false);
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

    public void setTameAttempts(int tameAttempts) {
        this.entityData.set(TAME_ATTEMPTS, tameAttempts);
    }

    public int getTameAttempts() {
        return this.entityData.get(TAME_ATTEMPTS);
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.ULUGHBEGSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.ULUGHBEGSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.ULUGHBEGSAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.ULUGHBEGSAURUS_STEP.get(), this.isBaby() ? 0.3F : 1.0F, this.isBaby() ? 1.2F : 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        Ulughbegsaurus baby = UP2Entities.ULUGHBEGSAURUS.get().create(level);
        if (baby != null) {
            baby.setVariantRawId(this.inheritVariantFrom(mob, this.getRandom()));
        }
        return baby;
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/ulughbegsaurus/ulughbegsaurus.png");
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.pickVariantForSpawn(level);
        return data;
    }

    // Goals
    private static class UlughbegsaurusAttackGoal extends AttackGoal {

        protected final Ulughbegsaurus ulughbegsaurus;

        public UlughbegsaurusAttackGoal(Ulughbegsaurus ulughbegsaurus) {
            super(ulughbegsaurus);
            this.ulughbegsaurus = ulughbegsaurus;
        }

        @Override
        public void tick() {
            LivingEntity target = ulughbegsaurus.getTarget();
            if (target != null) {
                double distance = ulughbegsaurus.distanceToSqr(target);
                this.ulughbegsaurus.lookAt(ulughbegsaurus.getTarget(), 30F, 30F);
                this.ulughbegsaurus.getLookControl().setLookAt(target, 30F, 30F);

                if (this.ulughbegsaurus.getAttackState() == 1) {
                    this.ulughbegsaurus.getNavigation().moveTo(target, 1.5D);
                    this.tickBite(target);
                } else {
                    this.ulughbegsaurus.getNavigation().moveTo(target, 1.7D);
                    if (distance <= this.getAttackReachSqr(target) && ulughbegsaurus.attackCooldown == 0) {
                        this.ulughbegsaurus.setAttackState(1);
                    }
                }
            }
        }

        protected void tickBite(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.ulughbegsaurus.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 8) ulughbegsaurus.playSound(UP2SoundEvents.ULUGHBEGSAURUS_ATTACK.get(), 1.0F, 0.9F + ulughbegsaurus.getRandom().nextFloat() * 0.2F);
            if (timer == 10) {
                if (this.isInAttackRange(target, 1.8D)) {
                    this.ulughbegsaurus.doHurtTarget(target);
                }
            }
            if (timer > 15) {
                this.timer = 0;
                this.ulughbegsaurus.setPose(Pose.STANDING);
                this.ulughbegsaurus.setAttackState(0);
            }
        }
    }
}
