package de.teamlapen.werewolves.blocks;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class LeavesBlock extends net.minecraft.world.level.block.LeavesBlock {

    public LeavesBlock(Properties properties) {
        super(properties);
        ((FireBlock) Blocks.FIRE).setFlammable(this, 30, 60);
    }
}
