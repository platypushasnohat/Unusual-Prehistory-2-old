package com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.cenozoic.GiantCampanileAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.GiantCampanile;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class GiantCampanileModel extends UP2Model<GiantCampanile> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart shell;
    private final ModelPart body;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart thingy_left;
    private final ModelPart thingy_right;
    private final ModelPart foot;

	public GiantCampanileModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.shell = this.body_main.getChild("shell");
        this.body = this.body_main.getChild("body");
        this.eye_left = this.body.getChild("eye_left");
        this.eye_right = this.body.getChild("eye_right");
        this.thingy_left = this.body.getChild("thingy_left");
        this.thingy_right = this.body.getChild("thingy_right");
        this.foot = this.body.getChild("foot");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 0.0F));

        PartDefinition shell = body_main.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -22.0F, -10.0F, 20.0F, 22.0F, 19.0F, new CubeDeformation(0.0F))
                .texOffs(0, 41).addBox(-5.0F, -83.0F, -10.0F, 10.0F, 61.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 4.0F, -0.6109F, 0.0F, 0.0F));

        PartDefinition spikes_r1 = shell.addOrReplaceChild("spikes_r1", CubeListBuilder.create().texOffs(100, 0).mirror().addBox(-3.0F, -11.0F, 0.0F, 3.0F, 23.0F, 0.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-10.0F, -12.0F, 9.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition spikes_r2 = shell.addOrReplaceChild("spikes_r2", CubeListBuilder.create().texOffs(87, 0).mirror().addBox(-3.0F, -11.0F, 0.0F, 3.0F, 23.0F, 0.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(-10.0F, -12.0F, -10.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition spikes_r3 = shell.addOrReplaceChild("spikes_r3", CubeListBuilder.create().texOffs(100, 0).addBox(0.0F, -11.0F, 0.0F, 3.0F, 23.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(10.0F, -12.0F, 9.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition spikes_r4 = shell.addOrReplaceChild("spikes_r4", CubeListBuilder.create().texOffs(87, 0).addBox(0.0F, -11.0F, 0.0F, 3.0F, 23.0F, 0.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(10.0F, -12.0F, -10.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition spikes_r5 = shell.addOrReplaceChild("spikes_r5", CubeListBuilder.create().texOffs(88, 50).mirror().addBox(0.0F, -48.0F, -10.0F, 0.0F, 58.0F, 20.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offsetAndRotation(0.0F, -32.0F, -5.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition spikes_r6 = shell.addOrReplaceChild("spikes_r6", CubeListBuilder.create().texOffs(88, 50).addBox(0.0F, -48.0F, -10.0F, 0.0F, 58.0F, 20.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, -32.0F, -5.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(25, 98).addBox(-7.0F, -16.9375F, -7.3125F, 14.0F, 15.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(44, 57).addBox(6.955F, -18.9375F, -9.3125F, 0.0F, 16.0F, 19.0F, new CubeDeformation(0.02F))
                .texOffs(44, 57).mirror().addBox(-6.955F, -18.9375F, -9.3125F, 0.0F, 16.0F, 19.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(0.0F, 8.9375F, -0.6875F));

        PartDefinition eye_left = body.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(45, 50).addBox(-1.5F, -5.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(78, 35).addBox(-1.5F, -5.0F, 1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -15.9375F, -7.3125F));

        PartDefinition eye_right = body.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(45, 50).mirror().addBox(-1.5F, -5.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(78, 35).mirror().addBox(-1.5F, -5.0F, 1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, -15.9375F, -7.3125F));

        PartDefinition thingy_left = body.addOrReplaceChild("thingy_left", CubeListBuilder.create().texOffs(41, 41).addBox(0.0F, -1.0F, -5.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -11.9375F, -5.3125F));

        PartDefinition thingy_right = body.addOrReplaceChild("thingy_right", CubeListBuilder.create().texOffs(41, 41).mirror().addBox(-3.0F, -1.0F, -5.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, -11.9375F, -5.3125F));

        PartDefinition foot = body.addOrReplaceChild("foot", CubeListBuilder.create().texOffs(40, 41).addBox(-9.0F, -1.0F, -2.0F, 18.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.9375F, -7.3125F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(@NotNull GiantCampanile entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;
        if (!entity.isSitting()) {
            this.animateWalk(GiantCampanileAnimations.SLIDE, limbSwing, limbSwingAmount, 1, 2);
        }
        this.animateIdleSmooth(entity.idleAnimationState, GiantCampanileAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 2);
        this.animateSmooth(entity.sitAnimationState, GiantCampanileAnimations.MONOLITH, ageInTicks, partialTicks);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}