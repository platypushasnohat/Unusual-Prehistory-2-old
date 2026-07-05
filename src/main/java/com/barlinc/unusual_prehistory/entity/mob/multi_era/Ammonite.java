package com.barlinc.unusual_prehistory.entity.mob.multi_era;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.FollowVariantLeaderGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricAvoidEntityGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricSwimGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricSchoolingAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class Ammonite extends PrehistoricSchoolingAquaticMob implements Bucketable, VariantHolder<Ammonite.AmmoniteVariant> {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Ammonite.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HIDE_TICKS = SynchedEntityData.defineId(Ammonite.class, EntityDataSerializers.INT);

    private int hideCooldown = 0;

    public final SmoothAnimationState hideAnimationState = new SmoothAnimationState(0.25F);

    public Ammonite(EntityType<? extends PrehistoricSchoolingAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.7F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.3D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 2.0D, 10, 7));
        this.goalSelector.addGoal(2, new PrehistoricAvoidEntityGoal<>(this, Player.class, 6.0F,2.0D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new PrehistoricSwimGoal(this, 1.0D, 80, 10, 7));
        this.goalSelector.addGoal(5, new FollowVariantLeaderGoal(this));
    }

    @Override
    public int getMaxSchoolSize() {
        return 5;
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.refuseToMove()) {
            travelVector = travelVector.multiply(0.0D, this.isInWaterOrBubble() ? 0.0D : 1.0D, 0.0D);
        }
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getHideTicks() > 0;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (!this.level().isClientSide) {
            if (this.isAlive() && this.getHideTicks() == 0 && hideCooldown == 0) {
                this.setHideTicks(30 + this.getRandom().nextInt(20));
                this.hideCooldown = 100;
                this.playSound(UP2SoundEvents.AMMONITE_BLOCK.get(), 1.0F, this.getVoicePitch());
                return super.hurt(source, amount * 0.5F);
            }
            if (this.getHideTicks() > 0) {
                this.playSound(UP2SoundEvents.AMMONITE_BLOCK.get(), 1.0F, this.getVoicePitch());
                return super.hurt(source, amount * 0.5F);
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    public float flopChance() {
        return 0.05F;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.isInWater() && this.getDeltaMovement().lengthSqr() > 0.001D && tickCount % 10 == 0) {
                Vec3 viewVector = this.getViewVector(0.0F);
                this.level().addParticle(ParticleTypes.BUBBLE, this.getRandomX(0.5D) - viewVector.x * 0.5D, this.getRandomY() - viewVector.y * 0.25D, this.getRandomZ(0.5D) - viewVector.z * 0.5D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void tickCooldowns() {
        if (!this.level().isClientSide) {
            if (this.getHideTicks() > 0) {
                this.setHideTicks(this.getHideTicks() - 1);
            }
            if (hideCooldown > 0) {
                this.hideCooldown--;
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.hideAnimationState.animateWhen(this.getHideTicks() > 0, this.tickCount);
    }

    @Override
    public void addFollowers(Stream<? extends PrehistoricSchoolingAquaticMob> entity) {
        entity.limit(this.getMaxSchoolSize() - this.schoolSize).filter((entity1) -> entity1 != this).forEach((entity2) -> {
            if (this.getVariant() == ((Ammonite) entity2).getVariant() && !this.isBaby()) {
                entity2.startFollowing(this);
            }
        });
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(HIDE_TICKS, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant().getId());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(AmmoniteVariant.byId(compoundTag.getInt("Variant")));
    }

    @Override
    public @NotNull Ammonite.AmmoniteVariant getVariant() {
        return AmmoniteVariant.byId(this.entityData.get(VARIANT));
    }
    @Override
    public void setVariant(AmmoniteVariant variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant.getId(), 0, AmmoniteVariant.values().length));
    }

    public int getHideTicks() {
        return this.entityData.get(HIDE_TICKS);
    }
    public void setHideTicks(int hideTicks) {
        this.entityData.set(HIDE_TICKS, hideTicks);
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
        return new ItemStack(UP2Items.JAWLESS_FISH_BUCKET.get());
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
        this.setVariant(AmmoniteVariant.byId(compoundTag.getInt("Variant")));
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.AMMONITE_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.AMMONITE_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.AMMONITE_HURT.get();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.AMMONITE_FLOP.get();
    }

    @Override
    protected @NotNull SoundEvent getSwimSound() {
        return UP2SoundEvents.AMMONITE_SWIM.get();
    }

    @Override
    public float getSoundVolume() {
        return 0.6F;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        Ammonite ammonite = UP2Entities.AMMONITE.get().create(serverLevel);
        if (ammonite != null) {
            ammonite.setVariant(this.getVariant());
        }
        return ammonite;
    }

    public enum AmmoniteVariant {
        CRIOCERATITES(0),
        HOPLITES(1),
        NOSTOCERAS(2),
        PINACOCERAS(3),
        TROPITES(4);

        private final int variant;

        AmmoniteVariant(int variant) {
            this.variant = variant;
        }

        public int getId() {
            return this.variant;
        }

        public static AmmoniteVariant byId(int id) {
            if (id < 0 || id >= AmmoniteVariant.values().length) {
                id = 0;
            }
            return AmmoniteVariant.values()[id];
        }
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        if (spawnType == MobSpawnType.BUCKET) {
            return spawnGroupData;
        } else {
            this.setVariant(AmmoniteVariant.byId(level.getRandom().nextInt(AmmoniteVariant.values().length)));
        }
        return spawnGroupData;
    }
}