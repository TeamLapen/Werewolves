package de.teamlapen.werewolves.common.world.features;

import com.mojang.serialization.MapCodec;
import de.teamlapen.werewolves.common.tags.ModBiomeTags;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

import net.minecraft.core.Direction;

import net.minecraft.world.level.biome.MobSpawnSettings;

import net.minecraft.util.random.Weighted;

import net.minecraft.util.random.WeightedList;

import com.google.common.collect.ImmutableList;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.blocks.WolfBerryBushBlock;
import de.teamlapen.werewolves.common.config.CommonConfig;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.core.ModBlocks;
import de.teamlapen.werewolves.common.core.ModEntities;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.holdersets.AndHolderSet;
import net.neoforged.neoforge.registries.holdersets.NotHolderSet;

import java.util.List;

public class WerewolvesBiomeFeatures {

    public static final DeferredRegister<MapCodec<? extends IntProvider>> INT_PROVIDER = DeferredRegister.create(Registries.INT_PROVIDER_TYPE, REFERENCE.MODID);

    public static void register(IEventBus bus) {
        INT_PROVIDER.register(bus);
    }

    public static final DeferredHolder<MapCodec<? extends IntProvider>, MapCodec<ConfigIntProvider>> CONFIG_INT_PROVIDER = INT_PROVIDER.register("config_int_provider", () -> ConfigIntProvider.CODEC);

    public static final ResourceKey<ConfiguredFeature<?, ?>> JACARANDA_TREE = createConfiguredKey("jacaranda_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAGIC_TREE = createConfiguredKey("magic_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WOLFSBANE = createConfiguredKey("wolfsbane");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILVER_ORE = createConfiguredKey("silver_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GIANT_SPRUCE_TREE = createConfiguredKey("giant_spruce_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_SPRUCE_TREE = createConfiguredKey("dead_spruce_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WEREWOLF_HEAVEN_TREES = createConfiguredKey("werewolf_heaven_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WOLF_BERRY_BUSH = createConfiguredKey("wolf_berry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FOREST_ROCK = createConfiguredKey("forest_rock");

    public static final ResourceKey<PlacedFeature> JACARANDA_TREE_PLACED = createPlacedKey("jacaranda_tree");
    public static final ResourceKey<PlacedFeature> MAGIC_TREE_PLACED = createPlacedKey("magic_tree");
    public static final ResourceKey<PlacedFeature> GIANT_SPRUCE_TREE_PLACED = createPlacedKey("giant_spruce_tree");
    public static final ResourceKey<PlacedFeature> DEAD_SPRUCE_TREE_PLACED = createPlacedKey("dead_spruce_tree");
    public static final ResourceKey<PlacedFeature> WOLFSBANE_PLACED = createPlacedKey("wolfsbane");
    public static final ResourceKey<PlacedFeature> WEREWOLF_HAVEN_TREES_PLACED = createPlacedKey("heaven_trees");
    public static final ResourceKey<PlacedFeature> SILVER_ORE_PLACED = createPlacedKey("silver_ore");
    public static final ResourceKey<PlacedFeature> WOLF_BERRY_BUSH_PLACED = createPlacedKey("wolf_berry_bush");
    public static final ResourceKey<PlacedFeature> FOREST_ROCK_PLACED = createPlacedKey("forest_rock");

    public static final ResourceKey<BiomeModifier> WEREWOLF_SPAWN = createModifierKey("spawn/werewolf_spawns");
    public static final ResourceKey<BiomeModifier> HUMAN_WEREWOLF_SPAWN = createModifierKey("spawn/human_werewolf_spawns");
    public static final ResourceKey<BiomeModifier> SILVER_ORE_GEN = createModifierKey("gen/silver_ore");
    public static final ResourceKey<BiomeModifier> WOLFSBANE_GEN = createModifierKey("gen/wolfsbane");

    @SuppressWarnings("EmptyMethod")
    public static void init() {

    }

    public static void addWerewolvesFlowers(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WOLFSBANE_PLACED);
    }

    public static void addWerewolfBiomeTrees(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WEREWOLF_HAVEN_TREES_PLACED);
    }

    public static void addWolfBerries(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WOLF_BERRY_BUSH_PLACED);
    }

    public static void addForestStone(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FOREST_ROCK_PLACED);
    }

