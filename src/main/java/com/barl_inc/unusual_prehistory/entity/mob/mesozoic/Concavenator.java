package com.barl_inc.unusual_prehistory.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricLookControl;
import com.barl_inc.unusual_prehistory.entity.ai.control.PrehistoricMoveControl;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.ai.goals.update_6.ConcavenatorAttackGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.PackAnimal;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Particles;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public class Concavenator extends PrehistoricMob implements PackAnimal {

    private static final EntityDataAccessor<Integer> ARMOR_TYPE = SynchedEntityData.defineId(Concavenator.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SAND_SWIMMING = SynchedEntityData.defineId(Concavenator.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SAND_SWIM_COOLDOWN = SynchedEntityData.defineId(Concavenator.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SAND_SWIM_TIME = SynchedEntityData.defineId(Concavenator.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(Concavenator.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> PACK_LEADER = SynchedEntityData.defineId(Concavenator.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDimensions EEPY_DIMENSIONS = EntityDimensions.scalable(1.5F, 1.4F).withEyeHeight(1.3F);
    private static final EntityDimensions SAND_SWIMMING_DIMENSIONS = EntityDimensions.scalable(1.5F, 1.1F).withEyeHeight(1.0F);

    public boolean isLandNavigator;

    public int attackCooldown = 0;
    public int diveAttackCooldown = 0;
    public int slashAttackCooldown = 0;

    @Nullable
    private Concavenator priorPackMember;
    @Nullable
    private Concavenator afterPackMember;

    public final SmoothAnimationState sandSwimIdleAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sandSwimStartAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState sandSwimEndAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState attackAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState diveAttackAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState slashAttackAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState eatAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState sandSitAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState scratch1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState scratch2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState sandSnortAnimationState = new SmoothAnimationState(1.0F);

    private final byte SAND_SNORT = 74;

    private int sandSwimStartTicks = 0;
    private int sandSwimEndTicks = 0;

    private boolean scratchAlt = false;

    public Concavenator(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricSitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new ConcavenatorAvoidEntityGoal<>(this, LivingEntity.class, 10.0F, entity -> entity.getType().is(UP2EntityTags.CONCAVENATOR_AVOIDS)));
        this.goalSelector.addGoal(3, new PrehistoricBabyPanicGoal(this, 1.6D, 10, 4));
        this.goalSelector.addGoal(4, new JoinPackGoal(this, 60, 5));
        this.goalSelector.addGoal(5, new ConcavenatorAttackGoal(this));
        this.goalSelector.addGoal(6, new ConcavenatorFollowOwnerGoal(this, 7.0F, 4.0F));
        this.goalSelector.addGoal(7, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.TEMPTS_CONCAVENATOR), false));
        this.goalSelector.addGoal(8, new FollowParentGoal(this, 1.2D));
        this.goalSelector.addGoal(9, new SandSwimmingWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new ConcavenatorWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(12, new EepyGoal(this));
        this.goalSelector.addGoal(13, new IdleAnimationGoal(this, 60, 1, true, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(13, new IdleAnimationGoal(this, 30, 2, true, 0.001F, this::canPlayIdles) {
            @Override
            public void tick() {
                super.tick();
                if (timer == 15) {
                    Concavenator.this.level().broadcastEntityEvent(Concavenator.this, SAND_SNORT);
                    if (!Concavenator.this.isSitting()) {
                        Concavenator.this.addDeltaMovement(new Vec3(0, 0.125D, 0));
                        Concavenator.this.addDeltaMovement(Concavenator.this.getLookAngle().scale(2.0D).multiply(-0.15D, 0, -0.15D));
                    }
                }
            }
        });
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(1, new PrehistoricOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new PrehistoricOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new PrehistoricNearestAttackableTargetGoal<>(this, Player.class, 200, true, true, this::canAttackPlayers));
        this.targetSelector.addGoal(4, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 200, true, true, entity -> entity.getType().is(UP2EntityTags.CONCAVENATOR_TARGETS)));
        this.targetSelector.addGoal(5, new PackAnimalNearestAttackableTargetGoal<>(this, LivingEntity.class, 200, true, true, entity -> entity.getType().is(UP2EntityTags.MEDIUM_CONCAVENATOR_PACK_TARGETS), 2));
        this.targetSelector.addGoal(6, new PackAnimalNearestAttackableTargetGoal<>(this, LivingEntity.class, 200, true, true, entity -> entity.getType().is(UP2EntityTags.LARGE_CONCAVENATOR_PACK_TARGETS), 4));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                .add(Attributes.MOVEMENT_SPEED, 0.24F)
                .add(Attributes.ARMOR, 1.0D)
                .add(Attributes.STEP_HEIGHT, 1.1D)
                .add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    @Override
    public void travel(Vec3 travelVec) {
        if (this.refuseToMove()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        if (this.shouldBePushedDown()) {
            this.addDeltaMovement(new Vec3(0, -0.7F, 0));
        }
        super.travel(travelVec);
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.getNavigation().stop();
            this.moveControl = new PrehistoricMoveControl(this);
            this.lookControl = new PrehistoricLookControl(this);
            this.isLandNavigator = true;
        } else {
            this.getNavigation().stop();
            this.moveControl = new SandSwimmingMoveControl(this, 10);
            this.lookControl = new SandSwimmingLookControl(this, 10);
            this.isLandNavigator = false;
        }
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).is(UP2BlockTags.CONCAVENATOR_SWIMS_ON) ? 10.0F : super.getWalkTargetValue(pos, level);
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        if (this.isEepy()) return EEPY_DIMENSIONS.scale(this.getAgeScale());
        else if (this.isSandSwimming()) return SAND_SWIMMING_DIMENSIONS.scale(this.getAgeScale());
        return super.getDefaultDimensions(pose);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_CARNIVORE);
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.isSwitchingToSandSwim() || this.getIdleState() == 1;
    }

    private boolean canPlayIdles(Entity entity) {
        return !entity.isInWaterOrBubble();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 1000 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 2) {
            return 900 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (this.isSandSwimming()) {
            return super.isInvulnerableTo(source) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.CACTUS) || source.is(DamageTypeTags.IS_FIRE);
        }
        return super.isInvulnerableTo(source) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.CACTUS);
    }

    @Override
    public boolean isOnFire() {
        if (this.isSandSwimming()) {
            return false;
        }
        return super.isOnFire();
    }

    @Override
    protected float getBlockSpeedFactor() {
        BlockState blockstate = this.level().getBlockState(this.blockPosition());
        float speedFactor = blockstate.getBlock().getSpeedFactor();
        if (blockstate.is(Blocks.SOUL_SAND) && this.isSandSwimming()) {
            // cancel out soul sand slowdown
            return Mth.lerp((float) this.getAttributeValue(Attributes.MOVEMENT_EFFICIENCY), speedFactor * 2.5F, 1.0F);
        } else {
            return Mth.lerp((float) this.getAttributeValue(Attributes.MOVEMENT_EFFICIENCY), super.getBlockSpeedFactor(), 1.0F);
        }
    }

    public boolean canAttackPlayers(LivingEntity target) {
        return this.canAttack(target) && this.isSandSwimming();
    }

    private int getSandSwimCooldownTime() {
        return 150 + this.getRandom().nextInt(150);
    }

    private void breakArmor(LivingEntity living, ItemStack itemStack) {
        if (!this.level().isClientSide) {
            if (this.getArmorType().armorBlock != null) {
                this.playSound(SoundEvents.SHIELD_BREAK, 1.0F, 0.9F + this.getRandom().nextFloat() * 0.2F);
            }
            if (this.getArmorType().armorBlock != null) {
                LootTable lootTable = Objects.requireNonNull(this.level().getServer()).reloadableRegistries().getLootTable(this.getArmorType().armorBlock.getLootTable());
                List<ItemStack> items = lootTable.getRandomItems((new LootParams.Builder((ServerLevel) this.level())).withParameter(LootContextParams.THIS_ENTITY, this).create(LootContextParamSets.PIGLIN_BARTER));
                items.forEach(this::spawnAtLocation);
            }
            if (this.isSandSwimming()) {
                this.setSandSwimming(false);
                this.setSandSwimCooldown(this.getSandSwimCooldownTime());
                if (this.onGround() && this.getPose() == Pose.STANDING) {
                    this.setPose(UP2Poses.STOP_SWIMMING.get());
                }
            }
            this.setArmorType(ArmorType.NONE);
            if (living instanceof Player player) {
                player.getCooldowns().addCooldown(itemStack.getItem(), player.getAbilities().instabuild ? 0 : 60);
            }
        } else {
            this.spawnArmorBreakParticles();
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof LivingEntity living && living.getMainHandItem().is(ItemTags.PICKAXES) && this.hasArmor()) {
            this.breakArmor(living, living.getMainHandItem());
        }
        else if (source.getEntity() instanceof LivingEntity living && living.getMainHandItem().is(ItemTags.SHOVELS) && this.isSandSwimming()) {
            this.playSound(SoundEvents.SHOVEL_FLATTEN, 1.0F, 0.9F + this.getRandom().nextFloat() * 0.2F);
            this.setSandSwimming(false);
            this.setSandSwimCooldown(200 + this.getRandom().nextInt(200));
            if (this.onGround() && this.getPose() == Pose.STANDING) {
                this.setPose(UP2Poses.STOP_SWIMMING.get());
            }
            if (living instanceof Player player) {
                player.getCooldowns().addCooldown(living.getMainHandItem().getItem(), player.getAbilities().instabuild ? 0 : 60);
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);
        if (itemStack.is(ItemTags.PICKAXES) && this.hasArmor()) {
            this.breakArmor(player, itemStack);
            if (!this.level().isClientSide) {
                if (this.getOwner() != player && !player.getAbilities().instabuild) {
                    this.setTarget(player);
                }
            }
            if (this.isEepy()) {
                this.setEepy(false);
                this.setEepyCooldown(100);
            }
            return InteractionResult.SUCCESS;
        }
        else if (itemStack.is(ItemTags.SHOVELS) && this.isSandSwimming()) {
            if (!this.level().isClientSide) {
                this.playSound(SoundEvents.SHOVEL_FLATTEN, 1.0F, 0.9F + this.getRandom().nextFloat() * 0.2F);
                this.setSandSwimming(false);
                this.setSandSwimCooldown(this.getSandSwimCooldownTime());
                if (this.onGround() && this.getPose() == Pose.STANDING) {
                    this.setPose(UP2Poses.STOP_SWIMMING.get());
                }
                player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), player.getAbilities().instabuild ? 0 : 60);
            }
            return InteractionResult.SUCCESS;
        }
        else if (!this.isTame() && itemStack.is(UP2ItemTags.TAMES_CONCAVENATOR) && this.getEatTicks() == 0) {
            if (!this.level().isClientSide) {
                int attempts = this.getTameAttempts();
                int remaining = 256 - attempts;
                int consume = Math.min(itemStack.getCount(), remaining);
                this.setTameAttempts(attempts + consume);
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                this.playSound(this.getEatingSound(), 1.0F, 0.9F + this.getRandom().nextFloat() * 0.2F);
                if (this.getNavigation().getPath() != null) {
                    this.getNavigation().stop();
                }
                if (this.getTameAttempts() >= 256 || player.isCreative()) {
                    this.level().broadcastEntityEvent(this, (byte) 7);
                    this.tame(player);
                    this.setTameAttempts(0);
                    this.heal(this.getMaxHealth());
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(consume);
                }
            } else {
                this.spawnEatingParticles(itemStack);
            }
            this.setEatTicks(30);
            return InteractionResult.SUCCESS;
        }
        else if (this.isTame() && itemStack.is(UP2ItemTags.ARMORS_CONCAVENATOR) && !this.hasArmor() && this.isOwnedBy(player)) {
            ArmorType armorType = ArmorType.armorTypeFromItem(itemStack);
            if (!this.level().isClientSide) {
                this.setArmorType(armorType);
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                if (this.getNavigation().getPath() != null) {
                    this.getNavigation().stop();
                }
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                if (armorType.armorBlock != null) {
                    this.playSound(armorType.armorBlock.defaultBlockState().getSoundType().getPlaceSound(), 1.0F, 0.9F + this.getRandom().nextFloat() * 0.2F);
                }
            }
            return InteractionResult.SUCCESS;
        }
        else {
            return type;
        }
    }

    @Override
    public boolean canOwnerCommand(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        return !itemStack.is(UP2ItemTags.ARMORS_CONCAVENATOR) && !itemStack.is(ItemTags.PICKAXES) && !itemStack.is(ItemTags.SHOVELS);
    }

    private void spawnEatingParticles(ItemStack itemStack) {
        for (int i = 0; i < 4; i++) {
            Vec3 eatPos = this.getEyePosition().add(this.getViewVector(0.0F).scale(1.8F).add(0, -0.7F, -this.getBbWidth() * 0.5F));
            Vec3 vec3 = (new Vec3((this.getRandom().nextFloat() - 0.5F) * 0.1F, this.getRandom().nextFloat() * 0.1F + 0.1F, 0.0F)).xRot(-this.getXRot() * ((float) Math.PI / 180F)).yRot(-this.getYRot() * ((float) Math.PI / 180F));
            this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, itemStack), eatPos.x, eatPos.y, eatPos.z, vec3.x, vec3.y + 0.05, vec3.z);
        }
    }

    private void spawnSandSnortParticles() {
        Vec3 deltaMovement = this.getDeltaMovement();
        this.level().addParticle(UP2Particles.SAND_SNORT.get(), this.getX() - (double) (this.getBbWidth() + (this.isSitting() ? 1.25F : 3.25F)) * 0.5 * (double) Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0)), this.getEyeY() - (this.isSitting() ? -0.5F : 0.2F), this.getZ() + (double) (this.getBbWidth() + (this.isSitting() ? 1.25F : 3.25F)) * 0.5 * (double) Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0)), deltaMovement.x, 0.0, deltaMovement.z);
    }

    @Override
    public boolean isEepyTime() {
        return this.level().isNight() && !this.isSandSwimming();
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, 0.2D, this.getBbWidth()).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public boolean isPushable() {
        return !this.isSandSwimming();
    }

    @Override
    protected void doPush(Entity entity) {
        if (!this.isSandSwimming()) {
            super.doPush(entity);
        }
    }

    @Override
    protected void pushEntities() {
        if (!this.isSandSwimming()) {
            super.pushEntities();
        }
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        return !effectInstance.is(MobEffects.HUNGER) && !effectInstance.is(MobEffects.POISON) && super.canBeAffected(effectInstance);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isSandSwimming() && this.isLandNavigator) {
            this.switchNavigator(false);
        }
        if (!this.isSandSwimming() && !this.isLandNavigator) {
            this.switchNavigator(true);
        }

        if (!this.level().isClientSide) {
            this.tickSandSwimming();
        }
        else if (this.isSandSwimming() && !this.isInWaterOrBubble() && this.getDeltaMovement().horizontalDistance() > 0.05D) {
            this.spawnSandSwimmingParticles();
        }

        if (this.isSandSwimming()) {
            this.setSandSwimTime(this.getSandSwimTime() + 1);
            if (this.getSandSwimTime() > 200 && !this.hasArmor() && this.getDeltaMovement().horizontalDistance() > 0.05D) {
                ArmorType type = ArmorType.armorTypeFromBlock(this.level().getBlockState(this.blockPosition().below()));
                if (type != ArmorType.NONE) {
                    this.setArmorType(type);
                    if (this.level().isClientSide) {
                        this.spawnArmorParticles();
                    }
                    if (this.getArmorType().armorBlock != null) {
                        this.playSound(this.getArmorType().armorBlock.defaultBlockState().getSoundType().getPlaceSound(), 1.0F, 0.9F + this.getRandom().nextFloat() * 0.25F);
                    }
                }
            }
        } else if (this.getSandSwimTime() > 0) {
            this.setSandSwimTime(0);
        }

       LivingEntity target = this.getTarget();
       if (target != null && target.isAlive() && !(target instanceof Player player && player.isCreative())) {
           if (this.isPackLeader()) {
               PackAnimal leader = this;
               while (leader.getAfterPackMember() != null) {
                   leader = leader.getAfterPackMember();
                   if (!((Concavenator) leader).isAlliedTo(target)) {
                       ((Concavenator) leader).setTarget(target);
                   }
               }
           }
       }
    }

    private void tickSandSwimming() {
        if (this.getRemainingFireTicks() > 0) {
            this.setRemainingFireTicks(0);
        }
        if (this.getSandSwimCooldown() > 0 && !this.isEepy()) {
            this.setSandSwimCooldown(this.getSandSwimCooldown() - 1);
        }

        if ((this.isSandSwimmingBlockBelow(1) || this.level().getBlockState(this.blockPosition()).is(UP2BlockTags.CONCAVENATOR_SWIMS_ON)) && !this.isSandSwimming() && this.getPose() == Pose.STANDING && this.getSandSwimCooldown() == 0 && !this.isInWaterOrBubble() && !this.isEepy()  && !this.isSitting()) {
            this.setSandSwimming(true);
            this.setPose(UP2Poses.START_SWIMMING.get());
        }

        if (this.isSandSwimming() && this.getPose() == Pose.STANDING && (!this.isSandSwimmingBlockBelow(3) || this.isInWaterOrBubble())) {
            this.setSandSwimming(false);
            this.setSandSwimCooldown(this.getSandSwimCooldownTime());
            if (this.onGround()) {
                this.setPose(UP2Poses.STOP_SWIMMING.get());
            } else {
                this.setPose(Pose.STANDING);
            }
        }
    }

    private boolean isSandSwimmingBlockBelow(int depth) {
        int valid = 0;
        for (int i = 1; i <= depth; i++) {
            BlockState state = this.level().getBlockState(this.blockPosition().below(i));
            if (state.is(UP2BlockTags.CONCAVENATOR_SWIMS_ON)) {
                valid++;
            }
        }
        return valid >= 1;
    }

    private boolean shouldBePushedDown() {
        if (!this.isSandSwimming()) {
            return false;
        } else if (this.onGround()) {
            return false;
        }
        return this.fallDistance > 0.0F && this.fallDistance < 0.2F && this.canStepDownBlock(this);
    }

    private void spawnSandSwimmingParticles() {
        BlockPos onPos = this.getOnPos(0.2F);
        BlockState blockstate = this.level().getBlockState(onPos);
        Vec3 deltaMovement = this.getDeltaMovement();
        BlockPos blockPos = this.blockPosition();
        if (!blockstate.addRunningEffects(this.level(), onPos, this) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
            double xPos = this.getX() + (this.getRandom().nextDouble() - (double) 0.5F) * (double) this.getDimensions(this.getPose()).width();
            double zPos = this.getZ() + (this.getRandom().nextDouble() - (double) 0.5F) * (double) this.getDimensions(this.getPose()).width();
            if (blockPos.getX() != onPos.getX()) {
                xPos = Mth.clamp(xPos, onPos.getX(), (double) onPos.getX() + (double) 1.5F);
            }
            if (blockPos.getZ() != onPos.getZ()) {
                zPos = Mth.clamp(zPos, onPos.getZ(), (double) onPos.getZ() + (double) 1.5F);
            }
            this.level().addParticle((new BlockParticleOption(ParticleTypes.BLOCK, blockstate)).setPos(onPos), xPos, this.getY() + 0.1, zPos, deltaMovement.x * (double) -6.0F, 1.5F, deltaMovement.z * (double) -6.0F);
        }
    }

    public void spawnSandDigParticles(BlockPos blockPos) {
        float radius = this.getBbWidth();
        for (int i = 0; i < 4; i++) {
            double motionX = this.getRandom().nextGaussian() * 0.07D;
            double motionY = this.getRandom().nextGaussian() * 0.07D;
            double motionZ = this.getRandom().nextGaussian() * 0.07D;
            float angle = (float) ((0.0174532925 * this.yBodyRot) + i);
            double extraX = radius * Mth.sin(Mth.PI + angle);
            double extraZ = radius * Mth.cos(angle);
            BlockState state = this.level().getBlockState(blockPos);
            ((ServerLevel) this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), blockPos.getX() + 0.5F + extraX, blockPos.getY() + 1.5F, blockPos.getZ() + 0.5F + extraZ, 1, motionX, motionY, motionZ, 1);
        }
    }

    private void spawnArmorParticles() {
        if (this.getArmorType().armorBlock != null) {
            BlockPos onPos = this.getOnPos(0.2F);
            for (int i = 0; i < 20; i++) {
                double xVelocity = this.random.nextGaussian() * 0.1D;
                double yVelocity = this.random.nextGaussian() * 0.1D;
                double zVelocity = this.random.nextGaussian() * 0.1D;
                this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.getArmorType().armorBlock.defaultBlockState()).setPos(onPos), this.getRandomX(0.8D), this.getRandomY(), this.getRandomZ(0.8D), xVelocity, yVelocity, zVelocity);
            }
        }
    }

    private void spawnArmorBreakParticles() {
        if (this.getArmorType().armorBlock != null) {
            for (int i = 0; i < 20; i++) {
                double xVelocity = this.random.nextGaussian() * 0.15D;
                double yVelocity = this.random.nextGaussian() * 0.15D;
                double zVelocity = this.random.nextGaussian() * 0.15D;
                this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, this.getArmorType().armorBlock.defaultBlockState()), this.getRandomX(0.8D), this.getRandomY(), this.getRandomZ(0.8D), xVelocity, yVelocity, zVelocity);
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        if (this.eatAnimationState.isStarted() && this.getPose() == UP2Poses.ATTACKING.get() || this.getPose() == UP2Poses.CHARGING.get() || this.getPose() == UP2Poses.KICKING.get()) {
            this.eatAnimationState.stop();
        }
        this.idleAnimationState.animateWhen(!this.isEepy() && !this.isSitting() && !this.isSandSwimming() && !this.isInWaterOrBubble() && this.getPose() != UP2Poses.KICKING.get() && !this.isSwitchingToSandSwim(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.sandSwimStartAnimationState.animateWhen(this.getPose() == UP2Poses.START_SWIMMING.get(), this.tickCount);
        this.sandSwimEndAnimationState.animateWhen(this.getPose() == UP2Poses.STOP_SWIMMING.get(), this.tickCount);
        this.sandSwimIdleAnimationState.animateWhen(this.isSandSwimming() && !this.isSitting() && !this.isSwitchingToSandSwim() && !this.isInWaterOrBubble(), this.tickCount);
        this.attackAnimationState.animateWhen(this.getPose() == UP2Poses.ATTACKING.get(), this.tickCount);
        this.diveAttackAnimationState.animateWhen(this.getPose() == UP2Poses.CHARGING.get(), this.tickCount);
        this.slashAttackAnimationState.animateWhen(this.getPose() == UP2Poses.KICKING.get(), this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.sitAnimationState.animateWhen(this.isSitting() && !this.isSandSwimming() && !this.isSwitchingToSandSwim(), this.tickCount);
        this.sandSitAnimationState.animateWhen(this.isSitting() && this.isSandSwimming() && !this.isSwitchingToSandSwim(), this.tickCount);
        this.scratch1AnimationState.animateWhen(this.getIdleState() == 1 && !scratchAlt, this.tickCount);
        this.scratch2AnimationState.animateWhen(this.getIdleState() == 1 && scratchAlt, this.tickCount);
        this.sandSnortAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
        this.eatAnimationState.animateWhen(this.getEatTicks() > 0, this.tickCount);
    }

    public boolean isSwitchingToSandSwim() {
        return this.getPose() == UP2Poses.START_SWIMMING.get() || this.getPose() == UP2Poses.STOP_SWIMMING.get();
    }

    @Override
    public void tickCooldowns() {
        super.tickCooldowns();
        if (attackCooldown > 0) attackCooldown--;
        if (diveAttackCooldown > 0) diveAttackCooldown--;
        if (slashAttackCooldown > 0) slashAttackCooldown--;
        if (this.sandSwimStartTicks > 0) {
            this.sandSwimStartTicks--;
            if (!this.level().isClientSide) {
                this.spawnSandDigParticles(this.blockPosition().below());
            }
        }
        if (this.sandSwimEndTicks > 0) {
            this.sandSwimEndTicks--;
            if (!this.level().isClientSide) {
                this.spawnSandDigParticles(this.blockPosition().below());
            }
        }
        if (this.sandSwimStartTicks == 0 && this.getPose() == UP2Poses.START_SWIMMING.get()) {
            this.setPose(Pose.STANDING);
        }
        if (this.sandSwimEndTicks == 0 && this.getPose() == UP2Poses.STOP_SWIMMING.get()) {
            this.setPose(Pose.STANDING);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (SAND_SWIMMING.equals(key)) {
            this.refreshDimensions();
        }
        if (DATA_POSE.equals(key)) {
            if (this.getPose() == UP2Poses.START_SWIMMING.get()) {
                this.sandSwimStartTicks = 20;
            }
            else if (this.getPose() == UP2Poses.STOP_SWIMMING.get()) {
                this.sandSwimEndTicks = 20;
            }
        }
        if (ARMOR_TYPE.equals(key)) {
            if (this.getArmorType() == ArmorType.NONE) {
                Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(1.0D);
            } else {
                Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(15.0D);
            }
        }
        if (PACK_LEADER.equals(key)) {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(36.0D);
            Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(7.0D);
            this.heal(36.0F);
        }
        if (IDLE_STATE.equals(key)) {
            if (this.getIdleState() == 1) {
                this.scratchAlt = this.getRandom().nextBoolean();
            }
        }
        super.onSyncedDataUpdated(key);
    }

    public void handleEntityEvent(byte id) {
        if (id == SAND_SNORT) {
            this.spawnSandSnortParticles();
        }
        else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ARMOR_TYPE, ArmorType.NONE.getId());
        builder.define(SAND_SWIMMING, false);
        builder.define(SAND_SWIM_COOLDOWN, 80 + this.getRandom().nextInt(80));
        builder.define(SAND_SWIM_TIME, 0);
        builder.define(TAME_ATTEMPTS, 0);
        builder.define(PACK_LEADER, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("ArmorType", this.getArmorType().getId());
        compoundTag.putInt("TameAttempts", this.getTameAttempts());
        compoundTag.putBoolean("PackLeader", this.isPackLeader());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setArmorType(ArmorType.byId(compoundTag.getInt("ArmorType")));
        this.setTameAttempts(compoundTag.getInt("TameAttempts"));
        this.setPackLeader(compoundTag.getBoolean("PackLeader"));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasArmor() {
        return this.getArmorType() != ArmorType.NONE;
    }

    public ArmorType getArmorType() {
        return ArmorType.byId(this.entityData.get(ARMOR_TYPE));
    }

    public void setArmorType(ArmorType type) {
        this.entityData.set(ARMOR_TYPE, type.getId());
    }

    public boolean isSandSwimming() {
        return this.entityData.get(SAND_SWIMMING);
    }

    public void setSandSwimming(boolean digging) {
        this.entityData.set(SAND_SWIMMING, digging);
    }

    public int getSandSwimCooldown() {
        return this.entityData.get(SAND_SWIM_COOLDOWN);
    }

    public void setSandSwimCooldown(int cooldown) {
        this.entityData.set(SAND_SWIM_COOLDOWN, cooldown);
    }

    public int getSandSwimTime() {
        return this.entityData.get(SAND_SWIM_TIME);
    }

    public void setSandSwimTime(int time) {
        this.entityData.set(SAND_SWIM_TIME, time);
    }

    public void setTameAttempts(int tameAttempts) {
        this.entityData.set(TAME_ATTEMPTS, tameAttempts);
    }

    public int getTameAttempts() {
        return this.entityData.get(TAME_ATTEMPTS);
    }

    public boolean isPackLeader() {
        return this.entityData.get(PACK_LEADER);
    }

    public void setPackLeader(boolean packLeader) {
        this.entityData.set(PACK_LEADER, packLeader);
    }

    @Override
    @Nullable
    public PackAnimal getPriorPackMember() {
        return priorPackMember;
    }
    @Override
    @Nullable
    public PackAnimal getAfterPackMember() {
        return afterPackMember;
    }

    @Override
    public void setPriorPackMember(PackAnimal animal) {
        this.priorPackMember = (Concavenator) animal;
    }

    @Override
    public void setAfterPackMember(PackAnimal animal) {
        this.afterPackMember = (Concavenator) animal;
    }

    @Override
    public boolean isValidLeader(PackAnimal packLeader) {
        return packLeader instanceof Concavenator && ((Concavenator) packLeader).isAlive() && ((Concavenator) packLeader).isPackLeader();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.CONCAVENATOR_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return UP2SoundEvents.CONCAVENATOR_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.CONCAVENATOR_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        if (this.isSandSwimming()) {
            SoundType soundtype = state.getSoundType(this.level(), pos, this);
            this.playSound(soundtype.getHitSound(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
        } else {
            this.playSound(UP2SoundEvents.CONCAVENATOR_STEP.get(), 0.2F, 1.0F);
        }
    }

    @Override
    public float getVoicePitch() {
        if (this.isPackLeader() && !this.isBaby()) {
            return (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 0.925F;
        }
        return super.getVoicePitch();
    }

    public enum ArmorType {
        NONE(0, null, null, null),
        ARMOR_SAND(1, UP2BlockTags.CONCAVENATOR_SAND_ARMOR_BLOCKS, UP2ItemTags.CONCAVENATOR_SAND_ARMOR_ITEMS, Blocks.SANDSTONE),
        ARMOR_RED_SAND(2, UP2BlockTags.CONCAVENATOR_RED_SAND_ARMOR_BLOCKS, UP2ItemTags.CONCAVENATOR_RED_SAND_ARMOR_ITEMS, Blocks.RED_SANDSTONE),
        ARMOR_SOUL_SAND(3, UP2BlockTags.CONCAVENATOR_SOUL_SAND_ARMOR_BLOCKS, UP2ItemTags.CONCAVENATOR_SOUL_SAND_ARMOR_ITEMS, Blocks.SOUL_SOIL);

        private final int id;
        @Nullable
        private final TagKey<Block> blockTag;
        @Nullable
        private final Block armorBlock;
        @Nullable
        private final TagKey<Item> itemTag;

        ArmorType(int id, @Nullable TagKey<Block> blockTag, @Nullable TagKey<Item> itemTag, @Nullable Block armorBlock) {
            this.id = id;
            this.blockTag = blockTag;
            this.itemTag = itemTag;
            this.armorBlock = armorBlock;
        }

        public int getId() {
            return id;
        }

        public static ArmorType armorTypeFromBlock(BlockState state) {
            for (ArmorType type : values()) {
                if (type.blockTag != null && state.is(type.blockTag)) {
                    return type;
                }
            }
            return NONE;
        }

        public static ArmorType armorTypeFromItem(ItemStack item) {
            for (ArmorType type : values()) {
                if (type.itemTag != null && item.is(type.itemTag)) {
                    return type;
                }
            }
            return NONE;
        }

        public static ArmorType byId(int id) {
            if (id < 0 || id >= ArmorType.values().length) {
                id = 0;
            }
            return ArmorType.values()[id];
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        Concavenator baby = UP2Entities.CONCAVENATOR.get().create(level);
        if (baby != null) {
            baby.setVariantRawId(this.inheritVariantFrom(mob, this.getRandom()));
        }
        return baby;
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/concavenator/concavenator.png");
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        var nearbyConcavenator = level.getEntitiesOfClass(Concavenator.class, this.getBoundingBox().inflate(16.0D));
        boolean hasPackLeader = nearbyConcavenator.stream().anyMatch(Concavenator::isPackLeader);
        this.setPackLeader(nearbyConcavenator.size() >= 2 && !hasPackLeader);
        this.pickVariantForSpawn(level);
        return data;
    }

    // Goals
    private static class ConcavenatorWanderGoal extends PrehistoricWanderGoal {

        private final Concavenator concavenator;

        public ConcavenatorWanderGoal(Concavenator concavenator, double speedModifier) {
            super(concavenator, speedModifier, true);
            this.concavenator = concavenator;
        }

        @Override
        public boolean canUse() {
            return !concavenator.isSandSwimming() && concavenator.isLandNavigator && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !concavenator.isSandSwimming() && concavenator.isLandNavigator && super.canContinueToUse();
        }
    }

    private static class SandSwimmingWanderGoal extends PrehistoricWanderGoal {

        private final Concavenator concavenator;

        public SandSwimmingWanderGoal(Concavenator concavenator, double speedModifier) {
            super(concavenator, speedModifier, 20, 5, 40, true);
            this.concavenator = concavenator;
        }

        @Override
        public boolean canUse() {
            return concavenator.isSandSwimming() && !concavenator.isLandNavigator && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return concavenator.isSandSwimming() && !concavenator.isLandNavigator && super.canContinueToUse();
        }

        @Nullable
        @Override
        protected Vec3 getPosition() {
            Vec3 position = LandRandomPos.getPos(concavenator, radius, height);
            if (position != null) {
                BlockPos blockPos = BlockPos.containing(position);
                if (concavenator.level().getBlockState(blockPos).is(UP2BlockTags.CONCAVENATOR_SWIMS_ON)) {
                    return position;
                }
            }
            return super.getPosition();
        }
    }

    private static class ConcavenatorFollowOwnerGoal extends PrehistoricFollowOwnerGoal {

        private final Concavenator concavenator;

        public ConcavenatorFollowOwnerGoal(Concavenator concavenator, float startDistance, float stopDistance) {
            super(concavenator, 1.0D, 1.0D, startDistance, stopDistance);
            this.concavenator = concavenator;
        }

        @Override
        public void tick() {
            this.tamedMob.getLookControl().setLookAt(owner, 10.0F, (float) tamedMob.getMaxHeadXRot());
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                if (this.tamedMob.distanceToSqr(owner) >= 256.0D) {
                    this.tamedMob.setRunning(false);
                    this.teleportToOwner();
                } else if (this.tamedMob.distanceToSqr(owner) >= 64.0D) {
                    this.tamedMob.setRunning(true);
                    this.tamedMob.getNavigation().moveTo(owner, concavenator.isSandSwimming() ? 1.4D : 1.6D);
                } else {
                    this.tamedMob.setRunning(false);
                    this.tamedMob.getNavigation().moveTo(owner, concavenator.isSandSwimming() ? 1.0D : 1.2D);
                }
            }
        }
    }

    private static class ConcavenatorAvoidEntityGoal<T extends LivingEntity> extends PrehistoricAvoidEntityGoal<T> {

        private final Concavenator concavenator;

        public ConcavenatorAvoidEntityGoal(Concavenator concavenator, Class<T> classToAvoid, float distance, Predicate<LivingEntity> avoidPredicate) {
            super(concavenator, classToAvoid, distance, 1.0D, false, avoidPredicate);
            this.concavenator = concavenator;
        }

        @Override
        public boolean canUse() {
            return !concavenator.isTame() && super.canUse();
        }

        @Override
        public void start() {
            this.mob.setRunning(true);
            this.mob.getNavigation().moveTo(posX, posY, posZ, concavenator.isSandSwimming() ? 1.4D : 1.6D);
        }
    }

    private static class SandSwimmingLookControl extends PrehistoricLookControl {

        private final int maxYRotFromCenter;

        public SandSwimmingLookControl(PrehistoricMob mob, int maxYRotFromCenter) {
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
                    this.getXRotD().ifPresent(f -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), f + 10.0F, this.xMaxRotAngle)));
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

    private static class SandSwimmingMoveControl extends PrehistoricMoveControl {

        private final int maxTurnY;

        public SandSwimmingMoveControl(PrehistoricMob mob, int maxTurnY) {
            super(mob);
            this.maxTurnY = maxTurnY;
        }

        @Override
        public void tick() {
            if (!prehistoricMob.refuseToMove()) {
                if (this.operation == Operation.MOVE_TO && !mob.getNavigation().isDone()) {
                    double d0 = wantedX - mob.getX();
                    double d2 = wantedZ - mob.getZ();
                    double d3 = d0 * d0 + d2 * d2;
                    if (d3 < (double) 2.5000003E-7F) {
                        this.mob.setZza(0.0F);
                    } else {
                        float f = (float) (Mth.atan2(d2, d0) * (double) 180.0F / (double) (float) Math.PI) - 90.0F;
                        this.mob.setYRot(this.rotlerp(mob.getYRot(), f, (float) maxTurnY));
                        this.mob.yBodyRot = mob.getYRot();
                        this.mob.yHeadRot = mob.getYRot();
                        float speed = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                        this.mob.setSpeed(speed * 3.0F);
                        float f6 = Mth.cos(mob.getXRot() * ((float) Math.PI / 180F));
                        float f4 = Mth.sin(mob.getXRot() * ((float) Math.PI / 180F));
                        this.mob.zza = f6 * speed;
                        this.mob.yya = -f4 * speed;
                    }
                } else {
                    this.mob.setSpeed(0.0F);
                    this.mob.setXxa(0.0F);
                    this.mob.setYya(0.0F);
                    this.mob.setZza(0.0F);
                }
            }
        }
    }
}
