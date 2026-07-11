package com.barl_inc.unusual_prehistory.entity.mob.other;

import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricPanicGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricWanderGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricBreedableMob;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
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
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Unicorn extends PrehistoricBreedableMob {

    private static final EntityDataAccessor<Boolean> SKELETAL = SynchedEntityData.defineId(Unicorn.class, EntityDataSerializers.BOOLEAN);
    private UUID lastLightningBoltUUID;

    public Unicorn(EntityType<? extends PrehistoricBreedableMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_HERBIVORE), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(5, new PrehistoricWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    @Override
    public float getWalkAnimationSpeed() {
        return this.isBaby() ? 3.0F : 6.0F;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_HERBIVORE);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isFood(itemstack)) {
            int i = this.getAge();
            if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
                this.usePlayerItem(player, hand, itemstack);
                this.setInLove(player);
                this.playSound(SoundEvents.GOAT_EAT);
                return InteractionResult.SUCCESS;
            }
            if (this.isBaby()) {
                this.usePlayerItem(player, hand, itemstack);
                this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
                this.playSound(SoundEvents.GOAT_EAT);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            if (this.level().isClientSide) {
                return InteractionResult.CONSUME;
            }
        }
        if (itemstack.is(Items.BUCKET) && !this.isBaby()) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack result = ItemUtils.createFilledResult(itemstack, player, Items.MILK_BUCKET.getDefaultInstance());
            player.setItemInHand(hand, result);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (itemstack.is(Items.BOWL) && !this.isBaby()) {
            player.playSound(SoundEvents.MOOSHROOM_MILK, 1.0F, 1.0F);
            ItemStack result = ItemUtils.createFilledResult(itemstack, player, Items.BEETROOT_SOUP.getDefaultInstance());
            player.setItemInHand(hand, result);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        else return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.UNICORN.get().create(serverLevel);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.UNICORN_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        if (this.isSkeletal()) {
            return SoundEvents.SKELETON_HURT;
        }
        else return UP2SoundEvents.UNICORN_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        if (this.isSkeletal()) {
            return SoundEvents.SKELETON_DEATH;
        }
        else return UP2SoundEvents.UNICORN_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.25F, 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 150;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Skeletal", this.isSkeletal());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSkeletal(compoundTag.getBoolean("Skeletal"));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SKELETAL, false);
    }

    public boolean isSkeletal() {
        return this.entityData.get(SKELETAL);
    }

    private void setSkeletal(boolean isSkeletal) {
        this.entityData.set(SKELETAL, isSkeletal);
    }

    @Override
    public void thunderHit(@NotNull ServerLevel level, LightningBolt lightningBolt) {
        UUID uuid = lightningBolt.getUUID();
        if (!uuid.equals(this.lastLightningBoltUUID)) {
            this.setSkeletal((!this.isSkeletal() || this.isSkeletal()) != this.isSkeletal());
            this.lastLightningBoltUUID = uuid;
            this.playSound(SoundEvents.SKELETON_DEATH, 2.0F, 1.0F);
        }
    }
}
