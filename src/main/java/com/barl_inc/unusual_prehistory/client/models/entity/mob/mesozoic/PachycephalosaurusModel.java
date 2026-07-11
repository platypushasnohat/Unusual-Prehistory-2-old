package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.PachycephalosaurusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Pachycephalosaurus;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class PachycephalosaurusModel extends UP2Model<Pachycephalosaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart snout;
    private final ModelPart jaw;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart tail;
    private final ModelPart leg_control;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart left_leg3;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;
    private final ModelPart right_leg3;

	public PachycephalosaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.snout = this.head.getChild("snout");
        this.jaw = this.head.getChild("jaw");
        this.left_arm = this.body.getChild("left_arm");
        this.right_arm = this.body.getChild("right_arm");
        this.tail = this.body.getChild("tail");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg1 = this.leg_control.getChild("left_leg1");
        this.left_leg2 = this.left_leg1.getChild("left_leg2");
        this.left_leg3 = this.left_leg2.getChild("left_leg3");
        this.right_leg1 = this.leg_control.getChild("right_leg1");
        this.right_leg2 = this.right_leg1.getChild("right_leg2");
        this.right_leg3 = this.right_leg2.getChild("right_leg3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -14.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -5.0F, -6.0F, 9.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 43).addBox(-2.5F, -8.0F, -4.0F, 5.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(40, 22).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 8.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(43, 0).addBox(-5.0F, -5.0F, -1.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(41, 39).addBox(-5.0F, -4.0F, -1.0F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(24, 54).addBox(-4.0F, -1.0F, -4.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0025F))
                .texOffs(32, 61).addBox(-3.0F, -2.0F, -4.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(58, 48).addBox(-4.0F, 0.0F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(2.0F, -2.0F, -5.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(24, 32).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0025F))
                .texOffs(58, 53).addBox(-2.0F, 0.99F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -2.0F, -5.0F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(58, 58).addBox(-0.5F, -1.5F, -1.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 61).addBox(-0.5F, 4.5F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 2.5F, -2.5F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(58, 58).mirror().addBox(-1.5F, -1.5F, -1.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 61).mirror().addBox(-1.5F, 4.5F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, 2.5F, -2.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 6.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 4.0F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(42, 8).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(46, 48).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 2.0F));

        PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(24, 48).addBox(-2.5F, -0.01F, -4.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 8.45F, 0.5F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(42, 8).mirror().addBox(-2.5F, -2.5F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(46, 48).mirror().addBox(-1.5F, -1.5F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, 2.0F));

        PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(24, 48).mirror().addBox(-2.5F, -0.01F, -4.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 8.45F, 0.5F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Pachycephalosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

		if (!entity.isInWaterOrBubble() && !entity.isEepy() && entity.getPose() != UP2Poses.WARNING.get() && entity.getPose() != UP2Poses.RECOVERING.get()) {
            if (entity.isRunning() && entity.getAttackState() != 1) {
                this.animateWalk(PachycephalosaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.25F, 2.5F);
            } else if (entity.getAttackState() == 1) {
                this.animateWalk(PachycephalosaurusAnimations.CHARGE, limbSwing, limbSwingAmount, 1.25F, 2.5F);
            } else {
                this.animateWalk(PachycephalosaurusAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 3);
            }
        }

        this.animateIdleSmooth(entity.idleAnimationState, PachycephalosaurusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.recoverAnimationState, PachycephalosaurusAnimations.CHARGE_STUN, ageInTicks, partialTicks);
        this.animateSmooth(entity.warnAnimationState, PachycephalosaurusAnimations.WARN_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.huffAnimationState, PachycephalosaurusAnimations.HUFF_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.grazeAnimationState, PachycephalosaurusAnimations.GRAZE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.swimAnimationState, PachycephalosaurusAnimations.SWIM, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, PachycephalosaurusAnimations.SLEEP, ageInTicks, partialTicks);

        if (this.young) this.applyStatic(PachycephalosaurusAnimations.BABY_TRANSFORM);

        this.animateHead(entity, this.head, netHeadYaw, headPitch);
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail.yRot = Mth.lerp(0.3F, this.tail.yRot, tailYaw * 0.25F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}