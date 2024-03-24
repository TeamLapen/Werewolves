package de.teamlapen.werewolves.world.tree;

import de.teamlapen.vampirism.world.gen.VampirismFeatures;
import de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class WTreeGrower {

    public static final TreeGrower JACARANDA = new TreeGrower("jacaranda", Optional.empty(), Optional.of(WerewolvesBiomeFeatures.JACARANDA_TREE), Optional.empty());
    public static final TreeGrower MAGIC = new TreeGrower("magic", Optional.empty(), Optional.of(WerewolvesBiomeFeatures.MAGIC_TREE), Optional.empty());
}
