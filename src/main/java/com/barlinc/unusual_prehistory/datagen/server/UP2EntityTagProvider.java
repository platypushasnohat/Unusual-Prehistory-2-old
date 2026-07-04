package com.barlinc.unusual_prehistory.datagen.server;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.tags.UP2EntityTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.barlinc.unusual_prehistory.registry.UP2Entities.*;

public class UP2EntityTagProvider extends EntityTypeTagsProvider {

    public UP2EntityTagProvider(PackOutput packOutput, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, provider, UnusualPrehistory2.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider provider) {

        // Update 1
        this.tag(UP2EntityTags.CARNOTAURUS_TARGETS).add(
                EntityType.COW, EntityType.GOAT, EntityType.HORSE, EntityType.PIG, EntityType.SHEEP, EntityType.CAMEL, EntityType.VILLAGER,
                PACHYCEPHALOSAURUS.get(), MAJUNGASAURUS.get(), KENTROSAURUS.get()
        );
        this.tag(UP2EntityTags.DROMAEOSAURUS_TARGETS).add(
                EntityType.CHICKEN
        );
        this.tag(UP2EntityTags.SMALL_DUNKLEOSTEUS_TARGETS).add(
                EntityType.TROPICAL_FISH, EntityType.TADPOLE,
                JAWLESS_FISH.get(),
                EntityType.ZOMBIE, EntityType.PIGLIN, EntityType.SKELETON, EntityType.WITHER_SKELETON
        );
        this.tag(UP2EntityTags.MEDIUM_DUNKLEOSTEUS_TARGETS).add(
                EntityType.COD,
                EntityType.SALMON,
                JAWLESS_FISH.get(),
                LOBE_FINNED_FISH.get(),
                EntityType.ZOMBIE, EntityType.PIGLIN, EntityType.SKELETON, EntityType.WITHER_SKELETON
        );
        this.tag(UP2EntityTags.LARGE_DUNKLEOSTEUS_TARGETS).add(
                EntityType.TURTLE, EntityType.DROWNED,
                EntityType.GUARDIAN, EntityType.ZOMBIE, EntityType.PIGLIN, EntityType.SKELETON, EntityType.WITHER_SKELETON
        );
        this.tag(UP2EntityTags.MAJUNGASAURUS_TARGETS).add(
                EntityType.COW, EntityType.GOAT, EntityType.PIG, EntityType.SHEEP
        );
        this.tag(UP2EntityTags.MEGALANIA_TARGETS).add(
                EntityType.COW, EntityType.GOAT, EntityType.HORSE, EntityType.PIG, EntityType.SHEEP, EntityType.CAMEL, EntityType.VILLAGER,
                EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIE_HORSE
        );
        this.tag(UP2EntityTags.STETHACANTHUS_TARGETS).add(
                EntityType.TROPICAL_FISH, JAWLESS_FISH.get()
        );

        this.tag(UP2EntityTags.DIPLOCAULUS_AVOIDS).add(
                EntityType.GUARDIAN, EntityType.DROWNED
        );
        this.tag(UP2EntityTags.JAWLESS_FISH_AVOIDS).add(
                DUNKLEOSTEUS.get(), METRIORHYNCHUS.get(), ONCHOPRISTIS.get(),
                STETHACANTHUS.get(), EntityType.GUARDIAN, EntityType.DROWNED
        );
        this.tag(UP2EntityTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_AVOIDS).add(
                EntityType.SPIDER, EntityType.CAVE_SPIDER
        );
        this.tag(UP2EntityTags.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_AVOIDS).add(
                DUNKLEOSTEUS.get(), METRIORHYNCHUS.get(), ONCHOPRISTIS.get(), STETHACANTHUS.get(),
                EntityType.AXOLOTL, EntityType.DROWNED, EntityType.GUARDIAN
        );
        this.tag(UP2EntityTags.MAJUNGASAURUS_AVOIDS).add(
                CARNOTAURUS.get(), ULUGHBEGSAURUS.get()
        );
        this.tag(UP2EntityTags.STETHACANTHUS_AVOIDS).add(
                DUNKLEOSTEUS.get(), PROGNATHODON.get(), EntityType.DROWNED, EntityType.GUARDIAN
        );
        this.tag(UP2EntityTags.TALPANAS_AVOIDS).add(
                EntityType.OCELOT, EntityType.CAT, EntityType.FOX, EntityType.WOLF
        );
        this.tag(UP2EntityTags.TELECREX_AVOIDS).add(
                DROMAEOSAURUS.get(), EntityType.OCELOT, EntityType.CAT, EntityType.WOLF, EntityType.FOX
        );

        this.tag(UP2EntityTags.CARNOTAURUS_IGNORES).add(
                EntityType.CREEPER
        ).addOptional(ResourceLocation.fromNamespaceAndPath("species", "quake"));

        this.tag(UP2EntityTags.SWEET_BERRY_BUSH_IMMUNE).add(
                KENTROSAURUS.get()
        );

        // Update 3
        this.tag(UP2EntityTags.METRIORHYNCHUS_TARGETS).add(
                EntityType.TROPICAL_FISH,
                EntityType.COD,
                EntityType.SALMON,
                JAWLESS_FISH.get(),
                LOBE_FINNED_FISH.get()
        );

        this.tag(UP2EntityTags.TARTUOSTEUS_AVOIDS).add(
                DUNKLEOSTEUS.get(), EntityType.GUARDIAN, EntityType.DROWNED
        );

        this.tag(UP2EntityTags.METRIORHYNCHUS_CANT_GRAB).add(
                METRIORHYNCHUS.get(), EntityType.ENDER_DRAGON, EntityType.WITHER
        );
        this.tag(UP2EntityTags.METRIORHYNCHUS_CAN_GRAB).add(
                TARTUOSTEUS.get()
        );

        // Update 4
        this.tag(UP2EntityTags.KAPROSUCHUS_TARGETS).add(
                EntityType.PIG, EntityType.PIGLIN, EntityType.SKELETON, EntityType.WITHER_SKELETON, EntityType.SKELETON_HORSE
        );
        this.tag(UP2EntityTags.PACHYCEPHALOSAURUS_TARGETS).add(
                EntityType.COW, EntityType.SHEEP, EntityType.CHICKEN, EntityType.PIG, EntityType.VILLAGER, EntityType.MOOSHROOM
        );
        this.tag(UP2EntityTags.PRAEPUSA_TARGETS).add(
                EntityType.COD, EntityType.SALMON
        );
        this.tag(UP2EntityTags.ULUGHBEGSAURUS_TARGETS).add(
                EntityType.COW, EntityType.GOAT, EntityType.HORSE, EntityType.PIG, EntityType.SHEEP, EntityType.CAMEL
        );

        this.tag(UP2EntityTags.COELACANTHUS_AVOIDS).add(
                DUNKLEOSTEUS.get(), PROGNATHODON.get(), EntityType.GUARDIAN, EntityType.DROWNED
        );
        this.tag(UP2EntityTags.LEPTICTIDIUM_AVOIDS).add(
                EntityType.OCELOT, EntityType.PIG, EntityType.CAT, EntityType.FOX, EntityType.WOLF
        );
        this.tag(UP2EntityTags.PRAEPUSA_AVOIDS).add(
                DUNKLEOSTEUS.get(), PROGNATHODON.get(), EntityType.GUARDIAN, EntityType.DROWNED
        );

        this.tag(UP2EntityTags.COELACANTHUS_NEVER_EATS).add(
                EntityType.ENDER_DRAGON
        );

        this.tag(UP2EntityTags.PACHYCEPHALOSAURUS_TARGETS_TO_KILL).add(
                EntityType.GOAT
        );

        // Update 5
        this.tag(UP2EntityTags.PSILOPTERUS_KICK_TARGETS).add(
                EntityType.CHICKEN, EntityType.RABBIT, EntityType.SLIME, EntityType.MAGMA_CUBE,
                PRAEPUSA.get(), LYSTROSAURUS.get(), TALPANAS.get(), LIVING_OOZE.get()
        );

        this.tag(UP2EntityTags.MEDIUM_PSILOPTERUS_PACK_TARGETS).add(
                EntityType.COW, EntityType.SHEEP, EntityType.PIG, EntityType.GOAT
        );
        this.tag(UP2EntityTags.LARGE_PSILOPTERUS_PACK_TARGETS).add(
                EntityType.LLAMA, EntityType.HORSE, EntityType.CAMEL
        );

        this.tag(UP2EntityTags.PALEOZOIC_MOBS).add(
                AEGIROCASSIS.get(),
                DUNKLEOSTEUS.get(),
                JAWLESS_FISH.get(),
                STETHACANTHUS.get(),
                TARTUOSTEUS.get(),
                DIPLOCAULUS.get(),
                HIBBERTOPTERUS.get(),
                LOBE_FINNED_FISH.get(),
                COELACANTHUS.get(),
                LYSTROSAURUS.get(),
                COTYLORHYNCHUS.get(),
                HYNERPETON.get(),
                BRONTOSCORPIO.get(),
                RHIZODUS.get(),
                ARTHROPLEURA.get(),
                AMMONITE.get(),
                CAMEROCERAS.get(),
                DIICTODON.get()
        );

        this.tag(UP2EntityTags.MESOZOIC_MOBS).add(
                CRYPTOCLIDUS.get(),
                DESMATOSUCHUS.get(),
                LYSTROSAURUS.get(),
                BRACHIOSAURUS.get(),
                KENTROSAURUS.get(),
                KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(),
                METRIORHYNCHUS.get(),
                PTERODACTYLUS.get(),
                CARNOTAURUS.get(),
                DROMAEOSAURUS.get(),
                KAPROSUCHUS.get(),
                MAJUNGASAURUS.get(),
                PROGNATHODON.get(),
                ONCHOPRISTIS.get(),
                PACHYCEPHALOSAURUS.get(),
                ULUGHBEGSAURUS.get(),
                ANTARCTOPELTA.get(),
                CONCAVENATOR.get(),
                ICHTHYOSAURUS.get(),
                LORRAINOSAURUS.get(),
                AMMONITE.get(),
                SHRINGASAURUS.get(),
                TUSOTEUTHIS.get(),
                LEEDSICHTHYS.get(),
                ANUROGNATHUS.get(),
                AUSTRORAPTOR.get(),
                PACHYRHINOSAURUS.get(),
                HESPERORNIS.get(),
                AQUILOLAMNA.get(),
                BANANOGMIUS.get(),
                HENODUS.get(),
                SALTOPUS.get()
        );

        this.tag(UP2EntityTags.CENOZOIC_MOBS).add(
                LEPTICTIDIUM.get(),
                PSILOPTERUS.get(),
                TELECREX.get(),
                PRAEPUSA.get(),
                MEGALANIA.get(),
                TALPANAS.get(),
                WOOLLY_MAMMOTH.get(),
                SPIKE_TOOTHED_SALMON.get(),
                GASTRIC_BROODING_FROG.get(),
                GIANT_CAMPANILE.get(),
                THYLACINE.get(),
                KING_LINGCOD.get(),
                DIRE_WOLF.get(),
                NIHOHAE.get()
        );

        // Update 6
        this.tag(UP2EntityTags.ARTHROPLEURA_CANT_CARRY).add(
                EntityType.ENDER_DRAGON, EntityType.WITHER, ARTHROPLEURA.get(), ARTHROPLEURA_PART.get()
        );

        this.tag(UP2EntityTags.CONCAVENATOR_TARGETS).add(
                EntityType.COW, EntityType.PIG, EntityType.RABBIT, EntityType.VILLAGER
        );
        this.tag(UP2EntityTags.MEDIUM_CONCAVENATOR_PACK_TARGETS).add(
                EntityType.CAMEL, DESMATOSUCHUS.get()
        );
        this.tag(UP2EntityTags.LARGE_CONCAVENATOR_PACK_TARGETS).add(
                COTYLORHYNCHUS.get()
        );
        this.tag(UP2EntityTags.CONCAVENATOR_AVOIDS).add(
                CARNOTAURUS.get()
        );

        this.tag(UP2EntityTags.THYLACINE_AVOIDS).add(
                EntityType.WOLF,
                EntityType.FOX
        );

        this.tag(UP2EntityTags.CRYPTOCLIDUS_TARGETS).add(
                EntityType.SQUID, EntityType.GLOW_SQUID, EntityType.COD, EntityType.TROPICAL_FISH
        );

        this.tag(UP2EntityTags.LORRAINOSAURUS_TARGETS).add(
                EntityType.VILLAGER, EntityType.COW, EntityType.GOAT,
                DUNKLEOSTEUS.get(), STETHACANTHUS.get()
        );
        this.tag(UP2EntityTags.LORRAINOSAURUS_CANT_GRAB).add(
                EntityType.ENDER_DRAGON, EntityType.WITHER, LORRAINOSAURUS.get()
        );

        this.tag(UP2EntityTags.UNAFFECTED_BY_PARALYSIS).add(
                EntityType.ENDER_DRAGON, EntityType.WITHER, BRONTOSCORPIO.get()
        );

        this.tag(UP2EntityTags.PROGNATHODON_TARGETS).add(
                EntityType.TURTLE, EntityType.DOLPHIN, EntityType.GOAT
        );
        this.tag(UP2EntityTags.PROGNATHODON_FIGHT_TARGETS).add(
                PROGNATHODON.get()
        );

        this.tag(UP2EntityTags.THYLACINE_TARGETS).add(
                EntityType.CHICKEN,
                EntityType.PARROT
        );

        this.tag(UP2EntityTags.GASTRIC_BROODING_FROG_TARGETS).add(
                EntityType.SLIME,
                EntityType.MAGMA_CUBE,
                EntityType.BEE,
                EntityType.SILVERFISH,
                DELITZSCHALA.get(),
                ZHANGSOLVA.get()
        );

        // Minecraft
        this.tag(EntityTypeTags.FROG_FOOD).add(
                DELITZSCHALA.get(),
                ZHANGSOLVA.get()
        );
    }
}
