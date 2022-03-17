package de.teamlapen.werewolves.world;

import de.teamlapen.vampirism.world.biome.VampirismBiomeFeatures;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.ModEntities;
import net.minecraft.world.biome.*;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class WerewolfHeavenBiome {

    public static Biome createWerewolfHeavenBiome() {
        return new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.FOREST).depth(0.2f).scale(0.5f).temperature(0.3f).downfall(0)
                .specialEffects(getAmbienceBuilder().build())
                .mobSpawnSettings(getMobSpawnBuilder().build())
                .generationSettings(getGenerationBuilder().build())
                .build();
    }

    public static MobSpawnSettings.Builder getMobSpawnBuilder() {
        MobSpawnSettings.Builder mob_builder = new MobSpawnSettings.Builder();
        mob_builder.addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.alpha_werewolf, 10, 1, 1));
        mob_builder.addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.werewolf_beast, 70, 1, 2));
        mob_builder.addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.werewolf_survivalist, 70, 1, 2));
        return mob_builder;
    }

    public static BiomeSpecialEffects.Builder getAmbienceBuilder() {
        BiomeSpecialEffects.Builder ambienceBuilder = new BiomeSpecialEffects.Builder();
        ambienceBuilder.waterColor(0x4CCCFF);
        ambienceBuilder.waterFogColor(0x4CCCFF);
        ambienceBuilder.skyColor(0x66DBFF);
        ambienceBuilder.fogColor(0x4CCCFF);//TODO
        ambienceBuilder.foliageColorOverride(0x70E0B5);
        ambienceBuilder.grassColorOverride(0x69CFDB);
        ambienceBuilder.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);
        return ambienceBuilder;
    }

    public static BiomeGenerationSettings.Builder getGenerationBuilder() {
        BiomeGenerationSettings.Builder biomeGeneratorSettings = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_GRASS));

        BiomeDefaultFeatures.addDefaultCarvers(biomeGeneratorSettings);

        BiomeDefaultFeatures.addForestGrass(biomeGeneratorSettings);
        WerewolvesBiomeFeatures.addWerewolfBiomeTrees(biomeGeneratorSettings);

        BiomeDefaultFeatures.addDefaultSoftDisks(biomeGeneratorSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeGeneratorSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeGeneratorSettings);
        BiomeDefaultFeatures.addDefaultOres(biomeGeneratorSettings);
        BiomeDefaultFeatures.addSparseBerryBushes(biomeGeneratorSettings);
        VampirismBiomeFeatures.addModdedWaterLake(biomeGeneratorSettings);
        BiomeDefaultFeatures.addDefaultFlowers(biomeGeneratorSettings);
        BiomeDefaultFeatures.addSavannaGrass(biomeGeneratorSettings);
        return biomeGeneratorSettings;
    }

}
