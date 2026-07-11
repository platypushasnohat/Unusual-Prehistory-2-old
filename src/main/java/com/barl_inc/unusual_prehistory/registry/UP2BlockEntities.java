package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.blocks.entity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("DataFlowIssue")
public class UP2BlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TransmogrifierBlockEntity>> TRANSMOGRIFIER_BLOCK_ENTITY = BLOCK_ENTITIES.register("transmogrifier_block_entity", () -> BlockEntityType.Builder.of(TransmogrifierBlockEntity::new, UP2Blocks.TRANSMOGRIFIER.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EggBlockEntity>> EGG_BLOCK_ENTITY = BLOCK_ENTITIES.register("egg_block_entity", () -> BlockEntityType.Builder.of(EggBlockEntity::new, UP2Blocks.EGG_BLOCKS.stream().map(Supplier::get).toArray(Block[]::new)).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PlushieBlockEntity>> PLUSHIE_BLOCK_ENTITY = BLOCK_ENTITIES.register("plushie_block_entity", () -> BlockEntityType.Builder.of(PlushieBlockEntity::new, UP2Blocks.PLUSHIE_BLOCKS.stream().map(Supplier::get).toArray(Block[]::new)).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MatrixBlockEntity>> MATRIX_BLOCK_ENTITY = BLOCK_ENTITIES.register("matrix_block_entity", () -> BlockEntityType.Builder.of(MatrixBlockEntity::new, UP2Blocks.DIRT_MATRIX.get(), UP2Blocks.MUD_MATRIX.get(), UP2Blocks.GRAVEL_MATRIX.get(), UP2Blocks.SAND_MATRIX.get(), UP2Blocks.RED_SAND_MATRIX.get(), UP2Blocks.SNOW_MATRIX.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FossilBedBlockEntity>> FOSSIL_BED_BLOCK_ENTITY = BLOCK_ENTITIES.register("fossil_bed_block_entity", () -> BlockEntityType.Builder.of(FossilBedBlockEntity::new, UP2Blocks.COMMON_FOSSIL_BED.get(), UP2Blocks.UNCOMMON_FOSSIL_BED.get(), UP2Blocks.RARE_FOSSIL_BED.get(), UP2Blocks.UNUSUAL_FOSSIL_BED.get()).build(null));

    public static void addBlockEntities(final BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN,
                UP2Blocks.DRYOPHYLLUM.sign().get(), UP2Blocks.DRYOPHYLLUM.wallSign().get(),
                UP2Blocks.GINKGO.sign().get(), UP2Blocks.GINKGO.wallSign().get(),
                UP2Blocks.LEPIDODENDRON.sign().get(), UP2Blocks.LEPIDODENDRON.wallSign().get(),
                UP2Blocks.METASEQUOIA.sign().get(), UP2Blocks.METASEQUOIA.wallSign().get()
        );

        event.modify(BlockEntityType.HANGING_SIGN,
                UP2Blocks.DRYOPHYLLUM.hangingSign().get(), UP2Blocks.DRYOPHYLLUM.hangingWallSign().get(),
                UP2Blocks.GINKGO.hangingSign().get(), UP2Blocks.GINKGO.hangingWallSign().get(),
                UP2Blocks.LEPIDODENDRON.hangingSign().get(), UP2Blocks.LEPIDODENDRON.hangingWallSign().get(),
                UP2Blocks.METASEQUOIA.hangingSign().get(), UP2Blocks.METASEQUOIA.hangingWallSign().get()
        );
    }
}
