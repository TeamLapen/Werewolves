package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.werewolves.common.world.features.WerewolvesBiomeFeatures;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModRegistries {

    public static final RegistrySetBuilder DATA_BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, ModBiomes::createBiomes)
            .add(Registries.CONFIGURED_FEATURE, WerewolvesBiomeFeatures::createConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, WerewolvesBiomeFeatures::createPlacedFeatures)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, WerewolvesBiomeFeatures::createBiomeModifier)
            .add(Registries.DAMAGE_TYPE, ModDamageTypes::createDamageTypes)
            .add(FactionRegistries.Keys.TASK, ModTasks::createTasks)
            .add(FactionRegistries.Keys.SKILL_SEGMENT, ModSkills.Nodes::createSkillNodes)
            .add(FactionRegistries.Keys.SKILL_TREE, ModSkills.Trees::createSkillTrees)
            .add(Registries.TRADE_SET, ModTrades::bootstrapTradeSets)
            ;

}
