package com.barlinc.unusual_prehistory.entity.mob.base;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricBodyRotationControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundNavigation;
import com.barlinc.unusual_prehistory.entity.utils.PrehistoricMobInteractions;
import com.barlinc.unusual_prehistory.entity.utils.PrehistoricRideableMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2Particles;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class PrehistoricMob extends TamableAnimal implements PrehistoricRideableMob, PrehistoricMobInteractions {

    protected static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> IDLE_STATE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Boolean> FROM_EGG = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> SHOT_FROM_OOZE = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Integer> EAT_COOLDOWN = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> EAT_TICKS = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Boolean> AGE_LOCKED = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Integer> EEPY_COOLDOWN = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> EEPY = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Integer> SIT_COOLDOWN = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> SITTING_TICKS = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.INT);

    protected static final EntityDataAccessor<Boolean> PACIFIED = SynchedEntityData.defineId(PrehistoricMob.class, EntityDataSerializers.BOOLEAN);

    protected int eepyTicks;

    protected float bodyYaw;
    protected float prevBodyYaw;
    protected float tailYaw;
    protected float prevTailYaw;

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState swimAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState eepyAnimationState = new SmoothAnimationState(0.25F);
    public final SmoothAnimationState sitAnimationState = new SmoothAnimationState(0.25F);
    public final SmoothAnimationState eatAnimationState = new SmoothAnimationState(1.0F);

    protected int idleAnimationCooldown;

    protected PrehistoricMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricMoveControl(this);
        this.lookControl = new PrehistoricLookControl(this);
        this.setPersistenceRequired();
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
    public boolean isPushable() {
        return !this.isEepy();
    }

    @Override
    public boolean killedEntity(ServerLevel level, LivingEntity victim) {
        this.heal(this.getMaxHealth() * this.getKilledEntityHealMultiplier());
        return super.killedEntity(level, victim);
    }

    public float getKilledEntityHealMultiplier() {
        return 0.2F;
    }

    // Navigation
    @Override
    protected BodyRotationControl createBodyControl() {
        return new PrehistoricBodyRotationControl(this);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothGroundNavigation(this, level);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0.0F;
    }

    // Floating
    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && this.horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.6D * this.getBbHeight();
    }

    // Mating
    @Override
    public boolean canFallInLove() {
        return false;
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        return false;
    }

    // Persistence
    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.requiresCustomPersistence();
    }

    // Mob interactions
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        InteractionResult result = super.mobInteract(player, hand);
        if (this.isFood(itemStack) && this.getEatTicks() <= 0) {
            if (this.getHealth() < this.getMaxHealth()) {
                this.healMob(this, player, hand);
                return InteractionResult.SUCCESS;
            }
            else if (this.isBaby()) {
                this.increaseMobAge(this, player, hand);
                return InteractionResult.SUCCESS;
            }
        }
        if (this.isTame() && this.isOwnedBy(player) && !this.isFood(itemStack) && !result.consumesAction()) {
            if (this.canOwnerCommand(player, hand)) {
                this.cycleMobCommands(this, player);
                return InteractionResult.SUCCESS;
            }
            else if (this.canOwnerMount(player, hand) && !this.level().isClientSide && player.startRiding(this)) {
                this.ownerStartRidingMob(this, player);
                return InteractionResult.CONSUME;
            }
        }
        if (itemStack.is(UP2ItemTags.STOPS_MOB_AGING) && this.isBaby()) {
            this.lockMobAge(this, player, hand);
            return InteractionResult.SUCCESS;
        }
        if (player.isCreative() && !this.isPacified() && itemStack.is(Items.ENCHANTED_GOLDEN_APPLE)) {
            this.pacifyMob(this, player, hand);
            return InteractionResult.SUCCESS;
        }
        return result;
    }

    public boolean canOwnerMount(Player player, InteractionHand hand) {
        return false;
    }

    public boolean canOwnerCommand(Player ownerPlayer, InteractionHand hand) {
        return false;
    }

    // Entity events
    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case 10 -> this.spawnPacifyParticles();
            case 11 -> this.spawnHeart();
            case 45 -> this.spawnEatParticlesFromMainhand();
            default -> super.handleEntityEvent(id);
        }
    }

    protected void spawnHeart() {
        this.level().addParticle(ParticleTypes.HEART, this.getX(), this.getEyeY() + 0.5F, this.getZ(), 0, 0, 0);
    }

    protected void spawnPacifyParticles() {
        for (int i = 0; i < 8; i++) {
            double xSpeed = this.random.nextGaussian() * 0.02D;
            double ySpeed = this.random.nextGaussian() * 0.08D;
            double zSpeed = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(UP2Particles.GOLDEN_HEART.get(), this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), xSpeed, ySpeed, zSpeed);
        }
    }

    protected void spawnEatParticlesFromMainhand() {
        ItemStack itemStack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!itemStack.isEmpty()) {
            for (int i = 0; i < 8; i++) {
                Vec3 headPos = this.getEatParticlePos();
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemStack), this.getX() + headPos.x, this.getY(0.5) + headPos.y, this.getZ() + headPos.z, (random.nextFloat() - 0.5F) * 0.1F, random.nextFloat() * 0.15F, (random.nextFloat() - 0.5F) * 0.1F);
            }
        }
    }

    protected Vec3 getEatParticlePos() {
        return (new Vec3(0, 0.8D, 1.1D)).xRot(-this.getXRot() * ((float) Math.PI / 180F)).yRot(-this.yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public void tick () {
        super.tick();

        this.tickCooldowns();

        if (this.level().isClientSide) {
            this.setupAnimationStates();
            if (this.shouldDoEepyParticles()) {
                this.doEepyParticles();
            }
        } else {
            if (this.canHealOverTime()) {
                this.heal(2);
            }
//            else if (this.isFood(this.getMainHandItem())) {
//                ItemStack stack = this.getMainHandItem();
//                this.level().broadcastEntityEvent(this, (byte) 45);
//                this.level().playSound(null, this.blockPosition(), this.getEatingSound(), SoundSource.NEUTRAL, 1.0F, 0.9F + this.getRandom().nextFloat() * 0.2F);
//                FoodProperties foodproperties = stack.getFoodProperties(this);
//                float healAmount = foodproperties != null ? (float) foodproperties.nutrition() : 2.0F;
//                this.heal(2.0F * healAmount);
//                stack.shrink(1);
//            }
        }

        if (this.isAgeLocked() && this.isBaby()) {
            this.setAge(-24000);
        }

        if (this.isLeashed()) {
            this.resetFallDistance();
        }

        if (this.wasShotFromOoze()) {
            if (!this.onGround() && !this.isInWaterOrBubble() && !this.onClimbable()) this.resetFallDistance();
            else this.setShotFromOoze(false);
        }
    }

    public void tickCooldowns() {
        if (this.isAlive() && !this.isNoAi()) {
            if (this.getEatCooldown() > 0 && this.canEat()) {
                this.setEatCooldown(this.getEatCooldown() - 1);
            }
            if (this.getLastHurtByMob() == null && this.getTarget() == null && this.canSleepCooldown() && !this.isBaby()) {
                if (this.getEepyCooldown() > 0) {
                    this.setEepyCooldown(this.getEepyCooldown() - 1);
                }
                if (!this.isEepy() && this.getSitCooldown() > 0 && !this.isSitting()) {
                    this.setSitCooldown(this.getSitCooldown() - 1);
                }
            }
            if (this.getSittingTicks() > 0) {
                this.setSittingTicks(this.getSittingTicks() - 1);
            }

            if (idleAnimationCooldown > 0 && !this.isEepy()) {
                this.idleAnimationCooldown--;
            }
        }

        if (this.getEatTicks() > 0) {
            this.setEatTicks(this.getEatTicks() - 1);
        }
    }

    // Idle animation cooldowns
    public void setIdleAnimationCooldown(int animationCooldown) {
        this.idleAnimationCooldown = animationCooldown;
    }

    public int getIdleAnimationCooldown() {
        return this.idleAnimationCooldown;
    }

    public int getIdleAnimationCooldown(int idleState) {
        return 0;
    }

    public boolean canSleepCooldown() {
        return !this.isInWaterOrBubble();
    }

    public boolean canEat() {
        return true;
    }

    // Tail
    public void tickTailYaw(float maxYaw, float yawMultiplier) {
        this.prevBodyYaw = bodyYaw;
        this.bodyYaw += Mth.clamp(Mth.wrapDegrees(yBodyRot - bodyYaw) * yawMultiplier, -maxYaw, maxYaw);

        this.prevTailYaw = tailYaw;
        this.tailYaw = Mth.clamp(Mth.wrapDegrees(bodyYaw - yBodyRot), -maxYaw, maxYaw);
    }

    public float getTailYaw(float partialTicks) {
        if (this.isPassenger()) {
            return 0.0F;
        }
        return Mth.lerp(partialTicks, prevTailYaw, tailYaw);
    }

    // Animations
    public void setupAnimationStates() {
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float pos = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float speed = Math.min(pos * this.getWalkAnimationSpeed(), 1.0F);
        this.walkAnimation.update(speed, 0.4F);
    }

    public float getWalkAnimationSpeed() {
        return this.isBaby() ? 5.0F : 10.0F;
    }

    // Healing
    public int getHealCooldown() {
        return this.isEepy() ? 50 : 150;
    }

    public boolean canHealOverTime() {
        return this.tickCount % this.getHealCooldown() == 0 && this.getHealth() < this.getMaxHealth() && this.isAlive();
    }

    // Look & Move overrides
    public boolean refuseToMove() {
        return this.isEepy() || this.isSitting();
    }

    public boolean refuseToLook() {
        return this.isEepy() || this.isSitting();
    }

    // Sitting & Sleeping
    @Override
    protected void actuallyHurt(DamageSource source, float amount) {
        if (this.isSitting() && !this.isOrderedToSit()) {
            this.setSitting(false);
            this.setSitCooldown(400);
        }
        else if (this.isEepy()) {
            this.setEepy(false);
            this.setEepyCooldown(400);
        }
        super.actuallyHurt(source, amount);
    }

    @Override
    public boolean handleLeashAtDistance(Entity leashHolder, float distance) {
        if (distance > 6.0F) {
            if (this.isSitting() && !this.isOrderedToSit()) {
                this.setSitting(false);
                this.setSitCooldown(this.getSitCooldown() + 200);
            }
            if (this.isEepy()) {
                this.setEepy(false);
                this.setEepyCooldown(this.getEepyCooldown() + 200);
            }
        }
        return true;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isEepyTime() {
        return this.level().isNight();
    }

    public void doEepyParticles() {
        Vec3 lookVec = this.getEepyParticleVec();
        if (eepyTicks == 0) {
            this.eepyTicks = 60 + this.getRandom().nextInt(20);
            this.level().addParticle(UP2Particles.EEPY.get(), this.getX() + lookVec.x, this.getEyeY() + lookVec.y, this.getZ() + lookVec.z, 0, 0, 0);
        }
        if (eepyTicks > 0) {
            this.eepyTicks--;
        }
    }

    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, 0.25D, this.getBbWidth()).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    public boolean shouldDoEepyParticles() {
        return this.isEepy();
    }

    // Taming
    @Override
    public boolean isAlliedTo(Entity entity) {
        if (this.isTame() && this.getOwner() != null) {
            if (entity == this.getOwner()) {
                return true;
            }
            if (entity instanceof TamableAnimal tamableAnimal) {
                return tamableAnimal.isOwnedBy(this.getOwner());
            }
            return this.getOwner().isAlliedTo(entity);
        }
        return super.isAlliedTo(entity);
    }

    @Override
    public boolean isInSittingPose() {
        return super.isInSittingPose() && !(this.isVehicle() || this.isPassenger());
    }

    // Riding
    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (!this.level().isClientSide) {
            if (this.getCommand() == 1 && !this.isSitting()) {
                this.setSitting(true);
            }
        }
    }

    @Override
    public void removeVehicle() {
        // Fix wandering back if dismounted
        if (this.isVehicle()) {
            this.getNavigation().stop();
        }
        super.removeVehicle();
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        Vec3 escapeVector = getCollisionHorizontalEscapeVector(getBbWidth(), passenger.getBbWidth(), passenger.getYRot());
        @Nullable Vec3 location = this.getDismountLocationInDirection(this, escapeVector, passenger);
        return Objects.requireNonNullElseGet(location, () -> super.getDismountLocationForPassenger(passenger));
    }

    @Override
    public void positionRider(Entity passenger, MoveFunction moveFunction) {
        super.positionRider(passenger, moveFunction);
        passenger.setYBodyRot(this.yBodyRot);
        passenger.fallDistance = 0.0F;
    }

    @Override
    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        if (entity instanceof Player player) {
            return player;
        }
        return null;
    }

    @Override
    protected Vec3 getRiddenInput(Player player, Vec3 vec3) {
        float f = player.xxa * 0.5F;
        float f1 = player.zza;
        if (f1 <= 0.0F) {
            f1 *= 0.25F;
        }
        return new Vec3(f, 0.0F, f1);
    }

    @Override
    protected void tickRidden(Player player, Vec3 travelVector) {
        super.tickRidden(player, travelVector);
        Vec2 vec2 = this.getRiddenRotation(player);
        this.setRot(vec2.y, vec2.x);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
        if (this.shouldStepDown(this)) {
            this.addDeltaMovement(new Vec3(0, -0.65F, 0));
        }
        if (player.zza > 0.0F) {
            if (this.isSitting()) {
                this.setSitting(false);
            }
            if (this.isEepy()) {
                this.setEepy(false);
            }
        }
    }

    protected Vec2 getRiddenRotation(LivingEntity entity) {
        return new Vec2(entity.getXRot() * 0.5F, entity.getYRot());
    }

    @Override
    @SuppressWarnings("all")
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
        // Prevent rider from taking fall damage
        float[] livingFall = CommonHooks.onLivingFall(this, fallDistance, multiplier);
        if (livingFall == null) return false;
        fallDistance = livingFall[0];
        multiplier = livingFall[1];
        boolean flag = this.causeInternalFallDamage(fallDistance, multiplier, source);
        int i = this.calculateFallDamage(fallDistance, multiplier);
        if (i > 0) {
            this.playSound(i > 4 ? this.getFallSounds().big() : this.getFallSounds().small(), 1.0F, 1.0F);
            this.playBlockFallSound();
            this.hurt(source, (float)i);
            return true;
        } else {
            return flag;
        }
    }

    @SuppressWarnings("all")
    private boolean causeInternalFallDamage(float f1, float f2, DamageSource damageSource) {
        float[] livingFall = CommonHooks.onLivingFall(this, f1, f2);
        if (livingFall == null) return false;
        f1 = livingFall[0];
        f2 = livingFall[1];
        int i = this.calculateFallDamage(f1, f2);
        if (i > 0) {
            this.playBlockFallSound();
            this.hurt(damageSource, (float) i);
            return true;
        } else {
            return this.getType().is(EntityTypeTags.FALL_DAMAGE_IMMUNE);
        }
    }

    // Sounds
    public boolean canPlayAmbientSound() {
        return !this.isEepy();
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundevent = this.getAmbientSound();
        if (soundevent != null && this.canPlayAmbientSound()) {
            this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
        }
    }

    // Data
    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (EEPY.equals(accessor)) {
            this.refreshDimensions();
        }
        else if (SITTING.equals(accessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACK_STATE, 0);
        builder.define(FROM_EGG, false);
        builder.define(SHOT_FROM_OOZE, false);
        builder.define(RUNNING, false);
        builder.define(IDLE_STATE, 0);
        builder.define(EAT_COOLDOWN, 100);
        builder.define(EAT_TICKS, 0);
        builder.define(AGE_LOCKED, false);
        builder.define(SIT_COOLDOWN, 3000 + this.getRandom().nextInt(3000));
        builder.define(EEPY_COOLDOWN, 100);
        builder.define(COMMAND, 0);
        builder.define(EEPY, false);
        builder.define(SITTING, false);
        builder.define(SITTING_TICKS, 0);
        builder.define(PACIFIED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("FromEgg", this.isFromEgg());
        compoundTag.putInt("EatCooldown", this.getEatCooldown());
        compoundTag.putBoolean("AgeLocked", this.isAgeLocked());
        compoundTag.putInt("SitCooldown", this.getSitCooldown());
        compoundTag.putInt("EepyCooldown", this.getEepyCooldown());
        compoundTag.putInt("Command", this.getCommand());
        compoundTag.putBoolean("Eepy", this.isEepy());
        compoundTag.putBoolean("Sitting", this.isSitting());
        compoundTag.putInt("SittingTicks", this.getSittingTicks());
        compoundTag.putBoolean("Pacified", this.isPacified());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setFromEgg(compoundTag.getBoolean("FromEgg"));
        this.setEatCooldown(compoundTag.getInt("EatCooldown"));
        this.setAgeLocked(compoundTag.getBoolean("AgeLocked"));
        this.setSitCooldown(compoundTag.getInt("SitCooldown"));
        this.setEepyCooldown(compoundTag.getInt("EepyCooldown"));
        this.setCommand(compoundTag.getInt("Command"));
        this.setEepy(compoundTag.getBoolean("Eepy"));
        this.setSitting(compoundTag.getBoolean("Sitting"));
        this.setSittingTicks(compoundTag.getInt("SittingTicks"));
        this.setPacified(compoundTag.getBoolean("Pacified"));
    }

    // Idle and attack states
    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }
    public void setAttackState(int attackState) {
        this.entityData.set(ATTACK_STATE, attackState);
    }

    public int getIdleState() {
        return this.entityData.get(IDLE_STATE);
    }
    public void setIdleState(int idleState) {
        this.entityData.set(IDLE_STATE, idleState);
    }

    // Pacify
    public boolean isPacified() {
        return this.entityData.get(PACIFIED);
    }
    public void setPacified(boolean pacified) {
        this.entityData.set(PACIFIED, pacified);
    }

    // From egg
    public boolean isFromEgg() {
        return this.entityData.get(FROM_EGG);
    }
    public void setFromEgg(boolean fromEgg) {
        this.entityData.set(FROM_EGG, fromEgg);
    }

    // Was just shot from ooze
    public boolean wasShotFromOoze() {
        return this.entityData.get(SHOT_FROM_OOZE);
    }
    public void setShotFromOoze(boolean shotFromOoze) {
        this.entityData.set(SHOT_FROM_OOZE, shotFromOoze);
    }

    // Running
    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }
    public void setRunning(boolean running) {
        this.entityData.set(RUNNING, running);
    }

    // Eating
    public int getEatCooldown() {
        return this.entityData.get(EAT_COOLDOWN);
    }
    public void setEatCooldown(int cooldown) {
        this.entityData.set(EAT_COOLDOWN, cooldown);
    }

    public int getEatTicks() {
        return this.entityData.get(EAT_TICKS);
    }
    public void setEatTicks(int eatTicks) {
        this.entityData.set(EAT_TICKS, eatTicks);
    }

    // Never grows up
    public boolean isAgeLocked() {
        return this.entityData.get(AGE_LOCKED);
    }
    public void setAgeLocked(boolean ageLocked) {
        this.entityData.set(AGE_LOCKED, ageLocked);
    }

    // Sitting
    public int getSitCooldown() {
        return this.entityData.get(SIT_COOLDOWN);
    }
    public void setSitCooldown(int cooldown) {
        this.entityData.set(SIT_COOLDOWN, cooldown);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }
    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
    }

    public int getSittingTicks() {
        return this.entityData.get(SITTING_TICKS);
    }
    public void setSittingTicks(int ticks) {
        this.entityData.set(SITTING_TICKS, ticks);
    }

    // Sleeping
    public int getEepyCooldown() {
        return this.entityData.get(EEPY_COOLDOWN);
    }
    public void setEepyCooldown(int cooldown) {
        this.entityData.set(EEPY_COOLDOWN, cooldown);
    }

    public boolean isEepy() {
        return this.entityData.get(EEPY);
    }
    public void setEepy(boolean eepy) {
        this.entityData.set(EEPY, eepy);
    }

    // Tame commands
    public int getCommand() {
        return this.entityData.get(COMMAND);
    }
    public void setCommand(int command) {
        this.entityData.set(COMMAND, command);
    }

    public boolean isFollowingOwner() {
        return this.getCommand() == 2;
    }
}
