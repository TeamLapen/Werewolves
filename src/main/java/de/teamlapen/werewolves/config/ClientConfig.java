package de.teamlapen.werewolves.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public final ForgeConfigSpec.BooleanValue disableScreenFurRendering;

    ClientConfig(ForgeConfigSpec.Builder builder) {
        disableScreenFurRendering = builder.comment("Disables the fur border at the screen edge in werewolf form").define("disableScreenFurRendering", false);
    }
}
