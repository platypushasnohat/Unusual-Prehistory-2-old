package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.goals.EepyGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.IdleAnimationGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricNearestAttackableTargetGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricWanderGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.update_1.CarnotaurusAttackGoal;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
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

import java.util.function.Predicate;

public class Carnotaurus extends PrehistoricMob implements PlushableMob {

    public int chargeCooldown = 100;
    public int roarCooldown = 100;
    public int biteCooldown = 0;
    public int headbuttCooldown = 0;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState chargeStartAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState chargeEndAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState headbuttAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState angryAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState roarAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState swimAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sniff1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sniff2AnimationState = new SmoothAnimationState();

    private boolean attackAlt = false;
    private boolean sniffAlt = false;

    private int startChargeTicks;
    private int stopChargeTicks;

    private final byte ANGRY = 39;

    public Carnotaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CarnotaurusAttackGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_CARNIVORE), false));
        this.goalSelector.addGoal(3, new PrehistoricWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new EepyGoal(this));
        this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 60, 1, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 40, 2, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 80, 3, true, 0.001F, this::canPlayIdles) {
            @Override
            public void tick() {
                super.tick();
                if (timer == 50) Carnotaurus.this.playSound(UP2SoundEvents.CARNOTAURUS_SNIFF.get(), 1.0F, Carnotaurus.this.getVoicePitch());
            }
        });
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new CarnotaurusTargetGoal<>(this, Monster.class, 60, true, true, entity -> !entity.getType().is(UP2EntityTags.CARNOTAURUS_IGNORES)));
        this.targetSelector.addGoal(2, new CarnotaurusTargetGoal<>(this, Player.class, 100, true, true, this::canAttack));
        this.targetSelector.addGoal(3, new CarnotaurusTargetGoal<>(this, LivingEntity.class, 200, true, true, entity -> entity.getType().is(UP2EntityTags.CARNOTAURUS_TARGETS)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 70.0D)
                .add(Attributes.ATTACK_DAMAGE, 9.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.STEP_HEIGHT, 1.5D);
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

    public void roar() {
        if (this.isAlive()) {
            this.level().broadcastEntityEvent(this, ANGRY);
            this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 4));
            this.gameEvent(GameEvent.ENTITY_ACTION);
        }
    }

    @Override
    public @NotNull ItemStack getPlushieItemStack() {
        return new ItemStack(UP2Blocks.CARNOTAURUS_PLUSHIE.get());
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_CARNIVORE);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 3 || this.getPose() == UP2Poses.STOP_CHARGING.get();
    }

    @Override
    public boolean refuseToLook() {
        return super.refuseToLook() || this.getPose() == UP2Poses.STOP_CHARGING.get();
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, -this.getBbHeight() * 0.4F, this.getBbWidth()).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public void setupAnimationStates() {
        this.angryAnimationState.animateWhen(this.canPlayAngryAnimation(), this.tickCount);
        this.idleAnimationState.animateWhen(this.getPose() != UP2Poses.ROARING.get() && this.getPose() != UP2Poses.START_CHARGING.get() && this.getPose() != UP2Poses.START_CHARGING.get() && !this.isInWaterOrBubble() && !this.isEepy(), this.tickCount);
        this.swimAnimationState.animateWhen(this.getPose() != UP2Poses.ROARING.get() && this.isInWaterOrBubble(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, this.tickCount);
        this.headbuttAnimationState.animateWhen(this.getPose() == UP2Poses.HEADBUTTING.get(), this.tickCount);
        this.chargeStartAnimationState.animateWhen(this.getPose() == UP2Poses.START_CHARGING.get(), this.tickCount);
        this.chargeEndAnimationState.animateWhen(this.getPose() == UP2Poses.STOP_CHARGING.get(), this.tickCount);
        this.roarAnimationState.animateWhen(this.getPose() == UP2Poses.ROARING.get(), this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.sniff1AnimationState.animateWhen(this.getIdleState() == 3 && !sniffAlt, this.tickCount);
        this.sniff2AnimationState.animateWhen(this.getIdleState() == 3 && sniffAlt, this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
    }

    private boolean canPlayAngryAnimation() {
        return this.isAggressive() && this.getPose() == Pose.STANDING;
    }

    private boolean canPlayIdles(Entity entity) {
        return !entity.isInWaterOrBubble();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 900 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 2) {
            return 800 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 3) {
            return 1000 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (chargeCooldown > 0) chargeCooldown--;
        if (roarCooldown > 0) roarCooldown--;
        if (headbuttCooldown > 0) headbuttCooldown--;
        if (biteCooldown > 0) biteCooldown--;
        if (startChargeTicks > 0) startChargeTicks--;
        if (stopChargeTicks > 0) stopChargeTicks--;
        if (startChargeTicks == 0 && this.getPose() == UP2Poses.START_CHARGING.get()) this.setPose(UP2Poses.CHARGING.get());
        if (stopChargeTicks == 0 && this.getPose() == UP2Poses.STOP_CHARGING.get()) this.setPose(Pose.STANDING);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (DATA_POSE.equals(key)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.attackAlt = this.getRandom().nextBoolean();
            }
            else if (this.getPose() == UP2Poses.START_CHARGING.get()) {
                this.startChargeTicks = 30;
            }
            else if (this.getPose() == UP2Poses.STOP_CHARGING.get()) {
                this.stopChargeTicks = 15;
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
    public void handleEntityEvent(byte id) {
        if (id == ANGRY) {
            this.roarEffect();
        } else {
            super.handleEntityEvent(id);
        }
    }

    private void roarEffect() {
        for (int i = 0; i < 10; ++i) {
            double d0 = this.getRandom().nextGaussian() * 0.02D;
            double d1 = this.getRandom().nextGaussian() * 0.02D;
            double d2 = this.getRandom().nextGaussian() * 0.02D;
            this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.CARNOTAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return UP2SoundEvents.CARNOTAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.CARNOTAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.CARNOTAURUS_STEP.get(), this.isBaby() ? 0.25F : 1.0F, this.isBaby() ? 1.2F : 0.9F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        Carnotaurus baby = UP2Entities.CARNOTAURUS.get().create(level);
        if (baby != null) {
            baby.setVariantRawId(this.inheritVariantFrom(mob, this.getRandom()));
        }
        return baby;
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/carnotaurus/carnotaurus_demon.png");
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.pickVariantForSpawn(level);
        return data;
    }

    // Goals
    private static class CarnotaurusTargetGoal<T extends LivingEntity> extends PrehistoricNearestAttackableTargetGoal<T> {

        public CarnotaurusTargetGoal(PrehistoricMob mob, Class<T> targetClass, int interval, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> predicate) {
            super(mob, targetClass, interval, mustSee, mustReach, predicate);
        }

        @Override
        public boolean canUse() {
            if (prehistoricMob.isTame() || prehistoricMob.isPacified() || prehistoricMob.isEepy()) {
                return false;
            }
            else if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
                return false;
            }
            else {
                this.findTarget();
                return this.target != null;
            }
        }
    }
}
