package de.teamlapen.werewolves.core;

import com.google.common.collect.Lists;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.core.ModFeatures;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.WerewolfHeavenBiome;
import de.teamlapen.werewolves.world.WerewolvesBiomeFeatures;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;


public class ModBiomes {
    @ObjectHolder(REFERENCE.MODID + ":werewolf_heaven")
    public static final Biome werewolf_heaven = getNull();
    public static final ResourceKey<Biome> WEREWOLF_HEAVEN_KEY = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(REFERENCE.MODID, "werewolf_heaven"));

    static void registerBiomes(IForgeRegistry<Biome> registry) {
        registry.register(WerewolfHeavenBiome.createWerewolfHeavenBiome().setRegistryName(WEREWOLF_HEAVEN_KEY.location()));

        BiomeDictionary.addTypes(WEREWOLF_HEAVEN_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.HILLS);
    }

    static void removeStructuresFromBiomes() {
        VampirismAPI.worldGenRegistry().removeStructureFromBiomes(ModFeatures.hunter_camp.getRegistryName(), Lists.newArrayList(WEREWOLF_HEAVEN_KEY.location()));
    }

    static void addBiomesToGeneratorUnsafe() {
        if (!WerewolvesConfig.COMMON.disableWerewolfHeaven.get()) {
            BiomeManager.addAdditionalOverworldBiomes(WEREWOLF_HEAVEN_KEY);
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(WEREWOLF_HEAVEN_KEY, WerewolvesConfig.COMMON.werewolfHeavenWeight.get()));
        }
    }

    public static void onBiomeLoadingEventAdditions(BiomeLoadingEvent event) {
        if (!WerewolvesConfig.COMMON.disableOreGen.get() && event.getCategory() != Biome.BiomeCategory.THEEND && event.getCategory() != Biome.BiomeCategory.NETHER) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WerewolvesBiomeFeatures.silver_ore_placed);
        }
        if (event.getCategory() == Biome.BiomeCategory.FOREST) {
            WerewolvesBiomeFeatures.addWerewolvesFlowers(event.getGeneration());
        }

        if (event.getCategory() == Biome.BiomeCategory.TAIGA) {
            event.getSpawns().addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.human_werewolf, 3, 1, 1));
            event.getSpawns().addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.werewolf_survivalist, 1, 1, 1));
            event.getSpawns().addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.werewolf_beast, 1, 1, 1));
        }

    }
}
