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

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> jacaranda_tree = registerFeature("jacaranda_tree", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.jacaranda_log.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.jacaranda_leaves.getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1)).setIgnoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> magic_tree = registerFeature("magic_tree", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.magic_log.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.magic_leaves.getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1)).setIgnoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> magic_tree_big = registerFeature("magic_tree", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.magic_log.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.magic_leaves.getDefaultState()), new FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4), new FancyTrunkPlacer(3, 11, 0), new TwoLayerFeature(0, 0, 1, OptionalInt.of(4))).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build()));

    public static final ConfiguredFeature<?, ?> werewolf_heaven_trees = registerFeature("werewolf_heaven_trees", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(jacaranda_tree.withChance(0.4f), Features.FANCY_OAK.withChance(0.1f), magic_tree_big.withChance(0.1f)), magic_tree)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));

    public static final ConfiguredFeature<?, ?> wolfsbane = registerFeature("wolfsbane", Feature.FLOWER.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.wolfsbane.getDefaultState()), new SimpleBlockPlacer()).build())).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(1);

    public static final ConfiguredFeature<?, ?> silver_ore = registerFeature("silver_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, ModBlocks.silver_ore.getDefaultState(), 5)).range(45).square().func_242731_b(2));

    private static <T extends IFeatureConfig> ConfiguredFeature<T, ?> registerFeature(String name, ConfiguredFeature<T, ?> feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(REFERENCE.MODID, name), feature);
    }

    public static void addWerewolvesFlowers(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, wolfsbane);
    }

    public static void addWerewolfBiomeTrees(BiomeGenerationSettings.Builder biomeGeneratorSettings) {
        biomeGeneratorSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, werewolf_heaven_trees);
    }
}
