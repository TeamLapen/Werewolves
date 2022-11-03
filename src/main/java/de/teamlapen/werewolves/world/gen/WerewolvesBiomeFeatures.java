package de.teamlapen.werewolves.world.gen;

import com.google.common.collect.ImmutableList;
import de.teamlapen.werewolves.config.CommonConfig;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProviderType;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.OptionalInt;

public class WerewolvesBiomeFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURE = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, REFERENCE.MODID);
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, REFERENCE.MODID);
    public static final DeferredRegister<IntProviderType<?>> INT_PROVIDER = DeferredRegister.create(Registry.INT_PROVIDER_TYPE_REGISTRY, REFERENCE.MODID);

    public static void register(IEventBus bus) {
        PLACED_FEATURE.register(bus);
        CONFIGURED_FEATURES.register(bus);
        INT_PROVIDER.register(bus);
    }

    public static final RegistryObject<IntProviderType<ConfigIntProvider>> CONFIG_INT_PROVIDER = INT_PROVIDER.register("config_int_provider", () -> () -> ConfigIntProvider.CODEC);

    public static final RegistryObject<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> JACARANDA_TREE = CONFIGURED_FEATURES.register("jacaranda_tree", () -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.JACARANDA_LOG.get().defaultBlockState()), new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.simple(ModBlocks.JACARANDA_LEAVES.get()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));
    public static final RegistryObject<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> MAGIC_TREE = CONFIGURED_FEATURES.register("magic_tree", () -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.MAGIC_LOG.get().defaultBlockState()), new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.simple(ModBlocks.MAGIC_LEAVES.get().defaultBlockState()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));
    public static final RegistryObject<ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>>> MAGIC_TREE_BIG = CONFIGURED_FEATURES.register("magic_tree_big", () -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.MAGIC_LOG.get().defaultBlockState()), new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(ModBlocks.MAGIC_LEAVES.get().defaultBlockState()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 1, OptionalInt.of(4))).ignoreVines().build()));
    public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, Feature<RandomPatchConfiguration>>> WOLFSBANE = CONFIGURED_FEATURES.register("wolfsbane", () -> new ConfiguredFeature<>(Feature.FLOWER, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.WOLFSBANE.get())))));
    public static final RegistryObject<ConfiguredFeature<OreConfiguration, Feature<OreConfiguration>>> SILVER_ORE = CONFIGURED_FEATURES.register("silver_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.SILVER_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())), 7)));

    public static final RegistryObject<PlacedFeature> JACARANDA_TREE_PLACED = PLACED_FEATURE.register("jacaranda_tree", () -> new PlacedFeature(getHolder(JACARANDA_TREE), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.JACARANDA_SAPLING.get()))));
    public static final RegistryObject<PlacedFeature> MAGIC_TREE_PLACED = PLACED_FEATURE.register("magic_tree", () -> new PlacedFeature(getHolder(MAGIC_TREE), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.MAGIC_SAPLING.get()))));
    public static final RegistryObject<PlacedFeature> MAGIC_TREE_BIG_PLACED = PLACED_FEATURE.register("magic_tree_big", () -> new PlacedFeature(getHolder(MAGIC_TREE_BIG), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.MAGIC_SAPLING.get()))));
    public static final RegistryObject<ConfiguredFeature<RandomFeatureConfiguration, Feature<RandomFeatureConfiguration>>> WEREWOLF_HEAVEN_TREES = CONFIGURED_FEATURES.register("werewolf_heaven_trees", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(getHolder(JACARANDA_TREE_PLACED), 0.4f), new WeightedPlacedFeature(TreePlacements.FANCY_OAK_CHECKED, 0.1f), new WeightedPlacedFeature(getHolder(MAGIC_TREE_BIG_PLACED), 0.1f)), getHolder(MAGIC_TREE_PLACED))));

    public static final RegistryObject<PlacedFeature> WOLFSBANE_PLACED = PLACED_FEATURE.register("wolfsbane", () -> new PlacedFeature(getHolder(WOLFSBANE), List.of(RarityFilter.onAverageOnceEvery(20), PlacementUtils.HEIGHTMAP, InSquarePlacement.spread())));
    public static final RegistryObject<PlacedFeature> WEREWOLF_HAVEN_TREES_PLACED = PLACED_FEATURE.register("heaven_trees", () -> new PlacedFeature(getHolder(WEREWOLF_HEAVEN_TREES), VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.2f, 2))));
    public static final RegistryObject<PlacedFeature> SILVER_ORE_PLACED = PLACED_FEATURE.register("silver_ore", () -> new PlacedFeature(getHolder(SILVER_ORE), commonOrePlacement(WerewolvesConfig.COMMON.silverOreWeight, HeightRangePlacement.triangle(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(50)))));

    public static void addWerewolvesFlowers(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getHolder(WOLFSBANE_PLACED));
    }

    public static void addWerewolfBiomeTrees(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, getHolder(WEREWOLF_HAVEN_TREES_PLACED));
    }

    @SuppressWarnings("EmptyMethod")
    public static void init() {

    }

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    @SuppressWarnings("SameParameterValue")
    private static List<PlacementModifier> commonOrePlacement(CommonConfig.IntValueExt p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(ConfigIntProvider.of(p_195344_)), p_195345_);
    }

    private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    /**
     * Get the holder for the given registry object and strip it of generic
     * MUST only be used for objects that belong to registries that are guaranteed to be present
     */
    @SuppressWarnings({"unchecked"})
    private static <T> Holder<T> getHolder(RegistryObject<? extends T> object) {
        return (Holder<T>) object.getHolder().orElseThrow(() -> new IllegalStateException("Registry object " + object.getKey() + " does not have a holder. Something is wrong"));
    }
}
