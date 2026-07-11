package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.JawlessFishAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.JawlessFish;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class JawlessFishArandaspisModel extends UP2Model<JawlessFish> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body_sideways;
	private final ModelPart body;
	private final ModelPart tail1;

	public JawlessFishArandaspisModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body_sideways = this.swim_control.getChild("body_sideways");
		this.body = this.body_sideways.getChild("body");
		this.tail1 = this.body.getChild("tail1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -0.5F));

		PartDefinition body_sideways = swim_control.addOrReplaceChild("body_sideways", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = body_sideways.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 1).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 5).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(JawlessFish entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(JawlessFishAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        this.animateIdle(entity.swimIdleAnimationState, JawlessFishAnimations.SWIM, ageInTicks, 0.8F, limbSwingAmount * 3);
		this.animate(entity.flopAnimationState, JawlessFishAnimations.FLOP, ageInTicks);
		this.swim_control.xRot = headPitch * (Mth.DEG_TO_RAD);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}