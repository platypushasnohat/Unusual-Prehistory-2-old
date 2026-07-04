package com.barlinc.unusual_prehistory.client.models.entity.mob.other;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.other.LivingOozeAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.other.LivingOoze;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class LivingOozeModel extends UP2Model<LivingOoze> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart body_squish;
    public final ModelPart core;
    public final ModelPart core_squish;

	public LivingOozeModel(ModelPart root) {
        super(1, 0);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.body_squish = this.body.getChild("body_squish");
        this.core = this.body.getChild("core");
        this.core_squish = this.core.getChild("core_squish");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body_squish = body.addOrReplaceChild("body_squish", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition core = body.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition core_squish = core.addOrReplaceChild("core_squish", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -12.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull LivingOoze entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.processingAnimationState, LivingOozeAnimations.PROCESSING, ageInTicks);
        this.animate(entity.spittingAnimationState, LivingOozeAnimations.SPIT_OUT, ageInTicks);
        this.animate(entity.cooldownAnimationState, LivingOozeAnimations.TRAUMA, ageInTicks);

        float partialTicks = ageInTicks - entity.tickCount;
        float jumpProgress = entity.getJumpProgress(partialTicks);
        float squishProgress = entity.getSquishProgress(partialTicks);
        float jiggleTime = entity.getJiggleTime(partialTicks);
        float squishJiggle = jiggleTime * (float) (1D + Math.sin(jiggleTime * Math.PI * 2.0F)) * 0.5F;
        float xzScale = 0.9F - 0.1F * jumpProgress + 0.15F * squishProgress + squishJiggle;
        float yScale = 1F + (jumpProgress / 3) - 0.5F * squishProgress * 0.03F - squishJiggle * 0.5F;
        this.root.xScale = xzScale;
        this.root.yScale = yScale;
        this.root.zScale = xzScale;
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}

    public void translateToCore(PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        this.core.translateAndRotate(poseStack);
    }
}