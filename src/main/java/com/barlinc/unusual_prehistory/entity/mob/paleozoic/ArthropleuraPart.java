package com.barlinc.unusual_prehistory.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2EntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ArthropleuraPart extends Entity {

    private static final EntityDataAccessor<Optional<UUID>> HEAD_ENTITY_UUID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> HEAD_ENTITY_ID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Optional<UUID>> FRONT_ENTITY_UUID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> FRONT_ENTITY_ID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Optional<UUID>> BACK_ENTITY_UUID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> BACK_ENTITY_ID = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(ArthropleuraPart.class, EntityDataSerializers.INT);

    private double prevHeadSpeed;

    public boolean renderHurtFlag = false;
    private boolean wasPreviouslyBaby;

    private int lSteps;
    private double lx;
    private double ly;
    private double lz;
    private double lyr;
    private double lxr;
    private double lxd;
    private double lyd;
    private double lzd;

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();

    public ArthropleuraPart(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isPickable() {
        Entity head = this.getHeadEntity();
        return head != null && head.isPickable();
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(UP2Items.ARTHROPLEURA_SPAWN_EGG.get());
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        Entity head = this.getHeadEntity();
        if (!this.isInvulnerableTo(source) && head != null) {
            head.hurt(source, amount);
        }
        return false;
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FALL);
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return super.canCollideWith(entity) && !(entity instanceof ArthropleuraPart) && !(entity instanceof Arthropleura);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public boolean isControlledByLocalInstance() {
        return false;
    }

    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        InteractionResult result = super.interact(player, hand);
        ItemStack itemStack = player.getItemInHand(hand);
        if (this.getBackEntity() != null && !(this.getHeadEntity() instanceof Arthropleura arthropleura && arthropleura.isBaby() && !arthropleura.isFood(itemStack))) {
            Entity leashed = this.getLeashed(player).orElse(null);
            if (leashed instanceof Mob mob) {
                mob.stopRiding();
                mob.setLeashedTo(player, true);
                mob.startRiding(this, true);
                return InteractionResult.SUCCESS;
            }
            if (!this.isVehicle()) {
                player.startRiding(this);
            }
            else {
                for (Entity passenger : this.getPassengers()) {
                    if (this.getControllingPassenger() != passenger) {
                        passenger.stopRiding();
                        if (!this.level().isClientSide) {
                            Arthropleura.yeetPassengers(passenger);
                        }
                    }
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            return result;
        }
    }

    public boolean canEntityRide(LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        if (entity.getType().is(UP2EntityTags.ARTHROPLEURA_CANT_CARRY)) {
            return false;
        }
        return (entity.getBbWidth() < this.getBbWidth() * 2.0F && entity.getBbHeight() < this.getBbHeight() * 4.0F) || entity.getType().is(UP2EntityTags.ARTHROPLEURA_CAN_CARRY);
    }

    public Optional<Entity> getLeashed(Player player) {
        List<Entity> entities = player.level().getEntities((Entity) null, player.getBoundingBox().inflate(10), entity -> true);
        for (Entity entity : entities) {
            if (entity instanceof Mob mob && mob.getLeashHolder() == player && this.canEntityRide(mob)) {
                return Optional.of(mob);
            }
        }
        return Optional.empty();
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        float scale = this.getHeadEntity() instanceof Arthropleura arthropleura ? arthropleura.getAgeScale() : 1.0F;
        return super.getDimensions(pose).scale(scale);
    }

    @Override
    public void tick() {
        super.tick();

        Entity head = this.getHeadEntity();
        Entity front = this.getFrontEntity();
        Entity back = this.getBackEntity();

        if (head instanceof Arthropleura arthropleura && arthropleura.isBaby() != wasPreviouslyBaby) {
            this.wasPreviouslyBaby = arthropleura.isBaby();
            this.refreshDimensions();
        }

        if (this.level().isClientSide) {
            this.setupAnimationStates();
            if (head instanceof Arthropleura arthropleura) {
                this.renderHurtFlag = arthropleura.hurtTime > 0 || arthropleura.deathTime > 0;
            }
            if (this.lSteps > 0) {
                double x = this.getX() + (this.lx - this.getX()) / (double) this.lSteps;
                double y = this.getY() + (this.ly - this.getY()) / (double) this.lSteps;
                double z = this.getZ() + (this.lz - this.getZ()) / (double) this.lSteps;
                double lerpRot = Mth.wrapDegrees(this.lyr - (double) this.getYRot());
                this.setYRot(this.getYRot() + (float) lerpRot / (float) this.lSteps);
                this.setXRot(this.getXRot() + (float) (this.lxr - (double) this.getXRot()) / (float) this.lSteps);
                this.lSteps--;
                this.setPos(x, y, z);
            } else {
                this.reapplyPosition();
            }
        }
        else {
            this.entityData.set(HEAD_ENTITY_ID, head != null ? head.getId() : -1);
            this.entityData.set(FRONT_ENTITY_ID, front != null ? front.getId() : -1);
            this.entityData.set(BACK_ENTITY_ID, back != null ? back.getId() : -1);
            if (front == null || head == null) {
                if (tickCount > 3) {
                    this.discard();
                }
            } else {
                this.tickPartPosition(head, front, back);
                if (this.isHeadMoving()) {
                    this.tickPartRotation(front, Math.max(0.1F, 0.18F - (this.getIndex() * 0.001F)));
                }
            }
        }
    }

    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    private void tickPartPosition(Entity head, Entity front, Entity back) {
        float scale = head instanceof Arthropleura arthropleura ? arthropleura.getAgeScale() : 1.0F;
        float offset = 1.35F * scale;

        double headSpeed = head.getDeltaMovement().horizontalDistance();
        double speed = Mth.lerp(0.5D, prevHeadSpeed, headSpeed);
        double speedDelta = speed - prevHeadSpeed;
        this.prevHeadSpeed = speed;

        if (this.getBackEntity() == null) {
            offset -= 0.19F * scale;
        }

        float wiggle = 0.0F;
        if (this.isHeadMoving()) {
            float speedScale = (float) Mth.clamp(speedDelta * 6.0D, -1.0D, 1.0D);
            float indexScale = 1.0F + (this.getIndex() * 0.08F);
            offset -= speedScale * 0.25F * scale * indexScale;
            wiggle = 0.07F * (float) Math.sin(head.tickCount * 0.15F - this.getIndex());
        }

        Vec3 target = front.position().add(new Vec3(wiggle, 0, -offset).xRot(-(float)Math.toRadians(front.getXRot())).yRot(-(float)Math.toRadians(front.getYRot())));

        if (head.isInFluidType()) {
            this.setPos(target.x, target.y, target.z);
        } else {
            double currentY = this.getY();
            double targetY = this.calculateSurfaceY(target, head, front, back);
            if (Math.abs(targetY - this.getY()) >= 0.03D) {
                currentY = targetY;
            }
            this.setPos(target.x, currentY, target.z);
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    private void tickPartRotation(Entity front, float speed) {
        Vec3 direction = front.position().add(new Vec3(0.0F, 0.0F, 1.0F).xRot(-(float) Math.toRadians(front.getXRot())).yRot(-(float) Math.toRadians(front.getYRot())));
        double xDirection = direction.x - this.getX();
        double yDirection = direction.y - this.getY();
        double zDirection = direction.z - this.getZ();
        double magnitude = Math.sqrt(xDirection * xDirection + zDirection * zDirection);
        float xRot = Mth.wrapDegrees((float) (-(Mth.atan2(yDirection, magnitude) * (double) (180.0F / (float) Math.PI))));
        float yRot = Mth.wrapDegrees((float) (Mth.atan2(zDirection, xDirection) * (double) (180.0F / (float) Math.PI)) - 90.0F);
        float yDelta = Mth.clamp(Mth.wrapDegrees(yRot - this.getYRot()), -40.0F, 40.0F);
        float xDelta = Mth.wrapDegrees(xRot - this.getXRot());
        this.setXRot(this.getXRot() + xDelta);
        this.setYRot(this.getYRot() + yDelta * speed);
    }

    private boolean isHeadMoving() {
        Entity head = this.getHeadEntity();
        if (head == null) {
            return false;
        }
        return head.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }

    private double calculateSurfaceY(Vec3 pos, Entity head, Entity front, Entity back) {
        Vec3 pushed = this.pushOutOfBlocks(pos);
        double posX = pos.x;
        double posY = pushed.y;
        double posZ = pos.z;

        double frontY = front.getY();
        double backY = back != null ? back.getY() : frontY;
        double average = (frontY + backY) * 0.5D;

        double terrainCenter = posY + (this.getLowPartHeight(posX, posY, posZ) + this.getHighPartHeight(posX, posY, posZ)) * 0.5D;
        double targetY = Mth.lerp(0.5D, average, terrainCenter);

        double t = Mth.clamp((double) this.getIndex() / (head instanceof Arthropleura arthropleura ? arthropleura.getSegments() : 5.0D), 0.0D, 1.0D);

        double down = Mth.lerp(t, 0.1D, 0.9D);
        double up = Mth.lerp(t, 0.1D, 0.9D);

        double minY = frontY - down;
        double maxY = frontY + up;

        return Mth.clamp(targetY, minY, maxY);
    }

    private Vec3 pushOutOfBlocks(Vec3 pos) {
        AABB aabb = this.getBoundingBox().move(pos.x - this.getX(), pos.y - this.getY(), pos.z - this.getZ());
        double pushY = 0.0D;
        BlockPos min = BlockPos.containing(aabb.minX, aabb.minY, aabb.minZ);
        BlockPos max = BlockPos.containing(aabb.maxX, aabb.maxY, aabb.maxZ);
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
                for (int z = min.getZ(); z <= max.getZ(); z++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    BlockState state = this.level().getBlockState(blockPos);
                    if (state.isAir()) {
                        continue;
                    }
                    VoxelShape shape = state.getCollisionShape(this.level(), blockPos);
                    if (shape.isEmpty()) {
                        continue;
                    }
                    AABB aabb1 = shape.bounds().move(blockPos);
                    if (!aabb.intersects(aabb1)) {
                        continue;
                    }
                    pushY += 0.03D;
                }
            }
        }
        return pos.add(0.0D, pushY, 0.0D);
    }

    public double getLowPartHeight(double x, double y, double z) {
        double check = 0.0D;
        while (check > -2.0D && !this.isOpaqueBlockAt(x,y + check, z)) {
            check -= 0.03D;
        }
        return check;
    }

    public double getHighPartHeight(double x, double y, double z) {
        double check = 0.0D;
        while (check <= 2.0D) {
            if (this.isOpaqueBlockAt(x, y + check, z)) {
                check += 0.03D;
            } else {
                break;
            }
        }
        return check;
    }

    public boolean isOpaqueBlockAt(double x, double y, double z) {
        if (this.noPhysics) {
            return false;
        }
        BlockPos pos = BlockPos.containing(x, y, z);
        BlockState state = this.level().getBlockState(pos);
        if (state.isAir()) {
            return false;
        }
        VoxelShape shape = state.getCollisionShape(this.level(), pos);
        if (shape.isEmpty()) {
            return false;
        }
        AABB aabb = shape.bounds().move(pos);
        return aabb.contains(x, y, z);
    }

    public static void createArthropleuraSegments(Arthropleura arthropleura, int count) {
        ArthropleuraPart prev = null;
        for (int i = 0; i < count; i++) {
            ArthropleuraPart current = new ArthropleuraPart(UP2Entities.ARTHROPLEURA_PART.get(), arthropleura.level());
            current.setHeadUUID(arthropleura.getUUID());
            current.setFrontEntityUUID(prev == null ? arthropleura.getUUID() : prev.getUUID());
            if (prev != null) {
                prev.setBackEntityUUID(current.getUUID());
            }
            current.setIndex(i);
            Entity front = prev == null ? arthropleura : prev;
            float scale = arthropleura.getAgeScale();
            float offset = 0.9F * scale;
            Vec3 pos = front.position().add(new Vec3(0.0F, 0.0F, -offset).xRot(-(float) Math.toRadians(front.getXRot())).yRot(-(float) Math.toRadians(front.getYRot())));
            current.setPos(pos);
            current.setYRot(arthropleura.getYRot());
            arthropleura.setSegments(arthropleura.getSegments() + 1);
            arthropleura.level().addFreshEntity(current);
            prev = current;
        }
    }

    @Override
    public @NotNull Vec3 getLightProbePosition(float partialTicks) {
        if (this.getHeadEntity() != null) {
            return this.getHeadEntity().getEyePosition(partialTicks);
        }
        return super.getLightProbePosition(partialTicks);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void push(@NotNull Entity entity) {
        if (!(entity instanceof ArthropleuraPart)) {
            super.push(entity);
        }
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return super.getBoundingBoxForCulling().inflate(2);
    }

    @Override
    public boolean shouldBeSaved() {
        return (this.getRemovalReason() == null || this.getRemovalReason().shouldSave()) && !this.isPassenger();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(HEAD_ENTITY_UUID, Optional.empty());
        builder.define(HEAD_ENTITY_ID, -1);
        builder.define(FRONT_ENTITY_UUID, Optional.empty());
        builder.define(FRONT_ENTITY_ID, -1);
        builder.define(BACK_ENTITY_UUID, Optional.empty());
        builder.define(BACK_ENTITY_ID, -1);
        builder.define(INDEX, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.hasUUID("HeadUUID")) {
            this.setHeadUUID(compoundTag.getUUID("HeadUUID"));
        }
        if (compoundTag.hasUUID("FrontUUID")) {
            this.setFrontEntityUUID(compoundTag.getUUID("FrontUUID"));
        }
        if (compoundTag.hasUUID("BackUUID")) {
            this.setBackEntityUUID(compoundTag.getUUID("BackUUID"));
        }
        this.setIndex(compoundTag.getInt("Index"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        if (this.getHeadUUID() != null) {
            compoundTag.putUUID("HeadUUID", this.getHeadUUID());
        }
        if (this.getFrontEntityUUID() != null) {
            compoundTag.putUUID("FrontUUID", this.getFrontEntityUUID());
        }
        if (this.getBackEntityUUID() != null) {
            compoundTag.putUUID("BackUUID", this.getBackEntityUUID());
        }
        compoundTag.putInt("Index", this.getIndex());
    }

    @Nullable
    public UUID getBackEntityUUID() {
        return this.entityData.get(BACK_ENTITY_UUID).orElse(null);
    }
    public void setBackEntityUUID(@Nullable UUID uniqueId) {
        this.entityData.set(BACK_ENTITY_UUID, Optional.ofNullable(uniqueId));
    }

    @Nullable
    public UUID getHeadUUID() {
        return this.entityData.get(HEAD_ENTITY_UUID).orElse(null);
    }
    public void setHeadUUID(@Nullable UUID uniqueId) {
        this.entityData.set(HEAD_ENTITY_UUID, Optional.ofNullable(uniqueId));
    }

    @Nullable
    public UUID getFrontEntityUUID() {
        return this.entityData.get(FRONT_ENTITY_UUID).orElse(null);
    }
    public void setFrontEntityUUID(@Nullable UUID uniqueId) {
        this.entityData.set(FRONT_ENTITY_UUID, Optional.ofNullable(uniqueId));
    }

    public Entity getHeadEntity() {
        if (!this.level().isClientSide) {
            UUID id = this.getHeadUUID();
            return id == null ? null : ((ServerLevel) this.level()).getEntity(id);
        } else {
            int id = this.entityData.get(HEAD_ENTITY_ID);
            return id == -1 ? null : this.level().getEntity(id);
        }
    }
    public Entity getFrontEntity() {
        if (!this.level().isClientSide) {
            UUID id = this.getFrontEntityUUID();
            return id == null ? null : ((ServerLevel) this.level()).getEntity(id);
        } else {
            int id = this.entityData.get(FRONT_ENTITY_ID);
            return id == -1 ? null : this.level().getEntity(id);
        }
    }
    public Entity getBackEntity() {
        if (!this.level().isClientSide) {
            UUID id = this.getBackEntityUUID();
            return id == null ? null : ((ServerLevel) this.level()).getEntity(id);
        } else {
            int id = this.entityData.get(BACK_ENTITY_ID);
            return id == -1 ? null : this.level().getEntity(id);
        }
    }

    public int getIndex() {
        return this.entityData.get(INDEX);
    }
    public void setIndex(int i) {
        this.entityData.set(INDEX, i);
    }

    @Override
    public void lerpTo(double x, double y, double z, float yr, float xr, int steps) {
        this.lx = x;
        this.ly = y;
        this.lz = z;
        this.lyr = yr;
        this.lxr = xr;
        this.lSteps = steps;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    @Override
    public void lerpMotion(double lerpX, double lerpY, double lerpZ) {
        this.lxd = lerpX;
        this.lyd = lerpY;
        this.lzd = lerpZ;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.ARTHROPLEURA_STEP.get(), 0.15F, 1.0F);
    }
}