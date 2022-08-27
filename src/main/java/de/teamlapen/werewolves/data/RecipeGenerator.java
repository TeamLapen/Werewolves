package de.teamlapen.werewolves.data;

import com.google.common.collect.ImmutableList;
import de.teamlapen.vampirism.data.recipebuilder.AlchemyTableRecipeBuilder;
import de.teamlapen.vampirism.data.recipebuilder.ShapedWeaponTableRecipeBuilder;
import de.teamlapen.vampirism.util.NBTIngredient;
import de.teamlapen.vampirism.util.RegUtil;
import de.teamlapen.werewolves.core.*;
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
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {

    protected static final ImmutableList<ItemLike> SILVER_SMELTABLES = ImmutableList.of(ModBlocks.SILVER_ORE.get(), ModBlocks.DEEPSLATE_SILVER_ORE.get(), ModItems.RAW_SILVER.get());

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
        ItemLike crossbow_arrow = ModItems.V.CROSSBOW_ARROW_NORMAL.get();

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

        AlchemyTableRecipeBuilder
                .builder(ModOils.SILVER_OIL_1)
                .bloodOilIngredient()
                .input(Ingredient.of(ModTags.Items.SILVER_INGOT)).withCriterion("has_silver_ingot", has(ModTags.Items.SILVER_INGOT))
                .build(consumer, modId("silver_oil_1"));
        AlchemyTableRecipeBuilder
                .builder(ModOils.SILVER_OIL_2)
                .ingredient(new NBTIngredient(de.teamlapen.vampirism.core.ModItems.OIL_BOTTLE.get().withOil(ModOils.SILVER_OIL_1.get()))).withCriterion("has_silver_oil_1", has(de.teamlapen.vampirism.core.ModItems.OIL_BOTTLE.get()))
                .input(Ingredient.of(ModTags.Items.SILVER_INGOT)).withCriterion("has_silver_ingot", has(ModTags.Items.SILVER_INGOT))
                .build(consumer, modId("silver_oil_2"));

        ShapedRecipeBuilder.shaped(ModItems.SILVER_HELMET.get()).define('X', silver_ingot).pattern("XXX").pattern("X X").unlockedBy("has_silver_ingot", has(silver_ingot)).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SILVER_CHESTPLATE.get()).define('X', silver_ingot).pattern("X X").pattern("XXX").pattern("XXX").unlockedBy("has_silver_ingot", has(silver_ingot)).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SILVER_LEGGINGS.get()).define('X', silver_ingot).pattern("XXX").pattern("X X").pattern("X X").unlockedBy("has_silver_ingot", has(silver_ingot)).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SILVER_BOOTS.get()).define('X', silver_ingot).pattern("X X").pattern("X X").unlockedBy("has_silver_ingot", has(silver_ingot)).save(consumer);

        ShapelessRecipeBuilder.shapeless(Items.YELLOW_DYE).requires(ModBlocks.DAFFODIL.get()).unlockedBy("has_daffodil", has(ModBlocks.DAFFODIL.get())).save(consumer, modId("daffodil_yellow_dye"));

        generateRecipes(consumer, ModBlockFamilies.JACARANDA_PLANKS);
        generateRecipes(consumer, ModBlockFamilies.MAGIC_PLANKS);

        planksFromLog(consumer, ModBlocks.JACARANDA_PLANKS.get(), ModTags.Items.JACARANDA_LOG);
        planksFromLog(consumer, ModBlocks.MAGIC_PLANKS.get(), ModTags.Items.MAGIC_LOG);
        woodFromLogs(consumer, ModBlocks.JACARANDA_WOOD.get(), ModBlocks.JACARANDA_LOG.get());
        woodFromLogs(consumer, ModBlocks.MAGIC_WOOD.get(), ModBlocks.MAGIC_LOG.get());
        woodFromLogs(consumer, ModBlocks.STRIPPED_JACARANDA_WOOD.get(), ModBlocks.STRIPPED_JACARANDA_LOG.get());
        woodFromLogs(consumer, ModBlocks.STRIPPED_MAGIC_WOOD.get(), ModBlocks.STRIPPED_MAGIC_LOG.get());
        woodenBoat(consumer, ModItems.JACARANDA_BOAT.get(), ModBlocks.JACARANDA_PLANKS.get());
        woodenBoat(consumer, ModItems.MAGIC_BOAT.get(), ModBlocks.MAGIC_PLANKS.get());
        chestBoat(consumer, ModItems.JACARANDA_CHEST_BOAT.get(), ModBlocks.JACARANDA_PLANKS.get());
        chestBoat(consumer, ModItems.MAGIC_CHEST_BOAT.get(), ModBlocks.MAGIC_PLANKS.get());
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
        return RegUtil.id(itemLike.asItem()).toString();
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
