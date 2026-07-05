package com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.paleozoic.AegirocassisAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.Aegirocassis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class AegirocassisBabyModel extends UP2Model<Aegirocassis> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart mandible_left;
    private final ModelPart baleen_left;
    private final ModelPart mandible_right;
    private final ModelPart baleen_right;
    private final ModelPart segment1;
    private final ModelPart fin_left1;
    private final ModelPart fin_right1;
    private final ModelPart segment2;
    private final ModelPart rudder_left1;
    private final ModelPart rudder_right1;
    private final ModelPart fin_left2;
    private final ModelPart fin_right2;
    private final ModelPart segment3;
    private final ModelPart rudder_left2;
    private final ModelPart rudder_right2;
    private final ModelPart fin_left3;
    private final ModelPart fin_right3;
    private final ModelPart segment4;
    private final ModelPart rudder_left3;
    private final ModelPart rudder_right3;
    private final ModelPart fin_left4;
    private final ModelPart fin_right4;
    private final ModelPart segment5;
    private final ModelPart rudder_left4;
    private final ModelPart rudder_right4;
    private final ModelPart fin_left5;
    private final ModelPart fin_right5;

    public AegirocassisBabyModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.head = this.body.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.mandible_left = this.head.getChild("mandible_left");
        this.baleen_left = this.mandible_left.getChild("baleen_left");
        this.mandible_right = this.head.getChild("mandible_right");
        this.baleen_right = this.mandible_right.getChild("baleen_right");
        this.segment1 = this.body.getChild("segment1");
        this.fin_left1 = this.segment1.getChild("fin_left1");
        this.fin_right1 = this.segment1.getChild("fin_right1");
        this.segment2 = this.segment1.getChild("segment2");
        this.rudder_left1 = this.segment2.getChild("rudder_left1");
        this.rudder_right1 = this.segment2.getChild("rudder_right1");
        this.fin_left2 = this.segment2.getChild("fin_left2");
        this.fin_right2 = this.segment2.getChild("fin_right2");
        this.segment3 = this.segment2.getChild("segment3");
        this.rudder_left2 = this.segment3.getChild("rudder_left2");
        this.rudder_right2 = this.segment3.getChild("rudder_right2");
        this.fin_left3 = this.segment3.getChild("fin_left3");
        this.fin_right3 = this.segment3.getChild("fin_right3");
        this.segment4 = this.segment3.getChild("segment4");
        this.rudder_left3 = this.segment4.getChild("rudder_left3");
        this.rudder_right3 = this.segment4.getChild("rudder_right3");
        this.fin_left4 = this.segment4.getChild("fin_left4");
        this.fin_right4 = this.segment4.getChild("fin_right4");
        this.segment5 = this.segment4.getChild("segment5");
        this.rudder_left4 = this.segment5.getChild("rudder_left4");
        this.rudder_right4 = this.segment5.getChild("rudder_right4");
        this.fin_left5 = this.segment5.getChild("fin_left5");
        this.fin_right5 = this.segment5.getChild("fin_right5");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 31).addBox(-8.0F, -6.0F, -13.0F, 16.0F, 8.0F, 11.0F, new CubeDeformation(0.01F))
                .texOffs(86, 0).addBox(4.0F, 2.0F, -13.0F, 4.0F, 4.0F, 11.0F, new CubeDeformation(0.01F))
                .texOffs(86, 0).mirror().addBox(-8.0F, 2.0F, -13.0F, 4.0F, 4.0F, 11.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(0, 0).addBox(-5.0F, -9.0F, -27.0F, 10.0F, 6.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(44, 65).addBox(-5.0F, -9.0F, -2.0F, 10.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, 3.0F, -10.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-2.5F, 3.0F, -10.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(-0.4F))
                .texOffs(0, 50).addBox(-4.0F, -6.0F, -12.0F, 8.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(0, 74).addBox(1.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(45, 53).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -4.5F, 0.5F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(0, 74).mirror().addBox(-4.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(45, 53).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -4.75F, 0.5F));

        PartDefinition mandible_left = head.addOrReplaceChild("mandible_left", CubeListBuilder.create().texOffs(54, 13).addBox(-1.0F, -1.0F, -8.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, -13.0F));

        PartDefinition baleen_left = mandible_left.addOrReplaceChild("baleen_left", CubeListBuilder.create().texOffs(70, 19).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 1.0F, -7.0F));

        PartDefinition mandible_right = head.addOrReplaceChild("mandible_right", CubeListBuilder.create().texOffs(54, 13).mirror().addBox(-1.0F, -1.0F, -8.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 0.0F, -13.0F));

        PartDefinition baleen_right = mandible_right.addOrReplaceChild("baleen_right", CubeListBuilder.create().texOffs(70, 19).mirror().addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 1.0F, -7.0F));

        PartDefinition segment1 = body.addOrReplaceChild("segment1", CubeListBuilder.create().texOffs(54, 31).addBox(-6.5F, -5.0F, 0.0F, 13.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(70, 0).addBox(-5.5F, -4.0F, 4.0F, 11.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fin_left1 = segment1.addOrReplaceChild("fin_left1", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(6.5F, 5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_right1 = segment1.addOrReplaceChild("fin_right1", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-6.5F, 5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment2 = segment1.addOrReplaceChild("segment2", CubeListBuilder.create().texOffs(54, 31).addBox(-6.5F, -5.0F, 0.0F, 13.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(70, 0).addBox(-5.5F, -4.0F, 4.0F, 11.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition rudder_left1 = segment2.addOrReplaceChild("rudder_left1", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(6.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition rudder_right1 = segment2.addOrReplaceChild("rudder_right1", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-6.5F, -5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_left2 = segment2.addOrReplaceChild("fin_left2", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(6.5F, 5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_right2 = segment2.addOrReplaceChild("fin_right2", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-6.5F, 5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment3 = segment2.addOrReplaceChild("segment3", CubeListBuilder.create().texOffs(88, 31).addBox(-6.5F, -5.0F, 0.0F, 13.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(70, 0).addBox(-5.5F, -4.0F, 4.0F, 11.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition rudder_left2 = segment3.addOrReplaceChild("rudder_left2", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(6.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition rudder_right2 = segment3.addOrReplaceChild("rudder_right2", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-6.5F, -5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_left3 = segment3.addOrReplaceChild("fin_left3", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(6.5F, 5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_right3 = segment3.addOrReplaceChild("fin_right3", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-6.5F, 5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment4 = segment3.addOrReplaceChild("segment4", CubeListBuilder.create().texOffs(88, 31).addBox(-6.5F, -5.0F, 0.0F, 13.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition rudder_left3 = segment4.addOrReplaceChild("rudder_left3", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(6.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition rudder_right3 = segment4.addOrReplaceChild("rudder_right3", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-6.5F, -5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_left4 = segment4.addOrReplaceChild("fin_left4", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(6.5F, 5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_right4 = segment4.addOrReplaceChild("fin_right4", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-6.5F, 5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment5 = segment4.addOrReplaceChild("segment5", CubeListBuilder.create().texOffs(91, 53).addBox(-4.5F, -3.0F, 1.0F, 9.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(93, 45).addBox(-3.5F, -2.0F, -1.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

        PartDefinition rudder_left4 = segment5.addOrReplaceChild("rudder_left4", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(4.5F, -3.0F, 3.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition rudder_right4 = segment5.addOrReplaceChild("rudder_right4", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-4.5F, -3.0F, 3.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_left5 = segment5.addOrReplaceChild("fin_left5", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(4.5F, 3.0F, 3.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_right5 = segment5.addOrReplaceChild("fin_right5", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 3.0F, 3.0F, 0.0F, 0.0F, -0.5236F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Aegirocassis entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble()) {
            if (entity.getIdleState() != 2) {
                this.animateWalk(AegirocassisAnimations.BABY_MOUTH_SWIM_OVERLAY, limbSwing, limbSwingAmount, 1.5F, 2.5F);
            }
            this.animateWalk(AegirocassisAnimations.BABY_SWIM, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }

        this.animateIdleSmooth(entity.swimIdleAnimationState, AegirocassisAnimations.BABY_IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
        this.animateIdleSmooth(entity.mouthAnimationState, AegirocassisAnimations.BABY_MOUTH_IDLE_OVERLAY, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
        this.animateSmooth(entity.flopAnimationState, AegirocassisAnimations.BABY_BEACHED, ageInTicks, partialTicks);
        this.animateSmooth(entity.eyesAnimationState, AegirocassisAnimations.BABY_EYE_OVERLAY, ageInTicks, partialTicks);

        double bodyYRot = Mth.wrapDegrees(entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTicks);

        double segment1Y = (entity.getTrailTransformation(5, partialTicks)) - bodyYRot;
        double segment2Y = (entity.getTrailTransformation(10, partialTicks)) - bodyYRot - segment1Y;
        double segment3Y = (entity.getTrailTransformation(15, partialTicks)) - bodyYRot - segment2Y;
        double segment4Y = (entity.getTrailTransformation(20, partialTicks)) - bodyYRot - segment3Y;

        this.segment2.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment1Y) * 0.4F);
        this.segment3.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment2Y) * 0.3F);
        this.segment4.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment2Y) * 0.3F);
        this.segment5.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment3Y) * 0.2F);

        this.swim_control.xRot += entity.getTilt(partialTicks) * Mth.DEG_TO_RAD;
        this.swim_control.zRot += entity.getRoll(partialTicks) * Mth.DEG_TO_RAD;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}