package de.teamlapen.werewolves.util;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;
import org.jetbrains.annotations.NotNull;

public class Permissions {
    public static final PermissionNode<Boolean> WEREWOLVES = new PermissionNode<>(REFERENCE.MODID, "check", PermissionTypes.BOOLEAN, ((player, playerUUID, context) -> true));
    public static final PermissionNode<Boolean> FORM = new PermissionNode<>(REFERENCE.MODID, "form", PermissionTypes.BOOLEAN, ((player, playerUUID, context) -> true));
    public static final PermissionNode<Boolean> TRANSFORMATION = new PermissionNode<>(REFERENCE.MODID, "form.transform", PermissionTypes.BOOLEAN, ((player, playerUUID, context) -> true));
    public static final PermissionNode<Boolean> BITE = new PermissionNode<>(REFERENCE.MODID, "bite", PermissionTypes.BOOLEAN, ((player, playerUUID, context) -> true));
    public static final PermissionNode<Boolean> BITE_PLAYER = new PermissionNode<>(REFERENCE.MODID, "bite.player", PermissionTypes.BOOLEAN, ((player, playerUUID, context) -> true));
    public static final PermissionNode<Boolean> INFECT_PLAYER = new PermissionNode<>(REFERENCE.MODID, "infect", PermissionTypes.BOOLEAN, ((player, playerUUID, context) -> true));

    @SubscribeEvent
    public static void registerNodes(PermissionGatherEvent.@NotNull Nodes event) {
        event.addNodes(WEREWOLVES, FORM, TRANSFORMATION, BITE, BITE_PLAYER, INFECT_PLAYER);
    }
}
