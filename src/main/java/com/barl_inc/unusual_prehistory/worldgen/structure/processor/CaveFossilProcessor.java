package com.barl_inc.unusual_prehistory.worldgen.structure.processor;

import com.barl_inc.unusual_prehistory.registry.UP2StructureProcessors;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class CaveFossilProcessor extends StructureProcessor {

    public static final MapCodec<CaveFossilProcessor> CODEC = MapCodec.unit(CaveFossilProcessor::new);

    public CaveFossilProcessor() {
    }

    @Override
    protected @NotNull StructureProcessorType<?> getType() {
        return UP2StructureProcessors.CAVE_FOSSIL_PROCESSOR.get();
    }

    @SuppressWarnings("deprecation")
    @Nullable
    public StructureTemplate.StructureBlockInfo processBlock(@NotNull LevelReader level, BlockPos blockPos, @NotNull BlockPos pos, StructureTemplate.@NotNull StructureBlockInfo relativeInfo, StructureTemplate.StructureBlockInfo info, @NotNull StructurePlaceSettings settings) {
        BlockState state = info.state();
        if (blockPos.getY() < 0) {
            if (state.is(Blocks.STONE)) {
                return new StructureTemplate.StructureBlockInfo(info.pos(), Blocks.DEEPSLATE.defaultBlockState(), info.nbt());
            }
        }
        return info;
    }
}