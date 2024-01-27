package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.mixin.FireBlockAccessor;
import net.minecraft.world.level.block.Blocks;

public class LeavesBlock extends net.minecraft.world.level.block.LeavesBlock {

    public LeavesBlock(Properties properties) {
        super(properties);
        ((FireBlockAccessor) Blocks.FIRE).invokeSetFireInfo_werewolves(this, 30, 60);
    }
}
