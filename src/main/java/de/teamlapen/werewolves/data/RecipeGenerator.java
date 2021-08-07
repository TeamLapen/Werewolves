package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        ITag<Item> sticks = Tags.Items.RODS_WOODEN;
        ITag<Item> silver_ingot = ModTags.Items.SILVER_INGOT;
        ITag<Item> red_dye = Tags.Items.DYES_RED;
        ITag<Item> string = Tags.Items.STRING;
        Item paper = Items.PAPER;
        Item tnt = Items.TNT;

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ModTags.Items.SILVER_ORE), ModItems.silver_ingot, 0.7F, 200).addCriterion("has_silver_ore", hasItem(ModTags.Items.SILVER_ORE)).build(consumer);
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ModTags.Items.SILVER_ORE), ModItems.silver_ingot, 0.7F, 100).addCriterion("has_silver_ore", hasItem(ModTags.Items.SILVER_ORE)).build(consumer,modId("silver_ingot_from_blasting"));

        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.magic_planks, 4)
                .addIngredient(ModBlocks.magic_log).addCriterion("has_magic_log", hasItem(ModBlocks.magic_log))
                .build(consumer, modId("magic_planks_from_magic_log"));
        ShapelessRecipeBuilder.shapelessRecipe(Blocks.OAK_PLANKS, 4)
                .addIngredient(ModBlocks.jacaranda_log).addCriterion("has_jacaranda_log", hasItem(ModBlocks.jacaranda_log))
                .build(consumer, modId("oak_planks_from_jacaranda_log"));
        ShapelessRecipeBuilder.shapelessRecipe(Items.BONE, 2)
                .addIngredient(ModItems.bone).addCriterion("has_bone", hasItem(ModItems.bone))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_DYE)
                .addIngredient(ModBlocks.wolfsbane).addCriterion("has_wolfsbane", hasItem(ModBlocks.wolfsbane))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.silver_ingot,9)
                .addIngredient(ModBlocks.silver_block).addCriterion("has_silver_block", hasItem(ModBlocks.silver_block))
                .build(consumer, modId("iron_ingot_from_iron_block"));

        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_hoe).patternLine("XX").patternLine(" #").patternLine(" #")
                .key('#', sticks).addCriterion("has_sticks", hasItem(sticks))
                .key('X', silver_ingot).addCriterion("has_silver_ingot", hasItem(silver_ingot))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_shovel).patternLine("X").patternLine("#").patternLine("#")
                .key('#', sticks).addCriterion("has_sticks", hasItem(sticks))
                .key('X', silver_ingot).addCriterion("has_silver_ingot", hasItem(silver_ingot))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_sword).patternLine("X").patternLine("X").patternLine("#")
                .key('#', sticks).addCriterion("has_sticks", hasItem(sticks))
                .key('X', silver_ingot).addCriterion("has_silver_ingot", hasItem(silver_ingot))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_axe).patternLine("XX").patternLine("X#").patternLine(" #")
                .key('#', sticks).addCriterion("has_sticks", hasItem(sticks))
                .key('X', silver_ingot).addCriterion("has_silver_ingot", hasItem(silver_ingot))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.silver_pickaxe).patternLine("XXX").patternLine(" # ").patternLine(" # ")
                .key('#', sticks).addCriterion("has_sticks", hasItem(sticks))
                .key('X', silver_ingot).addCriterion("has_silver_ingot", hasItem(silver_ingot))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.stone_altar).patternLine("S S").patternLine("SSS").patternLine("SSS")
                .key('S', Items.STONE_BRICKS).addCriterion("has_stone_bricks", hasItem(Items.STONE_BRICKS))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.stone_altar_fire_bowl).patternLine("SPS").patternLine("SSS").patternLine(" S ")
                .key('S', Items.STONE_BRICKS).addCriterion("has_stone_bricks", hasItem(Items.STONE_BRICKS))
                .key('P', ItemTags.PLANKS).addCriterion("has_planks", hasItem(ItemTags.PLANKS))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.silver_block).patternLine("###").patternLine("###").patternLine("###")
                .key('#', silver_ingot).addCriterion("has_silver_ingot", hasItem(silver_ingot))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.fire_cracker).patternLine("RSS").patternLine("PTP").patternLine("PTP")
                .key('R', red_dye).addCriterion("has_red_dye", hasItem(red_dye))
                .key('S', string).addCriterion("has_strings", hasItem(string))
                .key('P', paper).addCriterion("has_paper", hasItem(paper))
                .key('T', tnt).addCriterion("has_tnt", hasItem(tnt))
                .build(consumer);
    }

    private static ResourceLocation modId(String name) {
        return new ResourceLocation(REFERENCE.MODID, name);
    }

}
