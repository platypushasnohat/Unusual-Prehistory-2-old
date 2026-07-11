package com.barl_inc.unusual_prehistory.client.models.entity.mob.recently_extinct;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.recently_extinct.ThylacineAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.recently_extinct.Thylacine;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class ThylacineModel extends UP2Model<Thylacine> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body_upper;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart jaw;
    private final ModelPart cheeks;
    private final ModelPart tail;
    private final ModelPart joey;
    private final ModelPart joey_eyes;
    private final ModelPart joey_jaw;
    private final ModelPart arm_control;
    private final ModelPart arm_left;
    private final ModelPart arm_right;
    private final ModelPart leg_control;
    private final ModelPart leg_left;
    private final ModelPart leg_right;

	public ThylacineModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body_upper = this.body_main.getChild("body_upper");
        this.body = this.body_upper.getChild("body");
        this.head = this.body.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.jaw = this.head.getChild("jaw");
        this.cheeks = this.jaw.getChild("cheeks");
        this.tail = this.body.getChild("tail");
        this.joey = this.body.getChild("joey");
        this.joey_eyes = this.joey.getChild("joey_eyes");
        this.joey_jaw = this.joey.getChild("joey_jaw");
        this.arm_control = this.body_upper.getChild("arm_control");
        this.arm_left = this.arm_control.getChild("arm_left");
        this.arm_right = this.arm_control.getChild("arm_right");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_left = this.leg_control.getChild("leg_left");
        this.leg_right = this.leg_control.getChild("leg_right");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -9.5F, 0.0F));

        PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, 4.5F));

        PartDefinition body = body_upper.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.5F, -5.5F, 5.0F, 5.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -2.5F, -4.5F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(29, 15).addBox(-3.5F, -2.0F, -4.0F, 7.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 10).mirror().addBox(1.5F, -3.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(36, 10).addBox(-3.5F, -3.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 23).addBox(-1.5F, -1.0F, -9.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(23, 39).addBox(-1.0F, 1.0F, -9.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -5.5F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(28, 23).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(2.5F, -0.5F, -3.5F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(28, 23).mirror().addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-2.5F, -0.5F, -3.5F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 31).addBox(-1.5F, 0.0F, -8.75F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(28, 36).addBox(-1.0F, -3.0F, -8.75F, 2.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -0.25F));

        PartDefinition cheeks = jaw.addOrReplaceChild("cheeks", CubeListBuilder.create().texOffs(0, 40).addBox(-3.5F, 0.0F, -4.0F, 7.0F, 1.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.25F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, 5.5F));

        PartDefinition joey = body.addOrReplaceChild("joey", CubeListBuilder.create().texOffs(22, 0).addBox(-0.5F, -2.0F, -3.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(23, 7).addBox(-0.5F, -1.0F, -5.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(31, 0).addBox(-1.5F, -3.0F, -2.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.5F, 2.5F, 4.5F, -2.618F, 0.0F, 3.1416F));

        PartDefinition joey_eyes = joey.addOrReplaceChild("joey_eyes", CubeListBuilder.create().texOffs(33, 3).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.5F, -0.5F, -2.5F));

        PartDefinition joey_jaw = joey.addOrReplaceChild("joey_jaw", CubeListBuilder.create().texOffs(34, 0).addBox(-0.5F, 0.0F, -5.0F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition arm_control = body_upper.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -8.0F));

        PartDefinition arm_left = arm_control.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(0, 45).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offset(1.5F, 4.0F, 0.0F));

        PartDefinition arm_right = arm_control.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-1.5F, 4.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, 4.5F));

        PartDefinition leg_left = leg_control.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(18, 31).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 0.0F, 0.0F));

        PartDefinition leg_right = leg_control.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(18, 31).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Thylacine entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isInWaterOrBubble() && !entity.isEepy() && !entity.isSitting() && !entity.isLeaping()) {
            if (entity.isBipedal()) {
                if (entity.isRunning()) {
                    this.animateWalk(ThylacineAnimations.RUN_BIPEDAL, limbSwing, limbSwingAmount, 1.5F, 3);
                } else {
                    this.animateWalk(ThylacineAnimations.WALK_BIPEDAL, limbSwing, limbSwingAmount, 2, 4);
                }
            } else {
                if (entity.isRunning()) {
                    this.animateWalk(ThylacineAnimations.RUN, limbSwing, limbSwingAmount, 1.5F, 3);
                } else {
                    this.animateWalk(ThylacineAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
                }
            }
        }

        this.animateIdleSmooth(entity.idleAnimationState, ThylacineAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, entity.isRunning() ? 3 : 4);
        this.animateIdleSmooth(entity.idleBipedalAnimationState, ThylacineAnimations.IDLE_BIPEDAL, ageInTicks, partialTicks, limbSwingAmount, entity.isRunning() ? 3 : 4);
        this.animateSmooth(entity.swimAnimationState, ThylacineAnimations.SWIM, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, ThylacineAnimations.SLEEP, ageInTicks, partialTicks);
        this.animateSmooth(entity.sitAnimationState, ThylacineAnimations.SIT, ageInTicks, partialTicks);
        this.animateSmooth(entity.sniffAnimationState, ThylacineAnimations.IDLE_SNIFF_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.yawnAnimationState, ThylacineAnimations.YAWN, ageInTicks, partialTicks);
        this.animateSmooth(entity.fallAnimationState, ThylacineAnimations.JUMP_DOWN, ageInTicks, partialTicks);
        this.animateSmooth(entity.jumpAnimationState, ThylacineAnimations.JUMP_UP, ageInTicks, partialTicks);
        this.animateSmooth(entity.chewAnimationState, ThylacineAnimations.CHEW_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.attackAnimationState, ThylacineAnimations.BITE_BLEND, ageInTicks, partialTicks);
        this.animate(entity.eatAnimationState, ThylacineAnimations.EAT_BLEND, ageInTicks);

        if (this.young) {
            this.applyStatic(ThylacineAnimations.HIDE_JOEY);
            this.applyStatic(ThylacineAnimations.BABY_TRANSFORM);
        }
        if (!entity.hasJoey()) {
            this.applyStatic(ThylacineAnimations.HIDE_JOEY);
        }

        this.faceTarget(entity, netHeadYaw, headPitch, 2, head);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}