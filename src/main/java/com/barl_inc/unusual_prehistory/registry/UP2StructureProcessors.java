package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.worldgen.structure.processor.CaveFossilProcessor;
import com.barl_inc.unusual_prehistory.worldgen.structure.processor.MatrixProcessor;
import com.barl_inc.unusual_prehistory.worldgen.structure.processor.MountainFossilProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2StructureProcessors {

    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<MatrixProcessor>> MATRIX_PROCESSOR = STRUCTURE_PROCESSOR_TYPES.register("matrix", () -> () -> MatrixProcessor.CODEC);

    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<CaveFossilProcessor>> CAVE_FOSSIL_PROCESSOR = STRUCTURE_PROCESSOR_TYPES.register("cave_fossil_processor", () -> () -> CaveFossilProcessor.CODEC);
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<MountainFossilProcessor>> MOUNTAIN_FOSSIL_PROCESSOR = STRUCTURE_PROCESSOR_TYPES.register("mountain_fossil_processor", () -> () -> MountainFossilProcessor.CODEC);

}
