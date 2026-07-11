package com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.cenozoic.WoollyMammothAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.WoollyMammoth;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class WoollyMammothModel extends UP2Model<WoollyMammoth> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart wooly_fur;
    private final ModelPart head;
    private final ModelPart wooly_head;
    private final ModelPart left_ear;
    private final ModelPart right_ear;
    private final ModelPart trunk1;
    private final ModelPart trunk2;
    private final ModelPart trunk3;
    private final ModelPart jaw;
    private final ModelPart left_tusk;
    private final ModelPart left_tusk_wooly;
    private final ModelPart right_tusk;
    private final ModelPart right_tusk_wooly;
    private final ModelPart rightbrow;
    private final ModelPart leftbrow;
    private final ModelPart tail;
    private final ModelPart arm_control;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart leg_control;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

	public WoollyMammothModel(ModelPart root) {
        super(0.25F, 72);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.wooly_fur = this.body.getChild("wooly_fur");
        this.head = this.body.getChild("head");
        this.wooly_head = this.head.getChild("wooly_head");
        this.left_ear = this.head.getChild("left_ear");
        this.right_ear = this.head.getChild("right_ear");
        this.trunk1 = this.head.getChild("trunk1");
        this.trunk2 = this.trunk1.getChild("trunk2");
        this.trunk3 = this.trunk2.getChild("trunk3");
        this.jaw = this.head.getChild("jaw");
        this.left_tusk = this.head.getChild("left_tusk");
        this.left_tusk_wooly = this.left_tusk.getChild("left_tusk_wooly");
        this.right_tusk = this.head.getChild("right_tusk");
        this.right_tusk_wooly = this.right_tusk.getChild("right_tusk_wooly");
        this.rightbrow = this.head.getChild("rightbrow");
        this.leftbrow = this.head.getChild("leftbrow");
        this.tail = this.body.getChild("tail");
        this.arm_control = this.body_main.getChild("arm_control");
        this.left_arm = this.arm_control.getChild("left_arm");
        this.right_arm = this.arm_control.getChild("right_arm");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg = this.leg_control.getChild("left_leg");
        this.right_leg = this.leg_control.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -37.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 134).addBox(-19.0F, -43.0F, -32.0F, 38.0F, 47.0F, 63.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wooly_fur = body.addOrReplaceChild("wooly_fur", CubeListBuilder.create().texOffs(0, 0).addBox(-26.5F, 1.0F, -32.0F, 53.0F, 71.0F, 63.0F, new CubeDeformation(1.0F))
                .texOffs(202, 134).addBox(-26.5F, 64.0F, -33.0F, 53.0F, 0.0F, 65.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -44.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(202, 241).addBox(-10.5F, -18.0F, -19.0F, 21.0F, 29.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(288, 291).addBox(-10.5F, 11.0F, -19.0F, 21.0F, 5.0F, 19.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -29.0F, -32.0F));

        PartDefinition wooly_head = head.addOrReplaceChild("wooly_head", CubeListBuilder.create().texOffs(232, 0).addBox(-13.5F, 1.5F, -15.5F, 27.0F, 3.0F, 31.0F, new CubeDeformation(0.0F))
                .texOffs(0, 334).mirror().addBox(-13.5F, -7.5F, -15.5F, 27.0F, 9.0F, 31.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -14.5F, -7.5F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(160, 268).addBox(-1.0F, -6.0F, 0.0F, 11.0F, 16.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(11.5F, -7.0F, -5.0F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(160, 268).mirror().addBox(-10.0F, -6.0F, 0.0F, 11.0F, 16.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-11.5F, -7.0F, -5.0F));

        PartDefinition trunk1 = head.addOrReplaceChild("trunk1", CubeListBuilder.create().texOffs(310, 199).addBox(-5.5F, -5.5F, -11.0F, 11.0F, 30.0F, 11.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 1.5F, -19.0F));

        PartDefinition trunk2 = trunk1.addOrReplaceChild("trunk2", CubeListBuilder.create().texOffs(308, 315).addBox(-5.5F, 0.0F, -5.5F, 11.0F, 22.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 24.5F, -5.5F));

        PartDefinition trunk3 = trunk2.addOrReplaceChild("trunk3", CubeListBuilder.create().texOffs(194, 318).addBox(-5.5F, -3.5F, -0.5F, 11.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.5F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(324, 56).addBox(-4.5F, 0.0F, -11.0F, 9.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, -8.0F));

        PartDefinition left_tusk = head.addOrReplaceChild("left_tusk", CubeListBuilder.create().texOffs(260, 292).addBox(-2.8627F, -4.0755F, -2.5F, 6.0F, 49.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5F, 6.0F, -19.0F, 0.0503F, -0.5214F, -0.1007F));

        PartDefinition left_tusk_wooly = left_tusk.addOrReplaceChild("left_tusk_wooly", CubeListBuilder.create().texOffs(160, 244).addBox(2.0F, -2.0F, -5.0F, 6.0F, 19.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(232, 34).addBox(2.0F, 17.0F, -5.0F, 6.0F, 6.0F, 40.0F, new CubeDeformation(0.0F))
                .texOffs(0, 334).addBox(-4.0F, -2.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.8627F, 27.9245F, -31.5F));

        PartDefinition right_tusk = head.addOrReplaceChild("right_tusk", CubeListBuilder.create().texOffs(260, 292).mirror().addBox(-3.1373F, -4.0755F, -2.5F, 6.0F, 49.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-10.5F, 6.0F, -19.0F, 0.0503F, 0.5214F, 0.1007F));

        PartDefinition right_tusk_wooly = right_tusk.addOrReplaceChild("right_tusk_wooly", CubeListBuilder.create().texOffs(160, 244).mirror().addBox(-8.0F, -2.0F, -5.0F, 6.0F, 19.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(232, 34).mirror().addBox(-8.0F, 17.0F, -5.0F, 6.0F, 6.0F, 40.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 334).mirror().addBox(-2.0F, -2.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.8627F, 27.9245F, -31.5F));

        PartDefinition rightbrow = head.addOrReplaceChild("rightbrow", CubeListBuilder.create().texOffs(48, 58).addBox(-2.0F, -3.0F, 0.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.5F, -7.0F, -19.25F));

        PartDefinition leftbrow = head.addOrReplaceChild("leftbrow", CubeListBuilder.create().texOffs(48, 58).mirror().addBox(-4.0F, -3.0F, 0.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.5F, -7.0F, -19.25F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(194, 244).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 20.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(324, 111).addBox(-2.5F, 19.0F, -2.0F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -36.0F, 30.5F, 0.1745F, 0.0F, 0.0F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(11.0F, 0.0F, -17.5F));

        PartDefinition left_arm = arm_control.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 284).addBox(-6.5F, 4.0F, -10.5F, 14.0F, 33.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm = arm_control.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 284).mirror().addBox(-7.5F, 4.0F, -10.5F, 14.0F, 33.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-22.0F, 0.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(11.5F, 0.0F, 18.5F));

        PartDefinition left_leg = leg_control.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(124, 284).addBox(-7.0F, 4.0F, -8.5F, 14.0F, 33.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg = leg_control.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(124, 284).mirror().addBox(-7.0F, 4.0F, -8.5F, 14.0F, 33.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-23.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(@NotNull WoollyMammoth entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        this.animateWalk(WoollyMammothAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 3.0F);
		this.animateIdleSmooth(entity.idleAnimationState, WoollyMammothAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        if (this.young) this.applyStatic(WoollyMammothAnimations.BABY_TRANSFORM);
        this.faceTarget(entity, netHeadYaw, headPitch, 3, head);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}