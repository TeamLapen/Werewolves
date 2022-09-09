package de.teamlapen.werewolves.world.gen;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEntities;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class WerewolvesBiomes {
    public static void onBiomeLoadingEventAdditions(BiomeLoadingEvent event) {
        if (!WerewolvesConfig.COMMON.disableOreGen.get() && event.getCategory() != Biome.BiomeCategory.THEEND && event.getCategory() != Biome.BiomeCategory.NETHER) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WerewolvesBiomeFeatures.silver_ore_placed);
        }
        if (event.getCategory() == Biome.BiomeCategory.FOREST) {
            WerewolvesBiomeFeatures.addWerewolvesFlowers(event.getGeneration());
        }

        if (event.getCategory() == Biome.BiomeCategory.TAIGA) {
            event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.human_werewolf, 5, 1, 1));
            event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.werewolf_survivalist, 80, 1, 2));
            event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.werewolf_beast, 80, 1, 2));
        }
    }
}
