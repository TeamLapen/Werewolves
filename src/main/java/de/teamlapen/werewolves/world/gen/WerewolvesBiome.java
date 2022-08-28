package de.teamlapen.werewolves.world.gen;

import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.NotNull;

import static de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures.*;

public class WerewolvesBiome {
    public static void addWolfsbane(BiomeGenerationSettings.@NotNull Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getHolder(WOLFSBANE_PLACED));
    }

    public static void addRareWolfBerryBush(BiomeGenerationSettings.@NotNull Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getHolder(WOLF_BERRY_RARE_PLACED));
    }

    public static void addCommonWolfBerryBush(BiomeGenerationSettings.@NotNull Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getHolder(WOLF_BERRY_COMMON_PLACED));
    }

    public static void addDaffodil(BiomeGenerationSettings.@NotNull Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getHolder(DAFFODIL_PLACED));
    }

    public static void addWerewolfBiomeTrees(BiomeGenerationSettings.@NotNull Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getHolder(WEREWOLF_HAVEN_TREES_PLACED));
    }
}
