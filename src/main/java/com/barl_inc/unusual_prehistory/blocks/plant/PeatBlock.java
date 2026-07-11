package com.barl_inc.unusual_prehistory.blocks.plant;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class PeatBlock extends Block {

    public static final MapCodec<PeatBlock> CODEC = simpleCodec(PeatBlock::new);

    public PeatBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull MapCodec<PeatBlock> codec() {
        return CODEC;
    }
}
