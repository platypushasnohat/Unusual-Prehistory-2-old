package com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.paleozoic.LobeFinnedFishAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.LobeFinnedFish;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class LobeFinnedFishAllenypterusModel extends UP2Model<LobeFinnedFish> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart dorsal2;
    private final ModelPart left_pectoralfin;
    private final ModelPart right_pectoralfin;
    private final ModelPart left_pelvicfin;
    private final ModelPart right_pelvicfin;
    private final ModelPart tail1;
    private final ModelPart tail2;

	public LobeFinnedFishAllenypterusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.dorsal2 = this.body.getChild("dorsal2");
        this.left_pectoralfin = this.body.getChild("left_pectoralfin");
        this.right_pectoralfin = this.body.getChild("right_pectoralfin");
        this.left_pelvicfin = this.body.getChild("left_pelvicfin");
        this.right_pelvicfin = this.body.getChild("right_pelvicfin");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -6.0F, 4.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(32, 11).addBox(-2.0F, 3.0F, -6.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition dorsal2 = body.addOrReplaceChild("dorsal2", CubeListBuilder.create().texOffs(41, 26).addBox(0.0F, -4.0F, -2.0F, 0.0F, 7.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -6.0F, 2.0F));

        PartDefinition left_pectoralfin = body.addOrReplaceChild("left_pectoralfin", CubeListBuilder.create().texOffs(40, 18).addBox(0.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.0F, 2.0F, 1.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition right_pectoralfin = body.addOrReplaceChild("right_pectoralfin", CubeListBuilder.create().texOffs(40, 18).mirror().addBox(-4.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 2.0F, 1.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition left_pelvicfin = body.addOrReplaceChild("left_pelvicfin", CubeListBuilder.create().texOffs(33, 17).addBox(0.0F, 0.0F, -1.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.0F, 6.0F, -1.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition right_pelvicfin = body.addOrReplaceChild("right_pelvicfin", CubeListBuilder.create().texOffs(33, 17).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 6.0F, -1.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(1, 32).addBox(0.0F, -5.0F, 2.0F, 0.0F, 10.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 2.0F, 6.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(16, 33).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 8.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(LobeFinnedFish entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateWalk(LobeFinnedFishAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        this.animateIdle(entity.swimIdleAnimationState, LobeFinnedFishAnimations.SWIM_IDLE, ageInTicks, 1, limbSwingAmount * 3);
		this.animate(entity.flopAnimationState, LobeFinnedFishAnimations.FLOP, ageInTicks);
		this.swim_control.xRot = headPitch * (Mth.DEG_TO_RAD);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}