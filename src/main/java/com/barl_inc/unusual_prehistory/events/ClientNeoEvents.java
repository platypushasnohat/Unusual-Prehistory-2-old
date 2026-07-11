package com.barl_inc.unusual_prehistory.events;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.ClientProxy;
import com.barl_inc.unusual_prehistory.client.renderer.entity.layers.CustomPlayerRidePose;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Ichthyosaurus;
import com.barl_inc.unusual_prehistory.events.custom.ModelRotationEvent;
import com.barl_inc.unusual_prehistory.events.custom.PlayerPoseEvent;
import com.barl_inc.unusual_prehistory.events.custom.ScreenShakeEvent;
import com.barl_inc.unusual_prehistory.mixins.minecraft.client.GameRendererAccessor;
import com.barl_inc.unusual_prehistory.registry.UP2EnumProxy;
import com.barl_inc.unusual_prehistory.registry.UP2MobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerHeartTypeEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ClientNeoEvents {

    private static final ResourceLocation DAZZLED_SHADER = UnusualPrehistory2.modPrefix("shaders/post/dazzled.json");
    private static final ResourceLocation TRANQUILITY_SHADER = UnusualPrehistory2.modPrefix("shaders/post/tranquility.json");

    private static float shakeAmount;
    private static float prevShakeAmount;

    public static final List<ScreenShakeEvent> SCREEN_SHAKE_EVENTS = new ArrayList<>();

    @SuppressWarnings({"rawtypes", "unchecked", "UnstableApiUsage"})
    @SubscribeEvent
    public void preRenderLiving(RenderLivingEvent.Pre event) {
        if (ClientProxy.blockedEntityRenders.contains(event.getEntity().getUUID())) {
            if (!UnusualPrehistory2.PROXY.isFirstPersonPlayer(event.getEntity())) {
                NeoForge.EVENT_BUS.post(new RenderLivingEvent.Post(event.getEntity(), event.getRenderer(), event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight()));
                event.setCanceled(true);
            }
            ClientProxy.blockedEntityRenders.remove(event.getEntity().getUUID());
        }
    }

    @SubscribeEvent
    public void clientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity cameraEntity = minecraft.getCameraEntity();
        prevShakeAmount = shakeAmount;
        float shake = 0.0F;
        Iterator<ScreenShakeEvent> groundShakeMomentIterator = SCREEN_SHAKE_EVENTS.iterator();
        while (groundShakeMomentIterator.hasNext()) {
            ScreenShakeEvent groundShakeMoment = groundShakeMomentIterator.next();
            groundShakeMoment.tick();
            if (groundShakeMoment.isDone()) {
                groundShakeMomentIterator.remove();
            }
            else if (cameraEntity != null) {
                shake = Math.max(shake, groundShakeMoment.getDegree(cameraEntity, 1.0F));
            }
        }
        shakeAmount = shake * minecraft.options.screenEffectScale().get().floatValue();
    }

    @SubscribeEvent
    public void computeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        Minecraft minecraft = Minecraft.getInstance();
        float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);;
        float lerpedShakeAmount = Mth.clamp(prevShakeAmount + (shakeAmount - prevShakeAmount) * partialTicks, 0, 4.0F);
        if (lerpedShakeAmount > 0) {
            float time = minecraft.cameraEntity == null ? 0.0F : minecraft.cameraEntity.tickCount + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
            event.setRoll((float) (lerpedShakeAmount * Math.sin(2.0F * time)));
        }
    }

    @SubscribeEvent
    public void onModelRotation(ModelRotationEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player && player.getVehicle() != null) {
            event.setCanceled(Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity.getVehicle()) instanceof CustomPlayerRidePose);
        }
    }

    @SubscribeEvent
    public void onPlayerPose(PlayerPoseEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player && player.getVehicle() != null) {
            if (Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player.getVehicle()) instanceof CustomPlayerRidePose playerRidePose) {
                playerRidePose.applyRiderPose(event.getHumanoidModel(), entity);
            }
        }
    }

    @SubscribeEvent
    public void onPoseHand(PlayerPoseEvent event) {
        float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
        float ageInTicks = event.getEntity().tickCount + partialTicks;
        LivingEntity entity = event.getEntity();

        if (entity.isPassenger() && entity.getVehicle() instanceof Ichthyosaurus) {
            if (Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                event.getHumanoidModel().rightArm.zRot = (float) Math.toRadians(-12.0F);
                event.getHumanoidModel().leftArm.zRot = (float) Math.toRadians(12.0F);
                event.setResult(TriState.TRUE);
            } else {
                event.getHumanoidModel().leftArm.xRot = (float) Math.toRadians(200.0F);
                event.getHumanoidModel().rightArm.xRot = (float) Math.toRadians(200.0F);
                event.getHumanoidModel().rightArm.zRot = (float) Math.toRadians(25.0F);
                event.getHumanoidModel().leftArm.zRot = (float) Math.toRadians(-25.0F);
                event.getHumanoidModel().head.xRot = (float) Math.toRadians(-85.0F);
                event.getHumanoidModel().hat.copyFrom(event.getHumanoidModel().head);
                event.getHumanoidModel().leftLeg.xRot = (float) Math.toRadians(15.0F * Mth.cos(ageInTicks * 0.4F + (float) Math.PI));
                event.getHumanoidModel().rightLeg.xRot = (float) Math.toRadians(15.0F * Mth.cos(ageInTicks * 0.4F));
                event.setResult(TriState.TRUE);
            }
        }
    }

    @SubscribeEvent
    public void renderHeartTypes(PlayerHeartTypeEvent event) {
        Player player = event.getEntity();
        if (player.hasEffect(UP2MobEffects.PARALYSIS)) {
            event.setType(UP2EnumProxy.PARALYSIS_HEART_TYPE.getValue());
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @SubscribeEvent
    public void postRenderStage(RenderLevelStageEvent event) {
        Entity player = Minecraft.getInstance().getCameraEntity();
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            GameRenderer renderer = Minecraft.getInstance().gameRenderer;
            if (player instanceof LivingEntity afflicted && afflicted.hasEffect(UP2MobEffects.DAZZLED)) {
                if (renderer.currentEffect() == null || !DAZZLED_SHADER.toString().equals(renderer.currentEffect().getName())) {
                    attemptLoadShader(DAZZLED_SHADER);
                }
            } else if (renderer.currentEffect() != null && DAZZLED_SHADER.toString().equals(renderer.currentEffect().getName())) {
                renderer.checkEntityPostEffect(null);
            }

            else if (player instanceof LivingEntity afflicted && afflicted.hasEffect(UP2MobEffects.TRANQUILITY)) {
                if (renderer.currentEffect() == null || !TRANQUILITY_SHADER.toString().equals(renderer.currentEffect().getName())) {
                    attemptLoadShader(TRANQUILITY_SHADER);
                }
            } else if (renderer.currentEffect() != null && TRANQUILITY_SHADER.toString().equals(renderer.currentEffect().getName())) {
                renderer.checkEntityPostEffect(null);
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        if (ClientProxy.shaderLoadAttemptCooldown > 0) {
            ClientProxy.shaderLoadAttemptCooldown--;
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderFog(ViewportEvent.RenderFog event) {
        if (event.isCanceled()) {
            return;
        }

        Player player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(UP2MobEffects.DAZZLED)) {
            float farPlane = event.getFarPlaneDistance();
            float distance = 12.0F;
            float f = player.getEffect(UP2MobEffects.DAZZLED).isInfiniteDuration() ? distance : Mth.lerp(Math.min(1.0F, player.getEffect(UP2MobEffects.DAZZLED).getDuration() / 20.0F), farPlane, distance);
            if (event.getMode() == FogRenderer.FogMode.FOG_SKY) {
                event.setNearPlaneDistance(0.0F);
                event.setFarPlaneDistance(f * 0.75F);
            } else {
                event.setNearPlaneDistance(f * 0.25F);
                event.setFarPlaneDistance(f);
            }
            event.setCanceled(true);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onFogColor(ViewportEvent.ComputeFogColor event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(UP2MobEffects.DAZZLED)) {
            float factor = 1.0F;
            if (!player.getEffect(UP2MobEffects.DAZZLED).isInfiniteDuration()) {
                factor = Math.min(1.0F, player.getEffect(UP2MobEffects.DAZZLED).getDuration() / 20.0F);
            }
            float brightness = Mth.lerp(factor, 1.0F, 6.0F);
            event.setRed(Math.min(1.0F, event.getRed() * brightness));
            event.setGreen(Math.min(1.0F, event.getGreen() * brightness));
            event.setBlue(Math.min(1.0F, event.getBlue() * brightness));
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void attemptLoadShader(ResourceLocation resourceLocation) {
        GameRenderer renderer = Minecraft.getInstance().gameRenderer;
        if (ClientProxy.shaderLoadAttemptCooldown <= 0) {
            renderer.loadEffect(resourceLocation);
            if (!((GameRendererAccessor) renderer).isEffectActive()) {
                ClientProxy.shaderLoadAttemptCooldown = 12000;
                UnusualPrehistory2.LOGGER.warn("Unusual Prehistory could not load the shader {}, will attempt to load shader in 30 seconds", resourceLocation);
            }
        }
    }
}
