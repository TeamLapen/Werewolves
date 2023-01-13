package de.teamlapen.werewolves.world.gen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import de.teamlapen.vampirism.world.gen.modifier.ExtendedAddSpawnsBiomeModifier;
import de.teamlapen.werewolves.config.CommonConfig;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProviderType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.OptionalInt;

public class WerewolvesBiomeFeatures {

    public static final DeferredRegister<IntProviderType<?>> INT_PROVIDER = DeferredRegister.create(Registries.INT_PROVIDER_TYPE, REFERENCE.MODID);

    public static void register(IEventBus bus) {
        INT_PROVIDER.register(bus);
    }

    public static final RegistryObject<IntProviderType<ConfigIntProvider>> CONFIG_INT_PROVIDER = INT_PROVIDER.register("config_int_provider", () -> () -> ConfigIntProvider.CODEC);

    public static final ResourceKey<ConfiguredFeature<?,?>> JACARANDA_TREE = createConfiguredKey("jacaranda_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> MAGIC_TREE = createConfiguredKey("magic_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> MAGIC_TREE_BIG = createConfiguredKey("magic_tree_big");
    public static final ResourceKey<ConfiguredFeature<?,?>> WOLFSBANE = createConfiguredKey("wolfsbane");
    public static final ResourceKey<ConfiguredFeature<?,?>> SILVER_ORE = createConfiguredKey("silver_ore");

    public static final ResourceKey<PlacedFeature> JACARANDA_TREE_PLACED = createPlacedKey("jacaranda_tree");
    public static final ResourceKey<PlacedFeature> MAGIC_TREE_PLACED = createPlacedKey("magic_tree");
    public static final ResourceKey<PlacedFeature> MAGIC_TREE_BIG_PLACED = createPlacedKey("magic_tree_big");
    public static final ResourceKey<ConfiguredFeature<?,?>> WEREWOLF_HEAVEN_TREES = createConfiguredKey("werewolf_heaven_trees");

    public static final ResourceKey<PlacedFeature> WOLFSBANE_PLACED = createPlacedKey("wolfsbane");
    public static final ResourceKey<PlacedFeature> WEREWOLF_HAVEN_TREES_PLACED = createPlacedKey("heaven_trees");
    public static final ResourceKey<PlacedFeature> SILVER_ORE_PLACED = createPlacedKey("silver_ore");

    public static final ResourceKey<BiomeModifier> WEREWOLF_SPAWN = createModifierKey("spawn/werewolf_spawns");
    public static final ResourceKey<BiomeModifier> HUMAN_WEREWOLF_SPAWN = createModifierKey("spawn/human_werewolf_spawns");
    public static final ResourceKey<BiomeModifier> SILVER_ORE_GEN = createModifierKey("gen/silver_ore");
    public static final ResourceKey<BiomeModifier> WOLFSBANE_GEN = createModifierKey("gen/wolfsbane");

    private static ResourceKey<ConfiguredFeature<?, ?>> createConfiguredKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(REFERENCE.MODID, name));
    }

    private static ResourceKey<PlacedFeature> createPlacedKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(REFERENCE.MODID, name));
    }

    private static ResourceKey<BiomeModifier> createModifierKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(REFERENCE.MODID, name));
    }

    private static ResourceKey<StructureSet> createStructureSetKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(REFERENCE.MODID, name));
    }

    public static void addWerewolvesFlowers(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WOLFSBANE_PLACED);
    }

    public static void addWerewolfBiomeTrees(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WEREWOLF_HAVEN_TREES_PLACED);
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

    public static void createConfiguredFeatures(BootstapContext<ConfiguredFeature<?,?>> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        RuleTest stoneTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        context.register(JACARANDA_TREE, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.JACARANDA_LOG.get().defaultBlockState()), new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.simple(ModBlocks.JACARANDA_LEAVES.get()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));
        context.register(MAGIC_TREE, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.MAGIC_LOG.get().defaultBlockState()), new StraightTrunkPlacer(4, 2, 0), BlockStateProvider.simple(ModBlocks.MAGIC_LEAVES.get().defaultBlockState()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));
        context.register(MAGIC_TREE_BIG, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.MAGIC_LOG.get().defaultBlockState()), new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(ModBlocks.MAGIC_LEAVES.get().defaultBlockState()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 1, OptionalInt.of(4))).ignoreVines().build()));
        context.register(WOLFSBANE, new ConfiguredFeature<>(Feature.FLOWER, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.WOLFSBANE.get())))));
        context.register(SILVER_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(stoneTest, ModBlocks.SILVER_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateTest, ModBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())), 7)));
        context.register(WEREWOLF_HEAVEN_TREES, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(JACARANDA_TREE_PLACED), 0.4f), new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_CHECKED), 0.1f), new WeightedPlacedFeature(placedFeatures.getOrThrow(MAGIC_TREE_BIG_PLACED), 0.1f)), placedFeatures.getOrThrow(MAGIC_TREE_PLACED))));
    }
    public static void createPlacedFeatures(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> placedFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(WOLFSBANE_PLACED, new PlacedFeature(placedFeatures.getOrThrow(WOLFSBANE), List.of(RarityFilter.onAverageOnceEvery(20), PlacementUtils.HEIGHTMAP, InSquarePlacement.spread())));
        context.register(WEREWOLF_HAVEN_TREES_PLACED, new PlacedFeature(placedFeatures.getOrThrow(WEREWOLF_HEAVEN_TREES), VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.2f, 2))));
        context.register(SILVER_ORE_PLACED, new PlacedFeature(placedFeatures.getOrThrow(SILVER_ORE), commonOrePlacement(WerewolvesConfig.COMMON.silverOreWeight, HeightRangePlacement.triangle(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(50)))));
        context.register(MAGIC_TREE_BIG_PLACED, new PlacedFeature(placedFeatures.getOrThrow(MAGIC_TREE_BIG), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.MAGIC_SAPLING.get()))));
        context.register(MAGIC_TREE_PLACED, new PlacedFeature(placedFeatures.getOrThrow(MAGIC_TREE), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.MAGIC_SAPLING.get()))));
        context.register(JACARANDA_TREE_PLACED, new PlacedFeature(placedFeatures.getOrThrow(JACARANDA_TREE), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.JACARANDA_SAPLING.get()))));
    }

    public static void createBiomeModifier(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomeLookup = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatureLookup = context.lookup(Registries.PLACED_FEATURE);
        context.register(WEREWOLF_SPAWN, new ExtendedAddSpawnsBiomeModifier(biomeLookup.getOrThrow(ModTags.Biomes.HasSpawn.WEREWOLF), biomeLookup.getOrThrow(ModTags.Biomes.NoSpawn.WEREWOLF), Lists.newArrayList(new ExtendedAddSpawnsBiomeModifier.ExtendedSpawnData(ModEntities.WEREWOLF_BEAST.get(), 80, 1, 2, MobCategory.MONSTER), new ExtendedAddSpawnsBiomeModifier.ExtendedSpawnData(ModEntities.WEREWOLF_SURVIVALIST.get(), 80, 1, 2, MobCategory.MONSTER))));
        context.register(HUMAN_WEREWOLF_SPAWN, ExtendedAddSpawnsBiomeModifier.singleSpawn(biomeLookup.getOrThrow(ModTags.Biomes.HasSpawn.HUMAN_WEREWOLF), biomeLookup.getOrThrow(ModTags.Biomes.NoSpawn.HUMAN_WEREWOLF), new ExtendedAddSpawnsBiomeModifier.ExtendedSpawnData(ModEntities.HUMAN_WEREWOLF.get(), 5, 1, 1, MobCategory.MONSTER)));
        context.register(SILVER_ORE_GEN, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomeLookup.getOrThrow(ModTags.Biomes.HasGen.SILVER_ORE), HolderSet.direct(placedFeatureLookup.getOrThrow(WerewolvesBiomeFeatures.SILVER_ORE_PLACED)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(WOLFSBANE_GEN, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomeLookup.getOrThrow(ModTags.Biomes.HasGen.WOLFSBANE), HolderSet.direct(placedFeatureLookup.getOrThrow(WerewolvesBiomeFeatures.WOLFSBANE_PLACED)), GenerationStep.Decoration.VEGETAL_DECORATION));
    }
}
