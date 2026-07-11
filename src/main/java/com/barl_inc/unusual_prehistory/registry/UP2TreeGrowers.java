package com.barl_inc.unusual_prehistory.registry;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public final class UP2TreeGrowers {

    // Update 1
    public static final TreeGrower GINKGO = new TreeGrower("ginkgo", Optional.empty(), Optional.of(UP2Features.GINKGO), Optional.empty());
    public static final TreeGrower GOLDEN_GINKGO = new TreeGrower("golden_ginkgo", Optional.empty(), Optional.of(UP2Features.GOLDEN_GINKGO), Optional.empty());
    public static final TreeGrower LEPIDODENDRON = new TreeGrower("lepidodendron", Optional.empty(), Optional.of(UP2Features.LEPIDODENDRON), Optional.empty());

    // Update 4
    public static final TreeGrower DRYOPHYLLUM = new TreeGrower("dryophyllum", Optional.empty(), Optional.of(UP2Features.DRYOPHYLLUM), Optional.empty());
    public static final TreeGrower METASEQUOIA = new TreeGrower("metasequoia", Optional.of(UP2Features.MEGA_METASEQUOIA), Optional.of(UP2Features.METASEQUOIA), Optional.empty());
}
