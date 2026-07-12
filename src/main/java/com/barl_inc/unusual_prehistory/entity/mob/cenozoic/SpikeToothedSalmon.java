package com.barl_inc.unusual_prehistory.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricSchoolingAquaticMob;
import com.barl_inc.unusual_prehistory.entity.utils.LeapingMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

public class SpikeToothedSalmon extends PrehistoricSchoolingAquaticMob implements LeapingMob {

    private static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(SpikeToothedSalmon.class, EntityDataSerializers.BOOLEAN);

    private int attackCooldown = 0;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();

    public SpikeToothedSalmon(EntityType<? extends PrehistoricSchoolingAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.switchShallowNavigation(false);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 1000, 6, 0.02F, 0.1F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 4);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 1.5D, 12, 6));
        this.goalSelector.addGoal(2, new SpikeToothedSalmonAttackGoal(this));
        this.goalSelector.addGoal(3, new AquaticLeapGoal(this, 10, 0.7D, 0.8D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(5, new PrehistoricSwimGoal(this, 1.0D, 10, 16, 8, 3, true));
        this.goalSelector.addGoal(6, new FollowVariantLeaderGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
    }

    @Override
    public int getMaxSchoolSize() {
        return 5;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    protected void switchShallowNavigation(boolean inShallows) {
        this.navigation.stop();
        if (inShallows) {
            this.navigation = new SmoothAmphibiousNavigation(this, this.level());
            this.shallowWater = true;
        } else {
            this.navigation = this.createNavigation(this.level());
            this.shallowWater = false;
        }
    }

    @Override
    public void addFollowers(Stream<? extends PrehistoricSchoolingAquaticMob> entity) {
        entity.limit(this.getMaxSchoolSize() - this.schoolSize).filter((entity1) -> entity1 != this).forEach((entity2) -> {
            if ((Objects.equals(this.getVariantRawId(), entity2.getVariantRawId())) && !this.isBaby()) {
                entity2.startFollowing(this);
            }
        });
    }

    @Override
    public void tick() {
        super.tick();

        final boolean shallowWater = this.isInShallowWater();
        if (shallowWater && !this.shallowWater) {
            this.switchShallowNavigation(true);
        } else if (!shallowWater && this.shallowWater) {
            this.switchShallowNavigation(false);
        }

        if (attackCooldown > 0 && !this.level().isClientSide) {
            this.attackCooldown--;
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen((this.isInWaterOrBubble() || this.isLeaping()) && this.getPose() != UP2Poses.ATTACKING.get(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isLeaping(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
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
        return this.entityData.get(LEAPING);
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.SPIKE_TOOTHED_SALMON_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.SPIKE_TOOTHED_SALMON_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.SPIKE_TOOTHED_SALMON_FLOP.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        SpikeToothedSalmon baby = UP2Entities.SPIKE_TOOTHED_SALMON.get().create(level);
        if (baby != null) {
            baby.setVariantRawId(this.inheritVariantFrom(mob, this.getRandom()));
        }
        return baby;
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/spike_toothed_salmon/spike_toothed_salmon_golden.png");
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        if (spawnType == MobSpawnType.BUCKET) {
            return data;
        } else {
            this.pickVariantForSpawn(level);
        }
        return data;
    }

    // Goals
    private static class SpikeToothedSalmonAttackGoal extends AttackGoal {

        private final SpikeToothedSalmon salmon;

        public SpikeToothedSalmonAttackGoal(SpikeToothedSalmon salmon) {
            super(salmon);
            this.salmon = salmon;
        }

        @Override
        public void tick() {
            LivingEntity target = salmon.getTarget();
            if (target != null) {
                double distance = salmon.distanceToSqr(target.getX(), target.getY(), target.getZ());
                this.lookAtTarget(target, 30.0F, 30.0F);

                this.salmon.getNavigation().moveTo(target, 1.5D);
                if (salmon.getAttackState() == 1) {
                    this.tickAttack(target);
                } else if (distance <= this.getAttackReachSqr(target) && salmon.attackCooldown == 0) {
                    this.salmon.setAttackState(1);
                }
            }
        }

        protected void tickAttack(LivingEntity target) {
            this.timer++;
            if (this.timer == 1) {
                this.salmon.setPose(UP2Poses.ATTACKING.get());
            }
            if (this.timer == 4) {
                this.salmon.playSound(UP2SoundEvents.SPIKE_TOOTHED_SALMON_ATTACK.get(), 1.0F, 0.9F + salmon.getRandom().nextFloat() * 0.2F);
            }
            if (this.timer == 6) {
                if (this.isInAttackRange(target, 1.5D) && target != null) {
                    this.salmon.doHurtTarget(target);
                    this.salmon.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (this.timer > 10) {
                this.timer = 0;
                this.salmon.attackCooldown = 5;
                this.salmon.setPose(Pose.STANDING);
                this.salmon.setAttackState(0);
            }
        }
    }
}