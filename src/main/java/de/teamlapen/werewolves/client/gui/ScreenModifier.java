package de.teamlapen.werewolves.client.gui;

import net.minecraft.client.gui.widget.Widget;

public interface ScreenModifier {

    <T extends Widget> void removeButton(T button);
}
