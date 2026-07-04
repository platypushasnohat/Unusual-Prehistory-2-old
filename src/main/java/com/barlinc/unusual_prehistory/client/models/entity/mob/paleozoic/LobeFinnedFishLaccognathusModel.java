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
public class LobeFinnedFishLaccognathusModel extends UP2Model<LobeFinnedFish> {

    private final ModelPart root;
    private final ModelPart flop_upright;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart dorsal1;
    private final ModelPart head;
    private final ModelPart maw;
    private final ModelPart left_pectoralfin;
    private final ModelPart right_pectoralfin;
    private final ModelPart left_pelvicfin;
    private final ModelPart right_pelvicfin;
    private final ModelPart anal;
    private final ModelPart tail1;
    private final ModelPart dorsal2;
    private final ModelPart tail2;

	public LobeFinnedFishLaccognathusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.flop_upright = this.root.getChild("flop_upright");
        this.swim_control = this.flop_upright.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.dorsal1 = this.body.getChild("dorsal1");
        this.head = this.body.getChild("head");
        this.maw = this.head.getChild("maw");
        this.left_pectoralfin = this.head.getChild("left_pectoralfin");
        this.right_pectoralfin = this.head.getChild("right_pectoralfin");
        this.left_pelvicfin = this.body.getChild("left_pelvicfin");
        this.right_pelvicfin = this.body.getChild("right_pelvicfin");
        this.anal = this.body.getChild("anal");
        this.tail1 = this.body.getChild("tail1");
        this.dorsal2 = this.tail1.getChild("dorsal2");
        this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition flop_upright = root.addOrReplaceChild("flop_upright", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -7.0F));

        PartDefinition swim_control = flop_upright.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(50, 20).addBox(-6.5F, -3.0F, -7.0F, 13.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 7.0F));

        PartDefinition dorsal1 = body.addOrReplaceChild("dorsal1", CubeListBuilder.create().texOffs(1, 61).addBox(0.0F, -6.0F, -1.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -3.0F, -1.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.5F, -3.0F, -15.0F, 17.0F, 3.0F, 15.0F, new CubeDeformation(0.01F))
                .texOffs(48, 40).addBox(-8.5F, -2.0F, -15.0F, 17.0F, 2.0F, 12.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -7.0F));

        PartDefinition maw = head.addOrReplaceChild("maw", CubeListBuilder.create().texOffs(64, 0).addBox(-8.5F, 0.0F, -15.0F, 17.0F, 3.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-8.5F, 0.0F, -15.0F, 17.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_pectoralfin = head.addOrReplaceChild("left_pectoralfin", CubeListBuilder.create().texOffs(10, 55).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(8.5F, 2.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition right_pectoralfin = head.addOrReplaceChild("right_pectoralfin", CubeListBuilder.create().texOffs(10, 55).mirror().addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-8.5F, 2.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition left_pelvicfin = body.addOrReplaceChild("left_pelvicfin", CubeListBuilder.create().texOffs(30, 55).addBox(0.0F, 0.0F, -1.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(6.5F, 3.0F, 2.0F, 0.0F, 0.0F, 1.2217F));

        PartDefinition right_pelvicfin = body.addOrReplaceChild("right_pelvicfin", CubeListBuilder.create().texOffs(30, 55).mirror().addBox(-5.0F, 0.0F, -1.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-6.5F, 3.0F, 2.0F, 0.0F, 0.0F, -1.2217F));

        PartDefinition anal = body.addOrReplaceChild("anal", CubeListBuilder.create().texOffs(21, 54).addBox(0.0F, -1.0F, 0.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 3.0F, 4.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(48, 56).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 7.0F));

        PartDefinition dorsal2 = tail1.addOrReplaceChild("dorsal2", CubeListBuilder.create().texOffs(3, 69).addBox(0.0F, -5.0F, -1.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -1.5F, 1.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(4, 28).addBox(0.0F, 0.0F, -2.0F, 0.0F, 7.0F, 20.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -1.5F, 8.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
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