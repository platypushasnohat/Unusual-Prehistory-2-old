package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class PrehistoricSwimmingMoveControl extends PrehistoricMoveControl {

    private final int maxTurnX;
    private final int maxTurnY;
    private final float inWaterSpeedModifier;
    private final float outsideWaterSpeedModifier;

    public PrehistoricSwimmingMoveControl(PrehistoricMob mob, int maxTurnX, int maxTurnY, float inWaterSpeedModifier) {
        this(mob, maxTurnX, maxTurnY, inWaterSpeedModifier, 1.0F);
    }

    public PrehistoricSwimmingMoveControl(PrehistoricMob mob, int maxTurnX, int maxTurnY, float inWaterSpeedModifier, float outsideWaterSpeedModifier) {
        super(mob);
        this.maxTurnX = maxTurnX;
        this.maxTurnY = maxTurnY;
        this.inWaterSpeedModifier = inWaterSpeedModifier;
        this.outsideWaterSpeedModifier = outsideWaterSpeedModifier;
    }

    @Override
    public void tick() {
        if (operation != Operation.MOVE_TO && operation != Operation.STRAFE) {
            this.mob.setSpeed(0.0F);
            this.mob.setXxa(0.0F);
            this.mob.setYya(0.0F);
            this.mob.setZza(0.0F);
            return;
        }
        if (!prehistoricMob.refuseToMove()) {
            if (operation == Operation.MOVE_TO && !prehistoricMob.getNavigation().isDone()) {
                this.doMoveTo();
            }
            else if (operation == Operation.STRAFE) {
                this.doStrafing(false);
            }
        }
    }

    @Override
    public void doMoveTo() {
        double x = wantedX - mob.getX();
        double y = wantedY - mob.getY();
        double z = wantedZ - mob.getZ();
        double length = x * x + y * y + z * z;
        if (length < (double) 2.5000003E-7F) {
            this.mob.setZza(0.0F);
        } else {
            float movementAngle = (float) (Mth.atan2(z, x) * (double) 180.0F / (double) (float) Math.PI) - 90.0F;
            this.mob.setYRot(this.rotlerp(mob.getYRot(), movementAngle, (float) maxTurnY));
            this.mob.yBodyRot = mob.getYRot();
            this.mob.yHeadRot = mob.getYRot();
            float speed = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            if (this.mob.isInWater()) {
                this.mob.setSpeed(speed * inWaterSpeedModifier);
                double sqrt = Math.sqrt(x * x + z * z);
                if (Math.abs(y) > (double) 1.0E-5F || Math.abs(sqrt) > (double)1.0E-5F) {
                    float xRot = -((float) (Mth.atan2(y, sqrt) * (double) 180.0F / (double) (float) Math.PI));
                    xRot = Mth.clamp(Mth.wrapDegrees(xRot), (float) (-maxTurnX), (float) maxTurnX);
                    this.mob.setXRot(this.rotlerp(mob.getXRot(), xRot, 5.0F));
                }
                float cos = Mth.cos(mob.getXRot() * ((float) Math.PI / 180F));
                float sin = Mth.sin(mob.getXRot() * ((float) Math.PI / 180F));
                this.mob.zza = cos * speed;
                this.mob.yya = -sin * speed;
            } else {
                float degreesToTurn = Math.abs(Mth.wrapDegrees(mob.getYRot() - movementAngle));
                float turnSpeed = this.getTurningSpeedFactor(degreesToTurn);
                this.mob.setSpeed(speed * outsideWaterSpeedModifier * turnSpeed);
            }
        }
    }

    @Override
    public void doStrafing(boolean requireWalkable) {
        float speed = (float) speedModifier * (float) mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
        float forward = strafeForwards;
        float right = strafeRight;

        float length = Mth.sqrt(forward * forward + right * right);
        if (length < 1.0F) {
            length = 1.0F;
        }

        float scale = speed / length;
        forward *= scale;
        right *= scale;

        float sin = Mth.sin(mob.getYRot() * ((float) Math.PI / 180F));
        float cos = Mth.cos(mob.getYRot() * ((float) Math.PI / 180F));
        float x = forward * cos - right * sin;
        float z = right * cos + forward * sin;

        if (requireWalkable && !this.isWalkable(x, z)) {
            this.strafeForwards = 1.0F;
            this.strafeRight = 0.0F;
            return;
        }

        double speedMultiplier = mob.isInWater() ? inWaterSpeedModifier : outsideWaterSpeedModifier;
        this.mob.setDeltaMovement(mob.getDeltaMovement().add(x * speedMultiplier, 0.0D, z * speedMultiplier));
        this.operation = Operation.WAIT;
    }

    protected float getTurningSpeedFactor(float degreesToTurn) {
        return 1.0F - Mth.clamp((degreesToTurn - 10.0F) / 50.0F, 0.0F, 1.0F);
    }
}
