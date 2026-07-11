package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.UlughbegsaurusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Ulughbegsaurus;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class UlughbegsaurusModel extends UP2Model<Ulughbegsaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart upper_jaw;
    private final ModelPart brow;
    private final ModelPart crestdetail;
    private final ModelPart jaw;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart leg_control;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart left_leg3;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;
    private final ModelPart right_leg3;

	public UlughbegsaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.upper_jaw = this.head.getChild("upper_jaw");
        this.brow = this.upper_jaw.getChild("brow");
        this.crestdetail = this.brow.getChild("crestdetail");
        this.jaw = this.head.getChild("jaw");
        this.left_arm = this.body.getChild("left_arm");
        this.right_arm = this.body.getChild("right_arm");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
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

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.5F, -12.0F, -22.0F, 17.0F, 19.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 51).addBox(-3.5F, -20.9F, -8.0F, 7.0F, 28.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.1F, -20.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.5F, -15.4F, -1.0F));

        PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(90, 55).addBox(-4.5F, -10.0F, -11.0F, 9.0F, 10.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(96, 2).addBox(-3.5F, -10.0F, -19.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 4.5F, 0.0F));

        PartDefinition brow = upper_jaw.addOrReplaceChild("brow", CubeListBuilder.create(), PartPose.offset(0.0F, -7.75F, -3.0F));

        PartDefinition crestdetail = brow.addOrReplaceChild("crestdetail", CubeListBuilder.create().texOffs(85, 77).addBox(-6.0F, 0.0F, -7.0F, 13.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(4, 110).addBox(-6.0F, -1.0F, -7.0F, 13.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -0.25F, 3.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(90, 41).addBox(-5.0F, 0.0F, -11.0F, 9.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(96, 22).addBox(-4.0F, 0.0F, -19.0F, 7.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.5F, 0.0F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 10).addBox(-4.0F, -3.0F, -4.0F, 5.0F, 14.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 95).addBox(-2.0F, 11.0F, 2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 95).addBox(-2.0F, 11.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 95).addBox(-2.0F, 11.0F, -4.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 4.0F, -16.0F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 10).mirror().addBox(-1.0F, -3.0F, -4.0F, 5.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 95).mirror().addBox(-1.0F, 11.0F, -4.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 95).mirror().addBox(-1.0F, 11.0F, 2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 95).mirror().addBox(-1.0F, 11.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5F, 4.0F, -16.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(56, 88).addBox(-4.5F, -5.5F, 0.0F, 9.0F, 11.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, 10.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(15, 90).addBox(-2.5F, -3.5F, 0.0F, 5.0F, 7.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 22.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(46, 51).addBox(-4.0F, -5.0F, -6.0F, 8.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(8.5F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(66, 8).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 18.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 6.0F));

        PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(6, 95).addBox(-4.0F, 0.0F, -7.0F, 8.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(39, 54).addBox(2.0F, 0.0F, -10.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(39, 54).addBox(-1.0F, 0.0F, -10.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(39, 54).addBox(-4.0F, 0.0F, -10.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(46, 51).mirror().addBox(-4.0F, -5.0F, -6.0F, 8.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.5F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(66, 8).mirror().addBox(-3.0F, -3.0F, -3.0F, 6.0F, 18.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 8.0F, 6.0F));

        PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(6, 95).mirror().addBox(-4.0F, 0.0F, -7.0F, 8.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(39, 54).mirror().addBox(2.0F, 0.0F, -10.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(39, 54).mirror().addBox(-4.0F, 0.0F, -10.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(39, 54).mirror().addBox(-1.0F, 0.0F, -10.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 15.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(@NotNull Ulughbegsaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isInWaterOrBubble() && !entity.isSitting() && !entity.isEepy() && !entity.isLeaping()) {
            if (entity.isRunning() || (entity.hasControllingPassenger() && entity.getControllingPassenger().isSprinting())) {
                this.animateWalk(UlughbegsaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.25F, 2.5F);
            } else {
                this.animateWalk(UlughbegsaurusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
            }
        }
		this.animateIdleSmooth(entity.idleAnimationState, UlughbegsaurusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.swimAnimationState, UlughbegsaurusAnimations.SWIM, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack1AnimationState, UlughbegsaurusAnimations.ATTACK_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack2AnimationState, UlughbegsaurusAnimations.ATTACK_BLEND2, ageInTicks, partialTicks);
		this.animateSmooth(entity.sitAnimationState, UlughbegsaurusAnimations.SIT, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, UlughbegsaurusAnimations.SLEEP, ageInTicks, partialTicks);
        this.animateSmooth(entity.yawnAnimationState, UlughbegsaurusAnimations.YAWN_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.shakeAnimationState, UlughbegsaurusAnimations.SHAKE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.jumpAnimationState, UlughbegsaurusAnimations.JUMP, ageInTicks, partialTicks);
        this.animateSmooth(entity.blinkAnimationState, UlughbegsaurusAnimations.BLINK_BLEND, ageInTicks, partialTicks);
        this.animate(entity.eatAnimationState, UlughbegsaurusAnimations.EAT_BLEND, ageInTicks);

        if (this.young) this.applyStatic(UlughbegsaurusAnimations.BABY_TRANSFORM);

        this.animateHead(entity, this.neck, netHeadYaw, headPitch);

        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail1.yRot = Mth.lerp(0.2F, this.tail1.yRot, tailYaw * 0.2F);
        this.tail2.yRot = Mth.lerp(0.2F, this.tail2.yRot, tailYaw * 0.15F);
	}

    public Vec3 getRiderPosition(Vec3 offset) {
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        Vector4f armOffsetVec = new Vector4f((float) offset.x, (float) offset.y, (float) offset.z, 1.0F);
        armOffsetVec.mul(poseStack.last().pose());
        Vec3 vec3 = new Vec3(armOffsetVec.x(), armOffsetVec.y(), armOffsetVec.z());
        poseStack.popPose();
        return vec3;
    }

    public void translateRiderToBody(PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        this.body_main.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}