package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.BrachiosaurusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Brachiosaurus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class BrachiosaurusBabyModel extends UP2Model<Brachiosaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body_upper;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart back;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart arm_control;
    private final ModelPart left_arm1;
    private final ModelPart right_arm1;
    private final ModelPart leg_control;
    private final ModelPart left_leg1;
    private final ModelPart right_leg1;

	public BrachiosaurusBabyModel(ModelPart root) {
        super(1, 0);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body_upper = this.body_main.getChild("body_upper");
        this.body = this.body_upper.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.back = this.body.getChild("back");
        this.tail1 = this.back.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.arm_control = this.body_upper.getChild("arm_control");
        this.left_arm1 = this.arm_control.getChild("left_arm1");
        this.right_arm1 = this.arm_control.getChild("right_arm1");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg1 = this.leg_control.getChild("left_leg1");
        this.right_leg1 = this.leg_control.getChild("right_leg1");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 6.5F));

        PartDefinition body = body_upper.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 0).addBox(-5.0F, -6.0F, -9.0F, 10.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -6.5F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -34.5F, -3.5F, 5.0F, 38.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -8.5F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(18, 49).addBox(-1.5F, -2.5F, -4.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(12, 54).addBox(0.0F, -2.5F, -7.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(44, 55).addBox(-1.5F, 0.5F, -8.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -32.0F, -3.5F));

        PartDefinition back = body.addOrReplaceChild("back", CubeListBuilder.create().texOffs(24, 24).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 3.0F));

        PartDefinition tail1 = back.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 45).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 2.0F, 6.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(48, 24).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 7.0F));

        PartDefinition arm_control = body_upper.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -11.5F));

        PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(24, 36).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(32, 49).mirror().addBox(-2.0F, 7.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(2.5F, 0.0F, 0.0F));

        PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(24, 36).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 49).addBox(-2.0F, 7.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.5F, 0.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 6.5F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(0, 54).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(56, 33).mirror().addBox(-1.5F, 4.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(2.5F, 0.0F, 0.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(0, 54).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(56, 33).addBox(-1.5F, 4.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.5F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NotNull Brachiosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isRunning()) {
            this.animateWalk(BrachiosaurusAnimations.BABY_RUN, limbSwing, limbSwingAmount, 1, 2);
        } else {
            this.animateWalk(BrachiosaurusAnimations.BABY_WALK, limbSwing, limbSwingAmount, 1.5F, 3);
        }
		this.animateIdleSmooth(entity.idleAnimationState, BrachiosaurusAnimations.BABY_IDLE, ageInTicks, partialTicks, limbSwingAmount, entity.isRunning() ? 2 : 3);

        this.head.xRot += headPitch * ((float) Math.PI / 180F) / 6;
		this.head.yRot += netHeadYaw * ((float) Math.PI / 180F) / 6;
        this.neck.xRot += headPitch * ((float) Math.PI / 180F) / 4;
        this.neck.yRot += netHeadYaw * ((float) Math.PI / 180F) / 4;
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}