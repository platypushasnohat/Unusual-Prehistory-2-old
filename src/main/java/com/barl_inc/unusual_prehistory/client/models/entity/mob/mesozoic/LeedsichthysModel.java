package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.LeedsichthysAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Leedsichthys;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class LeedsichthysModel extends UP2Model<Leedsichthys> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart gills;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart mouth;
    private final ModelPart dorsal;
    private final ModelPart left_front_fin;
    private final ModelPart right_front_fin;
    private final ModelPart left_back_fin;
    private final ModelPart right_back_fin;
    private final ModelPart tail1;
    private final ModelPart ventral;
    private final ModelPart tail2;
    private final ModelPart top_fin;
    private final ModelPart bottom_fin;

	public LeedsichthysModel(ModelPart root) {
        super(1.0F, 0);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.gills = this.body.getChild("gills");
        this.head = this.body.getChild("head");
        this.jaw = this.body.getChild("jaw");
        this.mouth = this.jaw.getChild("mouth");
        this.dorsal = this.body.getChild("dorsal");
        this.left_front_fin = this.body.getChild("left_front_fin");
        this.right_front_fin = this.body.getChild("right_front_fin");
        this.left_back_fin = this.body.getChild("left_back_fin");
        this.right_back_fin = this.body.getChild("right_back_fin");
        this.tail1 = this.body.getChild("tail1");
        this.ventral = this.tail1.getChild("ventral");
        this.tail2 = this.tail1.getChild("tail2");
        this.top_fin = this.tail2.getChild("top_fin");
        this.bottom_fin = this.tail2.getChild("bottom_fin");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -30.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-27.5F, -36.0F, -42.0F, 55.0F, 66.0F, 121.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition gills = body.addOrReplaceChild("gills", CubeListBuilder.create().texOffs(0, 299).addBox(-29.5F, -10.5F, -10.5F, 59.0F, 21.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.5F, -27.5F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(231, 0).addBox(-27.5F, -44.0F, -68.0F, 55.0F, 44.0F, 67.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 8.0F, -41.0F));

        PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(10, 197).addBox(-28.5F, -2.95F, -77.0F, 57.0F, 17.0F, 85.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, -32.0F));

        PartDefinition mouth = jaw.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(5, 408).addBox(22.0F, -37.0F, 0.0F, 2.0F, 37.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(5, 408).mirror().addBox(-27.0F, -37.0F, 0.0F, 2.0F, 37.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.5F, -3.0F, -28.0F));

        PartDefinition dorsal = body.addOrReplaceChild("dorsal", CubeListBuilder.create().texOffs(37, 4).addBox(-0.5F, -20.0F, 24.0F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(56, 63).addBox(-0.5F, -20.0F, 0.0F, 2.0F, 24.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -36.0F, 50.0F));

        PartDefinition left_front_fin = body.addOrReplaceChild("left_front_fin", CubeListBuilder.create().texOffs(64, 0).addBox(0.0F, 0.0F, -6.0F, 15.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(209, 187).addBox(7.0F, 1.6F, -5.9F, 79.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(27.5F, 19.0F, -10.0F, 0.0F, -0.3491F, 0.7854F));

        PartDefinition right_front_fin = body.addOrReplaceChild("right_front_fin", CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-15.0F, 0.0F, -6.0F, 15.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(209, 187).mirror().addBox(-86.0F, 1.6F, -5.9F, 79.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-27.5F, 19.0F, -10.0F, 0.0F, 0.3491F, -0.7854F));

        PartDefinition left_back_fin = body.addOrReplaceChild("left_back_fin", CubeListBuilder.create().texOffs(160, 314).addBox(0.0F, -0.9848F, -5.8263F, 13.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(143, 327).addBox(9.0F, 0.6152F, -5.7264F, 53.0F, 3.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(26.5F, 26.0F, 14.0F, 0.1745F, -0.6109F, 1.0472F));

        PartDefinition right_back_fin = body.addOrReplaceChild("right_back_fin", CubeListBuilder.create().texOffs(160, 314).mirror().addBox(-13.0F, -0.9848F, -5.8263F, 13.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(143, 327).mirror().addBox(-62.0F, 0.6152F, -5.7264F, 53.0F, 3.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-26.5F, 26.0F, 14.0F, 0.1745F, 0.6109F, -1.0472F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(234, 229).addBox(-14.5F, -8.0F, 0.0F, 29.0F, 28.0F, 70.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -22.0F, 77.0F));

        PartDefinition ventral = tail1.addOrReplaceChild("ventral", CubeListBuilder.create().texOffs(37, 4).addBox(-0.5F, 21.0F, 24.0F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(56, 63).addBox(-0.5F, 0.0F, 0.0F, 2.0F, 24.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, 0.0F, 24.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 19.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 187).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 6.0F, 20.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 6.0F, 70.0F));

        PartDefinition top_fin = tail2.addOrReplaceChild("top_fin", CubeListBuilder.create().texOffs(80, 15).addBox(-2.0F, -71.0F, 17.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(40, 0).addBox(-2.0F, -71.0F, 1.0F, 4.0F, 71.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -1.0F));

        PartDefinition bottom_fin = tail2.addOrReplaceChild("bottom_fin", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 71.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(24, 0).addBox(-2.0F, 65.0F, 15.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(Leedsichthys entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble()) {
            this.animateWalk(LeedsichthysAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 3);
        }
        this.animateIdleSmooth(entity.swimIdleAnimationState, LeedsichthysAnimations.IDLE, ageInTicks, partialTicks, limbSwingAmount, 3);

        if ((entity.getId() & 1) == 0) {
            this.animateSmooth(entity.flopAnimationState, LeedsichthysAnimations.BEACHED1, ageInTicks, partialTicks);
        } else {
            this.animateSmooth(entity.flopAnimationState, LeedsichthysAnimations.BEACHED2, ageInTicks, partialTicks);
        }

        this.animateSmooth(entity.gulpAnimationState, LeedsichthysAnimations.BIGGULP_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.yawnAnimationState, LeedsichthysAnimations.YAWN_BLEND, ageInTicks, partialTicks);

        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail1.yRot = Mth.lerp(0.2F, this.tail1.yRot, tailYaw * 0.325F);
        this.tail2.yRot = Mth.lerp(0.2F, this.tail2.yRot, tailYaw * 0.1F);
        this.swim_control.xRot = (headPitch * ((float) Math.PI / 180F)) / 3;
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}