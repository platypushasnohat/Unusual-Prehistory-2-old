package com.barl_inc.unusual_prehistory;

import com.barl_inc.unusual_prehistory.events.custom.ScreenShakeEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class CommonProxy {

    public void commonInit() {
    }

    public void clientInit() {
    }

    public boolean isKeyDown(int keyType) {
        return false;
    }

    public Player getClientSidePlayer() {
        return null;
    }

    public boolean isFirstPersonPlayer(Entity entity) {
        return false;
    }

    public void blockRenderingEntity(UUID id) {
    }

    public void releaseRenderingEntity(UUID id) {
    }

    public void screenShake(ScreenShakeEvent event) {
    }

    public void playWorldSound(@Nullable Object soundEmitter, byte type) {
    }

    public void clearSoundCacheFor(Entity entity) {
    }

    public void clearSoundCacheFor(BlockEntity entity) {
    }
}
