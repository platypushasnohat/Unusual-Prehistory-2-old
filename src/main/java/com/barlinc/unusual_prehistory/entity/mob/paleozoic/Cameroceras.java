package com.barlinc.unusual_prehistory.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricSwimGoal;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothGroundNavigation;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Cameroceras extends PrehistoricAquaticMob {

    private static final EntityDataAccessor<Boolean> CRAWLING = SynchedEntityData.defineId(Cameroceras.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> CRAWL_COOLDOWN = SynchedEntityData.defineId(Cameroceras.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ROCKETING = SynchedEntityData.defineId(Cameroceras.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ROCKET_COOLDOWN = SynchedEntityData.defineId(Cameroceras.class, EntityDataSerializers.INT);

    private int attackCooldown = 0;
    public boolean isCrawlNavigator;

    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState eyeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState rocketAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState parachuteAnimationState = new SmoothAnimationState(0.25F);
    public final SmoothAnimationState crawlIdleAnimationState = new SmoothAnimationState();

    public Cameroceras(EntityType<? extends Cameroceras> entityType, Level level) {
        super(entityType, level);
        this.switchShallowNavigation(false);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 85, 10, 0.325F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 20);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15F)
                .add(Attributes.ATTACK_DAMAGE, 6.0F)
                .add(Attributes.ARMOR, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.STEP_HEIGHT, 1.2D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new CamerocerasRocketGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(4, new CamerocerasCrawlAroundGoal(this));
        this.goalSelector.addGoal(4, new CamerocerasSwimGoal(this));
    }

    protected void switchShallowNavigation(boolean crawling) {
        this.getNavigation().stop();
        if (crawling) {
            this.navigation = new SmoothGroundNavigation(this, this.level());
            this.isCrawlNavigator = true;
        } else {
            this.navigation = new SmoothAmphibiousNavigation(this, this.level());
            this.isCrawlNavigator = false;
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.refuseToMove()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVector = travelVector.multiply(0.0, 1.0, 0.0);
        }
        if (this.isEffectiveAi() && this.isInWater()) {
            if (this.isCrawling()) {
                this.moveRelative(this.getSpeed(), travelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                if (this.getNavigation().isDone()) {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.0D, 1.0D, 0.0D));
                } else {
                    this.setDeltaMovement(this.getDeltaMovement().scale(0.85D));
                }
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.06D, 0.0D));
            }
            else {
                UP2MobUtils.travelInWater(this, travelVector);
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        if (this.isCrawling()) {
            return level.getFluidState(pos.above()).is(FluidTags.WATER) ? 10.0F : super.getWalkTargetValue(pos, level);
        }
        return UP2MobUtils.getDepthPathfindingFavor(pos, level);
    }

    @Override
    public boolean isPushable() {
        return this.isBaby() || (!this.isInWaterOrBubble() && !this.isRocketing());
    }

    @Override
    public boolean shouldFlop() {
        return false;
    }

    @Override
    protected void handleAirSupply(int airSupply) {
        if (this.isAlive() && !this.isInWaterOrBubble() && !this.isRocketing()) {
            this.setAirSupply(airSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0F);
            }
        } else {
            this.setAirSupply(1000);
        }
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isCrawling() && !isCrawlNavigator) {
            this.switchShallowNavigation(true);
        }
        if (!this.isCrawling() && isCrawlNavigator) {
            this.switchShallowNavigation(false);
        }

        if (this.isInWater()) {
            if (this.getCrawlCooldown() > 0) {
                this.setCrawlCooldown(this.getCrawlCooldown() - 1);
            }
            if (this.getCrawlCooldown() == 0 && this.getRandom().nextFloat() < 0.02F) {
                this.setCrawling(!this.isCrawling());
                this.setCrawlCooldown(this.getRandom().nextInt(800) + 800);
                this.getNavigation().stop();
            }

            if (this.isCrawling()) {
                if (this.getRocketCooldown() > 0) {
                    this.setRocketCooldown(this.getRocketCooldown() - 1);
                }
            }
        }

        if (this.level().isClientSide) {
            if (this.isRocketing() && !this.isInWaterOrBubble() && this.getDeltaMovement().y > 0) {
                for (int i = 0; i < 4; i++) {
                    this.level().addParticle(ParticleTypes.POOF, this.getRandomX(0.2D), this.getY() - (0.3D * this.getRandom().nextDouble()), this.getRandomZ(0.2D), 0.0D, -2.5D, 0.0D);
                }
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isRocketing() && !this.isCrawling(), tickCount);
        this.crawlIdleAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isRocketing() && this.isCrawling(), tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isRocketing(), tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), tickCount);
        this.eyeAnimationState.animateWhen(this.isInWaterOrBubble() && !this.isRocketing(), tickCount);
        this.rocketAnimationState.animateWhen(this.isRocketing() && this.getDeltaMovement().y > 0, tickCount);
        this.parachuteAnimationState.animateWhen(this.isRocketing() && this.getDeltaMovement().y <= 0, tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float) Mth.length(this.getX() - xo, this.isCrawling() ? 0.0D : this.getY() - yo, this.getZ() - zo);
        if (this.isBaby()) {
            this.updateWalkAnimation(f * 0.5F);
        } else {
            this.updateWalkAnimation(f);
        }
    }

    @Override
    protected void updateWalkAnimation(float partialTicks) {
        float speed;
        if (this.isCrawling() && this.getDeltaMovement().horizontalDistanceSqr() < 1.0E-4D) {
            speed = 0.0F;
        } else {
            speed = Math.min(partialTicks * 22.5F, 1.0F);
        }
        this.walkAnimation.update(speed, 0.4F);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CRAWLING, false);
        builder.define(CRAWL_COOLDOWN, 800 + this.getRandom().nextInt(800));
        builder.define(ROCKETING, false);
        builder.define(ROCKET_COOLDOWN, 1200 + this.getRandom().nextInt(1200));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Crawling", this.isCrawling());
        compoundTag.putInt("CrawlCooldown", this.getCrawlCooldown());
        compoundTag.putInt("RocketCooldown", this.getRocketCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setCrawling(compoundTag.getBoolean("Crawling"));
        this.setCrawlCooldown(compoundTag.getInt("CrawlCooldown"));
        this.setRocketCooldown(compoundTag.getInt("RocketCooldown"));
    }

    public boolean isCrawling() {
        return entityData.get(CRAWLING);
    }
    public void setCrawling(boolean crawling) {
        this.entityData.set(CRAWLING, crawling);
    }
    public int getCrawlCooldown() {
        return entityData.get(CRAWL_COOLDOWN);
    }
    public void setCrawlCooldown(int cooldown) {
        this.entityData.set(CRAWL_COOLDOWN, cooldown);
    }

    public boolean isRocketing() {
        return entityData.get(ROCKETING);
    }
    public void setRocketing(boolean rocketing) {
        this.entityData.set(ROCKETING, rocketing);
    }
    public int getRocketCooldown() {
        return entityData.get(ROCKET_COOLDOWN);
    }
    public void setRocketCooldown(int cooldown) {
        this.entityData.set(ROCKET_COOLDOWN, cooldown);
    }
    private void rocketCooldown() {
        this.setRocketCooldown(1200 + this.getRandom().nextInt(1200));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return UP2Entities.CAMEROCERAS.get().create(level);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return UP2SoundEvents.TUSOTEUTHIS_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.TUSOTEUTHIS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.TUSOTEUTHIS_IDLE.get();
    }

    private static class CamerocerasRocketGoal extends Goal {

        private final Cameroceras cameroceras;
        private int airTime;

        public CamerocerasRocketGoal(Cameroceras cameroceras) {
            this.cameroceras = cameroceras;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        }

        @Override
        public void start() {
            this.airTime = 0;
            this.cameroceras.getNavigation().stop();
            this.cameroceras.setRocketing(true);
            this.cameroceras.setCrawling(false);
        }

        @Override
        public void stop() {
            this.cameroceras.setRocketing(false);
            this.cameroceras.getNavigation().stop();
            this.cameroceras.setDeltaMovement(0.0D, -1.5D, 0.0D);
        }

        @Override
        public boolean canUse() {
            if (!cameroceras.isInWater()) {
                return false;
            }
            if (!cameroceras.isCrawling()) {
                return false;
            }
            if (cameroceras.getRocketCooldown() > 0) {
                return false;
            }
            if (cameroceras.getRandom().nextInt(100) != 0) {
                return false;
            }
            return this.canRocket(cameroceras.level(), cameroceras.blockPosition().above());
        }

        @Override
        public boolean canContinueToUse() {
            return !cameroceras.verticalCollision && !cameroceras.horizontalCollision && cameroceras.getRocketCooldown() <= 0;
        }

        @Override
        public void tick() {
            Vec3 deltaMovement = cameroceras.getDeltaMovement();
            this.cameroceras.getNavigation().stop();
            if (!cameroceras.isInWater()) {
                this.airTime++;
                if (airTime >= 7 && deltaMovement.y < 0.0D) {
                    this.cameroceras.setDeltaMovement(deltaMovement.multiply(1.0D, 0.6D, 1.0D));
                }
            }
            if (airTime < 7) {
                this.cameroceras.addDeltaMovement(new Vec3(0.0D, 0.5D, 0.0D));
            }
            if (airTime >= 7 && (cameroceras.isInWaterOrBubble() || cameroceras.onGround() || cameroceras.onClimbable())) {
                this.cameroceras.rocketCooldown();
            }
        }

        private boolean canRocket(Level level, BlockPos pos) {
            BlockPos.MutableBlockPos checkPos = pos.mutable();
            while (level.getFluidState(checkPos).is(FluidTags.WATER)) {
                if (level.isOutsideBuildHeight(checkPos)) {
                    this.cameroceras.rocketCooldown();
                    return false;
                }
                checkPos.move(0, 1, 0);
            }
            for (int i = 0; i < 64; i++) {
                if (level.isOutsideBuildHeight(checkPos)) {
                    this.cameroceras.rocketCooldown();
                    return false;
                }
                if (!level.getBlockState(checkPos).isAir()) {
                    this.cameroceras.rocketCooldown();
                    return false;
                }
                checkPos.move(0, 1, 0);
            }
            return true;
        }
    }

    private static class CamerocerasSwimGoal extends PrehistoricSwimGoal {

        protected final Cameroceras cameroceras;

        public CamerocerasSwimGoal(Cameroceras cameroceras) {
            super(cameroceras, 1.0D, 120, 15, 7);
            this.cameroceras = cameroceras;
        }

        @Override
        public boolean canUse() {
            return cameroceras.isInWaterOrBubble() && !cameroceras.isCrawling() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return cameroceras.isInWaterOrBubble() && !cameroceras.isCrawling() && super.canContinueToUse();
        }
    }

    private static class CamerocerasCrawlAroundGoal extends RandomStrollGoal {

        private final Cameroceras cameroceras;

        public CamerocerasCrawlAroundGoal(Cameroceras cameroceras) {
            super(cameroceras, 1.0D);
            this.cameroceras = cameroceras;
        }

        @Override
        public boolean canUse() {
            return cameroceras.isInWaterOrBubble() && cameroceras.isCrawling() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return cameroceras.isInWaterOrBubble() && cameroceras.isCrawling() && super.canContinueToUse();
        }

        @Override
        @Nullable
        protected Vec3 getPosition() {
            for (int i = 0; i < 8; i++) {
                Vec3 pos = DefaultRandomPos.getPos(cameroceras, 10, 7);
                if (pos != null) {
                    BlockPos blockPos = BlockPos.containing(pos);
                    if (cameroceras.level().getFluidState(blockPos).is(FluidTags.WATER)) {
                        return pos;
                    }
                }
            }
            return null;
        }
    }
}
