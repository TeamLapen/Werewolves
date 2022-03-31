package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.gen.WerewolfHeavenBiome;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.IForgeRegistry;


public class ModBiomes {

    public static final ResourceKey<Biome> WEREWOLF_HEAVEN = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(REFERENCE.MODID, "werewolf_heaven"));

    static void registerBiomes(IForgeRegistry<Biome> registry) {
        registry.register(WerewolfHeavenBiome.createWerewolfHeavenBiome().setRegistryName(WEREWOLF_HEAVEN.location()));

        BiomeDictionary.addTypes(WEREWOLF_HEAVEN, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.HILLS);
    }
}
