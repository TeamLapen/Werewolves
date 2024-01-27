package de.teamlapen.werewolves;

import de.teamlapen.werewolves.util.Permissions;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.WerewolvesWorld;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.permission.PermissionAPI;
import org.jetbrains.annotations.NotNull;

public class GeneralEventHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer && !PermissionAPI.getPermission((ServerPlayer) event.getEntity(), Permissions.WEREWOLVES)) {
            event.getEntity().sendSystemMessage(Component.literal("[" + ChatFormatting.DARK_RED + "Werewolves" + ChatFormatting.RESET + "] It seems like the permission plugin used is not properly set up. Make sure all players have 'werewolves.*' for the mod to work (or at least '" + Permissions.WEREWOLVES + "' to suppress this warning)."));
        }
    }

    @SubscribeEvent
    public void onAttachCapabilityWorld(@NotNull AttachCapabilitiesEvent<Level> event) {
        event.addCapability(REFERENCE.WORLD_CAP_KEY, WerewolvesWorld.createNewCapability(event.getObject()));
    }
}
