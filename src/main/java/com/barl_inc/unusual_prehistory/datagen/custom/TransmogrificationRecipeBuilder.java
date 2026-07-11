package com.barl_inc.unusual_prehistory.datagen.custom;

import com.barl_inc.unusual_prehistory.recipes.TransmogrificationRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class TransmogrificationRecipeBuilder implements RecipeBuilder {

    private final Item result;
    private final ItemStack stackResult;
    private final Ingredient ingredient;
    private final float experience;
    private final int processingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private final TransmogrificationRecipe.Factory factory;

    private TransmogrificationRecipeBuilder(ItemStack result, Ingredient ingredient, float experience, int processingTime, TransmogrificationRecipe.Factory factory) {
        this.result = result.getItem();
        this.stackResult = result;
        this.ingredient = ingredient;
        this.experience = experience;
        this.processingTime = processingTime;
        this.factory = factory;
    }

    public static TransmogrificationRecipeBuilder transmogrification(Ingredient ingredient, ItemStack result, float experience, int processingTime) {
        return new TransmogrificationRecipeBuilder(result, ingredient, experience, processingTime, TransmogrificationRecipe::new);
    }

    @Override
    public @NotNull TransmogrificationRecipeBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public @NotNull TransmogrificationRecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return this.result;
    }

    @Override
    public void save(RecipeOutput recipeOutput, @NotNull ResourceLocation location) {
        this.ensureValid(location);
        Advancement.Builder advancement = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(location)).rewards(AdvancementRewards.Builder.recipe(location)).requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        TransmogrificationRecipe recipe = this.factory.create(this.ingredient, this.stackResult, this.experience, this.processingTime);
        recipeOutput.accept(location, recipe, advancement.build(location.withPrefix("recipes/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
