package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.WoollyMammoth;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2LootTables;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Austroraptor extends PrehistoricAmphibiousMob {

    private static final EntityDataAccessor<Integer> SHEARED_TICKS = SynchedEntityData.defineId(Austroraptor.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> HATED_UUID = SynchedEntityData.defineId(Austroraptor.class, EntityDataSerializers.OPTIONAL_UUID);

    private int attackCooldown = 0;
    private int fishingCooldown = 2000 + this.getRandom().nextInt(2000);
    private int timeUntilEating = 60 + this.getRandom().nextInt(60);

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState fallAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState fishingAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState preen1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState preen2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();

    private boolean preenAlt = false;

    public Austroraptor(EntityType<? extends Austroraptor> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new SurfaceSwimGoal(this, 0.65D));
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new AustroraptorAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new AustroraptorFishGoal(this));
        this.goalSelector.addGoal(5, new LeaveWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new PrehistoricWanderGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new EepyGoal(this));
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 80, 1, true, 0.001F, this::canPreen));
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 20, 2, false, 0.001F));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 10, true, false, entity -> entity == this.getHatedEntity()));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.STEP_HEIGHT, 1.2D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

    public boolean canPreen(Entity entity) {
        return !entity.isInWaterOrBubble() && ((Austroraptor) entity).getShearedTicks() <= 0 && !((Austroraptor) entity).isBaby();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 1400 + this.getRandom().nextInt(1400);
        }
        else if (idleState == 2) {
            return 1200 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        Vec3 deltaMovement = this.getDeltaMovement();
        if (!this.onGround() && !this.isInWaterOrBubble() && deltaMovement.y < 0.0D) {
            this.setDeltaMovement(deltaMovement.multiply(1.0D, 0.8D, 1.0D));
            if (this.getMovementEmission().emitsEvents()) {
                this.gameEvent(GameEvent.FLAP);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.isFood(this.getMainHandItem()) && this.getPose() == Pose.STANDING) {
                if (timeUntilEating > 0) {
                    this.timeUntilEating--;
                }
                else if (this.getRandom().nextInt(40) == 0) {
                    if (this.getEatTicks() <= 0) {
                        this.setEatTicks(30);
                    }
                    if (timeUntilEating <= 0) {
                        this.timeUntilEating = 60 + this.getRandom().nextInt(60);
                    }
                    ItemStack mainHandItem = this.getMainHandItem();
                    this.level().broadcastEntityEvent(this, (byte) 45);
                    this.level().playSound(null, this.blockPosition(), this.getEatingSound(), SoundSource.NEUTRAL, 0.3F, this.getVoicePitch());
                    FoodProperties foodProperties = mainHandItem.getFoodProperties(this);
                    float healAmount = foodProperties != null ? (float) foodProperties.nutrition() : 2.0F;
                    this.heal(2.0F * healAmount);
                    mainHandItem.shrink(1);
                }
            }
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (this.getShearedTicks() > 0) {
            this.setShearedTicks(this.getShearedTicks() - 1);
        }
        if (this.getShearedTicks() <= 0 && this.getHatedEntity() != null) {
            this.setHatedUUID(null);
        }
        if (!this.level().isClientSide) {
            if (attackCooldown > 0) {
                this.attackCooldown--;
            }
            if (this.getMainHandItem().isEmpty() && fishingCooldown > 0) {
                this.fishingCooldown--;
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy() && this.onGround() && !this.isInWaterOrBubble() && this.getIdleState() != 1, tickCount);
        this.fallAnimationState.animateWhen(!this.onGround() && !this.isInWaterOrBubble() && !this.onClimbable() && !this.isPassenger() && !this.isEepy(), tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), tickCount);
        this.preen1AnimationState.animateWhen(this.getIdleState() == 1 && !preenAlt, tickCount);
        this.preen2AnimationState.animateWhen(this.getIdleState() == 1 && preenAlt, tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 2, tickCount);
        this.fishingAnimationState.animateWhen(this.getPose() == UP2Poses.FISHING.get(), tickCount);
        this.eatAnimationState.animateWhen(this.getEatTicks() > 0, tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float) Mth.length(this.getX() - xo, includeHeight ? this.getY() - yo : 0.0, this.getZ() - zo);
        if (this.isBaby()) {
            this.updateWalkAnimation(f * 0.5F);
        } else {
            this.updateWalkAnimation(f);
        }
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f = Math.min(partialTick * 25.0F, 1.0F);
        this.walkAnimation.update(f, 0.4F);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (IDLE_STATE.equals(key)) {
            if (this.getIdleState() == 2) {
                this.preenAlt = this.getRandom().nextBoolean();
            }
        }
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, this.getBbHeight() * 0.3F, this.getBbWidth() * 1.15F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.refuseToMove()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVector = travelVector.multiply(0.0D, 1.0D, 0.0D);
        }
        super.travel(travelVector);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!this.isBaby() && this.getShearedTicks() <= 0 && itemStack.is(Tags.Items.TOOLS_SHEAR)) {
            if (this.isEepy()) {
                this.setEepy(false);
                this.setEepyCooldown(100);
            }
            this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
            this.playSound(UP2SoundEvents.AUSTRORAPTOR_SCREAM.get(), 2.0F, this.getVoicePitch());
            this.gameEvent(GameEvent.SHEAR, player);
            if (!this.level().isClientSide) {
                LootTable loottable = Objects.requireNonNull(this.level().getServer()).reloadableRegistries().getLootTable(UP2LootTables.AUSTRORAPTOR_SHEARING);
                List<ItemStack> items = loottable.getRandomItems((new LootParams.Builder((ServerLevel) this.level())).withParameter(LootContextParams.THIS_ENTITY, this).create(LootContextParamSets.PIGLIN_BARTER));
                items.forEach(this::spawnFeathers);
            }
            this.setShearedTicks(24000 + this.getRandom().nextInt(24000));
            this.setHatedUUID(player.getUUID());
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @SuppressWarnings("DataFlowIssue")
    public void spawnFeathers(ItemStack stack) {
        ItemEntity item = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), stack);
        item.setDefaultPickUpDelay();
        if (this.captureDrops() != null) {
            this.captureDrops().add(item);
        } else {
            this.level().addFreshEntity(item);
        }
        item.setDeltaMovement(item.getDeltaMovement().add((item.getRandom().nextFloat() - item.getRandom().nextFloat()) * 0.1F, item.getRandom().nextFloat() * 0.05F, (item.getRandom().nextFloat() - item.getRandom().nextFloat()) * 0.1F));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SHEARED_TICKS, 0);
        builder.define(HATED_UUID, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("ShearedTicks", this.getShearedTicks());
        if (this.getHatedUUID() != null) {
            compoundTag.putUUID("Hated", this.getHatedUUID());
        }
        compoundTag.putInt("FishingCooldown", fishingCooldown);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setShearedTicks(compoundTag.getInt("ShearedTicks"));
        if (compoundTag.hasUUID("Hated")) {
            this.setHatedUUID(compoundTag.getUUID("Hated"));
        }
        this.fishingCooldown = compoundTag.getInt("FishingCooldown");
    }

    public int getShearedTicks() {
        return entityData.get(SHEARED_TICKS);
    }
    public void setShearedTicks(int i) {
        this.entityData.set(SHEARED_TICKS, i);
    }

    @Nullable
    public UUID getHatedUUID() {
        return entityData.get(HATED_UUID).orElse(null);
    }
    public void setHatedUUID(@Nullable UUID uuid) {
        this.entityData.set(HATED_UUID, Optional.ofNullable(uuid));
    }
    @Nullable
    public LivingEntity getHatedEntity() {
        UUID uuid = this.getHatedUUID();
        return uuid == null ? null : this.level().getPlayerByUUID(uuid);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.getShearedTicks() > 0 ? UP2SoundEvents.AUSTRORAPTOR_HISS.get() : UP2SoundEvents.AUSTRORAPTOR_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.AUSTRORAPTOR_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.AUSTRORAPTOR_DEATH.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        Austroraptor baby = UP2Entities.AUSTRORAPTOR.get().create(level);
        if (baby != null) {
            baby.setVariantRawId(this.inheritVariantFrom(mob, this.getRandom()));
        }
        return baby;
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/austroraptor/austroraptor.png");
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.pickVariantForSpawn(level);
        return data;
    }

    // Goals
    private static class AustroraptorAttackGoal extends AttackGoal {

        protected final Austroraptor austroraptor;

        public AustroraptorAttackGoal(Austroraptor austroraptor) {
            super(austroraptor);
            this.austroraptor = austroraptor;
        }

        @Override
        public void tick() {
            LivingEntity target = austroraptor.getTarget();
            if (target != null) {
                double distance = austroraptor.distanceToSqr(target);
                this.austroraptor.lookAt(target, 30.0F, 30.0F);
                this.austroraptor.getLookControl().setLookAt(target, 30.0F, 30.0F);
                if (this.austroraptor.getAttackState() == 1) {
                    this.austroraptor.getNavigation().stop();
                    this.tickAttack(target);
                } else {
                    this.austroraptor.getNavigation().moveTo(target, 1.8D);
                    if (distance <= this.getAttackReachSqr(target) && austroraptor.attackCooldown == 0) {
                        this.austroraptor.setAttackState(1);
                    }
                }
            }
        }

        private void tickAttack(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.austroraptor.playSound(UP2SoundEvents.AUSTRORAPTOR_ATTACK.get(), 1.0F, austroraptor.getVoicePitch());
                this.austroraptor.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 6) {
                if (this.isInAttackRange(target, 2.0D)) {
                    this.austroraptor.doHurtTarget(target);
                }
            }
            if (timer > 10) {
                this.timer = 0;
                this.austroraptor.attackCooldown = 6;
                this.austroraptor.setPose(Pose.STANDING);
                this.austroraptor.setAttackState(0);
            }
        }
    }

    private static class AustroraptorFishGoal extends Goal {

        private final Austroraptor austroraptor;
        private BlockPos targetPos = null;
        private int idleTime = 0;
        private int navigateTime = 0;

        public AustroraptorFishGoal(Austroraptor austroraptor) {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            this.austroraptor = austroraptor;
        }

        @Override
        public void stop() {
            this.austroraptor.setPose(Pose.STANDING);
            this.austroraptor.fishingCooldown = 2000 + austroraptor.getRandom().nextInt(2000);
            this.targetPos = null;
            this.idleTime = 0;
            this.navigateTime = 0;
            this.austroraptor.getNavigation().stop();
        }

        @Override
        public boolean canUse() {
            if (austroraptor.getTarget() != null) {
                return false;
            }
            else if (!austroraptor.getMainHandItem().isEmpty()) {
                return false;
            }
            else if (austroraptor.fishingCooldown == 0 && austroraptor.getRandom().nextInt(60) == 0) {
                if (austroraptor.isInWater()) {
                    this.targetPos = austroraptor.blockPosition();
                    return true;
                }
                else {
                    this.targetPos = this.findWater();
                    return targetPos != null;
                }
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return targetPos != null && austroraptor.fishingCooldown == 0 && austroraptor.getTarget() == null;
        }

        @Override
        public void tick() {
            if (targetPos != null) {
                double distance = austroraptor.distanceToSqr(Vec3.atCenterOf(targetPos));
                if (distance <= 1.0F) {
                    this.navigateTime = 0;
                    this.austroraptor.getNavigation().stop();
                    this.idleTime++;
                    if (idleTime == 1) {
                        this.austroraptor.setPose(UP2Poses.FISHING.get());
                    }
                    if (idleTime == 30 && austroraptor.getPose() == UP2Poses.FISHING.get()) {
                        this.austroraptor.gameEvent(GameEvent.ITEM_INTERACT_START);
                        this.austroraptor.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.1F, 0.9F + austroraptor.getRandom().nextFloat());
                        this.spawnFishingLoot();
                    }
                    if (idleTime > 40) {
                        this.stop();
                    }
                }
                else {
                    this.navigateTime++;
                    this.austroraptor.getNavigation().moveTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.2D);
                }
                if (navigateTime > 3600) {
                    this.stop();
                }
            }
        }

        public void spawnFishingLoot() {
            LootParams.Builder builder = new LootParams.Builder((ServerLevel) austroraptor.level());
            LootContextParamSet.Builder parameters = new LootContextParamSet.Builder();
            LootTable loot = Objects.requireNonNull(austroraptor.level().getServer()).reloadableRegistries().getLootTable(BuiltInLootTables.FISHING_FISH);
            List<ItemStack> result = loot.getRandomItems(builder.create(parameters.build()));
            for (ItemStack itemStack : result) {
                ItemEntity item = new ItemEntity(austroraptor.level(), austroraptor.getX() + 0.5F, austroraptor.getY(), austroraptor.getZ(), itemStack);
                if (!austroraptor.level().isClientSide) {
                    if (itemStack.is(UP2ItemTags.DIET_PISCIVORE)) {
                        this.austroraptor.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
                    }
                    else {
                        this.austroraptor.level().addFreshEntity(item);
                    }
                }
            }
        }

        public BlockPos findWater() {
            RandomSource random = austroraptor.getRandom();
            int range = 32;
            for (int i = 0; i < 15; i++) {
                BlockPos blockPos = austroraptor.blockPosition().offset(random.nextInt(range) - range / 2, 3, random.nextInt(range) - range / 2);
                while (austroraptor.level().isEmptyBlock(blockPos) && blockPos.getY() > 1) {
                    blockPos = blockPos.below();
                }
            }
            return null;
        }
    }
}
