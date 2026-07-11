package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.CoelacanthusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Coelacanthus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class CoelacanthusModel extends UP2Model<Coelacanthus> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart dorsal1;
    private final ModelPart dorsal2;
    private final ModelPart left_pectoralfin;
    private final ModelPart right_pectoralfin;
    private final ModelPart left_pelvicfin;
    private final ModelPart right_pelvicfin;
    private final ModelPart anal;
    private final ModelPart tail1;
    private final ModelPart tail2;

	public CoelacanthusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.head = this.body.getChild("head");
        this.dorsal1 = this.body.getChild("dorsal1");
        this.dorsal2 = this.body.getChild("dorsal2");
        this.left_pectoralfin = this.body.getChild("left_pectoralfin");
        this.right_pectoralfin = this.body.getChild("right_pectoralfin");
        this.left_pelvicfin = this.body.getChild("left_pelvicfin");
        this.right_pelvicfin = this.body.getChild("right_pelvicfin");
        this.anal = this.body.getChild("anal");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -5.0F, 3.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(21, 17).addBox(-1.5F, -2.5F, -4.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(19, 27).addBox(-1.5F, 2.5F, -2.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 26).addBox(-1.5F, -0.5F, -4.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -5.0F));

        PartDefinition dorsal1 = body.addOrReplaceChild("dorsal1", CubeListBuilder.create().texOffs(26, 0).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -3.0F, -2.0F));

        PartDefinition dorsal2 = body.addOrReplaceChild("dorsal2", CubeListBuilder.create().texOffs(26, 8).addBox(0.0F, -2.0F, -1.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -3.0F, 3.0F));

        PartDefinition left_pectoralfin = body.addOrReplaceChild("left_pectoralfin", CubeListBuilder.create().texOffs(20, 33).addBox(0.0F, -2.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, 1.0F, -2.0F));

        PartDefinition right_pectoralfin = body.addOrReplaceChild("right_pectoralfin", CubeListBuilder.create().texOffs(20, 33).mirror().addBox(-4.0F, -2.0F, 0.0F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, 1.0F, -2.0F));

        PartDefinition left_pelvicfin = body.addOrReplaceChild("left_pelvicfin", CubeListBuilder.create().texOffs(28, 29).addBox(0.0F, -0.5F, -1.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.0F, 3.0F, -2.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition right_pelvicfin = body.addOrReplaceChild("right_pelvicfin", CubeListBuilder.create().texOffs(28, 29).mirror().addBox(0.0F, -0.5F, -1.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 3.0F, -2.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition anal = body.addOrReplaceChild("anal", CubeListBuilder.create().texOffs(20, 26).addBox(0.0F, -1.0F, -1.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 3.0F, 3.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(2, 18).addBox(0.0F, -5.0F, 0.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(7, 33).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Coelacanthus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble()) {
            if (entity.isRunning()) this.animateWalk(CoelacanthusAnimations.SWIMFAST, limbSwing, limbSwingAmount, 1.25F, 2.5F);
            this.animateWalk(CoelacanthusAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        }

        this.animateIdleSmooth(entity.swimIdleAnimationState, CoelacanthusAnimations.SWIM_IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.flopAnimationState, CoelacanthusAnimations.LAND, ageInTicks, partialTicks);
        this.animateSmooth(entity.absorbAnimationState, CoelacanthusAnimations.EAT_BLEND, ageInTicks, partialTicks);

        if (entity.isInWaterOrBubble()) {
            this.swim_control.xRot = headPitch * ((float) Math.PI / 180F);
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}