package de.teamlapen.werewolves.data;

import com.google.common.collect.ImmutableList;
import de.teamlapen.vampirism.data.recipebuilder.AlchemyTableRecipeBuilder;
import de.teamlapen.vampirism.data.recipebuilder.ShapedWeaponTableRecipeBuilder;
import de.teamlapen.vampirism.util.RegUtil;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModOils;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {

    protected static final ImmutableList<ItemLike> SILVER_SMELTABLES = ImmutableList.of(ModBlocks.SILVER_ORE.get(), ModBlocks.DEEPSLATE_SILVER_ORE.get(), ModItems.RAW_SILVER.get());

    public RecipeGenerator(@NotNull DataGenerator generatorIn) {
        super(generatorIn);
    }

    private static @NotNull ResourceLocation modId(@NotNull String name) {
        return new ResourceLocation(REFERENCE.MODID, name);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        TagKey<Item> sticks = Tags.Items.RODS_WOODEN;
        TagKey<Item> silver_ingot = ModTags.Items.SILVER_INGOT;
        TagKey<Item> silver_nugget = ModTags.Items.SILVER_NUGGET;
        TagKey<Item> raw_silver = ModTags.Items.RAW_MATERIALS_SILVER;
        TagKey<Item> iron_ingot = Tags.Items.INGOTS_IRON;
        TagKey<Item> feathers = Tags.Items.FEATHERS;
        ItemLike crossbow_arrow = ModItems.V.CROSSBOW_ARROW_NORMAL.get();

        planksFromLog(consumer, ModBlocks.MAGIC_PLANKS.get(), ModTags.Items.MAGIC_LOGS);
        ShapelessRecipeBuilder.shapeless(Blocks.OAK_PLANKS, 4).requires(ModTags.Items.JACARANDA_LOGS).group("planks").unlockedBy("has_log", has(ModTags.Items.MAGIC_LOGS)).save(consumer, REFERENCE.MODID + ":oak_planks");


        ShapelessRecipeBuilder.shapeless(Items.BONE, 2)
                .requires(ModItems.CRACKED_BONE.get()).unlockedBy("has_broken_bone", has(ModItems.CRACKED_BONE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE)
                .requires(ModBlocks.WOLFSBANE.get()).unlockedBy("has_wolfsbane", has(ModBlocks.WOLFSBANE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.SILVER_HOE.get()).pattern("XX").pattern(" #").pattern(" #")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SILVER_SHOVEL.get()).pattern("X").pattern("#").pattern("#")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SILVER_SWORD.get()).pattern("X").pattern("X").pattern("#")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SILVER_AXE.get()).pattern("XX").pattern("X#").pattern(" #")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SILVER_PICKAXE.get()).pattern("XXX").pattern(" # ").pattern(" # ")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.STONE_ALTAR.get()).pattern("S S").pattern("SSS").pattern("SSS")
                .define('S', Items.STONE_BRICKS).unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.STONE_ALTAR_FIRE_BOWL.get()).pattern("SPS").pattern("SSS").pattern(" S ")
                .define('S', Items.STONE_BRICKS).unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
                .define('P', ItemTags.PLANKS).unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(consumer);
        nineBlockStorageRecipes(consumer, ModItems.SILVER_INGOT.get(), ModBlocks.SILVER_BLOCK.get());
        nineBlockStorageRecipes(consumer, ModItems.RAW_SILVER.get(), ModBlocks.RAW_SILVER_BLOCK.get());
        nineBlockStorageRecipesWithCustomPacking(consumer, ModItems.SILVER_NUGGET.get(), ModItems.SILVER_INGOT.get(), REFERENCE.MODID + ":silver_ingot_from_nuggets", "silver_ingot");
        oreSmelting(consumer, SILVER_SMELTABLES, ModItems.SILVER_INGOT.get(), 0.7f, 200, "silver_ingot");
        oreBlasting(consumer, SILVER_SMELTABLES, ModItems.SILVER_INGOT.get(), 0.7f, 100, "silver_ingot");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.SILVER_AXE.get(), ModItems.SILVER_HOE.get(), ModItems.SILVER_PICKAXE.get(), ModItems.SILVER_SHOVEL.get(), ModItems.SILVER_SWORD.get()), ModItems.SILVER_NUGGET.get(), 0.1f, 200).unlockedBy("has_silver_axe", has(ModItems.SILVER_AXE.get())).unlockedBy("has_silver_hoe", has(ModItems.SILVER_HOE.get())).unlockedBy("has_silver_pickaxe", has(ModItems.SILVER_PICKAXE.get())).unlockedBy("has_silver_shovel", has(ModItems.SILVER_SHOVEL.get())).unlockedBy("has_silver_sword", has(ModItems.SILVER_SWORD.get())).save(consumer, getSmeltingRecipeName(ModItems.SILVER_NUGGET.get()));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.SILVER_AXE.get(), ModItems.SILVER_HOE.get(), ModItems.SILVER_PICKAXE.get(), ModItems.SILVER_SHOVEL.get(), ModItems.SILVER_SWORD.get()), ModItems.SILVER_NUGGET.get(), 0.1f, 100).unlockedBy("has_silver_axe", has(ModItems.SILVER_AXE.get())).unlockedBy("has_silver_hoe", has(ModItems.SILVER_HOE.get())).unlockedBy("has_silver_pickaxe", has(ModItems.SILVER_PICKAXE.get())).unlockedBy("has_silver_shovel", has(ModItems.SILVER_SHOVEL.get())).unlockedBy("has_silver_sword", has(ModItems.SILVER_SWORD.get())).save(consumer, getBlastingRecipeName(ModItems.SILVER_NUGGET.get()));

        ShapedWeaponTableRecipeBuilder.shapedWeaponTable(ModItems.CROSSBOW_ARROW_SILVER_BOLT.get(), 3).pattern(" X ").pattern("XYX").pattern(" S ").pattern(" F ")
                .lava(1)
                .define('S', sticks).unlockedBy("hasSticks", has(sticks))
                .define('X', silver_nugget).unlockedBy("has_silver_nugget", has(silver_nugget))
                .define('F', feathers).unlockedBy("has_feathers", has(feathers))
                .define('Y', iron_ingot).unlockedBy("has_iron", has(iron_ingot))
                .unlockedBy("has_crossbow_arrow", has(crossbow_arrow))
                .save(consumer, modId("crossbow_arrow_silver_bolt"));

        AlchemyTableRecipeBuilder.builder(ModOils.SILVER_OIL_1).bloodOilIngredient().input(Ingredient.of(ModTags.Items.SILVER_INGOT)).build(consumer, new ResourceLocation(REFERENCE.MODID, "silver_oil_1"));
        AlchemyTableRecipeBuilder.builder(ModOils.SILVER_OIL_2).oilIngredient(ModOils.SILVER_OIL_1.get()).input(Ingredient.of(ModTags.Items.SILVER_INGOT)).build(consumer, new ResourceLocation(REFERENCE.MODID, "silver_oil_2"));
    }

    protected static void nineBlockStorageRecipes(@NotNull Consumer<FinishedRecipe> p_176744_, @NotNull ItemLike p_176745_, @NotNull ItemLike p_176746_) {
        nineBlockStorageRecipes(p_176744_, p_176745_, p_176746_, getSimpleRecipeName(p_176746_), (String) null, getSimpleRecipeName(p_176745_), (String) null);
    }

    protected static void nineBlockStorageRecipesWithCustomPacking(@NotNull Consumer<FinishedRecipe> p_176563_, @NotNull ItemLike p_176564_, @NotNull ItemLike p_176565_, @NotNull String p_176566_, String p_176567_) {
        nineBlockStorageRecipes(p_176563_, p_176564_, p_176565_, p_176566_, p_176567_, getSimpleRecipeName(p_176564_), (String) null);
    }

    protected static @NotNull String getSimpleRecipeName(@NotNull ItemLike itemLike) {
        return getItemName(itemLike);
    }

    protected static @NotNull String getItemName(@NotNull ItemLike itemLike) {
        return RegUtil.id(itemLike.asItem()).toString();
    }

    protected static @NotNull String getBlastingRecipeName(@NotNull ItemLike p_176669_) {
        return getItemName(p_176669_) + "_from_blasting";
    }

    protected static @NotNull String getSmeltingRecipeName(@NotNull ItemLike p_176657_) {
        return getItemName(p_176657_) + "_from_smelting";
    }

    protected static void oreSmelting(@NotNull Consumer<FinishedRecipe> p_176592_, @NotNull List<ItemLike> p_176593_, @NotNull ItemLike p_176594_, float p_176595_, int p_176596_, String p_176597_) {
        oreCooking(p_176592_, RecipeSerializer.SMELTING_RECIPE, p_176593_, p_176594_, p_176595_, p_176596_, p_176597_, "_from_smelting");
    }

    protected static void oreBlasting(@NotNull Consumer<FinishedRecipe> p_176626_, @NotNull List<ItemLike> p_176627_, @NotNull ItemLike p_176628_, float p_176629_, int p_176630_, String p_176631_) {
        oreCooking(p_176626_, RecipeSerializer.BLASTING_RECIPE, p_176627_, p_176628_, p_176629_, p_176630_, p_176631_, "_from_blasting");
    }

    protected static void oreCooking(@NotNull Consumer<FinishedRecipe> p_176534_, @NotNull SimpleCookingSerializer<?> p_176535_, @NotNull List<ItemLike> p_176536_, @NotNull ItemLike p_176537_, float p_176538_, int p_176539_, String p_176540_, String p_176541_) {
        for (ItemLike itemlike : p_176536_) {
            SimpleCookingRecipeBuilder.cooking(Ingredient.of(itemlike), p_176537_, p_176538_, p_176539_, p_176535_).group(p_176540_).unlockedBy(getHasName(itemlike), has(itemlike)).save(p_176534_, getItemName(p_176537_) + p_176541_ + "_" + getItemName(itemlike).replace(":", "_"));
        }

    }
}
