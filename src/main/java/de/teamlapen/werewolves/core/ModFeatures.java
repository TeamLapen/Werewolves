package de.teamlapen.werewolves.core;

import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraftforge.registries.IForgeRegistry;

public class ModFeatures extends de.teamlapen.vampirism.core.ModFeatures {

    public static final TreeFeatureConfig jacaranda_tree;
    public static final TreeFeatureConfig magic_tree;
    public static final TreeFeatureConfig magic_tree_big;
    public static final BlockClusterFeatureConfig wolfsbane;

    static {
        jacaranda_tree = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.jacaranda_log.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.jacaranda_leaves.getDefaultState()), new BlobFoliagePlacer(2,0)).baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines().setSapling(ModBlocks.jacaranda_sapling).build();
        magic_tree = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.magic_log.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.magic_leaves.getDefaultState()), new BlobFoliagePlacer(2,0)).baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines().setSapling(ModBlocks.magic_sapling).build();
        magic_tree_big = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.magic_log.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.magic_leaves.getDefaultState()), new BlobFoliagePlacer(0,0)).setSapling(ModBlocks.magic_sapling).build();
        wolfsbane = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.wolfsbane.getDefaultState()), new SimpleBlockPlacer()).tries(20).build();
    }

    //needed for world gen
    static void registerFeatures(IForgeRegistry<Feature<?>> registry) {
    }
}
