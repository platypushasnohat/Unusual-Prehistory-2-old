 package com.barlinc.unusual_prehistory.entity.mob.mesozoic;

 import com.barlinc.unusual_prehistory.UnusualPrehistory2;
 import com.barlinc.unusual_prehistory.entity.ai.goals.*;
 import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundNavigation;
 import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
 import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricPartEntity;
 import com.barlinc.unusual_prehistory.entity.utils.PlushableMob;
 import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
 import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barlinc.unusual_prehistory.events.custom.ScreenShakeEvent;
 import com.barlinc.unusual_prehistory.registry.UP2Blocks;
 import com.barlinc.unusual_prehistory.registry.UP2Entities;
 import com.barlinc.unusual_prehistory.registry.UP2Particles;
 import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
 import com.barlinc.unusual_prehistory.utils.UP2Math;
 import net.minecraft.core.BlockPos;
 import net.minecraft.core.particles.BlockParticleOption;
 import net.minecraft.core.particles.ParticleTypes;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.util.Mth;
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
 import net.minecraft.world.level.pathfinder.PathType;
 import net.minecraft.world.phys.AABB;
 import net.minecraft.world.phys.Vec3;
 import net.neoforged.neoforge.entity.PartEntity;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 public class Brachiosaurus extends PrehistoricAmphibiousMob implements PlushableMob {

     private static final EntityDataAccessor<Integer> STOMP_COOLDOWN = SynchedEntityData.defineId(Brachiosaurus.class, EntityDataSerializers.INT);

     private final BrachiosaurusPart[] allParts;
     private final BrachiosaurusPart headPart;
     private final BrachiosaurusPart neckPart1;
     private final BrachiosaurusPart neckPart2;
     private final BrachiosaurusPart tailPart1;
     private final BrachiosaurusPart tailPart2;

     private double lastStompX = 0;
     private double lastStompZ = 0;
     private float screenShakeAmount;

     private float neckXRot;
     private float neckYRot;

     private float fakeYRot = 0;
     @SuppressWarnings("FieldMayBeFinal")
     private float[] yawBuffer = new float[128];
     private int yawPointer = -1;

     private boolean wasPreviouslyBaby;

     public final SmoothAnimationState stompAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState callAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();

     public Brachiosaurus(EntityType<? extends PrehistoricAmphibiousMob> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(PathType.WATER, 0.0F);
         this.setPathfindingMalus(PathType.WATER_BORDER, 0.0F);
         this.setPathfindingMalus(PathType.FENCE, 0.0F);
         this.headPart = new BrachiosaurusPart(this, 2.5F, 2.5F);
         this.neckPart1 = new BrachiosaurusPart(this, 2.5F, 6.0F);
         this.neckPart2 = new BrachiosaurusPart(this, 2.5F, 6.0F);
         this.tailPart1 = new BrachiosaurusPart(this, 3.0F, 3.0F);
         this.tailPart2 = new BrachiosaurusPart(this, 3.0F, 1.5F);
         this.allParts = new BrachiosaurusPart[]{headPart, neckPart1, neckPart2, tailPart1, tailPart2};
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 400.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.17F)
                 .add(Attributes.ATTACK_DAMAGE, 21.0D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                 .add(Attributes.FOLLOW_RANGE, 20.0D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(0, new PrehistoricBabyPanicGoal(this, 1.8D, 10, 4));
         this.goalSelector.addGoal(1, new BrachiosaurusAttackGoal(this));
         this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_HERBIVORE), false));
         this.goalSelector.addGoal(3, new PrehistoricWanderGoal(this, 1.0D, false));
         this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
         this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(6, new EepyGoal(this));
         this.goalSelector.addGoal(7, new IdleAnimationGoal(this, 60, 1, true, 0.001F) {
             @Override
             public void start() {
                 super.start();
                 Brachiosaurus.this.playSound(UP2SoundEvents.BRACHIOSAURUS_CALL.get(), 4.0F, 0.9F + Brachiosaurus.this.getRandom().nextFloat() * 0.15F);
             }
         });
         this.goalSelector.addGoal(7, new IdleAnimationGoal(this, 100, 2, false, 0.001F));
         this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
     }

     @Override
     protected float getWaterSlowDown() {
         return 0.9F;
     }

     @Override
     protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
         SmoothGroundNavigation navigation = new SmoothGroundNavigation(this, level);
         navigation.setCanWalkOverFences(true);
         return navigation;
     }

     @Override
     public void travel(@NotNull Vec3 travelVec) {
         if (this.refuseToMove() && this.onGround()) {
             if (this.getNavigation().getPath() != null) {
                 this.getNavigation().stop();
             }
             travelVec = travelVec.multiply(0.0, 1.0, 0.0);
         }
         if (this.isEffectiveAi() && this.isInWater() && !this.onGround()) {
             this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.01D, 0.0D));
         }
         super.travel(travelVec);
     }

     @Override
     public float maxUpStep() {
         return this.isBaby() ? 1.1F : 3.0F;
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.DIET_HERBIVORE);
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove() || this.getIdleState() == 1;
     }

     @Override
     public boolean canBeCollidedWith() {
         return !this.isAggressive() && !this.isBaby() && this.isAlive();
     }

     @Override
     public boolean isPushable() {
         return this.isBaby();
     }

     @Override
     public void doEepyParticles() {
         Vec3 lookVec = this.getEepyParticleVec();
         if (eepyTicks == 0) {
             this.eepyTicks = 60 + this.getRandom().nextInt(20);
             this.level().addParticle(UP2Particles.EEPY.get(), headPart.getX() + lookVec.x, headPart.getEyeY() + lookVec.y, headPart.getZ() + lookVec.z, 0, 0, 0);
         }
         if (eepyTicks > 0) {
             this.eepyTicks--;
         }
     }

     @Override
     public Vec3 getEepyParticleVec() {
         return new Vec3(0.0D, 1.5D, headPart.getBbWidth() * 0.25F).yRot(-yBodyRot * ((float) Math.PI / 180F));
     }

     @Override
     public @NotNull ItemStack getPlushieItemStack() {
         return new ItemStack(UP2Blocks.BRACHIOSAURUS_PLUSHIE.get());
     }

     public AABB getRidingBox() {
         return this.getBoundingBox().move(0, this.getBbHeight() / 2, 0).deflate(0, this.getBbHeight() * 0.8F, 0).move(0, -this.getBbHeight() * 0.1F, 0);
     }

     @Override
     public void tick() {

//         AABB movementBox = this.getRidingBox();
//         for (Entity entity : this.level().getEntities(this, movementBox, EntitySelector.NO_SPECTATORS.and((entity) -> !entity.isPassengerOfSameVehicle(this)))) {
//             if (!(entity instanceof Brachiosaurus) && !entity.noPhysics && entity.onGround() && !entity.isPassenger()) {
//                 float scale = 0.83F;
//                 entity.setDeltaMovement(entity.getDeltaMovement().add(this.getDeltaMovement().multiply(scale, 1.0F, scale)));
//             }
//         }

         if (!this.isBaby()) {
             this.tickMultipart();
         }

         super.tick();

         this.lastStompX = this.getX();
         this.lastStompZ = this.getZ();

         if (!this.isBaby()) {
             this.yBodyRot = Mth.approachDegrees(this.yBodyRotO, this.getYRot(), 5);
         }

         this.fakeYRot = Mth.approachDegrees(fakeYRot, this.yBodyRot, 10);

         if (screenShakeAmount > 0) screenShakeAmount = Math.max(0, screenShakeAmount - 0.34F);
         if (this.onGround() && this.walkAnimation.speed() > 0.1F && !this.isBaby()) {
             this.tickFootsteps();
         }

         if (wasPreviouslyBaby != this.isBaby()) {
             this.wasPreviouslyBaby = this.isBaby();
             this.refreshDimensions();
             for (BrachiosaurusPart part : this.allParts) {
                 part.refreshDimensions();
             }
         }

         if (this.getStompCooldown() > 0) this.setStompCooldown(this.getStompCooldown() - 1);
     }

     @Override
     public float getAgeScale() {
         return this.isBaby() ? 0.25F : 1.0F;
     }

     private void tickMultipart() {
         if (yawPointer == -1) {
             this.fakeYRot = this.yBodyRot;
             for (int i = 0; i < yawBuffer.length; i++) {
                 this.yawBuffer[i] = this.fakeYRot;
             }
         }
         if (++this.yawPointer == this.yawBuffer.length) {
             this.yawPointer = 0;
         }
         this.yawBuffer[this.yawPointer] = this.fakeYRot;

         Vec3[] vec3s = new Vec3[this.allParts.length];
         for (int j = 0; j < this.allParts.length; ++j) {
             vec3s[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
         }

         Vec3 center = this.position().add(0, this.getBbHeight(), 0);
         this.neckXRot = this.wrapNeckDegrees(Mth.approachDegrees(this.neckXRot, -30.0F, 45.0F));
         this.neckYRot = this.wrapNeckDegrees(Mth.approachDegrees(this.neckYRot, this.getTargetNeckYRot(), 45.0F));
         float headXStep = neckXRot / 4F;
         float headYStep = neckYRot / 4F;

         float headOffset = 0.0F;

         if (this.isEepy()) {
             headOffset = -2.5F;
         }

         this.headPart.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 10.5F + headOffset, 7.8F + headOffset).scale(this.getAgeScale()), headXStep, (yBodyRot + headYStep)).add(center));
         this.neckPart1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, -4.0F, -2.2F).scale(this.getAgeScale()), headXStep, (yBodyRot + headYStep)).add(this.headPart.centeredPosition()));
         this.neckPart2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, -6.0F, -2.2F).scale(this.getAgeScale()), headXStep, (yBodyRot + headYStep)).add(this.neckPart1.centeredPosition()));
         this.tailPart1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, -4.5F, -4.5F), 0.0F, this.getYawFromBuffer(2, 1.0F)).scale(this.getAgeScale()).add(center));
         this.tailPart2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, -1.5F, -3.0F), 0.0F, this.getYawFromBuffer(4, 1.0F)).scale(this.getAgeScale()).add(this.tailPart1.centeredPosition()));

         for (int l = 0; l < this.allParts.length; ++l) {
             this.allParts[l].xo = vec3s[l].x;
             this.allParts[l].yo = vec3s[l].y;
             this.allParts[l].zo = vec3s[l].z;
             this.allParts[l].xOld = vec3s[l].x;
             this.allParts[l].yOld = vec3s[l].y;
             this.allParts[l].zOld = vec3s[l].z;
         }
     }

     private float wrapNeckDegrees(float f) {
         return f % 360.0F;
     }

     private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
         return offset.xRot(-xRot * ((float) Math.PI / 180F)).yRot(-yRot * ((float) Math.PI / 180F));
     }

     public float getYawFromBuffer(int pointer, float partialTick) {
         int i = this.yawPointer - pointer & 127;
         int j = this.yawPointer - pointer - 1 & 127;
         float d0 = this.yawBuffer[j];
         float d1 = this.yawBuffer[i] - d0;
         return d0 + d1 * partialTick;
     }

     public float getTrailTransformation(int pointer, float partialTick) {
         if (this.isRemoved()) {
             partialTick = 1.0F;
         }
         int i = this.yawPointer - pointer & 127;
         int j = this.yawPointer - pointer - 1 & 127;
         float d0 = this.yawBuffer[j];
         float d1 = this.yawBuffer[i] - d0;
         return d0 + d1 * partialTick;
     }

     public float getTargetNeckYRot() {
         float buffered = this.getYawFromBuffer(10, 1.0F) - this.yBodyRot;
         return this.getYHeadRot() - this.yBodyRot + buffered;
     }

     @Override
     public boolean isMultipartEntity() {
         return !this.isBaby();
     }

     @Override
     public PartEntity<?> @NotNull [] getParts() {
         return this.isBaby() ? super.getParts() : allParts;
     }

     private void tickFootsteps() {
         float walkPosition = (float) Math.cos(this.walkAnimation.position() * 0.515F - 1.5F);
         if (Math.abs(walkPosition) < 0.2F) {
             if (this.screenShakeAmount <= 0.3F) {
                 if (this.isInFluidType()) {
                     this.playSound(UP2SoundEvents.BRACHIOSAURUS_STEP.get(), 1.5F, 0.8F + this.getRandom().nextFloat() * 0.2F);
                 } else {
                     this.playSound(UP2SoundEvents.BRACHIOSAURUS_STEP.get(), 2.5F, 0.9F + this.getRandom().nextFloat() * 0.2F);
                 }
                 UnusualPrehistory2.PROXY.screenShake(new ScreenShakeEvent(this.position(), 10, 2.5F, 16, false));
             }
             this.screenShakeAmount = 1F;
         }
     }

     @Override
     public void calculateEntityAnimation(boolean flying) {
         float f1 = (float) Mth.length(this.getX() - this.lastStompX, 0, this.getZ() - this.lastStompZ);
         float f2 = Math.min(f1 * this.getWalkAnimationSpeed(), 1.0F);
         this.walkAnimation.update(f2, 0.4F);
     }

     @Override
     public float getWalkAnimationSpeed() {
         return this.isBaby() ? 3.0F : 4.0F;
     }

     @Override
     public int getHeadRotSpeed() {
         return this.isBaby() ? super.getHeadRotSpeed() : 4;
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(this.getPose() != UP2Poses.STOMPING.get() && !this.isEepy(), this.tickCount);
         this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
         this.stompAnimationState.animateWhen(this.getPose() == UP2Poses.STOMPING.get(), this.tickCount);
         this.callAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
         this.shakeAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
     }

     @Override
     public int getIdleAnimationCooldown(int idleState) {
         if (idleState == 1) {
             return 1300 + this.getRandom().nextInt(1200);
         }
         else if (idleState == 2) {
             return 800 + this.getRandom().nextInt(1200);
         }
         else {
             throw new IllegalStateException("Unexpected value: " + idleState);
         }
     }

     @Override
     protected void defineSynchedData(SynchedEntityData.Builder builder) {
         super.defineSynchedData(builder);
         builder.define(STOMP_COOLDOWN, 0);
     }

     public void setStompCooldown(int cooldown) {
         this.entityData.set(STOMP_COOLDOWN, cooldown);
     }

     public int getStompCooldown() {
         return this.entityData.get(STOMP_COOLDOWN);
     }

     @Override
     public @NotNull AABB getBoundingBoxForCulling() {
         return this.getBoundingBox().inflate(4, 6, 4);
     }

     @Override
     public boolean shouldRenderAtSqrDistance(double distance) {
         return Math.sqrt(distance) < 1024.0D;
     }

     public void handleEntityEvent(byte id) {
         if (id == 40) {
             this.stompEffect();
         } else {
             super.handleEntityEvent(id);
         }
     }

     private void stompEffect() {
         Vec3 groundedVec = UP2Math.getGroundBelowPosition(level(), new Vec3(this.getRandomX(2.0D), this.getY() + 0.25F, this.getRandomZ(2.0D)));
         BlockPos ground = BlockPos.containing(groundedVec.subtract(0, 0.5F, 0));
         BlockState state = this.level().getBlockState(ground);
         for (int i = 0; i <= (this.getRandom().nextInt(60) + 80); i++) {
             this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), true, this.getRandomX(2.0D), this.getY() + 0.25F, this.getRandomZ(2.0D), 0.0D, 0.0D, 0.0D);
         }
     }

     @Override
     @Nullable
     protected SoundEvent getAmbientSound() {
         return UP2SoundEvents.BRACHIOSAURUS_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource source) {
         return UP2SoundEvents.BRACHIOSAURUS_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.BRACHIOSAURUS_DEATH.get();
     }

     @Override
     protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
         if (this.isBaby()) {
             super.playStepSound(pos, state);
         }
     }

     @Override
     public float getSoundVolume() {
         return this.isBaby() ? 1.0F : 3.0F;
     }

     @Override
     public int getAmbientSoundInterval() {
         return 200;
     }

     @Override
     public boolean canPlayAmbientSound() {
         return super.canPlayAmbientSound() && this.getIdleState() != 1;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.BRACHIOSAURUS.get().create(level);
     }

     // Goals
     private static class BrachiosaurusAttackGoal extends AttackGoal {

         private final Brachiosaurus brachiosaurus;

         public BrachiosaurusAttackGoal(Brachiosaurus brachiosaurus) {
             super(brachiosaurus);
             this.brachiosaurus = brachiosaurus;
         }

         @Override
         public void tick() {
             LivingEntity target = brachiosaurus.getTarget();
             if (target != null) {
                 double distance = brachiosaurus.distanceToSqr(target);
                 this.brachiosaurus.lookAt(target, 30F, 30F);
                 this.brachiosaurus.getLookControl().setLookAt(target, 30F, 30F);

                 if (this.brachiosaurus.getAttackState() == 1) {
                     this.brachiosaurus.getNavigation().stop();
                     this.tickStomp(target);
                 }
                 else {
                     if (distance > this.getAttackReachSqr(target)) {
                         this.brachiosaurus.getNavigation().moveTo(target, 1.5D);
                     }
                     if (distance <= this.getAttackReachSqr(target) && brachiosaurus.getStompCooldown() <= 0) {
                         brachiosaurus.setAttackState(1);
                     }
                 }
             }
         }

         protected void tickStomp(LivingEntity target) {
             this.timer++;
             if (timer == 6) brachiosaurus.playSound(UP2SoundEvents.BRACHIOSAURUS_ATTACK.get(), 4.0F, 1.0F);
             if (timer == 10) brachiosaurus.setPose(UP2Poses.STOMPING.get());
             if (timer == 51) {
                 for (LivingEntity entity : brachiosaurus.level().getEntitiesOfClass(LivingEntity.class, brachiosaurus.getBoundingBox().inflate(6.0D, -0.5D, 6.0D))) {
                     if (entity == brachiosaurus) {
                         continue;
                     }
                     entity.hurt(brachiosaurus.damageSources().mobAttack(brachiosaurus), (float) (brachiosaurus.getAttributeValue(Attributes.ATTACK_DAMAGE)));
                     this.strongKnockback(entity, 6.0D, 0.6D);
                 }
                 UnusualPrehistory2.PROXY.screenShake(new ScreenShakeEvent(brachiosaurus.position(), 40, 4.0F, 32, false));
                 this.brachiosaurus.level().broadcastEntityEvent(brachiosaurus, (byte) 40);
             }

             if (timer > 70) {
                 this.brachiosaurus.lookAt(target, 30F, 30F);
                 this.brachiosaurus.getLookControl().setLookAt(target, 30F, 30F);
             }

             if (this.timer > 80) {
                 this.timer = 0;
                 this.brachiosaurus.setPose(Pose.STANDING);
                 this.brachiosaurus.setAttackState(0);
                 this.brachiosaurus.setStompCooldown(24 + brachiosaurus.getRandom().nextInt(20));
             }
         }
     }

     private static class BrachiosaurusPart extends PrehistoricPartEntity<Brachiosaurus> {

         public BrachiosaurusPart(Brachiosaurus parent, float width, float height) {
             super(parent, width, height);
         }
     }
 }
