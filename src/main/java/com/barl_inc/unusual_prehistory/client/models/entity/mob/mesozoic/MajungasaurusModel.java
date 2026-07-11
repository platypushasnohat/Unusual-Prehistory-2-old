package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.MajungasaurusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Majungasaurus;
import com.barl_inc.unusual_prehistory.utils.UP2ColorUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class MajungasaurusModel extends UP2Model<Majungasaurus> {

	private float alpha = 1.0F;

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart breathe;
    private final ModelPart arm_left;
    private final ModelPart arm_right;
    private final ModelPart neck;
    private final ModelPart dewlap;
    private final ModelPart head;
    private final ModelPart jaw_upper;
    private final ModelPart eye_left;
    private final ModelPart pupil_left;
    private final ModelPart eye_right;
    private final ModelPart pupil_right;
    private final ModelPart jaw;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

	public MajungasaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.breathe = this.body.getChild("breathe");
        this.arm_left = this.breathe.getChild("arm_left");
        this.arm_right = this.breathe.getChild("arm_right");
        this.neck = this.body.getChild("neck");
        this.dewlap = this.neck.getChild("dewlap");
        this.head = this.neck.getChild("head");
        this.jaw_upper = this.head.getChild("jaw_upper");
        this.eye_left = this.jaw_upper.getChild("eye_left");
        this.pupil_left = this.eye_left.getChild("pupil_left");
        this.eye_right = this.jaw_upper.getChild("eye_right");
        this.pupil_right = this.eye_right.getChild("pupil_right");
        this.jaw = this.head.getChild("jaw");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
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

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(-0.5F, 2.0F, -6.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.5F, 3.0F, 4.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition breathe = body.addOrReplaceChild("breathe", CubeListBuilder.create().texOffs(80, 42).addBox(-3.0F, -8.5F, -11.0F, 6.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(0, 80).addBox(-6.5F, -7.5F, -11.0F, 13.0F, 15.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -4.0F));

        PartDefinition arm_left = breathe.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(66, 123).addBox(-0.99F, 0.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 4.5F, -8.0F));

        PartDefinition arm_right = breathe.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(66, 123).mirror().addBox(-0.01F, 0.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.5F, 4.5F, -8.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(104, 85).addBox(-2.5F, -15.0F, -2.0F, 5.0F, 17.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(70, 85).addBox(-3.5F, -14.0F, -5.0F, 7.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -15.0F));

        PartDefinition dewlap = neck.addOrReplaceChild("dewlap", CubeListBuilder.create().texOffs(58, 117).addBox(0.0F, -4.0F, -4.0F, 0.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(122, 65).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -5.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, -1.0F));

        PartDefinition jaw_upper = head.addOrReplaceChild("jaw_upper", CubeListBuilder.create().texOffs(0, 117).addBox(-4.5F, -6.0F, -5.0F, 9.0F, 6.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(104, 110).addBox(-4.5F, -6.0F, -11.0F, 9.0F, 7.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(70, 80).addBox(-1.0F, -9.0F, -4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(122, 76).addBox(1.5F, -8.0F, -9.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(122, 76).mirror().addBox(-3.5F, -8.0F, -9.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(116, 35).addBox(-4.5F, 1.0F, -11.0F, 9.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition eye_left = jaw_upper.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(120, 25).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -2.5F, -5.5F));

        PartDefinition pupil_left = eye_left.addOrReplaceChild("pupil_left", CubeListBuilder.create().texOffs(136, 27).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(2.0F, 0.0F, 0.0F));

        PartDefinition eye_right = jaw_upper.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(120, 25).mirror().addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -2.5F, -5.5F));

        PartDefinition pupil_right = eye_right.addOrReplaceChild("pupil_right", CubeListBuilder.create().texOffs(136, 27).mirror().addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-2.0F, 0.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(28, 117).addBox(-5.0F, 1.0F, -11.0F, 9.0F, 3.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(120, 0).addBox(-5.0F, 0.0F, -5.0F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(86, 35).addBox(-5.0F, 0.0F, -11.0F, 9.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 2.0F, 0.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(80, 65).addBox(-4.5F, -2.5F, 0.0F, 9.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(86, 22).addBox(-2.5F, -3.5F, 0.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, 7.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.5F, 0.0F, 6.0F, 5.0F, 37.0F, new CubeDeformation(0.0F))
                .texOffs(1, 42).addBox(-1.5F, -3.5F, 0.0F, 3.0F, 1.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(86, 0).addBox(-4.0F, -4.0F, -5.0F, 7.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(120, 9).addBox(-3.0F, -1.0F, -3.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 4.0F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(70, 113).addBox(-3.5F, 0.0F, -4.5F, 7.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(82, 123).addBox(2.5F, 0.0F, -6.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(74, 123).addBox(-0.5F, 0.0F, -6.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(74, 123).addBox(-3.5F, 0.0F, -6.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 10.0F, -0.5F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(86, 0).mirror().addBox(-3.0F, -4.0F, -5.0F, 7.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.5F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(120, 9).mirror().addBox(-2.0F, -1.0F, -3.0F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 7.0F, 4.0F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(70, 113).mirror().addBox(-3.5F, 0.0F, -4.5F, 7.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(82, 123).mirror().addBox(-3.5F, 0.0F, -6.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(74, 123).mirror().addBox(-1.5F, 0.0F, -6.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(74, 123).mirror().addBox(1.5F, 0.0F, -6.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, 10.0F, -0.5F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Majungasaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isInWaterOrBubble() && !entity.isEepy()) {
            if (entity.isCamo()) {
                this.animateWalk(MajungasaurusAnimations.CAMO_WALK, limbSwing, limbSwingAmount, 2, 4);
            } else {
                if (entity.isRunning()) {
                    this.animateWalk(MajungasaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.5F, 3);
                } else {
                    this.animateWalk(MajungasaurusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
                }
            }
        }

		if (this.young) this.applyStatic(MajungasaurusAnimations.BABY_TRANSFORM);

        this.animateIdleSmooth(entity.idleAnimationState, MajungasaurusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, entity.isRunning() ? 3 : 4);
        this.animateIdleSmooth(entity.camoIdleAnimationState, MajungasaurusAnimations.CAMO_IDLE, ageInTicks, partialTicks, limbSwingAmount, 4);
        this.animateSmooth(entity.swimAnimationState, MajungasaurusAnimations.SWIM, ageInTicks, partialTicks);
		this.animateSmooth(entity.attack1AnimationState, MajungasaurusAnimations.BITE_BLEND1, ageInTicks, partialTicks);
		this.animateSmooth(entity.attack2AnimationState, MajungasaurusAnimations.BITE_BLEND2, ageInTicks, partialTicks);
		this.animateSmooth(entity.eyesAnimationState, MajungasaurusAnimations.EYESWIVEL_OVERLAY, ageInTicks, partialTicks);
        this.animateSmooth(entity.yawnAnimationState, MajungasaurusAnimations.IDLE_YAWN_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.sniff1AnimationState, MajungasaurusAnimations.SNIFF_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.sniff2AnimationState, MajungasaurusAnimations.SNIFF_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.shakeAnimationState, MajungasaurusAnimations.IDLE_SHAKE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, MajungasaurusAnimations.SLEEP, ageInTicks, partialTicks);

        this.animateHead(entity, this.neck, netHeadYaw, headPitch);

        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail1.yRot = Mth.lerp(0.2F, this.tail1.yRot, tailYaw * 0.2F);
        this.tail2.yRot = Mth.lerp(0.2F, this.tail2.yRot, tailYaw * 0.15F);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        poseStack.pushPose();
        float red = UP2ColorUtils.unpackRed(color);
        float green = UP2ColorUtils.unpackGreen(color);
        float blue = UP2ColorUtils.unpackBlue(color);
        float modelAlpha = UP2ColorUtils.unpackAlpha(color);
        if (this.young) {
            poseStack.scale(this.youngScaleFactor, this.youngScaleFactor, this.youngScaleFactor);
            poseStack.translate(0.0F, this.bodyYOffset / 16.0F, 0.0F);
        }
        this.root().render(poseStack, vertexConsumer, packedLight, packedOverlay, UP2ColorUtils.packColor(red, green, blue, modelAlpha * alpha));
        poseStack.popPose();
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

    public float getAlpha() {
        return this.alpha;
    }
}