package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.DunkleosteusSmallAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Dunkleosteus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DunkleosteusSmallModel extends UP2Model<Dunkleosteus> {

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

	public DunkleosteusSmallModel(ModelPart root) {
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

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -2.5F, 6.0F, 8.0F, 7.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -2.0F));

        PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(20, 15).addBox(-3.0F, -3.0F, -5.5F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0025F))
                .texOffs(-4, 41).addBox(-3.0F, -1.0F, -5.5F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F))
                .texOffs(26, 0).addBox(-3.0F, -3.0F, -3.5F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(2, 47).addBox(-3.0F, 0.0F, -5.5F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(-4, 41).addBox(-3.0F, 1.0F, -5.5F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition dorsal = body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(20, 28).addBox(0.0F, -3.0F, -4.5F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -3.0F, 4.0F));

        PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create().texOffs(34, 26).addBox(0.0F, -1.0F, -1.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.0F, 4.0F, -2.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create().texOffs(34, 26).mirror().addBox(0.0F, -1.0F, -1.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 4.0F, -2.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition left_back_fin = body.addOrReplaceChild("left_back_fin", CubeListBuilder.create().texOffs(27, 9).addBox(0.0F, -1.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.0F, 4.0F, 3.5F, 0.0F, 0.0F, -0.7854F));

        PartDefinition right_back_fin = body.addOrReplaceChild("right_back_fin", CubeListBuilder.create().texOffs(27, 9).mirror().addBox(0.0F, -1.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.0F, 4.0F, 3.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(2, 17).addBox(0.0F, -6.0F, -1.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 4.5F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(38, 6).addBox(0.0F, -4.0F, 0.0F, 0.0F, 8.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 7.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Dunkleosteus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isRunning()) {
            this.animateWalk(DunkleosteusSmallAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        } else {
            this.animateWalk(DunkleosteusSmallAnimations.SWIM_CHASE, limbSwing, limbSwingAmount, 1, 2);
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, DunkleosteusSmallAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.flopAnimationState, DunkleosteusSmallAnimations.FLOP, ageInTicks, partialTicks);
        this.animateSmooth(entity.attackAnimationState, DunkleosteusSmallAnimations.ATTACK_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.quirkAnimationState, DunkleosteusSmallAnimations.QUIRK_BLEND, ageInTicks, partialTicks);
        this.swim_control.xRot = headPitch * ((float) Math.PI / 180F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}