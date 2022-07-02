package de.teamlapen.werewolves.data;

import com.google.common.collect.ImmutableList;
import de.teamlapen.vampirism.data.recipebuilder.ShapedWeaponTableRecipeBuilder;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModRecipes;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {

    protected static final ImmutableList<ItemLike> SILVER_SMELTABLES = ImmutableList.of(ModBlocks.silver_ore.get(), ModBlocks.deepslate_silver_ore.get(), ModItems.raw_silver.get());

    public RecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private static ResourceLocation modId(String name) {
        return new ResourceLocation(REFERENCE.MODID, name);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        TagKey<Item> sticks = Tags.Items.RODS_WOODEN;
        TagKey<Item> silver_ingot = ModTags.Items.SILVER_INGOT;
        TagKey<Item> silver_nugget = ModTags.Items.SILVER_NUGGET;
        TagKey<Item> raw_silver = ModTags.Items.RAW_MATERIALS_SILVER;
        TagKey<Item> iron_ingot = Tags.Items.INGOTS_IRON;
        TagKey<Item> feathers = Tags.Items.FEATHERS;
        ItemLike crossbow_arrow = ModItems.V.crossbow_arrow_normal.get();

        planksFromLog(consumer, ModBlocks.magic_planks.get(), ModTags.Items.MAGIC_LOGS);
        ShapelessRecipeBuilder.shapeless(Blocks.OAK_PLANKS, 4).requires(ModTags.Items.JACARANDA_LOGS).group("planks").unlockedBy("has_log", has(ModTags.Items.MAGIC_LOGS)).save(consumer, REFERENCE.MODID + ":oak_planks");


        ShapelessRecipeBuilder.shapeless(Items.BONE, 2)
                .requires(ModItems.cracked_bone.get()).unlockedBy("has_broken_bone", has(ModItems.cracked_bone.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE)
                .requires(ModBlocks.wolfsbane.get()).unlockedBy("has_wolfsbane", has(ModBlocks.wolfsbane.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.silver_hoe.get()).pattern("XX").pattern(" #").pattern(" #")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.silver_shovel.get()).pattern("X").pattern("#").pattern("#")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.silver_sword.get()).pattern("X").pattern("X").pattern("#")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.silver_axe.get()).pattern("XX").pattern("X#").pattern(" #")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.silver_pickaxe.get()).pattern("XXX").pattern(" # ").pattern(" # ")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.stone_altar.get()).pattern("S S").pattern("SSS").pattern("SSS")
                .define('S', Items.STONE_BRICKS).unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.stone_altar_fire_bowl.get()).pattern("SPS").pattern("SSS").pattern(" S ")
                .define('S', Items.STONE_BRICKS).unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
                .define('P', ItemTags.PLANKS).unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(consumer);
        nineBlockStorageRecipes(consumer, ModItems.silver_ingot.get(), ModBlocks.silver_block.get());
        nineBlockStorageRecipes(consumer, ModItems.raw_silver.get(), ModBlocks.raw_silver_block.get());
        nineBlockStorageRecipesWithCustomPacking(consumer, ModItems.silver_nugget.get(), ModItems.silver_ingot.get(), REFERENCE.MODID + ":silver_ingot_from_nuggets", "silver_ingot");
        oreSmelting(consumer, SILVER_SMELTABLES, ModItems.silver_ingot.get(), 0.7f, 200, "silver_ingot");
        oreBlasting(consumer, SILVER_SMELTABLES, ModItems.silver_ingot.get(), 0.7f, 100, "silver_ingot");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.silver_axe.get(), ModItems.silver_hoe.get(), ModItems.silver_pickaxe.get(), ModItems.silver_shovel.get(), ModItems.silver_sword.get()), ModItems.silver_nugget.get(), 0.1f, 200).unlockedBy("has_silver_axe", has(ModItems.silver_axe.get())).unlockedBy("has_silver_hoe", has(ModItems.silver_hoe.get())).unlockedBy("has_silver_pickaxe", has(ModItems.silver_pickaxe.get())).unlockedBy("has_silver_shovel", has(ModItems.silver_shovel.get())).unlockedBy("has_silver_sword", has(ModItems.silver_sword.get())).save(consumer, getSmeltingRecipeName(ModItems.silver_nugget.get()));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.silver_axe.get(), ModItems.silver_hoe.get(), ModItems.silver_pickaxe.get(), ModItems.silver_shovel.get(), ModItems.silver_sword.get()), ModItems.silver_nugget.get(), 0.1f, 100).unlockedBy("has_silver_axe", has(ModItems.silver_axe.get())).unlockedBy("has_silver_hoe", has(ModItems.silver_hoe.get())).unlockedBy("has_silver_pickaxe", has(ModItems.silver_pickaxe.get())).unlockedBy("has_silver_shovel", has(ModItems.silver_shovel.get())).unlockedBy("has_silver_sword", has(ModItems.silver_sword.get())).save(consumer, getBlastingRecipeName(ModItems.silver_nugget.get()));

        SpecialRecipeBuilder.special(ModRecipes.weapon_oil.get()).save(consumer, REFERENCE.MODID + ":weapon_oil");

        ShapedWeaponTableRecipeBuilder.shapedWeaponTable(ModItems.crossbow_arrow_silver_bolt.get(), 3).pattern(" X ").pattern("XYX").pattern(" S ").pattern(" F ")
                .lava(1)
                .define('S', sticks).unlockedBy("hasSticks", has(sticks))
                .define('X', silver_nugget).unlockedBy("has_silver_nugget", has(silver_nugget))
                .define('F', feathers).unlockedBy("has_feathers", has(feathers))
                .define('Y', iron_ingot).unlockedBy("has_iron", has(iron_ingot))
                .unlockedBy("has_crossbow_arrow", has(crossbow_arrow))
                .save(consumer, modId("crossbow_arrow_silver_bolt"));
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> p_176744_, ItemLike p_176745_, ItemLike p_176746_) {
        nineBlockStorageRecipes(p_176744_, p_176745_, p_176746_, getSimpleRecipeName(p_176746_), (String) null, getSimpleRecipeName(p_176745_), (String) null);
    }

    protected static void nineBlockStorageRecipesWithCustomPacking(Consumer<FinishedRecipe> p_176563_, ItemLike p_176564_, ItemLike p_176565_, String p_176566_, String p_176567_) {
        nineBlockStorageRecipes(p_176563_, p_176564_, p_176565_, p_176566_, p_176567_, getSimpleRecipeName(p_176564_), (String) null);
    }

    protected static String getSimpleRecipeName(ItemLike itemLike) {
        return getItemName(itemLike);
    }

    protected static String getItemName(ItemLike itemLike) {
        return itemLike.asItem().getRegistryName().toString();
    }

    protected static String getBlastingRecipeName(ItemLike p_176669_) {
        return getItemName(p_176669_) + "_from_blasting";
    }

    protected static String getSmeltingRecipeName(ItemLike p_176657_) {
        return getItemName(p_176657_) + "_from_smelting";
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> p_176592_, List<ItemLike> p_176593_, ItemLike p_176594_, float p_176595_, int p_176596_, String p_176597_) {
        oreCooking(p_176592_, RecipeSerializer.SMELTING_RECIPE, p_176593_, p_176594_, p_176595_, p_176596_, p_176597_, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> p_176626_, List<ItemLike> p_176627_, ItemLike p_176628_, float p_176629_, int p_176630_, String p_176631_) {
        oreCooking(p_176626_, RecipeSerializer.BLASTING_RECIPE, p_176627_, p_176628_, p_176629_, p_176630_, p_176631_, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> p_176534_, SimpleCookingSerializer<?> p_176535_, List<ItemLike> p_176536_, ItemLike p_176537_, float p_176538_, int p_176539_, String p_176540_, String p_176541_) {
        for (ItemLike itemlike : p_176536_) {
            SimpleCookingRecipeBuilder.cooking(Ingredient.of(itemlike), p_176537_, p_176538_, p_176539_, p_176535_).group(p_176540_).unlockedBy(getHasName(itemlike), has(itemlike)).save(p_176534_, getItemName(p_176537_) + p_176541_ + "_" + getItemName(itemlike).replace(":", "_"));
        }

    }
}
