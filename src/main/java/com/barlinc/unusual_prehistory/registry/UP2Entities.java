package com.barlinc.unusual_prehistory.registry;

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
import com.barlinc.unusual_prehistory.entity.projectile.ThrowableEgg;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UP2Entities {

    public static List<DeferredHolder<EntityType<?>, ? extends EntityType<?>>> ENTITY_TRANSLATIONS = new ArrayList<>();

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(Registries.ENTITY_TYPE, UnusualPrehistory2.MOD_ID);

    // Paleozoic
    public static final DeferredHolder<EntityType<?>, EntityType<Aegirocassis>> AEGIROCASSIS = registerEntity("aegirocassis", Aegirocassis::new, MobCategory.WATER_CREATURE, builder -> builder.sized(3.5F, 3.9F).eyeHeight(1.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Arthropleura>> ARTHROPLEURA = registerEntity("arthropleura", Arthropleura::new, MobCategory.CREATURE, builder -> builder.sized(1.5F, 0.75F).eyeHeight(0.375F).clientTrackingRange(10).setShouldReceiveVelocityUpdates(true));
    public static final DeferredHolder<EntityType<?>, EntityType<ArthropleuraPart>> ARTHROPLEURA_PART = registerEntity("arthropleura_part", ArthropleuraPart::new, MobCategory.MISC, builder -> builder.sized(1.5F, 0.75F).clientTrackingRange(10).noSummon().setShouldReceiveVelocityUpdates(true));
    public static final DeferredHolder<EntityType<?>, EntityType<Brontoscorpio>> BRONTOSCORPIO = registerEntity("brontoscorpio", Brontoscorpio::new, MobCategory.CREATURE, builder -> builder.sized(1.15F, 0.9F).eyeHeight(0.45F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Cameroceras>> CAMEROCERAS = registerEntity("cameroceras", Cameroceras::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.6F, 8.5F).eyeHeight(4.25F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Coelacanthus>> COELACANTHUS = registerEntity("coelacanthus", Coelacanthus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(0.75F, 0.6F).eyeHeight(0.3F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Cotylorhynchus>> COTYLORHYNCHUS = registerEntity("cotylorhynchus", Cotylorhynchus::new, MobCategory.CREATURE, builder -> builder.sized(2.1F, 1.7F).eyeHeight(1.6F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Diictodon>> DIICTODON = registerEntity("diictodon", Diictodon::new, MobCategory.CREATURE, builder -> builder.sized(0.6F, 0.45F).eyeHeight(0.35F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Diplocaulus>> DIPLOCAULUS = registerEntity("diplocaulus", Diplocaulus::new, MobCategory.CREATURE, builder -> builder.sized(0.6F, 0.4F).eyeHeight(0.2F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Dunkleosteus>> DUNKLEOSTEUS = registerEntity("dunkleosteus", Dunkleosteus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(0.75F, 0.75F).eyeHeight(0.375F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Hibbertopterus>> HIBBERTOPTERUS = registerEntity("hibbertopterus", Hibbertopterus::new, MobCategory.CREATURE, builder -> builder.sized(2.75F, 1.7F).eyeHeight(1.6F).passengerAttachments(new Vec3(0.0F, 1.7F, -0.5F)).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Hynerpeton>> HYNERPETON = registerEntity("hynerpeton", Hynerpeton::new, MobCategory.CREATURE, builder -> builder.sized(0.8F, 0.5F).eyeHeight(0.3F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<JawlessFish>> JAWLESS_FISH = registerEntity("jawless_fish", JawlessFish::new, MobCategory.WATER_CREATURE, builder -> builder.sized(0.35F, 0.35F).eyeHeight(0.175F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<LobeFinnedFish>> LOBE_FINNED_FISH = registerEntity("lobe_finned_fish", LobeFinnedFish::new, MobCategory.WATER_CREATURE, builder -> builder.sized(0.75F, 0.75F).eyeHeight(0.375F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Rhizodus>> RHIZODUS = registerEntity("rhizodus", Rhizodus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.7F, 1.8F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Stethacanthus>> STETHACANTHUS = registerEntity("stethacanthus", Stethacanthus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(0.8F, 0.8F).eyeHeight(0.5F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Tartuosteus>> TARTUOSTEUS = registerEntity("tartuosteus", Tartuosteus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.2F, 0.5F).eyeHeight(0.5F).clientTrackingRange(10));

    // Mesozoic
    public static final DeferredHolder<EntityType<?>, EntityType<Antarctopelta>> ANTARCTOPELTA = registerEntity("antarctopelta", Antarctopelta::new, MobCategory.CREATURE, builder -> builder.sized(1.4F, 1.4F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Anurognathus>> ANUROGNATHUS = registerEntity("anurognathus", Anurognathus::new, MobCategory.CREATURE, builder -> builder.sized(0.9F, 0.575F).eyeHeight(0.475F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Aquilolamna>> AQUILOLAMNA = registerEntity("aquilolamna", Aquilolamna::new, MobCategory.WATER_CREATURE, builder -> builder.sized(2.5F, 0.7F).eyeHeight(0.35F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Austroraptor>> AUSTRORAPTOR = registerEntity("austroraptor", Austroraptor::new, MobCategory.CREATURE, builder -> builder.sized(0.9F, 2.3F).eyeHeight(2.2F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Bananogmius>> BANANOGMIUS = registerEntity("bananogmius", Bananogmius::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.25F, 1.25F).eyeHeight(0.625F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Brachiosaurus>> BRACHIOSAURUS = registerEntity("brachiosaurus", Brachiosaurus::new, MobCategory.CREATURE, builder -> builder.sized(5.5F, 9.25F).eyeHeight(9.0F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Carnotaurus>> CARNOTAURUS = registerEntity("carnotaurus", Carnotaurus::new, MobCategory.CREATURE, builder -> builder.sized(1.75F, 2.9F).eyeHeight(2.8F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Coelophysis>> COELOPHYSIS = registerEntity("coelophysis", Coelophysis::new, MobCategory.CREATURE, builder -> builder.sized(0.7F, 1.2F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Concavenator>> CONCAVENATOR = registerEntity("concavenator", Concavenator::new, MobCategory.CREATURE, builder -> builder.sized(1.5F, 2.3F).eyeHeight(2.2F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Cryptoclidus>> CRYPTOCLIDUS = registerEntity("cryptoclidus", Cryptoclidus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.7F, 1.25F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Desmatosuchus>> DESMATOSUCHUS = registerEntity("desmatosuchus", Desmatosuchus::new, MobCategory.CREATURE, builder -> builder.sized(1.3F, 1.2F).eyeHeight(0.8F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Dromaeosaurus>> DROMAEOSAURUS = registerEntity("dromaeosaurus", Dromaeosaurus::new, MobCategory.CREATURE, builder -> builder.sized(0.7F, 1.2F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Henodus>> HENODUS = registerEntity("henodus", Henodus::new, MobCategory.CREATURE, builder -> builder.sized(2.25F, 0.4F).eyeHeight(0.2F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Hesperornis>> HESPERORNIS = registerEntity("hesperornis", Hesperornis::new, MobCategory.WATER_CREATURE, builder -> builder.sized(0.8F, 1.9F).eyeHeight(1.7F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Ichthyosaurus>> ICHTHYOSAURUS = registerEntity("ichthyosaurus", Ichthyosaurus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.5F, 1.5F).eyeHeight(0.8F).clientTrackingRange(10).passengerAttachments(new Vec3(0.0F, 1.8F, -0.5F)));
    public static final DeferredHolder<EntityType<?>, EntityType<Kaprosuchus>> KAPROSUCHUS = registerEntity("kaprosuchus", Kaprosuchus::new, MobCategory.CREATURE, builder -> builder.sized(1.25F, 1.4F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Kentrosaurus>> KENTROSAURUS = registerEntity("kentrosaurus", Kentrosaurus::new, MobCategory.CREATURE, builder -> builder.sized(1.75F, 2.3F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Kimmeridgebrachypteraeschnidium>> KIMMERIDGEBRACHYPTERAESCHNIDIUM = registerEntity("kimmeridgebrachypteraeschnidium", Kimmeridgebrachypteraeschnidium::new, MobCategory.CREATURE, builder -> builder.sized(0.6F, 0.75F).eyeHeight(0.375F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Leedsichthys>> LEEDSICHTHYS = registerEntity("leedsichthys", Leedsichthys::new, MobCategory.WATER_CREATURE, builder -> builder.sized(6.0F, 4.5F).eyeHeight(2.25F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Lorrainosaurus>> LORRAINOSAURUS = registerEntity("lorrainosaurus", Lorrainosaurus::new, MobCategory.WATER_CREATURE, builder -> builder.sized(2.6F, 2.2F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Majungasaurus>> MAJUNGASAURUS = registerEntity("majungasaurus", Majungasaurus::new, MobCategory.CREATURE, builder -> builder.sized(1.2F, 1.9F).eyeHeight(1.8F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Metriorhynchus>> METRIORHYNCHUS = registerEntity("metriorhynchus", Metriorhynchus::new, MobCategory.CREATURE, builder -> builder.sized(1.3F, 1.2F).eyeHeight(0.7F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Onchopristis>> ONCHOPRISTIS = registerEntity("onchopristis", Onchopristis::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.2F, 0.5F).eyeHeight(0.5F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Pachycephalosaurus>> PACHYCEPHALOSAURUS = registerEntity("pachycephalosaurus", Pachycephalosaurus::new, MobCategory.CREATURE, builder -> builder.sized(0.8F, 1.3F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Pachyrhinosaurus>> PACHYRHINOSAURUS = registerEntity("pachyrhinosaurus", Pachyrhinosaurus::new, MobCategory.CREATURE, builder -> builder.sized(2.9F, 3.8F).eyeHeight(3.6F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Prognathodon>> PROGNATHODON = registerEntity("prognathodon", Prognathodon::new, MobCategory.WATER_CREATURE, builder -> builder.sized(2.8F, 2.3F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Pterodactylus>> PTERODACTYLUS = registerEntity("pterodactylus", Pterodactylus::new, MobCategory.CREATURE, builder -> builder.sized(0.6F, 0.6F).eyeHeight(0.4F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Saltopus>> SALTOPUS = registerEntity("saltopus", Saltopus::new, MobCategory.CREATURE, builder -> builder.sized(0.8F, 1.2F).eyeHeight(1.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Shringasaurus>> SHRINGASAURUS = registerEntity("shringasaurus", Shringasaurus::new, MobCategory.CREATURE, builder -> builder.sized(1.5F, 2.9F).eyeHeight(2.8F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Therizinosaurus>> THERIZINOSAURUS = registerEntity("therizinosaurus", Therizinosaurus::new, MobCategory.CREATURE, builder -> builder.sized(3.3F, 5.25F).eyeHeight(5.2F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Tusoteuthis>> TUSOTEUTHIS = registerEntity("tusoteuthis", Tusoteuthis::new, MobCategory.WATER_CREATURE, builder -> builder.sized(2.75F, 4.75F).eyeHeight(2.375F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Ulughbegsaurus>> ULUGHBEGSAURUS = registerEntity("ulughbegsaurus", Ulughbegsaurus::new, MobCategory.CREATURE, builder -> builder.sized(1.5F, 2.4F).eyeHeight(2.3F).clientTrackingRange(10));

    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> ANUROGNATHUS_EGG = registerEntity("anurognathus_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.ANUROGNATHUS_EGG, UP2Entities.ANUROGNATHUS::get), MobCategory.MISC, builder -> builder.sized(0.25F, 0.25F));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> AUSTRORAPTOR_EGG = registerEntity("austroraptor_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.AUSTRORAPTOR_EGG, UP2Entities.AUSTRORAPTOR::get), MobCategory.MISC, builder -> builder.sized(0.25F, 0.25F));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> DROMAEOSAURUS_EGG = registerEntity("dromaeosaurus_egg", (entityType, level) -> new ThrowableEgg(entityType, level, UP2Items.DROMAEOSAURUS_EGG, UP2Entities.DROMAEOSAURUS::get), MobCategory.MISC, builder -> builder.sized(0.25F, 0.25F));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> PTERODACTYLUS_EGG = registerEntity("pterodactylus_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.PTERODACTYLUS_EGG, UP2Entities.PTERODACTYLUS::get), MobCategory.MISC, builder -> builder.sized(0.25F, 0.25F));

    // Cenozoic
    public static final DeferredHolder<EntityType<?>, EntityType<DireWolf>> DIRE_WOLF = registerEntity("dire_wolf", DireWolf::new, MobCategory.CREATURE, builder -> builder.sized(1.15F, 2.15F).eyeHeight(2.05F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<GiantCampanile>> GIANT_CAMPANILE = registerEntity("giant_campanile", GiantCampanile::new, MobCategory.CREATURE, builder -> builder.sized(1.25F, 5.15F).eyeHeight(2.575F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<KingLingcod>> KING_LINGCOD = registerEntity("king_lingcod", KingLingcod::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.25F, 1.1F).eyeHeight(0.55F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Leptictidium>> LEPTICTIDIUM = registerEntity("leptictidium", Leptictidium::new, MobCategory.CREATURE, builder -> builder.sized(0.55F, 0.7F).eyeHeight(0.5F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Megalania>> MEGALANIA = registerEntity("megalania", Megalania::new, MobCategory.CREATURE, builder -> builder.sized(1.7F, 1.5F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Nihohae>> NIHOHAE = registerEntity("nihohae", Nihohae::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.125F, 0.8F).eyeHeight(0.4F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Praepusa>> PRAEPUSA = registerEntity("praepusa", Praepusa::new, MobCategory.CREATURE, builder -> builder.sized(0.6F, 0.5F).eyeHeight(0.25F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Psilopterus>> PSILOPTERUS = registerEntity("psilopterus", Psilopterus::new, MobCategory.CREATURE, builder -> builder.sized(0.6F, 1.4F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<SpikeToothedSalmon>> SPIKE_TOOTHED_SALMON = registerEntityNoLang("spike_toothed_salmon", SpikeToothedSalmon::new, MobCategory.WATER_CREATURE, builder -> builder.sized(1.3F, 1.3F).eyeHeight(0.65F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Talpanas>> TALPANAS = registerEntity("talpanas", Talpanas::new, MobCategory.CREATURE, builder -> builder.sized(0.7F, 0.9F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Telecrex>> TELECREX = registerEntity("telecrex", Telecrex::new, MobCategory.CREATURE, builder -> builder.sized(0.7F, 0.9F).eyeHeight(0.9F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<WoollyMammoth>> WOOLLY_MAMMOTH = registerEntity("woolly_mammoth", WoollyMammoth::new, MobCategory.CREATURE, builder -> builder.sized(3.6F, 4.9F).eyeHeight(4.7F).clientTrackingRange(10));

    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> PSILOPTERUS_EGG = registerEntity("psilopterus_egg", (entityType, level) -> new ThrowableEgg(entityType, level , UP2Items.PSILOPTERUS_EGG, UP2Entities.PSILOPTERUS::get), MobCategory.MISC, builder -> builder.sized(0.25F, 0.25F));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> TALPANAS_EGG = registerEntity("talpanas_egg", (entityType, level) -> new ThrowableEgg(entityType, level, UP2Items.TALPANAS_EGG, UP2Entities.TALPANAS::get), MobCategory.MISC, builder -> builder.sized(0.25F, 0.25F));
    public static final DeferredHolder<EntityType<?>, EntityType<ThrowableEgg>> TELECREX_EGG = registerEntity("telecrex_egg", (entityType, level) -> new ThrowableEgg(entityType, level, UP2Items.TELECREX_EGG, UP2Entities.TELECREX::get), MobCategory.MISC, builder -> builder.sized(0.25F, 0.25F));

    // Multi-Era
    public static final DeferredHolder<EntityType<?>, EntityType<Ammonite>> AMMONITE = registerEntity("ammonite", Ammonite::new, MobCategory.WATER_CREATURE, builder -> builder.sized(0.8F, 0.8F).eyeHeight(0.4F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Lystrosaurus>> LYSTROSAURUS = registerEntity("lystrosaurus", Lystrosaurus::new, MobCategory.CREATURE, builder -> builder.sized(0.9F, 0.9F).eyeHeight(0.9F).clientTrackingRange(10));

    // Recently Extinct
    public static final DeferredHolder<EntityType<?>, EntityType<GastricBroodingFrog>> GASTRIC_BROODING_FROG = registerEntityNoLang("gastric_brooding_frog", GastricBroodingFrog::new, MobCategory.CREATURE, builder -> builder.sized(0.8F, 0.8F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Thylacine>> THYLACINE = registerEntity("thylacine", Thylacine::new, MobCategory.CREATURE, builder -> builder.sized(0.7F, 0.8F).eyeHeight(0.7F).clientTrackingRange(10));

    // Ambient
    public static final DeferredHolder<EntityType<?>, EntityType<Ampyx>> AMPYX = registerEntity("ampyx", Ampyx::new, MobCategory.WATER_AMBIENT, builder -> builder.sized(0.4F, 0.2F).eyeHeight(0.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Delitzschala>> DELITZSCHALA = registerEntity("delitzschala", Delitzschala::new, MobCategory.AMBIENT, builder -> builder.sized(0.75F, 0.2F).eyeHeight(0.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Setapedites>> SETAPEDITES = registerEntity("setapedites", Setapedites::new, MobCategory.WATER_AMBIENT, builder -> builder.sized(0.4F, 0.2F).eyeHeight(0.1F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Zhangsolva>> ZHANGSOLVA = registerEntity("zhangsolva", Zhangsolva::new, MobCategory.AMBIENT, builder -> builder.sized(0.5F, 0.45F).eyeHeight(0.225F).clientTrackingRange(10));

    // Other
    public static final DeferredHolder<EntityType<?>, EntityType<Grug>> GRUG = registerEntity("grug", Grug::new, MobCategory.CREATURE, builder -> builder.sized(1.5F, 3.1F).eyeHeight(0.8F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Lingcod>> LINGCOD = registerEntity("lingcod", Lingcod::new, MobCategory.WATER_CREATURE, builder -> builder.sized(0.9375F, 0.825F).eyeHeight(0.5F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<LivingOoze>> LIVING_OOZE = registerEntity("living_ooze", LivingOoze::new, MobCategory.CREATURE, builder -> builder.sized(0.98F, 0.98F).eyeHeight(0.75F).clientTrackingRange(10));
    public static final DeferredHolder<EntityType<?>, EntityType<Unicorn>> UNICORN = registerEntity("unicorn", Unicorn::new, MobCategory.CREATURE, builder -> builder.sized(1.2F, 2.9F).eyeHeight(0.8F).clientTrackingRange(10));

    public static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> registerEntity(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, Consumer<EntityType.Builder<E>> builderConsumer) {
        DeferredHolder<EntityType<?>, EntityType<E>> entity = registerEntityNoLang(name, factory, entityClassification, builderConsumer);
        ENTITY_TRANSLATIONS.add(entity);
        return entity;
    }

    public static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> registerEntityNoLang(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, Consumer<EntityType.Builder<E>> builderConsumer) {
        return ENTITY_TYPE.register(name, () -> {
            var builder = EntityType.Builder.of(factory, entityClassification);
            builderConsumer.accept(builder);
            return builder.build(UnusualPrehistory2.MOD_ID + ":" + name);
        });
    }
}
