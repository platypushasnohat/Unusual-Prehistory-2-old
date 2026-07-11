package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.TherizinosaurusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Therizinosaurus;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class TherizinosaurusModel extends UP2Model<Therizinosaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart body_adjust;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart jaw;
    private final ModelPart hair;
    private final ModelPart arm_left;
    private final ModelPart claw_left1;
    private final ModelPart claw_left2;
    private final ModelPart claw_left3;
    private final ModelPart arm_right;
    private final ModelPart claw_right1;
    private final ModelPart claw_right2;
    private final ModelPart claw_right3;
    private final ModelPart tail;
    private final ModelPart tail_adjust;
    private final ModelPart tail_poof;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

	public TherizinosaurusModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.body_adjust = this.body.getChild("body_adjust");
        this.neck = this.body_adjust.getChild("neck");
        this.head = this.neck.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.jaw = this.head.getChild("jaw");
        this.hair = this.head.getChild("hair");
        this.arm_left = this.body_adjust.getChild("arm_left");
        this.claw_left1 = this.arm_left.getChild("claw_left1");
        this.claw_left2 = this.arm_left.getChild("claw_left2");
        this.claw_left3 = this.arm_left.getChild("claw_left3");
        this.arm_right = this.body_adjust.getChild("arm_right");
        this.claw_right1 = this.arm_right.getChild("claw_right1");
        this.claw_right2 = this.arm_right.getChild("claw_right2");
        this.claw_right3 = this.arm_right.getChild("claw_right3");
        this.tail = this.body_adjust.getChild("tail");
        this.tail_adjust = this.tail.getChild("tail_adjust");
        this.tail_poof = this.tail_adjust.getChild("tail_poof");
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

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -37.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body_adjust = body.addOrReplaceChild("body_adjust", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition feathers_r1 = body_adjust.addOrReplaceChild("feathers_r1", CubeListBuilder.create().texOffs(176, 94).addBox(-14.5F, 16.0F, -36.0F, 29.0F, 4.0F, 44.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 1.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition Torso_r1 = body_adjust.addOrReplaceChild("Torso_r1", CubeListBuilder.create().texOffs(9, 95).addBox(-14.5F, -16.0F, -36.0F, 29.0F, 31.0F, 45.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition neck = body_adjust.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(5, 179).addBox(-8.5F, -16.0F, -11.0F, 17.0F, 28.0F, 22.0F, new CubeDeformation(0.01F))
                .texOffs(141, 206).addBox(-4.5F, -45.0F, -1.0F, 9.0F, 29.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -41.0F, 1.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(187, 55).addBox(-3.0F, -5.0F, -17.0F, 6.0F, 7.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(183, 79).addBox(-3.0F, 2.0F, -17.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -40.0F, -1.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(217, 68).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(2.5F, -2.5F, -4.0F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(217, 68).mirror().addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-2.5F, -2.5F, -4.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(243, 56).addBox(-2.0F, 0.0F, -13.0F, 5.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 2.0F, -2.0F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(241, 204).addBox(-5.0F, 0.0F, -5.5F, 10.0F, 14.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -5.0F, -4.5F));

        PartDefinition arm_left = body_adjust.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(240, 229).addBox(0.0F, -4.0F, -4.0F, 6.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(62, 229).addBox(0.0F, -4.0F, 8.0F, 6.0F, 26.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(90, 175).addBox(5.99F, -9.0F, -4.0F, 0.0F, 27.0F, 24.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(14.5F, -35.0F, -9.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition claw_left1 = arm_left.addOrReplaceChild("claw_left1", CubeListBuilder.create().texOffs(188, 244).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 35.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(138, 199).addBox(-5.0F, 33.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 22.0F, 9.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition claw_left2 = arm_left.addOrReplaceChild("claw_left2", CubeListBuilder.create().texOffs(188, 244).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 37.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(138, 199).addBox(-5.0F, 35.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 22.0F, 12.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition claw_left3 = arm_left.addOrReplaceChild("claw_left3", CubeListBuilder.create().texOffs(188, 244).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 35.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(138, 199).addBox(-5.0F, 33.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 22.0F, 15.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition arm_right = body_adjust.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(240, 229).mirror().addBox(-6.0F, -4.0F, -4.0F, 6.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(62, 229).mirror().addBox(-6.0F, -4.0F, 8.0F, 6.0F, 26.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(90, 175).mirror().addBox(-5.99F, -9.0F, -4.0F, 0.0F, 27.0F, 24.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-14.5F, -35.0F, -9.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition claw_right1 = arm_right.addOrReplaceChild("claw_right1", CubeListBuilder.create().texOffs(188, 244).mirror().addBox(-2.0F, 0.0F, -1.0F, 4.0F, 35.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(138, 199).mirror().addBox(2.0F, 33.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 22.0F, 9.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition claw_right2 = arm_right.addOrReplaceChild("claw_right2", CubeListBuilder.create().texOffs(188, 244).mirror().addBox(-2.0F, 0.0F, -1.0F, 4.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(138, 199).mirror().addBox(2.0F, 35.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 22.0F, 12.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition claw_right3 = arm_right.addOrReplaceChild("claw_right3", CubeListBuilder.create().texOffs(188, 244).mirror().addBox(-2.0F, 0.0F, -1.0F, 4.0F, 35.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(138, 199).mirror().addBox(2.0F, 33.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 22.0F, 15.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition tail = body_adjust.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 16.0F));

        PartDefinition tail_adjust = tail.addOrReplaceChild("tail_adjust", CubeListBuilder.create().texOffs(164, 142).addBox(-4.5F, -5.4F, -4.0F, 9.0F, 11.0F, 51.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition tail_poof = tail_adjust.addOrReplaceChild("tail_poof", CubeListBuilder.create().texOffs(0, 0).addBox(-22.5F, 0.0F, -23.0F, 45.0F, 40.0F, 46.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.5F, 27.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(183, 0).addBox(-7.0F, -9.0F, -12.0F, 14.0F, 24.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(188, 204).addBox(6.99F, 5.0F, -12.0F, 0.0F, 14.0F, 26.0F, new CubeDeformation(0.02F)), PartPose.offset(12.5F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(90, 226).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 27.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 12.0F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(0, 229).addBox(-7.0F, 0.0F, -9.0F, 14.0F, 4.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(138, 188).addBox(3.0F, 0.0F, -15.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(132, 179).addBox(3.0F, 3.0F, -15.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(132, 179).addBox(-7.0F, 3.0F, -15.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(138, 188).addBox(-7.0F, 0.0F, -15.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(138, 175).addBox(-2.0F, 0.0F, -17.0F, 4.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(131, 175).addBox(-2.0F, 3.0F, -17.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(183, 0).mirror().addBox(-7.0F, -9.0F, -12.0F, 14.0F, 24.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(188, 204).mirror().addBox(-6.99F, 5.0F, -12.0F, 0.0F, 14.0F, 26.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-12.5F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(90, 226).mirror().addBox(-5.0F, -5.0F, -5.0F, 10.0F, 27.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 13.0F, 12.0F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(0, 229).mirror().addBox(-7.0F, 0.0F, -9.0F, 14.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(138, 188).mirror().addBox(-7.0F, 0.0F, -15.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(132, 179).mirror().addBox(-7.0F, 3.0F, -15.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(132, 179).mirror().addBox(3.0F, 3.0F, -15.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(138, 188).mirror().addBox(3.0F, 0.0F, -15.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(138, 175).mirror().addBox(-2.0F, 0.0F, -17.0F, 4.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(131, 175).mirror().addBox(-2.0F, 3.0F, -17.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 20.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(@NotNull Therizinosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.getPose() != UP2Poses.ATTACKING.get() && entity.getPose() != UP2Poses.WARNING.get() && !entity.isEepy() && !entity.isInWaterOrBubble()) {
            if (entity.isRunning()) {
                this.animateWalk(TherizinosaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.5F, 3);
            } else {
                this.animateWalk(TherizinosaurusAnimations.WALK_UNSHAVED, limbSwing, limbSwingAmount, 1.5F, 3);
            }
        }

		this.animateIdleSmooth(entity.idleAnimationState, TherizinosaurusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 3);
        this.animateSmooth(entity.attack1AnimationState, TherizinosaurusAnimations.SLASH1, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack2AnimationState, TherizinosaurusAnimations.SLASH2, ageInTicks, partialTicks);
        this.animateSmooth(entity.roarAnimationState, TherizinosaurusAnimations.AGGRO_START_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, TherizinosaurusAnimations.SLEEP, ageInTicks, partialTicks);
        this.animateSmooth(entity.swimAnimationState, TherizinosaurusAnimations.SWIM, ageInTicks, partialTicks);
        this.animateSmooth(entity.shakeAnimationState, TherizinosaurusAnimations.IDLE_SHAKE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.stretchAnimationState, TherizinosaurusAnimations.IDLE_STRETCH_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.angryAnimationState, TherizinosaurusAnimations.AGGRO_BLEND, ageInTicks, partialTicks);

        this.faceTarget(entity, netHeadYaw, headPitch, 2, head, neck);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}