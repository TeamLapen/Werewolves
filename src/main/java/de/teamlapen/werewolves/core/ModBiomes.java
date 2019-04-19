package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.gen.BiomeGenWerewolfHeaven;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBiomes {

    public static Biome werewolfHeaven;

    static void registerBiomes(IForgeRegistry<Biome> registry) {
        werewolfHeaven = new BiomeGenWerewolfHeaven().setRegistryName(REFERENCE.MODID, "werewolfHeaven");
        registry.register(werewolfHeaven);
        BiomeDictionary.addTypes(werewolfHeaven, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.SPOOKY);
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(werewolfHeaven, 99));
    }
}
