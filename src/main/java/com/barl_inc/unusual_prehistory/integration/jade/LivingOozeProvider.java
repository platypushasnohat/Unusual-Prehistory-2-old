package com.barl_inc.unusual_prehistory.integration.jade;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.mob.other.LivingOoze;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

public class LivingOozeProvider implements IEntityComponentProvider {

    @Override
    public ResourceLocation getUid() {
        return UnusualPrehistory2.modPrefix("living_ooze");
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        LivingOoze livingOoze = (LivingOoze) accessor.getEntity();
        if (livingOoze.hasEntity()) {
            tooltip.add(Component.translatable("unusual_prehistory.jade.living_ooze.gestation_time", IThemeHelper.get().seconds(livingOoze.getSpitTime(), accessor.tickRate())));
        }
        if (livingOoze.getCooldown() > 0) {
            tooltip.add(Component.translatable("unusual_prehistory.jade.living_ooze.gestation_cooldown", IThemeHelper.get().seconds(livingOoze.getCooldown(), accessor.tickRate())));
        }
    }
}
