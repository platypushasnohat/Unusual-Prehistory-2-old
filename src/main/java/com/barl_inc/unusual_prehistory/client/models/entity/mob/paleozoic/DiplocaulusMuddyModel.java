package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.DiplocaulusAnimations;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DiplocaulusMuddyModel extends DiplocaulusModel {

	public DiplocaulusMuddyModel(ModelPart root) {
        super(root);
	}

    @Override
    protected AnimationDefinition getQuirkAnimation() {
        return DiplocaulusAnimations.QUIRK_BLEND_MUDDY;
    }

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -2.05F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 7).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.05F))
                .texOffs(14, 28).addBox(0.0F, -4.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 0).addBox(-5.0F, -2.05F, -5.0F, 10.0F, 0.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 37).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -3.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -1.0F));

        PartDefinition righthead_r1 = head.addOrReplaceChild("righthead_r1", CubeListBuilder.create().texOffs(0, 25).mirror().addBox(-5.0F, -2.0F, 0.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.825F, 0.0F, -2.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition lefthead_r1 = head.addOrReplaceChild("lefthead_r1", CubeListBuilder.create().texOffs(26, 28).addBox(0.0F, -2.0F, 0.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.825F, 0.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition face_r1 = head.addOrReplaceChild("face_r1", CubeListBuilder.create().texOffs(20, 18).addBox(-2.0F, -0.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -2.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition jaw_r1 = jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(14, 23).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 29).addBox(-2.0F, -1.01F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 1.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -3.0F, -1.0F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0025F))
                .texOffs(31, 22).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.4F, -2.0F));

        PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(12, 35).addBox(0.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));

        PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(-1, 33).addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(3.0F, 1.59F, 0.0F));

        PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(12, 35).mirror().addBox(-3.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 0.0F, 0.0F));

        PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(-1, 33).mirror().addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-3.0F, 1.59F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.4F, 3.0F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(12, 35).addBox(0.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(-1, 33).addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(3.0F, 1.59F, 0.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(12, 35).mirror().addBox(-3.0F, -0.42F, -1.01F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 0.0F, -1.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(-1, 33).mirror().addBox(-1.0F, 0.0F, -2.01F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-3.0F, 1.59F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}
}