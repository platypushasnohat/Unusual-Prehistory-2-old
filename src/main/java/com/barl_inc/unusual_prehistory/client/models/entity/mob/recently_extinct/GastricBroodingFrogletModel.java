package com.barl_inc.unusual_prehistory.client.models.entity.mob.recently_extinct;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.recently_extinct.GastricBroodingFrogAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.recently_extinct.GastricBroodingFrog;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class GastricBroodingFrogletModel extends UP2Model<GastricBroodingFrog> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart arm_control;
    private final ModelPart arm_left1;
    private final ModelPart arm_left2;
    private final ModelPart arm_right1;
    private final ModelPart arm_right2;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;

	public GastricBroodingFrogletModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.eye_left = this.body.getChild("eye_left");
        this.eye_right = this.body.getChild("eye_right");
        this.arm_control = this.body_main.getChild("arm_control");
        this.arm_left1 = this.arm_control.getChild("arm_left1");
        this.arm_left2 = this.arm_left1.getChild("arm_left2");
        this.arm_right1 = this.arm_control.getChild("arm_right1");
        this.arm_right2 = this.arm_right1.getChild("arm_right2");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_left1 = this.leg_control.getChild("leg_left1");
        this.leg_left2 = this.leg_left1.getChild("leg_left2");
        this.leg_right1 = this.leg_control.getChild("leg_right1");
        this.leg_right2 = this.leg_right1.getChild("leg_right2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -4.0F, 6.0F, 4.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eye_left = body.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(12, 14).mirror().addBox(-1.0F, -1.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, -1.75F, -1.0F));

        PartDefinition eye_right = body.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(12, 14).addBox(-1.0F, -1.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -1.75F, -1.0F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -1.5F));

        PartDefinition arm_left1 = arm_control.addOrReplaceChild("arm_left1", CubeListBuilder.create().texOffs(12, 17).mirror().addBox(-0.05F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition arm_left2 = arm_left1.addOrReplaceChild("arm_left2", CubeListBuilder.create().texOffs(12, 11).mirror().addBox(-2.0F, -0.05F, -1.6F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 2.0F, 0.1F));

        PartDefinition arm_right1 = arm_control.addOrReplaceChild("arm_right1", CubeListBuilder.create().texOffs(12, 17).addBox(0.05F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition arm_right2 = arm_right1.addOrReplaceChild("arm_right2", CubeListBuilder.create().texOffs(12, 11).addBox(0.0F, -0.05F, -1.6F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 2.0F, 0.1F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(0, 11).mirror().addBox(-1.0F, 0.0F, -1.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(-4, 18).mirror().addBox(0.0F, 0.0F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(2.0F, 4.0F, 1.0F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(0, 11).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(-4, 18).addBox(-4.0F, 0.0F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offset(-2.0F, 4.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(@NotNull GastricBroodingFrog entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isLeaping() && !entity.wasLaunched()) {
            if (entity.isInWaterOrBubble()) {
                this.animateWalk(GastricBroodingFrogAnimations.FROGLET_SWIM, limbSwing, limbSwingAmount, 1.0F, 2.5F);
            } else {
                this.animateWalk(GastricBroodingFrogAnimations.FROGLET_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
            }
        }
		this.animateIdleSmooth(entity.idleAnimationState, GastricBroodingFrogAnimations.FROGLET_IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
        this.animateIdleSmooth(entity.idleAnimationState, GastricBroodingFrogAnimations.FROGLET_IDLE_OVERLAY, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
        this.animateIdleSmooth(entity.swimIdleAnimationState, GastricBroodingFrogAnimations.FROGLET_SWIM, ageInTicks, partialTicks, limbSwingAmount, 3);
        this.animateSmooth(entity.leapAnimationState, GastricBroodingFrogAnimations.FROGLET_JUMP_HOLD, ageInTicks, partialTicks);
        this.animateSmooth(entity.launchAnimationState, GastricBroodingFrogAnimations.FROGLET_LAUNCH, ageInTicks, partialTicks);

        if (entity.isInWaterOrBubble() && !entity.wasLaunched()) {
            this.root.xRot = headPitch * ((float) Math.PI / 180F);
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}