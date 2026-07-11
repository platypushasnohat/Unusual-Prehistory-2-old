package com.barl_inc.unusual_prehistory.entity.mob.other;

import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.AbstractLingcod;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.KingLingcod;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Lingcod extends AbstractLingcod {

    private static final EntityDataAccessor<Boolean> SUMMONED = SynchedEntityData.defineId(Lingcod.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SUMMON_TIME = SynchedEntityData.defineId(Lingcod.class, EntityDataSerializers.INT);

    public Lingcod(EntityType<? extends AbstractLingcod> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.8F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 1.5D, 10, 4));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TEMPTS_LINGCOD), false));
        this.goalSelector.addGoal(3, new LingcodAttackGoal(this));
        this.goalSelector.addGoal(4, new PrehistoricSwimGoal(this, 1.0D, 150));
        this.goalSelector.addGoal(5, new PrehistoricFollowMobGoal(this, 30, 1.0F, 3.0F, 10.0F, (mob) -> mob instanceof KingLingcod));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, true, this::canHunt));
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (entity instanceof AbstractLingcod) {
            return true;
        }
        return super.isAlliedTo(entity);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isSummoned()) {
            this.setSummonTime(this.getSummonTime() + 1);
            if (this.getSummonTime() > 1200) {
                this.setSummonTime(this.getSummonTime() - 20);
                this.hurt(this.damageSources().starve(), 2.0F);
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SUMMONED, false);
        builder.define(SUMMON_TIME, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Summoned", this.isSummoned());
        compoundTag.putInt("SummonTime", this.getSummonTime());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSummoned(compoundTag.getBoolean("Summoned"));
        this.setSummonTime(compoundTag.getInt("SummonTime"));
    }

    public boolean isSummoned() {
        return this.entityData.get(SUMMONED);
    }
    public void setSummoned(boolean summoned) {
        this.entityData.set(SUMMONED, summoned);
    }

    public int getSummonTime() {
        return this.entityData.get(SUMMON_TIME);
    }
    public void setSummonTime(int summonTime) {
        this.entityData.set(SUMMON_TIME, summonTime);
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(UP2Items.STETHACANTHUS_BUCKET.get());
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.STETHACANTHUS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return UP2SoundEvents.STETHACANTHUS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getFlopSound() {
        return UP2SoundEvents.STETHACANTHUS_FLOP.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.LINGCOD.get().create(serverLevel);
    }

    private static class LingcodAttackGoal extends AttackGoal {

        private final Lingcod lingcod;

        public LingcodAttackGoal(Lingcod lingcod) {
            super(lingcod);
            this.lingcod = lingcod;
        }

        @Override
        public void tick() {
            LivingEntity target = lingcod.getTarget();
            if (target != null) {
                this.lingcod.lookAt(target, 30.0F, 30.0F);
                this.lingcod.getLookControl().setLookAt(target, 30.0F, 30.0F);
                double distance = lingcod.distanceToSqr(target.getX(), target.getY(), target.getZ());

                if (lingcod.getAttackState() == 1) {
                    this.lingcod.getNavigation().stop();
                    this.tickAttack(target);
                } else {
                    if (distance <= 4 && lingcod.attackCooldown == 0) {
                        this.lingcod.setAttackState(1);
                    }
                    this.lingcod.getNavigation().moveTo(target, 1.5D);
                }
            }
        }

        protected void tickAttack(LivingEntity target) {
            this.timer++;
            if (timer == 1) {
                this.lingcod.setPose(UP2Poses.ATTACKING.get());
            }
            if (timer == 5) {
                this.lingcod.playSound(UP2SoundEvents.STETHACANTHUS_BITE.get(), 0.7F, lingcod.getVoicePitch());
            }
            if (timer == 6) {
                if (this.isInAttackRange(target, 1.5D)) {
                    this.lingcod.doHurtTarget(target);
                }
            }
            if (timer > 20) {
                this.timer = 0;
                this.lingcod.attackCooldown = 4;
                this.lingcod.setPose(Pose.STANDING);
                this.lingcod.setAttackState(0);
            }
        }
    }

    private static class CopyKingTargetGoal extends TargetGoal {

        private final Lingcod lingcod;
        private LivingEntity target;

        public CopyKingTargetGoal(Lingcod lingcod) {
            super(lingcod, false);
            this.lingcod = lingcod;
        }

        @Override
        public boolean canUse() {
            KingLingcod kingLingcod = lingcod.level().getNearestEntity(lingcod.level().getEntitiesOfClass(KingLingcod.class, lingcod.getBoundingBox().inflate(24.0D)), TargetingConditions.DEFAULT, lingcod, lingcod.getX(), lingcod.getY(), lingcod.getZ());
            if (kingLingcod == null) {
                return false;
            }
            this.target = kingLingcod.getTarget();
            return target != null && target.isAlive() && !(target instanceof KingLingcod);
        }

        @Override
        public void start() {
            this.lingcod.setTarget(target);
        }

        @Override
        public boolean canContinueToUse() {
            return target != null && target.isAlive() && lingcod.getTarget() == target;
        }
    }
}