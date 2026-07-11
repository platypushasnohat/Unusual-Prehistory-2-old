package com.barl_inc.unusual_prehistory.blocks.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FallingLeavesBlock extends LeavesBlock {

    public final Supplier<SimpleParticleType> particle;

    public FallingLeavesBlock(BlockBehaviour.Properties properties, Supplier<SimpleParticleType> particle) {
        super(properties);
        this.particle = particle;
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (!(random.nextFloat() >= 0.017F)) {
            BlockPos pos1 = pos.below();
            BlockState state1 = level.getBlockState(pos1);
            if (!isFaceFull(state1.getCollisionShape(level, pos1), Direction.UP)) {
                ParticleUtils.spawnParticleBelow(level, pos, random, particle.get());
            }
        }
    }
}
