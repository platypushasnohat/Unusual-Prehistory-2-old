package com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.paleozoic.CamerocerasAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Cameroceras;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused, FieldCanBeLocal")
public class CamerocerasModel extends UP2Model<Cameroceras> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart shell;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart hood;
    private final ModelPart beak;
    private final ModelPart jaw_upper;
    private final ModelPart jaw;
    private final ModelPart umbrella;
    private final ModelPart arms_upper;
    private final ModelPart tentacles_upper;
    private final ModelPart arms_left;
    private final ModelPart tentacles_left;
    private final ModelPart arms_right;
    private final ModelPart tentacles_right;
    private final ModelPart arms_bottom;
    private final ModelPart tentacles_bottom;

	public CamerocerasModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.shell = this.body.getChild("shell");
        this.head = this.body.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.hood = this.head.getChild("hood");
        this.beak = this.head.getChild("beak");
        this.jaw_upper = this.beak.getChild("jaw_upper");
        this.jaw = this.beak.getChild("jaw");
        this.umbrella = this.head.getChild("umbrella");
        this.arms_upper = this.umbrella.getChild("arms_upper");
        this.tentacles_upper = this.arms_upper.getChild("tentacles_upper");
        this.arms_left = this.umbrella.getChild("arms_left");
        this.tentacles_left = this.arms_left.getChild("tentacles_left");
        this.arms_right = this.umbrella.getChild("arms_right");
        this.tentacles_right = this.arms_right.getChild("tentacles_right");
        this.arms_bottom = this.umbrella.getChild("arms_bottom");
        this.tentacles_bottom = this.arms_bottom.getChild("tentacles_bottom");
	}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.5F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shell = body.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 72).addBox(-7.0F, -6.5F, 0.0F, 14.0F, 13.0F, 39.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -3.5F, 39.0F, 8.0F, 7.0F, 65.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.5F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(106, 110).addBox(-5.0F, -5.5F, -11.0F, 10.0F, 11.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.5F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(56, 124).addBox(-1.0F, -3.5F, -3.5F, 4.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 1.0F, -5.5F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(56, 124).mirror().addBox(-3.0F, -3.5F, -3.5F, 4.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, 1.0F, -5.5F));

        PartDefinition hood = head.addOrReplaceChild("hood", CubeListBuilder.create().texOffs(0, 124).addBox(-6.0F, -1.0F, -8.0F, 12.0F, 3.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(78, 132).addBox(-6.0F, -3.0F, -8.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.5F, -2.0F));

        PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -12.1F));

        PartDefinition jaw_upper = beak.addOrReplaceChild("jaw_upper", CubeListBuilder.create().texOffs(69, 74).addBox(-2.0F, -3.0F, -4.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(73, 90).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.1F));

        PartDefinition jaw = beak.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(71, 83).addBox(-1.5F, 0.0F, -2.9F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition umbrella = head.addOrReplaceChild("umbrella", CubeListBuilder.create().texOffs(98, 0).addBox(-11.0F, -11.0F, 0.0F, 22.0F, 22.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, -11.1F));

        PartDefinition arms_upper = umbrella.addOrReplaceChild("arms_upper", CubeListBuilder.create().texOffs(89, 23).addBox(-11.0F, 0.0F, -9.0F, 22.0F, 0.0F, 9.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, -11.0F, 0.0F));

        PartDefinition tentacles_upper = arms_upper.addOrReplaceChild("tentacles_upper", CubeListBuilder.create().texOffs(104, 103).mirror().addBox(-12.0F, 0.0F, -7.0F, 24.0F, 0.0F, 7.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 0.0F, -9.0F));

        PartDefinition arms_left = umbrella.addOrReplaceChild("arms_left", CubeListBuilder.create().texOffs(143, -9).addBox(0.0F, -11.0F, -9.0F, 0.0F, 22.0F, 9.0F, new CubeDeformation(0.02F)), PartPose.offset(11.0F, 0.0F, 0.0F));

        PartDefinition tentacles_left = arms_left.addOrReplaceChild("tentacles_left", CubeListBuilder.create().texOffs(42, 124).addBox(0.0F, -12.0F, -7.0F, 0.0F, 24.0F, 7.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, -9.0F));

        PartDefinition arms_right = umbrella.addOrReplaceChild("arms_right", CubeListBuilder.create().texOffs(162, -9).mirror().addBox(0.0F, -11.0F, -9.0F, 0.0F, 22.0F, 9.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-11.0F, 0.0F, 0.0F));

        PartDefinition tentacles_right = arms_right.addOrReplaceChild("tentacles_right", CubeListBuilder.create().texOffs(42, 124).mirror().addBox(0.0F, -12.0F, -7.0F, 0.0F, 24.0F, 7.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 0.0F, -9.0F));

        PartDefinition arms_bottom = umbrella.addOrReplaceChild("arms_bottom", CubeListBuilder.create().texOffs(89, 33).addBox(-11.0F, 0.0F, -9.0F, 22.0F, 0.0F, 9.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 11.0F, 0.0F));

        PartDefinition tentacles_bottom = arms_bottom.addOrReplaceChild("tentacles_bottom", CubeListBuilder.create().texOffs(104, 103).addBox(-12.0F, 0.0F, -7.0F, 24.0F, 0.0F, 7.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, -9.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

	@Override
	public void setupAnim(@NotNull Cameroceras entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (!entity.isRocketing()) {
            if (entity.isInWaterOrBubble()) {
                if (entity.isCrawling()) {
                    this.animateWalk(CamerocerasAnimations.CRAWL_WALK, limbSwing, limbSwingAmount, 1.5F, 2.5F);
                } else {
                    this.animateWalk(CamerocerasAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 2.5F);
                }
            }
        }

        this.animateIdleSmooth(entity.swimIdleAnimationState, CamerocerasAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
        this.animateIdleSmooth(entity.crawlIdleAnimationState, CamerocerasAnimations.CRAWL_IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);
        this.animateSmooth(entity.attackAnimationState, CamerocerasAnimations.ATTACK_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.eyeAnimationState, CamerocerasAnimations.EYE_OVERLAY, ageInTicks, partialTicks);
        this.animateSmooth(entity.rocketAnimationState, CamerocerasAnimations.ROCKET, ageInTicks, partialTicks);
        this.animateSmooth(entity.parachuteAnimationState, CamerocerasAnimations.PARACHUTE, ageInTicks, partialTicks);

        if ((entity.getId() & 1) == 0) {
            this.animateSmooth(entity.flopAnimationState, CamerocerasAnimations.BEACHED1, ageInTicks, partialTicks);
        } else {
            this.animateSmooth(entity.flopAnimationState, CamerocerasAnimations.BEACHED2, ageInTicks, partialTicks);
        }
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}