package com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.paleozoic.DunkleosteusMediumAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.Dunkleosteus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DunkleosteusMediumModel extends UP2Model<Dunkleosteus> {

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

	public DunkleosteusMediumModel(ModelPart root) {
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

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(50, 30).addBox(-4.5F, -8.0F, -8.0F, 9.0F, 15.0F, 16.0F, new CubeDeformation(0.2F))
                .texOffs(0, 30).addBox(-4.5F, -8.0F, -8.0F, 9.0F, 15.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, -8.0F));

        PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(54, 0).addBox(-3.5F, -5.0F, -9.0F, 7.0F, 4.0F, 12.0F, new CubeDeformation(0.06F))
                .texOffs(46, 90).addBox(-2.5F, -5.0F, -10.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.06F))
                .texOffs(54, 16).addBox(-3.5F, -1.0F, -9.0F, 7.0F, 2.0F, 12.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(34, 61).addBox(-3.5F, 0.0F, -9.0F, 7.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(72, 61).addBox(-3.5F, 3.0F, -9.0F, 7.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition dorsal = body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(72, 72).addBox(-1.0F, -4.5F, -5.0F, 2.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 5.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create().texOffs(34, 76).addBox(0.0F, -1.0F, -3.0F, 11.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 80).mirror().addBox(-0.25F, -1.25F, -3.25F, 11.5F, 2.5F, 5.5F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(4.5F, 6.0F, -3.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create().texOffs(34, 76).mirror().addBox(-11.0F, -1.0F, -3.0F, 11.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 80).addBox(-11.25F, -1.25F, -3.25F, 11.5F, 2.5F, 5.5F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-4.5F, 6.0F, -3.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition left_back_fin = body.addOrReplaceChild("left_back_fin", CubeListBuilder.create().texOffs(46, 83).mirror().addBox(0.0F, -1.0F, -1.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.5F, 6.0F, 6.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition right_back_fin = body.addOrReplaceChild("right_back_fin", CubeListBuilder.create().texOffs(46, 83).addBox(-4.0F, -1.0F, -1.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 6.0F, 6.5F, 0.0F, 0.0F, -0.7854F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 61).addBox(-2.5F, -3.5F, 0.0F, 5.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, 8.0F));

        PartDefinition fin_r1 = tail1.addOrReplaceChild("fin_r1", CubeListBuilder.create().texOffs(12, 87).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.5F, 4.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 9.0F, 0.6981F, 0.0F, 0.0F));

        PartDefinition fluke_r1 = tail2.addOrReplaceChild("fluke_r1", CubeListBuilder.create().texOffs(32, 83).addBox(-1.0F, -3.0F, -4.5F, 2.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 4.5F, -0.2618F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Dunkleosteus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isRunning()) {
            this.animateWalk(DunkleosteusMediumAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        } else {
            this.animateWalk(DunkleosteusMediumAnimations.SWIM_CHASE, limbSwing, limbSwingAmount, 1, 2);
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, DunkleosteusMediumAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.flopAnimationState, DunkleosteusMediumAnimations.FLOP, ageInTicks, partialTicks);
        this.animateSmooth(entity.attackAnimationState, DunkleosteusMediumAnimations.ATTACK_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.quirkAnimationState, DunkleosteusMediumAnimations.QUIRK_BLEND, ageInTicks, partialTicks);
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