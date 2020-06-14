package de.teamlapen.werewolves.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BalanceConfig {

    public final ForgeConfigSpec.IntValue drownsytime;

    public final ForgeConfigSpec.DoubleValue werewolf_speed_amount;
    public final ForgeConfigSpec.DoubleValue werewolf_armor_toughness;
    public final ForgeConfigSpec.DoubleValue werewolf_damage;

    public final ForgeConfigSpec.BooleanValue werewolf_form_enabled;
    public final ForgeConfigSpec.IntValue werewolf_form_duration;
    public final ForgeConfigSpec.IntValue werewolf_form_cooldown;
    public final ForgeConfigSpec.DoubleValue werewolf_form_speed_amount;
    public final ForgeConfigSpec.DoubleValue werewolf_form_armor;
    public final ForgeConfigSpec.DoubleValue werewolf_form_armor_toughness;

    public final ForgeConfigSpec.BooleanValue howling_enabled;
    public final ForgeConfigSpec.DoubleValue howling_attackspeed_amount;
    public final ForgeConfigSpec.IntValue howling_cooldown;
    public final ForgeConfigSpec.IntValue howling_duration;
    public final ForgeConfigSpec.IntValue howling_disabled_duration;


    BalanceConfig(ForgeConfigSpec.Builder builder) {
        drownsytime = builder.comment("in minutes").defineInRange("drownsytime",10,1, Integer.MAX_VALUE);
        werewolf_damage = builder.comment("In seconds").defineInRange("werewolf_form_duration", 1.0, 0, 10);
        werewolf_speed_amount = builder.defineInRange("werewolf_speed_amount", 0.5, 0, 5);
        werewolf_armor_toughness = builder.defineInRange("werewolf_armor_toughness", 5.0, 0, 10.0);

        builder.push("werewolf form");
        werewolf_form_enabled = builder.define("werewolf_form_enabled",true);
        werewolf_form_duration = builder.comment("In seconds").defineInRange("werewolf_form_duration", 10, 0, Integer.MAX_VALUE);
        werewolf_form_cooldown = builder.comment("In seconds").defineInRange("werewolf_form_cooldown", 0, 0, 10000);
        werewolf_form_speed_amount = builder.defineInRange("werewolf_form_speed_amount", 0.5, 0, 5);
        werewolf_form_armor = builder.defineInRange("werewolf_form_armor", 5.0, 0, 10.0);
        werewolf_form_armor_toughness = builder.defineInRange("werewolf_form_armor_toughness", 5.0, 0, 10.0);
        builder.pop();

        builder.push("howling skill");
        howling_enabled = builder.comment("Hownling Action enabled").define("howling_enabled", true);
        howling_attackspeed_amount = builder.comment("Hownling Attack speed multiplier").defineInRange("howling_attackspeed_amount",2.0,0.0,5.0);
        howling_cooldown = builder.comment("Howling cooldown").defineInRange("howling_cooldown",10,1,Integer.MAX_VALUE);
        howling_duration = builder.comment("Howling duration").defineInRange("howling_duration",10,1,Integer.MAX_VALUE);
        howling_disabled_duration = builder.comment("Howling disabled duration").defineInRange("howling_disabled_duration",10,1,Integer.MAX_VALUE);
        builder.pop();
    }
}
