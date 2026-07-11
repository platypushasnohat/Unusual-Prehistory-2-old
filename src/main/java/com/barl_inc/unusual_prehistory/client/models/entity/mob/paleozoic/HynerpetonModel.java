package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.HynerpetonAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Hynerpeton;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class HynerpetonModel extends UP2Model<Hynerpeton> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart fin;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart tail_water;
    private final ModelPart tail_land;
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

	public HynerpetonModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.fin = this.body.getChild("fin");
        this.head = this.body.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tail = this.body.getChild("tail");
        this.tail_water = this.tail.getChild("tail_water");
        this.tail_land = this.tail.getChild("tail_land");
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

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -4.0F, 7.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fin = body.addOrReplaceChild("fin", CubeListBuilder.create().texOffs(0, 27).addBox(0.0F, -2.0F, -4.0F, 0.0F, 8.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 14).addBox(-3.0F, -1.0F, -8.0F, 6.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -4.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 29).addBox(-3.0F, 0.0F, -1.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.004F))
                .texOffs(22, 24).addBox(-3.0F, 1.0F, -8.0F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.004F)), PartPose.offset(0.0F, 0.0F, -1.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -3.0F, 2.0F, 2.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 4.0F));

        PartDefinition tail_water = tail.addOrReplaceChild("tail_water", CubeListBuilder.create().texOffs(20, 22).addBox(0.0F, -2.0F, 0.0F, 0.0F, 6.0F, 15.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition tail_land = tail.addOrReplaceChild("tail_land", CubeListBuilder.create().texOffs(20, 28).addBox(0.0F, -2.0F, 0.0F, 0.0F, 8.0F, 15.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -3.0F));

        PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(30, 7).addBox(0.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offset(3.5F, 0.0F, 0.0F));

        PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(30, 3).addBox(-2.0F, -0.01F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.0F, 2.0F, 0.0F));

        PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(30, 7).mirror().addBox(-2.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offset(-3.5F, 0.0F, 0.0F));

        PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(30, 3).mirror().addBox(-2.0F, -0.01F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.0F, 2.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 3.0F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(30, 7).addBox(0.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offset(3.5F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(30, 0).addBox(-2.0F, -0.01F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.0F, 2.0F, 0.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(30, 7).mirror().addBox(-2.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offset(-3.5F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(30, 0).mirror().addBox(-2.0F, -0.01F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.0F, 2.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Hynerpeton entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble()) {
            this.animateWalk(HynerpetonAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        } else {
            this.animateWalk(HynerpetonAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
        }
		this.animateIdleSmooth(entity.idleAnimationState, HynerpetonAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.idleAnimationState, HynerpetonAnimations.IDLE_OVERLAY_BLEND, ageInTicks, partialTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.swimIdleAnimationState, HynerpetonAnimations.SWIM_IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.bask1AnimationState, HynerpetonAnimations.BASK1, ageInTicks, partialTicks);
        this.animateSmooth(entity.bask2AnimationState, HynerpetonAnimations.BASK2, ageInTicks, partialTicks);

        if (this.young) this.applyStatic(HynerpetonAnimations.BABY_TRANSFORM);
        this.faceTarget(entity, netHeadYaw, headPitch, 2, head);

        if (entity.isInWaterOrBubble()) {
            this.root.xRot = headPitch * ((float) Math.PI / 180F);
            this.applyStatic(HynerpetonAnimations.HIDE_TAIL_LAND);
        } else {
            this.applyStatic(HynerpetonAnimations.HIDE_TAIL_WATER);
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}