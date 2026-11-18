package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.vampirism.api.world.items.oil.IOil;
import de.teamlapen.vampirism.common.world.entity.player.hunter.skills.HunterSkills;
import de.teamlapen.vampirism.data.builder.AlchemyTableRecipeBuilder;
import de.teamlapen.vampirism.data.provider.ModRecipeProvider;
import de.teamlapen.vampirism.data.provider.base.VampirismRecipeProvider;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.core.ModBlocks;
import de.teamlapen.werewolves.common.core.ModItems;
import de.teamlapen.werewolves.common.core.ModTags;
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
import net.minecraft.world.item.ItemStackTemplate;
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
                .input(Ingredient.of(this.itemLookup.getOrThrow(ModTags.Items.SILVER_INGOT))).unlockedBy("has_silver_ingot", has(ModTags.Items.SILVER_INGOT))
                .save(output, modId("silver_oil_1").toString());
        alchemyTable(CompatOils.SILVER_OIL_2)
                .oilIngredient(CompatOils.SILVER_OIL_1).unlockedBy("has_silver_oil_1", has(de.teamlapen.vampirism.common.core.ModItems.OIL_BOTTLE.get()))
                .input(Ingredient.of(this.itemLookup.getOrThrow(ModTags.Items.SILVER_INGOT))).unlockedBy("has_silver_ingot", has(ModTags.Items.SILVER_INGOT))
                .save(output, modId("silver_oil_2").toString());

        cauldronRecipe(CompatItems.WOLFSBANE_DIFFUSER_CORE).withIngredient(ItemTags.WOOL).withFluid(ModItems.WOLFSBANE).withSkills(HunterSkills.REFINED_GARLIC).save(output);
        cauldronRecipe(CompatItems.WOLFSBANE_DIFFUSER_CORE_IMPROVED).withIngredient(CompatItems.WOLFSBANE_DIFFUSER_CORE).withFluid(ModItems.WOLFSBANE).withSkills(HunterSkills.REFINED_GARLIC).experience(2.0f).save(output);
        shaped(RecipeCategory.DECORATIONS, CompatBlocks.WOLFSBANE_DIFFUSER_LONG.get()).pattern("XYX").pattern("YZY").pattern("OOO").define('X', ItemTags.PLANKS).define('Y', diamond).define('O', obsidian).define('Z', CompatItems.WOLFSBANE_DIFFUSER_CORE).unlockedBy("has_diamond", has(diamond)).save(output, modId("wolfsbane_diffuser_normal").toString());
        shaped(RecipeCategory.DECORATIONS, CompatBlocks.WOLFSBANE_DIFFUSER.get()).pattern("XYX").pattern("YZY").pattern("OOO").define('X', ItemTags.PLANKS).define('Y', diamond).define('O', obsidian).define('Z', CompatItems.WOLFSBANE_DIFFUSER_CORE_IMPROVED).unlockedBy("has_garlic_diffuser", has(CompatItems.WOLFSBANE_DIFFUSER)).unlockedBy("has_diamond", has(diamond)).save(output, modId("wolfsbane_diffuser_improved").toString());
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
