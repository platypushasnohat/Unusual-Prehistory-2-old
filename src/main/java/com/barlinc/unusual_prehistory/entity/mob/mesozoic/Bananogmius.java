package com.barlinc.unusual_prehistory.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.FollowVariantLeaderGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricSwimGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricSchoolingAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Bananogmius extends PrehistoricSchoolingAquaticMob {

    private static final float MAX_TILT = 60.0F;
    private static final float MAX_ROLL = 20.0F;
    private static final float ROLL_PER_YAW = 2.0F;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);

    public Bananogmius(EntityType<? extends PrehistoricSchoolingAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 60, 10, 0.02F, 0.1F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.5F)
                .add(Attributes.ATTACK_DAMAGE, 2.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 1.5D, 20, 8, true));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(5, new PrehistoricSwimGoal(this, 1.0D, 10, 30, 15));
        this.goalSelector.addGoal(6, new FollowVariantLeaderGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
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
    public int getMaxSchoolSize() {
        return 20;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return UP2MobUtils.getSurfacePathfindingFavor(pos, level);
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isInWater() ? 1 : super.getMaxHeadXRot();
    }

    @Override
    public int getMaxHeadYRot() {
        return this.isInWater() ? 1 : super.getMaxHeadYRot();
    }

    @Override
    protected boolean shouldUseShallowNavigation() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.isInWater() && this.getDeltaMovement().lengthSqr() > 0.04D && this.getRandom().nextFloat() < 0.4F) {
                Vec3 viewVector = this.getViewVector(0.0F);
                this.level().addParticle(ParticleTypes.BUBBLE, this.getRandomX(0.5D) - viewVector.x * 0.5D, this.getRandomY() - viewVector.y * 0.25D, this.getRandomZ(0.5D) - viewVector.z * 0.5D, 0.0D, 0.0D, 0.0D);
            }
        }

        this.tickRotations(MAX_TILT, MAX_ROLL, ROLL_PER_YAW);
    }

    @Override
    public void setupAnimationStates() {
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), tickCount);
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float) Mth.length(this.getX() - xo, this.isInWater() ? this.getY() - yo : 0.0, this.getZ() - zo);
        if (this.isBaby()) {
            this.updateWalkAnimation(f * 0.5F);
        } else {
            this.updateWalkAnimation(f);
        }
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f = Math.min(partialTick * 35.0F, 1.0F);
        this.walkAnimation.update(f, 0.4F);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
        return UP2Entities.BANANOGMIUS.get().create(level);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.STETHACANTHUS_FLOP.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return UP2SoundEvents.STETHACANTHUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.STETHACANTHUS_DEATH.get();
    }
}
