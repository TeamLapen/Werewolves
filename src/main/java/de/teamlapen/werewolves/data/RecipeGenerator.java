package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_hoe).key('#', Items.STICK).key('X', ModTags.Items.SILVER_INGOT).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_silver_ingot", hasItem(ModTags.Items.SILVER_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_shovel).key('#', Items.STICK).key('X', ModTags.Items.SILVER_INGOT).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_silver_ingot", hasItem(ModTags.Items.SILVER_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_sword).key('#', Items.STICK).key('X', ModTags.Items.SILVER_INGOT).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_silver_ingot", hasItem(ModTags.Items.SILVER_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_axe).key('#', Items.STICK).key('X', ModTags.Items.SILVER_INGOT).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_silver_ingot", hasItem(ModTags.Items.SILVER_INGOT)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_pickaxe).key('#', Items.STICK).key('X', ModTags.Items.SILVER_INGOT).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_silver_ingot", hasItem(ModTags.Items.SILVER_INGOT)).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.magic_planks, 4).addIngredient(ModBlocks.magic_log).addCriterion("has_magic_log", hasItem(ModBlocks.magic_log)).build(consumer, modId("magic_planks_from_magic_log"));
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.OAK_PLANKS, 4).addIngredient(ModBlocks.jacaranda_log).addCriterion("has_jacaranda_log", hasItem(ModBlocks.jacaranda_log)).build(consumer, modId("oak_planks_from_jacaranda_log"));

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ModTags.Items.SILVER_ORE), ModItems.silver_ingot, 0.7F, 200).addCriterion("has_silver_ore", hasItem(ModTags.Items.SILVER_ORE)).build(consumer);
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ModTags.Items.SILVER_ORE), ModItems.silver_ingot, 0.7F, 100).addCriterion("has_silver_ore", hasItem(ModTags.Items.SILVER_ORE)).build(consumer,modId("silver_ingot_from_blasting"));

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.stone_altar).key('S', Items.STONE_BRICKS).patternLine("S S").patternLine("SSS").patternLine("SSS").addCriterion("has_stone_bricks", hasItem(Items.STONE_BRICKS)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.stone_altar_fire_bowl).key('S', Items.STONE_BRICKS).key('P', ItemTags.PLANKS).patternLine("SPS").patternLine("SSS").patternLine(" S ").addCriterion("has_stone_bricks", hasItem(Items.STONE_BRICKS)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.BONE, 2).addIngredient(ModItems.bone).addCriterion("has_bone", hasItem(ModItems.bone)).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_DYE).addIngredient(ModBlocks.wolfsbane).addCriterion("has_wolfsbane", hasItem(ModBlocks.wolfsbane)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.silver_block).key('#', ModItems.silver_ingot).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_silver_ingot", hasItem(ModItems.silver_ingot)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.silver_ingot,9).addIngredient(ModBlocks.silver_block).setGroup("silver_ingot").addCriterion("has_silver_block", hasItem(ModBlocks.silver_block)).build(consumer, modId("iron_ingot_from_iron_block"));

    }

    private static ResourceLocation modId(String name) {
        return new ResourceLocation(REFERENCE.MODID, name);
    }

}
