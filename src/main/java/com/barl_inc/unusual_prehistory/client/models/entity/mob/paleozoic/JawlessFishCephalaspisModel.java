package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class JawlessFishCephalaspisModel extends JawlessFishModel {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body_sideways;
	private final ModelPart body;
	private final ModelPart pectoralfin_left;
	private final ModelPart pectoralfin_right;
	private final ModelPart tail1;
	private final ModelPart tail2;

	public JawlessFishCephalaspisModel(ModelPart root) {
        super();
		this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body_sideways = this.swim_control.getChild("body_sideways");
		this.body = this.body_sideways.getChild("body");
		this.pectoralfin_left = this.body.getChild("pectoralfin_left");
		this.pectoralfin_right = this.body.getChild("pectoralfin_right");
		this.tail1 = this.body.getChild("tail1");
		this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, -2.0F));

		PartDefinition body_sideways = swim_control.addOrReplaceChild("body_sideways", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, -2.0F));

		PartDefinition body = body_sideways.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 17).addBox(-3.0F, 2.0F, -3.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, -0.05F, -1.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(10, 12).addBox(0.0F, -2.0F, 4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition pectoralfin_left = body.addOrReplaceChild("pectoralfin_left", CubeListBuilder.create().texOffs(0, 0).addBox(0.05F, 0.0F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 2.0F, 1.0F, 0.0F, 0.48F, 0.0F));

		PartDefinition pectoralfin_right = body.addOrReplaceChild("pectoralfin_right", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.05F, 0.0F, 0.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 2.0F, 1.0F, 0.0F, -0.48F, 0.0F));

		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -1.5F, 0.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 5.0F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 9).addBox(0.0F, -2.5F, 0.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public ModelPart swimControl() {
		return this.swim_control;
	}
}