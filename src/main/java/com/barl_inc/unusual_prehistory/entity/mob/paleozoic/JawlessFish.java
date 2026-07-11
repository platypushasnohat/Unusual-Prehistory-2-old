package com.barl_inc.unusual_prehistory.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.AquaticNibbleBlockGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.FollowVariantLeaderGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricPanicGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricSwimGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricSchoolingAquaticMob;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
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

public class JawlessFish extends PrehistoricSchoolingAquaticMob implements Bucketable, VariantHolder<JawlessFish.JawlessFishVariant> {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(JawlessFish.class, EntityDataSerializers.INT);

    public JawlessFish(EntityType<? extends PrehistoricSchoolingAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 1000, 10, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0)
                .add(Attributes.MOVEMENT_SPEED, 0.9F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 2.0D, 10, 7));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, LivingEntity.class, 6.0F, 2.0D, 2.0D, entity -> entity.getType().is(UP2EntityTags.JAWLESS_FISH_AVOIDS)));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 6.0F, 2.0D, 2.0D, EntitySelector.NO_SPECTATORS::test));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_HERBIVORE), false));
        this.goalSelector.addGoal(4, new AquaticNibbleBlockGoal(this, UP2BlockTags.JAWLESS_FISH_FOOD_BLOCKS));
        this.goalSelector.addGoal(5, new PrehistoricSwimGoal(this, 1.0D, 20, 10, 7));
        this.goalSelector.addGoal(6, new FollowVariantLeaderGoal(this));
    }

    @Override
    public int getMaxSchoolSize() {
        return 10;
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
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_HERBIVORE);
    }

    @Override
    public void addFollowers(Stream<? extends PrehistoricSchoolingAquaticMob> entity) {
        entity.limit(this.getMaxSchoolSize() - this.schoolSize).filter((entity1) -> entity1 != this).forEach((entity2) -> {
            if (this.getVariant() == ((JawlessFish) entity2).getVariant() && !this.isBaby()) {
                entity2.startFollowing(this);
            }
        });
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant().getId());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(JawlessFishVariant.byId(compoundTag.getInt("Variant")));
    }

    @Override
    public @NotNull JawlessFishVariant getVariant() {
        return JawlessFishVariant.byId(this.entityData.get(VARIANT));
    }

    @Override
    public void setVariant(JawlessFishVariant variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant.getId(), 0, JawlessFishVariant.values().length));
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
        this.setVariant(JawlessFishVariant.byId(compoundTag.getInt("Variant")));
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.JAWLESS_FISH_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.JAWLESS_FISH_HURT.get();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.JAWLESS_FISH_FLOP.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        JawlessFish jawlessFish = UP2Entities.JAWLESS_FISH.get().create(serverLevel);
        if (jawlessFish != null) {
            jawlessFish.setVariant(this.getVariant());
        }
        return jawlessFish;
    }

    public enum JawlessFishVariant {
        ARANDASPIS(0),
        CEPHALASPIS(1),
        DORYASPIS(2),
        FURCACAUDA(3),
        SACABAMBASPIS(4);

        private final int variant;

        JawlessFishVariant(int variant) {
            this.variant = variant;
        }

        public int getId() {
            return this.variant;
        }

        public static JawlessFishVariant byId(int id) {
            if (id < 0 || id >= JawlessFishVariant.values().length) {
                id = 0;
            }
            return JawlessFishVariant.values()[id];
        }
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        if (spawnType == MobSpawnType.BUCKET) {
            return spawnGroupData;
        } else {
            this.setVariant(JawlessFishVariant.byId(level.getRandom().nextInt(JawlessFishVariant.values().length)));
        }
        return spawnGroupData;
    }
}