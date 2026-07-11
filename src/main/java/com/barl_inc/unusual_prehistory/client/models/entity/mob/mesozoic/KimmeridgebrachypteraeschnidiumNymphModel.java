package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.KimmeridgebrachypteraeschnidiumAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Kimmeridgebrachypteraeschnidium;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class KimmeridgebrachypteraeschnidiumNymphModel extends UP2Model<Kimmeridgebrachypteraeschnidium> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leg_control;
    private final ModelPart leg_front_left;
    private final ModelPart leg_front_right;
    private final ModelPart leg_middle_left;
    private final ModelPart leg_middle_right;
    private final ModelPart leg_back_left;
    private final ModelPart leg_back_right;

	public KimmeridgebrachypteraeschnidiumNymphModel(ModelPart root) {
        super(1, 0);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_front_left = this.leg_control.getChild("leg_front_left");
        this.leg_front_right = this.leg_control.getChild("leg_front_right");
        this.leg_middle_left = this.leg_control.getChild("leg_middle_left");
        this.leg_middle_right = this.leg_control.getChild("leg_middle_right");
        this.leg_back_left = this.leg_control.getChild("leg_back_left");
        this.leg_back_right = this.leg_control.getChild("leg_back_right");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 22.4F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(1, 7).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(5, 2).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(17, 9).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(17, 15).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition leg_front_left = leg_control.addOrReplaceChild("leg_front_left", CubeListBuilder.create().texOffs(1, 15).addBox(0.0F, 0.0F, -1.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.0F, 0.0F, -1.5F, 0.0F, 0.1745F, 0.1309F));

        PartDefinition leg_front_right = leg_control.addOrReplaceChild("leg_front_right", CubeListBuilder.create().texOffs(1, 15).mirror().addBox(-4.0F, 0.0F, -1.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, -1.5F, 0.0F, -0.1745F, -0.1309F));

        PartDefinition leg_middle_left = leg_control.addOrReplaceChild("leg_middle_left", CubeListBuilder.create().texOffs(16, 13).addBox(0.0F, 0.0F, -1.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.5F, 0.0F, 0.0F, 0.1309F));

        PartDefinition leg_middle_right = leg_control.addOrReplaceChild("leg_middle_right", CubeListBuilder.create().texOffs(16, 13).mirror().addBox(-4.0F, 0.0F, -1.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.5F, 0.0F, 0.0F, -0.1309F));

        PartDefinition leg_back_left = leg_control.addOrReplaceChild("leg_back_left", CubeListBuilder.create().texOffs(17, 7).addBox(0.0F, 0.0F, -0.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.0F, 0.0F, 1.5F, 0.0F, -0.1745F, 0.1309F));

        PartDefinition leg_back_right = leg_control.addOrReplaceChild("leg_back_right", CubeListBuilder.create().texOffs(17, 7).mirror().addBox(-4.0F, 0.0F, -0.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, 1.5F, 0.0F, 0.1745F, -0.1309F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Kimmeridgebrachypteraeschnidium entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        this.animateWalk(KimmeridgebrachypteraeschnidiumAnimations.BABY_WALK, limbSwing, limbSwingAmount, 1.5F, 3);
        this.animateIdleSmooth(entity.idleAnimationState, KimmeridgebrachypteraeschnidiumAnimations.BABY_IDLE, ageInTicks, partialTicks, limbSwingAmount);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}