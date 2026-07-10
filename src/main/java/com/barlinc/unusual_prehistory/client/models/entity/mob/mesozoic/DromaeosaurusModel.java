package com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.mesozoic.DromaeosaurusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Dromaeosaurus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DromaeosaurusModel extends UP2Model<Dromaeosaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart breathing;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart jaw;
    private final ModelPart arm_left;
    private final ModelPart arm_right;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart claw_left;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;
    private final ModelPart claw_right;

	public DromaeosaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.breathing = this.body.getChild("breathing");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.jaw = this.head.getChild("jaw");
        this.arm_left = this.body.getChild("arm_left");
        this.arm_right = this.body.getChild("arm_right");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_left1 = this.leg_control.getChild("leg_left1");
        this.leg_left2 = this.leg_left1.getChild("leg_left2");
        this.leg_left3 = this.leg_left2.getChild("leg_left3");
        this.claw_left = this.leg_left3.getChild("claw_left");
        this.leg_right1 = this.leg_control.getChild("leg_right1");
        this.leg_right2 = this.leg_right1.getChild("leg_right2");
        this.leg_right3 = this.leg_right2.getChild("leg_right3");
        this.claw_right = this.leg_right3.getChild("claw_right");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 3.5F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition breathing = body.addOrReplaceChild("breathing", CubeListBuilder.create().texOffs(0, 25).addBox(-4.0F, -7.0F, -5.0F, 6.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.0F, -3.5F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(32, 35).addBox(-1.5F, -5.0F, -3.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 42).addBox(-1.5F, -10.0F, -1.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(30, 10).addBox(-1.5F, -10.01F, 3.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -8.5F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(18, 42).addBox(-2.5F, -1.01F, -4.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(33, 15).addBox(-1.5F, -1.01F, -8.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 2.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(4, 32).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(2.0F, 0.49F, -3.0F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(4, 32).mirror().addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-2.0F, 0.49F, -3.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(23, 18).addBox(-1.5F, -0.01F, -4.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -4.0F));

        PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(35, 23).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, -7).addBox(0.99F, 1.0F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(15, 19).addBox(-1.0F, 2.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(2, 28).addBox(-1.0F, 2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.0F, -6.5F));

        PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(35, 23).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, -7).mirror().addBox(-0.99F, 1.0F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(15, 19).mirror().addBox(-1.0F, 2.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(2, 28).mirror().addBox(-1.0F, 2.0F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 1.0F, -6.5F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 1.5F));

        PartDefinition tail_r1 = tail1.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-7.0F, 0.0F, 0.0F, 7.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition tail_r2 = tail1.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 7.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 15.0F));

        PartDefinition tail_r3 = tail2.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(-24, 0).mirror().addBox(-7.0F, 0.0F, 0.0F, 7.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition tail_r4 = tail2.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(-24, 0).addBox(0.0F, 0.0F, 0.0F, 7.0F, 0.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(22, 23).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(42, 29).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 2.5F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(41, 4).addBox(-1.0F, 0.09F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.9F, 1.0F));

        PartDefinition claw_left = leg_left3.addOrReplaceChild("claw_left", CubeListBuilder.create().texOffs(45, -2).addBox(0.0F, -2.91F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, -2.0F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(22, 23).mirror().addBox(-2.0F, -2.0F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(42, 29).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.0F, 2.5F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(41, 4).mirror().addBox(-3.0F, 0.09F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 6.9F, 1.0F));

        PartDefinition claw_right = leg_right3.addOrReplaceChild("claw_right", CubeListBuilder.create().texOffs(45, -2).mirror().addBox(0.0F, -2.91F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, 0.0F, -2.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Dromaeosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        this.animateWalk(DromaeosaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.25F, 2.5F);

		this.animateIdleSmooth(entity.idleAnimationState, DromaeosaurusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
		this.animateSmooth(entity.attackAnimationState, DromaeosaurusAnimations.BITE_BLEND, ageInTicks, partialTicks);
		this.animateSmooth(entity.fallAnimationState, DromaeosaurusAnimations.JUMP, ageInTicks, partialTicks);
		this.animateSmooth(entity.eepyAnimationState, DromaeosaurusAnimations.SLEEP, ageInTicks, partialTicks);

        if (this.young) {
            this.applyStatic(DromaeosaurusAnimations.BABY_TRANSFORM);
        }

        this.faceTarget(entity, netHeadYaw, headPitch, 1.5F, this.neck, this.head);
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail1.yRot += entity.getTailYaw(partialTicks) * 0.7F * Mth.DEG_TO_RAD;
        this.tail2.yRot += entity.getTailYaw(partialTicks) * 0.8F * Mth.DEG_TO_RAD;
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}