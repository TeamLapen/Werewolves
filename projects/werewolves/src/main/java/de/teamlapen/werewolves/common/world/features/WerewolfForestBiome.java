package de.teamlapen.werewolves.common.world.features;

import de.teamlapen.werewolves.api.WEnums;
import net.neoforged.neoforge.common.world.BiomeSpecialEffectsBuilder;

import net.minecraft.world.attribute.AmbientSounds;

import net.minecraft.world.attribute.EnvironmentAttributes;

import de.teamlapen.werewolves.common.core.ModEntities;
import net.minecraft.core.HolderGetter;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.LinkedHashMap;
import java.util.Map;

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
                .mobSpawnSettings(buildWithoutEmptySpawners(spawnBuilder))
                .generationSettings(featureBuilder.build())
                .setAttribute(EnvironmentAttributes.WATER_FOG_COLOR, 0x444444)
                .setAttribute(EnvironmentAttributes.SKY_COLOR, 0x444444)
                .setAttribute(EnvironmentAttributes.FOG_COLOR, 0x444444)
                .setAttribute(EnvironmentAttributes.AMBIENT_SOUNDS, AmbientSounds.LEGACY_CAVE_SETTINGS);
    }

    /**
     * Builds the spawn settings and removes all mob categories without spawns.
     * <p>
     * {@link MobSpawnSettings.Builder} contains an entry for every {@link MobCategory}, including the ones added by other mods (e.g. Vampirism).
     * Those would be written to the biome json during datagen and the biome would fail to load if the other mod is not installed.
     * Missing categories are treated as empty by {@link MobSpawnSettings#getMobs(MobCategory)}.
     */
    public static MobSpawnSettings buildWithoutEmptySpawners(MobSpawnSettings.Builder builder) {
        MobSpawnSettings settings = builder.build();
        Map<MobCategory, WeightedList<MobSpawnSettings.SpawnerData>> spawners = new LinkedHashMap<>();
        for (MobCategory category : settings.getSpawnerTypes()) {
            WeightedList<MobSpawnSettings.SpawnerData> mobs = settings.getMobs(category);
            if (!mobs.isEmpty()) {
                spawners.put(category, mobs);
            }
        }
        Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> costs = new LinkedHashMap<>();
        for (EntityType<?> type : settings.getEntityTypes()) {
            costs.put(type, settings.getMobSpawnCost(type));
        }
        return new MobSpawnSettings(settings.getCreatureProbability(), spawners, costs);
    }

    public static MobSpawnSettings.Builder createMobSpawnBuilder() {
        return new MobSpawnSettings.Builder()
                .addSpawn(WEnums.WEREWOLF_CATEGORY.getValue(), 10, new MobSpawnSettings.SpawnerData(ModEntities.ALPHA_WEREWOLF.get(), 1, 1))
                .addSpawn(WEnums.WEREWOLF_CATEGORY.getValue(), 70, new MobSpawnSettings.SpawnerData(ModEntities.WEREWOLF_BEAST.get(), 1, 2))
                .addSpawn(WEnums.WEREWOLF_CATEGORY.getValue(), 70, new MobSpawnSettings.SpawnerData(ModEntities.WEREWOLF_SURVIVALIST.get(), 1, 2));
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
