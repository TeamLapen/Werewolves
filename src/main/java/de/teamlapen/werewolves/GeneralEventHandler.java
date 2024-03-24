package de.teamlapen.werewolves;

import de.teamlapen.werewolves.util.Permissions;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.server.permission.PermissionAPI;
import org.jetbrains.annotations.NotNull;

public class GeneralEventHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer && !PermissionAPI.getPermission((ServerPlayer) event.getEntity(), Permissions.WEREWOLVES)) {
            event.getEntity().sendSystemMessage(Component.literal("[" + ChatFormatting.DARK_RED + "Werewolves" + ChatFormatting.RESET + "] It seems like the permission plugin used is not properly set up. Make sure all players have 'werewolves.*' for the mod to work (or at least '" + Permissions.WEREWOLVES + "' to suppress this warning)."));
        }
    }

}
