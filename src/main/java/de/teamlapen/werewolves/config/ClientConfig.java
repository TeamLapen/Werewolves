package de.teamlapen.werewolves.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public final ForgeConfigSpec.BooleanValue disableScreenFurRendering;
    public final ForgeConfigSpec.BooleanValue disableFangCrosshairRendering;

    public final ForgeConfigSpec.BooleanValue mcaMessage;

    ClientConfig(ForgeConfigSpec.Builder builder) {
        this.disableScreenFurRendering = builder.comment("Disables the fur border at the screen edge in werewolf form").define("disableScreenFurRendering", false);
        this.disableFangCrosshairRendering = builder.comment("Disables fangs crosshair when you can bite in werewolf form").define("disableFangCrosshairRendering", false);

        builder.push("internal");
        this.mcaMessage = builder.comment("If the mca integration message was send").define("mcaMessage", false);
        builder.pop();
    }
}
