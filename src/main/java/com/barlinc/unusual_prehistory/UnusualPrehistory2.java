package com.barlinc.unusual_prehistory;

import com.barlinc.unusual_prehistory.client.ClientProxy;
import com.barlinc.unusual_prehistory.datagen.client.UP2BlockstateProvider;
import com.barlinc.unusual_prehistory.datagen.client.UP2ItemModelProvider;
import com.barlinc.unusual_prehistory.datagen.client.UP2LanguageProvider;
import com.barlinc.unusual_prehistory.datagen.client.UP2SoundDefinitionsProvider;
import com.barlinc.unusual_prehistory.datagen.server.*;
import com.barlinc.unusual_prehistory.network.GiantCampanilePartPacket;
import com.barlinc.unusual_prehistory.network.MountedEntityKeyPacket;
import com.barlinc.unusual_prehistory.network.MultipartEntityPacket;
import com.barlinc.unusual_prehistory.network.ParticlePacket;
import com.barlinc.unusual_prehistory.registry.*;
import com.barlinc.unusual_prehistory.utils.UP2LoadedMods;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Mod(UnusualPrehistory2.MOD_ID)
public class UnusualPrehistory2 {

    public static final String MOD_ID = "unusual_prehistory";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CommonProxy PROXY = unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public UnusualPrehistory2(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::loadComplete);
        modEventBus.addListener(this::packetSetup);
        modEventBus.addListener(this::dataSetup);

        UP2Entities.ENTITY_TYPE.register(modEventBus);
        UP2Blocks.BLOCKS.register(modEventBus);
        UP2Items.ITEMS.register(modEventBus);
        UP2Fluids.FLUIDS.register(modEventBus);
        UP2Fluids.TYPES.register(modEventBus);
        UP2BlockEntities.BLOCK_ENTITIES.register(modEventBus);
        UP2MobEffects.MOB_EFFECTS.register(modEventBus);
        UP2MenuTypes.MENU_TYPE.register(modEventBus);
        UP2Recipes.RECIPE_TYPES.register(modEventBus);
        UP2Recipes.RECIPE_SERIALIZERS.register(modEventBus);
        UP2FeatureTypes.FEATURE_TYPES.register(modEventBus);
        UP2Trees.TREE_DECORATORS.register(modEventBus);
        UP2Trees.TRUNK_PLACERS.register(modEventBus);
        UP2Trees.FOLIAGE_PLACERS.register(modEventBus);
        UP2StructurePieces.STRUCTURE_PIECE_TYPES.register(modEventBus);
        UP2StructureProcessors.STRUCTURE_PROCESSOR_TYPES.register(modEventBus);
        UP2Structures.STRUCTURE_TYPES.register(modEventBus);
        UP2SoundEvents.SOUND_EVENTS.register(modEventBus);
        UP2Particles.PARTICLE_TYPES.register(modEventBus);
        UP2CriteriaTriggers.TRIGGERS.register(modEventBus);
        UnusualPrehistory2Tab.CREATIVE_TABS.register(modEventBus);

        modEventBus.addListener(UP2BlockEntities::addBlockEntities);
        modEventBus.addListener(UP2MobVariants::registerVariantRegistries);
        PROXY.commonInit();
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(UP2Compat::registerCompat);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(PROXY::clientInit);
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(UP2Fluids::postInit);
        event.enqueueWork(UP2LoadedMods::afterAllModsLoaded);
    }

    public void packetSetup(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MOD_ID).versioned("1.0.0").optional();

        // Client
        registrar.playToClient(ParticlePacket.TYPE, ParticlePacket.CODEC, ParticlePacket::handle);

        // Server
        registrar.playToServer(MountedEntityKeyPacket.TYPE, MountedEntityKeyPacket.CODEC, MountedEntityKeyPacket::handle);
        registrar.playToServer(MultipartEntityPacket.TYPE, MultipartEntityPacket.CODEC, MultipartEntityPacket::handle);
        registrar.playToServer(GiantCampanilePartPacket.TYPE, GiantCampanilePartPacket.CODEC, GiantCampanilePartPacket::handle);
    }

    private void dataSetup(GatherDataEvent data) {
        DataGenerator generator = data.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = data.getLookupProvider();
        ExistingFileHelper helper = data.getExistingFileHelper();

        boolean server = data.includeServer();

        UP2DatapackProvider datapackEntries = new UP2DatapackProvider(output, provider);
        generator.addProvider(server, datapackEntries);
        provider = datapackEntries.getRegistryProvider();

        UP2BlockTagProvider blockTags = new UP2BlockTagProvider(output, provider, helper);
        UP2FluidTagsProvider fluidTags = new UP2FluidTagsProvider(output, provider, helper);

        generator.addProvider(server, blockTags);
        generator.addProvider(server, fluidTags);

        generator.addProvider(server, new UP2ItemTagProvider(output, provider, blockTags.contentsGetter(), helper));
        generator.addProvider(server, new UP2EntityTagProvider(output, provider, helper));
        generator.addProvider(server, new UP2BiomeTagProvider(output, provider, helper));
        generator.addProvider(server, new UP2BannerPatternTagProvider(output, provider, helper));
        generator.addProvider(server, new UP2DamageTypeTagProvider(output, provider, helper));
        generator.addProvider(server, new UP2GameEventTagProvider(output, provider, helper));
        generator.addProvider(server, new UP2LootTableProvider(output, provider));
        generator.addProvider(server, new UP2RecipeProvider(output, provider));
        generator.addProvider(server, UP2AdvancementProvider.register(output, provider, helper));

        boolean client = data.includeClient();

        generator.addProvider(client, new UP2BlockstateProvider(output, helper));
        generator.addProvider(client, new UP2ItemModelProvider(data));
        generator.addProvider(client, new UP2SoundDefinitionsProvider(output, helper));
        generator.addProvider(client, new UP2LanguageProvider(data));
    }

    public static ResourceLocation modPrefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    private static <T> T unsafeRunForDist(Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        return switch (FMLEnvironment.dist) {
            case CLIENT -> clientTarget.get().get();
            case DEDICATED_SERVER -> serverTarget.get().get();
        };
    }
}

