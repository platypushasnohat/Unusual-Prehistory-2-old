package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.JawlessFishAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.JawlessFish;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public abstract class JawlessFishModel extends UP2Model<JawlessFish> {

    public JawlessFishModel() {
        super(0.5F, 24);
    }

    @Override
    public void setupAnim(JawlessFish entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;
        if (entity.isInWaterOrBubble()) {
            this.animateWalk(JawlessFishAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, JawlessFishAnimations.SWIM, ageInTicks, partialTicks, limbSwingAmount, 2.5F, 0.75F);
        this.animateSmooth(entity.flopAnimationState, JawlessFishAnimations.FLOP, ageInTicks, partialTicks);
        this.swimControl().xRot = entity.getTilt(partialTicks) * Mth.DEG_TO_RAD;
        this.swimControl().zRot = entity.getRoll(partialTicks) * Mth.DEG_TO_RAD;
    }

    public abstract ModelPart swimControl();
}
