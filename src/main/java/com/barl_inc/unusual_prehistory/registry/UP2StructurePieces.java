package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.worldgen.structure.piece.BadlandsFossilPiece;
import com.barl_inc.unusual_prehistory.worldgen.structure.piece.CaveFossilPiece;
import com.barl_inc.unusual_prehistory.worldgen.structure.piece.MountainFossilPiece;
import com.barl_inc.unusual_prehistory.worldgen.structure.piece.TundraFossilPiece;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2StructurePieces {

    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<StructurePieceType, StructurePieceType> BADLANDS_FOSSIL = STRUCTURE_PIECE_TYPES.register("badlands_fossil", () -> BadlandsFossilPiece::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType> CAVE_FOSSIL = STRUCTURE_PIECE_TYPES.register("cave_fossil", () -> CaveFossilPiece::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType> MOUNTAIN_FOSSIL = STRUCTURE_PIECE_TYPES.register("mountain_fossil", () -> MountainFossilPiece::new);
    public static final DeferredHolder<StructurePieceType, StructurePieceType> TUNDRA_FOSSIL = STRUCTURE_PIECE_TYPES.register("tundra_fossil", () -> TundraFossilPiece::new);

}