package de.teamlapen.werewolves;

import de.teamlapen.werewolves.util.Permissions;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.permission.PermissionAPI;

public class GeneralEventHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!PermissionAPI.hasPermission(event.getPlayer(), Permissions.WEREWOLVES)) {
            event.getPlayer().sendMessage(new StringTextComponent("[" + TextFormatting.DARK_RED + "Werewolves" + TextFormatting.RESET + "] It seems like the permission plugin used is not properly set up. Make sure all players have 'werewolves.*' for the mod to work (or at least '" + Permissions.WEREWOLVES + "' to suppress this warning)."), Util.NIL_UUID);
        }
    }
}
