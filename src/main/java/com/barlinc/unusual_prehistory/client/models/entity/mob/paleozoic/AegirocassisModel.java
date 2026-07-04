package com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.paleozoic.AegirocassisAnimations;
import com.barlinc.unusual_prehistory.client.animations.entity.mob.paleozoic.AegirocassisLeapAnimations;
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
public class AegirocassisModel extends UP2Model<Aegirocassis> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart left_eye;
    private final ModelPart right_eye;
    private final ModelPart left_mandible;
    private final ModelPart left_filter1;
    private final ModelPart left_filter2;
    private final ModelPart left_filter3;
    private final ModelPart left_filter4;
    private final ModelPart left_filter5;
    private final ModelPart left_filter6;
    private final ModelPart right_mandible;
    private final ModelPart right_filter1;
    private final ModelPart right_filter2;
    private final ModelPart right_filter3;
    private final ModelPart right_filter4;
    private final ModelPart right_filter5;
    private final ModelPart right_filter6;
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
    private final ModelPart right_fin4;
    private final ModelPart left_fin4;
    private final ModelPart segment5;
    private final ModelPart left_rudder4;
    private final ModelPart right_rudder4;
    private final ModelPart left_fin5;
    private final ModelPart right_fin5;
    private final ModelPart segment6;
    private final ModelPart left_rudder5;
    private final ModelPart right_rudder5;
    private final ModelPart left_fin6;
    private final ModelPart right_fin6;

    public AegirocassisModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.head = this.body.getChild("head");
        this.left_eye = this.head.getChild("left_eye");
        this.right_eye = this.head.getChild("right_eye");
        this.left_mandible = this.head.getChild("left_mandible");
        this.left_filter1 = this.left_mandible.getChild("left_filter1");
        this.left_filter2 = this.left_mandible.getChild("left_filter2");
        this.left_filter3 = this.left_mandible.getChild("left_filter3");
        this.left_filter4 = this.left_mandible.getChild("left_filter4");
        this.left_filter5 = this.left_mandible.getChild("left_filter5");
        this.left_filter6 = this.left_mandible.getChild("left_filter6");
        this.right_mandible = this.head.getChild("right_mandible");
        this.right_filter1 = this.right_mandible.getChild("right_filter1");
        this.right_filter2 = this.right_mandible.getChild("right_filter2");
        this.right_filter3 = this.right_mandible.getChild("right_filter3");
        this.right_filter4 = this.right_mandible.getChild("right_filter4");
        this.right_filter5 = this.right_mandible.getChild("right_filter5");
        this.right_filter6 = this.right_mandible.getChild("right_filter6");
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
        this.right_fin4 = this.segment4.getChild("right_fin4");
        this.left_fin4 = this.segment4.getChild("left_fin4");
        this.segment5 = this.segment4.getChild("segment5");
        this.left_rudder4 = this.segment5.getChild("left_rudder4");
        this.right_rudder4 = this.segment5.getChild("right_rudder4");
        this.left_fin5 = this.segment5.getChild("left_fin5");
        this.right_fin5 = this.segment5.getChild("right_fin5");
        this.segment6 = this.segment5.getChild("Segment6");
        this.left_rudder5 = this.segment6.getChild("left_rudder5");
        this.right_rudder5 = this.segment6.getChild("right_rudder5");
        this.left_fin6 = this.segment6.getChild("left_fin6");
        this.right_fin6 = this.segment6.getChild("right_fin6");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -29.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 161).addBox(-20.0F, -19.0F, -61.0F, 40.0F, 50.0F, 82.0F, new CubeDeformation(0.0F))
                .texOffs(244, 259).addBox(-23.0F, 26.0F, -76.0F, 46.0F, 11.0F, 70.0F, new CubeDeformation(0.0F))
                .texOffs(354, 150).addBox(-23.0F, 22.0F, -76.0F, 46.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(244, 161).addBox(-35.0F, -19.0F, -63.01F, 70.0F, 41.0F, 57.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-22.0F, -32.0F, -139.0F, 44.0F, 28.0F, 133.0F, new CubeDeformation(0.0F))
                .texOffs(314, 340).addBox(-22.0F, -32.0F, -6.0F, 44.0F, 13.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 8.0F));

        PartDefinition left_eye = head.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(92, 365).addBox(11.0F, -8.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(336, 424).addBox(-1.0F, -6.0F, -5.0F, 13.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(20.0F, -6.0F, 3.0F));

        PartDefinition right_eye = head.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(92, 365).mirror().addBox(-25.0F, -8.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(336, 424).mirror().addBox(-12.0F, -6.0F, -5.0F, 13.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-20.0F, -6.0F, 3.0F));

        PartDefinition left_mandible = head.addOrReplaceChild("left_mandible", CubeListBuilder.create().texOffs(354, 0).addBox(-4.0F, -5.0F, -45.0F, 9.0F, 10.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 10.0F, -63.0F));

        PartDefinition left_filter1 = left_mandible.addOrReplaceChild("left_filter1", CubeListBuilder.create().texOffs(382, 424).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -41.5F, 0.0F, -0.1745F, 0.0F));

        PartDefinition left_filter2 = left_mandible.addOrReplaceChild("left_filter2", CubeListBuilder.create().texOffs(396, 424).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -37.5F, 0.0F, -0.1745F, 0.0F));

        PartDefinition left_filter3 = left_mandible.addOrReplaceChild("left_filter3", CubeListBuilder.create().texOffs(0, 425).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -33.5F, 0.0F, -0.1745F, 0.0F));

        PartDefinition left_filter4 = left_mandible.addOrReplaceChild("left_filter4", CubeListBuilder.create().texOffs(14, 425).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -29.5F, 0.0F, -0.1745F, 0.0F));

        PartDefinition left_filter5 = left_mandible.addOrReplaceChild("left_filter5", CubeListBuilder.create().texOffs(28, 425).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -25.5F, 0.0F, -0.1745F, 0.0F));

        PartDefinition left_filter6 = left_mandible.addOrReplaceChild("left_filter6", CubeListBuilder.create().texOffs(42, 425).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -21.5F, 0.0F, -0.1745F, 0.0F));

        PartDefinition right_mandible = head.addOrReplaceChild("right_mandible", CubeListBuilder.create().texOffs(354, 0).mirror().addBox(-5.0F, -5.0F, -45.0F, 9.0F, 10.0F, 48.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-10.0F, 10.0F, -63.0F));

        PartDefinition right_filter1 = right_mandible.addOrReplaceChild("right_filter1", CubeListBuilder.create().texOffs(382, 424).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -41.5F, 0.0F, 0.1745F, 0.0F));

        PartDefinition right_filter2 = right_mandible.addOrReplaceChild("right_filter2", CubeListBuilder.create().texOffs(396, 424).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -37.5F, 0.0F, 0.1745F, 0.0F));

        PartDefinition right_filter3 = right_mandible.addOrReplaceChild("right_filter3", CubeListBuilder.create().texOffs(0, 425).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -33.5F, 0.0F, 0.1745F, 0.0F));

        PartDefinition right_filter4 = right_mandible.addOrReplaceChild("right_filter4", CubeListBuilder.create().texOffs(14, 425).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -29.5F, 0.0F, 0.1745F, 0.0F));

        PartDefinition right_filter5 = right_mandible.addOrReplaceChild("right_filter5", CubeListBuilder.create().texOffs(28, 425).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -25.5F, 0.0F, 0.1745F, 0.0F));

        PartDefinition right_filter6 = right_mandible.addOrReplaceChild("right_filter6", CubeListBuilder.create().texOffs(42, 425).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -21.5F, 0.0F, 0.1745F, 0.0F));

        PartDefinition segment1 = body.addOrReplaceChild("segment1", CubeListBuilder.create().texOffs(0, 293).addBox(-28.0F, -24.0F, 8.0F, 56.0F, 49.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(0, 365).addBox(-18.0F, -14.0F, -2.0F, 36.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_fin1 = segment1.addOrReplaceChild("left_fin1", CubeListBuilder.create().texOffs(314, 382).addBox(0.0F, -2.0F, -8.5F, 34.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, 20.0F, 19.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition right_fin1 = segment1.addOrReplaceChild("right_fin1", CubeListBuilder.create().texOffs(314, 382).mirror().addBox(-34.0F, -2.0F, -6.5F, 34.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-28.0F, 20.0F, 17.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment2 = segment1.addOrReplaceChild("segment2", CubeListBuilder.create().texOffs(0, 293).addBox(-28.0F, -24.0F, 9.0F, 56.0F, 49.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(0, 365).addBox(-18.0F, -14.0F, -1.0F, 36.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 28.0F));

        PartDefinition left_rudder1 = segment2.addOrReplaceChild("left_rudder1", CubeListBuilder.create().texOffs(314, 403).addBox(0.0F, -2.0F, -8.5F, 34.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, -19.0F, 22.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_rudder1 = segment2.addOrReplaceChild("right_rudder1", CubeListBuilder.create().texOffs(314, 403).mirror().addBox(-34.0F, -2.0F, -8.5F, 34.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-28.0F, -19.0F, 20.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition left_fin2 = segment2.addOrReplaceChild("left_fin2", CubeListBuilder.create().texOffs(0, 404).addBox(0.0F, -2.0F, -8.5F, 34.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, 20.0F, 20.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition right_fin2 = segment2.addOrReplaceChild("right_fin2", CubeListBuilder.create().texOffs(102, 411).addBox(-34.0F, -2.077F, -8.5F, 34.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-28.0F, 20.0F, 20.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment3 = segment2.addOrReplaceChild("segment3", CubeListBuilder.create().texOffs(158, 340).addBox(-28.0F, -24.0F, 8.0F, 56.0F, 49.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(0, 365).addBox(-18.0F, -14.0F, -2.0F, 36.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 29.0F));

        PartDefinition left_rudder2 = segment3.addOrReplaceChild("left_rudder2", CubeListBuilder.create().texOffs(416, 400).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, -19.0F, 19.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_rudder2 = segment3.addOrReplaceChild("right_rudder2", CubeListBuilder.create().texOffs(416, 400).mirror().addBox(-23.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-28.0F, -19.0F, 19.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition left_fin3 = segment3.addOrReplaceChild("left_fin3", CubeListBuilder.create().texOffs(416, 418).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, 20.0F, 19.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition right_fin3 = segment3.addOrReplaceChild("right_fin3", CubeListBuilder.create().texOffs(416, 418).mirror().addBox(-23.866F, -2.5F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-27.0F, 20.0F, 19.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment4 = segment3.addOrReplaceChild("segment4", CubeListBuilder.create().texOffs(158, 340).addBox(-28.0F, -24.0F, 8.0F, 56.0F, 49.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(0, 365).addBox(-18.0F, -14.0F, -2.0F, 36.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 27.0F));

        PartDefinition left_rudder3 = segment4.addOrReplaceChild("left_rudder3", CubeListBuilder.create().texOffs(416, 400).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, -19.0F, 19.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_rudder3 = segment4.addOrReplaceChild("right_rudder3", CubeListBuilder.create().texOffs(416, 400).mirror().addBox(-23.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-28.0F, -19.0F, 19.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition right_fin4 = segment4.addOrReplaceChild("right_fin4", CubeListBuilder.create().texOffs(158, 293).mirror().addBox(-23.5F, -1.134F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-28.0F, 19.0F, 19.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition left_fin4 = segment4.addOrReplaceChild("left_fin4", CubeListBuilder.create().texOffs(158, 293).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, 20.0F, 19.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition segment5 = segment4.addOrReplaceChild("segment5", CubeListBuilder.create().texOffs(354, 104).addBox(-18.0F, -13.0F, 3.0F, 36.0F, 26.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(278, 424).addBox(-11.0F, -6.0F, -4.0F, 22.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 31.0F));

        PartDefinition left_rudder4 = segment5.addOrReplaceChild("left_rudder4", CubeListBuilder.create().texOffs(204, 411).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -10.0F, 13.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_rudder4 = segment5.addOrReplaceChild("right_rudder4", CubeListBuilder.create().texOffs(204, 411).mirror().addBox(-23.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-18.0F, -10.0F, 13.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition left_fin5 = segment5.addOrReplaceChild("left_fin5", CubeListBuilder.create().texOffs(416, 382).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, 10.0F, 13.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition right_fin5 = segment5.addOrReplaceChild("right_fin5", CubeListBuilder.create().texOffs(416, 382).mirror().addBox(-23.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-18.0F, 10.0F, 13.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition Segment6 = segment5.addOrReplaceChild("Segment6", CubeListBuilder.create().texOffs(354, 58).addBox(-18.0F, -13.0F, 3.0F, 36.0F, 26.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(278, 424).addBox(-11.0F, -6.0F, -4.0F, 22.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 24.0F));

        PartDefinition left_rudder5 = Segment6.addOrReplaceChild("left_rudder5", CubeListBuilder.create().texOffs(204, 411).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -10.0F, 13.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_rudder5 = Segment6.addOrReplaceChild("right_rudder5", CubeListBuilder.create().texOffs(204, 411).mirror().addBox(-23.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-18.0F, -10.0F, 13.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition left_fin6 = Segment6.addOrReplaceChild("left_fin6", CubeListBuilder.create().texOffs(158, 311).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, 10.0F, 13.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition right_fin6 = Segment6.addOrReplaceChild("right_fin6", CubeListBuilder.create().texOffs(158, 311).mirror().addBox(-23.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-18.0F, 10.0F, 13.0F, 0.0F, 0.0F, -0.5236F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }

    @Override
    public void setupAnim(@NotNull Aegirocassis entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float partialTicks = ageInTicks - entity.tickCount;
        float deg = ((float) Math.PI / 180F);
        double bodyYRot = Mth.wrapDegrees(entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTicks);

        if ((entity.isInWaterOrBubble() || entity.isLeaping()) && !entity.isTryingToFly()) {
            if (entity.getIdleState() != 2) {
                this.animateWalk(AegirocassisAnimations.MOUTH_SWIM_OVERLAY, limbSwing, limbSwingAmount, 1.5F, 1.5F);
            }
            this.animateWalk(AegirocassisAnimations.SWIM, limbSwing, limbSwingAmount, 2, 2);
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, AegirocassisAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.mouthAnimationState, AegirocassisAnimations.MOUTH_IDLE_OVERLAY, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.eyesAnimationState, AegirocassisAnimations.EYE_OVERLAY, ageInTicks, partialTicks);
        this.animateSmooth(entity.flopAnimationState, AegirocassisAnimations.BEACHED, ageInTicks, partialTicks);
        this.animate(entity.leapStartAnimationState, AegirocassisLeapAnimations.LEAP_START, ageInTicks);
        this.animate(entity.leapAnimationState, AegirocassisLeapAnimations.LEAP_HOLD, ageInTicks);
        this.animateSmooth(entity.eatAnimationState, AegirocassisAnimations.EAT_OVERLAY, ageInTicks, partialTicks);
        this.animate(entity.rollAnimationState, AegirocassisAnimations.ROLL_BLEND, ageInTicks);

        double segment1Y = (entity.getTrailTransformation(5, partialTicks)) - bodyYRot;
        double segment2Y = (entity.getTrailTransformation(10, partialTicks)) - bodyYRot - segment1Y;
        double segment3Y = (entity.getTrailTransformation(15, partialTicks)) - bodyYRot - segment2Y;
        double segment4Y = (entity.getTrailTransformation(20, partialTicks)) - bodyYRot - segment3Y;
        double segment5Y = (entity.getTrailTransformation(25, partialTicks)) - bodyYRot - segment4Y;

        this.segment2.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment1Y) * 0.4F);
        this.segment3.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment2Y) * 0.3F);
        this.segment4.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment2Y) * 0.3F);
        this.segment5.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment3Y) * 0.2F);
        this.segment6.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment4Y) * 0.1F);

        if (entity.isInWater() && !entity.isTryingToFly()) {
            this.swim_control.xRot = headPitch * deg / 3;
        }
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}