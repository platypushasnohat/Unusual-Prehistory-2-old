package com.barl_inc.unusual_prehistory.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricPanicGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricWanderGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.SaddlelessItemBasedSteering;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Arthropleura extends PrehistoricMob implements ItemSteerable, VariantHolder<Arthropleura.ArthropleuraVariant> {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Arthropleura.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BOOST_TIME = SynchedEntityData.defineId(Arthropleura.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SEGMENTS = SynchedEntityData.defineId(Arthropleura.class, EntityDataSerializers.INT);

    private final SaddlelessItemBasedSteering steering = new SaddlelessItemBasedSteering(this.entityData, BOOST_TIME);

    public Arthropleura(EntityType<? extends PrehistoricMob> type, Level level) {
        super(type, level);
        this.moveControl = new ArthropleuraMoveControl(this, 4);
        this.lookControl = new ArthropleuraLookControl(this, 6);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.225F)
                .add(Attributes.ARMOR, 12.0D)
                .add(Attributes.STEP_HEIGHT, 1.15D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ArthropleuraRidingGoal(this));
        this.goalSelector.addGoal(2, new PrehistoricPanicGoal(this, 1.5D, 20, 4) {
            @Override
            public boolean canUse() {
                return !Arthropleura.this.hasRidingPlayer() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !Arthropleura.this.hasRidingPlayer() && super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TEMPTS_ARTHROPLEURA), false) {
            @Override
            public boolean canUse() {
                return !Arthropleura.this.hasRidingPlayer() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !Arthropleura.this.hasRidingPlayer() && super.canContinueToUse();
            }
        });
        this.goalSelector.addGoal(4, new ArthropleuraWanderGoal(this));
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(UP2ItemTags.DIET_DETRITIVORE);
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return super.canCollideWith(entity) && !(entity instanceof ArthropleuraPart) && !(entity instanceof Arthropleura);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVector = travelVector.multiply(0.0, 1.0, 0.0);
        }
        this.floatWhileRidden(this, travelVector);
        super.travel(travelVector);
    }

    @Override
    protected void travelRidden(Player player, Vec3 travelVector) {
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return null;
    }

    @Nullable
    public Player getRidingPlayer() {
        Entity entity = this.getFirstPassenger();
        if (entity instanceof Player player) {
            if (player.getMainHandItem().is(UP2Items.BROWN_MUSHROOM_ON_A_STICK.get()) || player.getOffhandItem().is(UP2Items.BROWN_MUSHROOM_ON_A_STICK.get())) {
                return player;
            }
        }
        return null;
    }

    public boolean hasRidingPlayer() {
        return this.getRidingPlayer() != null;
    }

    @Override
    public double getFluidJumpThreshold() {
        if (this.isInWater() && horizontalCollision) {
            return super.getFluidJumpThreshold();
        }
        return 0.48D * this.getBbHeight();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult result = super.mobInteract(player, hand);
        ItemStack itemStack = player.getItemInHand(hand);
        Entity leashed = this.getLeashed(player).orElse(null);
        if (!this.isFood(itemStack) && !this.isBaby() && !itemStack.is(Items.LEAD)) {
            if (leashed instanceof Mob mob) {
                mob.stopRiding();
                mob.setLeashedTo(player, true);
                mob.startRiding(this, true);
                return InteractionResult.SUCCESS;
            }
            else if (player.isShiftKeyDown() && leashed == null) {
                for (Entity entity : this.getAllRidingEntities()) {
                    entity.stopRiding();
                    if (!this.level().isClientSide) {
                        yeetPassengers(entity);
                    }
                }
                for (Entity passenger : this.getPassengers()) {
                    if (this.getRidingPlayer() != passenger) {
                        passenger.stopRiding();
                        if (!this.level().isClientSide) {
                            yeetPassengers(passenger);
                        }
                    }
                }
                return InteractionResult.SUCCESS;
            }
            else {
                if (!this.isVehicle()) {
                    player.startRiding(this);
                }
                else {
                    for (Entity passenger : this.getPassengers()) {
                        if (this.getRidingPlayer() != passenger) {
                            passenger.stopRiding();
                            if (!this.level().isClientSide) {
                                yeetPassengers(passenger);
                            }
                        }
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return result;
    }

    public static void yeetPassengers(Entity passenger) {
        if (passenger instanceof LivingEntity living) {
            double x = (passenger.getRandom().nextDouble() - 0.5D) * 0.5D;
            double y = 0.25D + passenger.getRandom().nextDouble() * 0.25D;
            double z = (passenger.getRandom().nextDouble() - 0.5D) * 0.5D;
            living.setDeltaMovement(living.getDeltaMovement().add(x, y, z));
            living.hasImpulse = true;
        }
    }

    public boolean canEntityRide(LivingEntity entity) {
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

    public List<Entity> getAllRidingEntities() {
        return this.level().getEntities((Entity) null, this.getBoundingBox().inflate(32), entity -> entity.getVehicle() instanceof ArthropleuraPart part && part.getHeadEntity() == this);
    }

    public float getBoostFactor() {
        return this.steering.boostFactor();
    }

    public boolean isBoosting() {
        return this.steering.isBoosting();
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        return new Vec3(this.getX(), this.getBoundingBox().maxY + 0.1F, this.getZ());
    }

    @Override
    public boolean boost() {
        return this.steering.boost(this.getRandom());
    }

    @Override
    public void tick() {
        super.tick();
        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
        this.steering.tickBoost();
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        Arthropleura arthropleura = UP2Entities.ARTHROPLEURA.get().create(serverLevel);
        if (arthropleura != null) {
            serverLevel.getServer().execute(() -> {
                if (!arthropleura.isRemoved()) {
                    ArthropleuraPart.createArthropleuraSegments(arthropleura, 5 + serverLevel.getRandom().nextInt(3));
                }
            });
        }
        return arthropleura;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void push(Entity entity) {
        if (!this.isPassengerOfSameVehicle(entity) && !(entity instanceof ArthropleuraPart)) {
            super.push(entity);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FALL);
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(2);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (BOOST_TIME.equals(key) && this.level().isClientSide) {
            this.steering.onSynced();
        }
        if (SEGMENTS.equals(key)) {
            this.refreshDimensions();
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(10.0F + (10.0F * this.getSegments()));
            this.heal(this.getMaxHealth());
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(BOOST_TIME, 0);
        builder.define(SEGMENTS, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("Variant", this.getVariant().getId());
        compoundTag.putInt("Segments", this.getSegments());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        this.setVariant(ArthropleuraVariant.byId(compoundTag.getInt("Variant")));
        this.setSegments(compoundTag.getInt("Segments"));
    }

    @Override
    public ArthropleuraVariant getVariant() {
        return ArthropleuraVariant.byId(this.entityData.get(VARIANT));
    }

    @Override
    public void setVariant(ArthropleuraVariant variant) {
        this.entityData.set(VARIANT, Mth.clamp(variant.getId(), 0, ArthropleuraVariant.values().length));
    }

    public void setSegments(int segments) {
        this.entityData.set(SEGMENTS, segments);
    }

    public int getSegments() {
        return this.entityData.get(SEGMENTS);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.ARTHROPLEURA_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return UP2SoundEvents.ARTHROPLEURA_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.ARTHROPLEURA_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(UP2SoundEvents.ARTHROPLEURA_STEP.get(), 0.15F, 1.0F);
    }

    public enum ArthropleuraVariant {
        ROYAL(0),
        CRIMSON(1),
        GOLDEN(2),
        SCARAB(3),
        TRILOBITE(4);

        private final int id;

        ArthropleuraVariant(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static ArthropleuraVariant byId(int id) {
            if (id < 0 || id >= ArthropleuraVariant.values().length) {
                id = 0;
            }
            return ArthropleuraVariant.values()[id];
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
        int segmentAmount = 3 + level.getRandom().nextInt(5);
        this.setVariant(ArthropleuraVariant.byId(level.getRandom().nextInt(ArthropleuraVariant.values().length)));
        ArthropleuraPart.createArthropleuraSegments(this, segmentAmount);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
    }

    // Goals
    public static class ArthropleuraRidingGoal extends Goal {

        private final Arthropleura arthropleura;

        public ArthropleuraRidingGoal(Arthropleura arthropleura) {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            this.arthropleura = arthropleura;
        }

        @Override
        public void start() {
            this.arthropleura.getNavigation().stop();
        }

        @Override
        public boolean canUse() {
            return arthropleura.hasRidingPlayer() && arthropleura.isVehicle();
        }

        @Override
        public boolean canContinueToUse() {
            return arthropleura.hasRidingPlayer() && arthropleura.isVehicle();
        }

        @Override
        public void tick() {
            Player ridingPlayer = arthropleura.getRidingPlayer();
            if (ridingPlayer != null) {
                this.arthropleura.getNavigation().stop();
                double x = arthropleura.getX();
                double z = arthropleura.getZ();
                if (arthropleura.isVehicle()) {
                    Vec3 lookVec = ridingPlayer.getLookAngle();
                    x += lookVec.x * 10.0D;
                    z += lookVec.z * 10.0D;
                    this.arthropleura.getMoveControl().setWantedPosition(x, 0.0D, z, 1.25D);
                }
            }
        }
    }

    public static class ArthropleuraWanderGoal extends PrehistoricWanderGoal {

        private final Arthropleura arthropleura;
        @Nullable
        protected Vec3 wantedPos;

        public ArthropleuraWanderGoal(Arthropleura arthropleura) {
            super(arthropleura, 1.0D, 20, 7, 80, true);
            this.arthropleura = arthropleura;
        }

        @Override
        public boolean canUse() {
            return !arthropleura.hasRidingPlayer() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            this.wantedPos = new Vec3(wantedX, wantedY, wantedZ);
            return !arthropleura.hasRidingPlayer() && super.canContinueToUse() && !(wantedPos.distanceTo(arthropleura.position()) <= arthropleura.getBbWidth() * 4);
        }
    }

    private static class ArthropleuraMoveControl extends PrehistoricMoveControl {

        private final Arthropleura arthropleura;

        public ArthropleuraMoveControl(Arthropleura arthropleura, float maxYRotChange) {
            super(arthropleura, maxYRotChange);
            this.arthropleura = arthropleura;
        }

        @Override
        public void doMoveTo() {
            if (!prehistoricMob.refuseToMove()) {
                if (this.operation == Operation.MOVE_TO && (!mob.getNavigation().isDone() || arthropleura.hasRidingPlayer())) {
                    double xDiff = wantedX - mob.getX();
                    double zDiff = wantedZ - mob.getZ();
                    double horizontalDist = xDiff * xDiff + zDiff * zDiff;
                    if (horizontalDist < (double) 2.5000003E-7F) {
                        this.mob.setZza(0.0F);
                    } else {
                        if (horizontalDist > 0.12D) {
                            float yRot = (float) Mth.wrapDegrees(Mth.atan2(zDiff, xDiff) * Mth.RAD_TO_DEG - 90.0F);
                            float currentYRot = mob.getYRot();
                            float yDelta = Mth.wrapDegrees(yRot - mob.getYRot());
                            float moveAngle = currentYRot + yDelta * this.getTurnSpeed();
                            this.mob.setYRot(this.rotlerp(mob.getYRot(), moveAngle, maxYRotChange));
                        }
                        float speed = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                        if (arthropleura.hasRidingPlayer()) {
                            this.mob.setSpeed(speed * arthropleura.getBoostFactor());
                        } else {
                            this.mob.setSpeed(speed);
                        }
                        float zza = Mth.cos(mob.getXRot() * ((float) Math.PI / 180F));
                        float yya = Mth.sin(mob.getXRot() * ((float) Math.PI / 180F));
                        this.mob.zza = zza * speed;
                        this.mob.yya = -yya * speed;
                    }
                } else {
                    this.mob.setSpeed(0.0F);
                    this.mob.setXxa(0.0F);
                    this.mob.setYya(0.0F);
                    this.mob.setZza(0.0F);
                }
            }
        }

        private float getTurnSpeed() {
            if (arthropleura.hasRidingPlayer()) {
                if (!arthropleura.isBoosting()) {
                    return 0.17F;
                }
                float factor = 1.0F - Mth.sin(arthropleura.steering.getBoostProgress() * (float) Math.PI);
                factor = Mth.lerp(factor * factor, 0.02F / 0.17F, 1.0F);
                return 0.17F * factor;
            } else {
                return 0.17F;
            }
        }
    }

    private static class ArthropleuraLookControl extends PrehistoricLookControl {

        private final int maxYRotFromCenter;

        public ArthropleuraLookControl(PrehistoricMob mob, int maxYRotFromCenter) {
            super(mob);
            this.maxYRotFromCenter = maxYRotFromCenter;
        }

        @Override
        public void tick() {
            if (!mob.refuseToLook()) {
                if (this.resetXRotOnTick()) {
                    this.mob.setXRot(0.0F);
                }
                if (this.lookAtCooldown > 0) {
                    this.lookAtCooldown--;
                    this.getYRotD().ifPresent(f -> this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, f + 20.0F, this.yMaxRotSpeed));
                } else {
                    this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, this.yMaxRotSpeed);
                }
                float f = Mth.wrapDegrees(this.mob.yHeadRot - this.mob.yBodyRot);
                if (f < (float) (-this.maxYRotFromCenter)) {
                    this.mob.yBodyRot -= 4.0F;
                } else if (f > (float) this.maxYRotFromCenter) {
                    this.mob.yBodyRot += 4.0F;
                }
            }
        }
    }
}