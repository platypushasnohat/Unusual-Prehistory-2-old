package com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.mesozoic.BrachiosaurusAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Brachiosaurus;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class BrachiosaurusModel extends UP2Model<Brachiosaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body_upper;
    private final ModelPart body;
    private final ModelPart neck1;
    private final ModelPart neck2_pivot;
    private final ModelPart neck2;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart back;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart arm_control;
    private final ModelPart left_arm1;
    private final ModelPart left_arm2;
    private final ModelPart left_arm3;
    private final ModelPart right_arm1;
    private final ModelPart right_arm2;
    private final ModelPart right_arm3;
    private final ModelPart leg_control;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart left_leg3;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;
    private final ModelPart right_leg3;

	public BrachiosaurusModel(ModelPart root) {
        super(1, 0);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body_upper = this.body_main.getChild("body_upper");
        this.body = this.body_upper.getChild("body");
        this.neck1 = this.body.getChild("neck1");
        this.neck2_pivot = this.neck1.getChild("neck2_pivot");
        this.neck2 = this.neck2_pivot.getChild("neck2");
        this.head = this.neck2.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.back = this.body.getChild("back");
        this.tail1 = this.back.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.arm_control = this.body_upper.getChild("arm_control");
        this.left_arm1 = this.arm_control.getChild("left_arm1");
        this.left_arm2 = this.left_arm1.getChild("left_arm2");
        this.left_arm3 = this.left_arm2.getChild("left_arm3");
        this.right_arm1 = this.arm_control.getChild("right_arm1");
        this.right_arm2 = this.right_arm1.getChild("right_arm2");
        this.right_arm3 = this.right_arm2.getChild("right_arm3");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg1 = this.leg_control.getChild("left_leg1");
        this.left_leg2 = this.left_leg1.getChild("left_leg2");
        this.left_leg3 = this.left_leg2.getChild("left_leg3");
        this.right_leg1 = this.leg_control.getChild("right_leg1");
        this.right_leg2 = this.right_leg1.getChild("right_leg2");
        this.right_leg3 = this.right_leg2.getChild("right_leg3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -106.0F, 0.0F));

        PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 34.0F, 37.0F));

        PartDefinition body = body_upper.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-27.5F, -32.0F, -47.0F, 55.0F, 80.0F, 61.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -34.0F, -37.0F));

        PartDefinition neck1 = body.addOrReplaceChild("neck1", CubeListBuilder.create().texOffs(139, 146).addBox(-15.0F, -99.0F, -22.0F, 31.0F, 125.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -12.0F, -45.0F));

        PartDefinition neck2_pivot = neck1.addOrReplaceChild("neck2_pivot", CubeListBuilder.create(), PartPose.offset(0.0F, -100.0F, -21.0F));

        PartDefinition neck2 = neck2_pivot.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(0, 174).addBox(-14.0F, -125.0F, -17.5F, 29.0F, 125.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 17.5F));

        PartDefinition head = neck2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(312, 450).addBox(-4.5F, -4.0F, -34.0F, 13.0F, 11.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(222, 434).addBox(-8.5F, -14.0F, -21.0F, 21.0F, 21.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(0, 420).addBox(-3.5F, 7.0F, -33.0F, 11.0F, 3.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(319, 441).addBox(-7.5F, 7.0F, -20.0F, 19.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(354, 400).addBox(-8.5F, 0.0F, -8.0F, 21.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -110.0F, -20.5F));

        PartDefinition crest_r1 = head.addOrReplaceChild("crest_r1", CubeListBuilder.create().texOffs(354, 335).addBox(-4.0F, -17.0F, -15.0F, 7.0F, 23.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -7.0F, -14.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 146).addBox(-8.0F, 0.0F, -9.0F, 21.0F, 12.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(89, 148).addBox(-7.0F, -12.0F, -7.0F, 19.0F, 12.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(364, 450).addBox(-4.0F, 7.0F, -35.0F, 13.0F, 5.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(354, 382).addBox(-8.0F, 7.0F, -22.0F, 21.0F, 5.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(363, 427).addBox(-7.0F, 4.0F, -21.0F, 19.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(48, 420).addBox(-3.0F, 4.0F, -34.0F, 11.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 0.0F, 1.0F));

        PartDefinition back = body.addOrReplaceChild("back", CubeListBuilder.create().texOffs(284, 142).addBox(-21.5F, -27.5F, 0.0F, 43.0F, 55.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.5F, 14.0F));

        PartDefinition tail1 = back.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 338).addBox(-9.0F, -16.0F, 0.0F, 19.0F, 30.0F, 46.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.5F, 32.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(340, 0).addBox(-6.5F, -9.0F, -1.0F, 13.0F, 16.0F, 66.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 46.0F));

        PartDefinition arm_control = body_upper.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, -7.5F, -74.0F));

        PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(290, 238).addBox(-19.0F, -19.9175F, -17.0571F, 35.0F, 56.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(27.0F, 0.0F, 0.0F));

        PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(246, 335).addBox(-14.0F, -7.0F, -12.0F, 27.0F, 54.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 26.0825F, 5.9429F));

        PartDefinition left_arm3 = left_arm2.addOrReplaceChild("left_arm3", CubeListBuilder.create().texOffs(176, 0).addBox(-15.0F, -3.0F, -13.0F, 29.0F, 22.0F, 29.0F, new CubeDeformation(0.0F))
                .texOffs(64, 146).addBox(-21.0F, 14.0F, -4.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 46.0F, 0.0F));

        PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(290, 238).mirror().addBox(-16.0F, -19.9175F, -17.0571F, 35.0F, 56.0F, 32.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-27.0F, 0.0F, 0.0F));

        PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(246, 335).mirror().addBox(-13.0F, -7.0F, -12.0F, 27.0F, 54.0F, 27.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 26.0825F, 5.9429F));

        PartDefinition right_arm3 = right_arm2.addOrReplaceChild("right_arm3", CubeListBuilder.create().texOffs(176, 0).mirror().addBox(-14.0F, -3.0F, -13.0F, 29.0F, 22.0F, 29.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(64, 146).mirror().addBox(15.0F, 14.0F, -4.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 46.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 34.0F, 38.0F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(241, 62).addBox(-15.5F, -16.0F, -14.0F, 31.0F, 44.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(21.0F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(136, 318).addBox(-13.0F, -8.0F, -9.0F, 26.0F, 53.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 9.0F));

        PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(3, 440).addBox(-14.0F, -1.5F, -20.0F, 28.0F, 10.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 43.5F, 3.0F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(241, 62).mirror().addBox(-15.5F, -16.0F, -14.0F, 31.0F, 44.0F, 30.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-21.0F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(136, 318).mirror().addBox(-13.0F, -8.0F, -9.0F, 26.0F, 53.0F, 23.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 20.0F, 9.0F));

        PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(3, 440).mirror().addBox(-14.0F, -1.5F, -20.0F, 28.0F, 10.0F, 33.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 43.5F, 3.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(@NotNull Brachiosaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isEepy() && entity.getPose() != UP2Poses.STOMPING.get()) {
            if (entity.isRunning()) {
                this.animateWalk(BrachiosaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.5F, 3);
            } else {
                this.animateWalk(BrachiosaurusAnimations.WALK, limbSwing, limbSwingAmount, 3.5F, 7);
            }
        }
		this.animateIdleSmooth(entity.idleAnimationState, BrachiosaurusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, entity.isRunning() ? 3.0F : 7.0F);
		this.animateSmooth(entity.eepyAnimationState, BrachiosaurusAnimations.SIT, ageInTicks, partialTicks);
        this.animateSmooth(entity.stompAnimationState, BrachiosaurusAnimations.SLAM, ageInTicks, partialTicks);
        this.animateSmooth(entity.shakeAnimationState, BrachiosaurusAnimations.SHAKE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.callAnimationState, BrachiosaurusAnimations.BELLOW_BLEND, ageInTicks, partialTicks);

        if (!entity.isEepy()) {
            this.head.xRot += headPitch * ((float) Math.PI / 180F) / 6;
            this.head.yRot += netHeadYaw * ((float) Math.PI / 180F) / 6;
            this.neck1.xRot += headPitch * ((float) Math.PI / 180F) / 4;
            this.neck1.yRot += netHeadYaw * ((float) Math.PI / 180F) / 4;
        }

        double bodyYRot = Mth.wrapDegrees(entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTicks);
        double segment1Y = (entity.getTrailTransformation(8, partialTicks)) - bodyYRot;
        double segment2Y = (entity.getTrailTransformation(16, partialTicks)) - bodyYRot - segment1Y;
        this.tail1.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment1Y) * 0.3F);
        this.tail2.yRot += (float) Math.toRadians(Mth.wrapDegrees(segment2Y) * 0.2F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}