package com.barl_inc.unusual_prehistory.entity.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public interface DancingMob {

    boolean isDancing();

    void setDancing(boolean dancing);

    @Nullable
    BlockPos getJukeboxPosition();

    void setJukeboxPosition(BlockPos jukeboxPosition);

    default void danceToJukebox(BlockPos pos, boolean dancing) {
        if (dancing) {
            if (!this.isDancing()) {
                this.setJukeboxPosition(pos);
                this.setDancing(true);
            }
        } else if (pos.equals(this.getJukeboxPosition())) {
            this.setJukeboxPosition(null);
            this.setDancing(false);
        }
    }

    default boolean shouldStopDancing(Mob mob) {
        return mob.getLastHurtByMob() != null || mob.getTarget() != null || mob.hasControllingPassenger() || this.getJukeboxPosition() == null || !this.getJukeboxPosition().closerToCenterThan(mob.position(), GameEvent.JUKEBOX_PLAY.value().notificationRadius()) || !mob.level().getBlockState(this.getJukeboxPosition()).is(Blocks.JUKEBOX);
    }
}
