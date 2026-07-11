package com.barl_inc.unusual_prehistory.client.models.entity.mob.multi_era;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.multi_era.LystrosaurusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.multi_era.Lystrosaurus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class LystrosaurusModel extends UP2Model<Lystrosaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart breathing;
    private final ModelPart head;
    private final ModelPart left_brow;
    private final ModelPart right_brow;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart arm_control;
    private final ModelPart left_arm1;
    private final ModelPart left_arm2;
    private final ModelPart right_arm1;
    private final ModelPart right_arm2;
    private final ModelPart leg_control;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;

	public LystrosaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.breathing = this.body.getChild("breathing");
        this.head = this.body.getChild("head");
        this.left_brow = this.head.getChild("left_brow");
        this.right_brow = this.head.getChild("right_brow");
        this.jaw = this.head.getChild("jaw");
        this.tail = this.body.getChild("tail");
        this.arm_control = this.body_main.getChild("arm_control");
        this.left_arm1 = this.arm_control.getChild("left_arm1");
        this.left_arm2 = this.left_arm1.getChild("left_arm2");
        this.right_arm1 = this.arm_control.getChild("right_arm1");
        this.right_arm2 = this.right_arm1.getChild("right_arm2");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg1 = this.leg_control.getChild("left_leg1");
        this.left_leg2 = this.left_leg1.getChild("left_leg2");
        this.right_leg1 = this.leg_control.getChild("right_leg1");
        this.right_leg2 = this.right_leg1.getChild("right_leg2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -4.5F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition breathing = body.addOrReplaceChild("breathing", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.5F, -8.0F, 12.0F, 11.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(1, 27).addBox(-3.5F, -4.0F, -6.0F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(21, 49).addBox(-1.5F, -2.0F, -9.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(37, 45).mirror().addBox(-3.5F, -1.0F, -8.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(37, 45).addBox(1.5F, -1.0F, -8.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, -8.0F));

        PartDefinition left_brow = head.addOrReplaceChild("left_brow", CubeListBuilder.create().texOffs(47, 49).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(2.0F, -4.5F, -4.5F));

        PartDefinition right_brow = head.addOrReplaceChild("right_brow", CubeListBuilder.create().texOffs(47, 49).mirror().addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-2.0F, -4.5F, -4.5F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(52, 27).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -6.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(32, 27).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -5.5F));

        PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(0, 43).addBox(-2.0F, -2.0F, -1.5F, 7.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(52, 32).addBox(2.0F, 2.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.0F, 0.0F));

        PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(21, 45).addBox(-2.5F, 0.0F, -4.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(3.5F, 4.0F, -0.5F));

        PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(0, 43).mirror().addBox(-5.0F, -2.0F, -1.5F, 7.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(52, 32).mirror().addBox(-5.0F, 2.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, 0.0F, 0.0F));

        PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(21, 45).mirror().addBox(-3.5F, 0.0F, -4.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-3.5F, 4.0F, -0.5F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(6.0F, 0.5F, 5.0F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(32, 37).addBox(-2.0F, -2.0F, -2.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 50).addBox(2.0F, 2.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(21, 45).addBox(-2.5F, 0.0F, -4.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(3.5F, 4.0F, -1.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(32, 37).mirror().addBox(-5.0F, -2.0F, -2.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 50).mirror().addBox(-5.0F, 2.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-12.0F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(21, 45).mirror().addBox(-3.5F, 0.0F, -4.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-3.5F, 4.0F, -1.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Lystrosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

		if ((entity.isInWaterOrBubble() && entity.onGround()) || !entity.isEepy()) {
            if (entity.isRunning()) this.animateWalk(LystrosaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.5F, 3);
            else this.animateWalk(LystrosaurusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
        }

        this.animateIdleSmooth(entity.idleAnimationState, LystrosaurusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.shakeAnimationState, LystrosaurusAnimations.IDLE_SHAKE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.attackAnimationState, LystrosaurusAnimations.BITE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.grazeAnimationState, LystrosaurusAnimations.EAT_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.digAnimationState, LystrosaurusAnimations.DIG, ageInTicks, partialTicks);
        this.animateSmooth(entity.scratch1AnimationState, LystrosaurusAnimations.IDLE_SCATCH1, ageInTicks, partialTicks);
        this.animateSmooth(entity.scratch2AnimationState, LystrosaurusAnimations.IDLE_SCATCH2, ageInTicks, partialTicks);
        this.animateSmooth(entity.blinkAnimationState, LystrosaurusAnimations.IDLE_BLINK_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, LystrosaurusAnimations.SLEEP, ageInTicks, partialTicks);
        this.animateSmooth(entity.swimAnimationState, LystrosaurusAnimations.SWIM, ageInTicks, partialTicks);

		if (this.young) this.applyStatic(LystrosaurusAnimations.BABY_TRANSFORM);

        this.animateHead(entity, this.head, netHeadYaw, headPitch);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}