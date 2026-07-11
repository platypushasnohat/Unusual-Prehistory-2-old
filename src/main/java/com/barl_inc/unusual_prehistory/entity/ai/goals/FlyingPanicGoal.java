package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricFlyingMob;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;

public class FlyingPanicGoal extends PrehistoricPanicGoal {

    protected final PrehistoricFlyingMob flyingMob;

    public FlyingPanicGoal(PrehistoricFlyingMob mob, double speedModifier) {
        this(mob, speedModifier, 16, 10);
    }

    public FlyingPanicGoal(PrehistoricFlyingMob mob, double speedModifier, int radius, int height) {
        super(mob, speedModifier, radius, height, false);
        this.flyingMob = mob;
    }

    @Override
    public void start() {
        super.start();
        this.flyingMob.setFlying(true);
        if (flyingMob.onGround()) {
            this.flyingMob.setDeltaMovement(flyingMob.getDeltaMovement().add(0.0D, 0.5D, 0.0D));
        }
    }

    @Override
    public boolean canUse() {
        if (!this.shouldPanic()) {
            return false;
        } else {
            return this.findRandomPosition();
        }
    }

    @Override
    protected boolean findRandomPosition() {
        Vec3 pos;
        if (flyingMob.getLastHurtByMob() != null) {
            Vec3 away = flyingMob.position().subtract(flyingMob.getLastHurtByMob().position());
            if (away.horizontalDistanceSqr() < 1.0E-6D) {
                away = new Vec3(flyingMob.getRandom().nextGaussian(), 0.0D, flyingMob.getRandom().nextGaussian());
            }
            away = away.normalize().scale(1.5D);
            pos = HoverRandomPos.getPos(flyingMob, radius, height, away.x, away.z, (float) (Math.PI / 2.0D), height, Math.max(1, height / 2));
        } else {
            pos = HoverRandomPos.getPos(flyingMob, radius, height, 0.0D, 0.0D, (float) Math.PI, height, Math.max(1, height / 2));
        }
        if (pos == null) {
            return false;
        }
        this.posX = pos.x;
        this.posY = pos.y;
        this.posZ = pos.z;
        return true;
    }
}