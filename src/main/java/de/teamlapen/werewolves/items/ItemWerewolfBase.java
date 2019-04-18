package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.util.REFERENCE;

import net.minecraft.item.Item;

public class ItemWerewolfBase extends Item {
    private String regName;

    public ItemWerewolfBase(String name) {
        regName = name;
        this.setCreativeTab(WerewolvesMod.creativeTab);
        this.setRegistryName(REFERENCE.MODID, regName);
        this.setUnlocalizedName(REFERENCE.MODID + "." + regName);
    }
}
