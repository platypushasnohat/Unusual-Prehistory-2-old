package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.AustroraptorAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Austroraptor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class AustroraptorModel extends UP2Model<Austroraptor> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart neck1;
    private final ModelPart neck2;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart jaw;
    private final ModelPart crest;
    private final ModelPart crest_top;
    private final ModelPart crest_left;
    private final ModelPart crest_right;
    private final ModelPart arm_left1;
    private final ModelPart arm_left2;
    private final ModelPart wing_left;
    private final ModelPart arm_right1;
    private final ModelPart arm_right2;
    private final ModelPart wing_right;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

	public AustroraptorModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.neck1 = this.body.getChild("neck1");
        this.neck2 = this.neck1.getChild("neck2");
        this.head = this.neck2.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.jaw = this.head.getChild("jaw");
        this.crest = this.head.getChild("crest");
        this.crest_top = this.crest.getChild("crest_top");
        this.crest_left = this.crest.getChild("crest_left");
        this.crest_right = this.crest.getChild("crest_right");
        this.arm_left1 = this.body.getChild("arm_left1");
        this.arm_left2 = this.arm_left1.getChild("arm_left2");
        this.wing_left = this.arm_left1.getChild("wing_left");
        this.arm_right1 = this.body.getChild("arm_right1");
        this.arm_right2 = this.arm_right1.getChild("arm_right2");
        this.wing_right = this.arm_right1.getChild("wing_right");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_left1 = this.leg_control.getChild("leg_left1");
        this.leg_left2 = this.leg_left1.getChild("leg_left2");
        this.leg_left3 = this.leg_left2.getChild("leg_left3");
        this.leg_right1 = this.leg_control.getChild("leg_right1");
        this.leg_right2 = this.leg_right1.getChild("leg_right2");
        this.leg_right3 = this.leg_right2.getChild("leg_right3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 56).addBox(-4.0F, -8.0F, -12.0F, 8.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck1 = body.addOrReplaceChild("neck1", CubeListBuilder.create().texOffs(26, 80).addBox(-2.0F, -9.5F, -3.0F, 4.0F, 12.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -5.5F, -12.0F));

        PartDefinition neck2 = neck1.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(56, 84).addBox(-2.0F, -9.0F, -1.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, 2.0F));

        PartDefinition head = neck2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(84, 26).addBox(-2.5F, -2.51F, -4.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 80).addBox(-1.5F, -0.5F, -14.0F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(12, 92).addBox(-2.5F, -2.51F, -6.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, 0.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(100, 28).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(2.0F, -0.01F, -2.5F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(100, 28).mirror().addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-2.0F, -0.01F, -2.5F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(80, 0).addBox(-1.5F, 0.0F, -10.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, -4.0F));

        PartDefinition crest = head.addOrReplaceChild("crest", CubeListBuilder.create().texOffs(58, 27).addBox(-6.5F, -20.0F, 0.0F, 13.0F, 20.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -2.5F, -2.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition crest_top = crest.addOrReplaceChild("crest_top", CubeListBuilder.create().texOffs(84, 37).addBox(-7.5F, -4.0F, 0.0F, 15.0F, 4.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -20.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition crest_left = crest.addOrReplaceChild("crest_left", CubeListBuilder.create().texOffs(44, 84).addBox(0.0F, -9.0F, 0.0F, 6.0F, 20.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(6.5F, -11.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition crest_right = crest.addOrReplaceChild("crest_right", CubeListBuilder.create().texOffs(44, 84).mirror().addBox(-6.0F, -9.0F, 0.0F, 6.0F, 20.0F, 0.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-6.5F, -11.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition arm_left1 = body.addOrReplaceChild("arm_left1", CubeListBuilder.create().texOffs(72, 91).addBox(0.0F, -2.0F, -1.5F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 92).addBox(0.0F, 4.0F, -5.5F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(90, 68).addBox(2.0F, 7.0F, -5.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.02F)), PartPose.offset(4.0F, 0.0F, -9.5F));

        PartDefinition arm_left2 = arm_left1.addOrReplaceChild("arm_left2", CubeListBuilder.create().texOffs(94, 55).addBox(-2.0F, 0.0F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 7.0F, -4.0F));

        PartDefinition wing_left = arm_left1.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(74, 68).addBox(-0.01F, -10.0F, 0.0F, 0.0F, 15.0F, 8.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(2.0F, 6.0F, 1.5F, 0.0F, 0.3491F, 0.0F));

        PartDefinition arm_right1 = body.addOrReplaceChild("arm_right1", CubeListBuilder.create().texOffs(72, 91).mirror().addBox(-2.0F, -2.0F, -1.5F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 92).mirror().addBox(-2.0F, 4.0F, -5.5F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(90, 68).mirror().addBox(-2.0F, 7.0F, -5.5F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-4.0F, 0.0F, -9.5F));

        PartDefinition arm_right2 = arm_right1.addOrReplaceChild("arm_right2", CubeListBuilder.create().texOffs(94, 55).mirror().addBox(0.0F, 0.0F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 7.0F, -4.0F));

        PartDefinition wing_right = arm_right1.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(74, 68).mirror().addBox(0.01F, -10.0F, 0.0F, 0.0F, 15.0F, 8.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 6.0F, 1.5F, 0.0F, -0.3491F, 0.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(44, 68).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.5F, 2.0F));

        PartDefinition tailfeathers_r1 = tail1.addOrReplaceChild("tailfeathers_r1", CubeListBuilder.create().texOffs(58, 47).mirror().addBox(-5.0F, 0.0F, -2.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.5F, 7.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition tailfeathers_r2 = tail1.addOrReplaceChild("tailfeathers_r2", CubeListBuilder.create().texOffs(58, 47).addBox(0.0F, 0.0F, -2.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -1.5F, 7.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 27).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 11.0F));

        PartDefinition tailfeathers_r3 = tail2.addOrReplaceChild("tailfeathers_r3", CubeListBuilder.create().texOffs(3, 3).mirror().addBox(-11.0F, 0.0F, -9.0F, 11.0F, 0.0F, 24.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.5F, 11.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition tailfeathers_r4 = tail2.addOrReplaceChild("tailfeathers_r4", CubeListBuilder.create().texOffs(3, 3).addBox(0.0F, 0.0F, -9.0F, 11.0F, 0.0F, 24.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -0.5F, 11.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 26.0F));

        PartDefinition tailfeathers_r5 = tail3.addOrReplaceChild("tailfeathers_r5", CubeListBuilder.create().texOffs(44, 56).mirror().addBox(-13.0F, 0.0F, 0.0F, 13.0F, 0.0F, 12.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition tailfeathers_r6 = tail3.addOrReplaceChild("tailfeathers_r6", CubeListBuilder.create().texOffs(44, 56).addBox(0.0F, 0.0F, 0.0F, 13.0F, 0.0F, 12.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(80, 11).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(90, 79).addBox(-1.0F, -2.01F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 3.0F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(82, 91).addBox(-1.0F, -4.01F, -4.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.02F))
                .texOffs(82, 41).addBox(-2.0F, -0.01F, -5.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(80, 11).mirror().addBox(-2.0F, -3.0F, -3.0F, 4.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.5F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(90, 79).mirror().addBox(-1.0F, -2.01F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 5.0F, 3.0F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(82, 91).mirror().addBox(1.0F, -4.01F, -4.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.02F)).mirror(false)
                .texOffs(82, 41).mirror().addBox(-3.0F, -0.01F, -5.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NotNull Austroraptor entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isInWaterOrBubble()) {
            if (entity.onGround()) {
                if (entity.isRunning()) {
                    this.animateWalk(AustroraptorAnimations.RUN, limbSwing, limbSwingAmount, 1.0F, 2.5F);
                } else {
                    this.animateWalk(AustroraptorAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
                }
            }
        } else {
            this.animateWalk(AustroraptorAnimations.SWIM, limbSwing, limbSwingAmount, 1.0F, 2.5F);
        }

		this.animateIdleSmooth(entity.idleAnimationState, AustroraptorAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
        this.animateIdleSmooth(entity.swimAnimationState, AustroraptorAnimations.SWIM_IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F, 1.8F);

        if (!entity.onGround() || entity.isInWaterOrBubble()) {
            this.animateSmooth(entity.attackAnimationState, AustroraptorAnimations.BITE_BLEND, ageInTicks, partialTicks);
        } else {
            this.animateSmooth(entity.attackAnimationState, AustroraptorAnimations.BITE_RUN_BLEND, ageInTicks, partialTicks);
        }

		this.animateSmooth(entity.fallAnimationState, AustroraptorAnimations.JUMP, ageInTicks, partialTicks);
        this.animateSmooth(entity.fishingAnimationState, AustroraptorAnimations.FISHING_SURFACE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.preen1AnimationState, AustroraptorAnimations.PREEN1, ageInTicks, partialTicks);
        this.animateSmooth(entity.preen2AnimationState, AustroraptorAnimations.PREEN2, ageInTicks, partialTicks);
        this.animateSmooth(entity.shakeAnimationState, AustroraptorAnimations.SHAKE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.eatAnimationState, AustroraptorAnimations.EAT_BLEND, ageInTicks, partialTicks);

        if (young) {
            this.applyStatic(AustroraptorAnimations.BABY_TRANSFORM);
        }

        if (entity.getShearedTicks() > 0) {
            this.applyStatic(AustroraptorAnimations.SHEARED_OVERLAY);
        }

        if ((entity.getId() & 1) == 0) {
            this.animateSmooth(entity.eepyAnimationState, AustroraptorAnimations.SLEEP1, ageInTicks, partialTicks);
        } else {
            this.animateSmooth(entity.eepyAnimationState, AustroraptorAnimations.SLEEP2, ageInTicks, partialTicks);
        }

        this.faceTarget(entity, netHeadYaw, headPitch, 2, neck1);
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail1.yRot = Mth.lerp(0.25F, tail1.yRot, tailYaw * 0.15F);
        this.tail2.yRot = Mth.lerp(0.25F, tail2.yRot, tailYaw * 0.1F);
	}

    public void translateToMouth(PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        this.body_main.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        this.neck1.translateAndRotate(poseStack);
        this.neck2.translateAndRotate(poseStack);
        this.head.translateAndRotate(poseStack);
        this.jaw.translateAndRotate(poseStack);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}