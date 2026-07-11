package com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.client.animations.entity.mob.mesozoic.PrognathodonAnimations;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Prognathodon;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused, FieldCanBeLocal")
public class PrognathodonModel extends UP2Model<Prognathodon> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw_top;
    private final ModelPart eye_left;
    private final ModelPart eye_right;
    private final ModelPart nostril_left;
    private final ModelPart nostril_right;
    private final ModelPart jaw;
    private final ModelPart tongue1;
    private final ModelPart tongue2;
    private final ModelPart throat;
    private final ModelPart fin_left;
    private final ModelPart fin_right;
    private final ModelPart fin_back_left;
    private final ModelPart fin_back_right;
    private final ModelPart tail1;
    private final ModelPart tail2;

	public PrognathodonModel(ModelPart root) {
        super(0.25F, 72);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.jaw_top = this.head.getChild("jaw_top");
        this.eye_left = this.jaw_top.getChild("eye_left");
        this.eye_right = this.jaw_top.getChild("eye_right");
        this.nostril_left = this.jaw_top.getChild("nostril_left");
        this.nostril_right = this.jaw_top.getChild("nostril_right");
        this.jaw = this.head.getChild("jaw");
        this.tongue1 = this.jaw.getChild("tongue1");
        this.tongue2 = this.tongue1.getChild("tongue2");
        this.throat = this.jaw.getChild("throat");
        this.fin_left = this.body.getChild("fin_left");
        this.fin_right = this.body.getChild("fin_right");
        this.fin_back_left = this.body.getChild("fin_back_left");
        this.fin_back_right = this.body.getChild("fin_back_right");
        this.tail1 = this.body.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
	}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-16.5F, -14.0F, -27.0F, 33.0F, 35.0F, 54.0F, new CubeDeformation(0.0F))
                .texOffs(115, 109).addBox(0.0F, -19.0F, -28.0F, 0.0F, 13.0F, 55.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(88, 151).addBox(0.0F, -11.0F, -15.0F, 0.0F, 24.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(174, 0).addBox(-5.5F, -9.0F, -13.0F, 11.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -27.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -6.0F));

        PartDefinition jaw_top = head.addOrReplaceChild("jaw_top", CubeListBuilder.create().texOffs(113, 90).addBox(-5.5F, -5.0F, -30.0F, 11.0F, 8.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(8, 31).addBox(-4.5F, 3.0F, -29.0F, 9.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(148, 5).addBox(-6.5F, 3.0F, -16.0F, 13.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(174, 36).addBox(-7.5F, -5.0F, -17.0F, 15.0F, 8.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(148, 14).addBox(5.5F, -7.0F, -17.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(148, 14).mirror().addBox(-7.5F, -7.0F, -17.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition eye_left = jaw_top.addOrReplaceChild("eye_left", CubeListBuilder.create().texOffs(153, 119).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offset(7.0F, -2.5F, -12.0F));

        PartDefinition eye_right = jaw_top.addOrReplaceChild("eye_right", CubeListBuilder.create().texOffs(153, 119).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.02F)), PartPose.offset(-7.0F, -2.5F, -12.0F));

        PartDefinition nostril_left = jaw_top.addOrReplaceChild("nostril_left", CubeListBuilder.create().texOffs(149, 96).addBox(-1.5F, -0.5F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.01F)), PartPose.offset(2.0F, -4.5F, -21.5F));

        PartDefinition nostril_right = jaw_top.addOrReplaceChild("nostril_right", CubeListBuilder.create().texOffs(149, 96).mirror().addBox(-1.5F, -0.5F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(-0.01F)).mirror(false), PartPose.offset(-2.0F, -4.5F, -21.5F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(174, 61).addBox(-7.5F, 0.0F, -17.0F, 15.0F, 3.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(77, 124).addBox(-5.5F, 0.0F, -30.0F, 11.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition teeth_r1 = jaw.addOrReplaceChild("teeth_r1", CubeListBuilder.create().texOffs(100, 84).mirror().addBox(0.0F, -4.0F, -8.0F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.5F, 0.0F, -21.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition teeth_r2 = jaw.addOrReplaceChild("teeth_r2", CubeListBuilder.create().texOffs(73, 84).mirror().addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.5F, 0.0F, -16.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition teeth_r3 = jaw.addOrReplaceChild("teeth_r3", CubeListBuilder.create().texOffs(174, 81).addBox(-11.0F, -4.0F, 0.0F, 11.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, 0.0F, -30.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition teeth_r4 = jaw.addOrReplaceChild("teeth_r4", CubeListBuilder.create().texOffs(100, 84).addBox(0.0F, -4.0F, -8.0F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5F, 0.0F, -21.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition teeth_r5 = jaw.addOrReplaceChild("teeth_r5", CubeListBuilder.create().texOffs(73, 84).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.5F, 0.0F, -16.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition tongue1 = jaw.addOrReplaceChild("tongue1", CubeListBuilder.create().texOffs(102, 112).addBox(-2.5F, 0.0F, -11.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.0F));

        PartDefinition tongue2 = tongue1.addOrReplaceChild("tongue2", CubeListBuilder.create().texOffs(102, 123).addBox(-2.5F, 0.0F, -11.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -11.0F));

        PartDefinition throat = jaw.addOrReplaceChild("throat", CubeListBuilder.create().texOffs(124, 28).addBox(-6.5F, -8.0F, 0.0F, 13.0F, 8.0F, 10.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, -10.0F));

        PartDefinition fin_left = body.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(126, 12).addBox(-1.5F, -1.0F, -3.5F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(33, 90).addBox(-1.5F, 5.0F, -3.5F, 3.0F, 21.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(16.5F, 12.0F, -15.5F));

        PartDefinition fin_right = body.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(126, 12).mirror().addBox(-1.5F, -1.0F, -3.5F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(33, 90).mirror().addBox(-1.5F, 5.0F, -3.5F, 3.0F, 21.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-16.5F, 12.0F, -15.5F));

        PartDefinition fin_back_left = body.addOrReplaceChild("fin_back_left", CubeListBuilder.create().texOffs(127, 113).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(211, 82).addBox(-1.5F, 5.0F, -1.5F, 3.0F, 21.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(16.5F, 12.0F, 17.5F));

        PartDefinition fin_back_right = body.addOrReplaceChild("fin_back_right", CubeListBuilder.create().texOffs(127, 113).mirror().addBox(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(211, 82).mirror().addBox(-1.5F, 5.0F, -1.5F, 3.0F, 21.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-16.5F, 12.0F, 17.5F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(125, 89).addBox(-5.5F, -7.0F, -3.0F, 11.0F, 14.0F, 50.0F, new CubeDeformation(0.0F))
                .texOffs(0, 121).addBox(0.0F, -12.0F, 1.0F, 0.0F, 24.0F, 43.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 27.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 47.0F));

        PartDefinition tailfluke_r1 = tail2.addOrReplaceChild("tailfluke_r1", CubeListBuilder.create().texOffs(0, 89).addBox(-1.5F, -3.0F, -5.0F, 3.0F, 8.0F, 66.0F, new CubeDeformation(0.0F))
                .texOffs(76, 90).addBox(-1.5F, -21.0F, -5.0F, 3.0F, 18.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, -0.5236F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

	@Override
	public void setupAnim(Prognathodon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble() && !entity.isLeaping()) {
            if (entity.isRunning()) {
                this.animateWalk(PrognathodonAnimations.SWIM_FAST, limbSwing, limbSwingAmount, 1, 2.5F);
            } else {
                this.animateWalk(PrognathodonAnimations.SWIM, limbSwing, limbSwingAmount, 1.5F, 2.5F);
            }
        }

        if (this.young) {
            this.applyStatic(PrognathodonAnimations.BABY_TRANSFORM);
        }

        this.animateIdleSmooth(entity.swimIdleAnimationState, PrognathodonAnimations.SWIM_IDLE, ageInTicks, partialTicks, limbSwingAmount, 2.5F);

        this.animateSmooth(entity.flopAnimationState, PrognathodonAnimations.BEACHED, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack1AnimationState, PrognathodonAnimations.BITE_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack2AnimationState, PrognathodonAnimations.BITE_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.yawnAnimationState, PrognathodonAnimations.YAWN_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.tongueAnimationState, PrognathodonAnimations.TONGUE_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.nip1AnimationState, PrognathodonAnimations.NIP_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.nip2AnimationState, PrognathodonAnimations.NIP_BLEND2, ageInTicks, partialTicks);

        this.animateSmooth(entity.leapAnimationState, PrognathodonAnimations.JUMP, ageInTicks, partialTicks);
        this.animate(entity.leapAnimationState, PrognathodonAnimations.JUMP_OVERLAY, ageInTicks);

        this.faceTarget(entity, netHeadYaw, headPitch, 2, this.neck, this.head);

        this.swim_control.xRot = entity.getTilt(partialTicks) * Mth.DEG_TO_RAD;
        this.swim_control.zRot = entity.getRoll(partialTicks) * Mth.DEG_TO_RAD;
        this.tail1.yRot += entity.getTailYaw(partialTicks) * 0.6F * Mth.DEG_TO_RAD;
        this.tail2.yRot += entity.getTailYaw(partialTicks) * 0.9F * Mth.DEG_TO_RAD;
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}