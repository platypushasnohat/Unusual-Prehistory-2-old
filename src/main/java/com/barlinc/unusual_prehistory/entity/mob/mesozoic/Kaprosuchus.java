 package com.barlinc.unusual_prehistory.entity.mob.mesozoic;

 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.ai.goals.update_4.KaprosuchusAttackGoal;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
 import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
 import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
 import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.tags.UP2EntityTags;
 import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.damagesource.DamageSource;
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
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.gameevent.GameEvent;
 import net.minecraft.world.level.pathfinder.PathType;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Kaprosuchus extends PrehistoricAmphibiousMob implements LeapingMob {

     private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(Kaprosuchus.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Kaprosuchus.class, EntityDataSerializers.BOOLEAN);

     public int leapCooldown = 70 + this.getRandom().nextInt(80);
     public int attackCooldown = 0;

     public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState leapAnimationState = new SmoothAnimationState();

     public boolean attackAlt = false;

     public Kaprosuchus(EntityType<? extends PrehistoricAmphibiousMob> entityType, Level level) {
         super(entityType, level);
         this.switchNavigator(true);
         this.setPathfindingMalus(PathType.WATER, 0.0F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 20.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.2F)
                 .add(Attributes.ARMOR, 4.0D)
                 .add(Attributes.ATTACK_DAMAGE, 6.0D)
                 .add(Attributes.STEP_HEIGHT, 1.1D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new PrehistoricSitWhenOrderedToGoal(this, false));
         this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 1.8D, 10, 4));
         this.goalSelector.addGoal(2, new KaprosuchusAttackGoal(this));
         this.goalSelector.addGoal(3, new PrehistoricFollowOwnerGoal(this, 1.2D, 1.9D, 5.0F, 2.0F, false));
         this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_OMNIVORE), false));
         this.goalSelector.addGoal(5, new LeaveWaterGoal(this, 1.0D, 2000));
         this.goalSelector.addGoal(5, new EnterWaterGoal(this, 1.0D, 2000, false));
         this.goalSelector.addGoal(6, new PrehistoricSwimGoal(this, 1.0D, 50));
         this.goalSelector.addGoal(6, new AmphibiousWanderGoal(this, 1.0D));
         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 10.0F));
         this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(8, new EepyGoal(this));
         this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
         this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.KAPROSUCHUS_TARGETS)));
         this.targetSelector.addGoal(2, new PrehistoricOwnerHurtByTargetGoal(this));
         this.targetSelector.addGoal(3, new PrehistoricOwnerHurtTargetGoal(this));
     }

     @Override
     protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
         return new SmoothAmphibiousNavigation(this, level);
     }

     protected void switchNavigator(boolean onLand) {
         if (onLand) {
             this.lookControl = new PrehistoricLookControl(this);
             this.moveControl = new PrehistoricMoveControl(this);
             this.isLandNavigator = true;
         } else {
             this.lookControl = new PrehistoricSwimmingLookControl(this, 20);
             this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.4F);
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
         if (this.isEffectiveAi() && this.isInWater()) {
             this.moveRelative(this.getSpeed(), travelVec);
             this.move(MoverType.SELF, this.getDeltaMovement());
             this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
         } else {
             super.travel(travelVec);
         }
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.DIET_OMNIVORE);
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove();
     }

     @Override
     public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource damageSource) {
         return false;
     }

     @Override
     protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
     }

     public void leapCooldown() {
         this.leapCooldown = 70 + this.getRandom().nextInt(80);
     }

     @Override
     public boolean canOwnerCommand(Player player, @NotNull InteractionHand hand) {
         return true;
     }

     @Override
     public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         InteractionResult type = super.mobInteract(player, hand);

         if (!this.isTame() && itemstack.is(UP2ItemTags.TAMES_KAPROSUCHUS)) {
             this.gameEvent(GameEvent.ENTITY_INTERACT);
             if(!this.level().isClientSide) {
                 if (!player.getAbilities().instabuild) {
                     itemstack.shrink(1);
                 }
                 if (this.getTameAttempts() > 2 && this.getRandom().nextBoolean()) {
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

     @Override
     public Vec3 getEepyParticleVec() {
         return new Vec3(0.0D, 0.1D, this.getBbWidth() * 0.98F).yRot(-yBodyRot * ((float) Math.PI / 180F));
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
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isLeaping() && !this.isSitting() && !this.isEepy(), this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
         this.leapAnimationState.animateWhen(this.isLeaping(), this.tickCount);
         this.sitAnimationState.animateWhen(this.isSitting() && !this.isInWaterOrBubble(), this.tickCount);
         this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
         this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
         this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
     }

     @Override
     public void tickCooldowns() {
         super.tickCooldowns();
         if (leapCooldown > 0) leapCooldown--;
         if (attackCooldown > 0) attackCooldown--;
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
     public void setLeaping(boolean leaping) {
         this.entityData.set(LEAPING, leaping);
     }

     @Override
     public boolean isLeaping() {
         return this.entityData.get(LEAPING);
     }

     @Override
     @Nullable
     protected SoundEvent getAmbientSound() {
         return UP2SoundEvents.KAPROSUCHUS_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource source) {
         return UP2SoundEvents.KAPROSUCHUS_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.KAPROSUCHUS_DEATH.get();
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.KAPROSUCHUS.get().create(level);
     }
 }
