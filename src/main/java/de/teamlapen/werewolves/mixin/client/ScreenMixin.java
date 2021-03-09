package de.teamlapen.werewolves.mixin.client;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Screen.class)
public interface ScreenMixin {
    @Accessor("font")
    FontRenderer getFont();

    @Invoker("addButton")
    <T extends Widget> T invokeAddButton_werewolves(T button);
}
