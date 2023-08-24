package de.teamlapen.werewolves.data;

import com.google.common.collect.ImmutableList;
import de.teamlapen.vampirism.data.recipebuilder.AlchemicalCauldronRecipeBuilder;
import de.teamlapen.vampirism.data.recipebuilder.AlchemyTableRecipeBuilder;
import de.teamlapen.vampirism.data.recipebuilder.ShapedWeaponTableRecipeBuilder;
import de.teamlapen.vampirism.entity.player.hunter.skills.HunterSkills;
import de.teamlapen.vampirism.util.NBTIngredient;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModOils;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RecipeGenerator extends VanillaRecipeProvider {

    protected static final ImmutableList<ItemLike> SILVER_SMELTABLES = ImmutableList.of(ModBlocks.SILVER_ORE.get(), ModBlocks.DEEPSLATE_SILVER_ORE.get(), ModItems.RAW_SILVER.get());

    public RecipeGenerator(@NotNull PackOutput packOutput) {
        super(packOutput);
    }

    private static ResourceLocation modId(String name) {
        return new ResourceLocation(REFERENCE.MODID, name);
    }

    @Override
    protected void buildRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        TagKey<Item> sticks = Tags.Items.RODS_WOODEN;
        TagKey<Item> silver_ingot = ModTags.Items.SILVER_INGOT;
        TagKey<Item> silver_nugget = ModTags.Items.SILVER_NUGGET;
        TagKey<Item> raw_silver = ModTags.Items.RAW_MATERIALS_SILVER;
        TagKey<Item> iron_ingot = Tags.Items.INGOTS_IRON;
        TagKey<Item> feathers = Tags.Items.FEATHERS;
        ItemLike crossbow_arrow = ModItems.V.CROSSBOW_ARROW_NORMAL.get();
        TagKey<Item> planks = ItemTags.PLANKS;
        TagKey<Item> diamond = Tags.Items.GEMS_DIAMOND;
        TagKey<Item> obsidian = Tags.Items.OBSIDIAN;
        ItemLike wolfsbane_diffuser_core = ModItems.WOLFSBANE_DIFFUSER_CORE.get();
        ItemLike wolfsbane_diffuser_core_improved = ModItems.WOLFSBANE_DIFFUSER_CORE_IMPROVED.get();
        ItemLike wolfsbane_diffuser = ModBlocks.WOLFSBANE_DIFFUSER.get();
        TagKey<Item> wool = ItemTags.WOOL;
        ItemLike wolfsbane = ModBlocks.WOLFSBANE.get();


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE, 2)
                .requires(ModItems.CRACKED_BONE.get()).unlockedBy("has_broken_bone", has(ModItems.CRACKED_BONE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PURPLE_DYE)
                .requires(ModBlocks.WOLFSBANE.get()).unlockedBy("has_wolfsbane", has(ModBlocks.WOLFSBANE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SILVER_HOE.get()).pattern("XX").pattern(" #").pattern(" #")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModItems.SILVER_SHOVEL.get()).pattern("X").pattern("#").pattern("#")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModItems.SILVER_SWORD.get()).pattern("X").pattern("X").pattern("#")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModItems.SILVER_AXE.get()).pattern("XX").pattern("X#").pattern(" #")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModItems.SILVER_PICKAXE.get()).pattern("XXX").pattern(" # ").pattern(" # ")
                .define('#', sticks).unlockedBy("has_sticks", has(sticks))
                .define('X', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModBlocks.STONE_ALTAR.get()).pattern("S S").pattern("SSS").pattern("SSS")
                .define('S', Items.STONE_BRICKS).unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModBlocks.STONE_ALTAR_FIRE_BOWL.get()).pattern("SPS").pattern("SSS").pattern(" S ")
                .define('S', Items.STONE_BRICKS).unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
                .define('P', ItemTags.PLANKS).unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(consumer);
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, ModItems.SILVER_INGOT.get(), RecipeCategory.MISC, ModBlocks.SILVER_BLOCK.get());
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, ModItems.RAW_SILVER.get(), RecipeCategory.MISC, ModBlocks.RAW_SILVER_BLOCK.get());
        nineBlockStorageRecipesWithCustomPacking(consumer, RecipeCategory.MISC, ModItems.SILVER_NUGGET.get(), RecipeCategory.MISC, ModItems.SILVER_INGOT.get(), REFERENCE.MODID + ":silver_ingot_from_nuggets", "silver_ingot");
        oreSmelting(consumer, SILVER_SMELTABLES, RecipeCategory.MISC, ModItems.SILVER_INGOT.get(), 0.7f, 200, "silver_ingot");
        oreBlasting(consumer, SILVER_SMELTABLES, RecipeCategory.MISC, ModItems.SILVER_INGOT.get(), 0.7f, 100, "silver_ingot");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.SILVER_AXE.get(), ModItems.SILVER_HOE.get(), ModItems.SILVER_PICKAXE.get(), ModItems.SILVER_SHOVEL.get(), ModItems.SILVER_SWORD.get()), RecipeCategory.MISC, ModItems.SILVER_NUGGET.get(), 0.1f, 200).unlockedBy("has_silver_axe", has(ModItems.SILVER_AXE.get())).unlockedBy("has_silver_hoe", has(ModItems.SILVER_HOE.get())).unlockedBy("has_silver_pickaxe", has(ModItems.SILVER_PICKAXE.get())).unlockedBy("has_silver_shovel", has(ModItems.SILVER_SHOVEL.get())).unlockedBy("has_silver_sword", has(ModItems.SILVER_SWORD.get())).save(consumer, getSmeltingRecipeName(ModItems.SILVER_NUGGET.get()));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.SILVER_AXE.get(), ModItems.SILVER_HOE.get(), ModItems.SILVER_PICKAXE.get(), ModItems.SILVER_SHOVEL.get(), ModItems.SILVER_SWORD.get()), RecipeCategory.MISC, ModItems.SILVER_NUGGET.get(), 0.1f, 100).unlockedBy("has_silver_axe", has(ModItems.SILVER_AXE.get())).unlockedBy("has_silver_hoe", has(ModItems.SILVER_HOE.get())).unlockedBy("has_silver_pickaxe", has(ModItems.SILVER_PICKAXE.get())).unlockedBy("has_silver_shovel", has(ModItems.SILVER_SHOVEL.get())).unlockedBy("has_silver_sword", has(ModItems.SILVER_SWORD.get())).save(consumer, getBlastingRecipeName(ModItems.SILVER_NUGGET.get()));

        ShapedWeaponTableRecipeBuilder.shapedWeaponTable(RecipeCategory.MISC,ModItems.CROSSBOW_ARROW_SILVER_BOLT.get(), 3).pattern(" X ").pattern("XYX").pattern(" S ").pattern(" F ")
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

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SILVER_HELMET.get()).define('X', silver_ingot).pattern("XXX").pattern("X X").unlockedBy("has_silver_ingot", has(silver_ingot)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SILVER_CHESTPLATE.get()).define('X', silver_ingot).pattern("X X").pattern("XXX").pattern("XXX").unlockedBy("has_silver_ingot", has(silver_ingot)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SILVER_LEGGINGS.get()).define('X', silver_ingot).pattern("XXX").pattern("X X").pattern("X X").unlockedBy("has_silver_ingot", has(silver_ingot)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SILVER_BOOTS.get()).define('X', silver_ingot).pattern("X X").pattern("X X").unlockedBy("has_silver_ingot", has(silver_ingot)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.YELLOW_DYE).requires(ModBlocks.DAFFODIL.get()).unlockedBy("has_daffodil", has(ModBlocks.DAFFODIL.get())).save(consumer, modId("daffodil_yellow_dye"));

        generateRecipes(consumer, ModBlockFamilies.JACARANDA_PLANKS);
        generateRecipes(consumer, ModBlockFamilies.MAGIC_PLANKS);

        planksFromLog(consumer, ModBlocks.JACARANDA_PLANKS.get(), ModTags.Items.JACARANDA_LOG, 4);
        planksFromLog(consumer, ModBlocks.MAGIC_PLANKS.get(), ModTags.Items.MAGIC_LOG, 4);
        woodFromLogs(consumer, ModBlocks.JACARANDA_WOOD.get(), ModBlocks.JACARANDA_LOG.get());
        woodFromLogs(consumer, ModBlocks.MAGIC_WOOD.get(), ModBlocks.MAGIC_LOG.get());
        woodFromLogs(consumer, ModBlocks.STRIPPED_JACARANDA_WOOD.get(), ModBlocks.STRIPPED_JACARANDA_LOG.get());
        woodFromLogs(consumer, ModBlocks.STRIPPED_MAGIC_WOOD.get(), ModBlocks.STRIPPED_MAGIC_LOG.get());
        woodenBoat(consumer, ModItems.JACARANDA_BOAT.get(), ModBlocks.JACARANDA_PLANKS.get());
        woodenBoat(consumer, ModItems.MAGIC_BOAT.get(), ModBlocks.MAGIC_PLANKS.get());
        chestBoat(consumer, ModItems.JACARANDA_CHEST_BOAT.get(), ModBlocks.JACARANDA_PLANKS.get());
        chestBoat(consumer, ModItems.MAGIC_CHEST_BOAT.get(), ModBlocks.MAGIC_PLANKS.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.WOLFSBANE_DIFFUSER.get()).pattern("XYX").pattern("YZY").pattern("OOO").define('X', planks).define('Y', diamond).define('O', obsidian).define('Z', wolfsbane_diffuser_core).unlockedBy("has_diamond", has(diamond)).save(consumer, "wolfsbane_diffuser_normal");
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get()).pattern("XYX").pattern("YZY").pattern("OOO").define('X', planks).define('Y', diamond).define('O', obsidian).define('Z', wolfsbane_diffuser_core_improved).unlockedBy("has_garlic_diffuser", has(wolfsbane_diffuser)).unlockedBy("has_diamond", has(diamond)).save(consumer, "wolfsbane_diffuser_improved");
        AlchemicalCauldronRecipeBuilder.cauldronRecipe(ModItems.WOLFSBANE_DIFFUSER_CORE.get()).withIngredient(wool).withFluid(wolfsbane).withSkills(HunterSkills.GARLIC_DIFFUSER.get()).build(consumer, modId("wolfsbane_diffuser_core"));
        AlchemicalCauldronRecipeBuilder.cauldronRecipe(ModItems.WOLFSBANE_DIFFUSER_CORE_IMPROVED.get()).withIngredient(wolfsbane_diffuser_core).withFluid(wolfsbane).withSkills(HunterSkills.GARLIC_DIFFUSER_IMPROVED.get()).experience(2.0f).build(consumer, modId("wolfsbane_diffuser_core_improved"));
    }
}
