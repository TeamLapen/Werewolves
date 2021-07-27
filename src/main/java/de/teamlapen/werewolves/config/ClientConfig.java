package de.teamlapen.werewolves.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public final ForgeConfigSpec.BooleanValue disableScreenFurRendering;
    public final ForgeConfigSpec.BooleanValue disableFangCrosshairRendering;

    ClientConfig(ForgeConfigSpec.Builder builder) {
        this.disableScreenFurRendering = builder.comment("Disables the fur border at the screen edge in werewolf form").define("disableScreenFurRendering", false);
        this.disableFangCrosshairRendering = builder.comment("Disables fangs crosshair when you can bite in werewolf form").define("disableFangCrosshairRendering", false);
    }
}
