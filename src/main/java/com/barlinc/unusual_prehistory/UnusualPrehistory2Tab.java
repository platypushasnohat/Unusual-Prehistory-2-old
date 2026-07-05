package com.barlinc.unusual_prehistory;

import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import com.barlinc.unusual_prehistory.registry.UP2PaintingVariants;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.barlinc.unusual_prehistory.registry.UP2Items.*;

public class UnusualPrehistory2Tab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> UNUSUAL_PREHISTORY_2_TAB = CREATIVE_TABS.register("unusual_prehistory",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(SPIRAL_FOSSIL.get()))
                    .title(Component.translatable("itemGroup.unusual_prehistory"))
                    .displayItems((parameters, output) -> {

                        // region spawn eggs

                        // Paleozoic
                        output.accept(AEGIROCASSIS_SPAWN_EGG.get());
                        output.accept(ARTHROPLEURA_SPAWN_EGG.get());
                        output.accept(BRONTOSCORPIO_SPAWN_EGG.get());
                        output.accept(CAMEROCERAS_SPAWN_EGG.get());
                        output.accept(COELACANTHUS_SPAWN_EGG.get());
                        output.accept(COTYLORHYNCHUS_SPAWN_EGG.get());
                        output.accept(DIICTODON_SPAWN_EGG.get());
                        output.accept(DIPLOCAULUS_SPAWN_EGG.get());
                        output.accept(DUNKLEOSTEUS_SPAWN_EGG.get());
                        output.accept(HIBBERTOPTERUS_SPAWN_EGG.get());
                        output.accept(HYNERPETON_SPAWN_EGG.get());
                        output.accept(JAWLESS_FISH_SPAWN_EGG.get());
                        output.accept(LOBE_FINNED_FISH_SPAWN_EGG.get());
                        output.accept(RHIZODUS_SPAWN_EGG.get());
                        output.accept(STETHACANTHUS_SPAWN_EGG.get());
                        output.accept(TARTUOSTEUS_SPAWN_EGG.get());

                        // Mesozoic
                        output.accept(ANTARCTOPELTA_SPAWN_EGG.get());
                        output.accept(ANUROGNATHUS_SPAWN_EGG.get());
                        output.accept(AQUILOLAMNA_SPAWN_EGG.get());
                        output.accept(AUSTRORAPTOR_SPAWN_EGG.get());
                        output.accept(BANANOGMIUS_SPAWN_EGG.get());
                        output.accept(BRACHIOSAURUS_SPAWN_EGG.get());
                        output.accept(CARNOTAURUS_SPAWN_EGG.get());
                        output.accept(COELOPHYSIS_SPAWN_EGG.get());
                        output.accept(CONCAVENATOR_SPAWN_EGG.get());
                        output.accept(CRYPTOCLIDUS_SPAWN_EGG.get());
                        output.accept(DESMATOSUCHUS_SPAWN_EGG.get());
                        output.accept(DROMAEOSAURUS_SPAWN_EGG.get());
                        output.accept(HENODUS_SPAWN_EGG.get());
                        output.accept(HESPERORNIS_SPAWN_EGG.get());
                        output.accept(ICHTHYOSAURUS_SPAWN_EGG.get());
                        output.accept(KAPROSUCHUS_SPAWN_EGG.get());
                        output.accept(KENTROSAURUS_SPAWN_EGG.get());
                        output.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_SPAWN_EGG.get());
                        output.accept(LEEDSICHTHYS_SPAWN_EGG.get());
                        output.accept(LORRAINOSAURUS_SPAWN_EGG.get());
                        output.accept(MAJUNGASAURUS_SPAWN_EGG.get());
                        output.accept(METRIORHYNCHUS_SPAWN_EGG.get());
                        output.accept(ONCHOPRISTIS_SPAWN_EGG.get());
                        output.accept(PACHYCEPHALOSAURUS_SPAWN_EGG.get());
                        output.accept(PACHYRHINOSAURUS_SPAWN_EGG.get());
                        output.accept(PROGNATHODON_SPAWN_EGG.get());
                        output.accept(PTERODACTYLUS_SPAWN_EGG.get());
                        output.accept(SALTOPUS_SPAWN_EGG.get());
                        output.accept(SHRINGASAURUS_SPAWN_EGG.get());
                        output.accept(THERIZINOSAURUS_SPAWN_EGG.get());
                        output.accept(TUSOTEUTHIS_SPAWN_EGG.get());
                        output.accept(ULUGHBEGSAURUS_SPAWN_EGG.get());

                        // Cenozoic
                        output.accept(DIRE_WOLF_SPAWN_EGG.get());
                        output.accept(GIANT_CAMPANILE_SPAWN_EGG.get());
                        output.accept(KING_LINGCOD_SPAWN_EGG.get());
                        output.accept(LEPTICTIDIUM_SPAWN_EGG.get());
                        output.accept(MEGALANIA_SPAWN_EGG.get());
                        output.accept(NIHOHAE_SPAWN_EGG.get());
                        output.accept(PRAEPUSA_SPAWN_EGG.get());
                        output.accept(PSILOPTERUS_SPAWN_EGG.get());
                        output.accept(SPIKE_TOOTHED_SALMON_SPAWN_EGG.get());
                        output.accept(TALPANAS_SPAWN_EGG.get());
                        output.accept(TELECREX_SPAWN_EGG.get());
                        output.accept(WOOLLY_MAMMOTH_SPAWN_EGG.get());

                        // Recently Extinct
                        output.accept(GASTRIC_BROODING_FROG_SPAWN_EGG.get());
                        output.accept(THYLACINE_SPAWN_EGG.get());

                        // Multi-Era
                        output.accept(AMMONITE_SPAWN_EGG.get());
                        output.accept(LYSTROSAURUS_SPAWN_EGG.get());

                        // Ambient
                        output.accept(AMPYX_SPAWN_EGG.get());
                        output.accept(DELITZSCHALA_SPAWN_EGG.get());
                        output.accept(SETAPEDITES_SPAWN_EGG.get());
                        output.accept(ZHANGSOLVA_SPAWN_EGG.get());

                        // Other
                        output.accept(LINGCOD_SPAWN_EGG.get());
                        output.accept(LIVING_OOZE_SPAWN_EGG.get());

                        // end region

                        // region fossils

                        output.accept(UP2Blocks.DIRT_MATRIX.get());
                        output.accept(UP2Blocks.MUD_MATRIX.get());
                        output.accept(UP2Blocks.GRAVEL_MATRIX.get());
                        output.accept(UP2Blocks.SAND_MATRIX.get());
                        output.accept(UP2Blocks.RED_SAND_MATRIX.get());
                        output.accept(UP2Blocks.SNOW_MATRIX.get());

                        output.accept(UP2Blocks.FOSSILIZED_BONE_BLOCK.get());
                        output.accept(UP2Blocks.FOSSILIZED_BONE_BARK.get());
                        output.accept(UP2Blocks.FOSSILIZED_BONE_VERTEBRA.get());
                        output.accept(UP2Blocks.FOSSILIZED_SKULL.get());
                        output.accept(UP2Blocks.FOSSILIZED_SKULL_LANTERN.get());
                        output.accept(UP2Blocks.FOSSILIZED_SKULL_SOUL_LANTERN.get());
                        output.accept(UP2Blocks.FOSSILIZED_BONE_ROD.get());
                        output.accept(UP2Blocks.FOSSILIZED_BONE_ROW.get());
                        output.accept(UP2Blocks.FOSSILIZED_BONE_SPIKE.get());
                        output.accept(UP2Blocks.COBBLED_FOSSILIZED_BONE.get());
                        output.accept(UP2Blocks.COBBLED_FOSSILIZED_BONE_STAIRS.get());
                        output.accept(UP2Blocks.COBBLED_FOSSILIZED_BONE_SLAB.get());
                        output.accept(UP2Blocks.COMMON_FOSSIL_BED.get());
                        output.accept(UP2Blocks.UNCOMMON_FOSSIL_BED.get());
                        output.accept(UP2Blocks.RARE_FOSSIL_BED.get());
                        output.accept(UP2Blocks.UNUSUAL_FOSSIL_BED.get());

                        // Paleozoic
                        output.accept(BRISTLE_FOSSIL.get()); // aegirocassis
                        output.accept(FORCIPULE_FOSSIL.get()); // arthropleura
                        output.accept(THUNDEROUS_FOSSIL.get()); // brontoscorpio
                        output.accept(ROCKET_FOSSIL.get()); // cameroceras
                        output.accept(GLUTTONOUS_FOSSIL.get()); // coelacanthus
                        output.accept(ROTUND_FOSSIL.get()); // cotylorhynchus
                        output.accept(BOOMERANG_FOSSIL.get()); // diplocaulus
                        output.accept(GUILLOTINE_FOSSIL.get()); // dunkleosteus
                        output.accept(PLOW_FOSSIL.get()); // hibbertopterus
                        output.accept(COMBUSTIBLE_FOSSIL.get()); // hynerpeton
                        output.accept(JAWLESS_FOSSIL.get()); // jawless fish
                        output.accept(FISH_FOSSIL.get()); // lobe finned fish
                        output.accept(SUCTION_FOSSIL.get()); // rhizodus
                        output.accept(ANVIL_FOSSIL.get()); // stethacanthus
                        output.accept(MOSSY_FOSSIL.get()); // tartuosteus

                        // Mesozoic
                        output.accept(SNOW_SHOVEL_FOSSIL.get()); // antarctopelta
                        output.accept(SHORT_WING_FOSSIL.get()); // anurognathus
                        output.accept(GRACILE_FOSSIL.get()); // austroraptor
                        output.accept(ARM_FOSSIL.get()); // brachiosaurus
                        output.accept(FURY_FOSSIL.get()); // carnotaurus
                        output.accept(CONCAVE_FOSSIL.get()); // concavenator
                        output.accept(CRYPTIC_FOSSIL.get()); // cryptoclidus
                        output.accept(FLAT_BACK_FOSSIL.get()); // desmatosuchus
                        output.accept(RUNNER_FOSSIL.get()); // dromaeosaurus
                        output.accept(FISH_REPTILE_FOSSIL.get()); // ichthyosaurus
                        output.accept(BOAR_TOOTH_FOSSIL.get()); // kaprosuchus
                        output.accept(PRICKLY_FOSSIL.get()); // kentrosaurus
                        output.accept(IMPERATIVE_FOSSIL.get()); // kimmeridgebrachypteraeschnidium
                        output.accept(GARGANTUAN_FOSSIL.get()); // leedsichthys
                        output.accept(CLAMP_JAW_FOSSIL.get()); // lorrainosaurus
                        output.accept(RUGOSE_FOSSIL.get()); // majungasaurus
                        output.accept(MELTDOWN_FOSSIL.get()); // metriorhynchus
                        output.accept(SAW_FOSSIL.get()); // onchopristis
                        output.accept(CRANIUM_FOSSIL.get()); // pachycephalosaurus
                        output.accept(SURGE_FOSSIL.get()); // prognathodon
                        output.accept(WING_FOSSIL.get()); // pterodactylus
                        output.accept(SCYTHE_FOSSIL.get()); // therizinosaurus
                        output.accept(SHIMMER_FOSSIL.get()); // tusoteuthis
                        output.accept(DUBIOUS_FOSSIL.get()); // ulughbegsaurus

                        // Cenozoic
                        output.accept(PILLAR_FOSSIL.get()); // giant campanile
                        output.accept(CROWN_FOSSIL.get()); // king lingcod
                        output.accept(TRUNK_MOUSE_FOSSIL.get()); // leptictidium
                        output.accept(THERMAL_FOSSIL.get()); // megalania
                        output.accept(DOLPHIN_FOSSIL.get()); // nihohae
                        output.accept(FLIPPER_FOSSIL.get()); // praepusa
                        output.accept(CROOKED_BEAK_FOSSIL.get()); // psilopterus
                        output.accept(ROT_FOSSIL.get()); // spike-toothed salmon
                        output.accept(AGED_FEATHER.get()); // talpanas
                        output.accept(PLUMAGE_FOSSIL.get()); // telecrex
                        output.accept(MOLAR_FOSSIL.get()); // woolly mammoth

                        // Multi-Era
                        output.accept(SPIRAL_FOSSIL.get()); // ammonite
                        output.accept(IMPERVIOUS_FOSSIL.get()); // lystrosaurus

                        // Recently Extinct
                        output.accept(STRIPED_PELT.get()); // thylacine

                        // Plants
                        output.accept(UP2Blocks.PETRIFIED_BUSH.get());
                        output.accept(UP2Blocks.PETRIFIED_LOG.get());
                        output.accept(UP2Blocks.PETRIFIED_WOOD.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD_STAIRS.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD_SLAB.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD_PRESSURE_PLATE.get());
                        output.accept(UP2Blocks.POLISHED_PETRIFIED_WOOD_BUTTON.get());

                        output.accept(BRACHYPHYLLUM_FOSSIL.get());
                        output.accept(QUILLWORT_FOSSIL.get());
                        output.accept(CYCAD_FOSSIL.get());
                        output.accept(GUANGDEDENDRON_FOSSIL.get());
                        output.accept(PROTOTAXITES_FOSSIL.get());
                        output.accept(DRYOPHYLLUM_FOSSIL.get());
                        output.accept(GINKGO_FOSSIL.get());
                        output.accept(LEPIDODENDRON_FOSSIL.get());
                        output.accept(METASEQUOIA_FOSSIL.get());

                        // end region

                        // region revival

                        output.accept(PALEOPEDIA.get());

                        output.accept(ORGANIC_OOZE.get());
                        output.accept(UP2Blocks.ORGANIC_OOZE_BLOCK.get());
                        output.accept(LIVING_OOZE_BUCKET.get());
                        output.accept(MACHINE_PARTS.get());
                        output.accept(UP2Blocks.TRANSMOGRIFIER.get());

                        // Paleozoic
                        output.accept(UP2Blocks.AEGIROCASSIS_EGGS.get());
                        output.accept(BRONTOSCORPIO_EMBRYO.get());
                        output.accept(UP2Blocks.CAMEROCERAS_EGGS.get());
                        output.accept(UP2Blocks.COELACANTHUS_ROE.get());
                        output.accept(UP2Blocks.DIPLOCAULUS_EGGS.get());
                        output.accept(UP2Blocks.DUNKLEOSTEUS_SAC.get());
                        output.accept(UP2Blocks.HIBBERTOPTERUS_EGGS.get());
                        output.accept(UP2Blocks.HYNERPETON_EGGS.get());
                        output.accept(UP2Blocks.JAWLESS_FISH_ROE.get());
                        output.accept(UP2Blocks.LOBE_FINNED_FISH_ROE.get());
                        output.accept(UP2Blocks.RHIZODUS_ROE.get());
                        output.accept(UP2Blocks.STETHACANTHUS_SAC.get());
                        output.accept(UP2Blocks.TARTUOSTEUS_ROE.get());

                        // Mesozoic
                        output.accept(ANUROGNATHUS_EGG.get());
                        output.accept(AUSTRORAPTOR_EGG.get());
                        output.accept(UP2Blocks.BRACHIOSAURUS_EGG.get());
                        output.accept(UP2Blocks.CARNOTAURUS_EGG.get());
                        output.accept(UP2Blocks.CONCAVENATOR_EGG.get());
                        output.accept(CRYPTOCLIDUS_EMBRYO.get());
                        output.accept(UP2Blocks.DESMATOSUCHUS_EGG.get());
                        output.accept(DROMAEOSAURUS_EGG.get());
                        output.accept(ICHTHYOSAURUS_EMBRYO.get());
                        output.accept(UP2Blocks.KAPROSUCHUS_EGG.get());
                        output.accept(UP2Blocks.KENTROSAURUS_EGG.get());
                        output.accept(UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get());
                        output.accept(UP2Blocks.LEEDSICHTHYS_ROE.get());
                        output.accept(LORRAINOSAURUS_EMBRYO.get());
                        output.accept(UP2Blocks.MAJUNGASAURUS_EGG.get());
                        output.accept(METRIORHYNCHUS_EMBRYO.get());
                        output.accept(UP2Blocks.ONCHOPRISTIS_SAC.get());
                        output.accept(UP2Blocks.PACHYCEPHALOSAURUS_EGG.get());
                        output.accept(PROGNATHODON_EMBRYO.get());
                        output.accept(PTERODACTYLUS_EGG.get());
                        output.accept(UP2Blocks.THERIZINOSAURUS_EGG.get());
                        output.accept(UP2Blocks.TUSOTEUTHIS_EGGS.get());
                        output.accept(UP2Blocks.ULUGHBEGSAURUS_EGG.get());

                        // Cenozoic
                        output.accept(UP2Blocks.GIANT_CAMPANILE_EGGS.get());
                        output.accept(UP2Blocks.KING_LINGCOD_ROE.get());
                        output.accept(LEPTICTIDIUM_EMBRYO.get());
                        output.accept(UP2Blocks.MEGALANIA_EGG.get());
                        output.accept(NIHOHAE_EMBRYO.get());
                        output.accept(PRAEPUSA_EMBRYO.get());
                        output.accept(PSILOPTERUS_EGG.get());
                        output.accept(UP2Blocks.SPIKE_TOOTHED_SALMON_ROE.get());
                        output.accept(TALPANAS_EGG.get());
                        output.accept(TELECREX_EGG.get());
                        output.accept(WOOLLY_MAMMOTH_EMBRYO.get());

                        // Multi-Era
                        output.accept(UP2Blocks.AMMONITE_EGGS.get());
                        output.accept(UP2Blocks.LYSTROSAURUS_EGG.get());

                        // Recently Extinct
                        output.accept(THYLACINE_EMBRYO.get());

                        // Plants
                        output.accept(UP2Blocks.AETHOPHYLLUM.get());
                        output.accept(UP2Blocks.ARCHAEOSIGILLARIA.get());
                        output.accept(UP2Blocks.BENNETTITALES.get());
                        output.accept(UP2Blocks.BRACHYPHYLLUM.get());
                        output.accept(UP2Blocks.CALAMOPHYTON.get());
                        output.accept(UP2Blocks.CLADOPHLEBIS.get());
                        output.accept(UP2Blocks.COOKSONIA.get());
                        output.accept(UP2Blocks.DELITZSCHALA_STALK.get());
                        output.accept(UP2Blocks.HORSETAIL.get());
                        output.accept(UP2Blocks.LARGE_HORSETAIL.get());
                        output.accept(UP2Blocks.LEEFRUCTUS.get());
                        output.accept(UP2Blocks.NEOMARIOPTERIS.get());
                        output.accept(UP2Blocks.QUILLWORT.get());
                        output.accept(UP2Blocks.RAIGUENRAYUN.get());
                        output.accept(UP2Blocks.RHYNIA.get());
                        output.accept(UP2Blocks.TEMPSKYA.get());
                        output.accept(UP2Blocks.ZHANGSOLVA_BLOOM.get());

                        output.accept(UP2Blocks.CYCAD_SEEDLING.get());
                        output.accept(UP2Blocks.CYCAD_STEM.get());
                        output.accept(UP2Blocks.CYCAD_CROWN.get());
                        output.accept(UP2Blocks.GUANGDEDENDRON.get());

                        output.accept(UP2Blocks.PROTOTAXITES_CLUSTER.get());
                        output.accept(UP2Blocks.PROTOTAXITES.get());
                        output.accept(UP2Blocks.PROTOTAXITES_NUB.get());
                        output.accept(UP2Blocks.LARGE_PROTOTAXITES_NUB.get());
                        output.accept(UP2Blocks.PEAT.get());

                        output.accept(UP2Blocks.DRYOPHYLLUM.log().get());
                        output.accept(UP2Blocks.DRYOPHYLLUM.wood().get());
                        output.accept(UP2Blocks.DRYOPHYLLUM.strippedLog().get());
                        output.accept(UP2Blocks.DRYOPHYLLUM.strippedWood().get());
                        output.accept(UP2Blocks.DRYOPHYLLUM.planks().get());
                        output.accept(UP2Blocks.DRYOPHYLLUM.stairs().get());
                        output.accept(UP2Blocks.DRYOPHYLLUM.slab().get());
                        output.accept(UP2Blocks.DRYOPHYLLUM.fence().get());
                        output.accept(UP2Blocks.DRYOPHYLLUM.fenceGate().get());
                        output.accept(UP2Blocks.DRYOPHYLLUM.door().get());
                        output.accept(UP2Blocks.DRYOPHYLLUM.trapdoor().get());
                        output.accept(UP2Blocks.DRYOPHYLLUM.pressurePlate().get());
                        output.accept(UP2Blocks.DRYOPHYLLUM.button().get());
                        output.accept(DRYOPHYLLUM_SIGN.get());
                        output.accept(DRYOPHYLLUM_HANGING_SIGN.get());
                        output.accept(DRYOPHYLLUM_BOAT.get());
                        output.accept(DRYOPHYLLUM_CHEST_BOAT.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_LEAVES.get());
                        output.accept(UP2Blocks.DRYOPHYLLUM_SAPLING.get());

                        output.accept(UP2Blocks.GINKGO.log().get());
                        output.accept(UP2Blocks.GINKGO.wood().get());
                        output.accept(UP2Blocks.GINKGO.strippedLog().get());
                        output.accept(UP2Blocks.GINKGO.strippedWood().get());
                        output.accept(UP2Blocks.GINKGO.planks().get());
                        output.accept(UP2Blocks.GINKGO.stairs().get());
                        output.accept(UP2Blocks.GINKGO.slab().get());
                        output.accept(UP2Blocks.GINKGO.fence().get());
                        output.accept(UP2Blocks.GINKGO.fenceGate().get());
                        output.accept(UP2Blocks.GINKGO.door().get());
                        output.accept(UP2Blocks.GINKGO.trapdoor().get());
                        output.accept(UP2Blocks.GINKGO.pressurePlate().get());
                        output.accept(UP2Blocks.GINKGO.button().get());
                        output.accept(GINKGO_SIGN.get());
                        output.accept(GINKGO_HANGING_SIGN.get());
                        output.accept(GINKGO_BOAT.get());
                        output.accept(GINKGO_CHEST_BOAT.get());
                        output.accept(UP2Blocks.GINKGO_LEAVES.get());
                        output.accept(UP2Blocks.GOLDEN_GINKGO_LEAVES.get());
                        output.accept(UP2Blocks.GINKGO_SAPLING.get());
                        output.accept(UP2Blocks.GOLDEN_GINKGO_SAPLING.get());
                        output.accept(GINKGO_FRUIT.get());
                        output.accept(UP2Blocks.MOSSY_DIRT.get());

                        output.accept(UP2Blocks.LEPIDODENDRON.log().get());
                        output.accept(UP2Blocks.MOSSY_LEPIDODENDRON_LOG.get());
                        output.accept(UP2Blocks.LEPIDODENDRON.wood().get());
                        output.accept(UP2Blocks.MOSSY_LEPIDODENDRON_WOOD.get());
                        output.accept(UP2Blocks.LEPIDODENDRON.strippedLog().get());
                        output.accept(UP2Blocks.LEPIDODENDRON.strippedWood().get());
                        output.accept(UP2Blocks.LEPIDODENDRON.planks().get());
                        output.accept(UP2Blocks.LEPIDODENDRON.stairs().get());
                        output.accept(UP2Blocks.LEPIDODENDRON.slab().get());
                        output.accept(UP2Blocks.LEPIDODENDRON.fence().get());
                        output.accept(UP2Blocks.LEPIDODENDRON.fenceGate().get());
                        output.accept(UP2Blocks.LEPIDODENDRON.door().get());
                        output.accept(UP2Blocks.LEPIDODENDRON.trapdoor().get());
                        output.accept(UP2Blocks.LEPIDODENDRON.pressurePlate().get());
                        output.accept(UP2Blocks.LEPIDODENDRON.button().get());
                        output.accept(LEPIDODENDRON_SIGN.get());
                        output.accept(LEPIDODENDRON_HANGING_SIGN.get());
                        output.accept(LEPIDODENDRON_BOAT.get());
                        output.accept(LEPIDODENDRON_CHEST_BOAT.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_LEAVES.get());
                        output.accept(UP2Blocks.HANGING_LEPIDODENDRON_LEAVES.get());
                        output.accept(UP2Blocks.LEPIDODENDRON_CONE.get());
                        output.accept(UP2Blocks.MOSS_LAYER.get());
                        output.accept(UP2Blocks.LIVING_PEAT.get());

                        output.accept(UP2Blocks.METASEQUOIA.log().get());
                        output.accept(UP2Blocks.METASEQUOIA.wood().get());
                        output.accept(UP2Blocks.METASEQUOIA.strippedLog().get());
                        output.accept(UP2Blocks.METASEQUOIA.strippedWood().get());
                        output.accept(UP2Blocks.METASEQUOIA.planks().get());
                        output.accept(UP2Blocks.METASEQUOIA.stairs().get());
                        output.accept(UP2Blocks.METASEQUOIA.slab().get());
                        output.accept(UP2Blocks.METASEQUOIA.fence().get());
                        output.accept(UP2Blocks.METASEQUOIA.fenceGate().get());
                        output.accept(UP2Blocks.METASEQUOIA.door().get());
                        output.accept(UP2Blocks.METASEQUOIA.trapdoor().get());
                        output.accept(UP2Blocks.METASEQUOIA.pressurePlate().get());
                        output.accept(UP2Blocks.METASEQUOIA.button().get());
                        output.accept(METASEQUOIA_SIGN.get());
                        output.accept(METASEQUOIA_HANGING_SIGN.get());
                        output.accept(METASEQUOIA_BOAT.get());
                        output.accept(METASEQUOIA_CHEST_BOAT.get());
                        output.accept(UP2Blocks.METASEQUOIA_LEAVES.get());
                        output.accept(UP2Blocks.DAWN_METASEQUOIA_LEAVES.get());
                        output.accept(UP2Blocks.DUSK_METASEQUOIA_LEAVES.get());
                        output.accept(UP2Blocks.METASEQUOIA_SAPLING.get());
                        output.accept(UP2Blocks.NEEDLE_LITTER.get());

                        // end region

                        // region misc

                        // Paleozoic
                        output.accept(BABY_AEGIROCASSIS_BUCKET.get());
                        output.accept(COELACANTHUS_BUCKET.get());
                        output.accept(DIPLOCAULUS_BUCKET.get());
                        output.accept(DUNKLEOSTEUS_BUCKET.get());
                        output.accept(HYNERPETON_BUCKET.get());
                        output.accept(JAWLESS_FISH_BUCKET.get());
                        output.accept(LOBE_FINNED_FISH_BUCKET.get());
                        output.accept(STETHACANTHUS_BUCKET.get());

                        output.accept(UP2Blocks.COTYLORHYNCHUS_PLUSHIE.get());
                        output.accept(UP2Blocks.HIBBERTOPTERUS_PLUSHIE.get());
                        output.accept(UP2Blocks.TARTUOSTEUS_PLUSHIE.get());

                        // Mesozoic
                        output.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_BUCKET.get());
                        output.accept(KIMMERIDGEBRACHYPTERAESCHNIDIUM_BOTTLE.get());
                        output.accept(ONCHOPRISTIS_BUCKET.get());
                        output.accept(PTERODACTYLUS_POT.get());

                        output.accept(SWEET_GROG_BOTTLE.get());
                        output.accept(FOUL_GROG_BOTTLE.get());

                        output.accept(LEEDSICHTHYS_SLICE.get());
                        output.accept(UP2Blocks.LEEDSICHTHYS_SLICE_BLOCK.get());
                        output.accept(UP2Blocks.LEEDSICHTHYS_SLICE_STAIRS.get());
                        output.accept(UP2Blocks.LEEDSICHTHYS_SLICE_SLAB.get());

                        output.accept(UP2Blocks.BRACHIOSAURUS_PLUSHIE.get());
                        output.accept(UP2Blocks.CARNOTAURUS_PLUSHIE.get());
                        output.accept(UP2Blocks.KENTROSAURUS_PLUSHIE.get());
                        output.accept(UP2Blocks.MAJUNGASAURUS_PLUSHIE.get());

                        // Cenozoic
                        output.accept(PRAEPUSA_BUCKET.get());

                        output.accept(UP2Blocks.GIANT_CAMPANILE_PLUSHIE.get());

                        // Recently Extinct
                        output.accept(GASTRIC_BROODING_FROG_BUCKET.get());

                        output.accept(UP2Blocks.BILE_GASTRICLIGHT.get());
                        output.accept(UP2Blocks.BROOD_GASTRICLIGHT.get());
                        output.accept(UP2Blocks.BLOOD_GASTRICLIGHT.get());

                        // Other
                        output.accept(BROWN_MUSHROOM_ON_A_STICK.get());
                        output.accept(DIRT_ON_A_STICK.get());

                        output.accept(PLUSHIE_KIT.get());

                        output.accept(AMBER_BEAR.get());
                        output.accept(COPROLITE.get());
                        output.accept(FOSSILIZED_FOOTPRINT.get());
                        output.accept(GASTROLITH.get());
                        output.accept(PETRIFIED_EGG.get());
                        output.accept(INDOCHINITE.get());
                        output.accept(FOSSILIZED_METEORITE.get());
                        output.accept(CHONDRITE.get());
                        output.accept(PALLASITE.get());

                        output.accept(DOOMSURF_DISC.get());
                        output.accept(PUMMEL_AND_SNATCH_DISC.get());
                        output.accept(TARIFYING_DISC.get());
                        output.accept(MEGALANIA_DISC.get());

                        output.accept(OOZE_BANNER_PATTERN.get());
                        output.accept(PALEOZOIC_BANNER_PATTERN.get());
                        output.accept(MESOZOIC_BANNER_PATTERN.get());
                        output.accept(CENOZOIC_BANNER_PATTERN.get());
                        output.accept(getPainting(UP2PaintingVariants.UNUSUAL_ENCOUNTER));

                        output.accept(TAR_BUCKET.get());
                        output.accept(UP2Blocks.ASPHALT.get());

                        output.accept(UP2Blocks.REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.TINTED_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.WHITE_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.LIGHT_GRAY_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.GRAY_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.BLACK_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.BROWN_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.RED_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.ORANGE_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.YELLOW_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.LIME_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.GREEN_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.CYAN_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.LIGHT_BLUE_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.BLUE_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.PURPLE_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.MAGENTA_REINFORCED_GLASS.get());
                        output.accept(UP2Blocks.PINK_REINFORCED_GLASS.get());

                        // end region

                    }).build());

    @SuppressWarnings("SameParameterValue")
    public static ItemStack getPainting(ResourceKey<PaintingVariant> painting) {
        ItemStack itemStack = new ItemStack(Items.PAINTING);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("id", "minecraft:painting");
        compoundTag.putString("variant", painting.location().toString());
        itemStack.set(DataComponents.ENTITY_DATA, CustomData.of(compoundTag));
        return itemStack;
    }
}