 package com.barlinc.unusual_prehistory.entity.mob.cenozoic;

 import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
 import com.barlinc.unusual_prehistory.entity.ai.goals.EnterWaterGoal;
 import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricWanderGoal;
 import com.barlinc.unusual_prehistory.entity.ai.goals.RandomUnderwaterSitGoal;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundNavigation;
 import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
 import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricPartEntity;
 import com.barlinc.unusual_prehistory.entity.utils.PlushableMob;
 import com.barlinc.unusual_prehistory.network.GiantCampanilePartPacket;
 import com.barlinc.unusual_prehistory.registry.UP2Blocks;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2MobEffects;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
 import net.minecraft.core.BlockPos;
 import net.minecraft.nbt.CompoundTag;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.tags.FluidTags;
 import net.minecraft.util.Mth;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.effect.MobEffectInstance;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.TemptGoal;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.ai.targeting.TargetingConditions;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.LevelReader;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.PathType;
 import net.minecraft.world.phys.Vec3;
 import net.neoforged.neoforge.entity.PartEntity;
 import net.neoforged.neoforge.network.PacketDistributor;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class GiantCampanile extends PrehistoricAmphibiousMob implements PlushableMob {

     private static final EntityDataAccessor<Integer> MOISTNESS = SynchedEntityData.defineId(GiantCampanile.class, EntityDataSerializers.INT);

     private final GiantCampanilePart[] allParts;
     private final GiantCampanilePart shellPart1;
     private final GiantCampanilePart shellPart2;
     private final GiantCampanilePart shellPart3;
     private final GiantCampanilePart shellPart4;

     @SuppressWarnings("all")
     private float[] yawBuffer = new float[128];
     private int yawPointer = -1;

     private boolean wasPreviouslyBaby;

     public GiantCampanile(EntityType<? extends PrehistoricAmphibiousMob> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(PathType.WATER, 0.0F);
         this.moveControl = new PrehistoricSwimmingMoveControl(this, 20, 85, 0.4F, 0.55F);
         this.shellPart1 = new GiantCampanilePart(this, 1.5F, 4.25F);
         this.shellPart2 = new GiantCampanilePart(this, 1.5F, 2.25F);
         this.shellPart3 = new GiantCampanilePart(this, 1.25F, 2.25F);
         this.shellPart4 = new GiantCampanilePart(this, 1.0F, 2.0F);
         this.allParts = new GiantCampanilePart[]{shellPart1, shellPart2, shellPart3, shellPart4};
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 30.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.125F)
                 .add(Attributes.ARMOR, 4.0D)
                 .add(Attributes.STEP_HEIGHT, 1.1D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new EnterWaterGoal(this, 1.2D, 30, false));
         this.goalSelector.addGoal(1, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_HERBIVORE), false));
         this.goalSelector.addGoal(2, new PrehistoricWanderGoal(this, 1.0D, false));
         this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(4, new RandomUnderwaterSitGoal(this));
     }

     @Override
     public @NotNull PathNavigation createNavigation(@NotNull Level level) {
         return new SmoothGroundNavigation(this, level);
     }

     @Override
     public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
         return level.getFluidState(pos.above()).is(FluidTags.WATER) ? 10.0F : 0.0F;
     }

     @Override
     public void travel(@NotNull Vec3 travelVector) {
         if (this.refuseToMove()) {
             if (this.getNavigation().getPath() != null) {
                 this.getNavigation().stop();
             }
             travelVector = travelVector.multiply(0.0, 1.0, 0.0);
         }
         if (this.isEffectiveAi() && this.isInWater()) {
             this.moveRelative(this.getSpeed(), travelVector);
             this.move(MoverType.SELF, this.getDeltaMovement());
             this.setDeltaMovement(this.getDeltaMovement().scale(0.6F));
             if (this.jumping) {
                 this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.3D, 0.0D));
             } else {
                 this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.07D, 0.0D));
             }
             this.calculateEntityAnimation(false);
         } else {
             super.travel(travelVector);
         }
     }

     @Override
     public @NotNull ItemStack getPlushieItemStack() {
         return new ItemStack(UP2Blocks.GIANT_CAMPANILE_PLUSHIE.get());
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.DIET_HERBIVORE);
     }

     @Override
     public boolean isPushable() {
         return false;
     }

     @Override
     public boolean canBeCollidedWith() {
         return this.isSitting();
     }

     @Override
     public boolean canSleepCooldown() {
         return this.isInWaterOrBubble();
     }

     @Override
     public void aiStep() {
         if (this.isInWaterOrBubble() && this.level().getGameTime() % 20 == 0) {
             for (LivingEntity living : this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat().selector(entity -> entity instanceof Player || entity instanceof Mob).ignoreLineOfSight().ignoreInvisibilityTesting(), this, this.getBoundingBox().inflate(20.0D))) {
                 if (!this.level().isClientSide) {
                     living.addEffect(new MobEffectInstance(UP2MobEffects.TRANQUILITY, 100, 0, true, false, true));
                 }
             }
         }
         super.aiStep();
     }

     @Override
     public void tick() {
         this.tickMultipart();
         super.tick();
         if (wasPreviouslyBaby != this.isBaby()) {
             this.wasPreviouslyBaby = this.isBaby();
             this.refreshDimensions();
             for (GiantCampanilePart part : this.allParts) {
                 part.refreshDimensions();
             }
         }

         if (!this.isNoAi()) {
             if (this.isInWaterRainOrBubble()) {
                 this.setMoistness(2400);
             } else {
                 if (this.getMoistness() > 0) {
                     this.setMoistness(this.getMoistness() - 1);
                 }
                 if (this.getMoistness() <= 0) {
                     this.hurt(this.damageSources().dryOut(), 2.0F);
                 }
             }
         }
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(!this.isSitting(), this.tickCount);
         this.sitAnimationState.animateWhen(this.isSitting(), this.tickCount);
     }

     @Override
     public void calculateEntityAnimation(boolean flying) {
         float pos = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
         float speed = Math.min(pos * this.getWalkAnimationSpeed() * 8.0F, 1.0F);
         this.walkAnimation.update(speed, 0.4F);
     }

     private void tickMultipart() {
         if (yawPointer == -1) {
             for (int i = 0; i < yawBuffer.length; i++) {
                 this.yawBuffer[i] = this.yBodyRot;
             }
         }
         if (++this.yawPointer == this.yawBuffer.length) {
             this.yawPointer = 0;
         }
         this.yawBuffer[this.yawPointer] = this.yBodyRot;

         Vec3[] vec3 = new Vec3[this.allParts.length];
         for (int j = 0; j < this.allParts.length; ++j) {
             vec3[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
         }

         Vec3 center = this.position().add(0, this.getBbHeight(), 0);
         this.shellPart1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, -1.75F, 0.0F).scale(this.getAgeScale()), yBodyRot).add(center));
         this.shellPart2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, -3.5F, this.isSitting() ? 0.0F : -1.25F).scale(this.getAgeScale()), yBodyRot).add(center));
         this.shellPart3.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, -1.25F, this.isSitting() ? 0.0F : -1.75F).scale(this.getAgeScale()), yBodyRot).add(center));
         this.shellPart4.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, -0.5F, this.isSitting() ? 0.0F : -2.75F).scale(this.getAgeScale()), yBodyRot).add(center));

         for (int l = 0; l < this.allParts.length; l++) {
             this.allParts[l].xo = vec3[l].x;
             this.allParts[l].yo = vec3[l].y;
             this.allParts[l].zo = vec3[l].z;
             this.allParts[l].xOld = vec3[l].x;
             this.allParts[l].yOld = vec3[l].y;
             this.allParts[l].zOld = vec3[l].z;
         }
     }

     private Vec3 rotateOffsetVec(Vec3 offset, float yRot) {
         return offset.yRot(-yRot * ((float) Math.PI / 180F));
     }

     @Override
     public void remove(@NotNull RemovalReason removalReason) {
         super.remove(removalReason);
         if (allParts != null) {
             for (GiantCampanilePart part : allParts) {
                 part.remove(RemovalReason.KILLED);
             }
         }
     }

     @Override
     public boolean isMultipartEntity() {
         return true;
     }

     @Override
     public PartEntity<?> @NotNull [] getParts() {
         return allParts;
     }

     @Override
     protected void defineSynchedData(SynchedEntityData.Builder builder) {
         super.defineSynchedData(builder);
         builder.define(MOISTNESS, 2400);
     }

     @Override
     public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.addAdditionalSaveData(compoundTag);
         compoundTag.putInt("Moistness", this.getMoistness());
     }

     @Override
     public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
         super.readAdditionalSaveData(compoundTag);
         this.setMoistness(compoundTag.getInt("Moistness"));
     }

     public int getMoistness() {
         return this.entityData.get(MOISTNESS);
     }

     public void setMoistness(int moistness) {
         this.entityData.set(MOISTNESS, moistness);
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.GIANT_CAMPANILE.get().create(level);
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource source) {
         return UP2SoundEvents.GIANT_CAMPANILE_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.GIANT_CAMPANILE_DEATH.get();
     }

     @Override
     protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
         this.playSound(UP2SoundEvents.GIANT_CAMPANILE_STEP.get(), 0.12F, 1.0F);
     }

     private static class GiantCampanilePart extends PrehistoricPartEntity<GiantCampanile> {

         public GiantCampanilePart(GiantCampanile parent, float width, float height) {
             super(parent, width, height);
         }

         @Override
         public boolean hurt(@NotNull DamageSource source, float amount) {
             GiantCampanile parent = this.getParent();
             if (!this.isInvulnerableTo(source)) {
                 Entity attacker = source.getEntity();
                 if (attacker != null && attacker.level().isClientSide) {
                     PacketDistributor.sendToServer(new GiantCampanilePartPacket(parent.getId(), attacker.getId()));
                     return false;
                 } else if (attacker == null || !attacker.level().isClientSide) {
                     return false;
                 }
             }
             return false;
         }
     }
 }
