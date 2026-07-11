package com.barl_inc.unusual_prehistory.worldgen.structure;

import com.barl_inc.unusual_prehistory.registry.UP2Structures;
import com.barl_inc.unusual_prehistory.worldgen.structure.piece.MountainFossilPiece;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MountainFossilStructure extends Structure {

    public static final MapCodec<MountainFossilStructure> CODEC = MountainFossilStructure.simpleCodec(MountainFossilStructure::new);

    public MountainFossilStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        WorldgenRandom random = context.random();
        int x = context.chunkPos().getMinBlockX() + random.nextInt(16);
        int z = context.chunkPos().getMinBlockZ() + random.nextInt(16);
        int y = context.chunkGenerator().getBaseHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());

        if (y < 90) {
            return Optional.empty();
        }
        BlockPos pos = new BlockPos(x, y, z);
        return Optional.of(new GenerationStub(pos, pieces -> MountainFossilPiece.addPieces(context.structureTemplateManager(), pos, pieces, random)));
    }

    @Override
    public @NotNull StructureType<?> type() {
        return UP2Structures.MOUNTAIN_FOSSIL.get();
    }
}