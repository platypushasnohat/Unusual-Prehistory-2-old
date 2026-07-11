package com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.cenozoic.DireWolfAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.DireWolf;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DireWolfModel extends UP2Model<DireWolf> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body_upper;
    private final ModelPart body;
    private final ModelPart body_jiggle;
    private final ModelPart mane;
    private final ModelPart neck;
    private final ModelPart neck_jiggle;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart ear_left;
    private final ModelPart ear_right;
    private final ModelPart snout;
    private final ModelPart snarl;
    private final ModelPart teeth;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart arm_control;
    private final ModelPart arm_left1;
    private final ModelPart arm_left2;
    private final ModelPart arm_right1;
    private final ModelPart arm_right2;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

	public DireWolfModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body_upper = this.body_main.getChild("body_upper");
        this.body = this.body_upper.getChild("body");
        this.body_jiggle = this.body.getChild("body_jiggle");
        this.mane = this.body.getChild("mane");
        this.neck = this.body.getChild("neck");
        this.neck_jiggle = this.neck.getChild("neck_jiggle");
        this.head = this.neck.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.ear_left = this.head.getChild("ear_left");
        this.ear_right = this.head.getChild("ear_right");
        this.snout = this.head.getChild("snout");
        this.snarl = this.snout.getChild("snarl");
        this.teeth = this.snout.getChild("teeth");
        this.jaw = this.head.getChild("jaw");
        this.tail = this.body.getChild("tail");
        this.arm_control = this.body_upper.getChild("arm_control");
        this.arm_left1 = this.arm_control.getChild("arm_left1");
        this.arm_left2 = this.arm_left1.getChild("arm_left2");
        this.arm_right1 = this.arm_control.getChild("arm_right1");
        this.arm_right2 = this.arm_right1.getChild("arm_right2");
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

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -24.0F, 0.0F));

        PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 7.5F));

        PartDefinition body = body_upper.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -7.5F));

        PartDefinition body_jiggle = body.addOrReplaceChild("body_jiggle", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.5F, -12.0F, 10.0F, 13.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, 0.0F));

        PartDefinition mane = body.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(91, 54).addBox(0.0F, -7.0F, -2.0F, 0.0F, 3.0F, 18.0F, new CubeDeformation(0.02F))
                .texOffs(97, 105).addBox(-6.5F, -4.0F, 12.0F, 13.0F, 14.0F, 0.0F, new CubeDeformation(0.02F))
                .texOffs(0, 37).addBox(-6.5F, -4.0F, -2.0F, 13.0F, 14.0F, 18.0F, new CubeDeformation(0.021F)), PartPose.offset(0.0F, -5.0F, -12.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -12.0F));

        PartDefinition neck_jiggle = neck.addOrReplaceChild("neck_jiggle", CubeListBuilder.create().texOffs(92, 27).addBox(-2.5F, -4.0F, -6.0F, 5.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(68, 18).addBox(-4.5F, -3.0F, -7.0F, 9.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -8.0F));

        PartDefinition sideburn_r1 = head.addOrReplaceChild("sideburn_r1", CubeListBuilder.create().texOffs(4, 103).mirror().addBox(-3.0F, -3.0F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 3.0F, -3.0F, 0.0F, 0.1745F, 0.0F));

        PartDefinition sideburn_r2 = head.addOrReplaceChild("sideburn_r2", CubeListBuilder.create().texOffs(4, 103).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(3.5F, 3.0F, -3.0F, 0.0F, -0.1745F, 0.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(94, 22).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.offset(3.0F, -0.5F, -6.5F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(94, 22).mirror().addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-3.0F, -0.5F, -6.5F));

        PartDefinition ear_left = head.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(52, 69).addBox(0.0F, -5.0F, 0.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(1.5F, -3.0F, -2.0F));

        PartDefinition ear_right = head.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(52, 69).mirror().addBox(-3.0F, -5.0F, 0.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-1.5F, -3.0F, -2.0F));

        PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, -7.0F));

        PartDefinition snarl = snout.addOrReplaceChild("snarl", CubeListBuilder.create().texOffs(26, 83).addBox(-1.5F, 0.0F, -6.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition teeth = snout.addOrReplaceChild("teeth", CubeListBuilder.create().texOffs(60, 44).addBox(-2.0F, -1.0F, -3.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -1.0F, -3.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(84, 80).addBox(-1.5F, 0.0F, -6.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -7.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(62, 37).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.021F))
                .texOffs(24, 106).addBox(-3.0F, -3.0F, 13.0F, 6.0F, 6.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, -2.0F, 12.0F));

        PartDefinition arm_control = body_upper.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -16.5F));

        PartDefinition arm_left1 = arm_control.addOrReplaceChild("arm_left1", CubeListBuilder.create().texOffs(52, 80).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, 0.0F));

        PartDefinition arm_left2 = arm_left1.addOrReplaceChild("arm_left2", CubeListBuilder.create().texOffs(70, 73).addBox(2.0F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F))
                .texOffs(70, 73).addBox(0.75F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F))
                .texOffs(70, 73).addBox(-0.75F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F))
                .texOffs(70, 73).addBox(-2.0F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition arm_right1 = arm_control.addOrReplaceChild("arm_right1", CubeListBuilder.create().texOffs(52, 80).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 18.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 0.0F, 0.0F));

        PartDefinition arm_right2 = arm_right1.addOrReplaceChild("arm_right2", CubeListBuilder.create().texOffs(70, 73).mirror().addBox(-2.0F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false)
                .texOffs(70, 73).mirror().addBox(-0.75F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false)
                .texOffs(70, 73).mirror().addBox(0.75F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false)
                .texOffs(70, 73).mirror().addBox(2.0F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 7.5F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(0, 69).addBox(-2.5F, -3.0F, -3.5F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(68, 80).addBox(-2.0F, -1.5F, -1.5F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.5F, 2.0F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(70, 73).addBox(0.75F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F))
                .texOffs(70, 73).addBox(-0.75F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F))
                .texOffs(70, 73).addBox(-2.0F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F))
                .texOffs(70, 73).addBox(2.0F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 11.5F, 0.5F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(0, 69).mirror().addBox(-2.5F, -3.0F, -3.5F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.5F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(68, 80).mirror().addBox(-2.0F, -1.5F, -1.5F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 6.5F, 2.0F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(70, 73).mirror().addBox(-0.75F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false)
                .texOffs(70, 73).mirror().addBox(0.75F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false)
                .texOffs(70, 73).mirror().addBox(2.0F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false)
                .texOffs(70, 73).mirror().addBox(-2.0F, -1.0F, -4.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 11.5F, 0.5F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NotNull DireWolf entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isRunning()) {
            this.animateWalk(DireWolfAnimations.RUN, limbSwing, limbSwingAmount, 1.0F, 2.5F);
        } else {
            this.animateWalk(DireWolfAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }

		this.animateIdleSmooth(entity.idleAnimationState, DireWolfAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);

        if ((entity.getId() & 1) == 0) {
            this.animateSmooth(entity.eepyAnimationState, DireWolfAnimations.SLEEP1, ageInTicks, partialTicks);
        } else {
            this.animateSmooth(entity.eepyAnimationState, DireWolfAnimations.SLEEP2, ageInTicks, partialTicks);
        }

        if (this.young) {
            this.applyStatic(DireWolfAnimations.BABY_TRANSFORM);
        }

        this.faceTarget(entity, netHeadYaw, headPitch, 1.75F, neck, head);
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail.yRot = Mth.lerp(0.25F, tail.yRot, tailYaw * 0.2F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}