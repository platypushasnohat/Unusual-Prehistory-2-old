package com.barl_inc.unusual_prehistory.utils;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class UP2MobUtils {

    public static float getDepthPathfindingFavor(BlockPos pos, LevelReader world) {
        int y = pos.getY() + Math.abs(world.getMinBuildHeight());
        return 1.0F / (float) (y == 0 ? 1 : y);
    }

    public static float getSurfacePathfindingFavor(BlockPos pos, LevelReader world) {
        int y = Math.abs(world.getMaxBuildHeight()) - pos.getY();
        return 1.0F / (float) (y == 0 ? 1 : y);
    }

    public static void travelInWater(PathfinderMob mob, Vec3 travelVector) {
        mob.moveRelative(mob.getSpeed(), travelVector);
        mob.move(MoverType.SELF, mob.getDeltaMovement());
        mob.setDeltaMovement(mob.getDeltaMovement().scale(0.9D));
        if (mob.horizontalCollision && mob.isEyeInFluid(FluidTags.WATER) && mob.isPathFinding()) {
            mob.setDeltaMovement(mob.getDeltaMovement().add(0.0, 0.005, 0.0));
        }
    }

    public static void savePrehistoricDataToBucket(PrehistoricMob mob, ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(mob, bucket);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, (compoundTag) -> {
            compoundTag.putInt("Age", mob.getAge());
            compoundTag.putBoolean("FromEgg", mob.isFromEgg());
            compoundTag.putInt("EatingCooldown", mob.getEatCooldown());
            compoundTag.putBoolean("Pacified", mob.isPacified());
            if (mob.getOwnerUUID() != null) {
                compoundTag.putUUID("Owner", mob.getOwnerUUID());
            }
        });
    }

    public static void loadPrehistoricDataFromBucket(PrehistoricMob mob, CompoundTag compoundTag) {
        Bucketable.loadDefaultDataFromBucketTag(mob, compoundTag);
        if (compoundTag.contains("Age")) {
            mob.setAge(compoundTag.getInt("Age"));
        }
        if (compoundTag.contains("Pacified")) {
            mob.setPacified(compoundTag.getBoolean("Pacified"));
        }
        if (compoundTag.contains("FromEgg")) {
            mob.setFromEgg(compoundTag.getBoolean("FromEgg"));
        }
        if (compoundTag.contains("EatingCooldown")) {
            mob.setEatCooldown(compoundTag.getInt("EatingCooldown"));
        }

        UUID uuid;
        if (compoundTag.hasUUID("Owner")) {
            uuid = compoundTag.getUUID("Owner");
        } else {
            String owner = compoundTag.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(Objects.requireNonNull(mob.getServer()), owner);
        }
        if (uuid != null) {
            try {
                mob.setOwnerUUID(uuid);
                mob.setTame(true, false);
            } catch (Throwable throwable) {
                mob.setTame(false, true);
            }
        }
    }
}
