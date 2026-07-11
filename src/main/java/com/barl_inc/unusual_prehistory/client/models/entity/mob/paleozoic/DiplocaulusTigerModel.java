package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DiplocaulusTigerModel extends DiplocaulusModel {

	public DiplocaulusTigerModel(ModelPart root) {
        super(root);
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -2.05F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(21, 26).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.05F))
                .texOffs(28, 16).addBox(0.0F, -4.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(31, 3).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -3.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));

        PartDefinition righthead_r1 = head.addOrReplaceChild("righthead_r1", CubeListBuilder.create().texOffs(1, 13).addBox(-1.0F, -2.0F, -1.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5355F, 0.0F, -1.0858F, 0.0F, -0.7854F, 0.0F));

        PartDefinition lefthead_r1 = head.addOrReplaceChild("lefthead_r1", CubeListBuilder.create().texOffs(1, 7).addBox(-1.0F, -2.0F, -1.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5355F, 0.0F, -1.0858F, 0.0F, -0.7854F, 0.0F));

        PartDefinition face_r1 = head.addOrReplaceChild("face_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition jaw_r1 = jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(0, 20).addBox(-2.5F, -2.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 26).addBox(-2.5F, -1.01F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 1.0F, -2.5F, 0.0F, 0.7854F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 24).addBox(0.0F, -1.0F, -1.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -2.0F, 3.0F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.4F, -2.0F));

        PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(0, 39).addBox(0.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));

        PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(28, 7).addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(3.0F, 1.59F, 0.0F));

        PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(0, 39).mirror().addBox(-3.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 0.0F, 0.0F));

        PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(28, 7).mirror().addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-3.0F, 1.59F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.4F, 3.0F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(0, 39).addBox(0.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(28, 7).addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(3.0F, 1.59F, 0.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(0, 39).mirror().addBox(-3.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 0.0F, -1.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(28, 7).mirror().addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-3.0F, 1.59F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}
}