package de.teamlapen.werewolves.common.config;


import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {

    public final ModConfigSpec.BooleanValue disableScreenFurRendering;
    public final ModConfigSpec.BooleanValue disableFangCrosshairRendering;
    public final ModConfigSpec.BooleanValue renderScreenOverlay;

    public final ModConfigSpec.BooleanValue mcaMessage;

    ClientConfig(ModConfigSpec.Builder builder) {
        this.disableScreenFurRendering = builder.comment("Disables the fur border at the screen edge in werewolf form").define("disableScreenFurRendering", false);
        this.disableFangCrosshairRendering = builder.comment("Disables fangs crosshair when you can bite in werewolf form").define("disableFangCrosshairRendering", false);
        this.renderScreenOverlay = builder.comment("Render a colored overlay at the screen edges, e.g. when an entity notices you with the sixth sense skill or while raging").define("renderScreenOverlay", true);

        builder.push("internal");
        this.mcaMessage = builder.comment("If the mca integration message was send").define("mcaMessage", false);
        builder.pop();
    }
}
