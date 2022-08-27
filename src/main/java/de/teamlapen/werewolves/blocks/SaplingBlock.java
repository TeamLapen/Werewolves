package de.teamlapen.werewolves.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

public class SaplingBlock extends net.minecraft.world.level.block.SaplingBlock {

    public SaplingBlock(@NotNull AbstractTreeGrower treeIn) {
        super(treeIn, Block.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
    }
}
