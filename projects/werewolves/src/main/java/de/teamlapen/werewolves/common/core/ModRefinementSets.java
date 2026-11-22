package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.factions.refinements.IRefinement;
import de.teamlapen.faction.api.factions.refinements.IRefinementSet;
import de.teamlapen.faction.common.RefinementSet;
import de.teamlapen.faction.common.core.FactionRefinements;
import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

import java.util.Arrays;

/**
 * Rarity, faction and slot restrictions are defined through tags, see {@link de.teamlapen.werewolves.data.provider.ModTagsProvider}
 */
public class ModRefinementSets {

    private static final int COMMON = 0xFFFFFF;
    private static final int UNCOMMON = 0x55FF55;
    private static final int RARE = 0x5555FF;

    // Common refinement sets
    public static final ResourceKey<IRefinementSet> ARMOR1 = key("armor1");
    public static final ResourceKey<IRefinementSet> HEALTH1 = key("health1");
    public static final ResourceKey<IRefinementSet> SPEED1 = key("speed1");
    public static final ResourceKey<IRefinementSet> ATTACK_SPEED1 = key("attack_speed1");
    public static final ResourceKey<IRefinementSet> DAMAGE1 = key("damage1");
    public static final ResourceKey<IRefinementSet> DAMAGE1_ATTACK_SPEED1_N_ARMOR2 = key("damage1_attack_speed1_n_armor2");
    public static final ResourceKey<IRefinementSet> ARMOR1_HEALTH1_N_ATTACK_SPEED2 = key("armor1_health1_n_attack_speed2");

    // Uncommon refinement sets
    public static final ResourceKey<IRefinementSet> ARMOR2_N_HEALTH2 = key("armor2_n_health2");
    public static final ResourceKey<IRefinementSet> HEALTH2_N_DAMAGE1 = key("health2_n_damage1");
    public static final ResourceKey<IRefinementSet> ATTACK_SPEED2_N_ARMOR1 = key("attack_speed2_n_armor1");
    public static final ResourceKey<IRefinementSet> DAMAGE2_N_SPEED1 = key("damage2_n_speed1");
    public static final ResourceKey<IRefinementSet> SPEED2_N_DAMAGE1 = key("speed2_n_damage1");
    public static final ResourceKey<IRefinementSet> ARMOR3_N_HEALTH3 = key("armor3_n_health3");
    public static final ResourceKey<IRefinementSet> HEALTH3_N_DAMAGE2 = key("health3_n_damage2");
    public static final ResourceKey<IRefinementSet> ATTACK_SPEED3_N_ARMOR2 = key("attack_speed3_n_armor2");
    public static final ResourceKey<IRefinementSet> DAMAGE3_N_SPEED2 = key("damage3_n_speed2");
    public static final ResourceKey<IRefinementSet> SPEED3_N_DAMAGE2 = key("speed3_n_damage2");

    // Rare refinement sets
    public static final ResourceKey<IRefinementSet> ARMOR3_N_HEALTH2 = key("armor3_n_health2");
    public static final ResourceKey<IRefinementSet> HEALTH3_N_ARMOR1 = key("health3_n_armor1");
    public static final ResourceKey<IRefinementSet> ATTACK_SPEED3_N_SPEED1 = key("attack_speed3_n_speed1");
    public static final ResourceKey<IRefinementSet> SPEED1_ARMOR1_HEALTH1 = key("speed1_armor1_health1");
    public static final ResourceKey<IRefinementSet> DAMAGE3_N_ARMOR1 = key("damage3_n_armor1");
    public static final ResourceKey<IRefinementSet> SPEED3_N_ATTACK_SPEED1 = key("speed3_n_attack_speed1");
    public static final ResourceKey<IRefinementSet> DAMAGE1_ATTACK_SPEED1 = key("damage1_attack_speed1");

