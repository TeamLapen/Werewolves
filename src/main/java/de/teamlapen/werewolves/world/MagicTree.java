package de.teamlapen.werewolves.world;

import de.teamlapen.werewolves.core.ModFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class MagicTree extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
        return randomIn.nextInt(10)< 3 ? Feature.FANCY_TREE.withConfiguration(ModFeatures.magic_tree_big) : Feature.NORMAL_TREE.withConfiguration(ModFeatures.magic_tree);
    }
}
