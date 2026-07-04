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
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class AegirocassisBabyModel extends UP2Model<Aegirocassis> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart left_eye;
    private final ModelPart right_eye;
    private final ModelPart left_mandible;
    private final ModelPart left_filter;
    private final ModelPart right_mandible;
    private final ModelPart right_filter;
    private final ModelPart segment1;
    private final ModelPart left_fin1;
    private final ModelPart right_fin1;
    private final ModelPart segment2;
    private final ModelPart left_rudder1;
    private final ModelPart right_rudder1;
    private final ModelPart left_fin2;
    private final ModelPart right_fin2;
    private final ModelPart segment3;
    private final ModelPart left_rudder2;
    private final ModelPart right_rudder2;
    private final ModelPart left_fin3;
    private final ModelPart right_fin3;
    private final ModelPart segment4;
    private final ModelPart left_rudder3;
    private final ModelPart right_rudder3;
    private final ModelPart left_fin4;
    private final ModelPart right_fin4;
    private final ModelPart segment5;
    private final ModelPart left_rudder4;
    private final ModelPart right_rudder4;
    private final ModelPart left_fin5;
    private final ModelPart right_fin5;

    public AegirocassisBabyModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.head = this.body.getChild("head");
        this.left_eye = this.head.getChild("left_eye");
        this.right_eye = this.head.getChild("right_eye");
        this.left_mandible = this.head.getChild("left_mandible");
        this.left_filter = this.left_mandible.getChild("left_filter");
        this.right_mandible = this.head.getChild("right_mandible");
        this.right_filter = this.right_mandible.getChild("right_filter");
        this.segment1 = this.body.getChild("segment1");
        this.left_fin1 = this.segment1.getChild("left_fin1");
        this.right_fin1 = this.segment1.getChild("right_fin1");
        this.segment2 = this.segment1.getChild("segment2");
        this.left_rudder1 = this.segment2.getChild("left_rudder1");
        this.right_rudder1 = this.segment2.getChild("right_rudder1");
        this.left_fin2 = this.segment2.getChild("left_fin2");
        this.right_fin2 = this.segment2.getChild("right_fin2");
        this.segment3 = this.segment2.getChild("segment3");
        this.left_rudder2 = this.segment3.getChild("left_rudder2");
        this.right_rudder2 = this.segment3.getChild("right_rudder2");
        this.left_fin3 = this.segment3.getChild("left_fin3");
        this.right_fin3 = this.segment3.getChild("right_fin3");
        this.segment4 = this.segment3.getChild("segment4");
        this.left_rudder3 = this.segment4.getChild("left_rudder3");
        this.right_rudder3 = this.segment4.getChild("right_rudder3");
        this.left_fin4 = this.segment4.getChild("left_fin4");
        this.right_fin4 = this.segment4.getChild("right_fin4");
        this.segment5 = this.segment4.getChild("segment5");
        this.left_rudder4 = this.segment5.getChild("left_rudder4");
        this.right_rudder4 = this.segment5.getChild("right_rudder4");
        this.left_fin5 = this.segment5.getChild("left_fin5");
        this.right_fin5 = this.segment5.getChild("right_fin5");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 31).addBox(-8.0F, -6.0F, -13.0F, 16.0F, 8.0F, 11.0F, new CubeDeformation(0.01F))
                .texOffs(0, 0).addBox(-5.0F, -9.0F, -27.0F, 10.0F, 6.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(44, 65).addBox(-5.0F, -9.0F, -2.0F, 10.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 50).addBox(-4.0F, -6.0F, -12.0F, 8.0F, 10.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(44, 50).addBox(-5.0F, 3.0F, -15.0F, 10.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(35, 61).addBox(-5.0F, 2.0F, -15.0F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_eye = head.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(0, 74).addBox(1.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(45, 53).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -4.5F, 0.5F));

        PartDefinition right_eye = head.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(0, 74).mirror().addBox(-4.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(45, 53).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -4.75F, 0.5F));

        PartDefinition left_mandible = head.addOrReplaceChild("left_mandible", CubeListBuilder.create().texOffs(54, 13).addBox(-1.0F, -1.0F, -8.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, -13.0F));

        PartDefinition left_filter = left_mandible.addOrReplaceChild("left_filter", CubeListBuilder.create().texOffs(70, 19).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 1.0F, -7.0F));

        PartDefinition right_mandible = head.addOrReplaceChild("right_mandible", CubeListBuilder.create().texOffs(54, 13).mirror().addBox(-1.0F, -1.0F, -8.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 0.0F, -13.0F));

        PartDefinition right_filter = right_mandible.addOrReplaceChild("right_filter", CubeListBuilder.create().texOffs(70, 19).mirror().addBox(0.0F, 0.0F, -1.0F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 1.0F, -7.0F));

        PartDefinition segment1 = body.addOrReplaceChild("segment1", CubeListBuilder.create().texOffs(54, 31).addBox(-6.5F, -5.0F, 0.0F, 13.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(70, 0).addBox(-5.5F, -4.0F, 4.0F, 11.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_fin1 = segment1.addOrReplaceChild("left_fin1", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(6.5F, 5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition right_fin1 = segment1.addOrReplaceChild("right_fin1", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-6.5F, 5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment2 = segment1.addOrReplaceChild("segment2", CubeListBuilder.create().texOffs(54, 31).addBox(-6.5F, -5.0F, 0.0F, 13.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(70, 0).addBox(-5.5F, -4.0F, 4.0F, 11.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition left_rudder1 = segment2.addOrReplaceChild("left_rudder1", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(6.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_rudder1 = segment2.addOrReplaceChild("right_rudder1", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-6.5F, -5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition left_fin2 = segment2.addOrReplaceChild("left_fin2", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(6.5F, 5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition right_fin2 = segment2.addOrReplaceChild("right_fin2", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-6.5F, 5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment3 = segment2.addOrReplaceChild("segment3", CubeListBuilder.create().texOffs(88, 31).addBox(-6.5F, -5.0F, 0.0F, 13.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(70, 0).addBox(-5.5F, -4.0F, 4.0F, 11.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition left_rudder2 = segment3.addOrReplaceChild("left_rudder2", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(6.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_rudder2 = segment3.addOrReplaceChild("right_rudder2", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-6.5F, -5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition left_fin3 = segment3.addOrReplaceChild("left_fin3", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(6.5F, 5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition right_fin3 = segment3.addOrReplaceChild("right_fin3", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-6.5F, 5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment4 = segment3.addOrReplaceChild("segment4", CubeListBuilder.create().texOffs(88, 31).addBox(-6.5F, -5.0F, 0.0F, 13.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition left_rudder3 = segment4.addOrReplaceChild("left_rudder3", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(6.5F, -5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_rudder3 = segment4.addOrReplaceChild("right_rudder3", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-6.5F, -5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition left_fin4 = segment4.addOrReplaceChild("left_fin4", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(6.5F, 5.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition right_fin4 = segment4.addOrReplaceChild("right_fin4", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-6.5F, 5.0F, 2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment5 = segment4.addOrReplaceChild("segment5", CubeListBuilder.create().texOffs(91, 53).addBox(-4.5F, -3.0F, 1.0F, 9.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(93, 45).addBox(-3.5F, -2.0F, -1.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

        PartDefinition left_rudder4 = segment5.addOrReplaceChild("left_rudder4", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.5F, -3.0F, 3.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_rudder4 = segment5.addOrReplaceChild("right_rudder4", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.5F, -3.0F, 3.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition left_fin5 = segment5.addOrReplaceChild("left_fin5", CubeListBuilder.create().texOffs(54, 46).addBox(0.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.5F, 3.0F, 3.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition right_fin5 = segment5.addOrReplaceChild("right_fin5", CubeListBuilder.create().texOffs(54, 46).mirror().addBox(-6.0F, 0.0F, -2.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 3.0F, 3.0F, 0.0F, 0.0F, -0.5236F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(@NotNull Aegirocassis entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble()) {
            if (entity.getIdleState() != 2) {
                this.animateWalk(AegirocassisAnimations.BABY_MOUTH_SWIM_OVERLAY, limbSwing, limbSwingAmount, 1.5F, 1.5F);
            }
            this.animateWalk(AegirocassisAnimations.BABY_SWIM, limbSwing, limbSwingAmount, 2, 2);
        }

        this.animateIdleSmooth(entity.swimIdleAnimationState, AegirocassisAnimations.BABY_IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.mouthAnimationState, AegirocassisAnimations.BABY_MOUTH_IDLE_OVERLAY, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.flopAnimationState, AegirocassisAnimations.BABY_BEACHED, ageInTicks, partialTicks);
        this.animateSmooth(entity.eyesAnimationState, AegirocassisAnimations.BABY_EYE_OVERLAY, ageInTicks, partialTicks);
        this.animateSmooth(entity.eatAnimationState, AegirocassisAnimations.BABY_EAT_OVERLAY, ageInTicks, partialTicks);
        this.animate(entity.rollAnimationState, AegirocassisAnimations.BABY_ROLL_BLEND, ageInTicks);

        float deg = ((float) Math.PI / 180F);
        double bodyYRot = Mth.wrapDegrees(entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTicks);

        double segment1Y = (entity.getTrailTransformation(5, partialTicks)) - bodyYRot;
        double segment2Y = (entity.getTrailTransformation(10, partialTicks)) - bodyYRot - segment1Y;
        double segment3Y = (entity.getTrailTransformation(15, partialTicks)) - bodyYRot - segment2Y;
        double segment4Y = (entity.getTrailTransformation(20, partialTicks)) - bodyYRot - segment3Y;

        this.segment2.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment1Y) * 0.4F);
        this.segment3.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment2Y) * 0.3F);
        this.segment4.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment2Y) * 0.3F);
        this.segment5.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment3Y) * 0.2F);

        if (entity.isInWater()) {
            this.swim_control.xRot = headPitch * deg / 3;
        }
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}