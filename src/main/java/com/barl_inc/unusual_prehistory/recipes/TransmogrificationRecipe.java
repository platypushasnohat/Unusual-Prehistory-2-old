package com.barl_inc.unusual_prehistory.recipes;

import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.registry.UP2Recipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record TransmogrificationRecipe(Ingredient ingredient, ItemStack result, float experience, int processingTime) implements Recipe<SingleRecipeInput> {

    @Override
    public boolean matches(SingleRecipeInput input, @NotNull Level level) {
        return this.ingredient.test(input.item());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput input, HolderLookup.@NotNull Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.ingredient);
        return nonnulllist;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return this.result;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return UP2Recipes.TRANSMOGRIFICATION.get();
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(UP2Blocks.TRANSMOGRIFIER.get());
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return UP2Recipes.TRANSMOGRIFICATION_SERIALIZER.get();
    }

    public interface Factory {
        TransmogrificationRecipe create(Ingredient ingredient, ItemStack result, float experience, int cookingTime);
    }

    public static class Serializer implements RecipeSerializer<TransmogrificationRecipe> {

        private final Factory factory;
        private final MapCodec<TransmogrificationRecipe> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, TransmogrificationRecipe> streamCodec;

        public Serializer(Factory factory) {
            this.factory = factory;
            this.codec = RecordCodecBuilder.mapCodec(
                    instance -> instance.group(
                                    Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(TransmogrificationRecipe::ingredient),
                                    ItemStack.CODEC.fieldOf("output").forGetter(TransmogrificationRecipe::result),
                                    Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(TransmogrificationRecipe::experience),
                                    Codec.INT.fieldOf("processing_time").orElse(1200).forGetter(TransmogrificationRecipe::processingTime)
                            )
                            .apply(instance, factory::create)
            );
            this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
        }

        @Override
        public @NotNull MapCodec<TransmogrificationRecipe> codec() {
            return this.codec;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, TransmogrificationRecipe> streamCodec() {
            return this.streamCodec;
        }

        private TransmogrificationRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
            float f = buffer.readFloat();
            int i = buffer.readVarInt();
            return this.factory.create(ingredient, itemstack, f, i);
        }

        private void toNetwork(RegistryFriendlyByteBuf buffer, TransmogrificationRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient());
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result());
            buffer.writeFloat(recipe.experience());
            buffer.writeVarInt(recipe.processingTime());
        }

        public TransmogrificationRecipe create(Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
            return this.factory.create(ingredient, result, experience, cookingTime);
        }
    }
}

