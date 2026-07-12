package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.PlushableMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import com.barl_inc.unusual_prehistory.tags.UP2DamageTypeTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class Kentrosaurus extends PrehistoricMob implements PlushableMob {

    private static final int IDLE_GRAZE = 1;
    private static final int IDLE_SHAKE = 2;
    private static final int IDLE_YAWN = 3;
    private static final int IDLE_STRETCH = 4;

    private int attackCooldown = 0;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState grazeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState shakeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState stretch1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState stretch2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState angryAnimationState = new SmoothAnimationState();

    private boolean attackAlt = false;
    private boolean stretchAlt = false;

    public Kentrosaurus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.DANGER_OTHER, 0.0F);
        this.setPathfindingMalus(PathType.DAMAGE_OTHER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 2.0D, 10, 4) {
            @Override
            protected boolean shouldPanic() {
                return super.shouldPanic() && (Kentrosaurus.this.getHealth() <= Kentrosaurus.this.getMaxHealth() * 0.4F || Kentrosaurus.this.isBaby());
            }
        });
        this.goalSelector.addGoal(2, new KentrosaurusAttackGoal(this));
        this.goalSelector.addGoal(3, new KentrosaurusFollowThornsGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_HERBIVORE), false));
        this.goalSelector.addGoal(5, new PrehistoricWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new PrehistoricFollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new EepyGoal(this));
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 40, IDLE_GRAZE, true, 0.001F, this::canGraze));
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 40, IDLE_SHAKE, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 80, IDLE_YAWN, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 60, IDLE_STRETCH, true, 0.001F, this::canPlayIdles));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new KentrosaurusDefendThornsGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.16F)
                .add(Attributes.ARMOR, 4.0F)
                .add(Attributes.STEP_HEIGHT, 1.2D);
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && this.horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.4D * this.getBbHeight();
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVector = travelVector.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVector);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_HERBIVORE);
    }

    public boolean entityHasThorns(LivingEntity entity) {
        Holder<Enchantment> thorns = entity.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.THORNS);
        return entity.getItemBySlot(EquipmentSlot.HEAD).getEnchantmentLevel(thorns) > 0 || entity.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(thorns) > 0 || entity.getItemBySlot(EquipmentSlot.LEGS).getEnchantmentLevel(thorns) > 0 || entity.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(thorns) > 0;
    }

    public static void angerNearbyKentrosaurus(Player player, boolean angerIfSeen) {
        List<Kentrosaurus> list = player.level().getEntitiesOfClass(Kentrosaurus.class, player.getBoundingBox().inflate(16));
        list.stream().filter((kentrosaurus) -> !angerIfSeen || BehaviorUtils.canSee(kentrosaurus, player)).forEach((kentrosaurus) -> kentrosaurus.setTarget(player));
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, 0.2D, this.getBbWidth() * 0.97F).yRot(-this.yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public ItemStack getPlushieItemStack() {
        return new ItemStack(UP2Blocks.KENTROSAURUS_PLUSHIE.get());
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (!this.level().isClientSide) {
            if (this.attackCooldown > 0) {
                this.attackCooldown--;
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.getPose() != UP2Poses.ATTACKING.get() && !this.isInWater() && !this.isEepy(), this.tickCount);
        this.swimAnimationState.animateWhen(this.getPose() != UP2Poses.ATTACKING.get() && this.isInWater(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.attack1AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && !this.attackAlt, this.tickCount);
        this.attack2AnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get() && this.attackAlt, this.tickCount);
        this.angryAnimationState.animateWhen(this.getPose() != UP2Poses.ATTACKING.get() && this.isAggressive(), this.tickCount);
        this.grazeAnimationState.animateWhen(this.getIdleState() == IDLE_GRAZE, this.tickCount);
        this.shakeAnimationState.animateWhen(this.getIdleState() == IDLE_SHAKE, this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == IDLE_YAWN, this.tickCount);
        this.stretch1AnimationState.animateWhen(this.getIdleState() == IDLE_STRETCH && !this.stretchAlt, this.tickCount);
        this.stretch2AnimationState.animateWhen(this.getIdleState() == IDLE_STRETCH && this.stretchAlt, this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_POSE.equals(key)) {
            if (this.getPose() == UP2Poses.ATTACKING.get()) {
                this.attackAlt = this.getRandom().nextBoolean();
            }
        }
        else if (IDLE_STATE.equals(key)) {
            if (this.getIdleState() == IDLE_STRETCH) {
                this.stretchAlt = this.getRandom().nextBoolean();
            }
        }
    }

    private boolean canGraze(Entity entity) {
        return entity.level().getBlockState(entity.blockPosition().below()).is(UP2BlockTags.KENTROSAURUS_FOOD_BLOCKS) && !entity.isInWaterOrBubble();
    }

    private boolean canPlayIdles(Entity entity) {
        return !entity.isInWaterOrBubble();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == IDLE_GRAZE) {
            return 1000 + this.getRandom().nextInt(1200);
        }
        else if (idleState == IDLE_SHAKE) {
            return 900 + this.getRandom().nextInt(1200);
        }
        else if (idleState == IDLE_YAWN) {
            return 850 + this.getRandom().nextInt(1200);
        }
        else if (idleState == IDLE_STRETCH) {
            return 1100 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    protected void actuallyHurt(DamageSource source, float amount) {
        if (!source.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) && !source.is(DamageTypes.THORNS)) {
            Entity entity = source.getDirectEntity();
            if (entity instanceof LivingEntity target) {
                target.hurt(this.damageSources().thorns(this), 2.0F);
            }
        }
        super.actuallyHurt(source, amount);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(UP2DamageTypeTags.KENTROSAURUS_IMMUNE_TO);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == IDLE_GRAZE || this.getIdleState() == IDLE_STRETCH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.KENTROSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return UP2SoundEvents.KENTROSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.KENTROSAURUS_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(UP2SoundEvents.KENTROSAURUS_STEP.get(), 1.0F, 1.1F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        Kentrosaurus baby = UP2Entities.KENTROSAURUS.get().create(level);
        if (baby != null) {
            baby.setVariantRawId(this.inheritVariantFrom(mob, this.getRandom()));
        }
        return baby;
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/kentrosaurus/kentrosaurus.png");
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.pickVariantForSpawn(level);
        return data;
    }

    // Goals
    private static class KentrosaurusDefendThornsGoal extends TargetGoal {

        private final Kentrosaurus kentrosaurus;
        private LivingEntity ownerLastHurtBy;
        private int timestamp;

        @Nullable
        protected LivingEntity target;

        public KentrosaurusDefendThornsGoal(Kentrosaurus kentrosaurus) {
            super(kentrosaurus, false);
            this.kentrosaurus = kentrosaurus;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        @Override
        public boolean canUse() {
            this.findTarget();
            if (this.target == null) {
                return false;
            } else {
                this.ownerLastHurtBy = this.target.getLastHurtByMob();
                int i = this.target.getLastHurtByMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT);
            }
        }

        @Override
        public void start() {
            this.mob.setTarget(this.ownerLastHurtBy);
            LivingEntity livingentity = this.target;
            if (livingentity != null) {
                this.timestamp = livingentity.getLastHurtByMobTimestamp();
            }
            super.start();
        }

        protected void findTarget() {
            this.target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(LivingEntity.class, this.getTargetSearchArea(this.getFollowDistance()), (target) -> true), TargetingConditions.forCombat().range(this.getFollowDistance()).selector(this.kentrosaurus::entityHasThorns), this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        }

        protected AABB getTargetSearchArea(double distance) {
            return this.kentrosaurus.getBoundingBox().inflate(distance, 4.0D, distance);
        }
    }

    private static class KentrosaurusFollowThornsGoal extends Goal {

        private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
        private final TargetingConditions targetingConditions;
        protected final Kentrosaurus kentrosaurus;

        @Nullable
        protected LivingEntity livingEntity;
        private int calmDown;

        public KentrosaurusFollowThornsGoal(Kentrosaurus kentrosaurus) {
            this.kentrosaurus = kentrosaurus;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
        }

        @Override
        public boolean canUse() {
            if (this.calmDown > 0) {
                this.calmDown--;
                return false;
            } else {
                this.livingEntity = this.kentrosaurus.level().getNearestPlayer(this.targetingConditions, this.kentrosaurus);
                return this.livingEntity != null;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }

        @Override
        public void stop() {
            this.livingEntity = null;
            this.kentrosaurus.getNavigation().stop();
            this.calmDown = reducedTickDelay(100);
        }

        @Override
        public void tick() {
            if (this.livingEntity != null) {
                this.kentrosaurus.getLookControl().setLookAt(this.livingEntity, (float) (this.kentrosaurus.getMaxHeadYRot() + 20), (float) this.kentrosaurus.getMaxHeadXRot());
                if (this.kentrosaurus.distanceToSqr(this.livingEntity) < 6.25D) {
                    this.kentrosaurus.getNavigation().stop();
                } else {
                    this.kentrosaurus.getNavigation().moveTo(this.livingEntity, 1.0D);
                }
            }
        }

        private boolean shouldFollow(LivingEntity entity) {
            return this.kentrosaurus.entityHasThorns(entity);
        }
    }

    private static class KentrosaurusAttackGoal extends AttackGoal {

        private final Kentrosaurus kentrosaurus;

        public KentrosaurusAttackGoal(Kentrosaurus kentrosaurus) {
            super(kentrosaurus);
            this.kentrosaurus = kentrosaurus;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.kentrosaurus.getHealth() > this.kentrosaurus.getMaxHealth() * 0.4F;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.kentrosaurus.getHealth() > this.kentrosaurus.getMaxHealth() * 0.4F;
        }

        @Override
        public void tick() {
            LivingEntity target = this.kentrosaurus.getTarget();
            if (target != null) {
                double distance = this.kentrosaurus.distanceToSqr(target.getX(), target.getY(), target.getZ());
                this.lookAtTarget(target, 30.0F, 30.0F);

                if (this.kentrosaurus.getAttackState() == 1) {
                    this.tickAttack(target);
                } else {
                    this.kentrosaurus.getNavigation().moveTo(target, 2.0D);
                    if (distance <= this.getAttackReachSqr(target) && this.kentrosaurus.attackCooldown == 0) {
                        this.kentrosaurus.setAttackState(1);
                    }
                }
            }
        }

        protected void tickAttack(LivingEntity target) {
            this.timer++;
            if (this.timer == 1) {
                this.kentrosaurus.setPose(UP2Poses.ATTACKING.get());
            }
            if (this.timer == 19) {
                if (this.isInAttackRange(target, 3.0D)) {
                    this.kentrosaurus.swing(InteractionHand.MAIN_HAND);
                    this.kentrosaurus.doHurtTarget(target);
                }
            }
            if (this.timer > 40) {
                this.timer = 0;
                this.kentrosaurus.setAttackState(0);
                this.kentrosaurus.attackCooldown = 5;
                this.kentrosaurus.setPose(Pose.STANDING);
            }
        }
    }
}
