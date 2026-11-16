package de.teamlapen.werewolves.common.integration.jei;

import de.teamlapen.werewolves.api.WIdentifier;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.Identifier;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
@JeiPlugin
public class WerewolvesJEIPlugins implements IModPlugin {

    private static final Identifier ID = WIdentifier.mod("plugin");

    @Nonnull
    @Override
    public Identifier getPluginUid() {
        return ID;
    }

    @Override
    public void registerItemSubtypes(@Nonnull ISubtypeRegistration registration) {
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
    }

}
