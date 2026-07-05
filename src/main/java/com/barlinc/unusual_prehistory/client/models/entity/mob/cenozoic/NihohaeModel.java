package com.barlinc.unusual_prehistory.client.models.entity.mob.cenozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.cenozoic.NihohaeAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.cenozoic.Nihohae;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class NihohaeModel extends UP2Model<Nihohae> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart jaw;
    private final ModelPart fin_left;
    private final ModelPart fin_right;
    private final ModelPart tail1;
    private final ModelPart tail2;

	public NihohaeModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.eye_left = this.body.getChild("eye_left");
        this.eye_right = this.body.getChild("eye_right");
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

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -4.5F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -7.5F, -9.0F, 10.0F, 12.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(54, 53).addBox(-1.0F, 0.5F, -21.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 56).addBox(-1.0F, 0.5F, -33.0F, 2.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(76, 74).mirror().addBox(-3.0F, 1.5F, -36.0F, 2.0F, 0.0F, 11.0F, new CubeDeformation(0.02F)).mirror(false)
                .texOffs(76, 74).addBox(1.0F, 1.5F, -36.0F, 2.0F, 0.0F, 11.0F, new CubeDeformation(0.02F))
                .texOffs(56, 19).addBox(-1.0F, 1.5F, -33.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition dorsal_r1 = body.addOrReplaceChild("dorsal_r1", CubeListBuilder.create().texOffs(28, 56).addBox(-0.5F, -7.0F, 0.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.5F, 3.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition eye_left = body.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(39, 14).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(4.5F, 2.0F, -7.0F));

        PartDefinition eye_right = body.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(39, 14).mirror().addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-4.5F, 2.0F, -7.0F));

        PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(56, 0).addBox(-1.0F, -1.0F, -25.0F, 2.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(-1.0F, 0.0F, -25.0F, 2.0F, 1.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.5F, -9.0F));

        PartDefinition fin_left = body.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(56, 13).addBox(0.0F, 0.0F, -2.5F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 3.5F, -2.5F));

        PartDefinition fin_right = body.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(56, 13).mirror().addBox(-8.0F, 0.0F, -2.5F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, 3.5F, -2.5F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(54, 38).addBox(-3.0F, -2.5F, 0.0F, 6.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 9.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(54, 30).addBox(-7.0F, -0.5F, -1.5F, 14.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.5F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Nihohae entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble() && !entity.isLeaping()) {
            this.animateWalk(NihohaeAnimations.SWIM, limbSwing, limbSwingAmount, 1.25F, 2.5F);
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, NihohaeAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
		this.animateSmooth(entity.flopAnimationState, NihohaeAnimations.FLOP, ageInTicks, partialTicks);
		this.animateSmooth(entity.attackAnimationState, NihohaeAnimations.BITE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.jumpAnimationState, NihohaeAnimations.AIRBORNE, ageInTicks, partialTicks);

        this.swim_control.xRot = entity.getTilt(partialTicks) * Mth.DEG_TO_RAD;
        this.swim_control.zRot = entity.getRoll(partialTicks) * Mth.DEG_TO_RAD;
        this.tail1.yRot += entity.getTailYaw(partialTicks) * Mth.DEG_TO_RAD;
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}