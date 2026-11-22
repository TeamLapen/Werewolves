package de.teamlapen.werewolves.common.integration.jei.vampirism;

import mezz.jei.api.runtime.IIngredientManager;

import java.util.Comparator;
import java.util.List;

public class WolfsbaneDiffuserRecipeMaker {

    public static List<WolfsbaneDiffuserRecipe> getRecipes(IIngredientManager ingredientManager, VampirismIntegrationRefs refs) {
        return ingredientManager.getAllItemStacks().stream()
                .<WolfsbaneDiffuserRecipe>mapMulti((stack, consumer) -> refs.burnTime(stack.typeHolder()).ifPresent(burnTime -> consumer.accept(new WolfsbaneDiffuserRecipe(stack, burnTime))))
                .sorted(Comparator.comparingInt(WolfsbaneDiffuserRecipe::burnTime)).toList();
    }
}
