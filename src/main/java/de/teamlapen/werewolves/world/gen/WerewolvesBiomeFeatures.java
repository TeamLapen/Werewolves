package de.teamlapen.werewolves.world.gen;

import com.google.common.collect.ImmutableList;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;
import java.util.OptionalInt;

public class WerewolvesBiomeFeatures {

    public static final List<OreConfiguration.TargetBlockState> ORE_SILVER_TARGET_LIST = List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.silver_ore.defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.deepslate_silver_ore.defaultBlockState()));
    public static final RandomPatchConfiguration WOLFSBANE_CONFIG = FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.wolfsbane)));
    public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> jacaranda_tree = registerConfiguredFeature("jacaranda_tree", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.jacaranda_log.defaultBlockState()), new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.simple(ModBlocks.jacaranda_leaves), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> magic_tree = registerConfiguredFeature("magic_tree", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.magic_log.defaultBlockState()), new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.simple(ModBlocks.magic_leaves.defaultBlockState()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
    public static final Holder<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> magic_tree_big = registerConfiguredFeature("magic_tree_big", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.magic_log.defaultBlockState()), new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(ModBlocks.magic_leaves.defaultBlockState()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 1, OptionalInt.of(4))).ignoreVines().build());
    public static final Holder<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> wolfsbane = registerConfiguredFeature("wolfsbane", Feature.FLOWER, WOLFSBANE_CONFIG);
    public static final Holder<ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>>> silver_ore = registerConfiguredFeature("silver_ore", Feature.ORE, new OreConfiguration(ORE_SILVER_TARGET_LIST, 7));

    public static final Holder<PlacedFeature> jacaranda_tree_placed = PlacementUtils.register(REFERENCE.MODID + ":jacaranda_tree", jacaranda_tree, PlacementUtils.filteredByBlockSurvival(ModBlocks.jacaranda_sapling));
    public static final Holder<PlacedFeature> magic_tree_placed = PlacementUtils.register(REFERENCE.MODID + ":magic_tree", magic_tree, PlacementUtils.filteredByBlockSurvival(ModBlocks.magic_sapling));
    public static final Holder<PlacedFeature> magic_tree_big_placed = PlacementUtils.register(REFERENCE.MODID + ":magic_tree_big", magic_tree_big, PlacementUtils.filteredByBlockSurvival(ModBlocks.magic_sapling));
    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> werewolf_heaven_trees = FeatureUtils.register("werewolf_heaven_trees", Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(jacaranda_tree_placed, 0.4f), new WeightedPlacedFeature(TreePlacements.FANCY_OAK_CHECKED, 0.1f), new WeightedPlacedFeature(magic_tree_big_placed, 0.1f)), magic_tree_placed));


    //.tries(32).build()).count(UniformInt.of(-1, 4)).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(1));
    public static final Holder<PlacedFeature> wolfsbane_placed = PlacementUtils.register(REFERENCE.MODID + ":wolfsbane", wolfsbane, RarityFilter.onAverageOnceEvery(5), PlacementUtils.HEIGHTMAP, InSquarePlacement.spread());
    //decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(10, 0.1F, 1))));
    public static final Holder<PlacedFeature> werewolf_haven_trees_placed = PlacementUtils.register(REFERENCE.MODID + ":heaven_trees", werewolf_heaven_trees, VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.2f, 2)));
    public static final Holder<PlacedFeature> silver_ore_placed = PlacementUtils.register(REFERENCE.MODID + ":silver_ore", silver_ore, commonOrePlacement(48, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(96))));

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, F>> registerConfiguredFeature(String name, F feature, FC config) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, REFERENCE.MODID + ":" + name, new ConfiguredFeature<>(feature, config));
    }

    public static void addWerewolvesFlowers(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, wolfsbane_placed);
    }

    public static void addWerewolfBiomeTrees(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, werewolf_haven_trees_placed);
    }

    @SuppressWarnings("EmptyMethod")
    public static void init() {

    }

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    @SuppressWarnings("SameParameterValue")
    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }
}
