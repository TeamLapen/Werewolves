package de.teamlapen.werewolves.client.gui;

import net.minecraft.client.gui.components.AbstractWidget;

public interface ScreenModifier {

    <T extends AbstractWidget> void removeButton(T button);
}
