package de.teamlapen.werewolves.world;

import com.google.common.collect.ImmutableList;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import java.util.OptionalInt;

public class WerewolvesBiomeFeatures {

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> jacaranda_tree = registerFeature("jacaranda_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.jacaranda_log.get().defaultBlockState()), new SimpleBlockStateProvider(ModBlocks.jacaranda_leaves.get().defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1)).ignoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> magic_tree = registerFeature("magic_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.magic_log.get().defaultBlockState()), new SimpleBlockStateProvider(ModBlocks.magic_leaves.get().defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1)).ignoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> magic_tree_big = registerFeature("magic_tree", Feature.TREE.configured(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.magic_log.get().defaultBlockState()), new SimpleBlockStateProvider(ModBlocks.magic_leaves.get().defaultBlockState()), new FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(4), 4), new FancyTrunkPlacer(3, 11, 0), new TwoLayerFeature(0, 0, 1, OptionalInt.of(4))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build()));

    public static final ConfiguredFeature<?, ?> werewolf_heaven_trees = registerFeature("werewolf_heaven_trees", Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(jacaranda_tree.weighted(0.4f), Features.FANCY_OAK.weighted(0.1f), magic_tree_big.weighted(0.1f)), magic_tree)).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));

    public static final ConfiguredFeature<?, ?> wolfsbane = registerFeature("wolfsbane", Feature.FLOWER.configured(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.wolfsbane.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE).tries(32).build()).count(FeatureSpread.of(-1, 4)).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP_SQUARE).count(1));

    public static final ConfiguredFeature<?, ?> silver_ore = registerFeature("silver_ore", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.silver_ore.get().defaultBlockState(), 5)).range(45).squared().count(2));

    private static <T extends IFeatureConfig> ConfiguredFeature<T, ?> registerFeature(String name, ConfiguredFeature<T, ?> feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(REFERENCE.MODID, name), feature);
    }

    public static void addWerewolvesFlowers(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, wolfsbane);
    }

    public static void addWerewolfBiomeTrees(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, werewolf_heaven_trees);
    }

    public static void init() {

    }
}
