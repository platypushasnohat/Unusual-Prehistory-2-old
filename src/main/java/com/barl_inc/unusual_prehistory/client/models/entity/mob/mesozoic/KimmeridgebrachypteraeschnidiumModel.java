package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.KimmeridgebrachypteraeschnidiumAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Kimmeridgebrachypteraeschnidium;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class KimmeridgebrachypteraeschnidiumModel extends UP2Model<Kimmeridgebrachypteraeschnidium> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart head;
    private final ModelPart wing_front_left;
    private final ModelPart wing_front_right;
    private final ModelPart wing_back_left;
    private final ModelPart wing_back_right;
    private final ModelPart arm_left;
    private final ModelPart arm_right;
    private final ModelPart leg_control;
    private final ModelPart leg_front_left;
    private final ModelPart leg_front_right;
    private final ModelPart leg_back_left;
    private final ModelPart leg_back_right;

	public KimmeridgebrachypteraeschnidiumModel(ModelPart root) {
        super(1, 0);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.tail = this.body.getChild("tail");
        this.head = this.body.getChild("head");
        this.wing_front_left = this.body.getChild("wing_front_left");
        this.wing_front_right = this.body.getChild("wing_front_right");
        this.wing_back_left = this.body.getChild("wing_back_left");
        this.wing_back_right = this.body.getChild("wing_back_right");
        this.arm_left = this.body.getChild("arm_left");
        this.arm_right = this.body.getChild("arm_right");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_front_left = this.leg_control.getChild("leg_front_left");
        this.leg_front_right = this.leg_control.getChild("leg_front_right");
        this.leg_back_left = this.leg_control.getChild("leg_back_left");
        this.leg_back_right = this.leg_control.getChild("leg_back_right");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(14, 16).addBox(-2.0F, -2.0F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 1.5F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(12, 22).addBox(-3.0F, -1.0F, -2.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.5F));

        PartDefinition wing_front_left = body.addOrReplaceChild("wing_front_left", CubeListBuilder.create().texOffs(-1, 8).mirror().addBox(0.0F, 0.0F, -7.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(1.0F, -2.0F, -0.5F, 0.0F, 0.0F, -0.1745F));

        PartDefinition wing_front_right = body.addOrReplaceChild("wing_front_right", CubeListBuilder.create().texOffs(-1, 8).addBox(-10.0F, 0.0F, -7.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-1.0F, -2.0F, -0.5F, 0.0F, 0.0F, 0.1745F));

        PartDefinition wing_back_left = body.addOrReplaceChild("wing_back_left", CubeListBuilder.create().texOffs(-1, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0F, 1.5F, 0.0F, 0.0F, -0.1745F));

        PartDefinition wing_back_right = body.addOrReplaceChild("wing_back_right", CubeListBuilder.create().texOffs(-1, 0).addBox(-10.0F, 0.0F, -1.0F, 10.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, -2.0F, 1.5F, 0.0F, 0.0F, 0.1745F));

        PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(0, 7).mirror().addBox(0.0F, 0.0F, -3.0F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(1.0F, 1.0F, -0.5F, -0.48F, 0.0F, 0.0F));

        PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, 0.0F, -3.0F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-1.0F, 1.0F, -0.5F, -0.48F, 0.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(1.0F, 1.0F, -0.5F));

        PartDefinition leg_front_left = leg_control.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(2, -2).mirror().addBox(0.0F, 0.0F, -2.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7418F));

        PartDefinition leg_front_right = leg_control.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(2, -2).addBox(0.0F, 0.0F, -2.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7418F));

        PartDefinition leg_back_left = leg_control.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7418F));

        PartDefinition leg_back_right = leg_control.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(0, 3).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7418F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Kimmeridgebrachypteraeschnidium entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        this.animateSmooth(entity.flyAnimationState, KimmeridgebrachypteraeschnidiumAnimations.FLY, ageInTicks, partialTicks);
        this.animateSmooth(entity.flyAnimationState, KimmeridgebrachypteraeschnidiumAnimations.FLY_OVERLAY, ageInTicks, partialTicks);
        this.animateSmooth(entity.hoverAnimationState, KimmeridgebrachypteraeschnidiumAnimations.HOVER, ageInTicks,  partialTicks,1.5F);
		this.animateSmooth(entity.hoverAnimationState, KimmeridgebrachypteraeschnidiumAnimations.HOVER_OVERLAY, ageInTicks, partialTicks, 1.5F);

		this.animateSmooth(entity.idleAnimationState, KimmeridgebrachypteraeschnidiumAnimations.IDLE1, ageInTicks, partialTicks);
        this.animateSmooth(entity.attachedAnimationState, KimmeridgebrachypteraeschnidiumAnimations.IDLE2, ageInTicks, partialTicks);
        this.animateSmooth(entity.preenAnimationState, KimmeridgebrachypteraeschnidiumAnimations.PREEN, ageInTicks, partialTicks);

        float rollAmount = entity.getFlightRoll(partialTicks) / (180F / (float) Math.PI);
        float flightPitchAmount = entity.getFlightPitch(partialTicks) / (180F / (float) Math.PI);

        if (entity.isFlying()) {
            this.body_main.xRot += flightPitchAmount;
            this.body_main.zRot += rollAmount;
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}