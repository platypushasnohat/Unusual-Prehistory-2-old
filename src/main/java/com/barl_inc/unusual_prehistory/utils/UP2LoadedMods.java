package com.barl_inc.unusual_prehistory.utils;

import net.neoforged.fml.ModList;

public class UP2LoadedMods {

    private static boolean oculusLoaded;
    private static boolean dyeDepotLoaded;

    public static void afterAllModsLoaded() {
        oculusLoaded = ModList.get().isLoaded("oculus");
        dyeDepotLoaded = ModList.get().isLoaded("dye_depot");
    }

    public static boolean isOculusLoaded() {
        return oculusLoaded;
    }

    public static boolean isDyeDepotLoaded() {
        return dyeDepotLoaded;
    }
}
