package com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.cenozoic.LeptictidiumAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Leptictidium;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class LeptictidiumModel extends UP2Model<Leptictidium> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart snout;
    private final ModelPart ear_left;
    private final ModelPart ear_right;
    private final ModelPart arm_left;
    private final ModelPart arm_right;
    private final ModelPart tail1;
    private final ModelPart leg_control;
    private final ModelPart leg_left1;
    private final ModelPart leg_left2;
    private final ModelPart leg_left3;
    private final ModelPart leg_right1;
    private final ModelPart leg_right2;
    private final ModelPart leg_right3;

	public LeptictidiumModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.snout = this.body.getChild("snout");
        this.ear_left = this.body.getChild("ear_left");
        this.ear_right = this.body.getChild("ear_right");
        this.arm_left = this.body.getChild("arm_left");
        this.arm_right = this.body.getChild("arm_right");
        this.tail1 = this.body.getChild("tail1");
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

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -9.1F, -0.5F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -7.0F, 5.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 3.5F));

        PartDefinition snout = body.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(8, 14).addBox(-1.0F, -0.5F, -6.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(8, 14).addBox(-1.0F, -2.5F, -7.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.5F, -7.0F));

        PartDefinition ear_left = body.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -3.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(2.5F, -5.0F, -4.0F));

        PartDefinition ear_right = body.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(18, 0).mirror().addBox(-3.0F, -3.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-2.5F, -5.0F, -4.0F));

        PartDefinition arm_left = body.addOrReplaceChild("arm_left", CubeListBuilder.create().texOffs(1, 14).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(2.0F, 1.0F, -5.0F));

        PartDefinition arm_right = body.addOrReplaceChild("arm_right", CubeListBuilder.create().texOffs(1, 14).mirror().addBox(-1.5F, 0.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-2.0F, 1.0F, -5.0F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(10, 19).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 13.0F, new CubeDeformation(0.0025F))
                .texOffs(20, 22).addBox(-0.5F, -4.0F, 13.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 2.5F));

        PartDefinition leg_left1 = leg_control.addOrReplaceChild("leg_left1", CubeListBuilder.create().texOffs(17, 14).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)), PartPose.offset(2.0F, 0.0F, 0.0F));

        PartDefinition leg_left2 = leg_left1.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(24, 14).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 1.0F));

        PartDefinition leg_left3 = leg_left2.addOrReplaceChild("leg_left3", CubeListBuilder.create().texOffs(-2, 19).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition leg_right1 = leg_control.addOrReplaceChild("leg_right1", CubeListBuilder.create().texOffs(17, 14).mirror().addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.05F)).mirror(false), PartPose.offset(-2.0F, 0.0F, 0.0F));

        PartDefinition leg_right2 = leg_right1.addOrReplaceChild("leg_right2", CubeListBuilder.create().texOffs(24, 14).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 1.0F, 1.0F));

        PartDefinition leg_right3 = leg_right2.addOrReplaceChild("leg_right3", CubeListBuilder.create().texOffs(-2, 19).mirror().addBox(-1.5F, 0.0F, -3.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 4.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Leptictidium entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

		if (!entity.isInWaterOrBubble()) {
            if (entity.isRunning()) this.animateWalk(LeptictidiumAnimations.RUN, limbSwing, limbSwingAmount, 1.3F, 2.6F);
			else this.animateWalk(LeptictidiumAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
		}

		if (this.young) this.applyStatic(LeptictidiumAnimations.BABY_TRANSFORM);

		this.animateIdleSmooth(entity.idleAnimationState, LeptictidiumAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 4);
        this.animateSmooth(entity.attackAnimationState, LeptictidiumAnimations.ATTACK, ageInTicks, partialTicks);
        this.animateSmooth(entity.swimAnimationState, LeptictidiumAnimations.SWIM, ageInTicks, partialTicks);
        this.animateSmooth(entity.preenAnimationState, LeptictidiumAnimations.IDLE_PREEN, ageInTicks, partialTicks);
        this.animateSmooth(entity.sniffAnimationState, LeptictidiumAnimations.SNIFF_BLEND, ageInTicks, partialTicks);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}