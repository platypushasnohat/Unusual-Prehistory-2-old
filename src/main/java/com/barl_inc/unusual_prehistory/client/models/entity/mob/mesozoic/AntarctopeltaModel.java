package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.AntarctopeltaAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Antarctopelta;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class AntarctopeltaModel extends UP2Model<Antarctopelta> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart neck_spike_left;
    private final ModelPart neck_spike_right;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart arm_control;
    private final ModelPart arm_left;
    private final ModelPart arm_right;
    private final ModelPart leg_control;
    private final ModelPart leg_left;
    private final ModelPart leg_right;

	public AntarctopeltaModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.neck = this.body.getChild("neck");
        this.neck_spike_left = this.neck.getChild("neck_spike_left");
        this.neck_spike_right = this.neck.getChild("neck_spike_right");
        this.head = this.neck.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.jaw = this.head.getChild("jaw");
        this.tail = this.body.getChild("tail");
        this.arm_control = this.body_main.getChild("arm_control");
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

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -15.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.5F, -8.0F, -11.5F, 17.0F, 14.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(-19, 86).addBox(-10.5F, 4.0F, -10.5F, 21.0F, 0.0F, 21.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(69, 38).addBox(-4.5F, -2.5F, -10.0F, 9.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(68, 56).addBox(-3.5F, 2.5F, -8.0F, 7.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -11.5F));

        PartDefinition neck_spike_left = neck.addOrReplaceChild("neck_spike_left", CubeListBuilder.create().texOffs(68, 36).addBox(0.0F, -7.0F, 1.0F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0025F))
                .texOffs(60, 7).addBox(0.0F, -5.0F, -2.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0025F))
                .texOffs(67, 7).addBox(0.0F, -3.0F, -5.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.5F, -1.5F, -5.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition neck_spike_right = neck.addOrReplaceChild("neck_spike_right", CubeListBuilder.create().texOffs(68, 36).mirror().addBox(0.0F, -7.0F, 1.0F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(60, 7).mirror().addBox(0.0F, -5.0F, -2.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(67, 7).mirror().addBox(0.0F, -3.0F, -5.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.5F, -1.5F, -5.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(1, 50).addBox(-2.5F, -2.5F, -5.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(1, 39).addBox(-2.5F, -2.5F, -7.0F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -10.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(17, 60).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offset(2.0F, -1.0F, -1.5F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(17, 60).mirror().addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-2.0F, -1.0F, -1.5F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(42, 44).addBox(-2.5F, 0.0F, -5.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(66, 0).addBox(-7.5F, -1.0F, 6.0F, 15.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(-3.5F, -3.0F, 0.0F, 7.0F, 6.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(45, 72).addBox(-10.5F, -1.0F, 22.0F, 21.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 71).addBox(-9.5F, -1.0F, 15.0F, 19.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 79).addBox(-8.5F, -1.0F, 10.0F, 17.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 11.5F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, -7.5F));

        PartDefinition arm_left = arm_control.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(80, 5).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(41, 40).addBox(-2.5F, 6.0F, -4.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, 0.0F));

        PartDefinition arm_right = arm_control.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(80, 5).mirror().addBox(-2.5F, -2.0F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(41, 40).mirror().addBox(-2.5F, 6.0F, -4.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 0.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 7.5F));

        PartDefinition leg_left = leg_control.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(80, 21).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(54, 40).addBox(-2.5F, 6.0F, -4.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, 0.0F));

        PartDefinition leg_right = leg_control.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(80, 21).mirror().addBox(-2.5F, -2.0F, -3.0F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(54, 40).mirror().addBox(-2.5F, 6.0F, -4.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NotNull Antarctopelta entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        this.animateWalk(AntarctopeltaAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
		this.animateIdleSmooth(entity.idleAnimationState, AntarctopeltaAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        if (this.young) this.applyStatic(AntarctopeltaAnimations.BABY_TRANSFORM);
        this.faceTarget(entity, netHeadYaw, headPitch, 2, neck, head);
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail.yRot = Mth.lerp(0.2F, this.tail.yRot, tailYaw * 0.25F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}