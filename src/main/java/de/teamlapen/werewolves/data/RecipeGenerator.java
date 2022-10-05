package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.data.recipebuilder.AlchemyTableRecipeBuilder;
import de.teamlapen.vampirism.data.recipebuilder.ShapedWeaponTableRecipeBuilder;
import de.teamlapen.vampirism.util.NBTIngredient;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModOils;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private static ResourceLocation modId(String name) {
        return new ResourceLocation(REFERENCE.MODID, name);
    }

    @Override
    protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        ITag<Item> sticks = Tags.Items.RODS_WOODEN;
        ITag<Item> silver_ingot = ModTags.Items.SILVER_INGOT;
        ITag<Item> silver_nugget = ModTags.Items.SILVER_NUGGET;
        ITag<Item> iron_ingot = Tags.Items.INGOTS_IRON;
        ITag<Item> feathers = Tags.Items.FEATHERS;
        IItemProvider crossbow_arrow = ModItems.V.CROSSBOW_ARROW_NORMAL.get();
        IItemProvider magic_log = ModBlocks.MAGIC_LOG.get();
        IItemProvider jacaranda_log = ModBlocks.JACARANDA_LOG.get();
        IItemProvider cracked_bone = ModItems.CRACKED_BONE.get();
        IItemProvider wolfsbane = ModBlocks.WOLFSBANE.get();

        CookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.SILVER_ORE), ModItems.SILVER_INGOT.get(), 0.7F, 200).unlockedBy("has_silver_ore", has(ModTags.Items.SILVER_ORE)).save(consumer);
        CookingRecipeBuilder.blasting(Ingredient.of(ModTags.Items.SILVER_ORE), ModItems.SILVER_INGOT.get(), 0.7F, 100).unlockedBy("has_silver_ore", has(ModTags.Items.SILVER_ORE)).save(consumer, modId("silver_ingot_from_blasting"));

        ShapelessRecipeBuilder.shapeless(ModBlocks.MAGIC_PLANKS.get(), 4)
                .requires(magic_log).unlockedBy("has_magic_log", has(magic_log))
                .save(consumer, modId("magic_planks_from_magic_log"));
        ShapelessRecipeBuilder.shapeless(Blocks.OAK_PLANKS, 4)
                .requires(jacaranda_log).unlockedBy("has_jacaranda_log", has(jacaranda_log))
                .save(consumer, modId("oak_planks_from_jacaranda_log"));
        ShapelessRecipeBuilder.shapeless(Items.BONE, 2)
                .requires(cracked_bone).unlockedBy("has_broken_bone", has(cracked_bone))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE)
                .requires(wolfsbane).unlockedBy("has_wolfsbane", has(wolfsbane))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.SILVER_INGOT.get(), 9)
                .requires(ModBlocks.SILVER_BLOCK.get()).unlockedBy("has_silver_block", has(ModBlocks.SILVER_BLOCK.get()))
                .save(consumer, modId("iron_ingot_from_iron_block"));
        ShapelessRecipeBuilder.shapeless(ModItems.SILVER_NUGGET.get(), 9)
                .requires(silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer, modId("silver_nugget"));


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
        ShapedRecipeBuilder.shaped(ModBlocks.SILVER_BLOCK.get()).pattern("###").pattern("###").pattern("###")
                .define('#', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SILVER_INGOT.get()).pattern("###").pattern("###").pattern("###")
                .define('#', silver_nugget).unlockedBy("has_silver_nugget", has(silver_nugget))
                .save(consumer, modId("silver_ingot_from_nuggets"));

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
    }

}
