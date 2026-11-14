package de.teamlapen.werewolves.common.core;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.features.WerewolfForestBiome;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;


public class ModBiomes {

    public static final ResourceKey<Biome> WEREWOLF_FOREST = ResourceKey.create(Registries.BIOME, WIdentifier.mod("werewolf_heaven"));

    public static void createBiomes(BootstrapContext<Biome> context) {
        context.register(WEREWOLF_FOREST, WerewolfForestBiome.createWerewolfForestBiome(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER)));
    }
}
