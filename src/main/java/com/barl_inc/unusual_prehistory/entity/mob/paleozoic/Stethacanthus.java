package com.barl_inc.unusual_prehistory.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricSchoolingAquaticMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Stethacanthus extends PrehistoricSchoolingAquaticMob implements Bucketable {

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();

    public Stethacanthus(EntityType<? extends PrehistoricSchoolingAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.85F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 1.5D, 10, 7));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(3, new PrehistoricAvoidEntityGoal<>(this, Player.class, 10.0F, 1.5D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(3, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 10.0F,1.5D, entity -> entity.getType().is(UP2EntityTags.STETHACANTHUS_AVOIDS)));
        this.goalSelector.addGoal(4, new StethacanthusAttackGoal(this));
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(6, new FollowVariantLeaderGoal(this));
        this.targetSelector.addGoal(0, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.STETHACANTHUS_TARGETS)));
    }

    @Override
    public int getMaxSchoolSize() {
        return 3;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && this.getPose() != UP2Poses.ATTACKING.get(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
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
        return new ItemStack(UP2Items.STETHACANTHUS_BUCKET.get());
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
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.STETHACANTHUS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.STETHACANTHUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.STETHACANTHUS_FLOP.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.STETHACANTHUS.get().create(serverLevel);
    }

    private static class StethacanthusAttackGoal extends AttackGoal {

        private final Stethacanthus stethacanthus;

        public StethacanthusAttackGoal(Stethacanthus stethacanthus) {
            super(stethacanthus);
            this.stethacanthus = stethacanthus;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = stethacanthus.getTarget();
            return super.canUse() && target != null && target.isAlive() && target.isInWater() && !target.getType().is(UP2EntityTags.STETHACANTHUS_AVOIDS) && !(target instanceof Player);
        }

        @Override
        public void tick() {
            LivingEntity target = stethacanthus.getTarget();
            if (target != null && target.isInWater()) {
                stethacanthus.lookAt(target, 30F, 30F);
                stethacanthus.getLookControl().setLookAt(target, 30F, 30F);
                double distance = stethacanthus.distanceToSqr(target.getX(), target.getY(), target.getZ());

                if (stethacanthus.getAttackState() == 1) {
                    this.tickAttack(target);
                } else {
                    if (distance <= 4) {
                        this.stethacanthus.setAttackState(1);
                    }
                    this.stethacanthus.getNavigation().moveTo(target, 1.5D);
                }
            }
        }

        protected void tickAttack(LivingEntity target) {
            this.timer++;
            if (timer == 1) stethacanthus.setPose(UP2Poses.ATTACKING.get());
            if (timer == 5) stethacanthus.playSound(UP2SoundEvents.STETHACANTHUS_BITE.get(), 0.7F, stethacanthus.getVoicePitch());
            if (timer == 6) {
                if (this.isInAttackRange(target, 1.5D)) {
                    this.stethacanthus.doHurtTarget(target);
                    this.stethacanthus.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 20) {
                this.timer = 0;
                this.stethacanthus.setPose(Pose.STANDING);
                this.stethacanthus.setAttackState(0);
            }
        }
    }
}