    public static void createConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        RuleTest stoneTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        context.register(JACARANDA_TREE, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.JACARANDA_LOG.get()),
                new CherryTrunkPlacer(6,1,0, new WeightedListInt(WeightedList.<IntProvider>builder().add(ConstantInt.of(1), 1).add(ConstantInt.of(2), 1).add(ConstantInt.of(3), 1).build()), UniformInt.of(2, 4), UniformInt.of(-4, -3), UniformInt.of(-1, 0)),
                BlockStateProvider.simple(ModBlocks.JACARANDA_LEAVES.get()),
                new CherryFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(5), 0.25F, 0.5F, 0.16666667F, 0.33333334F),
                new TwoLayersFeatureSize(1, 0, 2)
        ).ignoreVines().build()));
        context.register(GIANT_SPRUCE_TREE, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new StraightTrunkPlacer(8, 2, 6),
                BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
                new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(5, 17)),
                new TwoLayersFeatureSize(2, 1, 2)).build()));
        context.register(DEAD_SPRUCE_TREE, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new StraightTrunkPlacer(5, 1, 3),
                BlockStateProvider.simple(Blocks.AIR),
                new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(0)),
                new TwoLayersFeatureSize(2, 1, 2)).build()));
        context.register(MAGIC_TREE, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.MAGIC_LOG.get().defaultBlockState()), new StraightTrunkPlacer(5, 2, 2), BlockStateProvider.simple(ModBlocks.MAGIC_LEAVES.get().defaultBlockState()), new MagicFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));
        context.register(WOLFSBANE, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.WOLFSBANE.get()))));
        context.register(SILVER_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(stoneTest, ModBlocks.SILVER_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateTest, ModBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())), 7)));
        context.register(WEREWOLF_HEAVEN_TREES, new ConfiguredFeature<>(Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(JACARANDA_TREE_PLACED), 0.04f), new WeightedPlacedFeature(placedFeatures.getOrThrow(MAGIC_TREE_PLACED), 0.01f), new WeightedPlacedFeature(placedFeatures.getOrThrow(DEAD_SPRUCE_TREE_PLACED), 0.3f)), placedFeatures.getOrThrow(GIANT_SPRUCE_TREE_PLACED))));
        context.register(WOLF_BERRY_BUSH, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.WOLF_BERRY_BUSH.get().defaultBlockState().setValue(WolfBerryBushBlock.AGE, 3)))));
        context.register(FOREST_ROCK, new ConfiguredFeature<>(Feature.BLOCK_BLOB, new BlockBlobConfiguration(Blocks.COBBLESTONE.defaultBlockState(), BlockPredicate.matchesTag(BlockTags.FOREST_ROCK_CAN_PLACE_ON))));
    }

    public static void createPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> placedFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        // former random patch configuration (96 tries, 7 xz spread, 3 y spread) is now expressed through placement modifiers
        context.register(WOLFSBANE_PLACED, new PlacedFeature(placedFeatures.getOrThrow(WOLFSBANE), List.of(RarityFilter.onAverageOnceEvery(20), PlacementUtils.HEIGHTMAP, InSquarePlacement.spread(), CountPlacement.of(96), RandomOffsetPlacement.ofTriangle(7, 3), PlacementUtils.isEmpty())));
        context.register(MAGIC_TREE_PLACED, new PlacedFeature(placedFeatures.getOrThrow(MAGIC_TREE), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.MAGIC_SAPLING.get()))));
        context.register(JACARANDA_TREE_PLACED, new PlacedFeature(placedFeatures.getOrThrow(JACARANDA_TREE), List.of(PlacementUtils.filteredByBlockSurvival(ModBlocks.JACARANDA_SAPLING.get()))));
        context.register(GIANT_SPRUCE_TREE_PLACED, new PlacedFeature(placedFeatures.getOrThrow(GIANT_SPRUCE_TREE), List.of(PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING))));
        context.register(DEAD_SPRUCE_TREE_PLACED, new PlacedFeature(placedFeatures.getOrThrow(DEAD_SPRUCE_TREE), List.of(PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING))));
        context.register(WEREWOLF_HAVEN_TREES_PLACED, new PlacedFeature(placedFeatures.getOrThrow(WEREWOLF_HEAVEN_TREES), VegetationPlacements.treePlacement(PlacementUtils.countExtra(15, 0.2f, 4))));
        context.register(SILVER_ORE_PLACED, new PlacedFeature(placedFeatures.getOrThrow(SILVER_ORE), commonOrePlacement(ModConfig.common().silverOreWeight, HeightRangePlacement.triangle(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(50)))));
        context.register(WOLF_BERRY_BUSH_PLACED, new PlacedFeature(placedFeatures.getOrThrow(WOLF_BERRY_BUSH), List.of(RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome(), CountPlacement.of(96), RandomOffsetPlacement.ofTriangle(7, 3), BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK))))));
        context.register(FOREST_ROCK_PLACED, new PlacedFeature(placedFeatures.getOrThrow(FOREST_ROCK), List.of(CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
    }

    public static void createBiomeModifier(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomeLookup = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatureLookup = context.lookup(Registries.PLACED_FEATURE);
        // spawn in the has_spawn biomes, except for those listed in the no_spawn tag (e.g. other faction biomes)
        // the registry lookup of NotHolderSet is only used to resolve its contents, which never happens during datagen (only the wrapped value is serialized).
        // the bootstrap context cannot provide a RegistryLookup, so null is passed here
        HolderLookup.RegistryLookup<Biome> biomeRegistry = null;
        HolderSet<Biome> werewolfBiomes = new AndHolderSet<>(biomeLookup.getOrThrow(ModBiomeTags.HasSpawn.WEREWOLF), new NotHolderSet<>(biomeRegistry, biomeLookup.getOrThrow(ModBiomeTags.NoSpawn.WEREWOLF)));
        HolderSet<Biome> humanWerewolfBiomes = new AndHolderSet<>(biomeLookup.getOrThrow(ModBiomeTags.HasSpawn.HUMAN_WEREWOLF), new NotHolderSet<>(biomeRegistry, biomeLookup.getOrThrow(ModBiomeTags.NoSpawn.HUMAN_WEREWOLF)));
        context.register(WEREWOLF_SPAWN, new BiomeModifiers.AddSpawnsBiomeModifier(werewolfBiomes, WeightedList.<MobSpawnSettings.SpawnerData>builder().add(new MobSpawnSettings.SpawnerData(ModEntities.WEREWOLF_BEAST.get(), 1, 2), 80).add(new MobSpawnSettings.SpawnerData(ModEntities.WEREWOLF_SURVIVALIST.get(), 1, 2), 80).build()));
        context.register(HUMAN_WEREWOLF_SPAWN, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(humanWerewolfBiomes, new Weighted<>(new MobSpawnSettings.SpawnerData(ModEntities.HUMAN_WEREWOLF.get(), 1, 1), 5)));
        context.register(SILVER_ORE_GEN, new BiomeModifiers.AddFeaturesBiomeModifier(biomeLookup.getOrThrow(ModBiomeTags.HasGen.SILVER_ORE), HolderSet.direct(placedFeatureLookup.getOrThrow(WerewolvesBiomeFeatures.SILVER_ORE_PLACED)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(WOLFSBANE_GEN, new BiomeModifiers.AddFeaturesBiomeModifier(biomeLookup.getOrThrow(ModBiomeTags.HasGen.WOLFSBANE), HolderSet.direct(placedFeatureLookup.getOrThrow(WerewolvesBiomeFeatures.WOLFSBANE_PLACED)), GenerationStep.Decoration.VEGETAL_DECORATION));
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

    private static ResourceKey<ConfiguredFeature<?, ?>> createConfiguredKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, WIdentifier.mod(name));
    }

    private static ResourceKey<PlacedFeature> createPlacedKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, WIdentifier.mod(name));
    }

    private static ResourceKey<BiomeModifier> createModifierKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, WIdentifier.mod( name));
    }

    private static ResourceKey<StructureSet> createStructureSetKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, WIdentifier.mod( name));
    }
}
