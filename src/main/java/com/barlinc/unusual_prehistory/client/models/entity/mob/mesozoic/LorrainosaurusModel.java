package com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.mesozoic.LorrainosaurusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Lorrainosaurus;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused, FieldCanBeLocal")
public class LorrainosaurusModel extends UP2Model<Lorrainosaurus> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw_upper;
    private final ModelPart jaw_lower;
    private final ModelPart throat;
    private final ModelPart tail;
    private final ModelPart front_fin_control;
    private final ModelPart front_fin_left;
    private final ModelPart front_fin_right;
    private final ModelPart back_fin_control;
    private final ModelPart back_fin_left;
    private final ModelPart back_fin_right;

	public LorrainosaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body_main = this.swim_control.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.jaw_upper = this.head.getChild("jaw_upper");
        this.jaw_lower = this.head.getChild("jaw_lower");
        this.throat = this.jaw_lower.getChild("throat");
        this.tail = this.body.getChild("tail");
        this.front_fin_control = this.body_main.getChild("front_fin_control");
        this.front_fin_left = this.front_fin_control.getChild("front_fin_left");
        this.front_fin_right = this.front_fin_control.getChild("front_fin_right");
        this.back_fin_control = this.body_main.getChild("back_fin_control");
        this.back_fin_left = this.back_fin_control.getChild("back_fin_left");
        this.back_fin_right = this.back_fin_control.getChild("back_fin_right");
	}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-13.5F, -24.0F, -19.0F, 27.0F, 34.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -19.0F));

        PartDefinition jaw_upper = head.addOrReplaceChild("jaw_upper", CubeListBuilder.create().texOffs(138, 150).addBox(-8.5F, -2.5F, -10.0F, 17.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(136, 167).addBox(-8.5F, 2.5F, -10.0F, 17.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 72).addBox(-4.5F, 2.5F, -45.0F, 9.0F, 6.0F, 35.0F, new CubeDeformation(0.0F))
                .texOffs(0, 113).addBox(-4.5F, 8.5F, -45.0F, 9.0F, 2.0F, 35.0F, new CubeDeformation(-0.02F)), PartPose.offset(0.0F, -5.5F, 0.0F));

        PartDefinition frontteeth_r1 = jaw_upper.addOrReplaceChild("frontteeth_r1", CubeListBuilder.create().texOffs(176, 80).addBox(-8.0F, 0.0F, 0.0F, 9.0F, 3.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.5F, 8.5F, -45.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition outerteeth_r1 = jaw_upper.addOrReplaceChild("outerteeth_r1", CubeListBuilder.create().texOffs(130, 38).mirror().addBox(0.0F, 0.0F, -14.0F, 0.0F, 2.0F, 32.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 8.5F, -28.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition outerteeth_r2 = jaw_upper.addOrReplaceChild("outerteeth_r2", CubeListBuilder.create().texOffs(130, 38).addBox(0.0F, 0.0F, -14.0F, 0.0F, 2.0F, 32.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.5F, 8.5F, -28.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition jaw_lower = head.addOrReplaceChild("jaw_lower", CubeListBuilder.create().texOffs(88, 72).addBox(-4.5F, 6.0F, -47.0F, 9.0F, 5.0F, 35.0F, new CubeDeformation(0.0F))
                .texOffs(88, 112).addBox(-4.5F, 3.0F, -47.0F, 9.0F, 3.0F, 35.0F, new CubeDeformation(-0.02F))
                .texOffs(176, 72).addBox(-8.5F, 6.0F, -12.0F, 17.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(50, 164).addBox(-8.5F, 0.0F, -9.0F, 17.0F, 11.0F, 9.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -3.0F, 2.0F));

        PartDefinition outerteeth_r3 = jaw_lower.addOrReplaceChild("outerteeth_r3", CubeListBuilder.create().texOffs(130, 0).mirror().addBox(-0.01F, -3.0F, -11.0F, 0.0F, 3.0F, 35.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 6.0F, -36.0F, 0.0F, 0.0F, -0.0349F));

        PartDefinition outerteeth_r4 = jaw_lower.addOrReplaceChild("outerteeth_r4", CubeListBuilder.create().texOffs(130, 0).addBox(0.01F, -3.0F, -11.0F, 0.0F, 3.0F, 35.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.5F, 6.0F, -36.0F, 0.0F, 0.0F, 0.0349F));

        PartDefinition throat = jaw_lower.addOrReplaceChild("throat", CubeListBuilder.create().texOffs(65, 86).addBox(-7.5F, -5.0F, 0.0F, 15.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 164).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(102, 164).addBox(-1.0F, -6.0F, 7.0F, 2.0F, 12.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 19.0F));

        PartDefinition front_fin_control = body_main.addOrReplaceChild("front_fin_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition front_fin_left = front_fin_control.addOrReplaceChild("front_fin_left", CubeListBuilder.create().texOffs(0, 150).addBox(0.0F, -1.0F, -4.0F, 24.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(13.5F, 9.0F, -15.0F));

        PartDefinition front_fin_right = front_fin_control.addOrReplaceChild("front_fin_right", CubeListBuilder.create().texOffs(0, 150).mirror().addBox(-24.0F, -1.0F, -4.0F, 24.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-13.5F, 9.0F, -15.0F));

        PartDefinition back_fin_control = body_main.addOrReplaceChild("back_fin_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition back_fin_left = back_fin_control.addOrReplaceChild("back_fin_left", CubeListBuilder.create().texOffs(72, 150).addBox(0.0F, -1.0F, -4.0F, 21.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(13.5F, 9.0F, 11.0F));

        PartDefinition back_fin_right = back_fin_control.addOrReplaceChild("back_fin_right", CubeListBuilder.create().texOffs(72, 150).mirror().addBox(-21.0F, -1.0F, -4.0F, 21.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-13.5F, 9.0F, 11.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

	@Override
	public void setupAnim(Lorrainosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble()) {
            if (entity.isRunning()) {
                this.animateWalk(LorrainosaurusAnimations.SWIM_FAST, limbSwing, limbSwingAmount, 2, 4);
            } else {
                this.animateWalk(LorrainosaurusAnimations.SWIM, limbSwing, limbSwingAmount, 2, 4);
            }
        } else {
            this.animateWalk(LorrainosaurusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
        }

        if (this.young) {
            this.applyStatic(LorrainosaurusAnimations.BABY_TRANSFORM);
        }

        this.animateIdleSmooth(entity.swimIdleAnimationState, LorrainosaurusAnimations.SWIM_IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.idleAnimationState, LorrainosaurusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 4);
        this.animateSmooth(entity.attack1AnimationState, LorrainosaurusAnimations.BITE_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack2AnimationState, LorrainosaurusAnimations.BITE_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.yawnAnimationState, LorrainosaurusAnimations.YAWN_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.nip1AnimationState, LorrainosaurusAnimations.NIP_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.nip2AnimationState, LorrainosaurusAnimations.NIP_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.grabStartAnimationState, LorrainosaurusAnimations.GRAB_START_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.grabAnimationState, LorrainosaurusAnimations.GRAB_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.aggroAnimationState, LorrainosaurusAnimations.AGGRO_BLEND, ageInTicks, partialTicks);

        this.swim_control.xRot = entity.getTilt(partialTicks) * Mth.DEG_TO_RAD;
        this.swim_control.zRot = entity.getRoll(partialTicks) * Mth.DEG_TO_RAD;
        this.faceTarget(entity, netHeadYaw, headPitch, 4, head);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    public void translateToMouth(PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        this.swim_control.translateAndRotate(poseStack);
        this.body_main.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        this.head.translateAndRotate(poseStack);
        this.jaw_lower.translateAndRotate(poseStack);
    }
}