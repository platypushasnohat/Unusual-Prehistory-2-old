package com.barl_inc.unusual_prehistory.datagen.server;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.tags.UP2BannerPatternTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.barl_inc.unusual_prehistory.registry.UP2BannerPatterns.*;

public class UP2BannerPatternTagProvider extends BannerPatternTagsProvider {

	public UP2BannerPatternTagProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, UnusualPrehistory2.MOD_ID, helper);
	}

	@Override
	public void addTags(@NotNull Provider provider) {

		this.tag(UP2BannerPatternTags.PALEOZOIC_BANNER_PATTERN).add(
                AEGIROCASSIS,
                COELACANTHUS,
                DIPLOCAULUS,
                DUNKLEOSTEUS,
                HIBBERTOPTERUS,
                JAWLESS_FISH,
                LOBE_FINNED_FISH,
                LYSTROSAURUS,
                STETHACANTHUS,
                TARTUOSTEUS
        );

        this.tag(UP2BannerPatternTags.MESOZOIC_BANNER_PATTERN).add(
                BRACHIOSAURUS,
                CARNOTAURUS,
                DESMATOSUCHUS,
                DROMAEOSAURUS,
                KAPROSUCHUS,
                KENTROSAURUS,
                KIMMERIDGEBRACHYPTERAESCHNIDIUM,
                LYSTROSAURUS,
                MAJUNGASAURUS,
                METRIORHYNCHUS,
                PROGNATHODON,
                ONCHOPRISTIS,
                PACHYCEPHALOSAURUS,
                PTERODACTYLUS,
                ULUGHBEGSAURUS
        );

        this.tag(UP2BannerPatternTags.CENOZOIC_BANNER_PATTERN).add(
                LEPTICTIDIUM,
                MEGALANIA,
                PRAEPUSA,
                PSILOPTERUS,
                TALPANAS,
                TELECREX
        );

        this.tag(UP2BannerPatternTags.OOZE_BANNER_PATTERN).add(
                OOZE
        );
	}
}