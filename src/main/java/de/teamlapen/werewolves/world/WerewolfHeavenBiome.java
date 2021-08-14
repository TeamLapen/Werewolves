package de.teamlapen.werewolves.world;

import de.teamlapen.vampirism.world.biome.VampirismBiomeFeatures;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.ModEntities;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class WerewolfHeavenBiome {

    public static Biome createWerewolfHeavenBiome() {
        return new Biome.Builder().precipitation(Biome.RainType.NONE).biomeCategory(Biome.Category.FOREST).depth(0.2f).scale(0.5f).temperature(0.3f).downfall(0)
                .specialEffects(getAmbienceBuilder().build())
                .mobSpawnSettings(getMobSpawnBuilder().build())
                .generationSettings(getGenerationBuilder().build())
                .build();
    }

    public static MobSpawnInfo.Builder getMobSpawnBuilder() {
        MobSpawnInfo.Builder mob_builder = new MobSpawnInfo.Builder();
        mob_builder.creatureGenerationProbability(0.25f);
        mob_builder.addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnInfo.Spawners(ModEntities.werewolf_beast, 70, 1, 2));
        mob_builder.addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnInfo.Spawners(ModEntities.werewolf_survivalist, 70, 1, 2));
        return mob_builder;
    }

    public static BiomeAmbience.Builder getAmbienceBuilder() {
        BiomeAmbience.Builder ambienceBuilder = new BiomeAmbience.Builder();
        ambienceBuilder.waterColor(0x4CCCFF);
        ambienceBuilder.waterFogColor(0x4CCCFF);
        ambienceBuilder.skyColor(0x66DBFF);
        ambienceBuilder.fogColor(0x4CCCFF);//TODO
        ambienceBuilder.foliageColorOverride(0x70E0B5);
        ambienceBuilder.grassColorOverride(0x69CFDB);
        ambienceBuilder.ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS);
        return ambienceBuilder;
    }

    public static BiomeGenerationSettings.Builder getGenerationBuilder() {
        BiomeGenerationSettings.Builder biomeGeneratorSettings = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_GRASS));

        DefaultBiomeFeatures.addDefaultCarvers(biomeGeneratorSettings);

        DefaultBiomeFeatures.addForestGrass(biomeGeneratorSettings);
        WerewolvesBiomeFeatures.addWerewolfBiomeTrees(biomeGeneratorSettings);

        DefaultBiomeFeatures.addDefaultSoftDisks(biomeGeneratorSettings);
        DefaultBiomeFeatures.addDefaultUndergroundVariety(biomeGeneratorSettings);
        DefaultBiomeFeatures.addDefaultMonsterRoom(biomeGeneratorSettings);
        DefaultBiomeFeatures.addDefaultOres(biomeGeneratorSettings);
        DefaultBiomeFeatures.addSparseBerryBushes(biomeGeneratorSettings);
        VampirismBiomeFeatures.addModdedWaterLake(biomeGeneratorSettings);
        DefaultBiomeFeatures.addDefaultFlowers(biomeGeneratorSettings);
        DefaultBiomeFeatures.addSavannaGrass(biomeGeneratorSettings);
        return biomeGeneratorSettings;
    }

}
