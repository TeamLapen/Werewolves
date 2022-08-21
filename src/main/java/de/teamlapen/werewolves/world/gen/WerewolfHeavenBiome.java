package de.teamlapen.werewolves.world.gen;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.ModEntities;
import net.minecraft.world.level.biome.*;

import static de.teamlapen.vampirism.world.biome.VampirismBiomes.addModdedWaterLake;
import static de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures.addWerewolfBiomeTrees;
import static net.minecraft.data.worldgen.BiomeDefaultFeatures.*;

public class WerewolfHeavenBiome {

    public static Biome createWerewolfHeavenBiome() {
        return addAttributes(createGenerationBuilder(), createMobSpawnBuilder(), createEffectBuilder()).build();
    }

    public static Biome.BiomeBuilder addAttributes(BiomeGenerationSettings.Builder featureBuilder, MobSpawnSettings.Builder spawnBuilder, BiomeSpecialEffects.Builder ambienceBuilder) {
        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE)
                .temperature(0.3f)
                .downfall(0)
                .specialEffects(ambienceBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(featureBuilder.build());
    }

    public static MobSpawnSettings.Builder createMobSpawnBuilder() {
        return new MobSpawnSettings.Builder()
                .addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.alpha_werewolf.get(), 10, 1, 1))
                .addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.werewolf_beast.get(), 70, 1, 2))
                .addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.werewolf_survivalist.get(), 70, 1, 2));
    }

    public static BiomeSpecialEffects.Builder createEffectBuilder() {
        return new BiomeSpecialEffects.Builder()
                .waterColor(0x4CCCFF)
                .waterFogColor(0x4CCCFF)
                .skyColor(0x66DBFF)
                .fogColor(0x4CCCFF)
                .foliageColorOverride(0x70E0B5)
                .grassColorOverride(0x69CFDB)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);
    }

    public static BiomeGenerationSettings.Builder createGenerationBuilder() {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder();
        addDefaultCarversAndLakes(builder);
        addModdedWaterLake(builder);
        addDefaultMonsterRoom(builder);
        addDefaultUndergroundVariety(builder);
        addDefaultOres(builder);
        addDefaultSoftDisks(builder);


        addWerewolfBiomeTrees(builder);
        addDefaultFlowers(builder);
        addForestGrass(builder);
//        addSavannaGrass(builder); TODO causing mysterious feature cycle order with Oh the biomes you'll go
        addRareBerryBushes(builder);
        return builder;
    }
}
