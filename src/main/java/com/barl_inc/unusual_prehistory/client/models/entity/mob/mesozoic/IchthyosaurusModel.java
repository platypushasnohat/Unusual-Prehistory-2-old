package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.IchthyosaurusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Ichthyosaurus;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused, FieldCanBeLocal")
public class IchthyosaurusModel extends UP2Model<Ichthyosaurus> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body_main;
    private final ModelPart rider;
    private final ModelPart dorsal_fin;
    private final ModelPart jaw;
    private final ModelPart fin_left;
    private final ModelPart fin_right;
    private final ModelPart fin_left_back;
    private final ModelPart fin_right_back;
    private final ModelPart tail;
    private final ModelPart tail_fin;
    private final ModelPart tail_upper;
    private final ModelPart tail_lower;

	public IchthyosaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body_main = this.swim_control.getChild("body_main");
        this.rider = this.body_main.getChild("rider");
        this.dorsal_fin = this.body_main.getChild("dorsal_fin");
        this.jaw = this.body_main.getChild("jaw");
        this.fin_left = this.body_main.getChild("fin_left");
        this.fin_right = this.body_main.getChild("fin_right");
        this.fin_left_back = this.body_main.getChild("fin_left_back");
        this.fin_right_back = this.body_main.getChild("fin_right_back");
        this.tail = this.body_main.getChild("tail");
        this.tail_fin = this.tail.getChild("tail_fin");
        this.tail_upper = this.tail_fin.getChild("tail_upper");
        this.tail_lower = this.tail_fin.getChild("tail_lower");
	}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 1.0F));

        PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, -11.0F, -12.0F, 15.0F, 21.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-2.5F, 6.0F, -31.0F, 5.0F, 3.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(48, 62).addBox(-8.5F, 4.0F, -10.0F, 17.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rider = body_main.addOrReplaceChild("rider", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, 8.0F));

        PartDefinition dorsal_fin = body_main.addOrReplaceChild("dorsal_fin", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, -3.0F));

        PartDefinition dorsal_r1 = dorsal_fin.addOrReplaceChild("dorsal_r1", CubeListBuilder.create().texOffs(28, 71).addBox(-0.5F, -14.0F, 0.0F, 1.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition jaw = body_main.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(48, 43).addBox(-2.0F, 0.0F, -18.0F, 4.0F, 1.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -12.0F));

        PartDefinition fin_left = body_main.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(0, 65).addBox(0.0F, -0.5F, -2.0F, 16.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 9.5F, -2.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition fin_right = body_main.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(0, 65).mirror().addBox(-16.0F, -0.5F, -2.0F, 16.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, 9.5F, -2.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition fin_left_back = body_main.addOrReplaceChild("fin_left_back", CubeListBuilder.create().texOffs(42, 72).addBox(0.0F, -0.5F, -2.0F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, 9.5F, 8.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition fin_right_back = body_main.addOrReplaceChild("fin_right_back", CubeListBuilder.create().texOffs(42, 72).mirror().addBox(-8.0F, -0.5F, -2.0F, 8.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.5F, 9.5F, 8.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition tail = body_main.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 71).addBox(-2.5F, -3.0F, 0.0F, 5.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition tail_fin = tail.addOrReplaceChild("tail_fin", CubeListBuilder.create(), PartPose.offset(0.5F, 0.0F, 9.0F));

        PartDefinition tail_upper = tail_fin.addOrReplaceChild("tail_upper", CubeListBuilder.create().texOffs(68, 72).addBox(-1.0F, -17.0F, 0.0F, 1.0F, 17.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(42, 65).addBox(-1.0F, -17.0F, 4.01F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition tail_lower = tail_fin.addOrReplaceChild("tail_lower", CubeListBuilder.create().texOffs(74, 0).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 14.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(74, 18).addBox(-1.0F, 11.0F, 4.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.3491F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

	@Override
	public void setupAnim(@NotNull Ichthyosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble() || entity.isLeaping() || entity.isDashing() || entity.hasControllingPassenger()) {
            this.animateWalk(IchthyosaurusAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        }

        this.animateIdleSmooth(entity.swimIdleAnimationState, IchthyosaurusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateSmooth(entity.flopAnimationState, IchthyosaurusAnimations.FLOP, ageInTicks, partialTicks);
        this.animate(entity.roll1AnimationState, IchthyosaurusAnimations.ROLL_BLEND1, ageInTicks);
        this.animate(entity.roll2AnimationState, IchthyosaurusAnimations.ROLL_BLEND2, ageInTicks);

        if (entity.isInWaterOrBubble() || entity.isLeaping() || entity.isDashing() || entity.hasControllingPassenger()) {
            this.swim_control.xRot = headPitch * ((float) Math.PI / 180F);
        }

        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail.yRot = Mth.lerp(0.2F, this.tail.yRot, tailYaw * 0.2F);
        this.tail_fin.yRot = Mth.lerp(0.2F, this.tail_fin.yRot, tailYaw * 0.1F);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }

    public Vec3 getRiderPosition(Vec3 offset) {
        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        Vector4f armOffsetVec = new Vector4f((float) offset.x, (float) offset.y, (float) offset.z, 1.0F);
        armOffsetVec.mul(poseStack.last().pose());
        Vec3 vec3 = new Vec3(armOffsetVec.x(), armOffsetVec.y(), armOffsetVec.z());
        poseStack.popPose();
        return vec3;
    }

    public void translateRiderToBody(PoseStack poseStack) {
        this.root().translateAndRotate(poseStack);
        this.swim_control.translateAndRotate(poseStack);
        this.body_main.translateAndRotate(poseStack);
        this.rider.translateAndRotate(poseStack);
    }
}