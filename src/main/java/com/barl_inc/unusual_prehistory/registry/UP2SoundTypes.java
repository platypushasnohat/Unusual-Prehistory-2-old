package com.barl_inc.unusual_prehistory.registry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public class UP2SoundTypes {

    public static final SoundType MOSSY_DIRT = new DeferredSoundType(1.0F, 1.0F, () -> SoundEvents.GRAVEL_BREAK, () -> SoundEvents.MOSS_STEP, () -> SoundEvents.GRAVEL_PLACE, () -> SoundEvents.GRAVEL_HIT, () -> SoundEvents.MOSS_FALL);
    public static final SoundType LIVING_PEAT = new DeferredSoundType(1.0F, 1.0F, () -> SoundEvents.MUD_BREAK, () -> SoundEvents.MOSS_STEP, () -> SoundEvents.MUD_PLACE, () -> SoundEvents.MUD_HIT, () -> SoundEvents.MOSS_FALL);

}
