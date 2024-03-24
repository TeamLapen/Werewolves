package de.teamlapen.werewolves.config;


import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {

    public final ModConfigSpec.BooleanValue disableScreenFurRendering;
    public final ModConfigSpec.BooleanValue disableFangCrosshairRendering;

    public final ModConfigSpec.BooleanValue mcaMessage;

    ClientConfig(ModConfigSpec.Builder builder) {
        this.disableScreenFurRendering = builder.comment("Disables the fur border at the screen edge in werewolf form").define("disableScreenFurRendering", false);
        this.disableFangCrosshairRendering = builder.comment("Disables fangs crosshair when you can bite in werewolf form").define("disableFangCrosshairRendering", false);

        builder.push("internal");
        this.mcaMessage = builder.comment("If the mca integration message was send").define("mcaMessage", false);
        builder.pop();
    }
}
