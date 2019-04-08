package de.teamlapen.vampirewerewolf.items;

import de.teamlapen.vampirewerewolf.VampireWerewolfMod;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import net.minecraft.item.Item;

public class ItemWerewolfBase extends Item {
    private String regName;

    public ItemWerewolfBase(String name) {
        regName = name;
        this.setCreativeTab(VampireWerewolfMod.creativeTab);
        this.setRegistryName(REFERENCE.MODID, regName);
        this.setUnlocalizedName(REFERENCE.MODID + "." + regName);
    }
}
