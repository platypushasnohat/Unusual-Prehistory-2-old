package com.barlinc.unusual_prehistory.client.models.entity.mob.cenozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.cenozoic.MegalaniaAnimations;
import com.barlinc.unusual_prehistory.client.animations.entity.mob.cenozoic.MegalaniaIdleAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.cenozoic.Megalania;
import com.barlinc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class MegalaniaModel extends UP2Model<Megalania> {

	public final ModelPart root;
	public final ModelPart body_main;
	public final ModelPart body_upper;
	public final ModelPart body;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart tongue;
	private final ModelPart tail1_rot;
	private final ModelPart tail1;
	private final ModelPart tail2_rot;
	private final ModelPart tail2;
	private final ModelPart tail3_rot;
	private final ModelPart tail3;
	private final ModelPart arm_control;
	private final ModelPart left_arm1;
	private final ModelPart left_arm2;
	private final ModelPart right_arm1;
	private final ModelPart right_arm2;
	private final ModelPart leg_control;
	private final ModelPart left_leg1;
	private final ModelPart left_leg2;
	private final ModelPart right_leg1;
	private final ModelPart right_leg2;

	public MegalaniaModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
		this.body_main = this.root.getChild("body_main");
		this.body_upper = this.body_main.getChild("body_upper");
		this.body = this.body_upper.getChild("body");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.tongue = this.jaw.getChild("tongue");
		this.tail1_rot = this.body.getChild("tail1_rot");
		this.tail1 = this.tail1_rot.getChild("tail1");
		this.tail2_rot = this.tail1.getChild("tail2_rot");
		this.tail2 = this.tail2_rot.getChild("tail2");
		this.tail3_rot = this.tail2.getChild("tail3_rot");
		this.tail3 = this.tail3_rot.getChild("tail3");
		this.arm_control = this.body_upper.getChild("arm_control");
		this.left_arm1 = this.arm_control.getChild("left_arm1");
		this.left_arm2 = this.left_arm1.getChild("left_arm2");
		this.right_arm1 = this.arm_control.getChild("right_arm1");
		this.right_arm2 = this.right_arm1.getChild("right_arm2");
		this.leg_control = this.body_main.getChild("leg_control");
		this.left_leg1 = this.leg_control.getChild("left_leg1");
		this.left_leg2 = this.left_leg1.getChild("left_leg2");
		this.right_leg1 = this.leg_control.getChild("right_leg1");
		this.right_leg2 = this.right_leg1.getChild("right_leg2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -13.5F, 0.0F));

		PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 9.5F));

		PartDefinition body = body_upper.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, -7.5F, -15.5F, 19.0F, 15.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, -9.5F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(89, 46).addBox(-3.0F, 0.0F, -13.0F, 6.0F, 11.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, -15.5F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(90, 111).addBox(-3.5F, 0.0F, -16.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(88, 70).addBox(-4.5F, 0.0F, -8.0F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.01F))
				.texOffs(102, 96).addBox(-2.5F, 2.5F, -15.0F, 5.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(28, 114).addBox(-4.5F, 3.0F, -4.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(60, 111).addBox(-3.5F, 0.0F, -12.0F, 7.0F, 4.0F, 8.0F, new CubeDeformation(0.01F))
				.texOffs(112, 114).addBox(-2.5F, -0.5F, -11.0F, 5.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(116, 81).addBox(-4.5F, 0.0F, -4.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 3.0F, -4.0F));

		PartDefinition tongue = jaw.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(11, 54).addBox(-1.5F, -0.01F, -9.0F, 3.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition tail1_rot = body.addOrReplaceChild("tail1_rot", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, 15.5F));

		PartDefinition tail1 = tail1_rot.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 46).addBox(-5.5F, -4.0F, -4.0F, 11.0F, 7.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail2_rot = tail1.addOrReplaceChild("tail2_rot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 29.0F));

		PartDefinition tail2 = tail2_rot.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(60, 86).addBox(-4.0F, -2.5F, 0.0F, 8.0F, 5.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail3_rot = tail2.addOrReplaceChild("tail3_rot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 20.0F));

		PartDefinition tail3 = tail3_rot.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 86).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 4.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition arm_control = body_upper.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -19.0F));

		PartDefinition left_arm1 = arm_control.addOrReplaceChild("left_arm1", CubeListBuilder.create().texOffs(100, 0).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 0.0F, 0.0F));

		PartDefinition left_arm2 = left_arm1.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(100, 36).addBox(-3.5F, 0.0F, -5.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 9.0F, -1.0F));

		PartDefinition right_arm1 = arm_control.addOrReplaceChild("right_arm1", CubeListBuilder.create().texOffs(100, 0).mirror().addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5F, 0.0F, 0.0F));

		PartDefinition right_arm2 = right_arm1.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(100, 36).mirror().addBox(-4.5F, 0.0F, -5.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 9.0F, -1.0F));

		PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, 9.5F));

		PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(100, 18).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 0.0F, 0.0F));

		PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(0, 115).addBox(-4.0F, 0.0F, -5.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -1.0F));

		PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(100, 18).mirror().addBox(-3.0F, -2.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5F, 0.0F, 0.0F));

		PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(0, 115).mirror().addBox(-4.0F, 0.0F, -5.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 9.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Megalania entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float deg = ((float) Math.PI / 180);
		float partialTicks = ageInTicks - entity.tickCount;

		if (entity.getIdleState() != 2 && entity.getPose() != UP2Poses.TAIL_WHIPPING.get() && !entity.isEepy()) {
            if (entity.isInWaterOrBubble()) {
                this.animateWalk(MegalaniaAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 1.5F);
                this.root.xRot = headPitch * (Mth.DEG_TO_RAD) / 2;
            } else if (!entity.isSitting()) {
                if (this.canMegalaniaRun(entity)) {
                    this.animateWalk(MegalaniaAnimations.RUN, limbSwing, limbSwingAmount, 1, 1.5F);
                } else {
                    this.animateWalk(MegalaniaAnimations.WALK, limbSwing, limbSwingAmount, 2, 1.5F);
                }
            }
		}

		if (this.young) this.applyStatic(MegalaniaAnimations.BABY_TRANSFORM);

        this.animateIdleSmooth(entity.idleAnimationState, this.getIdleAnimation(entity), ageInTicks, partialTicks, limbSwingAmount);
		this.animateSmooth(entity.tongueAnimationState, MegalaniaAnimations.TONGUE_BLEND, ageInTicks, partialTicks);
		this.animateSmooth(entity.roarAnimationState, MegalaniaAnimations.ROAR, ageInTicks, partialTicks);
        this.animateSmooth(entity.sitAnimationState, MegalaniaAnimations.SIT, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, MegalaniaAnimations.SLEEP, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack1AnimationState, MegalaniaAnimations.BITE_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack2AnimationState, MegalaniaAnimations.BITE_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.tailWhipAnimationState, MegalaniaAnimations.TAILWHIP, ageInTicks, partialTicks);
        this.animateSmooth(entity.aggroAnimationState, MegalaniaAnimations.AGGRO_BLEND, ageInTicks, partialTicks);
        this.animateIdleSmooth(entity.swimAnimationState, MegalaniaAnimations.SWIM, ageInTicks, partialTicks, limbSwingAmount);

        if (!entity.isEepy() && !entity.isSitting()) {
            this.head.xRot += headPitch * deg / 4;
            this.head.yRot += netHeadYaw * deg / 4;
            this.neck.xRot += headPitch * deg / 2;
            this.neck.yRot += netHeadYaw * deg / 4;
        }

        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail1.yRot = Mth.lerp(0.2F, this.tail1.yRot, tailYaw * 0.35F);
        this.tail2.yRot = Mth.lerp(0.2F, this.tail2.yRot, tailYaw * 0.3F);
        this.tail3.yRot = Mth.lerp(0.2F, this.tail3.yRot, tailYaw * 0.25F);
    }

    private AnimationDefinition getIdleAnimation(Megalania entity) {
        switch (entity.getTemperatureState()) {
            case COLD -> {
                return MegalaniaIdleAnimations.IDLE_COLD;
            }
            case WARM -> {
                return MegalaniaIdleAnimations.IDLE_WARM;
            }
            case NETHER -> {
                return MegalaniaIdleAnimations.IDLE_NETHER;
            }
            default -> {
                return MegalaniaIdleAnimations.IDLE_TEMPERATE;
            }
        }
    }

    private boolean canMegalaniaRun(Megalania entity) {
        return ((entity.isRunning() || (entity.hasControllingPassenger())) || entity.getTemperatureState() == Megalania.TemperatureStates.NETHER) && entity.getTemperatureState() != Megalania.TemperatureStates.COLD;
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}