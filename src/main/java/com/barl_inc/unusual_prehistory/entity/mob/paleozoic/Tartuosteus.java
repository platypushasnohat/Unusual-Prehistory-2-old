package com.barl_inc.unusual_prehistory.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barl_inc.unusual_prehistory.entity.utils.LeapingMob;
import com.barl_inc.unusual_prehistory.entity.utils.PlushableMob;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Tartuosteus extends PrehistoricAquaticMob implements LeapingMob, VariantHolder<Tartuosteus.TartuosteusVariant>, PlushableMob {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Tartuosteus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Tartuosteus.class, EntityDataSerializers.BOOLEAN);

    public Tartuosteus(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.55F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 2.0D, 10, 7));
        this.goalSelector.addGoal(2, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 6.0F, 2.0D, entity -> entity.getType().is(UP2EntityTags.TARTUOSTEUS_AVOIDS)));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_HERBIVORE), false));
        this.goalSelector.addGoal(4, new AquaticNibbleBlockGoal(this, 30, 800, UP2BlockTags.TARTUOSTEUS_FOOD_BLOCKS, 1.0D));
        this.goalSelector.addGoal(5, new PrehistoricSwimGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(6, new TartuosteusGlideGoal(this));
    }

    @Override
    public int getMaxHeadXRot() {
        return 1;
    }

    @Override
    public int getMaxHeadYRot() {
        return 1;
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.25F : 1.0F;
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
    public @NotNull ItemStack getPlushieItemStack() {
        return new ItemStack(UP2Blocks.TARTUOSTEUS_PLUSHIE.get());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isLeaping()) {
            if (!this.isInWaterOrBubble() && this.getDeltaMovement().y < 0.0) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0F, 0.66F, 1.0F));
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(LEAPING, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant().getId());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(TartuosteusVariant.byId(compoundTag.getInt("Variant")));
    }

    @Override
    public @NotNull TartuosteusVariant getVariant() {
        return TartuosteusVariant.byId(this.entityData.get(VARIANT));
    }

    @Override
    public void setVariant(TartuosteusVariant variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant.getId(), 0, TartuosteusVariant.values().length));
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
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
        Tartuosteus tartuosteus = UP2Entities.TARTUOSTEUS.get().create(serverLevel);
        if (tartuosteus != null) {
            tartuosteus.setVariant(this.getVariant());
        }
        return tartuosteus;
    }

    public enum TartuosteusVariant {
        MOSS_BALL(0),
        EVIL_MOSS_BALL(1);

        private final int variant;

        TartuosteusVariant(int variant) {
            this.variant = variant;
        }

        public int getId() {
            return this.variant;
        }

        public static TartuosteusVariant byId(int id) {
            if (id < 0 || id >= TartuosteusVariant.values().length) {
                id = 0;
            }
            return TartuosteusVariant.values()[id];
        }
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        if (spawnType == MobSpawnType.BUCKET) {
            return spawnGroupData;
        } else {
            if (level.getLevel().isNight()) {
                this.setVariant(TartuosteusVariant.EVIL_MOSS_BALL);
            } else {
                this.setVariant(TartuosteusVariant.MOSS_BALL);
            }
        }
        return spawnGroupData;
    }

    // Goals
    private static class TartuosteusGlideGoal extends AquaticLeapGoal {

        private final Tartuosteus tartuosteus;

        public TartuosteusGlideGoal(Tartuosteus tartuosteus) {
            super(tartuosteus, 10, 1.0D, 0.5D);
            this.tartuosteus = tartuosteus;
        }

        @Override
        public void tick() {
            boolean flag = breached;
            this.tartuosteus.getNavigation().stop();
            if (!flag) {
                FluidState fluidstate = tartuosteus.level().getFluidState(tartuosteus.blockPosition());
                this.breached = fluidstate.is(FluidTags.WATER);
            }

            if (breached && !flag) {
                this.tartuosteus.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
            }

            Vec3 vec3 = tartuosteus.getDeltaMovement();
            if (vec3.y * vec3.y < (double) 0.03F && tartuosteus.getXRot() != 0.0F) {
                this.tartuosteus.setXRot(Mth.rotLerp(0.2F, tartuosteus.getXRot(), 0.0F));
            } else if (vec3.length() > (double) 1.0E-5F) {
                double d0 = vec3.horizontalDistance();
                double d1 = Math.atan2(-vec3.y, d0) * (double) (180F / (float) Math.PI);
                this.tartuosteus.setXRot((float) d1);
            }

            Vec3 movement = new Vec3(tartuosteus.getMotionDirection().getStepX(), 0, tartuosteus.getMotionDirection().getStepZ()).normalize().scale(0.53F);
            Vec3 glide = new Vec3(movement.x, vec3.y, movement.z);
            this.tartuosteus.setDeltaMovement(glide);
            this.tartuosteus.setYRot(((float) Mth.atan2(tartuosteus.getMotionDirection().getStepZ(), tartuosteus.getMotionDirection().getStepX())) * Mth.RAD_TO_DEG - 90F);
        }
    }
}