    // Werewolf refinement sets
    public static final ResourceKey<IRefinementSet> WEREWOLF_FORM_DURATION_GENERAL_2 = key("werewolf_form_duration_general_2");
    public static final ResourceKey<IRefinementSet> WEREWOLF_FORM_DURATION_GENERAL_2_N_DAMAGE_N_SPEED = key("werewolf_form_duration_general_2_n_damage_n_speed");
    public static final ResourceKey<IRefinementSet> WEREWOLF_FORM_DURATION_GENERAL_1 = key("werewolf_form_duration_general_1");
    public static final ResourceKey<IRefinementSet> WEREWOLF_FORM_DURATION_GENERAL_1_N_DAMAGE_N_SPEED = key("werewolf_form_duration_general_1_n_damage_n_speed");
    public static final ResourceKey<IRefinementSet> WEREWOLF_FORM_DURATION_SURVIVAL_2 = key("werewolf_form_duration_survival_2");
    public static final ResourceKey<IRefinementSet> WEREWOLF_FORM_DURATION_SURVIVAL_1 = key("werewolf_form_duration_survival_1");
    public static final ResourceKey<IRefinementSet> WEREWOLF_FORM_DURATION_BEAST_2 = key("werewolf_form_duration_beast_2");
    public static final ResourceKey<IRefinementSet> WEREWOLF_FORM_DURATION_BEAST_1 = key("werewolf_form_duration_beast_1");
    public static final ResourceKey<IRefinementSet> RAGE_FURY = key("rage_fury");
    public static final ResourceKey<IRefinementSet> HEALTH_AFTER_KILL = key("health_after_kill");
    public static final ResourceKey<IRefinementSet> HEALTH = key("health");
    public static final ResourceKey<IRefinementSet> STUN_BITE = key("stun_bite");
    public static final ResourceKey<IRefinementSet> BLEEDING_BITE = key("bleeding_bite");
    public static final ResourceKey<IRefinementSet> VARIABLE_BITE = key("variable_bite");
    public static final ResourceKey<IRefinementSet> MORE_WOLVES = key("more_wolves");
    public static final ResourceKey<IRefinementSet> GREATER_DOGE_CHANCE = key("greater_doge_chance");

