package de.teamlapen.werewolves.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BalanceConfig {

    public final ForgeConfigSpec.IntValue drownsytime;
    public final ForgeConfigSpec.BooleanValue werewolf_form_enabled;
    public final ForgeConfigSpec.IntValue werewolf_form_duration;
    public final ForgeConfigSpec.IntValue werewolf_form_cooldown;



    BalanceConfig(ForgeConfigSpec.Builder builder) {
        drownsytime = builder.comment("in minutes").defineInRange("drownsytime",10,1, Integer.MAX_VALUE);
        werewolf_form_enabled = builder.define("werewolf_form_enabled",true);
        werewolf_form_duration = builder.comment("In seconds").defineInRange("werewolf_form_duration", Integer.MAX_VALUE, 10, Integer.MAX_VALUE);
        werewolf_form_cooldown = builder.comment("In seconds").defineInRange("werewolf_form_cooldown", 0, 0, 10000);

    }
}
