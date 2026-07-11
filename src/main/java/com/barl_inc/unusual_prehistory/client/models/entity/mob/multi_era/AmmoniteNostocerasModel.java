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
public class AmmoniteNostocerasModel extends UP2Model<Ammonite> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart shell_nos;
    private final ModelPart shell;
    private final ModelPart head_nos;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart arms_upper;
    private final ModelPart arms_left;
    private final ModelPart tentacle_left;
    private final ModelPart arms_right;
    private final ModelPart tentacle_right;
    private final ModelPart arms_lower;

	public AmmoniteNostocerasModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.shell_nos = this.body.getChild("shell_nos");
        this.shell = this.shell_nos.getChild("shell");
        this.head_nos = this.body.getChild("head_nos");
        this.head = this.head_nos.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
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

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -3.5F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -6.5F, 0.0F));

        PartDefinition shell_nos = body.addOrReplaceChild("shell_nos", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, -1.5F));

        PartDefinition shell = shell_nos.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 22).addBox(-1.5F, 0.0F, -2.5F, 3.0F, 5.0F, 8.0F, new CubeDeformation(0.25F))
                .texOffs(22, 22).addBox(-1.5F, -7.0F, 2.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.2F))
                .texOffs(0, 0).addBox(-4.5F, -10.0F, -3.5F, 9.0F, 3.0F, 9.0F, new CubeDeformation(0.25F))
                .texOffs(0, 12).addBox(-3.5F, -13.0F, -2.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(34, 22).addBox(-1.5F, -15.0F, -0.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head_nos = body.addOrReplaceChild("head_nos", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, -1.5F));

        PartDefinition head = head_nos.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 32).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(0, 35).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(0, 35).mirror().addBox(-0.5F, -1.5F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -0.5F, 0.5F));

        PartDefinition arms_upper = head.addOrReplaceChild("arms_upper", CubeListBuilder.create().texOffs(28, 12).addBox(-1.5F, 0.0F, -5.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -2.0F, -1.5F));

        PartDefinition arms_left = head.addOrReplaceChild("arms_left", CubeListBuilder.create().texOffs(39, 28).addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, -0.5F, -1.5F));

        PartDefinition tentacle_left = arms_left.addOrReplaceChild("tentacle_left", CubeListBuilder.create().texOffs(38, 30).addBox(0.0F, -1.5F, -6.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -4.0F));

        PartDefinition arms_right = head.addOrReplaceChild("arms_right", CubeListBuilder.create().texOffs(39, 28).mirror().addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, -0.5F, -1.5F));

        PartDefinition tentacle_right = arms_right.addOrReplaceChild("tentacle_right", CubeListBuilder.create().texOffs(38, 30).mirror().addBox(0.0F, -1.5F, -6.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, -4.0F));

        PartDefinition arms_lower = head.addOrReplaceChild("arms_lower", CubeListBuilder.create().texOffs(28, 17).addBox(-1.5F, 0.0F, -5.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 1.0F, -1.5F));

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