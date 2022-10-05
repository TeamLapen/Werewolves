package de.teamlapen.werewolves.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.core.ModFeatures;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.WerewolfHeavenBiome;
import de.teamlapen.werewolves.world.WerewolvesBiomeFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;


public class ModBiomes {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, REFERENCE.MODID);

    public static final RegistryKey<Biome> WEREWOLF_HEAVEN_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(REFERENCE.MODID, "werewolf_heaven"));

    public static final RegistryObject<Biome> WEREWOLF_HEAVEN = BIOMES.register("werewolf_heaven", WerewolfHeavenBiome::createWerewolfHeavenBiome);

    static void registerBiomes(IEventBus bus) {
        BIOMES.register(bus);

        BiomeDictionary.addTypes(WEREWOLF_HEAVEN_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.HILLS);

    }

    static void removeStructuresFromBiomes() {
        VampirismAPI.worldGenRegistry().removeStructureFromBiomes(ModFeatures.HUNTER_CAMP.getId(), Lists.newArrayList(WEREWOLF_HEAVEN_KEY.location()));
    }

    static void addBiomesToGeneratorUnsafe() {
        if (!WerewolvesConfig.COMMON.disableWerewolfHeaven.get()) {
            List<RegistryKey<Biome>> modlist = new ArrayList<>(OverworldBiomeProvider.POSSIBLE_BIOMES);
            modlist.add(WEREWOLF_HEAVEN_KEY);
            OverworldBiomeProvider.POSSIBLE_BIOMES = ImmutableList.copyOf(modlist);
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(WEREWOLF_HEAVEN_KEY, WerewolvesConfig.COMMON.werewolfHeavenWeight.get()));
        }
    }

    public static void onBiomeLoadingEventAdditions(BiomeLoadingEvent event) {
        if (!WerewolvesConfig.COMMON.disableOreGen.get() && event.getCategory() != Biome.Category.THEEND && event.getCategory() != Biome.Category.NETHER) {
            event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, WerewolvesBiomeFeatures.silver_ore);
        }
        if (event.getCategory() == Biome.Category.FOREST) {
            WerewolvesBiomeFeatures.addWerewolvesFlowers(event.getGeneration());
        }

        if (event.getCategory() == Biome.Category.TAIGA) {
            event.getSpawns().addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(ModEntities.HUMAN_WEREWOLF.get(), 5, 1, 1));
            event.getSpawns().addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(ModEntities.WEREWOLF_SURVIVALIST.get(), 80, 1, 2));
            event.getSpawns().addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(ModEntities.WEREWOLF_BEAST.get(), 80, 1, 2));
        }

    }
}
