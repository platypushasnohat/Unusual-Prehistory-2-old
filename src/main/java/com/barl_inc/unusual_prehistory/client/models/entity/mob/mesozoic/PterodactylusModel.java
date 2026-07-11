package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.PterodactylusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Pterodactylus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class PterodactylusModel extends UP2Model<Pterodactylus> {

    private final ModelPart root;
    private final ModelPart flight_control;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart eyes;
    private final ModelPart leg_control;
    private final ModelPart leg_left;
    private final ModelPart leg_right;
    private final ModelPart wing_left1;
    private final ModelPart wing_left2;
    private final ModelPart wing_left3;
    private final ModelPart wing_right1;
    private final ModelPart wing_right2;
    private final ModelPart wing_right3;

	public PterodactylusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.flight_control = this.root.getChild("flight_control");
        this.body_main = this.flight_control.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.eyes = this.body.getChild("eyes");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_left = this.leg_control.getChild("leg_left");
        this.leg_right = this.leg_control.getChild("leg_right");
        this.wing_left1 = this.body_main.getChild("wing_left1");
        this.wing_left2 = this.wing_left1.getChild("wing_left2");
        this.wing_left3 = this.wing_left2.getChild("wing_left3");
        this.wing_right1 = this.body_main.getChild("wing_right1");
        this.wing_right2 = this.wing_right1.getChild("wing_right2");
        this.wing_right3 = this.wing_right2.getChild("wing_right3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition flight_control = root.addOrReplaceChild("flight_control", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition body_main = flight_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(0.0F, 0.0F, 1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 7).addBox(-0.5F, -3.0F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(20, 11).addBox(0.0F, -6.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eyes = body.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(17, 8).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -2.5F, -0.5F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition leg_left = leg_control.addOrReplaceChild("leg_left", CubeListBuilder.create(), PartPose.offset(0.5F, 0.0F, 0.5F));

        PartDefinition cube_r1 = leg_left.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(20, 18).mirror().addBox(-0.5F, 2.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(8, 21).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition leg_right = leg_control.addOrReplaceChild("leg_right", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.0F, 0.5F));

        PartDefinition cube_r2 = leg_right.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(20, 18).addBox(-1.5F, 2.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 21).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition wing_left1 = body_main.addOrReplaceChild("wing_left1", CubeListBuilder.create().texOffs(-5, 7).addBox(0.0F, 0.0F, -2.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.5F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition wing_left2 = wing_left1.addOrReplaceChild("wing_left2", CubeListBuilder.create().texOffs(-2, 23).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, -2.5F));

        PartDefinition wing_left3 = wing_left2.addOrReplaceChild("wing_left3", CubeListBuilder.create().texOffs(-1, 2).addBox(0.0F, 0.0F, -2.0F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition wing_right1 = body_main.addOrReplaceChild("wing_right1", CubeListBuilder.create().texOffs(-5, 7).mirror().addBox(-4.0F, 0.0F, -2.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -0.5F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition wing_right2 = wing_right1.addOrReplaceChild("wing_right2", CubeListBuilder.create().texOffs(-2, 23).mirror().addBox(-1.0F, 0.0F, -2.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 0.0F, -2.5F));

        PartDefinition wing_right3 = wing_right2.addOrReplaceChild("wing_right3", CubeListBuilder.create().texOffs(-1, 2).mirror().addBox(-7.0F, 0.0F, -2.0F, 7.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 2.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Pterodactylus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        this.animateSmooth(entity.idleAnimationState, PterodactylusAnimations.GROUD_IDLE, ageInTicks, partialTicks);
        this.animateSmooth(entity.hangIdleAnimationState, PterodactylusAnimations.HANG_IDLE, ageInTicks, partialTicks);
        this.animateSmooth(entity.flyAnimationState, PterodactylusAnimations.FLY, ageInTicks, partialTicks, 1.25F);
        this.animateSmooth(entity.flyFastAnimationState, PterodactylusAnimations.FLYFAST, ageInTicks, partialTicks);
        this.animateSmooth(entity.stretchAnimationState, PterodactylusAnimations.GROUND_STRETCH_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.hangingStretchAnimationState, PterodactylusAnimations.HANG_STRETCH_BLEND, ageInTicks, partialTicks);

		float rollAmount = entity.getFlightRoll(partialTicks) / (180F / (float) Math.PI);
		float flightPitchAmount = entity.getFlightPitch(partialTicks) / (180F / (float) Math.PI);

        if (entity.isFlying()) {
            this.flight_control.xRot += flightPitchAmount;
            this.flight_control.zRot += rollAmount;
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}