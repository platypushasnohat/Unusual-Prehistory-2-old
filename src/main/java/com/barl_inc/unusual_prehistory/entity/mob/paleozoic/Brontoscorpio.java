 package com.barl_inc.unusual_prehistory.entity.mob.paleozoic;

 import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricBodyRotationControl;
 import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
 import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
 import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothGroundNavigation;
 import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
 import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
 import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
 import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
 import com.barl_inc.unusual_prehistory.registry.UP2Entities;
 import com.barl_inc.unusual_prehistory.registry.UP2MobEffects;
 import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
 import net.minecraft.core.BlockPos;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.util.Mth;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.effect.MobEffectInstance;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.control.BodyRotationControl;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.TemptGoal;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.pathfinder.PathType;
 import net.minecraft.world.phys.Vec3;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 import java.util.function.Predicate;

 public class Brontoscorpio extends PrehistoricAmphibiousMob {

     private int attackCooldown = 0;

     public final SmoothAnimationState warnAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState(1.0F);
     public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState(1.0F);
     public final SmoothAnimationState stingAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState snip1AnimationState = new SmoothAnimationState(1.0F);
     public final SmoothAnimationState snip2AnimationState = new SmoothAnimationState(1.0F);
     public final SmoothAnimationState quirkAnimationState = new SmoothAnimationState(1.0F);
     public final SmoothAnimationState feedAnimationState = new SmoothAnimationState(1.0F);

     private int warnTicks = 0;
     private boolean attackAlt = false;
     private boolean snipAlt = false;

     public Brontoscorpio(EntityType<? extends PrehistoricAmphibiousMob> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(PathType.WATER, 0.0F);
         this.setPathfindingMalus(PathType.WATER_BORDER, 0.0F);
         this.moveControl = new PrehistoricSwimmingMoveControl(this, 20, 85, 0.5F, 0.65F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 8.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.23F)
                 .add(Attributes.ARMOR, 5.0D)
                 .add(Attributes.STEP_HEIGHT, 1.1D)
                 .add(Attributes.ATTACK_DAMAGE, 5.0D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(1, new AmphibiousPanicGoal(this, 1.7D));
         this.goalSelector.addGoal(2, new BrontoscorpioAttackGoal(this));
         this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
         this.goalSelector.addGoal(4, new PrehistoricWanderGoal(this, 1.0D, false));
         this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
         this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 20, 1, false, 0.001F));
         this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 60, 2, true, 0.001F));
         this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 80, 3, true, 0.001F, this::canFeed));
         this.targetSelector.addGoal(1, new BrontoscorpioTargetNearbyPlayersGoal(this, this::canAttack));
     }

     @Override
     protected @NotNull BodyRotationControl createBodyControl() {
         return new BrontoscorpioBodyRotationControl(this);
     }

     @Override
     public @NotNull PathNavigation createNavigation(@NotNull Level level) {
         return new SmoothGroundNavigation(this, level);
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
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.DIET_PISCIVORE);
     }

     @Override
     public void thunderHit(@NotNull ServerLevel level, @NotNull LightningBolt lightning) {
         this.hurt(this.damageSources().lightningBolt(), 1);
     }

     @Override
     public boolean refuseToMove() {
         return super.refuseToMove() || this.getPose() == UP2Poses.WARNING.get() || this.getIdleState() == 2 || this.getIdleState() == 3;
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(this.getPose() != UP2Poses.WARNING.get() && this.getPose() != UP2Poses.TAIL_WHIPPING.get(), this.tickCount);
         this.warnAnimationState.animateWhen(this.getPose() == UP2Poses.WARNING.get(), this.tickCount);
         this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
         this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
         this.stingAnimationState.animateWhen(this.getPose() == UP2Poses.TAIL_WHIPPING.get(), this.tickCount);
         this.snip1AnimationState.animateWhen(this.getIdleState() == 1 && !snipAlt, this.tickCount);
         this.snip2AnimationState.animateWhen(this.getIdleState() == 1 && snipAlt, this.tickCount);
         this.quirkAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
         this.feedAnimationState.animateWhen(this.getIdleState() == 3, this.tickCount);
     }

     private boolean canFeed(Entity entity) {
         return entity.isInWaterOrBubble();
     }

     @Override
     public int getIdleAnimationCooldown(int idleState) {
         if (idleState == 1) {
             return 900 + this.getRandom().nextInt(1200);
         }
         else if (idleState == 2) {
             return 1000 + this.getRandom().nextInt(1200);
         }
         else if (idleState == 3) {
             return 1200 + this.getRandom().nextInt(1200);
         }
         else {
             throw new IllegalStateException("Unexpected value: " + idleState);
         }
     }

     @Override
     public void tickCooldowns() {
         super.tickCooldowns();
         if (attackCooldown > 0) {
             this.attackCooldown--;
         }
         if (warnTicks > 0) {
             this.warnTicks--;
         }
         if (warnTicks == 0 && this.getPose() == UP2Poses.WARNING.get()) {
             this.setPose(Pose.STANDING);
         }
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         if (DATA_POSE.equals(accessor)) {
             if (this.getPose() == UP2Poses.WARNING.get()) {
                 this.warnTicks = 60;
             }
             else if (this.getPose() == UP2Poses.ATTACKING.get()) {
                 this.attackAlt = this.getRandom().nextBoolean();
             }
         }
         if (IDLE_STATE.equals(accessor)) {
             if (this.getIdleState() == 1) {
                 this.snipAlt = this.getRandom().nextBoolean();
             }
         }
     }

     @Override
     @Nullable
     protected SoundEvent getAmbientSound() {
         return UP2SoundEvents.BRONTOSCORPIO_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource source) {
         return UP2SoundEvents.BRONTOSCORPIO_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.BRONTOSCORPIO_DEATH.get();
     }

     @Override
     protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
         this.playSound(UP2SoundEvents.BRONTOSCORPIO_STEP.get(), 0.12F, 1.0F);
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.BRONTOSCORPIO.get().create(level);
     }

     // Goals
     private static class BrontoscorpioAttackGoal extends AttackGoal {

         private final Brontoscorpio brontoscorpio;

         public BrontoscorpioAttackGoal(Brontoscorpio brontoscorpio) {
             super(brontoscorpio);
             this.brontoscorpio = brontoscorpio;
         }

         @Override
         public void start() {
             super.start();
             this.brontoscorpio.setPose(UP2Poses.WARNING.get());
             this.brontoscorpio.playSound(UP2SoundEvents.BRONTOSCORPIO_IDLE.get(), 1.25F, 0.8F + brontoscorpio.getRandom().nextFloat() * 0.2F);
         }

         @Override
         public void tick() {
             LivingEntity target = brontoscorpio.getTarget();
             if (target != null) {
                 double distance = brontoscorpio.distanceTo(target);
                 int attackState = brontoscorpio.getAttackState();

                 this.brontoscorpio.lookAt(target, 30F, 30F);
                 this.brontoscorpio.getLookControl().setLookAt(target, 30F, 30F);

                 if (attackState == 1) {
                     this.tickAttack(target);
                 }
                 else if (attackState == 2) {
                     this.tickSting(target);
                 }
                 else if (distance < 2.2D && brontoscorpio.getPose() == Pose.STANDING) {
                     if (brontoscorpio.attackCooldown == 0) {
                         if (brontoscorpio.getRandom().nextFloat() < 0.4F) {
                             this.brontoscorpio.setAttackState(2);
                         } else {
                             this.brontoscorpio.setAttackState(1);
                         }
                     }
                 }
             }
         }

         protected void tickAttack(LivingEntity target) {
             this.timer++;
             if (timer == 1) {
                 this.brontoscorpio.setPose(UP2Poses.ATTACKING.get());
             }
             if (timer == 15) {
                 if (this.isInAttackRange(target, 1.5D)) {
                     this.brontoscorpio.doHurtTarget(target);
                     this.brontoscorpio.swing(InteractionHand.MAIN_HAND);
                 }
             }
             if (timer > 20) {
                 this.timer = 0;
                 this.brontoscorpio.attackCooldown = 5;
                 this.brontoscorpio.setPose(Pose.STANDING);
                 this.brontoscorpio.setAttackState(0);
             }
         }

         protected void tickSting(LivingEntity target) {
             this.timer++;
             if (timer == 1) {
                 this.brontoscorpio.setPose(UP2Poses.TAIL_WHIPPING.get());
             }
             if (timer == 18) {
                 if (this.isInAttackRange(target, 1.5D)) {
                     this.brontoscorpio.doHurtTarget(target);
                     this.brontoscorpio.swing(InteractionHand.MAIN_HAND);
                     target.addEffect(new MobEffectInstance(UP2MobEffects.PARALYSIS, 200, 0), brontoscorpio);
                 }
             }
             if (timer > 30) {
                 this.timer = 0;
                 this.brontoscorpio.attackCooldown = 10;
                 this.brontoscorpio.setPose(Pose.STANDING);
                 this.brontoscorpio.setAttackState(0);
             }
         }
     }

     private static class BrontoscorpioTargetNearbyPlayersGoal extends PrehistoricNearestAttackableTargetGoal<Player> {

         public BrontoscorpioTargetNearbyPlayersGoal(PrehistoricMob prehistoricMob, Predicate<LivingEntity> targetPredicate) {
             super(prehistoricMob, Player.class, 5, true, true, targetPredicate);
         }

         @Override
         protected double getFollowDistance() {
             return 4.0D;
         }
     }

     private static class BrontoscorpioBodyRotationControl extends PrehistoricBodyRotationControl {

         public BrontoscorpioBodyRotationControl(PrehistoricMob mob) {
             super(mob);
         }

         @Override
         public void clientTick() {
             if (!mob.refuseToLook()) {
                 this.mob.yBodyRot = this.mob.getYRot();
                 this.rotateHeadIfNecessary();
             }
         }

         private void rotateHeadIfNecessary() {
             this.mob.yHeadRot = Mth.rotateIfNecessary(this.mob.yHeadRot, this.mob.yBodyRot, (float)this.mob.getMaxHeadYRot());
         }
     }
 }
