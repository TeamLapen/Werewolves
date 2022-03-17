package de.teamlapen.werewolves.mixin.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings("UnusedReturnValue")
@Mixin(Screen.class)
public interface ScreenAccessor {
    @Accessor("font")
    Font getFont();

    @Invoker("addButton")
    <T extends AbstractWidget> T invokeAddButton_werewolves(T button);
}
