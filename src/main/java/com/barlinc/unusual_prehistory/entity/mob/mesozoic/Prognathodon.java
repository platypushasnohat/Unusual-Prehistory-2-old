package com.barlinc.unusual_prehistory.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.*;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothWaterNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Prognathodon extends PrehistoricAquaticMob implements LeapingMob {
    // region data
    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Prognathodon.class, EntityDataSerializers.BOOLEAN);

    private static final float MAX_TILT = 45.0F;
    private static final float MAX_ROLL = 15.0F;
    private static final float ROLL_PER_YAW = 1.0F;
    private static final float MAX_TAIL_YAW = 30.0F;
    private static final float TAIL_YAW_MULITPLIER = 0.2F;

    private static final int IDLE_TONGUE = 1;
    private static final int IDLE_YAWN = 2;
    private static final int IDLE_NIP = 3;

    private int biteCooldown = 0;

    public final SmoothAnimationState leapAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState tongueAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState nip1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState nip2AnimationState = new SmoothAnimationState(1.0F);

    private boolean attackAlt = false;
    private boolean nipAlt = false;

    public Prognathodon(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 45, 5, 0.02F, 0.1F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 5);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0F)
                .add(Attributes.ATTACK_DAMAGE, 11.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LEAPING, false);
    }

    @Override
    public void setLeaping(boolean leaping) {
        this.entityData.set(LEAPING, leaping);
    }
    @Override
    public boolean isLeaping() {
        return entityData.get(LEAPING);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (IDLE_STATE.equals(accessor)) {
            if (this.getIdleState() == IDLE_NIP) {
                this.nipAlt = this.getRandom().nextBoolean();
            }
        }
    }
    // endregion

    // region behavior
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 2.0D, 16, 8));
        this.goalSelector.addGoal(3, new PrognathodonAttackGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_CARNIVORE), false));
        this.goalSelector.addGoal(5, new PrehistoricSwimGoal(this, 1.0D, 20, 30, 15, 3, true));
        this.goalSelector.addGoal(6, new AquaticLeapGoal(this, 10, 1.0D, 0.9D));
        this.goalSelector.addGoal(7, new IdleAnimationGoal(this, 40, 1, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(7, new IdleAnimationGoal(this, 60, 2, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(7, new IdleAnimationGoal(this, 20, 3, false, 0.001F, this::canPlayIdles));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, false, entity -> entity.getType().is(UP2EntityTags.PROGNATHODON_FIGHT_TARGETS)) {
            @Override
            public boolean canUse() {
                return super.canUse() && Prognathodon.this.isInWaterOrBubble();
            }
        });
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 400, true, true, entity -> entity.getType().is(UP2EntityTags.PROGNATHODON_TARGETS)) {
            @Override
            public boolean canUse() {
                return super.canUse() && Prognathodon.this.isInWaterOrBubble();
            }
        });
        this.targetSelector.addGoal(3, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 400, true, true, this::canAttack) {
            @Override
            public boolean canUse() {
                return super.canUse() && Prognathodon.this.isInWaterOrBubble();
            }
        });
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothWaterNavigation(this, level, true);
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean isPushable() {
        return !this.isInWater();
    }

    @Override
    public boolean shouldFlop() {
        return false;
    }

    @Override
    protected boolean shouldUseShallowNavigation() {
        return true;
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.25F : 1.0F;
    }

    @Override
    protected void handleAirSupply(int airSupply) {
    }

    @Override
    public void tick() {
        super.tick();
        this.tickRotations(MAX_TILT, MAX_ROLL, ROLL_PER_YAW);
        this.tickTailYaw(MAX_TAIL_YAW, TAIL_YAW_MULITPLIER);
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (!this.level().isClientSide) {
            if (this.biteCooldown > 0) {
                this.biteCooldown--;
            }
        }
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(2);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_CARNIVORE);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return UP2Entities.PROGNATHODON.get().create(level);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return UP2SoundEvents.PROGNATHODON_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.PROGNATHODON_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.PROGNATHODON_IDLE.get();
    }

    @Override
    public float getSoundVolume() {
        return this.isBaby() ? 1.0F : 1.5F;
    }
    // endregion

    // region animations
    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isLeaping(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isLeaping(), this.tickCount);
        this.leapAnimationState.animateWhen(this.isLeaping(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !this.attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && this.attackAlt, this.tickCount);
        this.tongueAnimationState.animateWhen(this.getIdleState() == IDLE_TONGUE, this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == IDLE_YAWN, this.tickCount);
        this.nip1AnimationState.animateWhen(this.getIdleState() == IDLE_NIP && !this.nipAlt, this.tickCount);
        this.nip2AnimationState.animateWhen(this.getIdleState() == IDLE_NIP && this.nipAlt, this.tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float) Mth.length(this.getX() - this.xo, this.isInWater() ? this.getY() - this.yo : 0.0D, this.getZ() - this.zo);
        if (this.isBaby()) {
            this.updateWalkAnimation(f * 0.5F);
        } else {
            this.updateWalkAnimation(f);
        }
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f = Math.min(partialTick * 20.0F, 1.0F);
        this.walkAnimation.update(f, 0.4F);
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == IDLE_TONGUE) {
            return 300 + this.getRandom().nextInt(400);
        }
        else if (idleState == IDLE_YAWN) {
            return 700 + this.getRandom().nextInt(1200);
        }
        else if (idleState == IDLE_NIP) {
            return 800 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    public boolean canPlayIdles(Entity entity) {
        return entity.isInWaterOrBubble();
    }
    // endregion

    // region goals
    private static class PrognathodonAttackGoal extends AttackGoal {

        private final Prognathodon prognathodon;

        public PrognathodonAttackGoal(Prognathodon prognathodon) {
            super(prognathodon);
            this.prognathodon = prognathodon;
        }

        @Override
        public void tick() {
            LivingEntity target = this.prognathodon.getTarget();
            if (target != null) {
                double distance = this.prognathodon.distanceToSqr(target);
                int attackState = this.prognathodon.getAttackState();

                this.lookAtTarget(target, 30.0F, 30.0F);

                if (attackState == 1) {
                    this.tickBite();
                }
                else {
                    if (distance <= this.getAttackReachSqr(target) && this.prognathodon.biteCooldown == 0) {
                        this.prognathodon.setAttackState(1);
                    } else {
                        this.prognathodon.getNavigation().moveTo(target, 1.9D);
                    }
                }
            }
        }

        protected void tickBite() {
            this.timer++;
            if (this.timer == 1) {
                this.prognathodon.attackAlt = this.prognathodon.getRandom().nextBoolean();
                this.prognathodon.setPose(UP2Poses.ATTACKING.get());
                this.prognathodon.playSound(UP2SoundEvents.PROGNATHODON_ATTACK.get(), 1.0F, this.prognathodon.getVoicePitch());
            }
            if (this.timer == 9) {
                this.biteNearbyEntities();
            }
            if (this.timer > 20) {
                this.timer = 0;
                this.prognathodon.setPose(Pose.STANDING);
                this.prognathodon.setAttackState(0);
                this.prognathodon.biteCooldown = 7;
            }
        }

        private void biteNearbyEntities() {
            List<LivingEntity> nearbyEntities = this.prognathodon.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this.prognathodon, this.prognathodon.getBoundingBox().inflate(3.5D));
            if (!nearbyEntities.isEmpty()) {
                nearbyEntities.stream().filter(entity -> entity != this.prognathodon).limit(3).forEach(entity -> {
                    entity.hurt(entity.damageSources().mobAttack(this.prognathodon), (float) this.prognathodon.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    entity.knockback(1.5F, Mth.sin(this.prognathodon.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.prognathodon.getYRot() * ((float) Math.PI / 180F)));
                    if (entity.isDamageSourceBlocked(this.prognathodon.damageSources().mobAttack(this.prognathodon)) && entity instanceof Player player) {
                        player.disableShield();
                    }
                    this.prognathodon.swing(InteractionHand.MAIN_HAND);
                });
            }
        }
    }
    // endregion
}