    static void createRefinementSets(BootstrapContext<IRefinementSet> context) {
        // Common refinement sets
        {
            // attribute modifier
            register(context, ARMOR1, COMMON, FactionRefinements.ARMOR1);
            register(context, HEALTH1, COMMON, FactionRefinements.HEALTH1);
            register(context, SPEED1, COMMON, FactionRefinements.SPEED1);
            register(context, ATTACK_SPEED1, COMMON, FactionRefinements.ATTACK_SPEED1);
            register(context, DAMAGE1, COMMON, FactionRefinements.DAMAGE1);
            register(context, DAMAGE1_ATTACK_SPEED1_N_ARMOR2, COMMON, FactionRefinements.DAMAGE1, FactionRefinements.ATTACK_SPEED1, FactionRefinements.N_ARMOR2);
            register(context, ARMOR1_HEALTH1_N_ATTACK_SPEED2, COMMON, FactionRefinements.ARMOR1, FactionRefinements.HEALTH1, FactionRefinements.N_ATTACK_SPEED2);
        }
        // Uncommon refinement sets
        {
            // attribute modifier
            register(context, ARMOR2_N_HEALTH2, UNCOMMON, FactionRefinements.ARMOR2, FactionRefinements.N_HEALTH2);
            register(context, HEALTH2_N_DAMAGE1, UNCOMMON, FactionRefinements.HEALTH2, FactionRefinements.N_DAMAGE1);
            register(context, ATTACK_SPEED2_N_ARMOR1, UNCOMMON, FactionRefinements.ATTACK_SPEED2, FactionRefinements.N_ARMOR1);
            register(context, DAMAGE2_N_SPEED1, UNCOMMON, FactionRefinements.DAMAGE2, FactionRefinements.N_SPEED1);
            register(context, SPEED2_N_DAMAGE1, UNCOMMON, FactionRefinements.SPEED2, FactionRefinements.N_DAMAGE1);

            // better attribute modifier with de-buffs
            register(context, ARMOR3_N_HEALTH3, UNCOMMON, FactionRefinements.ARMOR3, FactionRefinements.N_HEALTH3);
            register(context, HEALTH3_N_DAMAGE2, UNCOMMON, FactionRefinements.HEALTH3, FactionRefinements.N_DAMAGE2);
            register(context, ATTACK_SPEED3_N_ARMOR2, UNCOMMON, FactionRefinements.ATTACK_SPEED3, FactionRefinements.N_ARMOR2);
            register(context, DAMAGE3_N_SPEED2, UNCOMMON, FactionRefinements.DAMAGE3, FactionRefinements.N_SPEED3);
            register(context, SPEED3_N_DAMAGE2, UNCOMMON, FactionRefinements.SPEED3, FactionRefinements.N_DAMAGE2);
        }
        // Rare refinement sets
        {
            // attribute modifier
            register(context, ARMOR3_N_HEALTH2, RARE, FactionRefinements.ARMOR3, FactionRefinements.N_HEALTH2);
            register(context, HEALTH3_N_ARMOR1, RARE, FactionRefinements.HEALTH3, FactionRefinements.N_ARMOR1);
            register(context, ATTACK_SPEED3_N_SPEED1, RARE, FactionRefinements.ATTACK_SPEED3, FactionRefinements.N_SPEED1);
            register(context, SPEED1_ARMOR1_HEALTH1, RARE, FactionRefinements.SPEED1, FactionRefinements.ARMOR1, FactionRefinements.HEALTH1);
            register(context, DAMAGE3_N_ARMOR1, RARE, FactionRefinements.DAMAGE3, FactionRefinements.N_ARMOR2);
            register(context, SPEED3_N_ATTACK_SPEED1, RARE, FactionRefinements.SPEED3, FactionRefinements.N_ATTACK_SPEED1);
            register(context, DAMAGE1_ATTACK_SPEED1, RARE, FactionRefinements.DAMAGE1, FactionRefinements.ATTACK_SPEED1);
        }
        // Werewolf refinement sets
        {
            register(context, WEREWOLF_FORM_DURATION_GENERAL_2, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_2);
            register(context, WEREWOLF_FORM_DURATION_GENERAL_2_N_DAMAGE_N_SPEED, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_2, FactionRefinements.N_DAMAGE2, FactionRefinements.N_SPEED2);
            register(context, WEREWOLF_FORM_DURATION_GENERAL_1, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_1);
            register(context, WEREWOLF_FORM_DURATION_GENERAL_1_N_DAMAGE_N_SPEED, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_1, FactionRefinements.N_DAMAGE2, FactionRefinements.N_SPEED2);
            register(context, WEREWOLF_FORM_DURATION_SURVIVAL_2, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_SURVIVAL_2);
            register(context, WEREWOLF_FORM_DURATION_SURVIVAL_1, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_SURVIVAL_1);
            register(context, WEREWOLF_FORM_DURATION_BEAST_2, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_BEAST_2);
            register(context, WEREWOLF_FORM_DURATION_BEAST_1, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_BEAST_1);
            register(context, RAGE_FURY, 0xff0006, ModRefinements.RAGE_FURY);
            register(context, HEALTH_AFTER_KILL, 0x16af00, ModRefinements.HEALTH_AFTER_KILL);
            register(context, HEALTH, 0x16af00, ModRefinements.HEALTH_AFTER_KILL, FactionRefinements.HEALTH2);
            register(context, STUN_BITE, 0xedb521, ModRefinements.STUN_BITE);
            register(context, BLEEDING_BITE, 0xcdc2639, ModRefinements.BLEEDING_BITE);
            register(context, VARIABLE_BITE, 0xcdc2639, ModRefinements.BLEEDING_BITE, ModRefinements.STUN_BITE);
            register(context, MORE_WOLVES, 0x929292, ModRefinements.MORE_WOLVES);
            register(context, GREATER_DOGE_CHANCE, 0x4df1f3, ModRefinements.GREATER_DOGE_CHANCE);
        }
    }

    @SafeVarargs
    private static void register(BootstrapContext<IRefinementSet> context, ResourceKey<IRefinementSet> key, int color, ResourceKey<IRefinement>... refinements) {
        HolderGetter<IRefinement> lookup = context.lookup(FactionRegistries.Keys.REFINEMENT);
        context.register(key, RefinementSet.of(key, color, Arrays.stream(refinements).map(lookup::getOrThrow).toList()));
    }

    private static ResourceKey<IRefinementSet> key(String path) {
        return ResourceKey.create(FactionRegistries.Keys.REFINEMENT_SET, WIdentifier.mod(path));
    }
}
