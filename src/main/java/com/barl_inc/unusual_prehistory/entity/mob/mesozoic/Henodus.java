package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

public class Henodus extends PrehistoricAmphibiousMob implements Bucketable {

    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();

    public Henodus(EntityType<? extends Henodus> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.switchNavigator(false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AmphibiousPanicGoal(this, 1.5D, 14, 7, true));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_HERBIVORE), false));
        this.goalSelector.addGoal(2, new LeaveWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new EnterWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new PrehistoricSwimGoal(this, 1.0D, 120));
        this.goalSelector.addGoal(3, new AmphibiousWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15F)
                .add(Attributes.STEP_HEIGHT, 1.2D)
                .add(Attributes.ARMOR, 10.0D);
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
            this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.25F);
            this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
            this.isLandNavigator = false;
        }
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
        } else {
            super.travel(travelVec);
        }
    }

    @Override
    public boolean isPushable() {
        return !this.isSitting();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_HERBIVORE);
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return super.canCollideWith(entity) && !(entity instanceof Henodus);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive() && !this.isInWaterOrBubble();
    }

    @Override
    public void tick() {
        super.tick();
        final boolean ground = !this.isInWaterOrBubble();
        if (!ground && isLandNavigator) {
            this.switchNavigator(false);
        }
        if (ground && !isLandNavigator) {
            this.switchNavigator(true);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isSitting(), tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isSitting(), tickCount);
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
        return new ItemStack(UP2Items.HYNERPETON_BUCKET.get());
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

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.HENODUS.get().create(level);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.HYNERPETON_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.HYNERPETON_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.HYNERPETON_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.HYNERPETON_STEP.get(), 0.15F, 1.0F);
    }
}
