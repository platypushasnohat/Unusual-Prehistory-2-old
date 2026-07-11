package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.HesperornisAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Hesperornis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused, FieldCanBeLocal")
public class HesperornisModel extends UP2Model<Hesperornis> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart swallow_control;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart wing_left;
    private final ModelPart tail;
    private final ModelPart wing_right;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

	public HesperornisModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body_main = this.swim_control.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.neck = this.body.getChild("neck");
        this.swallow_control = this.neck.getChild("swallow_control");
        this.head = this.neck.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.wing_left = this.body.getChild("wing_left");
        this.tail = this.body.getChild("tail");
        this.wing_right = this.body.getChild("wing_right");
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

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.5F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -6.0F, -11.0F, 8.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.5F, 2.5F, -0.6109F, 0.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, -11.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition swallow_control = neck.addOrReplaceChild("swallow_control", CubeListBuilder.create().texOffs(15, 19).addBox(-2.0F, -7.0F, -1.0F, 3.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -6.0F, -1.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 19).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(29, 4).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(41, 12).addBox(-1.0F, -2.0F, -9.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -13.0F, -1.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition lashes_r1 = head.addOrReplaceChild("lashes_r1", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(0.0F, -3.0F, -2.5F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -3.0F, -0.5F, 0.0F, 0.0F, -0.3491F));

        PartDefinition lashes_r2 = head.addOrReplaceChild("lashes_r2", CubeListBuilder.create().texOffs(0, 34).addBox(0.0F, -3.0F, -2.5F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(2.0F, -3.0F, -0.5F, 0.0F, 0.0F, 0.3491F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(40, 3).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -2.0F));

        PartDefinition wing_left = body.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(32, 34).addBox(0.0F, -1.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(4.0F, -3.0F, -8.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, 1.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(29, 26).mirror().addBox(-5.0F, 0.0F, 0.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition tail_r2 = tail.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(29, 26).addBox(0.0F, 0.0F, 0.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition wing_right = body.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(32, 34).mirror().addBox(-3.0F, -1.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -3.0F, -8.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 5.5F, 2.5F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(27, 30).mirror().addBox(-0.5F, -1.0F, 0.0F, 1.0F, 7.0F, 0.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(28, 39).mirror().addBox(-3.5F, 0.0F, -4.0F, 7.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(27, 30).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 7.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(28, 39).addBox(-3.5F, 0.0F, -4.0F, 7.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

	@Override
	public void setupAnim(@NotNull Hesperornis entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;
        float deg = ((float) Math.PI / 180);

        if (entity.isInWaterOrBubble()) {
            switch (entity.getSwimType()) {
                case 0 -> this.animateWalk(HesperornisAnimations.SWIM1, limbSwing, limbSwingAmount, 1, 2.5F);
                case 1 -> this.animateWalk(HesperornisAnimations.SWIM2, limbSwing, limbSwingAmount, 1, 2.5F);
                case 2 -> this.animateWalk(HesperornisAnimations.SWIM3, limbSwing, limbSwingAmount, 1, 2.5F);
                case 3 -> this.animateWalk(HesperornisAnimations.SWIM4, limbSwing, limbSwingAmount, 1, 2.5F);
            }
        } else {
            this.animateWalk(HesperornisAnimations.WALK, limbSwing, limbSwingAmount, 2, 2.5F);
        }

        this.animateIdleSmooth(entity.swimIdleAnimationState, HesperornisAnimations.SWIM_IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
        this.animateIdleSmooth(entity.idleAnimationState, HesperornisAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);

        if (entity.isInWaterOrBubble()) {
            this.swim_control.xRot = headPitch * deg;
        }

        this.faceTarget(entity, netHeadYaw, headPitch, 2, neck);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}