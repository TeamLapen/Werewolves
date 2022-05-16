package de.teamlapen.werewolves.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public final ForgeConfigSpec.BooleanValue disableWerewolfHeaven;
    public final ForgeConfigSpec.BooleanValue disableOreGen;
    public final ForgeConfigSpec.IntValue werewolfHeavenWeightTerrablender;

    CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("common_server");
        this.werewolfHeavenWeightTerrablender = builder.comment("Only considered if terrablender installed. Heigher values increase Werewolf region weight (likelyhood to appear)").defineInRange("werewolfHeavenWeightTerrablender", 2, 1, 1000);
        this.disableWerewolfHeaven = builder.comment("Disable werewolf heaven generation").define("disableWerewolfHeaven", false);
        this.disableOreGen = builder.comment("Disable the ore generation").define("disableOreGen", false);
        builder.pop();
    }
}
