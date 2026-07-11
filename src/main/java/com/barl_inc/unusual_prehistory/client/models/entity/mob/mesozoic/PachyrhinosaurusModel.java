package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.PachyrhinosaurusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Pachyrhinosaurus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class PachyrhinosaurusModel extends UP2Model<Pachyrhinosaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body_upper;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart arm_right1;
    private final ModelPart arm_right2;
    private final ModelPart arm_left1;
    private final ModelPart arm_left2;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

	public PachyrhinosaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body_upper = this.body_main.getChild("body_upper");
        this.body = this.body_upper.getChild("body");
        this.tail = this.body.getChild("tail");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.arm_right1 = this.body_upper.getChild("arm_right1");
        this.arm_right2 = this.arm_right1.getChild("arm_right2");
        this.arm_left1 = this.body_upper.getChild("arm_left1");
        this.arm_left2 = this.arm_left1.getChild("arm_left2");
        this.leg_left1 = this.body_main.getChild("leg_left1");
        this.leg_left2 = this.leg_left1.getChild("leg_left2");
        this.leg_left3 = this.leg_left2.getChild("leg_left3");
        this.leg_right1 = this.body_main.getChild("leg_right1");
        this.leg_right2 = this.leg_right1.getChild("leg_right2");
        this.leg_right3 = this.leg_right2.getChild("leg_right3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -40.0F, 0.0F));

        PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 11.0F));

        PartDefinition body = body_upper.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 95).addBox(-17.5F, 10.0F, -22.0F, 35.0F, 5.0F, 46.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-17.5F, -38.0F, -22.0F, 35.0F, 48.0F, 46.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -12.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 147).addBox(-7.5F, -3.0F, 0.0F, 15.0F, 13.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(163, 81).addBox(-7.5F, 10.0F, 2.0F, 15.0F, 4.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 22.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(117, 0).addBox(-6.0F, -9.0F, -21.0F, 12.0F, 20.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -19.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(97, 206).addBox(-7.5F, -9.0F, -9.0F, 15.0F, 17.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(190, 18).addBox(-5.5F, -9.0F, -17.0F, 11.0F, 11.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 210).addBox(-5.5F, -9.0F, -25.0F, 11.0F, 21.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(51, 227).addBox(-5.5F, -14.0F, -25.0F, 11.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(42, 210).addBox(-5.5F, -15.0F, -25.0F, 11.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(163, 45).addBox(-15.5F, -28.0F, 0.0F, 31.0F, 28.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(83, 181).addBox(-7.5F, 3.0F, -9.0F, 15.0F, 7.0F, 14.0F, new CubeDeformation(-0.01F))
                .texOffs(197, 3).addBox(-8.5F, -10.0F, -11.0F, 17.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(92, 151).addBox(-8.5F, -11.0F, -11.0F, 17.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(106, 239).addBox(-8.5F, -10.0F, -8.0F, 17.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(166, 218).addBox(-21.5F, -35.0F, 4.0F, 43.0F, 33.0F, 0.0F, new CubeDeformation(0.02F))
                .texOffs(141, 171).addBox(0.0F, -22.0F, -8.0F, 0.0F, 11.0F, 8.0F, new CubeDeformation(0.02F))
                .texOffs(122, 123).mirror().addBox(7.5F, 3.0F, -3.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(122, 123).addBox(-11.5F, 3.0F, -3.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -16.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(166, 0).addBox(-4.5F, -3.0F, -11.0F, 9.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(134, 132).addBox(-4.5F, -5.0F, -11.0F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -6.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(145, 206).addBox(-0.5F, -1.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(7.0F, -7.0F, -6.0F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(145, 206).addBox(-0.5F, -1.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(-7.0F, -7.0F, -6.0F));

        PartDefinition arm_right1 = body_upper.addOrReplaceChild("arm_right1", CubeListBuilder.create().texOffs(0, 11).addBox(-4.5F, -4.0F, -6.0F, 9.0F, 19.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.0F, 8.0F, -29.0F));

        PartDefinition arm_right2 = arm_right1.addOrReplaceChild("arm_right2", CubeListBuilder.create().texOffs(121, 103).mirror().addBox(-6.0F, 0.0F, -8.0F, 12.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 14.0F, -1.0F));

        PartDefinition arm_left1 = body_upper.addOrReplaceChild("arm_left1", CubeListBuilder.create().texOffs(0, 11).mirror().addBox(-4.5F, -4.0F, -6.0F, 9.0F, 19.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(15.0F, 8.0F, -29.0F));

        PartDefinition arm_left2 = arm_left1.addOrReplaceChild("arm_left2", CubeListBuilder.create().texOffs(121, 103).addBox(-6.0F, 0.0F, -8.0F, 12.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, -1.0F));

        PartDefinition leg_left1 = body_main.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(163, 110).addBox(-6.5F, -7.0F, -8.0F, 13.0F, 18.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 187).addBox(-6.5F, 11.0F, -8.0F, 13.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(17.0F, 15.0F, 11.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(0, 108).mirror().addBox(-4.5F, -4.0F, -5.5F, 9.0F, 17.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 9.0F, 4.5F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(121, 103).addBox(-6.0F, 0.0F, -8.0F, 12.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 0.5F));

        PartDefinition leg_right1 = body_main.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(163, 110).mirror().addBox(-6.5F, -7.0F, -8.0F, 13.0F, 18.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 187).mirror().addBox(-6.5F, 11.0F, -8.0F, 13.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-17.0F, 15.0F, 11.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(0, 108).addBox(-4.5F, -4.0F, -5.5F, 9.0F, 17.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 4.5F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(121, 103).mirror().addBox(-6.0F, 0.0F, -8.0F, 12.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 13.0F, 0.5F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(@NotNull Pachyrhinosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isInWaterOrBubble()) {
            if (entity.isRunning()) {
                this.animateWalk(PachyrhinosaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.0F, 2.5F);
            } else {
                this.animateWalk(PachyrhinosaurusAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
            }
        }

        this.animateIdleSmooth(entity.idleAnimationState, PachyrhinosaurusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
        this.animateSmooth(entity.swimAnimationState, PachyrhinosaurusAnimations.SWIM, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, PachyrhinosaurusAnimations.SLEEP, ageInTicks, partialTicks);
        this.animateSmooth(entity.attackAnimationState, PachyrhinosaurusAnimations.ATTACK_BLEND, ageInTicks, partialTicks);

        if (this.young) {
            this.applyStatic(PachyrhinosaurusAnimations.BABY_TRANSFORM);
        }
        this.faceTarget(entity, netHeadYaw, headPitch, 3, head);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}