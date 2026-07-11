package com.barl_inc.unusual_prehistory.integration.jei;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.inventory.TransmogrifierScreen;
import com.barl_inc.unusual_prehistory.recipes.TransmogrificationRecipe;
import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2Items;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("removal")
public class TransmogrificationCategory implements IRecipeCategory<TransmogrificationRecipe> {

    public final static ResourceLocation UID = UnusualPrehistory2.modPrefix("transmogrification");

    public final static ResourceLocation BACKGROUND = UnusualPrehistory2.modPrefix("textures/gui/transmogrifier.png");

    private final IDrawable background;
    private final IDrawable icon;

    protected final IDrawableAnimated fuel;
    protected final IDrawableAnimated progress;

    public TransmogrificationCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(BACKGROUND, 32, 28, 116, 52);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(UP2Blocks.TRANSMOGRIFIER.get()));
        this.fuel = guiHelper.drawableBuilder(BACKGROUND, 176, 0, TransmogrifierScreen.FUEL_WIDTH, TransmogrifierScreen.FUEL_HEIGHT).buildAnimated(800, IDrawableAnimated.StartDirection.RIGHT, true);
        this.progress = guiHelper.drawableBuilder(BACKGROUND, 176, 14, TransmogrifierScreen.PROGRESS_WIDTH, TransmogrifierScreen.PROGRESS_HEIGHT).buildAnimated(1200, IDrawableAnimated.StartDirection.LEFT, false);;
    }

    @Override
    public @NotNull RecipeType<TransmogrificationRecipe> getRecipeType() {
        return JEIPlugin.TRANSMOGRIFICATION;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable(UnusualPrehistory2.MOD_ID + "." + "jei.transmogrification");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    protected void drawProgress(TransmogrificationRecipe recipe, GuiGraphics guiGraphics, int y, int x) {
        int cookTime = recipe.processingTime();
        float experience = recipe.experience();
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            int stringWidth = fontRenderer.width(timeString);
            guiGraphics.drawString(fontRenderer, timeString, (this.getWidth() - stringWidth) + x, y, 0xFF808080, false);
        }
        if (experience > 0) {
            Component experienceString = Component.translatable("gui.jei.category.smelting.experience", experience);
            int stringWidth = fontRenderer.width(experienceString);
            guiGraphics.drawString(fontRenderer, experienceString, (this.getWidth() - stringWidth) + x + 6, y + 10, 0xFF808080, false);
        }
    }

    @Override
    public void draw(@NotNull TransmogrificationRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        this.drawProgress(recipe, guiGraphics, 24, -91);
        this.fuel.draw(guiGraphics, 70, 32);
        this.progress.draw(guiGraphics, 30, 1);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TransmogrificationRecipe recipe, @NotNull IFocusGroup foci) {
        builder.addSlot(RecipeIngredientRole.INPUT, 5, 3).addIngredients(recipe.getIngredients().getFirst());
        builder.addSlot(RecipeIngredientRole.INPUT, 50, 31).addItemStack(new ItemStack(UP2Items.ORGANIC_OOZE.get()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 93, 3).addItemStack(getResultItem(recipe));
    }

    public static ItemStack getResultItem(Recipe<?> recipe) {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) {
            throw new NullPointerException("level must not be null.");
        }
        RegistryAccess registryAccess = level.registryAccess();
        return recipe.getResultItem(registryAccess);
    }
}
