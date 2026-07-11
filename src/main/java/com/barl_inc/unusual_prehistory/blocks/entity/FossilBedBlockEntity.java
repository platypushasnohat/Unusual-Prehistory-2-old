package com.barl_inc.unusual_prehistory.blocks.entity;

import com.barl_inc.unusual_prehistory.registry.UP2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class FossilBedBlockEntity extends BlockEntity {

    @Nullable
    private ResourceKey<LootTable> lootTable;
    private long lootTableSeed;

    public FossilBedBlockEntity(BlockPos pos, BlockState state) {
        super(UP2BlockEntities.FOSSIL_BED_BLOCK_ENTITY.get(), pos, state);
    }

    public void setLootTable(ResourceKey<LootTable> lootTable, long seed) {
        this.lootTable = lootTable;
        this.lootTableSeed = seed;
        this.setChanged();
    }

    @Nullable
    public ResourceKey<LootTable> getLootTable() {
        return lootTable;
    }

    public long getLootTableSeed() {
        return lootTableSeed;
    }

    public void unpackLootTable(ServerLevel level) {
        if (lootTable != null) {
            LootTable table = level.getServer().reloadableRegistries().getLootTable(lootTable);
            LootParams params = new LootParams.Builder(level).withParameter(LootContextParams.ORIGIN, worldPosition.getCenter()).create(LootContextParamSets.CHEST);
            List<ItemStack> items = table.getRandomItems(params, lootTableSeed);
            for (ItemStack stack : items) {
                Block.popResource(level, worldPosition, stack);
            }
            this.lootTable = null;
            this.setChanged();
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag, @NotNull Provider provider) {
        super.saveAdditional(compoundTag, provider);
        if (lootTable != null) {
            compoundTag.putString("LootTable", lootTable.location().toString());
            if (lootTableSeed != 0L) {
                compoundTag.putLong("LootTableSeed", lootTableSeed);
            }
        }
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag compoundTag, @NotNull Provider provider) {
        super.loadAdditional(compoundTag, provider);
        if (compoundTag.contains("LootTable")) {
            this.lootTable = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(compoundTag.getString("LootTable")));
            this.lootTableSeed = compoundTag.getLong("LootTableSeed");
        }
    }
}