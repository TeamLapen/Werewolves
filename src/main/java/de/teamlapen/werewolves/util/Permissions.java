package de.teamlapen.werewolves.util;

import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

public class Permissions {
    public static final String WEREWOLVES = "werewolves.check";
    public static final String FORM = "werewolves.form";
    public static final String TRANSFORMATION = "werewolves.form.transform";
    public static final String BITE = "werewolves.bite";
    public static final String BITE_PLAYER = "werewolves.bite.player";
    public static final String INFECT_PLAYER = "werewolves.infect";

    public static void init() {
        PermissionAPI.registerNode(WEREWOLVES, DefaultPermissionLevel.ALL, "Used to check if permission system works");
        PermissionAPI.registerNode(FORM, DefaultPermissionLevel.ALL, "Allow being in werewolf form");
        PermissionAPI.registerNode(TRANSFORMATION, DefaultPermissionLevel.ALL, "Allow transforming into the werewolf form");
        PermissionAPI.registerNode(BITE, DefaultPermissionLevel.ALL, "Allow werewolves to bite");
        PermissionAPI.registerNode(BITE_PLAYER, DefaultPermissionLevel.ALL, "Allow werewolves to bite player");
        PermissionAPI.registerNode(INFECT_PLAYER, DefaultPermissionLevel.ALL, "Allow player to infect other players");
    }
}
