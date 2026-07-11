package com.barl_inc.unusual_prehistory.client.models.entity.mob.ambient;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.ambient.ZhangsolvaAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.ambient.Zhangsolva;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class ZhangsolvaModel extends UP2Model<Zhangsolva> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart proboscis;
    private final ModelPart wing_left;
    private final ModelPart wing_right;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

	public ZhangsolvaModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.proboscis = this.body.getChild("proboscis");
        this.wing_left = this.body.getChild("wing_left");
        this.wing_right = this.body.getChild("wing_right");
        this.leg_left1 = this.body_main.getChild("leg_left1");
        this.leg_left2 = this.body_main.getChild("leg_left2");
        this.leg_left3 = this.body_main.getChild("leg_left3");
        this.leg_right1 = this.body_main.getChild("leg_right1");
        this.leg_right2 = this.body_main.getChild("leg_right2");
        this.leg_right3 = this.body_main.getChild("leg_right3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -4.0F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(14, 12).addBox(0.5F, -4.0F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 12).mirror().addBox(-2.5F, -4.0F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition proboscis = body.addOrReplaceChild("proboscis", CubeListBuilder.create().texOffs(20, 23).addBox(0.0F, 0.0F, -6.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, -1.0F, -4.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition wing_left = body.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.5F, -3.0F, 2.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition wing_right = body.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -3.0F, 2.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition leg_left1 = body_main.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(8, 23).addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.4F, 0.0F, 1.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition leg_left2 = body_main.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(8, 24).addBox(0.0F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.25F, 0.0F, 1.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition leg_left3 = body_main.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(8, 23).addBox(0.04F, 0.0F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.3F, 0.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition leg_right1 = body_main.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(8, 23).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.4F, 0.0F, 1.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition leg_right2 = body_main.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(8, 24).mirror().addBox(-1.0F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-0.25F, 0.0F, 1.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition leg_right3 = body_main.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(8, 23).mirror().addBox(-0.04F, 0.0F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.3F, 0.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(@NotNull Zhangsolva entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        this.animateIdleSmooth(entity.idleAnimationState, ZhangsolvaAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.flyAnimationState, ZhangsolvaAnimations.FLY, ageInTicks, partialTicks);

        float rollAmount = entity.getFlightRoll(partialTicks) / (180F / (float) Math.PI);
        float flightPitchAmount = entity.getFlightPitch(partialTicks) / (180F / (float) Math.PI);

        if (entity.isFlying()) {
            this.root.xRot += flightPitchAmount;
            this.root.zRot += rollAmount;
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}