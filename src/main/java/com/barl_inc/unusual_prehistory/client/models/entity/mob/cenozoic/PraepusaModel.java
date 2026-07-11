package com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.cenozoic.PraepusaAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Praepusa;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class PraepusaModel extends UP2Model<Praepusa> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart left_eye;
    private final ModelPart right_eye;
    private final ModelPart hair;
    private final ModelPart snout;
    private final ModelPart tail;
    private final ModelPart arm_control;
    private final ModelPart left_arm;
    private final ModelPart right_arm;

	public PraepusaModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body_main = this.swim_control.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.left_eye = this.head.getChild("left_eye");
        this.right_eye = this.head.getChild("right_eye");
        this.hair = this.head.getChild("hair");
        this.snout = this.head.getChild("snout");
        this.tail = this.body.getChild("tail");
        this.arm_control = this.body_main.getChild("arm_control");
        this.left_arm = this.arm_control.getChild("left_arm");
        this.right_arm = this.arm_control.getChild("right_arm");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, -1.0F));

        PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, 1.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -6.0F, -5.5F, 9.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(10, 40).addBox(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -5.5F));

        PartDefinition left_eye = head.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(0, 33).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(1.5F, -1.0F, -2.0F));

        PartDefinition right_eye = head.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(0, 38).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(-1.5F, -1.0F, -2.0F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -0.5F));

        PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(0, 28).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0F));

        PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(14, 24).addBox(0.0F, -2.01F, 4.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition tail_r2 = tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(18, 18).addBox(-4.0F, -2.01F, 3.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1F, 1.0F, -2.1F, 0.0F, 0.7854F, 0.0F));

        PartDefinition tail_r3 = tail.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(0, 23).addBox(-4.0F, -2.01F, 4.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -1.5F));

        PartDefinition left_arm = arm_control.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, -0.5F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 0.0F, 0.0F));

        PartDefinition right_arm = arm_control.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 18).mirror().addBox(-5.0F, -0.5F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Praepusa entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isEepy()) {
            if (entity.isInWaterOrBubble()) {
                if (entity.isRunning())
                    this.animateWalk(PraepusaAnimations.SWIM_FAST, limbSwing, limbSwingAmount, 1.5F, 3);
                else this.animateWalk(PraepusaAnimations.SWIM, limbSwing, limbSwingAmount, 2, 4);
            } else {
                if (entity.isRunning()) this.animateWalk(PraepusaAnimations.RUN, limbSwing, limbSwingAmount, 1.5F, 3);
                else this.animateWalk(PraepusaAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
            }
        }

		this.animateIdleSmooth(entity.idleAnimationState, PraepusaAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.swimIdleAnimationState, PraepusaAnimations.SWIM_IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.eepyAnimationState, PraepusaAnimations.ROLL, ageInTicks, partialTicks);
        this.animateSmooth(entity.slap1AnimationState, PraepusaAnimations.SLAP_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.slap2AnimationState, PraepusaAnimations.SLAP_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.applauseAnimationState, PraepusaAnimations.APPLAUSE, ageInTicks, partialTicks);
        this.animateSmooth(entity.loafAnimationState, PraepusaAnimations.LOAF_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.attackAnimationState, PraepusaAnimations.ATTACK_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.mitosisAnimationState, PraepusaAnimations.MITOSIS_BLEND, ageInTicks, partialTicks);
        this.animate(entity.bounceAnimationState, PraepusaAnimations.BOUNCE_BLEND, ageInTicks);

        if (this.young) this.applyStatic(PraepusaAnimations.BABY_TRANSFORM);
        if (entity.isEepy()) this.applyStatic(PraepusaAnimations.SLEEP_BLEND);

        if (entity.isInWaterOrBubble() && !entity.isEepy()) {
            this.swim_control.xRot = headPitch * ((float) Math.PI / 180F);
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}