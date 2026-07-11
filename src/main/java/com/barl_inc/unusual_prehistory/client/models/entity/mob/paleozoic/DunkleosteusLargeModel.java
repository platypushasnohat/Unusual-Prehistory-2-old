package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.DunkleosteusLargeAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Dunkleosteus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DunkleosteusLargeModel extends UP2Model<Dunkleosteus> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart upper_jaw;
    private final ModelPart jaw;
    private final ModelPart dorsal;
    private final ModelPart left_front_fin;
    private final ModelPart right_front_fin;
    private final ModelPart left_back_fin;
    private final ModelPart right_back_fin;
    private final ModelPart tail1;
    private final ModelPart tail2;

	public DunkleosteusLargeModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.head = this.body.getChild("head");
        this.upper_jaw = this.head.getChild("upper_jaw");
        this.jaw = this.head.getChild("jaw");
        this.dorsal = this.body.getChild("dorsal");
        this.left_front_fin = this.body.getChild("left_front_fin");
        this.right_front_fin = this.body.getChild("right_front_fin");
        this.left_back_fin = this.body.getChild("left_back_fin");
        this.right_back_fin = this.body.getChild("right_back_fin");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-12.5F, -18.0F, -16.0F, 25.0F, 35.0F, 32.0F, new CubeDeformation(0.2F))
                .texOffs(0, 67).addBox(-12.5F, -18.0F, -16.0F, 25.0F, 35.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, -16.0F));

        PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(114, 78).addBox(-6.5F, -7.0F, -11.0F, 13.0F, 7.0F, 14.0F, new CubeDeformation(0.06F))
                .texOffs(114, 99).addBox(-6.5F, 0.0F, -11.0F, 13.0F, 5.0F, 14.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(114, 118).addBox(-6.5F, -4.0F, -11.0F, 13.0F, 4.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(112, 136).addBox(-6.5F, 0.0F, -11.0F, 13.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition dorsal = body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(0, 146).addBox(-1.0F, -3.5F, -4.0F, 2.0F, 7.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.0F, 16.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create().texOffs(0, 134).addBox(0.0F, -1.0F, -5.0F, 18.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(56, 134).addBox(0.0F, -1.0F, -5.0F, 18.0F, 2.0F, 10.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(12.5F, 16.0F, -2.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create().texOffs(56, 134).mirror().addBox(-18.0F, -1.0F, -5.0F, 18.0F, 2.0F, 10.0F, new CubeDeformation(0.1F)).mirror(false)
                .texOffs(0, 134).mirror().addBox(-18.0F, -1.0F, -5.0F, 18.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.5F, 16.0F, -2.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition left_back_fin = body.addOrReplaceChild("left_back_fin", CubeListBuilder.create().texOffs(52, 146).addBox(0.0F, -1.0F, -1.5F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.5F, 16.0F, 14.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition right_back_fin = body.addOrReplaceChild("right_back_fin", CubeListBuilder.create().texOffs(52, 146).mirror().addBox(-6.0F, -1.0F, -1.5F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.5F, 16.0F, 14.5F, 0.0F, 0.0F, -0.7854F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(114, 45).addBox(-5.5F, -5.5F, 0.0F, 11.0F, 11.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 16.0F));

        PartDefinition fin_r1 = tail1.addOrReplaceChild("fin_r1", CubeListBuilder.create().texOffs(92, 146).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.5F, 8.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(114, 0).addBox(-1.5F, -3.0F, 0.0F, 3.0F, 8.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 19.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition fluke_r1 = tail2.addOrReplaceChild("fluke_r1", CubeListBuilder.create().texOffs(30, 146).addBox(-1.0F, -3.0F, -4.5F, 2.0F, 10.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 4.5F, -0.2618F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Dunkleosteus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isRunning()) {
            this.animateWalk(DunkleosteusLargeAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        } else {
            this.animateWalk(DunkleosteusLargeAnimations.SWIM_CHASE, limbSwing, limbSwingAmount, 1, 2);
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, DunkleosteusLargeAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.flopAnimationState, DunkleosteusLargeAnimations.FLOP, ageInTicks, partialTicks);
        this.animateSmooth(entity.attackAnimationState, DunkleosteusLargeAnimations.ATTACK_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.quirkAnimationState, DunkleosteusLargeAnimations.QUIRK_BLEND, ageInTicks, partialTicks);
        this.swim_control.xRot = headPitch * ((float) Math.PI / 180F);
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail1.yRot = Mth.lerp(0.2F, this.tail1.yRot, tailYaw * 0.3F);
        this.tail2.yRot = Mth.lerp(0.2F, this.tail2.yRot, tailYaw * 0.25F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}