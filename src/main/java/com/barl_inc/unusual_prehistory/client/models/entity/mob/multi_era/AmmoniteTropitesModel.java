package com.barl_inc.unusual_prehistory.client.models.entity.mob.multi_era;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.multi_era.AmmoniteAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.multi_era.Ammonite;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class AmmoniteTropitesModel extends UP2Model<Ammonite> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart shell_trop;
    private final ModelPart shell;
    private final ModelPart head_trop;
    private final ModelPart head;
    private final ModelPart eyes;
    private final ModelPart arms_upper;
    private final ModelPart arms_left;
    private final ModelPart tentacle_left;
    private final ModelPart arms_right;
    private final ModelPart tentacle_right;
    private final ModelPart arms_lower;

	public AmmoniteTropitesModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.shell_trop = this.body.getChild("shell_trop");
        this.shell = this.shell_trop.getChild("shell");
        this.head_trop = this.body.getChild("head_trop");
        this.head = this.head_trop.getChild("head");
        this.eyes = this.head.getChild("eyes");
        this.arms_upper = this.head.getChild("arms_upper");
        this.arms_left = this.head.getChild("arms_left");
        this.tentacle_left = this.arms_left.getChild("tentacle_left");
        this.arms_right = this.head.getChild("arms_right");
        this.tentacle_right = this.arms_right.getChild("tentacle_right");
        this.arms_lower = this.head.getChild("arms_lower");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 2.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shell_trop = body.addOrReplaceChild("shell_trop", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, -1.0F));

        PartDefinition shell = shell_trop.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -8.5F, -3.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-5.0F, -2.5F, 0.0F, 10.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head_trop = body.addOrReplaceChild("head_trop", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, -1.0F));

        PartDefinition head = head_trop.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 29).addBox(-4.0F, -2.5F, -4.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(34, 16).addBox(-5.0F, -1.5F, -1.49F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(34, 16).mirror().addBox(4.0F, -1.5F, -1.49F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -2.5F));

        PartDefinition arms_upper = head.addOrReplaceChild("arms_upper", CubeListBuilder.create().texOffs(24, 29).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -1.5F, -4.0F));

        PartDefinition arms_left = head.addOrReplaceChild("arms_left", CubeListBuilder.create().texOffs(41, 22).addBox(0.0F, -1.5F, -4.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(3.0F, 0.0F, -4.0F));

        PartDefinition tentacle_left = arms_left.addOrReplaceChild("tentacle_left", CubeListBuilder.create().texOffs(41, 32).addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition arms_right = head.addOrReplaceChild("arms_right", CubeListBuilder.create().texOffs(41, 22).mirror().addBox(0.0F, -1.5F, -4.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-3.0F, 0.0F, -4.0F));

        PartDefinition tentacle_right = arms_right.addOrReplaceChild("tentacle_right", CubeListBuilder.create().texOffs(41, 32).mirror().addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition arms_lower = head.addOrReplaceChild("arms_lower", CubeListBuilder.create().texOffs(24, 33).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 1.5F, -4.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Ammonite entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble()) {
            this.animateWalk(AmmoniteAnimations.SWIM, limbSwing, limbSwingAmount, 2, 4);
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, AmmoniteAnimations.SWIM, ageInTicks, partialTicks, limbSwingAmount, 4);
        this.animateSmooth(entity.hideAnimationState, AmmoniteAnimations.HIT_BLEND, ageInTicks, partialTicks);

        if ((entity.getId() & 1) == 0) {
            this.animateSmooth(entity.flopAnimationState, AmmoniteAnimations.FLOP1, ageInTicks, partialTicks);
        } else {
            this.animateSmooth(entity.flopAnimationState, AmmoniteAnimations.FLOP2, ageInTicks, partialTicks);
        }

        if (entity.isInWaterOrBubble()) {
            this.swim_control.xRot = headPitch * -((float) Math.PI / 180F);
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}