package de.teamlapen.werewolves.mixin;

import net.minecraft.client.gui.IngameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(IngameGui.class)
public interface InGameGuiAccessor {
    @Accessor("scaledWidth")
    int getScaledWidth();

    @Accessor("scaledHeight")
    int getScaledHeight();
}
