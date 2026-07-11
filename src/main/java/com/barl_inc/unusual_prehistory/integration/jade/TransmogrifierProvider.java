package com.barl_inc.unusual_prehistory.integration.jade;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.blocks.entity.TransmogrifierBlockEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

import java.util.List;

public class TransmogrifierProvider implements IBlockComponentProvider, StreamServerDataProvider<BlockAccessor, TransmogrifierProvider.Data> {

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
		Data data = this.decodeFromData(accessor).orElse(null);
		if (data == null) {
			return;
		}
		IElementHelper helper = IElementHelper.get();
        tooltip.remove(JadeIds.UNIVERSAL_ITEM_STORAGE);
		tooltip.add(helper.item(data.inventory.get(0)));
		tooltip.append(helper.item(data.inventory.get(1)));
		tooltip.append(helper.spacer(4, 0));
		tooltip.append(helper.progress((float) data.progress / data.total).translate(new Vec2(-2, 0)));
		tooltip.append(helper.item(data.inventory.get(2)));
	}

	@Override
	public Data streamData(BlockAccessor accessor) {
		TransmogrifierBlockEntity blockEntity = (TransmogrifierBlockEntity) accessor.getBlockEntity();
		return new Data(blockEntity.getProcessingProgress(), blockEntity.getProcessingTotalTime(), List.of(blockEntity.getItem(0), blockEntity.getItem(1), blockEntity.getItem(2)));
	}

	@Override
	public StreamCodec<RegistryFriendlyByteBuf, Data> streamCodec() {
		return Data.STREAM_CODEC;
	}

    @Override
    public ResourceLocation getUid() {
        return UnusualPrehistory2.modPrefix("transmogrifier");
    }

    @Override
    public int getDefaultPriority() {
        return TooltipPosition.TAIL - 100;
    }

	public record Data(int progress, int total, List<ItemStack> inventory) {
		public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, Data::progress, ByteBufCodecs.VAR_INT, Data::total, ItemStack.OPTIONAL_LIST_STREAM_CODEC, Data::inventory, Data::new);
	}
}