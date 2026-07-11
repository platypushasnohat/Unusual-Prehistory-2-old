package com.barl_inc.unusual_prehistory.client.models.entity.mob.ambient;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.ambient.DelitzschalaAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.ambient.Delitzschala;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DelitzschalaModel extends UP2Model<Delitzschala> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart wing_left1;
    private final ModelPart wing_left2;
    private final ModelPart wing_left3;
    private final ModelPart wing_right1;
    private final ModelPart wing_right2;
    private final ModelPart wing_right3;
    private final ModelPart tail;

	public DelitzschalaModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.wing_left1 = this.body.getChild("wing_left1");
        this.wing_left2 = this.body.getChild("wing_left2");
        this.wing_left3 = this.body.getChild("wing_left3");
        this.wing_right1 = this.body.getChild("wing_right1");
        this.wing_right2 = this.body.getChild("wing_right2");
        this.wing_right3 = this.body.getChild("wing_right3");
        this.tail = this.body.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, 0.0F, -4.0F, 3.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wing_left1 = body.addOrReplaceChild("wing_left1", CubeListBuilder.create().texOffs(7, 7).addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, 0.0F, -2.0F));

        PartDefinition wing_left2 = body.addOrReplaceChild("wing_left2", CubeListBuilder.create().texOffs(6, 4).addBox(0.0F, 0.0F, -1.0F, 7.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, 0.0F, 0.0F));

        PartDefinition wing_left3 = body.addOrReplaceChild("wing_left3", CubeListBuilder.create().texOffs(5, 0).addBox(0.0F, 0.0F, -1.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, 0.0F, 2.0F));

        PartDefinition wing_right1 = body.addOrReplaceChild("wing_right1", CubeListBuilder.create().texOffs(7, 7).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, 0.0F, -2.0F));

        PartDefinition wing_right2 = body.addOrReplaceChild("wing_right2", CubeListBuilder.create().texOffs(6, 4).mirror().addBox(-7.0F, 0.0F, -1.0F, 7.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, 0.0F, 0.0F));

        PartDefinition wing_right3 = body.addOrReplaceChild("wing_right3", CubeListBuilder.create().texOffs(5, 0).mirror().addBox(-5.0F, 0.0F, -1.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, 0.0F, 2.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 9).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 4.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(@NotNull Delitzschala entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        this.animateIdleSmooth(entity.idleAnimationState, DelitzschalaAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.flyAnimationState, DelitzschalaAnimations.FLY, ageInTicks, partialTicks);

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