package com.barl_inc.unusual_prehistory.entity.utils;

public interface LeverPullingMob {

    int getPullLeverCooldown();

    void setPullLeverCooldown(int cooldown);

    default boolean canPullLever() {
        return this.getPullLeverCooldown() == 0;
    }

    default void pullLever() {
    }
}
