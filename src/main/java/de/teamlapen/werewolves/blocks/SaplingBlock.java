package de.teamlapen.werewolves.blocks;

import de.teamlapen.lib.lib.util.UtilLib;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.material.PushReaction;

public class SaplingBlock extends net.minecraft.world.level.block.SaplingBlock {

    public SaplingBlock(AbstractTreeGrower treeIn) {
        super(treeIn, Block.Properties.of().isViewBlocking(UtilLib::never).ignitedByLava().pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
    }
}
