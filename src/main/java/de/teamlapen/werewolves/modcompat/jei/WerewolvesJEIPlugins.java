package de.teamlapen.werewolves.modcompat.jei;

import com.google.common.base.Objects;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.inventory.recipes.TagBrewingRecipe;
import de.teamlapen.werewolves.util.OilUtils;
import de.teamlapen.werewolves.util.REFERENCE;
import mezz.jei.Internal;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.plugins.vanilla.brewing.BrewingRecipeUtil;
import mezz.jei.plugins.vanilla.brewing.JeiBrewingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JeiPlugin
public class WerewolvesJEIPlugins implements IModPlugin {

    private static final ResourceLocation ID = new ResourceLocation(REFERENCE.MODID, "plugin");

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerItemSubtypes(@Nonnull ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(ModItems.oil_bottle, (stack, context) -> {
            return OilUtils.getOilOpt(stack).map(iOil -> iOil.getRegistryName().toString()).orElse("");
        });
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        BrewingRecipeUtil util = new BrewingRecipeUtil(Internal.getIngredientManager().getIngredientHelper(VanillaTypes.ITEM));
        Set<IJeiBrewingRecipe> recipes = new HashSet<>();
        BrewingRecipeRegistry.getRecipes().stream()
                .filter(TagBrewingRecipe.class::isInstance)
                .map(TagBrewingRecipe.class::cast)
                .forEach(brewingRecipe -> recipes.add(new OilJeiBrewingRecipe(Arrays.asList(brewingRecipe.getIngredient()), Arrays.asList(brewingRecipe.getInput().getItems()), brewingRecipe.getOutput(), util)));
        registration.addRecipes(recipes, VanillaRecipeCategoryUid.BREWING);
    }

    public static class OilJeiBrewingRecipe extends JeiBrewingRecipe {

        private final int hashCode;

        public OilJeiBrewingRecipe(List<ItemStack> ingredients, List<ItemStack> potionInputs, ItemStack potionOutput, BrewingRecipeUtil brewingRecipeUtil) {
            super(ingredients, potionInputs, potionOutput, brewingRecipeUtil);

            ItemStack firstIngredient = ingredients.get(0);
            ItemStack firstInput = potionInputs.get(0);
            this.hashCode = Objects.hashCode(firstInput.getItem(), OilUtils.getOil(firstInput),
                    potionOutput.getItem(), OilUtils.getOil(potionOutput),
                    firstIngredient.getItem());
        }

        @Override
        public int hashCode() {
            return hashCode;
        }
    }
}
