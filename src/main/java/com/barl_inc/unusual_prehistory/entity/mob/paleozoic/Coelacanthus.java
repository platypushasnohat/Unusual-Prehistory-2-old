package com.barl_inc.unusual_prehistory.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricAvoidEntityGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricPanicGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricSwimGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
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
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class Coelacanthus extends PrehistoricAquaticMob implements Bucketable, VariantHolder<Coelacanthus.CoelacanthusVariant> {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Coelacanthus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(Coelacanthus.class, EntityDataSerializers.INT);

    private int absorbCooldown = 60;

    public final SmoothAnimationState absorbAnimationState = new SmoothAnimationState();

    private int absorbTicks;

    public Coelacanthus(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.01D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 1.8D, 10, 7) {
            @Override
            public boolean canUse() {
                return super.canUse() && Coelacanthus.this.getCoelacanthusSize() <= 5;
            }
        });
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new PrehistoricAvoidEntityGoal<>(this, Player.class, 8.0F, 1.8D, EntitySelector.NO_SPECTATORS::test) {
            @Override
            public boolean canUse() {
                return super.canUse() && Coelacanthus.this.getCoelacanthusSize() <= 5;
            }
        });
        this.goalSelector.addGoal(4, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 8.0F,1.8D, entity -> entity.getType().is(UP2EntityTags.COELACANTHUS_AVOIDS)) {
            @Override
            public boolean canUse() {
                return super.canUse() && Coelacanthus.this.getCoelacanthusSize() <= 5;
            }
        });
        this.goalSelector.addGoal(5, new PrehistoricSwimGoal(this, 1.0D, 40));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVector);
            if (!this.isEyeInFluid(FluidTags.WATER)) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (absorbCooldown > 0) absorbCooldown--;
        if (!this.isPacified() && this.isAlive() && absorbCooldown == 0) {
            if (this.consumeNearbyMobs()) {
                this.gameEvent(GameEvent.EAT);
                this.playSound(SoundEvents.GENERIC_EAT, this.getSoundVolume(), this.getVoicePitch());
                this.heal(this.getMaxHealth());
                this.setCoelacanthusSize(this.getCoelacanthusSize() + 1);
                if (this.getPose() == Pose.STANDING) this.setPose(UP2Poses.ATTACKING.get());
                this.absorbCooldown = 40 + this.getRandom().nextInt(60);
            }
        }
    }

    public boolean canConsumeEntity(Entity entity) {
        if (entity == null || !entity.isAlive() || entity == this) return false;
        else if (entity.getType().is(UP2EntityTags.COELACANTHUS_NEVER_EATS)) return false;
        return (entity.getBbWidth() < this.getBbWidth() && entity.getBbHeight() < this.getBbHeight()) || entity.getType().is(UP2EntityTags.COELACANTHUS_ALWAYS_EATS);
    }

    private boolean consumeNearbyMobs() {
        List<LivingEntity> nearbyEntities = this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forNonCombat(), this, this.getBoundingBox().inflate(1.1D));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.getFirst();
            if (this.canConsumeEntity(entity) && !this.level().isClientSide) {
                if (entity instanceof Player player) {
                    if (!player.isCreative() && !player.isSpectator()) {
                        entity.hurt(this.damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    }
                } else {
                    entity.discard();
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.absorbAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (this.absorbTicks > 0) absorbTicks--;
        if (this.absorbTicks == 0 && this.getPose() == UP2Poses.ATTACKING.get()) this.setPose(Pose.STANDING);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (SIZE.equals(accessor)) {
            this.refreshDimensions();
            this.setYRot(this.yHeadRot);
            this.yBodyRot = this.yHeadRot;
        }
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.absorbTicks = 10;
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public boolean isPushable() {
        return this.getCoelacanthusSize() <= 5;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(SIZE, 1);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant().getId());
        compoundTag.putInt("Size", this.getCoelacanthusSize() - 1);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(CoelacanthusVariant.byId(compoundTag.getInt("Variant")));
        this.setCoelacanthusSize(compoundTag.getInt("Size") + 1);
    }

    @Override
    public @NotNull CoelacanthusVariant getVariant() {
        return CoelacanthusVariant.byId(this.entityData.get(VARIANT));
    }

    @Override
    public void setVariant(CoelacanthusVariant variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant.getId(), 0, CoelacanthusVariant.values().length));
    }

    public int getCoelacanthusSize() {
        return this.entityData.get(SIZE);
    }

    public void setCoelacanthusSize(int size) {
        int maxSize = Mth.clamp(size, 1, 200);
        this.entityData.set(SIZE, maxSize);
        this.reapplyPosition();
        this.refreshDimensions();
        Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(1 + this.getCoelacanthusSize());
        Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).setBaseValue(0.01D + this.getCoelacanthusSize() * 0.01D);
    }

    @Override
    public void refreshDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.refreshDimensions();
        this.setPos(x, y, z);
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        int size = this.getCoelacanthusSize();
        EntityDimensions dimensions = super.getDefaultDimensions(pose);
        if (dimensions.width() <= 0.0F || dimensions.height() <= 0.0F) return dimensions;
        float scale = (dimensions.width() + 0.05F * (float) size) / dimensions.width();
        return dimensions.scale(scale);
    }

    @Override
    public float getVoicePitch() {
        int size = this.getCoelacanthusSize();
        float pitch = Mth.clamp(size / 127.0F, 0.0F, 1.0F);
        return Mth.lerp(pitch, 1.8F, 0.1F);
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
        return new ItemStack(UP2Items.COELACANTHUS_BUCKET.get());
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
        this.setVariant(CoelacanthusVariant.byId(compoundTag.getInt("Variant")));
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.COELACANTHUS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.COELACANTHUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.COELACANTHUS_FLOP.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        Coelacanthus coelacanthus = UP2Entities.COELACANTHUS.get().create(level);
        if (coelacanthus != null) {
            coelacanthus.setVariant(this.getVariant());
        }
        return coelacanthus;
    }

    public enum CoelacanthusVariant {
        FISHY(0),
        GOLDEN(1),
        LILAC(2),
        PEACH(3),
        ROSE(4),
        SILVER(5),
        BLUE(6);

        private final int id;

        CoelacanthusVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static CoelacanthusVariant byId(int id) {
            if (id < 0 || id >= CoelacanthusVariant.values().length) {
                id = 0;
            }
            return CoelacanthusVariant.values()[id];
        }
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        this.setVariant(CoelacanthusVariant.byId(level.getRandom().nextInt(CoelacanthusVariant.values().length)));
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }
}