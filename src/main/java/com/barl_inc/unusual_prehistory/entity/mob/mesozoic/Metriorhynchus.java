 package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

 import com.barl_inc.unusual_prehistory.entity.accessor.LivingEntityAccessor;
 import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
 import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
 import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
 import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
 import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
 import com.barl_inc.unusual_prehistory.entity.ai.goals.update_3.MetriorhynchusAttackGoal;
 import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
 import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
 import com.barl_inc.unusual_prehistory.entity.utils.GrabbingMob;
 import com.barl_inc.unusual_prehistory.entity.utils.LeapingMob;
 import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
 import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barl_inc.unusual_prehistory.registry.UP2Entities;
 import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
 import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
 import net.minecraft.core.BlockPos;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.tags.FluidTags;
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
 import net.minecraft.world.item.Items;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelReader;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.gameevent.GameEvent;
 import net.minecraft.world.level.pathfinder.PathType;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 @SuppressWarnings("deprecation")
 public class Metriorhynchus extends PrehistoricAmphibiousMob implements LeapingMob, GrabbingMob {

     private static final EntityDataAccessor<Integer> HELD_MOB_ID = SynchedEntityData.defineId(Metriorhynchus.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Metriorhynchus.class, EntityDataSerializers.BOOLEAN);

     public int grabCooldown = 0;
     public int attackCooldown = 0;

     public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState grab1AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState grab2AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState bellowAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState angryAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState leapAnimationState = new SmoothAnimationState();

     public boolean grabAlt = false;
     public boolean attackAlt = false;

     public Metriorhynchus(EntityType<? extends PrehistoricAmphibiousMob> entityType, Level level) {
         super(entityType, level);
         this.switchNavigator(true);
         this.setPathfindingMalus(PathType.WATER, 0.0F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 28.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.15F)
                 .add(Attributes.ATTACK_DAMAGE, 7.0F)
                 .add(Attributes.STEP_HEIGHT, 1.1D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new PrehistoricSitWhenOrderedToGoal(this, false));
         this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 2.0D, 10, 4));
         this.goalSelector.addGoal(2, new AquaticLeapGoal(this, 10, 0.9D, 0.7D));
         this.goalSelector.addGoal(3, new MetriorhynchusAttackGoal(this));
         this.goalSelector.addGoal(4, new PrehistoricFollowOwnerGoal(this, 1.2D, 1.5D, 7.0F, 4.0F));
         this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
         this.goalSelector.addGoal(6, new EnterWaterGoal(this, 1.0D, 3000, false));
         this.goalSelector.addGoal(7, new PrehistoricSwimGoal(this, 1.0D, 20, 3));
         this.goalSelector.addGoal(7, new AmphibiousWanderGoal(this, 1.0D));
         this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 40, 1, false, 0.001F) {
             @Override
             public void start() {
                 super.start();
                 Metriorhynchus.this.playSound(UP2SoundEvents.METRIORHYNCHUS_BELLOW.get(), 1.5F, 1.0F);
             }
         });
         this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
         this.targetSelector.addGoal(1, new PrehistoricOwnerHurtByTargetGoal(this));
         this.targetSelector.addGoal(2, new PrehistoricOwnerHurtTargetGoal(this));
         this.targetSelector.addGoal(3, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.METRIORHYNCHUS_TARGETS)));
         this.targetSelector.addGoal(4, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 300, true, true, this::canAttack));
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
             this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.98F);
             this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
             this.isLandNavigator = false;
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
             if (this.horizontalCollision && this.isEyeInFluid(FluidTags.WATER) && this.isPathFinding()) {
                 this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
             }
         } else {
             super.travel(travelVec);
         }
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.DIET_PISCIVORE);
     }

     @Override
     public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         InteractionResult type = super.mobInteract(player, hand);
         if (!this.isTame() && itemstack.is(UP2ItemTags.TAMES_METRIORHYNCHUS)) {
             if (!this.level().isClientSide) {
                 if (!player.getAbilities().instabuild) {
                     if (itemstack.is(Items.TROPICAL_FISH_BUCKET)) {
                         player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
                     } else {
                         itemstack.shrink(1);
                     }
                 }
                 this.gameEvent(GameEvent.ENTITY_INTERACT);
                 this.level().broadcastEntityEvent(this, (byte) 7);
                 this.tame(player);
                 this.heal(this.getMaxHealth());
                 this.level().broadcastEntityEvent(this, (byte) 6);
             }
             return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
         return type;
     }

     @Override
     public boolean canOwnerCommand(Player player, @NotNull InteractionHand hand) {
         return true;
     }

     @Override
     public boolean checkSpawnObstruction(LevelReader level) {
         return level.isUnobstructed(this);
     }

     public boolean canPickUpTarget(LivingEntity target) {
         if (target == null) {
             return false;
         }
         if (((LivingEntityAccessor) target).unusualPrehistory$isBeingGrabbed()) {
             return false;
         }
         if (target.getType().is(UP2EntityTags.METRIORHYNCHUS_CANT_GRAB)) {
             return false;
         }
         return (target.getBbWidth() < this.getBbWidth() && target.getBbHeight() < this.getBbHeight()) || target.getType().is(UP2EntityTags.METRIORHYNCHUS_CAN_GRAB);
     }

     @Override
     public void remove(@NotNull RemovalReason removalReason) {
         super.remove(removalReason);
         if (this.getHeldMobId() != -1) {
             this.setHeldMobId(-1);
         }
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

         if (!this.level().isClientSide) {
             if (grabCooldown > 0) grabCooldown--;
             if (attackCooldown > 0) attackCooldown--;
         }
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isSitting(), this.tickCount);
         this.sitAnimationState.animateWhen(!this.isInWaterOrBubble() && this.isSitting(), this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && this.getPose() != UP2Poses.GRABBING.get(), this.tickCount);
         this.bellowAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
         this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
         this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
         this.grab1AnimationState.animateWhen(this.getPose() == UP2Poses.GRABBING.get() && !grabAlt, this.tickCount);
         this.grab2AnimationState.animateWhen(this.getPose() == UP2Poses.GRABBING.get() && grabAlt, this.tickCount);
         this.angryAnimationState.animateWhen(this.isAggressive() && this.getPose() != UP2Poses.GRABBING.get() && this.getPose() != UP2Poses.ATTACKING.get(), this.tickCount);
         this.leapAnimationState.animateWhen(this.isLeaping() && this.getPose() != UP2Poses.GRABBING.get(), this.tickCount);
     }

     @Override
     public int getIdleAnimationCooldown(int idleState) {
         if (idleState == 1) {
             return 1200 + this.getRandom().nextInt(1200);
         }
         else {
             throw new IllegalStateException("Unexpected value: " + idleState);
         }
     }

     @Override
     protected void defineSynchedData(SynchedEntityData.Builder builder) {
         super.defineSynchedData(builder);
         builder.define(HELD_MOB_ID, -1);
         builder.define(LEAPING, false);
     }

     @Override
     public void setHeldMobId(int id) {
         int oldId = this.getHeldMobId();
         if (oldId != -1) {
             Entity oldEntity = this.level().getEntity(oldId);
             if (oldEntity instanceof LivingEntity living) {
                 ((LivingEntityAccessor) living).unusualPrehistory$setBeingGrabbed(false);
             }
         }
         this.entityData.set(HELD_MOB_ID, id);
         if (id != -1) {
             Entity newEntity = this.level().getEntity(id);
             if (newEntity instanceof LivingEntity living) {
                 ((LivingEntityAccessor) living).unusualPrehistory$setBeingGrabbed(true);
             }
         }
     }

     @Override
     public int getHeldMobId() {
         return this.entityData.get(HELD_MOB_ID);
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
         return UP2SoundEvents.METRIORHYNCHUS_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
         return UP2SoundEvents.METRIORHYNCHUS_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.METRIORHYNCHUS_DEATH.get();
     }

     @Override
     protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
         this.playSound(SoundEvents.FROG_STEP, 0.3F, 0.9F);
     }

     @Override
     public int getAmbientSoundInterval() {
         return 200;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.METRIORHYNCHUS.get().create(level);
     }
 }
