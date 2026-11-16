package de.teamlapen.werewolves.common.world.features;

import de.teamlapen.vampirism.common.world.features.VampirismFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class WTreeGrower {

    public static final TreeGrower JACARANDA = new TreeGrower("jacaranda", Optional.empty(), Optional.of(WerewolvesBiomeFeatures.JACARANDA_TREE), Optional.empty());
    public static final TreeGrower MAGIC = new TreeGrower("magic", Optional.empty(), Optional.of(WerewolvesBiomeFeatures.MAGIC_TREE), Optional.empty());
}
