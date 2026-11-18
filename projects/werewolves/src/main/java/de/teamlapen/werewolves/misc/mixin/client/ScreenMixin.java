package de.teamlapen.werewolves.misc.mixin.client;

import de.teamlapen.werewolves.api.client.gui.ScreenAccessor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin implements ScreenAccessor {

    @Shadow
    @Final
    public List<Renderable> renderables;

    @Shadow
    @Final
    private List<GuiEventListener> children;

    @Shadow
    @Final
    private List<NarratableEntry> narratables;

    @Override
    public <T extends AbstractWidget & GuiEventListener & NarratableEntry> @NonNull T werewolves$addRenderableWidget(@NonNull T button) {
        this.renderables.add(button);
        this.children.add(button);
        this.narratables.add(button);
        return button;
    }

}
