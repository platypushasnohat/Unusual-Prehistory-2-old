package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.RhizodusAnimations;
import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.RhizodusAttackAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Rhizodus;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class RhizodusModel extends UP2Model<Rhizodus> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart cheeks;
    private final ModelPart upper_jaw;
    private final ModelPart whisker_left1;
    private final ModelPart whisker_right1;
    private final ModelPart lower_jaw;
    private final ModelPart whisker_left3;
    private final ModelPart whisker_left2;
    private final ModelPart whisker_right3;
    private final ModelPart whisker_right2;
    private final ModelPart fin_left;
    private final ModelPart fin_right;
    private final ModelPart body_back;
    private final ModelPart back_fin_left;
    private final ModelPart back_fin_right;
    private final ModelPart tail;
    private final ModelPart tail_tip;

	public RhizodusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body_main = this.swim_control.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.cheeks = this.head.getChild("cheeks");
        this.upper_jaw = this.head.getChild("upper_jaw");
        this.whisker_left1 = this.upper_jaw.getChild("whisker_left1");
        this.whisker_right1 = this.upper_jaw.getChild("whisker_right1");
        this.lower_jaw = this.head.getChild("lower_jaw");
        this.whisker_left3 = this.lower_jaw.getChild("whisker_left3");
        this.whisker_left2 = this.lower_jaw.getChild("whisker_left2");
        this.whisker_right3 = this.lower_jaw.getChild("whisker_right3");
        this.whisker_right2 = this.lower_jaw.getChild("whisker_right2");
        this.fin_left = this.body_main.getChild("fin_left");
        this.fin_right = this.body_main.getChild("fin_right");
        this.body_back = this.body_main.getChild("body_back");
        this.back_fin_left = this.body_back.getChild("back_fin_left");
        this.back_fin_right = this.body_back.getChild("back_fin_right");
        this.tail = this.body_back.getChild("tail");
        this.tail_tip = this.tail.getChild("tail_tip");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 13.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(1, 0).addBox(-7.5F, -12.25F, -34.25F, 15.0F, 25.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.75F, 4.25F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -0.25F, -34.25F));

        PartDefinition cheeks = head.addOrReplaceChild("cheeks", CubeListBuilder.create().texOffs(163, 84).addBox(-14.0F, -11.0F, -2.0F, 15.0F, 18.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offset(6.5F, 3.0F, 0.0F));

        PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(1, 62).addBox(-8.5F, -12.0F, -26.0F, 17.0F, 12.0F, 29.0F, new CubeDeformation(0.0F))
                .texOffs(162, 48).addBox(-5.5F, -14.0F, -24.0F, 11.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(131, 149).addBox(-8.5F, 0.0F, -26.0F, 17.0F, 3.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 5.0F));

        PartDefinition whisker_left1 = upper_jaw.addOrReplaceChild("whisker_left1", CubeListBuilder.create().texOffs(162, 100).addBox(0.0F, 0.0F, -0.5F, 0.0F, 6.0F, 20.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(8.5F, -2.0F, -23.5F, 0.0F, 0.0F, -1.0472F));

        PartDefinition whisker_right1 = upper_jaw.addOrReplaceChild("whisker_right1", CubeListBuilder.create().texOffs(162, 100).mirror().addBox(0.0F, 0.0F, -0.5F, 0.0F, 6.0F, 20.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-8.5F, -2.0F, -23.5F, 0.0F, 0.0F, 1.0472F));

        PartDefinition lower_jaw = head.addOrReplaceChild("lower_jaw", CubeListBuilder.create().texOffs(0, 151).addBox(-8.5F, -1.0F, -25.0F, 17.0F, 3.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(163, 70).addBox(-8.5F, 0.0F, -5.0F, 17.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(131, 126).addBox(-8.5F, 2.0F, -25.0F, 17.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 4.0F));

        PartDefinition whisker_left3 = lower_jaw.addOrReplaceChild("whisker_left3", CubeListBuilder.create().texOffs(130, 169).addBox(0.0F, 0.0F, -0.5F, 0.0F, 8.0F, 18.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.5F, 5.0F, -21.5F, 0.0F, 0.0F, -0.3491F));

        PartDefinition whisker_left2 = lower_jaw.addOrReplaceChild("whisker_left2", CubeListBuilder.create().texOffs(162, 100).addBox(0.0F, 0.0F, -0.5F, 0.0F, 6.0F, 20.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(8.5F, 4.0F, -18.5F, -0.1745F, 0.0F, -0.7854F));

        PartDefinition whisker_right3 = lower_jaw.addOrReplaceChild("whisker_right3", CubeListBuilder.create().texOffs(130, 169).mirror().addBox(0.0F, 0.0F, -0.5F, 0.0F, 8.0F, 18.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 5.0F, -21.5F, 0.0F, 0.0F, 0.3491F));

        PartDefinition whisker_right2 = lower_jaw.addOrReplaceChild("whisker_right2", CubeListBuilder.create().texOffs(162, 100).mirror().addBox(0.0F, 0.0F, -0.5F, 0.0F, 6.0F, 20.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-8.5F, 4.0F, -18.5F, -0.1745F, 0.0F, 0.7854F));

        PartDefinition fin_left = body_main.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(164, 173).addBox(-2.0F, -1.0F, -2.0F, 13.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(108, 48).addBox(2.0F, 0.0F, -2.0F, 16.0F, 0.0F, 11.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(7.5F, 11.0F, -17.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition fin_right = body_main.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(164, 173).mirror().addBox(-11.0F, -1.0F, -2.0F, 13.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(108, 48).mirror().addBox(-18.0F, 0.0F, -2.0F, 16.0F, 0.0F, 11.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-7.5F, 11.0F, -17.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition body_back = body_main.addOrReplaceChild("body_back", CubeListBuilder.create().texOffs(0, 103).addBox(-4.5F, -9.0F, 0.0F, 9.0F, 19.0F, 29.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -1.0F, 7.0F));

        PartDefinition lilfin_r1 = body_back.addOrReplaceChild("lilfin_r1", CubeListBuilder.create().texOffs(90, 103).addBox(0.0F, -6.0F, 0.0F, 0.0F, 6.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, -9.0F, 24.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition back_fin_left = body_back.addOrReplaceChild("back_fin_left", CubeListBuilder.create().texOffs(26, 173).addBox(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 173).addBox(0.0F, 0.0F, -1.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.5F, 9.5F, 11.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition back_fin_right = body_back.addOrReplaceChild("back_fin_right", CubeListBuilder.create().texOffs(26, 173).mirror().addBox(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 173).mirror().addBox(-7.0F, 0.0F, -1.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 9.5F, 11.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition tail = body_back.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(108, 0).addBox(-4.5F, -8.5F, 0.0F, 9.0F, 19.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 29.0F));

        PartDefinition lilfin_r2 = tail.addOrReplaceChild("lilfin_r2", CubeListBuilder.create().texOffs(90, 119).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, -8.5F, 2.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition lilfin_r3 = tail.addOrReplaceChild("lilfin_r3", CubeListBuilder.create().texOffs(90, 112).addBox(0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(0.0F, 10.5F, 2.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition tail_tip = tail.addOrReplaceChild("tail_tip", CubeListBuilder.create().texOffs(76, 126).addBox(-1.0F, -13.5F, -1.8333F, 2.0F, 27.0F, 25.0F, new CubeDeformation(0.0F))
                .texOffs(76, 103).addBox(-1.0F, -8.5F, 23.1667F, 2.0F, 17.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(96, 62).addBox(0.0F, -15.5F, -1.8333F, 0.0F, 31.0F, 33.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 1.0F, 10.8333F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(@NotNull Rhizodus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble()) {
            if (entity.getPose() != UP2Poses.ATTACKING.get() && entity.getPose() != UP2Poses.GRABBING.get()) {
                if (entity.isRunning()) {
                    this.animateWalk(RhizodusAnimations.SWIM_CHASE, limbSwing, limbSwingAmount, 2, 4);
                } else {
                    this.animateWalk(RhizodusAnimations.SWIM, limbSwing, limbSwingAmount, 2, 4);
                }
            }
        } else {
            this.animateWalk(RhizodusAnimations.LAND_WALK, limbSwing, limbSwingAmount, 2, 4);
        }

		this.animateIdleSmooth(entity.idleAnimationState, RhizodusAnimations.LAND_IDLE, ageInTicks, partialTicks, limbSwingAmount, 4);
        this.animateIdleSmooth(entity.swimIdleAnimationState, RhizodusAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 4);

        this.animateSmooth(entity.attack1AnimationState, RhizodusAttackAnimations.ATTACK1, ageInTicks,partialTicks);
        this.animateSmooth(entity.attack2AnimationState, RhizodusAttackAnimations.ATTACK2, ageInTicks,partialTicks);
        this.animateSmooth(entity.landAttack1AnimationState, RhizodusAttackAnimations.LAND_BITE_BLEND1, ageInTicks,partialTicks);
        this.animateSmooth(entity.landAttack2AnimationState, RhizodusAttackAnimations.LAND_BITE_BLEND2, ageInTicks,partialTicks);
        this.animateSmooth(entity.suctionAttackAnimationState, RhizodusAttackAnimations.SUCTION_ATTACK, ageInTicks,partialTicks);

        if (entity.isInWaterOrBubble()) {
            this.root.xRot = headPitch * ((float) Math.PI / 180F);
        }

        float tailYaw = entity.getTailYaw(partialTicks);
        this.body_back.yRot = Mth.lerp(0.2F, this.body_back.yRot, tailYaw * 0.35F);
        this.tail.yRot = Mth.lerp(0.2F, this.tail.yRot, tailYaw * 0.3F);
        this.tail_tip.yRot = Mth.lerp(0.2F, this.tail_tip.yRot, tailYaw * 0.25F);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}