package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.vampirism.api.world.items.oil.IOil;
import de.teamlapen.vampirism.common.world.entity.player.hunter.skills.HunterSkills;
import de.teamlapen.vampirism.data.builder.AlchemyTableRecipeBuilder;
import de.teamlapen.vampirism.data.provider.base.VampirismRecipeProvider;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.core.ModItems;
import de.teamlapen.werewolves.common.tags.ModItemTags;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatBlocks;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatItems;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatOils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class CompatRecipeProvider extends VampirismRecipeProvider {

    protected HolderLookup.RegistryLookup<Item> itemLookup = this.registries.lookupOrThrow(Registries.ITEM);

    protected CompatRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        TagKey<Item> diamond = Tags.Items.GEMS_DIAMOND;
        TagKey<Item> obsidian = Tags.Items.OBSIDIANS;

        upToThreeQuarrelRecipe(CompatItems.QUARREL_SILVER, CompatOils.SILVER_OIL_1);
        upToThreeQuarrelRecipe(CompatItems.QUARREL_SILVER, CompatOils.SILVER_OIL_2, BuiltInRegistries.ITEM.getKey(CompatItems.QUARREL_SILVER.get()) + "_2");

        alchemyTable(CompatOils.SILVER_OIL_1)
                .bloodOilIngredient()
                .input(Ingredient.of(this.itemLookup.getOrThrow(ModItemTags.SILVER_INGOT))).unlockedBy("has_silver_ingot", has(ModItemTags.SILVER_INGOT))
                .save(output, modId("silver_oil_1").toString());
        alchemyTable(CompatOils.SILVER_OIL_2)
                .oilIngredient(CompatOils.SILVER_OIL_1).unlockedBy("has_silver_oil_1", has(de.teamlapen.vampirism.common.core.ModItems.OIL_BOTTLE.get()))
                .input(Ingredient.of(this.itemLookup.getOrThrow(ModItemTags.SILVER_INGOT))).unlockedBy("has_silver_ingot", has(ModItemTags.SILVER_INGOT))
                .save(output, modId("silver_oil_2").toString());

        cauldronRecipe(CompatItems.WOLFSBANE_DIFFUSER_CORE).withIngredient(ItemTags.WOOL).withFluid(ModItems.WOLFSBANE).withSkills(HunterSkills.VAMPIRE_REPELLING).save(output);
        cauldronRecipe(CompatItems.WOLFSBANE_DIFFUSER_CORE_STRONG).withIngredient(CompatItems.WOLFSBANE_DIFFUSER_CORE).withFluid(de.teamlapen.vampirism.common.core.ModItems.HOLY_WATER_BOTTLE_ULTIMATE).withSkills(HunterSkills.AUGMENTED_DIFFUSERS).experience(2.0f).save(output);
        cauldronRecipe(CompatItems.WOLFSBANE_DIFFUSER_CORE_LONG).withIngredient(CompatItems.WOLFSBANE_DIFFUSER_CORE).withFluid(ModItems.WOLFSBANE).withSkills(HunterSkills.AUGMENTED_DIFFUSERS).experience(2.0f).save(output);
        diffuser(CompatBlocks.WOLFSBANE_DIFFUSER.get(), CompatItems.WOLFSBANE_DIFFUSER_CORE.get(), diamond, obsidian);
        diffuser(CompatBlocks.WOLFSBANE_DIFFUSER_STRONG.get(), CompatItems.WOLFSBANE_DIFFUSER_CORE_STRONG.get(), diamond, obsidian);
        diffuser(CompatBlocks.WOLFSBANE_DIFFUSER_LONG.get(), CompatItems.WOLFSBANE_DIFFUSER_CORE_LONG.get(), diamond, obsidian);
    }

    protected void diffuser(ItemLike result, ItemLike core, TagKey<Item> diamond, TagKey<Item> obsidian) {
        shaped(RecipeCategory.DECORATIONS, result).pattern("XYX").pattern("YZY").pattern("OOO").define('X', ItemTags.PLANKS).define('Y', diamond).define('O', obsidian).define('Z', core).unlockedBy("has_core", has(core)).save(output);
    }

    protected AlchemyTableRecipeBuilder alchemyTable(Holder<IOil> oilStack) {
        return AlchemyTableRecipeBuilder.builder(this.itemLookup, oilStack);
    }

    private static Identifier modId(String name) {
        return WIdentifier.mod(name);
    }

    public static class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new CompatRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "Vampirism Recipes";
        }
    }
}
