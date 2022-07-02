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

import java.util.Arrays;

public class ModRefinementSets {

    public static final DeferredRegister<IRefinementSet> REFINEMENT_SETS = DeferredRegister.create(ModRegistries.REFINEMENT_SETS, REFERENCE.MODID);

    //attribute modifiers
    public static final RegistryObject<IRefinementSet> armor1 = commonW("armor1", ModRefinements.V.armor1);
    public static final RegistryObject<IRefinementSet> health1 = commonW("health1", ModRefinements.V.health1);
    public static final RegistryObject<IRefinementSet> speed1 = commonW("speed1", ModRefinements.V.speed1);
    public static final RegistryObject<IRefinementSet> attack_speed1 = commonW("attack_speed1", ModRefinements.V.attack_speed1);
    public static final RegistryObject<IRefinementSet> damage1 = commonW("damage1", ModRefinements.V.damage1);
    public static final RegistryObject<IRefinementSet> damage1_attack_speed1_n_armor2 = commonW("damage1_attack_speed1_n_armor2", ModRefinements.V.damage1, ModRefinements.V.attack_speed1, ModRefinements.V.n_armor2);
    public static final RegistryObject<IRefinementSet> armor1_health1_n_attack_speed2 = commonW("armor1_health1_n_attack_speed2", ModRefinements.V.armor1, ModRefinements.V.health1, ModRefinements.V.n_attack_speed2);
    public static final RegistryObject<IRefinementSet> armor2_n_health2 = uncommonW("armor2_n_health2", ModRefinements.V.armor2, ModRefinements.V.n_health2);
    public static final RegistryObject<IRefinementSet> health2_n_damage1 = uncommonW("health2_n_damage1", ModRefinements.V.health2, ModRefinements.V.n_damage1);
    public static final RegistryObject<IRefinementSet> attack_speed2_n_armor1 = uncommonW("attack_speed2_n_armor1", ModRefinements.V.attack_speed2, ModRefinements.V.n_armor1);
    public static final RegistryObject<IRefinementSet> damage2_n_speed1 = uncommonW("damage2_n_speed1", ModRefinements.V.damage2, ModRefinements.V.n_speed1);
    public static final RegistryObject<IRefinementSet> speed2_n_damage1 = uncommonW("speed2_n_damage1", ModRefinements.V.speed2, ModRefinements.V.n_damage1);
    public static final RegistryObject<IRefinementSet> armor3_n_health2 = rareW("armor3_n_health2", ModRefinements.V.armor3, ModRefinements.V.n_health2);
    public static final RegistryObject<IRefinementSet> health3_n_armor1 = rareW("health3_n_armor1", ModRefinements.V.health3, ModRefinements.V.n_armor1);
    public static final RegistryObject<IRefinementSet> attack_speed3_n_speed1 = rareW("attack_speed3_n_speed1", ModRefinements.V.attack_speed3, ModRefinements.V.n_speed1);
    public static final RegistryObject<IRefinementSet> speed1_armor1_health1 = rareW("speed1_armor1_health1", ModRefinements.V.speed1, ModRefinements.V.armor1, ModRefinements.V.health1);
    public static final RegistryObject<IRefinementSet> damage3_n_armor1 = rareW("damage3_n_armor1", ModRefinements.V.damage3, ModRefinements.V.n_armor2);
    public static final RegistryObject<IRefinementSet> speed3_n_attack_speed1 = rareW("speed3_n_attack_speed1", ModRefinements.V.armor3, ModRefinements.V.n_health3);
    public static final RegistryObject<IRefinementSet> damage1_attack_speed1 = rareW("attack_speed3_n_damage1", ModRefinements.V.damage1, ModRefinements.V.attack_speed1);

    // better attribute modifier with de-buffs
    public static final RegistryObject<IRefinementSet> armor3_n_health3 = uncommonW("armor3_n_health3", ModRefinements.V.armor3, ModRefinements.V.n_health3);
    public static final RegistryObject<IRefinementSet> health3_n_damage2 = uncommonW("health3_n_damage2", ModRefinements.V.health3, ModRefinements.V.n_damage2);
    public static final RegistryObject<IRefinementSet> attack_speed3_n_armor2 = uncommonW("attack_speed3_n_armor2", ModRefinements.V.attack_speed3, ModRefinements.V.n_armor2);
    public static final RegistryObject<IRefinementSet> damage3_n_speed2 = uncommonW("damage3_n_speed2", ModRefinements.V.damage3, ModRefinements.V.n_speed3);
    public static final RegistryObject<IRefinementSet> speed3_n_damage2 = uncommonW("speed3_n_damage2",  ModRefinements.V.speed3, ModRefinements.V.n_damage2);

