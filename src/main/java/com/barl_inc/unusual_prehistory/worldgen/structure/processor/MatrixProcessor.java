package com.barl_inc.unusual_prehistory.worldgen.structure.processor;

import com.barl_inc.unusual_prehistory.blocks.MatrixBlock;
import com.barl_inc.unusual_prehistory.registry.UP2StructureProcessors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class MatrixProcessor extends StructureProcessor {

    public static final MapCodec<MatrixProcessor> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    BlockState.CODEC.fieldOf("target_state").forGetter(processor -> processor.targetState),
                    BlockState.CODEC.fieldOf("matrix_state").forGetter(processor -> processor.matrixState),
                    ResourceLocation.CODEC.fieldOf("loot_table").forGetter(processor -> processor.lootTable),
                    Codec.STRING.fieldOf("rarity").forGetter(processor -> processor.rarity)
            ).apply(instance, MatrixProcessor::new)
    );

    private final BlockState targetState;
    private final BlockState matrixState;
    private final ResourceLocation lootTable;
    private final String rarity;

    public MatrixProcessor(BlockState targetState, BlockState matrixState, ResourceLocation lootTable, String rarity) {
        this.targetState = targetState;
        this.matrixState = matrixState;
        this.lootTable = lootTable;
        this.rarity = rarity;
    }

    @Override
    protected @NotNull StructureProcessorType<?> getType() {
        return UP2StructureProcessors.MATRIX_PROCESSOR.get();
    }

    // minecraft:capped uses processBlock so we use that instead of process
    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo processBlock(@NotNull LevelReader level, @NotNull BlockPos offset, @NotNull BlockPos pos, StructureTemplate.@NotNull StructureBlockInfo blockInfo, StructureTemplate.StructureBlockInfo relativeBlockInfo, @NotNull StructurePlaceSettings settings) {
        if (relativeBlockInfo.state().getBlock() != targetState.getBlock()) {
            return relativeBlockInfo;
        }

        CompoundTag compoundTag = null;
        if (matrixState.getBlock() instanceof MatrixBlock) {
            compoundTag = new CompoundTag();
            RandomSource random = settings.getRandom(relativeBlockInfo.pos());
            compoundTag.putString("LootTable", lootTable.toString());
            compoundTag.putLong("LootTableSeed", random.nextLong());
            compoundTag.putString("Rarity", rarity);
        }

        return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), matrixState, compoundTag);
    }
}
