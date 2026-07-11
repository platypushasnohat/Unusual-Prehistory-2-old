package com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.SpikeToothedSalmonAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.SpikeToothedSalmon;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class SpikeToothedSalmonModel extends UP2Model<SpikeToothedSalmon> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart dorsal_fin;
    private final ModelPart jaw_upper;
    private final ModelPart jaw_lower;
    private final ModelPart fin_left;
    private final ModelPart fin_right;
    private final ModelPart back_fin_left;
    private final ModelPart back_fin_right;
    private final ModelPart tail;
    private final ModelPart tail_fin;

	public SpikeToothedSalmonModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.dorsal_fin = this.body.getChild("dorsal_fin");
        this.jaw_upper = this.body.getChild("jaw_upper");
        this.jaw_lower = this.body.getChild("jaw_lower");
        this.fin_left = this.body.getChild("fin_left");
        this.fin_right = this.body.getChild("fin_right");
        this.back_fin_left = this.body.getChild("back_fin_left");
        this.back_fin_right = this.body.getChild("back_fin_right");
        this.tail = this.body.getChild("tail");
        this.tail_fin = this.tail.getChild("tail_fin");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -10.0F, -11.0F, 7.0F, 23.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition dorsal_fin = body.addOrReplaceChild("dorsal_fin", CubeListBuilder.create().texOffs(0, 46).addBox(0.0F, -6.5F, -4.0F, 0.0F, 9.0F, 14.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -8.5F, 9.0F));

        PartDefinition jaw_upper = body.addOrReplaceChild("jaw_upper", CubeListBuilder.create().texOffs(62, 41).addBox(-2.5F, -2.0F, -5.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(28, 46).addBox(-2.5F, -4.0F, -12.0F, 5.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(16, 69).addBox(-2.5F, -2.0F, -12.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 67).addBox(2.5F, -3.0F, -10.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F))
                .texOffs(28, 67).mirror().addBox(-7.5F, -3.0F, -10.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(62, 48).addBox(-1.5F, -2.0F, -10.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, -11.0F));

        PartDefinition zombietusk_r1 = jaw_upper.addOrReplaceChild("zombietusk_r1", CubeListBuilder.create().texOffs(1, 78).mirror().addBox(-5.0F, 0.0F, -1.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.5F, -3.0F, -9.0F, 0.0F, 0.0F, -1.3963F));

        PartDefinition zombietusk_r2 = jaw_upper.addOrReplaceChild("zombietusk_r2", CubeListBuilder.create().texOffs(1, 78).addBox(0.0F, 0.0F, -1.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.5F, -3.0F, -9.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition jaw_lower = body.addOrReplaceChild("jaw_lower", CubeListBuilder.create().texOffs(28, 70).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(60, 31).addBox(-1.5F, 1.0F, -11.0F, 3.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(62, 54).addBox(-1.5F, 0.0F, -10.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(16, 71).addBox(-1.5F, 0.0F, -11.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, -11.0F));

        PartDefinition fin_left = body.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(60, 0).addBox(0.0F, 0.0F, -4.5F, 6.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.5F, 13.0F, -0.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition fin_right = body.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-6.0F, 0.0F, -4.5F, 6.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 13.0F, -0.5F, 0.0F, 0.0F, -0.7854F));

        PartDefinition back_fin_left = body.addOrReplaceChild("back_fin_left", CubeListBuilder.create().texOffs(28, 60).addBox(0.0F, 0.0F, -2.0F, 7.0F, 0.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.5F, 13.0F, 9.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition back_fin_right = body.addOrReplaceChild("back_fin_right", CubeListBuilder.create().texOffs(28, 60).mirror().addBox(-7.0F, 0.0F, -2.0F, 7.0F, 0.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.5F, 13.0F, 9.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(56, 60).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(44, 67).addBox(0.0F, -5.0F, 2.0F, 0.0F, 10.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 5.0F, 12.0F));

        PartDefinition tail_fin = tail.addOrReplaceChild("tail_fin", CubeListBuilder.create().texOffs(60, 9).addBox(0.0F, -6.5F, 0.0F, 0.0F, 13.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.5F, 7.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(SpikeToothedSalmon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble() || entity.isLeaping()) {
            if (entity.isRunning()) {
                this.animateWalk(SpikeToothedSalmonAnimations.SWIM_FAST, limbSwing, limbSwingAmount, 1.5F, 3);
            } else {
                this.animateWalk(SpikeToothedSalmonAnimations.SWIM, limbSwing, limbSwingAmount, 2, 4);
            }
        }

        this.animateIdleSmooth(entity.swimIdleAnimationState, SpikeToothedSalmonAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, entity.isRunning() ? 3 : 4);
		this.animateSmooth(entity.flopAnimationState, SpikeToothedSalmonAnimations.FLOP, ageInTicks,partialTicks);
		this.animateSmooth(entity.attackAnimationState, SpikeToothedSalmonAnimations.ATTACK_BLEND, ageInTicks,partialTicks);
        this.animateSmooth(entity.attackZombieAnimationState, SpikeToothedSalmonAnimations.ATTACK_BLEND_UNDEAD, ageInTicks,partialTicks);
        this.animateSmooth(entity.zombieAnimationState, SpikeToothedSalmonAnimations.UNDEAD_BLEND, ageInTicks,partialTicks);

        if (entity.isInWaterOrBubble() || entity.isLeaping()) {
            this.swim_control.xRot = headPitch * ((float) Math.PI / 180F);
        }
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}