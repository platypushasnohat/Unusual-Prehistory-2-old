package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.ConcavenatorAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Concavenator;
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
public class ConcavenatorModel extends UP2Model<Concavenator> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body_main;
    private final ModelPart body_upper;
    private final ModelPart body;
    private final ModelPart armor_body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart jaw;
    private final ModelPart armor_jaw;
    private final ModelPart fin;
    private final ModelPart armor_fin;
    private final ModelPart tail1;
    private final ModelPart armor_tail1;
    private final ModelPart tail2;
    private final ModelPart armor_tail2;
    private final ModelPart arm_left1;
    private final ModelPart armor_hand_left;
    private final ModelPart claw_left1;
    private final ModelPart claw_left2;
    private final ModelPart claw_left3;
    private final ModelPart arm_right1;
    private final ModelPart armor_hand_right;
    private final ModelPart claw_right1;
    private final ModelPart claw_right2;
    private final ModelPart claw_right3;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

    public ConcavenatorModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body_main = this.swim_control.getChild("body_main");
        this.body_upper = this.body_main.getChild("body_upper");
        this.body = this.body_upper.getChild("body");
        this.armor_body = this.body.getChild("armor_body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.jaw = this.head.getChild("jaw");
        this.armor_jaw = this.jaw.getChild("armor_jaw");
        this.fin = this.body.getChild("fin");
        this.armor_fin = this.fin.getChild("armor_fin");
        this.tail1 = this.body.getChild("tail1");
        this.armor_tail1 = this.tail1.getChild("armor_tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.armor_tail2 = this.tail2.getChild("armor_tail2");
        this.arm_left1 = this.body_upper.getChild("arm_left1");
        this.armor_hand_left = this.arm_left1.getChild("armor_hand_left");
        this.claw_left1 = this.armor_hand_left.getChild("claw_left1");
        this.claw_left2 = this.armor_hand_left.getChild("claw_left2");
        this.claw_left3 = this.armor_hand_left.getChild("claw_left3");
        this.arm_right1 = this.body_upper.getChild("arm_right1");
        this.armor_hand_right = this.arm_right1.getChild("armor_hand_right");
        this.claw_right1 = this.armor_hand_right.getChild("claw_right1");
        this.claw_right2 = this.armor_hand_right.getChild("claw_right2");
        this.claw_right3 = this.armor_hand_right.getChild("claw_right3");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_left1 = this.leg_control.getChild("leg_left1");
        this.leg_left2 = this.leg_left1.getChild("leg_left2");
        this.leg_left3 = this.leg_left2.getChild("leg_left3");
        this.leg_right1 = this.leg_control.getChild("leg_right1");
        this.leg_right2 = this.leg_right1.getChild("leg_right2");
        this.leg_right3 = this.leg_right2.getChild("leg_right3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -25.0F, 0.0F));

        PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 10.0F));

        PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = body_upper.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 49).addBox(-6.0F, -13.0F, -22.0F, 12.0F, 15.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition armor_body = body.addOrReplaceChild("armor_body", CubeListBuilder.create().texOffs(72, 49).addBox(-6.0F, -13.0F, -22.0F, 12.0F, 15.0F, 24.0F, new CubeDeformation(0.2F))
                .texOffs(82, 146).addBox(0.0F, -15.0F, -14.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(60, 88).addBox(-2.5F, -6.0F, -13.0F, 5.0F, 12.0F, 16.0F, new CubeDeformation(-0.25F))
                .texOffs(102, 88).addBox(0.0F, -8.75F, -13.0F, 0.0F, 7.0F, 18.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -9.0F, -19.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(60, 116).addBox(-3.0F, -6.0F, -17.0F, 6.0F, 8.0F, 9.0F, new CubeDeformation(0.01F))
                .texOffs(136, 113).addBox(-3.0F, -6.0F, -8.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(64, 146).addBox(-3.0F, -7.0F, -8.0F, 0.0F, 1.0F, 9.0F, new CubeDeformation(0.0025F))
                .texOffs(64, 146).addBox(3.0F, -7.0F, -8.0F, 0.0F, 1.0F, 9.0F, new CubeDeformation(0.0025F))
                .texOffs(144, 43).addBox(-3.0F, 1.75F, -17.0F, 6.0F, 1.0F, 9.0F, new CubeDeformation(-0.2F))
                .texOffs(34, 122).addBox(-3.0F, 0.0F, -6.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(-0.2F))
                .texOffs(118, 147).addBox(3.0F, -6.0F, -8.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(58, 150).addBox(3.0F, -5.0F, -8.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(90, 128).addBox(3.0F, -4.0F, -12.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(90, 128).mirror().addBox(-4.0F, -4.0F, -12.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(58, 150).mirror().addBox(-4.0F, -5.0F, -8.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(118, 147).mirror().addBox(-4.0F, -6.0F, -8.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, -9.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(156, 118).addBox(0.0F, -7.0F, -5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(2.0F, 2.0F, 0.0F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(156, 118).mirror().addBox(-1.0F, -7.0F, -5.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-2.0F, 2.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(138, 98).addBox(-3.0F, 0.0F, -6.0F, 6.0F, 5.0F, 8.0F, new CubeDeformation(0.1F))
                .texOffs(64, 133).addBox(-3.0F, 1.0F, -15.0F, 6.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

        PartDefinition armor_jaw = jaw.addOrReplaceChild("armor_jaw", CubeListBuilder.create().texOffs(118, 134).addBox(-3.5F, -2.0F, -7.0F, 7.0F, 5.0F, 8.0F, new CubeDeformation(0.2F))
                .texOffs(0, 122).addBox(-3.5F, 0.0F, -17.0F, 7.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(136, 127).addBox(-3.5F, 3.0F, -17.0F, 7.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(138, 88).addBox(-3.5F, -1.0F, -17.0F, 7.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 1.0F));

        PartDefinition fin = body.addOrReplaceChild("fin", CubeListBuilder.create().texOffs(16, 146).addBox(0.0F, -9.0F, 0.0F, 0.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, -6.0F));

        PartDefinition armor_fin = fin.addOrReplaceChild("armor_fin", CubeListBuilder.create().texOffs(140, 0).addBox(-1.0F, -2.0F, -9.0F, 2.0F, 14.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(90, 116).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 9.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(96, 0).addBox(-3.5F, -4.0F, 0.0F, 7.0F, 9.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 2.0F));

        PartDefinition armor_tail1 = tail1.addOrReplaceChild("armor_tail1", CubeListBuilder.create().texOffs(96, 24).addBox(-3.5F, -1.0F, -8.0F, 7.0F, 9.0F, 15.0F, new CubeDeformation(0.2F))
                .texOffs(144, 66).addBox(0.0F, -5.0F, -5.0F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -3.0F, 8.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.0F, 0.0F, 5.0F, 6.0F, 43.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 15.0F));

        PartDefinition armor_tail2 = tail2.addOrReplaceChild("armor_tail2", CubeListBuilder.create().texOffs(0, 88).addBox(0.0F, -6.0F, -1.0F, 0.0F, 4.0F, 30.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -1.0F, 2.0F));

        PartDefinition arm_left1 = body_upper.addOrReplaceChild("arm_left1", CubeListBuilder.create().texOffs(0, 146).addBox(-1.5F, -2.0F, -2.5F, 3.0F, 13.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(136, 147).addBox(-1.5F, 6.0F, -7.5F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(96, 134).addBox(1.49F, 2.0F, -5.5F, 0.0F, 12.0F, 11.0F, new CubeDeformation(0.0025F))
                .texOffs(32, 150).addBox(-1.5F, 6.0F, -9.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 150).addBox(-1.5F, 8.0F, -9.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 150).addBox(-1.5F, 10.0F, -9.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.0F, -17.5F));

        PartDefinition armor_hand_left = arm_left1.addOrReplaceChild("armor_hand_left", CubeListBuilder.create().texOffs(34, 133).addBox(-1.0F, -3.0F, -6.0F, 3.0F, 5.0F, 12.0F, new CubeDeformation(0.1F)), PartPose.offset(-0.5F, 9.0F, -3.5F));

        PartDefinition claw_left1 = armor_hand_left.addOrReplaceChild("claw_left1", CubeListBuilder.create().texOffs(144, 53).addBox(-1.0F, -1.5F, -8.0F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.1F))
                .texOffs(148, 141).addBox(-4.0F, -1.5F, -8.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(2.0F, -0.5F, -4.0F));

        PartDefinition claw_left2 = armor_hand_left.addOrReplaceChild("claw_left2", CubeListBuilder.create().texOffs(148, 133).addBox(-1.0F, -1.0F, -5.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.1F))
                .texOffs(50, 150).addBox(-4.0F, -1.0F, -5.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(1.0F, -3.0F, -4.0F));

        PartDefinition claw_left3 = armor_hand_left.addOrReplaceChild("claw_left3", CubeListBuilder.create().texOffs(148, 133).addBox(-1.0F, -1.0F, -5.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.1F))
                .texOffs(50, 150).addBox(-4.0F, -1.0F, -5.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(1.0F, 2.0F, -4.0F));

        PartDefinition arm_right1 = body_upper.addOrReplaceChild("arm_right1", CubeListBuilder.create().texOffs(0, 146).mirror().addBox(-1.5F, -2.0F, -2.5F, 3.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(136, 147).mirror().addBox(-1.5F, 6.0F, -7.5F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(96, 134).mirror().addBox(-1.49F, 2.0F, -5.5F, 0.0F, 12.0F, 11.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(32, 150).mirror().addBox(-1.5F, 6.0F, -9.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(32, 150).mirror().addBox(-1.5F, 8.0F, -9.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(32, 150).mirror().addBox(-1.5F, 10.0F, -9.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, 0.0F, -17.5F));

        PartDefinition armor_hand_right = arm_right1.addOrReplaceChild("armor_hand_right", CubeListBuilder.create().texOffs(34, 133).mirror().addBox(-2.0F, -3.0F, -6.0F, 3.0F, 5.0F, 12.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(0.5F, 9.0F, -3.5F));

        PartDefinition claw_right1 = armor_hand_right.addOrReplaceChild("claw_right1", CubeListBuilder.create().texOffs(144, 53).mirror().addBox(-2.0F, -1.5F, -8.0F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.1F)).mirror(false)
                .texOffs(148, 141).mirror().addBox(1.0F, -1.5F, -8.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(-2.0F, -0.5F, -4.0F));

        PartDefinition claw_right2 = armor_hand_right.addOrReplaceChild("claw_right2", CubeListBuilder.create().texOffs(148, 133).mirror().addBox(-2.0F, -1.0F, -5.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.1F)).mirror(false)
                .texOffs(50, 150).mirror().addBox(1.0F, -1.0F, -5.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(-1.0F, -3.0F, -4.0F));

        PartDefinition claw_right3 = armor_hand_right.addOrReplaceChild("claw_right3", CubeListBuilder.create().texOffs(148, 133).mirror().addBox(-2.0F, -1.0F, -5.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.1F)).mirror(false)
                .texOffs(50, 150).mirror().addBox(1.0F, -1.0F, -5.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(-1.0F, 2.0F, -4.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(102, 113).addBox(-3.5F, -4.0F, -5.0F, 7.0F, 11.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(140, 22).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 4.0F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(0, 135).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(42, 150).addBox(-4.0F, 0.0F, -6.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(42, 150).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(42, 150).addBox(2.0F, 0.0F, -6.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(144, 81).addBox(-4.0F, 0.0F, -9.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(144, 81).addBox(2.0F, 0.0F, -9.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(144, 81).addBox(-1.0F, 0.0F, -9.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(102, 113).mirror().addBox(-3.5F, -4.0F, -5.0F, 7.0F, 11.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.5F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(140, 22).mirror().addBox(-2.0F, -3.0F, -3.0F, 4.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 4.0F, 4.0F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(0, 135).mirror().addBox(-4.0F, 0.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(42, 150).mirror().addBox(2.0F, 0.0F, -6.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(42, 150).mirror().addBox(-1.0F, 0.0F, -6.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(42, 150).mirror().addBox(-4.0F, 0.0F, -6.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(144, 81).mirror().addBox(2.0F, 0.0F, -9.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(144, 81).mirror().addBox(-4.0F, 0.0F, -9.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(144, 81).mirror().addBox(-1.0F, 0.0F, -9.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(@NotNull Concavenator entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isSwitchingToSandSwim() && !entity.isEepy() && !entity.isSitting() && !entity.isInWaterOrBubble() && entity.getPose() != UP2Poses.CHARGING.get() && entity.getPose() != UP2Poses.KICKING.get()) {
            if (entity.isSandSwimming()) {
                this.animateWalk(ConcavenatorAnimations.SAND_DIVE, limbSwing, limbSwingAmount, 1.4F, 2.8F);
            } else {
                if (entity.isRunning()) {
                    this.animateWalk(ConcavenatorAnimations.RUN, limbSwing, limbSwingAmount, 1.4F, 2.8F);
                } else {
                    this.animateWalk(ConcavenatorAnimations.WALK, limbSwing, limbSwingAmount, 1.4F, 2.8F);
                }
            }
        }

        this.animateIdleSmooth(entity.idleAnimationState, ConcavenatorAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.8F);
        this.animateIdleSmooth(entity.sandSwimIdleAnimationState, ConcavenatorAnimations.SAND_IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.8F);
        this.animateSmooth(entity.sandSwimStartAnimationState, ConcavenatorAnimations.DIVE_START, ageInTicks, partialTicks);
        this.animateSmooth(entity.sandSwimEndAnimationState, ConcavenatorAnimations.DIVE_END, ageInTicks, partialTicks);
        this.animateSmooth(entity.swimAnimationState, ConcavenatorAnimations.SWIM, ageInTicks, partialTicks);
        this.animateSmooth(entity.attackAnimationState, ConcavenatorAnimations.BITE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.diveAttackAnimationState, ConcavenatorAnimations.DIVE_ATTACK, ageInTicks, partialTicks);
        this.animateSmooth(entity.slashAttackAnimationState, ConcavenatorAnimations.SANDSLASH, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, ConcavenatorAnimations.SLEEP, ageInTicks, partialTicks);
        this.animateSmooth(entity.sitAnimationState, ConcavenatorAnimations.SIT, ageInTicks, partialTicks);
        this.animateSmooth(entity.sandSitAnimationState, ConcavenatorAnimations.SAND_IDLE, ageInTicks, partialTicks);
        this.animateSmooth(entity.scratch1AnimationState, ConcavenatorAnimations.IDLE_SCRATCH_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.scratch2AnimationState, ConcavenatorAnimations.IDLE_SCRATCH_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.sandSnortAnimationState, ConcavenatorAnimations.SANDSNORT_BLEND, ageInTicks, partialTicks);
        this.animate(entity.eatAnimationState, ConcavenatorAnimations.EAT_BLEND, ageInTicks);

        if (this.young) this.applyStatic(ConcavenatorAnimations.BABY_TRANSFORM);

        this.faceTarget(entity, netHeadYaw, headPitch, 2, neck);
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail1.yRot = Mth.lerp(0.2F, this.tail1.yRot, tailYaw * 0.15F);
        this.tail2.yRot = Mth.lerp(0.2F, this.tail2.yRot, tailYaw * 0.1F);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}