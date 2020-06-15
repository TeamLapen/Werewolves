package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.WerewolfHeaven;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class WBiomes {

    public static final Biome werewolf_heaven = getNull();

    static void registerBiomes(IForgeRegistry<Biome> registry) {
        registry.register(new WerewolfHeaven().setRegistryName(REFERENCE.MODID, "werewolf_heaven"));
    }

    static void addBiomes() {
        BiomeDictionary.addTypes(werewolf_heaven, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.MAGICAL);
        if (!WerewolvesConfig.SERVER.disableWerewolfHeaven.get()) {
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(werewolf_heaven, WerewolvesConfig.BALANCE.UTIL.werewolfHeavenWeight.get()));
        }
    }
}
