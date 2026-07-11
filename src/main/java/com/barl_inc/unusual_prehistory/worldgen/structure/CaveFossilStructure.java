package com.barl_inc.unusual_prehistory.worldgen.structure;

import com.barl_inc.unusual_prehistory.registry.UP2Structures;
import com.barl_inc.unusual_prehistory.worldgen.structure.piece.CaveFossilPiece;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CaveFossilStructure extends Structure {

    public static final MapCodec<CaveFossilStructure> CODEC = CaveFossilStructure.simpleCodec(CaveFossilStructure::new);

    public CaveFossilStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        WorldgenRandom random = context.random();
        int blockX = chunkPos.getBlockX(random.nextInt(16));
        int blockZ = chunkPos.getBlockZ(random.nextInt(16));
        int baseHeight = context.chunkGenerator().getBaseHeight(blockX, blockZ, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        int blockY = Mth.clamp(baseHeight - random.nextIntBetweenInclusive(16, 128), 0, 40);
        int y = this.extraChecks(context, new BlockPos(blockX, blockY, blockZ));
        if (y == -1) {
            return Optional.empty();
        }
        BlockPos pos = new BlockPos(blockX, y, blockZ);
        // todo: change height stuff later when we add deep cave fossils
//        if (y < 0) {
//            return Optional.empty();
//        }
        return Optional.of(new GenerationStub(pos, pieces -> CaveFossilPiece.addPieces(context.structureTemplateManager(), pos, pieces, random)));
    }

    private int extraChecks(GenerationContext context, BlockPos pos) {
        NoiseColumn baseColumn = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor(), context.randomState());
        int surfaceLevel = pos.getY();
        while (true) {
            if (context.heightAccessor().isOutsideBuildHeight(surfaceLevel)) {
                return -1;
            }
            BlockState blockState = baseColumn.getBlock(surfaceLevel);
            BlockState floor = baseColumn.getBlock(surfaceLevel - 1);
            boolean isEmptyBlock = blockState.isAir() && blockState.getFluidState().isEmpty();
            boolean isSolidFloor = !floor.isAir() && !floor.canBeReplaced();
            if ((isEmptyBlock && isSolidFloor)) {
                break;
            }
            surfaceLevel--;
        }

        int emptySpace = 0;
        for (int i = 0; i < 20; i++) {
            BlockState blockState = baseColumn.getBlock(surfaceLevel + i);
            if (blockState.isAir() && blockState.getFluidState().isEmpty()) {
                emptySpace++;
            } else {
                break;
            }

        }
        if (emptySpace < 8) {
            return -1;
        }
        return surfaceLevel - 12;
    }

    @Override
    public @NotNull StructureType<?> type() {
        return UP2Structures.CAVE_FOSSIL.get();
    }
}