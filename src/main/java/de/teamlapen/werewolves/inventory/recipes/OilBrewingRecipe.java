package de.teamlapen.werewolves.inventory.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import javax.annotation.Nonnull;

public class OilBrewingRecipe implements IBrewingRecipe {

    @Nonnull
    private final Ingredient input;
    @Nonnull
    private final ITag<Item> ingredient_tag;
    @Nonnull
    private final ItemStack output;
    private Ingredient ingredient;

    public OilBrewingRecipe(@Nonnull Ingredient input, @Nonnull ITag<Item> ingredient, @Nonnull ItemStack output) {
        this.input = input;
        this.ingredient_tag = ingredient;
        this.output = output;
    }

    @Override
    public boolean isInput(@Nonnull ItemStack input) {
        return this.input.test(input);
    }

    @Override
    public boolean isIngredient(@Nonnull ItemStack ingredient) {
        if (this.ingredient == null){
            this.ingredient = Ingredient.of(this.ingredient_tag);
        }
        return this.ingredient.test(ingredient);
    }

    @Nonnull
    @Override
    public ItemStack getOutput(@Nonnull ItemStack input, @Nonnull ItemStack ingredient) {
        return isInput(input) && isIngredient(ingredient) ? this.output.copy() : ItemStack.EMPTY;
    }
}
