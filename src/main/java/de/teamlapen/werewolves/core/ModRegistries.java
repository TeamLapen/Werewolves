package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures;
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
            .add(VampirismRegistries.Keys.TASK, ModTasks::createTasks)
            .add(VampirismRegistries.Keys.SKILL_NODE, ModSkills.Nodes::createSkillNodes)
            .add(VampirismRegistries.Keys.SKILL_TREE, ModSkills.Trees::createSkillTrees)
            ;

}
