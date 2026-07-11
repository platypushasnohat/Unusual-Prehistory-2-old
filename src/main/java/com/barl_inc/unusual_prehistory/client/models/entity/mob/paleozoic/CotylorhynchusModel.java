package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.CotylorhynchusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Cotylorhynchus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class CotylorhynchusModel extends UP2Model<Cotylorhynchus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw_upper;
    private final ModelPart jaw;
    private final ModelPart dewlap;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart arm_control;
    private final ModelPart arm_left;
    private final ModelPart arm_right;
    private final ModelPart leg_control;
    private final ModelPart leg_left;
    private final ModelPart leg_right;

	public CotylorhynchusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.jaw_upper = this.head.getChild("jaw_upper");
        this.jaw = this.head.getChild("jaw");
        this.dewlap = this.jaw.getChild("dewlap");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
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

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-13.5F, -11.0F, -20.0F, 27.0F, 22.0F, 41.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-1.0F, -9.0F, -20.0F));

        PartDefinition jaw_upper = head.addOrReplaceChild("jaw_upper", CubeListBuilder.create().texOffs(34, 94).addBox(-2.5F, -4.0F, -6.0F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(48, 107).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offset(1.0F, 1.0F, 0.0F));

        PartDefinition dewlap = jaw.addOrReplaceChild("dewlap", CubeListBuilder.create().texOffs(70, 107).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(32, 103).addBox(0.0F, -0.01F, -4.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 1.01F, 0.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(62, 63).addBox(-5.5F, -4.0F, -2.0F, 11.0F, 8.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 21.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 63).addBox(-2.5F, -2.7F, 0.0F, 5.0F, 5.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.5F, 13.0F));

        PartDefinition arm_control = body_main.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 4.9F, -15.0F));

        PartDefinition arm_left = arm_control.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(90, 86).addBox(0.0F, -4.9F, -3.0F, 6.0F, 15.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 102).addBox(0.0F, 10.0F, -5.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.02F)), PartPose.offset(13.5F, 0.0F, 0.0F));

        PartDefinition arm_right = arm_control.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(90, 86).mirror().addBox(-6.0F, -4.9F, -3.0F, 6.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 102).mirror().addBox(-8.0F, 10.0F, -5.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-13.5F, 0.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 13.0F));

        PartDefinition leg_left = leg_control.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(0, 94).addBox(-2.0F, 8.9F, -7.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.02F))
                .texOffs(62, 86).addBox(-2.0F, -4.0F, -4.0F, 6.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(13.5F, 0.0F, 0.0F));

        PartDefinition leg_right = leg_control.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(62, 86).mirror().addBox(-4.0F, -4.0F, -4.0F, 6.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 94).mirror().addBox(-6.0F, 8.9F, -7.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-13.5F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Cotylorhynchus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

		if (!entity.isInWaterOrBubble() && !entity.isEepy()) {
            if (entity.isRunning()) {
                this.animateWalk(CotylorhynchusAnimations.RUN, limbSwing, limbSwingAmount, 1.5F, 3);
            } else {
                this.animateWalk(CotylorhynchusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
            }
        }

        this.animateIdleSmooth(entity.idleAnimationState, CotylorhynchusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, entity.isRunning() ? 3 : 4);
        this.animateSmooth(entity.swimAnimationState, CotylorhynchusAnimations.SWIM, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, CotylorhynchusAnimations.SLEEP, ageInTicks, partialTicks);
        this.animateSmooth(entity.grazeAnimationState, CotylorhynchusAnimations.EAT_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.burpAnimationState, CotylorhynchusAnimations.GROG_BURP_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.grogAnimationState, CotylorhynchusAnimations.GROG_BREW_BLEND, ageInTicks, partialTicks);

		if (this.young) {
            this.applyStatic(CotylorhynchusAnimations.BABY_TRANSFORM);
        }

        this.faceTarget(entity, netHeadYaw, headPitch, 2, head);
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail1.yRot = Mth.lerp(0.2F, this.tail1.yRot, tailYaw * 0.25F);
        this.tail2.yRot = Mth.lerp(0.2F, this.tail2.yRot, tailYaw * 0.2F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}