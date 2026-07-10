package com.barlinc.unusual_prehistory.datagen.server;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.tags.UP2BlockTags;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.barlinc.unusual_prehistory.registry.UP2Items.*;

public class UP2ItemTagProvider extends ItemTagsProvider {

    public UP2ItemTagProvider(PackOutput output, CompletableFuture<Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> lookup, ExistingFileHelper helper) {
        super(output, provider, lookup, UnusualPrehistory2.MOD_ID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(Provider provider) {

        // Update 1
        this.tag(UP2ItemTags.CARNOTAURUS_RECIPE_MAPPINGS).add(CARNOTAURUS_SPAWN_EGG.get(), FURY_FOSSIL.get(), UP2Blocks.CARNOTAURUS_EGG.asItem(), UP2Blocks.CARNOTAURUS_PLUSHIE.asItem());
        this.tag(UP2ItemTags.DIPLOCAULUS_RECIPE_MAPPINGS).add(DIPLOCAULUS_SPAWN_EGG.get(), BOOMERANG_FOSSIL.get(), UP2Blocks.DIPLOCAULUS_EGGS.asItem());
        this.tag(UP2ItemTags.DROMAEOSAURUS_RECIPE_MAPPINGS).add(DROMAEOSAURUS_SPAWN_EGG.get(), RUNNER_FOSSIL.get(), DROMAEOSAURUS_EGG.get());
        this.tag(UP2ItemTags.DUNKLEOSTEUS_RECIPE_MAPPINGS).add(DUNKLEOSTEUS_SPAWN_EGG.get(), GUILLOTINE_FOSSIL.get(), UP2Blocks.DUNKLEOSTEUS_SAC.asItem(), DUNKLEOSTEUS_BUCKET.get());
        this.tag(UP2ItemTags.JAWLESS_FISH_RECIPE_MAPPINGS).add(JAWLESS_FISH_SPAWN_EGG.get(), JAWLESS_FOSSIL.get(), UP2Blocks.JAWLESS_FISH_ROE.asItem(), JAWLESS_FISH_BUCKET.get());
        this.tag(UP2ItemTags.KENTROSAURUS_RECIPE_MAPPINGS).add(KENTROSAURUS_SPAWN_EGG.get(), PRICKLY_FOSSIL.get(), UP2Blocks.KENTROSAURUS_EGG.asItem(), UP2Blocks.KENTROSAURUS_PLUSHIE.asItem());
        this.tag(UP2ItemTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_RECIPE_MAPPINGS).add(KIMMERIDGEBRACHYPTERAESCHNIDIUM_SPAWN_EGG.get(), IMPERATIVE_FOSSIL.get(), UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.asItem(), KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get(), KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET.get());
        this.tag(UP2ItemTags.MAJUNGASAURUS_RECIPE_MAPPINGS).add(MAJUNGASAURUS_SPAWN_EGG.get(), RUGOSE_FOSSIL.get(), UP2Blocks.MAJUNGASAURUS_EGG.asItem(), UP2Blocks.MAJUNGASAURUS_PLUSHIE.asItem());
        this.tag(UP2ItemTags.MEGALANIA_RECIPE_MAPPINGS).add(MEGALANIA_SPAWN_EGG.get(), THERMAL_FOSSIL.get(), UP2Blocks.MEGALANIA_EGG.asItem());
        this.tag(UP2ItemTags.STETHACANTHUS_RECIPE_MAPPINGS).add(STETHACANTHUS_SPAWN_EGG.get(), ANVIL_FOSSIL.get(), UP2Blocks.STETHACANTHUS_SAC.asItem(), STETHACANTHUS_BUCKET.get());
        this.tag(UP2ItemTags.TALPANAS_RECIPE_MAPPINGS).add(TALPANAS_SPAWN_EGG.get(), AGED_FEATHER.get(), TALPANAS_EGG.get());
        this.tag(UP2ItemTags.TELECREX_RECIPE_MAPPINGS).add(TELECREX_SPAWN_EGG.get(), PLUMAGE_FOSSIL.get(), TELECREX_EGG.get());

        this.tag(UP2ItemTags.TAMES_MEGALANIA).add(
                Items.ROTTEN_FLESH
        );

        this.tag(UP2ItemTags.TRANSMOGRIFIER_FUEL).add(
                ORGANIC_OOZE.get()
        );

        this.copy(UP2BlockTags.GINKGO_LOGS, UP2ItemTags.GINKGO_LOGS);
        this.copy(UP2BlockTags.LEPIDODENDRON_LOGS, UP2ItemTags.LEPIDODENDRON_LOGS);

        this.copy(UP2BlockTags.DIPLOCAULUS_SLIDING_BLOCKS, UP2ItemTags.DIPLOCAULUS_SLIDING_BLOCKS);
        this.copy(UP2BlockTags.DIPLOCAULUS_BURROWING_BLOCKS, UP2ItemTags.DIPLOCAULUS_BURROWING_BLOCKS);

        this.copy(UP2BlockTags.JAWLESS_FISH_FOOD_BLOCKS, UP2ItemTags.JAWLESS_FISH_NIBBLING_BLOCKS);

        this.copy(UP2BlockTags.DESMATOSUCHUS_FOOD_BLOCKS, UP2ItemTags.DESMATOSUCHUS_GRAZING_BLOCKS);
        this.copy(UP2BlockTags.DESMATOSUCHUS_BURROWING_BLOCKS, UP2ItemTags.DESMATOSUCHUS_BURROWING_BLOCKS);
        this.copy(UP2BlockTags.DESMATOSUCHUS_ROLLING_BLOCKS, UP2ItemTags.DESMATOSUCHUS_ROLLING_BLOCKS);
        this.copy(UP2BlockTags.DESMATOSUCHUS_MUDDY_BLOCKS, UP2ItemTags.DESMATOSUCHUS_MUDDY_BLOCKS);
        this.copy(UP2BlockTags.DESMATOSUCHUS_SNOWY_BLOCKS, UP2ItemTags.DESMATOSUCHUS_SNOWY_BLOCKS);
        this.copy(UP2BlockTags.DESMATOSUCHUS_MOSSY_BLOCKS, UP2ItemTags.DESMATOSUCHUS_MOSSY_BLOCKS);

        this.tag(UP2ItemTags.GUARDED_BY_KENTROSAURUS).add(Blocks.CACTUS.asItem(), Items.SWEET_BERRIES);

        this.tag(UP2ItemTags.FRUITS_GINKGO).add(GINKGO_FRUIT.get());
        this.tag(Tags.Items.FOODS_FRUIT).addTag(UP2ItemTags.FRUITS_GINKGO);

        // Update 2
        this.tag(UP2ItemTags.ONCHOPRISTIS_RECIPE_MAPPINGS).add(ONCHOPRISTIS_SPAWN_EGG.get(), SAW_FOSSIL.get(), UP2Blocks.ONCHOPRISTIS_SAC.asItem());

        // Update 3
        this.tag(UP2ItemTags.LIVING_OOZE_RECIPE_MAPPINGS).add(LIVING_OOZE_SPAWN_EGG.get(), LIVING_OOZE_BUCKET.get());
        this.tag(UP2ItemTags.METRIORHYNCHUS_RECIPE_MAPPINGS).add(METRIORHYNCHUS_SPAWN_EGG.get(), MELTDOWN_FOSSIL.get(), METRIORHYNCHUS_EMBRYO.get());
        this.tag(UP2ItemTags.TARTUOSTEUS_RECIPE_MAPPINGS).add(TARTUOSTEUS_SPAWN_EGG.get(), MOSSY_FOSSIL.get(), UP2Blocks.TARTUOSTEUS_ROE.asItem(), UP2Blocks.TARTUOSTEUS_PLUSHIE.asItem());

        this.tag(UP2ItemTags.TAMES_METRIORHYNCHUS).add(Items.TROPICAL_FISH_BUCKET);

        this.copy(UP2BlockTags.TARTUOSTEUS_FOOD_BLOCKS, UP2ItemTags.TARTUOSTEUS_NIBBLING_BLOCKS);

        // Update 4
        this.tag(UP2ItemTags.BRACHIOSAURUS_RECIPE_MAPPINGS).add(BRACHIOSAURUS_SPAWN_EGG.get(), ARM_FOSSIL.get(), UP2Blocks.BRACHIOSAURUS_EGG.asItem(), UP2Blocks.BRACHIOSAURUS_PLUSHIE.asItem());
        this.tag(UP2ItemTags.COELACANTHUS_RECIPE_MAPPINGS).add(COELACANTHUS_SPAWN_EGG.get(), GLUTTONOUS_FOSSIL.get(), UP2Blocks.COELACANTHUS_ROE.asItem());
        this.tag(UP2ItemTags.HIBBERTOPTERUS_RECIPE_MAPPINGS).add(HIBBERTOPTERUS_SPAWN_EGG.get(), PLOW_FOSSIL.get(), UP2Blocks.HIBBERTOPTERUS_EGGS.asItem(), UP2Blocks.HIBBERTOPTERUS_PLUSHIE.asItem());
        this.tag(UP2ItemTags.KAPROSUCHUS_RECIPE_MAPPINGS).add(KAPROSUCHUS_SPAWN_EGG.get(), BOAR_TOOTH_FOSSIL.get(), UP2Blocks.KAPROSUCHUS_EGG.asItem());
        this.tag(UP2ItemTags.LEPTICTIDIUM_RECIPE_MAPPINGS).add(LEPTICTIDIUM_SPAWN_EGG.get(), TRUNK_MOUSE_FOSSIL.get(), LEPTICTIDIUM_EMBRYO.get());
        this.tag(UP2ItemTags.LOBE_FINNED_FISH_RECIPE_MAPPINGS).add(LOBE_FINNED_FISH_SPAWN_EGG.get(), FISH_FOSSIL.get(), UP2Blocks.LOBE_FINNED_FISH_ROE.asItem(), LOBE_FINNED_FISH_BUCKET.get());
        this.tag(UP2ItemTags.LYSTROSAURUS_RECIPE_MAPPINGS).add(LYSTROSAURUS_SPAWN_EGG.get(), IMPERVIOUS_FOSSIL.get(), UP2Blocks.LYSTROSAURUS_EGG.asItem());
        this.tag(UP2ItemTags.PACHYCEPHALOSAURUS_RECIPE_MAPPINGS).add(PACHYCEPHALOSAURUS_SPAWN_EGG.get(), CRANIUM_FOSSIL.get(), UP2Blocks.PACHYCEPHALOSAURUS_EGG.asItem());
        this.tag(UP2ItemTags.PRAEPUSA_RECIPE_MAPPINGS).add(PRAEPUSA_SPAWN_EGG.get(), FLIPPER_FOSSIL.get(), PRAEPUSA_EMBRYO.get());
        this.tag(UP2ItemTags.PTERODACTYLUS_RECIPE_MAPPINGS).add(PTERODACTYLUS_SPAWN_EGG.get(), WING_FOSSIL.get(), PTERODACTYLUS_EGG.get());
        this.tag(UP2ItemTags.ULUGHBEGSAURUS_RECIPE_MAPPINGS).add(ULUGHBEGSAURUS_SPAWN_EGG.get(), DUBIOUS_FOSSIL.get(), UP2Blocks.ULUGHBEGSAURUS_EGG.asItem());

        this.tag(UP2ItemTags.TAMES_KAPROSUCHUS).add(Items.BONE);
        this.tag(UP2ItemTags.TAMES_ULUGHBEGSAURUS).addTag(UP2ItemTags.DIET_CARNIVORE);

        this.tag(UP2ItemTags.AMBER_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "amber_dye"));
        this.tag(UP2ItemTags.AQUA_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "aqua_dye"));
        this.tag(UP2ItemTags.BEIGE_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "beige_dye"));
        this.tag(UP2ItemTags.CORAL_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "coral_dye"));
        this.tag(UP2ItemTags.FOREST_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "forest_dye"));
        this.tag(UP2ItemTags.GINGER_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "ginger_dye"));
        this.tag(UP2ItemTags.INDIGO_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "indigo_dye"));
        this.tag(UP2ItemTags.MAROON_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "maroon_dye"));
        this.tag(UP2ItemTags.MINT_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "mint_dye"));
        this.tag(UP2ItemTags.NAVY_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "navy_dye"));
        this.tag(UP2ItemTags.OLIVE_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "olive_dye"));
        this.tag(UP2ItemTags.ROSE_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "rose_dye"));
        this.tag(UP2ItemTags.SLATE_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "slate_dye"));
        this.tag(UP2ItemTags.TAN_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "tan_dye"));
        this.tag(UP2ItemTags.TEAL_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "teal_dye"));
        this.tag(UP2ItemTags.VERDANT_DYES).addOptional(ResourceLocation.fromNamespaceAndPath("dye_depot", "verdant_dye"));

        this.tag(UP2ItemTags.STOPS_MOB_AGING).add(Items.POISONOUS_POTATO);

        this.copy(UP2BlockTags.DRYOPHYLLUM_LOGS, UP2ItemTags.DRYOPHYLLUM_LOGS);
        this.copy(UP2BlockTags.METASEQUOIA_LOGS, UP2ItemTags.METASEQUOIA_LOGS);

        // Update 5
        this.tag(UP2ItemTags.AEGIROCASSIS_RECIPE_MAPPINGS).add(AEGIROCASSIS_SPAWN_EGG.get(), BRISTLE_FOSSIL.get(), UP2Blocks.AEGIROCASSIS_EGGS.asItem());
        this.tag(UP2ItemTags.DESMATOSUCHUS_RECIPE_MAPPINGS).add(DESMATOSUCHUS_SPAWN_EGG.get(), FLAT_BACK_FOSSIL.get(), UP2Blocks.DESMATOSUCHUS_EGG.asItem());
        this.tag(UP2ItemTags.PSILOPTERUS_RECIPE_MAPPINGS).add(PSILOPTERUS_SPAWN_EGG.get(), CROOKED_BEAK_FOSSIL.get(), PSILOPTERUS_EGG.get());

        this.tag(UP2ItemTags.SNOW).add(
                Items.SNOWBALL,
                Blocks.SNOW.asItem(),
                Blocks.SNOW_BLOCK.asItem()
        );
        this.tag(UP2ItemTags.MOSS).add(
                UP2Blocks.MOSS_LAYER.get().asItem(),
                Blocks.MOSS_BLOCK.asItem(),
                Blocks.MOSS_CARPET.asItem()
        );
        this.tag(UP2ItemTags.MUD).add(
                Blocks.MUD.asItem()
        );

        this.tag(UP2ItemTags.PALEOZOIC_FOSSILS).add(
                BRISTLE_FOSSIL.get(),
                GUILLOTINE_FOSSIL.get(),
                JAWLESS_FOSSIL.get(),
                ANVIL_FOSSIL.get(),
                MOSSY_FOSSIL.get(),
                BOOMERANG_FOSSIL.get(),
                PLOW_FOSSIL.get(),
                FISH_FOSSIL.get(),
                GLUTTONOUS_FOSSIL.get(),
                IMPERVIOUS_FOSSIL.get(),
                ROTUND_FOSSIL.get(),
                THUNDEROUS_FOSSIL.get(),
                ROCKET_FOSSIL.get(),
                FORCIPULE_FOSSIL.get(),
                SUCTION_FOSSIL.get(),
                COMBUSTIBLE_FOSSIL.get()
        );
        this.tag(UP2ItemTags.MESOZOIC_FOSSILS).add(
                GRACILE_FOSSIL.get(),
                SNOW_SHOVEL_FOSSIL.get(),
                IMPERVIOUS_FOSSIL.get(),
                FLAT_BACK_FOSSIL.get(),
                ARM_FOSSIL.get(),
                PRICKLY_FOSSIL.get(),
                IMPERATIVE_FOSSIL.get(),
                MELTDOWN_FOSSIL.get(),
                WING_FOSSIL.get(),
                FURY_FOSSIL.get(),
                RUNNER_FOSSIL.get(),
                BOAR_TOOTH_FOSSIL.get(),
                RUGOSE_FOSSIL.get(),
                SURGE_FOSSIL.get(),
                SAW_FOSSIL.get(),
                CRANIUM_FOSSIL.get(),
                DUBIOUS_FOSSIL.get(),
                FISH_REPTILE_FOSSIL.get(),
                GARGANTUAN_FOSSIL.get(),
                CLAMP_JAW_FOSSIL.get(),
                CRYPTIC_FOSSIL.get(),
                CONCAVE_FOSSIL.get(),
                SCYTHE_FOSSIL.get(),
                SHIMMER_FOSSIL.get()
        );
        this.tag(UP2ItemTags.CENOZOIC_FOSSILS).add(
                TRUNK_MOUSE_FOSSIL.get(),
                CROOKED_BEAK_FOSSIL.get(),
                PLUMAGE_FOSSIL.get(),
                FLIPPER_FOSSIL.get(),
                THERMAL_FOSSIL.get(),
                DOLPHIN_FOSSIL.get(),
                AGED_FEATHER.get(),
                MOLAR_FOSSIL.get(),
                ROT_FOSSIL.get(),
                PILLAR_FOSSIL.get(),
                STRIPED_PELT.get(),
                CROWN_FOSSIL.get()
        );
        this.tag(UP2ItemTags.PLANT_FOSSILS).add(
                QUILLWORT_FOSSIL.get(),
                GINKGO_FOSSIL.get(),
                LEPIDODENDRON_FOSSIL.get(),
                BRACHYPHYLLUM_FOSSIL.get(),
                CYCAD_FOSSIL.get(),
                GUANGDEDENDRON_FOSSIL.get(),
                PROTOTAXITES_FOSSIL.get(),
                METASEQUOIA_FOSSIL.get(),
                DRYOPHYLLUM_FOSSIL.get()
        );

        this.tag(UP2ItemTags.MOB_FOSSILS)
                .addTag(UP2ItemTags.PALEOZOIC_FOSSILS)
                .addTag(UP2ItemTags.MESOZOIC_FOSSILS)
                .addTag(UP2ItemTags.CENOZOIC_FOSSILS);

        this.tag(UP2ItemTags.FOSSILS)
                .addTag(UP2ItemTags.MOB_FOSSILS)
                .addTag(UP2ItemTags.PLANT_FOSSILS);

        this.tag(UP2ItemTags.PALEOZOIC_EGGS).add(
                UP2Blocks.AEGIROCASSIS_EGGS.get().asItem(),
                UP2Blocks.COELACANTHUS_ROE.get().asItem(),
                UP2Blocks.DIPLOCAULUS_EGGS.get().asItem(),
                UP2Blocks.DUNKLEOSTEUS_SAC.get().asItem(),
                UP2Blocks.HIBBERTOPTERUS_EGGS.get().asItem(),
                UP2Blocks.JAWLESS_FISH_ROE.get().asItem(),
                UP2Blocks.LOBE_FINNED_FISH_ROE.get().asItem(),
                UP2Blocks.LYSTROSAURUS_EGG.get().asItem(),
                UP2Blocks.STETHACANTHUS_SAC.get().asItem(),
                UP2Blocks.TARTUOSTEUS_ROE.get().asItem(),
                BRONTOSCORPIO_EMBRYO.get(),
                UP2Blocks.HYNERPETON_EGGS.asItem(),
                UP2Blocks.RHIZODUS_ROE.asItem(),
                UP2Blocks.AMMONITE_EGGS.asItem(),
                UP2Blocks.CAMEROCERAS_EGGS.asItem()
        );
        this.tag(UP2ItemTags.MESOZOIC_EGGS).add(
                AUSTRORAPTOR_EGG.get(),
                UP2Blocks.BRACHIOSAURUS_EGG.asItem(),
                UP2Blocks.CARNOTAURUS_EGG.asItem(),
                UP2Blocks.DESMATOSUCHUS_EGG.asItem(),
                DROMAEOSAURUS_EGG.get(),
                UP2Blocks.KAPROSUCHUS_EGG.asItem(),
                UP2Blocks.KENTROSAURUS_EGG.asItem(),
                UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.asItem(),
                UP2Blocks.LYSTROSAURUS_EGG.asItem(),
                UP2Blocks.MAJUNGASAURUS_EGG.asItem(),
                METRIORHYNCHUS_EMBRYO.get(),
                PROGNATHODON_EMBRYO.get(),
                UP2Blocks.ONCHOPRISTIS_SAC.asItem(),
                UP2Blocks.PACHYCEPHALOSAURUS_EGG.asItem(),
                PTERODACTYLUS_EGG.get(),
                UP2Blocks.ULUGHBEGSAURUS_EGG.asItem(),
                ICHTHYOSAURUS_EMBRYO.get(),
                LORRAINOSAURUS_EMBRYO.get(),
                CRYPTOCLIDUS_EMBRYO.get(),
                UP2Blocks.CONCAVENATOR_EGG.asItem(),
                UP2Blocks.THERIZINOSAURUS_EGG.asItem(),
                UP2Blocks.TUSOTEUTHIS_EGGS.asItem(),
                UP2Blocks.AMMONITE_EGGS.asItem(),
                UP2Blocks.LEEDSICHTHYS_ROE.asItem()
        );
        this.tag(UP2ItemTags.CENOZOIC_EGGS).add(
                LEPTICTIDIUM_EMBRYO.get(),
                UP2Blocks.MEGALANIA_EGG.asItem(),
                PRAEPUSA_EMBRYO.get(),
                PSILOPTERUS_EGG.get(),
                TALPANAS_EGG.get(),
                NIHOHAE_EMBRYO.get(),
                TELECREX_EGG.get(),
                WOOLLY_MAMMOTH_EMBRYO.get(),
                UP2Blocks.SPIKE_TOOTHED_SALMON_ROE.asItem(),
                UP2Blocks.GIANT_CAMPANILE_EGGS.asItem(),
                THYLACINE_EMBRYO.get(),
                UP2Blocks.KING_LINGCOD_ROE.asItem()
        );
        this.tag(UP2ItemTags.PREHISTORIC_EGGS)
                .addTag(UP2ItemTags.PALEOZOIC_EGGS)
                .addTag(UP2ItemTags.MESOZOIC_EGGS)
                .addTag(UP2ItemTags.CENOZOIC_EGGS);

        this.copy(UP2BlockTags.ACCELERATES_EGG_HATCHING, UP2ItemTags.ACCELERATES_EGG_HATCHING);
        this.copy(UP2BlockTags.PREVENTS_EGG_HATCHING, UP2ItemTags.PREVENTS_EGG_HATCHING);

        // Update 6
        this.tag(UP2ItemTags.AMMONITE_RECIPE_MAPPINGS).add(AMMONITE_SPAWN_EGG.get(), SPIRAL_FOSSIL.get(), UP2Blocks.AMMONITE_EGGS.asItem());
        this.tag(UP2ItemTags.ANTARCTOPELTA_RECIPE_MAPPINGS).add(ANTARCTOPELTA_SPAWN_EGG.get(), SNOW_SHOVEL_FOSSIL.get());
        this.tag(UP2ItemTags.ARTHROPLEURA_RECIPE_MAPPINGS).add(ARTHROPLEURA_SPAWN_EGG.get(), FORCIPULE_FOSSIL.get());
        this.tag(UP2ItemTags.AUSTRORAPTOR_RECIPE_MAPPINGS).add(AUSTRORAPTOR_SPAWN_EGG.get(), GRACILE_FOSSIL.get(), AUSTRORAPTOR_EGG.get());
        this.tag(UP2ItemTags.BRONTOSCORPIO_RECIPE_MAPPINGS).add(BRONTOSCORPIO_SPAWN_EGG.get(), THUNDEROUS_FOSSIL.get(), BRONTOSCORPIO_EMBRYO.get());
        this.tag(UP2ItemTags.CONCAVENATOR_RECIPE_MAPPINGS).add(CONCAVENATOR_SPAWN_EGG.get(), CONCAVE_FOSSIL.get(), UP2Blocks.CONCAVENATOR_EGG.asItem());
        this.tag(UP2ItemTags.COTYLORHYNCHUS_RECIPE_MAPPINGS).add(COTYLORHYNCHUS_SPAWN_EGG.get(), ROTUND_FOSSIL.get(), UP2Blocks.COTYLORHYNCHUS_PLUSHIE.asItem());
        this.tag(UP2ItemTags.CRYPTOCLIDUS_RECIPE_MAPPINGS).add(CRYPTOCLIDUS_SPAWN_EGG.get(), CRYPTIC_FOSSIL.get(), CRYPTOCLIDUS_EMBRYO.get());
        this.tag(UP2ItemTags.GASTRIC_BROODING_FROG_RECIPE_MAPPINGS).add(GASTRIC_BROODING_FROG_SPAWN_EGG.get());
        this.tag(UP2ItemTags.GIANT_CAMPANILE_RECIPE_MAPPINGS).add(GIANT_CAMPANILE_SPAWN_EGG.get(), PILLAR_FOSSIL.get(), UP2Blocks.GIANT_CAMPANILE_EGGS.asItem(), UP2Blocks.GIANT_CAMPANILE_PLUSHIE.asItem());
        this.tag(UP2ItemTags.HYNERPETON_RECIPE_MAPPINGS).add(HYNERPETON_SPAWN_EGG.get(), COMBUSTIBLE_FOSSIL.get(), UP2Blocks.HYNERPETON_EGGS.asItem(), HYNERPETON_BUCKET.get());
        this.tag(UP2ItemTags.ICHTHYOSAURUS_RECIPE_MAPPINGS).add(ICHTHYOSAURUS_SPAWN_EGG.get(), FISH_REPTILE_FOSSIL.get(), ICHTHYOSAURUS_EMBRYO.get());
        this.tag(UP2ItemTags.KING_LINGCOD_RECIPE_MAPPINGS).add(KING_LINGCOD_SPAWN_EGG.get(), CROWN_FOSSIL.get(), UP2Blocks.KING_LINGCOD_ROE.asItem());
        this.tag(UP2ItemTags.LEEDSICHTHYS_RECIPE_MAPPINGS).add(LEEDSICHTHYS_SPAWN_EGG.get(), GARGANTUAN_FOSSIL.get(), UP2Blocks.LEEDSICHTHYS_ROE.asItem(), LEEDSICHTHYS_SLICE.get());
        this.tag(UP2ItemTags.LINGCOD_RECIPE_MAPPINGS).add(LINGCOD_SPAWN_EGG.get());
        this.tag(UP2ItemTags.LORRAINOSAURUS_RECIPE_MAPPINGS).add(LORRAINOSAURUS_SPAWN_EGG.get(), CLAMP_JAW_FOSSIL.get(), LORRAINOSAURUS_EMBRYO.get());
        this.tag(UP2ItemTags.PROGNATHODON_RECIPE_MAPPINGS).add(PROGNATHODON_SPAWN_EGG.get(), SURGE_FOSSIL.get(), PROGNATHODON_EMBRYO.get());
        this.tag(UP2ItemTags.RHIZODUS_RECIPE_MAPPINGS).add(RHIZODUS_SPAWN_EGG.get(), SUCTION_FOSSIL.get(), UP2Blocks.RHIZODUS_ROE.asItem());
        this.tag(UP2ItemTags.SHRINGASAURUS_RECIPE_MAPPINGS).add(SHRINGASAURUS_SPAWN_EGG.get());
        this.tag(UP2ItemTags.SPIKE_TOOTHED_SALMON_RECIPE_MAPPINGS).add(SPIKE_TOOTHED_SALMON_SPAWN_EGG.get(), ROT_FOSSIL.get(), UP2Blocks.SPIKE_TOOTHED_SALMON_ROE.asItem());
        this.tag(UP2ItemTags.THERIZINOSAURUS_RECIPE_MAPPINGS).add(THERIZINOSAURUS_SPAWN_EGG.get(), SCYTHE_FOSSIL.get(), UP2Blocks.THERIZINOSAURUS_EGG.asItem());
        this.tag(UP2ItemTags.THYLACINE_RECIPE_MAPPINGS).add(THYLACINE_SPAWN_EGG.get(), STRIPED_PELT.get(), THYLACINE_EMBRYO.get());
        this.tag(UP2ItemTags.TUSOTEUTHIS_RECIPE_MAPPINGS).add(TUSOTEUTHIS_SPAWN_EGG.get(), SHIMMER_FOSSIL.get(), UP2Blocks.TUSOTEUTHIS_EGGS.asItem());
        this.tag(UP2ItemTags.WOOLLY_MAMMOTH_RECIPE_MAPPINGS).add(WOOLLY_MAMMOTH_SPAWN_EGG.get(), MOLAR_FOSSIL.get(), WOOLLY_MAMMOTH_EMBRYO.get());
        this.tag(UP2ItemTags.PACHYRHINOSAURUS_RECIPE_MAPPINGS).add(PACHYRHINOSAURUS_SPAWN_EGG.get());
        this.tag(UP2ItemTags.HESPERORNIS_RECIPE_MAPPINGS).add(HESPERORNIS_SPAWN_EGG.get());
        this.tag(UP2ItemTags.AQUILOLAMNA_RECIPE_MAPPINGS).add(AQUILOLAMNA_SPAWN_EGG.get());
        this.tag(UP2ItemTags.CAMEROCERAS_RECIPE_MAPPINGS).add(CAMEROCERAS_SPAWN_EGG.get(), ROCKET_FOSSIL.get(), UP2Blocks.CAMEROCERAS_EGGS.asItem());
        this.tag(UP2ItemTags.DIRE_WOLF_RECIPE_MAPPINGS).add(DIRE_WOLF_SPAWN_EGG.get());
        this.tag(UP2ItemTags.HENODUS_RECIPE_MAPPINGS).add(HENODUS_SPAWN_EGG.get());
        this.tag(UP2ItemTags.SALTOPUS_RECIPE_MAPPINGS).add(SALTOPUS_SPAWN_EGG.get());
        this.tag(UP2ItemTags.NIHOHAE_RECIPE_MAPPINGS).add(NIHOHAE_SPAWN_EGG.get(), DOLPHIN_FOSSIL.get(), NIHOHAE_EMBRYO.get());

        this.tag(UP2ItemTags.PLANTS).add(
                Blocks.SHORT_GRASS.asItem(),
                Blocks.FERN.asItem(),
                Blocks.BAMBOO.asItem(),
                Blocks.SUGAR_CANE.asItem(),
                Blocks.TALL_GRASS.asItem(),
                Blocks.LARGE_FERN.asItem(),
                Blocks.MOSS_BLOCK.asItem(),
                Blocks.MOSS_CARPET.asItem(),
                Blocks.CACTUS.asItem(),
                Blocks.VINE.asItem(),
                Blocks.HANGING_ROOTS.asItem(),
                Blocks.GLOW_LICHEN.asItem(),
                UP2Blocks.HORSETAIL.asItem(),
                UP2Blocks.LARGE_HORSETAIL.asItem(),
                UP2Blocks.DELITZSCHALA_STALK.asItem(),
                UP2Blocks.ARCHAEOSIGILLARIA.asItem(),
                UP2Blocks.BENNETTITALES.asItem(),
                UP2Blocks.BRACHYPHYLLUM.asItem(),
                UP2Blocks.CALAMOPHYTON.asItem(),
                UP2Blocks.CLADOPHLEBIS.asItem(),
                UP2Blocks.NEOMARIOPTERIS.asItem(),
                UP2Blocks.RHYNIA.asItem(),
                UP2Blocks.COOKSONIA.asItem(),
                UP2Blocks.TEMPSKYA.asItem(),
                UP2Blocks.CYCAD_SEEDLING.asItem(),
                UP2Blocks.GUANGDEDENDRON.asItem(),
                UP2Blocks.HANGING_LEPIDODENDRON_LEAVES.asItem(),
                UP2Blocks.MOSS_LAYER.asItem()
        );
        this.tag(UP2ItemTags.AQUATIC_PLANTS).add(
                Blocks.BIG_DRIPLEAF.asItem(),
                Blocks.SMALL_DRIPLEAF.asItem(),
                Blocks.LILY_PAD.asItem(),
                Blocks.SEAGRASS.asItem(),
                Blocks.KELP.asItem(),
                Items.DRIED_KELP,
                Blocks.DRIED_KELP_BLOCK.asItem()
        );
        this.tag(UP2ItemTags.CORALS).add(
                Blocks.TUBE_CORAL.asItem(),
                Blocks.TUBE_CORAL_BLOCK.asItem(),
                Blocks.TUBE_CORAL_FAN.asItem(),
                Blocks.BRAIN_CORAL.asItem(),
                Blocks.BRAIN_CORAL_BLOCK.asItem(),
                Blocks.BRAIN_CORAL_FAN.asItem(),
                Blocks.BUBBLE_CORAL.asItem(),
                Blocks.BUBBLE_CORAL_BLOCK.asItem(),
                Blocks.BUBBLE_CORAL_FAN.asItem(),
                Blocks.FIRE_CORAL.asItem(),
                Blocks.FIRE_CORAL_BLOCK.asItem(),
                Blocks.FIRE_CORAL_FAN.asItem(),
                Blocks.HORN_CORAL.asItem(),
                Blocks.HORN_CORAL_BLOCK.asItem(),
                Blocks.HORN_CORAL_FAN.asItem()
        );

        this.tag(UP2ItemTags.DIET_CARNIVORE).addTags(
                Tags.Items.FOODS_RAW_MEAT,
                Tags.Items.FOODS_COOKED_MEAT
        );
        this.tag(UP2ItemTags.DIET_HERBIVORE).addTags(
                Tags.Items.FOODS_VEGETABLE,
                ItemTags.LEAVES,
                ItemTags.SAPLINGS,
                UP2ItemTags.PLANTS,
                UP2ItemTags.AQUATIC_PLANTS
        );
        this.tag(UP2ItemTags.DIET_OMNIVORE).addTags(
                UP2ItemTags.DIET_CARNIVORE,
                UP2ItemTags.DIET_HERBIVORE
        );
        this.tag(UP2ItemTags.DIET_PISCIVORE).addTags(
                Tags.Items.FOODS_RAW_FISH,
                Tags.Items.FOODS_COOKED_FISH
        );
        this.tag(UP2ItemTags.DIET_INSECTIVORE).add(
                Items.SPIDER_EYE,
                Items.FERMENTED_SPIDER_EYE
        );
        this.tag(UP2ItemTags.DIET_SCAVENGER).add(
                Items.BONE,
                Items.BONE_MEAL,
                Items.ROTTEN_FLESH,
                Blocks.BONE_BLOCK.asItem()
        );
        this.tag(UP2ItemTags.DIET_NECTARIVORE).addTags(
                ItemTags.FLOWERS
        ).add(
                Items.HONEY_BLOCK,
                Items.HONEY_BOTTLE,
                Items.HONEYCOMB,
                Items.HONEYCOMB_BLOCK
        );
        this.tag(UP2ItemTags.DIET_DETRITIVORE).addTags(
                ItemTags.DIRT,
                Tags.Items.MUSHROOMS
        );
        this.tag(UP2ItemTags.DIET_FRUGIVORE).add(
                Blocks.PUMPKIN.asItem(),
                Blocks.MELON.asItem()
        ).addTags(
                Tags.Items.FOODS_FRUIT,
                Tags.Items.FOODS_BERRY
        );
        this.tag(UP2ItemTags.DIET_GRANIVORE).addTags(
                Tags.Items.SEEDS
        ).add(
                Items.PITCHER_POD
        );
        this.tag(UP2ItemTags.DIET_CORALLIVORE).addTags(
                UP2ItemTags.CORALS
        );
        this.tag(UP2ItemTags.DIET_OVIVORE).addTags(
                Tags.Items.EGGS
        ).add(
                Blocks.DRAGON_EGG.asItem(),
                Blocks.SNIFFER_EGG.asItem(),
                Blocks.TURTLE_EGG.asItem()
        );

        this.tag(UP2ItemTags.TEMPTS_ARTHROPLEURA).addTags(UP2ItemTags.DIET_DETRITIVORE).add(BROWN_MUSHROOM_ON_A_STICK.get());

        this.tag(UP2ItemTags.TAMES_CONCAVENATOR).addTags(ItemTags.SAND).add(
                Items.SOUL_SAND,
                Items.SOUL_SOIL
        );

        this.tag(UP2ItemTags.TEMPTS_CONCAVENATOR).addTags(
                UP2ItemTags.DIET_CARNIVORE,
                UP2ItemTags.TAMES_CONCAVENATOR
        );

        this.tag(UP2ItemTags.CONCAVENATOR_SAND_ARMOR_ITEMS).add(
                Blocks.SANDSTONE.asItem()
        );
        this.tag(UP2ItemTags.CONCAVENATOR_RED_SAND_ARMOR_ITEMS).add(
                Blocks.RED_SANDSTONE.asItem()
        );
        this.tag(UP2ItemTags.CONCAVENATOR_SOUL_SAND_ARMOR_ITEMS).add(
                Blocks.SOUL_SOIL.asItem(),
                Blocks.SOUL_SAND.asItem()
        );

        this.tag(UP2ItemTags.ARMORS_CONCAVENATOR).addTags(
                UP2ItemTags.CONCAVENATOR_SAND_ARMOR_ITEMS,
                UP2ItemTags.CONCAVENATOR_RED_SAND_ARMOR_ITEMS,
                UP2ItemTags.CONCAVENATOR_SOUL_SAND_ARMOR_ITEMS
        );

        this.tag(UP2ItemTags.TAMES_THYLACINE).add(
                Items.COOKED_CHICKEN,
                Items.CHICKEN
        );
        this.tag(UP2ItemTags.TEMPTS_THYLACINE).addTags(UP2ItemTags.DIET_CARNIVORE, UP2ItemTags.TAMES_THYLACINE);

        this.tag(UP2ItemTags.TAMES_GASTRIC_BROODING_FROG).add(
                Items.SLIME_BALL
        );
        this.tag(UP2ItemTags.TEMPTS_GASTRIC_BROODING_FROG).add(Items.MAGMA_CREAM).addTags(UP2ItemTags.DIET_INSECTIVORE, UP2ItemTags.TAMES_GASTRIC_BROODING_FROG);


        this.tag(UP2ItemTags.TAMES_KING_LINGCOD).add(
                Items.COD,
                Items.COOKED_COD
        );
        this.tag(UP2ItemTags.TEMPTS_LINGCOD).addTags(UP2ItemTags.DIET_PISCIVORE, UP2ItemTags.TAMES_KING_LINGCOD);

        this.tag(UP2ItemTags.SWEET_COTYLORHYNCHUS_FOOD).add(
                Items.SWEET_BERRIES
        );
        this.tag(UP2ItemTags.FOUL_COTYLORHYNCHUS_FOOD).add(
                GINKGO_FRUIT.get()
        );

        this.tag(UP2ItemTags.CURES_SPIKE_TOOTHED_SALMON).add(
                Items.GOLDEN_APPLE
        );

        this.copy(UP2BlockTags.PLUSHIES, UP2ItemTags.PLUSHIES);

        // minecraft
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
//        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
//        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
//        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
//        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
//        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
//        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
//        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
//        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
//        this.copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
//        this.copy(Tags.Blocks.FENCE_GATES, Tags.Items.FENCE_GATES);
//        this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
//        this.copy(BlockTags.SLABS, ItemTags.SLABS);
//        this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        this.copy(BlockTags.STONE_BUTTONS, ItemTags.STONE_BUTTONS);

        this.copy(UP2BlockTags.REINFORCED_GLASS, UP2ItemTags.REINFORCED_GLASS);

        this.tag(Tags.Items.GLASS_BLOCKS).addTag(UP2ItemTags.REINFORCED_GLASS);

        this.tag(ItemTags.SIGNS).add(
                DRYOPHYLLUM_SIGN.get(),
                GINKGO_SIGN.get(),
                LEPIDODENDRON_SIGN.get(),
                METASEQUOIA_SIGN.get()
        );

        this.tag(ItemTags.HANGING_SIGNS).add(
                DRYOPHYLLUM_HANGING_SIGN.get(),
                GINKGO_HANGING_SIGN.get(),
                LEPIDODENDRON_HANGING_SIGN.get(),
                METASEQUOIA_HANGING_SIGN.get()
        );

        this.tag(ItemTags.BOATS).add(
                DRYOPHYLLUM_BOAT.get(),
                GINKGO_BOAT.get(),
                LEPIDODENDRON_BOAT.get(),
                METASEQUOIA_BOAT.get()
        );

        this.tag(ItemTags.CHEST_BOATS).add(
                DRYOPHYLLUM_CHEST_BOAT.get(),
                GINKGO_CHEST_BOAT.get(),
                LEPIDODENDRON_CHEST_BOAT.get(),
                METASEQUOIA_CHEST_BOAT.get()
        );

        this.tag(ItemTags.LECTERN_BOOKS).add(
                PALEOPEDIA.get()
        );

        this.tag(ItemTags.VANISHING_ENCHANTABLE).add(
                PLUSHIE_KIT.get()
        );
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(
                PLUSHIE_KIT.get()
        );
    }
}
