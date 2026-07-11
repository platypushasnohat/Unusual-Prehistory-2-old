package com.barl_inc.unusual_prehistory.integration.jei;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.inventory.TransmogrifierMenu;
import com.barl_inc.unusual_prehistory.client.inventory.TransmogrifierScreen;
import com.barl_inc.unusual_prehistory.recipes.TransmogrificationRecipe;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2MenuTypes;
import com.barl_inc.unusual_prehistory.registry.UP2Recipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@JeiPlugin
@OnlyIn(Dist.CLIENT)
public class JEIPlugin implements IModPlugin {

    public static RecipeType<TransmogrificationRecipe> TRANSMOGRIFICATION = new RecipeType<>(TransmogrificationCategory.UID, TransmogrificationRecipe.class);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return UnusualPrehistory2.modPrefix("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new TransmogrificationCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<TransmogrificationRecipe> transmogrificationRecipes = recipeManager.getAllRecipesFor(UP2Recipes.TRANSMOGRIFICATION.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(TRANSMOGRIFICATION, transmogrificationRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(UP2Blocks.TRANSMOGRIFIER.get()), TRANSMOGRIFICATION);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(TransmogrifierScreen.class, 62, 29, 54, 20, TRANSMOGRIFICATION);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(TransmogrifierMenu.class, UP2MenuTypes.TRANSMOGRIFIER.get(), TRANSMOGRIFICATION, 1, 1, 3, 36);
    }
}
