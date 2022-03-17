package de.teamlapen.werewolves.mixin.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings("UnusedReturnValue")
@Mixin(Screen.class)
public interface ScreenAccessor {
    @Accessor("font")
    Font getFont();

    @Invoker("addRenderableWidget")
    <T extends GuiEventListener & Widget & NarratableEntry> T invokeAddRenderableWidget_werewolves(T button);

    @Invoker("removeWidget")
    void removeWidget_werewolves(GuiEventListener listener);

}
