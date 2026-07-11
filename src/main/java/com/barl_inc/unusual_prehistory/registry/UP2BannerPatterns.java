package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public final class UP2BannerPatterns {
    
    // Update 1
    public static final ResourceKey<BannerPattern> CARNOTAURUS = create("carnotaurus");
    public static final ResourceKey<BannerPattern> DIPLOCAULUS = create("diplocaulus");
    public static final ResourceKey<BannerPattern> DROMAEOSAURUS = create("dromaeosaurus");
    public static final ResourceKey<BannerPattern> DUNKLEOSTEUS = create("dunkleosteus");
    public static final ResourceKey<BannerPattern> JAWLESS_FISH = create("jawless_fish");
    public static final ResourceKey<BannerPattern> KENTROSAURUS = create("kentrosaurus");
    public static final ResourceKey<BannerPattern> KIMMERIDGEBRACHYPTERAESCHNIDIUM = create("kimmeridgebrachypteraeschnidium");
    public static final ResourceKey<BannerPattern> MAJUNGASAURUS = create("majungasaurus");
    public static final ResourceKey<BannerPattern> MEGALANIA = create("megalania");
    public static final ResourceKey<BannerPattern> STETHACANTHUS = create("stethacanthus");
    public static final ResourceKey<BannerPattern> TALPANAS = create("talpanas");
    public static final ResourceKey<BannerPattern> TELECREX = create("telecrex");

    // Update 2
    public static final ResourceKey<BannerPattern> ONCHOPRISTIS = create("onchopristis");

    // Update 3
    public static final ResourceKey<BannerPattern> OOZE = create("ooze");
    public static final ResourceKey<BannerPattern> METRIORHYNCHUS = create("metriorhynchus");
    public static final ResourceKey<BannerPattern> TARTUOSTEUS = create("tartuosteus");

    // Update 4
    public static final ResourceKey<BannerPattern> BRACHIOSAURUS = create("brachiosaurus");
    public static final ResourceKey<BannerPattern> COELACANTHUS = create("coelacanthus");
    public static final ResourceKey<BannerPattern> HIBBERTOPTERUS = create("hibbertopterus");
    public static final ResourceKey<BannerPattern> KAPROSUCHUS = create("kaprosuchus");
    public static final ResourceKey<BannerPattern> LEPTICTIDIUM = create("leptictidium");
    public static final ResourceKey<BannerPattern> LOBE_FINNED_FISH = create("lobe_finned_fish");
    public static final ResourceKey<BannerPattern> LYSTROSAURUS = create("lystrosaurus");
    public static final ResourceKey<BannerPattern> PACHYCEPHALOSAURUS = create("pachycephalosaurus");
    public static final ResourceKey<BannerPattern> PRAEPUSA = create("praepusa");
    public static final ResourceKey<BannerPattern> PTERODACTYLUS = create("pterodactylus");
    public static final ResourceKey<BannerPattern> ULUGHBEGSAURUS = create("ulughbegsaurus");

    // Update 5
    public static final ResourceKey<BannerPattern> AEGIROCASSIS = create("aegirocassis");
    public static final ResourceKey<BannerPattern> DESMATOSUCHUS = create("desmatosuchus");
    public static final ResourceKey<BannerPattern> PSILOPTERUS = create("psilopterus");

    // Update 6
    public static final ResourceKey<BannerPattern> PROGNATHODON = create("prognathodon");

    public static void bootstrap(BootstrapContext<BannerPattern> context) {
        // Update 1
        register(context, CARNOTAURUS);
        register(context, DIPLOCAULUS);
        register(context, DROMAEOSAURUS);
        register(context, DUNKLEOSTEUS);
        register(context, JAWLESS_FISH);
        register(context, KENTROSAURUS);
        register(context, KIMMERIDGEBRACHYPTERAESCHNIDIUM);
        register(context, MAJUNGASAURUS);
        register(context, MEGALANIA);
        register(context, STETHACANTHUS);
        register(context, TALPANAS);
        register(context, TELECREX);

        // Update 2
        register(context, ONCHOPRISTIS);

        // Update 3
        register(context, OOZE);
        register(context, METRIORHYNCHUS);
        register(context, TARTUOSTEUS);

        // Update 4
        register(context, BRACHIOSAURUS);
        register(context, COELACANTHUS);
        register(context, HIBBERTOPTERUS);
        register(context, KAPROSUCHUS);
        register(context, LEPTICTIDIUM);
        register(context, LOBE_FINNED_FISH);
        register(context, LYSTROSAURUS);
        register(context, PACHYCEPHALOSAURUS);
        register(context, PRAEPUSA);
        register(context, PTERODACTYLUS);
        register(context, ULUGHBEGSAURUS);

        // Update 5
        register(context, AEGIROCASSIS);
        register(context, DESMATOSUCHUS);
        register(context, PSILOPTERUS);

        // Update 6
        register(context, PROGNATHODON);
    }

    private static ResourceKey<BannerPattern> create(String name) {
        return ResourceKey.create(Registries.BANNER_PATTERN, UnusualPrehistory2.modPrefix(name));
    }

    public static void register(BootstrapContext<BannerPattern> context, ResourceKey<BannerPattern> resourceKey) {
        context.register(resourceKey, new BannerPattern(resourceKey.location(), "block.minecraft.banner." + resourceKey.location().toShortLanguageKey()));
    }
}