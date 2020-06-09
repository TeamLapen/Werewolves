package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.block.Block;

public class WerewolfBlock extends Block {

    public WerewolfBlock(String regName, Properties properties) {
        super(properties);
        this.setRegistryName(REFERENCE.MODID, regName);
    }
}
