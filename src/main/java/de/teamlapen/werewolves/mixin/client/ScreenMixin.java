package de.teamlapen.werewolves.mixin.client;

import de.teamlapen.werewolves.client.gui.ScreenModifier;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractContainerEventHandler implements ScreenModifier {

    @Shadow @Final protected List<GuiEventListener> children;

    @Shadow @Final protected List<AbstractWidget> buttons;

    private ScreenMixin() {
    }

    @Override
    public <T extends AbstractWidget> void removeButton(T button) {
        this.children.remove(button);
        this.buttons.remove(button);
    }
}
