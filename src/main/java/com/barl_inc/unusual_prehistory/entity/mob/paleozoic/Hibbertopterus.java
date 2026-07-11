 package com.barl_inc.unusual_prehistory.entity.mob.paleozoic;

 import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
 import com.barl_inc.unusual_prehistory.entity.ai.goals.AmphibiousPanicGoal;
 import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricWanderGoal;
 import com.barl_inc.unusual_prehistory.entity.ai.navigation.SmoothGroundNavigation;
 import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricAmphibiousMob;
 import com.barl_inc.unusual_prehistory.entity.utils.*;
 import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
 import com.barl_inc.unusual_prehistory.registry.UP2Entities;
 import com.barl_inc.unusual_prehistory.registry.UP2Items;
 import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
 import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
 import net.minecraft.core.BlockPos;
 import net.minecraft.core.Direction;
 import net.minecraft.network.syncher.EntityDataAccessor;
 import net.minecraft.network.syncher.EntityDataSerializers;
 import net.minecraft.network.syncher.SynchedEntityData;
 import net.minecraft.server.level.ServerLevel;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.tags.BlockTags;
 import net.minecraft.util.Mth;
 import net.minecraft.world.InteractionHand;
 import net.minecraft.world.InteractionResult;
 import net.minecraft.world.damagesource.DamageSource;
 import net.minecraft.world.entity.*;
 import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
 import net.minecraft.world.entity.ai.attributes.Attributes;
 import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
 import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
 import net.minecraft.world.entity.ai.goal.TemptGoal;
 import net.minecraft.world.entity.ai.navigation.PathNavigation;
 import net.minecraft.world.entity.player.Player;
 import net.minecraft.world.item.ItemStack;
 import net.minecraft.world.item.Items;
 import net.minecraft.world.item.context.UseOnContext;
 import net.minecraft.world.item.crafting.Ingredient;
 import net.minecraft.world.level.Level;
 import net.minecraft.world.level.block.Block;
 import net.minecraft.world.level.block.state.BlockState;
 import net.minecraft.world.level.gameevent.DynamicGameEventListener;
 import net.minecraft.world.level.gameevent.EntityPositionSource;
 import net.minecraft.world.level.gameevent.GameEvent;
 import net.minecraft.world.level.gameevent.PositionSource;
 import net.minecraft.world.level.pathfinder.PathType;
 import net.minecraft.world.phys.BlockHitResult;
 import net.minecraft.world.phys.Vec3;
 import net.neoforged.neoforge.common.ItemAbilities;
 import org.jetbrains.annotations.NotNull;
 import org.jetbrains.annotations.Nullable;

 import java.util.function.BiConsumer;

 public class Hibbertopterus extends PrehistoricAmphibiousMob implements ItemSteerable, DancingMob, PlushableMob {

     private static final EntityDataAccessor<Integer> PLOW_TIME = SynchedEntityData.defineId(Hibbertopterus.class, EntityDataSerializers.INT);
     protected static final EntityDataAccessor<Boolean> DANCING = SynchedEntityData.defineId(Hibbertopterus.class, EntityDataSerializers.BOOLEAN);

     private final SaddlelessItemBasedSteering steering = new SaddlelessItemBasedSteering(this.entityData, PLOW_TIME);

     private BlockPos jukeboxPosition;
     private final DynamicGameEventListener<JukeboxListener> dynamicJukeboxListener;

     public final SmoothAnimationState plowAnimationState = new SmoothAnimationState();
     public final SmoothAnimationState danceAnimationState = new SmoothAnimationState();

     public Hibbertopterus(EntityType<? extends PrehistoricAmphibiousMob> entityType, Level level) {
         super(entityType, level);
         this.setPathfindingMalus(PathType.WATER, 0.0F);
         this.setPathfindingMalus(PathType.WATER_BORDER, 0.0F);
         PositionSource source = new EntityPositionSource(this, this.getEyeHeight());
         this.dynamicJukeboxListener = new DynamicGameEventListener<>(new JukeboxListener(this, source, GameEvent.JUKEBOX_PLAY.value().notificationRadius()));
         this.moveControl = new PrehistoricSwimmingMoveControl(this, 20, 85, 0.6F, 0.8F);
     }

     public static AttributeSupplier.Builder createAttributes() {
         return Mob.createMobAttributes()
                 .add(Attributes.MAX_HEALTH, 36.0D)
                 .add(Attributes.MOVEMENT_SPEED, 0.17F)
                 .add(Attributes.ARMOR, 10.0D)
                 .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                 .add(Attributes.STEP_HEIGHT, 1.1D);
     }

     @Override
     protected void registerGoals() {
         this.goalSelector.addGoal(1, new AmphibiousPanicGoal(this, 1.8D));
         this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_DETRITIVORE), false));
         this.goalSelector.addGoal(3, new PrehistoricWanderGoal(this, 1.0D, false));
         this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
         this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
     }

     @Override
     public @NotNull PathNavigation createNavigation(@NotNull Level level) {
         return new SmoothGroundNavigation(this, level);
     }

     @Override
     public void travel(@NotNull Vec3 travelVector) {
         if (this.isEffectiveAi() && this.isInWater()) {
             this.moveRelative(this.getSpeed(), travelVector);
             this.move(MoverType.SELF, this.getDeltaMovement());
             this.setDeltaMovement(this.getDeltaMovement().scale(0.6F));
             if (this.jumping) {
                 this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.3D, 0.0D));
             } else {
                 this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.07D, 0.0D));
             }
             this.calculateEntityAnimation(false);
         } else {
             super.travel(travelVector);
         }
     }

     @Override
     public void updateDynamicGameEventListener(@NotNull BiConsumer<DynamicGameEventListener<?>, ServerLevel> consumer) {
         if (this.level() instanceof ServerLevel serverlevel) {
             consumer.accept(this.dynamicJukeboxListener, serverlevel);
         }
     }

     @Override
     public boolean canCollideWith(@NotNull Entity entity) {
         return super.canCollideWith(entity) && !(entity instanceof Hibbertopterus);
     }

     @Override
     public boolean canBeCollidedWith() {
         return this.isAlive() && !this.isBaby();
     }

     @Override
     public boolean refuseToMove() {
         return this.isDancing() || super.refuseToMove();
     }

     @Override
     public boolean isFood(ItemStack stack) {
         return stack.is(UP2ItemTags.DIET_DETRITIVORE);
     }

     @Nullable
     @Override
     public LivingEntity getControllingPassenger() {
         Entity entity = this.getFirstPassenger();
         if (entity instanceof Player player) {
             if (player.getMainHandItem().is(UP2Items.DIRT_ON_A_STICK.get()) || player.getOffhandItem().is(UP2Items.DIRT_ON_A_STICK.get())) {
                 return player;
             }
         }
         return null;
     }

     @Override
     public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
         boolean flag = this.isFood(player.getItemInHand(hand));
         if (!flag && !this.isBaby() && !this.isVehicle() && !player.isSecondaryUseActive() && !player.isShiftKeyDown()) {
             if (!this.level().isClientSide) {
                 player.startRiding(this);
             }
             return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
         else {
             return super.mobInteract(player, hand);
         }
     }

     @Override
     protected @NotNull Vec3 getRiddenInput(Player player, @NotNull Vec3 vec3) {
         return new Vec3(0.0D, 0.0D, 1.0D);
     }

     @Override
     protected void tickRidden(@NotNull Player player, @NotNull Vec3 travelVector) {
         super.tickRidden(player, travelVector);
         if (this.jumping) {
             this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.3D, 0.0D));
         } else {
             this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.07D, 0.0D));
         }
     }

     @Override
     protected float getRiddenSpeed(@NotNull Player player) {
         return (float) (this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (this.isInWater() ? 0.2D : 0.225D) * this.steering.boostFactor());
     }

     @Override
     public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity passenger) {
         return new Vec3(this.getX(), this.getBoundingBox().maxY + 0.1F, this.getZ());
     }

     @Override
     public boolean boost() {
         return this.steering.boost(this.getRandom());
     }

     private void tickPlowing() {
         if (!(this.getControllingPassenger() instanceof Player player)) return;

         for (int i = 0; i < 4; i++) {
             float offset = 38.0F - i * 38.0F;
             double blockPosX = this.getX() - Mth.sin((float) Math.toRadians(this.getYRot() - offset)) * 1.7D;
             double blockPosZ = this.getZ() + Mth.cos((float) Math.toRadians(this.getYRot() - offset)) * 1.7D;
             BlockPos pos = BlockPos.containing(blockPosX, this.getY() - 0.75D, blockPosZ);
             BlockPos above = pos.above();
             BlockState state = this.level().getBlockState(pos);
             BlockState aboveState = this.level().getBlockState(above);
             boolean replaceable = aboveState.canBeReplaced() || aboveState.is(BlockTags.REPLACEABLE) || aboveState.is(BlockTags.SMALL_FLOWERS) || aboveState.is(BlockTags.TALL_FLOWERS);
             if (replaceable && !aboveState.isAir()) this.level().destroyBlock(above, true);
             if (!replaceable) continue;
             UseOnContext context = new UseOnContext(player.level(), player, InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_HOE), new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false));
             BlockState modified = state.getToolModifiedState(context, ItemAbilities.HOE_TILL, false);
             if (modified != null) {
                 this.level().setBlock(pos, modified, 11);
                 this.level().levelEvent(2001, pos, Block.getId(state));
             }
         }
     }

     @Override
     public @NotNull ItemStack getPlushieItemStack() {
         return new ItemStack(UP2Blocks.HIBBERTOPTERUS_PLUSHIE.get());
     }

     @Override
     public void tick() {
         super.tick();
         if (!this.level().isClientSide) {
             if (this.steering.isBoosting()) {
                 this.tickPlowing();
             }
             if (tickCount % 20 == 0 && this.shouldStopDancing(this) && this.isDancing()) {
                 this.setDancing(false);
                 this.setJukeboxPosition(null);
             }
         }
         this.steering.tickBoost();
     }

     @Override
     public void setupAnimationStates() {
         this.idleAnimationState.animateWhen(!this.isDancing(), this.tickCount);
         this.plowAnimationState.animateWhen(this.steering.isBoosting() && this.hasControllingPassenger(), this.tickCount);
         this.danceAnimationState.animateWhen(this.isDancing(), this.tickCount);
     }

     @Override
     public float getWalkAnimationSpeed() {
         return this.isBaby() ? 5.0F : 10.0F;
     }

     @Override
     public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
         if (PLOW_TIME.equals(accessor) && this.level().isClientSide) {
             this.steering.onSynced();
         }
         super.onSyncedDataUpdated(accessor);
     }

     @Override
     protected void defineSynchedData(SynchedEntityData.Builder builder) {
         super.defineSynchedData(builder);
         builder.define(PLOW_TIME, 0);
         builder.define(DANCING, false);
     }

     @Override
     public boolean isDancing() {
         return this.entityData.get(DANCING);
     }

     @Override
     public void setDancing(boolean dancing) {
         this.entityData.set(DANCING, dancing);
     }

     @Override
     public @Nullable BlockPos getJukeboxPosition() {
         return jukeboxPosition;
     }

     @Override
     public void setJukeboxPosition(BlockPos jukeboxPosition) {
         this.jukeboxPosition = jukeboxPosition;
     }

     @Override
     @Nullable
     protected SoundEvent getAmbientSound() {
         return UP2SoundEvents.HIBBERTOPTERUS_IDLE.get();
     }

     @Override
     @Nullable
     protected SoundEvent getHurtSound(@NotNull DamageSource source) {
         return UP2SoundEvents.HIBBERTOPTERUS_HURT.get();
     }

     @Override
     @Nullable
     protected SoundEvent getDeathSound() {
         return UP2SoundEvents.HIBBERTOPTERUS_DEATH.get();
     }

     @Override
     protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
         this.playSound(UP2SoundEvents.HIBBERTOPTERUS_STEP.get(), 0.4F, this.getStepPitch());
     }

     @Override
     protected float nextStep() {
         return this.moveDist + 0.55F;
     }

     private float getStepPitch() {
         return this.isBaby() ? 1.45F + this.getRandom().nextFloat() * 0.1F : 0.95F + this.getRandom().nextFloat() * 0.1F;
     }

     @Nullable
     @Override
     public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
         return UP2Entities.HIBBERTOPTERUS.get().create(level);
     }
 }
