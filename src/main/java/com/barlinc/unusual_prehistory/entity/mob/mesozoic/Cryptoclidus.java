package com.barlinc.unusual_prehistory.entity.mob.mesozoic;

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
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Cryptoclidus extends PrehistoricAmphibiousMob {

    private int attackCooldown = 0;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();

    public Cryptoclidus(EntityType<? extends PrehistoricAmphibiousMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15F)
                .add(Attributes.ATTACK_DAMAGE, 6.0F)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 2.0D, 16, 8));
        this.goalSelector.addGoal(2, new CryptoclidusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new CryptoclidusLeaveWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new CryptoclidusEnterWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new AmphibiousWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new PrehistoricSwimGoal(this, 1.0D, 50, 30, 15, 3, true));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 100, true, true, this::canTargetPlayers));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, true, entity -> entity.getType().is(UP2EntityTags.CRYPTOCLIDUS_TARGETS)));
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new PrehistoricMoveControl(this);
            this.lookControl = new PrehistoricLookControl(this);
            this.navigation = this.createNavigation(this.level());
            this.setPathfindingMalus(PathType.WATER, 8.0F);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new PrehistoricSwimmingMoveControl(this, 1000, 6, 0.55F);
            this.lookControl = new PrehistoricSwimmingLookControl(this, 5);
            this.navigation = new SmoothAmphibiousNavigation(this, this.level());
            this.setPathfindingMalus(PathType.WATER, 0.0F);
            this.isLandNavigator = false;
        }
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        if (this.isInWaterOrBubble()) {
            return this.level().isDay() ? UP2MobUtils.getDepthPathfindingFavor(pos, level) : UP2MobUtils.getSurfacePathfindingFavor(pos, level);
        }
        return level.getBlockState(pos).is(BlockTags.SAND) ? 10.0F : super.getWalkTargetValue(pos, level);
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isInWaterOrBubble() ? 1 : super.getMaxHeadXRot();
    }

    @Override
    public int getMaxHeadYRot() {
        return this.isInWaterOrBubble() ? 1 : super.getMaxHeadYRot();
    }

    public boolean canTargetPlayers(LivingEntity target) {
        return this.canAttack(target) && this.level().isDay();
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(2);
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
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackCooldown > 0) attackCooldown--;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.CRYPTOCLIDUS.get().create(level);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.CRYPTOCLIDUS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.CRYPTOCLIDUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.CRYPTOCLIDUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        if (this.isInWaterOrBubble()) return;
        this.playSound(UP2SoundEvents.CRYPTOCLIDUS_STEP.get(), 0.25F, 1.0F);
    }

    // Goals
    private static class CryptoclidusAttackGoal extends AttackGoal {

        private final Cryptoclidus cryptoclidus;

        public CryptoclidusAttackGoal(Cryptoclidus cryptoclidus) {
            super(cryptoclidus);
            this.cryptoclidus = cryptoclidus;
        }

        @Override
        public void tick() {
            LivingEntity target = cryptoclidus.getTarget();
            if (target != null) {
                this.cryptoclidus.lookAt(target, 30F, 30F);
                this.cryptoclidus.getLookControl().setLookAt(target, 30F, 30F);

                double distance = cryptoclidus.distanceToSqr(target);
                int attackState = cryptoclidus.getAttackState();

                this.cryptoclidus.getNavigation().moveTo(target, 1.5D);

                if (attackState == 1) {
                    this.tickAttack(target);
                } else if (distance < this.getAttackReachSqr(target) && cryptoclidus.attackCooldown == 0) {
                    this.cryptoclidus.setAttackState(1);
                }
            }
        }

        protected void tickAttack(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.cryptoclidus.setPose(UP2Poses.ATTACKING.get());
                this.cryptoclidus.playSound(UP2SoundEvents.CRYPTOCLIDUS_ATTACK.get(), 1.0F, 0.9F + cryptoclidus.getRandom().nextFloat() * 0.2F);
            }
            if (timer == 10) {
                if (this.isInAttackRange(target, 2.0D)) {
                    this.cryptoclidus.doHurtTarget(target);
                    this.cryptoclidus.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 20) {
                this.timer = 0;
                this.cryptoclidus.attackCooldown = 5;
                this.cryptoclidus.setPose(Pose.STANDING);
                this.cryptoclidus.setAttackState(0);
            }
        }
    }

    private static class CryptoclidusEnterWaterGoal extends EnterWaterGoal {

        public CryptoclidusEnterWaterGoal(PrehistoricAmphibiousMob amphibiousMob, double speedModifier) {
            super(amphibiousMob, speedModifier, 100, false);
        }

        @Override
        public boolean canUse() {
            if (amphibiousMob.level().isNight()) {
                return false;
            } else {
                return super.canUse();
            }
        }
    }

    private static class CryptoclidusLeaveWaterGoal extends LeaveWaterGoal {

        public CryptoclidusLeaveWaterGoal(PrehistoricAmphibiousMob amphibiousMob, double speedModifier) {
            super(amphibiousMob, speedModifier, 100);
        }

        @Override
        public boolean canUse() {
            if (amphibiousMob.level().isDay()) {
                return false;
            } else {
                return super.canUse();
            }
        }
    }
}
