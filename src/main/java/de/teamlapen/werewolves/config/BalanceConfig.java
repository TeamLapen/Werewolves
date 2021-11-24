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
        public final ForgeConfigSpec.DoubleValue werewolf_claw_damage;
        public final ForgeConfigSpec.IntValue bite_cooldown;

        public Player(ForgeConfigSpec.Builder builder) {
            werewolf_damage = builder.comment("Level based damage attribute modifier").defineInRange("werewolf_damage", 1.0, 0, 10);
            werewolf_speed_amount = builder.comment("Level based speed attribute modifier").defineInRange("werewolf_speed_amount", 0.2, 0, 5);
            werewolf_armor_toughness = builder.comment("Level based armor toughness attribute modifier").defineInRange("werewolf_armor_toughness", 3.0, 0, 10.0);
            werewolf_claw_damage = builder.comment("Level based claw damage attribute modifier").defineInRange("werewolf_claw_damage", 2d, 0, Integer.MAX_VALUE);
            bite_cooldown = builder.comment("Cooldown of bite attack (in ticks)").defineInRange("bite_cooldown", 120, 5, Integer.MAX_VALUE);
        }
    }

    public static class MobProps {
        public final ForgeConfigSpec.DoubleValue werewolf_max_health;
        public final ForgeConfigSpec.DoubleValue werewolf_max_health_pl;
        public final ForgeConfigSpec.DoubleValue werewolf_attack_damage;
        public final ForgeConfigSpec.DoubleValue werewolf_attack_damage_pl;
        public final ForgeConfigSpec.DoubleValue werewolf_speed;

        public final ForgeConfigSpec.IntValue werewolf_transform_duration;

        public final ForgeConfigSpec.DoubleValue human_werewolf_max_health;
        public final ForgeConfigSpec.DoubleValue human_werewolf_attack_damage;
        public final ForgeConfigSpec.DoubleValue human_werewolf_speed;

        public MobProps(ForgeConfigSpec.Builder builder) {
            builder.push("werewolf");
            werewolf_attack_damage = builder.defineInRange("werewolf_attack_damage", 3, 0, Double.MAX_VALUE);
            werewolf_attack_damage_pl = builder.defineInRange("werewolf_attack_damage_pl", 1, 0, Double.MAX_VALUE);
            werewolf_max_health = builder.defineInRange("werewolf_max_health", 30.0, 10, Double.MAX_VALUE);
            werewolf_max_health_pl = builder.defineInRange("werewolf_max_health_pl", 3, 0, Double.MAX_VALUE);
            werewolf_speed = builder.defineInRange("werewolf_speed", 0.3, 0.1, 2);
            werewolf_transform_duration = builder.comment("Time until a werewolf turns back human").defineInRange("werewolf_transform_duration", 10, 10, Integer.MAX_VALUE);
            builder.pop();

            builder.push("human_werewolf");
            human_werewolf_attack_damage = builder.defineInRange("human_werewolf_attack_damage", 3, 0, Double.MAX_VALUE);
            human_werewolf_max_health = builder.defineInRange("human_werewolf_max_health", 30.0, 10, Double.MAX_VALUE);
            human_werewolf_speed = builder.defineInRange("human_werewolf_speed", 0.36, 0.1, 2);
            builder.pop();
        }
    }

    public static class Skills {

        //werewolf action
        public final ForgeConfigSpec.BooleanValue human_form_enabled;
        public final ForgeConfigSpec.IntValue human_form_cooldown;
        public final ForgeConfigSpec.DoubleValue human_form_speed_amount;
        public final ForgeConfigSpec.DoubleValue human_form_armor;
        public final ForgeConfigSpec.DoubleValue human_form_armor_toughness;

        public final ForgeConfigSpec.BooleanValue beast_form_enabled;
        public final ForgeConfigSpec.IntValue beast_form_cooldown;
        public final ForgeConfigSpec.DoubleValue beast_form_speed_amount;
        public final ForgeConfigSpec.DoubleValue beast_form_attack_damage;
        public final ForgeConfigSpec.DoubleValue beast_form_armor;
        public final ForgeConfigSpec.DoubleValue beast_form_armor_toughness;
        public final ForgeConfigSpec.DoubleValue beast_form_health;
        public final ForgeConfigSpec.DoubleValue beast_form_bite_damage;

        public final ForgeConfigSpec.BooleanValue survival_form_enabled;
        public final ForgeConfigSpec.IntValue survival_form_cooldown;
        public final ForgeConfigSpec.DoubleValue survival_form_speed_amount;
        public final ForgeConfigSpec.DoubleValue survival_form_attack_damage;
        public final ForgeConfigSpec.DoubleValue survival_form_armor;
        public final ForgeConfigSpec.DoubleValue survival_form_armor_toughness;

        public final ForgeConfigSpec.LongValue werewolf_form_time_limit;

        //howling action
        public final ForgeConfigSpec.BooleanValue howling_enabled;
        public final ForgeConfigSpec.DoubleValue howling_attackspeed_amount;
        public final ForgeConfigSpec.IntValue howling_cooldown;
        public final ForgeConfigSpec.IntValue howling_duration;
        public final ForgeConfigSpec.IntValue howling_disabled_duration;

        //bite action
        public final ForgeConfigSpec.BooleanValue bite_enabled;
        public final ForgeConfigSpec.IntValue bite_cooldown;

        //health skill
        public final ForgeConfigSpec.DoubleValue health_amount;

        //health_reg skill
        public final ForgeConfigSpec.DoubleValue health_reg_modifier;

        //resistance skill
        public final ForgeConfigSpec.DoubleValue resistance_amount;

        //speed skill
        public final ForgeConfigSpec.DoubleValue speed_amount;

        //rage action
        public final ForgeConfigSpec.BooleanValue rage_enabled;
        public final ForgeConfigSpec.IntValue rage_duration;
        public final ForgeConfigSpec.IntValue rage_cooldown;

        //sense action
        public final ForgeConfigSpec.BooleanValue sense_enabled;
        public final ForgeConfigSpec.IntValue sense_radius;
        public final ForgeConfigSpec.IntValue sense_duration;
        public final ForgeConfigSpec.IntValue sense_cooldown;

        //stun bite skill
        public final ForgeConfigSpec.IntValue stun_bite_duration;

        //bleeding bite skill
        public final ForgeConfigSpec.IntValue bleeding_bite_duration;

        //better claws
        public final ForgeConfigSpec.DoubleValue better_claw_damage;

        //fear action
        public final ForgeConfigSpec.BooleanValue fear_action_enabled;
        public final ForgeConfigSpec.IntValue fear_action_cooldown;

        //hide name action
        public final ForgeConfigSpec.BooleanValue hide_name_enabled;
        public final ForgeConfigSpec.IntValue hide_name_cooldown;
        public final ForgeConfigSpec.IntValue hide_name_duration;

        //leap action
        public final ForgeConfigSpec.BooleanValue leap_enabled;
        public final ForgeConfigSpec.IntValue leap_cooldown;

        //hide name action
        public final ForgeConfigSpec.BooleanValue sixth_sense_enabled;
        public final ForgeConfigSpec.IntValue sixth_sense_cooldown;
        public final ForgeConfigSpec.IntValue sixth_sense_duration;

        //wolf pack action
        public final ForgeConfigSpec.BooleanValue wolf_pack_enabled;
        public final ForgeConfigSpec.IntValue wolf_pack_cooldown;
        public final ForgeConfigSpec.IntValue wolf_pack_wolf_duration;

        public Skills(ForgeConfigSpec.Builder builder) {
            builder.push("werewolf_form");

            builder.push("human_form");
            this.human_form_enabled = builder.define("human_form_enabled", true);
            this.human_form_cooldown = builder.comment("In seconds").defineInRange("human_form_cooldown", 0, 0, 10000);
            this.human_form_speed_amount = builder.defineInRange("human_form_speed_amount", 0.2, 0, 5);
            this.human_form_armor = builder.defineInRange("human_form_armor", 5, 0, 20.0);
            this.human_form_armor_toughness = builder.defineInRange("human_form_armor_toughness", 2, 0, 10.0);
            builder.pop();

            builder.push("beast_form");
            this.beast_form_enabled = builder.define("beast_form_enabled", true);
            this.beast_form_cooldown = builder.comment("In seconds").defineInRange("beast_form_cooldown", 0, 0, 10000);
            this.beast_form_speed_amount = builder.defineInRange("beast_form_speed_amount", 0.4, 0, 5);
            this.beast_form_attack_damage = builder.defineInRange("beast_form_attack_damage", 4D, 0, 100);
            this.beast_form_armor = builder.defineInRange("beast_form_armor", 20, 0, 20.0);
            this.beast_form_armor_toughness = builder.defineInRange("beast_form_armor_toughness", 8, 0, 10.0);
            this.beast_form_health = builder.defineInRange("beast_form_health", 4.0, 0.0, 20.0);
            this.beast_form_bite_damage = builder.defineInRange("beast_form_bite_damage", 2.0, 0.0, 20.0);
            builder.pop();

            builder.push("survival_form");
            this.survival_form_enabled = builder.define("survival_form_enabled", true);
            this.survival_form_cooldown = builder.comment("In seconds").defineInRange("survival_form_cooldown", 0, 0, 10000);
            this.survival_form_speed_amount = builder.defineInRange("survival_form_speed_amount", 0.5, 0, 5);
            this.survival_form_attack_damage = builder.defineInRange("survival_form_attack_damage", 2D, 0, 100);
            this.survival_form_armor = builder.defineInRange("survival_form_armor", 16, 0, 20.0);
            this.survival_form_armor_toughness = builder.defineInRange("survival_form_armor_toughness", 6, 0, 10.0);
            builder.pop();

            this.werewolf_form_time_limit = builder.comment("Time a player can stay in werewolf form", "In seconds").defineInRange("werewolf_form_time_limit", 80, 1, Long.MAX_VALUE);
            builder.pop();

            builder.push("howling");
            this.howling_enabled = builder.define("howling_enabled", true);
            this.howling_attackspeed_amount = builder.defineInRange("howling_attackspeed_amount", 2.0, 0.0, 5.0);
            this.howling_cooldown = builder.comment("In seconds").defineInRange("howling_cooldown", 10, 1, Integer.MAX_VALUE);
            this.howling_duration = builder.comment("Howling effect duration", "In seconds").defineInRange("howling_duration", 10, 1, Integer.MAX_VALUE);
            this.howling_disabled_duration = builder.comment("Duration that cancels new howling effects", "In seconds").defineInRange("howling_disabled_duration", 10, 1, Integer.MAX_VALUE);
            builder.pop();

            builder.push("bite");
            this.bite_enabled = builder.define("bite_enabled", true);
            this.bite_cooldown = builder.comment("In seconds").defineInRange("bite_cooldown", 5, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.push("health");
            this.health_amount = builder.defineInRange("health_amount", 5.0, 0.0, 10.0);
            builder.pop();

            builder.push("health_reg");
            this.health_reg_modifier = builder.defineInRange("health_reg_modifier", 0.2, 0.0, 1);
            builder.pop();

            builder.push("resistance");
            this.resistance_amount = builder.defineInRange("resistance_amount", 3, 0.0, 10.0);
            builder.pop();

            builder.push("speed");
            this.speed_amount = builder.defineInRange("speed_amount", 0.02, 0.0, 1);
            builder.pop();

            builder.push("rage");
            this.rage_enabled = builder.define("rage_enabled", true);
            this.rage_duration = builder.comment("In seconds").defineInRange("rage_duration", 10, 0, Integer.MAX_VALUE);
            this.rage_cooldown = builder.comment("In seconds").defineInRange("rage_cooldown", 10, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.push("sense");
            this.sense_enabled = builder.define("sense_enabled", true);
            this.sense_radius = builder.defineInRange("sense_radius", 15, 1, 400);
            this.sense_duration = builder.comment("In seconds").defineInRange("sense_duration", 30, 1, Integer.MAX_VALUE);
            this.sense_cooldown = builder.comment("In seconds").defineInRange("sense_cooldown", 60, 1, Integer.MAX_VALUE);
            builder.pop();

            builder.push("stun_bite");
            this.stun_bite_duration = builder.comment("In ticks").defineInRange("stun_bite_duration", 40, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.push("bleeding_bite");
            this.bleeding_bite_duration = builder.comment("In ticks").defineInRange("bleeding_bite_duration", 100, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.push("better_claws");
            this.better_claw_damage = builder.defineInRange("better_claw_damage", 1d, 0, Integer.MAX_VALUE);
            builder.pop();

            builder.push("fear_action");
            this.fear_action_enabled = builder.define("fear_action_enabled", true);
            this.fear_action_cooldown = builder.comment("In seconds").defineInRange("fear_action_cooldown", 20, 1, Integer.MAX_VALUE);
            builder.pop();

            builder.push("hide_name");
            this.hide_name_enabled = builder.define("hide_name_enabled", true);
            this.hide_name_cooldown = builder.comment("In seconds").defineInRange("hide_name_cooldown", 1, 1, Integer.MAX_VALUE);
            this.hide_name_duration = builder.comment("Intended for unlimited usage", "In ticks").defineInRange("hide_name_duration", 1000000, 1, Integer.MAX_VALUE);
            builder.pop();

            builder.push("leap");
            this.leap_enabled = builder.define("leap_enabled", true);
            this.leap_cooldown = builder.comment("In seconds").defineInRange("leap_cooldown", 6, 1, Integer.MAX_VALUE);
            builder.pop();

            builder.push("sixth_sense");
            this.sixth_sense_enabled = builder.define("sixth_sense_enabled", true);
            this.sixth_sense_cooldown = builder.comment("In seconds").defineInRange("sixth_sense_cooldown", 30, 1, Integer.MAX_VALUE);
            this.sixth_sense_duration = builder.comment("Intended for unlimited usage", "In ticks").defineInRange("sixth_sense_duration", 1000000, 1, Integer.MAX_VALUE);
            builder.pop();

            builder.push("wolf_pack");
            this.wolf_pack_enabled = builder.define("wolf_pack_enabled", true);
            this.wolf_pack_cooldown = builder.comment("In seconds").defineInRange("wolf_pack_cooldown", 60, 1, Integer.MAX_VALUE);
            this.wolf_pack_wolf_duration = builder.comment("In seconds").defineInRange("wolf_pack_wolf_duration", 25, 1, Integer.MAX_VALUE);
            builder.pop();
        }
    }

    public static class Potions {
        public final ForgeConfigSpec.DoubleValue silverStatsReduction;

        public Potions(ForgeConfigSpec.Builder builder) {
            silverStatsReduction = builder.comment("How much a Werewolf should be weakened by a silver item").defineInRange("silverStatsReduction",-0.2,-1,0);
        }
    }

    public static class Util {

        public final ForgeConfigSpec.IntValue silverBoltEffectDuration;
        public final ForgeConfigSpec.IntValue silverItemEffectDuration;
        public final ForgeConfigSpec.BooleanValue skeletonIgnoreWerewolves;

        public Util(ForgeConfigSpec.Builder builder) {
            this.silverBoltEffectDuration = builder.comment("in seconds").defineInRange("silverBoldEffectDuration", 4, 1, Integer.MAX_VALUE);
            this.silverItemEffectDuration = builder.comment("in ticks").defineInRange("silverItemEffectDuration", 30, 1, Integer.MAX_VALUE);
            this.skeletonIgnoreWerewolves = builder.comment("if skeletons should ignore werewolves").define("skeletonIgnoreWerewolves", true);
        }
    }
}
