package com.barl_inc.unusual_prehistory.client.models.entity.mob.other;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.other.UnicornAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.other.Unicorn;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class UnicornModel extends UP2Model<Unicorn> {

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart upperBody;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart horn;
	private final ModelPart tail;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;

	public UnicornModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.upperBody = this.body.getChild("upperBody");
		this.neck = this.upperBody.getChild("neck");
		this.head = this.neck.getChild("head");
		this.horn = this.head.getChild("horn");
		this.tail = this.upperBody.getChild("tail");
		this.rightLeg = this.body.getChild("rightLeg");
		this.leftLeg = this.body.getChild("leftLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition upperBody = body.addOrReplaceChild("upperBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.0F));

		PartDefinition upperBody_r1 = upperBody.addOrReplaceChild("upperBody_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -14.0F, -34.0F, 12.0F, 14.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition neck = upperBody.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -35.0F, -9.0F));

		PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(70, 74).addBox(-4.0F, -14.0F, -41.0F, 8.0F, 9.0F, 9.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, 35.0F, 9.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -3.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(20, 71).addBox(-4.0F, -14.0F, -51.0F, 8.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(72, 48).addBox(-4.0F, -14.0F, -48.0F, 8.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 39.0F, 12.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset(0.0F, 30.0F, 9.0F));

		PartDefinition head_r2 = horn.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, -14.0F, -69.0F, 4.0F, 5.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 3.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition tail = upperBody.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 8.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(44, 48).addBox(-3.0F, -29.0F, -8.1F, 6.0F, 19.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, -8.0F, -1.0472F, 0.0F, 0.0F));

		PartDefinition rightLeg = body.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 71).addBox(-2.65F, -4.0F, -2.5F, 5.0F, 30.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -26.0F, -9.5F));

		PartDefinition leftLeg = body.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 71).mirror().addBox(-2.35F, -4.0F, -2.5F, 5.0F, 30.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, -26.0F, -9.5F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NotNull Unicorn entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		float partialTicks = ageInTicks - entity.tickCount;

        this.animateWalk(UnicornAnimations.WALK, limbSwing, limbSwingAmount, 4, 8);

        if (this.young) this.applyStatic(UnicornAnimations.BABY_TRANSFORM);

		this.animateIdleSmooth(entity.idleAnimationState, UnicornAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);

        float deg = ((float) Math.PI / 180F);
		this.head.xRot += headPitch * (deg) - (headPitch * (deg)) / 4;
		this.head.yRot += netHeadYaw * (deg) - (netHeadYaw * (deg)) / 4;
		this.neck.xRot += headPitch * (deg) / 4;
		this.neck.yRot += netHeadYaw * (deg) / 4;
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}