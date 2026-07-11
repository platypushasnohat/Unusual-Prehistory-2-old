package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.worldgen.structure.BadlandsFossilStructure;
import com.barl_inc.unusual_prehistory.worldgen.structure.CaveFossilStructure;
import com.barl_inc.unusual_prehistory.worldgen.structure.MountainFossilStructure;
import com.barl_inc.unusual_prehistory.worldgen.structure.TundraFossilStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2Structures {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<StructureType<?>, StructureType<BadlandsFossilStructure>> BADLANDS_FOSSIL = STRUCTURE_TYPES.register("badlands_fossil", () -> () -> BadlandsFossilStructure.CODEC);
    public static final DeferredHolder<StructureType<?>, StructureType<CaveFossilStructure>> CAVE_FOSSIL = STRUCTURE_TYPES.register("cave_fossil", () -> () -> CaveFossilStructure.CODEC);
    public static final DeferredHolder<StructureType<?>, StructureType<MountainFossilStructure>> MOUNTAIN_FOSSIL = STRUCTURE_TYPES.register("mountain_fossil", () -> () -> MountainFossilStructure.CODEC);
    public static final DeferredHolder<StructureType<?>, StructureType<TundraFossilStructure>> TUNDRA_FOSSIL = STRUCTURE_TYPES.register("tundra_fossil", () -> () -> TundraFossilStructure.CODEC);

}
