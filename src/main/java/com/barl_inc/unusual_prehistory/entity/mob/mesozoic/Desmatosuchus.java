package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.goals.EepyGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricPanicGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricWanderGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.RandomSitGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2LootTables;
import com.barl_inc.unusual_prehistory.registry.UP2Particles;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class Desmatosuchus extends PrehistoricMob {

    private static final EntityDataAccessor<Integer> DIRT_TYPE = SynchedEntityData.defineId(Desmatosuchus.class, EntityDataSerializers.INT);

    private static final EntityDimensions BURROWED_DIMENSIONS = EntityDimensions.scalable(1.3F, 0.4F).withEyeHeight(0.3F);
    private static final EntityDimensions EEPY_DIMENSIONS = EntityDimensions.scalable(1.3F, 0.7F).withEyeHeight(0.6F);

    public final SmoothAnimationState sniff1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sniff2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState grazeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState rollAnimationState = new SmoothAnimationState();

    private boolean sniffAlt = false;

    public Desmatosuchus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 2.0D, 10, 4));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_OMNIVORE), false));
        this.goalSelector.addGoal(3, new PrehistoricWanderGoal(this, 1));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new RandomSitGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && Desmatosuchus.this.canBurrow();
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && Desmatosuchus.this.canBurrow();
            }
        });
        this.goalSelector.addGoal(6, new EepyGoal(this));
