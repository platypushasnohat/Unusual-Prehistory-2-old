package com.barlinc.unusual_prehistory.events;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.mob.ambient.Ampyx;
import com.barlinc.unusual_prehistory.entity.mob.ambient.Delitzschala;
import com.barlinc.unusual_prehistory.entity.mob.ambient.Setapedites;
import com.barlinc.unusual_prehistory.entity.mob.ambient.Zhangsolva;
import com.barlinc.unusual_prehistory.entity.mob.cenozoic.*;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.*;
import com.barlinc.unusual_prehistory.entity.mob.multi_era.Ammonite;
import com.barlinc.unusual_prehistory.entity.mob.multi_era.Lystrosaurus;
import com.barlinc.unusual_prehistory.entity.mob.other.Grug;
import com.barlinc.unusual_prehistory.entity.mob.other.Lingcod;
import com.barlinc.unusual_prehistory.entity.mob.other.LivingOoze;
import com.barlinc.unusual_prehistory.entity.mob.other.Unicorn;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.*;
import com.barlinc.unusual_prehistory.entity.mob.recently_extinct.GastricBroodingFrog;
import com.barlinc.unusual_prehistory.entity.mob.recently_extinct.Thylacine;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        // Update 1
        event.put(UP2Entities.CARNOTAURUS.get(), Carnotaurus.createAttributes().build());
        event.put(UP2Entities.DIPLOCAULUS.get(), Diplocaulus.createAttributes().build());
        event.put(UP2Entities.DROMAEOSAURUS.get(), Dromaeosaurus.createAttributes().build());
        event.put(UP2Entities.DUNKLEOSTEUS.get(), Dunkleosteus.createAttributes().build());
        event.put(UP2Entities.JAWLESS_FISH.get(), JawlessFish.createAttributes().build());
        event.put(UP2Entities.KENTROSAURUS.get(), Kentrosaurus.createAttributes().build());
        event.put(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), Kimmeridgebrachypteraeschnidium.createAttributes().build());
        event.put(UP2Entities.MAJUNGASAURUS.get(), Majungasaurus.createAttributes().build());
        event.put(UP2Entities.MEGALANIA.get(), Megalania.createAttributes().build());
        event.put(UP2Entities.STETHACANTHUS.get(), Stethacanthus.createAttributes().build());
        event.put(UP2Entities.TALPANAS.get(), Talpanas.createAttributes().build());
        event.put(UP2Entities.TELECREX.get(), Telecrex.createAttributes().build());
        event.put(UP2Entities.UNICORN.get(), Unicorn.createAttributes().build());

        // Update 2
        event.put(UP2Entities.ONCHOPRISTIS.get(), Onchopristis.createAttributes().build());

        // Update 3
        event.put(UP2Entities.LIVING_OOZE.get(), LivingOoze.createAttributes().build());
        event.put(UP2Entities.METRIORHYNCHUS.get(), Metriorhynchus.createAttributes().build());
        event.put(UP2Entities.TARTUOSTEUS.get(), Tartuosteus.createAttributes().build());

        // Update 4
        event.put(UP2Entities.BRACHIOSAURUS.get(), Brachiosaurus.createAttributes().build());
        event.put(UP2Entities.COELACANTHUS.get(), Coelacanthus.createAttributes().build());
        event.put(UP2Entities.HIBBERTOPTERUS.get(), Hibbertopterus.createAttributes().build());
        event.put(UP2Entities.KAPROSUCHUS.get(), Kaprosuchus.createAttributes().build());
        event.put(UP2Entities.LEPTICTIDIUM.get(), Leptictidium.createAttributes().build());
        event.put(UP2Entities.LOBE_FINNED_FISH.get(), LobeFinnedFish.createAttributes().build());
        event.put(UP2Entities.LYSTROSAURUS.get(), Lystrosaurus.createAttributes().build());
        event.put(UP2Entities.PACHYCEPHALOSAURUS.get(), Pachycephalosaurus.createAttributes().build());
        event.put(UP2Entities.PRAEPUSA.get(), Praepusa.createAttributes().build());
        event.put(UP2Entities.PSILOPTERUS.get(), Psilopterus.createAttributes().build());
        event.put(UP2Entities.PTERODACTYLUS.get(), Pterodactylus.createAttributes().build());
        event.put(UP2Entities.ULUGHBEGSAURUS.get(), Ulughbegsaurus.createAttributes().build());

        // Update 5
        event.put(UP2Entities.AEGIROCASSIS.get(), Aegirocassis.createAttributes().build());
        event.put(UP2Entities.DELITZSCHALA.get(), Delitzschala.createAttributes().build());
        event.put(UP2Entities.DESMATOSUCHUS.get(), Desmatosuchus.createAttributes().build());
        event.put(UP2Entities.ZHANGSOLVA.get(), Zhangsolva.createAttributes().build());

        event.put(UP2Entities.GRUG.get(), Grug.createAttributes().build());

        // Update 6
        event.put(UP2Entities.AMMONITE.get(), Ammonite.createAttributes().build());
        event.put(UP2Entities.AMPYX.get(), Ampyx.createAttributes().build());
        event.put(UP2Entities.ANTARCTOPELTA.get(), Antarctopelta.createAttributes().build());
        event.put(UP2Entities.ANUROGNATHUS.get(), Anurognathus.createAttributes().build());
        event.put(UP2Entities.ARTHROPLEURA.get(), Arthropleura.createAttributes().build());
        event.put(UP2Entities.AUSTRORAPTOR.get(), Austroraptor.createAttributes().build());
        event.put(UP2Entities.BRONTOSCORPIO.get(), Brontoscorpio.createAttributes().build());
        event.put(UP2Entities.CONCAVENATOR.get(), Concavenator.createAttributes().build());
        event.put(UP2Entities.COTYLORHYNCHUS.get(), Cotylorhynchus.createAttributes().build());
        event.put(UP2Entities.CRYPTOCLIDUS.get(), Cryptoclidus.createAttributes().build());
        event.put(UP2Entities.HYNERPETON.get(), Hynerpeton.createAttributes().build());
        event.put(UP2Entities.GASTRIC_BROODING_FROG.get(), GastricBroodingFrog.createAttributes().build());
        event.put(UP2Entities.GIANT_CAMPANILE.get(), GiantCampanile.createAttributes().build());
        event.put(UP2Entities.ICHTHYOSAURUS.get(), Ichthyosaurus.createAttributes().build());
        event.put(UP2Entities.KING_LINGCOD.get(), KingLingcod.createAttributes().build());
        event.put(UP2Entities.LEEDSICHTHYS.get(), Leedsichthys.createAttributes().build());
        event.put(UP2Entities.LINGCOD.get(), Lingcod.createAttributes().build());
        event.put(UP2Entities.LORRAINOSAURUS.get(), Lorrainosaurus.createAttributes().build());
        event.put(UP2Entities.PROGNATHODON.get(), Prognathodon.createAttributes().build());
        event.put(UP2Entities.RHIZODUS.get(), Rhizodus.createAttributes().build());
        event.put(UP2Entities.SETAPEDITES.get(), Setapedites.createAttributes().build());
        event.put(UP2Entities.SHRINGASAURUS.get(), Shringasaurus.createAttributes().build());
        event.put(UP2Entities.SPIKE_TOOTHED_SALMON.get(), SpikeToothedSalmon.createAttributes().build());
        event.put(UP2Entities.THERIZINOSAURUS.get(), Therizinosaurus.createAttributes().build());
        event.put(UP2Entities.THYLACINE.get(), Thylacine.createAttributes().build());
        event.put(UP2Entities.TUSOTEUTHIS.get(), Tusoteuthis.createAttributes().build());
        event.put(UP2Entities.WOOLLY_MAMMOTH.get(), WoollyMammoth.createAttributes().build());
        event.put(UP2Entities.PACHYRHINOSAURUS.get(), Pachyrhinosaurus.createAttributes().build());
        event.put(UP2Entities.HESPERORNIS.get(), Hesperornis.createAttributes().build());
        event.put(UP2Entities.AQUILOLAMNA.get(), Aquilolamna.createAttributes().build());
        event.put(UP2Entities.CAMEROCERAS.get(), Cameroceras.createAttributes().build());
        event.put(UP2Entities.BANANOGMIUS.get(), Bananogmius.createAttributes().build());
        event.put(UP2Entities.COELOPHYSIS.get(), Coelophysis.createAttributes().build());
        event.put(UP2Entities.DIRE_WOLF.get(), DireWolf.createAttributes().build());
        event.put(UP2Entities.HENODUS.get(), Henodus.createAttributes().build());
        event.put(UP2Entities.SALTOPUS.get(), Saltopus.createAttributes().build());
        event.put(UP2Entities.DIICTODON.get(), Diictodon.createAttributes().build());
        event.put(UP2Entities.NIHOHAE.get(), Nihohae.createAttributes().build());
    }
}