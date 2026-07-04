package com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.mesozoic.CoelophysisAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Coelophysis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class CoelophysisModel extends UP2Model<Coelophysis> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body_upper;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart arm_control;
    private final ModelPart arm_left;
    private final ModelPart arm_right;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

	public CoelophysisModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body_upper = this.body_main.getChild("body_upper");
        this.neck = this.body_upper.getChild("neck");
        this.head = this.neck.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.arm_control = this.body_upper.getChild("arm_control");
        this.arm_left = this.arm_control.getChild("arm_left");
        this.arm_right = this.arm_control.getChild("arm_right");
        this.tail1 = this.body_upper.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
        this.leg_left1 = this.body_main.getChild("leg_left1");
        this.leg_left2 = this.leg_left1.getChild("leg_left2");
        this.leg_left3 = this.leg_left2.getChild("leg_left3");
        this.leg_right1 = this.body_main.getChild("leg_right1");
        this.leg_right2 = this.leg_right1.getChild("leg_right2");
        this.leg_right3 = this.leg_right2.getChild("leg_right3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create().texOffs(44, 0).addBox(-2.0F, -3.0F, -8.0F, 4.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 2.5F));

        PartDefinition neck = body_upper.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(44, 38).addBox(-1.0F, -11.0F, -2.0F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -8.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(45, 16).addBox(-1.0F, -1.5F, -11.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(27, 13).addBox(-1.0F, 0.5F, -10.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(52, 53).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.5F, -1.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(28, 7).addBox(-0.5F, -1.0F, -9.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(44, 26).addBox(-1.0F, 0.0F, -9.0F, 2.0F, 1.0F, 8.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.5F, -2.0F));

        PartDefinition arm_control = body_upper.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -5.0F));

        PartDefinition arm_left = arm_control.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(69, 60).addBox(0.0F, 0.0F, -2.0F, 7.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offset(2.0F, 0.0F, -1.0F));

        PartDefinition arm_right = arm_control.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(69, 60).mirror().addBox(-7.0F, 0.0F, -2.0F, 7.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-2.0F, 0.0F, -1.0F));

        PartDefinition tail1 = body_upper.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 20.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -2.0F, 3.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 20.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(74, 51).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 21.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 20.0F));

        PartDefinition leg_left1 = body_main.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(40, 53).addBox(-0.5F, -1.0F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 1.0F, 2.5F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(78, 31).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 7.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offset(0.5F, 4.0F, 2.0F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(108, 32).addBox(-1.5F, 0.0F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition leg_right1 = body_main.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(40, 53).mirror().addBox(-1.5F, -1.0F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 1.0F, 2.5F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(78, 31).mirror().addBox(-1.0F, 0.0F, 0.0F, 2.0F, 7.0F, 0.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-0.5F, 4.0F, 2.0F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(108, 32).mirror().addBox(-2.5F, 0.0F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 7.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Coelophysis entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isRunning()) {
            this.animateWalk(CoelophysisAnimations.RUN, limbSwing, limbSwingAmount, 1.0F, 2.5F);
        } else {
            this.animateWalk(CoelophysisAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }

		this.animateIdleSmooth(entity.idleAnimationState, CoelophysisAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
		this.animateSmooth(entity.eepyAnimationState, CoelophysisAnimations.SLEEP, ageInTicks, partialTicks);

        if (this.young) {
            this.applyStatic(CoelophysisAnimations.BABY_TRANSFORM);
        }

        this.faceTarget(entity, netHeadYaw, headPitch, 1.75F, neck, head);
        this.tail1.yRot += entity.getTailYaw(partialTicks) * Mth.DEG_TO_RAD;
        this.tail2.yRot += entity.getTailYaw(partialTicks) * 0.85F * Mth.DEG_TO_RAD;
        this.tail3.yRot += entity.getTailYaw(partialTicks) * 0.75F * Mth.DEG_TO_RAD;
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}