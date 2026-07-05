package com.barlinc.unusual_prehistory.entity.mob.cenozoic;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.AquaticLeapGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricSwimGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothWaterNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.LeapingMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowBoatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Nihohae extends PrehistoricAquaticMob implements LeapingMob {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(Nihohae.class, EntityDataSerializers.BOOLEAN);

    private static final float MAX_TILT = 60.0F;
    private static final float MAX_ROLL = 25.0F;
    private static final float ROLL_PER_YAW = 2.0F;
    private static final float MAX_TAIL_YAW = 20.0F;
    private static final float TAIL_YAW_MULITPLIER = 0.15F;

    public int attackCooldown = 0;

    public final SmoothAnimationState snickerAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState inspectAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState angryAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState jumpAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState roll1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState roll2AnimationState = new SmoothAnimationState(1.0F);


    public Nihohae(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.25F)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new PrehistoricSwimGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new AquaticLeapGoal(this, 10, 0.7D, 0.8D));
        this.goalSelector.addGoal(8, new FollowBoatGoal(this));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothWaterNavigation(this, level, true);
    }

    @Override
    public void travel(Vec3 travelVec) {
        if (this.refuseToMove()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        if (this.isEffectiveAi() && this.isInWater() && !this.isEepy()) {
            UP2MobUtils.travelInWater(this, travelVec);
        } else {
            super.travel(travelVec);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    public int getMaxHeadXRot() {
        return 1;
    }

    @Override
    public int getMaxHeadYRot() {
        return 1;
    }

    @Override
    protected void handleAirSupply(int airSupply) {
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && this.isInWater() && this.getDeltaMovement().lengthSqr() > 0.03D) {
            Vec3 vec3 = this.getViewVector(0.0F);
            float f = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)) * 0.3F;
            float f1 = Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)) * 0.3F;
            float f2 = 1.2F - this.getRandom().nextFloat() * 0.7F;

            for (int i = 0; i < 2; i++) {
                this.level().addParticle(ParticleTypes.DOLPHIN, this.getX() - vec3.x * (double) f2 + (double) f, this.getY() - vec3.y, this.getZ() - vec3.z * (double) f2 + (double) f1, 0.0, 0.0, 0.0);
                this.level().addParticle(ParticleTypes.DOLPHIN, this.getX() - vec3.x * (double) f2 - (double) f, this.getY() - vec3.y, this.getZ() - vec3.z * (double) f2 - (double) f1, 0.0, 0.0, 0.0);
            }
        }

        this.tickRotations(MAX_TILT, MAX_ROLL, ROLL_PER_YAW);
        this.tickTailYaw(MAX_TAIL_YAW, TAIL_YAW_MULITPLIER);
    }

    @Override
    public void setupAnimationStates() {
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isLeaping(), tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), tickCount);
        this.jumpAnimationState.animateWhen(this.isLeaping(), tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float) Mth.length(this.getX() - xo, this.isInWater() ? this.getY() - yo : 0.0D, this.getZ() - zo);
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
    public void tickCooldowns() {
        super.tickCooldowns();
        if (!this.level().isClientSide) {
            if (attackCooldown > 0) {
                this.attackCooldown--;
            }
        }
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
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return UP2Entities.NIHOHAE.get().create(level);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.DOLPHIN_HURT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return this.isInWater() ? SoundEvents.DOLPHIN_AMBIENT_WATER : SoundEvents.DOLPHIN_AMBIENT;
    }
}
