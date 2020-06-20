package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_hoe).key('#', Items.STICK).key('X', ModTags.Items.SILVER_INGOT).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_silver_ingot", this.hasItem(ModTags.Items.SILVER_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_shovel).key('#', Items.STICK).key('X', ModTags.Items.SILVER_INGOT).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_silver_ingot", this.hasItem(ModTags.Items.SILVER_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_sword).key('#', Items.STICK).key('X', ModTags.Items.SILVER_INGOT).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_silver_ingot", this.hasItem(ModTags.Items.SILVER_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_axe).key('#', Items.STICK).key('X', ModTags.Items.SILVER_INGOT).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_silver_ingot", this.hasItem(ModTags.Items.SILVER_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_pickaxe).key('#', Items.STICK).key('X', ModTags.Items.SILVER_INGOT).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_silver_ingot", this.hasItem(ModTags.Items.SILVER_INGOT)).build(consumer);

    }
}