    // special refinement sets
    public static final RegistryObject<WerewolfRefinementSet> werewolf_form_duration_general_2 = REFINEMENT_SETS.register("werewolf_form_duration_general_2", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.LEGENDARY,0xc86262, ModRefinements.werewolf_form_duration_general_2));
    public static final RegistryObject<WerewolfRefinementSet> werewolf_form_duration_general_2_n_damage_n_speed = REFINEMENT_SETS.register("werewolf_form_duration_general_2_n_damage_n_speed", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC,0xc86262, ModRefinements.werewolf_form_duration_general_2, ModRefinements.V.n_damage2, ModRefinements.V.n_speed2));
    public static final RegistryObject<WerewolfRefinementSet> werewolf_form_duration_general_1 = REFINEMENT_SETS.register("werewolf_form_duration_general_1", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC,0xc86262, ModRefinements.werewolf_form_duration_general_1));
    public static final RegistryObject<WerewolfRefinementSet> werewolf_form_duration_general_1_n_damage_n_speed = REFINEMENT_SETS.register("werewolf_form_duration_general_1_n_damage_n_speed", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE,0xc86262, ModRefinements.werewolf_form_duration_general_1, ModRefinements.V.n_damage2, ModRefinements.V.n_speed2));
    public static final RegistryObject<WerewolfRefinementSet> werewolf_form_duration_survival_2 = REFINEMENT_SETS.register("werewolf_form_duration_survival_2", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE,0xc86262, ModRefinements.werewolf_form_duration_survival_2));
    public static final RegistryObject<WerewolfRefinementSet> werewolf_form_duration_survival_1 = REFINEMENT_SETS.register("werewolf_form_duration_survival_1", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON,0xc86262, ModRefinements.werewolf_form_duration_survival_1));
    public static final RegistryObject<WerewolfRefinementSet> werewolf_form_duration_beast_2 = REFINEMENT_SETS.register("werewolf_form_duration_beast_2", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE,0xc86262, ModRefinements.werewolf_form_duration_beast_2));
    public static final RegistryObject<WerewolfRefinementSet> werewolf_form_duration_beast_1 = REFINEMENT_SETS.register("werewolf_form_duration_beast_1", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON,0xc86262, ModRefinements.werewolf_form_duration_beast_1));
    public static final RegistryObject<WerewolfRefinementSet> rage_fury = REFINEMENT_SETS.register("rage_fury", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.LEGENDARY,0xff0006, ModRefinements.rage_fury));
    public static final RegistryObject<WerewolfRefinementSet> health_after_kill = REFINEMENT_SETS.register("health_after_kill", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.COMMON,0x16af00, ModRefinements.health_after_kill));
    public static final RegistryObject<WerewolfRefinementSet> health = REFINEMENT_SETS.register("health", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE,0x16af00, ModRefinements.health_after_kill, ModRefinements.V.health2));
    public static final RegistryObject<WerewolfRefinementSet> stun_bite = REFINEMENT_SETS.register("stun_bite", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE,0xedb521, ModRefinements.stun_bite));
    public static final RegistryObject<WerewolfRefinementSet> bleeding_bite = REFINEMENT_SETS.register("bleeding_bite", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC,0xcdc2639, ModRefinements.bleeding_bite));
    public static final RegistryObject<WerewolfRefinementSet> variable_bite = REFINEMENT_SETS.register("variable_bite", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC,0xcdc2639, ModRefinements.bleeding_bite, ModRefinements.stun_bite));
    public static final RegistryObject<WerewolfRefinementSet> more_wolves = REFINEMENT_SETS.register("more_wolves", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON,0x929292, ModRefinements.more_wolves));
    public static final RegistryObject<WerewolfRefinementSet> greater_doge_chance = REFINEMENT_SETS.register("greater_doge_chance", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON,0x4df1f3, ModRefinements.greater_doge_chance));

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
    private static RefinementSet werewolf(IRefinementSet.Rarity rarity, RegistryObject<IRefinement>... refinementsSup) {
        IRefinement[] refinements = Arrays.stream(refinementsSup).flatMap(RegistryObject::stream).toArray(IRefinement[]::new);
        return new WerewolfRefinementSet(rarity, rarity.color.getColor(), refinements);
    }
}
