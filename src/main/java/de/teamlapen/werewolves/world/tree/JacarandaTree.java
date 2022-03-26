package de.teamlapen.werewolves.world.tree;

import de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class JacarandaTree extends AbstractTreeGrower {

    @Nullable
    @Override
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(@Nonnull Random randomIn, boolean p_225546_2_) {
        return WerewolvesBiomeFeatures.jacaranda_tree;
    }
}
