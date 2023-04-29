package de.teamlapen.werewolves.world.gen;

import de.teamlapen.werewolves.core.ModEntities;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures.*;
import static net.minecraft.data.worldgen.BiomeDefaultFeatures.*;

public class WerewolfForestBiome {

    public static Biome createWerewolfForestBiome(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        return addAttributes(createGenerationBuilder(featureGetter, carverGetter), createMobSpawnBuilder(), createEffectBuilder()).build();
    }

    public static Biome.BiomeBuilder addAttributes(BiomeGenerationSettings.Builder featureBuilder, MobSpawnSettings.Builder spawnBuilder, BiomeSpecialEffects.Builder ambienceBuilder) {
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(0.3f)
                .downfall(0)
                .specialEffects(ambienceBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(featureBuilder.build());
    }

    public static MobSpawnSettings.Builder createMobSpawnBuilder() {
        return new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.ALPHA_WEREWOLF.get(), 10, 1, 1))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.WEREWOLF_BEAST.get(), 70, 1, 2))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.WEREWOLF_SURVIVALIST.get(), 70, 1, 2));
    }

    public static BiomeSpecialEffects.Builder createEffectBuilder() {
        return new BiomeSpecialEffects.Builder()
                .waterColor(0x444444)
                .waterFogColor(0x444444)
                .skyColor(0x444444)
                .fogColor(0x444444)
                .foliageColorOverride(0x375b38)
                .grassColorOverride(0x444444)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);
    }

    public static BiomeGenerationSettings.Builder createGenerationBuilder(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(featureGetter, carverGetter);
        addDefaultCarversAndLakes(builder);
        addDefaultMonsterRoom(builder);
        addDefaultUndergroundVariety(builder);
        addForestStone(builder);
        addDefaultOres(builder);
        addDefaultSoftDisks(builder);

        addFerns(builder);
        addWerewolfBiomeTrees(builder);
        addWolfBerries(builder);
        addDefaultFlowers(builder);
        addSavannaGrass(builder);
        addSavannaExtraGrass(builder);
        addRareBerryBushes(builder);
        return builder;
    }
}
