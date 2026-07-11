package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.entity.accessor.LivingEntityAccessor;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.ai.goals.update_6.LorrainosaurusAttackGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
import com.barl_inc.unusual_prehistory.entity.utils.GrabbingMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Lorrainosaurus extends PrehistoricAmphibiousMob implements GrabbingMob {

    private static final EntityDataAccessor<Integer> HELD_MOB_ID = SynchedEntityData.defineId(Lorrainosaurus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GRAB_TIME = SynchedEntityData.defineId(Lorrainosaurus.class, EntityDataSerializers.INT);

    private static final float MAX_TILT = 45.0F;
    private static final float MAX_ROLL = 15.0F;
    private static final float ROLL_PER_YAW = 1.0F;

    private static final int IDLE_YAWN = 1;
    private static final int IDLE_NIP = 2;

    public int biteCooldown = 0;
    public int grabCooldown = 0;
    private int grabTicks;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState nip1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState nip2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState grabStartAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState grabAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState aggroAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState swimIdleAnimationState = new SmoothAnimationState();

    private boolean attackAlt = false;
    private boolean nipAlt = false;

    private int grabStartTicks;

    public Lorrainosaurus(EntityType<? extends PrehistoricAmphibiousMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15F)
                .add(Attributes.ATTACK_DAMAGE, 10.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                .add(Attributes.STEP_HEIGHT, 1.1D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new EnterWaterGoal(this, 1.0D, 100, true));
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 2.0D, 16, 8));
        this.goalSelector.addGoal(2, new LorrainosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_CARNIVORE), false));
        this.goalSelector.addGoal(4, new PrehistoricSwimGoal(this, 1.0D, 40, 30, 15, 3, true));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 60, IDLE_YAWN, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(6, new IdleAnimationGoal(this, 20, IDLE_NIP, false, 0.001F, this::canPlayIdles));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 100, true, true, entity -> entity.getType().is(UP2EntityTags.LORRAINOSAURUS_TARGETS)));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 100, true, true, this::canAttack));
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new PrehistoricMoveControl(this);
            this.lookControl = new PrehistoricLookControl(this);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 5, 0.8F);
            this.lookControl = new PrehistoricSwimmingLookControl(this, 5);
            this.isLandNavigator = false;
        }
    }

    @Override
    public boolean isPushable() {
        return this.isBaby();
    }

    @Override
    public void remove(RemovalReason removalReason) {
        super.remove(removalReason);
        if (this.getHeldMobId() != -1) {
            this.setHeldMobId(-1);
        }
    }

    @Override
    public void tick() {
        super.tick();
        final boolean ground = !this.isInWaterOrBubble();
        if (!ground && isLandNavigator) {
            this.switchNavigator(false);
        }
        if (ground && !isLandNavigator) {
            this.switchNavigator(true);
        }

        if ((this.getPose() == UP2Poses.GRAB_START.get() || this.getPose() == UP2Poses.GRABBING.get()) && this.getHeldMobId() != -1) {
            this.positionHeldMob();
        }

        this.tickRotations(MAX_TILT, MAX_ROLL, ROLL_PER_YAW);
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), tickCount);
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble(), tickCount);
        this.aggroAnimationState.animateWhen(this.isAggressive() && !this.isInAttackPose(), tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !attackAlt, tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && attackAlt, tickCount);
        this.grabStartAnimationState.animateWhen(this.getPose() == UP2Poses.GRAB_START.get(), tickCount);
        this.grabAnimationState.animateWhen(this.getPose() == UP2Poses.GRABBING.get(), tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == IDLE_YAWN, tickCount);
        this.nip1AnimationState.animateWhen(this.getIdleState() == IDLE_NIP && !nipAlt, tickCount);
        this.nip2AnimationState.animateWhen(this.getIdleState() == IDLE_NIP && nipAlt, tickCount);
    }

    private boolean isInAttackPose() {
        return this.getPose() == UP2Poses.GRAB_START.get() || this.getPose() == UP2Poses.ATTACKING.get() || this.getPose() == UP2Poses.GRABBING.get();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == IDLE_YAWN) {
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

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (DATA_POSE.equals(accessor)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.attackAlt = this.getRandom().nextBoolean();
            }
            else if (this.getPose() == UP2Poses.GRAB_START.get()) {
                this.grabStartTicks = 20;
            }
            else if (this.getPose() == UP2Poses.GRABBING.get()) {
                this.grabTicks = this.getGrabTime();
            }
        }
        if (IDLE_STATE.equals(accessor)) {
            if (this.getIdleState() == 2) {
                this.nipAlt = this.getRandom().nextBoolean();
            }
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (!this.level().isClientSide) {
            if (biteCooldown > 0) {
                this.biteCooldown--;
            }
            if (grabCooldown > 0) {
                this.grabCooldown--;
            }
        }
        if (grabTicks > 0) {
            this.grabTicks--;
        }
        if (grabStartTicks > 0) {
            this.grabStartTicks--;
        }
        if (grabStartTicks == 0 && this.getPose() == UP2Poses.GRAB_START.get()) {
            if (this.getHeldMobId() != -1) {
                this.setPose(UP2Poses.GRABBING.get());
            } else {
                this.setPose(Pose.STANDING);
            }
        }
    }

    public boolean canPickUpTarget(LivingEntity target) {
        if (((LivingEntityAccessor) target).unusualPrehistory$isBeingGrabbed()) {
            return false;
        }
        if (target.getType().is(UP2EntityTags.LORRAINOSAURUS_CANT_GRAB)) {
            return false;
        }
        return (target.getBbWidth() < this.getBbWidth() && target.getBbHeight() < this.getBbHeight()) || target.getType().is(UP2EntityTags.LORRAINOSAURUS_CAN_GRAB);
    }

    private void positionHeldMob() {
        Entity entity = this.level().getEntity(this.getHeldMobId());
        if (entity != null) {
            if (grabTicks < this.getGrabTime()) {
                Vec3 heldPos = this.position().add(new Vec3(0.0F, 0.1F, 3.0F).yRot(-yBodyRot * ((float) Math.PI / 180F)));
                Vec3 minus = new Vec3(heldPos.x - entity.getX(), heldPos.y - entity.getY(), heldPos.z - entity.getZ());
                entity.setDeltaMovement(minus);
                entity.fallDistance = 0.0F;
                entity.setYRot(0.0F);
                entity.setYBodyRot(0.0F);
                entity.setYHeadRot(0.0F);
                entity.setXRot(0.0F);
                if (grabTicks < (this.getGrabTime() * 0.8F) && grabTicks % 30 == 0) {
                    entity.hurt(damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.25F);
                }
            }
        } else {
            this.setHeldMobId(-1);
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

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HELD_MOB_ID, -1);
        builder.define(GRAB_TIME, 150 + this.getRandom().nextInt(50));
    }

    @Override
    public void setHeldMobId(int id) {
        int oldId = this.getHeldMobId();
        if (oldId != -1) {
            Entity oldEntity = this.level().getEntity(oldId);
            if (oldEntity instanceof LivingEntity living) {
                ((LivingEntityAccessor) living).unusualPrehistory$setBeingGrabbed(false);
            }
        }
        this.entityData.set(HELD_MOB_ID, id);
        if (id != -1) {
            Entity entity = this.level().getEntity(id);
            if (entity instanceof LivingEntity living) {
                ((LivingEntityAccessor) living).unusualPrehistory$setBeingGrabbed(true);
            }
        }
    }
    @Override
    public int getHeldMobId() {
        return entityData.get(HELD_MOB_ID);
    }

    public void setGrabTime(int grabTime) {
        this.entityData.set(GRAB_TIME, grabTime);
    }
    public int getGrabTime() {
        return entityData.get(GRAB_TIME);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return UP2Entities.LORRAINOSAURUS.get().create(level);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return UP2SoundEvents.LORRAINOSAURUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.LORRAINOSAURUS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.LORRAINOSAURUS_IDLE.get();
    }
}
