package de.teamlapen.werewolves.world;

import com.google.common.collect.ImmutableList;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.OptionalInt;

import net.minecraft.data.worldgen.Features;
import net.minecraft.util.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;

public class WerewolvesBiomeFeatures {

    public static final ConfiguredFeature<TreeConfiguration, ?> jacaranda_tree = registerFeature("jacaranda_tree", Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(ModBlocks.jacaranda_log.defaultBlockState()), new SimpleStateProvider(ModBlocks.jacaranda_leaves.defaultBlockState()), new BlobFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));
    public static final ConfiguredFeature<TreeConfiguration, ?> magic_tree = registerFeature("magic_tree", Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(ModBlocks.magic_log.defaultBlockState()), new SimpleStateProvider(ModBlocks.magic_leaves.defaultBlockState()), new BlobFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));
    public static final ConfiguredFeature<TreeConfiguration, ?> magic_tree_big = registerFeature("magic_tree", Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(ModBlocks.magic_log.defaultBlockState()), new SimpleStateProvider(ModBlocks.magic_leaves.defaultBlockState()), new FancyFoliagePlacer(UniformInt.fixed(2), UniformInt.fixed(4), 4), new FancyTrunkPlacer(3, 11, 0), new TwoLayersFeatureSize(0, 0, 1, OptionalInt.of(4))).ignoreVines().heightmap(Heightmap.Types.MOTION_BLOCKING).build()));

    public static final ConfiguredFeature<?, ?> werewolf_heaven_trees = registerFeature("werewolf_heaven_trees", Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(ImmutableList.of(jacaranda_tree.weighted(0.4f), Features.FANCY_OAK.weighted(0.1f), magic_tree_big.weighted(0.1f)), magic_tree)).decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(10, 0.1F, 1))));

    public static final ConfiguredFeature<?, ?> wolfsbane = registerFeature("wolfsbane", Feature.FLOWER.configured(new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(ModBlocks.wolfsbane.defaultBlockState()), SimpleBlockPlacer.INSTANCE).tries(32).build()).count(UniformInt.of(-1, 4)).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(1));

    public static final ConfiguredFeature<?, ?> silver_ore = registerFeature("silver_ore", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, ModBlocks.silver_ore.defaultBlockState(), 5)).range(45).squared().count(2));

    private static <T extends FeatureConfiguration> ConfiguredFeature<T, ?> registerFeature(String name, ConfiguredFeature<T, ?> feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(REFERENCE.MODID, name), feature);
    }

    public static void addWerewolvesFlowers(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, wolfsbane);
    }

    public static void addWerewolfBiomeTrees(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, werewolf_heaven_trees);
    }

    public static void init() {

    }
}
