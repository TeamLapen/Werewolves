package de.teamlapen.werewolves.mixin.client;

import de.teamlapen.werewolves.client.gui.ScreenModifier;
import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin extends FocusableGui implements ScreenModifier {

    @Shadow @Final protected List<IGuiEventListener> children;

    @Shadow @Final protected List<Widget> buttons;

    private ScreenMixin() {
    }

    @Override
    public <T extends Widget> void removeButton(T button) {
        this.children.remove(button);
        this.buttons.remove(button);
    }
}
