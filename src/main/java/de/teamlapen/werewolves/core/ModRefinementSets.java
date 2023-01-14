package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.refinement.IRefinement;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinementSet;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.vampirism.player.refinements.RefinementSet;
import de.teamlapen.werewolves.entities.player.WerewolfRefinementSet;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class ModRefinementSets {

    public static final DeferredRegister<IRefinementSet> REFINEMENT_SETS = DeferredRegister.create(ModRegistries.REFINEMENT_SETS, REFERENCE.MODID);

    //attribute modifiers
    public static final RegistryObject<IRefinementSet> ARMOR_1 = commonW("armor1", ModRefinements.V.ARMOR_1);
    public static final RegistryObject<IRefinementSet> HEALTH_1 = commonW("health1", ModRefinements.V.HEALTH_1);
    public static final RegistryObject<IRefinementSet> SPEED_1 = commonW("speed1", ModRefinements.V.SPEED_1);
    public static final RegistryObject<IRefinementSet> ATTACK_SPEED_1 = commonW("attack_speed1", ModRefinements.V.ATTACK_SPEED_1);
    public static final RegistryObject<IRefinementSet> DAMAGE_1 = commonW("damage1", ModRefinements.V.DAMAGE_1);
    public static final RegistryObject<IRefinementSet> DAMAGE_1_ATTACK_SPEED_1_N_ARMOR_2 = commonW("damage1_attack_speed1_n_armor2", ModRefinements.V.DAMAGE_1, ModRefinements.V.ATTACK_SPEED_1, ModRefinements.V.N_ARMOR_2);
    public static final RegistryObject<IRefinementSet> ARMOR_1_HEALTH_1_N_ATTACK_SPEED_2 = commonW("armor1_health1_n_attack_speed2", ModRefinements.V.ARMOR_1, ModRefinements.V.HEALTH_1, ModRefinements.V.N_ATTACK_SPEED_2);
    public static final RegistryObject<IRefinementSet> ARMOR_2_N_HEALTH_2 = uncommonW("armor2_n_health2", ModRefinements.V.ARMOR_2, ModRefinements.V.N_HEALTH_2);
    public static final RegistryObject<IRefinementSet> HEALTH_2_N_DAMAGE_1 = uncommonW("health2_n_damage1", ModRefinements.V.HEALTH_2, ModRefinements.V.N_DAMAGE_1);
    public static final RegistryObject<IRefinementSet> ATTACK_SPEED_2_N_ARMOR_1 = uncommonW("attack_speed2_n_armor1", ModRefinements.V.ATTACK_SPEED_2, ModRefinements.V.N_ARMOR_1);
    public static final RegistryObject<IRefinementSet> DAMAGE_2_N_SPEED_1 = uncommonW("damage2_n_speed1", ModRefinements.V.DAMAGE_2, ModRefinements.V.N_SPEED_1);
    public static final RegistryObject<IRefinementSet> SPEED_2_N_DAMAGE_1 = uncommonW("speed2_n_damage1", ModRefinements.V.SPEED_2, ModRefinements.V.N_DAMAGE_1);
    public static final RegistryObject<IRefinementSet> ARMOR_3_N_HEALTH_2 = rareW("armor3_n_health2", ModRefinements.V.ARMOR_3, ModRefinements.V.N_HEALTH_2);
    public static final RegistryObject<IRefinementSet> HEALTH_3_N_ARMOR_1 = rareW("health3_n_armor1", ModRefinements.V.HEALTH_3, ModRefinements.V.N_ARMOR_1);
    public static final RegistryObject<IRefinementSet> ATTACK_SPEED_3_N_SPEED_1 = rareW("attack_speed3_n_speed1", ModRefinements.V.ATTACK_SPEED_3, ModRefinements.V.N_SPEED_1);
    public static final RegistryObject<IRefinementSet> SPEED_1_ARMOR_1_HEALTH_1 = rareW("speed1_armor1_health1", ModRefinements.V.SPEED_1, ModRefinements.V.ARMOR_1, ModRefinements.V.HEALTH_1);
    public static final RegistryObject<IRefinementSet> DAMAGE_3_N_ARMOR_1 = rareW("damage3_n_armor1", ModRefinements.V.DAMAGE_3, ModRefinements.V.N_ARMOR_2);
    public static final RegistryObject<IRefinementSet> SPEED_3_N_ATTACK_SPEED_1 = rareW("speed3_n_attack_speed1", ModRefinements.V.SPEED_3, ModRefinements.V.N_ATTACK_SPEED_1);
    public static final RegistryObject<IRefinementSet> ATTACK_SPEED_3_N_DAMAGE_1 = rareW("attack_speed3_n_damage1", ModRefinements.V.DAMAGE_1, ModRefinements.V.ATTACK_SPEED_1);

    // better attribute modifier with de-buffs
    public static final RegistryObject<IRefinementSet> ARMOR_3_N_HEALTH_3 = uncommonW("armor3_n_health3", ModRefinements.V.ARMOR_3, ModRefinements.V.N_HEALTH_3);
    public static final RegistryObject<IRefinementSet> HEALTH_3_N_DAMAGE_2 = uncommonW("health3_n_damage2", ModRefinements.V.HEALTH_3, ModRefinements.V.N_DAMAGE_2);
    public static final RegistryObject<IRefinementSet> ATTACK_SPEED_3_N_ARMOR_2 = uncommonW("attack_speed3_n_armor2", ModRefinements.V.ATTACK_SPEED_3, ModRefinements.V.N_ARMOR_2);
    public static final RegistryObject<IRefinementSet> DAMAGE_3_N_SPEED_2 = uncommonW("damage3_n_speed2", ModRefinements.V.DAMAGE_3, ModRefinements.V.N_SPEED_3);
    public static final RegistryObject<IRefinementSet> SPEED_3_N_DAMAGE_2 = uncommonW("speed3_n_damage2",  ModRefinements.V.SPEED_3, ModRefinements.V.N_DAMAGE_2);

    // special refinement sets
    public static final RegistryObject<WerewolfRefinementSet> WEREWOLF_FORM_DURATION_GENERAL_2 = REFINEMENT_SETS.register("werewolf_form_duration_general_2", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.LEGENDARY,0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_2));
    public static final RegistryObject<WerewolfRefinementSet> WEREWOLF_FORM_DURATION_GENERAL_2_N_DAMAGE_N_SPEED = REFINEMENT_SETS.register("werewolf_form_duration_general_2_n_damage_n_speed", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC,0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_2, ModRefinements.V.N_DAMAGE_2, ModRefinements.V.N_SPEED_2));
    public static final RegistryObject<WerewolfRefinementSet> WEREWOLF_FORM_DURATION_GENERAL_1 = REFINEMENT_SETS.register("werewolf_form_duration_general_1", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC,0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_1));
    public static final RegistryObject<WerewolfRefinementSet> WEREWOLF_FORM_DURATION_GENERAL_1_N_DAMAGE_N_SPEED = REFINEMENT_SETS.register("werewolf_form_duration_general_1_n_damage_n_speed", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE,0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_1, ModRefinements.V.N_DAMAGE_2, ModRefinements.V.N_SPEED_2));
    public static final RegistryObject<WerewolfRefinementSet> WEREWOLF_FORM_DURATION_SURVIVAL_2 = REFINEMENT_SETS.register("werewolf_form_duration_survival_2", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE,0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_SURVIVAL_2));
    public static final RegistryObject<WerewolfRefinementSet> WEREWOLF_FORM_DURATION_SURVIVAL_1 = REFINEMENT_SETS.register("werewolf_form_duration_survival_1", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON,0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_SURVIVAL_1));
    public static final RegistryObject<WerewolfRefinementSet> WEREWOLF_FORM_DURATION_BEAST_2 = REFINEMENT_SETS.register("werewolf_form_duration_beast_2", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE,0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_BEAST_2));
    public static final RegistryObject<WerewolfRefinementSet> WEREWOLF_FORM_DURATION_BEAST_1 = REFINEMENT_SETS.register("werewolf_form_duration_beast_1", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON,0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_BEAST_1));
    public static final RegistryObject<WerewolfRefinementSet> RAGE_FURY = REFINEMENT_SETS.register("rage_fury", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.LEGENDARY,0xff0006, ModRefinements.RAGE_FURY));
    public static final RegistryObject<WerewolfRefinementSet> HEALTH_AFTER_KILL = REFINEMENT_SETS.register("health_after_kill", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.COMMON,0x16af00, ModRefinements.HEALTH_AFTER_KILL));
    public static final RegistryObject<WerewolfRefinementSet> HEALTH = REFINEMENT_SETS.register("health", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE,0x16af00, ModRefinements.HEALTH_AFTER_KILL, ModRefinements.V.HEALTH_2));
    public static final RegistryObject<WerewolfRefinementSet> STUN_BITE = REFINEMENT_SETS.register("stun_bite", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE,0xedb521, ModRefinements.STUN_BITE));
    public static final RegistryObject<WerewolfRefinementSet> BLEEDING_BITE = REFINEMENT_SETS.register("bleeding_bite", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC,0xcdc2639, ModRefinements.BLEEDING_BITE));
    public static final RegistryObject<WerewolfRefinementSet> VARIABLE_BITE = REFINEMENT_SETS.register("variable_bite", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC,0xcdc2639, ModRefinements.BLEEDING_BITE, ModRefinements.STUN_BITE));
    public static final RegistryObject<WerewolfRefinementSet> MORE_WOLVES = REFINEMENT_SETS.register("more_wolves", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON,0x929292, ModRefinements.MORE_WOLVES));
    public static final RegistryObject<WerewolfRefinementSet> GREATER_DOGE_CHANCE = REFINEMENT_SETS.register("greater_doge_chance", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON,0x4df1f3, ModRefinements.GREATER_DOGE_CHANCE));

    static void registerRefinementSets(IEventBus bus) {
        REFINEMENT_SETS.register(bus);
    }

    @SafeVarargs
    private static RegistryObject<IRefinementSet> commonW(String name, RegistryObject<IRefinement>... refinements) {
        return REFINEMENT_SETS.register(name, () -> werewolf(IRefinementSet.Rarity.COMMON, refinements));
    }

    @SafeVarargs
    private static RegistryObject<IRefinementSet> uncommonW(String name, RegistryObject<IRefinement>... refinements) {
        return REFINEMENT_SETS.register(name, () -> werewolf(IRefinementSet.Rarity.UNCOMMON, refinements));
    }

    @SafeVarargs
    private static RegistryObject<IRefinementSet> rareW(String name, RegistryObject<IRefinement>... refinements) {
        return REFINEMENT_SETS.register(name, () -> werewolf(IRefinementSet.Rarity.RARE, refinements));
    }

    @SafeVarargs
    private static RegistryObject<IRefinementSet> epicW(String name, RegistryObject<IRefinement>... refinements) {
        return REFINEMENT_SETS.register(name, () -> werewolf(IRefinementSet.Rarity.EPIC, refinements));
    }

    @SafeVarargs
    private static RegistryObject<IRefinementSet> legendaryW(String name, RegistryObject<IRefinement>... refinements) {
        return REFINEMENT_SETS.register(name, () -> werewolf(IRefinementSet.Rarity.LEGENDARY, refinements));
    }

    @SafeVarargs
    @SuppressWarnings("ConstantConditions")
    private static RefinementSet werewolf(IRefinementSet.Rarity rarity, RegistryObject<IRefinement>... refinements) {
        return new WerewolfRefinementSet(rarity, rarity.color.getColor(), refinements);
    }
}
