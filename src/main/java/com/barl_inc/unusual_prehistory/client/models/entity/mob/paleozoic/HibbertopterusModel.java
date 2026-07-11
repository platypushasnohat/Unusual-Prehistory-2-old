package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.HibbertopterusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Hibbertopterus;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class HibbertopterusModel extends UP2Model<Hibbertopterus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart left_arm1;
    private final ModelPart left_arm2;
    private final ModelPart right_arm1;
    private final ModelPart right_arm2;
    private final ModelPart thorax;
    private final ModelPart tail;
    private final ModelPart leg_control;
    private final ModelPart right_legs;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;
    private final ModelPart right_leg3;
    private final ModelPart left_legs;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart left_leg3;

	public HibbertopterusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.left_arm1 = this.body.getChild("left_arm1");
        this.left_arm2 = this.left_arm1.getChild("left_arm2");
        this.right_arm1 = this.body.getChild("right_arm1");
        this.right_arm2 = this.right_arm1.getChild("right_arm2");
        this.thorax = this.body.getChild("thorax");
        this.tail = this.thorax.getChild("tail");
        this.leg_control = this.body_main.getChild("leg_control");
        this.right_legs = this.leg_control.getChild("right_legs");
        this.right_leg1 = this.right_legs.getChild("right_leg1");
        this.right_leg2 = this.right_legs.getChild("right_leg2");
        this.right_leg3 = this.right_legs.getChild("right_leg3");
        this.left_legs = this.leg_control.getChild("left_legs");
        this.left_leg1 = this.left_legs.getChild("left_leg1");
        this.left_leg2 = this.left_legs.getChild("left_leg2");
        this.left_leg3 = this.left_legs.getChild("left_leg3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -19.0F, -0.5F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-25.0F, -9.5F, -19.5F, 50.0F, 21.0F, 39.0F, new CubeDeformation(0.0F))
                .texOffs(14, 98).addBox(1.0F, -10.5F, -4.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 98).mirror().addBox(-3.0F, -10.5F, -4.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm1 = body.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(42, 97).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 11.5F, -19.5F));

        PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(26, 81).addBox(-1.0F, -2.0F, -8.0F, 6.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 98).addBox(1.0F, 0.0F, -11.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.5F, 0.0F, -2.0F));

        PartDefinition right_arm1 = body.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(42, 97).mirror().addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, 11.5F, -19.5F));

        PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(26, 81).mirror().addBox(-5.0F, -2.0F, -8.0F, 6.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 98).mirror().addBox(-5.0F, 0.0F, -11.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-0.5F, 0.0F, -2.0F));

        PartDefinition thorax = body.addOrReplaceChild("thorax", CubeListBuilder.create().texOffs(0, 60).addBox(-7.5F, -4.5F, 0.0F, 15.0F, 9.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(54, 60).addBox(-8.5F, -4.51F, 0.0F, 17.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 19.5F));

        PartDefinition tail = thorax.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(54, 72).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(74, 81).addBox(-2.5F, -1.51F, 1.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 12.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 11.5F, -8.5F));

        PartDefinition right_legs = leg_control.addOrReplaceChild("right_legs", CubeListBuilder.create(), PartPose.offsetAndRotation(-22.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition right_leg1 = right_legs.addOrReplaceChild("right_leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -8.0F, -0.0873F, -0.4363F, 0.0F));

        PartDefinition arm_r1 = right_leg1.addOrReplaceChild("arm_r1", CubeListBuilder.create().texOffs(56, 97).mirror().addBox(-4.1812F, -0.7498F, -7.8442F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 81).mirror().addBox(-14.1812F, 2.2502F, -9.3442F, 7.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(76, 90).mirror().addBox(-15.1812F, 1.2502F, -5.3442F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(90, 72).mirror().addBox(-15.1812F, 1.2502F, -7.3442F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(26, 93).mirror().addBox(-14.1812F, 13.2502F, -9.3442F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(54, 90).mirror().addBox(-14.1812F, 15.2502F, -9.3442F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(92, 95).mirror().addBox(-10.1812F, 14.2502F, -9.3442F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(92, 89).mirror().addBox(-7.1812F, 4.2502F, -7.8442F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.9583F, 0.0F, 6.3442F, 0.0F, 0.0F, 0.4363F));

        PartDefinition right_leg2 = right_legs.addOrReplaceChild("right_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition arm_r2 = right_leg2.addOrReplaceChild("arm_r2", CubeListBuilder.create().texOffs(56, 97).mirror().addBox(-1.5F, -2.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 81).mirror().addBox(-11.5F, 1.0F, -3.0F, 7.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(76, 90).mirror().addBox(-12.5F, 0.0F, 1.0F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(90, 72).mirror().addBox(-12.5F, 0.0F, -1.0F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(26, 93).mirror().addBox(-11.5F, 12.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(54, 90).mirror().addBox(-11.5F, 14.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(92, 95).mirror().addBox(-7.5F, 13.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(92, 89).mirror().addBox(-4.5F, 3.0F, -1.5F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition right_leg3 = right_legs.addOrReplaceChild("right_leg3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, 8.0F, 0.0873F, 0.4363F, 0.0F));

        PartDefinition arm_r3 = right_leg3.addOrReplaceChild("arm_r3", CubeListBuilder.create().texOffs(56, 97).mirror().addBox(1.1812F, -3.2502F, -7.8441F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 81).mirror().addBox(-8.8188F, -0.2502F, -9.3441F, 7.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(76, 90).mirror().addBox(-9.8188F, -1.2502F, -5.3441F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(90, 72).mirror().addBox(-9.8188F, -1.2502F, -7.3441F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(26, 93).mirror().addBox(-8.8188F, 10.7498F, -9.3441F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(54, 90).mirror().addBox(-8.8188F, 12.7498F, -9.3441F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(92, 95).mirror().addBox(-4.8188F, 11.7498F, -9.3441F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(92, 89).mirror().addBox(-1.8188F, 1.7498F, -7.8441F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.9583F, 0.0F, 6.3441F, 0.0F, 0.0F, 0.4363F));

        PartDefinition left_legs = leg_control.addOrReplaceChild("left_legs", CubeListBuilder.create(), PartPose.offsetAndRotation(22.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition left_leg1 = left_legs.addOrReplaceChild("left_leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -8.0F, -0.0873F, 0.4363F, 0.0F));

        PartDefinition arm_r4 = left_leg1.addOrReplaceChild("arm_r4", CubeListBuilder.create().texOffs(56, 97).addBox(1.1812F, -0.7498F, -7.8442F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 81).addBox(7.1812F, 2.2502F, -9.3442F, 7.0F, 11.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(76, 90).addBox(7.1812F, 1.2502F, -5.3442F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(90, 72).addBox(7.1812F, 1.2502F, -7.3442F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(26, 93).addBox(12.1812F, 13.2502F, -9.3442F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(54, 90).addBox(9.1812F, 15.2502F, -9.3442F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(92, 95).addBox(9.1812F, 14.2502F, -9.3442F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(92, 89).addBox(1.1812F, 4.2502F, -7.8442F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9583F, 0.0F, 6.3442F, 0.0F, 0.0F, -0.4363F));

        PartDefinition left_leg2 = left_legs.addOrReplaceChild("left_leg2", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition arm_r5 = left_leg2.addOrReplaceChild("arm_r5", CubeListBuilder.create().texOffs(56, 97).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 81).addBox(4.5F, 1.0F, -3.0F, 7.0F, 11.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(76, 90).addBox(4.5F, 0.0F, 1.0F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(90, 72).addBox(4.5F, 0.0F, -1.0F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(26, 93).addBox(9.5F, 12.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(54, 90).addBox(6.5F, 14.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(92, 95).addBox(6.5F, 13.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(92, 89).addBox(-1.5F, 3.0F, -1.5F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition left_leg3 = left_legs.addOrReplaceChild("left_leg3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, 8.0F, 0.0873F, -0.4363F, 0.0F));

        PartDefinition arm_r6 = left_leg3.addOrReplaceChild("arm_r6", CubeListBuilder.create().texOffs(56, 97).addBox(-4.1812F, -3.2502F, -7.8441F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 81).addBox(1.8188F, -0.2502F, -9.3441F, 7.0F, 11.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(76, 90).addBox(1.8188F, -1.2502F, -5.3441F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(90, 72).addBox(1.8188F, -1.2502F, -7.3441F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(26, 93).addBox(6.8188F, 10.7498F, -9.3441F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(54, 90).addBox(3.8188F, 12.7498F, -9.3441F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(92, 95).addBox(3.8188F, 11.7498F, -9.3441F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(92, 89).addBox(-4.1812F, 1.7498F, -7.8441F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9583F, 0.0F, 6.3441F, 0.0F, 0.0F, -0.4363F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(@NotNull Hibbertopterus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;


        this.animateWalk(HibbertopterusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
		this.animateIdleSmooth(entity.idleAnimationState, HibbertopterusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 4);
        this.animateIdleSmooth(entity.idleAnimationState, HibbertopterusAnimations.IDLE_OVERLAY_BLEND, ageInTicks, partialTicks, limbSwingAmount, 4);
        this.animateSmooth(entity.plowAnimationState, HibbertopterusAnimations.DIG_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.danceAnimationState, HibbertopterusAnimations.DANCE, ageInTicks, partialTicks);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}

    public Vec3 getRiderPosition(Vec3 offset) {
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        Vector4f armOffsetVec = new Vector4f((float) offset.x, (float) offset.y, (float) offset.z, 1.0F);
        armOffsetVec.mul(poseStack.last().pose());
        Vec3 vec3 = new Vec3(armOffsetVec.x(), armOffsetVec.y(), armOffsetVec.z());
        poseStack.popPose();
        return vec3;
    }

    public void positionRider(PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        this.body_main.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
    }
}