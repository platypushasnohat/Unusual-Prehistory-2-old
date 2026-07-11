package com.barl_inc.unusual_prehistory.client;

import com.barl_inc.unusual_prehistory.CommonProxy;
import com.barl_inc.unusual_prehistory.blocks.entity.TransmogrifierBlockEntity;
import com.barl_inc.unusual_prehistory.client.sounds.*;
import com.barl_inc.unusual_prehistory.entity.mob.base.AmbientMob;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Kimmeridgebrachypteraeschnidium;
import com.barl_inc.unusual_prehistory.entity.mob.other.Grug;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Aegirocassis;
import com.barl_inc.unusual_prehistory.events.ClientNeoEvents;
import com.barl_inc.unusual_prehistory.events.custom.ScreenShakeEvent;
import com.barl_inc.unusual_prehistory.mixins.minecraft.client.SoundEngineAccessor;
import com.barl_inc.unusual_prehistory.mixins.minecraft.client.SoundManagerAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    public static final Int2ObjectMap<AbstractTickableSoundInstance> ENTITY_SOUND_INSTANCE_MAP = new Int2ObjectOpenHashMap<>();
    public static final Map<BlockEntity, AbstractTickableSoundInstance> BLOCK_ENTITY_SOUND_INSTANCE_MAP = new HashMap<>();

    public static List<UUID> blockedEntityRenders = new ArrayList<>();

    public static int shaderLoadAttemptCooldown = 0;

    @Override
    public void clientInit() {
        NeoForge.EVENT_BUS.register(new ClientNeoEvents());
    }

    @Override
    public boolean isKeyDown(int keyType) {
        if (keyType == 0) {
            return Minecraft.getInstance().options.keyJump.isDown();
        }
        if (keyType == 1) {
            return Minecraft.getInstance().options.keySprint.isDown();
        }
        if (keyType == 3) {
            return Minecraft.getInstance().options.keyAttack.isDown();
        }
        if (keyType == 4) {
            return Minecraft.getInstance().options.keyShift.isDown();
        }
        return false;
    }

    @Override
    @Nullable
    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public boolean isFirstPersonPlayer(Entity entity) {
        return entity.equals(Minecraft.getInstance().cameraEntity) && Minecraft.getInstance().options.getCameraType().isFirstPerson();
    }

    @Override
    public void blockRenderingEntity(UUID id) {
        blockedEntityRenders.add(id);
    }

    @Override
    public void releaseRenderingEntity(UUID id) {
        blockedEntityRenders.remove(id);
    }

    @Override
    public void screenShake(ScreenShakeEvent event) {
        ClientNeoEvents.SCREEN_SHAKE_EVENTS.add(event);
    }

    // todo: make this cleaner
    @Override
    public void playWorldSound(@Nullable Object soundEmitter, byte type) {
        if (soundEmitter instanceof Entity entity && !entity.level().isClientSide) {
            return;
        }
        switch (type) {
            case 0:
                if (soundEmitter instanceof TransmogrifierBlockEntity blockEntity) {
                    TransmogrifierSound sound;
                    AbstractTickableSoundInstance oldSound = BLOCK_ENTITY_SOUND_INSTANCE_MAP.get(blockEntity);
                    if (oldSound == null || !(oldSound instanceof TransmogrifierSound sound1 && sound1.isSameBlockEntity(blockEntity)) || oldSound.isStopped()) {
                        sound = new TransmogrifierSound(blockEntity);
                        BLOCK_ENTITY_SOUND_INSTANCE_MAP.put(blockEntity, sound);
                    } else {
                        sound = (TransmogrifierSound) oldSound;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
            case 1:
                if (soundEmitter instanceof Kimmeridgebrachypteraeschnidium entity) {
                    KimmeridgebrachypteraeschnidiumSound sound;
                    AbstractTickableSoundInstance oldSound = ENTITY_SOUND_INSTANCE_MAP.get(entity.getId());
                    if (oldSound == null || !(oldSound instanceof KimmeridgebrachypteraeschnidiumSound sound1 && sound1.isSameEntity(entity))) {
                        sound = new KimmeridgebrachypteraeschnidiumSound(entity);
                        ENTITY_SOUND_INSTANCE_MAP.put(entity.getId(), sound);
                    } else {
                        sound = (KimmeridgebrachypteraeschnidiumSound) oldSound;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
            case 2:
                if (soundEmitter instanceof Aegirocassis entity) {
                    AegirocassisHoverSound sound;
                    AbstractTickableSoundInstance oldSound = ENTITY_SOUND_INSTANCE_MAP.get(entity.getId());
                    if (oldSound == null || !(oldSound instanceof AegirocassisHoverSound sound1 && sound1.isSameEntity(entity))) {
                        sound = new AegirocassisHoverSound(entity);
                        ENTITY_SOUND_INSTANCE_MAP.put(entity.getId(), sound);
                    } else {
                        sound = (AegirocassisHoverSound) oldSound;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
            case 3:
                if (soundEmitter instanceof AmbientMob entity) {
                    BugBuzzSound sound;
                    AbstractTickableSoundInstance oldSound = ENTITY_SOUND_INSTANCE_MAP.get(entity.getId());
                    if (oldSound == null || !(oldSound instanceof BugBuzzSound sound1 && sound1.isSameEntity(entity))) {
                        sound = new BugBuzzSound(entity);
                        ENTITY_SOUND_INSTANCE_MAP.put(entity.getId(), sound);
                    } else {
                        sound = (BugBuzzSound) oldSound;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
            case 4:
                if (soundEmitter instanceof Grug entity) {
                    GrugSound sound;
                    AbstractTickableSoundInstance oldSound = ENTITY_SOUND_INSTANCE_MAP.get(entity.getId());
                    if (oldSound == null || !(oldSound instanceof GrugSound sound1 && sound1.isSameEntity(entity))) {
                        sound = new GrugSound(entity);
                        ENTITY_SOUND_INSTANCE_MAP.put(entity.getId(), sound);
                    } else {
                        sound = (GrugSound) oldSound;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isSoundPlaying(AbstractTickableSoundInstance sound) {
        SoundManager soundManager = Minecraft.getInstance().getSoundManager();
        SoundEngine soundEngine = ((SoundManagerAccessor) soundManager).unusualPrehistory$getSoundEngine();
        SoundEngineAccessor engineAccessor = (SoundEngineAccessor) soundEngine;
        return engineAccessor.unusualPrehistory$getQueuedTickableSounds().contains(sound) || engineAccessor.unusualPrehistory$getTickingSounds().contains(sound);
    }

    @Override
    public void clearSoundCacheFor(Entity entity) {
        ENTITY_SOUND_INSTANCE_MAP.remove(entity.getId());
    }

    @Override
    public void clearSoundCacheFor(BlockEntity entity) {
        BLOCK_ENTITY_SOUND_INSTANCE_MAP.remove(entity);
    }
}
