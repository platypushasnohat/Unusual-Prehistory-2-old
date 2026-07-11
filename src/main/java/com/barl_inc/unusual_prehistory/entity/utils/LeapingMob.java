package com.barl_inc.unusual_prehistory.entity.utils;

public interface LeapingMob {

    boolean isLeaping();

    void setLeaping(boolean leaping);

    default void onLeap() {
        this.setLeaping(true);
    }
}
