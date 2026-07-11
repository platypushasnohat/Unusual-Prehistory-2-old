package com.barl_inc.unusual_prehistory.client.models.entity.mob.other;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.cenozoic.KingLingcodAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.other.Lingcod;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class LingcodModel extends UP2Model<Lingcod> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart dorsal1;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart jaw;
    private final ModelPart pectoralfin_left;
    private final ModelPart pectoralfin_right;
    private final ModelPart pelvicfin_left;
    private final ModelPart pelvicfin_right;
    private final ModelPart tail1;
    private final ModelPart dorsal2;
    private final ModelPart anal;
    private final ModelPart tail2;

	public LingcodModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.dorsal1 = this.body.getChild("dorsal1");
        this.head = this.body.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.jaw = this.head.getChild("jaw");
        this.pectoralfin_left = this.body.getChild("pectoralfin_left");
        this.pectoralfin_right = this.body.getChild("pectoralfin_right");
        this.pelvicfin_left = this.body.getChild("pelvicfin_left");
        this.pelvicfin_right = this.body.getChild("pelvicfin_right");
        this.tail1 = this.body.getChild("tail1");
        this.dorsal2 = this.tail1.getChild("dorsal2");
        this.anal = this.tail1.getChild("anal");
        this.tail2 = this.tail1.getChild("tail2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -5.0F, -8.0F, 9.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition dorsal1 = body.addOrReplaceChild("dorsal1", CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, -6.0F, 0.0F, 0.0F, 6.0F, 12.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, -5.0F, -6.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(34, 26).addBox(-4.5F, -2.5F, -9.0F, 9.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-4.5F, 0.5F, -9.0F, 9.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, -8.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(50, 18).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -2.5F, -3.5F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(50, 18).mirror().addBox(-2.0F, -2.0F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, -2.5F, -3.5F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(34, 40).addBox(-5.0F, 0.0F, -9.0F, 10.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(35, 5).mirror().addBox(-6.0F, 0.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(35, 5).addBox(4.0F, 0.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -1.0F));

        PartDefinition pectoralfin_left = body.addOrReplaceChild("pectoralfin_left", CubeListBuilder.create(), PartPose.offset(4.5F, 2.0F, -6.0F));

        PartDefinition frontfin_r1 = pectoralfin_left.addOrReplaceChild("frontfin_r1", CubeListBuilder.create().texOffs(45, -8).addBox(0.01F, -1.0F, -1.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.48F, -0.2182F));

        PartDefinition pectoralfin_right = body.addOrReplaceChild("pectoralfin_right", CubeListBuilder.create(), PartPose.offset(-4.5F, 2.0F, -6.0F));

        PartDefinition frontfin_r2 = pectoralfin_right.addOrReplaceChild("frontfin_r2", CubeListBuilder.create().texOffs(45, -8).mirror().addBox(-0.01F, -1.0F, -1.0F, 0.0F, 9.0F, 10.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, -0.48F, 0.2182F));

        PartDefinition pelvicfin_left = body.addOrReplaceChild("pelvicfin_left", CubeListBuilder.create(), PartPose.offset(1.5F, 5.0F, -4.0F));

        PartDefinition bottomfin_r1 = pelvicfin_left.addOrReplaceChild("bottomfin_r1", CubeListBuilder.create().texOffs(31, 11).addBox(-1.0F, 0.01F, -1.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.6981F));

        PartDefinition pelvicfin_right = body.addOrReplaceChild("pelvicfin_right", CubeListBuilder.create(), PartPose.offset(-1.5F, 5.0F, -4.0F));

        PartDefinition bottomfin_r2 = pelvicfin_right.addOrReplaceChild("bottomfin_r2", CubeListBuilder.create().texOffs(31, 11).mirror().addBox(-3.0F, 0.01F, -1.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, -0.6981F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 26).addBox(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 8.0F));

        PartDefinition dorsal2 = tail1.addOrReplaceChild("dorsal2", CubeListBuilder.create().texOffs(66, -7).addBox(0.0F, -7.5F, -4.5F, 0.0F, 6.0F, 9.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 6.5F));

        PartDefinition anal = tail1.addOrReplaceChild("anal", CubeListBuilder.create().texOffs(75, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 5.0F, 9.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 2.5F, 2.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(70, 9).addBox(0.0F, -3.5F, 0.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, -0.5F, 12.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(Lingcod entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble() && entity.getPose() != UP2Poses.ATTACKING.get()) {
            this.animateWalk(KingLingcodAnimations.SWIM, limbSwing, limbSwingAmount, 1.25F, 2.5F);
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, KingLingcodAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
		this.animateSmooth(entity.flopAnimationState, KingLingcodAnimations.FLOP, ageInTicks, partialTicks);
		this.animateSmooth(entity.attackAnimationState, KingLingcodAnimations.ATTACK, ageInTicks, partialTicks);
        this.animate(entity.eatAnimationState, KingLingcodAnimations.EAT_BLEND, ageInTicks);

        if (entity.isInWaterOrBubble()) {
            this.swim_control.xRot = headPitch * ((float) Math.PI / 180F);
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}