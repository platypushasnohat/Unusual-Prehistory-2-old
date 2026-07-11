package com.barl_inc.unusual_prehistory.entity.utils;

import net.minecraft.world.entity.Pose;

public enum UP2Poses {

    ATTACKING,
    HEADBUTTING,
    TAIL_WHIPPING,
    START_CHARGING,
    CHARGING,
    STOP_CHARGING,
    GRABBING,
    GRAB_START,
    SPITTING,
    START_FLYING,
    WARNING,
    KICKING,
    POKING,
    FORAGING,
    MITOSIS,
    ALERTED,
    ENRAGED,
    STOMPING,
    RECOVERING,
    ROARING,
    BURPING,
    START_SWIMMING,
    STOP_SWIMMING,
    FISHING;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}