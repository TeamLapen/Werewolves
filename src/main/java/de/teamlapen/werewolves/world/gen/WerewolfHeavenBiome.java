package de.teamlapen.werewolves.world.gen;

import de.teamlapen.werewolves.core.ModEntities;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import org.jetbrains.annotations.NotNull;

import static de.teamlapen.vampirism.world.biome.VampirismBiomes.addModdedWaterLake;
import static de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures.addWerewolfBiomeTrees;
import static net.minecraft.data.worldgen.BiomeDefaultFeatures.*;

public class WerewolfHeavenBiome {

    public static @NotNull Biome createWerewolfHeavenBiome() {
        return addAttributes(createGenerationBuilder(), createMobSpawnBuilder(), createEffectBuilder()).build();
    }

    public static Biome.@NotNull BiomeBuilder addAttributes(BiomeGenerationSettings.@NotNull Builder featureBuilder, MobSpawnSettings.@NotNull Builder spawnBuilder, BiomeSpecialEffects.@NotNull Builder ambienceBuilder) {
        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.NONE)
                .temperature(0.3f)
                .downfall(0)
                .specialEffects(ambienceBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(featureBuilder.build());
    }

    public static MobSpawnSettings.@NotNull Builder createMobSpawnBuilder() {
        return new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.ALPHA_WEREWOLF.get(), 10, 1, 1))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.WEREWOLF_BEAST.get(), 70, 1, 2))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntities.WEREWOLF_SURVIVALIST.get(), 70, 1, 2));
    }

    public static BiomeSpecialEffects.@NotNull Builder createEffectBuilder() {
        return new BiomeSpecialEffects.Builder()
                .waterColor(0x4CCCFF)
                .waterFogColor(0x4CCCFF)
                .skyColor(0x66DBFF)
                .fogColor(0x4CCCFF)
                .foliageColorOverride(0x70E0B5)
                .grassColorOverride(0x69CFDB)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS);
    }

    public static BiomeGenerationSettings.@NotNull Builder createGenerationBuilder() {
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
