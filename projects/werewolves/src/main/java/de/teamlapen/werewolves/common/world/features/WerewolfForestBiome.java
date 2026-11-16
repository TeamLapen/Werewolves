package de.teamlapen.werewolves.common.world.features;

import net.neoforged.neoforge.common.world.BiomeSpecialEffectsBuilder;

import net.minecraft.world.attribute.AmbientSounds;

import net.minecraft.world.attribute.EnvironmentAttributes;

import de.teamlapen.werewolves.common.core.ModEntities;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static de.teamlapen.werewolves.common.world.features.WerewolvesBiomeFeatures.*;
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
                .generationSettings(featureBuilder.build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x444444)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x444444)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 0x444444)
                .setAttribute(EnvironmentAttributes.AMBIENT_SOUNDS, AmbientSounds.LEGACY_CAVE_SETTINGS);
    }

    public static MobSpawnSettings.Builder createMobSpawnBuilder() {
        return new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(ModEntities.ALPHA_WEREWOLF.get(), 1, 1))
                .addSpawn(MobCategory.MONSTER, 70, new MobSpawnSettings.SpawnerData(ModEntities.WEREWOLF_BEAST.get(), 1, 2))
                .addSpawn(MobCategory.MONSTER, 70, new MobSpawnSettings.SpawnerData(ModEntities.WEREWOLF_SURVIVALIST.get(), 1, 2));
    }

    public static BiomeSpecialEffects.Builder createEffectBuilder() {
        return BiomeSpecialEffectsBuilder.create(0x444444)
                .foliageColorOverride(0x375b38)
                .grassColorOverride(0x444444);
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
