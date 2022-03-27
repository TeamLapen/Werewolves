package de.teamlapen.werewolves.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public final ForgeConfigSpec.BooleanValue disableWerewolfHeaven;
    public final ForgeConfigSpec.BooleanValue disableOreGen;
    public final ForgeConfigSpec.IntValue werewolfHeavenWeight;

    CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("common_server");
        this.werewolfHeavenWeight = builder.defineInRange("werewolfHeavenWeight", 6, 1, Integer.MAX_VALUE);
        this.disableWerewolfHeaven = builder.comment("Disable werewolf heaven generation").define("disableWerewolfHeaven", false);
        this.disableOreGen = builder.comment("Disable the ore generation").define("disableOreGen", false);
        builder.pop();
    }
}
