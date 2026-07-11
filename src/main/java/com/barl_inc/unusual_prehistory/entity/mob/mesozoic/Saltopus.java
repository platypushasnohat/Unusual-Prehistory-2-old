package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricPanicGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricWanderGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.RandomLeapGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.LeapingMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Saltopus extends PrehistoricMob implements LeapingMob {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Saltopus.class, EntityDataSerializers.BOOLEAN);

    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int jumpDelayTicks;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState jumpAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState leapAnimationState = new SmoothAnimationState();

    public Saltopus(EntityType<? extends Saltopus> entityType, Level level) {
        super(entityType, level);
        this.jumpControl = new SaltopusJumpControl(this);
        this.moveControl = new SaltopusMoveControl(this);
        this.setSpeedModifier(0.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 2.2D));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, Ingredient.of(UP2ItemTags.DIET_INSECTIVORE), false));
        this.goalSelector.addGoal(5, new RandomLeapGoal(this, 80, 16, 1.0F));
        this.goalSelector.addGoal(7, new PrehistoricWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.STEP_HEIGHT, 1.5D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_INSECTIVORE);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 1) {
            this.spawnSprintParticle();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected float getJumpPower() {
        float power;
        if (horizontalCollision || this.getMoveControl().hasWanted() && this.isYRequiringJump(this.getMoveControl().getWantedY())) {
            power = 0.7F;
        }
        else {
            Path path = this.getNavigation().getPath();
            if (path != null && !path.isDone() && this.isYRequiringJump(path.getNextEntityPos(this.self()).y)) {
                power = 0.7F;
            }
            else {
                power = 0.4F;
            }
        }
        return this.getJumpPower(power / 0.42F);
    }

    @Override
    public void jumpFromGround() {
        super.jumpFromGround();
        double speedModifier = this.getMoveControl().getSpeedModifier();
        if (speedModifier > 0.0D) {
            double horizontalDistanceSqr = this.getDeltaMovement().horizontalDistanceSqr();
            if (horizontalDistanceSqr < 0.01D) {
                this.moveRelative(0.4F, new Vec3(0.0D, 0.0D, 1.0D));
            }
        }

        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 1);
        }
    }

    private boolean isYRequiringJump(double targetY) {
        return targetY > this.getY() + 0.5D;
    }

    public void setSpeedModifier(double speedModifier) {
        this.getNavigation().setSpeedModifier(speedModifier);
        this.getMoveControl().setWantedPosition(this.getMoveControl().getWantedX(), this.getMoveControl().getWantedY(), this.getMoveControl().getWantedZ(), speedModifier);
    }

    @Override
    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        if (jumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }
    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    private void enableJumpControl() {
        ((SaltopusJumpControl) this.getJumpControl()).setCanJump(true);
    }

    private void disableJumpControl() {
        ((SaltopusJumpControl) this.getJumpControl()).setCanJump(false);
    }

    private void facePoint(double x, double z) {
        this.setYRot((float) (Mth.atan2(z - this.getZ(), x - this.getX()) * 180.0F / (float) Math.PI) - 90.0F);
    }

    private void setLandingDelay() {
        if (this.getMoveControl().getSpeedModifier() < 2.0D) {
            this.jumpDelayTicks = 9;
        } else {
            this.jumpDelayTicks = 5;
        }
    }

    private void checkLandingDelay() {
        this.setLandingDelay();
        this.disableJumpControl();
    }

    @Override
    public void customServerAiStep() {
        if (jumpDelayTicks > 0) {
            this.jumpDelayTicks--;
        }

        if (this.onGround()) {
            if (!wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }
            SaltopusJumpControl jumpControl = (SaltopusJumpControl) this.getJumpControl();
            if (!jumpControl.wantsToJump()) {
                if (this.getMoveControl().hasWanted() && jumpDelayTicks == 0) {
                    Path path = this.getNavigation().getPath();
                    Vec3 vec3 = new Vec3(this.getMoveControl().getWantedX(), this.getMoveControl().getWantedY(), this.getMoveControl().getWantedZ());
                    if (path != null && !path.isDone()) {
                        vec3 = path.getNextEntityPos(this);
                    }
                    this.facePoint(vec3.x, vec3.z);
                    this.startJumping();
                }
            } else if (!jumpControl.canJump()) {
                this.enableJumpControl();
            }
        }
        this.wasOnGround = this.onGround();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (jumpTicks != jumpDuration) {
            this.jumpTicks++;
        } else if (jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    @Override
    public boolean canTrample(@NotNull BlockState state, @NotNull BlockPos pos, float fallDistance) {
        return false;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void tick() {
        super.tick();
        if (this.isLeaping() && (this.onGround() || this.isInFluidType())) {
            if (this.onGround() && !this.level().isClientSide) {
                this.level().playSound(null, this, UP2SoundEvents.GASTRIC_BROODING_FROG_STEP.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
            this.setLeaping(false);
        }

        if (this.isLeaping()) {
            Vec3 motion = this.getDeltaMovement();
            if (motion.horizontalDistanceSqr() > 1.0E-6D) {
                float yaw = -((float) Mth.atan2(motion.x, motion.z)) * Mth.RAD_TO_DEG;
                this.setYRot(yaw);
                this.setYHeadRot(yaw);
                this.setYBodyRot(yaw);
                this.yRotO = yaw;
                this.yHeadRotO = yaw;
                this.yBodyRotO = yaw;
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.onGround(), tickCount);
        this.jumpAnimationState.animateWhen(!this.onGround() && !this.isLeaping(), tickCount);
        this.leapAnimationState.animateWhen(!this.onGround() && this.isLeaping(), tickCount);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.SALTOPUS.get().create(level);
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, -this.getBbHeight() * 0.35F, this.getBbWidth() * 1.3F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public void travel(@NotNull Vec3 vec3) {
        if (this.refuseToMove() && this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            vec3 = vec3.multiply(0.0, 1.0, 0.0);
        }
        super.travel(vec3);
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

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.DROMAEOSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.DROMAEOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.DROMAEOSAURUS_DEATH.get();
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.RABBIT_JUMP;
    }

    private static class SaltopusJumpControl extends JumpControl {

        private final Saltopus saltopus;
        private boolean canJump;

        public SaltopusJumpControl(Saltopus saltopus) {
            super(saltopus);
            this.saltopus = saltopus;
        }

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        public boolean wantsToJump() {
            return jump;
        }

        public boolean canJump() {
            return canJump;
        }

        public void setCanJump(boolean canJump) {
            this.canJump = canJump;
        }

        @Override
        public void tick() {
            if (jump) {
                this.saltopus.startJumping();
                this.jump = false;
            }
        }
    }

    private static class SaltopusMoveControl extends MoveControl {

        private final Saltopus saltopus;
        private double nextJumpSpeed;

        public SaltopusMoveControl(Saltopus saltopus) {
            super(saltopus);
            this.saltopus = saltopus;
        }

        @Override
        public void tick() {
            if (saltopus.onGround() && !saltopus.jumping && !((SaltopusJumpControl) saltopus.getJumpControl()).wantsToJump()) {
                this.saltopus.setSpeedModifier(0.0D);
            }
            else if (this.hasWanted() || operation == Operation.JUMPING) {
                this.saltopus.setSpeedModifier(nextJumpSpeed);
            }
            super.tick();
        }

        @Override
        public void setWantedPosition(double x, double y, double z, double speed) {
            if (saltopus.isInWater()) {
                speed = 1.5D;
            }
            super.setWantedPosition(x, y, z, speed);
            if (speed > 0.0D) {
                this.nextJumpSpeed = speed;
            }
        }
    }
}
