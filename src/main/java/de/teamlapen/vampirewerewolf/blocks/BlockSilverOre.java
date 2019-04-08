package de.teamlapen.vampirewerewolf.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSilverOre extends VampireWerewolfBlock {
    private static final String name = "silver_ore";

    public BlockSilverOre() {
        super(name, Material.ROCK);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.STONE);
        this.setHarvestLevel("pickaxe", 2);
    }
}
