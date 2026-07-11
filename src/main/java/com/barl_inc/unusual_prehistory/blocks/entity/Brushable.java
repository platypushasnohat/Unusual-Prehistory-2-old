package com.barl_inc.unusual_prehistory.blocks.entity;

public interface Brushable {

    default boolean brush(long startTick) {
        return false;
    }
}
