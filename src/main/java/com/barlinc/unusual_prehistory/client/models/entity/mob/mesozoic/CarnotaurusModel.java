package com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.mesozoic.CarnotaurusAnimations;
import com.barlinc.unusual_prehistory.client.animations.entity.mob.mesozoic.CarnotaurusAttackAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Carnotaurus;
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
public class CarnotaurusModel extends UP2Model<Carnotaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart breathing;
    private final ModelPart arm_left;
    private final ModelPart arm_right;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw_upper;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart horn_left;
    private final ModelPart horn_right;
    private final ModelPart jaw;
    private final ModelPart dewlap;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

	public CarnotaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.breathing = this.body.getChild("breathing");
        this.arm_left = this.breathing.getChild("arm_left");
        this.arm_right = this.breathing.getChild("arm_right");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.jaw_upper = this.head.getChild("jaw_upper");
        this.eye_left = this.jaw_upper.getChild("eye_left");
        this.eye_right = this.jaw_upper.getChild("eye_right");
        this.horn_left = this.jaw_upper.getChild("horn_left");
        this.horn_right = this.jaw_upper.getChild("horn_right");
        this.jaw = this.head.getChild("jaw");
        this.dewlap = this.jaw.getChild("dewlap");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
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

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -27.0F, 6.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition breathing = body.addOrReplaceChild("breathing", CubeListBuilder.create().texOffs(127, 130).addBox(-2.5F, -13.0F, -1.0F, 5.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.5F, -12.0F, -16.0F, 17.0F, 24.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -6.0F));

        PartDefinition arm_left = breathing.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(58, 98).addBox(-1.5F, 0.0F, -1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(58, 119).addBox(-1.5F, 4.0F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(8.51F, 9.0F, -9.0F));

        PartDefinition arm_right = breathing.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(58, 98).mirror().addBox(-0.5F, 0.0F, -1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(58, 119).mirror().addBox(-0.5F, 4.0F, -1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.51F, 9.0F, -9.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(74, 90).addBox(-3.5F, -19.0F, -10.0F, 7.0F, 24.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(126, 90).addBox(-2.5F, -21.0F, -4.0F, 5.0F, 25.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, -18.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -12.6F, -5.1F));

        PartDefinition jaw_upper = head.addOrReplaceChild("jaw_upper", CubeListBuilder.create().texOffs(150, 11).addBox(-4.5F, -8.91F, -3.9F, 9.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(146, 36).addBox(-4.5F, -8.91F, -12.9F, 9.0F, 11.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(185, 25).addBox(-4.5F, 2.09F, -12.9F, 9.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(150, 24).addBox(-3.5F, 2.09F, -11.9F, 7.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, 0.0F));

        PartDefinition eye_left = jaw_upper.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(174, 39).addBox(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(4.0F, -1.91F, -5.4F));

        PartDefinition eye_right = jaw_upper.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(174, 39).mirror().addBox(-0.5F, -1.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-4.0F, -1.91F, -5.4F));

        PartDefinition horn_left = jaw_upper.addOrReplaceChild("horn_left", CubeListBuilder.create().texOffs(88, 133).addBox(-2.0F, -2.92F, -12.0F, 4.0F, 6.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(30, 152).addBox(-2.0F, -6.91F, -12.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(58, 110).addBox(-2.0F, -6.91F, -7.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -6.0F, -3.9F, 0.0F, -0.0873F, 0.0873F));

        PartDefinition horn_right = jaw_upper.addOrReplaceChild("horn_right", CubeListBuilder.create().texOffs(88, 133).mirror().addBox(-2.0F, -2.92F, -12.0F, 4.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 152).mirror().addBox(-2.0F, -6.91F, -12.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(58, 110).mirror().addBox(-2.0F, -6.91F, -7.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.5F, -6.0F, -3.9F, 0.0F, 0.0873F, -0.0873F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 152).addBox(-3.5F, 1.09F, -11.9F, 7.0F, 1.0F, 8.0F, new CubeDeformation(-0.01F))
                .texOffs(148, 82).addBox(-4.5F, 0.09F, -3.9F, 9.0F, 4.0F, 4.0F, new CubeDeformation(-0.01F))
                .texOffs(150, 0).addBox(-4.5F, 2.09F, -12.9F, 9.0F, 2.0F, 9.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 2.5F, 0.0F));

        PartDefinition dewlap = jaw.addOrReplaceChild("dewlap", CubeListBuilder.create().texOffs(126, 148).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 10.0F, 12.0F, new CubeDeformation(-0.01F))
                .texOffs(148, 56).addBox(0.0F, 0.0F, -7.0F, 0.0F, 11.0F, 15.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 4.0F, -4.9F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 98).addBox(-4.5F, -5.0F, 0.0F, 9.0F, 12.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(1, 130).addBox(-2.5F, -7.0F, 0.0F, 5.0F, 2.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 10.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 56).addBox(-2.5F, -5.0F, 0.0F, 5.0F, 10.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(74, 56).addBox(-2.5F, -7.0F, 0.0F, 5.0F, 2.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 20.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(98, 0).addBox(-5.0F, -11.0F, -8.0F, 10.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(54, 133).addBox(-3.5F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 7.0F, 6.0F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(98, 36).addBox(-4.5F, 0.0F, -8.0F, 9.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(58, 114).addBox(2.5F, 0.0F, -11.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(58, 114).addBox(-4.5F, 0.0F, -11.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(58, 105).addBox(-1.5F, 0.0F, -11.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(58, 123).addBox(-1.5F, 0.0F, 7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 1.0F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(98, 0).mirror().addBox(-5.0F, -11.0F, -8.0F, 10.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.5F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(54, 133).mirror().addBox(-3.5F, -2.0F, -4.0F, 7.0F, 17.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.5F, 7.0F, 6.0F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(98, 36).mirror().addBox(-4.5F, 0.0F, -8.0F, 9.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(58, 114).mirror().addBox(-4.5F, 0.0F, -11.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(58, 114).mirror().addBox(2.5F, 0.0F, -11.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(58, 105).mirror().addBox(-1.5F, 0.0F, -11.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(58, 123).mirror().addBox(-1.5F, 0.0F, 7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 15.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Carnotaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

		if (!entity.isInWaterOrBubble() && !entity.isEepy() && entity.getPose() != UP2Poses.START_CHARGING.get() && entity.getPose() != UP2Poses.STOP_CHARGING.get()) {
            if (entity.getPose() != UP2Poses.CHARGING.get()) {
                if (entity.isRunning()) {
                    this.animateWalk(CarnotaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.25F, 2.5F);
                }
                else {
                    this.animateWalk(CarnotaurusAnimations.WALK, limbSwing, limbSwingAmount, 1.8F, 3.6F);
                }
            } else {
                this.animateWalk(CarnotaurusAttackAnimations.CHARGE, limbSwing, limbSwingAmount, 1.25F, 2.5F);
            }
		}

		if (this.young) this.applyStatic(CarnotaurusAnimations.BABY_TRANSFORM);

		this.animateIdleSmooth(entity.idleAnimationState, CarnotaurusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, entity.isRunning() ? 2.5F : 3.6F);
		this.animateSmooth(entity.attack1AnimationState, CarnotaurusAttackAnimations.BITE_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack2AnimationState, CarnotaurusAttackAnimations.BITE_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.headbuttAnimationState, CarnotaurusAttackAnimations.HEADBUTT_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.chargeStartAnimationState, CarnotaurusAttackAnimations.CHARGE_START, ageInTicks, partialTicks);
        this.animateSmooth(entity.chargeEndAnimationState, CarnotaurusAttackAnimations.CHARGE_END, ageInTicks, partialTicks);
		this.animateSmooth(entity.roarAnimationState, CarnotaurusAttackAnimations.AGGRO_ROAR_BLEND, ageInTicks, partialTicks);
		this.animateSmooth(entity.angryAnimationState, CarnotaurusAttackAnimations.AGGRO_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.swimAnimationState, CarnotaurusAnimations.SWIM, ageInTicks, partialTicks);
        this.animateSmooth(entity.sniff1AnimationState, CarnotaurusAnimations.SNIFF_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.sniff2AnimationState, CarnotaurusAnimations.SNIFF_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.eepyAnimationState, CarnotaurusAnimations.SLEEP, ageInTicks, partialTicks);

        this.faceTarget(entity, netHeadYaw, headPitch, 2, neck);

        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail1.yRot = Mth.lerp(0.2F, this.tail1.yRot, tailYaw * 0.2F);
        this.tail2.yRot = Mth.lerp(0.2F, this.tail2.yRot, tailYaw * 0.15F);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}