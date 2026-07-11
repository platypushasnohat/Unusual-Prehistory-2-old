package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.DesmatosuchusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Desmatosuchus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DesmatosuchusModel extends UP2Model<Desmatosuchus> {

	private final ModelPart root;
	private final ModelPart body;
    private final ModelPart body_main;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart left_shoulder;
    private final ModelPart right_shoulder;
	private final ModelPart tail;
	private final ModelPart left_leg1;
	private final ModelPart left_leg2;
	private final ModelPart right_leg1;
	private final ModelPart right_leg2;
    private final ModelPart arm_control;
    private final ModelPart leg_control;
	private final ModelPart left_arm1;
	private final ModelPart left_arm2;
	private final ModelPart right_arm1;
	private final ModelPart right_arm2;

	public DesmatosuchusModel(ModelPart root) {
        super(0.5F, 24);
		this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.left_shoulder = this.body.getChild("left_shoulder");
		this.right_shoulder = this.body.getChild("right_shoulder");
		this.tail = this.body.getChild("tail");
        this.arm_control = this.body_main.getChild("arm_control");
        this.leg_control = this.body_main.getChild("leg_control");
		this.left_leg1 = this.leg_control.getChild("left_leg1");
		this.left_leg2 = this.left_leg1.getChild("left_leg2");
		this.right_leg1 = this.leg_control.getChild("right_leg1");
		this.right_leg2 = this.right_leg1.getChild("right_leg2");
		this.left_arm1 = this.arm_control.getChild("left_arm1");
		this.left_arm2 = this.left_arm1.getChild("left_arm2");
		this.right_arm1 = this.arm_control.getChild("right_arm1");
		this.right_arm2 = this.right_arm1.getChild("right_arm2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -13.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-7.0F, -5.5F, -12.0F, 14.0F, 11.0F, 24.0F, new CubeDeformation(0.01F))
                        .texOffs(40, 87).addBox(-5.0F, -9.5F, -5.6215F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.01F))
                        .texOffs(40, 87).addBox(0.0F, -9.5F, 4.3785F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.01F))
                        .texOffs(16, 85).addBox(-2.0F, -9.5F, -8.6215F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.01F))
                        .texOffs(16, 85).addBox(3.0F, -9.5F, 1.3785F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.1745F, 0.0F, 0.0F)
        );

        PartDefinition scutes_r1 = body.addOrReplaceChild("scutes_r1", CubeListBuilder.create().texOffs(66, 64).mirror().addBox(-3.0F, 0.0F, -10.0F, 4.0F, 0.0F, 20.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-7.0F, -5.5F, 3.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition scutes_r2 = body.addOrReplaceChild("scutes_r2", CubeListBuilder.create().texOffs(66, 64).addBox(-1.0F, 0.0F, -10.0F, 4.0F, 0.0F, 20.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(7.0F, -5.5F, 3.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 68).addBox(-4.0F, -3.0F, -8.0F, 8.0F, 7.0F, 10.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -1.5F, -12.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition Spike_r1 = neck.addOrReplaceChild("Spike_r1", CubeListBuilder.create().texOffs(76, 0).mirror().addBox(-3.0F, 0.0F, -4.5F, 4.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -3.0F, -4.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition Spike_r2 = neck.addOrReplaceChild("Spike_r2", CubeListBuilder.create().texOffs(76, 0).addBox(-1.0F, 0.0F, -4.5F, 4.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.0F, -3.0F, -4.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(76, 21).addBox(-3.0F, -2.0F, -5.0F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(56, 68).addBox(-2.0F, -3.0F, -10.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(72, 84).addBox(-2.0F, -2.0F, -10.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -8.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(76, 29).addBox(-2.5F, 0.0F, -5.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 85).addBox(-1.5F, 0.0F, -10.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(56, 70).addBox(-1.5F, 1.0F, -10.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition left_shoulder = body.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(28, 87).addBox(7.0F, 0.0F, 2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(76, 9).addBox(-1.0F, 0.0F, -2.0F, 10.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -5.5F, -10.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_spike = body.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(28, 87).mirror().addBox(-9.0F, 0.0F, 2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(76, 9).mirror().addBox(-9.0F, 0.0F, -2.0F, 10.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, -5.5F, -10.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 35).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 5.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.5F, 12.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition scutes_r3 = tail.addOrReplaceChild("scutes_r3", CubeListBuilder.create().texOffs(66, 35).mirror().addBox(-2.0F, 0.0F, -14.5F, 3.0F, 0.0F, 29.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -2.0F, 14.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition scutes_r4 = tail.addOrReplaceChild("scutes_r4", CubeListBuilder.create().texOffs(66, 35).addBox(-1.0F, 0.0F, -14.5F, 3.0F, 0.0F, 29.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.5F, -2.0F, 14.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 4.25F, -8.0F));

        PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(56, 84).addBox(-2.0F, -2.25F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 0.0F, 0.0F));

        PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(76, 15).addBox(-3.5F, -0.3F, -4.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 9.05F, -1.0F));

        PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(56, 84).mirror().addBox(-2.0F, -1.25F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.0F, -1.0F, 0.0F));

        PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(76, 15).mirror().addBox(-3.5F, 0.7F, -4.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 9.05F, -1.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 1.5F, 7.5F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(36, 68).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 14.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(76, 15).addBox(-3.5F, 0.0F, -4.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 11.5F, -1.5F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(36, 68).mirror().addBox(-2.5F, -2.5F, -2.5F, 5.0F, 14.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.0F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(76, 15).mirror().addBox(-3.5F, 0.0F, -4.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 11.5F, -1.5F));


		return LayerDefinition.create(meshdefinition, 256, 256);
	}

    @Override
    public void setupAnim(Desmatosuchus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

		if (!entity.isInWater() && !entity.isEepy()) {
            if (entity.isRunning()) this.animateWalk(DesmatosuchusAnimations.RUN, limbSwing, limbSwingAmount, 1, 2);
            else this.animateWalk(DesmatosuchusAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 3);
        }

        this.animateIdleSmooth(entity.idleAnimationState, DesmatosuchusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.swimAnimationState, DesmatosuchusAnimations.SWIM, ageInTicks, partialTicks);
        this.animateSmooth(entity.grazeAnimationState, DesmatosuchusAnimations.IDLE_GRAZE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.rollAnimationState, DesmatosuchusAnimations.ROLL, ageInTicks, partialTicks);
        this.animateSmooth(entity.shakeAnimationState, DesmatosuchusAnimations.IDLE_SHAKE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.sniff1AnimationState, DesmatosuchusAnimations.IDLE_SNIFF_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.sniff2AnimationState, DesmatosuchusAnimations.IDLE_SNIFF_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, DesmatosuchusAnimations.SLEEP, ageInTicks, partialTicks);
        this.animateSmooth(entity.sitAnimationState, DesmatosuchusAnimations.BURROW, ageInTicks, partialTicks);

        if (this.young) this.applyStatic(DesmatosuchusAnimations.BABY_TRANSFORM);

        this.animateHead(entity, this.head, netHeadYaw, headPitch);
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail.yRot = Mth.lerp(0.2F, this.tail.yRot, tailYaw * 0.25F);
	}

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}