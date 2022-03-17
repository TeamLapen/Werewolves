package de.teamlapen.werewolves.modcompat.jei;

import com.google.common.base.Objects;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModRegistries;
import de.teamlapen.werewolves.inventory.recipes.TagNBTBrewingRecipe;
import de.teamlapen.werewolves.items.oil.IOil;
import de.teamlapen.werewolves.util.OilUtils;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WeaponOilHelper;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

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
            return OilUtils.getOil(stack).getRegistryName().toString();
        });
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        registerOilBrewingRecipes(registration);
        registerOilApplyRecipes(registration);
    }

    private void registerOilBrewingRecipes(@Nonnull IRecipeRegistration registration) {
        BrewingRecipeUtil util = new BrewingRecipeUtil(Internal.getIngredientManager().getIngredientHelper(VanillaTypes.ITEM));
        Set<IJeiBrewingRecipe> recipes = new HashSet<>();
        BrewingRecipeRegistry.getRecipes().stream()
                .filter(TagNBTBrewingRecipe.class::isInstance)
                .map(TagNBTBrewingRecipe.class::cast)
                .forEach(brewingRecipe -> recipes.add(new OilJeiBrewingRecipe(Arrays.asList(brewingRecipe.getIngredient()), Arrays.asList(brewingRecipe.getInput().getItems()), brewingRecipe.getOutput(), util)));
        registration.addRecipes(recipes, VanillaRecipeCategoryUid.BREWING);
    }

    private void registerOilApplyRecipes(@Nonnull IRecipeRegistration registration) {
        Collection<IOil> oils = ModRegistries.WEAPON_OILS.getValues();
        List<Pair<IOil, ItemStack>> items = oils.stream().flatMap(x -> ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).filter(x::canBeAppliedTo).map(y -> Pair.of(x, y))).collect(Collectors.toList());
        List<ShapelessRecipe> recipes = items.stream().map(x -> new ShapelessRecipe(new ResourceLocation(REFERENCE.MODID, ("" + x.getKey().getRegistryName() + x.getValue().getItem().getRegistryName()).replace(':', '_')), "", WeaponOilHelper.setWeaponOils(x.getRight().copy(), x.getLeft(), x.getLeft().getMaxDuration(x.getRight())), NonNullList.of(Ingredient.EMPTY, Ingredient.of(x.getRight().copy()), Ingredient.of(OilUtils.setOil(new ItemStack(ModItems.oil_bottle), x.getLeft()))))).collect(Collectors.toList());
        registration.addRecipes(recipes, VanillaRecipeCategoryUid.CRAFTING);
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

    public static class OilApplyRecipe {

    }
}
