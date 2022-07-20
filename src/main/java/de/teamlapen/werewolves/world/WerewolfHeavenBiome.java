package de.teamlapen.werewolves.world;

import de.teamlapen.vampirism.world.biome.VampirismBiomeFeatures;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.core.ModEntities;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class WerewolfHeavenBiome {

    public static Biome createWerewolfHeavenBiome() {
        return new Biome.Builder().precipitation(Biome.RainType.NONE).biomeCategory(Biome.Category.FOREST).depth(0.2f).scale(0.5f).temperature(0.3f).downfall(0)
                .specialEffects(getAmbienceBuilder().build())
                .mobSpawnSettings(getMobSpawnBuilder().build())
                .generationSettings(getGenerationBuilder().build())
                .build();
    }

    public static void addFeatures(BiomeLoadingEvent event) {
        if (event.getName().equals(ModBiomes.WEREWOLF_HEAVEN.getId())) {
            BiomeGenerationSettingsBuilder generation = event.getGeneration();
            DefaultBiomeFeatures.addDefaultCarvers(generation);

            DefaultBiomeFeatures.addForestGrass(generation);
            WerewolvesBiomeFeatures.addWerewolfBiomeTrees(generation);

            DefaultBiomeFeatures.addDefaultSoftDisks(generation);
            DefaultBiomeFeatures.addDefaultUndergroundVariety(generation);
            DefaultBiomeFeatures.addDefaultMonsterRoom(generation);
            DefaultBiomeFeatures.addDefaultOres(generation);
            DefaultBiomeFeatures.addSparseBerryBushes(generation);
            VampirismBiomeFeatures.addModdedWaterLake(generation);

            DefaultBiomeFeatures.addDefaultFlowers(generation);
            DefaultBiomeFeatures.addSavannaGrass(generation);
        }
    }

    public static MobSpawnInfo.Builder getMobSpawnBuilder() {
        MobSpawnInfo.Builder mob_builder = new MobSpawnInfo.Builder();
        mob_builder.addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnInfo.Spawners(ModEntities.alpha_werewolf.get(), 10, 1, 1));
        mob_builder.addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnInfo.Spawners(ModEntities.werewolf_beast.get(), 70, 1, 2));
        mob_builder.addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnInfo.Spawners(ModEntities.werewolf_survivalist.get(), 70, 1, 2));
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
        return new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_GRASS));
    }

}
