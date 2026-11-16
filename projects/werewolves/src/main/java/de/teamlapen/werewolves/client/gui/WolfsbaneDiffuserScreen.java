package de.teamlapen.werewolves.client.gui;

import de.teamlapen.vampirism.client.gui.screens.diffuser.DiffuserScreen;
import de.teamlapen.werewolves.common.world.inventory.diffuser.WolfsbaneDiffuserMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class WolfsbaneDiffuserScreen extends DiffuserScreen<WolfsbaneDiffuserMenu> {


    public WolfsbaneDiffuserScreen(WolfsbaneDiffuserMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected int getProgressBarColor() {
        return 0xD0D0FF;
    }
}
