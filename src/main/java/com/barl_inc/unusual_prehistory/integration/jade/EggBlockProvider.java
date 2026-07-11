package com.barl_inc.unusual_prehistory.integration.jade;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.blocks.entity.EggBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

public class EggBlockProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

	@Override
	public void appendServerData(CompoundTag compoundTag, BlockAccessor accessor) {
		if (accessor.getBlockEntity() instanceof EggBlockEntity eggBlockEntity) {
			compoundTag.putInt("HatchTime", eggBlockEntity.getHatchTime());
		}
	}

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
		CompoundTag compoundTag = accessor.getServerData();
		if (compoundTag.contains("HatchTime")) {
			int hatchTime = compoundTag.getInt("HatchTime");
			tooltip.add(Component.translatable("unusual_prehistory.jade.egg_block.hatch_time", IThemeHelper.get().seconds(hatchTime, accessor.tickRate())));
		}
	}

    @Override
    public ResourceLocation getUid() {
        return UnusualPrehistory2.modPrefix("egg_block");
    }
}