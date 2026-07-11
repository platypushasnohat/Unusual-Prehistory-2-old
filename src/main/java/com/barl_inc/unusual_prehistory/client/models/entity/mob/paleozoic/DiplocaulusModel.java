package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.DiplocaulusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Diplocaulus;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DiplocaulusModel extends UP2Model<Diplocaulus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart arm_control;
    private final ModelPart left_arm1;
    private final ModelPart left_arm2;
    private final ModelPart right_arm1;
    private final ModelPart right_arm2;
    private final ModelPart leg_control;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;

	public DiplocaulusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tail = this.body.getChild("tail");
        this.arm_control = this.body_main.getChild("arm_control");
        this.left_arm1 = this.arm_control.getChild("left_arm1");
        this.left_arm2 = this.left_arm1.getChild("left_arm2");
        this.right_arm1 = this.arm_control.getChild("right_arm1");
        this.right_arm2 = this.right_arm1.getChild("right_arm2");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg1 = this.leg_control.getChild("left_leg1");
        this.left_leg2 = this.left_leg1.getChild("left_leg2");
        this.right_leg1 = this.leg_control.getChild("right_leg1");
        this.right_leg2 = this.right_leg1.getChild("right_leg2");
	}

	@Override
	public void setupAnim(Diplocaulus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        float deg = ((float) Math.PI / 180F);

        if (entity.getIdleState() != 2) {
            if (entity.isInWaterOrBubble()) {
                this.root.xRot = headPitch * deg;
                this.animateWalk(DiplocaulusAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
            } else {
                if (entity.isSliding())
                    this.animateWalk(DiplocaulusAnimations.SLIDE, limbSwing, limbSwingAmount, 1.5F, 3);
                else this.animateWalk(DiplocaulusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
            }
        }

		this.animateIdleSmooth(entity.idleAnimationState, DiplocaulusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.swimIdleAnimationState, DiplocaulusAnimations.SWIM_IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.quirkAnimationState, this.getQuirkAnimation(), ageInTicks, partialTicks);
		this.animateSmooth(entity.burrowAnimationState, DiplocaulusAnimations.BURROW_HOLD, ageInTicks, partialTicks);
        this.animate(entity.boomerangAnimationState, DiplocaulusAnimations.BOOMERANG, ageInTicks);

		if (this.young) this.applyStatic(DiplocaulusAnimations.BABY_TRANSFORM);

        if (entity.isSitting() && entity.level().getBlockState(entity.blockPosition()).is(Blocks.MUD)) {
            this.body_main.y = 1.0F;
        }
		if (!entity.isSitting()) {
            this.head.xRot += headPitch * deg / 4;
            this.head.yRot += netHeadYaw * deg / 4;
        }
	}

    protected AnimationDefinition getQuirkAnimation() {
        return DiplocaulusAnimations.QUIRK_BLEND_TIGER;
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}