package com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.mesozoic.DimorphodonAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Dimorphodon;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DimorphodonModel extends UP2Model<Dimorphodon> {

    private final ModelPart root;
    private final ModelPart flight_control;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart jaw;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart wing_control;
    private final ModelPart wing_left1;
    private final ModelPart wing_membrane_left;
    private final ModelPart wing_left2;
    private final ModelPart wing_left3;
    private final ModelPart wing_right1;
    private final ModelPart wing_membrane_right;
    private final ModelPart wing_right2;
    private final ModelPart wing_right3;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;

	public DimorphodonModel(ModelPart root) {
        super(0.5F, 24, RenderType::entityCutout);
        this.root = root.getChild("root");
        this.flight_control = this.root.getChild("flight_control");
        this.body_main = this.flight_control.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.jaw = this.head.getChild("jaw");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.wing_control = this.body_main.getChild("wing_control");
        this.wing_left1 = this.wing_control.getChild("wing_left1");
        this.wing_membrane_left = this.wing_left1.getChild("wing_membrane_left");
        this.wing_left2 = this.wing_left1.getChild("wing_left2");
        this.wing_left3 = this.wing_left1.getChild("wing_left3");
        this.wing_right1 = this.wing_control.getChild("wing_right1");
        this.wing_membrane_right = this.wing_right1.getChild("wing_membrane_right");
        this.wing_right2 = this.wing_right1.getChild("wing_right2");
        this.wing_right3 = this.wing_right1.getChild("wing_right3");
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

        PartDefinition flight_control = root.addOrReplaceChild("flight_control", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 0.0F));

        PartDefinition body_main = flight_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 20).addBox(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(43, 30).addBox(-2.5F, -6.0F, -2.0F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(0, 12).addBox(-2.5F, -6.0F, -10.0F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.01F))
                .texOffs(8, -4).addBox(-2.5F, -1.0F, -10.0F, 0.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(13, 6).addBox(-2.5F, -1.0F, -10.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(8, -4).mirror().addBox(2.5F, -1.0F, -10.0F, 0.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -7.0F, -6.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(18, 12).addBox(-0.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offset(2.0F, -2.5F, 0.5F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(18, 12).mirror().addBox(-0.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-2.0F, -2.5F, 0.5F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(18, 10).addBox(-2.0F, 0.0F, -8.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -2.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, -13).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, 2.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, -16).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 16.0F));

        PartDefinition wing_control = body_main.addOrReplaceChild("wing_control", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -4.0F));

        PartDefinition wing_left1 = wing_control.addOrReplaceChild("wing_left1", CubeListBuilder.create(), PartPose.offsetAndRotation(3.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.8378F));

        PartDefinition wing_membrane_left = wing_left1.addOrReplaceChild("wing_membrane_left", CubeListBuilder.create().texOffs(23, 7).addBox(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));

        PartDefinition wing_left2 = wing_left1.addOrReplaceChild("wing_left2", CubeListBuilder.create().texOffs(0, 4).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 0.0F, -1.0F, 0.0F, 0.0F, -0.8552F));

        PartDefinition wing_left3 = wing_left1.addOrReplaceChild("wing_left3", CubeListBuilder.create().texOffs(-11, 35).addBox(0.0F, 0.0F, -1.0F, 21.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 0.0F, 0.0F, 0.0F, 0.0F, -2.0944F));

        PartDefinition wing_right1 = wing_control.addOrReplaceChild("wing_right1", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.8378F));

        PartDefinition wing_membrane_right = wing_right1.addOrReplaceChild("wing_membrane_right", CubeListBuilder.create().texOffs(23, 7).mirror().addBox(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -1.0F));

        PartDefinition wing_right2 = wing_right1.addOrReplaceChild("wing_right2", CubeListBuilder.create().texOffs(0, 4).mirror().addBox(-1.5F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.8552F));

        PartDefinition wing_right3 = wing_right1.addOrReplaceChild("wing_right3", CubeListBuilder.create().texOffs(-11, 35).mirror().addBox(-21.0F, 0.0F, -1.0F, 21.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.0944F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(-3, 6).addBox(-2.5F, -0.02F, -4.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(9, 5).addBox(-0.5F, -0.02F, 1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(2, 11).addBox(-2.5F, -0.02F, -4.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(0, 4).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(-3, 6).addBox(3.5F, -0.02F, -4.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(9, 5).addBox(5.5F, -0.02F, 1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(2, 11).addBox(3.5F, -0.02F, -4.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 6.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Dimorphodon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

		if (!entity.isFlying()) {
            this.animateWalk(DimorphodonAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 3);
		}

        this.animateIdleSmooth(entity.idleAnimationState, DimorphodonAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.flyAnimationState, DimorphodonAnimations.FLY, ageInTicks, partialTicks);
        this.animateSmooth(entity.flyFastAnimationState, DimorphodonAnimations.FLY_FAST, ageInTicks, partialTicks);
        this.animateSmooth(entity.climbAnimationState, DimorphodonAnimations.CLIMB, ageInTicks, partialTicks);
        this.animateSmooth(entity.idleAnimationState, DimorphodonAnimations.CHATTER_BLEND, ageInTicks, partialTicks);

        this.faceTarget(entity, netHeadYaw, headPitch, 1.8F, head);

        if (entity.isFlying()) {
            this.flight_control.xRot += entity.getFlightPitch(partialTicks) * Mth.DEG_TO_RAD;
            this.flight_control.zRot += entity.getFlightRoll(partialTicks) * Mth.DEG_TO_RAD;
        }

		if (young) {
            this.applyStatic(DimorphodonAnimations.BABY_TRANSFORM);
        }
	}

	@Override
	public ModelPart root() {
		return root;
	}
}