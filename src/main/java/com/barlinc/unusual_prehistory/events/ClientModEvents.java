package com.barlinc.unusual_prehistory.events;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.inventory.TransmogrifierScreen;
import com.barlinc.unusual_prehistory.client.models.entity.mob.ambient.AmpyxModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.ambient.DelitzschalaModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.ambient.SetapeditesModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.ambient.ZhangsolvaModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.cenozoic.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.multi_era.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.other.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.recently_extinct.GastricBroodingFrogModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.recently_extinct.GastricBroodingFrogletModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.recently_extinct.ThylacineModel;
import com.barlinc.unusual_prehistory.client.particles.*;
import com.barlinc.unusual_prehistory.client.renderer.block.PlushieBlockRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.layers.UP2PotionEffectLayer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.ambient.AmpyxRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.ambient.DelitzschalaRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.ambient.SetapeditesRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.ambient.ZhangsolvaRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.cenozoic.*;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic.*;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.multi_era.AmmoniteRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.multi_era.LystrosaurusRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.other.GrugRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.other.LingcodRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.other.LivingOozeRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.other.UnicornRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.paleozoic.*;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.recently_extinct.GastricBroodingFrogRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.recently_extinct.ThylacineRenderer;
import com.barlinc.unusual_prehistory.registry.*;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import java.util.List;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(UP2ItemProperties::registerItemProperties);
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(UP2MenuTypes.TRANSMOGRIFIER.get(), TransmogrifierScreen::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(UP2Particles.GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
        event.registerSpriteSet(UP2Particles.GOLDEN_GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
        event.registerSpriteSet(UP2Particles.EEPY.get(), EepyParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.OOZE_BUBBLE.get(), OutOfWaterBubbleParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.TAR_BUBBLE.get(), OutOfWaterBubbleParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.GOLDEN_HEART.get(), GrowingHeartParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.IMPACT_STUN.get(), ImpactStunParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.OUT_OF_WATER_BUBBLE.get(), OutOfWaterBubbleParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.SWEET_GROG.get(), SpellParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.FOUL_GROG.get(), SpellParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.SAND_SNORT.get(), SandSnortParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.TUSOTEUTHIS_FLASH.get(), FlashParticle.TusoteuthisProvider::new);
        event.registerSpriteSet(UP2Particles.STUN.get(), StunParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.DAZZLE.get(), LitSpellParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(UP2BlockEntities.PLUSHIE_BLOCK_ENTITY.get(), PlushieBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Paleozoic
        event.registerEntityRenderer(UP2Entities.AEGIROCASSIS.get(), AegirocassisRenderer::new);
        event.registerEntityRenderer(UP2Entities.ARTHROPLEURA.get(), ArthropleuraRenderer::new);
        event.registerEntityRenderer(UP2Entities.ARTHROPLEURA_PART.get(), ArthropleuraPartRenderer::new);
        event.registerEntityRenderer(UP2Entities.BRONTOSCORPIO.get(), BrontoscorpioRenderer::new);
        event.registerEntityRenderer(UP2Entities.CAMEROCERAS.get(), CamerocerasRenderer::new);
        event.registerEntityRenderer(UP2Entities.COELACANTHUS.get(), CoelacanthusRenderer::new);
        event.registerEntityRenderer(UP2Entities.COTYLORHYNCHUS.get(), CotylorhynchusRenderer::new);
        event.registerEntityRenderer(UP2Entities.DIICTODON.get(), DiictodonRenderer::new);
        event.registerEntityRenderer(UP2Entities.DIPLOCAULUS.get(), DiplocaulusRenderer::new);
        event.registerEntityRenderer(UP2Entities.DUNKLEOSTEUS.get(), DunkleosteusRenderer::new);
        event.registerEntityRenderer(UP2Entities.HIBBERTOPTERUS.get(), HibbertopterusRenderer::new);
        event.registerEntityRenderer(UP2Entities.HYNERPETON.get(), HynerpetonRenderer::new);
        event.registerEntityRenderer(UP2Entities.JAWLESS_FISH.get(), JawlessFishRenderer::new);
        event.registerEntityRenderer(UP2Entities.LOBE_FINNED_FISH.get(), LobeFinnedFishRenderer::new);
        event.registerEntityRenderer(UP2Entities.RHIZODUS.get(), RhizodusRenderer::new);
        event.registerEntityRenderer(UP2Entities.STETHACANTHUS.get(), StethacanthusRenderer::new);
        event.registerEntityRenderer(UP2Entities.TARTUOSTEUS.get(), TartuosteusRenderer::new);

        // Mesozoic
        event.registerEntityRenderer(UP2Entities.ANTARCTOPELTA.get(), AntarctopeltaRenderer::new);
        event.registerEntityRenderer(UP2Entities.ANUROGNATHUS.get(), AnurognathusRenderer::new);
        event.registerEntityRenderer(UP2Entities.AQUILOLAMNA.get(), AquilolamnaRenderer::new);
        event.registerEntityRenderer(UP2Entities.AUSTRORAPTOR.get(), AustroraptorRenderer::new);
        event.registerEntityRenderer(UP2Entities.BANANOGMIUS.get(), BananogmiusRenderer::new);
        event.registerEntityRenderer(UP2Entities.BRACHIOSAURUS.get(), BrachiosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.CARNOTAURUS.get(), CarnotaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.COELOPHYSIS.get(), CoelophysisRenderer::new);
        event.registerEntityRenderer(UP2Entities.CONCAVENATOR.get(), ConcavenatorRenderer::new);
        event.registerEntityRenderer(UP2Entities.CRYPTOCLIDUS.get(), CryptoclidusRenderer::new);
        event.registerEntityRenderer(UP2Entities.DESMATOSUCHUS.get(), DesmatosuchusRenderer::new);
        event.registerEntityRenderer(UP2Entities.DIMORPHODON.get(), DimorphodonRenderer::new);
        event.registerEntityRenderer(UP2Entities.DROMAEOSAURUS.get(), DromaeosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.HENODUS.get(), HenodusRenderer::new);
        event.registerEntityRenderer(UP2Entities.HESPERORNIS.get(), HesperornisRenderer::new);
        event.registerEntityRenderer(UP2Entities.ICHTHYOSAURUS.get(), IchthyosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.KAPROSUCHUS.get(), KaprosuchusRenderer::new);
        event.registerEntityRenderer(UP2Entities.KENTROSAURUS.get(), KentrosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), KimmeridgebrachypteraeschnidiumRenderer::new);
        event.registerEntityRenderer(UP2Entities.LEEDSICHTHYS.get(), LeedsichthysRenderer::new);
        event.registerEntityRenderer(UP2Entities.LORRAINOSAURUS.get(), LorrainosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.MAJUNGASAURUS.get(), MajungasaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.METRIORHYNCHUS.get(), MetriorhynchusRenderer::new);
        event.registerEntityRenderer(UP2Entities.ONCHOPRISTIS.get(), OnchopristisRenderer::new);
        event.registerEntityRenderer(UP2Entities.PACHYCEPHALOSAURUS.get(), PachycephalosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.PACHYRHINOSAURUS.get(), PachyrhinosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.PROGNATHODON.get(), PrognathodonRenderer::new);
        event.registerEntityRenderer(UP2Entities.PTERODACTYLUS.get(), PterodactylusRenderer::new);
        event.registerEntityRenderer(UP2Entities.SALTOPUS.get(), SaltopusRenderer::new);
        event.registerEntityRenderer(UP2Entities.SHRINGASAURUS.get(), ShringasaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.THERIZINOSAURUS.get(), TherizinosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.TUSOTEUTHIS.get(), TusoteuthisRenderer::new);
        event.registerEntityRenderer(UP2Entities.ULUGHBEGSAURUS.get(), UlughbegsaurusRenderer::new);

        event.registerEntityRenderer(UP2Entities.ANUROGNATHUS_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UP2Entities.AUSTRORAPTOR_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UP2Entities.DROMAEOSAURUS_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UP2Entities.PTERODACTYLUS_EGG.get(), ThrownItemRenderer::new);

        // Cenozoic
        event.registerEntityRenderer(UP2Entities.DIRE_WOLF.get(), DireWolfRenderer::new);
        event.registerEntityRenderer(UP2Entities.GIANT_CAMPANILE.get(), GiantCampanileRenderer::new);
        event.registerEntityRenderer(UP2Entities.KING_LINGCOD.get(), KingLingcodRenderer::new);
        event.registerEntityRenderer(UP2Entities.LEPTICTIDIUM.get(), LeptictidiumRenderer::new);
        event.registerEntityRenderer(UP2Entities.MEGALANIA.get(), MegalaniaRenderer::new);
        event.registerEntityRenderer(UP2Entities.NIHOHAE.get(), NihohaeRenderer::new);
        event.registerEntityRenderer(UP2Entities.PRAEPUSA.get(), PraepusaRenderer::new);
        event.registerEntityRenderer(UP2Entities.PSILOPTERUS.get(), PsilopterusRenderer::new);
        event.registerEntityRenderer(UP2Entities.SPIKE_TOOTHED_SALMON.get(), SpikeToothedSalmonRenderer::new);
        event.registerEntityRenderer(UP2Entities.TALPANAS.get(), TalpanasRenderer::new);
        event.registerEntityRenderer(UP2Entities.TELECREX.get(), TelecrexRenderer::new);
        event.registerEntityRenderer(UP2Entities.WOOLLY_MAMMOTH.get(), WoollyMammothRenderer::new);

        event.registerEntityRenderer(UP2Entities.PSILOPTERUS_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UP2Entities.TALPANAS_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UP2Entities.TELECREX_EGG.get(), ThrownItemRenderer::new);

        // Multi-Era
        event.registerEntityRenderer(UP2Entities.AMMONITE.get(), AmmoniteRenderer::new);
        event.registerEntityRenderer(UP2Entities.LYSTROSAURUS.get(), LystrosaurusRenderer::new);

        // Recently Extinct
        event.registerEntityRenderer(UP2Entities.GASTRIC_BROODING_FROG.get(), GastricBroodingFrogRenderer::new);
        event.registerEntityRenderer(UP2Entities.THYLACINE.get(), ThylacineRenderer::new);

        // Ambient
        event.registerEntityRenderer(UP2Entities.AMPYX.get(), AmpyxRenderer::new);
        event.registerEntityRenderer(UP2Entities.DELITZSCHALA.get(), DelitzschalaRenderer::new);
        event.registerEntityRenderer(UP2Entities.SETAPEDITES.get(), SetapeditesRenderer::new);
        event.registerEntityRenderer(UP2Entities.ZHANGSOLVA.get(), ZhangsolvaRenderer::new);

        // Other
        event.registerEntityRenderer(UP2Entities.GRUG.get(), GrugRenderer::new);
        event.registerEntityRenderer(UP2Entities.LINGCOD.get(), LingcodRenderer::new);
        event.registerEntityRenderer(UP2Entities.LIVING_OOZE.get(), LivingOozeRenderer::new);
        event.registerEntityRenderer(UP2Entities.UNICORN.get(), UnicornRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Paleozoic
        event.registerLayerDefinition(UP2ModelLayers.AEGIROCASSIS, AegirocassisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AEGIROCASSIS_BABY, AegirocassisBabyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ARTHROPLEURA_HEAD, ArthropleuraHeadModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ARTHROPLEURA_BODY, ArthropleuraBodyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ARTHROPLEURA_TAIL, ArthropleuraTailModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.BRONTOSCORPIO, BrontoscorpioModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.CAMEROCERAS, CamerocerasModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.COELACANTHUS, CoelacanthusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.COTYLORHYNCHUS, CotylorhynchusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIICTODON, DiictodonModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_TIGER, DiplocaulusTigerModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_SWAMPY, DiplocaulusSwampyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_MUDDY, DiplocaulusMuddyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_DWARF, DiplocaulusDwarfModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_LARGE, DunkleosteusLargeModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_MEDIUM, DunkleosteusMediumModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_SMALL, DunkleosteusSmallModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.HIBBERTOPTERUS, HibbertopterusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.HYNERPETON, HynerpetonModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_ARANDASPIS, JawlessFishArandaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_CEPHALASPIS, JawlessFishCephalaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_DORYASPIS, JawlessFishDoryaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_FURACACAUDA, JawlessFishFurcacaudaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_SACABAMBASPIS, JawlessFishSacabambaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_ALLENYPTERUS, LobeFinnedFishAllenypterusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_EUSTHENOPTERON, LobeFinnedFishEusthenopteronModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_GOOLOOGONGIA, LobeFinnedFishGooloogongiaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_LACCOGNATHUS, LobeFinnedFishLaccognathusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_SCAUMENACIA, LobeFinnedFishScaumenaciaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.RHIZODUS, RhizodusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.STETHACANTHUS, StethacanthusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.TARTUOSTEUS, TartuosteusModel::createBodyLayer);

        // Mesozoic
        event.registerLayerDefinition(UP2ModelLayers.ANTARCTOPELTA, AntarctopeltaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ANUROGNATHUS, AnurognathusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AQUILOLAMNA, AquilolamnaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AUSTRORAPTOR, AustroraptorModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.BANANOGMIUS, BananogmiusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.BRACHIOSAURUS, BrachiosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.BRACHIOSAURUS_BABY, BrachiosaurusBabyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.CARNOTAURUS, CarnotaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.COELOPHYSIS, CoelophysisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.CONCAVENATOR, ConcavenatorModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.CRYPTOCLIDUS, CryptoclidusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DESMATOSUCHUS, DesmatosuchusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIMORPHODON, DimorphodonModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DROMAEOSAURUS, DromaeosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.HENODUS, HenodusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.HESPERORNIS, HesperornisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ICHTHYOSAURUS, IchthyosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KAPROSUCHUS, KaprosuchusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KENTROSAURUS, KentrosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KIMMERIDGEBRACHYPTERAESCHNIDIUM, KimmeridgebrachypteraeschnidiumModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH, KimmeridgebrachypteraeschnidiumNymphModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LEEDSICHTHYS, LeedsichthysModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LEEDSICHTHYS_BABY, LeedsichthysBabyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LORRAINOSAURUS, LorrainosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.MAJUNGASAURUS, MajungasaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.METRIORHYNCHUS, MetriorhynchusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ONCHOPRISTIS, OnchopristisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PACHYCEPHALOSAURUS, PachycephalosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PACHYRHINOSAURUS, PachyrhinosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PROGNATHODON, PrognathodonModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PTERODACTYLUS, PterodactylusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.SALTOPUS, SaltopusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.SHRINGASAURUS, ShringasaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.THERIZINOSAURUS, TherizinosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.THERIZINOSAURUS_BABY, TherizinosaurusBabyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.TUSOTEUTHIS, TusoteuthisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ULUGHBEGSAURUS, UlughbegsaurusModel::createBodyLayer);

        // Cenozoic
        event.registerLayerDefinition(UP2ModelLayers.DIRE_WOLF, DireWolfModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.GIANT_CAMPANILE, GiantCampanileModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KING_LINGCOD, KingLingcodModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LEPTICTIDIUM, LeptictidiumModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.MEGALANIA, MegalaniaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.NIHOHAE, NihohaeModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PRAEPUSA, PraepusaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PSILOPTERUS, PsilopterusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.SPIKE_TOOTHED_SALMON, SpikeToothedSalmonModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.TALPANAS, TalpanasModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.TELECREX, TelecrexModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.WOOLLY_MAMMOTH, WoollyMammothModel::createBodyLayer);

        // Multi-Era
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_CRIOCERATITES, AmmoniteCrioceratitesModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_HOPLITES, AmmoniteHoplitesModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_NOSTOCERAS, AmmoniteNostocerasModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_PINACOCERAS, AmmonitePinacocerasModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_TROPITES, AmmoniteTropitesModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LYSTROSAURUS, LystrosaurusModel::createBodyLayer);

        // Recentl Extinct
        event.registerLayerDefinition(UP2ModelLayers.GASTRIC_BROODING_FROG, GastricBroodingFrogModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.GASTRIC_BROODING_FROGLET, GastricBroodingFrogletModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.THYLACINE, ThylacineModel::createBodyLayer);

        // Ambient
        event.registerLayerDefinition(UP2ModelLayers.AMPYX, AmpyxModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DELITZSCHALA, DelitzschalaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.SETAPEDITES, SetapeditesModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ZHANGSOLVA, ZhangsolvaModel::createBodyLayer);

        // Other
        event.registerLayerDefinition(UP2ModelLayers.GRUG, GrugModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LINGCOD, LingcodModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LIVING_OOZE, LivingOozeModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.UNICORN, UnicornModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.UNICORN_SKELETON, UnicornSkeletonModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, world, pos, tintIndex) -> {
                    if (world == null || pos == null) {
                        return FoliageColor.getDefaultColor();
                    }
                    return BiomeColors.getAverageFoliageColor(world, pos);
                },
                UP2Blocks.CLADOPHLEBIS.get(),
                UP2Blocks.POTTED_CLADOPHLEBIS.get()
        );
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
                    BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
                    return event.getBlockColors().getColor(blockstate, null, null, tintIndex);
                },
                UP2Blocks.CLADOPHLEBIS.get()
        );
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        List<EntityType<? extends LivingEntity>> entityTypes = ImmutableList.copyOf(BuiltInRegistries.ENTITY_TYPE.stream().filter(DefaultAttributes::hasSupplier).map(entityType -> (EntityType<? extends LivingEntity>) entityType).collect(Collectors.toList()));
        entityTypes.forEach(entityType -> addLayerIfApplicable(entityType, event));
        for (PlayerSkin.Model modelType : event.getSkins()) {
            EntityRenderer<? extends Player> renderer = event.getSkin(modelType);
            if (renderer instanceof LivingEntityRenderer livingRenderer) {
                livingRenderer.addLayer(new UP2PotionEffectLayer(livingRenderer));
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addLayerIfApplicable(EntityType<? extends LivingEntity> entityType, EntityRenderersEvent.AddLayers event) {
        if (entityType == EntityType.ENDER_DRAGON) {
            return;
        }
        try {
            EntityRenderer<?> renderer = event.getRenderer(entityType);
            if (renderer instanceof LivingEntityRenderer livingRenderer) {
                livingRenderer.addLayer(new UP2PotionEffectLayer(livingRenderer));
            }
        } catch (Exception e) {
            UnusualPrehistory2.LOGGER.warn("Could not apply render layer to {}", BuiltInRegistries.ENTITY_TYPE.getKey(entityType));
        }
    }
}