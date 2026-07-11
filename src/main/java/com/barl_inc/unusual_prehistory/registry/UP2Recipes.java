package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.recipes.TransmogrificationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

public class UP2Recipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, UnusualPrehistory2.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<TransmogrificationRecipe>> TRANSMOGRIFICATION = RECIPE_TYPES.register("transmogrification", () -> new SimpleNamedRecipeType<>("transmogrification"));
    public static final DeferredHolder<RecipeSerializer<?>, TransmogrificationRecipe.Serializer> TRANSMOGRIFICATION_SERIALIZER = RECIPE_SERIALIZERS.register("transmogrification", () -> new TransmogrificationRecipe.Serializer(TransmogrificationRecipe::new));

    public record SimpleNamedRecipeType<T extends Recipe<?>>(String name) implements RecipeType<T> {
        @Override
        public @NotNull String toString() {
            return name;
        }
    }
}
