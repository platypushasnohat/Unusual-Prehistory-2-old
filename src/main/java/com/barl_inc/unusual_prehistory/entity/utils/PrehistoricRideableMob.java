package com.barl_inc.unusual_prehistory.entity.utils;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public interface PrehistoricRideableMob {

    default void floatInWaterWhileRidden(PrehistoricMob mob) {
        if (mob.isVehicle() && mob.getFluidHeight(FluidTags.WATER) > mob.getFluidJumpThreshold()) {
            mob.setDeltaMovement(mob.getDeltaMovement().add(0.0, 0.04F, 0.0));
        }
    }

    default void floatWhileRidden(PrehistoricMob mob, Vec3 travelVec) {
        if (mob.isInWater() || (mob.isInFluidType(mob.getEyeInFluidType()) && !mob.moveInFluid(mob.level().getFluidState(BlockPos.containing(mob.getEyePosition())), travelVec, mob.getAttributeValue(Attributes.GRAVITY)))) {
            this.floatInWaterWhileRidden(mob);
        }
    }

    @Nullable
    default Vec3 getDismountLocationInDirection(PrehistoricMob mob, Vec3 direction, LivingEntity passenger) {
        double directionX = mob.getX() + direction.x;
        double minY = mob.getBoundingBox().minY;
        double directionZ = mob.getZ() + direction.z;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (Pose pose : passenger.getDismountPoses()) {
            mutableBlockPos.set(directionX, minY, directionZ);
            double maxY = mob.getBoundingBox().maxY + 0.75F;
            do {
                double floorHeight = mob.level().getBlockFloorHeight(mutableBlockPos);
                if ((double) mutableBlockPos.getY() + floorHeight > maxY) {
                    break;
                }
                if (DismountHelper.isBlockFloorValid(floorHeight)) {
                    AABB aabb = passenger.getLocalBoundsForPose(pose);
                    Vec3 vec3 = new Vec3(directionX, (double) mutableBlockPos.getY() + floorHeight, directionZ);
                    if (DismountHelper.canDismountTo(mob.level(), passenger, aabb.move(vec3))) {
                        passenger.setPose(pose);
                        return vec3;
                    }
                }
                mutableBlockPos.move(Direction.UP);
            }
            while (!((double) mutableBlockPos.getY() < maxY));
        }
        return null;
    }

    default boolean shouldStepDown(PrehistoricMob mob) {
        if (!(mob.getControllingPassenger() instanceof Player)) {
            return false;
        }
        if (mob.onGround() || this instanceof LeapingMob leapingMob && leapingMob.isLeaping()) {
            return false;
        }
        return mob.fallDistance > 0.0F && mob.fallDistance < 0.2F && this.canStepDownBlock(mob);
    }

    default boolean canStepDownBlock(PrehistoricMob mob) {
        Level level = mob.level();
        BlockPos pos = mob.blockPosition();
        if (!level.getBlockState(pos.below()).getCollisionShape(level, pos.below()).isEmpty()) return true;
        return !level.getBlockState(pos.below(2)).getCollisionShape(level, pos.below(2)).isEmpty();
    }
}
