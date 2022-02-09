package de.teamlapen.werewolves.inventory.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.brewing.BrewingRecipe;

import javax.annotation.Nonnull;

public class TagBrewingRecipe extends BrewingRecipe {

    private final ITag<Item> ingredient;

    public TagBrewingRecipe(Ingredient input, ITag<Item> ingredient, ItemStack output) {
        super(input, null, output);
        this.ingredient = ingredient;
    }

    @Nonnull
    @Override
    public Ingredient getIngredient() {
        return Ingredient.of(this.ingredient);
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return this.ingredient.contains(ingredient.getItem());
    }
}
