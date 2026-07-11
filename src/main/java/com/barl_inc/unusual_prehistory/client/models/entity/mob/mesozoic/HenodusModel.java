package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.HenodusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Henodus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class HenodusModel extends UP2Model<Henodus> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart arm_left;
    private final ModelPart arm_right;
    private final ModelPart leg_left;
    private final ModelPart leg_right;

	public HenodusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body_main = this.swim_control.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.tail = this.body.getChild("tail");
        this.arm_left = this.body_main.getChild("arm_left");
        this.arm_right = this.body_main.getChild("arm_right");
        this.leg_left = this.body_main.getChild("leg_left");
        this.leg_right = this.body_main.getChild("leg_right");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 35).addBox(-9.0F, -1.8333F, -13.0F, 18.0F, 5.0F, 26.0F, new CubeDeformation(0.01F))
                .texOffs(0, 0).addBox(5.0F, -1.8333F, -16.0F, 12.0F, 3.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).mirror().addBox(-17.0F, -1.8333F, -16.0F, 12.0F, 3.0F, 32.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -0.1667F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(33, 67).addBox(-3.0F, -1.5F, -7.0F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(24, 86).addBox(-3.0F, -1.5F, -7.0F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.2F))
                .texOffs(32, 79).addBox(-4.0F, -0.5F, -10.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.6667F, -12.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(64, 66).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 66).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 3.0F, 12.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.6667F, 13.0F));

        PartDefinition arm_left = body_main.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(56, 80).addBox(-2.0F, -1.0F, -6.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(21, 111).addBox(-1.0F, 1.0F, -8.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(6.0F, 2.0F, -13.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition arm_right = body_main.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(56, 80).mirror().addBox(-2.0F, -1.0F, -6.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(21, 111).mirror().addBox(-4.0F, 1.0F, -8.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-6.0F, 2.0F, -13.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition leg_left = body_main.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(76, 80).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(1, 103).addBox(-1.0F, 1.0F, 4.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(5.0F, 2.0F, 13.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition leg_right = body_main.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(76, 80).mirror().addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(1, 103).mirror().addBox(-4.0F, 1.0F, 4.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 2.0F, 13.0F, 0.0F, -0.5236F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NotNull Henodus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble()) {
            this.animateWalk(HenodusAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        } else {
            this.animateWalk(HenodusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
        }

		this.animateIdleSmooth(entity.idleAnimationState, HenodusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.swimIdleAnimationState, HenodusAnimations.SWIM_IDLE, ageInTicks, partialTicks, limbSwingAmount);

        this.faceTarget(entity, netHeadYaw, headPitch, 2, head);

        if (entity.isInWaterOrBubble()) {
            this.root.xRot = headPitch * ((float) Math.PI / 180F);
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}