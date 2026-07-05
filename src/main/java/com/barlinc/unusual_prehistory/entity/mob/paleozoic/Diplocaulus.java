 package com.barlinc.unusual_prehistory.entity.mob.paleozoic;

 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
 import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
 import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2Items;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.tags.UP2BlockTags;
 import com.barlinc.unusual_prehistory.tags.UP2EntityTags;
 import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
 import com.barlinc.unusual_prehistory.utils.UP2MobUtils;
 import net.minecraft.core.BlockPos;
 import net.minecraft.core.component.DataComponents;
 import net.minecraft.core.particles.BlockParticleOption;
 import net.minecraft.core.particles.ParticleTypes;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.sounds.SoundEvents;
 import net.minecraft.util.Mth;
 import net.minecraft.world.DifficultyInstance;
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
 import net.minecraft.world.entity.ai.util.DefaultRandomPos;
 import net.minecraft.world.entity.animal.Bucketable;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.component.CustomData;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.ServerLevelAccessor;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.PathType;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 import java.util.EnumSet;

 public class Diplocaulus extends PrehistoricAmphibiousMob implements Bucketable, VariantHolder<Diplocaulus.DiplocaulusVariant> {

     private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Diplocaulus.class, EntityDataSerializers.INT);
     private static final EntityDataAccessor<Boolean> SLIDING = SynchedEntityData.defineId(Diplocaulus.class, EntityDataSerializers.BOOLEAN);

     public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState burrowAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState quirkAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState boomerangAnimationState = new SmoothAnimationState(1.0F);

     public Diplocaulus(EntityType<? extends PrehistoricAmphibiousMob> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(PathType.WATER, 0.0F);
         this.switchNavigator(true);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 10.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.23F)
                 .add(Attributes.STEP_HEIGHT, 1.1D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new PrehistoricPanicGoal(this, 1.6D, 10, 4, true));
         this.goalSelector.addGoal(1, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 10.0F, 1.6D, true, entity -> entity.getType().is(UP2EntityTags.DIPLOCAULUS_AVOIDS)));
         this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
         this.goalSelector.addGoal(3, new LeaveWaterGoal(this, 1.0D));
         this.goalSelector.addGoal(3, new EnterWaterGoal(this, 1.0D));
         this.goalSelector.addGoal(4, new DiplocaulusSlideGoal(this, 2.0D));
         this.goalSelector.addGoal(5, new PrehistoricSwimGoal(this, 1.0D, 80));
         this.goalSelector.addGoal(5, new AmphibiousWanderGoal(this, 1.0D) {
             @Override
             public boolean canUse() {
                 return super.canUse() && !Diplocaulus.this.isSliding();
             }
         });
         this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(7, new RandomSitGoal(this) {
             @Override
             public boolean canUse() {
                 return super.canUse() && Diplocaulus.this.canBurrow();
             }

             @Override
             public boolean canContinueToUse() {
                 return super.canContinueToUse() && Diplocaulus.this.canBurrow();
             }
         });
         this.goalSelector.addGoal(8, new IdleAnimationGoal(this, 60, 1, false, 0.001F, this::canQuirk));
         this.goalSelector.addGoal(8, new IdleAnimationGoal(this, 20, 2, false, 0.001F, this::canDash) {
             @Override
             public void start() {
                 super.start();
                 Diplocaulus.this.getNavigation().stop();
                 Diplocaulus.this.addDeltaMovement(Diplocaulus.this.calculateViewVector(0.0F, Diplocaulus.this.getYRot()).scale(2.0D).multiply(0.5D, Diplocaulus.this.getDeltaMovement().y * 0.5D, 0.5D));
             }
         });
     }

     @Override
     protected PathNavigation createNavigation(Level level) {
         return new SmoothAmphibiousNavigation(this, level);
     }

     protected void switchNavigator(boolean onLand) {
         if (onLand) {
             this.moveControl = new PrehistoricMoveControl(this);
             this.lookControl = new PrehistoricLookControl(this);
             this.isLandNavigator = true;
         } else {
             this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.3F);
             this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
             this.isLandNavigator = false;
         }
     }

     @Override
     public void travel(Vec3 travelVector) {
         if (this.refuseToMove() && this.onGround()) {
             if (this.getNavigation().getPath() != null) {
                 this.getNavigation().stop();
             }
             travelVector = travelVector.multiply(0.0, 1.0, 0.0);
         }
         if (this.isEffectiveAi() && this.isInWater()) {
             UP2MobUtils.travelInWater(this, travelVector);
         } else {
             super.travel(travelVector);
         }
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.DIET_PISCIVORE);
     }

     @Override
     public boolean isPushable() {
         return !this.isSitting();
     }

     @Override
     public void doPush(@NotNull Entity entity) {
         if (!this.isSitting()) super.doPush(entity);
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

         if (this.isSliding() && !this.isInWaterOrBubble() && this.getDeltaMovement().horizontalDistance() > 0.05D) {
             for (int i = 0; i < 1; i++) {
                 double velocityX = this.random.nextGaussian() * 0.15D;
                 double velocityY = this.random.nextGaussian() * 0.15D;
                 double velocityZ = this.random.nextGaussian() * 0.15D;
                 this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.getBlockStateOn()), this.getRandomX(1), this.getRandomY() - 0.5D, this.getRandomZ(1) - 0.75D, velocityX, velocityY, velocityZ);
             }
         }
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isSitting() && this.getIdleState() != 2, this.tickCount);
         this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isSitting() && this.getIdleState() != 2, this.tickCount);
         this.burrowAnimationState.animateWhen(this.isSitting(), this.tickCount);
         this.quirkAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
         this.boomerangAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
     }

     private boolean canQuirk(Entity entity) {
         if (entity instanceof Diplocaulus diplocaulus) {
             return !diplocaulus.isSliding() && !diplocaulus.isSitting();
         }
         return false;
     }

     private boolean canDash(Entity entity) {
         if (entity instanceof Diplocaulus diplocaulus) {
             return !diplocaulus.isSliding() && !diplocaulus.isSitting() && diplocaulus.isInWaterOrBubble();
         }
         return false;
     }

     @Override
     public int getIdleAnimationCooldown(int idleState) {
         if (idleState == 1) {
             return 800 + this.getRandom().nextInt(1200);
         } else if (idleState == 2) {
             return 900 + this.getRandom().nextInt(1200);
         } else {
             throw new IllegalStateException("Unexpected value: " + idleState);
         }
     }

     @Override
     public boolean refuseToLook() {
         return this.isEepy();
     }

     @Override
     protected void defineSynchedData(SynchedEntityData.Builder builder) {
         super.defineSynchedData(builder);
         builder.define(VARIANT, 0);
         builder.define(SLIDING, false);
     }

     @Override
     public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.addAdditionalSaveData(compoundTag);
         compoundTag.putInt("Variant", this.getVariant().getId());
     }

     @Override
     public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.readAdditionalSaveData(compoundTag);
         this.setVariant(DiplocaulusVariant.byId(compoundTag.getInt("Variant")));
     }

     @Override
     public @NotNull DiplocaulusVariant getVariant() {
         return DiplocaulusVariant.byId(this.entityData.get(VARIANT));
     }

     @Override
     public void setVariant(DiplocaulusVariant variant) {
         this.entityData.set(VARIANT, Mth.clamp(variant.getId(), 0, DiplocaulusVariant.values().length));
     }

     public boolean isSliding() {
         return this.entityData.get(SLIDING);
     }

     public void setSliding(boolean sliding) {
         this.entityData.set(SLIDING, sliding);
     }

     protected boolean isSlideableBlockBelow() {
         return this.level().getBlockState(this.blockPosition().below()).is(UP2BlockTags.DIPLOCAULUS_SLIDING_BLOCKS) || this.level().getBlockState(this.blockPosition()).is(UP2BlockTags.DIPLOCAULUS_SLIDING_BLOCKS);
     }

     private boolean canBurrow() {
         return this.level().getBlockState(this.blockPosition().below()).is(UP2BlockTags.DIPLOCAULUS_BURROWING_BLOCKS) || this.level().getBlockState(this.blockPosition()).is(UP2BlockTags.DIPLOCAULUS_BURROWING_BLOCKS);
     }

     @Override
     @Nullable
     protected SoundEvent getAmbientSound() {
         return UP2SoundEvents.DIPLOCAULUS_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
         return UP2SoundEvents.DIPLOCAULUS_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.DIPLOCAULUS_DEATH.get();
     }

     @Override
     protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
         if (this.isSliding()) super.playStepSound(pos, state);
         else this.playSound(UP2SoundEvents.DIPLOCAULUS_STEP.get(), 0.15F, 1.0F);
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         Diplocaulus diplocaulus = UP2Entities.DIPLOCAULUS.get().create(level);
         if (diplocaulus != null) {
             diplocaulus.setVariant(this.getVariant());
         }
         return diplocaulus;
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
         return new ItemStack(UP2Items.DIPLOCAULUS_BUCKET.get());
     }

     @Override
     public @NotNull SoundEvent getPickupSound() {
         return SoundEvents.BUCKET_EMPTY_FISH;
     }

     @Override
     public void saveToBucketTag(@NotNull ItemStack bucket) {
         UP2MobUtils.savePrehistoricDataToBucket(this, bucket);
         CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, (compoundTag) -> compoundTag.putInt("Variant", this.getVariant().getId()));
     }

     @Override
     public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
         UP2MobUtils.loadPrehistoricDataFromBucket(this, compoundTag);
         this.setVariant(DiplocaulusVariant.byId(compoundTag.getInt("Variant")));
     }

     @Override
     public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
     }

     public enum DiplocaulusVariant {
         TIGER(0),
         SWAMPY(1),
         MUDDY(2),
         DWARF(3);

         private final int id;

         DiplocaulusVariant(int id) {
             this.id = id;
         }

         public int getId() {
             return this.id;
         }

         public static DiplocaulusVariant byId(int id) {
             if (id < 0 || id >= DiplocaulusVariant.values().length) {
                 id = 0;
             }
             return DiplocaulusVariant.values()[id];
         }
     }

     @Override
     public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
         spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
         if (spawnType == MobSpawnType.BUCKET) {
             return spawnGroupData;
         } else {
             this.setVariant(DiplocaulusVariant.byId(random.nextInt(DiplocaulusVariant.values().length)));
         }
         return spawnGroupData;
     }

     // goals
     private static class DiplocaulusSlideGoal extends Goal {

         protected final Diplocaulus diplocaulus;
         protected double wantedX;
         protected double wantedY;
         protected double wantedZ;
         protected final double speedModifier;

         public DiplocaulusSlideGoal(Diplocaulus diplocaulus, double speedModifier) {
             this.diplocaulus = diplocaulus;
             this.speedModifier = speedModifier;
             this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
         }

         @Override
         public boolean canUse() {
             if (!diplocaulus.isSlideableBlockBelow()) {
                 return false;
             } else if (diplocaulus.isVehicle() || diplocaulus.isPathFinding() || diplocaulus.isInWaterOrBubble()) {
                 return false;
             } else {
                 if (diplocaulus.getNoActionTime() >= 100) return false;
                 if (diplocaulus.getRandom().nextInt(reducedTickDelay(120)) != 0) return false;
                 Vec3 vec3 = this.getPosition();
                 if (vec3 == null) return false;
                 else {
                     this.wantedX = vec3.x;
                     this.wantedY = vec3.y;
                     this.wantedZ = vec3.z;
                     return true;
                 }
             }
         }

         @Override
         public void tick() {
             this.diplocaulus.getLookControl().setLookAt(wantedX, wantedY, wantedZ, 30F, 30F);
         }

         @Nullable
         protected Vec3 getPosition() {
             return DefaultRandomPos.getPos(diplocaulus, 20, 1);
         }

         @Override
         public boolean canContinueToUse() {
             return !diplocaulus.getNavigation().isDone() && !diplocaulus.isVehicle() && !diplocaulus.isInWaterOrBubble() && diplocaulus.isSlideableBlockBelow();
         }

         @Override
         public void start() {
             this.diplocaulus.setSliding(true);
             this.diplocaulus.getNavigation().moveTo(wantedX, wantedY, wantedZ, speedModifier);
         }

         @Override
         public void stop() {
             this.diplocaulus.setSliding(false);
             this.diplocaulus.getNavigation().stop();
         }
     }
 }