//        this.goalSelector.addGoal(7, new DesmatosuchusRollGoal(this));
//        this.goalSelector.addGoal(7, new DesmatosuchusShakeGoal(this));
//        this.goalSelector.addGoal(7, new DesmatosuchusSniffGoal(this));
//        this.goalSelector.addGoal(7, new DesmatosuchusGrazeGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.18F)
                .add(Attributes.ARMOR, 12.0F);
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && this.horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.8D * this.getBbHeight();
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 3 || this.getIdleState() == 4;
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.3D, 0.15D, this.getBbWidth() * 1.2F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_OMNIVORE);
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        if (this.isSitting()) return BURROWED_DIMENSIONS.scale(this.getAgeScale());
        else if (this.isEepy()) return EEPY_DIMENSIONS.scale(this.getAgeScale());
        return super.getDefaultDimensions(pose);
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return super.canCollideWith(entity) && !(entity instanceof Desmatosuchus);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    private boolean canBurrow() {
        return this.level().getBlockState(this.blockPosition().below()).is(UP2BlockTags.DESMATOSUCHUS_BURROWING_BLOCKS) || this.level().getBlockState(this.blockPosition()).is(UP2BlockTags.DESMATOSUCHUS_BURROWING_BLOCKS);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);
        if (this.isDirty() && this.getDirtType().getLootTable() != null) {
            if (itemstack.is(ItemTags.SHOVELS) && this.getDirtType() != DirtType.MOSSY) {
                this.level().playSound(null, this, SoundEvents.SHOVEL_FLATTEN, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.gameEvent(GameEvent.BLOCK_DESTROY, player);
                if (!this.level().isClientSide) {
                    LootTable loottable = Objects.requireNonNull(this.level().getServer()).reloadableRegistries().getLootTable(this.getDirtType().getLootTable());
                    List<ItemStack> items = loottable.getRandomItems((new LootParams.Builder((ServerLevel) this.level())).withParameter(LootContextParams.THIS_ENTITY, this).create(LootContextParamSets.PIGLIN_BARTER));
                    items.forEach(this::spawnItemOnBack);
                }
                this.setDirtType(DirtType.CLEAN);
                return InteractionResult.SUCCESS;
            }
            else if (itemstack.is(Tags.Items.TOOLS_SHEAR) && this.getDirtType() == DirtType.MOSSY) {
                this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.gameEvent(GameEvent.SHEAR, player);
                if (!this.level().isClientSide) {
                    LootTable loottable = Objects.requireNonNull(this.level().getServer()).reloadableRegistries().getLootTable(this.getDirtType().getLootTable());
                    List<ItemStack> items = loottable.getRandomItems((new LootParams.Builder((ServerLevel) this.level())).withParameter(LootContextParams.THIS_ENTITY, this).create(LootContextParamSets.PIGLIN_BARTER));
                    items.forEach(this::spawnItemOnBack);
                }
                this.setDirtType(DirtType.CLEAN);
                return InteractionResult.SUCCESS;
            }
            else if (itemstack.is(Items.WATER_BUCKET)) {
                this.level().playSound(null, this, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.level().broadcastEntityEvent(this, (byte) 39);
                this.setDirtType(DirtType.CLEAN);
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                return InteractionResult.SUCCESS;
            }
            else if (itemstack.is(Items.POTION)) {
                this.level().playSound(null, this, SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.level().broadcastEntityEvent(this, (byte) 39);
                this.setDirtType(DirtType.CLEAN);
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                }
                return InteractionResult.SUCCESS;
            }
        }
        if (!this.isDirty()) {
            if (itemstack.is(Items.POWDER_SNOW_BUCKET)) {
                this.level().playSound(null, this, SoundEvents.BUCKET_EMPTY_POWDER_SNOW, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.setDirtType(DirtType.SNOWY);
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                return InteractionResult.SUCCESS;
            }
            if (itemstack.is(UP2ItemTags.SNOW)) {
                this.level().playSound(null, this, SoundEvents.SNOW_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.setDirtType(DirtType.SNOWY);
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
            if (itemstack.is(UP2ItemTags.MOSS)) {
                this.level().playSound(null, this, SoundEvents.MOSS_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.setDirtType(DirtType.MOSSY);
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
            if (itemstack.is(UP2ItemTags.MUD)) {
                this.level().playSound(null, this, SoundEvents.MUD_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.setDirtType(DirtType.MUDDY);
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return type;
    }

    private void spawnItemOnBack(ItemStack stack) {
        this.spawnAtLocation(stack, 1.4F);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.tickCount % 80 == 0) {
            if ((this.isInWaterOrBubble() || isRainingAt(this.level(), this.blockPosition().above())) && this.isDirty()) {
                this.level().broadcastEntityEvent(this, (byte) 39);
                this.setDirtType(DirtType.CLEAN);
            }
            if (isSnowingAt(this.level(), this.blockPosition().above()) && !this.isDirty()) {
                this.setDirtType(DirtType.SNOWY);
            }
            if (this.isInPowderSnow && !this.isDirty()) {
                this.setDirtType(DirtType.SNOWY);
            }
            if (this.getRemainingFireTicks() > 0 && this.isDirty()) {
                this.level().broadcastEntityEvent(this, (byte) 67);
                this.setDirtType(DirtType.CLEAN);
            }
        }
    }

    public static boolean isSnowingAt(Level level, BlockPos pos) {
        if (!level.isRaining()) {
            return false;
        } else if (!level.canSeeSky(pos)) {
            return false;
        } else if (level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() > pos.getY()) {
            return false;
        } else if (level.getGameRules().getInt(GameRules.RULE_SNOW_ACCUMULATION_HEIGHT) <= 0) {
            return false;
        } else {
            return level.getBiome(pos).value().getPrecipitationAt(pos) == Biome.Precipitation.SNOW;
        }
    }

    public static boolean isRainingAt(Level level, BlockPos pos) {
        if (!level.isRaining()) {
            return false;
        } else if (!level.canSeeSky(pos)) {
            return false;
        } else if (level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() > pos.getY()) {
            return false;
        } else {
            return level.getBiome(pos).value().getPrecipitationAt(pos) == Biome.Precipitation.RAIN;
        }
    }

    private void washEffect(ParticleOptions particleOptions) {
        for (int i = 0; i < 8; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(particleOptions, this.getRandomX(0.8D), this.getRandomY() + 0.6D, this.getRandomZ(0.8D), d0, d1, d2);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 39) {
            this.washEffect(UP2Particles.OUT_OF_WATER_BUBBLE.get());
        } else if (id == 67) {
            this.washEffect(ParticleTypes.FLAME);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isEepy() && !this.isSitting() && this.getIdleState() != 1, this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.sitAnimationState.animateWhen(this.isSitting(), this.tickCount);
        this.rollAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.sniff1AnimationState.animateWhen(this.getIdleState() == 3 && !sniffAlt, this.tickCount);
        this.sniff2AnimationState.animateWhen(this.getIdleState() == 3 && sniffAlt, this.tickCount);
        this.grazeAnimationState.animateWhen(this.getIdleState() == 4, this.tickCount);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DIRT_TYPE, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("DirtType", this.getDirtType().getId());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setDirtType(DirtType.byId(compoundTag.getInt("DirtType")));
    }

    public boolean isDirty() {
        return this.getDirtType() != DirtType.CLEAN;
    }

    public DirtType getDirtType() {
        return DirtType.byId(this.entityData.get(DIRT_TYPE));
    }

    public void setDirtType(DirtType type) {
        this.entityData.set(DIRT_TYPE, type.getId());
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.DESMATOSUCHUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.DESMATOSUCHUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.DESMATOSUCHUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.DESMATOSUCHUS_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    public enum DirtType {
        CLEAN(0, null),
        MOSSY(1, UP2LootTables.DESMATOSUCHUS_MOSSY),
        MUDDY(2, UP2LootTables.DESMATOSUCHUS_MUDDY),
        SNOWY(3, UP2LootTables.DESMATOSUCHUS_SNOWY);

        private final int id;
        private final ResourceKey<LootTable> lootTable;

        DirtType(int id, @Nullable ResourceKey<LootTable> lootTable) {
            this.id = id;
            this.lootTable = lootTable;
        }

        public int getId() {
            return this.id;
        }

        public ResourceKey<LootTable> getLootTable() {
            return this.lootTable;
        }

        public static DirtType byId(int id) {
            if (id < 0 || id >= DirtType.values().length) {
                id = 0;
            }
            return DirtType.values()[id];
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        Desmatosuchus baby = UP2Entities.DESMATOSUCHUS.get().create(level);
        if (baby != null) {
            baby.setVariantRawId(this.inheritVariantFrom(mob, this.getRandom()));
        }
        return baby;
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/desmatosuchus/desmatosuchus.png");
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.pickVariantForSpawn(level);
        return data;
    }

    // Goals
//    private static class DesmatosuchusRollGoal extends IdleAnimationGoal {
//
//        private final Desmatosuchus desmatosuchus;
//
//        public DesmatosuchusRollGoal(Desmatosuchus desmatosuchus) {
//            super(desmatosuchus, 80, 1);
//            this.desmatosuchus = desmatosuchus;
//        }
//
//        @Override
//        public boolean canUse() {
//            return super.canUse() && desmatosuchus.getRollCooldown() == 0 && this.isRollingBlock();
//        }
//
//        @Override
//        public void stop() {
//            super.stop();
//            this.desmatosuchus.setRollCooldown(1200 + desmatosuchus.getRandom().nextInt(1200));
//        }
//
//        @Override
//        public void tick() {
//            super.tick();
//            if (timer == 30 && !desmatosuchus.isDirty()) {
//                if (this.isDirtyBlock(UP2BlockTags.DESMATOSUCHUS_MOSSY_BLOCKS)) {
//                    this.desmatosuchus.setDirtType(DirtType.MOSSY);
//                } else if (this.isDirtyBlock(UP2BlockTags.DESMATOSUCHUS_MUDDY_BLOCKS)) {
//                    this.desmatosuchus.setDirtType(DirtType.MUDDY);
//                } else if (this.isDirtyBlock(UP2BlockTags.DESMATOSUCHUS_SNOWY_BLOCKS)) {
//                    this.desmatosuchus.setDirtType(DirtType.SNOWY);
//                }
//            }
//        }
//
//        protected boolean isRollingBlock() {
//            return desmatosuchus.level().getBlockState(desmatosuchus.blockPosition().below()).is(UP2BlockTags.DESMATOSUCHUS_ROLLING_BLOCKS) || desmatosuchus.level().getBlockState(desmatosuchus.blockPosition()).is(UP2BlockTags.DESMATOSUCHUS_ROLLING_BLOCKS);
//        }
//
//        protected boolean isDirtyBlock(TagKey<Block> blockTag) {
//            return desmatosuchus.level().getBlockState(desmatosuchus.blockPosition().below()).is(blockTag) || desmatosuchus.level().getBlockState(desmatosuchus.blockPosition()).is(blockTag);
//        }
//    }
//
//    private static class DesmatosuchusShakeGoal extends IdleAnimationGoal {
//
//        private final Desmatosuchus desmatosuchus;
//
//        public DesmatosuchusShakeGoal(Desmatosuchus desmatosuchus) {
//            super(desmatosuchus, 40, 2, false);
//            this.desmatosuchus = desmatosuchus;
//        }
//
//        @Override
//        public boolean canUse() {
//            return super.canUse() && desmatosuchus.shakeCooldown == 0 && !desmatosuchus.isSitting();
//        }
//
//        @Override
//        public void stop() {
//            super.stop();
//            this.desmatosuchus.shakeCooldown();
//        }
//    }
//
//    private static class DesmatosuchusSniffGoal extends IdleAnimationGoal {
//
//        private final Desmatosuchus desmatosuchus;
//
//        public DesmatosuchusSniffGoal(Desmatosuchus desmatosuchus) {
//            super(desmatosuchus, 40, 3);
//            this.desmatosuchus = desmatosuchus;
//        }
//
//        @Override
//        public boolean canUse() {
//            return super.canUse() && desmatosuchus.sniffCooldown == 0 && !desmatosuchus.isSitting();
//        }
//
//        @Override
//        public void stop() {
//            super.stop();
//            this.desmatosuchus.sniffCooldown();
//        }
//
//        @Override
//        public void start() {
//            super.start();
//            this.desmatosuchus.sniffAlt = desmatosuchus.getRandom().nextBoolean();
//        }
//    }
//
//    private static class DesmatosuchusGrazeGoal extends IdleAnimationGoal {
//
//        private final Desmatosuchus desmatosuchus;
//
//        public DesmatosuchusGrazeGoal(Desmatosuchus desmatosuchus) {
//            super(desmatosuchus, 40, 4);
//            this.desmatosuchus = desmatosuchus;
//        }
//
//        @Override
//        public boolean canUse() {
//            return super.canUse() && desmatosuchus.grazeCooldown == 0 && desmatosuchus.level().getBlockState(desmatosuchus.blockPosition().below()).is(UP2BlockTags.DESMATOSUCHUS_FOOD_BLOCKS) && !desmatosuchus.isSitting();
//        }
//
//        @Override
//        public void stop() {
//            super.stop();
//            this.desmatosuchus.grazeCooldown();
//        }
//    }
}
