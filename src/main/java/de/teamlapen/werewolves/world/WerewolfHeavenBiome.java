package de.teamlapen.werewolves.world;

import de.teamlapen.vampirism.world.biome.VampirismBiomeFeatures;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.ModEntities;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.*;

public class WerewolfHeavenBiome {

    public static Biome createWerewolfHeavenBiome() {
        return addAttributes(createGenerationBuilder(), createMobSpawnBuilder(), createEffectBuilder()).build();
    }

    public static Biome.BiomeBuilder addAttributes(BiomeGenerationSettings.Builder featureBuilder, MobSpawnSettings.Builder spawnBuilder, BiomeSpecialEffects.Builder ambienceBuilder) {
        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE)
                .biomeCategory(Biome.BiomeCategory.FOREST)
                .temperature(0.3f)
                .downfall(0)
                .specialEffects(ambienceBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(featureBuilder.build());
    }

    public static MobSpawnSettings.Builder createMobSpawnBuilder() {
        return new MobSpawnSettings.Builder()
                .addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.alpha_werewolf, 10, 1, 1))
                .addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.werewolf_beast, 70, 1, 2))
                .addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.werewolf_survivalist, 70, 1, 2));
    }

    public static BiomeSpecialEffects.Builder createEffectBuilder() {
        return new BiomeSpecialEffects.Builder()
                .waterColor(0x4CCCFF)
                .waterFogColor(0x4CCCFF)
                .skyColor(0x66DBFF)
                .fogColor(0x4CCCFF) //TODO
                .foliageColorOverride(0x70E0B5)
                .grassColorOverride(0x69CFDB)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);
    }

    public static BiomeGenerationSettings.Builder createGenerationBuilder() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        VampirismBiomeFeatures.addModdedWaterLake(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);


        WerewolvesBiomeFeatures.addWerewolfBiomeTrees(builder);
        BiomeDefaultFeatures.addDefaultFlowers(builder);
        BiomeDefaultFeatures.addForestGrass(builder);
        BiomeDefaultFeatures.addSavannaGrass(builder);
        BiomeDefaultFeatures.addRareBerryBushes(builder);
        return builder;
    }
}
