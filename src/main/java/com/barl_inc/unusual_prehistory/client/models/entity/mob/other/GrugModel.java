package com.barl_inc.unusual_prehistory.client.models.entity.mob.other;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.other.GrugAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.other.Grug;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class GrugModel extends UP2Model<Grug> {

    private final ModelPart grug;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart waist;
    private final ModelPart torso;
    private final ModelPart chest;
    private final ModelPart neck;
    private final ModelPart chadface;
    private final ModelPart arm_left1;
    private final ModelPart arm_left2;
    private final ModelPart arm_left3;
    private final ModelPart arm_right1;
    private final ModelPart arm_right2;
    private final ModelPart arm_right3;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

    public GrugModel(ModelPart root) {
        super(1.0F , 0);
        this.grug = root.getChild("grug");
        this.body_main = this.grug.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.waist = this.body.getChild("waist");
        this.torso = this.body.getChild("torso");
        this.chest = this.torso.getChild("chest");
        this.neck = this.torso.getChild("neck");
        this.chadface = this.neck.getChild("chadface");
        this.arm_left1 = this.torso.getChild("arm_left1");
        this.arm_left2 = this.arm_left1.getChild("arm_left2");
        this.arm_left3 = this.arm_left2.getChild("arm_left3");
        this.arm_right1 = this.torso.getChild("arm_right1");
        this.arm_right2 = this.arm_right1.getChild("arm_right2");
        this.arm_right3 = this.arm_right2.getChild("arm_right3");
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

        PartDefinition grug = partdefinition.addOrReplaceChild("grug", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = grug.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -18.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition waist = body.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(0, 33).addBox(-14.0F, -10.0F, -9.0F, 28.0F, 12.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(90, 84).addBox(-14.0F, 2.0F, -9.0F, 28.0F, 3.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 0.0F));

        PartDefinition chest = torso.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -16.0F, -8.0F, 32.0F, 16.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(74, 106).addBox(-20.0F, -16.0F, -8.0F, 4.0F, 8.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(0, 116).addBox(16.0F, -16.0F, -8.0F, 4.0F, 8.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(90, 64).addBox(-16.0F, 0.0F, -8.0F, 32.0F, 3.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck = torso.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(98, 17).addBox(-7.0F, -4.0F, -4.0F, 14.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, -1.0F));

        PartDefinition chadface = neck.addOrReplaceChild("chadface", CubeListBuilder.create().texOffs(117, 123).addBox(-3.0F, -5.25F, -3.0F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(95, 55).addBox(-3.0F, -7.25F, -3.0F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(189, 34).addBox(-4.0F, -3.25F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.75F, -3.5F));

        PartDefinition cube_r1 = chadface.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(120, 133).addBox(-2.0F, -4.0F, 0.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -5.75F, -3.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r2 = chadface.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(68, 137).addBox(0.0F, -1.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -5.25F, 0.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition cube_r3 = chadface.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(64, 137).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -5.25F, 0.0F, 0.0F, 0.3054F, 0.0F));

        PartDefinition arm_left1 = torso.addOrReplaceChild("arm_left1", CubeListBuilder.create().texOffs(94, 33).addBox(-1.0F, -5.0F, -5.5F, 18.0F, 11.0F, 11.0F, new CubeDeformation(0.1F)), PartPose.offset(16.0F, -10.0F, 1.5F));

        PartDefinition arm_left2 = arm_left1.addOrReplaceChild("arm_left2", CubeListBuilder.create().texOffs(98, 0).addBox(0.0F, -4.5F, -4.0F, 18.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(17.0F, -0.5F, 0.5F));

        PartDefinition arm_left3 = arm_left2.addOrReplaceChild("arm_left3", CubeListBuilder.create().texOffs(56, 134).addBox(2.0F, -1.25F, -5.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(122, 55).addBox(0.0F, -1.25F, -3.0F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(74, 92).addBox(8.0F, -1.25F, -3.0F, 2.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(18.0F, -3.25F, 0.0F));

        PartDefinition arm_right1 = torso.addOrReplaceChild("arm_right1", CubeListBuilder.create().texOffs(94, 33).mirror().addBox(-17.0F, -5.0F, -5.5F, 18.0F, 11.0F, 11.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(-16.0F, -10.0F, 1.5F));

        PartDefinition arm_right2 = arm_right1.addOrReplaceChild("arm_right2", CubeListBuilder.create().texOffs(98, 0).mirror().addBox(-18.0F, -4.5F, -4.0F, 18.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-17.0F, -0.5F, 0.5F));

        PartDefinition arm_right3 = arm_right2.addOrReplaceChild("arm_right3", CubeListBuilder.create().texOffs(56, 134).mirror().addBox(-4.0F, -1.25F, -5.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(122, 55).mirror().addBox(-8.0F, -1.25F, -3.0F, 8.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(74, 92).mirror().addBox(-10.0F, -1.25F, -3.0F, 2.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-18.0F, -3.25F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(42, 116).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(74, 131).addBox(-2.5F, 0.0F, -3.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 10.0F, 1.0F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(96, 133).addBox(-2.0F, 0.0F, -5.5F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(42, 134).addBox(-2.5F, -0.25F, -7.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(64, 134).addBox(-2.0F, 0.0F, -6.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -1.5F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(42, 116).mirror().addBox(-4.0F, 0.0F, -3.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.0F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(74, 131).mirror().addBox(-2.5F, 0.0F, -3.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 10.0F, 1.0F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(96, 133).mirror().addBox(-2.0F, 0.0F, -5.5F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(42, 134).mirror().addBox(-2.5F, -0.25F, -7.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(64, 134).mirror().addBox(-2.0F, 0.0F, -6.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 6.0F, -1.5F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }


    @Override
    public void setupAnim(@NotNull Grug entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isLeaping()) {
            if (entity.isRunning()) this.animateWalk(GrugAnimations.RUN, limbSwing, limbSwingAmount, 1, 2);
            else this.animateWalk(GrugAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 3);
        }

        this.animateIdleSmooth(entity.idleAnimationState, GrugAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.jumpAnimationState, GrugAnimations.JUMP, ageInTicks, partialTicks);

        this.animateHead(entity, this.chadface, netHeadYaw, headPitch);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.grug;
    }
}