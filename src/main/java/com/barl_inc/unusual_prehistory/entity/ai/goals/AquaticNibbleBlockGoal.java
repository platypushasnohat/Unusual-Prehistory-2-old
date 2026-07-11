package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class AquaticNibbleBlockGoal extends Goal {

    protected final PrehistoricAquaticMob aquaticMob;
    protected int digTime;
    private final int digTimeLimit;
    private int timeOut = 400;
    private final int cooldown;
    protected BlockPos digPos = null;
    protected TagKey<Block> foodBlocks;
    protected final double speedModifier;

    public AquaticNibbleBlockGoal(PrehistoricAquaticMob aquaticMob, TagKey<Block> foodBlocks) {
        this(aquaticMob, 20, 600, foodBlocks, 1.1D);
    }

    public AquaticNibbleBlockGoal(PrehistoricAquaticMob aquaticMob, int digTime, int cooldown, TagKey<Block> foodBlocks, double speedModifier) {
        this.foodBlocks = foodBlocks;
        this.aquaticMob = aquaticMob;
        this.digTime = digTime;
        this.digTimeLimit = digTime;
        this.cooldown = cooldown;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        if (aquaticMob.getEatCooldown() <= 0 && aquaticMob.isInWater()) {
            this.aquaticMob.setEatCooldown(600 + aquaticMob.getRandom().nextInt(600 * 4));
            this.digPos = this.getDigPos();
            this.timeOut = 800;
            return this.digPos != null;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return aquaticMob.getTarget() == null && aquaticMob.getLastHurtByMob() == null && digPos != null && aquaticMob.level().getBlockState(digPos).is(foodBlocks) && aquaticMob.level().getFluidState(digPos.above()).is(FluidTags.WATER) && timeOut >= 0;
    }

    @Override
    public void start() {
        this.digTime += aquaticMob.getRandom().nextInt(10);
        this.aquaticMob.getNavigation().moveTo(((float) digPos.getX()) + 0.5D, digPos.getY(), ((float) digPos.getZ()) + 0.5D, speedModifier);
    }

    @Override
    public void tick() {
        double dist = aquaticMob.position().distanceTo(Vec3.atCenterOf(digPos));
        double dy = digPos.getY() + 0.5 - aquaticMob.getY();
        double dx = digPos.getX() + 0.5 - aquaticMob.getX();
        double dz = digPos.getZ() + 0.5 - aquaticMob.getZ();
        float yaw = (float) (Mth.atan2(dz, dx) * 57.2957763671875D) - 90.0F;
        float pitch = (float) -(Mth.atan2(dy, Math.hypot(dx, dz)) * 57.2957763671875D);

        this.timeOut--;

        if (dist < aquaticMob.getBoundingBox().getXsize() + 1) {
            this.aquaticMob.setYRot(yaw);
            this.aquaticMob.setXRot(pitch);
            this.digTime--;
            if (dist < aquaticMob.getBoundingBox().getXsize()) {
                this.aquaticMob.getNavigation().stop();
            }

            if (digTime % 5 == 0) {
                this.spawnEffectsAtBlock(digPos);
                this.aquaticMob.playSound(aquaticMob.level().getBlockState(digPos).getSoundType().getHitSound(), 0.2F, 0.8F + aquaticMob.getRandom().nextFloat() * 0.25F);
            }

            if (digTime <= 0) {
                this.digPos = null;
            }
        } else {
            this.aquaticMob.getNavigation().moveTo(((float) digPos.getX()) + 0.5D, digPos.getY(), ((float) digPos.getZ()) + 0.5D, speedModifier);
            if (timeOut <= 0) {
                this.digPos = null;
            }
        }
    }

    @Override
    public void stop() {
        this.aquaticMob.setEatCooldown(cooldown + (aquaticMob.getRandom().nextInt(cooldown * 4)));
        this.digPos = null;
        this.digTime = digTimeLimit;
        this.timeOut = 400;
    }

    private BlockPos getSeafloorPos(BlockPos parent) {
        LevelAccessor world = aquaticMob.level();
        final RandomSource random = aquaticMob.getRandom();
        int range = 15;
        for (int i = 0; i < 25; i++) {
            BlockPos seafloor = parent.offset(random.nextInt(range) - range / 2, 0, random.nextInt(range) - range / 2);
            while (world.getFluidState(seafloor).is(FluidTags.WATER) && seafloor.getY() > 1) {
                BlockState state = world.getBlockState(seafloor);
                if (state.is(foodBlocks)) {
                    return seafloor;
                }
                seafloor = seafloor.below();
            }
            BlockState state = world.getBlockState(seafloor);
            if (state.is(foodBlocks)) {
                return seafloor;
            }
        }
        return null;
    }

    private BlockPos getDigPos() {
        final RandomSource random = aquaticMob.getRandom();
        int range = 15;
        if (aquaticMob.isInWater()) {
            return getSeafloorPos(aquaticMob.blockPosition());
        } else {
            for (int i = 0; i < 25; i++) {
                BlockPos blockpos1 = aquaticMob.blockPosition().offset(random.nextInt(range) - range / 2, 3, random.nextInt(range) - range / 2);
                while (aquaticMob.level().isEmptyBlock(blockpos1) && blockpos1.getY() > 1) {
                    blockpos1 = blockpos1.below();
                }
                if (aquaticMob.level().getFluidState(blockpos1).is(FluidTags.WATER)) {
                    BlockPos pos3 = getSeafloorPos(blockpos1);
                    if (pos3 != null && pos3.getY() < aquaticMob.getY()) {
                        return pos3;
                    }
                }
            }
        }
        return null;
    }

    public void spawnEffectsAtBlock(BlockPos blockPos) {
        float radius = 0.3F;
        for (int i1 = 0; i1 < 3; i1++) {
            double motionX = aquaticMob.getRandom().nextGaussian() * 0.07D;
            double motionY = aquaticMob.getRandom().nextGaussian() * 0.07D;
            double motionZ = aquaticMob.getRandom().nextGaussian() * 0.07D;
            float angle = (float) ((0.0174532925 * aquaticMob.yBodyRot) + i1);
            double extraX = radius * Mth.sin(Mth.PI + angle);
            double extraY = 0.8F;
            double extraZ = radius * Mth.cos(angle);
            BlockState state = aquaticMob.level().getBlockState(blockPos);
            ((ServerLevel) aquaticMob.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), blockPos.getX() + 0.5 + extraX, blockPos.getY() + 0.5 + extraY, blockPos.getZ() + 0.5 + extraZ, 1, motionX, motionY, motionZ, 1);
        }
    }
}