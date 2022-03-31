package de.teamlapen.werewolves.inventory.recipes;

import com.mojang.datafixers.util.Either;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class TagNBTBrewingRecipe implements IBrewingRecipe {

    private final Ingredient input;
    private final Either<TagKey<Item>, Ingredient> ingredient;
    private final ItemStack output;

    public TagNBTBrewingRecipe(Ingredient input, TagKey<Item> ingredient, ItemStack output) {
        this.input = input;
        this.ingredient = Either.left(ingredient);
        this.output = output;
    }

    public TagNBTBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
        this.input = input;
        this.ingredient = Either.right(ingredient);
        this.output = output;
    }

    @Override
    public boolean isInput(@Nonnull ItemStack input) {
        return Helper.matchesItem(this.input, input);
    }

    @Override
    public boolean isIngredient(@Nonnull ItemStack ingredient) {
        return this.ingredient.map(ingredient::is, i -> i.test(ingredient));
    }

    @Nonnull
    @Override
    public ItemStack getOutput(@Nonnull ItemStack input, @Nonnull ItemStack ingredient) {
        return isInput(input) && isIngredient(ingredient) ? this.output.copy() : ItemStack.EMPTY;
    }

    public ItemStack[] getIngredient() {
        return ingredient.map(i -> ForgeRegistries.ITEMS.tags().getTag(i).stream().map(ItemStack::new).toArray(ItemStack[]::new), Ingredient::getItems);
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }
}
