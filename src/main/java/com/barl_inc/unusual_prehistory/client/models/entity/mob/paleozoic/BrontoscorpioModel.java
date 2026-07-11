package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.BrontoscorpioAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Brontoscorpio;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class BrontoscorpioModel extends UP2Model<Brontoscorpio> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart arm_left1;
    private final ModelPart arm_left2;
    private final ModelPart claw_upper_left;
    private final ModelPart claw_lower_left;
    private final ModelPart arm_right1;
    private final ModelPart arm_right2;
    private final ModelPart claw_upper_right;
    private final ModelPart claw_lower_right;
    private final ModelPart leg_control;
    private final ModelPart leg_cluster_left;
    private final ModelPart leg_left1;
    private final ModelPart leg_pivot_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_pivot_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_pivot_left3;
    private final ModelPart leg_left4;
    private final ModelPart leg_pivot_left4;
    private final ModelPart leg_cluster_right;
    private final ModelPart leg_right1;
    private final ModelPart leg_pivot_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_pivot_right2;
    private final ModelPart leg_right3;
    private final ModelPart leg_pivot_right3;
    private final ModelPart leg_right4;
    private final ModelPart leg_pivot_right4;

	public BrontoscorpioModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
        this.arm_left1 = this.body.getChild("arm_left1");
        this.arm_left2 = this.arm_left1.getChild("arm_left2");
        this.claw_upper_left = this.arm_left2.getChild("claw_upper_left");
        this.claw_lower_left = this.arm_left2.getChild("claw_lower_left");
        this.arm_right1 = this.body.getChild("arm_right1");
        this.arm_right2 = this.arm_right1.getChild("arm_right2");
        this.claw_upper_right = this.arm_right2.getChild("claw_upper_right");
        this.claw_lower_right = this.arm_right2.getChild("claw_lower_right");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_cluster_left = this.leg_control.getChild("leg_cluster_left");
        this.leg_left1 = this.leg_cluster_left.getChild("leg_left1");
        this.leg_pivot_left1 = this.leg_left1.getChild("leg_pivot_left1");
        this.leg_left2 = this.leg_cluster_left.getChild("leg_left2");
        this.leg_pivot_left2 = this.leg_left2.getChild("leg_pivot_left2");
        this.leg_left3 = this.leg_cluster_left.getChild("leg_left3");
        this.leg_pivot_left3 = this.leg_left3.getChild("leg_pivot_left3");
        this.leg_left4 = this.leg_cluster_left.getChild("leg_left4");
        this.leg_pivot_left4 = this.leg_left4.getChild("leg_pivot_left4");
        this.leg_cluster_right = this.leg_control.getChild("leg_cluster_right");
        this.leg_right1 = this.leg_cluster_right.getChild("leg_right1");
        this.leg_pivot_right1 = this.leg_right1.getChild("leg_pivot_right1");
        this.leg_right2 = this.leg_cluster_right.getChild("leg_right2");
        this.leg_pivot_right2 = this.leg_right2.getChild("leg_pivot_right2");
        this.leg_right3 = this.leg_cluster_right.getChild("leg_right3");
        this.leg_pivot_right3 = this.leg_right3.getChild("leg_pivot_right3");
        this.leg_right4 = this.leg_cluster_right.getChild("leg_right4");
        this.leg_pivot_right4 = this.leg_right4.getChild("leg_pivot_right4");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, -5.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.5F, -3.5F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-3.0F, -2.5F, -3.5F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -0.5F, 2.5F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(30, 2).addBox(-1.0F, -8.0F, 0.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.5F, 5.5F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 1.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(30, 27).addBox(0.0F, -1.0F, -4.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -6.0F));

        PartDefinition arm_left1 = body.addOrReplaceChild("arm_left1", CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, -1.0F, -2.0F, 13.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -0.5F, -4.0F));

        PartDefinition arm_left2 = arm_left1.addOrReplaceChild("arm_left2", CubeListBuilder.create().texOffs(16, 27).addBox(-5.0F, -4.0F, -2.0F, 5.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, 0.0F, -2.0F));

        PartDefinition claw_upper_left = arm_left2.addOrReplaceChild("claw_upper_left", CubeListBuilder.create().texOffs(22, 20).addBox(-7.0F, -5.0F, -1.0F, 8.0F, 5.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(8, 32).addBox(-9.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-9.0F, -3.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 1.0F, -1.0F));

        PartDefinition claw_lower_left = arm_left2.addOrReplaceChild("claw_lower_left", CubeListBuilder.create().texOffs(17, 16).addBox(-8.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(30, 13).addBox(-10.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 35).addBox(-10.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 3.0F, -1.0F));

        PartDefinition arm_right1 = body.addOrReplaceChild("arm_right1", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-13.0F, -1.0F, -2.0F, 13.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, -0.5F, -4.0F));

        PartDefinition arm_right2 = arm_right1.addOrReplaceChild("arm_right2", CubeListBuilder.create().texOffs(16, 27).mirror().addBox(0.0F, -4.0F, -2.0F, 5.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-13.0F, 0.0F, -2.0F));

        PartDefinition claw_upper_right = arm_right2.addOrReplaceChild("claw_upper_right", CubeListBuilder.create().texOffs(22, 20).mirror().addBox(-1.0F, -5.0F, -1.0F, 8.0F, 5.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(8, 32).mirror().addBox(7.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 36).mirror().addBox(8.0F, -3.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, 1.0F, -1.0F));

        PartDefinition claw_lower_right = arm_right2.addOrReplaceChild("claw_lower_right", CubeListBuilder.create().texOffs(17, 16).mirror().addBox(-2.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(30, 13).mirror().addBox(8.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 35).mirror().addBox(9.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 3.0F, -1.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition leg_cluster_left = leg_control.addOrReplaceChild("leg_cluster_left", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition leg_left1 = leg_cluster_left.addOrReplaceChild("leg_left1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition leg_pivot_left1 = leg_left1.addOrReplaceChild("leg_pivot_left1", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, 0.0F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_cluster_left.addOrReplaceChild("leg_left2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.125F, -0.25F, 0.0F, 0.0F, 0.5236F));

        PartDefinition leg_pivot_left2 = leg_left2.addOrReplaceChild("leg_pivot_left2", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, 0.0F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.125F, 0.0F));

        PartDefinition leg_left3 = leg_cluster_left.addOrReplaceChild("leg_left3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, 1.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition leg_pivot_left3 = leg_left3.addOrReplaceChild("leg_pivot_left3", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, 0.0F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left4 = leg_cluster_left.addOrReplaceChild("leg_left4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.125F, 3.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition leg_pivot_left4 = leg_left4.addOrReplaceChild("leg_pivot_left4", CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, 0.0F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.125F, 0.0F));

        PartDefinition leg_cluster_right = leg_control.addOrReplaceChild("leg_cluster_right", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition leg_right1 = leg_cluster_right.addOrReplaceChild("leg_right1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition leg_pivot_right1 = leg_right1.addOrReplaceChild("leg_pivot_right1", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-8.0F, 0.0F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_cluster_right.addOrReplaceChild("leg_right2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.125F, -0.25F, 0.0F, 0.0F, -0.5236F));

        PartDefinition leg_pivot_right2 = leg_right2.addOrReplaceChild("leg_pivot_right2", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-8.0F, 0.0F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.125F, 0.0F));

        PartDefinition leg_right3 = leg_cluster_right.addOrReplaceChild("leg_right3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, 1.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition leg_pivot_right3 = leg_right3.addOrReplaceChild("leg_pivot_right3", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-8.0F, 0.0F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_right4 = leg_cluster_right.addOrReplaceChild("leg_right4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.125F, 3.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition leg_pivot_right4 = leg_right4.addOrReplaceChild("leg_pivot_right4", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-8.0F, 0.0F, -0.5F, 8.0F, 0.0F, 1.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.125F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Brontoscorpio entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.getPose() != UP2Poses.WARNING.get() && entity.getPose() != UP2Poses.TAIL_WHIPPING.get()) {
            this.animateWalk(BrontoscorpioAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
        }
		this.animateIdleSmooth(entity.idleAnimationState, BrontoscorpioAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 4);
        this.animateSmooth(entity.warnAnimationState, BrontoscorpioAnimations.SCREECH, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack1AnimationState, BrontoscorpioAnimations.PINCH_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack2AnimationState, BrontoscorpioAnimations.PINCH_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.stingAnimationState, BrontoscorpioAnimations.STING, ageInTicks, partialTicks);
        this.animateSmooth(entity.snip1AnimationState, BrontoscorpioAnimations.IDLE_SNIP_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.snip2AnimationState, BrontoscorpioAnimations.IDLE_SNIP_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.quirkAnimationState, BrontoscorpioAnimations.IDLE_QUIRK_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.feedAnimationState, BrontoscorpioAnimations.FEED_BLEND, ageInTicks, partialTicks);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}