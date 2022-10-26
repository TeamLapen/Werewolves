package de.teamlapen.werewolves.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public final ForgeConfigSpec.BooleanValue disableWerewolfHeaven;
    public final ForgeConfigSpec.IntValue werewolfHeavenWeightTerrablender;
    public final ForgeConfigSpec.IntValue silverOreWeight;

    CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("common_server");
        this.werewolfHeavenWeightTerrablender = builder.comment("Only considered if terrablender installed. Heigher values increase Werewolf region weight (likelyhood to appear)").defineInRange("werewolfHeavenWeightTerrablender", 2, 1, 1000);
        this.disableWerewolfHeaven = builder.comment("Disable werewolf heaven generation").define("disableWerewolfHeaven", false);
        this.silverOreWeight = builder.comment("Weight of silver ore spawning").defineInRange("silverOreWeight", 30, 0, 1000);
        builder.pop();
    }
}
