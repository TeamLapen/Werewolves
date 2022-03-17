package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.data.recipebuilder.ShapedWeaponTableRecipeBuilder;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModRecipes;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private static ResourceLocation modId(String name) {
        return new ResourceLocation(REFERENCE.MODID, name);
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        Tag<Item> sticks = Tags.Items.RODS_WOODEN;
        Tag<Item> silver_ingot = ModTags.Items.SILVER_INGOT;
        Tag<Item> silver_nugget = ModTags.Items.SILVER_NUGGET;
        Tag<Item> iron_ingot = Tags.Items.INGOTS_IRON;
        Tag<Item> feathers = Tags.Items.FEATHERS;
        ItemLike crossbow_arrow = ModItems.V.crossbow_arrow_normal;

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.SILVER_ORE), ModItems.silver_ingot, 0.7F, 200).unlockedBy("has_silver_ore", has(ModTags.Items.SILVER_ORE)).save(consumer);
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModTags.Items.SILVER_ORE), ModItems.silver_ingot, 0.7F, 100).unlockedBy("has_silver_ore", has(ModTags.Items.SILVER_ORE)).save(consumer, modId("silver_ingot_from_blasting"));

        ShapelessRecipeBuilder.shapeless(ModBlocks.magic_planks, 4)
                .requires(ModBlocks.magic_log).unlockedBy("has_magic_log", has(ModBlocks.magic_log))
                .save(consumer, modId("magic_planks_from_magic_log"));
        ShapelessRecipeBuilder.shapeless(Blocks.OAK_PLANKS, 4)
                .requires(ModBlocks.jacaranda_log).unlockedBy("has_jacaranda_log", has(ModBlocks.jacaranda_log))
                .save(consumer, modId("oak_planks_from_jacaranda_log"));
        ShapelessRecipeBuilder.shapeless(Items.BONE, 2)
                .requires(ModItems.cracked_bone).unlockedBy("has_broken_bone", has(ModItems.cracked_bone))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE)
                .requires(ModBlocks.wolfsbane).unlockedBy("has_wolfsbane", has(ModBlocks.wolfsbane))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.silver_ingot, 9)
                .requires(ModBlocks.silver_block).unlockedBy("has_silver_block", has(ModBlocks.silver_block))
                .save(consumer, modId("iron_ingot_from_iron_block"));
        ShapelessRecipeBuilder.shapeless(ModItems.silver_nugget, 9)
                .requires(silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer, modId("silver_nugget"));


        ShapedRecipeBuilder.shaped(ModItems.silver_hoe).pattern("XX").pattern(" #").pattern(" #")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.silver_shovel).pattern("X").pattern("#").pattern("#")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.silver_sword).pattern("X").pattern("X").pattern("#")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.silver_axe).pattern("XX").pattern("X#").pattern(" #")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.silver_pickaxe).pattern("XXX").pattern(" # ").pattern(" # ")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.stone_altar).pattern("S S").pattern("SSS").pattern("SSS")
                .define('S', Items.STONE_BRICKS).unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.stone_altar_fire_bowl).pattern("SPS").pattern("SSS").pattern(" S ")
                .define('S', Items.STONE_BRICKS).unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
                .define('P', ItemTags.PLANKS).unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.silver_block).pattern("###").pattern("###").pattern("###")
                .define('#', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.silver_ingot).pattern("###").pattern("###").pattern("###")
                .define('#', silver_nugget).unlockedBy("has_silver_nugget", has(silver_nugget))
                .save(consumer, modId("silver_ingot_from_nuggets"));

        SpecialRecipeBuilder.special(ModRecipes.weapon_oil).save(consumer, REFERENCE.MODID + ":weapon_oil");

        ShapedWeaponTableRecipeBuilder.shapedWeaponTable(ModItems.crossbow_arrow_silver_bolt, 3).pattern(" X ").pattern("XYX").pattern(" S ").pattern(" F ")
                .lava(1)
                .define('S', sticks).unlockedBy("hasSticks", has(sticks))
                .define('X', silver_nugget).unlockedBy("has_silver_nugget", has(silver_nugget))
                .define('F', feathers).unlockedBy("has_feathers", has(feathers))
                .define('Y', iron_ingot).unlockedBy("has_iron", has(iron_ingot))
                .unlockedBy("has_crossbow_arrow", has(crossbow_arrow))
                .save(consumer, modId("crossbow_arrow_silver_bolt"));
    }

}
