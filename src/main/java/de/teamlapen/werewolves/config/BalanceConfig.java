package de.teamlapen.werewolves.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BalanceConfig {

    public final ForgeConfigSpec.IntValue DROWNSYTIME;

    BalanceConfig(ForgeConfigSpec.Builder builder) {
        DROWNSYTIME = builder.comment("in minutes").defineInRange("drownsytime",10,1, Integer.MAX_VALUE);
    }
}
