package com.barl_inc.unusual_prehistory.entity.mob.recently_extinct;

import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricBreedableMob;
import com.barl_inc.unusual_prehistory.entity.utils.LeapingMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class Thylacine extends PrehistoricBreedableMob implements LeapingMob {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Thylacine.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(Thylacine.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> BIPEDAL = SynchedEntityData.defineId(Thylacine.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> CHEW_TIME = SynchedEntityData.defineId(Thylacine.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_JOEY = SynchedEntityData.defineId(Thylacine.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> JOEY_TIME = SynchedEntityData.defineId(Thylacine.class, EntityDataSerializers.INT);

    private int bipedalCooldown = 1500 + this.getRandom().nextInt(1500);
    public int leapCooldown = 100 + this.getRandom().nextInt(100);
    public int attackCooldown = 0;

    public final SmoothAnimationState sniffAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState jumpAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState fallAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState idleBipedalAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState chewAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);

    private final byte EAT = 67;

    public Thylacine(EntityType<? extends PrehistoricBreedableMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricSitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new PrehistoricAvoidEntityGoal<>(this, LivingEntity.class, 10.0F,1.5D, entity -> entity.getType().is(UP2EntityTags.THYLACINE_AVOIDS)));
        this.goalSelector.addGoal(2, new PrehistoricAvoidEntityGoal<>(this, Player.class, 10.0F, 1.5D, this::avoidsPlayers));
        this.goalSelector.addGoal(3, new ThylacineAttackGoal(this));
        this.goalSelector.addGoal(4, new PrehistoricPanicGoal(this, 1.5D, 10, 4) {
            @Override
            public boolean canUse() {
                return (Thylacine.this.isBaby() || Thylacine.this.getChewTime() > 0) && super.canUse();
            }
        });
        this.goalSelector.addGoal(5, new PrehistoricFollowOwnerGoal(this, 1.2D, 1.5D, 5.0F, 2.5F));
        this.goalSelector.addGoal(6, new ThylacineBreedGoal(this, 0.8D));
        this.goalSelector.addGoal(7, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TEMPTS_THYLACINE), false));
        this.goalSelector.addGoal(8, new PrehistoricWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(11, new EepyGoal(this));
        this.goalSelector.addGoal(12, new IdleAnimationGoal(this, 40, 1, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(12, new IdleAnimationGoal(this, 80, 2, true, 0.001F, this::canPlayIdles));
        this.targetSelector.addGoal(1, new PrehistoricOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new PrehistoricOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 400, true, true, entity -> entity.getType().is(UP2EntityTags.THYLACINE_TARGETS)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.STEP_HEIGHT, 1.1D);
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
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.TEMPTS_THYLACINE);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public boolean canTrample(@NotNull BlockState state, @NotNull BlockPos pos, float fallDistance) {
        return false;
    }

    private boolean canPlayIdles(Entity entity) {
        return !entity.isInWaterOrBubble() && !((Thylacine) entity).isSitting();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 1200 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 2) {
            return 1300 + this.getRandom().nextInt(1300);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    private boolean avoidsPlayers(LivingEntity living) {
        if (this.isTame()) {
            return false;
        }
        if (living instanceof Player player) {
            return !player.isSpectator() && !(player.isShiftKeyDown() && this.playerHasFood(player));
        }
        return false;
    }

    private boolean playerHasFood(Player player) {
        return player.getItemInHand(InteractionHand.MAIN_HAND).is(UP2ItemTags.TEMPTS_THYLACINE) || player.getItemInHand(InteractionHand.OFF_HAND).is(UP2ItemTags.TEMPTS_THYLACINE);
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity target) {
        return this.getChewTime() <= 0 && super.canAttack(target);
    }

    private boolean canSwallowTarget(LivingEntity target) {
        if (!this.canAttack(target)) {
            return false;
        }
        if (target instanceof Player) {
            return false;
        }
        if (target instanceof Thylacine) {
            return false;
        }
        return target.getBbWidth() < this.getBbWidth() * 2.5F && target.getBbHeight() < this.getBbHeight() * 2.5F;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!this.isTame() && this.getEatTicks() <= 0 && itemStack.is(UP2ItemTags.TAMES_THYLACINE) ) {
            this.setEatTicks(25);
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                this.level().broadcastEntityEvent(this, EAT);
                this.playSound(this.getEatingSound(), 1.0F, this.getVoicePitch());
                if (this.getNavigation().getPath() != null) {
                    this.getNavigation().stop();
                }
                if (this.getTameAttempts() > 3 && this.getRandom().nextBoolean()) {
                    this.level().broadcastEntityEvent(this, (byte) 7);
                    this.tame(player);
                    this.setTameAttempts(0);
                    this.heal(this.getMaxHealth());
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                    this.setTameAttempts(this.getTameAttempts() + 1);
                }
            } else {
                this.spawnEatingParticles(itemStack);
            }
            return InteractionResult.SUCCESS;
        }
        if (this.getEatTicks() > 0 && this.isFood(itemStack)) {
            return InteractionResult.PASS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean canOwnerCommand(Player player, @NotNull InteractionHand hand) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        return !effectInstance.is(MobEffects.HUNGER) && super.canBeAffected(effectInstance);
    }

    private void spawnEatingParticles(ItemStack itemStack) {
        for (int i = 0; i < 8; i++) {
            Vec3 eatPos = this.getEyePosition().add(this.getViewVector(0.0F).add(0, -0.5F, -this.getBbWidth() * 0.1F));
            Vec3 vec3 = (new Vec3((this.getRandom().nextFloat() - 0.5F) * 0.1F, this.getRandom().nextFloat() * 0.1F + 0.1F, 0.0F)).yRot(-this.getYRot() * ((float) Math.PI / 180F));
            this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemStack), eatPos.x, eatPos.y, eatPos.z, vec3.x, vec3.y + 0.05, vec3.z);
        }
    }

    @Override
    public void spawnChildFromBreeding(@NotNull ServerLevel level, @Nullable Animal partner) {
        AgeableMob ageablemob = this.getBreedOffspring(level, null);
        if (ageablemob != null) {
            ageablemob.setBaby(true);
            ageablemob.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            level.addFreshEntityWithPassengers(ageablemob);
        }
        this.setHasJoey(false);
    }

    @Override
    public void finalizeSpawnChildFromBreeding(ServerLevel level, Animal partner, @Nullable AgeableMob baby) {
        this.setAge(6000);
        partner.setAge(6000);
        this.resetLove();
        partner.resetLove();
        level.broadcastEntityEvent(this, (byte) 18);
        if (level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            level.addFreshEntity(new ExperienceOrb(level, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (bipedalCooldown > 0) {
            this.bipedalCooldown--;
        }
        if (bipedalCooldown <= 0) {
            this.bipedalCooldown = 1500 + this.getRandom().nextInt(1500);
            if (this.getRandom().nextBoolean() && !this.isBipedal()) {
                this.setBipedal(true);
            }
            else if (this.isBipedal()) {
                this.setBipedal(false);
            }
        }
        if (attackCooldown > 0) {
            this.attackCooldown--;
        }
        if (leapCooldown > 0) {
            this.leapCooldown--;
        }
        if (this.getChewTime() > 0) {
            this.setChewTime(this.getChewTime() - 1);
        }
        if (this.getJoeyTime() > 0) {
            this.setJoeyTime(this.getJoeyTime() - 1);
        }
        if (this.getJoeyTime() <= 0 && this.hasJoey() && !this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
            this.spawnChildFromBreeding(serverLevel, null);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isEepy() && !this.isSitting() && this.getIdleState() != 2 && !this.isBipedal() && !this.isLeaping(), this.tickCount);
        this.idleBipedalAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isEepy() && !this.isSitting() && this.getIdleState() != 2 && this.isBipedal() && !this.isLeaping(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.sitAnimationState.animateWhen(this.isSitting(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.sniffAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.fallAnimationState.animateWhen(!this.onGround() && this.isLeaping() && this.getDeltaMovement().y < 0.0D, this.tickCount);
        this.jumpAnimationState.animateWhen(!this.onGround() && this.isLeaping() && this.getDeltaMovement().y >= 0.0D, this.tickCount);
        this.chewAnimationState.animateWhen(this.getChewTime() > 0, this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == EAT) {
            this.eatAnimationState.start(this.tickCount);
        }
        else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TAME_ATTEMPTS, 0);
        builder.define(LEAPING, false);
        builder.define(BIPEDAL, false);
        builder.define(CHEW_TIME, 0);
        builder.define(HAS_JOEY, false);
        builder.define(JOEY_TIME, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TameAttempts", this.getTameAttempts());
        compoundTag.putBoolean("Bipedal", this.isBipedal());
        compoundTag.putInt("ChewTime", this.getChewTime());
        compoundTag.putBoolean("HasJoey", this.hasJoey());
        compoundTag.putInt("JoeyTime", this.getJoeyTime());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setTameAttempts(compoundTag.getInt("TameAttempts"));
        this.setBipedal(compoundTag.getBoolean("Bipedal"));
        this.setChewTime(compoundTag.getInt("ChewTime"));
        this.setHasJoey(compoundTag.getBoolean("HasJoey"));
        this.setJoeyTime(compoundTag.getInt("JoeyTime"));
    }

    @Override
    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }
    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }

    public void setTameAttempts(int tameAttempts) {
        this.entityData.set(TAME_ATTEMPTS, tameAttempts);
    }
    public int getTameAttempts() {
        return this.entityData.get(TAME_ATTEMPTS);
    }

    public boolean isBipedal() {
        return this.entityData.get(BIPEDAL);
    }
    public void setBipedal(boolean bipedal) {
        this.entityData.set(BIPEDAL, bipedal);
    }

    public void setChewTime(int chewTime) {
        this.entityData.set(CHEW_TIME, chewTime);
    }
    public int getChewTime() {
        return this.entityData.get(CHEW_TIME);
    }

    public boolean hasJoey() {
        return this.entityData.get(HAS_JOEY);
    }
    public void setHasJoey(boolean hasJoey) {
        this.entityData.set(HAS_JOEY, hasJoey);
    }
    public int getJoeyTime() {
        return this.entityData.get(JOEY_TIME);
    }
    public void setJoeyTime(int joeyTime) {
        this.entityData.set(JOEY_TIME, joeyTime);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @Nullable AgeableMob mob) {
        return UP2Entities.THYLACINE.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WOLF_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return SoundEvents.WOLF_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.1F);
    }

    private static class ThylacineAttackGoal extends AttackGoal {

        protected final Thylacine thylacine;
        private int waitTimer;

        public ThylacineAttackGoal(Thylacine thylacine) {
            super(thylacine);
            this.thylacine = thylacine;
        }

        @Override
        public void start() {
            super.start();
            this.waitTimer = 0;
        }

        @Override
        public void stop() {
            super.stop();
            this.thylacine.setLeaping(false);
        }

        @Override
        public boolean canUse() {
            return thylacine.getChewTime() <= 0 && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return thylacine.getChewTime() <= 0 && super.canContinueToUse();
        }

        @Override
        public void tick() {
            LivingEntity target = thylacine.getTarget();
            if (target != null) {
                double distance = thylacine.distanceTo(target);
                if (!thylacine.isLeaping()) {
                    this.thylacine.lookAt(target, 30.0F, 30.0F);
                    this.thylacine.getLookControl().setLookAt(target, 30.0F, 30.0F);
                }

                if (thylacine.isLeaping()) {
                    this.thylacine.setYRot(((float) Mth.atan2(thylacine.getMotionDirection().getStepZ(), thylacine.getMotionDirection().getStepX())) * Mth.RAD_TO_DEG - 90F);
                }

                if (waitTimer > 0) {
                    this.waitTimer--;
                    this.thylacine.getNavigation().stop();
                }

                if ((thylacine.onGround() || thylacine.isInWaterOrBubble()) && thylacine.isLeaping()) {
                    this.thylacine.setAttackState(0);
                    this.thylacine.setLeaping(false);
                    this.thylacine.leapCooldown = 100 + thylacine.getRandom().nextInt(100);
                    this.waitTimer = 15;
                }

                if (this.thylacine.getAttackState() == 1) {
                    this.thylacine.getNavigation().stop();
                    this.tickBite(target);
                }
                else if (thylacine.getAttackState() == 2) {
                    this.tickLeap(target);
                }
                else {
                    if (waitTimer == 0) {
                        this.thylacine.getNavigation().moveTo(target, 1.5D);
                    }
                    if (distance >= 4.0F && distance <= 7.0F && thylacine.leapCooldown == 0 && this.isPathClear(target) && waitTimer == 0) {
                        this.thylacine.setAttackState(2);
                    }
                    else if (distance <= 1.5D && thylacine.attackCooldown == 0 && waitTimer == 0) {
                        this.thylacine.setAttackState(1);
                    }
                }
            }
        }

        protected void tickBite(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.thylacine.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 16) {
                if (thylacine.canSwallowTarget(target)) {
                    target.discard();
                    this.thylacine.gameEvent(GameEvent.EAT);
                    this.thylacine.playSound(thylacine.getEatingSound(), thylacine.getSoundVolume(), thylacine.getVoicePitch());
                    int chewTime = 1200;
                    if (thylacine.hasEffect(MobEffects.MOVEMENT_SPEED)) {
                        chewTime /= (2 + Objects.requireNonNull(thylacine.getEffect(MobEffects.MOVEMENT_SPEED)).getAmplifier());
                    }
                    this.thylacine.setChewTime(chewTime);
                }
                else {
                    if (this.isInAttackRange(target, 2.0D)) {
                        this.thylacine.doHurtTarget(target);
                    }
                }
            }
            if (timer > 20) {
                this.timer = 0;
                this.thylacine.setPose(Pose.STANDING);
                this.thylacine.attackCooldown = 4 + thylacine.getRandom().nextInt(3);
                this.thylacine.setAttackState(0);
            }
        }

        protected void tickLeap(LivingEntity target) {
            this.timer++;
            this.thylacine.getNavigation().stop();
            if (timer <= 10) {
                this.thylacine.lookAt(target, 30.0F, 30.0F);
                this.thylacine.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }
            if (timer > 10 && thylacine.onGround()) {
                this.thylacine.setLeaping(true);
                Vec3 deltaMovement = thylacine.getDeltaMovement();
                Vec3 vec3 = new Vec3(target.getX() - thylacine.getX(), 0.0D, target.getZ() - thylacine.getZ());
                if (vec3.lengthSqr() > 1.0E-7D) {
                    vec3 = vec3.normalize().add(deltaMovement.scale(0.3D));
                }
                this.thylacine.setDeltaMovement(vec3.x, 0.9F, vec3.z);
            }

            if (this.isInAttackRange(target, 0.5D) && timer > 20) {
                this.thylacine.doHurtTarget(target);
                this.thylacine.setLeaping(false);
                this.thylacine.setPose(Pose.STANDING);
                this.thylacine.setAttackState(0);
                this.thylacine.leapCooldown = 100 + thylacine.getRandom().nextInt(100);
                this.waitTimer = 15;
            } else if (timer > 80) {
                this.thylacine.setPose(Pose.STANDING);
                this.thylacine.setLeaping(false);
                this.thylacine.setAttackState(0);
                this.thylacine.leapCooldown = 100 + thylacine.getRandom().nextInt(100);
                this.waitTimer = 15;
            }
        }

        private boolean isPathClear(LivingEntity target) {
            double z = target.getZ() - thylacine.getZ();
            double x = target.getX() - thylacine.getX();
            double d2 = z / x;

            for (int j = 0; j < 6; j++) {
                double d3 = d2 == 0.0D ? 0.0D : z * (j / 6.0D);
                double d4 = d2 == 0.0D ? x * (j / 6.0D) : d3 / d2;
                for (int k = 1; k < 4; k++) {
                    if (!thylacine.level().getBlockState(BlockPos.containing(thylacine.getX() + d4, thylacine.getY() + k, thylacine.getZ() + d3)).canBeReplaced()) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private static class ThylacineBreedGoal extends Goal {

        protected final Thylacine thylacine;
        protected final Level level;
        @Nullable
        protected Thylacine partner;
        private int loveTime;
        private final double speedModifier;

        public ThylacineBreedGoal(Thylacine thylacine, double speedModifier) {
            this.thylacine = thylacine;
            this.level = thylacine.level();
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (!thylacine.isInLove()) {
                return false;
            } else {
                this.partner = this.getFreePartner();
                return partner != null;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return partner != null && partner.isAlive() && partner.isInLove() && loveTime < 60 && !partner.isPanicking();
        }

        @Override
        public void stop() {
            this.partner = null;
            this.loveTime = 0;
        }

        @Override
        public void tick() {
            if (partner != null) {
                this.thylacine.getLookControl().setLookAt(partner, 10.0F, (float) thylacine.getMaxHeadXRot());
                this.thylacine.getNavigation().moveTo(partner, speedModifier);
                this.loveTime++;
                if (loveTime >= this.adjustedTickDelay(60) && thylacine.distanceToSqr(partner) < 9.0) {
                    this.breed();
                }
            }
        }

        @Nullable
        private Thylacine getFreePartner() {
            List<? extends Thylacine> list = level.getNearbyEntities(Thylacine.class, TargetingConditions.forNonCombat().range(8.0).ignoreLineOfSight(), thylacine, thylacine.getBoundingBox().inflate(8.0));
            double maxValue = Double.MAX_VALUE;
            Thylacine partner = null;
            for (Thylacine partner1 : list) {
                if (thylacine.canMate(partner1) && !partner1.isPanicking() && thylacine.distanceToSqr(partner1) < maxValue) {
                    partner = partner1;
                    maxValue = thylacine.distanceToSqr(partner1);
                }
            }
            return partner;
        }

        protected void breed() {
            if (level instanceof ServerLevel serverLevel && partner != null) {
                this.thylacine.finalizeSpawnChildFromBreeding(serverLevel, partner, null);
            }
            if (level.getRandom().nextBoolean() && partner != null) {
                this.partner.setHasJoey(true);
                this.partner.setJoeyTime(1200);
            } else {
                this.thylacine.setHasJoey(true);
                this.thylacine.setJoeyTime(1200);
            }
        }
    }
}
