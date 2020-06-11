package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.item.Item;

public class WerewolvesItem extends Item {
    public WerewolvesItem(String regName, Properties properties) {
        super(properties);
        this.setRegistryName(REFERENCE.MODID, regName);
    }
}
