package de.teamlapen.werewolves.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {

    public final ForgeConfigSpec.BooleanValue disableWerewolfHeaven;

    ServerConfig(ForgeConfigSpec.Builder builder) {

        disableWerewolfHeaven = builder.comment("Disable werewolf heaven generation").define("disableWerewolfHeaven", false);
    }
}
