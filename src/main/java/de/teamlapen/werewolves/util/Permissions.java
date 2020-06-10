package de.teamlapen.werewolves.util;

import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

public class Permissions {
    public static final String TRUE_FORM = "werewolves.transform";

    public static void init() {
        PermissionAPI.registerNode(TRUE_FORM, DefaultPermissionLevel.ALL, "Allow transforming into the werewolf form");
    }
}
