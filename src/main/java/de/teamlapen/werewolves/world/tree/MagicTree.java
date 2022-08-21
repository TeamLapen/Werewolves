package de.teamlapen.werewolves.world.tree;

import de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class MagicTree extends AbstractTreeGrower {
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomIn, boolean p_225546_2_) {
        return randomIn.nextInt(10) < 3 ? WerewolvesBiomeFeatures.magic_tree.getHolder().orElseThrow() : WerewolvesBiomeFeatures.magic_tree_big.getHolder().orElseThrow();
    }
}
