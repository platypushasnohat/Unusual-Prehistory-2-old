package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.ArthropleuraAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.ArthropleuraPart;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class ArthropleuraBodyModel extends UP2Model<ArthropleuraPart> {

    private final ModelPart root;
    private final ModelPart rotation_control;
    private final ModelPart segment;
    private final ModelPart segment_jiggle;
    private final ModelPart leg_control;
    private final ModelPart leg_wiggle;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_left4;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;
    private final ModelPart leg_right4;

	public ArthropleuraBodyModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.rotation_control = this.root.getChild("rotation_control");
        this.segment = this.rotation_control.getChild("segment");
        this.segment_jiggle = this.segment.getChild("segment_jiggle");
        this.leg_control = this.rotation_control.getChild("leg_control");
        this.leg_wiggle = this.leg_control.getChild("leg_wiggle");
        this.leg_left1 = this.leg_wiggle.getChild("leg_left1");
        this.leg_left2 = this.leg_wiggle.getChild("leg_left2");
        this.leg_left3 = this.leg_wiggle.getChild("leg_left3");
        this.leg_left4 = this.leg_wiggle.getChild("leg_left4");
        this.leg_right1 = this.leg_wiggle.getChild("leg_right1");
        this.leg_right2 = this.leg_wiggle.getChild("leg_right2");
        this.leg_right3 = this.leg_wiggle.getChild("leg_right3");
        this.leg_right4 = this.leg_wiggle.getChild("leg_right4");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition rotation_control = root.addOrReplaceChild("rotation_control", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition segment = rotation_control.addOrReplaceChild("segment", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -13.0F));

        PartDefinition segment_jiggle = segment.addOrReplaceChild("segment_jiggle", CubeListBuilder.create().texOffs(0, 22).addBox(-14.0F, -4.0F, 2.0F, 28.0F, 7.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-18.0F, 1.0F, 2.0F, 36.0F, 0.0F, 22.0F, new CubeDeformation(0.0025F))
                .texOffs(82, 51).addBox(-7.0F, -2.0F, -2.0F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_control = rotation_control.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition leg_wiggle = leg_control.addOrReplaceChild("leg_wiggle", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left1 = leg_wiggle.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(88, 88).addBox(-1.0F, -1.0F, 0.0F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(6.0F, 0.0F, -7.5F));

        PartDefinition leg_left2 = leg_wiggle.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(88, 88).addBox(-1.0F, -1.0F, 0.0F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(6.0F, 0.0F, -2.5F));

        PartDefinition leg_left3 = leg_wiggle.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(88, 88).addBox(-1.0F, -1.0F, 0.0F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(6.0F, 0.0F, 2.5F));

        PartDefinition leg_left4 = leg_wiggle.addOrReplaceChild("leg_left4", CubeListBuilder.create().texOffs(88, 88).addBox(-1.0F, -1.0F, 0.0F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(6.0F, 0.0F, 7.5F));

        PartDefinition leg_right1 = leg_wiggle.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(88, 88).mirror().addBox(-12.0F, -1.0F, 0.0F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-6.0F, 0.0F, -7.5F));

        PartDefinition leg_right2 = leg_wiggle.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(88, 88).mirror().addBox(-12.0F, -1.0F, 0.0F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-6.0F, 0.0F, -2.5F));

        PartDefinition leg_right3 = leg_wiggle.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(88, 88).mirror().addBox(-12.0F, -1.0F, 0.0F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-6.0F, 0.0F, 2.5F));

        PartDefinition leg_right4 = leg_wiggle.addOrReplaceChild("leg_right4", CubeListBuilder.create().texOffs(88, 88).mirror().addBox(-12.0F, -1.0F, 0.0F, 13.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-6.0F, 0.0F, 7.5F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NotNull ArthropleuraPart entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.getIndex() % 2 == 0) {
            this.animateWalk(ArthropleuraAnimations.BODY_WALK1, limbSwing, limbSwingAmount, 2, 4);
            this.animateIdleSmooth(entity.idleAnimationState, ArthropleuraAnimations.BODY_IDLE1, ageInTicks, partialTicks, limbSwingAmount, 4);
        } else {
            this.animateWalk(ArthropleuraAnimations.BODY_WALK2, limbSwing, limbSwingAmount, 2, 4);
            this.animateIdleSmooth(entity.idleAnimationState, ArthropleuraAnimations.BODY_IDLE2, ageInTicks, partialTicks, limbSwingAmount, 4);
        }
        this.look(rotation_control, rotationY, rotationX, 1.0F, 1.0F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}

    public void translateRiderToBody(PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        this.rotation_control.translateAndRotate(poseStack);
        this.segment.translateAndRotate(poseStack);
        this.segment_jiggle.translateAndRotate(poseStack);
    }
}