package de.teamlapen.werewolves.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

public class ModSaplingBlock extends SaplingBlock {
    public ModSaplingBlock(AbstractTreeGrower treeIn) {
        super(treeIn, Block.Properties.of(Material.PLANT).noCollission().randomTicks().strength(0.0f).sound(SoundType.GRASS));
    }
}
