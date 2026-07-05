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
public class AegirocassisModel extends UP2Model<Aegirocassis> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart mandible_left;
    private final ModelPart baleen_left1;
    private final ModelPart baleen_left2;
    private final ModelPart baleen_left3;
    private final ModelPart baleen_left4;
    private final ModelPart baleen_left5;
    private final ModelPart baleen_left6;
    private final ModelPart mandible_right;
    private final ModelPart baleen_right1;
    private final ModelPart baleen_right2;
    private final ModelPart baleen_right3;
    private final ModelPart baleen_right4;
    private final ModelPart baleen_right5;
    private final ModelPart baleen_right6;
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
    private final ModelPart segment6;
    private final ModelPart rudder_left5;
    private final ModelPart rudder_right5;
    private final ModelPart fin_left6;
    private final ModelPart fin_right6;

    public AegirocassisModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.head = this.body.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.mandible_left = this.head.getChild("mandible_left");
        this.baleen_left1 = this.mandible_left.getChild("baleen_left1");
        this.baleen_left2 = this.mandible_left.getChild("baleen_left2");
        this.baleen_left3 = this.mandible_left.getChild("baleen_left3");
        this.baleen_left4 = this.mandible_left.getChild("baleen_left4");
        this.baleen_left5 = this.mandible_left.getChild("baleen_left5");
        this.baleen_left6 = this.mandible_left.getChild("baleen_left6");
        this.mandible_right = this.head.getChild("mandible_right");
        this.baleen_right1 = this.mandible_right.getChild("baleen_right1");
        this.baleen_right2 = this.mandible_right.getChild("baleen_right2");
        this.baleen_right3 = this.mandible_right.getChild("baleen_right3");
        this.baleen_right4 = this.mandible_right.getChild("baleen_right4");
        this.baleen_right5 = this.mandible_right.getChild("baleen_right5");
        this.baleen_right6 = this.mandible_right.getChild("baleen_right6");
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
        this.segment6 = this.segment5.getChild("segment6");
        this.rudder_left5 = this.segment6.getChild("rudder_left5");
        this.rudder_right5 = this.segment6.getChild("rudder_right5");
        this.fin_left6 = this.segment6.getChild("fin_left6");
        this.fin_right6 = this.segment6.getChild("fin_right6");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -29.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(4, 17).addBox(-5.5F, 19.0F, -42.0F, 11.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(314, 340).addBox(-22.0F, -31.0F, -6.0F, 44.0F, 13.0F, 29.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-22.0F, -31.0F, -139.0F, 44.0F, 28.0F, 133.0F, new CubeDeformation(0.0F))
                .texOffs(30, 421).addBox(-19.0F, -16.0F, -63.01F, 38.0F, 41.0F, 50.0F, new CubeDeformation(0.0F))
                .texOffs(244, 161).addBox(-35.0F, -16.0F, -63.01F, 70.0F, 41.0F, 57.0F, new CubeDeformation(0.0F))
                .texOffs(226, 3).addBox(19.0F, 25.0F, -63.01F, 16.0F, 10.0F, 57.0F, new CubeDeformation(0.0F))
                .texOffs(226, 3).mirror().addBox(-35.0F, 25.0F, -63.01F, 16.0F, 10.0F, 57.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(1, 162).addBox(-20.0F, -19.0F, -60.0F, 40.0F, 34.0F, 81.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-6.5F, 15.0F, -43.0F, 13.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 8.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(92, 365).addBox(11.0F, -8.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(336, 424).addBox(-1.0F, -6.0F, -5.0F, 13.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(20.0F, -6.0F, 3.0F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(92, 365).mirror().addBox(-25.0F, -8.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(336, 424).mirror().addBox(-12.0F, -6.0F, -5.0F, 13.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-20.0F, -6.0F, 3.0F));

        PartDefinition mandible_left = head.addOrReplaceChild("mandible_left", CubeListBuilder.create().texOffs(354, 0).addBox(-4.0F, -5.0F, -45.0F, 9.0F, 10.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 10.0F, -63.0F));

        PartDefinition baleen_left1 = mandible_left.addOrReplaceChild("baleen_left1", CubeListBuilder.create().texOffs(382, 424).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -41.5F, 0.0F, -0.1745F, 0.0F));

        PartDefinition baleen_left2 = mandible_left.addOrReplaceChild("baleen_left2", CubeListBuilder.create().texOffs(396, 424).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -37.5F, 0.0F, -0.1745F, 0.0F));

        PartDefinition baleen_left3 = mandible_left.addOrReplaceChild("baleen_left3", CubeListBuilder.create().texOffs(0, 425).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -33.5F, 0.0F, -0.1745F, 0.0F));

        PartDefinition baleen_left4 = mandible_left.addOrReplaceChild("baleen_left4", CubeListBuilder.create().texOffs(14, 425).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -29.5F, 0.0F, -0.1745F, 0.0F));

        PartDefinition baleen_left5 = mandible_left.addOrReplaceChild("baleen_left5", CubeListBuilder.create().texOffs(28, 425).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -25.5F, 0.0F, -0.1745F, 0.0F));

        PartDefinition baleen_left6 = mandible_left.addOrReplaceChild("baleen_left6", CubeListBuilder.create().texOffs(42, 425).addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -21.5F, 0.0F, -0.1745F, 0.0F));

        PartDefinition mandible_right = head.addOrReplaceChild("mandible_right", CubeListBuilder.create().texOffs(354, 0).mirror().addBox(-5.0F, -5.0F, -45.0F, 9.0F, 10.0F, 48.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-10.0F, 10.0F, -63.0F));

        PartDefinition baleen_right1 = mandible_right.addOrReplaceChild("baleen_right1", CubeListBuilder.create().texOffs(382, 424).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -41.5F, 0.0F, 0.1745F, 0.0F));

        PartDefinition baleen_right2 = mandible_right.addOrReplaceChild("baleen_right2", CubeListBuilder.create().texOffs(396, 424).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -37.5F, 0.0F, 0.1745F, 0.0F));

        PartDefinition baleen_right3 = mandible_right.addOrReplaceChild("baleen_right3", CubeListBuilder.create().texOffs(0, 425).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -33.5F, 0.0F, 0.1745F, 0.0F));

        PartDefinition baleen_right4 = mandible_right.addOrReplaceChild("baleen_right4", CubeListBuilder.create().texOffs(14, 425).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -29.5F, 0.0F, 0.1745F, 0.0F));

        PartDefinition baleen_right5 = mandible_right.addOrReplaceChild("baleen_right5", CubeListBuilder.create().texOffs(28, 425).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -25.5F, 0.0F, 0.1745F, 0.0F));

        PartDefinition baleen_right6 = mandible_right.addOrReplaceChild("baleen_right6", CubeListBuilder.create().texOffs(42, 425).mirror().addBox(-2.5F, 0.0F, -1.0F, 5.0F, 37.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.5F, 5.0F, -21.5F, 0.0F, 0.1745F, 0.0F));

        PartDefinition segment1 = body.addOrReplaceChild("segment1", CubeListBuilder.create().texOffs(0, 293).addBox(-28.0F, -24.0F, 8.0F, 56.0F, 49.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(0, 365).addBox(-18.0F, -14.0F, -2.0F, 36.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fin_left1 = segment1.addOrReplaceChild("fin_left1", CubeListBuilder.create().texOffs(314, 382).addBox(0.0F, -2.0F, -8.5F, 34.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, 20.0F, 19.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_right1 = segment1.addOrReplaceChild("fin_right1", CubeListBuilder.create().texOffs(314, 382).mirror().addBox(-34.0F, -2.0F, -6.5F, 34.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-28.0F, 20.0F, 17.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment2 = segment1.addOrReplaceChild("segment2", CubeListBuilder.create().texOffs(0, 293).addBox(-28.0F, -24.0F, 9.0F, 56.0F, 49.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(0, 365).addBox(-18.0F, -14.0F, -1.0F, 36.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 28.0F));

        PartDefinition rudder_left1 = segment2.addOrReplaceChild("rudder_left1", CubeListBuilder.create().texOffs(314, 403).addBox(0.0F, -2.0F, -8.5F, 34.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, -19.0F, 22.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition rudder_right1 = segment2.addOrReplaceChild("rudder_right1", CubeListBuilder.create().texOffs(314, 403).mirror().addBox(-34.0F, -2.0F, -8.5F, 34.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-28.0F, -19.0F, 20.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_left2 = segment2.addOrReplaceChild("fin_left2", CubeListBuilder.create().texOffs(0, 89).addBox(0.0F, -2.0F, -8.5F, 34.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, 20.0F, 20.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_right2 = segment2.addOrReplaceChild("fin_right2", CubeListBuilder.create().texOffs(0, 111).addBox(-34.0F, -2.077F, -8.5F, 34.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-28.0F, 20.0F, 20.5F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment3 = segment2.addOrReplaceChild("segment3", CubeListBuilder.create().texOffs(158, 340).addBox(-28.0F, -24.0F, 8.0F, 56.0F, 49.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(0, 365).addBox(-18.0F, -14.0F, -2.0F, 36.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 29.0F));

        PartDefinition rudder_left2 = segment3.addOrReplaceChild("rudder_left2", CubeListBuilder.create().texOffs(416, 400).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, -19.0F, 19.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition rudder_right2 = segment3.addOrReplaceChild("rudder_right2", CubeListBuilder.create().texOffs(416, 400).mirror().addBox(-23.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-28.0F, -19.0F, 19.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_left3 = segment3.addOrReplaceChild("fin_left3", CubeListBuilder.create().texOffs(416, 418).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, 20.0F, 19.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_right3 = segment3.addOrReplaceChild("fin_right3", CubeListBuilder.create().texOffs(416, 418).mirror().addBox(-23.866F, -2.5F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-27.0F, 20.0F, 19.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment4 = segment3.addOrReplaceChild("segment4", CubeListBuilder.create().texOffs(158, 340).addBox(-28.0F, -24.0F, 8.0F, 56.0F, 49.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(0, 365).addBox(-18.0F, -14.0F, -2.0F, 36.0F, 29.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 27.0F));

        PartDefinition rudder_left3 = segment4.addOrReplaceChild("rudder_left3", CubeListBuilder.create().texOffs(416, 400).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, -19.0F, 19.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition rudder_right3 = segment4.addOrReplaceChild("rudder_right3", CubeListBuilder.create().texOffs(416, 400).mirror().addBox(-23.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-28.0F, -19.0F, 19.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_left4 = segment4.addOrReplaceChild("fin_left4", CubeListBuilder.create().texOffs(158, 293).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, 20.0F, 19.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_right4 = segment4.addOrReplaceChild("fin_right4", CubeListBuilder.create().texOffs(158, 293).mirror().addBox(-23.5F, -1.134F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-28.0F, 19.0F, 19.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment5 = segment4.addOrReplaceChild("segment5", CubeListBuilder.create().texOffs(354, 104).addBox(-18.0F, -13.0F, 3.0F, 36.0F, 26.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(278, 424).addBox(-11.0F, -6.0F, -4.0F, 22.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 31.0F));

        PartDefinition rudder_left4 = segment5.addOrReplaceChild("rudder_left4", CubeListBuilder.create().texOffs(204, 411).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -10.0F, 13.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition rudder_right4 = segment5.addOrReplaceChild("rudder_right4", CubeListBuilder.create().texOffs(204, 411).mirror().addBox(-23.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-18.0F, -10.0F, 13.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_left5 = segment5.addOrReplaceChild("fin_left5", CubeListBuilder.create().texOffs(416, 382).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, 10.0F, 13.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_right5 = segment5.addOrReplaceChild("fin_right5", CubeListBuilder.create().texOffs(416, 382).mirror().addBox(-23.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-18.0F, 10.0F, 13.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition segment6 = segment5.addOrReplaceChild("segment6", CubeListBuilder.create().texOffs(354, 58).addBox(-18.0F, -13.0F, 3.0F, 36.0F, 26.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(278, 424).addBox(-11.0F, -6.0F, -4.0F, 22.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 24.0F));

        PartDefinition rudder_left5 = segment6.addOrReplaceChild("rudder_left5", CubeListBuilder.create().texOffs(204, 411).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -10.0F, 13.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition rudder_right5 = segment6.addOrReplaceChild("rudder_right5", CubeListBuilder.create().texOffs(204, 411).mirror().addBox(-23.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-18.0F, -10.0F, 13.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_left6 = segment6.addOrReplaceChild("fin_left6", CubeListBuilder.create().texOffs(158, 311).addBox(0.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, 10.0F, 13.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_right6 = segment6.addOrReplaceChild("fin_right6", CubeListBuilder.create().texOffs(158, 311).mirror().addBox(-23.0F, -2.0F, -7.0F, 23.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-18.0F, 10.0F, 13.0F, 0.0F, 0.0F, -0.5236F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }

    @Override
    public void setupAnim(Aegirocassis entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if ((entity.isInWaterOrBubble() || entity.isLeaping()) && !entity.isTryingToFly()) {
            if (entity.getIdleState() != 2) {
                this.animateWalk(AegirocassisAnimations.MOUTH_SWIM_OVERLAY, limbSwing, limbSwingAmount, 1.5F, 2.5F);
            }
            this.animateWalk(AegirocassisAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, AegirocassisAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
        this.animateIdleSmooth(entity.mouthAnimationState, AegirocassisAnimations.MOUTH_IDLE_OVERLAY, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
        this.animateSmooth(entity.eyesAnimationState, AegirocassisAnimations.EYE_OVERLAY, ageInTicks, partialTicks);
        this.animateSmooth(entity.flopAnimationState, AegirocassisAnimations.BEACHED, ageInTicks, partialTicks);
        this.animate(entity.leapStartAnimationState, AegirocassisAnimations.LEAP_START, ageInTicks);
        this.animate(entity.leapAnimationState, AegirocassisAnimations.LEAP_HOLD, ageInTicks);
        this.animateSmooth(entity.eatAnimationState, AegirocassisAnimations.EAT_OVERLAY, ageInTicks, partialTicks);

        double bodyYRot = Mth.wrapDegrees(entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTicks);

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

        if (!entity.isTryingToFly()) {
            this.swim_control.xRot += entity.getTilt(partialTicks) * Mth.DEG_TO_RAD;
            this.swim_control.zRot += entity.getRoll(partialTicks) * Mth.DEG_TO_RAD;
        }
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}