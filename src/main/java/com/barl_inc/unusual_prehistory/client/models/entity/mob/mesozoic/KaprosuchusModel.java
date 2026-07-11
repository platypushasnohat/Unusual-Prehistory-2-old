package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.KaprosuchusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Kaprosuchus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class KaprosuchusModel extends UP2Model<Kaprosuchus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body_upper;
    private final ModelPart body;
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
    private final ModelPart left_leg3;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;
    private final ModelPart right_leg3;

	public KaprosuchusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body_upper = this.body_main.getChild("body_upper");
        this.body = this.body_upper.getChild("body");
        this.head = this.body.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tail = this.body.getChild("tail");
        this.arm_control = this.body_upper.getChild("arm_control");
        this.left_arm1 = this.arm_control.getChild("left_arm1");
        this.left_arm2 = this.left_arm1.getChild("left_arm2");
        this.right_arm1 = this.arm_control.getChild("right_arm1");
        this.right_arm2 = this.right_arm1.getChild("right_arm2");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg1 = this.leg_control.getChild("left_leg1");
        this.left_leg2 = this.left_leg1.getChild("left_leg2");
        this.left_leg3 = this.left_leg2.getChild("left_leg3");
        this.right_leg1 = this.leg_control.getChild("right_leg1");
        this.right_leg2 = this.right_leg1.getChild("right_leg2");
        this.right_leg3 = this.right_leg2.getChild("right_leg3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -16.0F, 0.0F));

        PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 6.5F));

        PartDefinition body = body_upper.addOrReplaceChild("body", CubeListBuilder.create().texOffs(34, 101).addBox(-3.0F, -4.0F, 1.5F, 6.0F, 8.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(37, 54).addBox(-2.0F, -6.0F, -11.5F, 4.0F, 2.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(82, 0).addBox(-5.5F, -4.0F, -11.5F, 11.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -6.5F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(58, 119).addBox(-2.5F, -5.0F, -8.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(82, 41).addBox(-2.5F, -3.0F, -7.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(68, 87).addBox(-2.5F, 1.0F, -18.0F, 5.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 87).addBox(-2.5F, -2.0F, -18.0F, 5.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(116, 25).addBox(-2.5F, -2.0F, -6.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -11.5F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 102).addBox(-3.5F, 0.0F, -5.0F, 7.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(34, 87).addBox(-2.5F, 2.0F, -17.0F, 5.0F, 2.0F, 12.0F, new CubeDeformation(0.01F))
                .texOffs(98, 41).addBox(-1.5F, 4.0F, -17.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -1.0F));

        PartDefinition fronteeth_r1 = jaw.addOrReplaceChild("fronteeth_r1", CubeListBuilder.create().texOffs(122, 87).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 2.0F, -17.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition sideteeth_r1 = jaw.addOrReplaceChild("sideteeth_r1", CubeListBuilder.create().texOffs(64, 101).mirror().addBox(0.0F, -6.0F, -1.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 2.0F, -16.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition sideteeth_r2 = jaw.addOrReplaceChild("sideteeth_r2", CubeListBuilder.create().texOffs(64, 101).addBox(0.0F, -6.0F, -1.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.5F, 2.0F, -16.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -5.5F, -1.5F, 3.0F, 7.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, 9.0F));

        PartDefinition spikes_r1 = tail.addOrReplaceChild("spikes_r1", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(0.0F, -8.0F, -1.0F, 0.0F, 8.0F, 34.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -5.5F, -0.5F, 0.0F, 0.0F, -0.0436F));

        PartDefinition spikes_r2 = tail.addOrReplaceChild("spikes_r2", CubeListBuilder.create().texOffs(0, 45).addBox(0.0F, -8.0F, -1.0F, 0.0F, 8.0F, 34.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.5F, -5.5F, -0.5F, 0.0F, 0.0F, 0.0873F));

        PartDefinition arm_control = body_upper.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, -16.0F));

        PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(78, 119).addBox(-2.0F, -1.0F, -2.0F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, 0.0F));

        PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(117, 38).addBox(-2.5F, -1.0F, -3.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 8.9F, -1.0F));

        PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(78, 119).mirror().addBox(-1.0F, -1.0F, -2.0F, 3.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 0.0F, 0.0F));

        PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(117, 38).mirror().addBox(-2.5F, -1.0F, -3.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, 8.9F, -1.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 6.5F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(102, 87).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(20, 112).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(26, 102).addBox(1.5F, -2.0F, 3.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(117, 45).addBox(-2.5F, -1.0F, -3.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.9F, 0.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(102, 87).mirror().addBox(-2.0F, -3.0F, -3.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(26, 102).mirror().addBox(-1.5F, -2.0F, 3.0F, 0.0F, 8.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(20, 112).mirror().addBox(-1.5F, -2.0F, -1.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(117, 45).mirror().addBox(-2.5F, -1.0F, -3.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 8.9F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(@NotNull Kaprosuchus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isLeaping() && !entity.isSitting() && !entity.isEepy()) {
            if (entity.isInWaterOrBubble()) {
                this.animateWalk(KaprosuchusAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
            } else {
                if (entity.isRunning()) this.animateWalk(KaprosuchusAnimations.RUN, limbSwing, limbSwingAmount, 1, 2);
                else this.animateWalk(KaprosuchusAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 3);
            }
        }
		this.animateIdleSmooth(entity.idleAnimationState, KaprosuchusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.swimIdleAnimationState, KaprosuchusAnimations.SWIM_IDLE, ageInTicks, partialTicks, limbSwingAmount);
        if (entity.isInWaterOrBubble()) {
            this.animateSmooth(entity.attack1AnimationState, KaprosuchusAnimations.SWIM_BITE_BLEND1, ageInTicks, partialTicks);
            this.animateSmooth(entity.attack2AnimationState, KaprosuchusAnimations.SWIM_BITE_BLEND2, ageInTicks, partialTicks);
        } else {
            this.animateSmooth(entity.attack1AnimationState, KaprosuchusAnimations.BITE_BLEND1, ageInTicks, partialTicks);
            this.animateSmooth(entity.attack2AnimationState, KaprosuchusAnimations.BITE_BLEND2, ageInTicks, partialTicks);
        }
        this.animateSmooth(entity.sitAnimationState, KaprosuchusAnimations.SIT, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, KaprosuchusAnimations.SLEEP, ageInTicks, partialTicks);
        this.animate(entity.leapAnimationState, KaprosuchusAnimations.POUNCE, ageInTicks);

        if (this.young) this.applyStatic(KaprosuchusAnimations.BABY_TRANSFORM);
        if (entity.isInWaterOrBubble() || entity.isLeaping()) this.root.xRot = headPitch * ((float) Math.PI / 180F);

        this.animateHead(entity, this.head, netHeadYaw, headPitch);
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail.yRot = Mth.lerp(0.3F, this.tail.yRot, tailYaw * 0.25F);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}