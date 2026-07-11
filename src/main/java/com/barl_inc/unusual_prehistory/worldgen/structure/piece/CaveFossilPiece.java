package com.barl_inc.unusual_prehistory.worldgen.structure.piece;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2Features;
import com.barl_inc.unusual_prehistory.registry.UP2StructurePieces;
import com.barl_inc.unusual_prehistory.worldgen.structure.processor.CaveFossilProcessor;
import com.barl_inc.unusual_prehistory.worldgen.structure.processor.MatrixProcessor;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.CappedProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CaveFossilPiece extends TemplateStructurePiece {

    public static final Map<ResourceLocation, Integer> HEIGHT_TO_TEMPLATES = Util.make(Maps.newHashMap(), map -> {
        map.put(UnusualPrehistory2.modPrefix("fossil/cave_1"), 0);
        map.put(UnusualPrehistory2.modPrefix("fossil/cave_2"), 0);
        map.put(UnusualPrehistory2.modPrefix("fossil/cave_3"), 0);
        map.put(UnusualPrehistory2.modPrefix("fossil/cave_4"), 0);
        map.put(UnusualPrehistory2.modPrefix("fossil/cave_5"), 0);
    });

    public CaveFossilPiece(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, Rotation rotation) {
        super(UP2StructurePieces.CAVE_FOSSIL.get(), HEIGHT_TO_TEMPLATES.get(id), manager, id, id.toString(), createPlacementData(rotation), pos);
    }

    public CaveFossilPiece(StructureTemplateManager manager, CompoundTag compoundTag) {
        super(UP2StructurePieces.CAVE_FOSSIL.get(), compoundTag, manager, id -> createPlacementData(Rotation.valueOf(compoundTag.getString("Rotation"))));
    }

    public CaveFossilPiece(StructurePieceSerializationContext context, CompoundTag compoundTag) {
        this(context.structureTemplateManager(), compoundTag);
    }

    @Override
    protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext context, @NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(context, compoundTag);
        compoundTag.putString("Rotation", placeSettings.getRotation().name());
    }

    @Override
    public void postProcess(@NotNull WorldGenLevel level, @NotNull StructureManager manager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox boundingBox, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos) {
        BlockPos blockPos = templatePosition;
        this.templatePosition = templatePosition.below(HEIGHT_TO_TEMPLATES.get(ResourceLocation.parse(templateName)));
        this.placeSettings.clearProcessors()
                .addProcessor(new CaveFossilProcessor())
                .addProcessor(matrixProcessor(UnusualPrehistory2.modPrefix("archaeology/fossil/badlands/common"), "common", 10))
                .addProcessor(matrixProcessor(UnusualPrehistory2.modPrefix("archaeology/fossil/badlands/uncommon"), "uncommon", 7))
                .addProcessor(matrixProcessor(UnusualPrehistory2.modPrefix("archaeology/fossil/badlands/rare"), "rare", 5))
                .addProcessor(matrixProcessor(UnusualPrehistory2.modPrefix("archaeology/fossil/badlands/unusual"), "unusual", 2));
        super.postProcess(level, manager, generator, random, boundingBox, chunkPos, pos);
        this.placeSurfaceFeature(level, generator, random, blockPos);
        this.templatePosition = blockPos;
    }

    @Override
    protected void handleDataMarker(@NotNull String key, @NotNull BlockPos blockPos, @NotNull ServerLevelAccessor level, @NotNull RandomSource random, @NotNull BoundingBox boundingBox) {
    }

    private void placeSurfaceFeature(WorldGenLevel level, ChunkGenerator generator, RandomSource random, BlockPos origin) {
        StructureTemplate template = level.getLevel().getStructureManager().getOrCreate(ResourceLocation.parse(templateName));
        BoundingBox box = template.getBoundingBox(placeSettings, origin);
        int centerX = (box.minX() + box.maxX()) / 2;
        int centerZ = (box.minZ() + box.maxZ()) / 2;
        int surfaceY = generator.getBaseHeight(centerX, centerZ, Heightmap.Types.WORLD_SURFACE_WG, level, level.getLevel().getChunkSource().randomState());
        BlockPos surfacePos = new BlockPos(centerX, surfaceY, centerZ);
        Holder<ConfiguredFeature<?, ?>> feature = level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).getOrThrow(UP2Features.FOSSIL_PILE);
        feature.value().place(level, generator, random, surfacePos);
    }

    public static void addPieces(StructureTemplateManager manager, BlockPos pos, StructurePieceAccessor holder, RandomSource random) {
        Set<ResourceLocation> resourceLocations = HEIGHT_TO_TEMPLATES.keySet();
        List<ResourceLocation> templates = new ArrayList<>(resourceLocations.stream().toList());
        ResourceLocation randomTemplate = Util.getRandom(templates, random);
        Rotation rotation = Rotation.getRandom(random);
        holder.addPiece(new CaveFossilPiece(manager, randomTemplate, pos, rotation));
    }

    private static StructurePlaceSettings createPlacementData(Rotation rotation) {
        return new StructurePlaceSettings().setRotation(rotation);
    }

    private static CappedProcessor matrixProcessor(ResourceLocation lootTable, String rarity, int cap) {
        return new CappedProcessor(new MatrixProcessor(Blocks.GRAVEL.defaultBlockState(), UP2Blocks.GRAVEL_MATRIX.get().defaultBlockState(), lootTable, rarity), ConstantInt.of(cap));
    }
}