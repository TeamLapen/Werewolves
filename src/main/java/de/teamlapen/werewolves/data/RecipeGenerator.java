package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.data.recipebuilder.ShapedWeaponTableRecipeBuilder;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModRecipes;
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
        IItemProvider crossbow_arrow = ModItems.V.crossbow_arrow_normal.get();
        IItemProvider magic_log = ModBlocks.magic_log.get();
        IItemProvider jacaranda_log = ModBlocks.jacaranda_log.get();
        IItemProvider cracked_bone = ModItems.cracked_bone.get();
        IItemProvider wolfsbane = ModBlocks.wolfsbane.get();

        CookingRecipeBuilder.smelting(Ingredient.of(ModTags.Items.SILVER_ORE), ModItems.silver_ingot.get(), 0.7F, 200).unlockedBy("has_silver_ore", has(ModTags.Items.SILVER_ORE)).save(consumer);
        CookingRecipeBuilder.blasting(Ingredient.of(ModTags.Items.SILVER_ORE), ModItems.silver_ingot.get(), 0.7F, 100).unlockedBy("has_silver_ore", has(ModTags.Items.SILVER_ORE)).save(consumer, modId("silver_ingot_from_blasting"));

        ShapelessRecipeBuilder.shapeless(ModBlocks.magic_planks.get(), 4)
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
        ShapelessRecipeBuilder.shapeless(ModItems.silver_ingot.get(), 9)
                .requires(ModBlocks.silver_block.get()).unlockedBy("has_silver_block", has(ModBlocks.silver_block.get()))
                .save(consumer, modId("iron_ingot_from_iron_block"));
        ShapelessRecipeBuilder.shapeless(ModItems.silver_nugget.get(), 9)
                .requires(silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer, modId("silver_nugget"));


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
        ShapedRecipeBuilder.shaped(ModBlocks.silver_block.get()).pattern("###").pattern("###").pattern("###")
                .define('#', silver_ingot).unlockedBy("has_silver_ingot", has(silver_ingot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.silver_ingot.get()).pattern("###").pattern("###").pattern("###")
                .define('#', silver_nugget).unlockedBy("has_silver_nugget", has(silver_nugget))
                .save(consumer, modId("silver_ingot_from_nuggets"));

        CustomRecipeBuilder.special(ModRecipes.weapon_oil.get()).save(consumer, REFERENCE.MODID + ":weapon_oil");

        ShapedWeaponTableRecipeBuilder.shapedWeaponTable(ModItems.crossbow_arrow_silver_bolt.get(), 3).pattern(" X ").pattern("XYX").pattern(" S ").pattern(" F ")
                .lava(1)
                .define('S', sticks).unlockedBy("hasSticks", has(sticks))
                .define('X', silver_nugget).unlockedBy("has_silver_nugget", has(silver_nugget))
                .define('F', feathers).unlockedBy("has_feathers", has(feathers))
                .define('Y', iron_ingot).unlockedBy("has_iron", has(iron_ingot))
                .unlockedBy("has_crossbow_arrow", has(crossbow_arrow))
                .save(consumer, modId("crossbow_arrow_silver_bolt"));
    }

}
