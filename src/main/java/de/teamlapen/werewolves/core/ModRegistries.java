package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRegistries {

    public static final RegistrySetBuilder DATA_BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, ModBiomes::createBiomes)
            .add(Registries.CONFIGURED_FEATURE, WerewolvesBiomeFeatures::createConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, WerewolvesBiomeFeatures::createPlacedFeatures)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, WerewolvesBiomeFeatures::createBiomeModifier)
            .add(Registries.DAMAGE_TYPE, ModDamageTypes::createDamageTypes)
            ;

}
