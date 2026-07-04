package com.barlinc.unusual_prehistory.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.entity.ai.goals.EepyGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricNearestAttackableTargetGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricWanderGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2EntityTags;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Coelophysis extends PrehistoricMob {

    private static final float MAX_TAIL_YAW = 30.0F;
    private static final float TAIL_YAW_MULITPLIER = 0.35F;

    public final SmoothAnimationState attack1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState attack2AnimationState = new SmoothAnimationState(1.0F);

    public Coelophysis(EntityType<? extends Coelophysis> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PrehistoricPanicGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new EepyGoal(this));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, Ingredient.of(UP2ItemTags.DIET_CARNIVORE), false));
        this.goalSelector.addGoal(7, new PrehistoricWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new PrehistoricNearestAttackableTargetGoal<>(this, LivingEntity.class, 300, true, false, entity -> entity.getType().is(UP2EntityTags.DROMAEOSAURUS_TARGETS)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.STEP_HEIGHT, 1.2D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_CARNIVORE);
    }

    @Override
    public void tick() {
        super.tick();
        this.tickTailYaw(MAX_TAIL_YAW, TAIL_YAW_MULITPLIER);
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isEepy(), tickCount);
        this.eepyAnimationState.animateWhen(this.isEepy(), tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean includeHeight) {
        float f = (float) Mth.length(this.getX() - xo, 0.0, this.getZ() - zo);
        if (this.isBaby()) {
            this.updateWalkAnimation(f * 0.5F);
        } else {
            this.updateWalkAnimation(f);
        }
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {
        float f = Math.min(partialTick * 30.0F, 1.0F);
        this.walkAnimation.update(f, 0.4F);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return UP2Entities.COELOPHYSIS.get().create(level);
    }

    @Override
    public Vec3 getEepyParticleVec() {
        return new Vec3(0.0D, -this.getBbHeight() * 0.35F, this.getBbWidth() * 1.3F).yRot(-yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    public void travel(Vec3 vec3) {
        if (this.refuseToMove() && this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0, 1.0, 0.0));
            vec3 = vec3.multiply(0.0, 1.0, 0.0);
        }
        super.travel(vec3);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.DROMAEOSAURUS_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return UP2SoundEvents.DROMAEOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.DROMAEOSAURUS_DEATH.get();
    }
}
