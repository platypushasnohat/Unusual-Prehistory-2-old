package com.barl_inc.unusual_prehistory.datagen.server;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2CriteriaTriggers;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import com.barl_inc.unusual_prehistory.tags.UP2ItemTags;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UP2AdvancementProvider implements AdvancementProvider.AdvancementGenerator {

    public static AdvancementProvider register(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
        return new AdvancementProvider(output, provider, helper, List.of(new UP2AdvancementProvider()));
    }

    @Override
    public void generate(@NotNull Provider provider, @NotNull Consumer<AdvancementHolder> consumer, @NotNull ExistingFileHelper helper) {

        AdvancementHolder root = Advancement.Builder.advancement().display(UP2Items.UNUSUAL_PREHISTORY.get(), Component.translatable("advancements.unusual_prehistory.root.title"), Component.translatable("advancements.unusual_prehistory.root.description"), UnusualPrehistory2.modPrefix("textures/block/cobbled_fossilized_bone.png"), AdvancementType.TASK, false, false, false)
                .addCriterion("fossils", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.FOSSILS).build()))
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PREHISTORIC_EGGS).build()))
                .addCriterion("machine_parts", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.MACHINE_PARTS.get()))
                .addCriterion("paleopedia", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.PALEOPEDIA.get()))
                .addCriterion("transmogrifier", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Blocks.TRANSMOGRIFIER.get()))
                .addCriterion("organic_ooze", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.ORGANIC_OOZE.get()))
                .addCriterion("living_ooze", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.LIVING_OOZE_BUCKET.get()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, UnusualPrehistory2.modPrefix("root"), helper);

        Advancement.Builder.advancement()
                .addCriterion("open_book_creative", UP2CriteriaTriggers.OPEN_BOOK_CREATIVE_MODE.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                .save(consumer, UnusualPrehistory2.modPrefix("open_book_creative"), helper);

        Advancement.Builder.advancement()
                .addCriterion("obtain_tar_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.TAR_BUCKET.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_tar_bucket"), helper);

        Advancement.Builder.advancement()
                .addCriterion("obtain_asphalt", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Blocks.ASPHALT.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_asphalt"), helper);

        // Progression & misc
        AdvancementHolder fossils = createAdvancement("obtain_fossil", root, UP2Items.UNKNOWN_FOSSIL.get(), AdvancementType.TASK)
                .addCriterion("fossils", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.FOSSILS).build()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_fossil"), helper);

        AdvancementHolder machineParts = createAdvancement("obtain_machine_parts", fossils, UP2Items.MACHINE_PARTS.get(), AdvancementType.TASK)
                .addCriterion("machine_parts", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.MACHINE_PARTS.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_machine_parts"), helper);

        AdvancementHolder transmogrifier = createAdvancement("obtain_transmogrifier", machineParts, UP2Blocks.TRANSMOGRIFIER.get(), AdvancementType.GOAL)
                .addCriterion("transmogrifier", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Blocks.TRANSMOGRIFIER.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_transmogrifier"), helper);

        AdvancementHolder organicOoze = createAdvancement("obtain_organic_ooze", transmogrifier, UP2Items.ORGANIC_OOZE.get(), AdvancementType.TASK)
                .addCriterion("organic_ooze", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.ORGANIC_OOZE.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_organic_ooze"), helper);

        AdvancementHolder eggs = Advancement.Builder.advancement().parent(organicOoze).display(UP2Items.UNKNOWN_EGG.get(), Component.translatable("advancements.unusual_prehistory.obtain_egg.title"), Component.translatable("advancements.unusual_prehistory.obtain_egg.description"), null, AdvancementType.GOAL, true, true, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PREHISTORIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_egg"), helper);

        createAdvancement("obtain_living_ooze", organicOoze, UP2Items.LIVING_OOZE_BUCKET.get(), AdvancementType.GOAL, false, false, false)
                .addCriterion("living_ooze_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(UP2Items.LIVING_OOZE_BUCKET.get()))
                .save(consumer, UnusualPrehistory2.modPrefix("obtain_living_ooze"), helper);

        // Paleozoic periods
        AdvancementHolder paleozoicRoot = Advancement.Builder.advancement().parent(eggs).display(UP2Items.PALEOZOIC_FOSSIL.get(), Component.translatable("advancements.unusual_prehistory.paleozoic_root.title"), Component.translatable("advancements.unusual_prehistory.paleozoic_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR)
                .save(consumer, UnusualPrehistory2.modPrefix("paleozoic_root"), helper);

        AdvancementHolder ordovicianRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PERIOD_ORDOVICIAN.get(), Component.translatable("advancements.unusual_prehistory.ordovician_root.title"), Component.translatable("advancements.unusual_prehistory.ordovician_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("ordovician_root"), helper);
        AdvancementHolder silurianRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PERIOD_SILURIAN.get(), Component.translatable("advancements.unusual_prehistory.silurian_root.title"), Component.translatable("advancements.unusual_prehistory.silurian_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("silurian_root"), helper);
        AdvancementHolder devonianRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PERIOD_DEVONIAN.get(), Component.translatable("advancements.unusual_prehistory.devonian_root.title"), Component.translatable("advancements.unusual_prehistory.devonian_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("devonian_root"), helper);
        AdvancementHolder carboniferousRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PERIOD_CARBONIFEROUS.get(), Component.translatable("advancements.unusual_prehistory.carboniferous_root.title"), Component.translatable("advancements.unusual_prehistory.carboniferous_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("carboniferous_root"), helper);
        AdvancementHolder permianRoot = Advancement.Builder.advancement().parent(paleozoicRoot).display(UP2Items.PERIOD_PERMIAN.get(), Component.translatable("advancements.unusual_prehistory.permian_root.title"), Component.translatable("advancements.unusual_prehistory.permian_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.PALEOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("permian_root"), helper);

        AdvancementHolder mesozoicRoot = Advancement.Builder.advancement().parent(eggs).display(UP2Items.MESOZOIC_FOSSIL.get(), Component.translatable("advancements.unusual_prehistory.mesozoic_root.title"), Component.translatable("advancements.unusual_prehistory.mesozoic_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.MESOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("mesozoic_root"), helper);

        AdvancementHolder triassicRoot = Advancement.Builder.advancement().parent(mesozoicRoot).display(UP2Items.PERIOD_TRIASSIC.get(), Component.translatable("advancements.unusual_prehistory.triassic_root.title"), Component.translatable("advancements.unusual_prehistory.triassic_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.MESOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("triassic_root"), helper);
        AdvancementHolder jurassicRoot = Advancement.Builder.advancement().parent(mesozoicRoot).display(UP2Items.PERIOD_JURASSIC.get(), Component.translatable("advancements.unusual_prehistory.jurassic_root.title"), Component.translatable("advancements.unusual_prehistory.jurassic_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.MESOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("jurassic_root"), helper);
        AdvancementHolder cretaceousRoot = Advancement.Builder.advancement().parent(mesozoicRoot).display(UP2Items.PERIOD_CRETACEOUS.get(), Component.translatable("advancements.unusual_prehistory.cretaceous_root.title"), Component.translatable("advancements.unusual_prehistory.cretaceous_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.MESOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("cretaceous_root"), helper);

        AdvancementHolder cenozoicRoot = Advancement.Builder.advancement().parent(eggs).display(UP2Items.CENOZOIC_FOSSIL.get(), Component.translatable("advancements.unusual_prehistory.cenozoic_root.title"), Component.translatable("advancements.unusual_prehistory.cenozoic_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("cenozoic_root"), helper);

        AdvancementHolder paleogeneRoot = Advancement.Builder.advancement().parent(cenozoicRoot).display(UP2Items.PERIOD_PALEOGENE.get(), Component.translatable("advancements.unusual_prehistory.paleogene_root.title"), Component.translatable("advancements.unusual_prehistory.paleogene_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("paleogene_root"), helper);
        AdvancementHolder neogeneRoot = Advancement.Builder.advancement().parent(cenozoicRoot).display(UP2Items.PERIOD_NEOGENE.get(), Component.translatable("advancements.unusual_prehistory.neogene_root.title"), Component.translatable("advancements.unusual_prehistory.neogene_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("neogene_root"), helper);
        AdvancementHolder quaternaryRoot = Advancement.Builder.advancement().parent(cenozoicRoot).display(UP2Items.PERIOD_QUATERNARY.get(), Component.translatable("advancements.unusual_prehistory.quaternary_root.title"), Component.translatable("advancements.unusual_prehistory.quaternary_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("quaternary_root"), helper);
        AdvancementHolder holoceneRoot = Advancement.Builder.advancement().parent(cenozoicRoot).display(UP2Items.PERIOD_HOLOCENE.get(), Component.translatable("advancements.unusual_prehistory.holocene_root.title"), Component.translatable("advancements.unusual_prehistory.holocene_root.description"), null, AdvancementType.TASK, false, false, false)
                .addCriterion("eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(UP2ItemTags.CENOZOIC_EGGS).build()))
                .requirements(AdvancementRequirements.Strategy.OR).save(consumer, UnusualPrehistory2.modPrefix("holocene_root"), helper);

        // Ordovician
        AdvancementHolder reviveAegirocassis = reviveMobAdvancement(ordovicianRoot, UP2Blocks.AEGIROCASSIS_EGGS.get(), UP2Entities.AEGIROCASSIS.get(), consumer, helper);

        // Silurian
        AdvancementHolder reviveBrontoscorpio = reviveMobAdvancement(silurianRoot, UP2Items.BRONTOSCORPIO_EMBRYO, UP2Entities.BRONTOSCORPIO.get(), consumer, helper);

        // Devonian
        AdvancementHolder reviveDunkleosteus = reviveMobAdvancement(devonianRoot, UP2Blocks.DUNKLEOSTEUS_SAC.get(), UP2Entities.DUNKLEOSTEUS.get(), consumer, helper);
        AdvancementHolder reviveHynerpeton = reviveMobAdvancement(reviveDunkleosteus, UP2Blocks.HYNERPETON_EGGS.get(), UP2Entities.HYNERPETON.get(), consumer, helper);
        AdvancementHolder reviveJawlessFish = reviveMobAdvancement(reviveHynerpeton, UP2Blocks.JAWLESS_FISH_ROE.get(), UP2Entities.JAWLESS_FISH.get(), consumer, helper);
        AdvancementHolder reviveStethacanthus = reviveMobAdvancement(reviveJawlessFish, UP2Blocks.STETHACANTHUS_SAC.get(), UP2Entities.STETHACANTHUS.get(), consumer, helper);
        AdvancementHolder reviveTartuosteus = reviveMobAdvancement(reviveStethacanthus, UP2Blocks.TARTUOSTEUS_ROE.get(), UP2Entities.TARTUOSTEUS.get(), consumer, helper);

        // Carboniferous
        AdvancementHolder reviveDiplocaulus = reviveMobAdvancement(carboniferousRoot, UP2Blocks.DIPLOCAULUS_EGGS.get(), UP2Entities.DIPLOCAULUS.get(), consumer, helper);
        AdvancementHolder reviveHibbertopterus = reviveMobAdvancement(reviveDiplocaulus, UP2Blocks.HIBBERTOPTERUS_EGGS.get(), UP2Entities.HIBBERTOPTERUS.get(), consumer, helper);
        AdvancementHolder reviveLobeFinnedFish = reviveMobAdvancement(reviveHibbertopterus, UP2Blocks.LOBE_FINNED_FISH_ROE.get(), UP2Entities.LOBE_FINNED_FISH.get(), consumer, helper);

        // Permian
        AdvancementHolder reviveCoelacanthus = reviveMobAdvancement(permianRoot, UP2Blocks.COELACANTHUS_ROE.get(), UP2Entities.COELACANTHUS.get(), consumer, helper);

        // Triassic
        AdvancementHolder reviveDesmatosuchus = reviveMobAdvancement(triassicRoot, UP2Blocks.DESMATOSUCHUS_EGG.get(), UP2Entities.DESMATOSUCHUS.get(), consumer, helper);
        AdvancementHolder reviveLystrosaurus = reviveMobAdvancement(reviveDesmatosuchus, UP2Blocks.LYSTROSAURUS_EGG.get(), UP2Entities.LYSTROSAURUS.get(), consumer, helper);

        // Jurassic
        AdvancementHolder reviveBrachiosaurus = reviveMobAdvancement(jurassicRoot, UP2Blocks.BRACHIOSAURUS_EGG.get(), UP2Entities.BRACHIOSAURUS.get(), consumer, helper);
        AdvancementHolder reviveCryptoclidus = reviveMobAdvancement(reviveBrachiosaurus, UP2Items.CRYPTOCLIDUS_EMBRYO.get(), UP2Entities.CRYPTOCLIDUS.get(), consumer, helper);
        AdvancementHolder reviveKentrosaurus = reviveMobAdvancement(reviveCryptoclidus, UP2Blocks.KENTROSAURUS_EGG.get(), UP2Entities.KENTROSAURUS.get(), consumer, helper);
        AdvancementHolder reviveKimmeridgebrachypteraeschnidium = reviveMobAdvancement(reviveKentrosaurus, UP2Blocks.KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS.get(), UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), consumer, helper);
        AdvancementHolder reviveIchthyosaurus = reviveMobAdvancement(reviveKimmeridgebrachypteraeschnidium, UP2Items.ICHTHYOSAURUS_EMBRYO.get(), UP2Entities.ICHTHYOSAURUS.get(), consumer, helper);
        AdvancementHolder reviveLeedsichthys = reviveMobAdvancement(reviveIchthyosaurus, UP2Blocks.LEEDSICHTHYS_ROE.get(), UP2Entities.LEEDSICHTHYS.get(), consumer, helper);
        AdvancementHolder reviveLorrainosaurus = reviveMobAdvancement(reviveLeedsichthys, UP2Items.LORRAINOSAURUS_EMBRYO.get(), UP2Entities.LORRAINOSAURUS.get(), consumer, helper);
        AdvancementHolder reviveMetriorhynchus = reviveMobAdvancement(reviveLorrainosaurus, UP2Items.METRIORHYNCHUS_EMBRYO.get(), UP2Entities.METRIORHYNCHUS.get(), consumer, helper);
        AdvancementHolder revivePterodactylus = reviveMobAdvancement(reviveMetriorhynchus, UP2Items.PTERODACTYLUS_EGG.get(), UP2Entities.PTERODACTYLUS.get(), consumer, helper);

        // Cretaceous
        AdvancementHolder reviveCarnotaurus = reviveMobAdvancement(cretaceousRoot, UP2Blocks.CARNOTAURUS_EGG.get(), UP2Entities.CARNOTAURUS.get(), consumer, helper);
        AdvancementHolder reviveDromaeosaurus = reviveMobAdvancement(reviveCarnotaurus, UP2Items.DROMAEOSAURUS_EGG.get(), UP2Entities.DROMAEOSAURUS.get(), consumer, helper);
        AdvancementHolder reviveKaprosuchus = reviveMobAdvancement(reviveDromaeosaurus, UP2Blocks.KAPROSUCHUS_EGG.get(), UP2Entities.KAPROSUCHUS.get(), consumer, helper);
        AdvancementHolder reviveMajungasaurus = reviveMobAdvancement(reviveKaprosuchus, UP2Blocks.MAJUNGASAURUS_EGG.get(), UP2Entities.MAJUNGASAURUS.get(), consumer, helper);
        AdvancementHolder reviveOnchopristis = reviveMobAdvancement(reviveMajungasaurus, UP2Blocks.ONCHOPRISTIS_SAC.get(), UP2Entities.ONCHOPRISTIS.get(), consumer, helper);
        AdvancementHolder revivePachycephalosaurus = reviveMobAdvancement(reviveOnchopristis, UP2Blocks.PACHYCEPHALOSAURUS_EGG.get(), UP2Entities.PACHYCEPHALOSAURUS.get(), consumer, helper);
        AdvancementHolder revivePrognathodon = reviveMobAdvancement(revivePachycephalosaurus, UP2Items.PROGNATHODON_EMBRYO.get(), UP2Entities.PROGNATHODON.get(), consumer, helper);
        AdvancementHolder reviveTherizinosaurus = reviveMobAdvancement(revivePrognathodon, UP2Blocks.THERIZINOSAURUS_EGG.get(), UP2Entities.THERIZINOSAURUS.get(), consumer, helper);
        AdvancementHolder reviveTusoteuthis = reviveMobAdvancement(reviveTherizinosaurus, UP2Blocks.TUSOTEUTHIS_EGGS.get(), UP2Entities.TUSOTEUTHIS.get(), consumer, helper);
        AdvancementHolder reviveUlughbegsaurus = reviveMobAdvancement(reviveTusoteuthis, UP2Blocks.ULUGHBEGSAURUS_EGG.get(), UP2Entities.ULUGHBEGSAURUS.get(), consumer, helper);

        // Paleogene
        AdvancementHolder reviveLeptictidium = reviveMobAdvancement(paleogeneRoot, UP2Items.LEPTICTIDIUM_EMBRYO.get(), UP2Entities.LEPTICTIDIUM.get(), consumer, helper);
        AdvancementHolder revivePsilopterus = reviveMobAdvancement(reviveLeptictidium, UP2Items.PSILOPTERUS_EGG.get(), UP2Entities.PSILOPTERUS.get(), consumer, helper);
        AdvancementHolder reviveTelecrex = reviveMobAdvancement(revivePsilopterus, UP2Items.TELECREX_EGG.get(), UP2Entities.TELECREX.get(), consumer, helper);

        // Neogene
        AdvancementHolder revivePraepusa = reviveMobAdvancement(neogeneRoot, UP2Items.PRAEPUSA_EMBRYO.get(), UP2Entities.PRAEPUSA.get(), consumer, helper);
        AdvancementHolder reviveKingLingcod = reviveMobAdvancement(revivePraepusa, UP2Blocks.KING_LINGCOD_ROE.get(), UP2Entities.KING_LINGCOD.get(), consumer, helper);
        AdvancementHolder reviveSpikeToothedSalmon = reviveMobAdvancement(reviveKingLingcod, UP2Blocks.SPIKE_TOOTHED_SALMON_ROE.get(), UP2Entities.SPIKE_TOOTHED_SALMON.get(), consumer, helper);

        // Quaternary
        AdvancementHolder reviveMegalania = reviveMobAdvancement(quaternaryRoot, UP2Blocks.MEGALANIA_EGG.get(), UP2Entities.MEGALANIA.get(), consumer, helper);
        AdvancementHolder reviveWoollyMammoth = reviveMobAdvancement(reviveMegalania, UP2Items.WOOLLY_MAMMOTH_EMBRYO.get(), UP2Entities.WOOLLY_MAMMOTH.get(), consumer, helper);

        // Holocene
        AdvancementHolder reviveTalpanas = reviveMobAdvancement(holoceneRoot, UP2Items.TALPANAS_EGG.get(), UP2Entities.TALPANAS.get(), consumer, helper);
    }

    private static Advancement.Builder createAdvancement(String name, AdvancementHolder parent, ItemLike icon, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(icon,
                Component.translatable("advancements." + UnusualPrehistory2.MOD_ID + "." + name + ".title"),
                Component.translatable("advancements." + UnusualPrehistory2.MOD_ID + "." + name + ".description"),
                null, frame, showToast, announceToChat, hidden);
    }

    private static Advancement.Builder createAdvancement(String name, AdvancementHolder parent, ItemLike icon, AdvancementType frame) {
        return createAdvancement(name, parent, icon, frame, true, true, false);
    }

    private static Advancement.Builder reviveMobAdvancement(String name, AdvancementHolder parent, ItemLike icon, EntityType<?> entityType) {
        return createAdvancement(name, parent, icon, AdvancementType.TASK, true, true, true).addCriterion(name, SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(entityType))).rewards(AdvancementRewards.Builder.experience(5));
    }

    private static AdvancementHolder reviveMobAdvancement(AdvancementHolder parent, ItemLike icon, EntityType<?> entityType, Consumer<AdvancementHolder> consumer, ExistingFileHelper helper) {
        String egg = "obtain_" + BuiltInRegistries.ITEM.getKey(icon.asItem()).getPath();
        String name = "revive_" + BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getPath();
        Advancement.Builder.advancement().addCriterion(egg, InventoryChangeTrigger.TriggerInstance.hasItems(icon)).save(consumer, UnusualPrehistory2.modPrefix(egg), helper);
        return reviveMobAdvancement(name, parent, icon, entityType).save(consumer, UnusualPrehistory2.modPrefix(name), helper);
    }
}
