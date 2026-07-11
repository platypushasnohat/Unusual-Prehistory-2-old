package com.barl_inc.unusual_prehistory.worldgen.structure;

import com.barl_inc.unusual_prehistory.registry.UP2Structures;
import com.barl_inc.unusual_prehistory.worldgen.structure.piece.TundraFossilPiece;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TundraFossilStructure extends Structure {

    public static final MapCodec<TundraFossilStructure> CODEC = TundraFossilStructure.simpleCodec(TundraFossilStructure::new);

    public TundraFossilStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        WorldgenRandom random = context.random();
        int x = context.chunkPos().getMinBlockX() + random.nextInt(16);
        int z = context.chunkPos().getMinBlockZ() + random.nextInt(16);
        int y = context.chunkGenerator().getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());

        if (!context.chunkGenerator().getBaseColumn(x, z, context.heightAccessor(), context.randomState()).getBlock(y).getFluidState().isEmpty() || context.chunkGenerator().getBaseColumn(x, z, context.heightAccessor(), context.randomState()).getBlock(y).is(Blocks.ICE)) {
            return Optional.empty();
        }
        BlockPos pos = new BlockPos(x, y, z);
        if (this.isSufficientlyFlat(context, pos, 4)) {
            return Optional.of(new GenerationStub(pos, pieces -> TundraFossilPiece.addPieces(context.structureTemplateManager(), pos, pieces, random)));
        }
        return Optional.empty();
    }

    public boolean isSufficientlyFlat(GenerationContext context, BlockPos origin, int check) {
        List<BlockPos> blockPosList = new ArrayList<>();
        for (int x = -check; x < check; x++) {
            for (int z = -check; z < check; z++) {
                blockPosList.add(origin.offset(x, 0, z));
            }
        }
        int count = 0;
        for (BlockPos pos : blockPosList) {
            NoiseColumn blockView = context.chunkGenerator().getBaseColumn(pos.getX(), pos.getZ(), context.heightAccessor(), context.randomState());
            if (blockView.getBlock(pos.getY()).isAir() && !blockView.getBlock(pos.below().getY()).isAir()) {
                count++;
            }
        }
        return count >= check * check * 2;
    }

    @Override
    public @NotNull StructureType<?> type() {
        return UP2Structures.TUNDRA_FOSSIL.get();
    }
}