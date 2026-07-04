package com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.mesozoic.AquilolamnaAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Aquilolamna;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class AquilolamnaModel extends UP2Model<Aquilolamna> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart left_wing1;
    private final ModelPart left_wing2;
    private final ModelPart left_wing3;
    private final ModelPart right_wing1;
    private final ModelPart right_wing2;
    private final ModelPart right_wing3;
    private final ModelPart tail1;
    private final ModelPart tail_pivot;
    private final ModelPart tail2;
    private final ModelPart left_pelvicfin;
    private final ModelPart right_pelvicfin;

	public AquilolamnaModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.left_wing1 = this.body.getChild("left_wing1");
        this.left_wing2 = this.left_wing1.getChild("left_wing2");
        this.left_wing3 = this.left_wing2.getChild("left_wing3");
        this.right_wing1 = this.body.getChild("right_wing1");
        this.right_wing2 = this.right_wing1.getChild("right_wing2");
        this.right_wing3 = this.right_wing2.getChild("right_wing3");
        this.tail1 = this.body.getChild("tail1");
        this.tail_pivot = this.tail1.getChild("tail_pivot");
        this.tail2 = this.tail_pivot.getChild("tail2");
        this.left_pelvicfin = this.tail1.getChild("left_pelvicfin");
        this.right_pelvicfin = this.tail1.getChild("right_pelvicfin");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 67).addBox(-6.5F, -3.0F, -9.0F, 13.0F, 6.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 92).addBox(-6.0F, -2.0F, -8.0F, 12.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_wing1 = body.addOrReplaceChild("left_wing1", CubeListBuilder.create().texOffs(0, 49).addBox(0.0F, -1.0F, -1.0F, 5.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 1.0F, -5.0F));

        PartDefinition left_wing2 = left_wing1.addOrReplaceChild("left_wing2", CubeListBuilder.create().texOffs(59, 92).addBox(0.0F, -0.5F, -1.0F, 21.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -0.5F, 0.0F));

        PartDefinition left_wing3 = left_wing2.addOrReplaceChild("left_wing3", CubeListBuilder.create().texOffs(61, 103).addBox(0.0F, -0.5F, -1.0F, 20.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(21.0F, 0.0F, 0.0F));

        PartDefinition right_wing1 = body.addOrReplaceChild("right_wing1", CubeListBuilder.create().texOffs(0, 49).mirror().addBox(-5.0F, -1.0F, -1.0F, 5.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.5F, 1.0F, -5.0F));

        PartDefinition right_wing2 = right_wing1.addOrReplaceChild("right_wing2", CubeListBuilder.create().texOffs(59, 92).mirror().addBox(-21.0F, -0.5F, -1.0F, 21.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -0.5F, 0.0F));

        PartDefinition right_wing3 = right_wing2.addOrReplaceChild("right_wing3", CubeListBuilder.create().texOffs(61, 103).mirror().addBox(-20.0F, -0.5F, -1.0F, 20.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-21.0F, 0.0F, 0.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(46, 58).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 3.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 9.0F));

        PartDefinition tail_pivot = tail1.addOrReplaceChild("tail_pivot", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.5F, 22.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition tail2 = tail_pivot.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(41, 12).addBox(0.0F, 0.0F, 0.0F, 0.0F, 9.0F, 33.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_pelvicfin = tail1.addOrReplaceChild("left_pelvicfin", CubeListBuilder.create().texOffs(78, 74).addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(1.5F, 2.0F, 11.0F, 0.0F, 0.0F, -0.48F));

        PartDefinition right_pelvicfin = tail1.addOrReplaceChild("right_pelvicfin", CubeListBuilder.create().texOffs(78, 74).mirror().addBox(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 2.0F, 11.0F, 0.0F, 0.0F, 0.48F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Aquilolamna entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble()) {
            if (entity.isRunning()) {
                this.animateWalk(AquilolamnaAnimations.SWIMFAST, limbSwing, limbSwingAmount, 1.5F, 2.5F);
            }
            else {
                this.animateWalk(AquilolamnaAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 2.5F);
            }
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, AquilolamnaAnimations.SWIM, ageInTicks, partialTicks, limbSwingAmount, 2.5F, 0.5F);
		this.animateSmooth(entity.flopAnimationState, AquilolamnaAnimations.FLOP, ageInTicks, partialTicks);

        this.swim_control.xRot = entity.getTilt(partialTicks) * Mth.DEG_TO_RAD;
        this.swim_control.zRot = entity.getRoll(partialTicks) * Mth.DEG_TO_RAD;
        this.tail1.yRot += entity.getTailYaw(partialTicks) * Mth.DEG_TO_RAD;
        this.tail2.yRot += entity.getTailYaw(partialTicks) * 0.15F * Mth.DEG_TO_RAD;
    }

	@Override
	public ModelPart root() {
		return this.root;
	}
}