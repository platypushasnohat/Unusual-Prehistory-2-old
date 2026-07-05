package com.barlinc.unusual_prehistory.datagen.client;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import static com.barlinc.unusual_prehistory.registry.UP2Items.*;

public class UP2ItemModelProvider extends ItemModelProvider {

    public UP2ItemModelProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), UnusualPrehistory2.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void registerModels() {
        this.generatedItem(PRAEPUSA_BUCKET);
        this.generatedItem(BABY_AEGIROCASSIS_BUCKET);
        this.generatedItem(HYNERPETON_BUCKET);
        this.generatedItem(ONCHOPRISTIS_BUCKET);

        this.generatedItem(LIVING_OOZE_BUCKET);

        this.generatedItem(PETRIFIED_LUCA);
        this.generatedItem(LUCA);

        this.generatedItem(UNKNOWN_FOSSIL);
        this.generatedItem(UNKNOWN_EGG);

        this.generatedItem(CENOZOIC_FOSSIL);
        this.generatedItem(PLANT_FOSSIL);
        this.generatedItem(HOLOCENE_FOSSIL);
        this.generatedItem(MESOZOIC_FOSSIL);
        this.generatedItem(PALEOZOIC_FOSSIL);
        this.generatedItem(CREATIVE_LOCK);

        this.generatedItem(PERIOD_CAMBRIAN);
        this.generatedItem(PERIOD_ORDOVICIAN);
        this.generatedItem(PERIOD_SILURIAN);
        this.generatedItem(PERIOD_DEVONIAN);
        this.generatedItem(PERIOD_CARBONIFEROUS);
        this.generatedItem(PERIOD_PERMIAN);

        this.generatedItem(PERIOD_TRIASSIC);
        this.generatedItem(PERIOD_JURASSIC);
        this.generatedItem(PERIOD_CRETACEOUS);

        this.generatedItem(PERIOD_PALEOGENE);
        this.generatedItem(PERIOD_NEOGENE);
        this.generatedItem(PERIOD_QUATERNARY);
        this.generatedItem(PERIOD_HOLOCENE);

        this.generatedItem(PALEOPEDIA);

        this.handheldRodItem(DIRT_ON_A_STICK);
        this.handheldRodItem(BROWN_MUSHROOM_ON_A_STICK);

        this.generatedItem(UP2Blocks.CYCAD_SEEDLING);

        this.generatedItem(UP2Blocks.GUANGDEDENDRON);

        // Update 1
        this.generatedItem(
                FURY_FOSSIL,
                BOOMERANG_FOSSIL,
                RUNNER_FOSSIL,
                GUILLOTINE_FOSSIL,
                JAWLESS_FOSSIL,
                PRICKLY_FOSSIL,
                IMPERATIVE_FOSSIL,
                RUGOSE_FOSSIL,
                THERMAL_FOSSIL,
                DOLPHIN_FOSSIL,
                ANVIL_FOSSIL,
                AGED_FEATHER,
                PLUMAGE_FOSSIL,

                UP2Blocks.CARNOTAURUS_EGG,
                UP2Blocks.DIPLOCAULUS_EGGS,
                DROMAEOSAURUS_EGG,
                UP2Blocks.DUNKLEOSTEUS_SAC,
                UP2Blocks.JAWLESS_FISH_ROE,
                UP2Blocks.KENTROSAURUS_EGG,
                UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS,
                UP2Blocks.MAJUNGASAURUS_EGG,
                UP2Blocks.MEGALANIA_EGG,
                UP2Blocks.STETHACANTHUS_SAC,
                TALPANAS_EGG,
                NIHOHAE_EMBRYO,
                TELECREX_EGG,

                KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE,
                KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET,
                STETHACANTHUS_BUCKET,

                GINKGO_FRUIT,

                TAR_BUCKET,
                TARIFYING_DISC,

                MACHINE_PARTS,
                UNUSUAL_PREHISTORY
        );

        // Update 2
        this.generatedItem(
                SAW_FOSSIL,

                UP2Blocks.ONCHOPRISTIS_SAC,

                DOOMSURF_DISC,
                MEGALANIA_DISC
        );

        // Update 3
        this.generatedItem(
                MELTDOWN_FOSSIL,
                MOSSY_FOSSIL,

                METRIORHYNCHUS_EMBRYO,
                UP2Blocks.TARTUOSTEUS_ROE
        );

        // Update 4
        this.generatedItem(
                ARM_FOSSIL,
                GLUTTONOUS_FOSSIL,
                PLOW_FOSSIL,
                BOAR_TOOTH_FOSSIL,
                TRUNK_MOUSE_FOSSIL,
                FISH_FOSSIL,
                IMPERVIOUS_FOSSIL,
                CRANIUM_FOSSIL,
                FLIPPER_FOSSIL,
                WING_FOSSIL,
                DUBIOUS_FOSSIL,

                UP2Blocks.BRACHIOSAURUS_EGG,
                UP2Blocks.COELACANTHUS_ROE,
                UP2Blocks.HIBBERTOPTERUS_EGGS,
                UP2Blocks.KAPROSUCHUS_EGG,
                LEPTICTIDIUM_EMBRYO,
                UP2Blocks.LOBE_FINNED_FISH_ROE,
                UP2Blocks.LYSTROSAURUS_EGG,
                UP2Blocks.PACHYCEPHALOSAURUS_EGG,
                PRAEPUSA_EMBRYO,
                PTERODACTYLUS_EGG,
                UP2Blocks.ULUGHBEGSAURUS_EGG
        );

        // Update 5
        this.generatedItem(
                BRISTLE_FOSSIL,
                FLAT_BACK_FOSSIL,
                CROOKED_BEAK_FOSSIL,

                UP2Blocks.AEGIROCASSIS_EGGS,
                UP2Blocks.DESMATOSUCHUS_EGG,
                PSILOPTERUS_EGG,

                PALEOZOIC_BANNER_PATTERN,
                MESOZOIC_BANNER_PATTERN,
                CENOZOIC_BANNER_PATTERN,
                OOZE_BANNER_PATTERN,

                PUMMEL_AND_SNATCH_DISC
        );

        // Update 6
        this.generatedItem(
                SPIRAL_FOSSIL,
                SNOW_SHOVEL_FOSSIL,
                SHORT_WING_FOSSIL,
                FORCIPULE_FOSSIL,
                GRACILE_FOSSIL,
                THUNDEROUS_FOSSIL,
                ROCKET_FOSSIL,
                CONCAVE_FOSSIL,
                ROTUND_FOSSIL,
                CRYPTIC_FOSSIL,
                PILLAR_FOSSIL,
                COMBUSTIBLE_FOSSIL,
                FISH_REPTILE_FOSSIL,
                CROWN_FOSSIL,
                GARGANTUAN_FOSSIL,
                CLAMP_JAW_FOSSIL,
                MOLAR_FOSSIL,
                SURGE_FOSSIL,
                SUCTION_FOSSIL,
                ROT_FOSSIL,
                SCYTHE_FOSSIL,
                SHIMMER_FOSSIL,
                STRIPED_PELT,

                UP2Blocks.AMMONITE_EGGS,
                UP2Blocks.CAMEROCERAS_EGGS,
                ANUROGNATHUS_EGG,
                AUSTRORAPTOR_EGG,
                BRONTOSCORPIO_EMBRYO,
                UP2Blocks.CONCAVENATOR_EGG,
                UP2Blocks.GIANT_CAMPANILE_EGGS,
                UP2Blocks.HYNERPETON_EGGS,
                UP2Blocks.KING_LINGCOD_ROE,
                UP2Blocks.LEEDSICHTHYS_ROE,
                UP2Blocks.RHIZODUS_ROE,
                CRYPTOCLIDUS_EMBRYO,
                ICHTHYOSAURUS_EMBRYO,
                LORRAINOSAURUS_EMBRYO,
                WOOLLY_MAMMOTH_EMBRYO,
                PROGNATHODON_EMBRYO,
                THYLACINE_EMBRYO,
                UP2Blocks.SPIKE_TOOTHED_SALMON_ROE,
                UP2Blocks.THERIZINOSAURUS_EGG,
                UP2Blocks.TUSOTEUTHIS_EGGS,

                SWEET_GROG_BOTTLE,
                FOUL_GROG_BOTTLE,

                LEEDSICHTHYS_SLICE,

                PLUSHIE_KIT,

                AMBER_BEAR,
                COPROLITE,
                FOSSILIZED_FOOTPRINT,
                GASTROLITH,
                PETRIFIED_EGG,

                INDOCHINITE,
                FOSSILIZED_METEORITE,
                PALLASITE,
                CHONDRITE
        );

        this.generatedItem(GINKGO_FOSSIL);
        this.generatedItem(PROTOTAXITES_FOSSIL);
        this.generatedItem(QUILLWORT_FOSSIL);
        this.generatedItem(LEPIDODENDRON_FOSSIL);
        this.generatedItem(BRACHYPHYLLUM_FOSSIL);
        this.generatedItem(GUANGDEDENDRON_FOSSIL);
        this.generatedItem(METASEQUOIA_FOSSIL);
        this.generatedItem(DRYOPHYLLUM_FOSSIL);
        this.generatedItem(CYCAD_FOSSIL);

        this.generatedItem(UP2Blocks.TEMPSKYA);
        this.generatedItem(UP2Blocks.BRACHYPHYLLUM);
        this.generatedItem(UP2Blocks.AETHOPHYLLUM);

        this.generatedItem(DRYOPHYLLUM_SIGN);
        this.generatedItem(DRYOPHYLLUM_HANGING_SIGN);
        this.generatedItem(DRYOPHYLLUM_BOAT);
        this.generatedItem(DRYOPHYLLUM_CHEST_BOAT);

        this.generatedItem(GINKGO_SIGN);
        this.generatedItem(GINKGO_HANGING_SIGN);
        this.generatedItem(GINKGO_BOAT);
        this.generatedItem(GINKGO_CHEST_BOAT);

        this.generatedItem(LEPIDODENDRON_SIGN);
        this.generatedItem(LEPIDODENDRON_HANGING_SIGN);
        this.generatedItem(LEPIDODENDRON_BOAT);
        this.generatedItem(LEPIDODENDRON_CHEST_BOAT);

        this.generatedItem(METASEQUOIA_SIGN);
        this.generatedItem(METASEQUOIA_HANGING_SIGN);
        this.generatedItem(METASEQUOIA_BOAT);
        this.generatedItem(METASEQUOIA_CHEST_BOAT);

        // spawn eggs
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof DeferredSpawnEggItem && BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(UnusualPrehistory2.MOD_ID)) {
                this.withExistingParent(name(item), "item/template_spawn_egg");
            }
        }
    }

    public static ResourceLocation key(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem());
    }

    public static String name(ItemLike item) {
        return key(item).getPath();
    }

    public static ResourceLocation itemTexture(ItemLike item) {
        ResourceLocation name = key(item);
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.ITEM_FOLDER + "/" + name.getPath());
    }

    public ItemModelBuilder item(DeferredHolder<? extends ItemLike, ?> item, String type) {
        return this.withExistingParent(name(item.get()), "item/" + type).texture("layer0", itemTexture(item.get()));
    }

    public ItemModelBuilder item(DeferredHolder<? extends ItemLike, ?> item, String path, String type) {
        return this.withExistingParent(name(item.get()), "item/" + type).texture("layer0", ResourceLocation.fromNamespaceAndPath(this.modid, "item/" + path));
    }

    public ItemModelBuilder item(ResourceLocation location, String type) {
        return this.withExistingParent(location.getPath(), "item/" + type).texture("layer0", ResourceLocation.fromNamespaceAndPath(this.modid, "item/" + location.getPath()));
    }

    public ItemModelBuilder blockItem(DeferredHolder<Block, ?> block) {
        return this.getBuilder(UP2BlockstateProvider.name(block.get())).parent(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(this.modid, "block/" + UP2BlockstateProvider.name(block.get()))));
    }

    @SafeVarargs
    public final void generatedItem(DeferredHolder<? extends ItemLike, ?>... items) {
        for (DeferredHolder<? extends ItemLike, ?> item : items) {
            this.item(item, "generated");
        }
    }

    @SafeVarargs
    public final void handheldItem(DeferredHolder<? extends ItemLike, ?>... items) {
        for (DeferredHolder<? extends ItemLike, ?> item : items) {
            this.item(item, "handheld");
        }
    }

    @SafeVarargs
    public final void handheldRodItem(DeferredHolder<? extends ItemLike, ?>... items) {
        for (DeferredHolder<? extends ItemLike, ?> item : items) {
            this.item(item, "handheld_rod");
        }
    }

    @SafeVarargs
    public final void spawnEggItem(DeferredHolder<? extends ItemLike, ?>... items) {
        for (DeferredHolder<? extends ItemLike, ?> item : items) {
            this.withExistingParent(name(item.get()), "item/template_spawn_egg");
        }
    }
}
