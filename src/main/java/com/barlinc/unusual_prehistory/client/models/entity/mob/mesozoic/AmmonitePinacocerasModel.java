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
public class AmmonitePinacocerasModel extends UP2Model<Ammonite> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart shell_pina;
    private final ModelPart shell;
    private final ModelPart head_pina;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart arms_upper;
    private final ModelPart arms_left;
    private final ModelPart tentacle_left;
    private final ModelPart arms_right;
    private final ModelPart tentacle_right;
    private final ModelPart arms_lower;

	public AmmonitePinacocerasModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.shell_pina = this.body.getChild("shell_pina");
        this.shell = this.shell_pina.getChild("shell");
        this.head_pina = this.body.getChild("head_pina");
        this.head = this.head_pina.getChild("head");
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

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shell_pina = body.addOrReplaceChild("shell_pina", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -4.0F));

        PartDefinition shell = shell_pina.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, 2.0F, 2.0F, 14.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(22, 7).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head_pina = body.addOrReplaceChild("head_pina", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -4.0F));

        PartDefinition head = head_pina.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 23).addBox(-1.5F, -4.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(14, 23).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(2.0F, -3.0F, 1.0F));

        PartDefinition eyelash_r1 = eye_left.addOrReplaceChild("eyelash_r1", CubeListBuilder.create().texOffs(14, 28).addBox(0.0F, -1.0F, -2.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.0F, -2.0F, -1.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(14, 23).mirror().addBox(-1.0F, -2.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(-2.0F, -3.0F, 1.0F));

        PartDefinition eyelash_r2 = eye_right.addOrReplaceChild("eyelash_r2", CubeListBuilder.create().texOffs(14, 28).mirror().addBox(0.0F, -1.0F, -2.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -2.0F, -1.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition arms_upper = head.addOrReplaceChild("arms_upper", CubeListBuilder.create().texOffs(22, 0).addBox(-1.5F, 0.0F, -7.0F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -4.0F, -2.0F));

        PartDefinition arms_left = head.addOrReplaceChild("arms_left", CubeListBuilder.create().texOffs(26, 18).addBox(0.0F, -1.5F, -7.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, -2.5F, -2.0F));

        PartDefinition tentacle_left = arms_left.addOrReplaceChild("tentacle_left", CubeListBuilder.create().texOffs(28, 23).addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -6.0F));

        PartDefinition arms_right = head.addOrReplaceChild("arms_right", CubeListBuilder.create().texOffs(26, 18).mirror().addBox(0.0F, -1.5F, -7.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, -2.5F, -2.0F));

        PartDefinition tentacle_right = arms_right.addOrReplaceChild("tentacle_right", CubeListBuilder.create().texOffs(28, 23).mirror().addBox(0.0F, -1.5F, -5.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, -6.0F));

        PartDefinition arms_lower = head.addOrReplaceChild("arms_lower", CubeListBuilder.create().texOffs(19, 0).mirror().addBox(-1.5F, 0.0F, -7.0F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, -1.0F, -2.0F));

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