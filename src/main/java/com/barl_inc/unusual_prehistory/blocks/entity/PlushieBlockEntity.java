package com.barl_inc.unusual_prehistory.blocks.entity;

import com.barl_inc.unusual_prehistory.registry.UP2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PlushieBlockEntity extends BlockEntity {

    @Nullable
    private Component customName;

    public int squishTicks = 0;
    public int prevSquishTicks = 0;

    public PlushieBlockEntity(BlockPos pos, BlockState blockState) {
        super(UP2BlockEntities.PLUSHIE_BLOCK_ENTITY.get(), pos, blockState);
    }

    public void squish() {
        this.squishTicks = 4;
        this.setChanged();
    }

    public float getSquishAmount(float partialTicks) {
        return (prevSquishTicks + (squishTicks - prevSquishTicks) * partialTicks) / 4.0F;
    }

    @SuppressWarnings("unused")
    public static void tick(Level level, BlockPos pos, BlockState state, PlushieBlockEntity blockEntity) {
        blockEntity.prevSquishTicks = blockEntity.squishTicks;
        if (blockEntity.squishTicks > 0) {
            blockEntity.squishTicks--;
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(compoundTag, provider);
        if (this.customName != null) {
            compoundTag.putString("custom_name", Component.Serializer.toJson(this.customName, provider));
        }
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag compoundTag, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(compoundTag, provider);
        if (compoundTag.contains("custom_name", 8)) {
            this.customName = parseCustomNameSafe(compoundTag.getString("custom_name"), provider);
        } else {
            this.customName = null;
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider provider) {
        return this.saveCustomOnly(provider);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.@NotNull DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        this.customName = componentInput.get(DataComponents.CUSTOM_NAME);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.@NotNull Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.CUSTOM_NAME, this.customName);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void removeComponentsFromTag(@NotNull CompoundTag compoundTag) {
        super.removeComponentsFromTag(compoundTag);
        compoundTag.remove("custom_name");
    }
}