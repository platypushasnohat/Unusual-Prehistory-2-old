package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.inventory.TransmogrifierMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2MenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(Registries.MENU, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<TransmogrifierMenu>> TRANSMOGRIFIER = MENU_TYPE.register("transmogrifier", () -> new MenuType<>(TransmogrifierMenu::new, FeatureFlags.DEFAULT_FLAGS));

}
