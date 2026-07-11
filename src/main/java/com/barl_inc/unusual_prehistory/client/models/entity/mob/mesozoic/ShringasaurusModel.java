package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.ShringasaurusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Shringasaurus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class ShringasaurusModel extends UP2Model<Shringasaurus> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body_upper;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart neck_glow1;
    private final ModelPart neck_glow2;
    private final ModelPart neck_glow3;
    private final ModelPart neck_glow4;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart horn_left;
    private final ModelPart horn_right;
    private final ModelPart jaw;
    private final ModelPart throat;
    private final ModelPart tail;
    private final ModelPart arm_control;
    private final ModelPart arm_left1;
    private final ModelPart arm_left2;
    private final ModelPart arm_right1;
    private final ModelPart arm_right2;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

	public ShringasaurusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body_upper = this.body_main.getChild("body_upper");
        this.body = this.body_upper.getChild("body");
        this.neck = this.body.getChild("neck");
        this.neck_glow1 = this.neck.getChild("neck_glow1");
        this.neck_glow2 = this.neck.getChild("neck_glow2");
        this.neck_glow3 = this.neck.getChild("neck_glow3");
        this.neck_glow4 = this.neck.getChild("neck_glow4");
        this.head = this.neck.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.horn_left = this.head.getChild("horn_left");
        this.horn_right = this.head.getChild("horn_right");
        this.jaw = this.head.getChild("jaw");
        this.throat = this.jaw.getChild("throat");
        this.tail = this.body.getChild("tail");
        this.arm_control = this.body_upper.getChild("arm_control");
        this.arm_left1 = this.arm_control.getChild("arm_left1");
        this.arm_left2 = this.arm_left1.getChild("arm_left2");
        this.arm_right1 = this.arm_control.getChild("arm_right1");
        this.arm_right2 = this.arm_right1.getChild("arm_right2");
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

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -16.0F, 0.0F));

        PartDefinition body_upper = body_main.addOrReplaceChild("body_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 9.0F));

        PartDefinition body = body_upper.addOrReplaceChild("body", CubeListBuilder.create().texOffs(88, 47).addBox(-9.5F, -7.0F, -12.0F, 19.0F, 15.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -9.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 47).addBox(-6.0F, -9.0F, -26.5F, 12.0F, 15.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -7.5F, -0.7854F, 0.0F, 0.0F));

        PartDefinition neck_glow1 = neck.addOrReplaceChild("neck_glow1", CubeListBuilder.create().texOffs(74, 94).mirror().addBox(-10.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(74, 94).addBox(-2.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -9.0F, -2.5F));

        PartDefinition neck_glow2 = neck.addOrReplaceChild("neck_glow2", CubeListBuilder.create().texOffs(74, 94).mirror().addBox(-10.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(74, 94).addBox(-2.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -9.0F, -11.5F));

        PartDefinition neck_glow3 = neck.addOrReplaceChild("neck_glow3", CubeListBuilder.create().texOffs(74, 94).mirror().addBox(-10.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(74, 94).addBox(-2.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -9.0F, -20.5F));

        PartDefinition neck_glow4 = neck.addOrReplaceChild("neck_glow4", CubeListBuilder.create().texOffs(0, 109).mirror().addBox(-9.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 109).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -1.0F, -27.5F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(90, 33).addBox(-3.5F, -3.0F, -6.0F, 7.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(48, 102).addBox(-2.5F, -3.0F, -10.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -25.0F, 0.9599F, 0.0F, 0.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(0, -4).addBox(0.0F, -1.5F, -1.5F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(3.5F, -1.5F, -3.5F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(0, -4).mirror().addBox(0.0F, -1.5F, -1.5F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-3.5F, -1.5F, -3.5F));

        PartDefinition horn_left = head.addOrReplaceChild("horn_left", CubeListBuilder.create().texOffs(100, 103).addBox(1.4F, -6.0F, -2.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(66, 102).addBox(1.4F, -6.0F, -8.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -3.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition horn_right = head.addOrReplaceChild("horn_right", CubeListBuilder.create().texOffs(100, 103).mirror().addBox(-3.4F, -6.0F, -2.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(66, 102).mirror().addBox(-3.4F, -6.0F, -8.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -3.0F, -3.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(22, 94).addBox(-3.5F, 0.0F, -4.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(82, 103).addBox(-2.5F, 0.0F, -8.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -2.0F));

        PartDefinition throat = jaw.addOrReplaceChild("throat", CubeListBuilder.create().texOffs(90, 21).addBox(-4.5F, 0.0F, -1.5F, 9.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -4.0F, -3.0F, 9.0F, 11.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 10.0F));

        PartDefinition arm_control = body_upper.addOrReplaceChild("arm_control", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -19.0F));

        PartDefinition arm_left1 = arm_control.addOrReplaceChild("arm_left1", CubeListBuilder.create().texOffs(90, 0).addBox(-2.5F, -3.0F, -3.0F, 5.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 0.0F, 0.0F));

        PartDefinition arm_left2 = arm_left1.addOrReplaceChild("arm_left2", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition foot_r1 = arm_left2.addOrReplaceChild("foot_r1", CubeListBuilder.create().texOffs(48, 94).addBox(-5.0F, 0.0F, -6.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition arm_right1 = arm_control.addOrReplaceChild("arm_right1", CubeListBuilder.create().texOffs(90, 0).mirror().addBox(-2.5F, -3.0F, -3.0F, 5.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5F, 0.0F, 0.0F));

        PartDefinition arm_right2 = arm_right1.addOrReplaceChild("arm_right2", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition foot_r2 = arm_right2.addOrReplaceChild("foot_r2", CubeListBuilder.create().texOffs(48, 94).mirror().addBox(-2.0F, 0.0F, -6.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 9.0F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(88, 86).addBox(-3.5F, -4.0F, -4.0F, 7.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(9.5F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(0, 94).addBox(-2.5F, -2.0F, -2.0F, 5.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 1.0F));

        PartDefinition foot_r3 = leg_left3.addOrReplaceChild("foot_r3", CubeListBuilder.create().texOffs(22, 102).addBox(-5.0F, 0.0F, -6.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(88, 86).mirror().addBox(-3.5F, -4.0F, -4.0F, 7.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.5F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(0, 94).mirror().addBox(-2.5F, -2.0F, -2.0F, 5.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 1.0F));

        PartDefinition foot_r4 = leg_right3.addOrReplaceChild("foot_r4", CubeListBuilder.create().texOffs(22, 102).mirror().addBox(-2.0F, 0.0F, -6.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(@NotNull Shringasaurus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isCharging()) {
            if (entity.isRunning()) {
                this.animateWalk(ShringasaurusAnimations.RUN, limbSwing, limbSwingAmount, 1.3F, 2.6F);
            }
            else {
                this.animateWalk(ShringasaurusAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
            }
        }

		this.animateIdleSmooth(entity.idleAnimationState, ShringasaurusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, entity.isRunning() ? 2.6F : 4);
        this.animateSmooth(entity.eepyAnimationState, ShringasaurusAnimations.SLEEP, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack1AnimationState, ShringasaurusAnimations.ATTACK_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack2AnimationState, ShringasaurusAnimations.ATTACK_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.chargeStartAnimationState, ShringasaurusAnimations.CHARGE_START, ageInTicks, partialTicks);
        this.animateSmooth(entity.chargeAnimationState, ShringasaurusAnimations.CHARGE, ageInTicks, partialTicks ,1.25F);
        this.animateSmooth(entity.chargeEndAnimationState, ShringasaurusAnimations.CHARGE_END, ageInTicks, partialTicks);
        this.animate(entity.eatAnimationState, ShringasaurusAnimations.EAT_BLEND, ageInTicks);

        this.faceTarget(entity, netHeadYaw, headPitch, 2, neck);

        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail.yRot = Mth.lerp(0.2F, this.tail.yRot, tailYaw * 0.15F);
	}

    public List<ModelPart> getGlowParts(int feedings) {
        List<ModelPart> parts = new ArrayList<>();
        if (feedings >= 1) {
            parts.add(this.neck_glow1);
        }
        if (feedings >= 2) {
            parts.add(this.neck_glow2);
        }
        if (feedings >= 3) {
            parts.add(this.neck_glow3);
        }
        if (feedings >= 4) {
            parts.add(this.neck_glow4);
        }
        return parts;
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}