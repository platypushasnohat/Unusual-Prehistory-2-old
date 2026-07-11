package com.barl_inc.unusual_prehistory.entity.ai.control;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class PrehistoricFlyingMoveControl extends MoveControl {

    protected final PrehistoricMob prehistoricMob;
    private final float stepSize;

    public PrehistoricFlyingMoveControl(PrehistoricMob prehistoricMob) {
        this(prehistoricMob, 10);
    }

    public PrehistoricFlyingMoveControl(PrehistoricMob prehistoricMob, float stepSize) {
        super(prehistoricMob);
        this.prehistoricMob = prehistoricMob;
        this.stepSize = stepSize;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void tick() {
        if (!prehistoricMob.refuseToMove()) {
            if (operation == Operation.MOVE_TO) {
                Vec3 moveDirection = new Vec3(wantedX - prehistoricMob.getX(), wantedY - prehistoricMob.getY(), wantedZ - prehistoricMob.getZ());
                double length = moveDirection.length();
                double width = prehistoricMob.getBoundingBox().getSize();
                float speed = (float) (speedModifier * prehistoricMob.getAttributeValue(Attributes.FLYING_SPEED));
                Vec3 scaled = moveDirection.scale(speed * 0.05D / length);
                this.prehistoricMob.setDeltaMovement(prehistoricMob.getDeltaMovement().add(scaled).add(0, -0.01D, 0));
                if (length < width) {
                    this.operation = Operation.WAIT;
                } else if (length >= width) {
                    float yaw = -((float) Mth.atan2(scaled.x, scaled.z)) * (180F / (float) Math.PI);
                    this.prehistoricMob.setYRot(Mth.approachDegrees(prehistoricMob.getYRot(), yaw, stepSize));
                }
            }
        }
    }
}