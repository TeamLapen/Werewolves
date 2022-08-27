package de.teamlapen.werewolves.modcompat.jei;

import de.teamlapen.werewolves.util.REFERENCE;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@JeiPlugin
public class WerewolvesJEIPlugins implements IModPlugin {

    private static final ResourceLocation ID = new ResourceLocation(REFERENCE.MODID, "plugin");

    @NotNull
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

}
