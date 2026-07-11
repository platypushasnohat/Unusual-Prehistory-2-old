package com.barl_inc.unusual_prehistory.worldgen.structure.processor;

import com.barl_inc.unusual_prehistory.registry.UP2StructureProcessors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class MountainFossilProcessor extends StructureProcessor {

    public static final MapCodec<MountainFossilProcessor> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("sink_by").forGetter(processor -> processor.sinkBy)
            ).apply(instance, MountainFossilProcessor::new)
    );

    private final int sinkBy;

    public MountainFossilProcessor(int sinkBy) {
        this.sinkBy = sinkBy;
    }

    @Override
    protected @NotNull StructureProcessorType<?> getType() {
        return UP2StructureProcessors.MOUNTAIN_FOSSIL_PROCESSOR.get();
    }

    @SuppressWarnings("deprecation")
    @Nullable
    public StructureTemplate.StructureBlockInfo processBlock(@NotNull LevelReader level, @NotNull BlockPos blockPos, @NotNull BlockPos pos, StructureTemplate.@NotNull StructureBlockInfo relativeInfo, StructureTemplate.StructureBlockInfo info, @NotNull StructurePlaceSettings settings) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(info.pos().getX(), pos.getY(), info.pos().getZ());
        while (this.sinkThrough(level.getBlockState(mutablePos)) && mutablePos.getY() > level.getMinBuildHeight()) {
            mutablePos.move(0, -1, 0);
        }
        mutablePos.move(0, -sinkBy, 0);
        return new StructureTemplate.StructureBlockInfo(new BlockPos(info.pos().getX(), mutablePos.getY() + relativeInfo.pos().getY() + 1, info.pos().getZ()), info.state(), info.nbt());
    }

    private boolean sinkThrough(BlockState blockState) {
        return !blockState.getFluidState().isEmpty() || blockState.is(BlockTags.REPLACEABLE) || blockState.isAir();
    }
}