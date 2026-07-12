package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.PlushableMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class Majungasaurus extends PrehistoricMob implements PlushableMob {

    private static final EntityDataAccessor<Boolean> CAMO = SynchedEntityData.defineId(Majungasaurus.class, EntityDataSerializers.BOOLEAN);

    private int attackCooldown = 0;
    private int camoCooldown = 0;

    public float prevCamoProgress;
    public float camoProgress;

    public float prevAngryProgress;
    public float angryProgress;

    public final SmoothAnimationState camoIdleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState eyesAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState swimAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState sniff1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState sniff2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState(1.0F);

    private boolean attackAlt = false;
    private boolean sniffAlt = false;

    public Majungasaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 1.7D, 10, 4));
        this.goalSelector.addGoal(2, new MajungasaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_CARNIVORE), false));
        this.goalSelector.addGoal(4, new PrehistoricWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new MajungasaurusAvoidEntityGoal<>(this, Majungasaurus.class, this::avoidsParents));
        this.goalSelector.addGoal(6, new MajungasaurusAvoidEntityGoal<>(this, LivingEntity.class, entity -> entity.getType().is(UP2EntityTags.MAJUNGASAURUS_AVOIDS)));
        this.goalSelector.addGoal(7, new EepyGoal(this));
        this.goalSelector.addGoal(8, new IdleAnimationGoal(this, 60, 1, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(8, new IdleAnimationGoal(this, 80, 2, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 80, 3, true, 0.001F, this::canPlayIdles) {
            @Override
            public void tick() {
                super.tick();
                if (timer == 50) Majungasaurus.this.playSound(UP2SoundEvents.MAJUNGASAURUS_SNIFF.get(), 1.0F, Majungasaurus.this.getVoicePitch());
            }
        });
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, Majungasaurus.class, 100, true, true, this::canCannibalize));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, true, entity -> entity.getType().is(UP2EntityTags.MAJUNGASAURUS_TARGETS)));
        this.targetSelector.addGoal(3, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 100, true, true, this::attacksPlayers));
        this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 32.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                .add(Attributes.ARMOR, 4.0D)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource source, float amount) {
        if (this.isCamo()) {
            this.setCamo(false);
            this.camoCooldown();
        }
        super.actuallyHurt(source, amount);
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
        return stack.is(UP2ItemTags.DIET_CARNIVORE);
    }

    public float getCamoProgress(float partialTicks) {
        return (prevCamoProgress + (camoProgress - prevCamoProgress) * partialTicks) * 0.2F;
    }

    public float getAngryProgress(float partialTicks) {
        return (prevAngryProgress + (angryProgress - prevAngryProgress) * partialTicks) * 0.2F;
    }

    public boolean canCannibalize(LivingEntity entity) {
        return this.canAttack(entity) && (entity.getHealth() < entity.getMaxHealth() * 0.5F || entity.isBaby());
    }

    public boolean avoidsParents(LivingEntity entity) {
        Majungasaurus majungasaurus = (Majungasaurus) entity;
        return this.isBaby() && !entity.isBaby() && !majungasaurus.isCamo();
    }

    protected boolean attacksPlayers(LivingEntity entity) {
        return this.canAttack(entity) && this.level().isNight();
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 3;
    }

    @Override
    public boolean isEepyTime() {
        return this.level().isDay();
    }

    @Override
    public @NotNull ItemStack getPlushieItemStack() {
        return new ItemStack(UP2Blocks.MAJUNGASAURUS_PLUSHIE.get());
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(-0.2D, -this.getBbHeight() * 0.2F, this.getBbWidth() * 1.6F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public void tick () {
        super.tick();

        this.prevCamoProgress = camoProgress;
        this.prevAngryProgress = angryProgress;

        if (this.isCamo() && camoProgress < 5F) {
            this.camoProgress++;
        }
        if (!this.isCamo() && camoProgress > 0F) {
            this.camoProgress--;
        }

        if (this.isAggressive() && angryProgress < 5F) {
            this.angryProgress++;
        }
        if (!this.isAggressive() && angryProgress > 0F) {
            this.angryProgress--;
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isEepy() && !this.isCamo(), this.tickCount);
        this.camoIdleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isEepy() && this.isCamo(), this.tickCount);
        this.eyesAnimationState.animateWhen(!this.isAggressive() && !this.isEepy(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.sniff1AnimationState.animateWhen(this.getIdleState() == 3 && !sniffAlt, this.tickCount);
        this.sniff2AnimationState.animateWhen(this.getIdleState() == 3 && sniffAlt, this.tickCount);
    }

    private boolean canPlayIdles(Entity entity) {
        return !entity.isInWaterOrBubble();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 850 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 2) {
            return 900 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 3) {
            return 1000 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (DATA_POSE.equals(key)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.attackAlt = this.getRandom().nextBoolean();
            }
        }
        if (IDLE_STATE.equals(key)) {
            if (this.getIdleState() == 3) {
                this.sniffAlt = this.getRandom().nextBoolean();
            }
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (camoCooldown > 0) camoCooldown--;
        if (attackCooldown > 0) attackCooldown--;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CAMO, false);
    }

    public boolean isCamo() {
        return this.entityData.get(CAMO);
    }
    public void setCamo(boolean camo) {
        this.entityData.set(CAMO, camo);
    }

    public void camoCooldown() {
        this.camoCooldown = 40 + this.getRandom().nextInt(40);
    }

    @Override
    public boolean canPlayAmbientSound() {
        return !this.isCamo() && super.canPlayAmbientSound();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.MAJUNGASAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.MAJUNGASAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.MAJUNGASAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
       if (!this.isCamo()) {
           this.playSound(UP2SoundEvents.MAJUNGASAURUS_STEP.get(), 1.0F, 0.9F);
       }
    }

    @Override
    protected @NotNull MovementEmission getMovementEmission() {
        return this.isCamo() ? MovementEmission.NONE : super.getMovementEmission();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        Majungasaurus baby = UP2Entities.MAJUNGASAURUS.get().create(level);
        if (baby != null) {
            baby.setVariantRawId(this.inheritVariantFrom(mob, this.getRandom()));
        }
        return baby;
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/majungasaurus/majungasaurus.png");
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.pickVariantForSpawn(level);
        return data;
    }

    // Goals
    private static class MajungasaurusAttackGoal extends AttackGoal {

        protected final Majungasaurus majungasaurus;

        public MajungasaurusAttackGoal(Majungasaurus majungasaurus) {
            super(majungasaurus);
            this.majungasaurus = majungasaurus;
        }

        @Override
        public void start() {
            super.start();
            this.majungasaurus.setCamo(false);
        }

        @Override
        public void stop() {
            super.stop();
            this.majungasaurus.setCamo(false);
            this.majungasaurus.camoCooldown();
        }

        @Override
        public void tick() {
            LivingEntity target = majungasaurus.getTarget();
            if (target != null) {
                double distance = majungasaurus.distanceToSqr(target);
                this.majungasaurus.lookAt(target, 30F, 30F);
                this.majungasaurus.getLookControl().setLookAt(target, 30F, 30F);

                if (majungasaurus.getAttackState() == 1) {
                    this.tickAttack(target);
                    this.majungasaurus.getNavigation().stop();
                } else {
                    this.majungasaurus.setCamo(distance > 25 && majungasaurus.camoCooldown == 0);
                    if (distance <= this.getAttackReachSqr(target) && majungasaurus.attackCooldown == 0) {
                        this.majungasaurus.setAttackState(1);
                    }
                    this.majungasaurus.getNavigation().moveTo(target, majungasaurus.isCamo() ? 1.0D : 2.0D);
                }
            }
        }

        protected void tickAttack(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.majungasaurus.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 8) {
                this.majungasaurus.playSound(UP2SoundEvents.MAJUNGASAURUS_ATTACK.get(), 1.0F, 0.9F + majungasaurus.getRandom().nextFloat() * 0.25F);
            }
            if (timer == 11) {
                if (this.isInAttackRange(target, 1.75D)) {
                    this.majungasaurus.doHurtTarget(target);
                    this.majungasaurus.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 17) {
                this.timer = 0;
                this.majungasaurus.setPose(Pose.STANDING);
                this.majungasaurus.attackCooldown = 6;
                this.majungasaurus.camoCooldown();
                this.majungasaurus.setAttackState(0);
            }
        }
    }

    private static class MajungasaurusAvoidEntityGoal<T extends LivingEntity> extends PrehistoricAvoidEntityGoal<T> {

        private final Majungasaurus majungasaurus;

        public MajungasaurusAvoidEntityGoal(Majungasaurus mob, Class<T> classToAvoid, Predicate<LivingEntity> predicateOnAvoid) {
            super(mob, classToAvoid, 12.0F, 1.0D, predicateOnAvoid);
            this.majungasaurus = mob;
        }

        @Override
        public void start() {
            if (majungasaurus.camoCooldown == 0) {
                this.majungasaurus.setCamo(true);
            }
            this.majungasaurus.setRunning(true);
            this.majungasaurus.getNavigation().moveTo(posX, posY, posZ, majungasaurus.isCamo() ? 1.0D : 2.0D);
        }

        @Override
        public void stop() {
            super.stop();
            this.majungasaurus.setCamo(false);
            this.majungasaurus.camoCooldown();
        }
    }
}
