package de.teamlapen.werewolves.modcompat.jei;

import de.teamlapen.werewolves.util.REFERENCE;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
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
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration) {
    }

}
