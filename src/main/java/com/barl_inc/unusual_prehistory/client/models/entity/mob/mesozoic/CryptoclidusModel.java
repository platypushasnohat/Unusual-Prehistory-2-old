package com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.client.animations.entity.mob.mesozoic.CryptoclidusAnimations;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Cryptoclidus;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused, FieldCanBeLocal")
public class CryptoclidusModel extends UP2Model<Cryptoclidus> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart dewlap;
    private final ModelPart tail;
    private final ModelPart tail_fluke;
    private final ModelPart front_fin_control;
    private final ModelPart front_fin_left;
    private final ModelPart front_fin_right;
    private final ModelPart back_fin_control;
    private final ModelPart back_fin_left;
    private final ModelPart back_fin_right;

	public CryptoclidusModel(ModelPart root) {
        super(0.5F, 24);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body_main = this.swim_control.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.dewlap = this.head.getChild("dewlap");
        this.tail = this.body.getChild("tail");
        this.tail_fluke = this.tail.getChild("tail_fluke");
        this.front_fin_control = this.body_main.getChild("front_fin_control");
        this.front_fin_left = this.front_fin_control.getChild("front_fin_left");
        this.front_fin_right = this.front_fin_control.getChild("front_fin_right");
        this.back_fin_control = this.body_main.getChild("back_fin_control");
        this.back_fin_left = this.back_fin_control.getChild("back_fin_left");
        this.back_fin_right = this.back_fin_control.getChild("back_fin_right");
	}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.5F, -5.0F, -12.0F, 17.0F, 11.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(66, 35).addBox(5.5F, -8.0F, -12.0F, 0.0F, 3.0F, 24.0F, new CubeDeformation(0.0025F))
                .texOffs(66, 35).mirror().addBox(-5.5F, -8.0F, -12.0F, 0.0F, 3.0F, 24.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 35).addBox(-3.5F, -3.0F, -26.0F, 7.0F, 6.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(24, 88).addBox(0.0F, -7.0F, -18.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 3.0F, -12.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(78, 81).addBox(-4.5F, -3.0F, -7.0F, 9.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(69, 101).addBox(2.5F, -4.0F, -7.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 88).addBox(-2.5F, -3.0F, -14.0F, 5.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(69, 101).mirror().addBox(-4.5F, -4.0F, -7.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -26.0F));

        PartDefinition thingie_r1 = head.addOrReplaceChild("thingie_r1", CubeListBuilder.create().texOffs(32, 100).mirror().addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -3.0F, -7.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition teeth_r1 = head.addOrReplaceChild("teeth_r1", CubeListBuilder.create().texOffs(80, 100).mirror().addBox(-0.01F, 0.0F, -1.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 0.0F, -6.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition teeth_r2 = head.addOrReplaceChild("teeth_r2", CubeListBuilder.create().texOffs(40, 98).mirror().addBox(-0.01F, 0.0F, -3.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 0.0F, -11.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition teeth_r3 = head.addOrReplaceChild("teeth_r3", CubeListBuilder.create().texOffs(40, 98).addBox(0.01F, 0.0F, -3.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.5F, 0.0F, -11.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition teeth_r4 = head.addOrReplaceChild("teeth_r4", CubeListBuilder.create().texOffs(80, 100).addBox(0.01F, 0.0F, -1.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.5F, 0.0F, -6.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition teeth_r5 = head.addOrReplaceChild("teeth_r5", CubeListBuilder.create().texOffs(42, 67).addBox(-4.0F, 0.0F, 0.0F, 9.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-0.5F, 0.0F, -7.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition teeth_r6 = head.addOrReplaceChild("teeth_r6", CubeListBuilder.create().texOffs(68, 98).addBox(-2.0F, 0.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-0.5F, 0.0F, -14.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition thingie_r2 = head.addOrReplaceChild("thingie_r2", CubeListBuilder.create().texOffs(32, 100).addBox(0.0F, -4.0F, -1.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.5F, -3.0F, -7.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(82, 0).addBox(-4.5F, 0.0F, -7.0F, 9.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(78, 91).addBox(-2.5F, 0.0F, -14.0F, 5.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition spikes_r1 = jaw.addOrReplaceChild("spikes_r1", CubeListBuilder.create().texOffs(16, 100).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 2.0F, -4.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition teeth_r7 = jaw.addOrReplaceChild("teeth_r7", CubeListBuilder.create().texOffs(54, 98).mirror().addBox(-0.01F, -2.0F, -3.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 0.0F, -11.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition teeth_r8 = jaw.addOrReplaceChild("teeth_r8", CubeListBuilder.create().texOffs(90, 100).mirror().addBox(-0.01F, -2.0F, -1.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 0.0F, -6.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition spikes_r2 = jaw.addOrReplaceChild("spikes_r2", CubeListBuilder.create().texOffs(16, 100).addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.5F, 2.0F, -4.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition teeth_r9 = jaw.addOrReplaceChild("teeth_r9", CubeListBuilder.create().texOffs(100, 100).addBox(-2.0F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-0.5F, 0.0F, -14.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition teeth_r10 = jaw.addOrReplaceChild("teeth_r10", CubeListBuilder.create().texOffs(54, 98).addBox(0.01F, -2.0F, -3.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.5F, 0.0F, -11.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition teeth_r11 = jaw.addOrReplaceChild("teeth_r11", CubeListBuilder.create().texOffs(42, 69).addBox(-4.0F, -2.0F, 0.0F, 9.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(-0.5F, 0.0F, -7.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition teeth_r12 = jaw.addOrReplaceChild("teeth_r12", CubeListBuilder.create().texOffs(90, 100).addBox(0.01F, -2.0F, -1.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.5F, 0.0F, -6.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition dewlap = head.addOrReplaceChild("dewlap", CubeListBuilder.create().texOffs(45, 20).addBox(0.0F, 0.0F, -6.0F, 0.0F, 10.0F, 22.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 2.0F, -3.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 67).addBox(-2.5F, -2.5F, -2.0F, 5.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.5F, 12.0F));

        PartDefinition tail_fluke = tail.addOrReplaceChild("tail_fluke", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 9.7929F));

        PartDefinition fluke_r1 = tail_fluke.addOrReplaceChild("fluke_r1", CubeListBuilder.create().texOffs(82, 19).mirror().addBox(-1.0F, -4.5F, -4.5F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 3.7071F, 0.7854F, 0.0F, 0.0F));

        PartDefinition front_fin_control = body_main.addOrReplaceChild("front_fin_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition front_fin_left = front_fin_control.addOrReplaceChild("front_fin_left", CubeListBuilder.create().texOffs(0, 98).addBox(0.0F, -1.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(66, 62).addBox(2.0F, -1.0F, -3.0F, 20.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(82, 9).addBox(22.0F, 0.5F, -3.0F, 5.0F, 0.0F, 10.0F, new CubeDeformation(0.0025F)), PartPose.offset(8.5F, 5.0F, -7.0F));

        PartDefinition front_fin_right = front_fin_control.addOrReplaceChild("front_fin_right", CubeListBuilder.create().texOffs(0, 98).mirror().addBox(-2.0F, -1.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(66, 62).mirror().addBox(-22.0F, -1.0F, -3.0F, 20.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(82, 9).mirror().addBox(-27.0F, 0.5F, -3.0F, 5.0F, 0.0F, 10.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-8.5F, 5.0F, -7.0F));

        PartDefinition back_fin_control = body_main.addOrReplaceChild("back_fin_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition back_fin_left = back_fin_control.addOrReplaceChild("back_fin_left", CubeListBuilder.create().texOffs(78, 72).addBox(0.0F, -1.0F, -2.0F, 18.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(8.5F, 5.0F, 9.0F));

        PartDefinition back_fin_right = back_fin_control.addOrReplaceChild("back_fin_right", CubeListBuilder.create().texOffs(78, 72).mirror().addBox(-18.0F, -1.0F, -2.0F, 18.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.5F, 5.0F, 9.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

	@Override
	public void setupAnim(@NotNull Cryptoclidus entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;
        float deg = ((float) Math.PI / 180);

        if (entity.isInWaterOrBubble()) {
            if (entity.isRunning()) this.animateWalk(CryptoclidusAnimations.SWIM_FAST, limbSwing, limbSwingAmount, 1.5F, 3);
            else this.animateWalk(CryptoclidusAnimations.SWIM, limbSwing, limbSwingAmount, 2, 4);
        } else {
            this.animateWalk(CryptoclidusAnimations.LAND_WALK, limbSwing, limbSwingAmount, 2, 4);
        }

        this.animateIdleSmooth(entity.swimIdleAnimationState, CryptoclidusAnimations.SWIM_IDLE, ageInTicks, partialTicks, limbSwingAmount, entity.isRunning() ? 3 : 4);
        this.animateIdleSmooth(entity.idleAnimationState, CryptoclidusAnimations.LAND_IDLE, ageInTicks, partialTicks, limbSwingAmount, 4);
        this.animateSmooth(entity.attackAnimationState, CryptoclidusAnimations.BITE_BLEND, ageInTicks, partialTicks);

        if (entity.isInWaterOrBubble()) {
            this.swim_control.xRot = headPitch * ((float) Math.PI / 180F);
        }

        this.neck.xRot += headPitch * deg / 3;
        this.neck.yRot += netHeadYaw * deg / 3;
        this.head.xRot += headPitch * deg / 6;
        this.head.yRot += netHeadYaw * deg / 6;

        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail.yRot = Mth.lerp(0.2F, this.tail.yRot, tailYaw * 0.2F);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}