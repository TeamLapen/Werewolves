package de.teamlapen.werewolves;

import de.teamlapen.werewolves.util.Permissions;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.permission.PermissionAPI;
import org.jetbrains.annotations.NotNull;

public class GeneralEventHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.@NotNull PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer && !PermissionAPI.getPermission((ServerPlayer) event.getEntity(), Permissions.WEREWOLVES)) {
            event.getEntity().sendSystemMessage(Component.literal("[" + ChatFormatting.DARK_RED + "Werewolves" + ChatFormatting.RESET + "] It seems like the permission plugin used is not properly set up. Make sure all players have 'werewolves.*' for the mod to work (or at least '" + Permissions.WEREWOLVES + "' to suppress this warning)."));
        }
    }
}
