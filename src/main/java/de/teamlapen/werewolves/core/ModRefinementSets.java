package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinement;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinementSet;
import de.teamlapen.vampirism.entity.player.refinements.RefinementSet;
import de.teamlapen.werewolves.entities.player.WerewolfRefinementSet;
import de.teamlapen.werewolves.util.REFERENCE;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRefinementSets {

    public static final DeferredRegister<IRefinementSet> REFINEMENT_SETS = DeferredRegister.create(VampirismRegistries.Keys.REFINEMENT_SET, REFERENCE.MODID);

    static void register(IEventBus bus) {
        REFINEMENT_SETS.register(bus);
    }

    static {
        // Common refinement set
        {
            // attribute modifier
            REFINEMENT_SETS.register("armor1", () -> commonW(ModRefinements.V.ARMOR_1));
            REFINEMENT_SETS.register("health1", () -> commonW(ModRefinements.V.HEALTH_1));
            REFINEMENT_SETS.register("speed1", () -> commonW(ModRefinements.V.SPEED_1));
            REFINEMENT_SETS.register("attack_speed1", () -> commonW(ModRefinements.V.ATTACK_SPEED_1));
            REFINEMENT_SETS.register("damage1", () -> commonW(ModRefinements.V.DAMAGE_1));
            REFINEMENT_SETS.register("damage1_attack_speed1_n_armor2", () -> commonW(ModRefinements.V.DAMAGE_1, ModRefinements.V.ATTACK_SPEED_1, ModRefinements.V.N_ARMOR_2));
            REFINEMENT_SETS.register("armor1_health1_n_attack_speed2", () -> commonW(ModRefinements.V.ARMOR_1, ModRefinements.V.HEALTH_1, ModRefinements.V.N_ATTACK_SPEED_2));
        }
        //Uncommon refinement sets
        {
            // attribute modifier
            REFINEMENT_SETS.register("armor2_n_health2", () -> uncommonW(ModRefinements.V.ARMOR_2, ModRefinements.V.N_HEALTH_2));
            REFINEMENT_SETS.register("health2_n_damage1", () -> uncommonW(ModRefinements.V.HEALTH_2, ModRefinements.V.N_DAMAGE_1));
            REFINEMENT_SETS.register("attack_speed2_n_armor1", () -> uncommonW(ModRefinements.V.ATTACK_SPEED_2, ModRefinements.V.N_ARMOR_1));
            REFINEMENT_SETS.register("damage2_n_speed1", () -> uncommonW(ModRefinements.V.DAMAGE_2, ModRefinements.V.N_SPEED_1));
            REFINEMENT_SETS.register("speed2_n_damage1", () -> uncommonW(ModRefinements.V.SPEED_2, ModRefinements.V.N_DAMAGE_1));

            // better attribute modifier with de-buffs
            REFINEMENT_SETS.register("armor3_n_health3", () -> uncommonW(ModRefinements.V.ARMOR_3, ModRefinements.V.N_HEALTH_3));
            REFINEMENT_SETS.register("health3_n_damage2", () -> uncommonW(ModRefinements.V.HEALTH_3, ModRefinements.V.N_DAMAGE_2));
            REFINEMENT_SETS.register("attack_speed3_n_armor2", () -> uncommonW(ModRefinements.V.ATTACK_SPEED_3, ModRefinements.V.N_ARMOR_2));
            REFINEMENT_SETS.register("damage3_n_speed2", () -> uncommonW(ModRefinements.V.DAMAGE_3, ModRefinements.V.N_SPEED_3));
            REFINEMENT_SETS.register("speed3_n_damage2", () -> uncommonW(ModRefinements.V.SPEED_3, ModRefinements.V.N_DAMAGE_2));
        }
        // Rare refinement sets
        {
            // attribute modifier
            REFINEMENT_SETS.register("armor3_n_health2", () -> rareW(ModRefinements.V.ARMOR_3, ModRefinements.V.N_HEALTH_2));
            REFINEMENT_SETS.register("health3_n_armor1", () -> rareW(ModRefinements.V.HEALTH_3, ModRefinements.V.N_ARMOR_1));
            REFINEMENT_SETS.register("attack_speed3_n_speed1", () -> rareW(ModRefinements.V.ATTACK_SPEED_3, ModRefinements.V.N_SPEED_1));
            REFINEMENT_SETS.register("speed1_armor1_health1", () -> rareW(ModRefinements.V.SPEED_1, ModRefinements.V.ARMOR_1, ModRefinements.V.HEALTH_1));
            REFINEMENT_SETS.register("damage3_n_armor1", () -> rareW(ModRefinements.V.DAMAGE_3, ModRefinements.V.N_ARMOR_2));
            REFINEMENT_SETS.register("speed3_n_attack_speed1", () -> rareW(ModRefinements.V.SPEED_3, ModRefinements.V.N_ATTACK_SPEED_1));
            REFINEMENT_SETS.register("damage1_attack_speed1", () -> rareW(ModRefinements.V.DAMAGE_1, ModRefinements.V.ATTACK_SPEED_1));
        }
        {
            REFINEMENT_SETS.register("werewolf_form_duration_general_2", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.LEGENDARY, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_2));
            REFINEMENT_SETS.register("werewolf_form_duration_general_2_n_damage_n_speed", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_2, ModRefinements.V.N_DAMAGE_2, ModRefinements.V.N_SPEED_2));
            REFINEMENT_SETS.register("werewolf_form_duration_general_1", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_1));
            REFINEMENT_SETS.register("werewolf_form_duration_general_1_n_damage_n_speed", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_GENERAL_1, ModRefinements.V.N_DAMAGE_2, ModRefinements.V.N_SPEED_2));
            REFINEMENT_SETS.register("werewolf_form_duration_survival_2", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_SURVIVAL_2));
            REFINEMENT_SETS.register("werewolf_form_duration_survival_1", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_SURVIVAL_1));
            REFINEMENT_SETS.register("werewolf_form_duration_beast_2", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_BEAST_2));
            REFINEMENT_SETS.register("werewolf_form_duration_beast_1", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON, 0xc86262, ModRefinements.WEREWOLF_FORM_DURATION_BEAST_1));
            REFINEMENT_SETS.register("rage_fury", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.LEGENDARY, 0xff0006, ModRefinements.RAGE_FURY));
            REFINEMENT_SETS.register("health_after_kill", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.COMMON, 0x16af00, ModRefinements.HEALTH_AFTER_KILL));
            REFINEMENT_SETS.register("health", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE, 0x16af00, ModRefinements.HEALTH_AFTER_KILL, ModRefinements.V.HEALTH_2));
            REFINEMENT_SETS.register("stun_bite", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.RARE, 0xedb521, ModRefinements.STUN_BITE));
            REFINEMENT_SETS.register("bleeding_bite", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC, 0xcdc2639, ModRefinements.BLEEDING_BITE));
            REFINEMENT_SETS.register("variable_bite", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC, 0xcdc2639, ModRefinements.BLEEDING_BITE, ModRefinements.STUN_BITE));
            REFINEMENT_SETS.register("more_wolves", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON, 0x929292, ModRefinements.MORE_WOLVES));
            REFINEMENT_SETS.register("greater_doge_chance", () -> new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON, 0x4df1f3, ModRefinements.GREATER_DOGE_CHANCE));
        }
    }

    @SafeVarargs
    private static IRefinementSet commonW(Supplier<IRefinement>... refinements) {
        return werewolf(IRefinementSet.Rarity.COMMON, refinements);
    }

    @SafeVarargs
    private static IRefinementSet uncommonW(Supplier<IRefinement>... refinements) {
        return werewolf(IRefinementSet.Rarity.UNCOMMON, refinements);
    }

    @SafeVarargs
    private static IRefinementSet rareW(Supplier<IRefinement>... refinements) {
        return werewolf(IRefinementSet.Rarity.RARE, refinements);
    }

    @SafeVarargs
    private static IRefinementSet epicW(Supplier<IRefinement>... refinements) {
        return werewolf(IRefinementSet.Rarity.EPIC, refinements);
    }

    @SafeVarargs
    private static IRefinementSet legendaryW(Supplier<IRefinement>... refinements) {
        return werewolf(IRefinementSet.Rarity.LEGENDARY, refinements);
    }

    @SafeVarargs
    @SuppressWarnings("ConstantConditions")
    private static RefinementSet werewolf(IRefinementSet.Rarity rarity, Supplier<IRefinement>... refinements) {
        return new WerewolfRefinementSet(rarity, rarity.color.getColor(), refinements);
    }
}
