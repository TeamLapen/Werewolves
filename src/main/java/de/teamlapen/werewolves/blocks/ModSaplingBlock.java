package de.teamlapen.werewolves.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;

public class ModSaplingBlock extends SaplingBlock {
    public ModSaplingBlock(Tree treeIn) {
        super(treeIn, Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0f).sound(SoundType.GRASS));
    }
}
