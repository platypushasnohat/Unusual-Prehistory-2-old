 package com.barlinc.unusual_prehistory.entity.mob.cenozoic;

 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
 import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
 import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2Items;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.tags.UP2EntityTags;
 import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
 import com.barlinc.unusual_prehistory.utils.UP2MobUtils;
 import net.minecraft.core.BlockPos;
 import net.minecraft.core.particles.BlockParticleOption;
 import net.minecraft.core.particles.ParticleTypes;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.sounds.SoundSource;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.goal.Goal;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.TemptGoal;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.animal.Bucketable;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.PathType;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Praepusa extends PrehistoricAmphibiousMob implements Bucketable {

     private static final EntityDataAccessor<Integer> MITOSIS_COOLDOWN = SynchedEntityData.defineId(Praepusa.class, EntityDataSerializers.INT);

     private boolean prevOnGround = false;
     private Vec3 prevVelocity = Vec3.ZERO;

     public int attackCooldown = 0;

     public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState slap1AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState slap2AnimationState = new SmoothAnimationState();
     public final SmoothAnimationState loafAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState applauseAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState mitosisAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState bounceAnimationState = new SmoothAnimationState();

     private int mitosisTicks;
     private int bounceTicks = 0;
     private boolean slapAlt = false;
     private int loafCooldown = 0;

     public Praepusa(EntityType<? extends PrehistoricAmphibiousMob> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(PathType.WATER, 0.0F);
         this.switchNavigator(true);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 10.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.17F)
                 .add(Attributes.ATTACK_DAMAGE, 3.0D)
                 .add(Attributes.STEP_HEIGHT, 1.1D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new SurfaceSwimGoal(this, 0.1D) {
             @Override
             public boolean canUse() {
                 return Praepusa.this.isEepy() && super.canUse();
             }
         });

         this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 1.6D, 10, 4, true));
         this.goalSelector.addGoal(2, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 8.0F, 1.8D, entity -> entity.getType().is(UP2EntityTags.PRAEPUSA_AVOIDS)));
         this.goalSelector.addGoal(3, new PraepusaAttackGoal(this));
         this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
         this.goalSelector.addGoal(5, new LeaveWaterGoal(this, 1.0D));
         this.goalSelector.addGoal(5, new EnterWaterGoal(this, 1.0D));
         this.goalSelector.addGoal(6, new AmphibiousWanderGoal(this, 1.0D));
         this.goalSelector.addGoal(6, new PrehistoricSwimGoal(this, 1.0D, 50));
         this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(8, new EepyGoal(this, false));
         this.goalSelector.addGoal(9, new PraepusaLoafGoal(this));
         this.goalSelector.addGoal(10, new IdleAnimationGoal(this, 40, 2, true, 0.001F, this::canPlayIdles));
         this.goalSelector.addGoal(10, new IdleAnimationGoal(this, 60, 3, true, 0.001F, this::canPlayIdles));
         this.targetSelector.addGoal(0, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 500, true, true, this::canHuntFish));
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
             this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.4F);
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
             travelVec = travelVec.multiply(0.0, 1.0, 0.0);
         }
         if (this.isEffectiveAi() && this.isInWater() && !this.isEepy()) {
             UP2MobUtils.travelInWater(this, travelVec);
         }
         else {
             super.travel(travelVec);
         }
     }

     @Override
     public boolean isEepyTime() {
         return super.isEepyTime() && this.isInWater();
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.DIET_PISCIVORE);
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 2 || this.getIdleState() == 3 || this.getPose() == UP2Poses.MITOSIS.get();
     }

     @Override
     public boolean killedEntity(@NotNull ServerLevel level, @NotNull LivingEntity victim) {
         if (this.getMitosisCooldown() == 0) {
             this.setPose(UP2Poses.MITOSIS.get());
         }
         return super.killedEntity(level, victim);
     }

     public boolean canHuntFish(Entity entity) {
         return this.getMitosisCooldown() == 0 && entity.getType().is(UP2EntityTags.PRAEPUSA_TARGETS);
     }

     @Override
     public boolean canSleepCooldown() {
         return this.isInWaterOrBubble();
     }

     @Override
     public Vec3 getEepyParticleVec() {
         return new Vec3(0.0D, 0.7D, this.getBbWidth() * 1.15F).yRot(-yBodyRot * ((float) Math.PI / 180F));
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

         if (this.getMitosisCooldown() > 0) this.setMitosisCooldown(this.getMitosisCooldown() - 1);
         if (this.getPose() == UP2Poses.MITOSIS.get()) {
             this.setMitosisCooldown(2400 + this.getRandom().nextInt(1200));
             if (this.mitosisTicks == 7) {
                 this.performMitosis();
                 this.addDeltaMovement(this.getLookAngle().scale(2.0D).multiply(this.level().getRandom().nextFloat() * (this.level().getRandom().nextBoolean() ? -0.25F : 0.25F), 0, this.level().getRandom().nextFloat() * (this.level().getRandom().nextBoolean() ? -0.25F : 0.25F)));
                 this.level().playSound(null, this.blockPosition(), UP2SoundEvents.PRAEPUSA_MITOSIS.get(), SoundSource.NEUTRAL, 1.0F, 0.9F + this.getRandom().nextFloat() * 0.25F);
             }
         }

         if (!this.level().isClientSide && this.isAlive() && !this.isInWaterOrBubble()) {
             this.bounce();
         }

         if (this.isEepy() && this.isInWater()) {
             this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.5, 1.0));
         }
     }

     @Override
     public boolean causeFallDamage(float distance, float multiplier, @NotNull DamageSource source) {
         return false;
     }

     protected void performMitosis() {
         Praepusa praepusa = UP2Entities.PRAEPUSA.get().create(this.level());
         if (praepusa != null) {
             praepusa.moveTo(this.position());
             praepusa.setPersistenceRequired();
             praepusa.setMitosisCooldown(2400 + praepusa.getRandom().nextInt(1200));
             praepusa.addDeltaMovement(praepusa.getLookAngle().scale(2.0D).multiply(praepusa.level().getRandom().nextFloat() * (praepusa.level().getRandom().nextBoolean() ? -0.25F : 0.25F), 0, praepusa.level().getRandom().nextFloat() * (praepusa.level().getRandom().nextBoolean() ? -0.25F : 0.25F)));
             this.level().addFreshEntity(praepusa);
         }
     }

     private void bounce() {
         double impactThreshold = 0.1D;
         if (this.onGround() && !prevOnGround && prevVelocity.y < -impactThreshold) {
             double impactSpeed = Math.abs(prevVelocity.y);
             double bounceFactor = 0.7D;
             double minBounceVelocity = 0.38D;
             double newYVelocity = impactSpeed * bounceFactor;
             if (newYVelocity > minBounceVelocity) {
                 this.setDeltaMovement(this.getDeltaMovement().x, newYVelocity, this.getDeltaMovement().z);
                 if (this.bounceTicks == 0) this.level().broadcastEntityEvent(this, (byte) 73);
                 this.bounceTicks = 10;
                 this.level().playSound(null, this.blockPosition(), UP2SoundEvents.PRAEPUSA_BOUNCE.get(), SoundSource.NEUTRAL, 0.3F, 0.9F + this.getRandom().nextFloat() * 0.25F);
                 this.setOnGround(false);
                 this.hasImpulse = true;
                 BlockPos blockBelow = BlockPos.containing(this.getX(), this.getY() - 0.1, this.getZ());
                 BlockState blockState = this.level().getBlockState(blockBelow);
                 if (!blockState.isAir()) {
                     ((ServerLevel) this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), this.getX(), this.getY() + 0.1, this.getZ(), 8, 0.15, 0.05, 0.15, 0.05);
                 }
             }
         }
         this.prevVelocity = this.getDeltaMovement();
         this.prevOnGround = this.onGround();
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && this.getIdleState() != 3 && !this.isEepy(), this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isEepy(), this.tickCount);
         this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
         this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
         this.loafAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
         this.slap1AnimationState.animateWhen(this.getIdleState() == 2 && !slapAlt, this.tickCount);
         this.slap2AnimationState.animateWhen(this.getIdleState() == 2 && slapAlt, this.tickCount);
         this.applauseAnimationState.animateWhen(this.getIdleState() == 3, this.tickCount);
         this.mitosisAnimationState.animateWhen(this.getPose() == UP2Poses.MITOSIS.get(), this.tickCount);
     }

     @Override
     public void tickCooldowns() {
         super.tickCooldowns();
         if (bounceTicks > 0) bounceTicks--;
         if (bounceTicks == 0) this.level().broadcastEntityEvent(this, (byte) 74);
         if (mitosisTicks > 0) mitosisTicks--;
         if (mitosisTicks == 0 && this.getPose() == UP2Poses.MITOSIS.get()) this.setPose(Pose.STANDING);
         if (attackCooldown > 0) attackCooldown--;
         if (loafCooldown > 0) loafCooldown--;
     }

     private boolean canPlayIdles(Entity entity) {
         return !entity.isInWaterOrBubble();
     }

     @Override
     public int getIdleAnimationCooldown(int idleState) {
         if (idleState == 1) {
             return 300 + this.getRandom().nextInt(300);
         }
         else if (idleState == 2) {
             return 800 + this.getRandom().nextInt(1200);
         }
         else if (idleState == 3) {
             return 1000 + this.getRandom().nextInt(1200);
         }
         else {
             throw new IllegalStateException("Unexpected value: " + idleState);
         }
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         if (DATA_POSE.equals(accessor)) {
             if (this.getPose() == UP2Poses.MITOSIS.get()) {
                 this.mitosisTicks = 40;
             }
         }
         if (IDLE_STATE.equals(accessor)) {
             if (this.getIdleState() == 2) {
                 this.slapAlt = this.getRandom().nextBoolean();
             }
         }
         super.onSyncedDataUpdated(accessor);
     }

     public void handleEntityEvent(byte id) {
         switch (id) {
             case 73 -> bounceAnimationState.start(this.tickCount);
             case 74 -> bounceAnimationState.stop();
             default -> super.handleEntityEvent(id);
         }
     }

     @Override
     protected void defineSynchedData(SynchedEntityData.Builder builder) {
         super.defineSynchedData(builder);
         builder.define(MITOSIS_COOLDOWN, 0);
     }

     @Override
     public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.addAdditionalSaveData(compoundTag);
         compoundTag.putInt("MitosisCooldown", this.getMitosisCooldown());
     }

     @Override
     public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.readAdditionalSaveData(compoundTag);
         this.setMitosisCooldown(compoundTag.getInt("MitosisCooldown"));
     }

     public int getMitosisCooldown() {
         return this.entityData.get(MITOSIS_COOLDOWN);
     }
     public void setMitosisCooldown(int cooldown) {
         this.entityData.set(MITOSIS_COOLDOWN, cooldown);
     }

     @Override
     public boolean fromBucket() {
         return false;
     }

     @Override
     public void setFromBucket(boolean fromBucket) {
     }

     @Override
     public @NotNull ItemStack getBucketItemStack() {
         return new ItemStack(UP2Items.PRAEPUSA_BUCKET.get());
     }

     @Override
     public @NotNull SoundEvent getPickupSound() {
         return SoundEvents.BUCKET_EMPTY_FISH;
     }

     @Override
     public void saveToBucketTag(@NotNull ItemStack bucket) {
         UP2MobUtils.savePrehistoricDataToBucket(this, bucket);
     }

     @Override
     public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
         UP2MobUtils.loadPrehistoricDataFromBucket(this, compoundTag);
     }

     @Override
     public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         ItemStack itemstack = player.getItemInHand(hand);
         if (this.isFood(itemstack) && !this.isBaby() && this.getMitosisCooldown() == 0 && this.getPose() == Pose.STANDING) {
             this.feedItemToMob(this, player, itemstack);
             this.setPose(UP2Poses.MITOSIS.get());
             return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
         if (itemstack.isEmpty() && this.getIdleState() == 0 && this.getLastHurtByMob() == null && this.loafCooldown == 0) {
             this.setIdleState(1);
             return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
         return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
     }

     @Override
     @Nullable
     protected SoundEvent getAmbientSound() {
         return UP2SoundEvents.PRAEPUSA_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
         return UP2SoundEvents.PRAEPUSA_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.PRAEPUSA_DEATH.get();
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.PRAEPUSA.get().create(level);
     }

     // Goals
     private static class PraepusaLoafGoal extends Goal {

         public final Praepusa praepusa;
         public int timer;

         public PraepusaLoafGoal(Praepusa praepusa) {
             this.praepusa = praepusa;
         }

         @Override
         public boolean canUse() {
             if (this.isInCombat()) {
                 return false;
             }
             return praepusa.loafCooldown == 0 && praepusa.isAlive() && praepusa.getIdleState() == 1 && !praepusa.isEepy();
         }

         @Override
         public void start() {
             this.praepusa.loafCooldown = 80;
             this.praepusa.setIdleAnimationCooldown(praepusa.getIdleAnimationCooldown() + 80);
             this.timer = 80;
             this.praepusa.getNavigation().stop();
         }

         @Override
         public boolean canContinueToUse() {
             if (this.isInCombat()) {
                 return false;
             }
             return timer > 0 && praepusa.getIdleState() == 1 && praepusa.isAlive() && !praepusa.isEepy();
         }

         @Override
         public void tick() {
             this.timer--;
             this.praepusa.getNavigation().stop();
         }

         @Override
         public void stop() {
             this.praepusa.setIdleState(0);
             this.praepusa.getNavigation().stop();
         }

         @Override
         public boolean requiresUpdateEveryTick() {
             return true;
         }

         protected boolean isInCombat() {
             return praepusa.getLastHurtByMob() != null || praepusa.getTarget() != null;
         }
     }

     private static class PraepusaAttackGoal extends AttackGoal {

         private final Praepusa praepusa;

         public PraepusaAttackGoal(Praepusa praepusa) {
             super(praepusa);
             this.praepusa = praepusa;
         }

         @Override
         public boolean canUse() {
             LivingEntity target = praepusa.getTarget();
             return super.canUse() && praepusa.getPose() == Pose.STANDING && target != null && target.isAlive() && target.isInWater() && !target.getType().is(UP2EntityTags.PRAEPUSA_AVOIDS) && !(target instanceof Player);
         }

         @Override
         public void tick() {
             LivingEntity target = praepusa.getTarget();
             if (target != null && target.isInWater()) {
                 this.praepusa.lookAt(target, 30F, 30F);
                 this.praepusa.getLookControl().setLookAt(target, 30F, 30F);
                 double distance = praepusa.distanceToSqr(target.getX(), target.getY(), target.getZ());
                 this.praepusa.getNavigation().moveTo(target, 1.5D);
                 if (distance <= 4 && praepusa.attackCooldown == 0) {
                     praepusa.setAttackState(1);
                 }
                 if (praepusa.getAttackState() == 1) {
                     this.tickAttack(target);
                 }
             }
         }

         protected void tickAttack(LivingEntity target) {
             this.timer++;
             if (timer == 1) praepusa.setPose(UP2Poses.ATTACKING.get());
             if (timer == 8) {
                 if (praepusa.distanceTo(target) < this.getAttackReachSqr(target)) {
                     this.praepusa.doHurtTarget(target);
                     this.praepusa.swing(InteractionHand.MAIN_HAND);
                 }
             }
             if (timer > 10) {
                 this.timer = 0;
                 this.praepusa.setAttackState(0);
                 this.praepusa.attackCooldown = 4;
             }
         }
     }
 }
