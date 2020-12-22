package de.teamlapen.werewolves.world;

import de.teamlapen.vampirism.world.biome.VampirismBiomeFeatures;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.ModEntities;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class WerewolfHeavenBiome {

    public static Biome createWerewolfHeavenBiome() {
        return new Biome.Builder().precipitation(Biome.RainType.NONE).category(Biome.Category.FOREST).depth(0.2f).scale(0.5f).temperature(0.3f).downfall(0)
                .setEffects(getAmbienceBuilder().build())
                .withMobSpawnSettings(getMobSpawnBuilder().copy())
                .withGenerationSettings(getGenerationBuilder().build())
                .build();
    }

    public static MobSpawnInfo.Builder getMobSpawnBuilder() {
        MobSpawnInfo.Builder mob_builder = new MobSpawnInfo.Builder();
        mob_builder.withCreatureSpawnProbability(0.25f);
        mob_builder.withSpawner(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnInfo.Spawners(ModEntities.werewolf_beast, 70, 1, 2));
        mob_builder.withSpawner(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnInfo.Spawners(ModEntities.werewolf_survivalist, 70, 1, 2));
        return mob_builder;
    }

    public static BiomeAmbience.Builder getAmbienceBuilder() {
        BiomeAmbience.Builder ambienceBuilder = new BiomeAmbience.Builder();
        ambienceBuilder.setWaterColor(0x4CCCFF);
        ambienceBuilder.setWaterFogColor(0x4CCCFF);
        ambienceBuilder.withSkyColor(0x66DBFF);
        ambienceBuilder.setFogColor(0x4CCCFF);//TODO
        ambienceBuilder.withFoliageColor(0x70E0B5);
        ambienceBuilder.withGrassColor(0x69CFDB);
        ambienceBuilder.setMoodSound(MoodSoundAmbience.DEFAULT_CAVE);
        return ambienceBuilder;
    }

    public static BiomeGenerationSettings.Builder getGenerationBuilder() {
        BiomeGenerationSettings.Builder biomeGeneratorSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG));

        DefaultBiomeFeatures.withCavesAndCanyons(biomeGeneratorSettings);

        DefaultBiomeFeatures.withForestGrass(biomeGeneratorSettings);
        WerewolvesBiomeFeatures.addWerewolfBiomeTrees(biomeGeneratorSettings);

        DefaultBiomeFeatures.withDisks(biomeGeneratorSettings);
        DefaultBiomeFeatures.withCommonOverworldBlocks(biomeGeneratorSettings);
        DefaultBiomeFeatures.withMonsterRoom(biomeGeneratorSettings);
        DefaultBiomeFeatures.withOverworldOres(biomeGeneratorSettings);
        DefaultBiomeFeatures.withSparseBerries(biomeGeneratorSettings);
        VampirismBiomeFeatures.addModdedWaterLake(biomeGeneratorSettings);
        DefaultBiomeFeatures.withDefaultFlowers(biomeGeneratorSettings);
        DefaultBiomeFeatures.withTallGrass(biomeGeneratorSettings);
        return biomeGeneratorSettings;
    }

}
