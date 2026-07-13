package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Coelacanthus;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Diplocaulus;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Dunkleosteus;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.LobeFinnedFish;
import com.barl_inc.unusual_prehistory.entity.mob.recently_extinct.GastricBroodingFrog;
import com.barl_inc.unusual_prehistory.items.*;
import com.barl_inc.unusual_prehistory.tags.UP2BannerPatternTags;
import com.barl_inc.unusual_prehistory.utils.VariantHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UP2Items {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(UnusualPrehistory2.MOD_ID);
    public static List<DeferredItem<? extends Item>> ITEM_TRANSLATIONS = new ArrayList<>();

    // region spawn eggs
    // Paleozoic
    public static final DeferredItem<Item> ARTHROPLEURA_SPAWN_EGG = registerSpawnEggItem("arthropleura", UP2Entities.ARTHROPLEURA, 0x854885, 0xf8dc3d);
    public static final DeferredItem<Item> AEGIROCASSIS_SPAWN_EGG = registerSpawnEggItem("aegirocassis", UP2Entities.AEGIROCASSIS, 0x0068e6, 0x102354);
    public static final DeferredItem<Item> AUSTRORAPTOR_SPAWN_EGG = registerSpawnEggItem("austroraptor", UP2Entities.AUSTRORAPTOR, 0xa09c96, 0xe05240);
    public static final DeferredItem<Item> BRONTOSCORPIO_SPAWN_EGG = registerSpawnEggItem("brontoscorpio", UP2Entities.BRONTOSCORPIO, 0x2f3a4a, 0x5bb548);
    public static final DeferredItem<Item> CAMEROCERAS_SPAWN_EGG = registerSpawnEggItem("cameroceras", UP2Entities.CAMEROCERAS, 0x4ebbc2, 0xf4415f);
    public static final DeferredItem<Item> COELACANTHUS_SPAWN_EGG = registerSpawnEggItem("coelacanthus", UP2Entities.COELACANTHUS, 0xea7a12, 0xa52d14);
    public static final DeferredItem<Item> COTYLORHYNCHUS_SPAWN_EGG = registerSpawnEggItem("cotylorhynchus", UP2Entities.COTYLORHYNCHUS, 0x944e32, 0xebe0c5);
    public static final DeferredItem<Item> DIPLOCAULUS_SPAWN_EGG = registerSpawnEggItem("diplocaulus", UP2Entities.DIPLOCAULUS, 0xe5721e, 0x292733);
    public static final DeferredItem<Item> DUNKLEOSTEUS_SPAWN_EGG = registerSpawnEggItem("dunkleosteus", UP2Entities.DUNKLEOSTEUS, 0x478975, 0x673d35);
    public static final DeferredItem<Item> HIBBERTOPTERUS_SPAWN_EGG = registerSpawnEggItem("hibbertopterus", UP2Entities.HIBBERTOPTERUS, 0xe4b57c, 0x5d3a2f);
    public static final DeferredItem<Item> HYNERPETON_SPAWN_EGG = registerSpawnEggItem("hynerpeton", UP2Entities.HYNERPETON, 0x292623, 0xefa32b);
    public static final DeferredItem<Item> JAWLESS_FISH_SPAWN_EGG = registerSpawnEggItem("jawless_fish", UP2Entities.JAWLESS_FISH, 0x312e38, 0x917388);
    public static final DeferredItem<Item> LOBE_FINNED_FISH_SPAWN_EGG = registerSpawnEggItem("lobe_finned_fish", UP2Entities.LOBE_FINNED_FISH, 0x387fb6, 0x3b4385);
    public static final DeferredItem<Item> RHIZODUS_SPAWN_EGG = registerSpawnEggItem("rhizodus", UP2Entities.RHIZODUS, 0x2b4426, 0x54adaa);
    public static final DeferredItem<Item> STETHACANTHUS_SPAWN_EGG = registerSpawnEggItem("stethacanthus", UP2Entities.STETHACANTHUS, 0x6e2e1f, 0xffa200);
    public static final DeferredItem<Item> TARTUOSTEUS_SPAWN_EGG = registerSpawnEggItem("tartuosteus", UP2Entities.TARTUOSTEUS, 0x23673c, 0x0b2720);

    // Mesozoic
    public static final DeferredItem<Item> ANTARCTOPELTA_SPAWN_EGG = registerSpawnEggItem("antarctopelta", UP2Entities.ANTARCTOPELTA, 0x201a17, 0xf3e2db);
    public static final DeferredItem<Item> AQUILOLAMNA_SPAWN_EGG = registerSpawnEggItem("aquilolamna", UP2Entities.AQUILOLAMNA, 0x896a42, 0xfbf6ea);
    public static final DeferredItem<Item> BRACHIOSAURUS_SPAWN_EGG = registerSpawnEggItem("brachiosaurus", UP2Entities.BRACHIOSAURUS, 0x285685, 0x141d26);
    public static final DeferredItem<Item> CARNOTAURUS_SPAWN_EGG = registerSpawnEggItem("carnotaurus", UP2Entities.CARNOTAURUS, 0x8c2f27, 0x252b33);
    public static final DeferredItem<Item> CONCAVENATOR_SPAWN_EGG = registerSpawnEggItem("concavenator", UP2Entities.CONCAVENATOR, 0x6e3f34, 0xcdbc91);
    public static final DeferredItem<Item> CRYPTOCLIDUS_SPAWN_EGG = registerSpawnEggItem("cryptoclidus", UP2Entities.CRYPTOCLIDUS, 0x786389, 0xb4d6d6);
    public static final DeferredItem<Item> DESMATOSUCHUS_SPAWN_EGG = registerSpawnEggItem("desmatosuchus", UP2Entities.DESMATOSUCHUS, 0x272d3e, 0xba7725);
    public static final DeferredItem<Item> DROMAEOSAURUS_SPAWN_EGG = registerSpawnEggItem("dromaeosaurus", UP2Entities.DROMAEOSAURUS, 0xf9fa5e, 0xebc754);
    public static final DeferredItem<Item> HENODUS_SPAWN_EGG = registerSpawnEggItem("henodus", UP2Entities.HENODUS, 0x7f4617, 0xb2d746);
    public static final DeferredItem<Item> HESPERORNIS_SPAWN_EGG = registerSpawnEggItem("hesperornis", UP2Entities.HESPERORNIS, 0x24274c, 0xf09f57);
    public static final DeferredItem<Item> ICHTHYOSAURUS_SPAWN_EGG = registerSpawnEggItem("ichthyosaurus", UP2Entities.ICHTHYOSAURUS, 0x4e5761, 0xd2c629);
    public static final DeferredItem<Item> KAPROSUCHUS_SPAWN_EGG = registerSpawnEggItem("kaprosuchus", UP2Entities.KAPROSUCHUS, 0x3a2523, 0xffb35c);
    public static final DeferredItem<Item> KENTROSAURUS_SPAWN_EGG = registerSpawnEggItem("kentrosaurus", UP2Entities.KENTROSAURUS, 0x5a6637, 0x1d1f18);
    public static final DeferredItem<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_SPAWN_EGG = registerSpawnEggItem("kimmeridgebrachypteraeschnidium", UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM, 0x74c70f, 0x8a3466);
    public static final DeferredItem<Item> LORRAINOSAURUS_SPAWN_EGG = registerSpawnEggItem("lorrainosaurus", UP2Entities.LORRAINOSAURUS, 0x4b3188, 0xcbdedd);
    public static final DeferredItem<Item> LEEDSICHTHYS_SPAWN_EGG = registerSpawnEggItem("leedsichthys", UP2Entities.LEEDSICHTHYS, 0x415778, 0x0f0f16);
    public static final DeferredItem<Item> MAJUNGASAURUS_SPAWN_EGG = registerSpawnEggItem("majungasaurus", UP2Entities.MAJUNGASAURUS, 0x5aa81e, 0x194e80);
    public static final DeferredItem<Item> METRIORHYNCHUS_SPAWN_EGG = registerSpawnEggItem("metriorhynchus", UP2Entities.METRIORHYNCHUS, 0x3a2c4e, 0x9d66ad);
    public static final DeferredItem<Item> ONCHOPRISTIS_SPAWN_EGG = registerSpawnEggItem("onchopristis", UP2Entities.ONCHOPRISTIS, 0xa27e47, 0x382b1e);
    public static final DeferredItem<Item> PACHYCEPHALOSAURUS_SPAWN_EGG = registerSpawnEggItem("pachycephalosaurus", UP2Entities.PACHYCEPHALOSAURUS, 0x4f3d72, 0x67bedb);
    public static final DeferredItem<Item> PACHYRHINOSAURUS_SPAWN_EGG = registerSpawnEggItem("pachyrhinosaurus", UP2Entities.PACHYRHINOSAURUS, 0x333835, 0xc9dbd7);
    public static final DeferredItem<Item> PROGNATHODON_SPAWN_EGG = registerSpawnEggItem("prognathodon", UP2Entities.PROGNATHODON, 0x181c2a, 0x5b9ba6);
    public static final DeferredItem<Item> PTERODACTYLUS_SPAWN_EGG = registerSpawnEggItem("pterodactylus", UP2Entities.PTERODACTYLUS, 0x634b39, 0xffdb63);
    public static final DeferredItem<Item> SALTOPUS_SPAWN_EGG = registerSpawnEggItem("saltopus", UP2Entities.SALTOPUS, 0xbef2ec, 0x617392);
    public static final DeferredItem<Item> SHRINGASAURUS_SPAWN_EGG = registerSpawnEggItem("shringasaurus", UP2Entities.SHRINGASAURUS, 0x356638, 0xe16ca0);
    public static final DeferredItem<Item> THERIZINOSAURUS_SPAWN_EGG = registerSpawnEggItem("therizinosaurus", UP2Entities.THERIZINOSAURUS, 0x0f0e0e, 0x514699);
    public static final DeferredItem<Item> TUSOTEUTHIS_SPAWN_EGG = registerSpawnEggItem("tusoteuthis", UP2Entities.TUSOTEUTHIS, 0x2e224e, 0xfaf758);
    public static final DeferredItem<Item> ULUGHBEGSAURUS_SPAWN_EGG = registerSpawnEggItem("ulughbegsaurus", UP2Entities.ULUGHBEGSAURUS, 0x423534, 0x4198e1);

    // Cenozoic
    public static final DeferredItem<Item> DIRE_WOLF_SPAWN_EGG = registerSpawnEggItem("dire_wolf", UP2Entities.DIRE_WOLF, 0xede1d9, 0x7b2929);
    public static final DeferredItem<Item> GIANT_CAMPANILE_SPAWN_EGG = registerSpawnEggItem("giant_campanile", UP2Entities.GIANT_CAMPANILE, 0x854048, 0x5e1717);
    public static final DeferredItem<Item> KING_LINGCOD_SPAWN_EGG = registerSpawnEggItem("king_lingcod", UP2Entities.KING_LINGCOD, 0x4d7278, 0x2a2f4a);
    public static final DeferredItem<Item> LEPTICTIDIUM_SPAWN_EGG = registerSpawnEggItem("leptictidium", UP2Entities.LEPTICTIDIUM, 0xac6746, 0x693d3d);
    public static final DeferredItem<Item> MEGALANIA_SPAWN_EGG = registerSpawnEggItem("megalania", UP2Entities.MEGALANIA, 0x4f432b, 0x0fd4e6);
    public static final DeferredItem<Item> NIHOHAE_SPAWN_EGG = registerSpawnEggItem("nihohae", UP2Entities.NIHOHAE, 0x5a3434, 0xfddb58);
    public static final DeferredItem<Item> PRAEPUSA_SPAWN_EGG = registerSpawnEggItem("praepusa", UP2Entities.PRAEPUSA, 0x4e5056, 0xcfcfc8);
    public static final DeferredItem<Item> PSILOPTERUS_SPAWN_EGG = registerSpawnEggItem("psilopterus", UP2Entities.PSILOPTERUS, 0x312e2a, 0xe3dacf);
    public static final DeferredItem<Item> SPIKE_TOOTHED_SALMON_SPAWN_EGG = registerSpawnEggItemNoLang("spike_toothed_salmon", UP2Entities.SPIKE_TOOTHED_SALMON, 0x69579b, 0xeb915e);
    public static final DeferredItem<Item> TALPANAS_SPAWN_EGG = registerSpawnEggItem("talpanas", UP2Entities.TALPANAS, 0x503527, 0x93ad87);
    public static final DeferredItem<Item> TELECREX_SPAWN_EGG = registerSpawnEggItem("telecrex", UP2Entities.TELECREX, 0x0e0c12, 0x931345);
    public static final DeferredItem<Item> WOOLLY_MAMMOTH_SPAWN_EGG = registerSpawnEggItem("woolly_mammoth", UP2Entities.WOOLLY_MAMMOTH, 0x66331a, 0x1a0901);

    // Multi-Era
    public static final DeferredItem<Item> AMMONITE_SPAWN_EGG = registerSpawnEggItem("ammonite", UP2Entities.AMMONITE, 0x715439, 0xe77eb8);
    public static final DeferredItem<Item> LYSTROSAURUS_SPAWN_EGG = registerSpawnEggItem("lystrosaurus", UP2Entities.LYSTROSAURUS, 0xaa9c65, 0x675a43);

    // Recently Extinct
    public static final DeferredItem<Item> GASTRIC_BROODING_FROG_SPAWN_EGG = registerSpawnEggItemNoLang("gastric_brooding_frog", UP2Entities.GASTRIC_BROODING_FROG, 0x5e3825, 0xe4b64d);
    public static final DeferredItem<Item> THYLACINE_SPAWN_EGG = registerSpawnEggItem("thylacine", UP2Entities.THYLACINE, 0xca8e2d, 0x2a231b);

    // Ambient
    public static final DeferredItem<Item> AMPYX_SPAWN_EGG = registerSpawnEggItem("ampyx", UP2Entities.AMPYX, 0x5c33d7, 0xf479f9);
    public static final DeferredItem<Item> DELITZSCHALA_SPAWN_EGG = registerSpawnEggItem("delitzschala", UP2Entities.DELITZSCHALA, 0xe38948, 0x1e512e);
    public static final DeferredItem<Item> SETAPEDITES_SPAWN_EGG = registerSpawnEggItem("setapedites", UP2Entities.SETAPEDITES, 0xd97ff2, 0xb12797);
    public static final DeferredItem<Item> ZHANGSOLVA_SPAWN_EGG = registerSpawnEggItem("zhangsolva", UP2Entities.ZHANGSOLVA, 0x4e371d, 0xff4731);

    // Other
    public static final DeferredItem<Item> GRUG_SPAWN_EGG = registerSpawnEggItem("grug", UP2Entities.GRUG, 0x433536, 0xffc27d);
    public static final DeferredItem<Item> LINGCOD_SPAWN_EGG = registerSpawnEggItem("lingcod", UP2Entities.LINGCOD, 0x6a584c, 0x362a31);
    public static final DeferredItem<Item> LIVING_OOZE_SPAWN_EGG = registerSpawnEggItem("living_ooze", UP2Entities.LIVING_OOZE, 0x51da69, 0x055b2f);
    public static final DeferredItem<Item> UNICORN_SPAWN_EGG = registerSpawnEggItem("unicorn", UP2Entities.UNICORN, 0x6f5848, 0xe5d6b7);
    // endregion

    // region fossils
    // Paleozoic fossils
    public static final DeferredItem<Item> BRISTLE_FOSSIL = registerFossilItem("bristle"); // aegirocassis
    public static final DeferredItem<Item> FORCIPULE_FOSSIL = registerFossilItem("forcipule"); // arthropleura
    public static final DeferredItem<Item> THUNDEROUS_FOSSIL = registerFossilItem("thunderous"); // brontoscorpio
    public static final DeferredItem<Item> ROCKET_FOSSIL = registerFossilItem("rocket"); // cameroceras
    public static final DeferredItem<Item> GLUTTONOUS_FOSSIL = registerFossilItem("gluttonous"); // coelacanthus
    public static final DeferredItem<Item> ROTUND_FOSSIL = registerFossilItem("rotund"); // cotylorhynchus
    public static final DeferredItem<Item> BOOMERANG_FOSSIL = registerFossilItem("boomerang"); // diplocaulus
    public static final DeferredItem<Item> GUILLOTINE_FOSSIL = registerFossilItem("guillotine"); // dunkleosteus
    public static final DeferredItem<Item> PLOW_FOSSIL = registerFossilItem("plow"); // hibbertopterus
    public static final DeferredItem<Item> COMBUSTIBLE_FOSSIL = registerFossilItem("combustible"); // hynerpeton
    public static final DeferredItem<Item> JAWLESS_FOSSIL = registerFossilItem("jawless"); // jawless fish
    public static final DeferredItem<Item> FISH_FOSSIL = registerFossilItem("fish"); // lobe finned fish
    public static final DeferredItem<Item> SUCTION_FOSSIL = registerFossilItem("suction"); // rhizodus
    public static final DeferredItem<Item> ANVIL_FOSSIL = registerFossilItem("anvil"); // stethacanthus
    public static final DeferredItem<Item> MOSSY_FOSSIL = registerFossilItem("mossy"); // tartuosteus

    // Mesozoic fossils
    public static final DeferredItem<Item> SNOW_SHOVEL_FOSSIL = registerFossilItem("snow_shovel"); // antarctopelta
    public static final DeferredItem<Item> GRACILE_FOSSIL = registerFossilItem("gracile"); // austroraptor
    public static final DeferredItem<Item> ARM_FOSSIL = registerFossilItem("arm"); // brachiosaurus
    public static final DeferredItem<Item> FURY_FOSSIL = registerFossilItem("fury"); // carnotaurus
    public static final DeferredItem<Item> CONCAVE_FOSSIL = registerFossilItem("concave"); // concavenator
    public static final DeferredItem<Item> CRYPTIC_FOSSIL = registerFossilItem("cryptic"); // cryptoclidus
    public static final DeferredItem<Item> FLAT_BACK_FOSSIL = registerFossilItem("flat_back"); // desmatosuchus
    public static final DeferredItem<Item> RUNNER_FOSSIL = registerFossilItem("runner"); // dromaeosaurus
    public static final DeferredItem<Item> FISH_REPTILE_FOSSIL = registerFossilItem("fish_reptile"); // ichthyosaurus
    public static final DeferredItem<Item> BOAR_TOOTH_FOSSIL = registerFossilItem("boar_tooth"); // kaprosuchus
    public static final DeferredItem<Item> PRICKLY_FOSSIL = registerFossilItem("prickly"); // kentrosaurus
    public static final DeferredItem<Item> IMPERATIVE_FOSSIL = registerFossilItem("imperative"); // kimmeridgebrachypteraeschnidium
    public static final DeferredItem<Item> GARGANTUAN_FOSSIL = registerFossilItem("gargantuan"); // leedsichthys
    public static final DeferredItem<Item> CLAMP_JAW_FOSSIL = registerFossilItem("clamp_jaw"); // lorrainosaurus
    public static final DeferredItem<Item> RUGOSE_FOSSIL = registerFossilItem("rugose"); // majungasaurus
    public static final DeferredItem<Item> MELTDOWN_FOSSIL = registerFossilItem("meltdown"); // metriorhynchus
    public static final DeferredItem<Item> SAW_FOSSIL = registerFossilItem("saw"); // onchopristis
    public static final DeferredItem<Item> CRANIUM_FOSSIL = registerFossilItem("cranium"); // pachycephalosaurus
    public static final DeferredItem<Item> SURGE_FOSSIL = registerFossilItem("surge"); // prognathodon
    public static final DeferredItem<Item> WING_FOSSIL = registerFossilItem("wing"); // pterodactylus
    public static final DeferredItem<Item> SCYTHE_FOSSIL = registerFossilItem("scythe"); // therizinosaurus
    public static final DeferredItem<Item> SHIMMER_FOSSIL = registerFossilItem("shimmer"); // tusoteuthis
    public static final DeferredItem<Item> DUBIOUS_FOSSIL = registerFossilItem("dubious"); // ulughbegsaurus

    // Cenozoic fossils
    public static final DeferredItem<Item> PILLAR_FOSSIL = registerFossilItem("pillar"); // giant campanile
    public static final DeferredItem<Item> CROWN_FOSSIL = registerFossilItem("crown"); // king lingcod
    public static final DeferredItem<Item> TRUNK_MOUSE_FOSSIL = registerFossilItem("trunk_mouse"); // leptictidium
    public static final DeferredItem<Item> THERMAL_FOSSIL = registerFossilItem("thermal"); // megalania
    public static final DeferredItem<Item> DOLPHIN_FOSSIL = registerFossilItem("dolphin"); // nihohae
    public static final DeferredItem<Item> FLIPPER_FOSSIL = registerFossilItem("flipper"); // praepusa
    public static final DeferredItem<Item> CROOKED_BEAK_FOSSIL = registerFossilItem("crooked_beak"); // psilopterus
    public static final DeferredItem<Item> ROT_FOSSIL = registerFossilItem("rot"); // spike-toothed salmon
    // todo: talpanas fossil
    public static final DeferredItem<Item> AGED_FEATHER = registerItem("aged_feather", () -> new Item(new Item.Properties())); // talpanas
    public static final DeferredItem<Item> PLUMAGE_FOSSIL = registerFossilItem("plumage"); // telecrex
    public static final DeferredItem<Item> MOLAR_FOSSIL = registerFossilItem("molar"); // woolly mammoth

    // Multi-Era fossils
    public static final DeferredItem<Item> SPIRAL_FOSSIL = registerFossilItem("spiral"); // ammonite
    public static final DeferredItem<Item> IMPERVIOUS_FOSSIL = registerFossilItem("impervious"); // lystrosaurus

    // Recently Extinct fossils
    public static final DeferredItem<Item> STRIPED_PELT = registerItem("striped_pelt", () -> new Item(new Item.Properties())); // thylacine
    // endregion

    // region revival
    public static final DeferredItem<Item> PALEOPEDIA = registerItem("paleopedia", () -> new PaleopediaItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ORGANIC_OOZE = registerItem("organic_ooze", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LIVING_OOZE_BUCKET = registerItemNoLang("living_ooze_bucket", () -> new LivingOozeBucketItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MACHINE_PARTS = registerItem("machine_parts", () -> new Item(new Item.Properties()));

    // Paleozoic revival
    public static final DeferredItem<Item> BRONTOSCORPIO_EMBRYO = registerEmbryoItem("brontoscorpio", UP2Entities.BRONTOSCORPIO);

    // Mesozoic revival
    public static final DeferredItem<Item> AUSTRORAPTOR_EGG = registerEggItem("austroraptor", UP2Entities.AUSTRORAPTOR_EGG);
    public static final DeferredItem<Item> CRYPTOCLIDUS_EMBRYO = registerEmbryoItem("cryptoclidus", UP2Entities.CRYPTOCLIDUS);
    public static final DeferredItem<Item> DROMAEOSAURUS_EGG = registerEggItem("dromaeosaurus", UP2Entities.DROMAEOSAURUS_EGG);
    public static final DeferredItem<Item> ICHTHYOSAURUS_EMBRYO = registerEmbryoItem("ichthyosaurus", UP2Entities.ICHTHYOSAURUS);
    public static final DeferredItem<Item> LORRAINOSAURUS_EMBRYO = registerEmbryoItem("lorrainosaurus", UP2Entities.LORRAINOSAURUS);
    public static final DeferredItem<Item> METRIORHYNCHUS_EMBRYO = registerEmbryoItem("metriorhynchus", UP2Entities.METRIORHYNCHUS);
    public static final DeferredItem<Item> PROGNATHODON_EMBRYO = registerEmbryoItem("prognathodon", UP2Entities.PROGNATHODON);
    public static final DeferredItem<Item> PTERODACTYLUS_EGG = registerEggItem("pterodactylus", UP2Entities.PTERODACTYLUS_EGG);

    // Cenozoic revival
    public static final DeferredItem<Item> LEPTICTIDIUM_EMBRYO = registerEmbryoItem("leptictidium", UP2Entities.LEPTICTIDIUM);
    public static final DeferredItem<Item> NIHOHAE_EMBRYO = registerEmbryoItem("nihohae", UP2Entities.NIHOHAE);
    public static final DeferredItem<Item> PRAEPUSA_EMBRYO = registerEmbryoItem("praepusa", UP2Entities.PRAEPUSA);
    public static final DeferredItem<Item> PSILOPTERUS_EGG = registerEggItem("psilopterus", UP2Entities.PSILOPTERUS_EGG);
    public static final DeferredItem<Item> TALPANAS_EGG = registerEggItem("talpanas", UP2Entities.TALPANAS_EGG);
    public static final DeferredItem<Item> TELECREX_EGG = registerEggItem("telecrex", UP2Entities.TELECREX_EGG);
    public static final DeferredItem<Item> WOOLLY_MAMMOTH_EMBRYO = registerEmbryoItem("woolly_mammoth", UP2Entities.WOOLLY_MAMMOTH);

    // Multi-Era revival

    // Recently Extinct revival
    public static final DeferredItem<Item> THYLACINE_EMBRYO = registerEmbryoItem("thylacine", UP2Entities.THYLACINE);

    // Plants
    public static final DeferredItem<Item> DRYOPHYLLUM_SIGN = registerItem("dryophyllum_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), UP2Blocks.DRYOPHYLLUM.sign().get(), UP2Blocks.DRYOPHYLLUM.wallSign().get()));
    public static final DeferredItem<Item> DRYOPHYLLUM_HANGING_SIGN = registerItem("dryophyllum_hanging_sign", () -> new HangingSignItem(UP2Blocks.DRYOPHYLLUM.hangingSign().get(), UP2Blocks.DRYOPHYLLUM.hangingWallSign().get(), (new Item.Properties()).stacksTo(16)));
    public static final DeferredItem<Item> DRYOPHYLLUM_BOAT = registerItem("dryophyllum_boat", () -> new BoatItem(false, UP2EnumProxy.DRYOPHYLLUM_BOAT_TYPE.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> DRYOPHYLLUM_CHEST_BOAT = registerItemNoLang("dryophyllum_chest_boat", () -> new BoatItem(true, UP2EnumProxy.DRYOPHYLLUM_BOAT_TYPE.getValue(), new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> GINKGO_FRUIT = registerItem("ginkgo_fruit", () -> new Item(registerFoodValue(UP2FoodValues.GINKGO_FRUIT)));
    public static final DeferredItem<Item> GINKGO_SIGN = registerItem("ginkgo_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), UP2Blocks.GINKGO.sign().get(), UP2Blocks.GINKGO.wallSign().get()));
    public static final DeferredItem<Item> GINKGO_HANGING_SIGN = registerItem("ginkgo_hanging_sign", () -> new HangingSignItem(UP2Blocks.GINKGO.hangingSign().get(), UP2Blocks.GINKGO.hangingWallSign().get(), (new Item.Properties()).stacksTo(16)));
    public static final DeferredItem<Item> GINKGO_BOAT = registerItem("ginkgo_boat", () -> new BoatItem(false, UP2EnumProxy.GINKGO_BOAT_TYPE.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GINKGO_CHEST_BOAT = registerItemNoLang("ginkgo_chest_boat", () -> new BoatItem(true, UP2EnumProxy.GINKGO_BOAT_TYPE.getValue(), new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> LEPIDODENDRON_SIGN = registerItem("lepidodendron_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), UP2Blocks.LEPIDODENDRON.sign().get(), UP2Blocks.LEPIDODENDRON.wallSign().get()));
    public static final DeferredItem<Item> LEPIDODENDRON_HANGING_SIGN = registerItem("lepidodendron_hanging_sign", () -> new HangingSignItem(UP2Blocks.LEPIDODENDRON.hangingSign().get(), UP2Blocks.LEPIDODENDRON.hangingWallSign().get(), (new Item.Properties()).stacksTo(16)));
    public static final DeferredItem<Item> LEPIDODENDRON_BOAT = registerItem("lepidodendron_boat", () -> new BoatItem(false, UP2EnumProxy.LEPIDODENDRON_BOAT_TYPE.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> LEPIDODENDRON_CHEST_BOAT = registerItemNoLang("lepidodendron_chest_boat", () -> new BoatItem(true, UP2EnumProxy.LEPIDODENDRON_BOAT_TYPE.getValue(), new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> METASEQUOIA_SIGN = registerItem("metasequoia_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), UP2Blocks.METASEQUOIA.sign().get(), UP2Blocks.METASEQUOIA.wallSign().get()));
    public static final DeferredItem<Item> METASEQUOIA_HANGING_SIGN = registerItem("metasequoia_hanging_sign", () -> new HangingSignItem(UP2Blocks.METASEQUOIA.hangingSign().get(), UP2Blocks.METASEQUOIA.hangingWallSign().get(), (new Item.Properties()).stacksTo(16)));
    public static final DeferredItem<Item> METASEQUOIA_BOAT = registerItem("metasequoia_boat", () -> new BoatItem(false, UP2EnumProxy.METASEQUOIA_BOAT_TYPE.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> METASEQUOIA_CHEST_BOAT = registerItemNoLang("metasequoia_chest_boat", () -> new BoatItem(true, UP2EnumProxy.METASEQUOIA_BOAT_TYPE.getValue(), new Item.Properties().stacksTo(1)));
    // endregion

    // region misc
    // Paleozoic items
    public static final DeferredItem<Item> BABY_AEGIROCASSIS_BUCKET = registerItemNoLang("baby_aegirocassis_bucket", () -> new UP2MobBucketItem(UP2Entities.AEGIROCASSIS.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> COELACANTHUS_BUCKET = registerItemNoLang("coelacanthus_bucket", () -> new UP2MobBucketItem(UP2Entities.COELACANTHUS.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1), VariantHelper.nameOf(Coelacanthus.CoelacanthusVariant::byId)));
    public static final DeferredItem<Item> DIPLOCAULUS_BUCKET = registerItemNoLang("diplocaulus_bucket", () -> new UP2MobBucketItem(UP2Entities.DIPLOCAULUS.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties(), VariantHelper.nameOf(Diplocaulus.DiplocaulusVariant::byId)));
    public static final DeferredItem<Item> DUNKLEOSTEUS_BUCKET = registerItemNoLang("dunkleosteus_bucket", () -> new UP2MobBucketItem(UP2Entities.DUNKLEOSTEUS.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties(), VariantHelper.nameOf(Dunkleosteus.DunkleosteusVariant::byId)));
    public static final DeferredItem<Item> HYNERPETON_BUCKET = registerItemNoLang("hynerpeton_bucket", () -> new UP2MobBucketItem(UP2Entities.HYNERPETON.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> JAWLESS_FISH_BUCKET = registerItemNoLang("jawless_fish_bucket", () -> new UP2MobBucketItem(UP2Entities.JAWLESS_FISH.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> LOBE_FINNED_FISH_BUCKET = registerItemNoLang("lobe_finned_fish_bucket", () -> new UP2MobBucketItem(UP2Entities.LOBE_FINNED_FISH.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1), VariantHelper.nameOf(LobeFinnedFish.LobeFinnedFishVariant::byId)));
    public static final DeferredItem<Item> STETHACANTHUS_BUCKET = registerItemNoLang("stethacanthus_bucket", () -> new UP2MobBucketItem(UP2Entities.STETHACANTHUS.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties()));

    public static final DeferredItem<Item> SWEET_GROG_BOTTLE = registerItem("sweet_grog_bottle", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FOUL_GROG_BOTTLE = registerItem("foul_grog_bottle", () -> new Item(new Item.Properties()));

    // Mesozoic items
    public static final DeferredItem<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE = registerItemNoLang("kimmeridgebrachypteraeschnidium_bottle", () -> new KimmeridgebrachypteraeschnidiumBottleItem(new Item.Properties()));
    public static final DeferredItem<Item> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET = registerItemNoLang("kimmeridgebrachypteraeschnidium_nymph_bucket", () -> new UP2MobBucketItem(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties()));
    public static final DeferredItem<Item> ONCHOPRISTIS_BUCKET = registerItemNoLang("onchopristis_bucket", () -> new UP2MobBucketItem(UP2Entities.ONCHOPRISTIS.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> PTERODACTYLUS_POT = registerItemNoLang("pterodactylus_pot", () -> new PterodactylusPotItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> LEEDSICHTHYS_SLICE = registerItem("leedsichthys_slice", () -> new Item(registerFoodValue(UP2FoodValues.LEEDSICHTHYS_SLICE)));

    // Cenozoic items
    public static final DeferredItem<Item> PRAEPUSA_BUCKET = registerItemNoLang("praepusa_bucket", () -> new UP2MobBucketItem(UP2Entities.PRAEPUSA.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));

    // Multi-Era items

    // Recently Extinct items
    public static final DeferredItem<Item> GASTRIC_BROODING_FROG_BUCKET = registerItemNoLang("gastric_brooding_frog_bucket", () -> new UP2MobBucketItem(UP2Entities.GASTRIC_BROODING_FROG.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1), VariantHelper.nameOf(GastricBroodingFrog.GastricBroodingFrogVariant::byId)));

    // Other items
    public static final DeferredItem<Item> BROWN_MUSHROOM_ON_A_STICK = registerItemNoLang("brown_mushroom_on_a_stick", () -> new BrownMushroomOnAStickItem((new Item.Properties()).durability(100), 1));
    public static final DeferredItem<Item> DIRT_ON_A_STICK = registerItemNoLang("dirt_on_a_stick", () -> new UP2FoodOnAStickItem((new Item.Properties()).durability(20), 4));

    public static final DeferredItem<Item> PLUSHIE_KIT = registerItem("plushie_kit", () -> new PlushieKitItem(new Item.Properties()));

    public static final DeferredItem<Item> AMBER_BEAR = registerItem("amber_bear", () -> new RelicItem(1, 5000, 10000, new Item.Properties()));
    public static final DeferredItem<Item> COPROLITE = registerItem("coprolite", () -> new RelicItem(3, 10, 400, new Item.Properties()));
    public static final DeferredItem<Item> FOSSILIZED_FOOTPRINT = registerItem("fossilized_footprint", () -> new RelicItem(3, 66, 237, new Item.Properties()));
    public static final DeferredItem<Item> GASTROLITH = registerItem("gastrolith", () -> new RelicItem(3, 66, 199, new Item.Properties()));
    public static final DeferredItem<Item> PETRIFIED_EGG = registerItem("petrified_egg", () -> new RelicItem(3, 100, 316, new Item.Properties()));
    public static final DeferredItem<Item> INDOCHINITE = registerItem("indochinite", () -> new RelicItem(2, 700, 800, new Item.Properties()));
    public static final DeferredItem<Item> FOSSILIZED_METEORITE = registerItem("fossilized_meteorite", () -> new RelicItem(3, 400, 600, new Item.Properties()));
    public static final DeferredItem<Item> CHONDRITE = registerItem("chondrite", () -> new RelicItem(4, 40, 70, new Item.Properties()));
    public static final DeferredItem<Item> PALLASITE = registerItem("pallasite", () -> new RelicItem(4, 40, 70, new Item.Properties()));

    public static final DeferredItem<Item> TAR_BUCKET = registerItem("tar_bucket", () -> new BucketItem(UP2Fluids.TAR_FLUID_SOURCE.get(), new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));

    // chips
    public static final DeferredItem<Item> DOOMSURF_DISC = registerItemNoLang("doomsurf_disc", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(UP2JukeboxSongs.DOOMSURF)));
    public static final DeferredItem<Item> PUMMEL_AND_SNATCH_DISC = registerItemNoLang("pummel_and_snatch_disc", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(UP2JukeboxSongs.PUMMEL_AND_SNATCH)));
    // val
    public static final DeferredItem<Item> MEGALANIA_DISC = registerItemNoLang("megalania_disc", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(UP2JukeboxSongs.MEGALANIA)));
    // dylan
    public static final DeferredItem<Item> TARIFYING_DISC = registerItemNoLang("tarifying_disc", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(UP2JukeboxSongs.TARIFYING)));

    public static final DeferredItem<Item> PALEOZOIC_BANNER_PATTERN = registerItemNoLang("paleozoic_banner_pattern", () -> new BannerPatternItem(UP2BannerPatternTags.PALEOZOIC_BANNER_PATTERN, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MESOZOIC_BANNER_PATTERN = registerItemNoLang("mesozoic_banner_pattern", () -> new BannerPatternItem(UP2BannerPatternTags.MESOZOIC_BANNER_PATTERN, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> CENOZOIC_BANNER_PATTERN = registerItemNoLang("cenozoic_banner_pattern", () -> new BannerPatternItem(UP2BannerPatternTags.CENOZOIC_BANNER_PATTERN, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> OOZE_BANNER_PATTERN = registerItemNoLang("ooze_banner_pattern", () -> new BannerPatternItem(UP2BannerPatternTags.OOZE_BANNER_PATTERN, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> UNUSUAL_PREHISTORY = registerItem("unusual_prehistory", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> UNKNOWN_FOSSIL = registerItemNoLang("unknown_fossil", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> UNKNOWN_EGG = registerItemNoLang("unknown_egg", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PLANT_FOSSIL = registerItem("plant_fossil", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CENOZOIC_FOSSIL = registerItem("cenozoic_fossil", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> HOLOCENE_FOSSIL = registerItem("holocene_fossil", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MESOZOIC_FOSSIL = registerItem("mesozoic_fossil", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PALEOZOIC_FOSSIL = registerItem("paleozoic_fossil", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CREATIVE_LOCK = registerItem("creative_lock", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PERIOD_CAMBRIAN = registerItem("period_cambrian", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PERIOD_ORDOVICIAN = registerItem("period_ordovician", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PERIOD_SILURIAN = registerItem("period_silurian", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PERIOD_DEVONIAN = registerItem("period_devonian", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PERIOD_CARBONIFEROUS = registerItem("period_carboniferous", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PERIOD_PERMIAN = registerItem("period_permian", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PERIOD_TRIASSIC = registerItem("period_triassic", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PERIOD_JURASSIC = registerItem("period_jurassic", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PERIOD_CRETACEOUS = registerItem("period_cretaceous", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PERIOD_PALEOGENE = registerItem("period_paleogene", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PERIOD_NEOGENE = registerItem("period_neogene", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PERIOD_QUATERNARY = registerItem("period_quaternary", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PERIOD_HOLOCENE = registerItem("period_holocene", () -> new Item(new Item.Properties()));
    // endregion

    // region to remove
    public static final DeferredItem<Item> QUILLWORT_FOSSIL = registerFossilItem("quillwort");
    public static final DeferredItem<Item> GINKGO_FOSSIL = registerFossilItem("ginkgo");
    public static final DeferredItem<Item> LEPIDODENDRON_FOSSIL = registerFossilItem("lepidodendron");
    public static final DeferredItem<Item> BRACHYPHYLLUM_FOSSIL = registerFossilItem("brachyphyllum");
    public static final DeferredItem<Item> CYCAD_FOSSIL = registerFossilItem("cycad");
    public static final DeferredItem<Item> GUANGDEDENDRON_FOSSIL = registerFossilItem("guangdedendron");
    public static final DeferredItem<Item> PROTOTAXITES_FOSSIL = registerFossilItem("prototaxites");
    public static final DeferredItem<Item> METASEQUOIA_FOSSIL = registerFossilItem("metasequoia");
    public static final DeferredItem<Item> DRYOPHYLLUM_FOSSIL = registerFossilItem("dryophyllum");
    // endregion

    private static <I extends Item> DeferredItem<I> registerItem(String name, Supplier<? extends I> supplier) {
        DeferredItem<I> item = ITEMS.register(name, supplier);
        ITEM_TRANSLATIONS.add(item);
        return item;
    }

    private static DeferredItem<Item> registerEggItem(String name, Supplier<? extends EntityType<?>> entityType) {
        return registerItem(name + "_egg", () -> new ThrowableEggItem(new Item.Properties(), entityType));
    }

    private static DeferredItem<Item> registerEmbryoItem(String name, Supplier<? extends EntityType<?>> entityType) {
        return registerItem(name + "_embryo", () -> new EmbryoItem(new Item.Properties(), entityType));
    }

    private static <I extends Item> DeferredItem<I> registerItemNoLang(String name, Supplier<? extends I> supplier) {
        return ITEMS.register(name, supplier);
    }

    private static DeferredItem<Item> registerSpawnEggItem(String name, Supplier<? extends EntityType<? extends Mob>> type, int baseColor, int spotColor) {
        return registerItem(name + "_spawn_egg", () -> new DeferredSpawnEggItem(type, baseColor, spotColor, new Item.Properties()));
    }

    @SuppressWarnings("SameParameterValue")
    private static DeferredItem<Item> registerSpawnEggItemNoLang(String name, Supplier<? extends EntityType<? extends Mob>> type, int baseColor, int spotColor) {
        return registerItemNoLang(name + "_spawn_egg", () -> new DeferredSpawnEggItem(type, baseColor, spotColor, new Item.Properties()));
    }

    private static DeferredItem<Item> registerFossilItem(String name) {
        return registerItem(name + "_fossil", () -> new Item(new Item.Properties()));
    }

    public static Item.Properties registerFoodValue(FoodProperties food) {
        return new Item.Properties().food(food);
    }
}
