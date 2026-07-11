package com.barl_inc.unusual_prehistory.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2DamageTypes;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Dunkleosteus extends PrehistoricAquaticMob implements Bucketable, VariantHolder<Dunkleosteus.DunkleosteusVariant> {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Dunkleosteus.class, EntityDataSerializers.INT);

    public int attackCooldown = 0;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState quirkAnimationState = new SmoothAnimationState();

    public Dunkleosteus(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.switchShallowNavigation(false);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.ARMOR, 2.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new DunkleosteusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new PrehistoricSwimGoal(this, 1.0D, 30, 10, 7, 3, true));
        this.goalSelector.addGoal(5, new IdleAnimationGoal(this, 40, 1, false, 0.001F, this::canQuirk));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, this::isTarget));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 300, true, true, this::wantsToAttackPlayers));
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
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackCooldown > 0) attackCooldown--;
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
        this.quirkAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
    }

    private boolean canQuirk(Entity entity) {
        return entity.isInWaterOrBubble();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 800 + this.getRandom().nextInt(1200);
        } else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        if (VARIANT.equals(accessor)) {
            this.refreshDimensions();
            this.setupSizeAttributes();
        }
        super.onSyncedDataUpdated(accessor);
    }

    private void setupSizeAttributes() {
        if (this.getVariant() == DunkleosteusVariant.SMALL) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(4.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.9F);
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
        }
        if (this.getVariant() == DunkleosteusVariant.MEDIUM) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(24.0D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(6.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.8F);
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.25D);
        }
        if (this.getVariant() == DunkleosteusVariant.LARGE) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40.0D);
            this.getAttribute(Attributes.ARMOR).setBaseValue(10.0D);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0D);
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.6F);
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
        }
        this.heal(50.0F);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    public boolean isTarget(Entity entity) {
        return entity.getType().is(this.getVariant().targets);
    }

    public boolean wantsToAttackPlayers(LivingEntity entity) {
        return this.canAttack(entity) && this.getVariant() != DunkleosteusVariant.SMALL;
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity entity) {
        boolean prev = super.canAttack(entity);
        if (prev && !this.isPacified() && entity instanceof LivingEntity && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().getUUID().equals(entity.getUUID()))) return false;
        else return prev;
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
        this.setVariant(DunkleosteusVariant.byId(compoundTag.getInt("Variant")));
    }

    @Override
    public @NotNull DunkleosteusVariant getVariant() {
        return DunkleosteusVariant.byId(this.entityData.get(VARIANT));
    }

    @Override
    public void setVariant(DunkleosteusVariant variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant.getId(), 0, DunkleosteusVariant.values().length));
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.DUNKLEOSTEUS_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.DUNKLEOSTEUS_DEATH.get();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.DUNKLEOSTEUS_FLOP.get();
    }

    @Override
    public float getVoicePitch() {
        final float size = (3 - this.getVariant().getId()) * 0.33F;
        return (float) (super.getVoicePitch() * Math.sqrt(size) * 1.2F);
    }

    @Override
    public boolean isPushable() {
        return super.isPushable() && this.getVariant() != DunkleosteusVariant.LARGE;
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        return this.getVariant().dimensions.scale(this.getAgeScale());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        Dunkleosteus dunkleosteus = UP2Entities.DUNKLEOSTEUS.get().create(serverLevel);
        if (dunkleosteus != null) {
            dunkleosteus.setVariant(this.getVariant());
        }
        return dunkleosteus;
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
        return new ItemStack(UP2Items.DUNKLEOSTEUS_BUCKET.get());
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
        this.setVariant(DunkleosteusVariant.byId(compoundTag.getInt("Variant")));
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    public enum DunkleosteusVariant {
        SMALL(0, UP2SoundEvents.SMALL_DUNKLEOSTEUS_BITE.get(), UP2EntityTags.SMALL_DUNKLEOSTEUS_TARGETS, EntityDimensions.scalable(0.75F, 0.75F).withEyeHeight(0.375F)),
        MEDIUM(1, UP2SoundEvents.MEDIUM_DUNKLEOSTEUS_BITE.get(), UP2EntityTags.MEDIUM_DUNKLEOSTEUS_TARGETS, EntityDimensions.scalable(1.25F, 1.25F).withEyeHeight(0.625F)),
        LARGE(2, UP2SoundEvents.LARGE_DUNKLEOSTEUS_BITE.get(), UP2EntityTags.LARGE_DUNKLEOSTEUS_TARGETS, EntityDimensions.scalable(2.1F, 2.1F).withEyeHeight(1.05F));

        private final int id;
        private final SoundEvent biteSound;
        private final TagKey<EntityType<?>> targets;
        private final EntityDimensions dimensions;

        DunkleosteusVariant(int id, SoundEvent biteSound, TagKey<EntityType<?>> targets, EntityDimensions dimensions) {
            this.id = id;
            this.biteSound = biteSound;
            this.targets = targets;
            this.dimensions = dimensions;
        }

        public int getId() {
            return this.id;
        }

        public static DunkleosteusVariant byId(int id) {
            if (id < 0 || id >= DunkleosteusVariant.values().length) {
                id = 0;
            }
            return DunkleosteusVariant.values()[id];
        }
    }

    private int getWaterDepthAbove(LevelReader level, BlockPos pos) {
        int depth = 0;
        BlockPos.MutableBlockPos checkPos = pos.mutable();
        while (level.getFluidState(checkPos).is(FluidTags.WATER)) {
            depth++;
            checkPos.move(0, 1, 0);
            if (depth > 12) break;
        }
        return depth;
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        if (spawnType == MobSpawnType.BUCKET) {
            return spawnGroupData;
        } else {
            int depth = this.getWaterDepthAbove(level, this.blockPosition());
            if (level.getFluidState(this.blockPosition()).is(FluidTags.WATER)) {
                if (depth > 10) {
                    this.setVariant(DunkleosteusVariant.LARGE);
                } else if (depth > 5) {
                    this.setVariant(DunkleosteusVariant.MEDIUM);
                } else {
                    this.setVariant(DunkleosteusVariant.SMALL);
                }
            } else {
                this.setVariant(DunkleosteusVariant.byId(level.getRandom().nextInt(DunkleosteusVariant.values().length)));
            }
        }
        return spawnGroupData;
    }

    // goals
    private static class DunkleosteusAttackGoal extends AttackGoal {

        private final Dunkleosteus dunkleosteus;

        public DunkleosteusAttackGoal(Dunkleosteus dunkleosteus) {
            super(dunkleosteus);
            this.dunkleosteus = dunkleosteus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && (dunkleosteus.getTarget().isInWaterOrBubble() || !dunkleosteus.isInWaterOrBubble());
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && (dunkleosteus.getTarget().isInWaterOrBubble() || !dunkleosteus.isInWaterOrBubble());
        }

        @Override
        public void tick() {
            LivingEntity target = this.dunkleosteus.getTarget();
            if (target != null) {
                this.dunkleosteus.lookAt(target, 30F, 30F);
                this.dunkleosteus.getLookControl().setLookAt(target, 30F, 30F);
                double distance = this.dunkleosteus.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int attackState = this.dunkleosteus.getAttackState();

                if (attackState == 1) {
                    this.tickBite(target);
                    this.dunkleosteus.getNavigation().stop();
                }
                else {
                    if (distance < this.getAttackReachSqr(target) && dunkleosteus.attackCooldown == 0) {
                        this.dunkleosteus.setAttackState(1);
                    }
                    this.dunkleosteus.getNavigation().moveTo(target, 1.5D);
                }
            }
        }

        protected void tickBite(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.dunkleosteus.setPose(UP2Poses.ATTACKING.get());
                this.dunkleosteus.playSound(dunkleosteus.getVariant().biteSound, 1.0F, dunkleosteus.getVoicePitch());
            }
            if (timer == 5) {
                if (this.isInAttackRange(target, 1.5D)) {
                    DamageSource source = UP2DamageTypes.execute(dunkleosteus.level(), dunkleosteus, dunkleosteus);
                    target.hurt(source, (float) dunkleosteus.getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
            }
            if (timer > 10) {
                this.timer = 0;
                this.dunkleosteus.setPose(Pose.STANDING);
                this.dunkleosteus.attackCooldown = 10;
                this.dunkleosteus.setAttackState(0);
            }
        }
    }
}
