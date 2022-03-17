package de.teamlapen.werewolves.mixin.client;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Gui.class)
public interface InGameGuiAccessor {
    @Accessor("screenWidth")
    int getScaledWidth();

    @Accessor("screenHeight")
    int getScaledHeight();
}
