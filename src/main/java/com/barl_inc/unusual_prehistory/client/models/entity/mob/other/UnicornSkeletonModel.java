package com.barl_inc.unusual_prehistory.client.models.entity.mob.other;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class UnicornSkeletonModel extends UnicornModel {

	public UnicornSkeletonModel(ModelPart root) {
        super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 12.0F));

		PartDefinition upperBody = body.addOrReplaceChild("upperBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -9.0F));

		PartDefinition upperBody_r1 = upperBody.addOrReplaceChild("upperBody_r1", CubeListBuilder.create().texOffs(0, 44).addBox(0.0F, -8.0F, -34.0F, 0.0F, 14.0F, 34.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, -14.0F, -34.0F, 4.0F, 6.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition neck = upperBody.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -37.0F, -10.0F));

		PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(84, 24).addBox(-2.0F, -14.0F, -41.0F, 4.0F, 6.0F, 9.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 37.0F, 10.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -1.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(18, 92).addBox(-3.0F, -14.0F, -51.0F, 6.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(47, 102).addBox(-3.0F, -9.0F, -44.1F, 6.0F, 10.0F, 1.0F, new CubeDeformation(-0.1F))
				.texOffs(88, 67).addBox(-3.0F, -14.0F, -48.0F, 6.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 39.0F, 11.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 25.0F, -33.0F));

		PartDefinition jaw_r1 = jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(84, 39).addBox(-3.0F, 1.0F, -41.0F, 6.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(88, 89).addBox(-3.0F, -14.0F, -44.0F, 6.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, 44.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset(0.0F, 30.0F, -30.0F));

		PartDefinition head_r2 = horn.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(68, 44).addBox(-2.0F, -14.0F, -69.0F, 4.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 41.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition tail = upperBody.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 12.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 92).addBox(-2.0F, -24.0F, -1.1F, 4.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -12.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(84, 0).mirror().addBox(-2.4F, -11.0F, -2.5F, 5.0F, 15.0F, 9.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(68, 67).mirror().addBox(-2.4F, -4.0F, -2.5F, 5.0F, 30.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.5F, -26.0F, -21.5F));

		PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(68, 67).addBox(-2.6F, -4.0F, -2.5F, 5.0F, 30.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(84, 0).addBox(-2.6F, -11.0F, -2.5F, 5.0F, 15.0F, 9.0F, new CubeDeformation(0.1F)), PartPose.offset(3.5F, -26.0F, -21.5F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}