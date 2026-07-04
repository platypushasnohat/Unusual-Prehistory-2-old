package com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.mesozoic.AmmoniteAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.multi_era.Ammonite;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class AmmoniteHoplitesModel extends UP2Model<Ammonite> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart shell;
    private final ModelPart head;
    private final ModelPart eyes;
    private final ModelPart arms_upper;
    private final ModelPart arms_left;
    private final ModelPart tentacle_left;
    private final ModelPart arms_right;
    private final ModelPart tentacle_right;
    private final ModelPart arms_lower;

	public AmmoniteHoplitesModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.shell = this.body.getChild("shell");
        this.head = this.body.getChild("head");
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

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -7.5F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shell = body.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 22).addBox(-3.0F, -12.5F, 0.0F, 6.0F, 15.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(30, 22).addBox(-3.0F, -12.5F, -5.0F, 6.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(36, 16).addBox(-3.0F, -4.5F, -9.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -13.5F, -6.0F, 4.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -1.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(30, 37).addBox(-2.5F, -2.5F, -6.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -1.0F));

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create().texOffs(0, 46).addBox(-3.5F, -1.0F, -2.0F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -3.0F));

        PartDefinition arms_upper = head.addOrReplaceChild("arms_upper", CubeListBuilder.create().texOffs(36, 0).addBox(-2.5F, 0.0F, -8.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -2.5F, -6.0F));

        PartDefinition arms_left = head.addOrReplaceChild("arms_left", CubeListBuilder.create().texOffs(27, 41).addBox(0.0F, -2.5F, -7.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(2.5F, 0.0F, -6.0F));

        PartDefinition tentacle_left = arms_left.addOrReplaceChild("tentacle_left", CubeListBuilder.create().texOffs(27, 46).addBox(0.0F, -2.5F, -7.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -7.0F));

        PartDefinition arms_right = head.addOrReplaceChild("arms_right", CubeListBuilder.create().texOffs(13, 51).addBox(0.0F, -2.5F, -7.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(-2.5F, 0.0F, -6.0F));

        PartDefinition tentacle_right = arms_right.addOrReplaceChild("tentacle_right", CubeListBuilder.create().texOffs(27, 51).mirror().addBox(0.0F, -2.5F, -7.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, -7.0F));

        PartDefinition arms_lower = head.addOrReplaceChild("arms_lower", CubeListBuilder.create().texOffs(31, 8).addBox(-2.5F, 0.0F, -8.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 2.5F, -6.0F));

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