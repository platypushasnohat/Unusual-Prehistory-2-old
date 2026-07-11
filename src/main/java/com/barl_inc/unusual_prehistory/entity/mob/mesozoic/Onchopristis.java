package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricBabyPanicGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricSwimGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Onchopristis extends PrehistoricAquaticMob implements Bucketable {

    public static final EntityDataAccessor<Boolean> BURROWED = SynchedEntityData.defineId(Onchopristis.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> BURROW_COOLDOWN = SynchedEntityData.defineId(Onchopristis.class, EntityDataSerializers.INT);

    private int attackCooldown = 0;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState burrowAnimationState = new SmoothAnimationState();

    private boolean attackAlt = false;

    public Onchopristis(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new OnchopristisMoveControl(this);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.64F)
                .add(Attributes.FOLLOW_RANGE, 16.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new OnchopristisAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new PrehistoricSwimGoal(this, 1.0D, 40, 10, 7));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVec);
            if ((!this.onGround() || this.isBurrowed()) && this.getBurrowCooldown() == 0 && !this.isLeashed()) {
                if (this.getNavigation().getPath() != null) {
                    this.getNavigation().stop();
                }
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.05D, 0.0D));
            }
        } else {
            super.travel(travelVec);
        }
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return UP2MobUtils.getDepthPathfindingFavor(pos, level);
    }

    @Override
    public boolean isPushable() {
        return !this.isBurrowed();
    }

    @Override
    public void doPush(@NotNull Entity entity) {
        if (!this.isPacified() && !this.isBaby() && entity instanceof LivingEntity livingEntity && this.canAttack(livingEntity) && !(livingEntity instanceof Onchopristis) && this.isBurrowed()) {
            this.setTarget(livingEntity);
            this.setBurrowed(false);
            this.setBurrowCooldown(600 + this.getRandom().nextInt(600));
        } else {
            super.doPush(entity);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (attackCooldown > 0) attackCooldown--;

        if (this.isInWaterOrBubble() && !this.isAggressive() && this.getLastHurtMob() == null) {
            if (this.getBurrowCooldown() > 0) {
                this.setBurrowCooldown(this.getBurrowCooldown() - 1);
            }
        }

        this.tickBurrowing();
    }

    private void tickBurrowing() {
        if (this.isBurrowed() && !this.isInWaterOrBubble()) this.setBurrowed(false);

        if (this.isInWaterOrBubble()) {
            if (!this.isLeashed() && this.getBurrowCooldown() == 0 && this.onGround() && !this.isBurrowed() && !this.isAggressive() && this.getLastHurtMob() == null) {
                this.setBurrowed(true);
            }
        }

        if (this.isBurrowed() && this.getRandom().nextInt(1600) == 0) {
            this.setBurrowCooldown(600 + this.getRandom().nextInt(600));
            this.setBurrowed(false);
        }
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource source, float amount) {
        super.actuallyHurt(source, amount);
        if (this.isBurrowed()) {
            this.setBurrowed(false);
            this.setBurrowCooldown(600 + this.getRandom().nextInt(600));
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isBurrowed(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.burrowAnimationState.animateWhen(this.isBurrowed(), this.tickCount);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.isBurrowed();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BURROWED, false);
        builder.define(BURROW_COOLDOWN, 600 + this.getRandom().nextInt(600));
    }

    public boolean isBurrowed() {
        return this.entityData.get(BURROWED);
    }

    public void setBurrowed(boolean burrowed) {
        this.entityData.set(BURROWED, burrowed);
    }

    public int getBurrowCooldown() {
        return this.entityData.get(BURROW_COOLDOWN);
    }

    public void setBurrowCooldown(int cooldown) {
        this.entityData.set(BURROW_COOLDOWN, cooldown);
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
        return new ItemStack(UP2Items.ONCHOPRISTIS_BUCKET.get());
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
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return UP2Entities.ONCHOPRISTIS.get().create(level);
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.ONCHOPRISTIS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.ONCHOPRISTIS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.ONCHOPRISTIS_FLOP.get();
    }

    // Goals
    private static class OnchopristisAttackGoal extends AttackGoal {

        private final Onchopristis onchopristis;

        public OnchopristisAttackGoal(Onchopristis onchopristis) {
            super(onchopristis);
            this.onchopristis = onchopristis;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && onchopristis.getTarget() != null && (onchopristis.getTarget().isInWaterOrBubble() || !onchopristis.isInWaterOrBubble());
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && onchopristis.getTarget() != null && (onchopristis.getTarget().isInWaterOrBubble() || !onchopristis.isInWaterOrBubble());
        }

        @Override
        public void tick() {
            LivingEntity target = onchopristis.getTarget();
            if (target != null) {
                this.onchopristis.lookAt(target, 30F, 30F);
                this.onchopristis.getLookControl().setLookAt(target, 30F, 30F);

                double distance = onchopristis.distanceToSqr(target);
                int attackState = onchopristis.getAttackState();

                this.onchopristis.getNavigation().moveTo(target, 1.5D);

                if (attackState == 1) {
                    this.tickAttack(target);
                } else if (distance < this.getAttackReachSqr(target)) {
                    this.onchopristis.setAttackState(1);
                }
            }
        }

        protected void tickAttack(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.onchopristis.attackAlt = onchopristis.getRandom().nextBoolean();
                this.onchopristis.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 9) {
                if (this.isInAttackRange(target, 2.0D)) {
                    this.onchopristis.doHurtTarget(target);
                    this.onchopristis.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 20) {
                this.timer = 0;
                this.onchopristis.setPose(Pose.STANDING);
                this.onchopristis.setAttackState(0);
            }
        }
    }

    private static class OnchopristisMoveControl extends PrehistoricSwimmingMoveControl {

        protected final Onchopristis onchopristis;

        public OnchopristisMoveControl(Onchopristis onchopristis) {
            super(onchopristis, 85, 10, 0.02F);
            this.onchopristis = onchopristis;
        }

        @Override
        public void tick() {
            if (!this.onchopristis.refuseToMove()) {
                if (this.operation == MoveControl.Operation.MOVE_TO && !this.onchopristis.isLeashed() && this.onchopristis.isBurrowed()) {
                    this.onchopristis.setBurrowed(false);
                }
                super.tick();
            }
        }
    }
}
