package com.barlinc.unusual_prehistory.client.models.entity.mob.recently_extinct;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.recently_extinct.GastricBroodingFrogAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.recently_extinct.GastricBroodingFrog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class GastricBroodingFrogModel extends UP2Model<GastricBroodingFrog> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw_upper;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart mouth;
    private final ModelPart jaw;
    private final ModelPart tongue;
    private final ModelPart arm_control;
    private final ModelPart arm_left1;
    private final ModelPart arm_left2;
    private final ModelPart arm_right1;
    private final ModelPart arm_right2;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;

	public GastricBroodingFrogModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.jaw_upper = this.head.getChild("jaw_upper");
        this.eye_left = this.jaw_upper.getChild("eye_left");
        this.eye_right = this.jaw_upper.getChild("eye_right");
        this.mouth = this.body.getChild("mouth");
        this.jaw = this.body.getChild("jaw");
        this.tongue = this.jaw.getChild("tongue");
        this.arm_control = this.body_main.getChild("arm_control");
        this.arm_left1 = this.arm_control.getChild("arm_left1");
        this.arm_left2 = this.arm_left1.getChild("arm_left2");
        this.arm_right1 = this.arm_control.getChild("arm_right1");
        this.arm_right2 = this.arm_right1.getChild("arm_right2");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_left1 = this.leg_control.getChild("leg_left1");
        this.leg_left2 = this.leg_left1.getChild("leg_left2");
        this.leg_right1 = this.leg_control.getChild("leg_right1");
        this.leg_right2 = this.leg_right1.getChild("leg_right2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(38, 0).addBox(-4.0F, -7.9F, -9.0F, 8.0F, 8.0F, 9.0F, new CubeDeformation(-0.05F)), PartPose.offset(0.0F, 1.0F, 5.0F));

        PartDefinition jaw_upper = head.addOrReplaceChild("jaw_upper", CubeListBuilder.create().texOffs(10, 42).addBox(-1.0F, -0.01F, -11.01F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.02F))
                .texOffs(0, 15).addBox(-4.0F, -3.0F, -11.0F, 8.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition eye_left = jaw_upper.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(38, 33).mirror().addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, -1.0F, -7.0F));

        PartDefinition eye_right = jaw_upper.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(38, 33).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -1.0F, -7.0F));

        PartDefinition mouth = body.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -11.0F, 8.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 29).addBox(-4.0F, 0.0F, -11.0F, 8.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition tongue = jaw.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(-11, 48).addBox(-2.0F, 0.0F, -10.978F, 4.0F, 0.0F, 11.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -0.5F));

        PartDefinition arm_left1 = arm_control.addOrReplaceChild("arm_left1", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-1.0F, 0.0F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition arm_left2 = arm_left1.addOrReplaceChild("arm_left2", CubeListBuilder.create().texOffs(38, 41).mirror().addBox(-1.0F, 0.0F, -2.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-1.0F, 2.9F, -1.5F));

        PartDefinition arm_right1 = arm_control.addOrReplaceChild("arm_right1", CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition arm_right2 = arm_right1.addOrReplaceChild("arm_right2", CubeListBuilder.create().texOffs(38, 41).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.02F)), PartPose.offset(1.0F, 2.9F, -1.5F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(38, 24).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(38, 17).addBox(-4.0F, -0.1F, -3.0F, 9.0F, 0.0F, 7.0F, new CubeDeformation(0.02F)), PartPose.offset(2.0F, 5.0F, -2.0F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(38, 24).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(38, 17).mirror().addBox(-5.0F, -0.1F, -3.0F, 9.0F, 0.0F, 7.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-2.0F, 5.0F, -2.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(@NotNull GastricBroodingFrog entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isLeaping() && !entity.isPassenger() && !entity.isSitting()) {
            if (entity.isInWaterOrBubble()) {
                this.animateWalk(GastricBroodingFrogAnimations.SWIM, limbSwing, limbSwingAmount, 1.0F, 2.5F);
            } else {
                this.animateWalk(GastricBroodingFrogAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
            }
        }
        this.animateIdleSmooth(entity.idleAnimationState, GastricBroodingFrogAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
        this.animateIdleSmooth(entity.idleAnimationState, GastricBroodingFrogAnimations.IDLE_OVERLAY, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
        this.animateIdleSmooth(entity.swimIdleAnimationState, GastricBroodingFrogAnimations.SWIM, ageInTicks, partialTicks, limbSwingAmount, 3);
        this.animateSmooth(entity.leapAnimationState, GastricBroodingFrogAnimations.JUMP_HOLD, ageInTicks, partialTicks);
        this.animateSmooth(entity.eatAnimationState, GastricBroodingFrogAnimations.ATTTACK_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.attackAnimationState, GastricBroodingFrogAnimations.VOMIT_LAUNCH_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.blinkAnimationState, GastricBroodingFrogAnimations.BLINK_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.croakAnimationState, GastricBroodingFrogAnimations.IDLE_CROAK_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.yawnAnimationState, GastricBroodingFrogAnimations.IDLE_YAWN_BLEND, ageInTicks, partialTicks);

        if (entity.isInWaterOrBubble()) {
            this.animateSmooth(entity.sitAnimationState, GastricBroodingFrogAnimations.SWIM, ageInTicks, partialTicks);
        } else {
            this.animateSmooth(entity.sitAnimationState, GastricBroodingFrogAnimations.IDLE, ageInTicks, partialTicks);
        }

        if (entity.isInWaterOrBubble() && !entity.isPassenger()) {
            this.root.xRot = headPitch * ((float) Math.PI / 180F);
        }

        if (entity.hasFroglet()) {
            this.applyStatic(GastricBroodingFrogAnimations.PREGNANT_OVERLAY);
        }
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}