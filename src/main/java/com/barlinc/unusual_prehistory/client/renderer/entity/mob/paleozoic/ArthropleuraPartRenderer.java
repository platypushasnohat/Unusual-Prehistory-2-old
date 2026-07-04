package com.barlinc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic.ArthropleuraBodyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic.ArthropleuraTailModel;
import com.barlinc.unusual_prehistory.client.renderer.entity.layers.RiderLayer;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.Arthropleura;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.ArthropleuraPart;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class ArthropleuraPartRenderer extends EntityRenderer<ArthropleuraPart> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/arthropleura/royal.png");

    protected UP2Model<ArthropleuraPart> model;
    private final ArthropleuraBodyModel bodyModel;
    private final ArthropleuraTailModel tailModel;

    public ArthropleuraPartRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ArthropleuraBodyModel(context.bakeLayer(UP2ModelLayers.ARTHROPLEURA_BODY));
        this.bodyModel = new ArthropleuraBodyModel(context.bakeLayer(UP2ModelLayers.ARTHROPLEURA_BODY));
        this.tailModel = new ArthropleuraTailModel(context.bakeLayer(UP2ModelLayers.ARTHROPLEURA_TAIL));
    }

    @Override
    public boolean shouldRender(@NotNull ArthropleuraPart entity, @NotNull Frustum camera, double x, double y, double z) {
        if (super.shouldRender(entity, camera, x, y, z)) {
            return true;
        } else {
            Entity front = entity.getFrontEntity();
            if (front != null) {
                Vec3 pos = entity.position();
                Vec3 frontPos = front.position();
                return camera.isVisible(new AABB(frontPos.x, frontPos.y, frontPos.z, pos.x, pos.y, pos.z));
            }
            return false;
        }
    }

    @Override
    public void render(ArthropleuraPart entity, float entityYaw, float partialTicks, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        Entity head = entity.getHeadEntity();
        Entity back = entity.getBackEntity();

        Arthropleura arthropleura = (Arthropleura) head;

        float bodyRotationX = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
        float bodyRotationY = Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());

        this.model = entity.getBackEntity() == null ? tailModel : bodyModel;

        this.model.riding = entity.isPassenger() && (entity.getVehicle() != null && entity.getVehicle().shouldRiderSit());

        float rotationBob = 0.0F;
        float scale = 1.0F;
        float limbSwing = 0.0F;
        float limbSwingAmount = 0.0F;

        if (arthropleura != null) {
            this.model.young = arthropleura.isBaby();
            rotationBob = this.getBob(arthropleura, partialTicks);
            scale = arthropleura.getScale();
            limbSwing = arthropleura.walkAnimation.position(partialTicks);
            limbSwingAmount = arthropleura.walkAnimation.speed(partialTicks);
            if (LivingEntityRenderer.isEntityUpsideDown(arthropleura)) {
                bodyRotationX = -bodyRotationX;
                bodyRotationY = -bodyRotationY;
            }
        }
        this.setupRotations(entity, poseStack, scale);

        this.model.setBodyRotation(bodyRotationX, bodyRotationY);

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.translate(0.0D, -1.501F, 0.0D);

        this.model.setupAnim(entity, limbSwing, limbSwingAmount, rotationBob, bodyRotationY, bodyRotationX);
        this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);

        Minecraft minecraft = Minecraft.getInstance();

        boolean isBodyVisible = arthropleura == null || !arthropleura.isInvisible();
        boolean isSpectator = !isBodyVisible && !entity.isInvisibleTo(Objects.requireNonNull(minecraft.player));
        boolean isGlowing = minecraft.shouldEntityAppearGlowing(entity);

        RenderType rendertype = this.getRenderType(entity, isBodyVisible, isSpectator, isGlowing);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = bufferSource.getBuffer(rendertype);
            int i = getOverlayCoords(entity, 0.0F);
            this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, i, -1);
        }

        if (entity.isVehicle() && back != null) {
            this.positionPassengers(entity, partialTicks, poseStack, bufferSource, packedLight);
        }

        poseStack.popPose();
    }

    protected float getBob(Arthropleura arthropleura, float partialTick) {
        return (float) arthropleura.tickCount + partialTick;
    }

    @Nullable
    protected RenderType getRenderType(ArthropleuraPart entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        ResourceLocation resourcelocation = this.getTextureLocation(entity);
        if (translucent) {
            return RenderType.itemEntityTranslucentCull(resourcelocation);
        } else if (bodyVisible) {
            return this.model.renderType(resourcelocation);
        } else {
            return glowing ? RenderType.outline(resourcelocation) : null;
        }
    }

    private void positionPassengers(ArthropleuraPart entity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        float bodyYaw = entity.yRotO + (entity.getYRot() - entity.yRotO) * partialTicks;
        for (Entity passenger : entity.getPassengers()) {
            if (passenger == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                continue;
            }
            UnusualPrehistory2.PROXY.releaseRenderingEntity(passenger.getUUID());
            poseStack.pushPose();
            this.bodyModel.translateRiderToBody(poseStack);
            double yOffset = passenger instanceof Player ? 0.25D : -0.25D;
            poseStack.translate(0.0D, yOffset, 1.0D);
            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            poseStack.mulPose(Axis.YN.rotationDegrees(360.0F - bodyYaw));
            passenger.setYBodyRot(entity.getYRot());
            RiderLayer.renderPassenger(passenger, 0, 0, 0, 0, partialTicks, poseStack, bufferSource, packedLight);
            poseStack.popPose();
            UnusualPrehistory2.PROXY.blockRenderingEntity(passenger.getUUID());
        }
    }

    protected void setupRotations(ArthropleuraPart entity, PoseStack poseStack, float scale) {
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        if (entity.getHeadEntity() instanceof Arthropleura arthropleura && LivingEntityRenderer.isEntityUpsideDown(arthropleura)) {
            poseStack.translate(0.0F, (entity.getBbHeight() + 0.1F) / scale, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ArthropleuraPart entity) {
        if (entity.getHeadEntity() instanceof Arthropleura arthropleura) {
            Arthropleura.ArthropleuraVariant variant = Arthropleura.ArthropleuraVariant.byId(arthropleura.getVariant().getId());
            return UnusualPrehistory2.modPrefix("textures/entity/mob/arthropleura/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
        } else {
            return TEXTURE;
        }
    }

    public static int getOverlayCoords(ArthropleuraPart segmentEntity, float f) {
        return OverlayTexture.pack(OverlayTexture.u(f), OverlayTexture.v(segmentEntity.renderHurtFlag));
    }
}
