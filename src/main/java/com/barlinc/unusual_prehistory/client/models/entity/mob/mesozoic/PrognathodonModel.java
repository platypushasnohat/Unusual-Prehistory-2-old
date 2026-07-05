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
    private final ModelPart throat;
    private final ModelPart jaw_top;
    private final ModelPart jaw_bottom;
    private final ModelPart tongue;
    private final ModelPart tongue_tip;
    private final ModelPart fin_left;
    private final ModelPart fin_right;
    private final ModelPart back_fin_left;
    private final ModelPart back_fin_right;
    private final ModelPart tail;
    private final ModelPart tail_fluke;

	public PrognathodonModel(ModelPart root) {
        super(0.25F, 72);
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body = this.swim_control.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.throat = this.head.getChild("throat");
        this.jaw_top = this.head.getChild("jaw_top");
        this.jaw_bottom = this.head.getChild("jaw_bottom");
        this.tongue = this.jaw_bottom.getChild("tongue");
        this.tongue_tip = this.tongue.getChild("tongue_tip");
        this.fin_left = this.body.getChild("fin_left");
        this.fin_right = this.body.getChild("fin_right");
        this.back_fin_left = this.body.getChild("back_fin_left");
        this.back_fin_right = this.body.getChild("back_fin_right");
        this.tail = this.body.getChild("tail");
        this.tail_fluke = this.tail.getChild("tail_fluke");
	}

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -21.0F, 0.0F));

        PartDefinition body = swim_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-16.5F, -14.0F, -27.0F, 33.0F, 35.0F, 54.0F, new CubeDeformation(0.0F))
                .texOffs(138, 153).addBox(0.0F, -19.0F, -28.0F, 0.0F, 13.0F, 55.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 230).addBox(0.0F, -11.0F, -15.0F, 0.0F, 24.0F, 13.0F, new CubeDeformation(0.0025F))
                .texOffs(174, 0).addBox(-5.5F, -9.0F, -13.0F, 11.0F, 20.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -27.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -6.0F));

        PartDefinition throat = head.addOrReplaceChild("throat", CubeListBuilder.create().texOffs(124, 28).addBox(-6.5F, -5.5F, -5.0F, 13.0F, 11.0F, 10.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.5F, -3.0F));

        PartDefinition jaw_top = head.addOrReplaceChild("jaw_top", CubeListBuilder.create().texOffs(86, 196).addBox(-5.5F, -5.0F, -30.0F, 11.0F, 8.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(228, 0).addBox(-4.5F, 3.0F, -29.0F, 9.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(233, 20).addBox(-6.5F, 3.0F, -16.0F, 13.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(174, 36).addBox(-7.5F, -5.0F, -17.0F, 15.0F, 8.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(230, 26).addBox(5.5F, -7.0F, -17.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(230, 26).mirror().addBox(-7.5F, -7.0F, -17.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition jaw_bottom = head.addOrReplaceChild("jaw_bottom", CubeListBuilder.create().texOffs(174, 61).addBox(-7.5F, 0.0F, -17.0F, 15.0F, 3.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(204, 221).addBox(-5.5F, 0.0F, -30.0F, 11.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 2.0F));

        PartDefinition teeth_r1 = jaw_bottom.addOrReplaceChild("teeth_r1", CubeListBuilder.create().texOffs(26, 230).mirror().addBox(0.0F, -4.0F, -8.0F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-5.5F, 0.0F, -21.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition teeth_r2 = jaw_bottom.addOrReplaceChild("teeth_r2", CubeListBuilder.create().texOffs(122, 163).mirror().addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-7.5F, 0.0F, -16.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition teeth_r3 = jaw_bottom.addOrReplaceChild("teeth_r3", CubeListBuilder.create().texOffs(174, 81).addBox(-11.0F, -4.0F, 0.0F, 11.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(5.5F, 0.0F, -30.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition teeth_r4 = jaw_bottom.addOrReplaceChild("teeth_r4", CubeListBuilder.create().texOffs(26, 230).addBox(0.0F, -4.0F, -8.0F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(5.5F, 0.0F, -21.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition teeth_r5 = jaw_bottom.addOrReplaceChild("teeth_r5", CubeListBuilder.create().texOffs(122, 163).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(7.5F, 0.0F, -16.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition tongue = jaw_bottom.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(97, 221).addBox(-2.5F, 0.0F, -11.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -5.0F));

        PartDefinition tongue_tip = tongue.addOrReplaceChild("tongue_tip", CubeListBuilder.create().texOffs(97, 232).addBox(-2.5F, 0.0F, -11.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -11.0F));

        PartDefinition fin_left = body.addOrReplaceChild("fin_left", CubeListBuilder.create().texOffs(48, 230).addBox(-1.5F, -1.0F, -3.5F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(140, 221).addBox(-1.5F, 5.0F, -3.5F, 3.0F, 21.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(16.5F, 12.0F, -15.5F));

        PartDefinition fin_right = body.addOrReplaceChild("fin_right", CubeListBuilder.create().texOffs(48, 230).mirror().addBox(-1.5F, -1.0F, -3.5F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(140, 221).mirror().addBox(-1.5F, 5.0F, -3.5F, 3.0F, 21.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-16.5F, 12.0F, -15.5F));

        PartDefinition back_fin_left = body.addOrReplaceChild("back_fin_left", CubeListBuilder.create().texOffs(204, 237).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(172, 221).addBox(-1.5F, 5.0F, -1.5F, 3.0F, 21.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(16.5F, 12.0F, 17.5F));

        PartDefinition back_fin_right = body.addOrReplaceChild("back_fin_right", CubeListBuilder.create().texOffs(204, 237).mirror().addBox(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(172, 221).mirror().addBox(-1.5F, 5.0F, -1.5F, 3.0F, 21.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-16.5F, 12.0F, 17.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(138, 89).addBox(-5.5F, -7.0F, -3.0F, 11.0F, 14.0F, 50.0F, new CubeDeformation(0.0F))
                .texOffs(0, 163).addBox(0.0F, -12.0F, 1.0F, 0.0F, 24.0F, 43.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 2.0F, 27.0F));

        PartDefinition tail_fluke = tail.addOrReplaceChild("tail_fluke", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 47.0F));

        PartDefinition cube_r1 = tail_fluke.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 89).addBox(-1.5F, -3.0F, -5.0F, 3.0F, 8.0F, 66.0F, new CubeDeformation(0.0F))
                .texOffs(86, 163).addBox(-1.5F, -21.0F, -5.0F, 3.0F, 18.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, -0.5236F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }

	@Override
	public void setupAnim(Prognathodon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        float partialTicks = ageInTicks - entity.tickCount;

        if (entity.isInWaterOrBubble() || entity.isLeaping()) {
            if (entity.isRunning()) {
                this.animateWalk(PrognathodonAnimations.SWIMFAST, limbSwing, limbSwingAmount, 1.5F, 3);
            } else {
                this.animateWalk(PrognathodonAnimations.SWIM, limbSwing, limbSwingAmount, 2, 4);
            }
        } else {
            this.animateWalk(PrognathodonAnimations.CRAWL, limbSwing, limbSwingAmount, 2, 4);
        }

        if (this.young) {
            this.applyStatic(PrognathodonAnimations.BABY_TRANSFORM);
        }

        this.animateIdleSmooth(entity.swimIdleAnimationState, PrognathodonAnimations.SWIM_IDLE, ageInTicks, partialTicks, limbSwingAmount);
        this.animateIdleSmooth(entity.idleAnimationState, PrognathodonAnimations.BEACHED, ageInTicks, partialTicks, limbSwingAmount, 4);
        this.animateSmooth(entity.attack1AnimationState, PrognathodonAnimations.BITE_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.attack2AnimationState, PrognathodonAnimations.BITE_BLEND2, ageInTicks, partialTicks);
        this.animateSmooth(entity.yawnAnimationState, PrognathodonAnimations.YAWN_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.tongueAnimationState, PrognathodonAnimations.FLICK_BLEND, ageInTicks, partialTicks);
        this.animateSmooth(entity.nip1AnimationState, PrognathodonAnimations.NIP_BLEND1, ageInTicks, partialTicks);
        this.animateSmooth(entity.nip2AnimationState, PrognathodonAnimations.NIP_BLEND2, ageInTicks, partialTicks);

        this.faceTarget(entity, netHeadYaw, headPitch, 3, neck, head);

        this.swim_control.xRot = entity.getTilt(partialTicks) * Mth.DEG_TO_RAD;
        this.swim_control.zRot = entity.getRoll(partialTicks) * Mth.DEG_TO_RAD;
        this.tail.yRot += entity.getTailYaw(partialTicks) * Mth.DEG_TO_RAD;
        this.tail_fluke.yRot += entity.getTailYaw(partialTicks) * 0.15F * Mth.DEG_TO_RAD;
    }

    @Override
    public ModelPart root() {
        return root;
    }
}