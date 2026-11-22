package de.teamlapen.werewolves.common.integration.jei.vampirism;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.util.REFERENCE;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * JEI support for the content of the Vampirism integration.
 * <p>
 * The content is looked up by id and class name ({@link VampirismIntegrationRefs}), so this plugin does not depend on the Vampirism integration at compile time.
 * JEI loads this plugin even if Vampirism is not installed, so nothing is registered then.
 */
@SuppressWarnings("unused")
@JeiPlugin
public class WerewolvesVampirismJEIPlugin implements IModPlugin {

    public static final IRecipeType<WolfsbaneDiffuserRecipe> WOLFSBANE_DIFFUSER = IRecipeType.create(REFERENCE.MODID, "wolfsbane_diffuser", WolfsbaneDiffuserRecipe.class);

    private static final Identifier ID = WIdentifier.mod("vampirism_plugin");

    @Nullable
    private static VampirismIntegrationRefs refs;

    /**
     * @return the Vampirism integration content or null if Vampirism is not loaded
     */
    @Nullable
    private static VampirismIntegrationRefs refs() {
        if (refs == null && ModList.get().isLoaded(REFERENCE.VMODID)) {
            refs = VampirismIntegrationRefs.resolve();
        }
        return refs;
    }

    @Nonnull
    @Override
    public Identifier getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(@Nonnull IRecipeCategoryRegistration registration) {
        VampirismIntegrationRefs refs = refs();
        if (refs == null) return;
        registration.addRecipeCategories(new WolfsbaneDiffuserRecipeCategory(registration.getJeiHelpers().getGuiHelper(), refs.diffusers().getFirst()));
    }

    @Override
    public void registerGuiHandlers(@Nonnull IGuiHandlerRegistration registration) {
        VampirismIntegrationRefs refs = refs();
        if (refs == null) return;
        registration.addRecipeClickArea(refs.screen(), 45, 55, 14, 14, WOLFSBANE_DIFFUSER);
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
        VampirismIntegrationRefs refs = refs();
        if (refs == null) return;
        registration.addRecipes(WOLFSBANE_DIFFUSER, WolfsbaneDiffuserRecipeMaker.getRecipes(registration.getIngredientManager(), refs));
    }

    @Override
    public void registerRecipeCatalysts(@Nonnull IRecipeCatalystRegistration registration) {
        VampirismIntegrationRefs refs = refs();
        if (refs == null) return;
        registration.addCraftingStation(WOLFSBANE_DIFFUSER, refs.diffusers().toArray(Block[]::new));
    }
}
