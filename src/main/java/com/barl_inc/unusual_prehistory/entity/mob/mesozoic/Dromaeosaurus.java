package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.EepyGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricNearestAttackableTargetGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricPanicGoal;
import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothGroundNavigation;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.entity.variant.UP2VariantMob;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Dromaeosaurus extends PrehistoricMob implements UP2VariantMob {

    private static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Dromaeosaurus.class, EntityDataSerializers.STRING);

    public int leapCooldown = 120 + this.getRandom().nextInt(120);

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState fallAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState eepyAnimationState = new SmoothAnimationState(0.25F);

    public Dromaeosaurus(EntityType<? extends Dromaeosaurus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new EepyGoal(this));
        this.goalSelector.addGoal(3, new DromaeosaurusLeapGoal(this));
        this.goalSelector.addGoal(4, new DromaeosaurusAttackGoal(this));
        this.goalSelector.addGoal(5, new OpenDoorGoal(this, false));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, Ingredient.of(UP2ItemTags.DIET_CARNIVORE), false));
        this.goalSelector.addGoal(7, new DromaeosaurusRunGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, false, entity -> entity.getType().is(UP2EntityTags.DROMAEOSAURUS_TARGETS)) {
            @Override
            public boolean canUse(){
                return super.canUse() && !Dromaeosaurus.this.isEepy() && (Dromaeosaurus.this.level().isDay() || Dromaeosaurus.this.level().dimensionType().fixedTime().isPresent());
            }

            @Override
            public boolean canContinueToUse(){
                return super.canContinueToUse() && !Dromaeosaurus.this.isEepy() && (Dromaeosaurus.this.level().isDay() || Dromaeosaurus.this.level().dimensionType().fixedTime().isPresent());
            }
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.34F)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, this.defaultVariant().location().toString());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        this.saveVariant(compoundTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.loadVariant(compoundTag);
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/dromaeosaurus/dromaeosaurus_yellow.png");
    }

    @Override
    public String getVariantRawId() {
        return this.entityData.get(VARIANT);
    }
    @Override
    public void setVariantRawId(String id) {
        this.entityData.set(VARIANT, id);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        SmoothGroundNavigation navigation = new SmoothGroundNavigation(this, level);
        navigation.setCanOpenDoors(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_CARNIVORE);
    }

    @Override
    public void tick () {
        super.tick();
        this.setSprinting(this.getDeltaMovement().horizontalDistance() > 0.05D);
        if (leapCooldown > 0) leapCooldown--;
    }

    @Override
    public boolean isEepyTime() {
        return this.level().isNight() && this.getHealth() > this.getMaxHealth() * 0.5F && !this.isInWater() && this.onGround() && this.getLastHurtByMob() == null && this.getTarget() == null && !this.isLeashed();
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy(), this.tickCount);
        this.fallAnimationState.animateWhen(!this.onGround() && !this.isInWaterOrBubble() && !this.onClimbable() && !this.isPassenger() && !this.isEepy(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        Dromaeosaurus baby = UP2Entities.DROMAEOSAURUS.get().create(level);
        if (baby != null) {
            baby.setVariantRawId(this.inheritVariantFrom(mob, this.getRandom()));
        }
        return baby;
    }
    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypes.FALL);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
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

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isEepy() ? UP2SoundEvents.DROMAEOSAURUS_EEPY.get() : UP2SoundEvents.DROMAEOSAURUS_IDLE.get();
    }

    @Override
    public boolean canPlayAmbientSound() {
        return true;
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

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.pickVariantForSpawn(level);
        return data;
    }

    // Goals
    private static class DromaeosaurusAttackGoal extends AttackGoal {

        protected final Dromaeosaurus dromaeosaurus;

        public DromaeosaurusAttackGoal(Dromaeosaurus dromaeosaurus) {
            super(dromaeosaurus);
            this.dromaeosaurus = dromaeosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.dromaeosaurus.getHealth() >= this.dromaeosaurus.getMaxHealth() * 0.5F;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.dromaeosaurus.getHealth() >= this.dromaeosaurus.getMaxHealth() * 0.5F;
        }

        @Override
        public void tick() {
            LivingEntity target = this.dromaeosaurus.getTarget();
            if (target != null) {
                double distance = this.dromaeosaurus.distanceToSqr(target);
                this.dromaeosaurus.getLookControl().setLookAt(target, 30F, 30F);
                this.dromaeosaurus.getNavigation().moveTo(target, 1.0D);
                if (this.dromaeosaurus.getAttackState() == 1) {
                    this.tickAttack(target);
                } else if (distance <= this.getAttackReachSqr(target, 1.75D)) {
                    this.dromaeosaurus.setAttackState(1);
                }
            }
        }

        private void tickAttack(LivingEntity target) {
            this.timer++;
            if (timer == 1) dromaeosaurus.setPose(UP2Poses.ATTACKING.get());
            if (timer == 6) {
                if (this.isInAttackRange(target, 1.5D)) {
                    this.dromaeosaurus.doHurtTarget(target);
                    this.dromaeosaurus.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (timer > 15) {
                this.timer = 0;
                this.dromaeosaurus.setPose(Pose.STANDING);
                this.dromaeosaurus.setAttackState(0);
            }
        }
    }

    private static class DromaeosaurusRunGoal extends Goal {

        protected final Dromaeosaurus dromaeosaurus;
        protected double wantedX;
        protected double wantedY;
        protected double wantedZ;

        public DromaeosaurusRunGoal(Dromaeosaurus dromaeosaurus) {
            this.dromaeosaurus = dromaeosaurus;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (this.dromaeosaurus.getRandom().nextInt(reducedTickDelay(8)) != 0) {
                return false;
            } else if (this.dromaeosaurus.isVehicle()) {
                return false;
            } else {
                Vec3 vec3 = this.getPosition();
                if (vec3 == null) {
                    return false;
                } else if ((!this.dromaeosaurus.isEepy() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F) && dromaeosaurus.getNavigation().isDone()) {
                    this.wantedX = vec3.x;
                    this.wantedY = vec3.y;
                    this.wantedZ = vec3.z;
                    return true;
                }
            }
            return false;
        }

        @Nullable
        protected Vec3 getPosition() {
            Vec3 randomPos;
            if (dromaeosaurus.isInWater()) {
                randomPos = LandRandomPos.getPos(dromaeosaurus, 30, 8);
                return randomPos == null ? LandRandomPos.getPos(dromaeosaurus, 16, 4) : randomPos;
            }
            randomPos = dromaeosaurus.getRandom().nextFloat() > 0.001F ? LandRandomPos.getPos(dromaeosaurus, 16, 4) : DefaultRandomPos.getPos(dromaeosaurus, 16, 4);
            return randomPos;
        }

        @Override
        public boolean canContinueToUse() {
            return (this.dromaeosaurus.level().isDay() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F || this.dromaeosaurus.level().dimension() != Level.OVERWORLD) && !this.dromaeosaurus.isVehicle() && !this.dromaeosaurus.getNavigation().isDone();
        }

        @Override
        public void start() {
            this.dromaeosaurus.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, 1.0D);
        }

        @Override
        public void stop() {
            this.dromaeosaurus.setSprinting(false);
            this.dromaeosaurus.getNavigation().stop();
        }
    }

    private static class DromaeosaurusLeapGoal extends Goal {

        protected final Dromaeosaurus dromaeosaurus;

        public DromaeosaurusLeapGoal(Dromaeosaurus dromaeosaurus) {
            this.dromaeosaurus = dromaeosaurus;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return (!this.dromaeosaurus.isEepy() || this.dromaeosaurus.getHealth() <= this.dromaeosaurus.getMaxHealth() * 0.5F) && !this.dromaeosaurus.isVehicle() && this.dromaeosaurus.isSprinting() && this.dromaeosaurus.leapCooldown == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }

        @Override
        public void tick() {
            this.dromaeosaurus.getNavigation().stop();
            if (this.dromaeosaurus.onGround()) {
                this.dromaeosaurus.addDeltaMovement(new Vec3(0, 0.8D, 0));
                this.dromaeosaurus.addDeltaMovement(this.dromaeosaurus.getLookAngle().scale(2.0D).multiply(0.6D, 0, 0.6D));
                this.dromaeosaurus.leapCooldown = 120 + dromaeosaurus.getRandom().nextInt(120);
            }
        }
    }
}
