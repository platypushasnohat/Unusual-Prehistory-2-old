package com.barl_inc.unusual_prehistory.client.models.entity.mob.ambient;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.ambient.SetapeditesAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.ambient.Setapedites;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class SetapeditesModel extends UP2Model<Setapedites> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart tail;

	public SetapeditesModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.tail = this.body.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 6).addBox(-2.5F, -3.0F, -3.0F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.5F, 0.0F, -4.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(-3, 15).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.025F)), PartPose.offset(0.0F, 0.0F, 3.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(@NotNull Setapedites entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isCrawling() || !entity.isInWaterOrBubble()) {
            this.animateWalk(SetapeditesAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
        } else {
            this.animateWalk(SetapeditesAnimations.SWIM, limbSwing, limbSwingAmount, 2, 4);
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, SetapeditesAnimations.SWIM, ageInTicks, partialTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.idleAnimationState, SetapeditesAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        if (entity.isInWaterOrBubble() && !entity.isCrawling()) {
            this.root.xRot = headPitch * ((float) Math.PI / 180F);
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}