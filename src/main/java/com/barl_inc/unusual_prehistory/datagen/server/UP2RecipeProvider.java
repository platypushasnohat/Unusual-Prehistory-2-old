package com.barl_inc.unusual_prehistory.datagen.server;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.datagen.custom.TransmogrificationRecipeBuilder;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

import static com.barl_inc.unusual_prehistory.registry.UP2Blocks.*;

public class UP2RecipeProvider extends RecipeProvider {

    public UP2RecipeProvider(PackOutput output, CompletableFuture<Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        // Update 1
        transmogrification(output, UP2Items.FURY_FOSSIL, CARNOTAURUS_EGG, 2400, 1.5F);
        transmogrification(output, UP2Items.BOOMERANG_FOSSIL, DIPLOCAULUS_EGGS, 1200, 1.0F);
        transmogrification(output, UP2Items.RUNNER_FOSSIL, UP2Items.DROMAEOSAURUS_EGG, 1200, 1.0F);
        transmogrification(output, UP2Items.GUILLOTINE_FOSSIL, DUNKLEOSTEUS_SAC, 2400, 1.5F);
        transmogrification(output, UP2Items.JAWLESS_FOSSIL, JAWLESS_FISH_ROE, 800, 0.5F);
        transmogrification(output, UP2Items.IMPERATIVE_FOSSIL, KIMMERIDGEBRACHYPTERAESCHNIDIUM_EGGS, 1200, 1.0F);
        transmogrification(output, UP2Items.PRICKLY_FOSSIL, KENTROSAURUS_EGG, 2400, 1.5F);
        transmogrification(output, UP2Items.RUGOSE_FOSSIL, MAJUNGASAURUS_EGG, 2400, 1.5F);
        transmogrification(output, UP2Items.THERMAL_FOSSIL, MEGALANIA_EGG, 2400, 1.5F);
        transmogrification(output, UP2Items.ANVIL_FOSSIL, STETHACANTHUS_SAC, 1200, 1.0F);
        transmogrification(output, UP2Items.AGED_FEATHER, UP2Items.TALPANAS_EGG, 1200, 1.0F);
        transmogrification(output, UP2Items.PLUMAGE_FOSSIL, UP2Items.TELECREX_EGG, 1200, 1.0F);

        // Update 2
        transmogrification(output, UP2Items.SAW_FOSSIL, ONCHOPRISTIS_SAC, 2400, 1.5F);

        // Update 3
        transmogrification(output, UP2Items.MELTDOWN_FOSSIL, UP2Items.METRIORHYNCHUS_EMBRYO, 2400, 1.5F);
        transmogrification(output, UP2Items.MOSSY_FOSSIL, TARTUOSTEUS_ROE, 1200, 1.0F);

        // Update 4
        transmogrification(output, UP2Items.ARM_FOSSIL, BRACHIOSAURUS_EGG, 3600, 2.0F);
        transmogrification(output, UP2Items.GLUTTONOUS_FOSSIL, COELACANTHUS_ROE, 1200, 1.0F);
        transmogrification(output, UP2Items.PLOW_FOSSIL, HIBBERTOPTERUS_EGGS, 2400, 1.5F);
        transmogrification(output, UP2Items.BOAR_TOOTH_FOSSIL, KAPROSUCHUS_EGG, 2400, 1.5F);
        transmogrification(output, UP2Items.TRUNK_MOUSE_FOSSIL, UP2Items.LEPTICTIDIUM_EMBRYO, 1200, 1.0F);
        transmogrification(output, UP2Items.FISH_FOSSIL, LOBE_FINNED_FISH_ROE, 800, 0.5F);
        transmogrification(output, UP2Items.IMPERVIOUS_FOSSIL, LYSTROSAURUS_EGG, 1200, 1.0F);
        transmogrification(output, UP2Items.CRANIUM_FOSSIL, PACHYCEPHALOSAURUS_EGG, 1200, 1.0F);
        transmogrification(output, UP2Items.FLIPPER_FOSSIL, UP2Items.PRAEPUSA_EMBRYO, 1200, 1.0F);
        transmogrification(output, UP2Items.WING_FOSSIL, UP2Items.PTERODACTYLUS_EGG, 800, 0.5F);
        transmogrification(output, UP2Items.DUBIOUS_FOSSIL, ULUGHBEGSAURUS_EGG, 2400, 1.5F);

        // Update 5
        transmogrification(output, UP2Items.BRISTLE_FOSSIL, AEGIROCASSIS_EGGS, 3600, 2.0F);
        transmogrification(output, UP2Items.FLAT_BACK_FOSSIL, DESMATOSUCHUS_EGG, 1200, 1.0F);
        transmogrification(output, UP2Items.CROOKED_BEAK_FOSSIL, UP2Items.PSILOPTERUS_EGG, 1200, 1.0F);

        // Update 6
        transmogrification(output, UP2Items.SPIRAL_FOSSIL, AMMONITE_EGGS, 800, 0.5F);
        transmogrification(output, UP2Items.GRACILE_FOSSIL, UP2Items.AUSTRORAPTOR_EGG, 1200, 1.0F);
        transmogrification(output, UP2Items.THUNDEROUS_FOSSIL, UP2Items.BRONTOSCORPIO_EMBRYO, 1200, 1.0F);
        transmogrification(output, UP2Items.CONCAVE_FOSSIL, CONCAVENATOR_EGG, 1200, 1.0F);
        transmogrification(output, UP2Items.CRYPTIC_FOSSIL, UP2Items.CRYPTOCLIDUS_EMBRYO, 1200, 1.0F);
        transmogrification(output, UP2Items.PILLAR_FOSSIL, GIANT_CAMPANILE_EGGS, 1200, 1.0F);
        transmogrification(output, UP2Items.COMBUSTIBLE_FOSSIL, HYNERPETON_EGGS, 1200, 1.0F);
        transmogrification(output, UP2Items.FISH_REPTILE_FOSSIL, UP2Items.ICHTHYOSAURUS_EMBRYO, 1200, 1.0F);
        transmogrification(output, UP2Items.CROWN_FOSSIL, KING_LINGCOD_ROE, 1200, 1.0F);
        transmogrification(output, UP2Items.GARGANTUAN_FOSSIL, LEEDSICHTHYS_ROE, 3600, 2.0F);
        transmogrification(output, UP2Items.CLAMP_JAW_FOSSIL, UP2Items.LORRAINOSAURUS_EMBRYO, 2400, 1.5F);
        transmogrification(output, UP2Items.SURGE_FOSSIL, UP2Items.PROGNATHODON_EMBRYO, 2400, 1.5F);
        transmogrification(output, UP2Items.SUCTION_FOSSIL, RHIZODUS_ROE, 2400, 1.5F);
        transmogrification(output, UP2Items.ROT_FOSSIL, SPIKE_TOOTHED_SALMON_ROE, 1200, 1.0F);
        transmogrification(output, UP2Items.SCYTHE_FOSSIL, THERIZINOSAURUS_EGG, 2400, 1.5F);
        transmogrification(output, UP2Items.SHIMMER_FOSSIL, TUSOTEUTHIS_EGGS, 1200, 1.0F);
        transmogrification(output, UP2Items.STRIPED_PELT, UP2Items.THYLACINE_EMBRYO, 1200, 1.0F);
        transmogrification(output, UP2Items.MOLAR_FOSSIL, UP2Items.WOOLLY_MAMMOTH_EMBRYO, 2400, 1.5F);
        transmogrification(output, UP2Items.ROCKET_FOSSIL, CAMEROCERAS_EGGS, 1200, 1.0F);
        transmogrification(output, UP2Items.DOLPHIN_FOSSIL, UP2Items.NIHOHAE_EMBRYO, 1200, 1.0F);
    }

    protected static void transmogrification(RecipeOutput output, ItemLike inputItem, ItemLike resultItem, int processingTime, float experience) {
        TransmogrificationRecipeBuilder.transmogrification(Ingredient.of(inputItem), resultItem.asItem().getDefaultInstance(), experience, processingTime).unlockedBy(getHasName(inputItem), has(inputItem)).save(output, UnusualPrehistory2.modPrefix("transmogrification/" + getItemName(resultItem)));
    }

    public static ResourceLocation suffix(ResourceLocation location, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(location.getNamespace(), location.getPath() + suffix);
    }
}
