package com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.paleozoic.JawlessFishAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.JawlessFish;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class JawlessFishDoryaspisModel extends UP2Model<JawlessFish> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart body_sIdeways;
	private final ModelPart body;
	private final ModelPart tail1;
	private final ModelPart tail2;

	public JawlessFishDoryaspisModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.body_sIdeways = this.swim_control.getChild("body_sIdeways");
		this.body = this.body_sIdeways.getChild("body");
		this.tail1 = this.body.getChild("tail1");
		this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -1.5F, 1.25F));

		PartDefinition body_sIdeways = swim_control.addOrReplaceChild("body_sIdeways", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 0.5F));

		PartDefinition body = body_sIdeways.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -1.0F, -3.5F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(17, 0).addBox(-1.5F, 1.0F, -13.5F, 3.0F, 0.0F, 9.0F, new CubeDeformation(0.001F))
				.texOffs(17, 1).addBox(-1.5F, -1.0F, -4.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Body_r1 = body.addOrReplaceChild("Body_r1", CubeListBuilder.create().texOffs(-4, 11).mirror().addBox(-6.0F, 0.0F, -2.0F, 7.0F, 0.0F, 4.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 1.0F, 0.5F, 0.0F, 0.0F, -0.0873F));

		PartDefinition Body_r2 = body.addOrReplaceChild("Body_r2", CubeListBuilder.create().texOffs(-4, 11).addBox(-1.0F, 0.0F, -2.0F, 7.0F, 0.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.5F, 1.0F, 0.5F, 0.0F, 0.0F, 0.0873F));

		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(1, 10).addBox(0.0F, -2.0F, -2.0F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 2.5F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

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