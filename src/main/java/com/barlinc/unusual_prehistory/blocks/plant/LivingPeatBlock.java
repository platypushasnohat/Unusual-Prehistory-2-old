package com.barlinc.unusual_prehistory.blocks.plant;

import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.registry.UP2Features;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.lighting.LightEngine;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class LivingPeatBlock extends Block implements BonemealableBlock {

    public static final MapCodec<LivingPeatBlock> CODEC = simpleCodec(LivingPeatBlock::new);

    public LivingPeatBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull MapCodec<LivingPeatBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return true;
    }

    private static boolean canBeLivingPeat(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        if (aboveState.is(Blocks.SNOW) && aboveState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (aboveState.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int light = LightEngine.getLightBlockInto(level, state, pos, aboveState, abovePos, Direction.UP, aboveState.getLightBlock(level, abovePos));
            return light < level.getMaxLightLevel();
        }
    }

    private static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.above();
        return canBeLivingPeat(state, level, pos) && !level.getFluidState(blockpos).is(FluidTags.WATER);
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!canBeLivingPeat(state, level, pos)) {
            if (!level.isAreaLoaded(pos, 1)) return;
            level.setBlockAndUpdate(pos, UP2Blocks.PEAT.get().defaultBlockState());
        } else {
            if (!level.isAreaLoaded(pos, 3)) return;
            if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
                BlockState blockstate = this.defaultBlockState();
                for (int i = 0; i < 4; i++) {
                    BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if (level.getBlockState(blockpos).is(UP2Blocks.PEAT.get()) && canPropagate(blockstate, level, blockpos)) {
                        level.setBlockAndUpdate(blockpos, blockstate);
                    }
                }
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, @NotNull BlockState state) {
        return level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, @NotNull RandomSource random, BlockPos pos, @NotNull BlockState state) {
        BlockPos abovePos = pos.above();
        BlockState fern = Blocks.FERN.defaultBlockState();
        Optional<Holder.Reference<PlacedFeature>> patch = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(UP2Features.LIVING_PEAT_PLANTS);

        outerLoop:
        for (int i = 0; i < 128; i++) {
            BlockPos blockpos = abovePos;

            for (int j = 0; j < i / 16; j++) {
                blockpos = blockpos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!level.getBlockState(blockpos.below()).is(this) || level.getBlockState(blockpos).isCollisionShapeFullBlock(level, blockpos)) {
                    continue outerLoop;
                }
            }

            BlockState state1 = level.getBlockState(blockpos);
            if ((state1.is(fern.getBlock())) && random.nextInt(10) == 0) {
                ((BonemealableBlock) state1.getBlock()).performBonemeal(level, random, blockpos, state1);
            }

            if (state1.isAir()) {
                if (patch.isEmpty()) {
                    continue;
                }
                Holder<PlacedFeature> featureHolder = patch.get();
                featureHolder.value().place(level, level.getChunkSource().getGenerator(), random, blockpos);
            }
        }
    }
}
