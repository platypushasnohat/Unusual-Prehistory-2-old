package com.barl_inc.unusual_prehistory.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.entity.ai.goals.EepyGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.IdleAnimationGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricPanicGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricWanderGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.PlushableMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.*;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Cotylorhynchus extends PrehistoricMob implements PlushableMob {

    private static final EntityDataAccessor<Integer> GROG_TICKS = SynchedEntityData.defineId(Cotylorhynchus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GROG_TYPE = SynchedEntityData.defineId(Cotylorhynchus.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GLUTTONY = SynchedEntityData.defineId(Cotylorhynchus.class, EntityDataSerializers.INT);

    private static final EntityDimensions EEPY_DIMENSIONS = EntityDimensions.scalable(2.1F, 1.3F);

    public final SmoothAnimationState grazeAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState burpAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState grogAnimationState = new SmoothAnimationState();

    private int burpTicks;

    public Cotylorhynchus(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 2.0D, 10, 4));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_FRUGIVORE), false));
        this.goalSelector.addGoal(5, new PrehistoricWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new EepyGoal(this));
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 40, 1, true, 0.001F, this::canGraze));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 26.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.15F)
                .add(Attributes.STEP_HEIGHT, 1.1D);
    }

    @Override
    public double getFluidJumpThreshold() {
        return 0.4D * this.getBbHeight();
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = travelVec.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVec);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_FRUGIVORE);
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return super.canCollideWith(entity) && !(entity instanceof Cotylorhynchus);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public boolean isPushable() {
        return this.isBaby();
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getPose() == UP2Poses.BURPING.get() || this.getIdleState() == 1;
    }

    @Override
    public @NotNull EntityDimensions getDefaultDimensions(@NotNull Pose pose) {
        if (this.isEepy()) {
            return EEPY_DIMENSIONS.scale(this.getAgeScale());
        }
        return super.getDefaultDimensions(pose);
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, 0.6D, this.getBbWidth() * 0.8F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public @NotNull ItemStack getPlushieItemStack() {
        return new ItemStack(UP2Blocks.COTYLORHYNCHUS_PLUSHIE.get());
    }

    private int getTimeUntilGrog() {
        return 2400 + this.getRandom().nextInt(1200);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);
        GrogType grogType = GrogType.fromItem(itemstack);
        if (!this.isFullOfGrog() && !this.level().isClientSide && !this.isEepy()) {
            if (grogType != GrogType.EMPTY) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                if (this.getGluttony() >= 4) {
                    if (this.getNavigation().getPath() != null) {
                        this.getNavigation().stop();
                    }
                    this.setPose(UP2Poses.BURPING.get());
                    this.setGrogType(grogType);
                    this.setGrogTicks(this.getTimeUntilGrog());
                    this.setGluttony(0);
                }
                this.setGluttony(this.getGluttony() + 1);
                this.playSound(this.getEatingSound(itemstack), 1.0F, 0.9F + this.getRandom().nextFloat() * 0.25F);
                return InteractionResult.SUCCESS;
            }
        }
        else if (itemstack.is(Items.GLASS_BOTTLE) && this.isFullOfGrog() && this.getGrogTicks() == 0 && !this.level().isClientSide) {
            Item outputItem = this.getGrogType().getOutputItem();
            if (outputItem != null) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }
                if (!player.addItem(new ItemStack(outputItem))) {
                    player.spawnAtLocation(outputItem);
                }
                this.playSound(SoundEvents.BOTTLE_FILL, 1.0F, 1.0F);
                this.setGrogType(GrogType.EMPTY);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        return type;
    }

    private void spawnGrogParticles() {
        if (this.getGrogType().getParticle() != null) {
            for (int i = 0; i < 5; ++i) {
                double d0 = this.random.nextGaussian() * 0.05D;
                double d1 = this.random.nextGaussian() * 0.05D;
                double d2 = this.random.nextGaussian() * 0.05D;
                this.level().addParticle(this.getGrogType().getParticle(), this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
            }
        }
    }

    private void spawnBurpParticles() {
        if (this.getGrogType().getParticle() != null) {
            Vec3 lookVec = new Vec3(0, 0, -this.getBbWidth() * 0.87F).yRot((float) Math.toRadians(180F - this.getYHeadRot()));
            Vec3 eyeVec = this.getEyePosition().add(lookVec);
            for (int i = 0; i < 3; i++) {
                double d0 = this.random.nextGaussian() * 0.08D;
                double d1 = this.random.nextGaussian() * 0.08D;
                double d2 = this.random.nextGaussian() * 0.08D;
                this.level().addParticle(this.getGrogType().getParticle(), eyeVec.x, eyeVec.y, eyeVec.z, d0, d1, d2);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getGrogTicks() > 0) {
            this.setGrogTicks(this.getGrogTicks() - 1);
        }

        if (burpTicks > 0) burpTicks--;
        if (this.getPose() == UP2Poses.BURPING.get() && burpTicks == 14) {
            if (this.level().isClientSide) {
                this.spawnBurpParticles();
            }
            this.playSound(UP2SoundEvents.COTYLORHYNCHUS_BURP.get(), 1.0F, 0.9F + this.getRandom().nextFloat() * 0.25F);
        }
        if (this.getPose() == UP2Poses.BURPING.get() && burpTicks == 0) {
            this.setPose(Pose.STANDING);
        }

        if (this.isFullOfGrog() && this.getGrogTicks() <= 0 && this.getRandom().nextFloat() < 0.08F && this.level().isClientSide) {
            this.spawnGrogParticles();
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.isEepy(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.grazeAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), this.tickCount);
        this.burpAnimationState.animateWhen(this.getPose() == UP2Poses.BURPING.get(), this.tickCount);
        this.grogAnimationState.animateWhen(this.getGrogTicks() > 0, this.tickCount);
    }

    private boolean canGraze(Entity entity) {
        return entity.level().getBlockState(entity.blockPosition().below()).is(UP2BlockTags.COTYLORHYNCHUS_FOOD_BLOCKS) && !entity.isInWaterOrBubble();
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 800 + this.getRandom().nextInt(1200);
        } else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (DATA_POSE.equals(key)) {
            if (this.getPose() == UP2Poses.BURPING.get()) {
                this.burpTicks = 40;
            }
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(GROG_TICKS, 0);
        builder.define(GROG_TYPE, 0);
        builder.define(GLUTTONY, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("GrogTicks", this.getGrogTicks());
        compoundTag.putInt("GrogType", this.getGrogType().getId());
        compoundTag.putInt("Gluttony", this.getGluttony());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setGrogTicks(compoundTag.getInt("GrogTicks"));
        this.setGrogType(GrogType.byId(compoundTag.getInt("GrogType")));
        this.setGluttony(compoundTag.getInt("Gluttony"));
    }

    public int getGrogTicks() {
        return this.entityData.get(GROG_TICKS);
    }

    public void setGrogTicks(int grogTicks) {
        this.entityData.set(GROG_TICKS, grogTicks);
    }

    public boolean isFullOfGrog() {
        return this.getGrogType() != GrogType.EMPTY;
    }

    public GrogType getGrogType() {
        return GrogType.byId(this.entityData.get(GROG_TYPE));
    }

    public void setGrogType(GrogType type) {
        this.entityData.set(GROG_TYPE, type.getId());
    }

    public int getGluttony() {
        return this.entityData.get(GLUTTONY);
    }

    public void setGluttony(int gluttony) {
        this.entityData.set(GLUTTONY, gluttony);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        return UP2Entities.COTYLORHYNCHUS.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.COTYLORHYNCHUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.COTYLORHYNCHUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.COTYLORHYNCHUS_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(UP2SoundEvents.COTYLORHYNCHUS_STEP.get(), 0.2F, 1.0F);
    }

    public enum GrogType {
        EMPTY(0, null, null, null),
        SWEET(1, UP2ItemTags.SWEET_COTYLORHYNCHUS_FOOD, UP2Items.SWEET_GROG_BOTTLE.get(), UP2Particles.SWEET_GROG.get()),
        FOUL(2, UP2ItemTags.FOUL_COTYLORHYNCHUS_FOOD, UP2Items.FOUL_GROG_BOTTLE.get(), UP2Particles.FOUL_GROG.get());

        private final int id;
        private final TagKey<Item> input;
        private final Item output;
        private final ParticleOptions particle;

        GrogType(int id, @Nullable TagKey<Item> input, @Nullable Item output, @Nullable ParticleOptions particle) {
            this.id = id;
            this.input = input;
            this.output = output;
            this.particle = particle;
        }

        public int getId() {
            return this.id;
        }

        public Item getOutputItem() {
            return this.output;
        }

        public ParticleOptions getParticle() {
            return this.particle;
        }

        public static GrogType fromItem(ItemStack stack) {
            for (GrogType type : values()) {
                if (type.input != null && stack.is(type.input)) {
                    return type;
                }
            }
            return EMPTY;
        }

        public static GrogType byId(int id) {
            if (id < 0 || id >= GrogType.values().length) {
                id = 0;
            }
            return GrogType.values()[id];
        }
    }
}
