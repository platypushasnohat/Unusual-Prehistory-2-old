package com.barl_inc.unusual_prehistory.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.ai.goals.*;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;
import com.barl_inc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import com.barl_inc.unusual_prehistory.tags.UP2EntityTags;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class Telecrex extends PrehistoricFlyingMob {

    private static final EntityDataAccessor<Boolean> SPLAT = SynchedEntityData.defineId(Telecrex.class, EntityDataSerializers.BOOLEAN);

    public final SmoothAnimationState peckAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState preen1AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState preen2AnimationState = new SmoothAnimationState();
    public final SmoothAnimationState splatAnimationState = new SmoothAnimationState(1.0F);

    private boolean preenAlt = false;
    private int splatTicks;

    public Telecrex(EntityType<? extends PrehistoricFlyingMob> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.FLYING_SPEED, 0.6F)
                .add(Attributes.STEP_HEIGHT, 1.15D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FlyingPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new TelecrexScatterGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_GRANIVORE), false));
        this.goalSelector.addGoal(4, new FlyingGroundWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LandFromFlightGoal(this, 100));
        this.goalSelector.addGoal(6, new FlyingWanderGoal(this, 1.0F, 6));
        this.goalSelector.addGoal(7, new FollowParentGoal(this, 1));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 60, 1, true, 0.001F, this::canPreen) {
            @Override
            public void start() {
                super.start();
                Telecrex.this.preenAlt = Telecrex.this.getRandom().nextBoolean();
            }
        });
        this.goalSelector.addGoal(9, new IdleAnimationGoal(this, 60, 2, true, 0.001F, this::canPeck));
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.refuseToMove() && this.onGround()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVector = travelVector.multiply(0.0, 1.0, 0.0);
        }
        super.travel(travelVector);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);
        if (hurt && source.getEntity() != null && this.isAlive() && source.getEntity() instanceof LivingEntity livingEntity) {
            double range = 8;
            List<? extends Telecrex> entities = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(range, range / 2, range));
            for (Telecrex telecrex : entities) {
                telecrex.setLastHurtByMob(livingEntity);
            }
        }
        return hurt;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isFlying() && this.horizontalCollision && this.getRandom().nextBoolean() && !this.level().isClientSide) {
            this.setSplat(true);
            this.setFlying(false);
            if (this.isRunning()) {
                this.setRunning(false);
            }
        }

        if (this.hasSplat()) {
            this.splatTicks++;
            this.setDeltaMovement(this.getDeltaMovement().x * 0.1F, this.getDeltaMovement().y * 0.4F, this.getDeltaMovement().z * 0.1F);
        }
        if (splatTicks > 60 || this.onGround() || this.isInWaterOrBubble()) {
            this.setSplat(false);
        }
    }

    @Override
    public boolean refuseToMove() {
        return super.refuseToMove() || this.getIdleState() == 1 || this.getIdleState() == 2 || this.hasSplat();
    }

    @Override
    public boolean refuseToLook() {
        return super.refuseToMove() || this.hasSplat();
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlying() && this.getIdleState() != 1 && this.getIdleState() != 2, this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying() && !this.isRunning() && !this.hasSplat(), this.tickCount);
        this.flyFastAnimationState.animateWhen(this.isFlying() && this.isRunning() && !this.hasSplat(), this.tickCount);
        this.splatAnimationState.animateWhen(this.hasSplat(), this.tickCount);
        this.preen1AnimationState.animateWhen(this.getIdleState() == 1 && !preenAlt, this.tickCount);
        this.preen2AnimationState.animateWhen(this.getIdleState() == 1 && preenAlt, this.tickCount);
        this.peckAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
    }

    private boolean canPeck(Entity entity) {
        if (entity instanceof Telecrex telecrex) {
            return !telecrex.hasSplat() && !telecrex.isFlying() && !telecrex.isInWaterOrBubble() && telecrex.level().getBlockState(telecrex.blockPosition().below()).is(UP2BlockTags.TELECREX_FOOD_BLOCKS);
        }
        return false;
    }

    private boolean canPreen(Entity entity) {
        if (entity instanceof Telecrex telecrex) {
            return !telecrex.hasSplat() && !telecrex.isFlying() && !telecrex.isInWaterOrBubble();
        }
        return false;
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 800 + this.getRandom().nextInt(1200);
        }
        else if (idleState == 2) {
            return 900 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SPLAT, false);
    }

    public boolean hasSplat() {
        return entityData.get(SPLAT);
    }
    public void setSplat(boolean splat) {
        this.entityData.set(SPLAT, splat);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_GRANIVORE);
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.PARROT_EAT;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.TELECREX_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return UP2SoundEvents.TELECREX_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.TELECREX_DEATH.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        Telecrex baby = UP2Entities.TELECREX.get().create(level);
        if (baby != null) {
            baby.setVariantRawId(this.inheritVariantFrom(mob, this.getRandom()));
        }
        return baby;
    }

    @Override
    public ResourceLocation fallbackVariantTexture() {
        return UnusualPrehistory2.modPrefix("textures/entity/mob/telecrex/telecrex.png");
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        this.pickVariantForSpawn(level);
        return data;
    }

    private static class TelecrexScatterGoal extends Goal {

        private final Telecrex telecrex;

        public TelecrexScatterGoal(Telecrex telecrex) {
            this.telecrex = telecrex;
        }

        @Override
        public boolean canUse() {
            if (telecrex.isFlying()) {
                return false;
            }
            long worldTime = telecrex.level().getGameTime() % 10;
            if (telecrex.getRandom().nextInt(10) != 0 && worldTime != 0) {
                return false;
            }
            AABB aabb = telecrex.getBoundingBox().inflate(8);
            List<Entity> list = telecrex.level().getEntitiesOfClass(Entity.class, aabb, (entity -> entity.getType().is(UP2EntityTags.TELECREX_AVOIDS) || entity instanceof Player && !((Player) entity).isCreative()));
            return !list.isEmpty();
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            this.telecrex.setFlying(true);
            if (telecrex.onGround()) {
                this.telecrex.setDeltaMovement(telecrex.getDeltaMovement().add(0.0D, 0.5D, 0.0D));
            }
        }
    }
}