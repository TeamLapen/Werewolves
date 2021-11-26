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
        ModRefinements.VampirismRefinements.validate();
        // Common refinement set
        {
            // attribute modifier
            registry.register(commonW("armor1", ModRefinements.VampirismRefinements.armor1));
            registry.register(commonW("health1", ModRefinements.VampirismRefinements.health1));
            registry.register(commonW("speed1", ModRefinements.VampirismRefinements.speed1));
            registry.register(commonW("attack_speed1", ModRefinements.VampirismRefinements.attack_speed1));
            registry.register(commonW("damage1", ModRefinements.VampirismRefinements.damage1));
            registry.register(commonW("damage1_attack_speed1_n_armor2", ModRefinements.VampirismRefinements.damage1, ModRefinements.VampirismRefinements.attack_speed1, ModRefinements.VampirismRefinements.n_armor2));
            registry.register(commonW("armor1_health1_n_attack_speed2", ModRefinements.VampirismRefinements.armor1, ModRefinements.VampirismRefinements.health1, ModRefinements.VampirismRefinements.n_attack_speed2));
        }
        //Uncommon refinement sets
        {
            // attribute modifier
            registry.register(uncommonW("armor2_n_health2", ModRefinements.VampirismRefinements.armor2, ModRefinements.VampirismRefinements.n_health2));
            registry.register(uncommonW("health2_n_damage1", ModRefinements.VampirismRefinements.health2, ModRefinements.VampirismRefinements.n_damage1));
            registry.register(uncommonW("attack_speed2_n_armor1", ModRefinements.VampirismRefinements.attack_speed2, ModRefinements.VampirismRefinements.n_armor1));
            registry.register(uncommonW("damage2_n_speed1", ModRefinements.VampirismRefinements.damage2, ModRefinements.VampirismRefinements.n_speed1));
            registry.register(uncommonW("speed2_n_damage1", ModRefinements.VampirismRefinements.speed2, ModRefinements.VampirismRefinements.n_damage1));

            // better attribute modifier with de-buffs
            registry.register(uncommonW("armor3_n_health3", ModRefinements.VampirismRefinements.armor3, ModRefinements.VampirismRefinements.n_health3));
            registry.register(uncommonW("health3_n_damage2", ModRefinements.VampirismRefinements.health3, ModRefinements.VampirismRefinements.n_damage2));
            registry.register(uncommonW("attack_speed3_n_armor2", ModRefinements.VampirismRefinements.attack_speed3, ModRefinements.VampirismRefinements.n_armor2));
            registry.register(uncommonW("damage3_n_speed2", ModRefinements.VampirismRefinements.damage3, ModRefinements.VampirismRefinements.n_speed3));
            registry.register(uncommonW("speed3_n_damage2", ModRefinements.VampirismRefinements.speed3, ModRefinements.VampirismRefinements.n_damage2));
        }
        // Rare refinement sets
        {
            // attribute modifier
            registry.register(rareW("armor3_n_health2", ModRefinements.VampirismRefinements.armor3, ModRefinements.VampirismRefinements.n_health2));
            registry.register(rareW("health3_n_armor1", ModRefinements.VampirismRefinements.health3, ModRefinements.VampirismRefinements.n_armor1));
            registry.register(rareW("attack_speed3_n_speed1", ModRefinements.VampirismRefinements.attack_speed3, ModRefinements.VampirismRefinements.n_speed1));
            registry.register(rareW("speed1_armor1_health1", ModRefinements.VampirismRefinements.speed1, ModRefinements.VampirismRefinements.armor1, ModRefinements.VampirismRefinements.health1));
            registry.register(rareW("damage3_n_armor1", ModRefinements.VampirismRefinements.damage3, ModRefinements.VampirismRefinements.n_armor2));
            registry.register(rareW("speed3_n_attack_speed1", ModRefinements.VampirismRefinements.speed3, ModRefinements.VampirismRefinements.n_attack_speed1));
            registry.register(rareW("damage1_attack_speed1", ModRefinements.VampirismRefinements.damage1, ModRefinements.VampirismRefinements.attack_speed1));
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

    @SuppressWarnings("ConstantConditions")
    private static RefinementSet werewolf(IRefinementSet.Rarity rarity, IRefinement... refinements) {
        refinements = Arrays.stream(refinements).filter(Objects::nonNull).toArray(IRefinement[]::new);
        return new WerewolfRefinementSet(rarity, rarity.color.getColor(), refinements);
    }
}
