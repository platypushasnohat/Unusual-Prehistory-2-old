package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FlyingWanderGoal extends Goal {

    protected final PrehistoricFlyingMob mob;
    protected final double speedModifier;
    protected final int flightHeight;
    protected int pathCooldown = 0;
    protected double x;
    protected double y;
    protected double z;

    public FlyingWanderGoal(PrehistoricFlyingMob mob, double speedModifier, int flightHeight) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.flightHeight = flightHeight;
        this.speedModifier = speedModifier;
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        if (mob.isEepy() || mob.isVehicle() || (mob.getTarget() != null && mob.getTarget().isAlive())) {
            return false;
        } else if (pathCooldown-- > 0) {
            return false;
        } else if (mob.canFly() && !mob.isPassenger()) {
            if (!mob.isFlying() && mob.getRandom().nextInt(200) != 0 && mob.fallDistance < 2.0F) {
                return false;
            } else if (!mob.isFlying() && !mob.level().getBlockState(BlockPos.containing(mob.getEyePosition()).above()).isAir()) {
                return false;
            } else {
                Vec3 target = this.findFlightPos();
                this.x = target.x;
                this.y = target.y;
                this.z = target.z;
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        if (mob.onGround()) {
            this.mob.addDeltaMovement(new Vec3(0.0F, 0.3F, 0.0F));
        }
        this.mob.setFlying(true);
        this.pathCooldown = mob.getRandom().nextInt(5);
        this.mob.getNavigation().moveTo(x, y, z, speedModifier);
    }

    @Override
    public boolean canContinueToUse() {
        return mob.isFlying() && !mob.getNavigation().isDone() && mob.canFly();
    }

    protected Vec3 findFlightPos() {
        Vec3 forward = mob.getViewVector(1.0F).normalize();
        Vec3 motion = mob.getDeltaMovement();
        Vec3 direction = motion.lengthSqr() > 0.02 ? motion.normalize() : forward;
        double forwardDist = (double) 6.0F + mob.getRandom().nextDouble() * (double) 8.0F;
        double sideways = (mob.getRandom().nextDouble() - (double) 0.5F) * (double) 16.0F;
        Vec3 right = new Vec3(-direction.z, 0.0F, direction.x);
        Vec3 target = mob.position().add(direction.scale(forwardDist)).add(right.scale(sideways));
        target = this.adjustFlightHeight(target);
        return this.clipFlightTarget(target);
    }

    @SuppressWarnings("deprecation")
    protected Vec3 adjustFlightHeight(Vec3 target) {
        BlockPos pos = BlockPos.containing(target);
        int desiredAboveGround = flightHeight + mob.getRandom().nextInt(3);
        BlockPos ground;
        for (ground = pos.below(); ground.getY() > mob.level().getMinBuildHeight() && !mob.level().getBlockState(ground).isSolid() && mob.level().getFluidState(ground).isEmpty(); ground = ground.below()) {
        }
        double desiredY = ground.getY() + desiredAboveGround;
        double y = Mth.clamp(desiredY, mob.getY() - (double) 3.0F, mob.getY() + (double) 3.0F);
        return new Vec3(target.x, y, target.z);
    }

    protected Vec3 clipFlightTarget(Vec3 target) {
        Vec3 start = mob.position().add(0.0F, (double) mob.getBbHeight() * (double) 0.5F, 0.0F);
        BlockHitResult hit = mob.level().clip(new ClipContext(start, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob));
        if (hit.getType() == HitResult.Type.MISS) {
            return target;
        } else {
            Vec3 hitPos = hit.getLocation();
            Vec3 normal = Vec3.atLowerCornerOf(hit.getDirection().getNormal());
            return hitPos.add(normal.scale(0.75F));
        }
    }
}
