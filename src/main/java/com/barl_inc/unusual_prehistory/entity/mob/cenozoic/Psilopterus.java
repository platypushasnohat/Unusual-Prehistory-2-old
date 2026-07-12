package com.barl_inc.unusual_prehistory.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothGroundNavigation;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.*;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Objects;
import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public class Psilopterus extends PrehistoricMob implements PackAnimal, ButtonPressingMob, LeverPullingMob, ChestLootingMob {

    private static final EntityDataAccessor<Boolean> PACK_LEADER = SynchedEntityData.defineId(Psilopterus.class, EntityDataSerializers.BOOLEAN);

    private Psilopterus priorPackMember;
    private Psilopterus afterPackMember;

    private boolean hasLeaderAttributes = false;

    private int fleeTicks = 0;
    private Vec3 fleeFromPosition;

    private int pushButtonCooldown = 0;
    private int pullLeverCooldown = 0;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState kickAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState pokeAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState dig1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState dig2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState preen1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState preen2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState flapAnimationState = new SmoothAnimationState();

    private int pokeTicks;
    private boolean attackAlt = false;
    private boolean digAlt = false;
    private boolean preenAlt = false;

    public Psilopterus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 1.7D, 10, 4));
        this.goalSelector.addGoal(2, new JoinPackGoal(this, 60, 5));
        this.goalSelector.addGoal(3, new PsilopterusAttackGoal(this));
        this.goalSelector.addGoal(4, new PsilopterusFleeGoal(this));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_CARNIVORE), false));
        this.goalSelector.addGoal(6, new PsilopterusOpenDoorGoal(this));
        this.goalSelector.addGoal(7, new LootChestGoal(this, 1, 8, 6));
        this.goalSelector.addGoal(8, new PressButtonGoal(this, 1, 6, 4));
        this.goalSelector.addGoal(8, new PullLeverGoal(this, 1, 6, 4));
        this.goalSelector.addGoal(9, new PrehistoricWanderGoal(this, 1));
        this.goalSelector.addGoal(10, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(12, new EepyGoal(this));
        this.goalSelector.addGoal(13, new IdleAnimationGoal(this, 60, 1, true, 0.001F, this::canDig) {
            @Override
            public void tick() {
                super.tick();
                if (timer % 6 == 0 && timer < 50 && timer > 20) {
                    Psilopterus.this.spawnEffectsAtBlock(Psilopterus.this.blockPosition().below());
                    Psilopterus.this.playSound(Psilopterus.this.level().getBlockState(Psilopterus.this.blockPosition().below()).getSoundType().getHitSound(), 0.3F, 0.8F + Psilopterus.this.getRandom().nextFloat() * 0.25F);
                }
            }
        });
        this.goalSelector.addGoal(13, new IdleAnimationGoal(this, 80, 2, true, 0.001F, this::canPreen));
        this.targetSelector.addGoal(0, (new HurtByTargetGoal(this, Psilopterus.class)).setAlertOthers());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 200, true, true, entity -> entity.getType().is(UP2EntityTags.PSILOPTERUS_KICK_TARGETS)));
        this.targetSelector.addGoal(2, new PsilopterusNearestAttackableTargetGoal<>(this, Player.class, 200, true, true, this::canAttack, 3));
        this.targetSelector.addGoal(3, new PsilopterusNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, entity -> entity.getType().is(UP2EntityTags.MEDIUM_PSILOPTERUS_PACK_TARGETS), 3));
        this.targetSelector.addGoal(4, new PsilopterusNearestAttackableTargetGoal<>(this, LivingEntity.class, 400, true, true, entity -> entity.getType().is(UP2EntityTags.LARGE_PSILOPTERUS_PACK_TARGETS), 5));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28F)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        SmoothGroundNavigation navigation = new SmoothGroundNavigation(this, level);
        navigation.setCanOpenDoors(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
        return false;
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
    public void aiStep() {
        super.aiStep();
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && !this.isInWaterOrBubble() && vec3.y < 0.0) {
            this.setDeltaMovement(vec3.multiply(1.0, 0.75, 1.0));
            if (this.getMovementEmission().emitsEvents()) {
                this.gameEvent(GameEvent.FLAP);
            }
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_CARNIVORE);
    }

    @Override
    public void pressButton() {
        this.setPose(UP2Poses.POKING.get());
    }

    @Override
    public int getPushButtonCooldown() {
        return this.pushButtonCooldown;
    }

    @Override
    public void setPushButtonCooldown(int cooldown) {
        this.pushButtonCooldown = cooldown;
    }

    @Override
    public void pullLever() {
        this.setPose(UP2Poses.POKING.get());
    }

    @Override
    public int getPullLeverCooldown() {
        return this.pullLeverCooldown;
    }

    @Override
    public void setPullLeverCooldown(int cooldown) {
        this.pullLeverCooldown = cooldown;
    }

    @Override
    public boolean shouldLootItem(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_CARNIVORE);
    }

    @Override
    public void startOpeningChest() {
        if (this.getPose() == Pose.STANDING) {
            this.setPose(UP2Poses.POKING.get());
        }
    }

    @Override
    public boolean openChest() {
        return this.getPose() == UP2Poses.POKING.get() && pokeTicks == 10;
    }

    @Override
    public void afterLooting(BlockPos stealPos) {
        this.fleeFromPosition = Vec3.atCenterOf(stealPos);
        this.fleeTicks = 30 + this.getRandom().nextInt(30);
        this.setEatTicks(70 + this.getRandom().nextInt(70));
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, 0.5D, this.getBbWidth() * 1.3F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = this.getTarget();
        if (target != null && target.isAlive() && !(target instanceof Player player && player.isCreative())) {
            if (this.isPackLeader()) {
                PackAnimal leader = this;
                while (leader.getAfterPackMember() != null) {
                    leader = leader.getAfterPackMember();
                    if (!((Psilopterus) leader).isAlliedTo(target)) {
                        ((Psilopterus) leader).setTarget(target);
                    }
                }
            }
        }

        if (this.isPackLeader() && !hasLeaderAttributes) {
            this.hasLeaderAttributes = true;
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(16.0D);
            Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(6.0D);
            this.heal(16.0F);
        }
        if (!this.isPackLeader() && hasLeaderAttributes) {
            this.hasLeaderAttributes = false;
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(12.0D);
            Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(5.0D);
            this.heal(12.0F);
        }

        if (!this.level().isClientSide) {
            if (fleeTicks > 0) fleeTicks--;
            if (pushButtonCooldown > 0) pushButtonCooldown--;
            if (pullLeverCooldown > 0) pullLeverCooldown--;
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (pokeTicks > 0) pokeTicks--;
        if (pokeTicks == 0 && this.getPose() == UP2Poses.POKING.get()) this.setPose(Pose.STANDING);
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.canPlayIdleAnimation() && this.onGround() && !this.isInWaterOrBubble(), this.tickCount);
        this.swimAnimationState.animateWhen(this.canPlayIdleAnimation() && this.isInWaterOrBubble(), this.tickCount);
        this.pokeAnimationState.animateWhen(this.getPose() == UP2Poses.POKING.get(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.kickAnimationState.animateWhen(this.getPose() == UP2Poses.KICKING.get(), this.tickCount);
        this.dig1AnimationState.animateWhen(this.getIdleState() == 1 && !digAlt, this.tickCount);
        this.dig2AnimationState.animateWhen(this.getIdleState() == 1 && digAlt, this.tickCount);
        this.preen1AnimationState.animateWhen(this.getIdleState() == 2 && !preenAlt, this.tickCount);
        this.preen2AnimationState.animateWhen(this.getIdleState() == 2 && preenAlt, this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.flapAnimationState.animateWhen(!this.onGround() && !this.isInWaterOrBubble() && !this.isEepy(), this.tickCount);
    }

    private boolean canPlayIdleAnimation() {
        return this.getIdleState() != 1 && this.getIdleState() != 2 && this.getPose() != UP2Poses.KICKING.get() && !this.isEepy();
    }

    private boolean canDig(Entity entity) {
        return entity.level().getBlockState(entity.blockPosition().below()).is(UP2BlockTags.PSILOPTERUS_DIGGING_BLOCKS) && !entity.isInWaterOrBubble();
    }

    private boolean canPreen(Entity entity) {
        return !entity.isInWaterOrBubble();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 1200 + this.getRandom().nextInt(1200);
        } else if (idleState == 2) {
            return 1000 + this.getRandom().nextInt(1200);
        } else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    public void spawnEffectsAtBlock(BlockPos blockPos) {
        float radius = 0.3F;
        for (int i1 = 0; i1 < 3; i1++) {
            double motionX = this.getRandom().nextGaussian() * 0.07D;
            double motionY = this.getRandom().nextGaussian() * 0.07D;
            double motionZ = this.getRandom().nextGaussian() * 0.07D;
            float angle = (float) ((0.0174532925 * this.yBodyRot) + i1);
            double extraX = radius * Mth.sin(Mth.PI + angle);
            double extraY = 0.8F;
            double extraZ = radius * Mth.cos(angle);
            BlockState state = this.level().getBlockState(blockPos);
            ((ServerLevel) this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), blockPos.getX() + 0.5 + extraX, blockPos.getY() + 0.5 + extraY, blockPos.getZ() + 0.5 + extraZ, 1, motionX, motionY, motionZ, 1);
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (DATA_POSE.equals(key)) {
            if (this.getPose() == UP2Poses.POKING.get()) {
                this.pokeTicks = 20;
            }
            else if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.attackAlt = this.getRandom().nextBoolean();
            }
        }
        if (IDLE_STATE.equals(key)) {
            if (this.getIdleState() == 1) {
                this.digAlt = this.getRandom().nextBoolean();
            }
            else if (this.getIdleState() == 2) {
                this.preenAlt = this.getRandom().nextBoolean();
            }
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 2;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PACK_LEADER, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("PackLeader", this.isPackLeader());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setPackLeader(compoundTag.getBoolean("PackLeader"));
    }

    public boolean isPackLeader() {
        return this.entityData.get(PACK_LEADER);
    }

    public void setPackLeader(boolean packLeader) {
        this.entityData.set(PACK_LEADER, packLeader);
    }

    @Override
    public PackAnimal getPriorPackMember() {
        return this.priorPackMember;
    }

    @Override
    public PackAnimal getAfterPackMember() {
        return afterPackMember;
    }

    @Override
    public void setPriorPackMember(PackAnimal animal) {
        this.priorPackMember = (Psilopterus) animal;
    }

    @Override
    public void setAfterPackMember(PackAnimal animal) {
        this.afterPackMember = (Psilopterus) animal;
    }

    @Override
    public boolean isValidLeader(PackAnimal packLeader) {
        return packLeader instanceof Psilopterus && ((Psilopterus) packLeader).isAlive() && ((Psilopterus) packLeader).isPackLeader();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        Psilopterus baby = UP2Entities.PSILOPTERUS.get().create(level);
        if (baby != null) {
            baby.setPackLeader(this.isPackLeader());
            baby.setVariantRawId(this.inheritVariantFrom(mob, this.getRandom()));
        }
        return baby;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.PSILOPTERUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.PSILOPTERUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.PSILOPTERUS_DEATH.get();
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/psilopterus/psilopterus.png");
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        var nearbyPsilopterus = level.getEntitiesOfClass(Psilopterus.class, this.getBoundingBox().inflate(16.0D));
        boolean hasPackLeader = nearbyPsilopterus.stream().anyMatch(Psilopterus::isPackLeader);
        this.setPackLeader(nearbyPsilopterus.size() >= 3 && !hasPackLeader);
        this.pickVariantForSpawn(level);
        return data;
    }

    private static class PsilopterusAttackGoal extends AttackGoal {

        protected final Psilopterus psilopterus;

        public PsilopterusAttackGoal(Psilopterus psilopterus) {
            super(psilopterus);
            this.psilopterus = psilopterus;
        }

        @Override
        public void tick() {
            LivingEntity target = psilopterus.getTarget();
            if (target != null) {
                double distance = psilopterus.distanceToSqr(target);
                this.psilopterus.getLookControl().setLookAt(target, 30F, 30F);

                if (psilopterus.getAttackState() == 1) {
                    this.tickPeck(target);
                    this.psilopterus.getNavigation().moveTo(target, 1.0D);
                }
                else if (psilopterus.getAttackState() == 2) {
                    this.tickKick(target);
                }
                else if (psilopterus.getAttackState() == 3) {
                    this.tickKickBabies(target);
                }
                else {
                    if (distance < this.getAttackReachSqr(target)) {
                        if (target.getType().is(UP2EntityTags.PSILOPTERUS_KICK_TARGETS)) {
                            this.psilopterus.setAttackState(3);
                        } else {
                            if (psilopterus.getRandom().nextFloat() < 0.3F && !psilopterus.isInWater()) {
                                this.psilopterus.setAttackState(2);
                            }
                            else {
                                this.psilopterus.setAttackState(1);
                            }
                        }
                    }
                    this.psilopterus.getNavigation().moveTo(target, 1.5D);
                }
            }
        }

        protected void tickPeck(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.psilopterus.setPose(UP2Poses.ATTACKING.get());
                this.psilopterus.playSound(UP2SoundEvents.PSILOPTERUS_BITE.get(), 1.0F, 0.9F + psilopterus.getRandom().nextFloat() * 0.2F);
            }
            if (timer == 5) {
                if (this.isInAttackRange(target, 1.5D)) {
                    this.psilopterus.doHurtTarget(target);
                    this.psilopterus.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 10) {
                this.timer = 0;
                this.psilopterus.setPose(Pose.STANDING);
                this.psilopterus.setAttackState(0);
            }
        }

        protected void tickKick(LivingEntity target) {
            timer++;
            this.psilopterus.getNavigation().stop();
            if (timer == 1) psilopterus.setPose(UP2Poses.KICKING.get());
            if (timer == 12) psilopterus.playSound(UP2SoundEvents.PSILOPTERUS_ATTACK.get(), 1.0F, 0.9F + psilopterus.getRandom().nextFloat() * 0.2F);
            if (timer == 14) {
                if (this.isInAttackRange(target, 2.0D)) {
                    this.psilopterus.doHurtTarget(target);
                    this.strongKnockback(target, 1.5D, 0.2D);
                }
            }
            if (timer > 30) {
                this.timer = 0;
                this.psilopterus.setPose(Pose.STANDING);
                this.psilopterus.setAttackState(0);
            }
        }

        protected void tickKickBabies(LivingEntity target) {
            this.timer++;
            this.psilopterus.getNavigation().stop();
            if (timer == 1) psilopterus.setPose(UP2Poses.KICKING.get());
            if (timer == 12) psilopterus.playSound(UP2SoundEvents.PSILOPTERUS_ATTACK.get(), 1.0F, 0.9F + psilopterus.getRandom().nextFloat() * 0.2F);
            if (timer == 14) {
                if (this.isInAttackRange(target, 2.0D)) {
                    this.strongKnockback(target, 3.0D, 0.4D);
                }
            }
            if (timer > 30) {
                this.stop();
            }
        }
    }

    private static class PsilopterusFleeGoal extends Goal {

        private final Psilopterus psilopterus;

        public PsilopterusFleeGoal(Psilopterus psilopterus) {
            this.psilopterus = psilopterus;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return psilopterus.fleeTicks > 0 && psilopterus.fleeFromPosition != null;
        }

        @Override
        public void stop() {
            this.psilopterus.fleeFromPosition = null;
            this.psilopterus.setRunning(false);
        }

        @Override
        public void tick() {
            this.psilopterus.setRunning(true);
            if (psilopterus.getNavigation().isDone()) {
                Vec3 vec3 = LandRandomPos.getPosAway(psilopterus, 10, 4, psilopterus.fleeFromPosition);
                if (vec3 != null) {
                    this.psilopterus.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.5F);
                }
            }
        }
    }

    private static class PsilopterusOpenDoorGoal extends DoorInteractGoal {

        private final Psilopterus psilopterus;
        private int timer;

        public PsilopterusOpenDoorGoal(Psilopterus psilopterus) {
            super(psilopterus);
            this.psilopterus = psilopterus;
        }

        @Override
        public void start() {
            this.timer = 0;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !this.isOpen() && psilopterus.getPose() == Pose.STANDING;
        }

        @Override
        public boolean canContinueToUse() {
            return !this.isOpen();
        }

        @Override
        public void tick() {
            super.tick();
            Vec3 vec3 = Vec3.atCenterOf(doorPos);
            if (!this.isOpen() && psilopterus.distanceToSqr(vec3) < 4) {
                this.psilopterus.lookAt(EntityAnchorArgument.Anchor.EYES, vec3);
                if (psilopterus.getPose() == Pose.STANDING) {
                    this.psilopterus.setPose(UP2Poses.POKING.get());
                }
            }
            if (psilopterus.getPose() == UP2Poses.POKING.get()) {
                this.timer++;
                if (timer == 6) {
                    this.setOpen(true);
                    this.timer = 0;
                }
            }
        }
    }

    private static class PsilopterusNearestAttackableTargetGoal<T extends LivingEntity> extends PackAnimalNearestAttackableTargetGoal<T> {

        protected final Psilopterus psilopterus;

        public PsilopterusNearestAttackableTargetGoal(Psilopterus psilopterus, Class<T> targetClass, int interval, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> entityPredicate, int packSizeRequired) {
            super(psilopterus, targetClass, interval, mustSee, mustReach, entityPredicate, packSizeRequired);
            this.psilopterus = psilopterus;
        }

        @Override
        public void start() {
            super.start();
            if (psilopterus.isPackLeader()) {
                this.psilopterus.playSound(UP2SoundEvents.PSILOPTERUS_CALL.get(), 3.0F, 0.9F + psilopterus.getRandom().nextFloat() * 0.25F);
            }
        }

        @Override
        public boolean canUse() {
            if (super.canUse()) {
                return packAnimal.getPackSize() >= packSizeRequired && psilopterus.isPackLeader();
            }
            return false;
        }
    }
}
