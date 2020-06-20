package de.teamlapen.werewolves.world;

import de.teamlapen.werewolves.core.ModFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class MagicTree extends Tree {
    @Nullable
    @Override
    protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(@Nonnull Random random) {
        return random.nextInt(10) == 0 ? ModFeatures.magic_tree_big: ModFeatures.magic_tree;
    }
}
