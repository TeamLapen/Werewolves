package de.teamlapen.werewolves.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.material.Material;

public class SaplingBlock extends net.minecraft.world.level.block.SaplingBlock {

    public SaplingBlock(AbstractTreeGrower treeIn) {
        super(treeIn, Block.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
    }
}
