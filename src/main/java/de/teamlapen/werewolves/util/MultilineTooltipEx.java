package de.teamlapen.werewolves.util;

import de.teamlapen.lib.lib.util.MultilineTooltip;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MultilineTooltipEx extends MultilineTooltip {

    public MultilineTooltipEx(List<Component> components) {
        super(components);
    }

    @Override
    protected @NotNull ClientTooltipPositioner createTooltipPositioner(boolean p_306065_, boolean p_306270_, @NotNull ScreenRectangle p_305822_) {
        return DefaultTooltipPositioner.INSTANCE;
    }
}
