package com.barlinc.unusual_prehistory.entity.mob.base;

import com.barlinc.unusual_prehistory.entity.ai.navigation.SmoothAmphibiousNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public abstract class AmbientWaterCrawlingMob extends AmbientMob {

    private static final EntityDataAccessor<Boolean> CRAWLING = SynchedEntityData.defineId(AmbientWaterCrawlingMob.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> CRAWLING_COOLDOWN = SynchedEntityData.defineId(AmbientWaterCrawlingMob.class, EntityDataSerializers.INT);

    protected AmbientWaterCrawlingMob(EntityType<? extends AmbientMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothAmphibiousNavigation(this, level);
    }

    @Override
    public boolean canDrownInFluidType(@NotNull FluidType fluidType) {
        return fluidType != NeoForgeMod.WATER_TYPE.value();
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.setAirSupply(300);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            if (!this.isCrawling()) {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.9F));
            } else {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.isInWaterOrBubble()) {
                if (this.getCrawlingCooldown() > 0) {
                    this.setCrawlingCooldown(this.getCrawlingCooldown() - 1);
                }
                if (this.getCrawlingCooldown() == 0 && this.getRandom().nextFloat() < 0.02F) {
                    boolean currentlyWalking = this.isCrawling();
                    this.setCrawling(!currentlyWalking);
                    this.setCrawlingCooldown(this.getRandom().nextInt(400) + 400);
                    this.getNavigation().stop();
                }
                if (this.isCrawling() && this.getCrawlingCooldown() > 20) {
                    this.addDeltaMovement(new Vec3(0.0F, -0.04, 0.0F));
                } else {
                    this.addDeltaMovement(new Vec3(0.0F, 0.0015, 0.0F));
                }
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CRAWLING, false);
        builder.define(CRAWLING_COOLDOWN, 400 + this.getRandom().nextInt(400));
    }

    public boolean isCrawling() {
        return this.entityData.get(CRAWLING);
    }

    public void setCrawling(boolean crawling) {
        this.entityData.set(CRAWLING, crawling);
    }

    public int getCrawlingCooldown() {
        return this.entityData.get(CRAWLING_COOLDOWN);
    }

    public void setCrawlingCooldown(int cooldown) {
        this.entityData.set(CRAWLING_COOLDOWN, cooldown);
    }

    public static class WaterCrawlerRandomSwimGoal extends RandomSwimmingGoal {

        protected final AmbientWaterCrawlingMob ambientMob;

        public WaterCrawlerRandomSwimGoal(AmbientWaterCrawlingMob ambientMob, double speedModifier, int interval) {
            super(ambientMob, speedModifier, interval);
            this.ambientMob = ambientMob;
        }

        @Override
        public boolean canUse() {
            return ambientMob.isInWaterOrBubble() && !ambientMob.onGround() && !ambientMob.isCrawling() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return ambientMob.isInWaterOrBubble() && !ambientMob.onGround() && super.canContinueToUse();
        }
    }

    public static class WaterCrawlerRandomStrollGoal extends RandomStrollGoal {

        private final AmbientWaterCrawlingMob ambientMob;

        public WaterCrawlerRandomStrollGoal(AmbientWaterCrawlingMob ambientMob, double speedModifier) {
            super(ambientMob, speedModifier);
            this.ambientMob = ambientMob;
        }

        @Override
        public boolean canUse() {
            return ambientMob.isInWaterOrBubble() && ambientMob.onGround() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return ambientMob.isInWaterOrBubble() && ambientMob.onGround() && super.canContinueToUse();
        }
    }

    public static class WaterCrawlerFindWaterGoal extends MoveToBlockGoal {

        protected final AmbientWaterCrawlingMob ambientMob;

        public WaterCrawlerFindWaterGoal(AmbientWaterCrawlingMob ambientMob, double speedModifier) {
            super(ambientMob, speedModifier, 16);
            this.ambientMob = ambientMob;
        }

        @Override
        public boolean canUse() {
            if (ambientMob.isInWater()) {
                return false;
            }
            return super.canUse();
        }

        @Override
        protected @NotNull BlockPos getMoveToTarget() {
            return this.blockPos;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !ambientMob.isInWater();
        }

        @Override
        protected boolean isValidTarget(LevelReader level, @NotNull BlockPos pos) {
            return level.getBlockState(pos).is(Blocks.WATER);
        }
    }
}

