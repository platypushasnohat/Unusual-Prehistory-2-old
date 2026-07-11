package com.barl_inc.unusual_prehistory.client.models.entity.mob.ambient;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.ambient.AmpyxAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.ambient.Ampyx;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class AmpyxModel extends UP2Model<Ampyx> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;

	public AmpyxModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 8).addBox(-2.5F, -0.5F, -2.25F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(-0.5F, -0.5F, 2.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.5F, 0.5F, -2.25F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F))
                .texOffs(18, 6).addBox(-0.5F, -0.5F, -10.25F, 1.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F))
                .texOffs(-4, 14).addBox(3.5F, 0.5F, -0.25F, 1.0F, 0.0F, 11.0F, new CubeDeformation(0.0025F))
                .texOffs(-4, 14).addBox(-4.5F, 0.5F, -0.25F, 1.0F, 0.0F, 11.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Ampyx entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isCrawling() || !entity.isInWaterOrBubble()) {
            this.animateWalk(AmpyxAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
        } else {
            this.animateWalk(AmpyxAnimations.SWIM, limbSwing, limbSwingAmount, 2, 4);
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, AmpyxAnimations.SWIM, ageInTicks, partialTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.idleAnimationState, AmpyxAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        if (entity.isInWaterOrBubble() && !entity.isCrawling()) {
            this.root.xRot = headPitch * ((float) Math.PI / 180F);
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}