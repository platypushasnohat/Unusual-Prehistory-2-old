package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.TherizinosaurusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Therizinosaurus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class TherizinosaurusBabyModel extends UP2Model<Therizinosaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart body_adjust;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart tail_adjust;
    private final ModelPart arm_left;
    private final ModelPart arm_right;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

	public TherizinosaurusBabyModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.body_adjust = this.body.getChild("body_adjust");
        this.neck = this.body_adjust.getChild("neck");
        this.head = this.neck.getChild("head");
        this.tail = this.body_adjust.getChild("tail");
        this.tail_adjust = this.tail.getChild("tail_adjust");
        this.arm_left = this.body_adjust.getChild("arm_left");
        this.arm_right = this.body_adjust.getChild("arm_right");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg_left1 = this.leg_control.getChild("leg_left1");
        this.leg_left2 = this.leg_left1.getChild("leg_left2");
        this.leg_left3 = this.leg_left2.getChild("leg_left3");
        this.leg_right1 = this.leg_control.getChild("leg_right1");
        this.leg_right2 = this.leg_right1.getChild("leg_right2");
        this.leg_right3 = this.leg_right2.getChild("leg_right3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body_adjust = body.addOrReplaceChild("body_adjust", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -3.0F, 8.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition neck = body_adjust.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, -11.0F, -2.5F, 4.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 2.5F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 34).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(42, 16).addBox(2.0F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(42, 16).mirror().addBox(-3.0F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -9.0F, -2.5F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(42, 27).addBox(0.8112F, 0.6383F, -2.3112F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -4.5837F, 2.6795F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(42, 22).addBox(-0.8112F, 0.6383F, -2.3112F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -4.5837F, 2.6795F, 0.0F, -0.7854F, 0.0F));

        PartDefinition tail = body_adjust.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 4.25F));

        PartDefinition tail_adjust = tail.addOrReplaceChild("tail_adjust", CubeListBuilder.create().texOffs(0, 19).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition arm_left = body_adjust.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(30, 19).addBox(0.0F, -1.0F, 0.0F, 0.0F, 16.0F, 6.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(4.0F, -9.0F, -1.0F, -1.5708F, 0.0F, -0.2182F));

        PartDefinition arm_right = body_adjust.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(30, 19).mirror().addBox(0.0F, -1.0F, 0.0F, 0.0F, 16.0F, 6.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -9.0F, -1.0F, -1.5708F, 0.0F, 0.2182F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(20, 41).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(20, 34).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(38, 41).addBox(-2.0F, -0.5F, -4.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.5F, 0.0F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(20, 41).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(20, 34).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(38, 41).mirror().addBox(-2.0F, -0.5F, -4.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Therizinosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isInWaterOrBubble()) {
            if (entity.isRunning()) {
                this.animateWalk(TherizinosaurusAnimations.BABY_RUN, limbSwing, limbSwingAmount, 1, 2);
            } else {
                this.animateWalk(TherizinosaurusAnimations.BABY_WALK, limbSwing, limbSwingAmount, 1.5F, 3);
            }
        }

		this.animateIdleSmooth(entity.idleAnimationState, TherizinosaurusAnimations.BABY_IDLE, ageInTicks, partialTicks, limbSwingAmount, entity.isRunning() ? 2 : 3);
        this.animateSmooth(entity.swimAnimationState, TherizinosaurusAnimations.BABY_SWIM, ageInTicks, partialTicks);

        this.faceTarget(entity, netHeadYaw, headPitch, 2, head, neck);
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail_adjust.yRot = Mth.lerp(0.2F, this.tail_adjust.yRot, tailYaw * 0.2F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}