package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.refinement.IRefinement;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinementSet;
import de.teamlapen.vampirism.player.refinements.RefinementSet;
import de.teamlapen.werewolves.entities.player.WerewolfRefinementSet;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Arrays;
import java.util.Objects;

public class ModRefinementSets {

    public static void register(IForgeRegistry<IRefinementSet> registry) {
        ModRefinements.V.validate();
        // Common refinement set
        {
            // attribute modifier
            registry.register(commonW("armor1", ModRefinements.V.armor1));
            registry.register(commonW("health1", ModRefinements.V.health1));
            registry.register(commonW("speed1", ModRefinements.V.speed1));
            registry.register(commonW("attack_speed1", ModRefinements.V.attack_speed1));
            registry.register(commonW("damage1", ModRefinements.V.damage1));
            registry.register(commonW("damage1_attack_speed1_n_armor2", ModRefinements.V.damage1, ModRefinements.V.attack_speed1, ModRefinements.V.n_armor2));
            registry.register(commonW("armor1_health1_n_attack_speed2", ModRefinements.V.armor1, ModRefinements.V.health1, ModRefinements.V.n_attack_speed2));
        }
        //Uncommon refinement sets
        {
            // attribute modifier
            registry.register(uncommonW("armor2_n_health2", ModRefinements.V.armor2, ModRefinements.V.n_health2));
            registry.register(uncommonW("health2_n_damage1", ModRefinements.V.health2, ModRefinements.V.n_damage1));
            registry.register(uncommonW("attack_speed2_n_armor1", ModRefinements.V.attack_speed2, ModRefinements.V.n_armor1));
            registry.register(uncommonW("damage2_n_speed1", ModRefinements.V.damage2, ModRefinements.V.n_speed1));
            registry.register(uncommonW("speed2_n_damage1", ModRefinements.V.speed2, ModRefinements.V.n_damage1));

            // better attribute modifier with de-buffs
            registry.register(uncommonW("armor3_n_health3", ModRefinements.V.armor3, ModRefinements.V.n_health3));
            registry.register(uncommonW("health3_n_damage2", ModRefinements.V.health3, ModRefinements.V.n_damage2));
            registry.register(uncommonW("attack_speed3_n_armor2", ModRefinements.V.attack_speed3, ModRefinements.V.n_armor2));
            registry.register(uncommonW("damage3_n_speed2", ModRefinements.V.damage3, ModRefinements.V.n_speed3));
            registry.register(uncommonW("speed3_n_damage2", ModRefinements.V.speed3, ModRefinements.V.n_damage2));
        }
        // Rare refinement sets
        {
            // attribute modifier
            registry.register(rareW("armor3_n_health2", ModRefinements.V.armor3, ModRefinements.V.n_health2));
            registry.register(rareW("health3_n_armor1", ModRefinements.V.health3, ModRefinements.V.n_armor1));
            registry.register(rareW("attack_speed3_n_speed1", ModRefinements.V.attack_speed3, ModRefinements.V.n_speed1));
            registry.register(rareW("speed1_armor1_health1", ModRefinements.V.speed1, ModRefinements.V.armor1, ModRefinements.V.health1));
            registry.register(rareW("damage3_n_armor1", ModRefinements.V.damage3, ModRefinements.V.n_armor2));
            registry.register(rareW("speed3_n_attack_speed1", ModRefinements.V.speed3, ModRefinements.V.n_attack_speed1));
            registry.register(rareW("damage1_attack_speed1", ModRefinements.V.damage1, ModRefinements.V.attack_speed1));
        }
        {
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.LEGENDARY, 0xc86262, ModRefinements.werewolf_form_duration_general_2).setRegistryName(REFERENCE.MODID, "werewolf_form_duration_general_2"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC, 0xc86262, ModRefinements.werewolf_form_duration_general_2, ModRefinements.V.n_damage2, ModRefinements.V.n_speed2).setRegistryName(REFERENCE.MODID, "werewolf_form_duration_general_2_n_damage_n_speed"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC, 0xc86262, ModRefinements.werewolf_form_duration_general_1).setRegistryName(REFERENCE.MODID, "werewolf_form_duration_general_1"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.RARE, 0xc86262, ModRefinements.werewolf_form_duration_general_1, ModRefinements.V.n_damage2, ModRefinements.V.n_speed2).setRegistryName(REFERENCE.MODID, "werewolf_form_duration_general_1_n_damage_n_speed"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.RARE, 0xc86262, ModRefinements.werewolf_form_duration_survival_2).setRegistryName(REFERENCE.MODID, "werewolf_form_duration_survival_2"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON, 0xc86262, ModRefinements.werewolf_form_duration_survival_1).setRegistryName(REFERENCE.MODID, "werewolf_form_duration_survival_1"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.RARE, 0xc86262, ModRefinements.werewolf_form_duration_beast_2).setRegistryName(REFERENCE.MODID, "werewolf_form_duration_beast_2"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON, 0xc86262, ModRefinements.werewolf_form_duration_beast_1).setRegistryName(REFERENCE.MODID, "werewolf_form_duration_beast_1"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.LEGENDARY, 0xff0006, ModRefinements.rage_fury).setRegistryName(REFERENCE.MODID, "rage_fury"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.COMMON, 0x16af00, ModRefinements.health_after_kill).setRegistryName(REFERENCE.MODID, "health_after_kill"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.RARE, 0x16af00, ModRefinements.health_after_kill, ModRefinements.V.health2).setRegistryName(REFERENCE.MODID, "health"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.RARE, 0xedb521, ModRefinements.stun_bite).setRegistryName(REFERENCE.MODID, "stun_bite"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC, 0xcdc2639, ModRefinements.bleeding_bite).setRegistryName(REFERENCE.MODID, "bleeding_bite"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.EPIC, 0xcdc2639, ModRefinements.bleeding_bite, ModRefinements.stun_bite).setRegistryName(REFERENCE.MODID, "variable_bite"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON, 0x929292, ModRefinements.more_wolves).setRegistryName(REFERENCE.MODID, "more_wolves"));
            registry.register(new WerewolfRefinementSet(IRefinementSet.Rarity.UNCOMMON, 0x4df1f3, ModRefinements.greater_doge_chance).setRegistryName(REFERENCE.MODID, "greater_doge_chance"));
        }
    }

    private static IRefinementSet commonW(String name, IRefinement... refinements) {
        return werewolf(IRefinementSet.Rarity.COMMON, refinements).setRegistryName(REFERENCE.MODID, name);
    }

    private static IRefinementSet uncommonW(String name, IRefinement... refinements) {
        return werewolf(IRefinementSet.Rarity.UNCOMMON, refinements).setRegistryName(REFERENCE.MODID, name);
    }

    private static IRefinementSet rareW(String name, IRefinement... refinements) {
        return werewolf(IRefinementSet.Rarity.RARE, refinements).setRegistryName(REFERENCE.MODID, name);
    }

    private static IRefinementSet epicW(String name, IRefinement... refinements) {
        return werewolf(IRefinementSet.Rarity.EPIC, refinements).setRegistryName(REFERENCE.MODID, name);
    }

    private static IRefinementSet legendaryW(String name, IRefinement... refinements) {
        return werewolf(IRefinementSet.Rarity.LEGENDARY, refinements).setRegistryName(REFERENCE.MODID, name);
    }

    @SuppressWarnings("ConstantConditions")
    private static RefinementSet werewolf(IRefinementSet.Rarity rarity, IRefinement... refinements) {
        refinements = Arrays.stream(refinements).filter(Objects::nonNull).toArray(IRefinement[]::new);
        return new WerewolfRefinementSet(rarity, rarity.color.getColor(), refinements);
    }
}
