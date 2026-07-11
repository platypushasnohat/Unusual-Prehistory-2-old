package com.barl_inc.unusual_prehistory.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricSchoolingAquaticMob;
import com.barl_inc.unusual_prehistory.entity.utils.LeapingMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class SpikeToothedSalmon extends PrehistoricSchoolingAquaticMob implements LeapingMob, VariantHolder<SpikeToothedSalmon.SpikeToothedSalmonVariant> {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SpikeToothedSalmon.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ZOMBIE = SynchedEntityData.defineId(SpikeToothedSalmon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(SpikeToothedSalmon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> CONVERSION_TIME = SynchedEntityData.defineId(SpikeToothedSalmon.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CONVERTING = SynchedEntityData.defineId(SpikeToothedSalmon.class, EntityDataSerializers.BOOLEAN);

    private int attackCooldown = 0;

    private final byte CURE = 67;
    private final byte INFECT = 68;
    private final byte CONVERT = 69;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attackZombieAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState zombieAnimationState = new SmoothAnimationState();

    public SpikeToothedSalmon(EntityType<? extends PrehistoricSchoolingAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.switchShallowNavigation(false);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 1000, 6, 0.02F, 0.1F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 4);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 1.5D, 12, 6) {
            @Override
            public boolean canUse() {
                return !SpikeToothedSalmon.this.isZombie() && super.canUse();
            }
        });
        this.goalSelector.addGoal(2, new SpikeToothedSalmonAttackGoal(this));
        this.goalSelector.addGoal(3, new AquaticLeapGoal(this, 10, 0.7D, 0.8D) {
            @Override
            public boolean canUse() {
                return !SpikeToothedSalmon.this.isZombie() && super.canUse();
            }
        });
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(5, new PrehistoricSwimGoal(this, 1.0D, 10, 16, 8, 3, true));
        this.goalSelector.addGoal(6, new FollowVariantLeaderGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 50, false, false, this::canAttackEverything));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 50, false, false, this::canAttackEverything));
    }

    @Override
    public int getMaxSchoolSize() {
        return 5;
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

    protected void switchShallowNavigation(boolean inShallows) {
        this.navigation.stop();
        if (inShallows) {
            this.navigation = new SmoothAmphibiousNavigation(this, this.level());
            this.shallowWater = true;
        } else {
            this.navigation = this.createNavigation(this.level());
            this.shallowWater = false;
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(UP2ItemTags.CURES_SPIKE_TOOTHED_SALMON) && this.isZombie() && !this.isConverting()) {
            if (this.hasEffect(MobEffects.WEAKNESS) || player.isCreative()) {
                itemstack.consume(1, player);
                if (!this.level().isClientSide) {
                    if (player.isCreative()) {
                        this.startConverting(10);
                    } else {
                        this.startConverting(this.getRandom().nextInt(2401) + 3600);
                    }
                }
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        } else if (itemstack.is(Items.ROTTEN_FLESH) && !this.isZombie() && player.isCreative()) {
            itemstack.consume(1, player);
            if (!this.level().isClientSide) {
                this.setZombie(true);
                this.playSound(this.getEatingSound());
            }
            return InteractionResult.SUCCESS;
        }
        else {
            return super.mobInteract(player, hand);
        }
    }

    public boolean canAttackEverything(LivingEntity target) {
        return this.isZombie() && !(target instanceof SpikeToothedSalmon salmon && salmon.isZombie()) && !(target instanceof Zombie);
    }

    @Override
    public void addFollowers(Stream<? extends PrehistoricSchoolingAquaticMob> entity) {
        entity.limit(this.getMaxSchoolSize() - this.schoolSize).filter((entity1) -> entity1 != this).forEach((entity2) -> {
            if ((this.getVariant() == ((SpikeToothedSalmon) entity2).getVariant() || this.isZombie() && ((SpikeToothedSalmon) entity2).isZombie()) && !this.isBaby()) {
                entity2.startFollowing(this);
            }
        });
    }

    @Override
    public boolean killedEntity(@NotNull ServerLevel level, @NotNull LivingEntity entity) {
        boolean flag = super.killedEntity(level, entity);
        if (this.isZombie() && this.isAlive()) {
            if (entity instanceof SpikeToothedSalmon salmon) {
                SpikeToothedSalmon zombieSalmon = salmon.convertTo(UP2Entities.SPIKE_TOOTHED_SALMON.get(), false);
                if (zombieSalmon != null) {
                    zombieSalmon.finalizeSpawn(level, level.getCurrentDifficultyAt(zombieSalmon.blockPosition()), MobSpawnType.CONVERSION, null);
                    zombieSalmon.setVariant(salmon.getVariant());
                    zombieSalmon.setZombie(true);
                    if (!this.isSilent()) {
                        level.broadcastEntityEvent(this, INFECT);
                    }
                    flag = false;
                }
            }
            if ((level.getDifficulty() == Difficulty.NORMAL || level.getDifficulty() == Difficulty.HARD) && entity instanceof Villager villager && EventHooks.canLivingConvert(entity, EntityType.ZOMBIE_VILLAGER, (timer) -> {})) {
                if (level.getDifficulty() != Difficulty.HARD && this.getRandom().nextBoolean()) {
                    return flag;
                }
                ZombieVillager zombievillager = villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);
                if (zombievillager != null) {
                    zombievillager.finalizeSpawn(level, level.getCurrentDifficultyAt(zombievillager.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true));
                    zombievillager.setVillagerData(villager.getVillagerData());
                    zombievillager.setGossips(villager.getGossips().store(NbtOps.INSTANCE));
                    zombievillager.setTradeOffers(villager.getOffers().copy());
                    zombievillager.setVillagerXp(villager.getVillagerXp());
                    EventHooks.onLivingConvert(entity, zombievillager);
                    if (!this.isSilent()) {
                        level.broadcastEntityEvent(this, INFECT);
                    }
                    flag = false;
                }
            }
        }
        return flag;
    }

    @Override
    protected void handleAirSupply(int airSupply) {
        if (this.isZombie() && this.isAlive()) {
            this.setAirSupply(300);
        } else {
            super.handleAirSupply(airSupply);
        }
    }

    @Override
    public void aiStep() {
        if (this.isAlive()) {
            if (this.isZombie() && this.isSunBurnTick()) {
                this.igniteForSeconds(8.0F);
            }
        }
        super.aiStep();
    }

    @Override
    public void tick() {
        super.tick();

        final boolean shallowWater = this.isInShallowWater();
        if (shallowWater && !this.shallowWater) {
            this.switchShallowNavigation(true);
        } else if (!shallowWater && this.shallowWater) {
            this.switchShallowNavigation(false);
        }

        if (attackCooldown > 0 && !this.level().isClientSide) {
            this.attackCooldown--;
        }
        if (this.isZombie() && this.isConverting() && this.isAlive()) {
            if (this.getConversionTime() > 0) {
                this.setConversionTime(this.getConversionTime() - 1);
            } else {
                this.finishConversion();
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen((this.isInWaterOrBubble() || this.isLeaping()) && this.getPose() != UP2Poses.ATTACKING.get(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isLeaping(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !this.isZombie(), this.tickCount);
        this.attackZombieAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && this.isZombie(), this.tickCount);
        this.zombieAnimationState.animateWhen(this.isZombie(), this.tickCount);
    }

    private void finishConversion() {
        this.setConverting(false);
        this.setZombie(false);
        if (!this.level().isClientSide) {
            this.setTarget(null);
            this.setLastHurtByMob(null);
            for (Mob mob : this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(8.0D), entity -> entity.getTarget() == this)) {
                mob.setTarget(null);
            }
            this.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            this.level().broadcastEntityEvent(this, CONVERT);
        }
    }

    private void startConverting(int conversionTime) {
        this.setConverting(true);
        this.setConversionTime(conversionTime);
        this.removeEffect(MobEffects.WEAKNESS);
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, conversionTime, Math.min(this.level().getDifficulty().getId() - 1, 0)));
        this.setTarget(null);
        this.setLastHurtByMob(null);
        this.level().broadcastEntityEvent(this, CURE);
    }

    public void handleEntityEvent(byte id) {
        if (id == CURE) {
            if (!this.isSilent()) {
                this.level().playLocalSound(this.blockPosition(), UP2SoundEvents.SPIKE_TOOTHED_SALMON_CURE.get(), this.getSoundSource(), 2.0F, 0.8F + this.getRandom().nextFloat(), false);
            }
        }
        if (id == INFECT) {
            if (!this.isSilent()) {
                this.level().playLocalSound(this.blockPosition(), UP2SoundEvents.SPIKE_TOOTHED_SALMON_INFECT.get(), SoundSource.HOSTILE, 2.0F, 0.8F + this.getRandom().nextFloat(), false);
            }
        }
        if (id == CONVERT) {
            if (!this.isSilent()) {
                this.level().playLocalSound(this.blockPosition(), UP2SoundEvents.SPIKE_TOOTHED_SALMON_CONVERT.get(), SoundSource.HOSTILE, 2.0F, 0.8F + this.getRandom().nextFloat(), false);
            }
        }
        else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(ZOMBIE, false);
        builder.define(LEAPING, false);
        builder.define(CONVERSION_TIME, 0);
        builder.define(CONVERTING, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Variant", this.getVariant().getId());
        compoundTag.putBoolean("Zombie", this.isZombie());
        compoundTag.putInt("ConversionTime", this.getConversionTime());
        compoundTag.putBoolean("Converting", this.isConverting());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setVariant(SpikeToothedSalmonVariant.byId(compoundTag.getInt("Variant")));
        this.setZombie(compoundTag.getBoolean("Zombie"));
        this.setConversionTime(compoundTag.getInt("ConversionTime"));
        this.setConverting(compoundTag.getBoolean("Converting"));
    }

    @Override
    public @NotNull SpikeToothedSalmonVariant getVariant() {
        return SpikeToothedSalmonVariant.byId(this.entityData.get(VARIANT));
    }

    @Override
    public void setVariant(SpikeToothedSalmonVariant variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant.getId(), 0, SpikeToothedSalmonVariant.values().length));
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    public void setZombie(boolean zombie) {
        this.entityData.set(ZOMBIE, zombie);
    }

    public boolean isZombie() {
        return this.entityData.get(ZOMBIE);
    }

    public void setConversionTime(int conversionTime) {
        this.entityData.set(CONVERSION_TIME, conversionTime);
    }

    public int getConversionTime() {
        return this.entityData.get(CONVERSION_TIME);
    }

    public void setConverting(boolean converting) {
        this.entityData.set(CONVERTING, converting);
    }

    public boolean isConverting() {
        return this.entityData.get(CONVERTING);
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.SPIKE_TOOTHED_SALMON_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.SPIKE_TOOTHED_SALMON_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.SPIKE_TOOTHED_SALMON_FLOP.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        SpikeToothedSalmon salmon = UP2Entities.SPIKE_TOOTHED_SALMON.get().create(serverLevel);
        if (salmon != null) {
            salmon.setVariant(this.getVariant());
            salmon.setZombie(this.isZombie());
        }
        return salmon;
    }

    public enum SpikeToothedSalmonVariant {
        GOLDEN(0),
        SILVER(1),
        RED(2);

        private final int id;

        SpikeToothedSalmonVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static SpikeToothedSalmonVariant byId(int id) {
            if (id < 0 || id >= SpikeToothedSalmonVariant.values().length) {
                id = 0;
            }
            return SpikeToothedSalmonVariant.values()[id];
        }
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        if (spawnType == MobSpawnType.BUCKET) {
            return spawnGroupData;
        } else if (spawnType != MobSpawnType.CONVERSION) {
            if (level.getBiome(this.blockPosition()).is(BiomeTags.IS_RIVER)) {
                this.setVariant(SpikeToothedSalmonVariant.RED);
            }
            else if (level.getBiome(this.blockPosition()).is(BiomeTags.IS_OCEAN)) {
                this.setVariant(SpikeToothedSalmonVariant.SILVER);
            }
            else {
                this.setVariant(SpikeToothedSalmonVariant.GOLDEN);
            }

            if (spawnType != MobSpawnType.SPAWN_EGG) {
                this.setZombie(true);
            }
        }
        return spawnGroupData;
    }

    // Goals
    private static class SpikeToothedSalmonAttackGoal extends AttackGoal {

        private final SpikeToothedSalmon salmon;

        public SpikeToothedSalmonAttackGoal(SpikeToothedSalmon salmon) {
            super(salmon);
            this.salmon = salmon;
        }

        @Override
        public boolean canUse() {
            return (!salmon.isBaby() || salmon.isZombie()) && salmon.getTarget() != null && salmon.getTarget().isAlive() && (salmon.getTarget().isInWaterOrBubble() || !salmon.isInWaterOrBubble()) && !salmon.isVehicle() && !salmon.isSitting() && !salmon.isEepy();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && (salmon.getTarget() != null && salmon.getTarget().isInWaterOrBubble() || !salmon.isInWaterOrBubble());
        }

        @Override
        public void tick() {
            LivingEntity target = salmon.getTarget();
            if (target != null) {
                this.salmon.lookAt(target, 30F, 30F);
                this.salmon.getLookControl().setLookAt(target, 30F, 30F);
                double distance = salmon.distanceToSqr(target.getX(), target.getY(), target.getZ());
                this.salmon.getNavigation().moveTo(target, 1.5D);

                if (salmon.getAttackState() == 1) {
                    this.tickAttack();
                } else if (distance <= this.getAttackReachSqr(target) && salmon.attackCooldown == 0) {
                    this.salmon.setAttackState(1);
                }
            }
        }

        protected void tickAttack() {
            this.timer++;
            LivingEntity target = salmon.getTarget();
            if (timer == 1) salmon.setPose(UP2Poses.ATTACKING.get());
            if (timer == 4) salmon.playSound(UP2SoundEvents.SPIKE_TOOTHED_SALMON_ATTACK.get(), 1.0F, 0.9F + salmon.getRandom().nextFloat() * 0.2F);
            if (timer == 6) {
                if (this.isInAttackRange(target, 1.5D) && target != null) {
                    this.salmon.doHurtTarget(target);
                    this.salmon.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 10) {
                this.timer = 0;
                this.salmon.attackCooldown = 5;
                this.salmon.setPose(Pose.STANDING);
                this.salmon.setAttackState(0);
            }
        }
    }
}