package com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.cenozoic.TalpanasAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Talpanas;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class TalpanasModel extends UP2Model<Talpanas> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart bill;
    private final ModelPart lowerbill;
    private final ModelPart hair;
    private final ModelPart tail;
    private final ModelPart left_wing;
    private final ModelPart right_wing;
    private final ModelPart leg_control;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

	public TalpanasModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.bill = this.head.getChild("bill");
        this.lowerbill = this.bill.getChild("lowerbill");
        this.hair = this.head.getChild("hair");
        this.tail = this.body.getChild("tail");
        this.left_wing = this.body.getChild("left_wing");
        this.right_wing = this.body.getChild("right_wing");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg = this.leg_control.getChild("left_leg");
        this.right_leg = this.leg_control.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, -5.0F, 8.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(25, 18).addBox(-2.0F, -5.0F, -2.5F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -3.5F));

        PartDefinition bill = head.addOrReplaceChild("bill", CubeListBuilder.create().texOffs(0, 4).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-2.5F, -1.0F, -5.0F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, -1.0F, -5.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -2.5F));

        PartDefinition lowerbill = bill.addOrReplaceChild("lowerbill", CubeListBuilder.create().texOffs(-5, 24).addBox(-2.5F, 0.0F, -5.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(38, 19).addBox(-1.5F, -3.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, -4.0F, -2.5F, 0.3927F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(27, 0).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 5.0F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(13, 18).addBox(0.0F, 0.0F, -1.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(13, 21).addBox(0.5F, 0.0F, 1.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -3.0F, -1.0F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(13, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 1.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(13, 21).mirror().addBox(-0.5F, 0.0F, 1.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -3.0F, -1.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(2.5F, 3.0F, 3.0F));

        PartDefinition left_leg = leg_control.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(37, 12).addBox(-0.5F, 0.0F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0025F))
                .texOffs(41, 13).addBox(-0.5F, 4.0F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(-1.0F, -1.025F, -0.5F));

        PartDefinition right_leg = leg_control.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(37, 12).mirror().addBox(-1.5F, 0.0F, -1.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(41, 13).mirror().addBox(-4.5F, 4.0F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-4.0F, -1.025F, -0.5F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Talpanas entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.onGround() && !entity.isEepy()) {
            if (entity.isRunning()) this.animateWalk(TalpanasAnimations.RUN, limbSwing, limbSwingAmount, 1, 2);
            else this.animateWalk(TalpanasAnimations.WALK, limbSwing, limbSwingAmount, 1.5F, 3);
        }

		if (this.young) this.applyStatic(TalpanasAnimations.BABY_TRANSFORM);

        this.animateIdleSmooth(entity.idleAnimationState, TalpanasAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.flapAnimationState, TalpanasAnimations.FALL, ageInTicks, partialTicks);
		this.animateSmooth(entity.peckAnimationState, TalpanasAnimations.PECK_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, TalpanasAnimations.SIT, ageInTicks, partialTicks);

        this.animateHead(entity, this.head, netHeadYaw, headPitch);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}