package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.UnaryOperator;

public class UP2EnumProxy {

    // Boats
    public static final EnumProxy<Boat.Type> GINKGO_BOAT_TYPE = new EnumProxy<>(Boat.Type.class, UP2Blocks.GINKGO.planks(), UnusualPrehistory2.MOD_ID + ":ginkgo", UP2Items.GINKGO_BOAT, UP2Items.GINKGO_CHEST_BOAT, Items.STICK, false);
    public static final EnumProxy<Boat.Type> LEPIDODENDRON_BOAT_TYPE = new EnumProxy<>(Boat.Type.class, UP2Blocks.LEPIDODENDRON.planks(), UnusualPrehistory2.MOD_ID + ":lepidodendron", UP2Items.LEPIDODENDRON_BOAT, UP2Items.LEPIDODENDRON_CHEST_BOAT, Items.STICK, false);

    public static final EnumProxy<Boat.Type> DRYOPHYLLUM_BOAT_TYPE = new EnumProxy<>(Boat.Type.class, UP2Blocks.DRYOPHYLLUM.planks(), UnusualPrehistory2.MOD_ID + ":dryophyllum", UP2Items.DRYOPHYLLUM_BOAT, UP2Items.DRYOPHYLLUM_CHEST_BOAT, Items.STICK, false);
    public static final EnumProxy<Boat.Type> METASEQUOIA_BOAT_TYPE = new EnumProxy<>(Boat.Type.class, UP2Blocks.METASEQUOIA.planks(), UnusualPrehistory2.MOD_ID + ":metasequoia", UP2Items.METASEQUOIA_BOAT, UP2Items.METASEQUOIA_CHEST_BOAT, Items.STICK, false);

    // Heart types
    public static final EnumProxy<Gui.HeartType> PARALYSIS_HEART_TYPE = new EnumProxy<>(Gui.HeartType.class,
            UnusualPrehistory2.modPrefix("hud/heart/paralysis_full"),
            UnusualPrehistory2.modPrefix("hud/heart/paralysis_full_blinking"),
            UnusualPrehistory2.modPrefix("hud/heart/paralysis_half"),
            UnusualPrehistory2.modPrefix("hud/heart/paralysis_half_blinking"),
            UnusualPrehistory2.modPrefix("hud/heart/paralysis_full_hardcore"),
            UnusualPrehistory2.modPrefix("hud/heart/paralysis_full_blinking_hardcore"),
            UnusualPrehistory2.modPrefix("hud/heart/paralysis_half_hardcore"),
            UnusualPrehistory2.modPrefix("hud/heart/paralysis_half_blinking_hardcore")
    );

    // Rarities
    public static final EnumProxy<Rarity> FOSSIL = new EnumProxy<>(Rarity.class, -1, UnusualPrehistory2.MOD_ID + ":fossil", (UnaryOperator<Style>) style -> style.withColor(0x986748));

    // Relic matches Sully's Mod ancient rarity (might be nice for modpack consistency :?)
    public static final EnumProxy<Rarity> RELIC = new EnumProxy<>(Rarity.class, -1, UnusualPrehistory2.MOD_ID + ":relic", (UnaryOperator<Style>) style -> style.withColor(0xe68600));

}
