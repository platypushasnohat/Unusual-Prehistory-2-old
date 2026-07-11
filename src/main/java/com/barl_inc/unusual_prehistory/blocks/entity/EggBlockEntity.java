package com.barl_inc.unusual_prehistory.blocks.entity;

import com.barl_inc.unusual_prehistory.blocks.egg.EggBlock;
import com.barl_inc.unusual_prehistory.registry.UP2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class EggBlockEntity extends BlockEntity {

    private UUID owner;
    private int hatchTime;
    private int totalHatchTime;

    public EggBlockEntity(BlockPos pos, BlockState state) {
        super(UP2BlockEntities.EGG_BLOCK_ENTITY.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, EggBlockEntity blockEntity) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        if (!(state.getBlock() instanceof EggBlock eggBlock)) {
            return;
        }

        if (!eggBlock.canHatch(level, pos)) {
            return;
        }

        int time = blockEntity.getHatchTime();
        if (time <= 0) {
            return;
        }

        time--;
        blockEntity.setHatchTime(time);

        int firstStage = (blockEntity.getTotalHatchTime() * 2) / 3;
        int secondStage = blockEntity.getTotalHatchTime() / 3;

        if ((time == firstStage || time == secondStage) && state.getValue(EggBlock.HATCH) < 2 && eggBlock.hasStages) {
            serverLevel.playSound(null, pos, SoundEvents.SNIFFER_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + level.getRandom().nextFloat() * 0.2F);
            serverLevel.setBlock(pos, state.setValue(EggBlock.HATCH, state.getValue(EggBlock.HATCH) + 1), 2);
        }

        if (time <= 0) {
            eggBlock.spawnEntity(serverLevel, pos, state, level.getRandom());
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag, @NotNull Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.putInt("HatchTime", hatchTime);
        compoundTag.putInt("TotalHatchTime", totalHatchTime);
        if (owner != null) {
            compoundTag.putUUID("Owner", owner);
        }
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag compoundTag, @NotNull Provider provider) {
        super.loadAdditional(compoundTag, provider);
        this.hatchTime = compoundTag.getInt("HatchTime");
        this.totalHatchTime = compoundTag.getInt("TotalHatchTime");
        if (compoundTag.hasUUID("Owner")) {
            this.owner = compoundTag.getUUID("Owner");
        }
    }

    public int getHatchTime() {
        return hatchTime;
    }

    public void setHatchTime(int hatchTime) {
        this.hatchTime = hatchTime;
        this.setChanged();
    }

    public int getTotalHatchTime() {
        return totalHatchTime;
    }

    public void setTotalHatchTime(int totalHatchTime) {
        this.totalHatchTime = totalHatchTime;
        this.setChanged();
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        this.setChanged();
    }
}
