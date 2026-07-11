package com.barl_inc.unusual_prehistory.entity.mob.multi_era;

import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.ai.goals.update_4.LystrosaurusRunLikeCrazyGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import com.barl_inc.unusual_prehistory.tags.UP2DamageTypeTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import com.barl_inc.unusual_prehistory.utils.UP2ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public class Lystrosaurus extends PrehistoricMob {

    public final SmoothAnimationState grazeAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState digAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState scratch1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState scratch2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState blinkAnimationState = new SmoothAnimationState(1.0F);

    private boolean scratchAlt = false;

    public Lystrosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LystrosaurusRunLikeCrazyGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 2.0D, 10, 4));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_HERBIVORE), false));
        this.goalSelector.addGoal(3, new PrehistoricWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new PrehistoricFollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new EepyGoal(this));
        this.goalSelector.addGoal(7, new IdleAnimationGoal(this, 60, 1, false, 0.001F));
        this.goalSelector.addGoal(8, new IdleAnimationGoal(this, 40, 2, true, 0.001F, this::canGraze));
        this.goalSelector.addGoal(8, new LystrosaurusDigGoal(this, 80, 3, true, 0.001F, this::canDig));
        this.goalSelector.addGoal(8, new IdleAnimationGoal(this, 30, 4, true, 0.001F, this::canPlayIdles) {
            @Override
            public void start() {
                super.start();
                Lystrosaurus.this.scratchAlt = Lystrosaurus.this.getRandom().nextBoolean();
            }
        });
        this.goalSelector.addGoal(8, new IdleAnimationGoal(this, 40, 5, false, 0.001F, this::canPlayIdles));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23F)
                .add(Attributes.ARMOR, 30.0F)
                .add(Attributes.ARMOR_TOUGHNESS, 20.0F)
                .add(Attributes.STEP_HEIGHT, 1.1D);
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
        return stack.is(UP2ItemTags.DIET_HERBIVORE);
    }

    @Override
    public int getHealCooldown() {
        return this.isEepy() ? 80 : 150;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.MAGIC) || source.is(DamageTypes.INDIRECT_MAGIC)) {
            return super.hurt(source, amount * 0.05F);
        } else {
            return super.hurt(source, amount * 0.1F);
        }
    }

    @Override
    public boolean canBeAffected(@NotNull MobEffectInstance effect) {
        return false;
    }

    // 3 hours of air
    @Override
    public int getMaxAirSupply() {
        return 216000;
    }

    @Override
    protected int increaseAirSupply(int currentAir) {
        return this.getMaxAirSupply();
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(-0.1D, 0.35D, this.getBbWidth() * 1.1F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive() && !this.level().isClientSide && this.tickCount % 3 == 0) {
            this.breakFallingBlocks();
        }
    }

    private void breakFallingBlocks() {
        this.level().getEntities(this, this.getBoundingBox().inflate(0.0D, 0.1D, 0.0D).move(0.0D, 0.1D, 0.0D)).forEach((entity) -> {
            if (entity instanceof FallingBlockEntity fallingBlockEntity) {
                if (fallingBlockEntity.dropItem && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                    fallingBlockEntity.spawnAtLocation(fallingBlockEntity.getBlockState().getBlock());
                }
                fallingBlockEntity.discard();
                UP2ParticleUtils.queueParticlesOnBlockFaces((ServerLevel) fallingBlockEntity.level(), fallingBlockEntity.blockPosition(), new BlockParticleOption(ParticleTypes.BLOCK, fallingBlockEntity.getBlockState()), UniformInt.of(2, 4));
                fallingBlockEntity.callOnBrokenAfterFall(fallingBlockEntity.getBlockState().getBlock(), fallingBlockEntity.blockPosition());
            }
        });
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.getIdleState() != 3 && !this.isEepy(), this.tickCount);
        this.blinkAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.grazeAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.digAnimationState.animateWhen(this.getIdleState() == 3, this.tickCount);
        this.scratch1AnimationState.animateWhen(this.getIdleState() == 4 && !scratchAlt, this.tickCount);
        this.scratch2AnimationState.animateWhen(this.getIdleState() == 4 && scratchAlt, this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == 5, this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble() && !this.onGround(), this.tickCount);
    }

    private boolean canGraze(Entity entity) {
        return !entity.isInWaterOrBubble() && entity.level().getBlockState(entity.blockPosition().below()).is(UP2BlockTags.LYSTROSAURUS_FOOD_BLOCKS);
    }

    private boolean canDig(Entity entity) {
        return !entity.isInWaterOrBubble() && entity.level().getBlockState(entity.blockPosition().below()).is(UP2BlockTags.LYSTROSAURUS_DIGGING_BLOCKS);
    }

    private boolean canPlayIdles(Entity entity) {
        return !entity.isInWaterOrBubble();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 300 + this.getRandom().nextInt(300);
        }
        else if (idleState == 2) {
            return 800 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 3) {
            return 900 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 4) {
            return 850 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 5) {
            return 750 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(UP2DamageTypeTags.LYSTROSAURUS_IMMUNE_TO);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 2 || this.getIdleState() == 3 || this.getIdleState() == 4;
    }

    @Override
    public boolean refuseToLook() {
        return super.refuseToLook() || this.getIdleState() == 2 || this.getIdleState() == 3;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.LYSTROSAURUS.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.hasCustomName() && this.getName().getString().contains("chainsmoker") && this.getRandom().nextFloat() < 0.3F) {
            return UP2SoundEvents.LYSTROSAURUS_CHAINSMOKER.get();
        }
        return UP2SoundEvents.LYSTROSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.LYSTROSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.LYSTROSAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.LYSTROSAURUS_STEP.get(), 0.15F, 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }

    // Goals
    private static class LystrosaurusDigGoal extends IdleAnimationGoal {

        public LystrosaurusDigGoal(PrehistoricMob prehistoricMob, int animationTime, int idleState, boolean stopMoving, float chance, Predicate<Mob> canUse) {
            super(prehistoricMob, animationTime, idleState, stopMoving, chance, canUse);
        }

        @Override
        public void tick() {
            super.tick();
            if (timer % 6 == 0 && timer < 60 && timer > 20) {
                this.spawnEffectsAtBlock(prehistoricMob.blockPosition().below());
                this.prehistoricMob.playSound(prehistoricMob.level().getBlockState(prehistoricMob.blockPosition().below()).getSoundType().getHitSound(), 0.3F, 0.8F + prehistoricMob.getRandom().nextFloat() * 0.25F);
            }
        }

        public void spawnEffectsAtBlock(BlockPos blockPos) {
            float radius = 0.4F;
            for (int i1 = 0; i1 < 3; i1++) {
                double motionX = prehistoricMob.getRandom().nextGaussian() * 0.07D;
                double motionY = prehistoricMob.getRandom().nextGaussian() * 0.07D;
                double motionZ = prehistoricMob.getRandom().nextGaussian() * 0.07D;
                float angle = (float) ((0.0174532925 * prehistoricMob.yBodyRot) + i1);
                double extraX = radius * Mth.sin(Mth.PI + angle);
                double extraY = 0.8F;
                double extraZ = radius * Mth.cos(angle);
                BlockState state = prehistoricMob.level().getBlockState(blockPos);
                ((ServerLevel) prehistoricMob.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), blockPos.getX() + 0.5 + extraX, blockPos.getY() + 0.5 + extraY, blockPos.getZ() + 0.5 + extraZ, 1, motionX, motionY, motionZ, 1);
            }
        }
    }
}
