package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.SaltopusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Saltopus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class SaltopusModel extends UP2Model<Saltopus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart jaw;
    private final ModelPart tongue1;
    private final ModelPart tongue2;
    private final ModelPart tongue3;
    private final ModelPart tongue4;
    private final ModelPart arm_control;
    private final ModelPart arm_left;
    private final ModelPart arm_right;
    private final ModelPart tail;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

	public SaltopusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.jaw = this.head.getChild("jaw");
        this.tongue1 = this.jaw.getChild("tongue1");
        this.tongue2 = this.tongue1.getChild("tongue2");
        this.tongue3 = this.tongue2.getChild("tongue3");
        this.tongue4 = this.tongue3.getChild("tongue4");
        this.arm_control = this.body.getChild("arm_control");
        this.arm_left = this.arm_control.getChild("arm_left");
        this.arm_right = this.arm_control.getChild("arm_right");
        this.tail = this.body.getChild("tail");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_left1 = this.leg_control.getChild("leg_left1");
        this.leg_left2 = this.leg_left1.getChild("leg_left2");
        this.leg_left3 = this.leg_left2.getChild("leg_left3");
        this.leg_right1 = this.leg_control.getChild("leg_right1");
        this.leg_right2 = this.leg_right1.getChild("leg_right2");
        this.leg_right3 = this.leg_right2.getChild("leg_right3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -6.6F, 2.5F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.5F, -6.5F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -7.0F, -2.0F, 2.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -5.5F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(12, 13).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(27, 10).addBox(-1.5F, -0.5F, -7.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.75F, -1.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(9, 13).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(1.5F, 0.0F, -2.0F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(9, 13).mirror().addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-1.5F, 0.0F, -2.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(28, 15).addBox(-1.5F, 0.0F, -4.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(13, 33).addBox(-1.5F, -1.0F, -4.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.5F, -3.0F));

        PartDefinition tongue1 = jaw.addOrReplaceChild("tongue1", CubeListBuilder.create().texOffs(38, 19).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 0.0F, 6.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tongue2 = tongue1.addOrReplaceChild("tongue2", CubeListBuilder.create().texOffs(38, 10).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 0.0F, 6.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, -6.0F));

        PartDefinition tongue3 = tongue2.addOrReplaceChild("tongue3", CubeListBuilder.create().texOffs(47, 24).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, -6.0F));

        PartDefinition tongue4 = tongue3.addOrReplaceChild("tongue4", CubeListBuilder.create().texOffs(47, 19).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition arm_control = body.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -6.0F));

        PartDefinition arm_left = arm_control.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(42, 34).addBox(0.0F, 0.0F, -1.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offset(2.5F, 0.0F, 0.0F));

        PartDefinition arm_right = arm_control.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(42, 34).mirror().addBox(-4.0F, 0.0F, -1.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-2.5F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(12, 20).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(22, 26).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-1.0F, -7.0F, 5.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, 1.5F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.5F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(0, 26).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(41, 35).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 2.5F, 1.5F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(48, 41).addBox(-2.5F, 0.0F, -4.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(0, 26).mirror().addBox(-1.5F, -1.5F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(41, 35).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 2.5F, 1.5F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(48, 41).mirror().addBox(-2.5F, 0.0F, -4.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 4.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Saltopus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

		this.animateSmooth(entity.idleAnimationState, SaltopusAnimations.IDLE, ageInTicks, partialTicks);
        this.animateSmooth(entity.jumpAnimationState, SaltopusAnimations.HOP_LOOP, ageInTicks, partialTicks);
        this.animateSmooth(entity.leapAnimationState, SaltopusAnimations.LEAP_LOOP, ageInTicks, partialTicks);

        if (young) {
            this.applyStatic(SaltopusAnimations.BABY_TRANSFORM);
        }
        this.applyStatic(SaltopusAnimations.TONGUE_OVERLAY);

        this.faceTarget(entity, netHeadYaw, headPitch, 2, neck, head);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}