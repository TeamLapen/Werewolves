package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.gen.WerewolfForestBiome;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;


public class ModBiomes {

    public static final ResourceKey<Biome> WEREWOLF_FOREST = ResourceKey.create(Registries.BIOME, WResourceLocation.mod("werewolf_heaven"));

    public static void createBiomes(BootstrapContext<Biome> context) {
        context.register(WEREWOLF_FOREST, WerewolfForestBiome.createWerewolfForestBiome(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER)));
    }
}
