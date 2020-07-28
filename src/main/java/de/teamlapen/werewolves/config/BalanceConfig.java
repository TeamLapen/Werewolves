package de.teamlapen.werewolves.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BalanceConfig {

    public final Player PLAYER;
    public final MobProps MOBPROPS;
    public final Skills SKILLS;
    public final Potions POTIONS;
    public final Util UTIL;

    BalanceConfig(ForgeConfigSpec.Builder builder) {

        builder.push("player");
        PLAYER = new Player(builder);
        builder.pop();
        builder.push("mobProps");
        MOBPROPS = new MobProps(builder);
        builder.pop();
        builder.push("skills");
        SKILLS = new Skills(builder);
        builder.pop();
        builder.push("potions");
        POTIONS = new Potions(builder);
        builder.pop();
        builder.push("util");
        UTIL = new Util(builder);
        builder.pop();
    }

    public static class Player {
        public final ForgeConfigSpec.DoubleValue werewolf_speed_amount;
        public final ForgeConfigSpec.DoubleValue werewolf_armor_toughness;
        public final ForgeConfigSpec.DoubleValue werewolf_damage;

        public Player(ForgeConfigSpec.Builder builder) {
            werewolf_damage = builder.comment("In seconds").defineInRange("werewolf_form_duration", 1.0, 0, 10);
            werewolf_speed_amount = builder.defineInRange("werewolf_speed_amount", 0.2, 0, 5);
            werewolf_armor_toughness = builder.defineInRange("werewolf_armor_toughness", 3.0, 0, 10.0);
        }
    }

    public static class MobProps {
        public final ForgeConfigSpec.DoubleValue WEREWOLF_MAX_HEALTH;
        public final ForgeConfigSpec.DoubleValue WEREWOLF_MAX_HEALTH_PL;
        public final ForgeConfigSpec.DoubleValue WEREWOLF_ATTACK_DAMAGE;
        public final ForgeConfigSpec.DoubleValue WEREWOLF_ATTACK_DAMAGE_PL;
        public final ForgeConfigSpec.DoubleValue WEREWOLF_SPEED;

        public MobProps(ForgeConfigSpec.Builder builder) {
            WEREWOLF_ATTACK_DAMAGE = builder.defineInRange("werewolfAttackDamage", 3, 0, Double.MAX_VALUE);
            WEREWOLF_ATTACK_DAMAGE_PL = builder.defineInRange("werewolfAttackDamagePl", 1, 0, Double.MAX_VALUE);
            WEREWOLF_MAX_HEALTH = builder.defineInRange("werewolfMaxHealth", 30.0, 10, Double.MAX_VALUE);
            WEREWOLF_MAX_HEALTH_PL = builder.defineInRange("werewolfMaxHealthPl", 3, 0, Double.MAX_VALUE);
            WEREWOLF_SPEED = builder.defineInRange("werewolfSpeed", 0.3, 0.1, 2);
        }
    }

    public static class Skills {

        public final WerewolfForm WEREWOLFFORM;
        public final Howling HOWLING;
        public final Bite BITE;
        public final Health HEALTH;
        public final HealthReg HEALTH_REG;
        public final Damage DAMAGE;

        public Skills(ForgeConfigSpec.Builder builder) {
            builder.push("werewolf_form");
            WEREWOLFFORM = new WerewolfForm(builder);
            builder.pop();
            builder.push("howling");
            HOWLING = new Howling(builder);
            builder.pop();
            builder.push("bite");
            BITE = new Bite(builder);
            builder.pop();
            builder.push("health");
            HEALTH = new Health(builder);
            builder.pop();
            builder.push("health_reg");
            HEALTH_REG = new HealthReg(builder);
            builder.pop();
            builder.push("damage");
            DAMAGE = new Damage(builder);
            builder.pop();


        }

        public static class WerewolfForm {
            public final ForgeConfigSpec.BooleanValue enabled;
            public final ForgeConfigSpec.IntValue duration;
            public final ForgeConfigSpec.IntValue cooldown;
            public final ForgeConfigSpec.DoubleValue speed_amount;
            public final ForgeConfigSpec.DoubleValue armor;
            public final ForgeConfigSpec.DoubleValue armor_toughness;
            public final ForgeConfigSpec.LongValue time_limit;

            public WerewolfForm(ForgeConfigSpec.Builder builder) {
                enabled = builder.define("werewolf_form_enabled", true);
                duration = builder.comment("In seconds").defineInRange("werewolf_form_duration", Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
                cooldown = builder.comment("In seconds").defineInRange("werewolf_form_cooldown", 0, 0, 10000);
                speed_amount = builder.defineInRange("werewolf_form_speed_amount", 0.5, 0, 5);
                armor = builder.defineInRange("werewolf_form_armor", 5.0, 0, 10.0);
                armor_toughness = builder.defineInRange("werewolf_form_armor_toughness", 5.0, 0, 10.0);
                time_limit = builder.comment("in Seconds").defineInRange("werewolf_form_time_limit",2,1,Long.MAX_VALUE);
            }
        }

        public static class Howling {
            public final ForgeConfigSpec.BooleanValue enabled;
            public final ForgeConfigSpec.DoubleValue attackspeed_amount;
            public final ForgeConfigSpec.IntValue cooldown;
            public final ForgeConfigSpec.IntValue duration;
            public final ForgeConfigSpec.IntValue disabled_duration;

            public Howling(ForgeConfigSpec.Builder builder) {
                enabled = builder.comment("Hownling Action enabled").define("howling_enabled", true);
                attackspeed_amount = builder.comment("Hownling Attack speed multiplier").defineInRange("howling_attackspeed_amount", 2.0, 0.0, 5.0);
                cooldown = builder.comment("Howling cooldown").defineInRange("howling_cooldown", 10, 1, Integer.MAX_VALUE);
                duration = builder.comment("Howling duration").defineInRange("howling_duration", 10, 1, Integer.MAX_VALUE);
                disabled_duration = builder.comment("Howling disabled duration").defineInRange("howling_disabled_duration", 10, 1, Integer.MAX_VALUE);
            }
        }

        public static class Bite {
            public final ForgeConfigSpec.BooleanValue enabled;
            public final ForgeConfigSpec.IntValue cooldown;

            public Bite(ForgeConfigSpec.Builder builder) {
                this.enabled = builder.define("bite_enabled", true);
                this.cooldown = builder.comment("In Seconds").defineInRange("bite_cooldown", 5, 0, Integer.MAX_VALUE);
            }
        }

        public static class Health {
            public final ForgeConfigSpec.DoubleValue amount;

            public Health(ForgeConfigSpec.Builder builder) {
                this.amount = builder.defineInRange("health_amount", 5.0, 0.0, 10.0);
            }
        }

        public static class HealthReg {
            public final ForgeConfigSpec.DoubleValue modifier;

            public HealthReg(ForgeConfigSpec.Builder builder) {
                this.modifier = builder.defineInRange("health_reg_modifier", 0.2, 0.0, 1);
            }
        }

        public static class Damage {
            public final ForgeConfigSpec.DoubleValue amount;

            public Damage(ForgeConfigSpec.Builder builder) {
                this.amount = builder.defineInRange("damage_amount", 3, 0.0, 10.0);
            }
        }
    }

    public static class Potions {
        public final ForgeConfigSpec.DoubleValue silverStatsReduction;

        public Potions(ForgeConfigSpec.Builder builder) {
            silverStatsReduction = builder.comment("How much a Werewolf should be weakened by a silver item").defineInRange("silverStatsReduction",-0.2,-1,0);
        }
    }

    public static class Util {
        public final ForgeConfigSpec.IntValue drownsytime;
        public final ForgeConfigSpec.IntValue werewolfHeavenWeight;
        public final ForgeConfigSpec.IntValue silverBoltEffectDuration;
        public final ForgeConfigSpec.IntValue silverItemEffectDuration;

        public Util(ForgeConfigSpec.Builder builder) {
            drownsytime = builder.comment("in minutes").defineInRange("drownsytime", 10, 1, Integer.MAX_VALUE);
            werewolfHeavenWeight = builder.defineInRange("werewolfHeavenWeight", 6, 1, Integer.MAX_VALUE);
            silverBoltEffectDuration = builder.comment("in seconds").defineInRange("silverBoldEffectDuration", 4, 1,Integer.MAX_VALUE);
            silverItemEffectDuration = builder.comment("in ticks").defineInRange("silverItemEffectDuration", 10, 1,Integer.MAX_VALUE);
        }
    }
}
