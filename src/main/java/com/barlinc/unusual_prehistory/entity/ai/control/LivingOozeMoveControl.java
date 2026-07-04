package com.barlinc.unusual_prehistory.entity.ai.control;

import com.barlinc.unusual_prehistory.entity.mob.other.LivingOoze;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class LivingOozeMoveControl extends MoveControl {

    private float yRot;
    private int jumpDelay;
    private final LivingOoze ooze;

    public LivingOozeMoveControl(LivingOoze ooze) {
        super(ooze);
        this.ooze = ooze;
        this.yRot = 180.0F * ooze.getYRot() / (float) Math.PI;
    }

    public void setDirection(float yRot) {
        this.yRot = yRot;
    }

    public void setWantedMovement(double speed) {
        this.speedModifier = speed;
        this.operation = MoveControl.Operation.MOVE_TO;
    }

    @Override
    public void tick() {
        this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
        this.mob.yHeadRot = this.mob.getYRot();
        this.mob.yBodyRot = this.mob.getYRot();
        if (this.operation != MoveControl.Operation.MOVE_TO) {
            this.mob.setZza(0.0F);
        } else {
            this.operation = MoveControl.Operation.WAIT;
            if (this.mob.onGround()) {
                this.mob.setSpeed((float) (this.speedModifier * 1.1F * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                if (this.jumpDelay-- <= 0) {
                    this.ooze.setWantsToJump(false);
                    this.jumpDelay = 20;
                    this.ooze.getJumpControl().jump();
                    this.ooze.setHasJumped(true);
                    this.ooze.playSound(this.ooze.getJumpSound(), 0.8F, this.ooze.getSoundPitch());
                } else {
                    this.ooze.xxa = 0.0F;
                    this.ooze.zza = 0.0F;
                    this.mob.setSpeed(0.0F);
                    this.ooze.setWantsToJump(true);
                }
            } else {
                this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            }
        }
    }
}