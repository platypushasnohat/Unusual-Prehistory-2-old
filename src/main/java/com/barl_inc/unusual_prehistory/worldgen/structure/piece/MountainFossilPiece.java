package com.barl_inc.unusual_prehistory.worldgen.structure.piece;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2StructurePieces;
import com.barl_inc.unusual_prehistory.worldgen.structure.processor.MatrixProcessor;
import com.barl_inc.unusual_prehistory.worldgen.structure.processor.MountainFossilProcessor;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.CappedProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MountainFossilPiece extends TemplateStructurePiece {

    public static final Map<ResourceLocation, Integer> HEIGHT_TO_TEMPLATES = Util.make(Maps.newHashMap(), map -> {
        map.put(UnusualPrehistory2.modPrefix("fossil/mountain_1"), 5);
        map.put(UnusualPrehistory2.modPrefix("fossil/mountain_2"), 6);
        map.put(UnusualPrehistory2.modPrefix("fossil/mountain_3"), 2);
    });

    public MountainFossilPiece(StructureTemplateManager manager, ResourceLocation id, BlockPos pos, Rotation rotation) {
        super(UP2StructurePieces.MOUNTAIN_FOSSIL.get(), HEIGHT_TO_TEMPLATES.get(id), manager, id, id.toString(), createPlacementData(rotation), pos);
    }

    public MountainFossilPiece(StructureTemplateManager manager, CompoundTag compoundTag) {
        super(UP2StructurePieces.MOUNTAIN_FOSSIL.get(), compoundTag, manager, id -> createPlacementData(Rotation.valueOf(compoundTag.getString("Rotation"))));
    }

    public MountainFossilPiece(StructurePieceSerializationContext context, CompoundTag compoundTag) {
        this(context.structureTemplateManager(), compoundTag);
    }

    public static void addPieces(StructureTemplateManager manager, BlockPos pos, StructurePieceAccessor holder, RandomSource random) {
        Set<ResourceLocation> resourceLocations = HEIGHT_TO_TEMPLATES.keySet();
        List<ResourceLocation> templates = new ArrayList<>(resourceLocations.stream().toList());
        ResourceLocation randomTemplate = Util.getRandom(templates, random);
        Rotation rotation = Rotation.getRandom(random);
        holder.addPiece(new MountainFossilPiece(manager, randomTemplate, pos, rotation));
    }

    private static StructurePlaceSettings createPlacementData(Rotation rotation) {
        return new StructurePlaceSettings().setRotation(rotation);
    }

    @Override
    protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext context, @NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(context, compoundTag);
        compoundTag.putString("Rotation", placeSettings.getRotation().name());
    }

    @Override
    public void postProcess(@NotNull WorldGenLevel level, @NotNull StructureManager manager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox boundingBox, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos) {
        BlockPos blockPos = templatePosition;
//        this.templatePosition = templatePosition.below(HEIGHT_TO_TEMPLATES.get(ResourceLocation.parse(templateName)));
        this.placeSettings.clearProcessors()
                .addProcessor(new MountainFossilProcessor(HEIGHT_TO_TEMPLATES.get(ResourceLocation.parse(templateName))))
                .addProcessor(matrixProcessor(UnusualPrehistory2.modPrefix("archaeology/fossil/badlands/common"), "common", 10))
                .addProcessor(matrixProcessor(UnusualPrehistory2.modPrefix("archaeology/fossil/badlands/uncommon"), "uncommon", 7))
                .addProcessor(matrixProcessor(UnusualPrehistory2.modPrefix("archaeology/fossil/badlands/rare"), "rare", 5))
                .addProcessor(matrixProcessor(UnusualPrehistory2.modPrefix("archaeology/fossil/badlands/unusual"), "unusual", 2));
        super.postProcess(level, manager, generator, random, boundingBox, chunkPos, pos);
        this.templatePosition = blockPos;
    }

    @Override
    protected void handleDataMarker(@NotNull String metadata, @NotNull BlockPos pos, @NotNull ServerLevelAccessor level, @NotNull RandomSource random, @NotNull BoundingBox boundingBox) {
    }

    private static CappedProcessor matrixProcessor(ResourceLocation lootTable, String rarity, int cap) {
        return new CappedProcessor(new MatrixProcessor(Blocks.GRAVEL.defaultBlockState(), UP2Blocks.GRAVEL_MATRIX.get().defaultBlockState(), lootTable, rarity), ConstantInt.of(cap));
    }
}