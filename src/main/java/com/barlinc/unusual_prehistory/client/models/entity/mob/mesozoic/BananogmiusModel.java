package com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.mesozoic.BananogmiusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Bananogmius;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class BananogmiusModel extends UP2Model<Bananogmius> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart dorsal_fin;
    private final ModelPart jaw;
    private final ModelPart fin_left;
    private final ModelPart fin_right;
    private final ModelPart tail1;
    private final ModelPart tail2;

	public BananogmiusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.dorsal_fin = this.body.getChild("dorsal_fin");
        this.jaw = this.body.getChild("jaw");
        this.fin_left = this.body.getChild("fin_left");
        this.fin_right = this.body.getChild("fin_right");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, -1.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 49).addBox(-3.5F, -7.0F, -8.5F, 7.0F, 14.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(43, 20).addBox(-3.5F, -7.0F, -13.5F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(49, 32).addBox(-3.5F, -1.0F, -13.5F, 7.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(75, 36).addBox(0.0F, 7.0F, -3.5F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.02F))
                .texOffs(37, 0).addBox(0.0F, 7.0F, 0.5F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 0.5F));

        PartDefinition dorsal_fin = body.addOrReplaceChild("dorsal_fin", CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -18.0F, -8.0F, 0.0F, 20.0F, 21.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -7.0F, -1.5F, -0.1745F, 0.0F, 0.0F));

        PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(49, 51).addBox(-3.5F, -5.0F, -5.0F, 7.0F, 5.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(49, 44).addBox(-3.5F, 0.0F, -5.0F, 7.0F, 1.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 6.0F, -8.5F));

        PartDefinition fin_left = body.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(49, 16).addBox(0.0F, 0.0F, -1.5F, 14.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.5F, 3.0F, -2.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition fin_right = body.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(49, 16).mirror().addBox(-14.0F, 0.0F, -1.5F, 14.0F, 0.0F, 3.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 3.0F, -2.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(49, 60).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 8.5F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(71, 65).addBox(0.0F, -5.0F, 0.0F, 0.0F, 10.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Bananogmius entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble()) {
            this.animateWalk(BananogmiusAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 2.5F);
        }

        this.animateIdleSmooth(entity.swimIdleAnimationState, BananogmiusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
		this.animateSmooth(entity.flopAnimationState, BananogmiusAnimations.FLOP, ageInTicks, partialTicks);
		this.animateSmooth(entity.attackAnimationState, BananogmiusAnimations.SPIN, ageInTicks, partialTicks);

        this.swim_control.xRot = entity.getTilt(partialTicks) * Mth.DEG_TO_RAD;
        this.swim_control.zRot = entity.getRoll(partialTicks) * Mth.DEG_TO_RAD;
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}