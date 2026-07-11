package com.barl_inc.unusual_prehistory.entity.mob.base;

import com.barl_inc.unusual_prehistory.entity.ai.goals.FlyingWanderGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.LandFromFlightGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class PrehistoricWallAttachingFlyingMob extends PrehistoricFlyingMob {

    private static final EntityDataAccessor<Boolean> ATTACHED = SynchedEntityData.defineId(PrehistoricWallAttachingFlyingMob.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> ATTACHED_X = SynchedEntityData.defineId(PrehistoricWallAttachingFlyingMob.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ATTACHED_Y = SynchedEntityData.defineId(PrehistoricWallAttachingFlyingMob.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> ATTACHED_Z = SynchedEntityData.defineId(PrehistoricWallAttachingFlyingMob.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Direction> ATTACHED_FACE = SynchedEntityData.defineId(PrehistoricWallAttachingFlyingMob.class, EntityDataSerializers.DIRECTION);

    private BlockPos attachBlockPos;
    private Vec3 attachLocation;
    private Direction targetAttachFace;
    private int attachCooldown = 150;
    private int attachTicks = 0;
    private int tryAttachTicks = 0;

    protected PrehistoricWallAttachingFlyingMob(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        this.tickAttaching();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (hurt) {
            this.stopAttachment();
        }
        return hurt;
    }

    protected int getAttachCooldown() {
        return 150 + this.getRandom().nextInt(150);
    }

    protected int getMaxAttachTime() {
        return 800;
    }

    private void tickAttaching() {
        if (!this.level().isClientSide) {
            if (tryAttachTicks > 0) {
                this.tryAttachTicks++;
            }
            if (attachCooldown > 0) {
                this.attachCooldown--;
            }
            if (!this.isAttached() && attachLocation != null) {
                double dist = attachLocation.distanceTo(this.position());
                if (dist < 1) {
                    this.getMoveControl().setWantedPosition(attachLocation.x, attachLocation.y, attachLocation.z, 0.9D);
                    this.getNavigation().stop();
                    if (!this.isAttachedToFace()) {
                        this.attachCooldown = this.getAttachCooldown();
                        this.tryAttachTicks = 300 - 40;
                        this.setAttachmentPos(attachLocation);
                        this.setAttachmentFace(targetAttachFace);
                    }
                    if (dist < 0.2) {
                        this.startAttachment();
                    }
                }
            }
            if (this.isAttached()) {
                this.attachTicks++;
                this.setDeltaMovement(Vec3.ZERO);
            }
            if (this.isAttachedToFace() && attachBlockPos != null) {
                this.setYRot(0);
                this.setXRot(0);
                this.yBodyRot = 0;
                this.yHeadRot = 0;
                if (!this.level().getBlockState(attachBlockPos).isFaceSturdy(this.level(), attachBlockPos, this.getAttachmentFace())) {
                    this.stopAttachment();
                }
                if (this.isAttached()) {
                    if (attachTicks > this.getMaxAttachTime() && this.getRandom().nextInt(100) == 0 || this.getTarget() != null) {
                        this.stopAttachment();
                    }
                }
            }
            if (tryAttachTicks > 300) {
                this.stopAttachment();
            }
        } else {
            if (this.isAttachedToFace()) {
                this.setYRot(0);
                this.setXRot(0);
                this.yBodyRot = 0;
                this.yHeadRot = 0;
            }
        }
    }

    public void setAttachTarget(BlockPos attachBlockPos, Direction attachFace) {
        double rad = this.getBbWidth() / 2;
        Vec3 pos = Vec3.atCenterOf(attachBlockPos).add((0.5 + rad) * attachFace.getStepX(), 0, (0.5 + rad) * attachFace.getStepZ());
        double randomOffset = (this.getRandom().nextDouble() * 2 - 1) * (0.5 - rad - 1.0E-5F);
        pos = pos.add(attachFace.getClockWise().getStepX() * randomOffset, (this.getRandom().nextFloat() * 2 - 1) * 0.3F, attachFace.getClockWise().getStepZ() * randomOffset);
        this.setAttachTarget(attachBlockPos, attachFace, pos);
    }

    public void setAttachTarget(BlockPos attachBlockPos, Direction attachFace, Vec3 attachLocation) {
        this.attachBlockPos = attachBlockPos;
        this.targetAttachFace = attachFace;
        this.attachLocation = attachLocation;
        this.tryAttachTicks = 0;
    }

    public void startAttachment() {
        this.attachCooldown = this.getAttachCooldown();
        this.attachTicks = 0;
        this.tryAttachTicks = 0;
        this.setAttached(true);
        this.setPos(attachLocation.x, this.getY(), attachLocation.z);
        this.setDeltaMovement(Vec3.ZERO);
        this.setFlying(false);
    }

    public void stopAttachment() {
        this.attachBlockPos = null;
        this.targetAttachFace = null;
        this.attachLocation = null;
        this.tryAttachTicks = 0;
        this.attachCooldown = this.getAttachCooldown();
        this.setAttached(false);
        this.setAttachmentPos(Vec3.ZERO);
        this.setAttachmentFace(Direction.UP);
        this.setFlying(true);
    }

    public boolean isAttachedToFace() {
        return this.getAttachmentFace() != Direction.UP;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACHED, false);
        builder.define(ATTACHED_X, 0.0F);
        builder.define(ATTACHED_Y, 0.0F);
        builder.define(ATTACHED_Z, 0.0F);
        builder.define(ATTACHED_FACE, Direction.UP);
    }

    public boolean isAttached() {
        return this.entityData.get(ATTACHED);
    }

    public void setAttached(boolean attached) {
        this.entityData.set(ATTACHED, attached);
    }

    public Direction getAttachmentFace() {
        return this.entityData.get(ATTACHED_FACE);
    }

    private void setAttachmentFace(Direction direction) {
        this.entityData.set(ATTACHED_FACE, direction);
    }

    public Vec3 getAttachmentPos() {
        return new Vec3(this.entityData.get(ATTACHED_X), this.entityData.get(ATTACHED_Y), this.entityData.get(ATTACHED_Z));
    }

    private void setAttachmentPos(Vec3 location) {
        this.entityData.set(ATTACHED_X, (float) location.x);
        this.entityData.set(ATTACHED_Y, (float) location.y);
        this.entityData.set(ATTACHED_Z, (float) location.z);
    }

    // Goals
    public static class AttachingFlyingWanderGoal extends FlyingWanderGoal {

        protected final PrehistoricWallAttachingFlyingMob flyingMob;

        public AttachingFlyingWanderGoal(PrehistoricWallAttachingFlyingMob flyingMob, float speedModifier, int flightHeight) {
            super(flyingMob, speedModifier, flightHeight);
            this.flyingMob = flyingMob;
        }

        @Override
        public boolean canUse() {
            if (flyingMob.isAttachedToFace()) {
                return false;
            }
            return super.canUse();
        }
    }

    public static class LandOrAttachFromFlightGoal extends LandFromFlightGoal {

        private final PrehistoricWallAttachingFlyingMob flyingMob;

        public LandOrAttachFromFlightGoal(PrehistoricWallAttachingFlyingMob flyingMob, int maxFlightTime) {
            super(flyingMob, maxFlightTime);
            this.flyingMob = flyingMob;
        }

        @Override
        protected BlockPos findLandingSpot() {
            if (flyingMob.attachCooldown == 0) {
                for (int i = 0; i < 5; i++) {
                    BlockPos blockPos = flyingMob.blockPosition().offset(flyingMob.getRandom().nextInt(16) - 8, flyingMob.getRandom().nextInt(10) - 2, flyingMob.getRandom().nextInt(16) - 8);
                    BlockHitResult hitResult = flyingMob.level().clip(new ClipContext(flyingMob.getEyePosition(), Vec3.atCenterOf(blockPos), ClipContext.Block.COLLIDER, ClipContext.Fluid.WATER, flyingMob));
                    if (hitResult.getType() == HitResult.Type.MISS || hitResult.getDirection().getAxis().isVertical()) {
                        continue;
                    }
                    if (!flyingMob.level().getBlockState(hitResult.getBlockPos()).isFaceSturdy(flyingMob.level(), hitResult.getBlockPos(), hitResult.getDirection())) {
                        continue;
                    }
                    this.flyingMob.setAttachTarget(hitResult.getBlockPos().immutable(), hitResult.getDirection());
                    return hitResult.getBlockPos();
                }
            }
            return super.findLandingSpot();
        }
    }
}
