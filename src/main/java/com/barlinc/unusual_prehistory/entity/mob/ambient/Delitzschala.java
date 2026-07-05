package com.barlinc.unusual_prehistory.entity.mob.ambient;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.ai.goals.AmbientMoveToRestrictionGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.AmbientMob;
import com.barlinc.unusual_prehistory.entity.mob.base.AmbientFlyingMob;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Delitzschala extends AmbientFlyingMob {

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState flyAnimationState = new SmoothAnimationState(1.0F);

    public Delitzschala(EntityType<? extends AmbientMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.01F)
                .add(Attributes.FLYING_SPEED, 0.7F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AmbientMoveToRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new FlyingWanderAroundGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new FlyingLookAroundGoal(this));
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        UnusualPrehistory2.PROXY.clearSoundCacheFor(this);
        super.remove(removalReason);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide && this.isAlive()) {
            UnusualPrehistory2.PROXY.playWorldSound(this, (byte) 3);
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlying(), this.tickCount);
        this.flyAnimationState.animateWhen(this.isFlying(), this.tickCount);
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.BUG_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.BUG_DEATH.get();
    }
}
