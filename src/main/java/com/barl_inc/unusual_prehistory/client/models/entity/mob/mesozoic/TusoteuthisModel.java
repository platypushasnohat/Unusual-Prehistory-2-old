package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.TusoteuthisAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Tusoteuthis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused, FieldCanBeLocal")
public class TusoteuthisModel extends UP2Model<Tusoteuthis> {

    private final ModelPart root;
    private final ModelPart spin_control;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart tentacle_control;
    private final ModelPart tentacles_front;
    private final ModelPart tentacles_left;
    private final ModelPart tentacles_right;
    private final ModelPart tentacles_back;
    private final ModelPart mantle;
    private final ModelPart head_fin;
    private final ModelPart head_fin_left;
    private final ModelPart head_fin_right;

	public TusoteuthisModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.spin_control = this.root.getChild("spin_control");
        this.body = this.spin_control.getChild("body");
        this.head = this.body.getChild("head");
        this.eye_left = this.head.getChild("eye_left");
        this.eye_right = this.head.getChild("eye_right");
        this.tentacle_control = this.head.getChild("tentacle_control");
        this.tentacles_front = this.tentacle_control.getChild("tentacles_front");
        this.tentacles_left = this.tentacle_control.getChild("tentacles_left");
        this.tentacles_right = this.tentacle_control.getChild("tentacles_right");
        this.tentacles_back = this.tentacle_control.getChild("tentacles_back");
        this.mantle = this.body.getChild("mantle");
        this.head_fin = this.mantle.getChild("head_fin");
        this.head_fin_left = this.head_fin.getChild("head_fin_left");
        this.head_fin_right = this.head_fin.getChild("head_fin_right");
	}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition spin_control = root.addOrReplaceChild("spin_control", CubeListBuilder.create(), PartPose.offset(0.0F, -55.0F, 0.0F));

        PartDefinition body = spin_control.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 41.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(88, 87).addBox(-10.0F, -8.0F, -10.0F, 20.0F, 15.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition eye_left = head.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(40, 130).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, 1.0F, 0.0F));

        PartDefinition eye_right = head.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(40, 130).mirror().addBox(-3.0F, -4.0F, -4.0F, 6.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-9.0F, 1.0F, 0.0F));

        PartDefinition tentacle_control = head.addOrReplaceChild("tentacle_control", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition tentacles_front = tentacle_control.addOrReplaceChild("tentacles_front", CubeListBuilder.create().texOffs(0, 130).addBox(-10.0F, 0.0F, 0.0F, 20.0F, 8.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, -10.0F));

        PartDefinition tentacles_left = tentacle_control.addOrReplaceChild("tentacles_left", CubeListBuilder.create().texOffs(88, 122).addBox(0.0F, 0.0F, -10.0F, 0.0F, 8.0F, 20.0F, new CubeDeformation(0.02F)), PartPose.offset(10.0F, 0.0F, 0.0F));

        PartDefinition tentacles_right = tentacle_control.addOrReplaceChild("tentacles_right", CubeListBuilder.create().texOffs(88, 122).mirror().addBox(0.0F, 0.0F, -10.0F, 0.0F, 8.0F, 20.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-10.0F, 0.0F, 0.0F));

        PartDefinition tentacles_back = tentacle_control.addOrReplaceChild("tentacles_back", CubeListBuilder.create().texOffs(0, 138).addBox(-10.0F, 0.0F, 0.0F, 20.0F, 8.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition mantle = body.addOrReplaceChild("mantle", CubeListBuilder.create().texOffs(0, 0).addBox(-22.0F, -55.0F, -16.0F, 44.0F, 55.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition head_fin = mantle.addOrReplaceChild("head_fin", CubeListBuilder.create().texOffs(0, 87).addBox(-22.0F, -43.0F, -1.0F, 44.0F, 43.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -26.0F, 1.0F));

        PartDefinition head_fin_left = head_fin.addOrReplaceChild("head_fin_left", CubeListBuilder.create().texOffs(128, 122).addBox(0.0F, -16.0F, 0.0F, 14.0F, 34.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(22.0F, -18.0F, -1.0F));

        PartDefinition head_fin_right = head_fin.addOrReplaceChild("head_fin_right", CubeListBuilder.create().texOffs(128, 122).mirror().addBox(-14.0F, -16.0F, 0.0F, 14.0F, 34.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-22.0F, -18.0F, -1.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

	@Override
	public void setupAnim(@NotNull Tusoteuthis entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble() && !entity.isAttackingOrFlashing()) {
            this.animateWalk(TusoteuthisAnimations.SWIM, limbSwing, limbSwingAmount, 2, 4);
        }

        this.animateIdleSmooth(entity.swimIdleAnimationState, TusoteuthisAnimations.SWIM_IDLE, ageInTicks, partialTicks, limbSwingAmount, 4);
        this.animateSmooth(entity.flopAnimationState, TusoteuthisAnimations.FLOP, ageInTicks, partialTicks);
        this.animateSmooth(entity.attackAnimationState, TusoteuthisAnimations.SUCK, ageInTicks, partialTicks);
        this.animateSmooth(entity.flashAnimationState, TusoteuthisAnimations.FLASHBANG, ageInTicks, partialTicks);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}